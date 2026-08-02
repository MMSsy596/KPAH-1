param(
    [string]$HostName = "127.0.0.1",
    [int]$Port = 19129,
    [string]$ServerName = "KPAH Local",
    [string]$ServerListUrl = "http://127.0.0.1:18080/NQSH2.txt",
    [string]$AppVersion = "1.0.7",
    [string]$UpdateManifestUrl = "http://127.0.0.1:18081/downloads/manifest.json",
    [string]$OutputName = "KPAH-PC"
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$sourceRoot = Join-Path $repoRoot "work\server\client chu_n\PC"
$outputRoot = Join-Path $PSScriptRoot ("dist\" + $OutputName)
$managedDir = Join-Path $outputRoot "KPAH_276_Data\Managed"
$assemblyPath = Join-Path $managedDir "Assembly-CSharp.dll"
$toolRoot = Join-Path $repoRoot "work\server\tools\pc_client_auth"
$workRoot = Join-Path $toolRoot "work"
$patcherSource = Join-Path $toolRoot "PatchPcServerBinding.cs"
$patcherExe = Join-Path $workRoot "PatchPcServerBinding.exe"
$gameplayHelperSource = Join-Path $toolRoot "PcGameplayEnhancements.cs"
$gameplayHelperDll = Join-Path $managedDir "KpahPcGameplay.dll"
$cecilDll = Join-Path $toolRoot "mono.cecil.0.11.6\lib\net40\Mono.Cecil.dll"
$cecilRocksDll = Join-Path $toolRoot "mono.cecil.0.11.6\lib\net40\Mono.Cecil.Rocks.dll"
$csc = "C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
$launcherSource = Join-Path $PSScriptRoot "launcher\KpahLauncher.cs"
$applyUpdateSource = Join-Path $PSScriptRoot "launcher\Apply-KPAH-Update.ps1"
$launcherExe = Join-Path $outputRoot "KPAH-Launcher.exe"

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

& $csc /nologo /target:library ("/r:" + $assemblyPath) `
    ("/r:" + (Join-Path $managedDir "UnityEngine.dll")) `
    ("/out:" + $gameplayHelperDll) $gameplayHelperSource
if ($LASTEXITCODE -ne 0) {
    throw "Build KpahPcGameplay.dll that bai."
}
if ($AppVersion -notmatch '^\d+\.\d+\.\d+(\.\d+)?$') {
    throw "AppVersion khong hop le."
}

& $csc /nologo ("/r:" + (Join-Path $workRoot "Mono.Cecil.dll")) `
    ("/r:" + (Join-Path $workRoot "Mono.Cecil.Rocks.dll")) `
    ("/out:" + $patcherExe) $patcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Build PatchPcServerBinding.exe that bai."
}

& $patcherExe $assemblyPath $HostName $Port $ServerName $ServerListUrl $gameplayHelperDll
if ($LASTEXITCODE -ne 0) {
    throw "Patch Assembly-CSharp.dll that bai."
}

# Không phát hành log máy build và bản DLL sao lưu vì client không cần các file này để chạy.
foreach ($temporaryArtifact in @(
    (Join-Path $outputRoot 'KPAH_276_Data\output_log.txt'),
    (Join-Path $managedDir 'Assembly-CSharp.backup.dll')
)) {
    if (Test-Path -LiteralPath $temporaryArtifact) {
        Remove-Item -LiteralPath $temporaryArtifact -Force
    }
}

& $csc /nologo /target:winexe /utf8output `
    /r:System.Windows.Forms.dll /r:System.Web.Extensions.dll `
    ("/out:" + $launcherExe) $launcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Build KPAH-Launcher.exe that bai."
}
$applyUpdateDestination = Join-Path $outputRoot "Apply-KPAH-Update.ps1"
# Windows PowerShell 5 cần BOM để đọc đúng các thông báo tiếng Việt trong script cập nhật.
$utf8Bom = New-Object System.Text.UTF8Encoding($true)
[System.IO.File]::WriteAllText(
    $applyUpdateDestination,
    [System.IO.File]::ReadAllText($applyUpdateSource),
    $utf8Bom
)
@{
    channel = 'pc'
    version = $AppVersion
    manifestUrl = $UpdateManifestUrl
    gameFile = 'KPAH_276.exe'
} | ConvertTo-Json | Set-Content -LiteralPath (Join-Path $outputRoot "client-update.json") -Encoding UTF8

$hashes = @(
    Get-FileHash -Algorithm SHA256 -LiteralPath (Join-Path $outputRoot "KPAH_276.exe")
    Get-FileHash -Algorithm SHA256 -LiteralPath $assemblyPath
    Get-FileHash -Algorithm SHA256 -LiteralPath $gameplayHelperDll
    Get-FileHash -Algorithm SHA256 -LiteralPath $launcherExe
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

Run KPAH-Launcher.exe to play and automatically check for updates.
Keyboard: top-row digits and NumPad 0-9 are supported (Num Lock must be on).
Inventory: press Enter to open the selected item's action menu; press Enter again on "Xem chi tiet" to view its description and attributes.
Consumables: choose "Dung tat ca" to use repeatedly with the item's cooldown; HP/MP stops when full.
Player support: press U in game to open Auto map, NPC directory, remote repair, and manual save.
Auto train: opening inventory, NPC menus, or utility menus no longer pauses attacks.
Remember login: tick "Nho mat khau" before logging in; the account is kept in the current Windows profile.
Do not copy server.ini, database files, or admin credentials into this folder.
"@ | Set-Content -LiteralPath (Join-Path $outputRoot "README.txt") -Encoding UTF8

Write-Host "PC client build OK: $outputRoot"
