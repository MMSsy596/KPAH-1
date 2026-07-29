package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class ClientIntegrityAuth {

    private static final String TOKEN_VERSION = "kpah-auth-v1";
    private static final String TOKEN_MARKER = "|" + TOKEN_VERSION + "|";
    private static final String JAR_TOKEN_VERSION = "kpah-jar-v1";
    private static final String JAR_TOKEN_MARKER = "|" + JAR_TOKEN_VERSION + "|";

    private ClientIntegrityAuth() {
    }

    public static VerificationResult verify(String platformRaw) {
        if (!TeamServer.clientAuthEnabled) {
            return VerificationResult.disabled();
        }
        if (platformRaw == null || platformRaw.trim().isEmpty()) {
            return VerificationResult.invalid("missing_token", "Thieu chu ky client. Vui long mo lai client chuan.", true);
        }
        int jarMarkerIndex = platformRaw.indexOf(JAR_TOKEN_MARKER);
        if (jarMarkerIndex >= 0) {
            return verifyJarToken(platformRaw.substring(jarMarkerIndex + 1).trim());
        }
        int markerIndex = platformRaw.indexOf(TOKEN_MARKER);
        if (markerIndex < 0) {
            VerificationResult plainPlatform = verifyPlainPlatform(platformRaw);
            if (plainPlatform.valid) {
                return plainPlatform;
            }
            if (isLegacyNokiaPlainPlatform(platformRaw)) {
                return legacyNokiaCompatibilityFailure(
                        "legacy_plain_nokia_platform",
                        "Client JAR tren Nokia cu chua gui duoc chu ky moi. Vui long tai lai ban JAR moi nhat, tai khoan se khong bi khoa."
                );
            }
            return VerificationResult.invalid("missing_token", "Client dang nhap khong hop le. Vui long dung client chuan duoc phep.", true);
        }
        String token = platformRaw.substring(markerIndex + 1).trim();
        String[] parts = token.split("\\|");
        if (parts.length != 4 || !TOKEN_VERSION.equals(parts[0])) {
            return VerificationResult.invalid("bad_token_format", "Client dang nhap khong hop le. Vui long dung lai client chuan.", true);
        }
        long issuedAt;
        try {
            issuedAt = Long.parseLong(parts[1]);
        } catch (Exception e) {
            return VerificationResult.invalid("bad_timestamp", "Client dang nhap khong hop le. Vui long dung lai client chuan.", true);
        }
        String clientHash = normalizeHex(parts[2]);
        String signature = normalizeHex(parts[3]);
        if (clientHash.length() != 64 || signature.length() != 64) {
            return VerificationResult.invalid("bad_signature_length", "Client dang nhap khong hop le. Vui long dung lai client chuan.", true);
        }
        long maxSkewSeconds = TeamServer.clientAuthMaxSkewSeconds;
        if (maxSkewSeconds > 0L) {
            long now = System.currentTimeMillis() / 1000L;
            long skew = Math.abs(now - issuedAt);
            if (skew > maxSkewSeconds) {
                return VerificationResult.invalid("expired_token", "Chu ky client het han. Vui long tat va mo lai client roi dang nhap lai.", false);
            }
        }
        String expectedSignature;
        try {
            expectedSignature = hmacSha256Hex(TeamServer.clientAuthSecret, TOKEN_VERSION + "|" + issuedAt + "|" + clientHash);
        } catch (Exception e) {
            return VerificationResult.invalid("server_error", "May chu xac thuc client bi loi. Vui long lien he admin.", false);
        }
        if (!safeEquals(expectedSignature, signature)) {
            return VerificationResult.invalid("signature_mismatch", "Client dang nhap khong hop le hoac da bi chinh sua.", true);
        }
        if (!isAllowedSignedHash(clientHash)) {
            return VerificationResult.invalid("hash_not_allowed", "File client da bi thay doi. Vui long dung dung 1 trong 2 client chuan.", true);
        }
        return VerificationResult.valid(clientHash);
    }

    private static VerificationResult verifyJarToken(String token) {
        String[] parts = token.split("\\|");
        if (parts.length != 3 || !JAR_TOKEN_VERSION.equals(parts[0])) {
            return VerificationResult.invalid("bad_jar_token_format", "Client dang nhap khong hop le. Vui long dung lai client chuan.", true);
        }
        String clientId = normalizeClientId(parts[1]);
        String rawMeasurement = parts[2] == null ? "" : parts[2].trim();
        if (isLegacyJarCompatibilityMeasurement(rawMeasurement)) {
            return legacyNokiaCompatibilityFailure(
                    "legacy_jar_measurement_" + rawMeasurement.toLowerCase(Locale.ROOT),
                    "Client JAR tren Nokia cu khong doc duoc chu ky file. Vui long tai lai ban JAR moi nhat, tai khoan se khong bi khoa."
            );
        }
        String measurement = normalizeHex(parts[2]);
        if (clientId.isEmpty() || measurement.length() != 64) {
            return VerificationResult.invalid("bad_jar_measurement", "Client dang nhap khong hop le hoac da bi chinh sua.", true);
        }
        if (!TeamServer.clientAuthAllowedJarMeasurementsById.isEmpty()) {
            String expectedMeasurement = TeamServer.clientAuthAllowedJarMeasurementsById.get(clientId);
            if (expectedMeasurement == null) {
                return VerificationResult.invalid("unknown_client_id", "Client dang nhap khong nam trong danh sach client chuan.", true);
            }
            if (!safeEquals(expectedMeasurement, measurement)) {
                return VerificationResult.invalid("jar_measurement_mismatch", "File client da bi thay doi. Vui long dung dung 1 trong 2 file client chuan.", true);
            }
            return VerificationResult.valid(measurement, clientId);
        }
        if (!TeamServer.clientAuthAllowedHashes.contains(measurement)) {
            return VerificationResult.invalid("jar_measurement_not_allowed", "File client da bi thay doi. Vui long dung dung 1 trong 2 file client chuan.", true);
        }
        return VerificationResult.valid(measurement, clientId);
    }

    private static VerificationResult verifyPlainPlatform(String platformRaw) {
        if (TeamServer.clientAuthAllowedPlainPlatforms == null
                || TeamServer.clientAuthAllowedPlainPlatforms.isEmpty()) {
            return VerificationResult.invalid("plain_platform_not_allowed", "", true);
        }
        String normalized = platformRaw == null ? "" : platformRaw.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return VerificationResult.invalid("plain_platform_not_allowed", "", true);
        }
        for (String marker : TeamServer.clientAuthAllowedPlainPlatforms) {
            if (marker != null && !marker.isEmpty() && normalized.contains(marker)) {
                return VerificationResult.valid("plain-platform:" + marker, marker);
            }
        }
        return VerificationResult.invalid("plain_platform_not_allowed", "", true);
    }

    private static VerificationResult legacyNokiaCompatibilityFailure(String reasonKey, String message) {
        return VerificationResult.invalid(reasonKey, message, false);
    }

    private static boolean isLegacyJarCompatibilityMeasurement(String rawMeasurement) {
        if (rawMeasurement == null) {
            return false;
        }
        String normalized = rawMeasurement.trim();
        return "ERROR".equalsIgnoreCase(normalized) || "MISSING".equalsIgnoreCase(normalized);
    }

    private static boolean isLegacyNokiaPlainPlatform(String platformRaw) {
        String normalized = platformRaw == null ? "" : platformRaw.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return false;
        }
        return normalized.contains("nokia")
                || normalized.contains("symbian")
                || normalized.contains("series60")
                || normalized.contains("series 60")
                || normalized.contains("s60");
    }

    private static boolean isAllowedSignedHash(String clientHash) {
        if (!TeamServer.clientAuthAllowedOutputHashes.isEmpty()) {
            return TeamServer.clientAuthAllowedOutputHashes.contains(clientHash);
        }
        return TeamServer.clientAuthAllowedHashes.contains(clientHash);
    }

    private static String hmacSha256Hex(String secret, String payload) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return toHex(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 255;
            if (value < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(value).toUpperCase(Locale.ROOT));
        }
        return sb.toString();
    }

    private static String normalizeHex(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }

    private static String normalizeClientId(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private static boolean safeEquals(String left, String right) {
        return MessageDigest.isEqual(left.getBytes(StandardCharsets.US_ASCII), right.getBytes(StandardCharsets.US_ASCII));
    }

    public static final class VerificationResult {
        public final boolean valid;
        public final boolean shouldLockAccount;
        public final String reasonKey;
        public final String message;
        public final String clientHash;
        public final String clientId;

        private VerificationResult(
                boolean valid,
                boolean shouldLockAccount,
                String reasonKey,
                String message,
                String clientHash,
                String clientId
        ) {
            this.valid = valid;
            this.shouldLockAccount = shouldLockAccount;
            this.reasonKey = reasonKey;
            this.message = message;
            this.clientHash = clientHash;
            this.clientId = clientId;
        }

        public static VerificationResult valid(String clientHash) {
            return valid(clientHash, "");
        }

        public static VerificationResult valid(String clientHash, String clientId) {
            return new VerificationResult(true, false, "ok", "", clientHash, clientId);
        }

        public static VerificationResult disabled() {
            return new VerificationResult(true, false, "disabled", "", "", "");
        }

        public static VerificationResult invalid(String reasonKey, String message, boolean shouldLockAccount) {
            return new VerificationResult(false, shouldLockAccount, reasonKey, message, "", "");
        }
    }
}
