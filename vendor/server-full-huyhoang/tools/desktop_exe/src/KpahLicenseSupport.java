import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

final class KpahLicenseSupport {
    private static final String DEFAULT_LICENSE_SERVER_URL = "http://163.61.183.129:18023";
    private static final String VALIDATE_PATH = "/api/license/validate";
    private static final long OFFLINE_GRACE_MS = 5L * 60L * 1000L;

    private KpahLicenseSupport() {
    }

    static StoredLicenseStatus ensureStoredLicense(Path appDir) throws Exception {
        return ensureStoredLicense(appDir, false);
    }

    static StoredLicenseStatus ensureStoredLicense(Path appDir, boolean allowGrace) throws Exception {
        StoredLicenseStatus status = readStatus(appDir, allowGrace);
        if (!status.valid) {
            throw new IllegalStateException(status.startupErrorMessage());
        }
        return status;
    }

    static StoredLicenseStatus ensureInteractiveLicense(Path appDir, String appName, Component parent) throws Exception {
        StoredLicenseStatus status = readStatus(appDir, false);
        if (status.valid) {
            return status;
        }
        return promptForActivation(appDir, appName, parent, status);
    }

    static StoredLicenseStatus promptForActivation(Path appDir, String appName, Component parent) throws Exception {
        return promptForActivation(appDir, appName, parent, readStatus(appDir, false));
    }

    static StoredLicenseStatus readStatus(Path appDir) throws Exception {
        return readStatus(appDir, false);
    }

    static StoredLicenseStatus readStatus(Path appDir, boolean allowGrace) throws Exception {
        Path licenseFile = appDir.resolve(KpahLicenseCodec.LICENSE_FILE_NAME);
        String deviceId = KpahLicenseCodec.currentDeviceId();
        LocalLicenseConfig local = loadLocalConfig(appDir, licenseFile);
        String serverUrl = local.serverUrl;

        if (local.code.length() == 0) {
            return StoredLicenseStatus.missing(licenseFile, deviceId, serverUrl);
        }

        try {
            ServerValidation validation = validateRemote(serverUrl, local.code, deviceId);
            StoredLicenseStatus status = StoredLicenseStatus.valid(licenseFile, deviceId, local.code, serverUrl, validation, false);
            persistStatus(status);
            return status;
        } catch (Exception error) {
            long now = System.currentTimeMillis();
            if (allowGrace
                && local.lastValidatedAt > 0L
                && local.expiresAt > now
                && now - local.lastValidatedAt <= OFFLINE_GRACE_MS) {
                return StoredLicenseStatus.valid(
                    licenseFile,
                    deviceId,
                    local.code,
                    serverUrl,
                    new ServerValidation("Tam chap nhan license trong thoi gian grace.", local.expiresAt, deviceId, false),
                    true
                );
            }
            return StoredLicenseStatus.invalid(licenseFile, deviceId, local.code, serverUrl, messageOf(error));
        }
    }

    static String footerSummary(StoredLicenseStatus status) {
        if (status == null) {
            return "License: chua xac dinh";
        }
        if (!status.valid) {
            return "License: chua kich hoat";
        }
        long now = System.currentTimeMillis();
        if (status.expiresAtMillis > 0L && status.expiresAtMillis < now) {
            return "License: da het han";
        }
        String summary = "License: den " + KpahLicenseCodec.formatTimestamp(status.expiresAtMillis);
        if (status.graceMode) {
            summary += " (tam mat ket noi server)";
        }
        return summary;
    }

