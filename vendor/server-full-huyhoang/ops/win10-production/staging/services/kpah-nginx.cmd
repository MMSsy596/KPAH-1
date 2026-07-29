@echo off
setlocal
cd /d "C:\nginx-1.30.0"
"C:\nginx-1.30.0\nginx.exe" -p "C:\nginx-1.30.0\" -c conf/nginx.conf
