# Quy ước phát hành client

Mọi thay đổi ảnh hưởng chức năng người chơi phải được kiểm tra và phát hành đồng thời cho hai client:

1. PC Unity: build bằng `work/client-pc/Build-LocalPcClient.ps1`.
2. Java J2ME: build bằng `work/client-j2me/Build-LocalClient.ps1` và kiểm tra bằng FreeJ2ME.

Chỉ xem một phiên bản là hoàn thành khi:

- Hai client cùng có hành vi nghiệp vụ tương đương, trừ khi có giới hạn nền tảng được ghi rõ.
- Cả hai gói đều build thành công và có SHA-256.
- Cả hai client đều qua kiểm tra khởi động.
- Các URL tải trong mạng LAN trả về HTTP 200.
- Tên phiên bản PC và Java được tăng đồng bộ.
