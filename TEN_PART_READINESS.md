# KPAH - trạng thái 10 phần sẵn sàng chơi

Ngày kiểm tra: 2026-07-30

## Kết quả hiện tại

| # | Hạng mục | Trạng thái | Bằng chứng |
| --- | --- | --- | --- |
| 1 | Client PC chạy trực tiếp, không cần J2ME | ĐẠT | Đã build và mở `work/client-pc/dist/KPAH-PC/KPAH_276.exe`; client được vá về `127.0.0.1:19129`, không còn host cũ trong assembly. |
| 2 | Giết quái nhận EXP và lưu EXP | ĐẠT | Regression hai client ghi nhận XP thật: `1adgjm` từ 0 lên 17 rồi 66; `abc1` từ 0 lên 47. XP giữ nguyên sau logout và restart game/login server. |
| 3 | Rơi đồ, nhặt và lưu | ĐẠT | Endpoint regression gọi đúng `Monster.onDropItem` và luồng nhặt bình thường. Kết quả: `spawned=1`, `picked=1`, `potion_delta=1`; dữ liệu potion được lưu DB. |
| 4 | Nhiệm vụ đầu game | ĐẠT phạm vi đầu game | Đã nhận nhiệm vụ đầu bằng logic `OnQuest`; DB lưu trạng thái `isfinish=0,2,0`. Chưa tuyên bố toàn bộ 49 mẫu quest đều hoàn thiện. |
| 5 | Hai tài khoản chơi đồng thời | ĐẠT | Báo cáo `runtime-local/regression/20260730-112307/REPORT.md` có `Both clients in game: True` và `Concurrent game connections: 2`. |
| 6 | Party, trade và PvP cơ bản | ĐẠT | Cùng báo cáo trên ghi `ok=1` và `party=true,trade=true,pvp=true`. Party dùng luồng mời/tham gia thật, trade dùng handshake/chốt thật, PvP đi qua `Map.doAttackPlayer`. |
| 7 | Restart không mất dữ liệu | ĐẠT | EXP, potion và quest được đối chiếu trước/sau logout, sau đó dừng và mở lại game/login server; dữ liệu giữ nguyên. |
| 8 | Khóa các cổng nội bộ | CHỜ QUYỀN ADMIN | Admin HTTP và server-list chỉ bind loopback. Script firewall đã được sửa để chỉ mở cổng game và chặn `8023`, `18023`, `18080`, `9072`, `3306`, nhưng Windows từ chối áp dụng vì PowerShell hiện không chạy Administrator. |
| 9 | Backup và khôi phục thử | ĐẠT | Backup local hoàn thành tại `runtime-local/backups/latest/kpah_server_latest.zip`. Restore sang schema tạm, kiểm tra 2 bảng account, 69 bảng game, 8 account, 1257 nhân vật, 13 quest rồi xóa schema tạm: PASS. |
| 10 | Soak test dài hạn | ĐANG CHẠY | Bài soak 6 giờ bắt đầu `2026-07-30 11:21:03` tại `runtime-local/soak/20260730-112103`. Mẫu đầu: DB/login/game/admin đều đạt, PID game `11160`, không có dòng lỗi được nhận diện. |

## Công cụ regression đã thêm

- `work/server/tools/regression/Run-GameplayRegression.ps1`: tạo mật khẩu test ngẫu nhiên, chạy hai client headless, kiểm tra đăng nhập đồng thời, snapshot DB trước/sau và tự dọn tiến trình.
- `work/server/tools/regression/Start-SoakTest.ps1`: lấy mẫu DB/login/game/admin, PID, RAM, số người online và lỗi log.
- `work/server/tools/backup/Test-BackupRestore.ps1`: khôi phục backup vào schema cô lập, kiểm tra số bảng/dòng rồi dọn schema.
- Local admin regression chỉ nghe `127.0.0.1`, yêu cầu token và không mở cho máy ngoài.

## Việc cần hoàn tất sau mốc này

1. Mở PowerShell bằng **Run as administrator**, chạy:

   ```powershell
   cd C:\Users\Admin\Desktop\KPAH\work\server
   .\ops\win10-production\apply-win10-hardening.ps1
   ```

2. Sau 17:21 ngày 2026-07-30, xem:

   ```powershell
   Get-Content C:\Users\Admin\Desktop\KPAH\runtime-local\soak\20260730-112103\REPORT.md
   ```

Chỉ khi firewall được áp dụng thành công và báo cáo soak kết thúc với `PASS: True` mới được đánh dấu đủ 10/10 ở mức vận hành.
