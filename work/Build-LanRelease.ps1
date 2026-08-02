param(
    [string]$Version = '1.0.7',
    [string]$MinimumVersion = '1.0.7',
    [string]$HostName = '192.168.110.152',
    [int]$GamePort = 19129,
    [int]$WebPort = 18081,
    [int]$ServerListPort = 18080,
    [string]$ServerName = 'KPAH Gia Dinh'
)

$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'
if ($Version -notmatch '^\d+\.\d+\.\d+(\.\d+)?$') {
    throw 'Version phải có dạng 1.0.7.'
}

$root = $PSScriptRoot
$pcRoot = Join-Path $root 'client-pc'
$javaRoot = Join-Path $root 'client-j2me'
$pcRelease = Join-Path $pcRoot 'release'
$javaRelease = Join-Path $javaRoot 'release'
$webDownloads = Join-Path $root 'server\web\web\public\downloads'
$manifestUrl = "http://${HostName}:${WebPort}/downloads/manifest.json"
$serverListUrl = "http://${HostName}:${ServerListPort}/NQSH2.txt"

& (Join-Path $pcRoot 'Build-LocalPcClient.ps1') `
    -HostName $HostName -Port $GamePort -ServerName $ServerName `
    -ServerListUrl $serverListUrl -AppVersion $Version -UpdateManifestUrl $manifestUrl
if ($LASTEXITCODE -ne 0) { throw 'Build client PC thất bại.' }

$javaVersionedName = "KPAH-Java-LAN-v${Version}.jar"
& (Join-Path $javaRoot 'Build-LocalClient.ps1') `
    -HostName $HostName -Port $GamePort -ClientId "kpah-java-lan-v${Version}" `
    -ServerListUrl $serverListUrl -OutputName $javaVersionedName -OutputDirectory 'dist'
if ($LASTEXITCODE -ne 0) { throw 'Build client Java thất bại.' }
& (Join-Path $javaRoot 'Build-LocalEmulator.ps1')
if ($LASTEXITCODE -ne 0) { throw 'Build FreeJ2ME thất bại.' }

New-Item -ItemType Directory -Force -Path $pcRelease, $javaRelease, $webDownloads | Out-Null
$pcZip = Join-Path $pcRelease "KPAH-PC-LAN-v${Version}.zip"
$javaZip = Join-Path $javaRelease "KPAH-Java-LAN-v${Version}.zip"
$javaVersionedJar = Join-Path $javaRelease $javaVersionedName
$javaStaging = Join-Path $javaRoot "dist\release-v${Version}"
if (Test-Path -LiteralPath $javaStaging) {
    $resolvedStaging = [System.IO.Path]::GetFullPath($javaStaging)
    $resolvedJavaRoot = [System.IO.Path]::GetFullPath($javaRoot)
    if (!$resolvedStaging.StartsWith($resolvedJavaRoot + [System.IO.Path]::DirectorySeparatorChar, [System.StringComparison]::OrdinalIgnoreCase)) {
        throw 'Từ chối xóa staging nằm ngoài client Java.'
    }
    Remove-Item -LiteralPath $javaStaging -Recurse -Force
}
New-Item -ItemType Directory -Path $javaStaging | Out-Null

Copy-Item -LiteralPath (Join-Path $javaRoot "dist\$javaVersionedName") -Destination $javaVersionedJar -Force
Copy-Item -LiteralPath $javaVersionedJar -Destination (Join-Path $javaStaging 'KPAH-Java.jar') -Force
Copy-Item -LiteralPath (Join-Path $javaRoot 'dist\freej2me-network.jar') -Destination $javaStaging -Force
Copy-Item -LiteralPath (Join-Path $javaRoot 'Start-KPAH-Java.cmd') -Destination $javaStaging -Force
$javaUpdaterDestination = Join-Path $javaStaging 'Update-KPAH-Java.ps1'
# Windows PowerShell 5 cần BOM để đọc đúng các thông báo tiếng Việt trong script cập nhật.
$utf8Bom = New-Object System.Text.UTF8Encoding($true)
[System.IO.File]::WriteAllText(
    $javaUpdaterDestination,
    [System.IO.File]::ReadAllText((Join-Path $javaRoot 'Update-KPAH-Java.ps1')),
    $utf8Bom
)
foreach ($directory in @('config', 'freej2me_system', 'rms')) {
    $source = Join-Path $javaRelease $directory
    if (Test-Path -LiteralPath $source) {
        Copy-Item -LiteralPath $source -Destination $javaStaging -Recurse -Force
    }
}
@{
    channel = 'java'
    version = $Version
    manifestUrl = $manifestUrl
} | ConvertTo-Json | Set-Content -LiteralPath (Join-Path $javaStaging 'client-update.json') -Encoding UTF8
@"
KPAH Java LAN v$Version

