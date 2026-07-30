$procs = Get-CimInstance Win32_Process
$matched = foreach ($proc in $procs) {
    if ($proc.Name -match '^java(w)?\.exe$' -and $proc.CommandLine -match '(?i)-jar\s+.*KPAH2?\.jar') {
        $proc.ProcessId
    }
}

if ($matched) {
    $matched -join ','
}
