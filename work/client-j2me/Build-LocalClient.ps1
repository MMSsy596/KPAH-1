param(
    [string]$HostName = "127.0.0.1",
    [ValidateRange(1, 65535)]
    [int]$Port = 19129,
    [string]$OutputName = "grinding2-local.jar",
    [string]$ClientId = "grinding2-local",
    [string]$ServerListUrl = "",
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
if ([string]::IsNullOrWhiteSpace($ServerListUrl)) {
    $ServerListUrl = "http://${HostName}:18080/NQSH2.txt"
}
$parsedServerListUrl = $null
if (![Uri]::TryCreate($ServerListUrl, [UriKind]::Absolute, [ref]$parsedServerListUrl) -or
    $parsedServerListUrl.Scheme -notin @("http", "https")) {
    throw "ServerListUrl phai la dia chi HTTP/HTTPS hop le."
}

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$toolchainsRoot = Join-Path $repoRoot ".toolchains"
$programFilesJava = Join-Path ([Environment]::GetFolderPath("ProgramFiles")) "Java"
$jdkCandidates = @()
foreach ($jdkSearchRoot in @($toolchainsRoot, $programFilesJava)) {
    if (Test-Path -LiteralPath $jdkSearchRoot) {
        $jdkCandidates += Get-ChildItem -LiteralPath $jdkSearchRoot -Directory |
        Where-Object {
            (Test-Path (Join-Path $_.FullName "bin\javac.exe")) -and
            (Test-Path (Join-Path $_.FullName "bin\java.exe"))
        }
    }
}
$jdkRoot = $jdkCandidates | Sort-Object Name | Select-Object -First 1
if ($null -ne $jdkRoot) {
    $javac = Join-Path $jdkRoot.FullName "bin\javac.exe"
    $java = Join-Path $jdkRoot.FullName "bin\java.exe"
} else {
    $javac = (Get-Command javac.exe -ErrorAction Stop).Source
    $java = (Get-Command java.exe -ErrorAction Stop).Source
}

$patcherSource = Join-Path $repoRoot "work\server\tools\client_jar_auth\PatchClientJar.java"
$gameplaySource = Join-Path $repoRoot "work\server\tools\client_jar_auth\kpahgameplay.java"
$sourceJar = Join-Path $repoRoot "work\server\tools\client_jar_auth\sources\grinding2.jar"
$freej2meJar = Join-Path $repoRoot "work\server\tools\freej2me\v1.52\freej2me.jar"
$classesDir = Join-Path $PSScriptRoot "patcher-classes"
$gameplayClass = Join-Path $classesDir "kpahgameplay.class"
$outputJar = Join-Path $PSScriptRoot ($OutputDirectory + "\" + $OutputName)

New-Item -ItemType Directory -Force -Path $classesDir, (Split-Path -Parent $outputJar) | Out-Null

& $javac `
    -encoding UTF-8 `
    -source 1.8 `
    -target 1.8 `
    -classpath ($sourceJar + ";" + $freej2meJar) `
    -d $classesDir `
    $gameplaySource
if ($LASTEXITCODE -ne 0) {
    throw "Bien dich kpahgameplay.java that bai."
}

& $javac `
    --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED `
    -encoding UTF-8 `
    -d $classesDir `
    $patcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Bien dich PatchClientJar.java that bai."
}

& $java `
    --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED `
    -cp $classesDir `
    PatchClientJar `
    $sourceJar `
    $outputJar `
    $ClientId `
    $HostName `
    $ServerListUrl `
    "-" `
    $Port `
    $gameplayClass
if ($LASTEXITCODE -ne 0) {
    throw "Va client J2ME that bai."
}

$hash = Get-FileHash -Algorithm SHA256 -LiteralPath $outputJar
$hashLine = "{0} *{1}" -f $hash.Hash.ToLowerInvariant(), (Split-Path -Leaf $outputJar)
$hashLine | Set-Content -LiteralPath ($outputJar + ".sha256") -Encoding ASCII
Write-Host "J2ME client build OK: $outputJar"
Write-Host "Server: ${HostName}:$Port"
Write-Host "SHA256: $($hash.Hash)"
