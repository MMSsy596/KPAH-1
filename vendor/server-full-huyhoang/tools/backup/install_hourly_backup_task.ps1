param(
    [string]$BackupRoot = "",
    [int]$RetentionCount = 168,
    [string]$TaskName = "KPAH Hourly Google Drive Backup"
)

$ErrorActionPreference = "Stop"

function Resolve-BackupRoot {
    param([string]$BackupRoot)

    $candidates = @()
    if (![string]::IsNullOrWhiteSpace($BackupRoot)) {
        $candidates += $BackupRoot
    }
    if ($env:KPAH_GOOGLE_BACKUP_ROOT) {
        $candidates += $env:KPAH_GOOGLE_BACKUP_ROOT
    }
    $candidates += @(
        "G:\My Drive\KPAH_Server_Backups",
        "G:\MyDrive\KPAH_Server_Backups",
        (Join-Path $env:USERPROFILE "My Drive\KPAH_Server_Backups"),
        (Join-Path $env:USERPROFILE "Google Drive\KPAH_Server_Backups"),
        (Join-Path $env:USERPROFILE "Google Drive\My Drive\KPAH_Server_Backups")
    )

    foreach ($candidate in $candidates) {
        if ([string]::IsNullOrWhiteSpace($candidate)) {
            continue
        }
        $normalized = [System.IO.Path]::GetFullPath($candidate)
        $parent = Split-Path -Parent $normalized
        if ($parent -and (Test-Path -LiteralPath $parent)) {
            New-Item -ItemType Directory -Force -Path $normalized | Out-Null
            return $normalized
        }
    }

    throw "Khong tim thay thu muc Google Drive Desktop. Hay truyen -BackupRoot."
}

$scriptDir = Split-Path -Parent $PSCommandPath
$scriptPath = Join-Path $scriptDir "hourly_backup.ps1"
$runnerPath = Join-Path $scriptDir "run_hourly_backup.cmd"
if (!(Test-Path -LiteralPath $scriptPath)) {
    throw "Khong tim thay script backup: $scriptPath"
}
if (!(Test-Path -LiteralPath $runnerPath)) {
    throw "Khong tim thay file chay backup: $runnerPath"
}

$resolvedBackupRoot = Resolve-BackupRoot $BackupRoot
$env:KPAH_GOOGLE_BACKUP_ROOT = $resolvedBackupRoot
[Environment]::SetEnvironmentVariable("KPAH_GOOGLE_BACKUP_ROOT", $resolvedBackupRoot, "User")

& schtasks.exe /Create /F /SC HOURLY /MO 1 /TN $TaskName /TR $runnerPath /RL HIGHEST /IT | Out-Null

Write-Host "Da cai task: $TaskName"
Write-Host "Backup root: $resolvedBackupRoot"
Write-Host "Runner     : $runnerPath"
