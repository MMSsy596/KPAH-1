$ErrorActionPreference = "Stop"

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$clientJar = Join-Path $PSScriptRoot "dist\grinding2-local.jar"
$freeJ2meJar = Join-Path $PSScriptRoot "dist\freej2me-network.jar"
$webRoot = Join-Path $PSScriptRoot "web"
$runtimeDir = Join-Path $PSScriptRoot "runtime"
$java8 = Join-Path $repoRoot ".toolchains\jdk8\jdk8u492-b09\bin\java.exe"
$python = (Get-Command python.exe -ErrorAction Stop).Source

if (!(Test-Path -LiteralPath $clientJar)) {
    throw "Chua co client local. Chay Build-LocalClient.ps1 truoc."
}
if (!(Test-Path -LiteralPath $freeJ2meJar)) {
    throw "Chua co FreeJ2ME local. Chay Build-LocalEmulator.ps1 truoc."
}

New-Item -ItemType Directory -Force -Path $runtimeDir | Out-Null

$webListener = Get-NetTCPConnection -LocalAddress "127.0.0.1" -LocalPort 18080 -State Listen -ErrorAction SilentlyContinue
if ($null -eq $webListener) {
    Start-Process `
        -FilePath $python `
        -ArgumentList @("-m", "http.server", "18080", "--bind", "127.0.0.1", "--directory", $webRoot) `
        -WorkingDirectory $webRoot `
        -WindowStyle Hidden `
        -RedirectStandardOutput (Join-Path $runtimeDir "server-list-http.out.log") `
        -RedirectStandardError (Join-Path $runtimeDir "server-list-http.err.log")
}

Start-Process `
    -FilePath $java8 `
    -ArgumentList @("-Dfile.encoding=ISO_8859_1", "-jar", $freeJ2meJar, $clientJar) `
    -WorkingDirectory $runtimeDir `
    -RedirectStandardOutput (Join-Path $runtimeDir "freej2me.out.log") `
    -RedirectStandardError (Join-Path $runtimeDir "freej2me.err.log")
