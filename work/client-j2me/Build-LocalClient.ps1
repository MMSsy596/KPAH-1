param(
    [string]$HostName = "127.0.0.1",
    [ValidateRange(1, 65535)]
    [int]$Port = 19129,
    [string]$OutputName = "grinding2-local.jar",
    [string]$ClientId = "grinding2-local",
    [ValidateSet("dist", "release")]
    [string]$OutputDirectory = "dist"
)

$ErrorActionPreference = "Stop"

if ($HostName -notmatch '^[A-Za-z0-9.-]+$') {
    throw "HostName khong hop le."
}
if ($OutputName -notmatch '^[A-Za-z0-9._-]+\.jar$') {
    throw "OutputName phai la ten file .jar an toan."
}
if ([string]::IsNullOrWhiteSpace($ClientId)) {
    throw "ClientId khong duoc de trong."
}

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$jdkRoot = Get-ChildItem -LiteralPath (Join-Path $repoRoot ".toolchains\jdk17") -Directory |
    Where-Object { Test-Path (Join-Path $_.FullName "bin\javac.exe") } |
    Select-Object -First 1

if ($null -eq $jdkRoot) {
    throw "Khong tim thay JDK 17 portable trong .toolchains\jdk17."
}

$patcherSource = Join-Path $repoRoot "work\server\tools\client_jar_auth\PatchClientJar.java"
$sourceJar = Join-Path $repoRoot "work\server\tools\client_jar_auth\sources\grinding2.jar"
$classesDir = Join-Path $PSScriptRoot "patcher-classes"
$outputJar = Join-Path $PSScriptRoot ($OutputDirectory + "\" + $OutputName)

New-Item -ItemType Directory -Force -Path $classesDir, (Split-Path -Parent $outputJar) | Out-Null

& (Join-Path $jdkRoot.FullName "bin\javac.exe") `
    --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED `
    -encoding UTF-8 `
    -d $classesDir `
    $patcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Bien dich PatchClientJar.java that bai."
}

& (Join-Path $jdkRoot.FullName "bin\java.exe") `
    --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED `
    -cp $classesDir `
    PatchClientJar `
    $sourceJar `
    $outputJar `
    $ClientId `
    $HostName `
    "http://127.0.0.1:18080/NQSH2.txt" `
    "-" `
    $Port
if ($LASTEXITCODE -ne 0) {
    throw "Va client J2ME that bai."
}

$hash = Get-FileHash -Algorithm SHA256 -LiteralPath $outputJar
$hashLine = "{0} *{1}" -f $hash.Hash.ToLowerInvariant(), (Split-Path -Leaf $outputJar)
$hashLine | Set-Content -LiteralPath ($outputJar + ".sha256") -Encoding ASCII
Write-Host "J2ME client build OK: $outputJar"
Write-Host "Server: ${HostName}:$Port"
Write-Host "SHA256: $($hash.Hash)"
