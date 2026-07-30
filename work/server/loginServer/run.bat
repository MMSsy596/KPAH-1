@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0..\tools\console\disable_quickedit.ps1" >nul 2>nul

if /i not "%~1"=="--allow-direct" (
  title KPAH Login Socket Only
  echo.
  echo File nay chi mo login socket, khong phai panel day du.
  echo De bat game server + panel moi, hay chay file run.bat o thu muc goc:
  echo   C:\Users\Administrator\Desktop\SEVER-KPAH-FULL\run.bat
  echo.
  pause
  exit /b 0
)

set "ROOT=%~dp0"
cd /d "%ROOT%"

set "JAR=CheckLoginSocket.jar"
set "PORT=8023"

if not exist "%JAR%" (
  echo Khong tim thay %JAR% trong thu muc loginServer.
  exit /b 1
)

for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "$port='8023'; if (Test-Path 'server.ini') { $line = Get-Content 'server.ini' | Where-Object { $_ -match '^\s*port\s*=' } | Select-Object -First 1; if ($line) { $parts = $line -split '=', 2; if ($parts.Count -eq 2 -and $parts[1].Trim()) { $port = $parts[1].Trim() } } }; Write-Output $port"`) do set "PORT=%%I"

set "JAVA_BIN="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%..\tools\java\resolve_java_bin.ps1" -RepoRoot .. -Role runtime`) do set "JAVA_BIN=%%I"
if not defined JAVA_BIN (
  echo Khong tim thay java.exe. Hay cai JAVA_HOME hoac cai JDK vao C:\Program Files\Java.
  exit /b 1
)
if not defined KPAH_LOGIN_JAVA_OPTS set "KPAH_LOGIN_JAVA_OPTS=-Xms256m -Xmx512m -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"

set "PORT_OWNER="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "$conn = Get-NetTCPConnection -LocalPort %PORT% -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1; if ($conn) { $proc = Get-CimInstance Win32_Process -Filter ('ProcessId = ' + $conn.OwningProcess) | Select-Object -First 1; if ($proc) { Write-Output ($proc.ProcessId.ToString() + '|' + $proc.Name + '|' + $proc.CommandLine) } else { Write-Output ($conn.OwningProcess.ToString() + '|unknown|') } }"`) do set "PORT_OWNER=%%I"

if defined PORT_OWNER (
  for /f "tokens=1,2,* delims=|" %%A in ("%PORT_OWNER%") do (
    echo Cong %PORT% dang duoc su dung boi PID %%A ^(%%B^).
    if not "%%C"=="" echo Lenh chay: %%C
  )
  echo Hay tat tien trinh cu hoac doi port trong server.ini roi chay lai.
  exit /b 1
)

title avmk
echo Dang dung Java: %JAVA_BIN%java.exe
echo JVM opts: %KPAH_LOGIN_JAVA_OPTS%
"%JAVA_BIN%java.exe" %KPAH_LOGIN_JAVA_OPTS% -jar "%JAR%"
set "SERVER_EXIT=%errorlevel%"
if not "%SERVER_EXIT%"=="0" (
  echo.
  echo Login server da dung voi ma loi %SERVER_EXIT%.
  pause
)
exit /b %SERVER_EXIT%
