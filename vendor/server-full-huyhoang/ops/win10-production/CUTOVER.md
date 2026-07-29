# Cutover Plan

Tai lieu nay dung cho luc chuyen doi trong cua so bao tri. Khong chay ngay khi server dang dong nguoi choi.

## Nguyen tac

- chuan bi stack moi song song
- backup truoc khi dong cong
- bat maintenance truoc khi stop server cu
- doi qua stack moi trong mot lan
- neu fail thi rollback ngay ve stack cu

## Chuan bi truoc ngay bao tri

1. Chay:

```powershell
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\preflight.ps1
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\render-stage.ps1
```

2. Cai rieng:

- Temurin JDK 8
- MariaDB hoac MySQL service
- PHP zip
- Nginx
- WinSW hoac NSSM

3. Tao DB user rieng:

- `kpah_game`
- `kpah_login`
- `kpah_web`

4. Sua password va secret truoc cutover:

- `server.ini`
- `loginServer/server.ini`
- `web/config.local.php`

5. Import hoac mirror DB sang DB service moi neu co doi host/may.
6. Chot danh sach cong public, sau do chay:

```powershell
powershell -ExecutionPolicy Bypass -File .\ops\win10-production\apply-win10-hardening.ps1
```

## Trinh tu cutover

1. Thong bao bao tri trong game.
2. Chay backup bang `tools/backup/hourly_backup.ps1`.
3. Bat maintenance mode de chan dang nhap moi.
4. Save nhan vat.
5. Stop game server cu.
6. Stop login server cu.
7. Start login service moi.
8. Start game service moi.
9. Start PHP FastCGI.
10. Start Nginx.
11. Test:

- login account
- vao map
- doi map
- giao dich
- luu nhan vat
- web register
- admin web

12. Mo server.

## Rollback

1. Stop Nginx/PHP/service moi.
2. Khoi dong lai `run.bat` cu.
3. Khoi dong lai `loginServer\run.bat --allow-direct`.
4. Restore DB tu backup neu cutover co ghi du lieu loi.

## Luu y ky thuat

- Khong public DB port.
- Khong public login port, local admin port, PHP FastCGI port.
- Khong de `root` password rong.
- Khong public `/admin` neu chua co allowlist IP hoac VPN.
- DDoS lon van can lop ngoai nha mang; firewall local chi giam duoc flood nho.
