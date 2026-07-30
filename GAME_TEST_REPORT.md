# Báo cáo kiểm thử game KPAH

Ngày kiểm thử: 2026-07-29  
Môi trường: Windows, JDK 8u492, MariaDB 10.4.32, FreeJ2ME-Plus đã bổ sung socket thật  
Tài khoản thử: tài khoản local ID 6; nhân vật `1adgjm`, ID 60673, cấp 1  
Phạm vi: kiểm thử thủ công như một người chơi mới, kiểm tra DB/log, đóng mở client và khởi động lại login + game server.

## Cập nhật tiến độ 2026-07-30

Phần nội dung chi tiết bên dưới là ảnh chụp kết quả của lượt kiểm thử ban đầu.
Sau báo cáo đó, các blocker P0/P1 đã tiến triển như sau:

- **Đăng ký local: PASS.** Client có thể tạo tài khoản local và đăng nhập bằng
  tài khoản vừa tạo.
- **Kỹ năng đánh cơ bản của nhân vật mới: ĐÃ SỬA.** Dữ liệu khởi tạo và đường
  nạp nhân vật giữ/khôi phục kỹ năng số 0 thay vì để hotbar không thể đánh.
- **Combat hai chiều và kill: PASS.** Nhân vật mới gây sát thương thực tế
  (`-11` trong lượt quan sát), hạ được Nhím cấp 1, nhận popup quà; quái phản
  công, nhân vật có thể chết và tự hồi sinh đúng thời gian đếm ngược.
- **EXP solo: ĐÃ SỬA MÃ NGUỒN, CHỜ XÁC NHẬN RUNTIME.** Sau kill, EXP vẫn bằng
  0 vì `calculatorXpParty` truy cập `p.party.userParty` khi người chơi solo có
  `p.party == null`. Exception bị catch rỗng nên kill vẫn hiện nhưng phần cộng
  EXP bị bỏ qua. Đã thêm null guard trả EXP trực tiếp cho người chơi solo và
  build server thành công.
- **Bốn bảng runtime thiếu: ĐÃ BỔ SUNG.** Migration
  `013_add_character_login_runtime_tables.sql` tạo các bảng cần cho luồng chọn
  nhân vật/tài khoản.
- **Quan sát runtime: ĐÃ BỔ SUNG.** Local admin API trả thêm level, EXP, phần
  nghìn EXP, HP/MP hiện tại và tối đa để regression không phải suy đoán từ HUD.
- **Còn chờ:** đăng nhập lại client sau restart, kill một quái để xác nhận EXP
  live, đóng client để xác nhận EXP lưu DB, rồi kiểm tra drop/nhặt đồ. Client
  hiện có một lần kẹt ở popup “Xin chờ...” sau khi các dịch vụ được khởi động
  lại; chưa được đánh dấu là lỗi cố định cho tới khi tái hiện và tìm nguyên nhân.

## Kết luận ngắn

Game hiện ở mức **playable alpha**: đã đăng nhập, tạo nhân vật, vào làng, di chuyển, tương tác NPC, mua/dùng bình máu, chuyển sang bản đồ quái, bị tấn công, chết, hồi sinh và lưu/nạp lại nhân vật được.

Game **chưa đủ tính năng và chưa sẵn sàng phát hành**. Vòng chơi cốt lõi đang bị chặn ở chiến đấu: trong lần test cấp 1, các thao tác đánh không làm thanh HP quái giảm, nên chưa chứng minh được giết quái, nhận EXP và nhặt đồ. Ngoài ra, mỗi lần chọn nhân vật server phát sinh bốn lỗi thiếu bảng DB; nhiều hệ thống nâng cao mới chỉ có mã nguồn/dữ liệu nền, chưa được kiểm chứng end-to-end.

## Bản build đã kiểm thử

