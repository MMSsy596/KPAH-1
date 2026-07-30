param(
    [string]$ProjectRoot = "",
    [string]$BackupRoot = "",
    [int]$RetentionCount = 168
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

function Write-Log {
    param(
        [string]$Message,
        [string]$LogFile
    )

    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $line = "[$timestamp] $Message"
    Write-Host $line
    if ($LogFile) {
        Add-Content -LiteralPath $LogFile -Value $line -Encoding UTF8
    }
}

function Read-JavaProperties {
    param([string]$Path)

    $result = @{}
    if (!(Test-Path -LiteralPath $Path)) {
        return $result
    }

    foreach ($rawLine in Get-Content -LiteralPath $Path -Encoding UTF8) {
        $line = $rawLine.Trim()
        if ($line.Length -eq 0 -or $line.StartsWith("#") -or $line.StartsWith("//")) {
            continue
        }
        $index = $line.IndexOf("=")
        if ($index -lt 0) {
            continue
        }
        $key = $line.Substring(0, $index).Trim()
        $value = $line.Substring($index + 1).Trim()
        if ($key.Length -gt 0) {
            $result[$key] = $value
        }
    }
    return $result
}

function Parse-JdbcUrl {
    param([string]$JdbcUrl)

    if ([string]::IsNullOrWhiteSpace($JdbcUrl)) {
        return $null
    }

    if ($JdbcUrl -notmatch "^jdbc:mysql://([^/:]+)(?::(\d+))?/([^?;]+)") {
        return $null
    }

    return [pscustomobject]@{
        Host = $matches[1]
        Port = if ($matches[2]) { [int]$matches[2] } else { 3306 }
        Database = $matches[3]
    }
}

function Resolve-ProjectRoot {
    param([string]$ProjectRoot)

    if (![string]::IsNullOrWhiteSpace($ProjectRoot)) {
        return (Resolve-Path -LiteralPath $ProjectRoot).Path
    }

    $scriptDir = Split-Path -Parent $PSCommandPath
    return (Resolve-Path -LiteralPath (Join-Path $scriptDir "..\..")).Path
}

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

    throw "Khong tim thay thu muc Google Drive Desktop. Hay truyen -BackupRoot hoac dat bien moi truong KPAH_GOOGLE_BACKUP_ROOT."
}

