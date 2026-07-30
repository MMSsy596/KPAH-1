@echo off
setlocal

set "ROOT=%~dp0"
cd /d "%ROOT%"

call build_admin_local.bat
if errorlevel 1 exit /b 1

set "JPACKAGE="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "$tool = Get-ChildItem 'C:\\Program Files\\Java\\jdk-*\\bin\\jpackage.exe' -ErrorAction SilentlyContinue | Sort-Object FullName -Descending | Select-Object -First 1; if ($tool) { Write-Output $tool.FullName }"`) do set "JPACKAGE=%%I"
if not defined JPACKAGE (
  echo Khong tim thay jpackage.exe
  exit /b 1
)

if exist dist\admin_local_pkg rmdir /s /q dist\admin_local_pkg
mkdir dist\admin_local_pkg

"%JPACKAGE%" --type app-image --name "KPAH Admin Local" --input "%ROOT%dist\admin_local" --main-jar "KPAHAdminLocal.jar" --main-class adminlocal.AdminLocalApp --dest "%ROOT%dist\admin_local_pkg\app-image" --vendor "KPAH" --java-options "-Dfile.encoding=UTF-8"
if errorlevel 1 exit /b 1

"%JPACKAGE%" --type exe --name "KPAH Admin Local" --input "%ROOT%dist\admin_local" --main-jar "KPAHAdminLocal.jar" --main-class adminlocal.AdminLocalApp --dest "%ROOT%dist\admin_local_pkg\installer" --vendor "KPAH" --app-version "1.0.0" --win-dir-chooser --win-shortcut --java-options "-Dfile.encoding=UTF-8"
if errorlevel 1 (
  echo Khong dong goi duoc installer exe vi may chua co WiX. App-image voi file KPAH Admin Local.exe da duoc tao san.
  exit /b 0
)

echo Da dong goi xong app-image va installer exe trong dist\admin_local_pkg
exit /b 0
