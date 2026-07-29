$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$workRoot = Join-Path $PSScriptRoot "out"
$classesDir = Join-Path $workRoot "classes"
$jarPath = Join-Path $workRoot "kpah-license-admin.jar"
$inputDir = Join-Path $workRoot "input"
$appOutputDir = Join-Path $workRoot "app"
$appName = "KPAH_License_Admin"
$zipPath = Join-Path $appOutputDir ($appName + "_portable.zip")
$sourceFiles = @((Join-Path $PSScriptRoot "src\\KpahLicenseAdminTool.java"))
$readmePath = Join-Path $PSScriptRoot "README.txt"
$jdkBin = "C:\\Program Files\\Java\\jdk-23\\bin"
$javac = Join-Path $jdkBin "javac.exe"
$jar = Join-Path $jdkBin "jar.exe"
$jpackage = Join-Path $jdkBin "jpackage.exe"

if (!(Test-Path $javac)) { throw "Khong tim thay javac.exe tai $javac" }
if (!(Test-Path $jar)) { throw "Khong tim thay jar.exe tai $jar" }
if (!(Test-Path $jpackage)) { throw "Khong tim thay jpackage.exe tai $jpackage" }
foreach ($sourceFile in $sourceFiles) {
    if (!(Test-Path $sourceFile)) {
        throw "Khong tim thay source: $sourceFile"
    }
}
if (!(Test-Path $readmePath)) { throw "Khong tim thay README: $readmePath" }

New-Item -ItemType Directory -Force $workRoot, $classesDir, $inputDir, $appOutputDir | Out-Null
Get-ChildItem -Path $classesDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $inputDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue

& $javac -d $classesDir $sourceFiles
if ($LASTEXITCODE -ne 0) { throw "Compile KPAH license admin that bai" }

if (Test-Path $jarPath) {
    Remove-Item $jarPath -Force
}

& $jar --create --file $jarPath --main-class KpahLicenseAdminTool -C $classesDir .
if ($LASTEXITCODE -ne 0) { throw "Dong goi kpah-license-admin.jar that bai" }

Copy-Item $jarPath (Join-Path $inputDir "kpah-license-admin.jar") -Force
Copy-Item $readmePath (Join-Path $inputDir "README.txt") -Force

$appDir = Join-Path $appOutputDir $appName
if (Test-Path $appDir) {
    Remove-Item $appDir -Recurse -Force
}
if (Test-Path $zipPath) {
    Remove-Item $zipPath -Force
}

& $jpackage `
    --type app-image `
    --dest $appOutputDir `
    --input $inputDir `
    --name $appName `
    --main-jar "kpah-license-admin.jar" `
    --java-options "-Dfile.encoding=UTF-8"

if ($LASTEXITCODE -ne 0) { throw "Dong goi KPAH license admin app that bai" }

Compress-Archive -Path (Join-Path $appDir "*") -DestinationPath $zipPath

$exePath = Join-Path $appDir ($appName + ".exe")
Write-Host "Jar: $jarPath"
Write-Host "Exe: $exePath"
Write-Host "Zip: $zipPath"
