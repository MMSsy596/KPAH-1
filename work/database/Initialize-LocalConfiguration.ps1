param(
    [string]$RepoRoot = ""
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    $RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
} else {
    $RepoRoot = (Resolve-Path -LiteralPath $RepoRoot).Path
}

$localConfigDir = Join-Path $RepoRoot "local-config"
$secretsPath = Join-Path $localConfigDir "secrets.local.json"
$serverTemplatePath = Join-Path $RepoRoot "work\config\server.ini.template"
$loginTemplatePath = Join-Path $RepoRoot "work\config\login-server.ini.template"
$serverConfigPath = Join-Path $RepoRoot "work\server\server.ini"
$loginConfigPath = Join-Path $RepoRoot "work\server\loginServer\server.ini"
$rootClientPath = Join-Path $localConfigDir "root-client.local.ini"
$appClientPath = Join-Path $localConfigDir "app-client.local.ini"
$bootstrapPath = Join-Path $localConfigDir "bootstrap-users.local.sql"
$secureRootPath = Join-Path $localConfigDir "secure-root.local.sql"

New-Item -ItemType Directory -Force -Path $localConfigDir | Out-Null
New-Item -ItemType Directory -Force -Path `
    (Join-Path $RepoRoot "work\server\logs\runtime"), `
    (Join-Path $RepoRoot "work\server\logs\vantieu"), `
    (Join-Path $RepoRoot "runtime-local\logs") | Out-Null

function New-HexSecret {
    param([int]$ByteCount)

    $bytes = New-Object byte[] $ByteCount
    $rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
    try {
        $rng.GetBytes($bytes)
    } finally {
        $rng.Dispose()
    }
    return (-join ($bytes | ForEach-Object { $_.ToString("x2") }))
}

function Write-Utf8NoBom {
    param(
        [string]$Path,
        [string]$Content
    )

    $encoding = New-Object System.Text.UTF8Encoding($false)
    [System.IO.File]::WriteAllText($Path, $Content, $encoding)
}

if (Test-Path -LiteralPath $secretsPath) {
    $secrets = Get-Content -Raw -LiteralPath $secretsPath | ConvertFrom-Json
} else {
    $secrets = [PSCustomObject]@{
        databasePassword = New-HexSecret -ByteCount 24
        rootPassword = New-HexSecret -ByteCount 24
        localAdminToken = New-HexSecret -ByteCount 32
        clientAuthSecret = New-HexSecret -ByteCount 32
        adminSessionSecret = New-HexSecret -ByteCount 32
    }
    Write-Utf8NoBom -Path $secretsPath -Content ($secrets | ConvertTo-Json)
}

$serverConfig = Get-Content -Raw -LiteralPath $serverTemplatePath
$serverConfig = $serverConfig -replace "db\.password=CHANGE_ME_LOCAL_ONLY", ("db.password=" + $secrets.databasePassword)
$serverConfig = $serverConfig -replace "db\.pass=CHANGE_ME_LOCAL_ONLY", ("db.pass=" + $secrets.databasePassword)
$serverConfig = $serverConfig -replace "db\.password1=CHANGE_ME_LOCAL_ONLY", ("db.password1=" + $secrets.databasePassword)
$serverConfig = $serverConfig -replace "sv\.localAdminToken=CHANGE_ME_LOCAL_ONLY", ("sv.localAdminToken=" + $secrets.localAdminToken)
$serverConfig = $serverConfig -replace "sv\.clientAuthSecret=CHANGE_ME_LOCAL_ONLY", ("sv.clientAuthSecret=" + $secrets.clientAuthSecret)
Write-Utf8NoBom -Path $serverConfigPath -Content $serverConfig

$loginConfig = Get-Content -Raw -LiteralPath $loginTemplatePath
$loginConfig = $loginConfig -replace "db\.password=CHANGE_ME_LOCAL_ONLY", ("db.password=" + $secrets.databasePassword)
Write-Utf8NoBom -Path $loginConfigPath -Content $loginConfig

$rootClient = @"
[client]
protocol=tcp
host=127.0.0.1
port=3306
user=root
password=$($secrets.rootPassword)
default-character-set=utf8mb4
"@
Write-Utf8NoBom -Path $rootClientPath -Content $rootClient

$appClient = @"
[client]
protocol=tcp
host=127.0.0.1
port=3306
user=kpah_local
password=$($secrets.databasePassword)
default-character-set=utf8mb4
"@
Write-Utf8NoBom -Path $appClientPath -Content $appClient

$bootstrapSql = @"
CREATE DATABASE IF NOT EXISTS account CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS kpah2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'kpah_local'@'127.0.0.1' IDENTIFIED BY '$($secrets.databasePassword)';
CREATE USER IF NOT EXISTS 'kpah_local'@'localhost' IDENTIFIED BY '$($secrets.databasePassword)';
ALTER USER 'kpah_local'@'127.0.0.1' IDENTIFIED BY '$($secrets.databasePassword)';
ALTER USER 'kpah_local'@'localhost' IDENTIFIED BY '$($secrets.databasePassword)';
GRANT ALL PRIVILEGES ON account.* TO 'kpah_local'@'127.0.0.1';
GRANT ALL PRIVILEGES ON kpah2.* TO 'kpah_local'@'127.0.0.1';
GRANT ALL PRIVILEGES ON account.* TO 'kpah_local'@'localhost';
GRANT ALL PRIVILEGES ON kpah2.* TO 'kpah_local'@'localhost';
FLUSH PRIVILEGES;
"@
Write-Utf8NoBom -Path $bootstrapPath -Content $bootstrapSql

$secureRootSql = @"
ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY '$($secrets.rootPassword)';
ALTER USER 'root'@'localhost' IDENTIFIED BY '$($secrets.rootPassword)';
ALTER USER 'root'@'::1' IDENTIFIED BY '$($secrets.rootPassword)';
FLUSH PRIVILEGES;
"@
Write-Utf8NoBom -Path $secureRootPath -Content $secureRootSql

$env:KPAH_ADMIN_SESSION_SECRET = $secrets.adminSessionSecret
Write-Output "Generated ignored local configuration and credential files."