| Thành phần | SHA-256 |
| --- | --- |
| Game server `KPAH.jar` | `C88F1861C38AE3266A988A76EBD14A1B643BDA406EA99BCADD6D866D5C4953EF` |
| Client local `grinding2-local.jar` | `2F734B001E47AE5700C0C21342EC7B55E99ED5F6CA08606C8E91858B478376AB` |
| Emulator socket thật `freej2me-network.jar` | `BF67DBFBEE198E07D1762F080A845491BA4CAEC523D3B4019E9636B6617D9B98` |

Các dịch vụ sau bài test đang chạy ở localhost: MariaDB `3306`, login `8023`, game `19129`, local admin `18023`, danh sách server HTTP `18080`.

## Kết quả kiểm thử chi tiết

Ký hiệu:

- **PASS**: đã thao tác thực tế và có kết quả đúng.
- **PARTIAL**: chạy được một phần hoặc chưa xác nhận toàn bộ kết quả.
- **FAIL**: đã tái hiện lỗi hoặc kết quả không đúng.
- **CODE/DATA ONLY**: thấy mã nguồn/dữ liệu nhưng chưa đủ bằng chứng chạy thực tế.
- **MISSING/DISABLED**: chưa có, thiếu dữ liệu bắt buộc hoặc đang chủ động tắt.

### 1. Khởi động, kết nối và tài khoản

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| MariaDB | PASS | Bind `127.0.0.1:3306`, game server duy trì các kết nối DB. |
| Login server | PASS | Nghe cổng `8023`, xác thực tài khoản local thành công. |
| Game server | PASS | Nghe cổng `19129`, nạp template/map và nhận client. |
| Kết nối J2ME thật | PASS | Có TCP `ESTABLISHED` từ FreeJ2ME tới `127.0.0.1:19129`; không còn socket giả. |
| Danh sách máy chủ local | PASS | Client tải và chọn được `khí phách grinding` qua HTTP local. |
| Đăng nhập đúng tài khoản/mật khẩu | PASS | Vào được màn hình chọn nhân vật. |
| Tạo nhân vật | PASS | Tạo `1adgjm`, ID 60673; nhân vật xuất hiện lại sau đóng/mở client. |
| Chọn nhân vật | PASS có lỗi nền | Vào game được, nhưng phát sinh bốn SQL exception thiếu bảng ở mục “Lỗi đã xác nhận”. |
| Đóng/mở client | PASS | Sau đóng FreeJ2ME và mở lại, tài khoản và nhân vật vẫn nạp được. |
| Restart login + game server | PASS | Dừng và khởi động lại hai dịch vụ, sau đó đăng nhập/chọn nhân vật thành công. |
| Nhớ mật khẩu | Chưa test | Checkbox có trên UI nhưng không được bật vì không cần lưu mật khẩu test. |
| Đăng ký tài khoản từ client | Chưa test | Tài khoản local đã có sẵn; chưa kiểm tra quy trình tự đăng ký. |

### 2. Nhân vật, giao diện và chỉ số

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| HUD HP/MP/cấp độ | PASS | Hiển thị đúng nhân vật cấp 1, thanh HP/MP thay đổi trong chiến đấu. |
| Hành trang | PASS một phần | Mở được lưới hành trang 1/1; hành trang thường đang trống. Bình máu thuộc vùng potion/quick slot riêng. |
| Kỹ năng | PASS một phần | Màn hình có 5 biểu tượng kỹ năng, điểm kỹ năng 0; chưa tăng điểm do không lên cấp được. |
| Tiềm năng | PASS | Hiển thị: điểm cộng 0; Sức mạnh 25, Khéo léo 20, Tinh thần 10, Sức khỏe 25, May mắn 10. |
| Trang bị | CODE/DATA ONLY | Menu và blob DB `equip` có dữ liệu, nhưng chưa hoàn tất kiểm tra mặc/tháo/đổi từng món. |
| Thông tin nhân vật | CODE/DATA ONLY | Menu có mục “Thông tin”; chưa kiểm tra toàn bộ trường hiển thị. |
| Quick slots | PASS một phần | Có kỹ năng/tấn công, bình HP, bình MP; dùng bình HP thực tế làm thanh HP tăng. |
| Bàn phím/Thay đồ/Tìm bạn | PASS menu | Menu nhanh trên biểu tượng bàn phím mở được, có đủ ba mục. Chưa test chức năng sâu. |
| Cấu hình đồ họa | PASS menu | Có Thấp, Vừa, Cao, Rất thấp; menu mở/đóng bình thường. |
| Bản đồ lớn, giao diện, hướng dẫn, âm thanh | PASS menu | Các mục tồn tại trong Cài đặt; chưa đo tác động của từng lựa chọn. |

