param(
    [string]$BackupZip = "",
    [string]$BackupRoot = "",
    [switch]$KeepRestoredSchemas
)

$ErrorActionPreference = "Stop"

function Invoke-MySql {
    param(
        [string]$Sql,
        [string]$InputFile = ""
    )

    if ($InputFile) {
        $process = Start-Process `
            -FilePath $script:MySqlExe `
            -ArgumentList @("--defaults-extra-file=$script:RootConfig") `
            -RedirectStandardInput $InputFile `
            -RedirectStandardOutput ($InputFile + ".out") `
            -RedirectStandardError ($InputFile + ".err") `
            -NoNewWindow `
            -Wait `
            -PassThru
        if ($process.ExitCode -ne 0) {
            throw [IO.File]::ReadAllText($InputFile + ".err")
        }
        return
    }

    $result = & $script:MySqlExe ("--defaults-extra-file=" + $script:RootConfig) -N -B -e $Sql
    if ($LASTEXITCODE -ne 0) {
        throw "MariaDB restore verification command failed."
    }
    return ($result -join "`n")
}

$serverRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$repoRoot = (Resolve-Path (Join-Path $serverRoot "..\..")).Path
$script:MySqlExe = (Get-ChildItem (Join-Path $repoRoot ".toolchains\mariadb") -Recurse -Filter "mysql.exe" -File | Select-Object -First 1).FullName
$script:RootConfig = (Resolve-Path (Join-Path $repoRoot "local-config\root-client.local.ini")).Path

if (-not $BackupZip) {
    if (-not $BackupRoot) {
        $BackupRoot = Join-Path $repoRoot "runtime-local\backups"
    }
    $BackupZip = Join-Path $BackupRoot "latest\kpah_server_latest.zip"
}
$BackupZip = (Resolve-Path -LiteralPath $BackupZip).Path

$timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
$verifyRoot = Join-Path $repoRoot "runtime-local\restore-verify\$timestamp"
$extractRoot = Join-Path $verifyRoot "extracted"
New-Item -ItemType Directory -Force -Path $extractRoot | Out-Null
Expand-Archive -LiteralPath $BackupZip -DestinationPath $extractRoot -Force

$accountDump = Get-ChildItem -LiteralPath $extractRoot -Recurse -Filter "account.sql" -File | Select-Object -First 1
$gameDump = Get-ChildItem -LiteralPath $extractRoot -Recurse -Filter "kpah2.sql" -File | Select-Object -First 1
if (-not $accountDump -or -not $gameDump) {
    throw "Backup archive does not contain account.sql and kpah2.sql."
}

$suffix = Get-Date -Format "yyyyMMddHHmmss"
$accountSchema = "kpah_restore_account_$suffix"
$gameSchema = "kpah_restore_game_$suffix"
$patchedAccount = Join-Path $verifyRoot "account.restore.sql"
$patchedGame = Join-Path $verifyRoot "kpah2.restore.sql"

function Write-PatchedDump {
    param(
        [string]$Source,
        [string]$OriginalSchema,
        [string]$TargetSchema,
        [string]$Destination
    )

    $reader = [IO.StreamReader]::new($Source, [Text.Encoding]::UTF8, $true)
    $writer = [IO.StreamWriter]::new($Destination, $false, [Text.UTF8Encoding]::new($false))
    $backtick = [string][char]96
    $quotedOriginal = $backtick + $OriginalSchema + $backtick
    $quotedTarget = $backtick + $TargetSchema + $backtick
    try {
        while (($line = $reader.ReadLine()) -ne $null) {
            $writer.WriteLine($line.Replace($quotedOriginal, $quotedTarget))
        }
    }
    finally {
        $reader.Dispose()
        $writer.Dispose()
    }
}

Write-PatchedDump -Source $accountDump.FullName -OriginalSchema "account" -TargetSchema $accountSchema -Destination $patchedAccount
Write-PatchedDump -Source $gameDump.FullName -OriginalSchema "kpah2" -TargetSchema $gameSchema -Destination $patchedGame

try {
    Invoke-MySql -InputFile $patchedAccount
    Invoke-MySql -InputFile $patchedGame

    $accountTables = Invoke-MySql -Sql "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='$accountSchema';"
    $gameTables = Invoke-MySql -Sql "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='$gameSchema';"
    $accountRows = Invoke-MySql -Sql "SELECT COUNT(*) FROM ``$accountSchema``.team_user;"
    $characterRows = Invoke-MySql -Sql "SELECT COUNT(*) FROM ``$gameSchema``.tob_char;"
    $questRows = Invoke-MySql -Sql "SELECT COUNT(*) FROM ``$gameSchema``.tob_char_quest;"

    $passed = [int]$accountTables -ge 2 -and [int]$gameTables -ge 60 -and [int]$accountRows -ge 1 -and [int]$characterRows -ge 1
    $report = @(
        "# KPAH backup restore verification",
        "",
        "Backup: $BackupZip",
        "Account schema: $accountSchema",
        "Game schema: $gameSchema",
        "Account tables: $accountTables",
        "Game tables: $gameTables",
        "Account rows: $accountRows",
        "Character rows: $characterRows",
        "Quest rows: $questRows",
        "PASS: $passed"
    )
    $reportPath = Join-Path $verifyRoot "REPORT.md"
    [IO.File]::WriteAllLines($reportPath, $report, [Text.UTF8Encoding]::new($false))
    Write-Output "Restore verification report: $reportPath"
    if (-not $passed) {
        throw "Restored database counts did not meet the expected minimums."
    }
}
finally {
    if (-not $KeepRestoredSchemas) {
        Invoke-MySql -Sql "DROP DATABASE IF EXISTS ``$accountSchema``; DROP DATABASE IF EXISTS ``$gameSchema``;" | Out-Null
    }
}
