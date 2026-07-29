$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$root = Split-Path -Parent $PSScriptRoot
$projectRoot = Split-Path -Parent $root
$sourceFile = Join-Path $PSScriptRoot "PatchClientJar.java"
$workRoot = Join-Path $PSScriptRoot "work"
$classesDir = Join-Path $workRoot "classes"
$inputDir = Join-Path $workRoot "input"
$sourceDir = Join-Path $PSScriptRoot "sources"
$outputDir = Join-Path $projectRoot "dist\\client_jar_locked"
$measurementsFile = Join-Path $outputDir "measurements.txt"
$serverListFile = Join-Path $projectRoot "web\\NQSH2.txt"

function Read-ClientAuthCatalog {
    param([string]$Path)

    $entries = @()
    if (!(Test-Path $Path)) {
        return $entries
    }

    $lines = Get-Content -Path $Path -Encoding UTF8
    $current = [ordered]@{}
    foreach ($line in (@($lines) + "")) {
        $trimmed = if ($null -eq $line) { "" } else { $line.Trim() }
        if ($trimmed.Length -gt 0 -and [int][char]$trimmed[0] -eq 0xFEFF) {
            $trimmed = $trimmed.Substring(1).Trim()
        }
        if ([string]::IsNullOrWhiteSpace($trimmed)) {
            if ($current.Count -gt 0) {
                $entries += [pscustomobject]$current
                $current = [ordered]@{}
            }
            continue
        }
        if ($trimmed.StartsWith("#") -or $trimmed.StartsWith("//")) {
            continue
        }
        $separatorIndex = $trimmed.IndexOf("=")
        if ($separatorIndex -le 0) {
            continue
        }
        $key = $trimmed.Substring(0, $separatorIndex).Trim().ToUpperInvariant()
        $value = $trimmed.Substring($separatorIndex + 1).Trim()
        $current[$key] = $value
    }
    return $entries
}

function Parse-ClientAuthOutput {
    param([string]$Text)

    $entry = [ordered]@{}
    foreach ($line in ($Text -split "`r?`n")) {
        $trimmed = if ($null -eq $line) { "" } else { $line.Trim() }
        if ([string]::IsNullOrWhiteSpace($trimmed)) {
            continue
        }
        $separatorIndex = $trimmed.IndexOf("=")
        if ($separatorIndex -le 0) {
            continue
        }
        $key = $trimmed.Substring(0, $separatorIndex).Trim().ToUpperInvariant()
        $value = $trimmed.Substring($separatorIndex + 1).Trim()
        $entry[$key] = $value
    }
    if ($entry.Count -eq 0) {
        return $null
    }
    return [pscustomobject]$entry
}

function Get-ClientAuthMeasurement {
    param(
        [object[]]$Entries,
        [string]$ClientId
    )

    foreach ($entry in $Entries) {
        $existingClientId = ("" + $entry.CLIENT_ID).Trim().ToLowerInvariant()
        if ($existingClientId -eq $ClientId.Trim().ToLowerInvariant()) {
            return ("" + $entry.MEASUREMENT).Trim()
        }
    }
    return ""
}

function Format-ClientAuthEntry {
    param([object]$Entry)

    $preferredKeys = @(
        "CLIENT_ID",
        "DEFAULT_HOST",
        "SERVER_LIST_URL",
        "MEASUREMENT",
        "OUTPUT_SHA256",
        "OUTPUT_JAR"
    )
    $lines = @()
    foreach ($key in $preferredKeys) {
        $property = $Entry.PSObject.Properties[$key]
        if ($null -eq $property) {
            continue
        }
        $value = ("" + $property.Value).Trim()
        if (-not [string]::IsNullOrWhiteSpace($value)) {
            $lines += "${key}=${value}"
        }
    }
    foreach ($property in $Entry.PSObject.Properties) {
        if ($preferredKeys -contains $property.Name) {
            continue
        }
        $value = ("" + $property.Value).Trim()
        if (-not [string]::IsNullOrWhiteSpace($value)) {
            $lines += "$($property.Name)=$value"
        }
    }
    return $lines -join "`r`n"
}

$jdkBin = ""
if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME "bin\\javac.exe")) -and (Test-Path (Join-Path $env:JAVA_HOME "bin\\java.exe"))) {
    $jdkBin = Join-Path $env:JAVA_HOME "bin"
} else {
    $javacCmd = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($javacCmd) {
        $jdkBin = Split-Path -Parent $javacCmd.Source
    } else {
        $jdk = Get-ChildItem "C:\\Program Files\\Java" -Directory -Filter "jdk-*" -ErrorAction SilentlyContinue | Sort-Object Name -Descending | Select-Object -First 1
        if ($jdk) {
            $jdkBin = Join-Path $jdk.FullName "bin"
        }
    }
}

