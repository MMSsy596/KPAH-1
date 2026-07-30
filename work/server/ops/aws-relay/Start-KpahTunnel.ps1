param(
    [string]$RepoRoot = "",
    [switch]$Foreground
)

$ErrorActionPreference = "Stop"
$root = if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
} else {
    (Resolve-Path -LiteralPath $RepoRoot).Path
}
$configDir = Join-Path $root "local-config"
$configPath = Join-Path $configDir "kpah-tunnel.json"
$examplePath = Join-Path $PSScriptRoot "kpah-tunnel.example.json"
$runtimeDir = Join-Path $root "runtime-local\ssh-tunnel"
$pidPath = Join-Path $runtimeDir "supervisor.pid"

if (-not (Test-Path -LiteralPath $configPath)) {
    New-Item -ItemType Directory -Force -Path $configDir | Out-Null
    Copy-Item -LiteralPath $examplePath -Destination $configPath
    Write-Output "Da tao cau hinh local: $configPath"
}

if (Test-Path -LiteralPath $pidPath) {
    $existingPid = [int](Get-Content -LiteralPath $pidPath -Raw)
    if (Get-Process -Id $existingPid -ErrorAction SilentlyContinue) {
        Write-Output "KPAH tunnel supervisor dang chay (PID $existingPid)."
        exit 0
    }
    Remove-Item -LiteralPath $pidPath -Force
}

$supervisorPath = Join-Path $PSScriptRoot "Run-KpahTunnelSupervisor.ps1"
if ($Foreground) {
    & $supervisorPath -RepoRoot $root -ConfigPath $configPath
    exit $LASTEXITCODE
}

$powerShellExe = Join-Path $PSHOME "powershell.exe"
$process = Start-Process -FilePath $powerShellExe -ArgumentList @(
    "-NoProfile",
    "-ExecutionPolicy", "Bypass",
    "-File", $supervisorPath,
    "-RepoRoot", $root,
    "-ConfigPath", $configPath
) -PassThru -WindowStyle Hidden

Start-Sleep -Seconds 2
if ($process.HasExited) {
    throw "Tunnel supervisor khong khoi dong duoc. Xem runtime-local\ssh-tunnel\logs\tunnel-supervisor.log."
}
Write-Output "Da khoi dong KPAH tunnel supervisor (PID $($process.Id))."
