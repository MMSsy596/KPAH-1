import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public final class KpahLicenseAdminTool {
    private static final String APP_NAME = "KPAH License Admin";
    private static final String DEFAULT_SERVER_URL = "http://127.0.0.1:18023";
    private static final String CREATE_PATH = "/api/license/admin/create";
    private static final ServerConfig DEFAULT_CONFIG = detectDefaultConfig();

    private KpahLicenseAdminTool() {
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            try {
                runCli(args);
            } catch (Throwable error) {
                error.printStackTrace();
                System.err.println(messageOf(error));
                System.exit(1);
            }
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminWindow().show();
                } catch (Throwable error) {
                    error.printStackTrace();
                    showError(null, error);
                }
            }
        });
    }

    private static void runCli(String[] args) throws Exception {
        CliConfig config = CliConfig.parse(args);
        CreatedLicense created = requestCreateLicense(config.serverUrl, config.token, config.days, config.note);
        System.out.println("Code   : " + created.code);
        System.out.println("Days   : " + created.days);
        System.out.println("Expire : " + created.expiresAtText);
        System.out.println("Note   : " + created.note);
    }

    private static CreatedLicense requestCreateLicense(String rawServerUrl, String rawToken, int days, String note) throws Exception {
        if (days <= 0) {
            throw new IllegalArgumentException("So ngay phai lon hon 0.");
        }
        String serverUrl = normalizeServerUrl(rawServerUrl);
        if (serverUrl.length() == 0) {
            throw new IllegalArgumentException("URL server khong hop le.");
        }

        String body = "days=" + encode(Integer.toString(days)) + "&note=" + encode(safe(note));
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serverUrl + CREATE_PATH);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            String token = safe(rawToken);
            if (token.length() > 0) {
                connection.setRequestProperty("X-Admin-Token", token);
            }
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

            boolean ok = "1".equals(properties.getProperty("ok"));
            String message = fallback(properties.getProperty("message"), "Khong nhan duoc phan hoi tu server.");
            if (!ok || statusCode >= 400) {
                throw new IllegalStateException(message);
            }

            return new CreatedLicense(
                fallback(properties.getProperty("license_code"), ""),
                parseInt(properties.getProperty("days"), days),
                parseLong(properties.getProperty("expires_at"), 0L),
                fallback(properties.getProperty("expires_at_text"), ""),
                fallback(properties.getProperty("note"), "")
            );
        } catch (Exception error) {
            throw new IllegalStateException("Khong tao duoc ma tren server: " + messageOf(error), error);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void showError(Component parent, Throwable error) {
        JOptionPane.showMessageDialog(parent, messageOf(error), APP_NAME, JOptionPane.ERROR_MESSAGE);
    }

    private static String messageOf(Throwable error) {
        String message = error == null ? null : error.getMessage();
        if (message == null || message.trim().length() == 0) {
            return String.valueOf(error);
        }
        return message.trim();
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

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private static String encode(String value) throws Exception {
        return URLEncoder.encode(value == null ? "" : value, "UTF-8");
    }

    private static String fallback(String value, String defaultValue) {
        String text = safe(value);
        return text.length() == 0 ? defaultValue : text;
    }

    private static ServerConfig detectDefaultConfig() {
        ServerConfig config = loadServerConfig(Paths.get("server.ini"));
        if (config != null) {
            return config;
        }

        Path appDir = resolveAppDirectory();
        if (appDir != null) {
            config = loadServerConfig(appDir.resolve("server.ini"));
            if (config != null) {
                return config;
            }
            Path parent = appDir.getParent();
            if (parent != null) {
                config = loadServerConfig(parent.resolve("server.ini"));
                if (config != null) {
                    return config;
                }
            }
        }

        return new ServerConfig(DEFAULT_SERVER_URL, "");
    }

    private static Path resolveAppDirectory() {
        try {
            CodeSource codeSource = KpahLicenseAdminTool.class.getProtectionDomain().getCodeSource();
            if (codeSource == null || codeSource.getLocation() == null) {
                return null;
            }
            Path location = Paths.get(codeSource.getLocation().toURI());
            if (Files.isRegularFile(location)) {
                return location.getParent();
            }
            if (Files.isDirectory(location)) {
                return location;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static ServerConfig loadServerConfig(Path configPath) {
        if (configPath == null || !Files.isRegularFile(configPath)) {
            return null;
        }
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(configPath.toFile())) {
            props.load(input);
            String host = fallback(props.getProperty("sv.localAdminHost"), "127.0.0.1");
            int port = parseInt(props.getProperty("sv.localAdminPort"), 18023);
            String token = fallback(props.getProperty("sv.localAdminToken"), "");
            return new ServerConfig("http://" + host + ":" + port, token);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static int parseInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception ignored) {
            return defaultValue;
        }
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

    private static final class AdminWindow {
        private final JFrame frame;
        private final JTextField serverUrlField;
        private final JTextField tokenField;
        private final JSpinner daysSpinner;
        private final JTextField noteField;
        private final JTextArea resultArea;
        private final JLabel statusLabel;

        private AdminWindow() {
            this.frame = new JFrame(APP_NAME);
            this.serverUrlField = new JTextField(DEFAULT_CONFIG.serverUrl, 32);
            this.tokenField = new JTextField(DEFAULT_CONFIG.token, 32);
            this.daysSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 3650, 1));
            this.noteField = new JTextField("", 32);
            this.resultArea = new JTextArea();
            this.statusLabel = new JLabel("Nhap so ngay va bam Tao ma.");
            buildUi();
        }

        private void show() {
            frame.setVisible(true);
        }

        private void buildUi() {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(760, 500);
            frame.setMinimumSize(new Dimension(700, 430));
            frame.setLocationRelativeTo(null);

            JPanel root = new JPanel(new BorderLayout(10, 10));
            root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            frame.setContentPane(root);

            JTextArea intro = new JTextArea(
                "Tool nay tao ma tren may chu. Ma sinh ra se duoc bind vao may dau tien nhap dung ma do.\n"
                    + "Neu may khac dung lai cung ma, server se tu choi."
            );
            intro.setEditable(false);
            intro.setOpaque(false);
            intro.setLineWrap(true);
            intro.setWrapStyleWord(true);
            intro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            root.add(intro, BorderLayout.NORTH);

            JPanel form = new JPanel();
            form.setLayout(new javax.swing.BoxLayout(form, javax.swing.BoxLayout.Y_AXIS));
            form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thong tin tao ma"),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            form.add(row("Server URL", serverUrlField));
            form.add(row("Admin token", tokenField));

            JPanel daysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            daysPanel.setOpaque(false);
            daysPanel.add(new JLabel("So ngay"));
            daysPanel.add(daysSpinner);
            JButton weekButton = new JButton("7 ngay");
            JButton monthButton = new JButton("30 ngay");
            weekButton.addActionListener(event -> daysSpinner.setValue(Integer.valueOf(7)));
            monthButton.addActionListener(event -> daysSpinner.setValue(Integer.valueOf(30)));
            daysPanel.add(weekButton);
            daysPanel.add(monthButton);
            form.add(daysPanel);
            form.add(row("Ghi chu", noteField));

            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            actionPanel.setOpaque(false);
            JButton createButton = new JButton("Tao ma");
            JButton copyButton = new JButton("Sao chep ma");
            createButton.addActionListener(event -> createCode());
            copyButton.addActionListener(event -> copyCode());
            actionPanel.add(createButton);
            actionPanel.add(copyButton);
            form.add(actionPanel);

            root.add(form, BorderLayout.CENTER);

            resultArea.setEditable(false);
            resultArea.setLineWrap(true);
            resultArea.setWrapStyleWord(true);
            resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
            resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            JScrollPane resultScroll = new JScrollPane(resultArea);
            resultScroll.setPreferredSize(new Dimension(0, 180));
            resultScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ma kich hoat"),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            ));
            JPanel bottom = new JPanel(new BorderLayout(0, 6));
            bottom.setOpaque(false);
            bottom.add(resultScroll, BorderLayout.CENTER);
            statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 2, 0, 2));
            bottom.add(statusLabel, BorderLayout.SOUTH);
            root.add(bottom, BorderLayout.SOUTH);
        }

        private JPanel row(String label, JTextField field) {
            JPanel panel = new JPanel(new BorderLayout(8, 4));
            panel.setOpaque(false);
            JLabel title = new JLabel(label);
            title.setPreferredSize(new Dimension(90, 24));
            panel.add(title, BorderLayout.WEST);
            panel.add(field, BorderLayout.CENTER);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            return panel;
        }

        private void createCode() {
            try {
                int days = ((Integer)daysSpinner.getValue()).intValue();
                CreatedLicense created = requestCreateLicense(
                    serverUrlField.getText(),
                    tokenField.getText(),
                    days,
                    noteField.getText()
                );
                resultArea.setText(created.code);
                resultArea.setCaretPosition(0);
                statusLabel.setText("Da tao ma " + created.code + " | Het han: " + created.expiresAtText);
            } catch (Throwable error) {
                showError(frame, error);
            }
        }

        private void copyCode() {
            String code = resultArea.getText().trim();
            if (code.length() == 0) {
                showError(frame, new IllegalStateException("Chua co ma de sao chep."));
                return;
            }
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(code), null);
            statusLabel.setText("Da sao chep ma vao clipboard.");
        }
    }

    private static final class CliConfig {
        private final String serverUrl;
        private final String token;
        private final int days;
        private final String note;

        private CliConfig(String serverUrl, String token, int days, String note) {
            this.serverUrl = serverUrl;
            this.token = token;
            this.days = days;
            this.note = note;
        }

        private static CliConfig parse(String[] args) {
            String serverUrl = DEFAULT_CONFIG.serverUrl;
            String token = DEFAULT_CONFIG.token;
            int days = 7;
            String note = "";

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if ("--server-url".equalsIgnoreCase(arg)) {
                    serverUrl = requireValue(args, ++i, "--server-url");
                } else if ("--token".equalsIgnoreCase(arg)) {
                    token = requireValue(args, ++i, "--token");
                } else if ("--days".equalsIgnoreCase(arg)) {
                    days = parseInt(requireValue(args, ++i, "--days"), 7);
                } else if ("--note".equalsIgnoreCase(arg)) {
                    note = requireValue(args, ++i, "--note");
                } else if ("--help".equalsIgnoreCase(arg) || "-h".equalsIgnoreCase(arg)) {
                    printUsageAndExit();
                } else {
                    throw new IllegalArgumentException("Tham so khong ho tro: " + arg);
                }
            }

            if (days <= 0) {
                throw new IllegalArgumentException("So ngay phai lon hon 0.");
            }
            return new CliConfig(serverUrl, token, days, note);
        }

        private static String requireValue(String[] args, int index, String label) {
            if (index >= args.length) {
                throw new IllegalArgumentException("Thieu gia tri cho " + label + ".");
            }
            return args[index];
        }

        private static void printUsageAndExit() {
            System.out.println("Usage:");
            System.out.println("  java -jar kpah-license-admin.jar --server-url http://127.0.0.1:18023 --days 30");
            System.out.println("  java -jar kpah-license-admin.jar --server-url http://127.0.0.1:18023 --token xxx --days 7 --note vip");
            System.exit(0);
        }
    }

    private static final class CreatedLicense {
        private final String code;
        private final int days;
        private final long expiresAt;
        private final String expiresAtText;
        private final String note;

        private CreatedLicense(String code, int days, long expiresAt, String expiresAtText, String note) {
            this.code = code;
            this.days = days;
            this.expiresAt = expiresAt;
            this.expiresAtText = expiresAtText;
            this.note = note;
        }
    }

    private static final class ServerConfig {
        private final String serverUrl;
        private final String token;

        private ServerConfig(String serverUrl, String token) {
            this.serverUrl = fallback(serverUrl, DEFAULT_SERVER_URL);
            this.token = fallback(token, "");
        }
    }
}
