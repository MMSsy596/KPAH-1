param(
    [int]$DurationSeconds = 180,
    [string]$FirstUsername = "1",
    [string]$FirstCharacter = "1adgjm",
    [string]$SecondUsername = "abc123",
    [string]$SecondCharacter = "abc1",
    [switch]$EnableParty,
    [switch]$TestSocial,
    [int]$TestMapId = 343
)

$ErrorActionPreference = "Stop"

function Wait-ForInGame {
    param(
        [string[]]$StatusFiles,
        [int]$TimeoutSeconds
    )

    $deadline = (Get-Date).AddSeconds($TimeoutSeconds)
    while ((Get-Date) -lt $deadline) {
        $ready = 0
        foreach ($statusFile in $StatusFiles) {
            if (Test-Path -LiteralPath $statusFile) {
                $content = Get-Content -LiteralPath $statusFile -Raw
                if ($content -match "(?m)^in_game=true\s*$") {
                    $ready++
                }
            }
        }
        if ($ready -eq $StatusFiles.Count) {
            return $true
        }
        Start-Sleep -Seconds 2
    }
    return $false
}

function Invoke-MySqlText {
    param([string]$Sql)

    $output = & $script:MySqlExe ("--defaults-extra-file=" + $script:MySqlConfig) -N -B -e $Sql
    if ($LASTEXITCODE -ne 0) {
        throw "MariaDB command failed."
    }
    return ($output -join "`n")
}

function Get-CharacterSnapshot {
    param([string[]]$CharacterNames)

    $quoted = $CharacterNames | ForEach-Object { "'" + $_.Replace("'", "''") + "'" }
    $sql = @"
SELECT c.charname,c.lastLv,c.xp,MD5(COALESCE(c.inven,'')),MD5(COALESCE(c.potion,'')),
       LENGTH(COALESCE(c.inven,'')),LENGTH(COALESCE(c.potion,'')),
       COALESCE(q.monskilled,''),COALESCE(q.itemget,''),COALESCE(q.isfinish,'')
FROM kpah2.tob_char c
LEFT JOIN kpah2.tob_char_quest q ON q.id_char=c.id
WHERE c.charname IN ($($quoted -join ','))
ORDER BY c.charname
"@
    return Invoke-MySqlText -Sql $sql
}

function New-ClientConfig {
    param(
        [string]$Path,
        [string]$Username,
        [string]$Password,
        [string]$ProfileName,
        [int]$TeamMode,
        [string]$LeaderName,
        [string]$TeamMembers
    )

    $properties = @(
        "enabled=true",
        "login_username=$Username",
        "login_password=$Password",
        "profile_name=$ProfileName",
        "login_settings_locked=true",
        "server_index=0",
        "custom_host=127.0.0.1",
        "custom_port=19129",
        "custom_server_name=KPAH Local",
        "character_index=0",
        "auto_mode=2",
        "attack_skills=0",
        "focus_mode=0",
        "team_mode=$TeamMode",
        "leader_name=$LeaderName",
        "team_members=$TeamMembers",
        "auto_party=true",
        ("auto_travel_by_level=" + $(if ($script:TestMapId -gt 0) { "false" } else { "true" })),
        "pickup_potion=true",
        "pickup_equipment=true",
        "pickup_material=true",
        "pickup_all=true",
        "hp_percent=60",
        "mp_percent=40",
        "poll_interval_ms=500",
        "reconnect_delay_ms=3000",
        "reload_when_no_damage=true",
        "no_damage_timeout_seconds=8"
    )
    [IO.File]::WriteAllLines($Path, $properties, [Text.UTF8Encoding]::new($false))
}

$serverRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$script:TestMapId = $TestMapId
$repoRoot = (Resolve-Path (Join-Path $serverRoot "..\..")).Path
$appRoot = Join-Path $repoRoot "work\client-j2me\dist\auto-harness\KPAH_Auto_Tool"
$launcherJar = Join-Path $appRoot "app\kpah-launcher.jar"
$clientJar = Join-Path $appRoot "app\grinding2-local.jar"
$javaExe = (Get-ChildItem (Join-Path $repoRoot ".toolchains\jdk17") -Recurse -Filter "java.exe" -File | Select-Object -First 1).FullName
$script:MySqlExe = (Get-ChildItem (Join-Path $repoRoot ".toolchains\mariadb") -Recurse -Filter "mysql.exe" -File | Select-Object -First 1).FullName
$script:MySqlConfig = (Resolve-Path (Join-Path $repoRoot "local-config\app-client.local.ini")).Path

foreach ($required in @($launcherJar, $clientJar, $javaExe, $script:MySqlExe, $script:MySqlConfig)) {
    if (-not $required -or -not (Test-Path -LiteralPath $required)) {
        throw "Missing regression prerequisite: $required"
    }
}
foreach ($port in @(3306, 8023, 19129)) {
    if (-not (Get-NetTCPConnection -State Listen -LocalPort $port -ErrorAction SilentlyContinue)) {
        throw "Required local service is not listening on port $port."
    }
}

$timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
$runRoot = Join-Path $repoRoot "runtime-local\regression\$timestamp"
$clientRoots = @((Join-Path $runRoot "client1"), (Join-Path $runRoot "client2"))
foreach ($clientRoot in $clientRoots) {
    New-Item -ItemType Directory -Force -Path $clientRoot, (Join-Path $clientRoot "data") | Out-Null
}