Chạy Start-KPAH-Java.cmd. Trình khởi động sẽ chủ động kiểm tra phiên bản mới trước khi mở game.
Server: ${HostName}:${GamePort}
Trang tải: http://${HostName}:${WebPort}/tai-game

- Auto train tiếp tục đánh khi mở hành trang, menu NPC và menu tiện ích.
- Enter mở menu vật phẩm, Enter lần nữa xem chi tiết hoặc đóng thông báo.
- Có Dùng tất cả và menu hỗ trợ người chơi bằng phím U.
"@ | Set-Content -LiteralPath (Join-Path $javaStaging 'README.txt') -Encoding UTF8

foreach ($archive in @($pcZip, $javaZip)) {
    if (Test-Path -LiteralPath $archive) { Remove-Item -LiteralPath $archive -Force }
}
Compress-Archive -Path (Join-Path $pcRoot 'dist\KPAH-PC\*') -DestinationPath $pcZip -CompressionLevel Optimal
Compress-Archive -Path (Join-Path $javaStaging '*') -DestinationPath $javaZip -CompressionLevel Optimal

$pcHash = (Get-FileHash -LiteralPath $pcZip -Algorithm SHA256).Hash.ToLowerInvariant()
$javaHash = (Get-FileHash -LiteralPath $javaZip -Algorithm SHA256).Hash.ToLowerInvariant()
$jarHash = (Get-FileHash -LiteralPath $javaVersionedJar -Algorithm SHA256).Hash.ToLowerInvariant()
"$pcHash *$(Split-Path -Leaf $pcZip)" | Set-Content -LiteralPath ($pcZip + '.sha256') -Encoding ASCII
"$javaHash *$(Split-Path -Leaf $javaZip)" | Set-Content -LiteralPath ($javaZip + '.sha256') -Encoding ASCII
"$jarHash *$(Split-Path -Leaf $javaVersionedJar)" | Set-Content -LiteralPath ($javaVersionedJar + '.sha256') -Encoding ASCII

foreach ($artifact in @($pcZip, ($pcZip + '.sha256'), $javaZip, ($javaZip + '.sha256'), $javaVersionedJar, ($javaVersionedJar + '.sha256'))) {
    Copy-Item -LiteralPath $artifact -Destination $webDownloads -Force
}

$baseUrl = "http://${HostName}:${WebPort}/downloads"
$manifest = [ordered]@{
    schemaVersion = 1
    publishedAt = (Get-Date).ToUniversalTime().ToString('o')
    pageUrl = "http://${HostName}:${WebPort}/tai-game"
    channels = [ordered]@{
        pc = [ordered]@{
            latestVersion = $Version
            minimumVersion = $MinimumVersion
            mandatory = $false
            downloadUrl = "$baseUrl/$(Split-Path -Leaf $pcZip)"
            sha256 = $pcHash
            summary = 'Auto train không dừng khi mở menu và có trình cập nhật chủ động.'
        }
        java = [ordered]@{
            latestVersion = $Version
            minimumVersion = $MinimumVersion
            mandatory = $false
            downloadUrl = "$baseUrl/$(Split-Path -Leaf $javaZip)"
            jarUrl = "$baseUrl/$(Split-Path -Leaf $javaVersionedJar)"
            sha256 = $javaHash
            jarSha256 = $jarHash
            summary = 'Auto train không dừng khi mở menu và gói FreeJ2ME tự kiểm tra cập nhật.'
        }
    }
}
$manifest | ConvertTo-Json -Depth 6 | Set-Content -LiteralPath (Join-Path $webDownloads 'manifest.json') -Encoding UTF8

Write-Host "Release LAN v$Version OK"
Write-Host "Trang tải: http://${HostName}:${WebPort}/tai-game"
Write-Host "PC SHA256: $pcHash"
Write-Host "Java SHA256: $javaHash"
