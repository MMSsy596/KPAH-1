@echo off
setlocal
cd /d "C:\Users\Administrator\Downloads\SEVER-KPAH-FULL\loginServer"
"C:\Program Files\Java\jdk-23\bin\java.exe" -Xms256m -Xmx512m -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 -jar "C:\Users\Administrator\Downloads\SEVER-KPAH-FULL\loginServer\CheckLoginSocket.jar"
