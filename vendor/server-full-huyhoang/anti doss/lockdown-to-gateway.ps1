# Game server firewall lock down to only accept Gateway IP
# Run this script as Administrator on the GAME SERVER

param(
    [string]$GatewayIp = "",
    [string]$Ports = "19129,8023"
)

function Test-Admin {
    $principal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
    return $principal.IsInRole([Security.Principal.WindowsBuiltinRole]::Administrator)
}

if (-not (Test-Admin)) {
    Write-Host "Please run as Administrator." -ForegroundColor Red
    exit 1
}

if ([string]::IsNullOrWhiteSpace($GatewayIp)) {
    $GatewayIp = Read-Host "Enter Gateway IP"
}

if ([string]::IsNullOrWhiteSpace($Ports)) {
    $Ports = Read-Host "Enter ports (comma separated)"
}

$portList = $Ports.Split(',') | ForEach-Object { $_.Trim() } | Where-Object { $_ -match '^[0-9]+$' }
if ($portList.Count -eq 0) {
    Write-Host "No valid ports." -ForegroundColor Red
    exit 1
}

foreach ($p in $portList) {
    $ruleName = "KPAH Allow Gateway $p"
    Write-Host "Configuring port $p to allow only $GatewayIp"
    netsh advfirewall firewall delete rule name="$ruleName" | Out-Null
    netsh advfirewall firewall add rule name="$ruleName" dir=in action=allow protocol=TCP localport=$p remoteip=$GatewayIp | Out-Null
}

Write-Host "Done. Make sure there are no other ALLOW rules for these ports."
