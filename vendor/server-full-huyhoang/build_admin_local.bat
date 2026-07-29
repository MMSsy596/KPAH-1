@echo off
setlocal

set "ROOT=%~dp0"
cd /d "%ROOT%"

set "JAVA_BIN="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\java\resolve_java_bin.ps1" -RepoRoot . -Role build`) do set "JAVA_BIN=%%I"
if not defined JAVA_BIN (
  echo Khong tim thay JDK de build Admin Local.
  exit /b 1
)

set "JAVAC=%JAVA_BIN%javac.exe"
set "JAR=%JAVA_BIN%jar.exe"

if exist build_admin_local rmdir /s /q build_admin_local
mkdir build_admin_local\classes
mkdir dist\admin_local >nul 2>nul

powershell -NoProfile -Command "Get-ChildItem -Recurse -Filter *.java admin_local_src | ForEach-Object { $_.FullName.Substring($pwd.Path.Length + 1) } | Set-Content build_admin_local\\sources.txt"

"%JAVAC%" -encoding UTF-8 -source 1.8 -target 1.8 -d build_admin_local\classes @build_admin_local\sources.txt
if errorlevel 1 exit /b 1

> build_admin_local\manifest.mf (
  echo Manifest-Version: 1.0
  echo Main-Class: adminlocal.AdminLocalApp
)

if exist dist\admin_local\KPAHAdminLocal.jar del /f /q dist\admin_local\KPAHAdminLocal.jar >nul 2>nul
"%JAR%" cfm dist\admin_local\KPAHAdminLocal.jar build_admin_local\manifest.mf -C build_admin_local\classes .
if errorlevel 1 exit /b 1

echo Build OK: dist\admin_local\KPAHAdminLocal.jar
exit /b 0
