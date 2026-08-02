$ErrorActionPreference = "Stop"

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$toolchainsRoot = Join-Path $repoRoot ".toolchains"
$programFilesJava = Join-Path ([Environment]::GetFolderPath("ProgramFiles")) "Java"
$jdkCandidates = @()
foreach ($jdkSearchRoot in @($toolchainsRoot, $programFilesJava)) {
    if (Test-Path -LiteralPath $jdkSearchRoot) {
        $jdkCandidates += Get-ChildItem -LiteralPath $jdkSearchRoot -Directory |
            Where-Object {
                (Test-Path (Join-Path $_.FullName "bin\javac.exe")) -and
                (Test-Path (Join-Path $_.FullName "bin\jar.exe"))
            }
    }
}
$jdkRoot = $jdkCandidates | Sort-Object Name | Select-Object -First 1
if ($null -ne $jdkRoot) {
    $javac = Join-Path $jdkRoot.FullName "bin\javac.exe"
    $jar = Join-Path $jdkRoot.FullName "bin\jar.exe"
} else {
    $javac = (Get-Command javac.exe -ErrorAction Stop).Source
    $jar = (Get-Command jar.exe -ErrorAction Stop).Source
}
$sourceJar = Join-Path $repoRoot "work\server\tools\freej2me\v1.52\freej2me.jar"
$outputJar = Join-Path $PSScriptRoot "dist\freej2me-network.jar"
$classesDir = Join-Path $PSScriptRoot "emulator-classes"
$sources = Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot "emulator-src") -Recurse -Filter "*.java"

New-Item -ItemType Directory -Force -Path $classesDir, (Split-Path -Parent $outputJar) | Out-Null
Copy-Item -LiteralPath $sourceJar -Destination $outputJar -Force

& $javac -encoding UTF-8 -source 1.8 -target 1.8 -classpath $sourceJar -d $classesDir $sources.FullName
if ($LASTEXITCODE -ne 0) {
    throw "Bien dich lop mang FreeJ2ME that bai."
}

Push-Location $classesDir
try {
    & $jar uf $outputJar `
        "javax/microedition/io/Connector.class" `
        "javax/microedition/io/SocketConnectionImpl.class" `
        "org/recompile/freej2me/FreeJ2ME`$3.class"
    if ($LASTEXITCODE -ne 0) {
        throw "Cap nhat FreeJ2ME JAR that bai."
    }
} finally {
    Pop-Location
}
