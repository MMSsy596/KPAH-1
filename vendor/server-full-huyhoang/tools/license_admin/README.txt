KPAH license admin

1. Build app admin:
   powershell -ExecutionPolicy Bypass -File tools/license_admin/build_kpah_license_admin.ps1

2. Exe sau khi build:
   tools/license_admin/out/app/KPAH_License_Admin/KPAH_License_Admin.exe

3. Cach dung nhanh:
   - Mo exe admin tren may chu
   - Tool tu doc `server.ini` de dien san Server URL va Admin token neu chay cung may chu
   - Neu can, sua lai Server URL va token thu cong
   - Nhap so ngay
   - Bam Tao ma
   - Bam Sao chep ma roi gui lai cho player

4. Neu van muon cap ma bang script:
   powershell -ExecutionPolicy Bypass -File tools/license_admin/issue_kpah_license.ps1 -ServerUrl http://127.0.0.1:18023 -Days 30

Luu y:
- Khong can private key nua. Server la noi tao va giu trang thai license.
- Ban portable cua player chi can launcher/manager, khong can admin exe nay.