if (-not $jdkBin) { throw "Khong tim thay JDK" }

$javac = Join-Path $jdkBin "javac.exe"
$java = Join-Path $jdkBin "java.exe"

if (!(Test-Path $javac)) { throw "Khong tim thay javac.exe tai $javac" }
if (!(Test-Path $java)) { throw "Khong tim thay java.exe tai $java" }
if (!(Test-Path $sourceFile)) { throw "Khong tim thay PatchClientJar.java" }
if (!(Test-Path $serverListFile)) { throw "Khong tim thay $serverListFile" }

New-Item -ItemType Directory -Force $classesDir, $inputDir, $sourceDir, $outputDir | Out-Null
Get-ChildItem -Path $classesDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
Get-ChildItem -Path $inputDir -Force -ErrorAction SilentlyContinue | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue

$deployDir = ""
$backupClientDir = Get-ChildItem -Path $projectRoot -Directory -ErrorAction SilentlyContinue |
    Where-Object { $_.Name -like "*_backup_*" -and (Test-Path (Join-Path $_.FullName "grinding2.jar")) } |
    Sort-Object Name -Descending |
    Select-Object -First 1
if ($backupClientDir) {
    $backupMarker = "_backup_"
    $markerIndex = $backupClientDir.Name.IndexOf($backupMarker)
    if ($markerIndex -gt 0) {
        $candidateName = $backupClientDir.Name.Substring(0, $markerIndex)
        $candidatePath = Join-Path $projectRoot $candidateName
        if (Test-Path $candidatePath) {
            $deployDir = $candidatePath
        }
    }
}
if ([string]::IsNullOrWhiteSpace($deployDir)) {
    $deployCandidate = Get-ChildItem -Path $projectRoot -Directory -ErrorAction SilentlyContinue |
        Where-Object {
            $_.Name -notlike "*_backup_*" -and
            (Test-Path (Join-Path $_.FullName "grinding2.jar")) -and
            (Get-ChildItem -Path $_.FullName -File -Filter "*.x5.jar" -ErrorAction SilentlyContinue | Select-Object -First 1)
        } |
        Sort-Object Name |
        Select-Object -First 1
    if ($deployCandidate) {
        $deployDir = $deployCandidate.FullName
    }
}
if ([string]::IsNullOrWhiteSpace($deployDir)) {
    throw "Khong tim thay thu muc phat hanh client"
}
New-Item -ItemType Directory -Force $deployDir | Out-Null

$serverListLine = Get-Content -Path $serverListFile | Where-Object { -not [string]::IsNullOrWhiteSpace($_) } | Select-Object -First 1
if ([string]::IsNullOrWhiteSpace($serverListLine)) {
    throw "File $serverListFile khong co du lieu"
}
$serverTokens = $serverListLine.Split(":")
if ($serverTokens.Length -lt 4) {
    throw "Noi dung $serverListFile khong dung dinh dang Name:Host:Port:Index"
}
$defaultHost = $serverTokens[1].Trim()
if ([string]::IsNullOrWhiteSpace($defaultHost)) {
    throw "Khong tach duoc host tu $serverListFile"
}
$serverListUrl = "http://$defaultHost/NQSH2.txt"

& $javac `
    --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED `
    -encoding UTF-8 `
    -d $classesDir `
    $sourceFile

if ($LASTEXITCODE -ne 0) { throw "Compile PatchClientJar that bai" }

$grindingSource = Join-Path $sourceDir "grinding2.jar"
$vanPhongSource = Join-Path $sourceDir "vanphong18x5.jar"
$vanPhongDeployItem = Get-ChildItem -Path $deployDir -File -Filter "*.x5.jar" -ErrorAction SilentlyContinue | Where-Object { $_.Name -like "*18+*" } | Select-Object -First 1
$vanPhongDeploy = if ($vanPhongDeployItem) { $vanPhongDeployItem.FullName } else { Join-Path $deployDir "vanphong18x5.jar" }
$vanPhongMirrorOutputs = @(
    Join-Path $outputDir "vanphong18x5.jar"
)
Get-ChildItem -Path $outputDir -File -Filter "*.x5.jar" -ErrorAction SilentlyContinue |
    Where-Object { $_.Name -like "*18+*" -or $_.BaseName -eq "vanphong18x5" } |
    ForEach-Object {
        if ($vanPhongMirrorOutputs -notcontains $_.FullName) {
            $vanPhongMirrorOutputs += $_.FullName
        }
    }