### 3. Hướng dẫn, NPC, bản đồ và di chuyển

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| Chuỗi hội thoại mở đầu | PASS | Hội thoại ông/bà và hướng dẫn tìm NPC chạy được. |
| NPC Thầy Ngũ Hành | PASS menu | Tương tác được; thấy Đổi hệ ngũ hành, Tháo ngọc khảm, Đặt mật khẩu rương, Vòng quay. |
| NPC/cổng thành | PASS một phần | Hội thoại và menu Tiên Du hoạt động. Tên “Vào”, “Dương đồng” và chỉ dẫn cổng khó hiểu với người chơi mới. |
| Di chuyển 4 hướng | PASS | Phím số sau bản vá giữ phím và chạm để tự chạy đều di chuyển nhân vật. |
| Chạm tìm đường | PASS | Chạm lên bản đồ đặt mũi tên đích và nhân vật tự chạy; có thể bị địa hình/NPC chặn. |
| Minimap | PASS | Hiển thị nhân vật, NPC/điểm bản đồ và cập nhật khi di chuyển. |
| Chuyển bản đồ | PASS | Đã đi từ map làng 70 sang khu Nam Tiên Du (runtime map 343), rồi dùng cổng Dương đồng quay về map 70. |
| Dữ liệu bản đồ | Có dữ liệu đáng kể | 99 file cMap số, 123 file cMap tổng cộng và 64 file nhị phân map. |
| Trải nghiệm tìm lối ra | PARTIAL | Cổng không được giải thích rõ; phải chọn vùng Tiên Du đúng và né NPC. Đây là vấn đề onboarding/UX, không phải thiếu map. |
| Prompt tự làm nhiệm vụ | FAIL UX/state | Câu hỏi “hệ thống tự động làm nhiệm vụ” xuất hiện lại sau mỗi lần đăng nhập/chọn nhân vật thay vì nhớ lựa chọn. |

### 4. Cửa hàng, vật phẩm và kinh tế

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| Danh sách cửa hàng | PASS | Shop có icon, mô tả và giá; đã xem Vé giờ vàng và gói 200 bình HP vừa. |
| Mua gói bình HP | PASS một phần | Nhấn Mua nhận popup “Bạn đã nhận được quà”; DB `potion` tăng/được lưu (332 byte) và bình dùng được sau đó. |
| Trừ tiền/giá | CẦN KIỂM TRA | Gold vẫn là 100000 sau phiên test. Có thể gói là quà/giá khác loại tiền; cần xác nhận quy tắc thay vì kết luận mất tiền. |
| Dùng bình HP | PASS | Trong lúc bị quái đánh, dùng quick slot HP làm thanh máu tăng. |
| Bình MP | Chưa test | Có quick slot nhưng chưa tạo tình huống thiếu MP. |
| Nhặt vật phẩm | BLOCKED | Chưa giết được quái nên chưa kiểm chứng drop/pickup. |
| Bán vật phẩm | Chưa test | Chưa có item thường để bán. |
| Nạp Xu | MISSING/DISABLED | Tích hợp thanh toán/provider ngoài đang bị stub/tắt; bảng `board_naptien` còn thiếu. Không phải chức năng usable. |