$passwordBytes = New-Object byte[] 18
[Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($passwordBytes)
$testPassword = [Convert]::ToBase64String($passwordBytes).Replace("+", "A").Replace("/", "B")
$safeUsers = @($FirstUsername, $SecondUsername) | ForEach-Object { "'" + $_.Replace("'", "''") + "'" }
$escapedPassword = $testPassword.Replace("'", "''")
Invoke-MySqlText -Sql "UPDATE account.team_user SET password=PASSWORD('$escapedPassword'),ban=0 WHERE username IN ($($safeUsers -join ','));" | Out-Null

$configFiles = @((Join-Path $clientRoots[0] "config.properties"), (Join-Path $clientRoots[1] "config.properties"))
$statusFiles = @((Join-Path $clientRoots[0] "status.properties"), (Join-Path $clientRoots[1] "status.properties"))
$firstTeamMode = if ($EnableParty) { 1 } else { 0 }
$secondTeamMode = if ($EnableParty) { 2 } else { 0 }
New-ClientConfig -Path $configFiles[0] -Username $FirstUsername -Password $testPassword -ProfileName "regression-1" -TeamMode $firstTeamMode -LeaderName $FirstCharacter -TeamMembers $SecondCharacter
New-ClientConfig -Path $configFiles[1] -Username $SecondUsername -Password $testPassword -ProfileName "regression-2" -TeamMode $secondTeamMode -LeaderName $FirstCharacter -TeamMembers $SecondCharacter

$before = Get-CharacterSnapshot -CharacterNames @($FirstCharacter, $SecondCharacter)
$processes = @()
$socialResult = "not_requested"
try {
    for ($i = 0; $i -lt 2; $i++) {
        $arguments = @(
            "-Dfile.encoding=ISO_8859_1",
            "-Dsun.java2d.noddraw=true",
            "-Dsun.java2d.d3d=false",
            "-jar", $launcherJar,
            "--no-ui",
            "--local-regression",
            "--config", $configFiles[$i],
            "--data-dir", (Join-Path $clientRoots[$i] "data"),
            "--client", $clientJar,
            "--status-file", $statusFiles[$i],
            "--profile-name", ("regression-" + ($i + 1))
        )
        $processes += Start-Process `
            -FilePath $javaExe `
            -ArgumentList $arguments `
            -WorkingDirectory (Join-Path $appRoot "app") `
            -RedirectStandardOutput (Join-Path $clientRoots[$i] "stdout.log") `
            -RedirectStandardError (Join-Path $clientRoots[$i] "stderr.log") `
            -WindowStyle Hidden `
            -PassThru
    }

    $bothInGame = Wait-ForInGame -StatusFiles $statusFiles -TimeoutSeconds 120
    $connectedPids = @(
        Get-NetTCPConnection -RemotePort 19129 -State Established -ErrorAction SilentlyContinue |
            Where-Object { $_.OwningProcess -in $processes.Id } |
            Select-Object -ExpandProperty OwningProcess -Unique
    )
    if ($bothInGame -and $TestMapId -gt 0) {
        $serverIni = Get-Content -LiteralPath (Join-Path $serverRoot "server.ini")
        $adminToken = (($serverIni | Where-Object { $_ -match "^\s*sv\.localAdminToken\s*=" } | Select-Object -First 1) -split "=", 2)[1].Trim()
        foreach ($characterName in @($FirstCharacter, $SecondCharacter)) {
            Invoke-WebRequest `
                -UseBasicParsing `
                -Uri "http://127.0.0.1:18023/api/command/player/teleport" `
                -Method Post `
                -Headers @{ "X-Admin-Token" = $adminToken } `
                -Body @{
                    playerName = $characterName
                    mapId = $TestMapId
                    x = 208
                    y = 160
                } | Out-Null
        }
        Start-Sleep -Seconds 3
        if ($TestSocial) {
            try {
                $socialResult = (Invoke-WebRequest `
                    -UseBasicParsing `
                    -Uri "http://127.0.0.1:18023/api/command/regression/social" `
                    -Method Post `
                    -Headers @{ "X-Admin-Token" = $adminToken } `
                    -Body @{
                        firstPlayer = $FirstCharacter
                        secondPlayer = $SecondCharacter
                    }).Content.Trim()
            } catch {
                $socialResult = if ($_.ErrorDetails.Message) {
                    $_.ErrorDetails.Message.Trim()
                } else {
                    $_.Exception.Message
                }
            }
        }
    }
    if ($bothInGame) {
        Start-Sleep -Seconds ([Math]::Max(15, $DurationSeconds))
    }
}
finally {
    foreach ($process in $processes) {
        Stop-Process -Id $process.Id -Force -ErrorAction SilentlyContinue
    }
}

Start-Sleep -Seconds 8
$after = Get-CharacterSnapshot -CharacterNames @($FirstCharacter, $SecondCharacter)
$statusCopies = @()
foreach ($statusFile in $statusFiles) {
    $statusCopies += if (Test-Path -LiteralPath $statusFile) {
        (Get-Content -LiteralPath $statusFile -Raw).Trim()
    } else {
        "missing"
    }
}

$report = @(
    "# KPAH gameplay regression",
    "",
    "Run: $timestamp",
    "Duration requested: $DurationSeconds seconds",
    "Party automation enabled: $EnableParty",
    "Social regression requested: $TestSocial",
    "Test map: $TestMapId",
    "Both clients reached in-game: $bothInGame",
    "Concurrent established client PIDs: $($connectedPids.Count)",
    "",
    "## Social regression",
    '```properties',
    $socialResult,
    '```',
    "",
    "## Database before",
    '```text',
    $before,
    '```',
    "",
    "## Database after logout/save",
    '```text',
    $after,
    '```',
    "",
    "## Client 1 status",
    '```properties',
    $statusCopies[0],
    '```',
    "",
    "## Client 2 status",
    '```properties',
    $statusCopies[1],
    '```'
)
$reportPath = Join-Path $runRoot "REPORT.md"
[IO.File]::WriteAllLines($reportPath, $report, [Text.UTF8Encoding]::new($false))

Write-Output "Regression report: $reportPath"
Write-Output "Both clients in game: $bothInGame"
Write-Output "Concurrent game connections: $($connectedPids.Count)"
Write-Output "Social regression: $socialResult"
Write-Output "Before: $before"
Write-Output "After : $after"