function Resolve-MysqlDump {
    $candidates = @(
        (Get-ChildItem `
            -LiteralPath (Join-Path $PSScriptRoot "..\..\..\..\.toolchains\mariadb") `
            -Recurse `
            -Filter "mysqldump.exe" `
            -File `
            -ErrorAction SilentlyContinue |
            Select-Object -First 1 |
            ForEach-Object { $_.FullName }),
        "C:\mysql\bin\mysqldump.exe",
        "C:\xampp\mysql\bin\mysqldump.exe",
        "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe",
        "C:\Program Files\MariaDB 10.4\bin\mysqldump.exe"
    )
    foreach ($candidate in $candidates) {
        if ($candidate -and (Test-Path -LiteralPath $candidate)) {
            return $candidate
        }
    }
    throw "Khong tim thay mysqldump.exe. Hay cai XAMPP/MySQL hoac sua script backup."
}

function Add-DatabaseTarget {
    param(
        [System.Collections.Generic.List[object]]$Targets,
        [string]$DbHost,
        [int]$DbPort,
        [string]$Database,
        [string]$User,
        [string]$Password,
        [string]$Source
    )

    if ([string]::IsNullOrWhiteSpace($Database) -or [string]::IsNullOrWhiteSpace($User)) {
        return
    }

    $key = "{0}|{1}|{2}|{3}" -f $DbHost, $DbPort, $Database, $User
    foreach ($target in $Targets) {
        if ($target.Key -eq $key) {
            return
        }
    }

    $Targets.Add([pscustomobject]@{
        Key = $key
        Host = if ([string]::IsNullOrWhiteSpace($DbHost)) { "127.0.0.1" } else { $DbHost }
        Port = if ($DbPort -gt 0) { $DbPort } else { 3306 }
        Database = $Database
        User = $User
        Password = if ($null -eq $Password) { "" } else { $Password }
        Source = $Source
    }) | Out-Null
}

function Get-DatabaseTargets {
    param([string]$ProjectRoot)

    $targets = New-Object 'System.Collections.Generic.List[object]'

    $serverIni = Read-JavaProperties (Join-Path $ProjectRoot "server.ini")
    if ($serverIni.Count -gt 0) {
        $hostParts = (($serverIni["db.host"] | ForEach-Object { $_ }) -as [string])
        $dbHost = "127.0.0.1"
        $dbPort = 3306
        if ($hostParts -and $hostParts.Contains(":")) {
            $split = $hostParts.Split(":")
            $dbHost = $split[0]
            $dbPort = [int]$split[1]
        } elseif ($hostParts) {
            $dbHost = $hostParts
        }
        Add-DatabaseTarget -Targets $targets -DbHost $dbHost -DbPort $dbPort -Database $serverIni["db.name"] -User $serverIni["db.user"] -Password $serverIni["db.password"] -Source "server.ini:db.name"
        Add-DatabaseTarget -Targets $targets -DbHost $dbHost -DbPort $dbPort -Database $serverIni["db.nameNap"] -User $serverIni["db.userNap"] -Password $serverIni["db.pass"] -Source "server.ini:db.nameNap"

        $host1Parts = (($serverIni["db.host1"] | ForEach-Object { $_ }) -as [string])
        $dbHost1 = "127.0.0.1"
        $dbPort1 = 3306
        if ($host1Parts -and $host1Parts.Contains(":")) {
            $split = $host1Parts.Split(":")
            $dbHost1 = $split[0]
            $dbPort1 = [int]$split[1]
        } elseif ($host1Parts) {
            $dbHost1 = $host1Parts
        }
        Add-DatabaseTarget -Targets $targets -DbHost $dbHost1 -DbPort $dbPort1 -Database $serverIni["db.name1"] -User $serverIni["db.user1"] -Password $serverIni["db.password1"] -Source "server.ini:db.name1"
    }

    $loginIni = Read-JavaProperties (Join-Path $ProjectRoot "loginServer\server.ini")
    if ($loginIni.Count -gt 0) {
        $jdbc = Parse-JdbcUrl $loginIni["db.url"]
        if ($jdbc) {
            Add-DatabaseTarget -Targets $targets -DbHost $jdbc.Host -DbPort $jdbc.Port -Database $jdbc.Database -User $loginIni["db.user"] -Password $loginIni["db.password"] -Source "loginServer/server.ini"
        }
    }

    return $targets
}

function Invoke-MySqlDump {
    param(
        [string]$MysqlDumpExe,
        [object]$Target,
        [string]$OutputFile,
        [string]$LogFile
    )

    $defaultsFile = [IO.Path]::GetTempFileName()
    try {
        $defaults = @(
            "[client]",
            ("host=" + $Target.Host),
            ("port=" + $Target.Port),
            ("user=" + $Target.User),
            ("password=" + $Target.Password)
        )
        [IO.File]::WriteAllLines($defaultsFile, $defaults, [Text.UTF8Encoding]::new($false))

        $arguments = New-Object System.Collections.Generic.List[string]
        $arguments.Add("--defaults-extra-file=$defaultsFile") | Out-Null
        $arguments.Add("--single-transaction") | Out-Null
        $arguments.Add("--skip-lock-tables") | Out-Null
        $arguments.Add("--routines") | Out-Null
        $arguments.Add("--events") | Out-Null
        $arguments.Add("--triggers") | Out-Null
        $arguments.Add("--default-character-set=utf8mb4") | Out-Null
        $arguments.Add("--databases") | Out-Null
        $arguments.Add($Target.Database) | Out-Null

        Write-Log ("Dump DB {0} ({1})" -f $Target.Database, $Target.Source) $LogFile
        $process = Start-Process -FilePath $MysqlDumpExe -ArgumentList $arguments -NoNewWindow -PassThru -Wait -RedirectStandardOutput $OutputFile -RedirectStandardError ($OutputFile + ".err")
        if ($process.ExitCode -ne 0) {
            $stderr = ""
            if (Test-Path -LiteralPath ($OutputFile + ".err")) {
                $stderr = [IO.File]::ReadAllText($OutputFile + ".err")
            }
            throw ("mysqldump that bai cho DB {0}. {1}" -f $Target.Database, $stderr.Trim())
        }
        Remove-Item -LiteralPath ($OutputFile + ".err") -Force -ErrorAction SilentlyContinue
    }
    finally {
        Remove-Item -LiteralPath $defaultsFile -Force -ErrorAction SilentlyContinue
    }
}

function Copy-IfExists {
    param(
        [string]$Source,
        [string]$Destination
    )

    if (!(Test-Path -LiteralPath $Source)) {
        return
    }
    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $Destination) | Out-Null
    Copy-Item -LiteralPath $Source -Destination $Destination -Recurse -Force
}

function Copy-ExternalFileIfExists {
    param(
        [string]$Source,
        [string]$DestinationRoot,
        [string]$RelativePath
    )

    if (!(Test-Path -LiteralPath $Source)) {
        return
    }

    $destinationPath = Join-Path $DestinationRoot $RelativePath
    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $destinationPath) | Out-Null
    Copy-Item -LiteralPath $Source -Destination $destinationPath -Force
}

function Remove-OldSnapshots {
    param(
        [string]$SnapshotRoot,
        [int]$RetentionCount,
        [string]$LogFile
    )

    if ($RetentionCount -lt 1 -or !(Test-Path -LiteralPath $SnapshotRoot)) {
        return
    }

    $files = Get-ChildItem -LiteralPath $SnapshotRoot -Filter "*.zip" -File | Sort-Object LastWriteTime -Descending
    if ($files.Count -le $RetentionCount) {
        return
    }

    $filesToDelete = $files | Select-Object -Skip $RetentionCount
    foreach ($file in $filesToDelete) {
        Write-Log ("Xoa snapshot cu: {0}" -f $file.FullName) $LogFile
        Remove-Item -LiteralPath $file.FullName -Force
    }
}

$resolvedProjectRoot = Resolve-ProjectRoot $ProjectRoot
$resolvedBackupRoot = Resolve-BackupRoot $BackupRoot
$snapshotRoot = Join-Path $resolvedBackupRoot "snapshots"
$latestRoot = Join-Path $resolvedBackupRoot "latest"
$logRoot = Join-Path $resolvedBackupRoot "logs"
$logFile = Join-Path $logRoot "hourly_backup.log"
$lockFile = Join-Path $resolvedBackupRoot "backup.lock"
$lockStream = $null

New-Item -ItemType Directory -Force -Path $snapshotRoot, $latestRoot, $logRoot | Out-Null

$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$stageRoot = Join-Path $env:TEMP ("kpah_hourly_backup_" + $timestamp)
$stageDataRoot = Join-Path $stageRoot ("kpah_server_backup_" + $timestamp)
$dbStage = Join-Path $stageDataRoot "databases"
$filesStage = Join-Path $stageDataRoot "server_files"

try {
    try {
        $lockStream = [System.IO.File]::Open($lockFile, [System.IO.FileMode]::OpenOrCreate, [System.IO.FileAccess]::ReadWrite, [System.IO.FileShare]::None)
        $lockContent = "pid=$PID`r`ntimestamp=$timestamp`r`nproject_root=$resolvedProjectRoot`r`n"
        $lockBytes = [System.Text.Encoding]::UTF8.GetBytes($lockContent)
        $lockStream.SetLength(0)
        $lockStream.Write($lockBytes, 0, $lockBytes.Length)
        $lockStream.Flush()
    }
    catch {
        Write-Log "Bo qua lan backup nay vi dang co mot tien trinh backup khac chay." $logFile
        return
    }

    Write-Log "Bat dau backup theo gio." $logFile
    New-Item -ItemType Directory -Force -Path $dbStage, $filesStage | Out-Null

    $mysqlDumpExe = Resolve-MysqlDump
    $dbTargets = Get-DatabaseTargets $resolvedProjectRoot
    if ($dbTargets.Count -eq 0) {
        throw "Khong tim thay cau hinh database nao de dump."
    }

    foreach ($target in $dbTargets) {
        $safeName = ($target.Database -replace "[^a-zA-Z0-9._-]", "_")
        $outputFile = Join-Path $dbStage ($safeName + ".sql")
        Invoke-MySqlDump -MysqlDumpExe $mysqlDumpExe -Target $target -OutputFile $outputFile -LogFile $logFile
    }

    $includeDirectories = @(
        "admin_local_src",
        "config",
        "dist",
        "web",
        "loginServer",
        "src",
        "libs",
        "lib",
        "map",
        "tools\admin_panel_config",
        "tools\maintenance_attach",
        "portable_release\KPAH_Auto_Portable",
        "build\classes"
    )

    foreach ($relativeDir in $includeDirectories) {
        $sourcePath = Join-Path $resolvedProjectRoot $relativeDir
        $destinationPath = Join-Path $filesStage $relativeDir
        Copy-IfExists -Source $sourcePath -Destination $destinationPath
    }

    $includeFiles = @(
        "server.ini",
        "build.xml",
        "manifest.mf",
        "run.bat",
        "run_server.bat",
        "run_admin_panel_clone.bat",
        "mo_panel_admin_rieng.bat",
        "build_server.bat",
        "build_admin_local.bat",
        "package_admin_local.bat",
        "find_running_server.ps1",
        "kpah.service",
        "README.md",
        "KPAH.jar",
        "KPAH_sell_at_place_20260330.jar",
        "dist\KPAH2.jar",
        "dist\KPAH2_sell_at_place_20260330.jar"
    )

    foreach ($relativeFile in $includeFiles) {
        $sourcePath = Join-Path $resolvedProjectRoot $relativeFile
        $destinationPath = Join-Path $filesStage $relativeFile
        Copy-IfExists -Source $sourcePath -Destination $destinationPath
    }

    $externalFiles = @(
        [pscustomobject]@{ Source = "C:\mysql\bin\my.ini"; RelativePath = "external_configs\mysql\my.ini" },
        [pscustomobject]@{ Source = "C:\nginx-1.30.0\conf\nginx.conf"; RelativePath = "external_configs\nginx\nginx.conf" },
        [pscustomobject]@{ Source = "C:\php\php.ini"; RelativePath = "external_configs\php\php.ini" },
        [pscustomobject]@{ Source = "C:\xampp_retired_20260426\mysql\bin\my.ini"; RelativePath = "external_configs\retired_xampp\mysql\my.ini" },
        [pscustomobject]@{ Source = "C:\xampp_retired_20260426\apache\conf\httpd.conf"; RelativePath = "external_configs\retired_xampp\apache\httpd.conf" },
        [pscustomobject]@{ Source = "C:\xampp_retired_20260426\apache\conf\extra\httpd-vhosts.conf"; RelativePath = "external_configs\retired_xampp\apache\httpd-vhosts.conf" },
        [pscustomobject]@{ Source = "C:\xampp_retired_20260426\php\php.ini"; RelativePath = "external_configs\retired_xampp\php\php.ini" }
    )

    foreach ($externalFile in $externalFiles) {
        Copy-ExternalFileIfExists -Source $externalFile.Source -DestinationRoot $filesStage -RelativePath $externalFile.RelativePath
    }

    $manifest = @(
        "timestamp=$timestamp",
        "project_root=$resolvedProjectRoot",
        "backup_root=$resolvedBackupRoot",
        "snapshot_name=kpah_server_backup_$timestamp.zip",
        "databases=" + (($dbTargets | ForEach-Object { $_.Database }) -join ","),
        "server_ini=" + (Join-Path $resolvedProjectRoot "server.ini"),
        "login_ini=" + (Join-Path $resolvedProjectRoot "loginServer\server.ini")
    )
    Set-Content -LiteralPath (Join-Path $stageDataRoot "backup-manifest.txt") -Value $manifest -Encoding UTF8

    $zipName = "kpah_server_backup_$timestamp.zip"
    $zipPath = Join-Path $snapshotRoot $zipName
    if (Test-Path -LiteralPath $zipPath) {
        Remove-Item -LiteralPath $zipPath -Force
    }
    Compress-Archive -Path (Join-Path $stageDataRoot "*") -DestinationPath $zipPath -CompressionLevel Optimal

    $latestZip = Join-Path $latestRoot "kpah_server_latest.zip"
    $latestInfo = Join-Path $latestRoot "LAST_BACKUP.txt"
    Copy-Item -LiteralPath $zipPath -Destination $latestZip -Force
    Set-Content -LiteralPath $latestInfo -Value @(
        "timestamp=$timestamp",
        "zip=$zipPath",
        "project_root=$resolvedProjectRoot"
    ) -Encoding UTF8

    Remove-OldSnapshots -SnapshotRoot $snapshotRoot -RetentionCount $RetentionCount -LogFile $logFile
    Write-Log ("Backup thanh cong: {0}" -f $zipPath) $logFile
}
finally {
    if (Test-Path -LiteralPath $stageRoot) {
        Remove-Item -LiteralPath $stageRoot -Recurse -Force -ErrorAction SilentlyContinue
    }
    if ($lockStream) {
        $lockStream.Dispose()
        $lockStream = $null
    }
    Remove-Item -LiteralPath $lockFile -Force -ErrorAction SilentlyContinue
}