    private static StoredLicenseStatus promptForActivation(
        Path appDir,
        String appName,
        Component parent,
        StoredLicenseStatus initialStatus
    ) throws Exception {
        StoredLicenseStatus status = initialStatus;
        String pastedCode = status.code == null ? "" : status.code;
        String serverUrl = status.serverUrl == null || status.serverUrl.length() == 0 ? DEFAULT_LICENSE_SERVER_URL : status.serverUrl;
        while (true) {
            JTextField deviceField = new JTextField(status.deviceId, 28);
            deviceField.setEditable(false);
            deviceField.setCaretPosition(0);

            JTextField serverField = new JTextField(serverUrl, 28);
            serverField.setCaretPosition(0);

            JTextArea statusArea = new JTextArea(status.dialogMessage());
            statusArea.setEditable(false);
            statusArea.setLineWrap(true);
            statusArea.setWrapStyleWord(true);
            statusArea.setOpaque(false);
            statusArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

            JTextArea codeArea = new JTextArea(pastedCode, 5, 28);
            codeArea.setLineWrap(true);
            codeArea.setWrapStyleWord(true);
            JScrollPane codeScroll = new JScrollPane(codeArea);
            codeScroll.setPreferredSize(new Dimension(0, 96));

            JPanel form = new JPanel(new BorderLayout(0, 6));
            form.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            form.add(statusArea, BorderLayout.NORTH);

            JPanel center = new JPanel(new BorderLayout(0, 4));
            center.add(new JLabel("Ma may"), BorderLayout.NORTH);
            center.add(deviceField, BorderLayout.CENTER);
            form.add(center, BorderLayout.CENTER);

            JPanel serverPanel = new JPanel(new BorderLayout(0, 4));
            serverPanel.add(new JLabel("URL server"), BorderLayout.NORTH);
            serverPanel.add(serverField, BorderLayout.CENTER);

            JPanel bottom = new JPanel(new BorderLayout(0, 4));
            bottom.add(serverPanel, BorderLayout.NORTH);
            JPanel codePanel = new JPanel(new BorderLayout(0, 4));
            codePanel.add(new JLabel("Dan ma kich hoat"), BorderLayout.NORTH);
            codePanel.add(codeScroll, BorderLayout.CENTER);
            bottom.add(codePanel, BorderLayout.CENTER);
            form.add(bottom, BorderLayout.SOUTH);

            Object[] options = new Object[]{"Kich hoat", "Dong"};
            int result = JOptionPane.showOptionDialog(
                parent,
                form,
                appName + " - Kich hoat license",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );
            if (result != 0) {
                return null;
            }

            pastedCode = codeArea.getText();
            serverUrl = serverField.getText();
            String code = normalizeCode(pastedCode);
            if (code.length() == 0) {
                status = StoredLicenseStatus.invalid(status.licenseFile, status.deviceId, null, serverUrl, "Ban chua dan ma kich hoat.");
                continue;
            }
            if (normalizeServerUrl(serverUrl).length() == 0) {
                status = StoredLicenseStatus.invalid(status.licenseFile, status.deviceId, code, serverUrl, "Ban chua nhap URL server.");
                continue;
            }

            try {
                StoredLicenseStatus installed = installCode(appDir, code, serverUrl);
                JOptionPane.showMessageDialog(
                    parent,
                    "Kich hoat thanh cong.\nHet han: " + KpahLicenseCodec.formatTimestamp(installed.expiresAtMillis),
                    appName,
                    JOptionPane.INFORMATION_MESSAGE
                );
                return installed;
            } catch (Exception error) {
                status = StoredLicenseStatus.invalid(status.licenseFile, status.deviceId, code, serverUrl, messageOf(error));
            }
        }
    }

    private static StoredLicenseStatus installCode(Path appDir, String rawCode, String rawServerUrl) throws Exception {
        String deviceId = KpahLicenseCodec.currentDeviceId();
        String code = normalizeCode(rawCode);
        String serverUrl = normalizeServerUrl(rawServerUrl);
        if (serverUrl.length() == 0) {
            throw new IllegalArgumentException("URL server khong hop le.");
        }
        ServerValidation validation = validateRemote(serverUrl, code, deviceId);
        StoredLicenseStatus status = StoredLicenseStatus.valid(
            appDir.resolve(KpahLicenseCodec.LICENSE_FILE_NAME),
            deviceId,
            code,
            serverUrl,
            validation,
            false
        );
        persistStatus(status);
        return status;
    }

    private static void persistStatus(StoredLicenseStatus status) throws IOException {
        Path licenseFile = status.licenseFile;
        Properties existing = new Properties();
        if (Files.exists(licenseFile)) {
            InputStream input = Files.newInputStream(licenseFile);
            try {
                existing.load(input);
            } finally {
                input.close();
            }
            long lastValidatedAt = parseLong(existing.getProperty("last_validated_at"), 0L);
            if (status.code.equals(normalizeCode(existing.getProperty("license.code")))
                && status.serverUrl.equals(normalizeServerUrl(existing.getProperty("license.server_url")))
                && status.expiresAtMillis == parseLong(existing.getProperty("expires.at"), 0L)
                && System.currentTimeMillis() - lastValidatedAt < 60000L) {
                return;
            }
        }
        Properties properties = new Properties();
        properties.setProperty("license.code", status.code);
        properties.setProperty("license.server_url", status.serverUrl);
        properties.setProperty("device.id", status.deviceId);
        properties.setProperty("expires.at", Long.toString(status.expiresAtMillis));
        properties.setProperty("last_validated_at", Long.toString(System.currentTimeMillis()));
        properties.setProperty("bound.machine_id", status.boundMachineId == null ? "" : status.boundMachineId);
        writePropertiesAtomically(licenseFile, properties, "KPAH online license");
    }

