$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$root = Split-Path -Parent $PSScriptRoot
$projectRoot = Split-Path -Parent $root
$workRoot = Join-Path $root "work"
$classesDir = Join-Path $workRoot "classes"
$jarDir = Join-Path $workRoot "jar"
$inputDir = Join-Path $workRoot "input"
$freeJ2meStage = Join-Path $workRoot "freej2me-stage"
$outputDir = Join-Path $projectRoot "..\\client-j2me\\dist\\auto-harness"
$launcherJar = Join-Path $jarDir "kpah-launcher.jar"
$appName = "KPAH_Auto_Tool"
$freeJ2meZip = Join-Path $projectRoot "tools\\freej2me\\freej2me-v1.52.zip"
$freeJ2meExtract = Join-Path $projectRoot "tools\\freej2me\\v1.52"
$networkFreeJ2meJar = Join-Path $projectRoot "..\\client-j2me\\dist\\freej2me-network.jar"
$sourceDir = Join-Path $PSScriptRoot "src"
$sourceFiles = @(
    (Join-Path $sourceDir "KpahAutologinLauncher.java"),
    (Join-Path $sourceDir "KpahLicenseCodec.java"),
    (Join-Path $sourceDir "KpahLicenseSupport.java")
)
$configTemplate = Join-Path $PSScriptRoot "kpah-auto.properties"
$clientCandidates = @(
    (Join-Path $projectRoot "..\\client-j2me\\dist\\grinding2-local.jar"),
    (Join-Path $projectRoot "dist\\client_jar_locked\\grinding2.jar"),
    (Join-Path $projectRoot "dist\\client_jar_locked\\vanphong18x5.jar"),
    (Join-Path $projectRoot "client chuẩn\\grinding2.jar"),
    (Join-Path $projectRoot "grinding2.jar"),
    (Join-Path $projectRoot "client2\\KPAH2.ME_autologin (1).jar"),
    (Join-Path $projectRoot "AngelChipEmulatorEXE\\kpah_local_19129.jar"),
    (Join-Path $projectRoot "kpah_local_19129.jar"),
    (Join-Path $projectRoot "client.jar")
)
$clientJar = $clientCandidates | Where-Object { $_ -and (Test-Path $_) } | Select-Object -First 1
$portableJpackage = Get-ChildItem `
    -LiteralPath (Join-Path $projectRoot "..\\..\\.toolchains\\jdk17") `
    -Recurse `
    -Filter "jpackage.exe" `
    -File `
    -ErrorAction SilentlyContinue |
    Select-Object -First 1
$jdkCandidates = @(
    $(if ($portableJpackage) { $portableJpackage.DirectoryName }),
    "C:\\Program Files\\Java\\jdk-23\\bin",
    "C:\\Program Files\\Java\\jdk-21\\bin",
    "C:\\Program Files\\Java\\jdk-17\\bin"
)
$jdkBin = $jdkCandidates |
    Where-Object { Test-Path (Join-Path $_ "jpackage.exe") } |
    Select-Object -First 1
if (!$jdkBin) { throw "Khong tim thay JDK 17+ co jpackage.exe" }
$javac = Join-Path $jdkBin "javac.exe"
$jar = Join-Path $jdkBin "jar.exe"
$jpackage = Join-Path $jdkBin "jpackage.exe"

if (!(Test-Path $javac)) { throw "Khong tim thay javac.exe tai $javac" }
if (!(Test-Path $jar)) { throw "Khong tim thay jar.exe tai $jar" }
if (!(Test-Path $jpackage)) { throw "Khong tim thay jpackage.exe tai $jpackage" }
foreach ($sourceFile in $sourceFiles) {
    if (!(Test-Path $sourceFile)) {
        throw "Khong tim thay source launcher: $sourceFile"
    }
}
if (!(Test-Path $configTemplate)) { throw "Khong tim thay file config template" }
if (!$clientJar) { throw "Khong tim thay client jar trong cac duong dan du kien" }
if (!(Test-Path $networkFreeJ2meJar) -and !(Test-Path $freeJ2meZip)) {
    throw "Khong tim thay freej2me network jar hoac archive goc."
}

New-Item -ItemType Directory -Force $classesDir, $jarDir, $inputDir, $freeJ2meStage, $outputDir | Out-Null
Get-ChildItem -Path $classesDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $jarDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $inputDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $freeJ2meStage -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue

if (!(Test-Path $networkFreeJ2meJar) -and !(Test-Path $freeJ2meExtract)) {
    Expand-Archive -Path $freeJ2meZip -DestinationPath $freeJ2meExtract -Force
}

New-Item -ItemType Directory -Force $classesDir, $jarDir, $inputDir, $freeJ2meStage | Out-Null

& $javac -d $classesDir $sourceFiles
if ($LASTEXITCODE -ne 0) { throw "Compile launcher that bai" }

$freeJ2meJar = if (Test-Path $networkFreeJ2meJar) {
    $networkFreeJ2meJar
} else {
    Join-Path $freeJ2meExtract "freej2me.jar"
}
if (!(Test-Path $freeJ2meJar)) { throw "Khong tim thay freej2me.jar trong $freeJ2meExtract" }

Push-Location $freeJ2meStage
try {
    & $jar xf $freeJ2meJar
    if ($LASTEXITCODE -ne 0) { throw "Giai nen freej2me.jar that bai" }
} finally {
    Pop-Location
}

$metaInf = Join-Path $freeJ2meStage "META-INF"
if (Test-Path $metaInf) {
    Get-ChildItem -Path $metaInf -File -ErrorAction SilentlyContinue |
        Where-Object { $_.Name -match '^(MANIFEST\.MF|.*\.(SF|RSA|DSA))$' } |
        Remove-Item -Force -ErrorAction SilentlyContinue
}

& $jar --create --file $launcherJar --main-class KpahAutologinLauncher -C $freeJ2meStage . -C $classesDir .
if ($LASTEXITCODE -ne 0) { throw "Dong goi launcher jar that bai" }

Copy-Item $launcherJar (Join-Path $inputDir "kpah-launcher.jar") -Force
Copy-Item $clientJar (Join-Path $inputDir ([System.IO.Path]::GetFileName($clientJar))) -Force
Copy-Item $configTemplate (Join-Path $inputDir "kpah-auto.properties") -Force

Remove-Item (Join-Path $outputDir $appName) -Recurse -Force -ErrorAction SilentlyContinue

& $jpackage `
    --type app-image `
    --dest $outputDir `
    --input $inputDir `
    --name $appName `
    --main-jar "kpah-launcher.jar" `
    --java-options "-Dsun.java2d.uiScale=1.0" `
    --java-options "-Dsun.java2d.noddraw=true" `
    --java-options "-Dsun.java2d.d3d=false" `
    --java-options "-Dsun.java2d.opengl=false" `
    --java-options "-Dfile.encoding=ISO_8859_1"

if ($LASTEXITCODE -ne 0) { throw "Dong goi app-image that bai" }

$appDir = Join-Path $outputDir $appName
$zipPath = Join-Path $outputDir ($appName + "_portable.zip")

if (Test-Path $zipPath) {
    Remove-Item $zipPath -Force
}

Compress-Archive -Path (Join-Path $appDir "*") -DestinationPath $zipPath

Write-Host "App dir : $appDir"
Write-Host "Zip     : $zipPath"
