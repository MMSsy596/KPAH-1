@echo off
setlocal
cd /d "C:\Users\Administrator\Downloads\SEVER-KPAH-FULL\ops\win10-production\staging"
"C:\php\php-cgi.exe" -b 127.0.0.1:9072 -c "C:\php\php.ini"