    private static LocalLicenseConfig loadLocalConfig(Path appDir, Path licenseFile) throws Exception {
        Properties properties = new Properties();
        if (Files.exists(licenseFile)) {
            InputStream input = Files.newInputStream(licenseFile);
            try {
                properties.load(input);
            } finally {
                input.close();
            }
        }
        String serverUrl = resolveServerUrl(appDir, properties);
        return new LocalLicenseConfig(
            normalizeCode(properties.getProperty("license.code")),
            serverUrl,
            parseLong(properties.getProperty("expires.at"), 0L),
            parseLong(properties.getProperty("last_validated_at"), 0L)
        );
    }

    private static String resolveServerUrl(Path appDir, Properties properties) {
        String systemProperty = System.getProperty("kpah.license.server.url");
        if (normalizeServerUrl(systemProperty).length() > 0) {
            return normalizeServerUrl(systemProperty);
        }
        String envValue = System.getenv("KPAH_LICENSE_SERVER_URL");
        if (normalizeServerUrl(envValue).length() > 0) {
            return normalizeServerUrl(envValue);
        }
        String storedValue = properties == null ? null : properties.getProperty("license.server_url");
        if (normalizeServerUrl(storedValue).length() > 0) {
            return normalizeServerUrl(storedValue);
        }
        String autoConfigHost = readCustomHost(appDir.resolve("kpah-auto.properties"));
        if (autoConfigHost.length() > 0) {
            return "http://" + autoConfigHost + ":18023";
        }
        Path profileRoot = appDir.resolve("profiles");
        if (Files.isDirectory(profileRoot)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(profileRoot)) {
                for (Path dir : stream) {
                    if (!Files.isDirectory(dir)) {
                        continue;
                    }
                    String host = readCustomHost(dir.resolve("kpah-auto.properties"));
                    if (host.length() > 0) {
                        return "http://" + host + ":18023";
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return DEFAULT_LICENSE_SERVER_URL;
    }

    private static String readCustomHost(Path configPath) {
        if (configPath == null || !Files.isRegularFile(configPath)) {
            return "";
        }
        Properties properties = new Properties();
        try {
            InputStream input = Files.newInputStream(configPath);
            try {
                properties.load(input);
            } finally {
                input.close();
            }
            return safe(properties.getProperty("custom_host"));
        } catch (Exception ignored) {
            return "";
        }
    }

    private static ServerValidation validateRemote(String serverUrl, String code, String deviceId) throws Exception {
        String normalizedServerUrl = normalizeServerUrl(serverUrl);
        if (normalizedServerUrl.length() == 0) {
            throw new IllegalArgumentException("URL server khong hop le.");
        }

        String body =
            "code=" + encode(code)
                + "&machine_id=" + encode(deviceId)
                + "&device_name=" + encode(safe(System.getenv("COMPUTERNAME")))
                + "&machine_user=" + encode(safe(System.getProperty("user.name")))
                + "&app_build=" + encode("desktop-portable");

        HttpURLConnection connection = null;
        try {
            URL url = new URL(normalizedServerUrl + VALIDATE_PATH);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            byte[] payload = body.getBytes(StandardCharsets.UTF_8);
            OutputStream output = connection.getOutputStream();
            try {
                output.write(payload);
            } finally {
                output.close();
            }

            int statusCode = connection.getResponseCode();
            Properties properties = new Properties();
            InputStream input = statusCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
            if (input != null) {
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            }
            String message = fallback(properties.getProperty("message"), "Khong nhan duoc phan hoi tu license server.");
            boolean ok = "1".equals(properties.getProperty("ok"));
            long expiresAt = parseLong(properties.getProperty("expires_at"), 0L);
            String boundMachineId = safe(properties.getProperty("bound_machine_id"));
            boolean newlyBound = "1".equals(properties.getProperty("newly_bound"));
            if (!ok || statusCode >= 400) {
                throw new IllegalStateException(message);
            }
            return new ServerValidation(message, expiresAt, boundMachineId, newlyBound);
        } catch (IOException error) {
            throw new IllegalStateException("Khong ket noi duoc license server: " + error.getMessage(), error);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void writePropertiesAtomically(Path path, Properties properties, String comments) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        String fileName = path.getFileName() == null ? "license.properties" : path.getFileName().toString();
        Path tempFile = Files.createTempFile(parent, fileName + ".", ".tmp");
        try {
            OutputStream output = Files.newOutputStream(tempFile);
            try {
                properties.store(output, comments);
            } finally {
                output.close();
            }
            try {
                Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ignored) {
                Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static String normalizeServerUrl(String value) {
        String text = safe(value);
        if (text.length() == 0) {
            return "";
        }
        while (text.endsWith("/")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    private static String normalizeCode(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder(value.length());
        String upper = value.toUpperCase();
        for (int i = 0; i < upper.length(); i++) {
            char ch = upper.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || ch == '-') {
                builder.append(ch);
            }
        }
        return builder.toString().trim();
    }

    private static String encode(String value) throws IOException {
        return URLEncoder.encode(value == null ? "" : value, "UTF-8");
    }

    private static long parseLong(String value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    private static String fallback(String value, String defaultValue) {
        String text = safe(value);
        return text.length() == 0 ? defaultValue : text;
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private static String messageOf(Throwable error) {
        String message = error == null ? null : error.getMessage();
        if (message == null || message.trim().length() == 0) {
            return String.valueOf(error);
        }
        return message.trim();
    }

    private static final class LocalLicenseConfig {
        private final String code;
        private final String serverUrl;
        private final long expiresAt;
        private final long lastValidatedAt;

        private LocalLicenseConfig(String code, String serverUrl, long expiresAt, long lastValidatedAt) {
            this.code = code;
            this.serverUrl = serverUrl;
            this.expiresAt = expiresAt;
            this.lastValidatedAt = lastValidatedAt;
        }
    }

    private static final class ServerValidation {
        private final String message;
        private final long expiresAt;
        private final String boundMachineId;
        private final boolean newlyBound;

        private ServerValidation(String message, long expiresAt, String boundMachineId, boolean newlyBound) {
            this.message = message;
            this.expiresAt = expiresAt;
            this.boundMachineId = boundMachineId == null ? "" : boundMachineId;
            this.newlyBound = newlyBound;
        }
    }

    static final class StoredLicenseStatus {
        final Path licenseFile;
        final String deviceId;
        final String code;
        final String serverUrl;
        final boolean valid;
        final String problem;
        final long expiresAtMillis;
        final String boundMachineId;
        final boolean newlyBound;
        final boolean graceMode;

        private StoredLicenseStatus(
            Path licenseFile,
            String deviceId,
            String code,
            String serverUrl,
            boolean valid,
            String problem,
            long expiresAtMillis,
            String boundMachineId,
            boolean newlyBound,
            boolean graceMode
        ) {
            this.licenseFile = licenseFile;
            this.deviceId = deviceId;
            this.code = code;
            this.serverUrl = serverUrl;
            this.valid = valid;
            this.problem = problem;
            this.expiresAtMillis = expiresAtMillis;
            this.boundMachineId = boundMachineId;
            this.newlyBound = newlyBound;
            this.graceMode = graceMode;
        }

        static StoredLicenseStatus missing(Path licenseFile, String deviceId, String serverUrl) {
            return new StoredLicenseStatus(
                licenseFile,
                deviceId,
                "",
                serverUrl,
                false,
                "Chua co ma kich hoat.",
                0L,
                "",
                false,
                false
            );
        }

        static StoredLicenseStatus invalid(Path licenseFile, String deviceId, String code, String serverUrl, String problem) {
            return new StoredLicenseStatus(
                licenseFile,
                deviceId,
                code == null ? "" : code,
                normalizeServerUrl(serverUrl),
                false,
                problem,
                0L,
                "",
                false,
                false
            );
        }

        static StoredLicenseStatus valid(
            Path licenseFile,
            String deviceId,
            String code,
            String serverUrl,
            ServerValidation validation,
            boolean graceMode
        ) {
            return new StoredLicenseStatus(
                licenseFile,
                deviceId,
                code,
                normalizeServerUrl(serverUrl),
                true,
                validation.message,
                validation.expiresAt,
                validation.boundMachineId,
                validation.newlyBound,
                graceMode
            );
        }

        String dialogMessage() {
            StringBuilder builder = new StringBuilder();
            if (valid) {
                builder.append("License hien tai dang hop le.\n");
                builder.append("Het han: ").append(KpahLicenseCodec.formatTimestamp(expiresAtMillis)).append("\n\n");
                builder.append("Neu muon doi ma, hay dan ma moi vao o ben duoi.");
                return builder.toString();
            }
            builder.append(problem == null ? "License chua hop le." : problem).append("\n\n");
            builder.append("Ma may nay se bind vao may dau tien nhap ma.\n");
            builder.append("Cac may khac dung lai cung ma se bi tu choi.");
            return builder.toString();
        }

        String startupErrorMessage() {
            StringBuilder builder = new StringBuilder();
            builder.append(problem == null ? "License khong hop le." : problem);
            builder.append("\nMa may hien tai: ").append(deviceId);
            builder.append("\nServer license: ").append(serverUrl);
            builder.append("\nHay mo tool co giao dien de nhap ma kich hoat hop le.");
            return builder.toString();
        }
    }
}
