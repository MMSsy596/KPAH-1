param(
    [string]$RepoRoot = "",
    [string]$StageRoot = "",
    [string]$JavaHome = "",
    [string]$NginxRoot = "C:\nginx",
    [string]$PhpRoot = "C:\php",
    [int]$WebPort = 8088,
    [int]$PhpFastCgiPort = 9072,
    [int]$GameXmsMb = 4096,
    [int]$GameXmxMb = 4096
)

$ErrorActionPreference = "Stop"

function Resolve-RepoRoot {
    param([string]$InputRoot)

    if (-not [string]::IsNullOrWhiteSpace($InputRoot)) {
        return (Resolve-Path -LiteralPath $InputRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
}

function Read-KeyValueFile {
    param([string]$Path)

    $result = @{}
    if (-not (Test-Path -LiteralPath $Path)) {
        return $result
    }

    foreach ($line in Get-Content -LiteralPath $Path -Encoding UTF8) {
        if ($null -eq $line) {
            continue
        }
        $trimmed = $line.Trim()
        if ($trimmed.Length -eq 0 -or $trimmed.StartsWith("#") -or $trimmed.StartsWith("//")) {
            continue
        }
        $index = $trimmed.IndexOf("=")
        if ($index -lt 0) {
            continue
        }
        $key = $trimmed.Substring(0, $index).Trim()
        $value = $trimmed.Substring($index + 1).Trim()
        if ($key.Length -gt 0) {
            $result[$key] = $value
        }
    }

    return $result
}

function Ensure-Directory {
    param([string]$Path)

    New-Item -ItemType Directory -Force -Path $Path | Out-Null
}

function Write-Utf8File {
    param(
        [string]$Path,
        [string[]]$Content
    )

    Ensure-Directory (Split-Path -Parent $Path)
    Set-Content -LiteralPath $Path -Value $Content -Encoding UTF8
}

function Quote-Cmd {
    param([string]$Value)

    return '"' + $Value.Replace('"', '""') + '"'
}

$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
if ([string]::IsNullOrWhiteSpace($StageRoot)) {
    $StageRoot = Join-Path $PSScriptRoot "staging"
}

$serverIni = Read-KeyValueFile (Join-Path $repoRoot "server.ini")
$loginIni = Read-KeyValueFile (Join-Path $repoRoot "loginServer\server.ini")

$gamePort = if ($serverIni.ContainsKey("sv.port")) { [int]$serverIni["sv.port"] } else { 19129 }
$loginPort = if ($loginIni.ContainsKey("port")) { [int]$loginIni["port"] } else { 8023 }
$localAdminPort = if ($serverIni.ContainsKey("sv.localAdminPort")) { [int]$serverIni["sv.localAdminPort"] } else { 18023 }
$gameJvmOpts = "-Xms${GameXmsMb}m -Xmx${GameXmxMb}m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
$loginJvmOpts = "-Xms256m -Xmx512m -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"

if ([string]::IsNullOrWhiteSpace($JavaHome)) {
    try {
        $javaBin = & (Join-Path $repoRoot "tools\java\resolve_java_bin.ps1") -RepoRoot $repoRoot -Role runtime
        $javaBin = $javaBin.TrimEnd("\")
    } catch {
        $javaBin = Join-Path ($env:JAVA_HOME) "bin"
    }
} else {
    $javaBin = Join-Path $JavaHome "bin"
}

$stageNginxConf = Join-Path $StageRoot "nginx\conf\nginx.conf"
$stagePhpCmd = Join-Path $StageRoot "services\kpah-web-php.cmd"
$stageNginxCmd = Join-Path $StageRoot "services\kpah-nginx.cmd"
$stageGameCmd = Join-Path $StageRoot "services\kpah-game.cmd"
$stageLoginCmd = Join-Path $StageRoot "services\kpah-login.cmd"
$stageSummary = Join-Path $StageRoot "docs\summary.txt"
$stagePorts = Join-Path $StageRoot "docs\ports.txt"
$stageGameWinSw = Join-Path $StageRoot "services\winsw\kpah-game.xml"
$stageLoginWinSw = Join-Path $StageRoot "services\winsw\kpah-login.xml"
$stageWebWinSw = Join-Path $StageRoot "services\winsw\kpah-web-php.xml"
$stageNginxWinSw = Join-Path $StageRoot "services\winsw\kpah-nginx.xml"

Ensure-Directory $StageRoot
Ensure-Directory (Join-Path $StageRoot "nginx\logs")
Ensure-Directory (Join-Path $StageRoot "php\logs")
Ensure-Directory (Join-Path $StageRoot "services\logs")
Ensure-Directory (Join-Path $StageRoot "docs")
Ensure-Directory (Join-Path $StageRoot "services\winsw")

$nginxLines = @(
    "worker_processes  1;",
    "",
    "events {",
    "    worker_connections  1024;",
    "}",
    "",
    "http {",
    "    include       mime.types;",
    "    default_type  application/octet-stream;",
    "    sendfile        on;",
    "    keepalive_timeout  15;",
    "    client_header_timeout 10;",
    "    client_body_timeout 10;",
    "    send_timeout 10;",
    "    client_max_body_size 2m;",
    "    server_tokens off;",
    "    access_log  logs/access.log;",
    "    error_log   logs/error.log warn;",
    "    limit_conn_zone `$binary_remote_addr zone=kpah_conn:10m;",
    "    limit_req_zone `$binary_remote_addr zone=kpah_req:10m rate=15r/s;",
    "",
    "    server {",
    ("        listen       " + $WebPort + ";"),
    "        server_name  localhost;",
    ("        root         " + (($repoRoot + "\web").Replace("\", "/")) + ";"),
    "        index        index.php index.html;",
    "",
    "        location / {",
    "            limit_conn kpah_conn 20;",
    "            limit_req zone=kpah_req burst=30 nodelay;",
    "            try_files `$uri `$uri/ /index.php?`$query_string;",
    "        }",
    "",
    "        location ~ \.php$ {",
    "            limit_conn kpah_conn 10;",
    "            limit_req zone=kpah_req burst=20 nodelay;",
    "            include       fastcgi_params;",
    "            fastcgi_param SCRIPT_FILENAME `$document_root`$fastcgi_script_name;",
    "            fastcgi_param PHP_VALUE ""session.cookie_httponly=1"";",
    ("            fastcgi_pass 127.0.0.1:" + $PhpFastCgiPort + ";"),
    "        }",
    "",
    "        location ~ /\. {",
    "            deny all;",
    "        }",
    "    }",
    "}"
)
Write-Utf8File -Path $stageNginxConf -Content $nginxLines

$gameCmdLines = @(
    "@echo off",
    "setlocal",
    ("cd /d " + (Quote-Cmd $repoRoot)),
    ((Quote-Cmd (Join-Path $javaBin "java.exe")) + " " + $gameJvmOpts + " -jar " + (Quote-Cmd (Join-Path $repoRoot "KPAH.jar")))
)
Write-Utf8File -Path $stageGameCmd -Content $gameCmdLines

$loginCmdLines = @(
    "@echo off",
    "setlocal",
    ("cd /d " + (Quote-Cmd (Join-Path $repoRoot "loginServer"))),
    ((Quote-Cmd (Join-Path $javaBin "java.exe")) + " " + $loginJvmOpts + " -jar " + (Quote-Cmd (Join-Path $repoRoot "loginServer\CheckLoginSocket.jar")))
)
Write-Utf8File -Path $stageLoginCmd -Content $loginCmdLines

$phpCmdLines = @(
    "@echo off",
    "setlocal",
    ("cd /d " + (Quote-Cmd $StageRoot)),
    ((Quote-Cmd (Join-Path $PhpRoot "php-cgi.exe")) + " -b 127.0.0.1:" + $PhpFastCgiPort + " -c " + (Quote-Cmd (Join-Path $PhpRoot "php.ini")))
)
Write-Utf8File -Path $stagePhpCmd -Content $phpCmdLines

$nginxCmdLines = @(
    "@echo off",
    "setlocal",
    ("cd /d " + (Quote-Cmd $NginxRoot)),
    ((Quote-Cmd (Join-Path $NginxRoot "nginx.exe")) + " -p " + (Quote-Cmd (Join-Path $StageRoot "nginx")) + " -c conf/nginx.conf")
)
Write-Utf8File -Path $stageNginxCmd -Content $nginxCmdLines

$gameXml = @(
    "<service>",
    "  <id>kpah-game</id>",
    "  <name>KPAH Game Server</name>",
    "  <description>KPAH Java game server</description>",
    ("  <executable>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\kpah-game.cmd")) + "</executable>"),
    ("  <logpath>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\logs")) + "</logpath>"),
    "  <log mode=""roll-by-size""></log>",
    "  <onfailure action=""restart"" delay=""10 sec"" />",
    "</service>"
)
Write-Utf8File -Path $stageGameWinSw -Content $gameXml

$loginXml = @(
    "<service>",
    "  <id>kpah-login</id>",
    "  <name>KPAH Login Server</name>",
    "  <description>KPAH Java login socket</description>",
    ("  <executable>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\kpah-login.cmd")) + "</executable>"),
    ("  <logpath>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\logs")) + "</logpath>"),
    "  <log mode=""roll-by-size""></log>",
    "  <onfailure action=""restart"" delay=""10 sec"" />",
    "</service>"
)
Write-Utf8File -Path $stageLoginWinSw -Content $loginXml

