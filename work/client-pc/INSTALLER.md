# KPAH PC single-file installer

Build a client and wrap the complete Unity directory into one installer:

```powershell
cd C:\Users\Admin\Desktop\KPAH
.\work\client-pc\Build-PcInstaller.ps1 `
  -HostName 192.168.1.88 `
  -Port 19129 `
  -ServerName "KPAH LAN" `
  -AppVersion 1.0.0 `
  -OutputName KPAH-PC-Setup-LAN
```

Output:

```text
work/client-pc/release/KPAH-PC-Setup-LAN.exe
work/client-pc/release/KPAH-PC-Setup-LAN.sha256
```

The player downloads only the setup EXE, installs without Administrator
rights and launches KPAH from the Desktop or Start menu. The installer
contains no database, server configuration, admin token or account password.

The host embedded during the build must be reachable from the player's PC:

- same LAN: use the server PC's LAN address;
- Tailscale: use the server PC's Tailscale address, and authorize the player
  in the same tailnet;
- public Internet: use a public IP or DNS name and forward TCP `19129` from
  the router to the server PC.

Rebuild the installer whenever the server address or client files change.
