import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

final class KpahLicenseCodec {
    static final String LICENSE_FILE_NAME = "kpah-license.properties";
    private static final String PUBLIC_KEY_BASE64 =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAufOq5mYR6foS3hHmbKFg4UcB/GuJopi4bl5zY0GFmv3Log0XApFiIRlg6hOsOo+w44OEvARxVqODkAroZl9zDGeqWqKmV6lxWx3vfWbo40lj4t3eGO0jgluMskYqUXTdkCn+zdeBlhjWjH4zCarAnpek4IUoOGlM5d1qytou7rtCucpVxsbOo4+3MRp4uwzl1LWTVwEYReMPCZ8g/uXV2E8AY8n3OpAzDdITdGUs8FuC7+Iq+EB5tfV9CyeCHSivXjdEaTB663JLInSmSXzonD3wXjAYmiZYKcPHxZI68xsG07ZPN/h57x9jljKuoGBjXqpuQ5Gx7Vwwio372+RliQIDAQAB";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DISPLAY_TIME =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
    private static final PublicKey PUBLIC_KEY = loadPublicKey(PUBLIC_KEY_BASE64);

    private KpahLicenseCodec() {
    }

    static String currentDeviceId() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(buildFingerprintSource().getBytes(StandardCharsets.UTF_8));
        String hex = toHex(digest.digest()).toUpperCase(Locale.ROOT);
        return formatDeviceId(hex.substring(0, 24));
    }

    static String createNonce() {
        byte[] bytes = new byte[12];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    static String normalizePlan(String value) {
        String text = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        if ("week".equals(text) || "month".equals(text)) {
            return text;
        }
        if (text.startsWith("days-")) {
            int days = parsePositiveInt(text.substring(5), "So ngay");
            return "days-" + days;
        }
        throw new IllegalArgumentException("Term license chi ho tro week, month hoac days-N.");
    }

    static String planLabel(String planKey) {
        String normalized = normalizePlan(planKey);
        if ("week".equals(normalized)) {
            return "Tuan";
        }
        if ("month".equals(normalized)) {
            return "Thang";
        }
        return normalized.substring(5) + " ngay";
    }

    static String formatTimestamp(long epochMillis) {
        return DISPLAY_TIME.format(Instant.ofEpochMilli(epochMillis));
    }

    static String normalizeCode(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (!Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString().trim();
    }

    static String formatDeviceId(String value) {
        String normalized = normalizeDeviceCore(value);
        StringBuilder builder = new StringBuilder("KPAH");
        for (int i = 0; i < normalized.length(); i += 4) {
            builder.append('-');
            builder.append(normalized.substring(i, i + 4));
        }
        return builder.toString();
    }

    static String normalizeDeviceLookup(String value) {
        String normalized = normalizeDeviceCore(value);
        return "KPAH" + normalized;
    }

    static LicenseEnvelope decodeAndVerify(String rawCode) throws Exception {
        String code = normalizeCode(rawCode);
        int separator = code.indexOf('.');
        if (separator <= 0 || separator == code.length() - 1 || code.indexOf('.', separator + 1) >= 0) {
            throw new IllegalArgumentException("Ma kich hoat khong dung dinh dang.");
        }

        byte[] payloadBytes;
        byte[] signatureBytes;
        try {
            payloadBytes = Base64.getUrlDecoder().decode(code.substring(0, separator));
            signatureBytes = Base64.getUrlDecoder().decode(code.substring(separator + 1));
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Ma kich hoat khong dung dinh dang.", error);
        }

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(PUBLIC_KEY);
        signature.update(payloadBytes);
        if (!signature.verify(signatureBytes)) {
            throw new IllegalArgumentException("Ma kich hoat khong hop le.");
        }
        return parsePayload(new String(payloadBytes, StandardCharsets.UTF_8));
    }

    static String createCode(PrivateKey privateKey, LicenseSpec spec) throws Exception {
        if (privateKey == null) {
            throw new IllegalArgumentException("Private key khong duoc de trong.");
        }
        LicenseSpec normalized = spec.normalized();
        byte[] payloadBytes = buildPayload(normalized).getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey, RANDOM);
        signature.update(payloadBytes);
        byte[] signatureBytes = signature.sign();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(payloadBytes)
            + "."
            + Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }

    static PrivateKey loadPrivateKey(Path path) throws Exception {
        if (path == null || !Files.exists(path)) {
            throw new IllegalArgumentException("Khong tim thay private key: " + path);
        }
        String pem = Files.readString(path, StandardCharsets.US_ASCII);
        String base64 = pem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(base64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    private static PublicKey loadPublicKey(String base64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (Exception error) {
            throw new IllegalStateException("Khong tai duoc public key license.", error);
        }
    }

    private static LicenseEnvelope parsePayload(String payload) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        String[] lines = payload.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.length() == 0) {
                continue;
            }
            int separator = line.indexOf('=');
            if (separator <= 0) {
                throw new IllegalArgumentException("Noi dung ma kich hoat bi loi.");
            }
            String key = line.substring(0, separator).trim();
            String value = line.substring(separator + 1).trim();
            values.put(key, value);
        }

        String version = required(values, "v");
        if (!"1".equals(version)) {
            throw new IllegalArgumentException("Ma kich hoat khong dung phien ban.");
        }

        String deviceId = formatDeviceId(required(values, "device"));
        String plan = normalizePlan(required(values, "plan"));
        long issuedAt = parsePositiveLong(required(values, "issuedAt"), "Ngay cap");
        long expiresAt = parsePositiveLong(required(values, "expiresAt"), "Ngay het han");
        String nonce = required(values, "nonce");
        if (nonce.length() < 8) {
            throw new IllegalArgumentException("Nonce trong ma kich hoat khong hop le.");
        }
        if (expiresAt <= issuedAt) {
            throw new IllegalArgumentException("Ngay het han phai lon hon ngay cap.");
        }
        return new LicenseEnvelope(deviceId, plan, issuedAt, expiresAt, nonce);
    }

    private static String buildPayload(LicenseSpec spec) {
        StringBuilder builder = new StringBuilder(160);
        builder.append("v=1\n");
        builder.append("device=").append(formatDeviceId(spec.deviceId)).append('\n');
        builder.append("plan=").append(normalizePlan(spec.plan)).append('\n');
        builder.append("issuedAt=").append(spec.issuedAtMillis).append('\n');
        builder.append("expiresAt=").append(spec.expiresAtMillis).append('\n');
        builder.append("nonce=").append(spec.nonce).append('\n');
        return builder.toString();
    }

    private static String buildFingerprintSource() {
        ArrayList<String> parts = new ArrayList<String>();
        addFingerprintPart(parts, System.getenv("COMPUTERNAME"));
        addFingerprintPart(parts, System.getenv("PROCESSOR_IDENTIFIER"));
        addFingerprintPart(parts, System.getenv("PROCESSOR_ARCHITECTURE"));
        addFingerprintPart(parts, System.getenv("SystemDrive"));
        addFingerprintPart(parts, System.getProperty("os.name"));
        addFingerprintPart(parts, System.getProperty("os.arch"));
        addFingerprintPart(parts, System.getProperty("os.version"));

        ArrayList<String> macs = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    NetworkInterface network = interfaces.nextElement();
                    try {
                        if (network == null || network.isLoopback() || network.isVirtual() || !network.isUp()) {
                            continue;
                        }
                        byte[] address = network.getHardwareAddress();
                        if (address != null && address.length >= 6) {
                            macs.add(toHex(address));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }
        Collections.sort(macs);
        for (int i = 0; i < macs.size(); i++) {
            addFingerprintPart(parts, macs.get(i));
        }

        if (parts.isEmpty()) {
            parts.add("kpah-fallback-device");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                builder.append('|');
            }
            builder.append(parts.get(i));
        }
        return builder.toString();
    }

    private static void addFingerprintPart(ArrayList<String> parts, String value) {
        if (value == null) {
            return;
        }
        String text = value.trim();
        if (text.length() > 0) {
            parts.add(text);
        }
    }

    private static String normalizeDeviceCore(String value) {
        String text = value == null ? "" : value.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "");
        if (text.startsWith("KPAH")) {
            text = text.substring(4);
        }
        if (text.length() != 24) {
            throw new IllegalArgumentException("Ma may phai co 24 ky tu hex.");
        }
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            boolean digit = ch >= '0' && ch <= '9';
            boolean hex = ch >= 'A' && ch <= 'F';
            if (!digit && !hex) {
                throw new IllegalArgumentException("Ma may chi duoc gom so va chu A-F.");
            }
        }
        return text;
    }

    private static String required(Map<String, String> values, String key) {
        String value = values.get(key);
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException("Ma kich hoat thieu truong " + key + ".");
        }
        return value;
    }

    private static int parsePositiveInt(String value, String label) {
        try {
            int parsed = Integer.parseInt(value);
            if (parsed <= 0) {
                throw new IllegalArgumentException(label + " phai lon hon 0.");
            }
            return parsed;
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(label + " khong hop le.", error);
        }
    }

    private static long parsePositiveLong(String value, String label) {
        try {
            long parsed = Long.parseLong(value);
            if (parsed <= 0L) {
                throw new IllegalArgumentException(label + " phai lon hon 0.");
            }
            return parsed;
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(label + " khong hop le.", error);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            builder.append(String.format(Locale.ROOT, "%02x", bytes[i] & 0xFF));
        }
        return builder.toString();
    }

    static final class LicenseSpec {
        final String deviceId;
        final String plan;
        final long issuedAtMillis;
        final long expiresAtMillis;
        final String nonce;

        LicenseSpec(String deviceId, String plan, long issuedAtMillis, long expiresAtMillis, String nonce) {
            this.deviceId = deviceId;
            this.plan = plan;
            this.issuedAtMillis = issuedAtMillis;
            this.expiresAtMillis = expiresAtMillis;
            this.nonce = nonce;
        }

        LicenseSpec normalized() {
            String normalizedDevice = formatDeviceId(deviceId);
            String normalizedPlan = normalizePlan(plan);
            String normalizedNonce = nonce == null ? "" : nonce.trim();
            if (normalizedNonce.length() < 8) {
                throw new IllegalArgumentException("Nonce phai dai toi thieu 8 ky tu.");
            }
            if (issuedAtMillis <= 0L || expiresAtMillis <= 0L || expiresAtMillis <= issuedAtMillis) {
                throw new IllegalArgumentException("Khoang thoi gian license khong hop le.");
            }
            return new LicenseSpec(normalizedDevice, normalizedPlan, issuedAtMillis, expiresAtMillis, normalizedNonce);
        }
    }

    static final class LicenseEnvelope {
        final String deviceId;
        final String plan;
        final long issuedAtMillis;
        final long expiresAtMillis;
        final String nonce;

        LicenseEnvelope(String deviceId, String plan, long issuedAtMillis, long expiresAtMillis, String nonce) {
            this.deviceId = deviceId;
            this.plan = plan;
            this.issuedAtMillis = issuedAtMillis;
            this.expiresAtMillis = expiresAtMillis;
            this.nonce = nonce;
        }
    }
}
