@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "ROOT=%~dp0"
cd /d "%ROOT%"
if not exist "%ROOT%logs" mkdir "%ROOT%logs"
if not exist "%ROOT%logs\runtime" mkdir "%ROOT%logs\runtime"
if not exist "%ROOT%logs\vantieu" mkdir "%ROOT%logs\vantieu"
if not exist "%ROOT%logs\players" mkdir "%ROOT%logs\players"
if not exist "%ROOT%logs\crash" mkdir "%ROOT%logs\crash"
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\console\disable_quickedit.ps1" >nul 2>nul

set "RESTART_REQUESTED="
set "DESKTOP_ADMIN_REQUESTED="
set "ADMIN_APP_REQUESTED=1"
set "FORWARDED_ARGS="

:parse_args
if "%~1"=="" goto args_done
if /i "%~1"=="--restart" (
  set "RESTART_REQUESTED=1"
) else if /i "%~1"=="--desktop-admin" (
  set "DESKTOP_ADMIN_REQUESTED=1"
) else if /i "%~1"=="--no-admin-app" (
  set "ADMIN_APP_REQUESTED="
) else (
  if defined FORWARDED_ARGS (
    set "FORWARDED_ARGS=!FORWARDED_ARGS! %~1"
  ) else (
    set "FORWARDED_ARGS=%~1"
  )
)
shift
goto parse_args

:args_done

if not defined DESKTOP_ADMIN_REQUESTED (
  findstr /b /i "sv.launchEmbeddedAdminPanel=1" "server.ini" >nul 2>nul
  if not errorlevel 1 set "DESKTOP_ADMIN_REQUESTED=1"
)
if defined ADMIN_APP_REQUESTED (
  findstr /b /i "sv.launchAdminApp=0" "server.ini" >nul 2>nul
  if not errorlevel 1 set "ADMIN_APP_REQUESTED="
)

title KPAH Full Server Launcher

if defined DESKTOP_ADMIN_REQUESTED (
  powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\admin_panel_config\ensure_embedded_admin_panel.ps1"
  if errorlevel 1 exit /b 1
)

set "LOGIN_PORT=8023"
for /f "tokens=1,* delims==" %%A in ('findstr /b /i "port=" "loginServer\server.ini" 2^>nul') do set "LOGIN_PORT=%%B"

set "GAME_PORT=19129"
for /f "tokens=1,* delims==" %%A in ('findstr /b /i "sv.port=" "server.ini" 2^>nul') do set "GAME_PORT=%%B"

set "LOGIN_PID="
for /f "tokens=5" %%I in ('netstat -ano -p tcp ^| findstr /R /C:":%LOGIN_PORT% .*LISTENING"') do set "LOGIN_PID=%%I"

if defined LOGIN_PID (
  echo Login server da dang chay o cong %LOGIN_PORT%.
) else (
  echo Dang mo login server o cong %LOGIN_PORT%...
  if defined FORWARDED_ARGS (
    start "KPAH Login Server" /D "%ROOT%loginServer" cmd /c run.bat --allow-direct !FORWARDED_ARGS!
  ) else (
    start "KPAH Login Server" /D "%ROOT%loginServer" cmd /c run.bat --allow-direct
  )
  for /l %%N in (1,1,15) do (
    if not defined LOGIN_PID (
      for /f "tokens=5" %%I in ('netstat -ano -p tcp ^| findstr /R /C:":%LOGIN_PORT% .*LISTENING"') do set "LOGIN_PID=%%I"
      if not defined LOGIN_PID timeout /t 1 >nul
    )
  )
  if not defined LOGIN_PID echo Khong xac nhan duoc login server sau 15 giay, van tiep tuc khoi dong game server...
)

set "GAME_PID="
for /f "tokens=5" %%I in ('netstat -ano -p tcp ^| findstr /R /C:":%GAME_PORT% .*LISTENING"') do set "GAME_PID=%%I"