if (!(Test-Path $grindingSource)) { throw "Khong tim thay nguon client: $grindingSource" }
if (!(Test-Path $vanPhongSource)) { throw "Khong tim thay nguon client: $vanPhongSource" }

$existingCatalog = Read-ClientAuthCatalog -Path $measurementsFile

$variants = @(
    @{
        Source = $grindingSource
        Output = (Join-Path $outputDir "grinding2.jar")
        Deploy = (Join-Path $deployDir "grinding2.jar")
        MirrorOutputs = @()
        ClientId = "grinding2"
    },
    @{
        Source = $vanPhongSource
        Output = (Join-Path $outputDir "vanphong18x5.jar")
        Deploy = $vanPhongDeploy
        MirrorOutputs = $vanPhongMirrorOutputs
        ClientId = "vanphong18x5"
    }
)

$rebuiltEntries = @{}
$rebuiltOrder = @()
foreach ($variant in $variants) {
    $asciiSource = Join-Path $inputDir ($variant.ClientId + ".jar")
    Copy-Item -LiteralPath $variant.Source -Destination $asciiSource -Force
    $measurementOverride = Get-ClientAuthMeasurement -Entries $existingCatalog -ClientId $variant.ClientId
    $javaArgs = @(
        "--add-exports",
        "java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED",
        "-cp",
        $classesDir,
        "PatchClientJar",
        $asciiSource,
        $variant.Output,
        $variant.ClientId,
        $defaultHost,
        $serverListUrl
    )
    if (-not [string]::IsNullOrWhiteSpace($measurementOverride)) {
        $javaArgs += $measurementOverride
    }
    $runOutput = & $java @javaArgs
    if ($LASTEXITCODE -ne 0) { throw "Patch client jar that bai: $($variant.Output)" }
    Copy-Item -LiteralPath $variant.Output -Destination $variant.Deploy -Force
    foreach ($mirrorOutput in $variant.MirrorOutputs) {
        if ($mirrorOutput -ne $variant.Output) {
            Copy-Item -LiteralPath $variant.Output -Destination $mirrorOutput -Force
        }
    }
    $parsedOutput = Parse-ClientAuthOutput -Text (($runOutput | Out-String).Trim())
    if ($null -eq $parsedOutput) {
        throw "Khong doc duoc ket qua PatchClientJar cho $($variant.ClientId)"
    }
    $clientIdKey = ("" + $parsedOutput.CLIENT_ID).Trim().ToLowerInvariant()
    if ([string]::IsNullOrWhiteSpace($clientIdKey)) {
        throw "PatchClientJar khong tra ve CLIENT_ID hop le cho $($variant.Output)"
    }
    $rebuiltEntries[$clientIdKey] = $parsedOutput
    $rebuiltOrder += $clientIdKey
}

$mergedEntries = @()
$seenEntries = @{}
foreach ($entry in $existingCatalog) {
    $clientIdKey = ("" + $entry.CLIENT_ID).Trim().ToLowerInvariant()
    if (-not [string]::IsNullOrWhiteSpace($clientIdKey) -and $rebuiltEntries.ContainsKey($clientIdKey)) {
        $mergedEntries += $rebuiltEntries[$clientIdKey]
        $seenEntries[$clientIdKey] = $true
    } else {
        $mergedEntries += $entry
    }
}
foreach ($clientIdKey in $rebuiltOrder) {
    if (-not $seenEntries.ContainsKey($clientIdKey) -and $rebuiltEntries.ContainsKey($clientIdKey)) {
        $mergedEntries += $rebuiltEntries[$clientIdKey]
        $seenEntries[$clientIdKey] = $true
    }
}
if ($mergedEntries.Count -eq 0) {
    throw "Khong tao duoc catalog client auth"
}
($mergedEntries | ForEach-Object { Format-ClientAuthEntry -Entry $_ }) -join "`r`n`r`n" | Set-Content -Path $measurementsFile -Encoding UTF8
Get-Content -Path $measurementsFile

$androidSplitScript = Join-Path $PSScriptRoot "build_android_split_client_jars.ps1"
if (Test-Path $androidSplitScript) {
    & powershell -NoProfile -ExecutionPolicy Bypass -File $androidSplitScript
}
