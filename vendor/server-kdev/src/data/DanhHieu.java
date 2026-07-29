package data;

public class DanhHieu {

    public static final int ANH_HUNG = 8767;
    public static final int BA_CHU = 8768;
    public static final int CHI_TON = 8769;
    public static final int DAI_GIA = 8770;
    public static final int DE_NHAT = 8771;
    public static final int DUY_NGA_DOC_TON = 8772;
    public static final int HIEP_KHACH = 8773;
    public static final int HAO_HAN = 8774;
    public static final int HUYEN_THOAI = 8775;
    public static final int TONG_SU = 8776;
    public static final int UY_PHONG = 8777;
    public static final int VO_DICH = 8778;
    public static final int VO_LAM_CHI_TON = 8779;
    public static final int NHAT_DUONG_TIEN = 8785;
    public static final int TUYET_THE_VO_SONG = 8786;
    public static final int VU_PHAM = 8787;
    public static final int FAN_CUNG = 8788;

    public static final int ANGLE_OF_DEATH = 8900;
    public static final int BAT_CHAP_CUOC_CHOI = 8901;
    public static final int CHIEN_THAN_BAT_DIET = 8902;
    public static final int LONG_TIEN_CHAN_THIEN = 8903;
    public static final int NGHICH_THIEN_NHI_HANH = 8904;
    public static final int KHAI_THIEN_THAN_KIEM = 8905;
    public static final int THIEN_HA_CHI_TON = 8906;
    public static final int VO_DICH_THIEN_HA = 8907;
    public static final int THAN_BINH_BAT_BAI = 8908;
    public static final int DOC_BA_NHAT_PHUONG = 8909;
    public static final int TUNG_HOANH_THIEN_HA = 8910;
    public static final int TUYET_DINH_CAO_THU = 8911;
    public static final int DOC_NHAT_VO_NHI = 8924;
    public static final int NHAT_TIEN_PHA_THIEN = 8925;
    public static final int VINH_DANH_BANG_HOI = 8935;
    public static final int CHAN_MENH_THIEN_TU = 8956;
    public static final int VO_LAM_CHI_TON2 = 8957;
    public static final int CHIEN_THAN_BAT_DIET2 = 8983;

    public static final int CUU_HUYEN_THAT_TO = 8984;
    public static final int HUY_THIEN_DIET_DIA = 8985;
    public static final int KINH_TAI_TUYET_DIEM = 8986;
    public static final int NU_THAN_TUYET_SAC = 8987;
    public static final int PHONG_VAN_TUY_BUT = 8988;
    public static final int QUY_AN_GIANG_HO = 8989;
    public static final int THIEN_HA_DE_NHAT = 8990;
    public static final int THIEN_HA_VO_SONG = 8991;
    public static final int TUNG_HOANH_THIEN_HA_2 = 8992;
    public static final int TUYET_THE_VO_SONG_2 = 8993;
    public static final int VINH_HOA_PHU_QUY = 8994;
    public static final int QUAN_LAM_THIEN_HA = 8995;
    public static final int TUYET_THE_GIAI_NHAN = 8996;
   public static final int TEST = 8999;

    public int ideff = -1;
    public long timeExpire = -1L;

    public boolean isExpire() {
        return this.timeExpire > 0L && System.currentTimeMillis() - this.timeExpire >= 0L;
    }

