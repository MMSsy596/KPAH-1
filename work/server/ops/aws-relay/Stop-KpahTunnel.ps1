param([string]$RepoRoot = "")

$ErrorActionPreference = "Stop"
$root = if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
} else {
    (Resolve-Path -LiteralPath $RepoRoot).Path
}
$runtimeDir = Join-Path $root "runtime-local\ssh-tunnel"
$sshPidPath = Join-Path $runtimeDir "ssh.pid"
$supervisorPidPath = Join-Path $runtimeDir "supervisor.pid"

# Stop the reconnect loop first so it cannot create a replacement SSH process
# while the child is being stopped.
foreach ($pidFile in @($supervisorPidPath, $sshPidPath)) {
    if (-not (Test-Path -LiteralPath $pidFile)) {
        continue
    }
    $processId = [int](Get-Content -LiteralPath $pidFile -Raw)
    $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
    if ($process) {
        Stop-Process -Id $processId -Force
        Write-Output "Da dung PID $processId."
    }
    Remove-Item -LiteralPath $pidFile -Force -ErrorAction SilentlyContinue
}
