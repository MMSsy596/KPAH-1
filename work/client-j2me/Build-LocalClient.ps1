$ErrorActionPreference = "Stop"

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
$outputJar = Join-Path $PSScriptRoot "dist\grinding2-local.jar"

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
    "grinding2-local" `
    "127.0.0.1" `
    "http://127.0.0.1:18080/NQSH2.txt"
if ($LASTEXITCODE -ne 0) {
    throw "Va client J2ME that bai."
}
