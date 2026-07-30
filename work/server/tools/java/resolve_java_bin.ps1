param(
    [string]$RepoRoot = "",
    [ValidateSet("runtime", "build", "admin")]
    [string]$Role = "runtime",
    [switch]$AllowX86
)

$ErrorActionPreference = "Stop"

function Resolve-RepoRoot {
    param(
        [string]$InputRoot
    )

    if (-not [string]::IsNullOrWhiteSpace($InputRoot)) {
        return (Resolve-Path -LiteralPath $InputRoot).Path
    }
    return (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
}

function Parse-IniFile {
    param(
        [string]$Path
    )

    $values = @{}
    if (-not (Test-Path -LiteralPath $Path)) {
        return $values
    }

    foreach ($line in Get-Content -LiteralPath $Path -Encoding UTF8) {
        if ($null -eq $line) {
            continue
        }
        $trimmed = $line.Trim()
        if ($trimmed.Length -eq 0 -or $trimmed.StartsWith("#") -or $trimmed.StartsWith("//")) {
            continue
        }
        $separator = $trimmed.IndexOf("=")
        if ($separator -lt 0) {
            continue
        }
        $key = $trimmed.Substring(0, $separator).Trim()
        $value = $trimmed.Substring($separator + 1).Trim()
        if ($key.Length -gt 0) {
            $values[$key] = $value
        }
    }

    return $values
}

function Test-JavaBin {
    param(
        [string]$BinPath,
        [string[]]$RequiredFiles
    )

    if ([string]::IsNullOrWhiteSpace($BinPath)) {
        return $false
    }
    foreach ($file in $RequiredFiles) {
        if (-not (Test-Path -LiteralPath (Join-Path $BinPath $file))) {
            return $false
        }
    }
    return $true
}

function Get-BinFromHome {
    param(
        [string]$HomePath,
        [string[]]$RequiredFiles
    )

    if ([string]::IsNullOrWhiteSpace($HomePath)) {
        return $null
    }

    $candidates = @($HomePath)
    if ($HomePath -notmatch "\\bin\\?$") {
        $candidates += (Join-Path $HomePath "bin")
    }

    foreach ($candidate in $candidates) {
        try {
            $resolved = (Resolve-Path -LiteralPath $candidate).Path
        } catch {
            continue
        }
        if (Test-JavaBin -BinPath $resolved -RequiredFiles $RequiredFiles) {
            return $resolved
        }
    }

    return $null
}

function Get-VersionRank {
    param(
        [string]$PathOrName
    )

    $value = ($PathOrName | Out-String).Trim().ToLowerInvariant()
    if ($value -match '(^|[^0-9])(17)([^0-9]|$)') {
        return 0
    }
    if ($value -match '(^|[^0-9])(1\.8|8)([^0-9]|$)') {
        return 1
    }
    return 9
}

function Add-Candidate {
    param(
        [System.Collections.Generic.List[object]]$List,
        [string]$HomePath,
        [string]$Label,
        [int]$SourceRank,
        [string[]]$RequiredFiles,
        [bool]$AllowX86Path
    )

    $bin = Get-BinFromHome -HomePath $HomePath -RequiredFiles $RequiredFiles
    if (-not $bin) {
        return
    }

    $normalized = $bin.ToLowerInvariant()
    foreach ($existing in $List) {
        if ($existing.Bin -eq $normalized) {
            return
        }
    }

    $isX86 = $bin -like "*Program Files (x86)*"
    if ($isX86 -and -not $AllowX86Path) {
        return
    }

    $versionRank = Get-VersionRank -PathOrName ($Label + " " + $bin)
    $score = $SourceRank * 100 + $versionRank * 10 + ($(if ($isX86) { 5 } else { 0 }))

    $List.Add([PSCustomObject]@{
        Bin = $normalized
        OriginalBin = $bin
        Score = $score
    })
}

$repoRoot = Resolve-RepoRoot -InputRoot $RepoRoot
$serverIni = Join-Path $repoRoot "server.ini"
$config = Parse-IniFile -Path $serverIni

$requiredFiles = switch ($Role) {
    "build" { @("java.exe", "javac.exe", "jar.exe") }
    "admin" { @("javaw.exe") }
    default { @("java.exe") }
}

$configKey = switch ($Role) {
    "build" { "sv.javaBuildHome" }
    "admin" { "sv.javaAdminHome" }
    default { "sv.javaRuntimeHome" }
}

$candidates = New-Object 'System.Collections.Generic.List[object]'

if ($config.ContainsKey($configKey)) {
    Add-Candidate -List $candidates -HomePath $config[$configKey] -Label $configKey -SourceRank 0 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
}

if ($env:JAVA_HOME) {
    Add-Candidate -List $candidates -HomePath $env:JAVA_HOME -Label "JAVA_HOME" -SourceRank 1 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
}

$searchRoots = @("C:\Program Files\Java")
if ($AllowX86) {
    $searchRoots += "C:\Program Files (x86)\Java"
}

foreach ($root in $searchRoots) {
    if (-not (Test-Path -LiteralPath $root)) {
        continue
    }
    $dirs = Get-ChildItem -LiteralPath $root -Directory -ErrorAction SilentlyContinue
    foreach ($dir in $dirs) {
        Add-Candidate -List $candidates -HomePath $dir.FullName -Label $dir.Name -SourceRank 2 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
    }
}

if ($Role -eq "build") {
    $javac = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($javac) {
        Add-Candidate -List $candidates -HomePath (Split-Path -Parent $javac.Source) -Label "PATH-javac" -SourceRank 3 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
    }
} elseif ($Role -eq "admin") {
    $javaw = Get-Command javaw.exe -ErrorAction SilentlyContinue
    if ($javaw) {
        Add-Candidate -List $candidates -HomePath (Split-Path -Parent $javaw.Source) -Label "PATH-javaw" -SourceRank 3 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
    }
} else {
    $java = Get-Command java.exe -ErrorAction SilentlyContinue
    if ($java) {
        Add-Candidate -List $candidates -HomePath (Split-Path -Parent $java.Source) -Label "PATH-java" -SourceRank 3 -RequiredFiles $requiredFiles -AllowX86Path $AllowX86.IsPresent
    }
}

$selected = $candidates | Sort-Object Score, OriginalBin | Select-Object -First 1
if (-not $selected) {
    throw "Khong tim thay Java phu hop cho role '$Role'."
}

Write-Output ($selected.OriginalBin + "\")
