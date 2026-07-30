KPAH hourly backup

Files:
- hourly_backup.ps1
- install_hourly_backup_task.ps1

What is backed up each run:
- MySQL database "kpah2" from server.ini
- MySQL database "account" from loginServer/server.ini
- Server files and folders:
  - config
  - dist
  - web
  - loginServer
  - src
  - libs
  - lib
  - map
  - portable_release\KPAH_Auto_Portable
  - build\classes
  - root files like server.ini, KPAH.jar, run_server.bat, build.xml
- Standalone runtime config files if present:
  - C:\mysql\bin\my.ini
  - C:\nginx-1.30.0\conf\nginx.conf
  - C:\php\php.ini
- Retired XAMPP config files if present:
  - C:\xampp_retired_20260426\mysql\bin\my.ini
  - C:\xampp_retired_20260426\apache\conf\httpd.conf
  - C:\xampp_retired_20260426\apache\conf\extra\httpd-vhosts.conf
  - C:\xampp_retired_20260426\php\php.ini

Default Google Drive target:
- G:\My Drive\KPAH_Server_Backups

Manual run:
1. Open PowerShell as Administrator.
2. Run:
   powershell -ExecutionPolicy Bypass -File "C:\Users\Administrator\Desktop\SEVER-KPAH-FULL\tools\backup\hourly_backup.ps1"

Install hourly task:
1. Open PowerShell as Administrator.
2. Run:
   powershell -ExecutionPolicy Bypass -File "C:\Users\Administrator\Desktop\SEVER-KPAH-FULL\tools\backup\install_hourly_backup_task.ps1"

Restore:
1. Download the newest zip from Google Drive:
   KPAH_Server_Backups\latest\kpah_server_latest.zip
2. Extract it to a new machine.
3. Restore SQL files in the "databases" folder into MySQL.
4. Copy back server files from the "server_files" folder.

Notes:
- Do not sync C:\mysql\data directly while MySQL is running.
- Sync the generated backup zip files instead.
- If your Google Drive path is different, run the scripts with:
  -BackupRoot "YOUR_GOOGLE_DRIVE_PATH"
