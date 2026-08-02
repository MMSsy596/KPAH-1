$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$configPath = Join-Path $root 'client-update.json'
$package = $null
$staging = $null
if (!(Test-Path -LiteralPath $configPath)) {
    exit 0
}

try {
    $config = Get-Content -LiteralPath $configPath -Raw | ConvertFrom-Json
    $manifest = Invoke-RestMethod -Uri $config.manifestUrl -UseBasicParsing -TimeoutSec 8
    $release = $manifest.channels.java
    if ($null -eq $release -or [version]$release.latestVersion -le [version]$config.version) {
        exit 0
    }

    Add-Type -AssemblyName System.Windows.Forms
    $required = [bool]$release.mandatory -or [version]$config.version -lt [version]$release.minimumVersion
    $buttons = if ($required) { [System.Windows.Forms.MessageBoxButtons]::OK } else { [System.Windows.Forms.MessageBoxButtons]::YesNo }
    $choice = [System.Windows.Forms.MessageBox]::Show(
        "Có phiên bản KPAH $($release.latestVersion).`r`n`r`n$($release.summary)`r`n`r`nTải và cập nhật ngay?",
        'Cập nhật KPAH Java',
        $buttons,
        [System.Windows.Forms.MessageBoxIcon]::Information
    )
    if ((!$required -and $choice -ne [System.Windows.Forms.DialogResult]::Yes) -or
        ($required -and $choice -ne [System.Windows.Forms.DialogResult]::OK)) {
        exit 0
    }

    $package = Join-Path ([System.IO.Path]::GetTempPath()) ('kpah-java-' + [Guid]::NewGuid().ToString('N') + '.zip')
    $staging = Join-Path ([System.IO.Path]::GetTempPath()) ('kpah-java-update-' + [Guid]::NewGuid().ToString('N'))
    Invoke-WebRequest -Uri $release.downloadUrl -OutFile $package -UseBasicParsing -TimeoutSec 120
    $actualHash = (Get-FileHash -LiteralPath $package -Algorithm SHA256).Hash.ToLowerInvariant()
    if ($actualHash -ne ([string]$release.sha256).ToLowerInvariant()) {
        throw 'Gói cập nhật không đúng mã SHA-256.'
    }
    New-Item -ItemType Directory -Path $staging | Out-Null
    Expand-Archive -LiteralPath $package -DestinationPath $staging -Force
    Get-ChildItem -LiteralPath $staging -Force | ForEach-Object {
        Copy-Item -LiteralPath $_.FullName -Destination $root -Recurse -Force
    }
} catch {
    Write-Warning "Chưa thể kiểm tra/cài cập nhật: $($_.Exception.Message)"
} finally {
    if ($package) {
        Remove-Item -LiteralPath $package -Force -ErrorAction SilentlyContinue
    }
    if ($staging) {
        Remove-Item -LiteralPath $staging -Recurse -Force -ErrorAction SilentlyContinue
    }
}