### 5. Nhiệm vụ và tiến trình

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| Menu Nhiệm vụ | PASS giao diện | Mở được, nhưng hiển thị “Chưa nhận nhiệm vụ”. |
| Dữ liệu nhiệm vụ tĩnh | Có dữ liệu | `data_quest`: 49 dòng. |
| Dữ liệu quest của nhân vật | Có nhưng lệch UI | DB có 1 dòng `tob_char_quest` cho nhân vật 60673, trong khi client báo chưa nhận nhiệm vụ. |
| Nhận/hoàn thành/trả quest | FAIL/BLOCKED | Chưa có quest theo dõi được trên client; chưa thể hoàn thành vòng quest. |
| EXP/lên cấp | FAIL/BLOCKED | Nhân vật vẫn `Lv 1 + 0.0%`, XP DB = 0 vì chưa hạ được quái. |
| Cộng điểm sau lên cấp | BLOCKED | Chưa có EXP nên không thể test tăng tiềm năng/kỹ năng. |

### 6. Chiến đấu, chết và hồi sinh

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| Quái xuất hiện | PASS | Thấy Nhím 200/200, Sâu 300/300 và nhiều quái trên bản đồ. `data_monster`: 138 dòng. |
| Quái chọn mục tiêu/tấn công | PASS | Quái áp sát, gây nhiều mức sát thương; HP nhân vật giảm đúng trên HUD. |
| Đòn đánh/kỹ năng cơ bản | **FAIL quan trọng** | Đã chọn quái, bấm phím 5 và chạm icon kiếm nhiều lần. Có animation/MISS nhưng thanh mục tiêu vẫn 200/200 hoặc 300/300; không xác nhận được sát thương lên quái. |
| Giết quái | BLOCKED | Không hạ được mục tiêu trong bài test cấp 1. |
| EXP và loot | BLOCKED | Không có kill nên XP vẫn 0 và chưa có loot. |
| Chết | PASS | HP về 0, sprite chết và hiện “Hồi sinh sau N”. |
| Hồi sinh tự động | PASS | Đếm ngược xong nhân vật sống lại; đã tái hiện nhiều lần. |
| Quay về làng sau khi chết | PASS | Vẫn dùng được cổng và quay lại map 70; đăng nhập lại cũng vào làng. |
| PvP/Đồ sát | CODE/DATA ONLY | Menu/mã xử lý có, nhưng không có người chơi thứ hai để test sát thương, PK point và hình phạt. |

### 7. Lưu dữ liệu và phục hồi

| Hạng mục | Trạng thái | Bằng chứng/kết quả |
| --- | --- | --- |
| Lưu nhân vật | PASS | `lastLog` cập nhật `2026-07-29 20:28:01`, `totalTimePlay` tăng, potion/skill/equip có blob lưu. |
| Lưu danh tính/cấp/tiền | PASS | ID, tên, userId, cấp 1, 100000 gold, 0 lượng được nạp lại. |
| Lưu potion | PASS | Blob potion 332 byte còn sau đóng/mở; bình HP đã sử dụng thực tế. |
| Lưu quest | PARTIAL | Có dòng quest nhưng UI không phản ánh nhiệm vụ đang nhận. |
| Lưu vị trí | Theo thiết kế hiện tại/không giữ map | DB lưu `map=-1, x=0, y=0`; đăng nhập lại đưa về map làng 70. Nếu yêu cầu tiếp tục đúng vị trí trước logout thì tính năng này chưa có. |
| Sau restart dịch vụ | PASS | Login + game server mới khởi động sạch, nhân vật vẫn đăng nhập/nạp được. MariaDB không cần reset để kiểm tra vòng này. |

## Lỗi đã xác nhận

### P0/P1 – chặn vòng gameplay

1. **Nhân vật cấp 1 chưa gây được sát thương xác nhận lên quái.** Mục tiêu luôn giữ 200/200 hoặc 300/300 sau nhiều lần bấm/chạm đòn cơ bản; có MISS và nhân vật tiếp tục nhận sát thương. Điều này chặn kill, EXP, loot, lên cấp và phần lớn nội dung sau đó.
2. **Bốn bảng DB thiếu mỗi lần chọn nhân vật:**
   - `kpah2.board_naptien`
   - `kpah2.board_created`
   - `kpah2.5h_notify`
   - `kpah2.team_user`

   Client vẫn vào game vì các exception bị nuốt/đi tiếp, nhưng các bảng liên quan bảng tin nạp tiền, thông báo, dữ liệu tạo mới/provider không hoạt động đúng.

