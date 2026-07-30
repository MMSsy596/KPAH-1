param(
    [string]$HostName = "127.0.0.1",
    [int]$Port = 19129,
    [string]$ServerName = "KPAH Local",
    [string]$ServerListUrl = "http://127.0.0.1:18080/NQSH2.txt"
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$sourceRoot = Join-Path $repoRoot "work\server\client chu_n\PC"
$outputRoot = Join-Path $PSScriptRoot "dist\KPAH-PC"
$managedDir = Join-Path $outputRoot "KPAH_276_Data\Managed"
$assemblyPath = Join-Path $managedDir "Assembly-CSharp.dll"
$toolRoot = Join-Path $repoRoot "work\server\tools\pc_client_auth"
$workRoot = Join-Path $toolRoot "work"
$patcherSource = Join-Path $toolRoot "PatchPcServerBinding.cs"
$patcherExe = Join-Path $workRoot "PatchPcServerBinding.exe"
$cecilDll = Join-Path $toolRoot "mono.cecil.0.11.6\lib\net40\Mono.Cecil.dll"
$cecilRocksDll = Join-Path $toolRoot "mono.cecil.0.11.6\lib\net40\Mono.Cecil.Rocks.dll"
$csc = "C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe"

if (!(Test-Path -LiteralPath (Join-Path $sourceRoot "KPAH_276.exe"))) {
    throw "Khong tim thay client Unity goc: $sourceRoot"
}
if (Test-Path -LiteralPath $outputRoot) {
    $resolvedOutput = [System.IO.Path]::GetFullPath($outputRoot)
    $resolvedClientWork = [System.IO.Path]::GetFullPath($PSScriptRoot)
    if (!$resolvedOutput.StartsWith($resolvedClientWork + [System.IO.Path]::DirectorySeparatorChar, [System.StringComparison]::OrdinalIgnoreCase)) {
        throw "Tu choi xoa output nam ngoai work/client-pc: $resolvedOutput"
    }
    Remove-Item -LiteralPath $outputRoot -Recurse -Force
}
if ($Port -lt 1 -or $Port -gt 65535) {
    throw "Port phai nam trong khoang 1-65535."
}
if (!(Test-Path -LiteralPath $csc)) {
    throw "Khong tim thay C# compiler: $csc"
}
if (!(Test-Path -LiteralPath $cecilDll)) {
    throw "Khong tim thay Mono.Cecil.dll."
}

New-Item -ItemType Directory -Force -Path $outputRoot, $workRoot | Out-Null
Copy-Item -LiteralPath (Join-Path $sourceRoot "KPAH_276.exe") -Destination $outputRoot -Force
Copy-Item -LiteralPath (Join-Path $sourceRoot "KPAH_276_Data") -Destination $outputRoot -Recurse -Force
Copy-Item -LiteralPath $cecilDll -Destination $workRoot -Force
Copy-Item -LiteralPath $cecilRocksDll -Destination $workRoot -Force

& $csc /nologo ("/r:" + (Join-Path $workRoot "Mono.Cecil.dll")) `
    ("/r:" + (Join-Path $workRoot "Mono.Cecil.Rocks.dll")) `
    ("/out:" + $patcherExe) $patcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Build PatchPcServerBinding.exe that bai."
}

& $patcherExe $assemblyPath $HostName $Port $ServerName $ServerListUrl
if ($LASTEXITCODE -ne 0) {
    throw "Patch Assembly-CSharp.dll that bai."
}

$hashes = @(
    Get-FileHash -Algorithm SHA256 -LiteralPath (Join-Path $outputRoot "KPAH_276.exe")
    Get-FileHash -Algorithm SHA256 -LiteralPath $assemblyPath
)
$hashLines = $hashes | ForEach-Object {
    "{0} *{1}" -f $_.Hash.ToLowerInvariant(), $_.Path.Substring($outputRoot.Length + 1).Replace("\", "/")
}
$hashLines | Set-Content -LiteralPath (Join-Path $outputRoot "SHA256SUMS.txt") -Encoding ASCII

@"
KPAH PC client

Server: $ServerName
Host: $HostName
Port: $Port

Run KPAH_276.exe to play.
Do not copy server.ini, database files, or admin credentials into this folder.
"@ | Set-Content -LiteralPath (Join-Path $outputRoot "README.txt") -Encoding UTF8

Write-Host "PC client build OK: $outputRoot"
