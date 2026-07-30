param(
    [double]$DurationHours = 6,
    [int]$SampleSeconds = 30
)

$ErrorActionPreference = "Stop"

if ($DurationHours -le 0 -or $SampleSeconds -lt 5) {
    throw "DurationHours must be positive and SampleSeconds must be at least 5."
}

$serverRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$repoRoot = (Resolve-Path (Join-Path $serverRoot "..\..")).Path
$timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
$runRoot = Join-Path $repoRoot "runtime-local\soak\$timestamp"
New-Item -ItemType Directory -Force -Path $runRoot | Out-Null
$csvPath = Join-Path $runRoot "samples.csv"
$reportPath = Join-Path $runRoot "REPORT.md"

$serverIni = Get-Content -LiteralPath (Join-Path $serverRoot "server.ini")
$adminToken = (($serverIni | Where-Object { $_ -match "^\s*sv\.localAdminToken\s*=" } | Select-Object -First 1) -split "=", 2)[1].Trim()
$mysqlExe = (Get-ChildItem (Join-Path $repoRoot ".toolchains\mariadb") -Recurse -Filter "mysqladmin.exe" -File | Select-Object -First 1).FullName
$mysqlConfig = (Resolve-Path (Join-Path $repoRoot "local-config\app-client.local.ini")).Path
$stdoutLog = Join-Path $serverRoot "logs\runtime\game_server_stdout.log"
$stderrLog = Join-Path $serverRoot "logs\runtime\game_server_stderr.log"

"timestamp,db,login,game,admin,game_pid,working_set_mb,online_players,error_lines" |
    Set-Content -LiteralPath $csvPath -Encoding UTF8

$startedAt = Get-Date
$deadline = $startedAt.AddHours($DurationHours)
$samples = 0
$failedSamples = 0
$peakMemoryMb = 0

while ((Get-Date) -lt $deadline) {
    $now = Get-Date
    $dbOk = $false
    if ($mysqlExe) {
        & $mysqlExe ("--defaults-extra-file=" + $mysqlConfig) ping --silent *> $null
        $dbOk = $LASTEXITCODE -eq 0
    }

    $listeners = @{}
    foreach ($port in @(8023, 19129, 18023)) {
        $connection = Get-NetTCPConnection -State Listen -LocalPort $port -ErrorAction SilentlyContinue | Select-Object -First 1
        $listeners[$port] = $connection
    }

    $adminOk = $false
    $onlinePlayers = -1
    try {
        $response = Invoke-WebRequest `
            -UseBasicParsing `
            -Uri "http://127.0.0.1:18023/api/status" `
            -Headers @{ "X-Admin-Token" = $adminToken } `
            -TimeoutSec 5
        $adminOk = $response.StatusCode -eq 200
        if ($response.Content -match "(?m)^online_players=(\d+)\s*$") {
            $onlinePlayers = [int]$matches[1]
        }
    } catch {
        $adminOk = $false
    }

    $gamePid = if ($listeners[19129]) { [int]$listeners[19129].OwningProcess } else { 0 }
    $memoryMb = 0
    if ($gamePid -gt 0) {
        $gameProcess = Get-Process -Id $gamePid -ErrorAction SilentlyContinue
        if ($gameProcess) {
            $memoryMb = [Math]::Round($gameProcess.WorkingSet64 / 1MB, 1)
            $peakMemoryMb = [Math]::Max($peakMemoryMb, $memoryMb)
        }
    }

    $errorLines = 0
    foreach ($logPath in @($stdoutLog, $stderrLog)) {
        if (Test-Path -LiteralPath $logPath) {
            $errorLines += @(
                Select-String `
                    -LiteralPath $logPath `
                    -Pattern "Exception|SQLException|OutOfMemoryError|NullPointerException" `
                    -ErrorAction SilentlyContinue
            ).Count
        }
    }

    $samplePassed = $dbOk -and $listeners[8023] -and $listeners[19129] -and $adminOk
    if (-not $samplePassed) {
        $failedSamples++
    }
    $samples++
    $row = @(
        $now.ToString("yyyy-MM-dd HH:mm:ss"),
        [int]$dbOk,
        [int][bool]$listeners[8023],
        [int][bool]$listeners[19129],
        [int]$adminOk,
        $gamePid,
        $memoryMb,
        $onlinePlayers,
        $errorLines
    ) -join ","
    Add-Content -LiteralPath $csvPath -Value $row -Encoding UTF8

    Start-Sleep -Seconds $SampleSeconds
}

$endedAt = Get-Date
$passed = $samples -gt 0 -and $failedSamples -eq 0
$report = @(
    "# KPAH soak test",
    "",
    "Started: $($startedAt.ToString('yyyy-MM-dd HH:mm:ss'))",
    "Ended: $($endedAt.ToString('yyyy-MM-dd HH:mm:ss'))",
    "Requested hours: $DurationHours",
    "Samples: $samples",
    "Failed samples: $failedSamples",
    "Peak game working set MB: $peakMemoryMb",
    "PASS: $passed",
    "",
    "Samples: $csvPath"
)
[IO.File]::WriteAllLines($reportPath, $report, [Text.UTF8Encoding]::new($false))
Write-Output "Soak report: $reportPath"
