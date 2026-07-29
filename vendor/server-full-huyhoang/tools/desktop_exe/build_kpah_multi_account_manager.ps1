$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$root = Split-Path -Parent $PSScriptRoot
$projectRoot = Split-Path -Parent $root
$workRoot = Join-Path $root "work_multi"
$classesDir = Join-Path $workRoot "classes"
$jarDir = Join-Path $workRoot "jar"
$inputDir = Join-Path $workRoot "input"
$freeJ2meStage = Join-Path $workRoot "freej2me-stage"
$outputDir = if ($env:KPAH_MULTI_OUTPUT_DIR) { $env:KPAH_MULTI_OUTPUT_DIR } else { Join-Path $projectRoot "build\\desktop_multi_manager" }
$launcherJar = Join-Path $jarDir "kpah-launcher.jar"
$managerJar = Join-Path $jarDir "kpah-manager.jar"
$appName = if ($env:KPAH_MULTI_APP_NAME) { $env:KPAH_MULTI_APP_NAME } else { "KPAH_Multi_Account_Manager" }
$freeJ2meZip = Join-Path $projectRoot "tools\\freej2me\\freej2me-v1.52.zip"
$freeJ2meExtract = Join-Path $projectRoot "tools\\freej2me\\v1.52"
$sourceDir = Join-Path $PSScriptRoot "src"
$clientCandidates = @(
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
$jdkBin = "C:\\Program Files\\Java\\jdk-23\\bin"
$javac = Join-Path $jdkBin "javac.exe"
$jar = Join-Path $jdkBin "jar.exe"
$jpackage = Join-Path $jdkBin "jpackage.exe"

if (!(Test-Path $javac)) { throw "Khong tim thay javac.exe tai $javac" }
if (!(Test-Path $jar)) { throw "Khong tim thay jar.exe tai $jar" }
if (!(Test-Path $jpackage)) { throw "Khong tim thay jpackage.exe tai $jpackage" }
if (!(Test-Path $sourceDir)) { throw "Khong tim thay thu muc source: $sourceDir" }
if (!(Test-Path $freeJ2meZip)) { throw "Khong tim thay freej2me zip: $freeJ2meZip" }
if (!$clientJar) { throw "Khong tim thay client jar trong cac duong dan du kien" }

if (!(Test-Path $freeJ2meExtract)) {
    Expand-Archive -Path $freeJ2meZip -DestinationPath $freeJ2meExtract -Force
}

$sourceFiles = Get-ChildItem -Path $sourceDir -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
if (!$sourceFiles -or $sourceFiles.Count -eq 0) {
    throw "Khong tim thay file java trong $sourceDir"
}

$freeJ2meJar = Join-Path $freeJ2meExtract "freej2me.jar"
if (!(Test-Path $freeJ2meJar)) { throw "Khong tim thay freej2me.jar trong $freeJ2meExtract" }

New-Item -ItemType Directory -Force $classesDir, $jarDir, $inputDir, $freeJ2meStage, $outputDir | Out-Null
Get-ChildItem -Path $classesDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $jarDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $inputDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $freeJ2meStage -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue

New-Item -ItemType Directory -Force $classesDir, $jarDir, $inputDir, $freeJ2meStage | Out-Null

& $javac -cp $freeJ2meJar -d $classesDir $sourceFiles
if ($LASTEXITCODE -ne 0) { throw "Compile java sources that bai" }

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

$patchedStageClasses = @(
    (Join-Path $freeJ2meStage "com\\nttdocomo\\io\\HttpConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\Connection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\ContentConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\HttpConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\InputConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\OutputConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\SocketConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\StreamConnection.class"),
    (Join-Path $freeJ2meStage "javax\\microedition\\io\\HttpConnectionImpl.class")
)
foreach ($patchedClass in $patchedStageClasses) {
    if (Test-Path $patchedClass) {
        Remove-Item $patchedClass -Force -ErrorAction SilentlyContinue
    }
}

& $jar --create --file $launcherJar --main-class KpahAutologinLauncher -C $freeJ2meStage . -C $classesDir .
if ($LASTEXITCODE -ne 0) { throw "Dong goi kpah-launcher.jar that bai" }

& $jar --create --file $managerJar --main-class KpahMultiAccountManager -C $classesDir .
if ($LASTEXITCODE -ne 0) { throw "Dong goi kpah-manager.jar that bai" }

Copy-Item $launcherJar (Join-Path $inputDir "kpah-launcher.jar") -Force
Copy-Item $managerJar (Join-Path $inputDir "kpah-manager.jar") -Force
Copy-Item $clientJar (Join-Path $inputDir ([System.IO.Path]::GetFileName($clientJar))) -Force

Remove-Item (Join-Path $outputDir $appName) -Recurse -Force -ErrorAction SilentlyContinue

& $jpackage `
    --type app-image `
    --dest $outputDir `
    --input $inputDir `
    --name $appName `
    --main-jar "kpah-manager.jar" `
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

Write-Host "Client  : $clientJar"
Write-Host "App dir : $appDir"
Write-Host "Zip     : $zipPath"
