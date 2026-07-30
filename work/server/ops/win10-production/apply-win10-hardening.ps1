param(
    [string]$RepoRoot = "",
    [int[]]$PublicWebPorts = @(),
    [int[]]$ExtraPublicPorts = @(),
    [int]$PhpFastCgiPort = 9072
)

$ErrorActionPreference = "Stop"

function Assert-Administrator {
    $identity = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = [Security.Principal.WindowsPrincipal]::new($identity)
    if (-not $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
        throw "Can chay PowerShell bang Run as administrator de thay doi Windows Firewall."
    }
}

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

function Remove-KpahFirewallRules {
    $existing = Get-NetFirewallRule -Group "KPAH Win10" -ErrorAction SilentlyContinue
    if ($existing) {
        $existing | Remove-NetFirewallRule | Out-Null
    }
}

function Add-KpahFirewallRule {
    param(
        [string]$DisplayName,
        [string]$Action,
        [int]$Port
    )

    New-NetFirewallRule `
        -DisplayName $DisplayName `
        -Group "KPAH Win10" `
        -Direction Inbound `
        -Profile Any `
        -Enabled True `
        -Action $Action `
        -Protocol TCP `
        -LocalPort $Port `
        -ErrorAction Stop `
        | Out-Null
}

Assert-Administrator
$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
$serverIni = Read-KeyValueFile (Join-Path $repoRoot "server.ini")
$loginIni = Read-KeyValueFile (Join-Path $repoRoot "loginServer\server.ini")

$gamePort = if ($serverIni.ContainsKey("sv.port")) { [int]$serverIni["sv.port"] } else { 19129 }
$loginPort = if ($loginIni.ContainsKey("port")) { [int]$loginIni["port"] } else { 8023 }
$localAdminPort = if ($serverIni.ContainsKey("sv.localAdminPort")) { [int]$serverIni["sv.localAdminPort"] } else { 18023 }

$publicPorts = New-Object System.Collections.Generic.List[int]
foreach ($port in ($PublicWebPorts + $ExtraPublicPorts)) {
    if ($port -gt 0 -and -not $publicPorts.Contains($port)) {
        $publicPorts.Add($port)
    }
}

$privatePorts = New-Object System.Collections.Generic.List[int]
foreach ($port in @($loginPort, $localAdminPort, 18080, $PhpFastCgiPort, 3306)) {
    if ($port -gt 0 -and -not $privatePorts.Contains($port) -and -not $publicPorts.Contains($port) -and $port -ne $gamePort) {
        $privatePorts.Add($port)
    }
}

Remove-KpahFirewallRules
Add-KpahFirewallRule -DisplayName "KPAH Allow Game TCP $gamePort" -Action Allow -Port $gamePort
foreach ($port in $publicPorts) {
    Add-KpahFirewallRule -DisplayName "KPAH Allow Web TCP $port" -Action Allow -Port $port
}
foreach ($port in $privatePorts) {
    Add-KpahFirewallRule -DisplayName "KPAH Block Private TCP $port" -Action Block -Port $port
}

$reportDir = Join-Path $PSScriptRoot "reports"
New-Item -ItemType Directory -Force -Path $reportDir | Out-Null
$reportPath = Join-Path $reportDir ("hardening-" + (Get-Date -Format "yyyyMMdd-HHmmss") + ".txt")

$lines = @(
    "KPAH Win10 hardening applied",
    ("Generated: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss")),
    ("RepoRoot: " + $repoRoot),
    ("Game allow port: " + $gamePort),
    ("Public web allow ports: " + ($(if ($publicPorts.Count -gt 0) { ($publicPorts -join ",") } else { "-" }))),
    ("Blocked private ports: " + ($(if ($privatePorts.Count -gt 0) { ($privatePorts -join ",") } else { "-" }))),
    "",
    "Rules are grouped under: KPAH Win10",
    "Rollback: powershell -ExecutionPolicy Bypass -File .\ops\win10-production\rollback-win10-hardening.ps1"
)

Set-Content -LiteralPath $reportPath -Value $lines -Encoding UTF8
Write-Output ("Applied Win10 hardening. Report: " + $reportPath)
