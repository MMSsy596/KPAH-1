using System;
using System.Security.Cryptography;
using System.Text;

namespace ClientAuthPcLib
{
    public static class ClientAuthPc
    {
        private const string PlatformBase = "kpahpc/1/1";
        private const string TokenVersion = "kpah-auth-v1";
        private const string ClientAuthSecret = "9F3A8C731D6B4E8EAB2C6F5D11A8B7C9E4F2D6A1C3B5870F96E2C14B5A8D3F71";
        private const string ForcedSignedHash = "7BF6001829A3F0AA2BD1E54F03B3AAD091C94C9E849B2C8019CFCA0518BF1AAB";

        public static string BuildJarPlatformToken()
        {
            long issuedAt = GetUnixTimeSecondsUtc();
            string clientHash = GetSignedClientHash();
            string payload = TokenVersion + "|" + issuedAt + "|" + clientHash;
            return PlatformBase + "|" + payload + "|" + HmacSha256Hex(ClientAuthSecret, payload);
        }

        public static string ComputeMeasurement()
        {
            return ForcedSignedHash;
        }

        public static void LogInfo(string message)
        {
        }

        public static void LogWarning(string message)
        {
        }

        public static void LogError(string message)
        {
        }

        private static string GetSignedClientHash()
        {
            return ForcedSignedHash;
        }

        private static long GetUnixTimeSecondsUtc()
        {
            DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
            TimeSpan span = DateTime.UtcNow - epoch;
            return (long)span.TotalSeconds;
        }

        private static string HmacSha256Hex(string secret, string payload)
        {
            using (HMACSHA256 hmac = new HMACSHA256(Encoding.UTF8.GetBytes(secret)))
            {
                return ToHex(hmac.ComputeHash(Encoding.UTF8.GetBytes(payload)));
            }
        }

        private static string ToHex(byte[] data)
        {
            StringBuilder sb = new StringBuilder(data.Length * 2);
            for (int i = 0; i < data.Length; i++)
            {
                sb.Append(data[i].ToString("X2"));
            }
            return sb.ToString();
        }
    }
}
