param(
    [string]$RepoRoot = ""
)

$ErrorActionPreference = "Stop"

function Resolve-RepoRoot {
    param([string]$InputRoot)

    if (-not [string]::IsNullOrWhiteSpace($InputRoot)) {
        return (Resolve-Path -LiteralPath $InputRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
}

function Read-KeyValueFile {
    param([string]$Path)

    $result = @{}
    if (-not (Test-Path -LiteralPath $Path)) {
        return $result
    }

    foreach ($line in Get-Content -LiteralPath $Path -Encoding UTF8) {
        if ($null -eq $line) {
            continue
        }
        $trimmed = $line.Trim()
        if ($trimmed.Length -eq 0 -or $trimmed.StartsWith("#") -or $trimmed.StartsWith("//")) {
            continue
        }
        $index = $trimmed.IndexOf("=")
        if ($index -lt 0) {
            continue
        }
        $key = $trimmed.Substring(0, $index).Trim()
        $value = $trimmed.Substring($index + 1).Trim()
        if ($key.Length -gt 0) {
            $result[$key] = $value
        }
    }

    return $result
}

function Find-FirstPath {
    param([string[]]$Candidates)

    foreach ($candidate in $Candidates) {
        if ([string]::IsNullOrWhiteSpace($candidate)) {
            continue
        }
        if (Test-Path -LiteralPath $candidate) {
            return (Resolve-Path -LiteralPath $candidate).Path
        }
    }
    return $null
}

function Get-PortOwner {
    param([int]$Port)

    try {
        $conn = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction Stop | Select-Object -First 1
        if (-not $conn) {
            return $null
        }
        $proc = Get-CimInstance Win32_Process -Filter ("ProcessId = " + $conn.OwningProcess) -ErrorAction SilentlyContinue
        return [PSCustomObject]@{
            Port = $Port
            ProcessId = $conn.OwningProcess
            ProcessName = if ($proc) { $proc.Name } else { "unknown" }
            CommandLine = if ($proc) { $proc.CommandLine } else { "" }
        }
    } catch {
        return $null
    }
}

$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
$serverIni = Read-KeyValueFile (Join-Path $repoRoot "server.ini")
$loginIni = Read-KeyValueFile (Join-Path $repoRoot "loginServer\server.ini")

$gamePort = 19129
if ($serverIni.ContainsKey("sv.port")) {
    $gamePort = [int]$serverIni["sv.port"]
}

$loginPort = 8023
if ($loginIni.ContainsKey("port")) {
    $loginPort = [int]$loginIni["port"]
}
$localAdminPort = 18023
if ($serverIni.ContainsKey("sv.localAdminPort")) {
    $localAdminPort = [int]$serverIni["sv.localAdminPort"]
}

$javaBin = $null
try {
    $javaBin = & (Join-Path $repoRoot "tools\java\resolve_java_bin.ps1") -RepoRoot $repoRoot -Role runtime
} catch {
}

$phpExe = Find-FirstPath @(
    "C:\php\php-cgi.exe",
    "C:\php\php.exe",
    "C:\xampp_retired_20260426\php\php-cgi.exe",
    "C:\xampp_retired_20260426\php\php.exe",
    "C:\xampp\php\php-cgi.exe",
    "C:\xampp\php\php.exe"
)

$nginxExe = Find-FirstPath @(
    "C:\nginx-1.30.0\nginx.exe",
    "C:\nginx\nginx.exe",
    "C:\tools\nginx\nginx.exe",
    "C:\Program Files\nginx\nginx.exe"
)

$mysqlDumpExe = Find-FirstPath @(
    "C:\mysql\bin\mysqldump.exe",
    "C:\Program Files\MariaDB 11.4\bin\mysqldump.exe",
    "C:\Program Files\MariaDB 11.3\bin\mysqldump.exe",
    "C:\Program Files\MariaDB 10.11\bin\mysqldump.exe",
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe",
    "C:\xampp_retired_20260426\mysql\bin\mysqldump.exe",
    "C:\xampp\mysql\bin\mysqldump.exe"
)

$reportDir = Join-Path $PSScriptRoot "reports"
New-Item -ItemType Directory -Force -Path $reportDir | Out-Null
$reportPath = Join-Path $reportDir ("preflight-" + (Get-Date -Format "yyyyMMdd-HHmmss") + ".txt")

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("KPAH Win10 production preflight")
$lines.Add("Generated: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))
$lines.Add("RepoRoot: " + $repoRoot)
$lines.Add("")
$lines.Add("[Current runtime]")
$lines.Add("Game port: " + $gamePort)
$lines.Add("Login port: " + $loginPort)
$lines.Add("Local admin port: " + $localAdminPort)
$lines.Add("Game owner: " + ($(if (Get-PortOwner -Port $gamePort) { (Get-PortOwner -Port $gamePort).ProcessName + " PID " + (Get-PortOwner -Port $gamePort).ProcessId } else { "not-listening" })))
$lines.Add("Login owner: " + ($(if (Get-PortOwner -Port $loginPort) { (Get-PortOwner -Port $loginPort).ProcessName + " PID " + (Get-PortOwner -Port $loginPort).ProcessId } else { "not-listening" })))
$lines.Add("Local admin owner: " + ($(if (Get-PortOwner -Port $localAdminPort) { (Get-PortOwner -Port $localAdminPort).ProcessName + " PID " + (Get-PortOwner -Port $localAdminPort).ProcessId } else { "not-listening" })))
$lines.Add("")
$lines.Add("[Detected binaries]")
$lines.Add("Java bin: " + ($(if ($javaBin) { $javaBin } else { "missing" })))
$lines.Add("PHP exe: " + ($(if ($phpExe) { $phpExe } else { "missing" })))
$lines.Add("Nginx exe: " + ($(if ($nginxExe) { $nginxExe } else { "missing" })))
$lines.Add("mysqldump.exe: " + ($(if ($mysqlDumpExe) { $mysqlDumpExe } else { "missing" })))
$lines.Add("")
$lines.Add("[Config hygiene]")
$lines.Add("server.ini db.user = " + ($serverIni["db.user"]))
$lines.Add("server.ini db.password empty = " + ($(if ([string]::IsNullOrWhiteSpace($serverIni["db.password"])) { "yes" } else { "no" })))
$lines.Add("server.ini localAdminEnabled = " + ($serverIni["sv.localAdminEnabled"]))
$lines.Add("server.ini localAdminHost = " + ($serverIni["sv.localAdminHost"]))
$lines.Add("server.ini ddosGuardEnabled = " + ($serverIni["sv.ddosGuardEnabled"]))
$lines.Add("server.ini ddosMaxConnectPerWindow = " + ($serverIni["sv.ddosMaxConnectPerWindow"]))
$lines.Add("server.ini socketBacklog = " + ($serverIni["sv.socketBacklog"]))
$lines.Add("loginServer/server.ini db.user = " + ($loginIni["db.user"]))
$lines.Add("loginServer/server.ini db.password empty = " + ($(if ([string]::IsNullOrWhiteSpace($loginIni["db.password"])) { "yes" } else { "no" })))
$lines.Add("")
$lines.Add("[Notes]")
$lines.Add("- Script nay chi doc va bao cao, khong thay doi may.")
$lines.Add("- Neu DB password con trong, can doi truoc cutover.")
$lines.Add("- Neu Nginx/PHP chua co, van co the render stage truoc.")
$lines.Add("- Sau preflight, co the chay apply-win10-hardening.ps1 de dong cac cong noi bo.")

Set-Content -LiteralPath $reportPath -Value $lines -Encoding UTF8
Write-Output ("Preflight report: " + $reportPath)
