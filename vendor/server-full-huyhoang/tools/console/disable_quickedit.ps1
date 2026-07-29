$ErrorActionPreference = "Stop"

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;

public static class ConsoleModeNative
{
    [DllImport("kernel32.dll", SetLastError = true)]
    public static extern IntPtr GetStdHandle(int nStdHandle);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out int lpMode);

    [DllImport("kernel32.dll", SetLastError = true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int dwMode);
}
"@

$STD_INPUT_HANDLE = -10
$ENABLE_INSERT_MODE = 0x0020
$ENABLE_QUICK_EDIT_MODE = 0x0040
$ENABLE_EXTENDED_FLAGS = 0x0080

$consoleHandle = [ConsoleModeNative]::GetStdHandle($STD_INPUT_HANDLE)
if ($consoleHandle -eq [IntPtr]::Zero -or $consoleHandle -eq [IntPtr](-1)) {
    exit 0
}

$consoleMode = 0
if (-not [ConsoleModeNative]::GetConsoleMode($consoleHandle, [ref]$consoleMode)) {
    exit 0
}

$newMode = $consoleMode -bor $ENABLE_EXTENDED_FLAGS
$newMode = $newMode -band (-bnot $ENABLE_QUICK_EDIT_MODE)
$newMode = $newMode -band (-bnot $ENABLE_INSERT_MODE)

[ConsoleModeNative]::SetConsoleMode($consoleHandle, $newMode) | Out-Null
