param(
    [string]$RepoRoot = ""
)

$ErrorActionPreference = "Stop"

function Resolve-RepoRoot {
    param([string]$InputRoot)

    if (-not [string]::IsNullOrWhiteSpace($InputRoot)) {
        return (Resolve-Path -LiteralPath $InputRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
}

$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
$serviceRoot = Join-Path $repoRoot "ops\win10-production\staging\services\winsw"
$services = @(
    "kpah-game",
    "kpah-nginx",
    "kpah-web-next",
    "kpah-login",
    "kpah-mysql"
)

foreach ($serviceName in $services) {
    $serviceExe = Join-Path $serviceRoot ($serviceName + ".exe")
    if (Test-Path -LiteralPath $serviceExe) {
        try {
            & $serviceExe stop | Out-Null
        } catch {
        }
        Start-Sleep -Seconds 2
        try {
            & $serviceExe uninstall | Out-Null
        } catch {
        }
    }
}

Get-Service -Name $services -ErrorAction SilentlyContinue | Select-Object Name,Status,StartType | Format-Table -AutoSize
