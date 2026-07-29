@echo off
setlocal

set "ROOT=%~dp0"
cd /d "%ROOT%"

echo Dang chuyen KPAH Game Server sang che do desktop de mo panel admin goc...
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$svc = Get-Service -Name 'kpah-game' -ErrorAction SilentlyContinue; " ^
  "if ($svc -and $svc.Status -ne 'Stopped') { Stop-Service -Name 'kpah-game' -Force -ErrorAction Stop; Start-Sleep -Seconds 3 }"
if errorlevel 1 (
  echo Khong dung duoc service kpah-game.
  exit /b 1
)

echo Da dung kpah-game service. Dang mo server desktop kem panel admin goc...
start "KPAH Desktop Admin" cmd /k ""%ROOT%run.bat" --desktop-admin --no-admin-app"
exit /b 0
