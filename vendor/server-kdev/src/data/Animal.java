package data;

import java.util.Vector;
import real.Actor;
import real.Char;
import real.Map;
import server.TeamServer;
import real.MessageCreator;

public class Animal extends Actor {

    public short[] otherAtt = new short[66];
    long tStartEat = System.currentTimeMillis();
    public byte[] attUp = new byte[14];
    int test = 0;
    public static byte[][] price = new byte[][]{{0, 3, 5, 15, 25}, {0, 3, 5, 15, 25}, {0, 3, 5, 15, 25}, {0, 10, 20, 30, 40}};
    public static byte[] levelup = new byte[]{0, 1, 20, 30, 35};
    public short[] att = new short[14];
    public byte KHANG_DONG_BANG = 0;
    public byte KHANG_BI_CHAY = 0;
    public byte KHANG_HOA_TUYET = 0;
    public byte KHANG_KIM = 0;
    public byte KHANG_MOC = 0;
    public byte KHANG_THO = 0;
    public byte KHANG_THUY = 0;
    public byte KHANG_HOA = 0;
    public long[] eatTime = new long[3];
    public int dbownerId;
    public int ownerId;
    public byte place;
    public byte idImg;
    public byte level;
    public int dbid;
    public int dbidLiendau = -1;
    public int isHoaHinh = 0;
    public long timeHoaHinh = 0L;
    public String name = "";
    static byte[] addMoreSP = new byte[]{0, 0, 0, 0, 0, 2, 3, 0, 3, 3, 3, 3, 3, 3, 3}; // Thêm giá trị cho index 14
    static byte[] addMoreBasic = new byte[]{0, 0, 0, 0, 0, 4, 3, 0, 6, 6, 6, 6, 6, 6, 6}; // Thêm giá trị cho index 14
    static byte[][] pcBasic = new byte[][]{{3, 5, 8, 10}, {3, 5, 8, 10}, {3, 5, 8, 10}, {2, 3, 4, 5}, {2, 3, 4, 5}};
    static byte[][][] pcTN = new byte[][][]{
        {{5, 9, 14, 18}, {5, 9, 14, 18}, {4, 8, 12, 16}, {4, 8, 12, 16}},          // index 0
        {{5, 11, 15, 20}, {4, 8, 12, 16}, {4, 8, 12, 16}, {4, 8, 12, 16}},         // index 1
        {{4, 8, 12, 16}, {4, 8, 12, 16}, {5, 11, 15, 20}, {4, 8, 12, 16}},         // index 2
        {{4, 8, 12, 16}, {5, 9, 14, 18}, {5, 9, 14, 18}, {4, 8, 12, 16}},          // index 3
        {{4, 8, 12, 16}, {4, 8, 12, 16}, {4, 8, 12, 16}, {5, 11, 15, 20}},         // index 4
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 5
        {{7, 13, 17, 21}, {7, 13, 17, 21}, {7, 13, 17, 21}, {7, 13, 17, 21}},      // index 6
        {{7, 13, 17, 21}, {7, 13, 17, 21}, {7, 13, 17, 21}, {7, 13, 17, 21}},      // index 7
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 8
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 9
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 10
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 11
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},     // index 12
        {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}},      // index 13 (new)
    {{6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}, {6, 12, 16, 20}}};
        static byte[][] pcSP = new byte[][]{{5, 10, 15, 20}, {3, 5, 8, 10}, {1, 2, 3, 4}, {2, 4, 6, 8}, {5, 10, 15, 20}};

    public final static byte SOI = 3;
    public static final byte HO = 1;
    public static final byte TRAU = 2;
    public static final byte HAC = 4;
    public static final byte BACH_MA = 0;
    public static final byte PHUONG_HOANG = 5;
    public static final byte HAC_THO = 6;
    public static final byte TUAN_LOC = 7;
    public static final byte PHUONG_HOANG_BANG = 8;
    public static final byte PORO = 9;
    public static final byte LAN = 10;
    public static final byte HEO = 11;
    public static final byte NGUA_XUONG = 12;
    public static final byte RONG_LON = 13;

    public static final byte PHUONG_HOANG_7MAU = 14;
    public static final byte PHUONG_HOANG_MOC = 15; // Mộc
    public static final byte PHUONG_HOANG_KIM = 16; // Kim
    public static final byte PHUONG_HOANG_THO = 17; // Thổ

    public static final byte HEO_BANG = 18; // DKBG
    public static final byte SU_TU = 19; // Sú tử

    // AVATAR Thú
    public static byte[] fr = new byte[]{3, 2, 2, 2, 3, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 , 5};

    // ID_icon_pet
    public static byte[] idImgPaint = new byte[]{2, 5, 4, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Gọi tổng số lượng id pet
    public static byte[] rideHorse = new byte[]{1, 3, 2, 4, 5, 6, 7, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};

    // images_animal _ img trong hành trang
    public static byte[] idImgIcon = new byte[]{0, 1, 2, 3, 4, 10, 13, 14, 20, 45, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56};

    /**
     * đi bộ là 0, bay là 1
     */
    public static byte[] typeAnimal = new byte[]{0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0 ,0};
    public long texpire = 0L;

    private int getStatProfileIndex() {
        switch (this.idImg) {
            case PHUONG_HOANG_MOC:
            case PHUONG_HOANG_KIM:
            case PHUONG_HOANG_THO:
                return PHUONG_HOANG_7MAU;
            case HEO_BANG:
                return HEO;
            case SU_TU:
                return LAN;
            default:
                return this.idImg >= 0 && this.idImg < pcTN.length ? this.idImg : BACH_MA;
        }
    }

    private int getAddMoreSP() {
        int index = this.getStatProfileIndex();
        return index >= 0 && index < addMoreSP.length ? addMoreSP[index] : 0;
    }

    private int getAddMoreBasic() {
        int index = this.getStatProfileIndex();
        return index >= 0 && index < addMoreBasic.length ? addMoreBasic[index] : 0;
    }

    private byte[] getPotentialLimit(final int potentialIndex) {
        int index = this.getStatProfileIndex();
        return pcTN[index][potentialIndex];
    }

    /**
     * todo option kháng thú cưỡi banglan, setlan, doclan, lualan
     *
     * @param type
     * @return
     */
    public int getGiamKhangLan(int type) {
        switch (type) {
            case 0:// băng
                switch (this.idImg) {
                    case HEO:
                        return 0;
                    default:
                        return 0;
                }
            case 1: // set
                switch (this.idImg) {
                    default:
                        return 0;
                }
            case 2://doc
                switch (this.idImg) {
                    default:
                        return 0;
                }
            case 3:// lua
                switch (this.idImg) {
                    default:
                        return 0;
                }
            default:
                return 0;
        }
    }

    /**
     * Max là  == 95
     * @return 
     */
    public int getGiamKhangDongBang() {
        switch (this.idImg) {
            case HEO:
                return 0;
            default:
                return 0;
        }
    }

    public Animal() {

    }

    public boolean doEat(short type) {
        try {
            this.eatTime[0] = 0L;
            this.eatTime[1] = 0L;
            this.eatTime[2] = 0L;

            byte i;
            for (i = 0; i < this.attUp.length; ++i) {
                this.attUp[i] = 0;
            }

            this.tStartEat = System.currentTimeMillis() + 1000L;
            this.eatTime[type - 112] = 14400L;
            switch (type) {
                case 112:
                    this.attUp[0] = 4;
                    this.attUp[1] = 4;
                    break;
                case 113:
                    this.attUp[2] = 4;
                    break;
                case 114:
                    this.attUp[0] = 4;
                    this.attUp[1] = 4;
                    this.attUp[2] = 4;

                    for (i = 9; i < 14; ++i) {
                        if (this.att[i] > 0) {
                            this.attUp[i] = 4;
                        }
                    }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return true;
    }


    public String getName() {
       
        return this.name;
    }

    public boolean isThuCuoiTool() {
        return this.isPoro() || this.isLan() || this.isHeo() || this.isHeoBang() || this.isNguaXuong() || isRongLon() || this.isSuTu();
    }

    public int getIdImageTool() {
        // Check if animal is transformed
        if (this.isHoaHinh()) {
            // Calculate remaining days
            long remainingTime = this.timeHoaHinh - System.currentTimeMillis();
            long remainingDays = remainingTime / (24L * 60L * 60000L);
            
            // Return different image ID based on remaining days
            if (remainingDays > 7) {
                return 8965; // Over 7 days
            } else {
                return 8966; // 7 days or less
            }
        }

        else if (this.isPoro()) {
            return 8756;
        } else if (this.isLan()) {
            return 8762;
        } else if (this.isHeo()) {
            return 8764;
        } else if (this.isRongLon()) {
            return 8934;
        } else if (this.isSuTu()) {
            return 8975;
        } else if (this.isHeoBang()) {
            return 8936;
        } else {
            return this.isNguaXuong() ? 8765 : -1;
        }
    }

    public int getIndexDxy() {
         // Check if animal is transformed  
         if (this.isHoaHinh()) {
            return 4; // Giá trị index mặc định khi hóa hình
        }

        else if (this.isPoro()) {
            return 0;
        } else if (this.isLan()) {
            return 1;
        } else if (this.isHeo() || this.isHeoBang()) {
            return 2;
        } else if (isRongLon()) {
            return 3;
        } else if (this.isSuTu()) {
            return -1;
        } else {
            return this.isNguaXuong() ? 3 : -1;
        }
    }

    public byte timeOutFood() {
        long curent = System.currentTimeMillis();

        for (byte i = 0; i < this.eatTime.length; ++i) {
            if (this.eatTime[i] > 0L && curent - this.tStartEat > 0L) {
                this.eatTime[i]--;
                if (this.eatTime[i] <= 0L) {
                    this.eatTime[i] = 0L;
                    this.initFood();
                    return i;
                }

                this.tStartEat = System.currentTimeMillis() + 1000L;
                return -1;
            }
        }

        return -1;
    }

    public boolean isAnimalCanTang() {
        return false;
//        return (this.isHeo() 
//                || this.isNguaXuong() 
//                || this.isPhuongHoangBang() 
//                || this.isPhuongHoangLua() 
//                || this.isPoro()) 
//                || this.level > 1;
//                && this.texpire > 0L; 
    }

    public boolean isRongLon() {
        return this.idImg == RONG_LON;
    }
    // pet mới
    public boolean isSuTu() {
        return this.idImg == SU_TU;
    }

    public boolean isHeo() {
        return this.idImg == HEO;
    }

    public boolean isHeoBang() {
        return this.idImg == HEO_BANG;
    }

    public boolean isNguaXuong() {
        return this.idImg == NGUA_XUONG;
    }

    public boolean isPoro() {
        return this.idImg == PORO;
    }

    public boolean isLan() {
        return this.idImg == LAN;
    }

    public boolean isPhuongHoangLua() {
        return this.idImg == PHUONG_HOANG;
    }

    public boolean isPhuongHoangBang() {
        return this.idImg == PHUONG_HOANG_BANG;
    }

    public boolean isPhuongHoang7Mau() {
        return this.idImg == PHUONG_HOANG_7MAU;
    }

    public boolean isPhuongHoangMoc() {
        return this.idImg == PHUONG_HOANG_MOC;
    }

    public boolean isPhuongHoangKim() {
        return this.idImg == PHUONG_HOANG_KIM;
    }

    public boolean isPhuongHoangTho() {
        return this.idImg == PHUONG_HOANG_THO;
    }

    public String getInfoSendUser() {
        String[] info1 = new String[]{
            "PT vật tăng: " + (this.att[0] + this.attUp[0]) + "%\n",
            "PT ma tăng: " + (this.att[1] + this.attUp[1]) + "%\n",
            "Tấn công tăng: " + (this.attUp[2] + this.att[2]) + "%\n",
            "Tăng: " + (this.attUp[3] + this.att[3]) + "HP\n",
            "Tăng: " + (this.att[4] + this.attUp[4]) + "MP\n",
            "Sức mạnh tăng: " + (this.att[5] + this.attUp[5]) + "\n",
            "Sức khỏe tăng: " + (this.att[6] + this.attUp[6]) + "\n",
            "Khéo léo tăng: " + (this.att[7] + this.attUp[7]) + "\n",
            "Tinh thần tăng: " + (this.attUp[8] + this.att[8]) + "\n",
            "Hấp thu " + (this.attUp[9] + this.att[9]) + "% sát thương tỷ lệ: " + (this.attUp[9] + this.att[9]) + "%\n",
            "Tỷ lệ giảm sát thương: " + (this.attUp[10] + this.att[10]) + "%\n",
            "Tăng chí mạng: " + (this.attUp[11] + this.att[11]) + "\n",
            "Tỷ lệ nhận x2: " + (this.attUp[12] + this.att[12]) + "%\n",
            "Né tránh: " + (this.attUp[13] + this.att[13]) + "%\n"};
        
        // Thêm ID vào đầu chuỗi info
        String info = "ID: " + this.dbid + "\n\n";

        for (int i = 0; i < this.att.length; ++i) {
            if (this.att[i] > 0) {
                info = info + info1[i];
            }
        }

        if (this.idImg == PHUONG_HOANG_BANG) {
            info = info + "Băng lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            if (this.KHANG_BI_CHAY > 0) {
                info = info + "Kháng bị cháy 95%\n";
            }
        }

        if (this.idImg == PORO) {
            info = info + "Băng lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            if (this.KHANG_BI_CHAY > 0) {
                info = info + "Kháng bị cháy 95%\n";
            }

            info = info + "Hoá đối phương thành người tuyết khi bị đánh: 20%";
        }

        if (this.idImg == PHUONG_HOANG) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            if (this.KHANG_DONG_BANG > 0) {
                info = info + "Kháng đóng băng 95%\n";
            }
        }

        if (this.idImg == PHUONG_HOANG_7MAU) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng Hỏa 15%\n";
            info = info + "Kháng Thủy 15%\n";

            this.KHANG_HOA = 15;
            this.KHANG_THUY = 15;

        }
        if (this.idImg == SU_TU) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng Hỏa 15%\n";
            info = info + "Kháng Thủy 15%\n";

            this.KHANG_HOA = 15;
            this.KHANG_THUY = 15;
        }
        if (this.idImg == PHUONG_HOANG_KIM) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng Kim 30%\n";
            this.KHANG_KIM = 30;

        }
        if (this.idImg == PHUONG_HOANG_MOC) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng Mộc 30%\n";
            this.KHANG_MOC = 30;

        }
        if (this.idImg == PHUONG_HOANG_THO) {
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng Thổ 30%\n";
            this.KHANG_THO = 30;

        }

        if (this.idImg == LAN) {
            info = info + "Lửa lan lên quái tăng 100%\n";
            info = info + "Độc lan lên quái tăng 100%\n";
            info = info + "Sét lan lên quái tăng 100%\n";
            info = info + "Băng lan lên quái tăng 100%\n";
            if (this.KHANG_HOA_TUYET > 0) {
                info = info + "Không bị hoá tuyết 95%\n";
            }
        }

        if (this.idImg == HEO) {
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng choáng lan 95%\n";
        }

        if (this.idImg == HEO_BANG) {
            info = info + "Sét lan tăng 100%\n";
            info = info + "Kháng choáng lan 95%\n";
            if (this.KHANG_HOA_TUYET > 0) {
                info = info + "Không bị hoá tuyết 30%\n";
            }
        }

        if (this.idImg == NGUA_XUONG) {
            info = info + "Độc lan tăng 100%\n";
            info = info + "95% hồi hp khi trúng độc lan.";
        }

        if (this.idImg == TUAN_LOC) {
            info = info + "Băng lan tăng 100%\n";
            info = info + "Lửa lan tăng 100%\n";
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Bạo kích 3%";
        }

        if (this.idImg == RONG_LON) {
            info = info + "Độc lan tăng 100%\n";
            info = info + "Sét lan tăng 100%\n";
            info = info + "Băng lan tăng 100%\n";
            info = info + "Cảm chịu:\n";
            info = info + "- Tích sát thương khi bị đánh 8 lần trong 8s miễn khống: \n";
            info = info + "- Xóa hiệu ứng bất lợi như đóng băng - băng lan của bản thân\n";
            info = info + "- Giảm kháng đóng băng - băng lan của kẻ thù trong 8s\n";
            info = info + "Kháng bị cháy 95%\n";
            info = info + "Kháng đóng băng 95%\n";
            info = info + "Kết hợp hỏa kì lân:\n";
            info = info + "- Trả đũa :\n Giảm 50% sát thương nhận từ phản sát thương\n";
        }

        if (this.isHoaHinh()) {
            info = info + "\nLửa thượng cổ\n";
            info = info + "Trừ 10% giáp mỗi hit bị đánh\n"; 
            info = info + "Thời gian tồn tại 10s\n";
            info = info + "Tỷ lệ xuất hiện 5%\n";
            
            // Calculate remaining days
            long remainingTime = this.timeHoaHinh - System.currentTimeMillis();
            long remainingDays = remainingTime / (24L * 60L * 60000L);
            info = info + "Thời gian hóa hình: " + remainingDays + " ngày\n";
        }
        String tmp = this.getInfoFood();
        if (!tmp.equals("")) {
            info = info + "\n" + tmp;
        }

        if (this.texpire > 0L) {
            info = info + "\nCòn lại: " + (this.texpire - System.currentTimeMillis()) / 60000L + " phút";
        }

        if (TeamServer.isServerIndo()) {
            String[] var10000 = new String[]{"Tambah materi pelindung: " + (this.att[0] + this.attUp[0]) + "%\n", "Tambah pelindung sihir: " + (this.att[1] + this.attUp[1]) + "%\n", "Tambah serangan: " + (this.attUp[2] + this.att[2]) + "%\n", "Tambah: " + (this.attUp[3] + this.att[3]) + "HP\n", "Tambah: " + (this.att[4] + this.attUp[4]) + "MP\n", "Tenaga bertambah: " + (this.att[5] + this.attUp[5]) + "\n", "Sehat bertambah: " + (this.att[6] + this.attUp[6]) + "\n", "Skil bertambah: " + (this.att[7] + this.attUp[7]) + "\n", "Moral bertambah: " + (this.attUp[8] + this.att[8]) + "\n", "Menyerap " + (this.attUp[9] + this.att[9]) + "% Tingkat kerusakan: " + (this.attUp[9] + this.att[9]) + "%\n", "Kurangi kerusakan: " + (this.attUp[10] + this.att[10]) + "%\n", "Tingkatkan fatal: " + (this.attUp[11] + this.att[11]) + "\n", "Menerima bagian x2: " + (this.attUp[12] + this.att[12]) + "%\n", "Menghindar: " + (this.attUp[13] + this.att[13]) + "%"};
        }

        return info;
    }

    public String getInfoFood() {
        String info = "";
        if (this.eatTime[0] > 0L) {
            info = "Đã ăn thức ăn tăng phòng thủ ";
        } else if (this.eatTime[1] > 0L) {
            info = "Đã ăn thức ăn tăng tấn công";
        } else if (this.eatTime[2] > 0L) {
            info = "Đã ăn thức ăn tổng hợp";
        }

        if (TeamServer.isServerIndo()) {
            if (this.eatTime[0] > 0L) {
                info = "Makanan pelindung sudah bertambah";
            } else if (this.eatTime[1] > 0L) {
                info = "Makan penambah serangan";
            } else if (this.eatTime[2] > 0L) {
                info = "Makan makanan ternak";
            }
        }

        return info;
    }

    public void initAtt(String stInfo) {
        String[] data = Char.split(stInfo, ",");

        for (int i = 0; i < this.att.length; ++i) {
            try {
                this.att[i] = Short.parseShort(data[i]);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        try {
            this.eatTime[0] = Long.parseLong(data[this.att.length]);
            this.eatTime[1] = Long.parseLong(data[this.att.length + 1]);
            this.eatTime[2] = Long.parseLong(data[this.att.length + 2]);
            this.initFood();
            this.KHANG_DONG_BANG = Byte.parseByte(data[this.att.length + 3]);
            this.KHANG_BI_CHAY = Byte.parseByte(data[this.att.length + 4]);
            this.KHANG_HOA_TUYET = Byte.parseByte(data[this.att.length + 5]);

            try {
                this.KHANG_KIM = Byte.parseByte(data[this.att.length + 6]);
                this.KHANG_MOC = Byte.parseByte(data[this.att.length + 7]);
                this.KHANG_THO = Byte.parseByte(data[this.att.length + 8]);
                this.KHANG_THUY = Byte.parseByte(data[this.att.length + 9]);
                this.KHANG_HOA = Byte.parseByte(data[this.att.length + 10]);
            } catch (Exception exception) {
            }

        } catch (Exception var5) {
        }

    }

    public int getPcHoaNguoiTuyetKhiBiDanh() {
        return this.isPoro() ? 20 : -1;
    }

    public int getPc8sRongLon() {
        return this.isRongLon() ? 100 : -1;
       
    }

    public int getPCKhangDongBang() {
        return this.KHANG_DONG_BANG;
    }

    public int getPCKhangBiChay() {
        return this.KHANG_BI_CHAY;
    }

    private void initFood() {
        for (byte i = 0; i < this.attUp.length; ++i) {
            this.attUp[i] = 0;
        }

        try {
            this.tStartEat = System.currentTimeMillis() + 1000L;

            for (int i = 0; i < this.eatTime.length; ++i) {
                int type = 112 + i;
                if (this.eatTime[i] > 0L) {
                    switch (type) {
                        case 112:
                            this.attUp[0] = 2;
                            this.attUp[1] = 2;
                            break;
                        case 113:
                            this.attUp[2] = 2;
                            break;
                        case 114:
                            this.attUp[0] = 2;
                            this.attUp[1] = 2;
                            this.attUp[2] = 2;

                            for (byte j = 9; j < 14; ++j) {
                                if (this.att[j] > 0) {
                                    this.attUp[j] = 2;
                                }
                            }
                    }
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public String getAttribute() {
        String info = "" + this.att[0];

        for (int i = 1; i < this.att.length; ++i) {
            info = info + "," + this.att[i];
        }

        info = info + "," + this.eatTime[0];
        info = info + "," + this.eatTime[1];
        info = info + "," + this.eatTime[2];
        info = info + "," + this.KHANG_DONG_BANG;
        info = info + "," + this.KHANG_BI_CHAY;
        info = info + "," + this.KHANG_HOA_TUYET;

        info = info + "," + this.KHANG_KIM;
        info = info + "," + this.KHANG_MOC;
        info = info + "," + this.KHANG_THO;
        info = info + "," + this.KHANG_THUY;
        info = info + "," + this.KHANG_HOA;
        return info;
    }

    public Animal clone(int ownerId) {
        Animal a = new Animal();
        a.ownerId = ownerId;
        a.place = this.place;
        String info = this.getAttribute();
        a.initAtt(info);
        a.idImg = this.idImg;
        a.level = this.level;
        a.name = this.name;
        a.texpire = this.texpire;
        a.isHoaHinh = this.isHoaHinh;
        a.timeHoaHinh = this.timeHoaHinh;
        return a;
    }

    public boolean changeAtt(Char p, int type) {
        boolean result = true;
        switch (type) {
            case 1:
                String oldatt = this.getAttribute();
                this.att[9] = 0;
                this.att[10] = 0;
                this.att[11] = 0;
                this.att[12] = 0;
                this.att[13] = 0;
                int temp = Map.r.nextInt(5) + 9;
                switch (temp) {
                    case 9:
                        this.att[temp] = (short) (Map.r.nextInt(pcSP[0][this.level - 1] - 1) + 1);
                        break;
                    case 10:
                        this.att[temp] = (short) (Map.r.nextInt(pcSP[1][this.level - 1] - 1) + 1);
                        break;
                    case 11:
                        int kkk = pcSP[2][this.level - 1] - 1;
                        if (kkk <= 0) {
                            kkk = 1;
                        }

                        this.att[temp] = (short) (Map.r.nextInt(kkk) + 1);
                        break;
                    case 12:
                        this.att[temp] = (short) (Map.r.nextInt(pcSP[3][this.level - 1] - 1) + 1);
                        break;
                    case 13:
                        this.att[temp] = (short) (Map.r.nextInt(pcSP[4][this.level - 1] - 1) + 1);
                }

                Database.instance.saveOrtherLog("tob_log_other_animal", p.getName(), this.getAttribute() + " | " + oldatt + "|" + this.name + "|" + this.level, "catta");
            case 0:
            default:
                return result;
        }
    }

    public boolean upLevel(Char p) {
        if (p.lvDetail.lv <= levelup[this.level]) {
            return false;
        } else if (this.level >= 4) {
            return false;
        } else if (!this.isMaxAttOfLevel()) {
            return false;
        } else {
            Vector<Integer> index = new Vector();

            int i;
            for (i = 0; i < 4; ++i) {
                if (this.att[i + 5] == 0) {
                    index.add(i + 5);
                }
            }

            i = (Integer) index.get(Map.r.nextInt(index.size()));
            Database.instance.saveOrtherLog("tob_log_other_animal", p.getName(), this.getAttribute() + " | " + i + " | " + this.level + " | " + this.name, "ulva");
            this.att[i] = (short) (Map.r.nextInt(this.getPotentialLimit(i - 5)[this.level - 1]) + 1);
            ++this.level;
            return true;
        }
    }

    public boolean isMaxAttOfLevel() {
        int i;
        for (i = 0; i < 5; ++i) {
            if (this.att[i] > 0) {
                if (i < 3 && this.att[i] < pcBasic[i][this.level - 1]) {
                    return false;
                }

                if (i >= 3 && this.att[i] < pcBasic[i][this.level - 1] * 1000) {
                    return false;
                }
            }
        }

        for (i = 0; i < 4; ++i) {
            if (this.att[i + 5] > 0 && this.att[i + 5] < this.getPotentialLimit(i)[this.level - 1]) {
                return false;
            }
        }

        for (i = 0; i < 5; ++i) {
            if (this.att[i + 9] > 0 && this.att[i + 9] < pcSP[i][this.level - 1]) {
                return false;
            }
        }

        return true;
    }

    public boolean upAttribute(int type) {
        boolean result = false;
        short[] var10000;
        int i;
        int addMoreSPValue = this.getAddMoreSP();
        int addMoreBasicValue = this.getAddMoreBasic();
        if (type == 1) {
            for (i = 0; i < 5; ++i) {
                if (this.att[i + 9] > 0 && this.att[i + 9] < pcSP[i][this.level - 1] + addMoreSPValue) {
                    var10000 = this.att;
                    var10000[i + 9] = (short) (var10000[i + 9] + 1 + addMoreSPValue);
                    result = true;
                }
            }
        } else if (type == 0) {
            for (i = 0; i < 4; ++i) {
                if (this.att[i + 5] > 0 && this.att[i + 5] < this.getPotentialLimit(i)[this.level - 1] + addMoreSPValue) {
                    var10000 = this.att;
                    var10000[i + 5] = (short) (var10000[i + 5] + 1 + addMoreSPValue);
                    result = true;
                }
            }
        } else {
            for (i = 0; i < 5; ++i) {
                if (this.att[i] < (i >= 3 ? (pcBasic[i][this.level - 1] + addMoreSPValue) * 1000 : pcBasic[i][this.level - 1] + (this.idImg != 8 && this.idImg != 5 ? addMoreSPValue : addMoreBasicValue))) {
                    if (this.idImg != 5 && this.idImg != 8) {
                        var10000 = this.att;
                        var10000[i] = (short) (var10000[i] + (i < 3 ? 1 : 1000) + addMoreSPValue);
                    } else {
                        var10000 = this.att;
                        var10000[i] = (short) (var10000[i] + (i < 3 ? 1 : 1000 + addMoreSPValue));
                    }

                    result = true;
                }
            }
        }

        return result;
    }

    private boolean isMaxAttLv() {
        return false;
    }

    /**
     * map.java gọi lúc tạo ở npc
     */
    public void createAtt() {
        this.createAttBasic();
        this.createBasePoint();
    }

    public void createAttMax() {
        this.createAttBasicMax();
        this.createBasePointMax();
    }

    public void reBuildAtt(int att) {
    }

    public void createAttBasic() {
        this.att[3] = 2000;
        this.att[4] = 2000;
        this.att[0] = (short) (Map.r.nextInt(2) + 1);
        this.att[1] = (short) (Map.r.nextInt(2) + 1);
        this.att[2] = (short) (Map.r.nextInt(2) + 1);

        if (this.idImg == PHUONG_HOANG_BANG) {
            this.att[0] = 3;
            this.att[1] = 3;
            this.att[2] = 3;
        }
        if (this.idImg == PHUONG_HOANG_7MAU) {
            this.att[0] = 3;
        }
        if (this.idImg == SU_TU) {
            this.att[0] = 3;
        }
        if (this.idImg == PHUONG_HOANG_KIM) {
            this.att[0] = 3;
        }
        if (this.idImg == PHUONG_HOANG_MOC) {
            this.att[0] = 3;
        }
        if (this.idImg == PHUONG_HOANG_THO) {
            this.att[0] = 3;
        }

    }

    public void createAttBasicMax() {

        switch (this.idImg) {
            case HEO_BANG:
                KHANG_HOA_TUYET = 30;
                this.att[3] = 5500;
                this.att[4] = 5500;
                this.att[0] = 15;
                this.att[1] = 15;
                this.att[2] = 10;
                break;
            default:
                this.att[3] = 5000;
                this.att[4] = 5000;
                this.att[0] = 10;
                this.att[1] = 10;
                this.att[2] = 10;
                break;
        }

    }

    public void createBasePoint() {
        this.att[5] = 0;
        this.att[6] = 0;
        this.att[7] = 0;
        this.att[8] = 0;
        int indexTiemNang = Map.r.nextInt(4) + 5;
        this.att[indexTiemNang] = (short) (Map.r.nextInt(3) + 1);
        switch (indexTiemNang) {
            case 5:
                if (this.idImg == HO || this.idImg == BACH_MA) {
                    this.att[indexTiemNang] = (short) (Map.r.nextInt(4) + 1);
                }
                break;
            case 6:
                if (this.idImg == SOI || this.idImg == BACH_MA) {
                    this.att[indexTiemNang] = (short) (Map.r.nextInt(4) + 1);
                }
                break;
            case 7:
                if (this.idImg == SOI || this.idImg == TRAU) {
                    this.att[indexTiemNang] = (short) (Map.r.nextInt(4) + 1);
                }
                break;
            case 8:
                if (this.idImg == HAC) {
                    this.att[indexTiemNang] = (short) (Map.r.nextInt(4) + 1);
                }
        }

        this.att[9] = 0;
        this.att[10] = 0;
        this.att[11] = 0;
        this.att[12] = 0;
        this.att[13] = 0;
        int temp = Map.r.nextInt(5) + 9;
        switch (temp) {
            case 9:
                this.att[temp] = (short) (Map.r.nextInt(4) + 1);
                break;
            case 10:
                this.att[temp] = (short) (Map.r.nextInt(2) + 1);
                break;
            case 11:
                this.att[temp] = 1;
                break;
            case 12:
                this.att[temp] = (short) (Map.r.nextInt(1) + 1);
                break;
            case 13:
                this.att[temp] = (short) (Map.r.nextInt(4) + 1);
        }

    }

    public void createBasePointMax() {
        switch (this.idImg) {
            case HEO_BANG:
                this.att[5] = 20;
                this.att[6] = 20;
                this.att[7] = 20;
                this.att[8] = 20;
                this.att[9] = 23;
                this.att[10] = 13;
                this.att[11] = 7;
                this.att[12] = 11;
                this.att[13] = 23;
                break;
            default:
                this.att[5] = 20;
                this.att[6] = 20;
                this.att[7] = 20;
                this.att[8] = 20;
                this.att[9] = 23;
                this.att[10] = 13;
                this.att[11] = 7;
                this.att[12] = 11;
                this.att[13] = 23;
                break;
        }
        int id1 = Map.r.nextInt(5) + 9;

        int id2;
        for (id2 = Map.r.nextInt(5) + 9; id2 == id1; id2 = Map.r.nextInt(5) + 9) {
        }

        for (int i = 9; i < 14; ++i) {
            if (i != id1 && i != id2) {
                this.att[i] = 0;
            }
        }

    }

    public int getBaoKich() {
        return this.idImg == TUAN_LOC ? 30 : 0;
    }

    public int getLuaLan() {
        return this.idImg != SU_TU && this.idImg != PHUONG_HOANG && this.idImg != PHUONG_HOANG_7MAU && this.idImg != PHUONG_HOANG_KIM && this.idImg != PHUONG_HOANG_MOC && this.idImg != PHUONG_HOANG_THO && this.idImg != TUAN_LOC && this.idImg != NGUA_XUONG && this.idImg != LAN  ? 0 : 1;
    }

    public int getBangLan() {
        return this.idImg != PHUONG_HOANG_BANG && this.idImg != PORO && this.idImg != TUAN_LOC && this.idImg != LAN && this.idImg != RONG_LON ? 0 : 1;
    }

    public int getSetLan() {
        return this.idImg != SU_TU && this.idImg != PHUONG_HOANG_BANG && this.idImg != PORO && this.idImg != TUAN_LOC && this.idImg != HEO && this.idImg != LAN && this.idImg != RONG_LON ? 0 : 1;
    }

    public int getDocLan() {
        return this.idImg != SU_TU && this.idImg != PHUONG_HOANG_BANG && this.idImg != PORO && this.idImg != TUAN_LOC && this.idImg != HEO && this.idImg != LAN && this.idImg != RONG_LON ? 0 : 1;
    }

    public void checTimeOut() {
    }

    public boolean timeExpire() {
        return this.texpire > 0L && System.currentTimeMillis() - this.texpire > 0L;
    }

    public boolean isHoaHinh() {
        return this.isHoaHinh == 1 && this.timeHoaHinh > System.currentTimeMillis();
    }

    public void doHoaHinh(Char p) {
        // Change required amount to 30
        int soluongDel = 30;
        
        // Check if player has enough materials
        if (p.listGemitem[268] < soluongDel && p.listGemitemLock[268] < soluongDel) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Ngươi cần có " + soluongDel + " lọ thuốc hóa hình để hóa hình", ""));
            return;
        }
    
        if (p.animalRide == null) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải cưỡi linh thú ", ""));
            return;
        }

        if (p.animalRide.isHoaHinh()) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Linh thú đã hóa hình, vui lòng đợi 15 ngày để hóa hình lại", ""));
            return;
        }
    
        // Random success chance (70%)
        if (Map.r.nextInt(100) < 70) {
            // Set hoa hinh status and time
            p.animalRide.isHoaHinh = 1;
            p.animalRide.timeHoaHinh = System.currentTimeMillis() + (15L * 24L * 60L * 60000L); // 15 days
            Database.instance.saveAnimal(p.animalRide);
            p.sendMessage(MessageCreator.createServerAlertMessage("Hóa hình thành công ! Vui lòng thoát game vào lại để Hóa hình", ""));
            
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
        } else {
            p.sendMessage(MessageCreator.createServerAlertMessage("Hóa hình thất bại!", ""));
        }
    
        // Use materials
        if (p.listGemitemLock[268] >= soluongDel) {
            p.delGem(268, soluongDel, true);
        } else {
            p.delGem(268, soluongDel, false); 
        }
    
        p.sendMessage(MessageCreator.createCharGemItem(p));
        Database.instance.saveOrtherLog("", p.charname, "Hoa hinh thu", "hoahinh");
    }

    

}