### P1/P2 – logic và trải nghiệm

3. **Quest lệch giữa client và DB:** client báo “Chưa nhận nhiệm vụ”, DB đã có một dòng quest cho nhân vật.
4. **Prompt tự làm nhiệm vụ lặp lại ở mọi lần đăng nhập**, không nhớ lựa chọn “Không”.
5. **Cổng/bản đồ đầu khó hiểu:** lối ra không có hướng dẫn rõ, NPC tên “Vào”, lựa chọn Tiên Du/Dương đồng không giải thích level phù hợp. Người chơi cấp 1 có thể vào vùng quái rồi chết liên tục.
6. **Luồng bảo trì cũ vẫn gọi HTTP bên ngoài/local endpoint không có dịch vụ**, tạo `java.net.ConnectException` trong `BaoTriDaily.run` theo chu kỳ.
7. **Client ghi `EOFException` khi kết nối bị đóng**, dù sau đó mở lại bình thường. Đây là lỗi log/teardown cần xử lý gọn.
8. **Log console bị sai mã hóa tiếng Việt (mojibake)**, làm vận hành và đọc lỗi khó khăn.

## Hệ thống có mã nguồn/dữ liệu nhưng chưa được chứng minh hoàn thiện

Các mục dưới đây không được đánh dấu “hoàn thiện” chỉ vì có class hoặc bảng DB. Chúng cần ít nhất hai tài khoản, nhân vật có level/item hoặc lịch sự kiện để test end-to-end.

| Hệ thống | Bằng chứng hiện có | Đánh giá hiện tại |
| --- | --- | --- |
| Bạn bè/Tìm bạn | `tob_friendlist` có 2 dòng toàn DB; nhân vật test có 0; menu “Tìm bạn” có | CODE/DATA ONLY |
| Tổ đội | Nhiều nhánh xử lý party trong server | CODE ONLY, cần 2 client |
| Bang hội | `MapClan`, `tob_clan`, `tob_clan_msg`; dữ liệu clan hiện 0 | CODE ONLY/EMPTY DATA |
| Giao dịch trực tiếp | Class `doTrade` | CODE ONLY, cần 2 client và item |
| Chợ/market | `tob_market` có 3 dòng | DATA/CODE ONLY |
| Đấu giá | `tob_dau_gia` và `tob_dau_gia2` mỗi bảng có 1 dòng | DATA/CODE ONLY |
| Boss | Nhiều class Boss; log server spawn Boss Thương Lượng/thỏ điên | SERVER SPAWN PASS, PLAYER FLOW chưa test |
| Chiến trường/MOBA | `MapChienTruongMoba`; bảng chiến trường hiện 0 | CODE ONLY |
| Lôi đài/Thách đấu | `MapLoiDai`; `tob_char_thach_dau` hiện 0 | CODE ONLY |
| Pet/Thú nuôi | `Pet.java`, `tob_pet` có 12 dòng, `tob_animal` có 39 | CODE/DATA ONLY |
| Farm | `tob_farm` hiện 0 | EMPTY/UNVERIFIED |
| Kết hôn | `Wedding.java`, `tob_wedding` hiện 0 | CODE ONLY |
| Vận tiêu | `tob_vantieu` có 3 dòng | DATA/CODE ONLY |
| Sự kiện/săn boss | `tob_event` có 1 dòng; server log bắt đầu sự kiện săn mãnh thú | SERVER SCHEDULE PASS, REWARD FLOW chưa test |
| Gift code/quà | Bảng liên quan có nhưng nhiều bảng 0; tài liệu Phase 8 chưa bật | NOT RESTORED |
| Admin panel | Local admin API status/online player hoạt động | OPERATOR API PASS; UI admin chưa test |
| Web UI | Có source web nhưng không nằm trong vòng player test | UNVERIFIED |
| Client integrity/auth HMAC | Cấu hình `clientAuthEnabled=false` | DISABLED |
| Thanh toán/SMS/provider | Chủ động stub/tắt; thiếu bảng nạp tiền/provider | MISSING/DISABLED |