    public static String getName(int idEff) {
        switch (idEff) {
            case 8767:
                return "Anh hùng";
            case 8768:
                return "Bá chủ";
            case 8769:
                return "Chí tôn";
            case 8770:
                return "Đại gia";
            case 8771:
                return "Đệ nhất";
            case 8772:
                return "Duy ngã độc tôn";
            case 8773:
                return "Hiệp khách";
            case 8774:
                return "Hảo hán";
            case 8775:
                return "Huyền thoại";
            case 8776:
                return "Tông sư";
            case 8777:
                return "Uy phong";
            case 8778:
                return "Vô địch";
            case 8779:
                return "Võ lâm chí tôn";
            case 8780:
            case 8781:
            case 8782:
            case 8783:
            case 8784:
            default:
                return "";
            case 8785:
                return "Nhất dương tiễn";
            case 8786:
                return "Tuyệt thế vô song";
            case 8787:
                return "ACE";
            case 8788:
                return "Fan cứng";

            case 8900:
                return "Angle Of Death";
            case 8901:
                return "Bất Chấp Cuộc Chơi";
            case 8902:
                return "Chiến Thần Bất Diệt";
            case 8903:
                return "Long Tiễn Chấn Thiên";
            case 8904:
                return "Nghịch Thiên Nhi Hành";
            case 8905:
                return "Khai Thiên Thần Kiếm";
            case 8906:
                return "Thiên Hạ Chí Tôn";
            case 8907:
                return "Vô Địch Thiên Hạ";
            case 8908:
                return "Thần Binh Bất Bại";
            case 8909:
                return "Độc Bá Nhất Phương";
            case 8910:
                return "Tung Hoành Thiên Hạ";
            case 8911:
                return "Tuyệt Đỉnh Cao Thủ";
            case 8924:
                return "Độc Nhất Vô Nhị";
            case 8925:
                return "Nhất Tiễn Phá Thiên";
            case 8935:
                return "Vinh Danh Bang Hội";
            case 8956:
                return "Chân Mên Thiên Tử";
            case 8957:
                return "Võ Lâm Chí Tôn 2";
            case 8983:
                return "Chiến Thần Bất Diệt 2";
            case 8984:
                return "Cửu Huyền Thất Tổ";
            case 8985:
                return "Hủy Thiên Diệt Địa";
            case 8986:
                return "Kinh Tài Tuyệt Diễm";
            case 8987:
                return "Nữ Thần Tuyệt Sắc";
            case 8988:
                return "Phong Vân Tùy Bút";
            case 8989:
                return "Quy Ân Giang Hồ";
            case 8990:
                return "Thiên Hạ Đệ Nhất";
            case 8991:
                return "Thiên Hạ Vô Song";
            case 8992:
                return "Tung Hoành Thiên Hạ";
            case 8993:
                return "Tuyệt Thế Vô Song";
            case 8994:
                return "Vinh Hoa Phú Qúy";
            case 8995:
                return "Quân Lâm Thiên Hạ";
            case 8996:
                return "Tuyệt Thế Giai Nhân";
             case 8999:
                return "TEST";
        }
    }

    public String getName() {
        switch (this.ideff) {
            case 8767:
                return "Anh hùng";
            case 8768:
                return "Bá chủ";
            case 8769:
                return "Chí tôn";
            case 8770:
                return "Đại gia";
            case 8771:
                return "Đệ nhất";
            case 8772:
                return "Duy ngã độc tôn";
            case 8773:
                return "Hiệp khách";
            case 8774:
                return "Hảo hán";
            case 8775:
                return "Huyền thoại";
            case 8776:
                return "Tông sư";
            case 8777:
                return "Uy phong";
            case 8778:
                return "Vô địch";
            case 8779:
                return "Võ lâm chí tôn";
            case 8780:
            case 8781:
            case 8782:
            case 8783:
            case 8784:
            default:
                return "";
            case 8785:
                return "Nhất dương tiễn";
            case 8786:
                return "Tuyệt thế vô song";
            case 8787:
                return "ACE";
            case 8788:
                return "Fan cứng";

            case 8900:
                return "Angle Of Death";
            case 8901:
                return "Bất Chấp Cuộc Chơi";
            case 8902:
                return "Chiến Thần Bất Diệt";
            case 8903:
                return "Long Tiễn Chấn Thiên";
            case 8904:
                return "Nghịch Thiên Nhi Hành";
            case 8905:
                return "Khai Thiên Thần Kiếm";
            case 8906:
                return "Thiên Hạ Chí Tôn";
            case 8907:
                return "Vô Địch Thiên Hạ";
            case 8908:
                return "Thần Binh Bất Bại";
            case 8909:
                return "Độc Bá Nhất Phương";
            case 8910:
                return "Tung Hoành Thiên Hạ";
            case 8911:
                return "Tuyệt Đỉnh Cao Thủ";
            case 8924:
                return "Độc Nhất Vô Nhị";
            case 8925:
                return "Nhất Tiễn Phá Thiên";
            case 8935:
                return "Vinh Danh Bang Hội";
            case 8956:
                return "Chân Mên Thiên Tử";
            case 8957:
                return "Võ Lâm Chí Tôn 2";
            case 8983:
                return "Chiến Thần Bất Diệt 2";
            case 8984:
                return "Cửu Huyền Thất Tổ";
            case 8985:
                return "Hủy Thiên Diệt Địa";
            case 8986:
                return "Kinh Tài Tuyệt Diễm";
            case 8987:
                return "Nữ Thần Tuyệt Sắc";
            case 8988:
                return "Phong Vân Tùy Bút";
            case 8989:
                return "Quy Ân Giang Hồ";
            case 8990:
                return "Thiên Hạ Đệ Nhất";
            case 8991:
                return "Thiên Hạ Vô Song";
            case 8992:
                return "Tung Hoành Thiên Hạ";
            case 8993:
                return "Tuyệt Thế Vô Song 2";
            case 8994:
                return "Vinh Hoa Phú Qúy";
            case 8995:
                return "Quân Lâm Thiên Hạ";
            case 8996:
                return "Tuyệt Thế Giai Nhân";
            case 8999:
                return "Test";   
        }
    }
}
