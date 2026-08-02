# KPAH trên Ubuntu bằng Docker Compose

Bộ này chạy MariaDB, login server, game server và file danh sách máy chủ trong
project Docker riêng `kpah`. TCP `19129` phục vụ game và TCP `18080` chỉ phục
vụ file `NQSH2.txt`; database, login socket, local-admin API và socket nạp tiền
cũ chỉ tồn tại trong mạng Docker nội bộ.

Trang quản trị chạy tại `http://IP_UBUNTU:18081/noi-vu-kpah-73x9/login`.
Container web dùng chung network namespace với game server để gọi local-admin
API qua loopback; token quản trị không được mở ra mạng LAN.

## Thư mục production

```text
/opt/kpah/
  compose.yaml
  data/mariadb/
  runtime/
  server-list/NQSH2.txt
  secrets/db.env
```

`runtime/` phải chứa `dist/KPAH2.jar`, `dist/libs/`, `loginServer/`, `map/`,
`cMap/`, `config/`, `server.ini` và các thư mục log có quyền ghi.

Để cho phép client tự đăng ký mà không bật các đặc quyền kiểm thử của chế độ
local, đặt `sv.registrationEnabled=1` trong `runtime/server.ini`.

## Build game server

Chạy script trong container JDK 8 để không phụ thuộc Java cài trên host:

```bash
docker run --rm \
  -v /opt/kpah/runtime:/workspace \
  -w /workspace \
  eclipse-temurin:8-jdk-jammy \
  bash ops/ubuntu-docker/build-server.sh
```

## Kiểm tra

```bash
cd /opt/kpah
docker compose ps
docker compose logs --tail=200 login game
ss -lnt | grep 19129
```

Backup được chạy mỗi giờ bằng `kpah-backup.timer`, lưu tối đa 14 ngày tại
`/opt/kpah/backups`. Mỗi gói chứa hai database, cấu hình runtime và checksum;
file được tạo với quyền chỉ root đọc.

Không chạy lại các migration `010` đến `012` trên database đã có dữ liệu nếu
chưa xem trước, vì các file này có câu lệnh xóa và nạp lại dữ liệu tĩnh.
