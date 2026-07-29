$ErrorActionPreference = "Stop"

$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$runtimeLogDir = Join-Path $projectRoot "logs\runtime"
if (!(Test-Path $runtimeLogDir)) {
    New-Item -ItemType Directory -Path $runtimeLogDir -Force | Out-Null
}

$launcherLog = Join-Path $runtimeLogDir "server_launcher.log"

function Write-LauncherLog {
    param([string]$Message)

    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Add-Content -LiteralPath $launcherLog -Value "[$timestamp] $Message" -Encoding UTF8
}

function Get-IniValue {
    param(
        [string]$Path,
        [string]$Key,
        [string]$DefaultValue
    )

    if (!(Test-Path $Path)) {
        return $DefaultValue
    }

    $line = Get-Content -LiteralPath $Path | Where-Object { $_ -match ('^\s*' + [regex]::Escape($Key) + '\s*=') } | Select-Object -First 1
    if (!$line) {
        return $DefaultValue
    }

    $parts = $line -split '=', 2
    if ($parts.Count -lt 2) {
        return $DefaultValue
    }
    return $parts[1].Trim()
}

function Get-ListeningProcessId {
    param([int]$Port)

    $connection = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($connection) {
        return [int]$connection.OwningProcess
    }
    return $null
}

function Wait-ForPort {
    param(
        [int]$Port,
        [int]$Seconds
    )

    for ($i = 0; $i -lt $Seconds; $i++) {
        $owner = Get-ListeningProcessId -Port $Port
        if ($owner) {
            return $owner
        }
        Start-Sleep -Seconds 1
    }
    return $null
}

function Start-HiddenJavaProcess {
    param(
        [string]$JavaExe,
        [string]$WorkingDirectory,
        [string[]]$ArgumentList,
        [string]$StdoutPath,
        [string]$StderrPath
    )

    Remove-Item -LiteralPath $StdoutPath -Force -ErrorAction SilentlyContinue
    Remove-Item -LiteralPath $StderrPath -Force -ErrorAction SilentlyContinue

    return Start-Process `
        -FilePath $JavaExe `
        -WorkingDirectory $WorkingDirectory `
        -ArgumentList $ArgumentList `
        -RedirectStandardOutput $StdoutPath `
        -RedirectStandardError $StderrPath `
        -WindowStyle Hidden `
        -PassThru
}

try {
    $javaBin = & powershell -NoProfile -ExecutionPolicy Bypass -File (Join-Path $projectRoot "tools\java\resolve_java_bin.ps1") -RepoRoot $projectRoot -Role runtime
    if ([string]::IsNullOrWhiteSpace($javaBin)) {
        throw "Khong tim thay java runtime."
    }

    $javaExe = Join-Path $javaBin "java.exe"
    if (!(Test-Path $javaExe)) {
        throw "Khong tim thay java.exe tai $javaExe"
    }

    $loginIni = Join-Path $projectRoot "loginServer\server.ini"
    $serverIni = Join-Path $projectRoot "server.ini"
    $loginPort = [int](Get-IniValue -Path $loginIni -Key "port" -DefaultValue "8023")
    $gamePort = [int](Get-IniValue -Path $serverIni -Key "sv.port" -DefaultValue "19129")
    $localAdminPort = [int](Get-IniValue -Path $serverIni -Key "sv.localAdminPort" -DefaultValue "18023")

    $loginPid = Get-ListeningProcessId -Port $loginPort
    if ($loginPid) {
        Write-LauncherLog "Login server da dang chay tren cong $loginPort voi PID $loginPid."
    } else {
        $loginJar = Join-Path $projectRoot "loginServer\CheckLoginSocket.jar"
        if (!(Test-Path $loginJar)) {
            throw "Khong tim thay $loginJar"
        }

        $loginProcess = Start-HiddenJavaProcess `
            -JavaExe $javaExe `
            -WorkingDirectory (Join-Path $projectRoot "loginServer") `
            -ArgumentList @(
                "-Xms256m",
                "-Xmx512m",
                "-Djava.net.preferIPv4Stack=true",
                "-Dfile.encoding=UTF-8",
                "-jar",
                "CheckLoginSocket.jar"
            ) `
            -StdoutPath (Join-Path $runtimeLogDir "login_server_stdout.log") `
            -StderrPath (Join-Path $runtimeLogDir "login_server_stderr.log")
        Write-LauncherLog "Da mo login server nen voi PID $($loginProcess.Id)."
        $loginOwner = Wait-ForPort -Port $loginPort -Seconds 15
        if ($loginOwner) {
            Write-LauncherLog "Login server da nghe o cong $loginPort voi PID $loginOwner."
        } else {
            Write-LauncherLog "Khong xac nhan duoc login server tren cong $loginPort sau 15 giay."
        }
    }

    $gamePid = Get-ListeningProcessId -Port $gamePort
    if ($gamePid) {
        Write-LauncherLog "Game server da dang chay tren cong $gamePort voi PID $gamePid."
    } else {
        $gameJar = Join-Path $projectRoot "KPAH.jar"
        if (!(Test-Path $gameJar)) {
            throw "Khong tim thay $gameJar"
        }

        $gameProcess = Start-HiddenJavaProcess `
            -JavaExe $javaExe `
            -WorkingDirectory $projectRoot `
            -ArgumentList @(
                "-Xms4096m",
                "-Xmx4096m",
                "-XX:+UseG1GC",
                "-XX:MaxGCPauseMillis=100",
                "-XX:+ParallelRefProcEnabled",
                "-Djava.net.preferIPv4Stack=true",
                "-Dfile.encoding=UTF-8",
                "-Djava.awt.headless=true",
                "-jar",
                "KPAH.jar"
            ) `
            -StdoutPath (Join-Path $runtimeLogDir "game_server_stdout.log") `
            -StderrPath (Join-Path $runtimeLogDir "game_server_stderr.log")
        Write-LauncherLog "Da mo game server nen voi PID $($gameProcess.Id)."

        $gameOwner = Wait-ForPort -Port $gamePort -Seconds 20
        if ($gameOwner) {
            Write-LauncherLog "Game server da nghe o cong $gamePort voi PID $gameOwner."
        } else {
            Write-LauncherLog "Khong xac nhan duoc game server tren cong $gamePort sau 20 giay."
        }

        $localAdminOwner = Wait-ForPort -Port $localAdminPort -Seconds 10
        if ($localAdminOwner) {
            Write-LauncherLog "Local admin HTTP da nghe o cong $localAdminPort voi PID $localAdminOwner."
        } else {
            Write-LauncherLog "Khong xac nhan duoc local admin HTTP tren cong $localAdminPort."
        }
    }
} catch {
    Write-LauncherLog ("LOI: " + $_.Exception.Message)
    throw
}
