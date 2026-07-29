package server.localadmin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public final class LicenseService {

    private static final Object LOCK = new Object();
    private static final Path STORE_PATH = Paths.get("config", "license-store.properties");
    private static final String CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    private LicenseService() {
    }

    public static CreatedLicense createLicense(int days, String note) throws Exception {
        if (days <= 0) {
            throw new IllegalArgumentException("So ngay phai lon hon 0.");
        }
        synchronized (LOCK) {
            LicenseStore store = loadStore();
            long now = System.currentTimeMillis();
            long expiresAt = now + days * 24L * 60L * 60L * 1000L;
            String code = nextUniqueCode(store.records);

            LicenseRecord record = new LicenseRecord();
            record.code = code;
            record.createdAt = now;
            record.expiresAt = expiresAt;
            record.days = days;
            record.note = safe(note);
            record.status = "active";
            store.records.put(code, record);
            saveStore(store);

            return new CreatedLicense(code, now, expiresAt, days, record.note);
        }
    }

    public static ValidationResult validateLicense(
            String rawCode,
            String rawMachineId,
            String rawDeviceName,
            String rawMachineUser,
            String rawAppBuild,
            String rawRemoteAddress
    ) throws Exception {
        String code = normalizeCode(rawCode);
        String machineId = normalizeMachineId(rawMachineId);
        String deviceName = safe(rawDeviceName);
        String machineUser = safe(rawMachineUser);
        String appBuild = safe(rawAppBuild);
        String remoteAddress = safe(rawRemoteAddress);

        synchronized (LOCK) {
            LicenseStore store = loadStore();
            LicenseRecord record = store.records.get(code);
            long now = System.currentTimeMillis();

            if (record == null) {
                return ValidationResult.fail("Khong tim thay ma kich hoat.", now, 0L, "", false);
            }
            if (!"active".equalsIgnoreCase(record.status)) {
                return ValidationResult.fail("Ma kich hoat da bi khoa.", now, record.expiresAt, record.machineId, false);
            }
            if (record.expiresAt <= now) {
                return ValidationResult.fail(
                        "Ma kich hoat da het han vao " + formatTime(record.expiresAt) + ".",
                        now,
                        record.expiresAt,
                        record.machineId,
                        false
                );
            }

            boolean newlyBound = false;
            if (record.machineId.length() == 0) {
                record.machineId = machineId;
                record.deviceName = deviceName;
                record.machineUser = machineUser;
                record.boundAt = now;
                newlyBound = true;
            } else if (!record.machineId.equalsIgnoreCase(machineId)) {
                return ValidationResult.fail(
                        "Ma kich hoat nay da duoc kich hoat tren may khac.",
                        now,
                        record.expiresAt,
                        record.machineId,
                        false
                );
            }

            record.deviceName = deviceName.length() == 0 ? record.deviceName : deviceName;
            record.machineUser = machineUser.length() == 0 ? record.machineUser : machineUser;
            record.appBuild = appBuild.length() == 0 ? record.appBuild : appBuild;
            record.remoteAddress = remoteAddress.length() == 0 ? record.remoteAddress : remoteAddress;
            record.lastSeenAt = now;
            saveStore(store);

            return ValidationResult.ok(
                    newlyBound ? "Da kich hoat ma thanh cong." : "Ma kich hoat hop le.",
                    now,
                    record.expiresAt,
                    record.machineId,
                    newlyBound
            );
        }
    }

    private static LicenseStore loadStore() throws IOException {
        LicenseStore store = new LicenseStore();
        if (!Files.exists(STORE_PATH)) {
            return store;
        }

        Properties properties = new Properties();
        InputStream input = Files.newInputStream(STORE_PATH);
        try {
            properties.load(input);
        } finally {
            input.close();
        }

        int count = parseInt(properties.getProperty("license.count"), 0);
        for (int i = 0; i < count; i++) {
            String prefix = "license." + i + ".";
            String code = normalizeCode(properties.getProperty(prefix + "code"));
            if (code.length() == 0) {
                continue;
            }
            LicenseRecord record = new LicenseRecord();
            record.code = code;
            record.createdAt = parseLong(properties.getProperty(prefix + "created_at"), 0L);
            record.expiresAt = parseLong(properties.getProperty(prefix + "expires_at"), 0L);
            record.days = parseInt(properties.getProperty(prefix + "days"), 0);
            record.note = safe(properties.getProperty(prefix + "note"));
            record.status = safe(properties.getProperty(prefix + "status"));
            if (record.status.length() == 0) {
                record.status = "active";
            }
            record.machineId = safe(properties.getProperty(prefix + "machine_id"));
            record.deviceName = safe(properties.getProperty(prefix + "device_name"));
            record.machineUser = safe(properties.getProperty(prefix + "machine_user"));
            record.appBuild = safe(properties.getProperty(prefix + "app_build"));
            record.remoteAddress = safe(properties.getProperty(prefix + "remote_address"));
            record.boundAt = parseLong(properties.getProperty(prefix + "bound_at"), 0L);
            record.lastSeenAt = parseLong(properties.getProperty(prefix + "last_seen_at"), 0L);
            store.records.put(code, record);
        }
        return store;
    }

    private static void saveStore(LicenseStore store) throws IOException {
        Files.createDirectories(STORE_PATH.getParent());
        Properties properties = new Properties();

        List<LicenseRecord> records = new ArrayList<LicenseRecord>(store.records.values());
        Collections.sort(records, new Comparator<LicenseRecord>() {
            @Override
            public int compare(LicenseRecord left, LicenseRecord right) {
                if (left == right) {
                    return 0;
                }
                return Long.compare(right.createdAt, left.createdAt);
            }
        });

        properties.setProperty("license.count", Integer.toString(records.size()));
        for (int i = 0; i < records.size(); i++) {
            LicenseRecord record = records.get(i);
            String prefix = "license." + i + ".";
            properties.setProperty(prefix + "code", record.code);
            properties.setProperty(prefix + "created_at", Long.toString(record.createdAt));
            properties.setProperty(prefix + "expires_at", Long.toString(record.expiresAt));
            properties.setProperty(prefix + "days", Integer.toString(record.days));
            properties.setProperty(prefix + "note", record.note);
            properties.setProperty(prefix + "status", record.status);
            properties.setProperty(prefix + "machine_id", record.machineId);
            properties.setProperty(prefix + "device_name", record.deviceName);
            properties.setProperty(prefix + "machine_user", record.machineUser);
            properties.setProperty(prefix + "app_build", record.appBuild);
            properties.setProperty(prefix + "remote_address", record.remoteAddress);
            properties.setProperty(prefix + "bound_at", Long.toString(record.boundAt));
            properties.setProperty(prefix + "last_seen_at", Long.toString(record.lastSeenAt));
        }

        Path tempFile = Files.createTempFile(STORE_PATH.getParent(), "license-store.", ".tmp");
        try {
            OutputStream output = Files.newOutputStream(tempFile);
            try {
                properties.store(output, "KPAH license store");
            } finally {
                output.close();
            }
            try {
                Files.move(tempFile, STORE_PATH, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ignored) {
                Files.move(tempFile, STORE_PATH, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static String nextUniqueCode(Map<String, LicenseRecord> records) {
        for (int attempt = 0; attempt < 200; attempt++) {
            String code = generateCode();
            if (!records.containsKey(code)) {
                return code;
            }
        }
        throw new IllegalStateException("Khong tao duoc ma kich hoat duy nhat.");
    }

    private static String generateCode() {
        StringBuilder builder = new StringBuilder("KPAH");
        for (int group = 0; group < 5; group++) {
            builder.append('-');
            for (int i = 0; i < 4; i++) {
                builder.append(CODE_ALPHABET.charAt(RANDOM.nextInt(CODE_ALPHABET.length())));
            }
        }
        return builder.toString();
    }

    private static String normalizeCode(String value) {
        if (value == null) {
            return "";
        }
        String text = value.trim().toUpperCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || ch == '-') {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private static String normalizeMachineId(String value) {
        String text = safe(value).toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9-]", "");
        if (text.length() == 0) {
            throw new IllegalArgumentException("Thieu ma may.");
        }
        return text;
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
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

    public static String formatTime(long epochMillis) {
        if (epochMillis <= 0L) {
            return "";
        }
        return DISPLAY_TIME.format(Instant.ofEpochMilli(epochMillis));
    }

    private static final class LicenseStore {
        private final LinkedHashMap<String, LicenseRecord> records = new LinkedHashMap<String, LicenseRecord>();
    }

    private static final class LicenseRecord {
        private String code = "";
        private long createdAt;
        private long expiresAt;
        private int days;
        private String note = "";
        private String status = "active";
        private String machineId = "";
        private String deviceName = "";
        private String machineUser = "";
        private String appBuild = "";
        private String remoteAddress = "";
        private long boundAt;
        private long lastSeenAt;
    }

    public static final class CreatedLicense {
        public final String code;
        public final long createdAt;
        public final long expiresAt;
        public final int days;
        public final String note;

        private CreatedLicense(String code, long createdAt, long expiresAt, int days, String note) {
            this.code = code;
            this.createdAt = createdAt;
            this.expiresAt = expiresAt;
            this.days = days;
            this.note = note;
        }
    }

    public static final class ValidationResult {
        public final boolean ok;
        public final String message;
        public final long serverTime;
        public final long expiresAt;
        public final String boundMachineId;
        public final boolean newlyBound;

        private ValidationResult(
                boolean ok,
                String message,
                long serverTime,
                long expiresAt,
                String boundMachineId,
                boolean newlyBound
        ) {
            this.ok = ok;
            this.message = message;
            this.serverTime = serverTime;
            this.expiresAt = expiresAt;
            this.boundMachineId = boundMachineId == null ? "" : boundMachineId;
            this.newlyBound = newlyBound;
        }

        private static ValidationResult ok(
                String message,
                long serverTime,
                long expiresAt,
                String boundMachineId,
                boolean newlyBound
        ) {
            return new ValidationResult(true, message, serverTime, expiresAt, boundMachineId, newlyBound);
        }

        private static ValidationResult fail(
                String message,
                long serverTime,
                long expiresAt,
                String boundMachineId,
                boolean newlyBound
        ) {
            return new ValidationResult(false, message, serverTime, expiresAt, boundMachineId, newlyBound);
        }
    }
}
