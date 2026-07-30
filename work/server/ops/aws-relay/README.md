# KPAH AWS relay operations

Bo script nay duy tri reverse SSH tunnel tu PC game server den AWS relay. SSH bi mat
ket noi se duoc khoi dong lai sau vai giay; Task Scheduler co the khoi dong supervisor
ngay khi Windows boot.

## Lenh van hanh

Chay tai thu muc goc `KPAH`:

```powershell
.\work\server\ops\aws-relay\Get-KpahServerStatus.ps1
.\work\server\ops\aws-relay\Start-KpahTunnel.ps1
.\work\server\ops\aws-relay\Stop-KpahTunnel.ps1
```

Lan dau, `Start-KpahTunnel.ps1` tao file local
`local-config\kpah-tunnel.json` tu file mau. Thu muc nay da duoc `.gitignore`.

De tunnel tu chay sau khi Windows khoi dong, mo PowerShell bang **Run as
administrator**:

```powershell
.\work\server\ops\aws-relay\Install-KpahTunnelStartup.ps1
```

Trang thai `Hourly backup` chi xanh khi task backup da duoc cai. Sau khi dat
`KPAH_GOOGLE_BACKUP_ROOT` hoac dam bao Google Drive nam o duong dan mac dinh,
chay trong PowerShell Administrator:

```powershell
.\work\server\tools\backup\install_hourly_backup_task.ps1
```

Private key chi o `runtime-local/ssh-tunnel`, khong duoc commit. Log supervisor:
`runtime-local/ssh-tunnel/logs/tunnel-supervisor.log`.

## VPS can giu

- OpenSSH tren TCP 22.
- `GatewayPorts clientspecified` de public reverse-forward tren TCP 19129.
- Security Group chi mo TCP 22 cho IP quan tri va TCP 19129 cho nguoi choi.
- Gan Elastic IP cho EC2 de dia chi client khong thay doi.
- MariaDB, login port 8023 va local-admin port 18023 khong mo ra Internet.
- FRP port 7000 co the tat khi SSH relay da on dinh.
