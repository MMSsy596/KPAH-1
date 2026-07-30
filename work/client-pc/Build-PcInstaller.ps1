param(
    [Parameter(Mandatory = $true)]
    [ValidateNotNullOrEmpty()]
    [string]$HostName,
    [ValidateRange(1, 65535)]
    [int]$Port = 19129,
    [string]$ServerName = "KPAH Online",
    [string]$AppVersion = "1.0.0",
    [string]$OutputName = "KPAH-PC-Setup"
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$clientRoot = $PSScriptRoot
$repoRoot = (Resolve-Path (Join-Path $clientRoot "..\..")).Path
$clientBuilder = Join-Path $clientRoot "Build-LocalPcClient.ps1"
$clientDir = Join-Path $clientRoot "dist\KPAH-PC"
$installerScript = Join-Path $clientRoot "installer\KPAH-PC.iss"
$releaseDir = Join-Path $clientRoot "release"
$isccCandidates = @(
    "C:\Program Files (x86)\Inno Setup 6\ISCC.exe",
    "C:\Program Files\Inno Setup 6\ISCC.exe"
)
$iscc = $isccCandidates | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1

if (!(Test-Path -LiteralPath $clientBuilder)) {
    throw "Khong tim thay client builder: $clientBuilder"
}
if (!(Test-Path -LiteralPath $installerScript)) {
    throw "Khong tim thay Inno Setup script: $installerScript"
}
if (!$iscc) {
    throw "Chua cai Inno Setup 6. Cai Inno Setup 6 roi chay lai."
}
if ($AppVersion -notmatch '^\d+\.\d+\.\d+(\.\d+)?$') {
    throw "AppVersion phai co dang 1.0.0 hoac 1.0.0.0."
}
if ($OutputName -notmatch '^[A-Za-z0-9._-]+$') {
    throw "OutputName chi duoc chua chu, so, dau cham, gach ngang hoac gach duoi."
}

& $clientBuilder `
    -HostName $HostName `
    -Port $Port `
    -ServerName $ServerName `
    -ServerListUrl "http://127.0.0.1:18080/NQSH2.txt"
if ($LASTEXITCODE -ne 0) {
    throw "Build client PC that bai."
}

New-Item -ItemType Directory -Force -Path $releaseDir | Out-Null

& $iscc `
    ("/DClientDir=" + $clientDir) `
    ("/DOutputDir=" + $releaseDir) `
    ("/DAppVersion=" + $AppVersion) `
    ("/DOutputName=" + $OutputName) `
    $installerScript
if ($LASTEXITCODE -ne 0) {
    throw "Build installer that bai."
}

$setupPath = Join-Path $releaseDir ($OutputName + ".exe")
if (!(Test-Path -LiteralPath $setupPath)) {
    throw "Inno Setup khong tao output mong doi: $setupPath"
}

$hash = Get-FileHash -Algorithm SHA256 -LiteralPath $setupPath
$hashLine = "{0} *{1}" -f $hash.Hash.ToLowerInvariant(), (Split-Path -Leaf $setupPath)
$hashLine | Set-Content -LiteralPath (Join-Path $releaseDir ($OutputName + ".sha256")) -Encoding ASCII

Write-Host "Installer build OK: $setupPath"
Write-Host "Server: ${HostName}:$Port"
Write-Host "SHA256: $($hash.Hash)"

