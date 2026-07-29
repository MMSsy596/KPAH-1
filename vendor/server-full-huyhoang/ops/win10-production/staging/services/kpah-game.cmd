@echo off
setlocal
cd /d "C:\Users\Administrator\Downloads\SEVER-KPAH-FULL"
"C:\Program Files\Java\jdk-23\bin\java.exe" -Xms4096m -Xmx4096m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 -jar "C:\Users\Administrator\Downloads\SEVER-KPAH-FULL\KPAH.jar"
