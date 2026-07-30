@echo off
setlocal

set "ROOT=%~dp0"
cd /d "%ROOT%"

set "CLASSPATH=libs\\jxl-2.6.jar;libs\\mysql-connector-java-5.1.49.jar;libs\\NQSH_5h.jar"
set "JAVA_BIN="
for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\java\resolve_java_bin.ps1" -RepoRoot . -Role build`) do set "JAVA_BIN=%%I"
if not defined JAVA_BIN (
  echo Khong tim thay javac.exe
  exit /b 1
)
set "JAVAC=%JAVA_BIN%javac.exe"
set "JAR=%JAVA_BIN%jar.exe"
if not exist "%JAR%" (
  echo Khong tim thay jar.exe
  exit /b 1
)

set "ROOT_JAR_CURRENT=0"
if exist KPAH.jar (
  for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "$jar = Get-Item 'KPAH.jar' -ErrorAction SilentlyContinue; $latestSrc = Get-ChildItem -Recurse -Filter *.java 'src' | Sort-Object LastWriteTimeUtc -Descending | Select-Object -First 1; if ($jar -and $latestSrc -and $jar.LastWriteTimeUtc -ge $latestSrc.LastWriteTimeUtc) { '1' } else { '0' }"`) do set "ROOT_JAR_CURRENT=%%I"
)

if "%ROOT_JAR_CURRENT%"=="1" (
  echo Khong co thay doi source, dung lai KPAH.jar hien tai.
) else (
  if exist build rmdir /s /q build
  mkdir build\\classes

  powershell -NoProfile -Command "Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName.Substring($pwd.Path.Length + 1) } | Set-Content build\\sources.txt"

  "%JAVAC%" -encoding UTF-8 -source 1.8 -target 1.8 -cp "%CLASSPATH%" -d build\\classes @build\\sources.txt
  if errorlevel 1 exit /b 1

  > build\\manifest.mf (
    echo Manifest-Version: 1.0
    echo Main-Class: server.TeamServer
    echo Class-Path: libs/jxl-2.6.jar libs/mysql-connector-java-5.1.49.jar libs/NQSH_5h.jar
  )

  if exist build\\KPAH.jar del /f /q build\\KPAH.jar >nul 2>nul
  "%JAR%" cfm build\\KPAH.jar build\\manifest.mf -C build\\classes .
  if errorlevel 1 exit /b 1

  call :sync_runtime_artifacts build\\KPAH.jar
  if errorlevel 1 exit /b 1

  copy /y build\\KPAH.jar KPAH.jar >nul
  if errorlevel 1 (
    echo KPAH.jar dang duoc process khac su dung. dist\\KPAH2.jar da duoc cap nhat, hay restart server desktop de dong bo KPAH.jar.
    exit /b 1
  )
)

call :sync_runtime_artifacts KPAH.jar
if errorlevel 1 exit /b 1

echo Build OK: KPAH.jar va dist\\KPAH2.jar
exit /b 0

:sync_runtime_artifacts
set "SOURCE_JAR=%~1"
if not defined SOURCE_JAR set "SOURCE_JAR=KPAH.jar"

if not exist "%SOURCE_JAR%" (
  echo Khong tim thay %SOURCE_JAR% de dong bo runtime artifact.
  exit /b 1
)

if not exist dist mkdir dist
if not exist dist\\libs mkdir dist\\libs

copy /y "%SOURCE_JAR%" dist\\KPAH2.jar >nul
if errorlevel 1 (
  echo Khong the cap nhat dist\\KPAH2.jar
  exit /b 1
)

for %%F in (libs\\*.jar) do (
  copy /y "%%F" "dist\\libs\\%%~nxF" >nul
  if errorlevel 1 (
    echo Khong the dong bo thu vien %%~nxF vao dist\\libs
    exit /b 1
  )
)

exit /b 0
