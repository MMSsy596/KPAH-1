using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Web.Script.Serialization;
using System.Windows.Forms;

internal static class KpahLauncher
{
    private sealed class ClientConfig
    {
        public string channel { get; set; }
        public string version { get; set; }
        public string manifestUrl { get; set; }
        public string gameFile { get; set; }
    }

    private sealed class ReleaseManifest
    {
        public Dictionary<string, ReleaseInfo> channels { get; set; }
    }

    private sealed class ReleaseInfo
    {
        public string latestVersion { get; set; }
        public string minimumVersion { get; set; }
        public bool mandatory { get; set; }
        public string downloadUrl { get; set; }
        public string sha256 { get; set; }
        public string summary { get; set; }
    }

    [STAThread]
    private static int Main(string[] args)
    {
        Application.EnableVisualStyles();
        string root = AppDomain.CurrentDomain.BaseDirectory;
        try
        {
            ClientConfig config = ReadJson<ClientConfig>(Path.Combine(root, "client-update.json"));
            bool skipUpdate = Array.IndexOf(args, "--skip-update") >= 0;
            if (!skipUpdate && TryStartUpdate(root, config))
            {
                return 0;
            }
            return StartGame(root, config.gameFile);
        }
        catch (Exception error)
        {
            MessageBox.Show(
                "Không thể khởi động KPAH: " + error.Message,
                "KPAH Launcher",
                MessageBoxButtons.OK,
                MessageBoxIcon.Error
            );
            return 1;
        }
    }

    private static bool TryStartUpdate(string root, ClientConfig config)
    {
        try
        {
            using (WebClient web = new WebClient())
            {
                web.Headers[HttpRequestHeader.CacheControl] = "no-cache";
                ReleaseManifest manifest = new JavaScriptSerializer().Deserialize<ReleaseManifest>(
                    web.DownloadString(config.manifestUrl)
                );
                ReleaseInfo release;
                if (manifest == null || manifest.channels == null ||
                    !manifest.channels.TryGetValue(config.channel, out release) ||
                    CompareVersion(release.latestVersion, config.version) <= 0)
                {
                    return false;
                }

                bool required = release.mandatory || CompareVersion(config.version, release.minimumVersion) < 0;
                string prompt = "Có phiên bản KPAH " + release.latestVersion + ".\n\n" +
                    (release.summary ?? "Bản cập nhật mới đã sẵn sàng.") + "\n\nTải và cập nhật ngay?";
                DialogResult choice = MessageBox.Show(
                    prompt,
                    "Cập nhật KPAH",
                    required ? MessageBoxButtons.OK : MessageBoxButtons.YesNo,
                    MessageBoxIcon.Information
                );
                if ((!required && choice != DialogResult.Yes) || (required && choice != DialogResult.OK))
                {
                    return false;
                }

                string packagePath = Path.Combine(Path.GetTempPath(), "kpah-pc-" + Guid.NewGuid().ToString("N") + ".zip");
                web.DownloadFile(release.downloadUrl, packagePath);
                if (!string.Equals(HashFile(packagePath), release.sha256, StringComparison.OrdinalIgnoreCase))
                {
                    File.Delete(packagePath);
                    throw new InvalidDataException("Gói cập nhật không đúng mã SHA-256.");
                }

                string applyScript = Path.Combine(root, "Apply-KPAH-Update.ps1");
                if (!File.Exists(applyScript))
                {
                    throw new FileNotFoundException("Thiếu bộ áp dụng cập nhật.", applyScript);
                }
                ProcessStartInfo updater = new ProcessStartInfo
                {
                    FileName = "powershell.exe",
                    Arguments = "-NoProfile -ExecutionPolicy Bypass -File " + Quote(applyScript) +
                        " -Package " + Quote(packagePath) +
                        " -Target " + Quote(root) +
                        " -WaitProcessId " + Process.GetCurrentProcess().Id,
                    WorkingDirectory = root,
                    UseShellExecute = false,
                    CreateNoWindow = true
                };
                Process.Start(updater);
                return true;
            }
        }
        catch (Exception error)
        {
            MessageBox.Show(
                "Chưa thể tải bản cập nhật. Bạn vẫn có thể chơi bản hiện tại.\n\n" + error.Message,
                "Cập nhật KPAH",
                MessageBoxButtons.OK,
                MessageBoxIcon.Warning
            );
            return false;
        }
    }

    private static int StartGame(string root, string gameFile)
    {
        string gamePath = Path.Combine(root, string.IsNullOrEmpty(gameFile) ? "KPAH_276.exe" : gameFile);
        if (!File.Exists(gamePath))
        {
            throw new FileNotFoundException("Không tìm thấy file game.", gamePath);
        }
        Process.Start(new ProcessStartInfo { FileName = gamePath, WorkingDirectory = root, UseShellExecute = true });
        return 0;
    }

    private static T ReadJson<T>(string path)
    {
        if (!File.Exists(path))
        {
            throw new FileNotFoundException("Thiếu cấu hình cập nhật.", path);
        }
        return new JavaScriptSerializer().Deserialize<T>(File.ReadAllText(path));
    }

    private static int CompareVersion(string left, string right)
    {
        Version leftVersion;
        Version rightVersion;
        return Version.TryParse(left, out leftVersion) && Version.TryParse(right, out rightVersion)
            ? leftVersion.CompareTo(rightVersion)
            : string.Compare(left ?? "", right ?? "", StringComparison.OrdinalIgnoreCase);
    }

    private static string HashFile(string path)
    {
        using (SHA256 sha = SHA256.Create())
        using (FileStream stream = File.OpenRead(path))
        {
            return BitConverter.ToString(sha.ComputeHash(stream)).Replace("-", "").ToLowerInvariant();
        }
    }

    private static string Quote(string value)
    {
        return "\"" + value.Replace("\"", "\\\"") + "\"";
    }
}
