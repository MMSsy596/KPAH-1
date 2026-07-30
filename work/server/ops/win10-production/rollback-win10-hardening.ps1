param()

$ErrorActionPreference = "Stop"

$existing = Get-NetFirewallRule -Group "KPAH Win10" -ErrorAction SilentlyContinue
if ($existing) {
    $existing | Remove-NetFirewallRule | Out-Null
    Write-Output "Removed firewall rules in group 'KPAH Win10'."
} else {
    Write-Output "No firewall rules found in group 'KPAH Win10'."
}
