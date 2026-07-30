param(
    [string]$RepoRoot = "",
    [string]$WinSwExe = "C:\Users\Administrator\Downloads\WinSW-x64.exe"
)

$ErrorActionPreference = "Stop"

function Resolve-RepoRoot {
    param([string]$InputRoot)

    if (-not [string]::IsNullOrWhiteSpace($InputRoot)) {
        return (Resolve-Path -LiteralPath $InputRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
}

function Stop-ProcessesByPattern {
    param([string[]]$Patterns)

    $procs = Get-CimInstance Win32_Process -ErrorAction SilentlyContinue
    foreach ($pattern in $Patterns) {
        $matches = $procs | Where-Object { $_.CommandLine -like $pattern }
        foreach ($proc in $matches) {
            if ($proc.ProcessId -and (Get-Process -Id $proc.ProcessId -ErrorAction SilentlyContinue)) {
                Stop-Process -Id $proc.ProcessId -Force
            }
        }
    }
}

function Install-Or-ReinstallService {
    param(
        [string]$ServiceName,
        [string]$ExePath
    )

    $service = Get-Service -Name $ServiceName -ErrorAction SilentlyContinue
    if ($service) {
        try {
            & $ExePath stop | Out-Null
        } catch {
        }
        Start-Sleep -Seconds 2
        & $ExePath uninstall | Out-Null
        Start-Sleep -Seconds 2
    }
    & $ExePath install | Out-Null
}

$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
$winSwSource = (Resolve-Path -LiteralPath $WinSwExe).Path
$serviceRoot = Join-Path $repoRoot "ops\win10-production\staging\services\winsw"

if (-not (Test-Path -LiteralPath $serviceRoot)) {
    throw "Khong tim thay thu muc WinSW: $serviceRoot"
}

if (-not (Test-Path -LiteralPath $winSwSource)) {
    throw "Khong tim thay WinSW-x64.exe tai $WinSwExe"
}

New-Item -ItemType Directory -Force -Path (Join-Path $repoRoot "ops\win10-production\staging\services\logs") | Out-Null

$services = @(
    "kpah-mysql",
    "kpah-login",
    "kpah-web-next",
    "kpah-nginx",
    "kpah-game"
)

foreach ($serviceName in $services) {
    $targetExe = Join-Path $serviceRoot ($serviceName + ".exe")
    Copy-Item -LiteralPath $winSwSource -Destination $targetExe -Force
}

Stop-ProcessesByPattern @(
    "*KPAH.jar*",
    "*CheckLoginSocket.jar*",
    "*next start*",
    "*C:\nginx-1.30.0\nginx.exe*",
    "*C:\mysql\bin\mysqld.exe*"
)
Start-Sleep -Seconds 3

foreach ($serviceName in $services) {
    $serviceExe = Join-Path $serviceRoot ($serviceName + ".exe")
    Install-Or-ReinstallService -ServiceName $serviceName -ExePath $serviceExe
}

foreach ($serviceName in $services) {
    Start-Service -Name $serviceName
    Start-Sleep -Seconds 4
}

Get-Service -Name $services | Select-Object Name,Status,StartType | Format-Table -AutoSize
