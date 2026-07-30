$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$projectRoot = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$toolDir = $PSScriptRoot
$workDir = Join-Path $toolDir "work"
$clientRootItem = Get-ChildItem -Path $projectRoot -Directory -Recurse |
    Where-Object {
        $_.Name -eq "kpahpc" -and (Test-Path (Join-Path $_.FullName "KPAH_276.exe"))
    } |
    Select-Object -First 1
if ($null -eq $clientRootItem) {
    throw "Khong tim thay thu muc client kpahpc"
}
$clientRoot = $clientRootItem.FullName
$managedDir = Join-Path $clientRoot "KPAH_276_Data\\Managed"
$assemblyPath = Join-Path $managedDir "Assembly-CSharp.dll"
$helperSource = Join-Path $toolDir "ClientAuthPcLib.cs"
$patcherSource = Join-Path $toolDir "PatchPcClientAuth.cs"
$helperBuildOutput = Join-Path $workDir "ClientAuthPcLib.dll"
$helperOutput = Join-Path $managedDir "ClientAuthPcLib.dll"
$patcherOutput = Join-Path $workDir "PatchPcClientAuth.exe"
$serverBindingSource = Join-Path $toolDir "PatchPcServerBinding.cs"
$serverBindingOutput = Join-Path $workDir "PatchPcServerBinding.exe"
$measurementsFile = Join-Path $projectRoot "dist\\client_jar_locked\\measurements.txt"
$measurementOverridePath = Join-Path $clientRoot "client_auth_measurement_override.txt"

New-Item -ItemType Directory -Force $workDir | Out-Null

$csc = "C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\csc.exe"
if (!(Test-Path $csc)) {
    throw "Khong tim thay csc.exe"
}

$cecilDir = Join-Path $toolDir "mono.cecil.0.11.6\\lib\\net40"
$cecilDll = Join-Path $cecilDir "Mono.Cecil.dll"
$cecilRocksDll = Join-Path $cecilDir "Mono.Cecil.Rocks.dll"
if (!(Test-Path $cecilDll)) {
    throw "Khong tim thay Mono.Cecil.dll. Hay tai package truoc."
}

& $csc /nologo /target:library /out:$helperBuildOutput $helperSource
if ($LASTEXITCODE -ne 0) { throw "Build ClientAuthPcLib.dll that bai" }
Copy-Item $helperBuildOutput $helperOutput -Force

Copy-Item $cecilDll (Join-Path $workDir "Mono.Cecil.dll") -Force
Copy-Item $cecilRocksDll (Join-Path $workDir "Mono.Cecil.Rocks.dll") -Force

& $csc /nologo ("/r:" + (Join-Path $workDir "Mono.Cecil.dll")) ("/r:" + (Join-Path $workDir "Mono.Cecil.Rocks.dll")) ("/out:" + $patcherOutput) $patcherSource
if ($LASTEXITCODE -ne 0) { throw "Build PatchPcClientAuth.exe that bai" }

& $csc /nologo ("/r:" + (Join-Path $workDir "Mono.Cecil.dll")) ("/r:" + (Join-Path $workDir "Mono.Cecil.Rocks.dll")) ("/out:" + $serverBindingOutput) $serverBindingSource
if ($LASTEXITCODE -ne 0) { throw "Build PatchPcServerBinding.exe that bai" }

Push-Location $workDir
try {
    & $patcherOutput $assemblyPath $helperOutput $measurementsFile $clientRoot "kpahpc"
    if ($LASTEXITCODE -ne 0) { throw "Patch client pc that bai" }
    & $serverBindingOutput $assemblyPath
    if ($LASTEXITCODE -ne 0) { throw "Patch server binding pc that bai" }
    if (Test-Path $measurementOverridePath) {
        Remove-Item -LiteralPath $measurementOverridePath -Force
    }
} finally {
    Pop-Location
}
