param(
    [string]$RepoRoot = "",
    [string]$ConfigPath = ""
)

$ErrorActionPreference = "Stop"

function Resolve-KpahRepoRoot {
    if (-not [string]::IsNullOrWhiteSpace($RepoRoot)) {
        return (Resolve-Path -LiteralPath $RepoRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
}

function Resolve-KpahPath {
    param([string]$Root, [string]$Value)
    if ([System.IO.Path]::IsPathRooted($Value)) {
        return [System.IO.Path]::GetFullPath($Value)
    }
    return [System.IO.Path]::GetFullPath((Join-Path $Root $Value))
}

$root = Resolve-KpahRepoRoot
if ([string]::IsNullOrWhiteSpace($ConfigPath)) {
    $ConfigPath = Join-Path $root "local-config\kpah-tunnel.json"
}
if (-not (Test-Path -LiteralPath $ConfigPath)) {
    throw "Thieu cau hinh tunnel: $ConfigPath"
}

$config = Get-Content -LiteralPath $ConfigPath -Raw -Encoding UTF8 | ConvertFrom-Json
$identityFile = Resolve-KpahPath -Root $root -Value ([string]$config.identityFile)
$knownHostsFile = Resolve-KpahPath -Root $root -Value ([string]$config.knownHostsFile)
if (-not (Test-Path -LiteralPath $identityFile)) {
    throw "Khong tim thay SSH private key: $identityFile"
}

$sshExe = Join-Path $env:WINDIR "System32\OpenSSH\ssh.exe"
if (-not (Test-Path -LiteralPath $sshExe)) {
    $sshCommand = Get-Command ssh.exe -ErrorAction SilentlyContinue
    if (-not $sshCommand) {
        throw "Khong tim thay OpenSSH Client (ssh.exe)."
    }
    $sshExe = $sshCommand.Source
}

$runtimeDir = Join-Path $root "runtime-local\ssh-tunnel"
$logDir = Join-Path $runtimeDir "logs"
New-Item -ItemType Directory -Force -Path $logDir | Out-Null
New-Item -ItemType Directory -Force -Path (Split-Path -Parent $knownHostsFile) | Out-Null
$supervisorPidPath = Join-Path $runtimeDir "supervisor.pid"
$sshPidPath = Join-Path $runtimeDir "ssh.pid"
$logPath = Join-Path $logDir "tunnel-supervisor.log"

$createdNew = $false
$mutex = New-Object System.Threading.Mutex($true, "Global\KPAH-SshTunnel-Supervisor", [ref]$createdNew)
if (-not $createdNew) {
    throw "KPAH tunnel supervisor da dang chay."
}

Set-Content -LiteralPath $supervisorPidPath -Value $PID -Encoding ASCII

function Write-TunnelLog {
    param([string]$Message)
    if ((Test-Path -LiteralPath $logPath) -and (Get-Item -LiteralPath $logPath).Length -gt 10MB) {
        Move-Item -LiteralPath $logPath -Destination ($logPath + ".previous") -Force
    }
    Add-Content -LiteralPath $logPath -Encoding UTF8 -Value (
        "{0} {1}" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss"), $Message
    )
}

try {
    Write-TunnelLog "Supervisor started (PID $PID)."
    while ($true) {
        $arguments = @(
            "-N",
            "-T",
            "-i", $identityFile,
            "-p", [string]$config.relaySshPort,
            "-o", "BatchMode=yes",
            "-o", "ExitOnForwardFailure=yes",
            "-o", "ServerAliveInterval=30",
            "-o", "ServerAliveCountMax=3",
            "-o", "ConnectTimeout=15",
            "-o", "StrictHostKeyChecking=accept-new",
            "-o", ("UserKnownHostsFile=" + $knownHostsFile),
            "-R", ("0.0.0.0:{0}:{1}:{2}" -f $config.remoteGamePort, $config.localGameHost, $config.localGamePort),
            ("{0}@{1}" -f $config.relayUser, $config.relayHost)
        )

        Write-TunnelLog ("Connecting to relay {0}:{1}, public game port {2}." -f $config.relayHost, $config.relaySshPort, $config.remoteGamePort)
        $process = Start-Process -FilePath $sshExe -ArgumentList $arguments -PassThru -WindowStyle Hidden
        Set-Content -LiteralPath $sshPidPath -Value $process.Id -Encoding ASCII
        $process.WaitForExit()
        $exitCode = $process.ExitCode
        Remove-Item -LiteralPath $sshPidPath -Force -ErrorAction SilentlyContinue
        Write-TunnelLog "SSH exited with code $exitCode. Reconnecting."
        Start-Sleep -Seconds ([Math]::Max(2, [int]$config.reconnectDelaySeconds))
    }
} finally {
    Remove-Item -LiteralPath $sshPidPath -Force -ErrorAction SilentlyContinue
    Remove-Item -LiteralPath $supervisorPidPath -Force -ErrorAction SilentlyContinue
    Write-TunnelLog "Supervisor stopped."
    $mutex.ReleaseMutex()
    $mutex.Dispose()
}
