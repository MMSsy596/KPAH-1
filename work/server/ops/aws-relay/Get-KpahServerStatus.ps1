param(
    [string]$RepoRoot = "",
    [switch]$Json
)

$root = if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
} else {
    (Resolve-Path -LiteralPath $RepoRoot).Path
}

function Test-TcpEndpoint {
    param([string]$HostName, [int]$Port, [int]$TimeoutMs = 2500)
    $client = New-Object System.Net.Sockets.TcpClient
    try {
        $task = $client.ConnectAsync($HostName, $Port)
        return $task.Wait($TimeoutMs) -and $client.Connected
    } catch {
        return $false
    } finally {
        $client.Dispose()
    }
}

function Get-ListeningProcess {
    param([int]$Port)
    $connection = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue |
        Select-Object -First 1
    if (-not $connection) {
        return $null
    }
    $process = Get-Process -Id $connection.OwningProcess -ErrorAction SilentlyContinue
    return [PSCustomObject]@{
        port = $Port
        listening = $true
        processId = $connection.OwningProcess
        processName = if ($process) { $process.ProcessName } else { "unknown" }
    }
}

$configPath = Join-Path $root "local-config\kpah-tunnel.json"
$config = if (Test-Path -LiteralPath $configPath) {
    Get-Content -LiteralPath $configPath -Raw -Encoding UTF8 | ConvertFrom-Json
} else {
    Get-Content -LiteralPath (Join-Path $PSScriptRoot "kpah-tunnel.example.json") -Raw -Encoding UTF8 | ConvertFrom-Json
}

$supervisorPidPath = Join-Path $root "runtime-local\ssh-tunnel\supervisor.pid"
$supervisorPid = $null
$supervisorRunning = $false
if (Test-Path -LiteralPath $supervisorPidPath) {
    $supervisorPid = [int](Get-Content -LiteralPath $supervisorPidPath -Raw)
    $supervisorRunning = $null -ne (Get-Process -Id $supervisorPid -ErrorAction SilentlyContinue)
}

$manualTunnel = Get-CimInstance Win32_Process -Filter "Name = 'ssh.exe'" -ErrorAction SilentlyContinue |
    Where-Object { $_.CommandLine -like "*-R*0.0.0.0:$($config.remoteGamePort):*" } |
    Select-Object -First 1
$tunnelTask = Get-ScheduledTask -TaskName "KPAH SSH Tunnel Supervisor" -ErrorAction SilentlyContinue
$backupTask = Get-ScheduledTask -TaskName "KPAH Hourly Google Drive Backup" -ErrorAction SilentlyContinue

$components = @(
    [PSCustomObject]@{ name = "MariaDB"; endpoint = "127.0.0.1:3306"; healthy = [bool](Get-ListeningProcess 3306); detail = Get-ListeningProcess 3306 },
    [PSCustomObject]@{ name = "Login server"; endpoint = "0.0.0.0:8023"; healthy = [bool](Get-ListeningProcess 8023); detail = Get-ListeningProcess 8023 },
    [PSCustomObject]@{ name = "Game server"; endpoint = "0.0.0.0:19129"; healthy = [bool](Get-ListeningProcess 19129); detail = Get-ListeningProcess 19129 },
    [PSCustomObject]@{ name = "Local admin"; endpoint = "127.0.0.1:18023"; healthy = [bool](Get-ListeningProcess 18023); detail = Get-ListeningProcess 18023 },
    [PSCustomObject]@{
        name = "SSH tunnel"
        endpoint = "$($config.relayHost):$($config.remoteGamePort)"
        healthy = [bool]($supervisorRunning -or $manualTunnel)
        detail = if ($supervisorRunning) { "supervisor PID $supervisorPid" } elseif ($manualTunnel) { "manual PID $($manualTunnel.ProcessId)" } else { "stopped" }
    },
    [PSCustomObject]@{
        name = "Public game"
        endpoint = "$($config.relayHost):$($config.remoteGamePort)"
        healthy = Test-TcpEndpoint -HostName ([string]$config.relayHost) -Port ([int]$config.remoteGamePort)
        detail = "AWS relay TCP"
    },
    [PSCustomObject]@{
        name = "Tunnel auto-start"
        endpoint = "Task Scheduler"
        healthy = [bool]$tunnelTask
        detail = if ($tunnelTask) { [string]$tunnelTask.State } else { "not installed (requires Administrator)" }
    },
    [PSCustomObject]@{
        name = "Hourly backup"
        endpoint = "Task Scheduler"
        healthy = [bool]$backupTask
        detail = if ($backupTask) { [string]$backupTask.State } else { "not installed/configured" }
    }
)

$result = [PSCustomObject]@{
    checkedAt = (Get-Date).ToString("yyyy-MM-dd HH:mm:ss")
    overallHealthy = ($components | Where-Object { -not $_.healthy }).Count -eq 0
    components = $components
}

if ($Json) {
    $result | ConvertTo-Json -Depth 5
} else {
    $result.components | Select-Object name, endpoint, healthy, detail | Format-Table -AutoSize
    Write-Output ("Overall healthy: " + $result.overallHealthy)
}