$webXml = @(
    "<service>",
    "  <id>kpah-web-php</id>",
    "  <name>KPAH Web PHP</name>",
    "  <description>KPAH PHP FastCGI worker</description>",
    ("  <executable>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\kpah-web-php.cmd")) + "</executable>"),
    ("  <logpath>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\logs")) + "</logpath>"),
    "  <log mode=""roll-by-size""></log>",
    "  <onfailure action=""restart"" delay=""10 sec"" />",
    "</service>"
)
Write-Utf8File -Path $stageWebWinSw -Content $webXml

$nginxXml = @(
    "<service>",
    "  <id>kpah-nginx</id>",
    "  <name>KPAH Nginx</name>",
    "  <description>KPAH web reverse proxy</description>",
    ("  <executable>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\kpah-nginx.cmd")) + "</executable>"),
    ("  <logpath>" + [System.Security.SecurityElement]::Escape((Join-Path $StageRoot "services\logs")) + "</logpath>"),
    "  <log mode=""roll-by-size""></log>",
    "  <onfailure action=""restart"" delay=""10 sec"" />",
    "</service>"
)
Write-Utf8File -Path $stageNginxWinSw -Content $nginxXml

$summaryLines = @(
    "KPAH Win10 production staging summary",
    ("Generated: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss")),
    ("Repo root: " + $repoRoot),
    ("Stage root: " + $StageRoot),
    "",
    "Current ports",
    ("- Game: " + $gamePort),
    ("- Login: " + $loginPort),
    ("- Local admin: " + $localAdminPort),
    ("- Web stage: " + $WebPort),
    ("- PHP FastCGI stage: " + $PhpFastCgiPort),
    "",
    "Generated files",
    "- nginx/conf/nginx.conf",
    "- services/kpah-game.cmd",
    "- services/kpah-login.cmd",
    "- services/kpah-web-php.cmd",
    "- services/kpah-nginx.cmd",
    "- services/winsw/*.xml",
    "- ..\apply-win10-hardening.ps1",
    "",
    "Safe usage",
    "- Chua dung de stop process dang chay",
    "- Co script hardening Win10 rieng, chi chay khi da chot danh sach cong public",
    "- Dung trong mot cua so cutover rieng khi den lich bao tri"
)
Write-Utf8File -Path $stageSummary -Content $summaryLines

$portsLines = @(
    "Recommended firewall plan",
    ("- Public allow: TCP " + $gamePort + " (game), TCP " + $WebPort + " or 80/443 via Nginx"),
    ("- Private only: TCP " + $loginPort + " (login socket)"),
    ("- Private only: TCP " + $localAdminPort + " (local admin)"),
    ("- Private only: TCP " + $PhpFastCgiPort),
    "- Private only: DB 3306"
)
Write-Utf8File -Path $stagePorts -Content $portsLines

Write-Output ("Stage rendered to: " + $StageRoot)
