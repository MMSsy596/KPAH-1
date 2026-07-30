$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$root = Split-Path -Parent $PSScriptRoot
$projectRoot = Split-Path -Parent $root
$sourceJar = Join-Path $projectRoot "dist\\client_jar_locked\\vanphong18x5.jar"
$outputDir = Join-Path $projectRoot "dist\\client_android_split"

if (!(Test-Path $sourceJar)) {
    throw "Khong tim thay client nguon: $sourceJar"
}

New-Item -ItemType Directory -Force $outputDir | Out-Null

$variants = @(
    @{
        Output = "vanphong18x5_slot1.jar"
        SuiteName = "Khi Phach 1"
        EntryName = "Khi Phach 1"
        EntryClass = "game.GameMidlet"
        EntryIcon = "/1775014945565.png"
        EntryConfig = "/1775014945565.ini"
    },
    @{
        Output = "vanphong18x5_slot2.jar"
        SuiteName = "Khi Phach 2"
        EntryName = "Khi Phach 2"
        EntryClass = "b.game.GameMidlet"
        EntryIcon = "/1775014945565.png"
        EntryConfig = "/1775014945565.ini"
    },
    @{
        Output = "vanphong18x5_slot3.jar"
        SuiteName = "Khi Phach 3"
        EntryName = "Khi Phach 3"
        EntryClass = "c.game.GameMidlet"
        EntryIcon = "/1775014945565.png"
        EntryConfig = "/1775014945565.ini"
    },
    @{
        Output = "vanphong18x5_slot4.jar"
        SuiteName = "Khi Phach 4"
        EntryName = "Khi Phach 4"
        EntryClass = "d.game.GameMidlet"
        EntryIcon = "/1775014945565.png"
        EntryConfig = "/1775014945565.ini"
    },
    @{
        Output = "vanphong18x5_slot5.jar"
        SuiteName = "Khi Phach 5"
        EntryName = "Khi Phach 5"
        EntryClass = "e.game.GameMidlet"
        EntryIcon = "/1775014945565.png"
        EntryConfig = "/1775014945565.ini"
    }
)

function Copy-JarEntry {
    param(
        [System.IO.Compression.ZipArchiveEntry]$SourceEntry,
        [System.IO.Compression.ZipArchive]$TargetArchive
    )

    $targetEntry = $TargetArchive.CreateEntry($SourceEntry.FullName, [System.IO.Compression.CompressionLevel]::Optimal)
    $targetEntry.LastWriteTime = $SourceEntry.LastWriteTime

    $input = $SourceEntry.Open()
    try {
        $output = $targetEntry.Open()
        try {
            $input.CopyTo($output)
        } finally {
            $output.Dispose()
        }
    } finally {
        $input.Dispose()
    }
}

function Write-ManifestEntry {
    param(
        [System.IO.Compression.ZipArchive]$TargetArchive,
        [string]$SuiteName,
        [string]$EntryName,
        [string]$EntryClass,
        [string]$EntryIcon,
        [string]$EntryConfig
    )

    $manifest = @(
        "Manifest-Version: 1.0"
        "MicroEdition-Configuration: CLDC-1.1"
        "MicroEdition-Profile: MIDP-2.0"
        "MIDlet-Version: 2.5"
        "MIDlet-Vendor: AngelChip"
        "MIDlet-Name: $SuiteName"
        "MIDlet-1: $SuiteName,/MrQuyet/mid.png,MrQuyet.MrQuyet"
        "0: $EntryName,$EntryIcon,$EntryClass,$EntryConfig"
        ""
    ) -join "`r`n"

    $manifestEntry = $TargetArchive.CreateEntry("META-INF/MANIFEST.MF", [System.IO.Compression.CompressionLevel]::Optimal)
    $writer = New-Object System.IO.StreamWriter($manifestEntry.Open(), (New-Object System.Text.UTF8Encoding($false)))
    try {
        $writer.NewLine = "`r`n"
        $writer.Write($manifest)
    } finally {
        $writer.Dispose()
    }
}

function Build-AndroidVariant {
    param(
        [string]$InputJar,
        [string]$OutputJar,
        [string]$SuiteName,
        [string]$EntryName,
        [string]$EntryClass,
        [string]$EntryIcon,
        [string]$EntryConfig
    )

    $inputStream = [System.IO.File]::Open($InputJar, [System.IO.FileMode]::Open, [System.IO.FileAccess]::Read, [System.IO.FileShare]::Read)
    try {
        $sourceArchive = New-Object System.IO.Compression.ZipArchive($inputStream, [System.IO.Compression.ZipArchiveMode]::Read, $false)
        try {
            $outputStream = [System.IO.File]::Open($OutputJar, [System.IO.FileMode]::Create, [System.IO.FileAccess]::ReadWrite, [System.IO.FileShare]::None)
            try {
                $targetArchive = New-Object System.IO.Compression.ZipArchive($outputStream, [System.IO.Compression.ZipArchiveMode]::Create, $false)
                try {
                    foreach ($entry in $sourceArchive.Entries) {
                        if ($entry.FullName -eq "META-INF/MANIFEST.MF") {
                            continue
                        }
                        Copy-JarEntry -SourceEntry $entry -TargetArchive $targetArchive
                    }
                    Write-ManifestEntry `
                        -TargetArchive $targetArchive `
                        -SuiteName $SuiteName `
                        -EntryName $EntryName `
                        -EntryClass $EntryClass `
                        -EntryIcon $EntryIcon `
                        -EntryConfig $EntryConfig
                } finally {
                    $targetArchive.Dispose()
                }
            } finally {
                $outputStream.Dispose()
            }
        } finally {
            $sourceArchive.Dispose()
        }
    } finally {
        $inputStream.Dispose()
    }
}

$results = @()
foreach ($variant in $variants) {
    $outputJar = Join-Path $outputDir $variant.Output
    Build-AndroidVariant `
        -InputJar $sourceJar `
        -OutputJar $outputJar `
        -SuiteName $variant.SuiteName `
        -EntryName $variant.EntryName `
        -EntryClass $variant.EntryClass `
        -EntryIcon $variant.EntryIcon `
        -EntryConfig $variant.EntryConfig

    $results += "OUTPUT_JAR=$outputJar"
}

$results -join "`r`n"
