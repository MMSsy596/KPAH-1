param(
    [string]$RepoRoot = "",
    [int]$Port = 4028
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
$webRoot = Join-Path $repoRoot "web\web"
$stdout = Join-Path $repoRoot "logs\runtime\next_web_stdout.log"
$stderr = Join-Path $repoRoot "logs\runtime\next_web_stderr.log"

$existing = Get-CimInstance Win32_Process -ErrorAction SilentlyContinue | Where-Object {
    $_.Name -match 'node(\.exe)?' -and $_.CommandLine -like "*next start*" -and $_.CommandLine -like "*$Port*"
}
if ($existing) {
    Write-Output ("Next web already running on port " + $Port + " with PID " + ($existing | Select-Object -First 1 -ExpandProperty ProcessId))
    exit 0
}

Push-Location $webRoot
try {
    & npm run build | Out-Null
    if ($LASTEXITCODE -ne 0) {
        throw "Next build failed with exit code $LASTEXITCODE"
    }
    Start-Process -WindowStyle Hidden -FilePath "cmd.exe" -ArgumentList "/c","npm run start" -WorkingDirectory $webRoot -RedirectStandardOutput $stdout -RedirectStandardError $stderr
} finally {
    Pop-Location
}

Start-Sleep -Seconds 10
$ok = Test-NetConnection -ComputerName 127.0.0.1 -Port $Port -WarningAction SilentlyContinue
if (-not $ok.TcpTestSucceeded) {
    throw "Next web did not start on 127.0.0.1:$Port"
}

Write-Output ("Next web started on http://127.0.0.1:" + $Port)