## Độ phủ dữ liệu nền

| Dữ liệu | Số dòng ước tính từ DB |
| --- | ---: |
| Thuộc tính item | 141 |
| Item | 904 |
| Monster | 138 |
| Potion | 162 |
| Quest template | 49 |
| Shop | 148 |
| Nguyên liệu đã nạp lúc server chạy | 225 |

Kho dữ liệu đủ lớn để tiếp tục kiểm thử, nhưng một số bảng gameplay đang rỗng: clan, clan message, farm, wedding, chiến trường, đăng ký chiến trường, inbox, quest clan và nhiều bảng event/top.

## Những phần được xem là đã hoàn thiện ở mốc hiện tại

- Stack local MariaDB → login → game → client hoạt động ổn định.
- Client J2ME dùng socket thật và kết nối localhost.
- Đăng nhập, tạo/chọn nhân vật, vào map.
- HUD, minimap, di chuyển phím số và chạm tìm đường.
- Hội thoại NPC và nhiều menu nền.
- Cửa hàng hiển thị, nhận gói bình HP và dùng bình.
- Chuyển map làng ↔ khu quái.
- Quái spawn, aggro, gây sát thương.
- Chết, đếm ngược, hồi sinh.
- Lưu/nạp nhân vật sau đóng client và restart login + game server.

## Những phần chưa thể xem là hoàn thiện

- Đánh chết quái, EXP, loot và lên cấp.
- Chuỗi quest nhận → theo dõi → hoàn thành → trả thưởng.
- Mặc/tháo/nâng cấp/trang bị và phân phối điểm sau lên cấp.
- Kinh tế đầy đủ: mua/bán/trừ tiền, market, đấu giá, giao dịch.
- Bạn bè, tổ đội, bang hội và PvP với nhiều người chơi.
- Pet, farm, cưới, vận tiêu, lôi đài, chiến trường/MOBA.
- Boss/event từ góc nhìn người chơi và phần thưởng.
- Gift code, nạp tiền, SMS/provider.
- Admin UI, web UI, client integrity.
- Bộ cài/release sạch và test trên máy sạch.

## Thứ tự sửa và test tiếp theo

1. Sửa vòng gây sát thương của nhân vật cấp 1; tạo test tự động hoặc admin fixture để bảo đảm một đòn làm HP quái giảm, kill tăng XP và tạo loot.
2. Bổ sung migration tối thiểu cho bốn bảng thiếu hoặc chặn hẳn các truy vấn optional khi tính năng tắt.
3. Đồng bộ quest DB ↔ client và hoàn thành một quest đầu game có chỉ dẫn rõ.
4. Ghi nhớ lựa chọn tự làm nhiệm vụ; thêm chỉ dẫn cổng và level đề nghị cho từng khu.
5. Tắt sạch hoặc thay bằng local stub cho `BaoTriDaily` và mọi HTTP/provider cũ.
6. Test trang bị, tăng điểm, mua/bán và lưu lại sau restart.
7. Mở client thứ hai để test friend, party, trade, clan và PvP.
8. Bật từng hệ thống Phase 8; không đánh dấu hoàn thiện trước khi có test player end-to-end.
9. Chuẩn hóa UTF-8/console encoding và xử lý EOF khi đóng client.
10. Tạo test suite regression; hiện repository chưa có file test/spec tự động được phát hiện.

## Giới hạn của đợt test

- Chỉ dùng một tài khoản/người chơi, nên các tính năng bắt buộc hai người chưa thể kiểm chứng.
- Nhân vật mới cấp 1 và không lên cấp được do lỗi/blocker chiến đấu.
- Không gọi thanh toán, SMS hoặc dịch vụ ngoài thật.
- Không chờ đủ lịch cho mọi event theo giờ/ngày.
- MariaDB được giữ chạy khi restart login + game server; dữ liệu vẫn được kiểm tra trực tiếp trước và sau restart.
