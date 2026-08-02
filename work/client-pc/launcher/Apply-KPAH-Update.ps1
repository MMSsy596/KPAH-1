param(
    [Parameter(Mandatory = $true)][string]$Package,
    [Parameter(Mandatory = $true)][string]$Target,
    [Parameter(Mandatory = $true)][int]$WaitProcessId
)

$ErrorActionPreference = 'Stop'
$resolvedTarget = [System.IO.Path]::GetFullPath($Target)
if (!(Test-Path -LiteralPath (Join-Path $resolvedTarget 'KPAH_276.exe'))) {
    throw 'Từ chối cập nhật vì thư mục đích không phải client KPAH.'
}

Wait-Process -Id $WaitProcessId -ErrorAction SilentlyContinue
$staging = Join-Path ([System.IO.Path]::GetTempPath()) ('kpah-update-' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $staging | Out-Null
try {
    Expand-Archive -LiteralPath $Package -DestinationPath $staging -Force
    Get-ChildItem -LiteralPath $staging -Force | ForEach-Object {
        Copy-Item -LiteralPath $_.FullName -Destination $resolvedTarget -Recurse -Force
    }
} catch {
    Add-Type -AssemblyName System.Windows.Forms
    [System.Windows.Forms.MessageBox]::Show(
        "Không thể áp dụng bản cập nhật. Client hiện tại sẽ được mở lại.`r`n`r`n$($_.Exception.Message)",
        'Cập nhật KPAH',
        [System.Windows.Forms.MessageBoxButtons]::OK,
        [System.Windows.Forms.MessageBoxIcon]::Warning
    ) | Out-Null
} finally {
    Remove-Item -LiteralPath $staging -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -LiteralPath $Package -Force -ErrorAction SilentlyContinue
}

Start-Process -FilePath (Join-Path $resolvedTarget 'KPAH-Launcher.exe') -ArgumentList '--skip-update' -WorkingDirectory $resolvedTarget
