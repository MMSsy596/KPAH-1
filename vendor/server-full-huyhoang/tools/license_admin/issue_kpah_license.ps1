param(
    [string]$ServerUrl = "http://127.0.0.1:18023",
    [string]$Token = "",
    [Parameter(Mandatory = $true)]
    [int]$Days,
    [string]$Note = ""
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

if ($Days -le 0) {
    throw "Days phai lon hon 0."
}

$buildScript = Join-Path $PSScriptRoot "build_kpah_license_admin.ps1"
$jarPath = Join-Path $PSScriptRoot "out\\kpah-license-admin.jar"
$java = "C:\\Program Files\\Java\\jdk-23\\bin\\java.exe"

if (!(Test-Path $java)) {
    throw "Khong tim thay java.exe tai $java"
}

if (!(Test-Path $jarPath)) {
    & powershell -ExecutionPolicy Bypass -File $buildScript
}

$arguments = @(
    "-jar",
    $jarPath,
    "--server-url",
    $ServerUrl,
    "--days",
    $Days
)

if (![string]::IsNullOrWhiteSpace($Token)) {
    $arguments += @("--token", $Token)
}
if (![string]::IsNullOrWhiteSpace($Note)) {
    $arguments += @("--note", $Note)
}

& $java $arguments
if ($LASTEXITCODE -ne 0) {
    throw "Tao ma license that bai"
}
