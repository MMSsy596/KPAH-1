@echo off
setlocal

set "ROOT=%~dp0"
cd /d "%ROOT%"
if not exist "%ROOT%logs" mkdir "%ROOT%logs"
if not exist "%ROOT%logs\runtime" mkdir "%ROOT%logs\runtime"

if not exist "%ROOT%dist\admin_local\KPAHAdminLocal.jar" (
  call "%ROOT%build_admin_local.bat"
  if errorlevel 1 exit /b 1
)

set "JAVA_BIN="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\java\resolve_java_bin.ps1" -RepoRoot . -Role runtime`) do set "JAVA_BIN=%%I"
if not defined JAVA_BIN (
  echo Khong tim thay java.exe
  exit /b 1
)

start "" powershell -NoProfile -ExecutionPolicy Bypass -WindowStyle Hidden -File "%ROOT%tools\admin_panel_config\start_hidden_server.ps1"

set "JAVA_GUI=%JAVA_BIN%javaw.exe"
if not exist "%JAVA_GUI%" set "JAVA_GUI=%JAVA_BIN%java.exe"
start "KPAH Admin Panel" /D "%ROOT%" "%JAVA_GUI%" -Djava.awt.headless=false -Dfile.encoding=UTF-8 -jar "%ROOT%dist\admin_local\KPAHAdminLocal.jar"
exit /b 0
