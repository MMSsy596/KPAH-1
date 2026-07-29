$ErrorActionPreference = "Stop"

$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$serverIni = Join-Path $projectRoot "server.ini"

if (!(Test-Path $serverIni)) {
    throw "Khong tim thay server.ini tai $serverIni"
}

$desired = [ordered]@{
    "sv.localAdminEnabled" = "1"
    "sv.localAdminHost" = "127.0.0.1"
    "sv.localAdminPort" = "18023"
    "sv.launchEmbeddedAdminPanel" = "1"
}

$lines = [System.Collections.Generic.List[string]]::new()
$lines.AddRange([string[]](Get-Content -LiteralPath $serverIni -Encoding UTF8))

foreach ($entry in $desired.GetEnumerator()) {
    $key = $entry.Key
    $value = $entry.Value
    $replaced = $false

    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match ('^\s*' + [regex]::Escape($key) + '\s*=')) {
            $lines[$i] = $key + "=" + $value
            $replaced = $true
            break
        }
    }

    if (-not $replaced) {
        $lines.Add($key + "=" + $value)
    }
}

Set-Content -LiteralPath $serverIni -Value $lines -Encoding UTF8

Write-Output "Da dam bao cau hinh embedded admin panel trong server.ini"
