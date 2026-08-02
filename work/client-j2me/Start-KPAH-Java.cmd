@echo off
setlocal
cd /d "%~dp0"
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0Update-KPAH-Java.ps1"
where java.exe >nul 2>nul
if errorlevel 1 (
  echo Khong tim thay Java. Hay cai Java 8 tro len roi mo lai file nay.
  pause
  exit /b 1
)
java.exe -Dfile.encoding=ISO_8859_1 -jar freej2me-network.jar KPAH-Java.jar
if errorlevel 1 pause
endlocal
