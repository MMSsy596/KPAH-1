# Win10 Production Staging

Bo nay dung de chuan bi mot stack van hanh moi song song voi server hien tai tren Windows 10.
Muc tieu la:

- khong sua `run.bat` dang chay
- khong cat chuyen ngay
- tao san config, wrapper, va checklist de den luc bao tri moi doi stack

## Pham vi

Bo staging nay chuan bi:

- preflight report de kiem tra Java, PHP, Nginx, MySQL/MariaDB
- stage folder cho Nginx + PHP web
- wrapper cmd cho game server, login server, web PHP, Nginx
- mau file `WinSW` de sau nay dang ky thanh Windows Service
- script hardening firewall cho Windows 10
- checklist cutover de doi trong cua so bao tri

## Cach dung

1. Kiem tra hien trang:

```powershell
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\preflight.ps1
```

2. Tao bo stage:

```powershell
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\render-stage.ps1
```

3. Doc checklist:

- `ops/win10-production/CUTOVER.md`
- `ops/win10-production/staging/docs/summary.txt`

4. Khoa cac cong noi bo tren Windows 10:

```powershell
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\apply-win10-hardening.ps1
```

## Ghi chu

- Stage folder sinh ra duoi `ops/win10-production/staging`.
- Cac script trong day khong tu dong stop server hien tai.
- Muon cutover that su, dung cua so bao tri rieng va chay theo `CUTOVER.md`.
- Rollback firewall: `ops/win10-production\rollback-win10-hardening.ps1`.