if defined GAME_PID (
  if defined RESTART_REQUESTED (
    echo Dang tat full game server cu voi PID: !GAME_PID!...
    taskkill /PID !GAME_PID! /F >nul 2>nul
    if errorlevel 1 (
      echo Khong the tat PID !GAME_PID!. Hay tat thu cong roi chay lai.
      exit /b 1
    )
    for /l %%N in (1,1,10) do (
      set "GAME_PID="
      for /f "tokens=5" %%I in ('netstat -ano -p tcp ^| findstr /R /C:":%GAME_PORT% .*LISTENING"') do set "GAME_PID=%%I"
      if defined GAME_PID timeout /t 1 >nul
    )
    if defined GAME_PID (
      echo Khong the xac nhan full game server da tat han. Hay thu lai sau.
      exit /b 1
    )
  ) else (
    echo Full game server da dang chay voi PID: !GAME_PID!
    echo Mo run.bat luc nay chi mo app admin vao server hien tai, khong thay the process dang chay.
    echo Neu ban vua sua source ^(vi du 108 Luong Son hoac NPC thu phi^), hay chay: run.bat --restart
    call :maybe_launch_admin_app_now
    pause
    exit /b 0
  )
)

echo Dang mo full game server...
call build_server.bat
if errorlevel 1 (
  echo.
  echo Launcher da dung voi ma loi 1.
  exit /b 1
)

set "GAME_PID="
for /f "tokens=5" %%I in ('netstat -ano -p tcp ^| findstr /R /C:":%GAME_PORT% .*LISTENING"') do set "GAME_PID=%%I"
if defined GAME_PID (
  echo Full game server da dang chay voi PID: !GAME_PID!
  exit /b 0
)

set "JAVA_BIN="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\java\resolve_java_bin.ps1" -RepoRoot . -Role runtime`) do set "JAVA_BIN=%%I"
if not defined JAVA_BIN (
  echo Khong tim thay java.exe
  exit /b 1
)
if not defined KPAH_JAVA_OPTS set "KPAH_JAVA_OPTS=-Xms4096m -Xmx4096m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"

echo Dang dung Java: %JAVA_BIN%java.exe
echo JVM opts: %KPAH_JAVA_OPTS%
call :maybe_launch_admin_app_delayed
if defined DESKTOP_ADMIN_REQUESTED (
  echo Dang mo server voi embedded AdminPanel desktop...
  "%JAVA_BIN%java.exe" %KPAH_JAVA_OPTS% -Djava.awt.headless=false -jar KPAH.jar
) else (
  "%JAVA_BIN%java.exe" %KPAH_JAVA_OPTS% -Djava.awt.headless=true -jar KPAH.jar
)
exit /b %errorlevel%

:ensure_admin_launcher
if not defined ADMIN_APP_REQUESTED exit /b 1
if exist "%ROOT%run_admin_panel_clone.bat" exit /b 0
exit /b 1

:maybe_launch_admin_app_now
if not defined ADMIN_APP_REQUESTED exit /b 0
call :ensure_admin_launcher
if errorlevel 1 (
  echo Canh bao: khong the mo panel admin rieng tu run.bat.
  exit /b 0
)
start "KPAH Admin App" "%ROOT%run_admin_panel_clone.bat"
exit /b 0

:maybe_launch_admin_app_delayed
if not defined ADMIN_APP_REQUESTED exit /b 0
call :ensure_admin_launcher
if errorlevel 1 (
  echo Canh bao: khong the chuan bi panel admin rieng de mo cung run.bat.
  exit /b 0
)
start "KPAH Admin App" powershell -NoProfile -ExecutionPolicy Bypass -WindowStyle Hidden -Command "Start-Sleep -Seconds 8; Start-Process -FilePath '%ROOT%run_admin_panel_clone.bat' -WorkingDirectory '%ROOT%'"
exit /b 0
