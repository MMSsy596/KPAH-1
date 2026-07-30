param([string]$RepoRoot = "")

# Must be run from an elevated PowerShell window.
$ErrorActionPreference = "Stop"
$root = if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
} else {
    (Resolve-Path -LiteralPath $RepoRoot).Path
}

$identityFile = Join-Path $root "runtime-local\ssh-tunnel\kpah-tunnel"
if (-not (Test-Path -LiteralPath $identityFile)) {
    throw "Khong tim thay SSH private key: $identityFile"
}

$isAdmin = ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole(
    [Security.Principal.WindowsBuiltInRole]::Administrator
)
if (-not $isAdmin) {
    throw "Hay mo PowerShell bang Run as administrator."
}

& (Join-Path $PSScriptRoot "Start-KpahTunnel.ps1") -RepoRoot $root

# Limit the private key to the current administrator, Administrators, and SYSTEM.
$currentUser = [Security.Principal.WindowsIdentity]::GetCurrent().Name
& icacls.exe $identityFile /inheritance:r /grant:r "${currentUser}:(R)" "*S-1-5-32-544:(R)" "*S-1-5-18:(R)" | Out-Null
if ($LASTEXITCODE -ne 0) {
    throw "Khong the dat ACL an toan cho SSH private key."
}

$taskName = "KPAH SSH Tunnel Supervisor"
$powerShellExe = Join-Path $PSHOME "powershell.exe"
$supervisorPath = Join-Path $PSScriptRoot "Run-KpahTunnelSupervisor.ps1"
$configPath = Join-Path $root "local-config\kpah-tunnel.json"
$arguments = "-NoProfile -ExecutionPolicy Bypass -WindowStyle Hidden -File `"$supervisorPath`" -RepoRoot `"$root`" -ConfigPath `"$configPath`""
$action = New-ScheduledTaskAction -Execute $powerShellExe -Argument $arguments
$trigger = New-ScheduledTaskTrigger -AtStartup
$settings = New-ScheduledTaskSettingsSet -RestartCount 999 -RestartInterval (New-TimeSpan -Minutes 1) `
    -ExecutionTimeLimit ([TimeSpan]::Zero) -StartWhenAvailable -MultipleInstances IgnoreNew
$principal = New-ScheduledTaskPrincipal -UserId "SYSTEM" -LogonType ServiceAccount -RunLevel Highest

Register-ScheduledTask -TaskName $taskName -Action $action -Trigger $trigger `
    -Settings $settings -Principal $principal -Description "Keeps the KPAH AWS reverse SSH tunnel online." -Force | Out-Null
Write-Output "Da cai Task Scheduler: $taskName"
Write-Output "Tunnel se tu khoi dong cung Windows va tu ket noi lai khi mat mang."
