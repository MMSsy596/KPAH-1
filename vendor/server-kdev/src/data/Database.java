package data;

import io.Session;

import real.Map;
import real.*;
import server.InfoClientConnect;
import server.TeamServer;
import util.Logger;
import io.Message;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.*;
import static real.Char.ID_MAT_NA;
import static real.Char.doDropModelClothe;

import real.plugins.GiftCode;


public class Database {

    private static final int LOCKED_MATERIAL_SHOP_MIN_ID = 116;
    private static final int LOCKED_MATERIAL_SHOP_MAX_ID = 125;
    private static final int LOCKED_MATERIAL_SHOP_AMOUNT = 50;
    private static String LINK;
    private static String LINK1;
    private static volatile boolean LOCAL_TEAM_USER_SCHEMA_READY = false;
    public static Database instance;
    List<Connection> connections;
    public static ConnPool connPool;
    public static ConnPool connPool1;
    public static ConnPool connPoolNap;
    public static ConnPool connGiftCode;
    List<Boolean> busy;
    public static String dayCreateRace;
    public static String[] nameTinhThanh;
    public static byte[][] basicPoint;
    public static SMSProvider smsNapMe;
    public static SMSProvider smsNapVTC;
    public static Vector<String> agent;
    public static Hashtable<String, CCUProvider> allProvider;
    public static Vector<String> badWord;
    public static String tableRace;
    public static byte createRace;
    private static SimpleDateFormat dateFormat;
    public static Vector<NewClan> allClanDB;
    public static DateFormat timeFormat;
    public String curWeekMonthYear;
    public static String curMonthYear;
    static String nameTableTopLoiDai;
    static String nameTableThachDau;
    static String[] tableNameWeek_month_year;
    static String[] tableNamemonth_year;
    static String curWeekMonthYearDrop;
    static String curMonthYearDrop;
    Vector<String> sqlUpdate;
    boolean stop;
    private int timeXP;
    Vector<ItemTemplates> templateTest;
    public static Random r;
    static String[] name;
    int sid;
    int mid;
    boolean notFalse;
    public static String[] namechar;
    int update;
    public static CharTemplate[] charTemplatesOld;
    public static CharTemplate[][] charTemplates;
    public static Vector<CharInfo> all_char_top;
    Hashtable<String, infoHack> hack;
    public static String[] nameMons_indo;
    public static String[] nameItem_indo;
    public static String[] name_att_in;
    public static String[] nameServer;
    public static boolean ENABLE_ACTIVE_CHECK = true;  // báº­t táº¯t kÃ­ch hoáº¡t  //false Ä‘Ã³ng //true báº­t

    static {
        Database.instance = new Database();
        Database.dayCreateRace = "2013-07-04";
        Database.nameTinhThanh = new String[]{"Cáº§n ThÆ¡", "ÄÃ  Náºµng", "Háº£i PhÃ²ng", "HÃ  Ná»™i", "TP HCM", "An Giang", "BÃ  Rá»‹a - VÅ©ng TÃ u", "Báº¯c Giang", "Báº¯c Káº¡n", "Báº¡c LiÃªu", "Báº¯c Ninh", "Báº¿n Tre", "BÃ¬nh Äá»‹nh", "BÃ¬nh DÆ°Æ¡ng", "BÃ¬nh PhÆ°á»›c", "BÃ¬nh Thuáº­n", "CÃ  Mau", "Cao Báº±ng", "Äáº¯k Láº¯k", "Äáº¯k NÃ´ng", "Äiá»‡n BiÃªn", "Äá»“ng Nai", "Äá»“ng ThÃ¡p", "Gia Lai", "HÃ  Giang", "HÃ  Nam", "HÃ  TÄ©nh", "Háº£i DÆ°Æ¡ng", "Háº­u Giang", "HÃ²a BÃ¬nh", "HÆ°ng YÃªn", "KhÃ¡nh HÃ²a", "KiÃªn Giang", "Kon Tum", "Lai ChÃ¢u", "LÃ¢m Äá»“ng", "Láº¡ng SÆ¡n", "LÃ o Cai", "Long An", "Nam Äá»‹nh", "Nghá»‡ An", "Ninh BÃ¬nh", "Ninh Thuáº­n", "PhÃº Thá»", "Quáº£ng BÃ¬nh", "Quáº£ng Nam", "Quáº£ng NgÃ£i", "Quáº£ng Ninh", "Quáº£ng Trá»‹", "SÃ³c TrÄƒng", "SÆ¡n La", "TÃ¢y Ninh", "ThÃ¡i BÃ¬nh", "ThÃ¡i NguyÃªn", "Thanh HÃ³a", "Thá»«a ThiÃªn Huáº¿", "Tiá»n Giang", "TrÃ  Vinh", "TuyÃªn Quang", "VÄ©nh Long", "VÄ©nh PhÃºc", "YÃªn BÃ¡i", "PhÃº YÃªn"};
        Database.basicPoint = new byte[][]{{25, 20, 10, 25, 10}, {30, 20, 10, 20, 10}, {10, 25, 35, 10, 10}, {20, 30, 10, 20, 10}, {20, 30, 15, 15, 10}};
        Database.smsNapMe = new SMSProvider(0);
        Database.smsNapVTC = new SMSProvider(1);
        Database.agent = null;
        Database.allProvider = new Hashtable<>();
        Database.badWord = new Vector<>();
        Database.createRace = 0;
        Database.dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Database.allClanDB = new Vector<>();
        Database.timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Database.nameTableTopLoiDai = "tob_qua_loi_dai";
        Database.nameTableThachDau = "tob_char_thach_dau";
        Database.tableNameWeek_month_year = new String[]{    "tob_log_use_luong"};
        Database.tableNamemonth_year = new String[]{"tob_nap_xai_luong"};
        Database.curWeekMonthYearDrop = "";
        Database.curMonthYearDrop = "";
        Database.r = new Random(System.currentTimeMillis());
        Database.name = new String[]{"vloz123", "kiepxike", "hylap", "ophongthan", "ocma", "okgaden", "hoangty72", "thienlong", "phuyeumai", "thuylong39", "bjnhnho113", "longhommm", "hotgirl75", "kissss", "hodaica", "huong6123", "tieuyen", "ccuibap", "ochuongheh", "nhoxat", "yeutaokg", "longo", "khungnha", "moonlove", "kimnguu", "iloveyou", "ranhoma", "rongchua", "kopato", "saubo", "thjetthu00", "ngoc84", "dajkaquy", "nututien", "pronqsh", "quan1986", "voodka", "kjmhoang", "buataa", "girlngheo", "athjenloj", "colong007", "tienhoangx", "songvedem", "icarus"};
        Database.namechar = new String[]{"luckystars@@", "colongzz", "xzhellozx", "fulever", "shdidaÂ ", "zzongnoizz", "acetatel", "Mexahoi", "anhkhoa15", "1thekybuon", "thuypro", "zzbaodaozz", "Zhieu7t7", "ZingproÂ ", "Dausi190Â ", "tecong02", "lonthjenhaÂ ", "thienson@@", "37rongÂ ", "emcodon01Â ", "kekhacmauÂ ", "vinhhuong", "dausi1966", "Dapdaups", "daothan35", "0ocbo0Â ", "zznokiazz@@", "maydetaoxuÂ ", "daovodanhÂ ", "cafedenÂ ", "donkihote@@", "rongconno1@@", "thjetthu@@", "hang9doi", "muavaban", "dytnhau24h", "pequynh00", "daophu6789", "hacbua6789Â ", "alacuopday", "minhxin", "gogogo6789", "mekiepvtc", "mecua5de", "police6789", "34queanh", "remix6789", "34hero6789Â ", "34hero", "x0son0xÂ ", "mexahoiÂ ", "ztanlongzÂ ", "remix6789", "laoliethoa", "zzongnoizz@@", "thuypro@@", "meocon12@@", "tutinhÂ ", "lag9", "daothan35Â ", "dapdaups", "Â zrose@@", "sockillÂ ", "girlvipvn@@", "longthjenha", "Zkieunuz@@", "girlbuidn", "boxiii@@", "nhiepphong@@", "yeuvotao", "boyanchoi1", "nlccvtc", "kingboss@@", "hotboyso5", "zpkgapkz", "phatieu1"};
        Database.charTemplatesOld = new CharTemplate[]{new CharTemplate(0, -1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(0, -1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(0, -1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, 1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(0, 1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, 1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(0, 1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, 1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)};
        Database.charTemplates = new CharTemplate[][]{{new CharTemplate(0, -1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 3, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 4, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 5, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 6, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}, {new CharTemplate(0, -1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10), new CharTemplate(1, -1, 7, 200, 200, 10, 10, 100, 100, 1, 10, 10, 10, 10, 10)}};
        Database.all_char_top = new Vector<>();
        Database.nameMons_indo = new String[]{"Landak", "Dalam", "Tetesan Air", "Ayam Gila", "Ular berbisa", "Bara Api", "Teko", "Setan kutu daun", "Tikus", "Bunga Iblis", "Ulat", "Hantu Api", "Mega Manikam", "Ayam", "Babi", "Kalajengking", "Penyu Merah", "Laba-laba Ungu", "Jamur Payung", "Beruang Iblis", "Lipan", "Ayam raksasa", "Ular raksasa", "Kupu-kupu raksasa", "Hantu", "Label kartu", "Tengkorak", "Harimau Raksasa", "Buaya", "Katak Iblis", "Jangkrik Raksasa", "Jantan", "Kerbau Gunung", "Bandit", "Bajak Laut", "Penyihir", "Monyet Kristal", "Berunding", "Kadal", "Kelabang Merah", "Kelabang Ungu", "Kelabang Hijau", "Ayam kelabu", "Statistik kematian", "Statistik gagal", "Api padat", "Kobra", "Singa", "Bekicot", "Kupu-kupu Coklat", "Mayat hidup", "Penghisap darah", "Gabus Lumpur", "Kepiting Besar", "Mata Satu", "Ikan Hantu", "Mata Merah", "Iblis bunga", "Tengkorak", "Badak", "Macan ungu", "Harimau hijau", "Macan merah", "Buaya merah", "Kepiting kuku", "Buaya hijau", "Kodok Hijau", "Katak Api", "Katak Hijau", "Jangkrik muda", "Jangkrik merah", "Jangkrik hitam", "Kerbau merah", "Manusia Batu", "Kerbau Hijau", "Siput", "Kepik", "Bandit 3", "Bajak Laut 1", "Jamur beracun", "Sayap oranye", "Burung elang", "Pilar Lengkung", "Zombie", "Batu", "Kapas", "Kayu", "Kulit", "Besi", "Manusia Salju", "Gadis Perkasa", "Anak Perkasa", "Mata Iblis", "Janggut Putih Sakti", "Kupu-kupu ungu", "Kupu-kupu biru"};
        Database.nameItem_indo = new String[]{"", "Baju Petani", "(Tanpa baju)", "Baju Kasar (Wanita)", "Baju Kasar (Pria)", "Baju Brokat (Wanita)", "Baju Brokat (Pria)", "Baju Katun (Wanita)", "Baju Katun (Pria)", "Baju Biasa sutra (Wanita)", "Baju Biasa sutra (Pria)", "Baju Sutra mewah (Wanita)", "Baju Sutra mewah (Pria)", "Baju Beludru (Wanita)", "Baju Beludru (Pria)", "Baju Bordir sutra (Wanita)", "Baju Bordir sutra (Pria)", "Baju Berkilau (Wanita)", "Baju Berkilau (Pria)", "Baju Mempesona (Wanita)", "Baju Mempesona (Pria)", "Baju Harimau (Wanita)", "Baju Harimau (Pria)", "Baju Singa (Wanita)", "Baju Singa (Pria)", "Baju Naga Emas (Wanita)", "Baju Naga Emas (Pria)", "Celana petani", "Celana pendek", "Celana kasar (Wanita)", "Celana kasar (Pria)", "Celana Brokat (Wanita)", "Celana Brokat (Pria)", "Celana Katun (Wanita)", "Celana Katun (Pria)", "Celana sutra biasal (Wanita)", "Celana sutra biasal (Pria)", "Celana Sutra mewah (Wanita)", "Celana Sutra mewah (Pria)", "Celana Beludru (Wanita)", "Celana Beludru (Pria)", "Celana Bordir sutra (Wanita)", "Celana Bordir sutra (Pria)", "Celana Berkilau (Wanita)", "Celana Berkilau (Pria)", "Celana Mempesona (Wanita)", "Baju Mempesona (Pria)", "Celana Harimau (Wanita)", "Celana Harimau (Pria)", "Celana Singa (Wanita)", "Celana Singa (Pria)", "Celana Naga Emas (Wanita)", "Celana Naga Emas (Pria)", "Ikat kepala", "Syal", "Topi Jubah berduri (Wanita)", "Topi Jubah berduri (Pria)", "Topi Bambu biru (Wanita)", "Topi Bambu biru (Pria)", "Topi Bambu Hitam (Wanita)", "Topi Bambu Hitam (Wanita)", "Topi Bambu hijau (Wanita)", "Topi Bambu hijau (Pria)", "Topi Biru kain (Wanita)", "Topi Biru kain (Pria)", "Topi Hitam tembaga (Wanita)", "Topi Hitam tembaga (Pria)", "Topi Tembaga merah (Wanita)", "Topi Tembaga merah (Pria)", "Topi besi hitam (Wanita)", "Topi besi hitam (Pria)", "Topi besi kelabu (Wanita)", "Topi besi kelabu (Pria)", "Topi Hati Sempurna (Wanita)", "Topi Hati Sempurna (Pria)", "Topi Pohon Pinus (Wanita)", "Topi Pohon Pinus (Pria)", "Topi Naga Emas (Wanita)", "Topi Naga Emas (Pria)", "Pedang Bambu", "Pedang kayu", "Pedang Tembaga", "Pedang Besi", "Pedang Tembang Lama", "Pedang Kesatuan", "Pedang Bintang", "Tombak Bambu", "Tombak kayu", "Tombak Tembaga", "Tombak Besi", "Tombak Buaya", "Tombak Kebijaksanaan", "Tombak Fenix", "Tongkat Bambu", "Tongkat kayu", "Tongkat Tembaga", "Tongkat besi", "Tongkat Nasib", "Tongkat Gravity", "Tongkat Mata Dewa", "Palu Bumi", "Palu kayu", "Palu Tembaga", "Palu Besi", "Palu Kepala Kerbau", "Palu Wajah Kuda", "Palu Arwah Bumi", "Busur Bambu", "Busur kayu", "Busur Tembaga", "Busur Besi", "Busur Bulan Sabit", "Busur Fenix Emas", "Busur Selatan", "Cincin Citrine", "Cincin Saphia", "Cincin Amber", "Cincin Turquoise", "Cincin Peridot", "Cincin Opal", "Cincin Giok", "Cincin Ruby", "Cincin Acient", "Cincin Aquamarine", "Cincin Ruby", "Cincin Moonstone", "Kalung Tembaga", "Kalung Koral", "Kalung kayu hijau", "Kalung Mata Kucing", "Kalung Platinum", "Kalung Kristal", "Kalung mutiara", "Kalung Amber", "Kalung perak", "Kalung Giok", "Batu Permata", "Kalung Fenix", "Sepatu kasar (Wanita)", "Sepatu Brokat (Wanita)", "Sepatu Katun (Wanita)", "Sepatu sutra biasa (Wanita)", "Sepatu mewah sutra (Wanita)", "Sepatu Beludru (Wanita)", "Sepatu Bordir sutra (Wanita)", "Sepatu Berkilau (Pria)", "Sepatu Mempesona (Wanita)", "Sepatu Harimau (Wanita)", "Sepatu Singa (Wanita)", "Sepatu Naga Emas (Wanita)", "Sarung tangan kasar (Wanita)", "Sarung tangan Brokat (Wanita)", "Sarung tangan Katun (Wanita)", "Sarung tangan sutra biasa (Wanita)", "Sarung tangan sutra mewah (Wanita)", "Sarung tangan Beludru (Wanita)", "Sarung tangan sutra bordir (Wanita)", "Sarung tangan Berkilau (Pria)", "Sarung tangan menyilaukan (Wanita)", "Sarung tangan Harimau (Wanita)", "Sarung tangan Singa (Wanita)", "Sarung tangan Naga Emas (Wanita)", "Permata Giok", "Permata Perak", "Permata Sempurna", "Permata Menara Emas", "Permata Kupu-kupu", "Permata Bunga biru", "Permata Peri", "Permata merah bundar", "Permata Purnama", "Permata Sakura", "Permata Aprikot", "Permata Peri Naga", "Pedang Bayu Biru", "Pedang Itik Lengkap", "Pedang Merah Darah", "Pedang Bunga", "Pedang Besi Hitam", "Pedang Titisan", "Pedang Macan", "Pedang Walet suci", "Pisau Iblis", "Pisau Pemotongan Bulan", "Pisau Belah Topan", "Pisau Mukjizat", "Pisau Air Mata Mutiara", "Pisau Tornado", "Pisau Baja Bersama", "Pisau Neraka", "Tongkat Junjung Metalik", "Tongkat Sakura", "Tongkat Telepati", "Tongkat Racun", "Tongkat Angry Angin", "Tongkat Bintang Qurban", "Tongkat Licin berkarat", "Tongkat Penangkap Angin", "Palu Tulang", "Palu Top Clumber", "Palu Delapan Ujung", "Palu Lima Bintang", "Palu Guntur Api", "Palu Raksa Keajaiban", "Palu Pisah Inti", "Palu Api Naga", "Busur Titik depan", "Busur Walet", "Busur Terbang Fenix", "Busur Hijau Limo", "Busur Raksa Keajaiban", "Busur Kupu-kupu", "Busur Bersayap Terbang", "Busur Surga", "Pedang Tembang Lama (Sewa)", "Pedang Bersatu (Sewa)", "Pedang Bintang (Sewa)", "Pedang Angin Biru (Sewa)", "Pedang Lengkap Drake (Sewa)", "Pedang Tujuh Rantai (Sewa)", "Pedang Bunga (Sewa)", "Pedang besi hitam (Sewa)", "Pedang titisan (Sewa)", "Pedang macan tutul (Sewa)", "Pisau Gerak (Sewa)", "Pisau Intel (Sewa)", "Pisau Fenix (Sewa)", "Pisau Setan Kepala (Sewa)", "Pisau Pemotongan Bulan (Sewa)", "Pisau Topan Breaker (Sewa)", "Pisau Mujizat (Sewa)", "Pisau Pearl Air Mata (Sewa)", "Pisau Tornado (Sewa)", "Pisau Baja Bersama (Sewa)", "Pena Takdir", "Tongkat Serenade Call (Sewa)", "Tongkat Allah Eye (Sewa)", "Tongkat Metalic Tribute (Sewa)", "Tongkat Sakura (Sewa)", "Tongkat telepati (Sewa)", "Tongkat Racun (Sewa)", "Tongkat Angry Angin (Sewa)", "Tongkat Bintang Qurban (Sewa)", "Tongkat Slick terkorupsi (Sewa)", "Palu Buffalo Kepala (Sewa)", "Palu Kuda Wajah (Sewa)", "Palu Bumi Roh (Sewa)", "Palu Tulang (Sewa)", "Palu Top Clumber (Sewa)", "Palu Delapan Ujung (Sewa)", "Palu Five Star (Sewa)", "Palu Guntur Api (Sewa)", "Palu Raksa Keajaiban (Sewa)", "Palu Inti Seperater (Sewa)", "Busur Bulan Sabit (Sewa)", "Busur Fenix Emas (Sewa)", "Busur Selatan (Sewa)", "Busur Titik depan (Sewa)", "Busur Walet (Sewa)", "Busur Terbang Fenix (Sewa)", "Busur Hijau Li (Sewa)", "Busur Raksa Keajaiban (Sewa)", "Busur Kupu-kupu (Sewa)", "Busur Bersayap Terbang (Sewa)", "Baju Peri", "Baju Naga King", "Baju Old guru", "Gaun panjang", "Baju Biru Sempurna (Wanita)", "Baju Biru Sempurna (Pria)", "Baju Marun (Wanita)", "Baju Marun (Pria)", "Baju Banyak (Wanita)", "Baju Banyak (Pria)", "Baju Ular Suci (Wanita)", "Baju Ular Suci (Pria)", "Baju Berwarna (Wanita)", "Baju Berwarna (Pria)", "Baju Setan Jiwa (Wanita)", "Baju Setan Jiwa (Pria)", "Baju Gelap macan tutul (Wanita)", "Baju Gelap macan tutul (Pria)", "Baju Es Roh (Wanita)", "Baju Es Roh (Pria)", "Baju Tujuh Harta (Wanita)", "Baju Tujuh Harta (Pria)", "Baju Lotus (Wanita)", "Baju Lotus (Pria)", "Baju Perak Sutra (Wanita)", "Baju Perak Sutra (Pria)", "Baju Abadi (Wanita)", "Baju Abadi (Pria)", "Celana Biru Sempurna (Wanita)", "Celana Biru Sempurna (Pria)", "Celana Marun (Wanita)", "Celana Marun (Pria)", "Celana cukup (Wanita)", "Celana cukup (Pria)", "Celana Ular Suci (Wanita)", "Celana Ular Suci (Pria)", "Celana berwarna (Wanita)", "Celana berwarna (Pria)", "Celana Setan Jiwa (Wanita)", "Celana Setan Jiwa (Pria)", "Celana Gelap macan tutul (Wanita)", "Celana Gelap macan tutul (Pria)", "Celana Roh es (Wanita)", "Celana Roh es (Pria)", "Celana Tujuh Harta (Wanita)", "Celana Tujuh Harta (Pria)", "Celana Lotus (Wanita)", "Celana Lotus (Pria)", "Celana Perak Sutra (Wanita)", "Celana Perak Sutra (Pria)", "Celana Abadi (Wanita)", "Celana Abadi (Pria)", "Topi Biru Sempurna (Wanita)", "Topi Biru Sempurna (Pria)", "Topi Marun (Wanita)", "Topi Marun (Pria)", "Topi Cukup (Wanita)", "Topi Cukup (Pria)", "Topi Ular Suci (Wanita)", "Topi Ular Suci (Pria)", "Topi Berwarna (Wanita)", "Topi Berwarna (Pria)", "Topi Setan Jiwa (Wanita)", "Topi Setan Jiwa (Pria)", "Topi Gelap macan tutul (Wanita)", "Topi Gelap macan tutul (Pria)", "Topi Semangat Beku (Wanita)", "Topi Semangat Beku (Pria)", "Topi Tujuh Harta (Wanita)", "Topi Tujuh Harta (Pria)", "Topi Lotus (Wanita)", "Topi Lotus (Pria)", "Topi Perak Sutra (Wanita)", "Topi Perak Sutra (Pria)", "Topi Abadi (Wanita)", "Topi Abadi (Pria)", "Sepatu Biru Sempurna", "Sepatu Marun", "Sepatu yang cukup", "Sepatu Ular Suci", "Sepatu berwarna", "Sepatu Iblis Jiwa", "Sepatu Gelap macan tutul", "Sepatu Semangat Beku", "Sepatu Tujuh Harta", "Sepatu Lotus", "Sepatu Perak Sutra", "Sepatu Abadi", "Sarung tangan Biru Sempurna", "Sarung tangan Marun", "Sarung tangan yang cukup", "Sarung tangan Ular Suci", "Sarung tangan berwarna", "Sarung tangan Iblis Jiwa", "Sarung tangan Gelap macan tutul", "Sarung tangan Roh Es", "Sarung tangan Tujuh Harta", "Sarung tangan Lotus", "Sarung tangan Perak Sutra", "Sarung tangan Abadi", "Cincin Batu Akik", "Cincin Ular Suci", "Cincin Pearl", "Cincin Batu Perak", "Cincin Koral", "Cincin Lima Warna", "Cincin Badak Hitam", "Cincin Gajah", "Cincin Yin Yang", "Cincin Mentari Bulan", "Cincin Ganda Fenix", "Cincin Ganda Naga", "Kalung batu", "Kalung Ular Suci", "Kalung mutiara", "Kalung Perak Gem", "Kalung Koral", "Kalung Lima Warna", "Kalung Badak Hitam", "Kalung gajah", "Kalung Yin Yang", "Kalung Mentari Bulan", "Kalung Ganda Fenix", "Kalung Ganda Naga", "Batu berlian", "Permata Ular Suci", "Permata Pearl", "Permata Perak Gem", "Permata Koral", "Permata Lima Warna", "Permata Badak Hitam", "Permata Gajah", "Permata Yin Yang", "Permata Mentari Bulan", "Permata Ganda Fenix", "Permata Ganda Naga", "Pedang Merak", "Pedang Sekilas", "Pedang Api Nirwana", "Pedang Bunga Hutan", "Pedang Ekor Rubah", "Pedang Laut", "Pedang emas", "Pedang Timah", "Pedang Hitam Putih", "Pedang Bunga Salju", "Pedang Kekasih", "Pedang Opal", "Pedang Tempur", "Tombak kuda", "Tombak Bara", "Tombak Gigi Serigala", "Pisau Sepuluh Palang", "Pisau Termahsyur", "Pisau Standard", "Pisau Petugas", "Pisau Bulan Suram", "Pisau Angin Biru", "Pisau Kerajaan", "Pisau Penghargaan", "Pisau Kaisar", "Pisau Pasir", "Tongkat berat", "Tongkat Walet Terbang", "Tongkat Langit", "Tongkat Timah", "Tongkat Angsa Terbang", "Tongkat Bola Perak", "Tongkat Rantai", "Tongkat Segi empat", "Tongkat Biru Firman", "Tongkat Cerdas", "Tongkat Merah Mempesona", "Tongkat Keadilan", "Tongkat Penggetar", "Palu Cerah", "Palu Setengah Wajah", "Palu Beras Perak", "Palu Tembaga", "Palu Emas Kain", "Palu Emas Tembaga", "Palu Rusuh", "Palu Lengan Panjang", "Palu Wajah Ganda", "Palu Deary", "Palu Syal Beludru", "Palu Buddha", "Palu Lingkaran", "Palu Surai Ayam", "Busur Fenix biru", "Busur Rose", "Busur Hutan", "Busur Univer", "Busur iblis biru", "Busur Iblis Serigala", "Busur Lapis", "Busur spektakuler", "Busur Benang Emas", "Busur Biru Bunga", "Busur Tulang", "Busur Nuri", "Cangkul biasa", "Cangkul Baik", "Baju Menari Wing (Wanita)", "Baju Menari Wing (Pria)", "Baju Cahaya Ungu (Wanita)", "Baju Cahaya Ungu (Pria)", "Baju Beruang besar (Wanita)", "Baju Beruang besar (Pria)", "Baju Walet Terbang (Wanita)", "Baju Walet Terbang (Pria)", "Celana Naga terbang (Wanita)", "Celana Naga terbang (Pria)", "Celana Cahaya ungu (Wanita)", "Celana Cahaya ungu (Pria)", "Celana Beruang besar (Wanita)", "Celana Beruang besar (Pria)", "Celana Terbang layang (Wanita)", "Celana Terbang layang (Pria)", "Topi Naga terbang (Wanita)", "Topi Naga terbang (Pria)", "Topi Ungu terang (Wanita)", "Topi Ungu terang (Pria)", "Topi Beruang besar (Wanita)", "Topi Beruang besar (Pria)", "Topi Terbang layang (Wanita)", "Topi Terbang layang (Pria)", "Sepatu Naga terbang", "Sepatu Ungu terang", "Sepatu Beruang besar", "Sepatu Walet Terbang", "Sarung tangan Naga terbang", "Sarung tangan Ungu terang", "Sarung tangan Beruang besar", "Sarung tangan Walet Terbang", "Cincin Naga terbang", "Cincin Ungu terang", "Cincin Beruang besar", "Cincin Walet Terbang", "Kalung Naga Terbang", "Kalung Cahaya ungu", "Kalung Beruang besar", "Kalung Walet Terbang", "Syal Merah", "Baju Ksatria hutan", "Topi Keberanian", "Topi Jantung Es", "Topi Dinamis", "Topi Dewata", "Topi Ruang", "Topi Ritual", "Topi Bulu", "Topi Raksasa", "Topi Kuat", "Topi Berawan", "Topi Kuat", "Topi Baik", "Pelindung Keberanian", "Pelindung Jantung Es", "Pelindung Dinamis", "Pelindung Dewata", "Pelindung Ruang", "Pelindung Ritual", "Pelindung Bulu", "Pelindung Raksasa", "Pelindung kokoh", "Pelindung Berawan", "Pelindung kekuatan", "Pelindung sempurna", "Pelana Keberanian", "Pelana Jantung Es", "Pelana Dinamis", "Pelana Dewata", "Pelana Ruang", "Pelana Ritual", "Pelana Bulu", "Pelana Raksasa", "Pelana kuat", "Pelana Berawan", "Pelana kuat", "Pelana Baik", "Pelana Keberanian", "Pelana Jantung Es", "Pelana Dinamis", "Pelana Dewata", "Pelana Ruang", "Pelana Ritual", "Pelana Bulu", "Pelana Raksasa", "Pelana kuat", "Pelana Berawan", "Pelana kuat", "Pelana Baik", "Pedal Keberanian", "Pedal Jantung es", "Pedal dinamis", "Pedal Dewata", "Pedal Ruang", "Pedal Ritual", "Pedal Bulu", "Pedal Raksasa", "Pedal kuat", "Pedal Berawan", "Pedal kuat", "Pedal Baik", "Permata Naga Terbang", "Permata Ungu terang", "Permata Beruang besar", "Permata Walet Terbang", "Kalung Naga Terbang", "Baju Peri Bulan (Wanita)", "Baju Dracula (Pria)", "Kemeja Lelaki Bulan", "Orang bodoh", "Topi Penyihir", "Baju Noel (Wanita)", "Baju Noel (Pria)", "Topi Noel", "Gaun panjang (Wanita)", "Gaun panjang (Pria)", "Mahkota", "Topi Magneta", "Topi General", "Topi Kapten", "Sepatu kasar (Pria)", "Sepatu Brokat (Pria)", "Sepatu Kapas (Pria)", "Sepatu sutra biasal (Pria)", "Sarung tangan kasar (Pria)", "Sarung tangan Brokat (Pria)", "Sarung tangan Katun (Pria)", "Sarung tangan sutra biasa (Pria)", "Sepatu mewah sutra (Pria)", "Sepatu Beludru (Pria)", "Sepatu Bordir sutra (Pria)", "Sepatu Berkilau (Pria)", "Sarung tangan mewah sutra (Pria)", "Sarung tangan Beludru (Pria)", "Sarung tangan Bordir sutra (Pria)", "Sarung tangan Berkilau (Pria)"};
        Database.name_att_in = new String[]{"", "Serangan", "Hewan", "Menghindar", "Benar sekali", "Kritis", "Kesehatan", "Hantu Utama", "Indikator 7", "Indikator 8", "Indeks 9", "Tambah kekuatan", "Tambah kelincahan", "Tambah semangat", "Tambah kesehatan", "Kebutaan", "Lapisan Es", "Meracun", "Membius", "Fosil", "Melambat", "Anti pelambat", "Anti racun", "Anti kebutaan", "Anti beku", "Anti setrum", "Anti fosil", "Meningkatkan hit 2x lipat", "Tambah penurunan kadar harta", "Bahan kurangi kerusakan", "Kurangi kerusakan hantu", "Meningkatkan serangan", "Baja", "Respon Kerusakan", "Tingkatkan HP", "Tingkatkan MP", "Tingkatkan kekuatan", "Tingkatkan kelincahan", "Tingkatkan moral", "Tingkatkan kesehatan", "Tingkatkan skil", "Tingkatkan fatal", "Tingkatkan kritis", "berisi", "1 Jurus tambahan", "2 Jurus tambahan", "3 Jurus tambahan", "4 Jurus tambahan", "5 Jurus tambahan", "Jurus 6 ditambah", "Jurus 7 ditambah", "Jurus 8 ditambah", "9 Jurus tambahan", "Jurus 10 ditambah", "Jurus 11 ditambah", "Jurus 12 ditambah", "Jurus 13 ditambah", "Jurus 14 ditambah", "Jurus 15 ditambah", "Tambah", "Tambah pelindung ajaib", "Tambah pelindung", "Abaikan Serangan Fantom", "Lewati Serangan Hewan", "Tambah alat perlindungan", "Tambah alat perlindungan", "Klik pertama"};
        Database.nameServer = new String[]{"lc", "tl", "tt", "hm", "ms", "km", "cb", "hl", "cb", "cb"};
    }
    

   
    
    public void updateCountBoss(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO top_boss (charname, count) VALUES (?, 1) " +
                        "ON DUPLICATE KEY UPDATE count = count + 1";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, charname);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) Database.connPool.free(conn);
            } catch (Exception ignored) {}
        }
    }

    public void saveCooking(Cooking cook, int id) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "update tob_nau_banh set listchar=?,timecook=?,water=?,chamnc=?,listCharGift=? where id=" + id;
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, cook.getListChar());
            pre.setInt(2, cook.timeCook);
            pre.setInt(3, cook.water);
            pre.setInt(4, cook.timeChamnc);
            pre.setString(5, cook.getListCharGift());
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetCooking() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "update tob_nau_banh set listchar='',listCharGift='',timecook=60,water=0,chamnc=0";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }




    public Cooking loadAllCharCooking(int id) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_nau_banh where id=" + id;
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                Cooking cook = new Cooking();
                cook.timeChamnc = rs.getByte("chamnc");
                cook.timeCook = rs.getByte("timecook");
                cook.water = rs.getByte("water");
                cook.setListCharCooking(rs.getString("listchar"));
                cook.setListCharGift(rs.getString("listCharGift"));
                cook.type = (byte) id;
                return cook;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void updateSoLuongChoiBan() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update tob_x2exp set banchoi=" + Map.totalChoiBan;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateLuongNap(int luong, Char player) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update `tob_char` set `topNap` = `topNap` + '" + luong + "' where charname = '" + player.charname + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadTimeX2Server() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        if (Database.agent == null) {
            Database.agent = new Vector<>();
        } else {
            Database.agent.removeAll(Database.agent);
        }
        try {
            final String sql = "select * from tob_x2exp";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            Char.timex2.removeAllElements();
            Char.timex3.removeAllElements();
            Char.timex50.removeAllElements();
            while (rs.next()) {
                try {
                    Map.tradePet = rs.getByte("tradepet");
                } catch (Exception ignored) {
                }
                String d = rs.getString("dayopenx2");
                if (d != null && !d.equals("")) {
                    String[] a = Char.split(d, "|");
                    for (String s : a) {
                        Char.timex2.add(s);
                    }
                }
                d = rs.getString("dayopenx3");
                if (d != null && !d.equals("")) {
                    String[] b = Char.split(d, ">");
                    String[] a2 = Char.split(b[0], "|");
                    for (String s : a2) {
                        Char.timex3.add(s);
                    }
                    Char.Lvx3Exp = Integer.parseInt(b[1]);
                }
                d = rs.getString("dayopenx50");
                if (d != null && !d.equals("")) {
                    String[] a = Char.split(d, "|");
                    for (String s : a) {
                        Char.timex50.add(s);
                    }
                }
                try {
                    Map.totalChoiBan = rs.getByte("banchoi");
                } catch (Exception ignored) {
                }
                try {
                    Map.pause = (rs.getByte("pause") == 1);
                } catch (Exception ignored) {
                }
                try {
                    Map.pauseSellTown = (rs.getByte("pauseT") == 1);
                } catch (Exception ignored) {
                }
                try {
                    Char.isBaotriVxmm = (rs.getByte("vxmm") == 1);
                } catch (Exception ignored) {
                }
                try {
                    Map.stopGiveMoney = (rs.getByte("gopbang") == 1);
                } catch (Exception ignored) {
                }
                try {
                    RealController.maquee2 = rs.getString("infoalert");
                    if (RealController.maquee2 == null) {
                        RealController.maquee2 = "";
                        RealController.dayAlertMarquest2 = new String[]{""};
                    } else {
                        String[] info = Char.split(RealController.maquee2, "|");
                        RealController.dayAlertMarquest2 = new String[info.length - 1];
                        RealController.maquee2 = info[info.length - 1];
                        for (int i = 0; i < info.length - 1; ++i) {
                            RealController.dayAlertMarquest2[i] = info[i];
                        }
                    }
                } catch (Exception ignored) {
                }
                String[] badword = Char.split(rs.getString("badword"), ",");
                if (badword.length > 0) {
                    BadWord.list = badword;
                }
                Char.onOffThamdinh = rs.getByte("thamdinh");
                try {
                    Char.onOffTime180 = rs.getByte("time180");
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    
    

    public static boolean haveBadWord(String infoCheck) {
        infoCheck = infoCheck.toLowerCase();
        for (int i = 0; i < Database.badWord.size(); ++i) {
            String st = Database.badWord.get(i);
            if (infoCheck.indexOf(st) != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isFullConnection() {
        return Database.connPool.countConnection >= Database.connPool.maxConn;
    }

    public void updateStateReceiveGiftEventOutGame(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "update tob_qua set qua=1 WHERE charname = ?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public byte checkAddGiftEventGame(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_qua WHERE charname = ? and qua=0";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getByte("typeQua");
            }
        } catch (Exception e) {
            Logger.logError(e, "");
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public boolean baotriCMD() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM 5h_systems WHERE command = ? and status = 0";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, "restart");
            rs = pre.executeQuery();
            if (rs.next()) {
                updateBaoTriCMD();
                return true;
            }
        } catch (Exception e) {
            Logger.logError(e, "");
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return false;
    }

    public void updateBaoTriCMD() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "update 5h_systems set status=1 WHERE command = ?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, "restart");
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public boolean checkAddGiftEventOutGame(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_qua WHERE charname = ? and qua=0";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            Logger.logError(e, "");
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean checkReceiveGiftEventOutGame(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_qua WHERE charname = ?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return false;
    }

    public void sendNotiLogin(Char p, Message m) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from 5h_notify";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("owner");
                String content = rs.getString("content");
//                m = new Message(27);
//                m.dos.writeShort(p.id);
//                m.dos.writeUTF(content);
//                p.sendMessage(m);
                p.sendMessage(MessageCreator.createServerAlertAutoOffMessage(content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public byte checkAddGiftQuaLoiDai(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_qua_loi_dai WHERE charname = ? and qua=0";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getByte("typeQua");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public void backupTobThachDau() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "insert into " + Database.nameTableThachDau + " SELECT * FROM tob_char_thach_dau";
            System.err.println(sql);
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getTopQuaLoiDai(int idNhom) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tob_char_thach_dau where nhom=" + idNhom + " and pointrank>=100 order by pointrank desc";
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            int count = 0;
            while (rs.next()) {
                this.addTopQuaLoiDai(rs.getString("charname"), idNhom, (count == 0) ? 1 : 2, rs.getInt("pointrank"));
                ++count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addTopQuaLoiDai(String name, int nhom, int typeQua, long diem) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "insert into " + Database.nameTableTopLoiDai + " (charname,nhom,typeQua,diem) values('" + name + "'," + nhom + "," + typeQua + "," + diem + ")";
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateStateReceiveGiftQuaLoiDai(String charname) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "update tob_qua_loi_dai set qua=1 WHERE charname = ?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadBadWords() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Database.badWord.removeAllElements();
        try {
            final String sql = "select * from bad_word";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Database.badWord.add(rs.getString("word").toLowerCase());
            }
        } catch (Exception e) {
            System.out.println("KHONG TIM THAY DATABASE");
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
      
    }

    public void loadPercentDrop() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from 5h_systems where command = 'percentDrop'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Monster.percentDrop = rs.getInt("status");
                System.out.println("percentDrop TOAN SERVER = " + Monster.percentDrop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadTileDrop() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from 5h_systems where command = 'tileDrop'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Monster.tileDrop = rs.getInt("status");
                System.out.println("tileDrop TOAN SERVER = " + Monster.tileDrop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadTangNap() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from 5h_systems where command = 'tangnap'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TileNap = rs.getInt("status");
                System.out.println("TANG NAP TOAN SERVER = " + TileNap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadTangExp() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from 5h_systems where command = 'tangexp'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Char.TangEXP = rs.getInt("status");
                System.out.println("TANG EXP TOAN SERVER = " + Char.TangEXP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public String loadSystemDB(String params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String db = "";
        try {
            final String sql = "select * from 5h_systems where command = '" + params + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                db = rs.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return db;
    }

    public void loadAllXosoLow() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from nin_xoso_low";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("charname");
                int money = rs.getInt("money");
                int win = rs.getInt("win");
                int moneyWin = rs.getInt("moneywin");
                int moneyBid = rs.getInt("moneyBid");
                if (money > 0) {
                    PlayerXoso pxs = new PlayerXoso();
                    pxs.name = name;
                    pxs.money = money;
                    Char.ALL_PLAYER_XOSO_low.put(name, pxs);
                    Char.MONEY_XOSO_low += money;
                }
                if (win == 1) {
                    Char.moneyCharXosoBid_low = moneyBid;
                    Char.moneyCharXosowin_low = moneyWin;
                    Char.charNameXosoWin_low = name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetXosoLow(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "update nin_xoso_low set money=0,win=0";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        this.updateWinXosoLow(charnameWin, moneyBid, moneyWin, moneyTaxWin);
    }

    public void updateWinXosoLow(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update nin_xoso_low set win=1,moneyBid=" + moneyBid + ",moneywin=" + moneyWin + " where charname='" + charnameWin + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        this.saveOrtherLog("", charnameWin, "chien thang vong quay thuong " + moneyWin + ", tax: " + moneyTaxWin, "xoso");
        this.updateTaxWinXosoLow(moneyTaxWin);
    }

    public void updateTaxWinXosoLow(int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update nin_xoso_low set moneyTax=moneyTax+" + moneyTaxWin;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void doAddUpdatePlayerXosoLow(String charname, int money) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into nin_xoso_low(charname,money) values ('" + charname + "'," + money + ") ON DUPLICATE KEY UPDATE money=" + money;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }


  
    public void loadAllXoso() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from nin_xoso";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("charname");
                int money = rs.getInt("money");
                int win = rs.getInt("win");
                int moneyWin = rs.getInt("moneywin");
                int moneyBid = rs.getInt("moneyBid");
                if (money > 0) {
                    PlayerXoso pxs = new PlayerXoso();
                    pxs.name = name;
                    pxs.money = money;
                    Char.ALL_PLAYER_XOSO.put(name, pxs);
                    Char.MONEY_XOSO += money;
                }
                if (win == 1) {
                    Char.moneyCharXosoBid = moneyBid;
                    Char.moneyCharXosowin = moneyWin;
                    Char.charNameXosoWin = name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetXoso(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "update nin_xoso set money=0,win=0";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        this.updateWinXoso(charnameWin, moneyBid, moneyWin, moneyTaxWin);
    }

    public void updateWinXoso(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update nin_xoso set win=1,moneyBid=" + moneyBid + ",moneywin=" + moneyWin + " where charname='" + charnameWin + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        this.saveOrtherLog("", charnameWin, "chien thang vong quay " + moneyWin + ", tax: " + moneyTaxWin, "xoso");
        this.updateTaxWinXoso(moneyTaxWin);
    }

    public void updateTaxWinXoso(int moneyTaxWin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update nin_xoso set moneyTax=moneyTax+" + moneyTaxWin;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void doAddUpdatePlayerXoso(String charname, int money) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into nin_xoso(charname,money) values ('" + charname + "'," + money + ") ON DUPLICATE KEY UPDATE money=" + money;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }


    public void loadNameAttributeItem() {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from data_attribute order by id";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            byte i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                if (TeamServer.isServerIndo()) {
                    name = rs.getString("namein");
                }
                ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.add(new NameAttributeItem(i, name, rs.getByte("ispercent"), rs.getByte("idcolor")));
                ++i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

   

    public static void checkFirstNap(Char p, Connection conn, boolean online) {
        ResultSet rs = null;
        Statement stm = null;
        try {
            String sql = "select * from tob_get_board_nap where charname='" + p.charname + "'";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if (!rs.next() && online) {
                p.doReceiveGifNap();
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
    }

    public void getTopNapLuong() {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = Database.connPoolNap.getConnection();
            stm = conn.createStatement();
            final String sql = "select * from tob_get_board_nap where dateGet>='2015-02-14 12:00:00' and dateGet<='2015-03-02 10:00:00' order by dateGet";
            rs = stm.executeQuery(sql);
            Hashtable<String, CharData> allchar = new Hashtable<>();
            Hashtable<String, CharData> allchar2 = new Hashtable<>();
            int count = 0;
            int gold = 0;
            while (rs.next()) {
                ++count;
                String data = rs.getString("info");
                if (data.indexOf("luong") > -1) {
                    String charname = rs.getString("charname").toLowerCase();
                    String[] d = Char.split(data, "luong: ");
                    int value = 0;
                    if (d[1].indexOf("X>") > -1) {
                        value = Integer.parseInt(d[1].substring(0, d[1].indexOf("X>")).trim());
                    } else {
                        value = Integer.parseInt(d[1].trim());
                    }
                    CharData c = allchar.get(charname);
                    if (c == null) {
                        c = new CharData();
                        allchar.put(c.charname = charname, c);
                    }
                    CharData charData = c;
                    charData.gold += value;
                    gold += value;
                    if (c.gold < 10000 || allchar2.get(charname) != null || allchar2.size() >= 50) {
                        continue;
                    }
                    allchar2.put(charname, c);
                }
            }
            System.out.println("TOG SO " + count + " >> " + gold);
            int i = 0;
            for (CharData c2 : allchar2.values()) {
                System.out.println("TOP 50 stt " + i + ": " + c2.charname + " > " + c2.gold);
                ++i;
            }
            i = 0;
            int max = 0;
            CharData topnap = null;
            for (CharData c3 : allchar.values()) {
                if (max < c3.gold) {
                    max = c3.gold;
                    topnap = c3;
                }
            }
            if (topnap != null) {
                System.err.println(topnap.charname + " > " + topnap.gold);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPoolNap.free(conn);
        } catch (Exception ignored) {
        }
        try {
            Thread.sleep(10000L);
            System.exit(0);
        } catch (Exception ignored) {
        }
    }

    public String getFirstNap(Char player) {
        String kq = "";
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stm = conn.createStatement();
            String sql = "select * from tob_first_nap where charname='" + player.charname + "'";
            rs = stm.executeQuery(sql);
            long oldPotion = 0L;
            int oldluong = 0;
            try {
                oldPotion = player.getxu();
                oldluong = player.getLuong();
            } catch (Exception ignored) {
            }
            if (rs.next()) {
                int xu = rs.getInt("xu");
                int luong = rs.getInt("luong");
                player.addXu(xu, "getFirstNap db");
                player.addLuong(luong);
                if (player.firstNapMoney == 0) {
                    player.firstNapMoney = 1;
                }
                String[] info = {"ChÃºc má»«ng báº¡n nháº­n Ä‘Æ°á»£c ", "You receive more ", "Kamu Akan Mendapatkan 2X Top Up "};
                String[] info2 = {" cho láº§n náº¡p Ä‘áº§u tiÃªn", " for the first insert coins", " Di Saat Top Up Pertama Kali"};
                if (xu > 0 && luong > 0) {
                    kq = info[0] + xu + " xu vÃ  " + luong + " lÆ°á»£ng" + info2[0];
                } else if (xu > 0) {
                    kq = info[0] + xu + " xu " + info2[0];
                } else if (luong > 0) {
                    kq = info[0] + luong + " lÆ°á»£ng " + info2[0];
                }
                String sqlUpdate = "update tob_first_nap set xu=0,luong=0 where charname='" + player.charname + "'";
                stm.executeUpdate(sqlUpdate);
                return kq;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return kq;
    }

    public boolean updateMoneyTopThidau(String name, int luong, int xu) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = Database.connPoolNap.getConnection();
            final String sql = "update board_naptien set luong=luong+?,xu=xu+? where username=?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, luong);
            pre.setInt(2, xu);
            pre.setString(3, name);
            return pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void insertMoneyTopThiDau(String name, int luong, int xu) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = Database.connPoolNap.getConnection();
            if (!this.updateMoneyTopThidau(name, luong, xu)) {
                final String sql = "insert into board_naptien(username,luong,xu) values(?,?,?) ON DUPLICATE KEY UPDATE luong=luong+?,xu=xu+?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, name);
                pre.setInt(2, luong);
                pre.setInt(3, xu);
                pre.setInt(4, luong);
                pre.setInt(5, xu);
                pre.execute();
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPoolNap.free(conn);
        } catch (Exception ignored) {
        }
    }

   

    public void newDoGetGiftCode(String text, Char player) {

        int userId = player.userID;
        if (!check_active_putcode(userId)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Vui lÃ²ng kÃ­ch hoáº¡t tÃ i khoáº£n Ä‘á»ƒ sá»­ dá»¥ng." + userId, ""));
            return;
        }

        Connection conn = null;
        PreparedStatement pre1 = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            pre1 = conn.prepareStatement("select * from giftcode_log where giftcode = '" + text + "' AND player = '" + player.charname + "';");
            rs = pre1.executeQuery();
            if (rs.next()) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Báº¡n Ä‘Ã£ nháº­p mÃ£ quÃ  táº·ng nÃ y rá»“i.", ""));
                return;
            }
            if (TeamServer.CodeTest) {
           switch (text) {

            case "petmax":
                int[] idpet = new int[]{693, 698, 708, 703, 713};
                int[] idtrungpet = new int[]{684, 685, 687, 686, 688};
                int indexPet = 3;
                if (indexPet >= 0 && indexPet < idpet.length) {
                    player.doCreatePetMax(idpet[indexPet], 5, idtrungpet[indexPet], 1008000000);
                    player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c Pet Max", ""));
                } else {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Loáº¡i pet khÃ´ng há»£p lá»‡ (0-4)", ""));
                }
                break;

              case "colon":
                  player.addDanhHieu(8935, 0);
                

                  try {
                      player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                      player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                  } catch (IOException ex) {
                  }
                  player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c danh hiá»‡u: " + DanhHieu.getName(8935), ""));

                  return;
              case "rongbang":
                  player.createRongLon(3, true);
                  Database.instance.saveOrtherLog("", player.charname, "CODE: " + text, "putGiftCode");
                  pre1.execute("insert into giftcode_log set player = '" + player.charname + "', giftcode = '" + text + "', item = '" + 0 + "', xu = " + 0 + ", luong = " + 0 + ", luongK = " + 0 + ";");
                  return;
            case "alldanhhieu":
                  // Add all titles from DanhHieu class
                  int[] allTitles = {
                      8767, 8768, 8769, 8770, 8771, 8772, 8773, 8774, 8775, 8776, 8777,
                      8778, 8779, 8785, 8786, 8787, 8788, 8900, 8901, 8902, 8903, 8904,
                      8905, 8906, 8907, 8908, 8909, 8910, 8911, 8924, 8925, 8935, 8956, 8957, 8983 ,8984,8985,8986,8987,8988,8989,8990,8991,8992,8993,8994,8995,8996,8999
                  };
                  
                  for (int titleId : allTitles) {
                      player.addDanhHieu(titleId, 0);
                  }
                  
                  try {
                      player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                      player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                  } catch (IOException ex) {
                  }
                  
                  player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c táº¥t cáº£ danh hiá»‡u!", ""));
                  
                  Database.instance.saveOrtherLog("", player.charname, "CODE: " + text, "putGiftCode");
                  pre1.execute("insert into giftcode_log set player = '" + player.charname + "', giftcode = '" + text + "', item = '" + 0 + "', xu = " + 0 + ", luong = " + 0 + ", luongK = " + 0 + ";");
                  
                  return;

             
              case "xuluong":
                        int xu = 2_000_000_000;
                        int luong = 20_000_000;
                        boolean canGetXu = player.getxu() < xu;
                        boolean canGetLuong = player.getLuong() < luong;

                        if (canGetXu) {
                            player.addXu(xu, "board_created db");
                        }
                        if (canGetLuong) {
                            player.addLuong(luong);
                            player.lockLuong += luong;
                        }

                        String msg = "Báº¡n vá»«a nháº­n Ä‘Æ°á»£c ";
                        if (canGetXu && canGetLuong) {
                            msg += "20 tá»‰ xu vÃ  20 triá»‡u lÆ°á»£ng.";
                        } else if (canGetXu) {
                            msg += "20 tá»‰ xu.";
                        } else if (canGetLuong) {
                            msg += "20 triá»‡u lÆ°á»£ng.";
                        }

                        player.sendMessage(MessageCreator.createServerAlertMessage(msg, ""));
                        return;

              case "hoakylan":
                  player.map.doCreateHoaKyLan(player, 683, 525948L);
                  player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c Há»a Ká»³ LÃ¢n.", ""));
                  return;
               case "lansu":
                  player.map.doCreateLanSuVu(player, 882, 525948L);
                  player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c LÃ¢n SÆ°.", ""));
                  return;
              case "duongkhang":
                  player.createHeo(0, false);
                  player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c ÄÆ°Æ¡ng Khang.", ""));
                  return;

              case "phiphong":
                  ItemLuckyDraw.createtemCoat(750, player, 0, true, 525948);
                  ItemLuckyDraw.createtemCoat(750, player, 1, true, 525948);
                  return;

              case "phuonghoang":
                  player.createPhuongHoang(3, Animal.PHUONG_HOANG, true);
                  player.createPhuongHoang(3, Animal.PHUONG_HOANG_BANG, true);
                  return;

              case "matna":
                  Item newitem = Map.doCreateMatNa(player, 525948, 837, 10, 30);
                  Item newitem2 = Map.doCreateMatNa(player, 525948, 838, 11, 30);
                  

                  int[] valuemn = {5, 5, 10, 30, 50};
                  Item newitem3 = Map.doCreateMatNa(player, -1, ID_MAT_NA[0], 0, valuemn[0]);
                  Item newitem4 = Map.doCreateMatNa(player, -1, ID_MAT_NA[1], 1, valuemn[1]);
                  Item newitem5 = Map.doCreateMatNa(player, -1, ID_MAT_NA[2], 2, valuemn[2]);
                  Item newitem6 = Map.doCreateMatNa(player, -1, ID_MAT_NA[3], 3, valuemn[3]);
                  Item newitem7 = Map.doCreateMatNa(player, -1, ID_MAT_NA[4], 4, valuemn[4]);   
                  player.sendMessage(MessageCreator.createServerAlertMessage("Nháº­n Ä‘Æ°á»£c táº¥t cáº£ máº·t náº¡ 365 ngÃ y", ""));

                  return;

              case "nguyenlieu":

                        player.doAddGemItem(4, 9999, true); // bao hiem 90
                        player.doAddGemItem(156, 9999, true); // DA MAY MAN 6
                        player.doAddGemItem(250, 9999, true); // DA_TIEN_GIAI
                        player.doAddGemItem(108, 9999, true);
                        player.doAddGemItem(66, 9999, true);
                        player.doAddGemItem(73, 9999, true);
                        player.doAddGemItem(80, 9999, true);
                        player.doAddGemItem(87, 9999, true);
                        player.doAddGemItem(94, 9999, true);
                        player.doAddGemItem(101, 9999, true);
                        player.doAddGemItem(108, 9999, true);
                        player.doAddGemItem(24, 100, false);
                        player.doAddGemItem(25, 100, false);
                        player.doAddGemItem(26, 100, false);
                        player.doAddGemItem(27, 100, false);
                        player.doAddGemItem(60, 100, false);
                        player.doAddGemItem(61, 100, false);
                        player.doAddGemItem(62, 100, false);
                        player.doAddGemItem(63, 100, false);
                        player.doAddGemItem(64, 100, false);
                        player.doAddGemItem(65, 100, false);
                        player.doAddGemItem(122, 9999, false);
                        player.doAddGemItem(115, 9999, false);
                        player.doAddGemItem(129, 9999, false);
                        player.doAddGemItem(136, 9999, false);
                        player.doAddGemItem(148, 9999, false);
                        player.doAddGemItem(142, 9999, false);
                        player.doAddGemItem(154, 9999, false);
                        player.doAddGemItem(228, 9999, false);
                        player.doAddGemItem(234, 9999, false);
                        player.doAddGemItem(240, 9999, false); //NGOC_HUYEN_MINH_6

                        player.doAddGemItem(249, 9999, true); // BOT_XANH_LA
                        player.doAddGemItem(66, 9999, true); // LKD_35
                        player.potions[9] = player.potions[9] + 9999; // NHAN SAM
                        player.calculateAttrib();
                        for (int j = 0; j < 10; j++) {
                            GiftCode.AddItem(player, 720, 0, 0, -1); // HLT
//                        GiftCode.AddItem(player, 714, 0, 0, -1); // THD
                        }
                        player.sendMessage(MessageCreator.createCharGemItem(player));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Full NguyÃªn Liá»‡u Cáº§n thiáº¿t.", ""));
                        return;
                        
                        case "tuhondan":
                        player.potions[83] = player.potions[83] + 9999; // tuhondan
                        player.sendMessage(MessageCreator.createCharGemItem(player));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n x9999 tá»¥ há»“n Ä‘an.", ""));
                        return;
                        
               case "tranh": {
                        // Tá»© BÃ¬nh â€“ 5 máº£nh má»—i loáº¡i (ID: 159 â†’ 174, 16 ID)
                        int[] tuBinh = {
                            159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
                            171, 172, 173, 174
                        };
                        for (int gemId : tuBinh) {
                            player.doAddGemItem(gemId, 5, false);
                        }

                        // Tá»© Nháº¡c â€“ 10 máº£nh má»—i loáº¡i (ID: 179 â†’ 194, 16 ID)
                        int[] tuNhac = {
                            179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190,
                            191, 192, 193, 194
                        };
                        for (int gemId : tuNhac) {
                            player.doAddGemItem(gemId, 10, false);
                        }

                        // Tá»© KhoÃ¡i â€“ 15 máº£nh má»—i loáº¡i (ID: 195 â†’ 210, 16 ID)
                        int[] tuKhoai = {
                            195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206,
                            207, 208, 209, 210
                        };
                        for (int gemId : tuKhoai) {
                            player.doAddGemItem(gemId, 15, false);
                        }

                        // Tá»© Linh â€“ 20 máº£nh má»—i loáº¡i (ID: 211 â†’ 226, 16 ID)
                        int[] tuLinh = {
                            211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222,
                            223, 224, 225, 226
                        };
                        for (int gemId : tuLinh) {
                            player.doAddGemItem(gemId, 20, false);
                        }

                        player.sendMessage(MessageCreator.createCharGemItem(player));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        player.sendMessage(MessageCreator.createServerAlertMessage("Báº¡n Ä‘Ã£ nháº­n full tranh!", ""));
                        return;
                    }         
              case "sachpet":
                  for (int j = 0; j < 50; j++) {
                      GiftCode.AddItem(player, 722, 0, 0, -1); // sÃ¡ch pet
                  }
                      return;
              case "pet":
                        int[] petEggIds = {684, 685, 686, 687, 688};
                        for (int id : petEggIds) {
                            for (int i = 0; i < 10; i++) {
                                GiftCode.AddItem(player, id, 0, 0, -1);
                            }
                        }
                        player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Trá»©ng Pet", ""));
                        return;
              case "sutu":
                        player.createSuTu(3, true);
                        Database.instance.saveOrtherLog("", player.charname, "CODE: " + text, "putGiftCode");
                        pre1.execute("insert into giftcode_log set player = '" + player.charname + "', giftcode = '" + text + "', item = '" + 0 + "', xu = " + 0 + ", luong = " + 0 + ", luongK = " + 0 + ";");
                        return;
                        
              case "lansutu":
                  player.createLan(525948, false, 95);
              return;
               case "kpahz":
                   player.sendMessage(MessageCreator.createServerAlertMessage("ÄÃ¢y chá»‰ lÃ  cÃ¡i thÃ´ng bÃ¡o chá»© khÃ´ng cÃ³ gÃ¬ Ä‘Ã¢u.", ""));
                   return;

               default:
                   break;
           }
        }

            String sql = "select * from giftcode where giftcode='" + text + "' and type = 0 limit 1";
            pre1 = conn.prepareStatement(sql);
            rs = pre1.executeQuery();

            if (rs.next()) {
                int xu = rs.getInt("xu");
                int luong = rs.getInt("luong");
                int luongkhoa = rs.getInt("luongLock");
                String item = rs.getString("item");
                String code = rs.getString("giftcode");
                int limit = rs.getInt("limit_use");

                if (limit == 0) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("MÃ£ QuÃ  Táº·ng Ä‘Ã£ háº¿t lÆ°á»£t sá»­ dá»¥ng.", ""));
                    return;
                }

                if (xu != 0) {
                    player.addXu(xu, "nhan tu giftcode" + code);
                }
                if (luong != 0) {
                    player.addLuong(luong);
                }

                if (luongkhoa != 0) {
                    player.addLuongLock(luongkhoa);
                }

                if (item != null && !item.equals("NULL") && !item.isEmpty()) {
                    String[] tachItem = item.trim().split(",");
                    for (int i = 0; i < tachItem.length; i++) {
                        String[] tachTime = tachItem[i].trim().split(":");
                        if (tachTime[0].equals("DB")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                GiftCode.AddItem(player, Integer.parseInt(tachTime[1]), 0, 0, Integer.parseInt(tachTime[3]));
                            }
                        } else if (tachTime[0].equals("EGG")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createTrungPhuongHoang(1L, Integer.parseInt(tachTime[1]));
                            }

                        } else if (tachTime[0].equals("DANHHIEU2")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.addDanhHieu(Short.parseShort(tachTime[1]), Long.parseLong(tachTime[3]));
                            }
                            try {
                                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                                player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                            } catch (IOException ex) {
                            }
                        } else if (tachTime[0].equals("PET")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createPet(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                            }
                        } else if (tachTime[0].equals("PETV")) {
                           
                                player.createPetVinhVien(Integer.parseInt(tachTime[1]));
                        
                        } else if (tachTime[0].equals("DH")) {
                            // Danh sÃ¡ch ID danh hiá»‡u cÃ³ sáºµn
                            int[] availableTitles = {
                                8767, 8768, 8769, 8770, 8771, 8772, 8773, 8774, 8775, 8776, 8777,
                                8778, 8779, 8785, 8786, 8787, 8788, 8900, 8901, 8902, 8903, 8904,
                                8905, 8906, 8907, 8908, 8909, 8910, 8911, 8924, 8925, 8935, 8956, 8957
                            };
                            
                            // Náº¿u ID = 0, chá»n ngáº«u nhiÃªn má»™t danh hiá»‡u
                            int titleId = Integer.parseInt(tachTime[1]);
                            if (titleId == 0) {
                                titleId = availableTitles[Map.r.nextInt(availableTitles.length)];
                            }
                            
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.addDanhHieu(titleId, Long.parseLong(tachTime[3]));
                            }
                            try {
                                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                                player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                            } catch (IOException ex) {
                            }

                        } else if (tachTime[0].equals("HLT")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                Map.doCreateHuyetLinhThao(player, Integer.parseInt(tachTime[1]));
                            }
                         
                        } else if (tachTime[0].equals("PPR")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createtemCoat(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]));
                            }
                        } else if (tachTime[0].equals("PPRK")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                            }
                        } else if (tachTime[0].equals("PPV")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createtemCoat(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]));
                            }
                        } else if (tachTime[0].equals("PPVK")) {
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                            }
                        } else if (tachTime[0].equals("PPLUCK")) {
                            player.createtemCoat(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), true, 0);
                        } else if (tachTime[0].equals("PT")) {
                            int idGem = Integer.parseInt(tachTime[1]);
                            int soluong = Integer.parseInt((tachTime[2]));
                            player.potions[idGem] = player.potions[idGem] + soluong;
                            player.calculateAttrib();
                            try {
                                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            } catch (IOException ex) {
                            }
                        } else if (tachTime[0].equals("GEM")) {
                            boolean lockitemgem = true;
                            try {
                                lockitemgem = (Integer.parseInt(tachTime[3]) < 0 ? true : false);
                            } catch (Exception e) {
                                lockitemgem = true;
                                e.printStackTrace();
                            }
                            // GEM:ID:SL
                            player.doAddGemItem(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[2]), lockitemgem);
                            player.sendMessage(MessageCreator.createCharGemItem(player));
                        } else if (tachTime[0].equals("DN")) {
                            player.diemNapVip += Integer.parseInt(tachTime[2]);
                        } else if (tachTime[0].equals("MN")) {
                            player.luongNap += Integer.parseInt(tachTime[2]);
                        } else if (tachTime[0].equals("PP")) {
                            Char.createPhiPhongMaxBaoKich(player, 0, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                            Char.createPhiPhongMaxBaoKich(player, 1, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                        } else if (tachTime[0].equals("PPLUCK")) {
                            player.createtemCoat(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[4]), true, 0);
                        } else if (tachTime[0].equals("PP8X")) {
                            player.createPhiPhongMaxBaoKich(player, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                            player.createPhiPhongMaxBaoKich(player, 1, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                        } else if (tachTime[0].equals("VKTT")) {
                            player.createVuKhiThoiTrang(player, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                        } else if (tachTime[0].equals("VKID")) {

                        player.createVuKhiThoiTrangID(player, Integer.parseInt(tachTime[1]), 0, Integer.parseInt(tachTime[2]), (Item) null, false, true, 1, true);

                        } else if (tachTime[0].equals("VKMAX")) {
                            player.createVuKhiThoiTrang(player, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                        } else if (tachTime[0].equals("VKTB")) {
                            player.createVuKhiThoiTrangTHANBINH_NEW(player, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                        } else if (tachTime[0].equals("VKTBMAX")) {
                            player.createVuKhiThoiTrangTHANBINH_NEW(player, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                        } else if (tachTime[0].equals("VKTBLV")) {
                            int thoihan = Integer.parseInt(tachTime[3]) * 24 * 60;
                            player.createVuKhiThoiTrangTHANBINH_LV(player, 0, thoihan, null, true, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[2]), false);
                        } else if (tachTime[0].equals("BT")) {
                            player.doCreatePetBachThu(Integer.parseInt(tachTime[3]), false);
                        } else if (tachTime[0].equals("AOTT")) {
                            Map.doBuyModelClothe(player, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]), 1);
                        } else if (tachTime[0].equals("AOSHOP")) {
                            doDropModelClothe(player, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                        } else if (tachTime[0].equals("CHOI")) {
                            player.createChoi(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[4]), true);
                        } else if (tachTime[0].equals("HOVELAN")) {
                            player.createHoVeLenhLan(Integer.parseInt(tachTime[1]), player, Integer.parseInt(tachTime[3]), 1);
                        } else if (tachTime[0].equals("TRANGPHUC")) {
                            // Format: TRANGPHUC:itemID:quantity:days
                            for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                                player.doDropModelClothe(player, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                            }
                        } else if (tachTime[0].equals("ITEM")) {
                            player.createItemSoLuong(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[2]), player);
                        } else if (tachTime[0].equals("MATNA")) {
                            int[] valuemn = {5, 5, 10, 30, 50};
                            int randomMN = Map.r.nextInt(valuemn.length);
                            if (Integer.parseInt(tachTime[1]) > -1) {
                                randomMN = Integer.parseInt(tachTime[1]);
                            }
                            Item newitem = Map.doCreateMatNa(player, Integer.parseInt(tachTime[3]), ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                            player.sendMessage(MessageCreator.createServerAlertMessage("Nháº­n " + newitem.getName() + " 7 ngÃ y", ""));
                        // ... existing code ...
                    } else if (tachTime[0].equals("MATNA2")) {
                        // Táº¡o máº·t náº¡ vá»›i option vÃ  value random
                        int[] optionValues = {5, 5, 10, 30, 50}; // CÃ¡c giÃ¡ trá»‹ value cÃ³ thá»ƒ
                        int randomIndex = Map.r.nextInt(optionValues.length);
                        int matnaID = Integer.parseInt(tachTime[1]); // ID máº·t náº¡
                        int option = randomIndex; // Option máº·t náº¡ (0-4)
                        int value = optionValues[randomIndex];  // GiÃ¡ trá»‹ option tÆ°Æ¡ng á»©ng
                        Item newitem = Map.doCreateMatNa2(player, Integer.parseInt(tachTime[3]), matnaID, option, value);
                        player.sendMessage(MessageCreator.createServerAlertMessage("Nháº­n " + newitem.getName() + (Integer.parseInt(tachTime[3]) > 0 ? " " + Integer.parseInt(tachTime[3])/1440 + " ngÃ y" : " vÄ©nh viá»…n"), ""));    
                    } else if (tachTime[0].equals("RONGBANG")) {
                            player.createRongLon(Integer.parseInt(tachTime[1]), true);
                        } else if (tachTime[0].equals("SUTU")) {
                            player.createSuTu(Integer.parseInt(tachTime[1]), true);
                        } else if (tachTime[0].equals("DK")) {
                            player.createHeo(0, false);
                        } else if (tachTime[0].equals("DKB")) {
                            player.createHeoBang(Integer.parseInt(tachTime[1]), false);
                        } else if (tachTime[0].equals("TL")) {
                            player.createTuanLoc(Integer.parseInt(tachTime[1]));
                            
                        } else if (tachTime[0].equals("DBT")) {
                            player.createPoro(Integer.parseInt(tachTime[1]), false);

                            ///  todo REGEX quÃ  tÃ­ch lÅ©y 
                        } else if (tachTime[0].equals("LANT")) {
                            player.createLan(Integer.parseInt(tachTime[1]), false, 95);

                        } else if (tachTime[0].equals("DKT")) {
                            player.createHeo(Integer.parseInt(tachTime[1]), true);
                            
                            
                        } else if (tachTime[0].equals("PHDS")) {
                            
                            Animal anima = null;
                            anima = new Animal();
                            anima.name = "PhÆ°á»£ng hoÃ ng Ä‘a sáº¯c";
                            anima.idImg = Animal.PHUONG_HOANG_7MAU;
                            anima.createAtt();
                            anima.level = 1;
                            anima.place = 0;
                            anima.ownerId = player.charDBID;
                            anima.id = player.getIDItem();
                            player.animal.add(anima);
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                            Database.instance.saveAnimal(anima);

                        } else if (tachTime[0].equals("PHT")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG, true);
                        } else if (tachTime[0].equals("PHDST")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_7MAU, true);
                        } else if (tachTime[0].equals("PHMT")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_MOC, true);
                         } else if (tachTime[0].equals("PHKT")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_KIM, true);
                         } else if (tachTime[0].equals("PHTT")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_THO, true);
                        } else if (tachTime[0].equals("PHBT")) {
                            player.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_BANG, true);
                        } else if (tachTime[0].equals("MNT")) {
                            Item newitem = Map.doCreateMatNa3(player, Integer.parseInt(tachTime[1]), 837, 10, 5);
                        } else if (tachTime[0].equals("MNC")) {
                            Item newitem2 = Map.doCreateMatNa3(player, Integer.parseInt(tachTime[1]), 838, 11, 5);
                            
                        } else if (tachTime[0].equals("NLT")) {
                            player.createNguyetLinhTruong(player, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                        } else if (tachTime[0].equals("TKMAX")) {
                            player.createThanKhi(player, Integer.parseInt(tachTime[1]), 0, Integer.parseInt(tachTime[3]), null, true, true, 1, false);
                        } else if (tachTime[0].equals("TK")) {
                            player.createThanKhi(player, Integer.parseInt(tachTime[1]), 0, Integer.parseInt(tachTime[3]), null, false, true, 1, false);                       
                        } else if (tachTime[0].equals("NLTMAX")) {
                            player.createNguyetLinhTruong(player, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, true);
                        } else if (tachTime[0].equals("HKL")) {
                            doUsePotion.doCreateHoaKyLanTime(player, Integer.parseInt(tachTime[1]));
                        }
                    }
                }

                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                player.sendMessage(MessageCreator.createServerAlertMessage("ChÃºc má»«ng báº¡n Ä‘Ã£ nháº­n Ä‘Æ°á»£c pháº§n quÃ  tá»«: " + code + ".", ""));
                Database.instance.saveOrtherLog("", player.charname, "CODE: " + code, "putGiftCode");
                sql = "insert into giftcode_log set player = '" + player.charname + "', giftcode = '" + text + "', item = '" + item + "', xu = " + xu + ", luong = " + luong + ", luongK = " + luongkhoa + ";";
                pre1.execute(sql);

                if (limit >= 1) {
                    String sql_updatelimit = "UPDATE giftcode SET limit_use = limit_use - '1' WHERE giftcode = '" + code + "'";
                    pre1.execute(sql_updatelimit);
                }
                System.out.println(limit);
            } else {
                player.sendMessage(MessageCreator.createServerAlertMessage("MÃ£ quÃ  táº·ng khÃ´ng tá»“n táº¡i.", ""));
            }
            // System.out.println("\n" + rs.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connPool.free(conn);
            } catch (Exception exception) {
            }
            try {
                pre1.close();
            } catch (Exception exception) {
            }
        }
    }


    public static boolean check_active(String username) {
        return true;
    }

    public static boolean check_active(int userID) {
        return true;
    }


    public static boolean insert_active(int userID, String username) {
        try (Connection conn = Database.instance.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO 5h_active (userID, username) VALUES (?, ?)")) {
            
            ps.setInt(1, userID);
            ps.setString(2, username);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Lá»—i khi thÃªm báº£n ghi kÃ­ch hoáº¡t cho userID: " + userID);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean check_active_putcode(int userID) {
        return check_active(userID);
    }






    public String getGiftCode_DB(Char player) {
        String respond = "0|KHONG CO DATA";
        try {
            Connection conn = Database.connGiftCode.getConnection();
            try (Statement stm = conn.createStatement()) {
                String sql = "select * from ngude_giftcode where charname='" + player.charname + "' and status = 0 limit 1";
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    respond = "1|" + rs.getInt("xu") + "|" + rs.getInt("luong") + "|" + rs.getInt("luongLock") + "|" + rs.getString("item") + "|" + rs.getInt("level") + "|" + rs.getString("code");
                    if (player.getLevel() >= rs.getInt("level")) {
                        sql = "update ngude_giftcode set status = 1 where id = '" + rs.getInt("id") + "'";
                        stm.execute(sql);
                    }
                    rs.close();
                }
            }
            Database.connGiftCode.free(conn);
            return respond;
        } catch (SQLException e) {
            return "0|LOI MYSQL: " + e.getMessage();
        }
    }

    public void countChar(Char p, int UserID) {
//        int userId = getUserIDbyCharname(p);
//        if (userId == -1) {
//            p.countChar = 0;
//            return;
//        }
        try {
            Connection conn = Database.connGiftCode.getConnection();
            String sql = "SELECT `charname` FROM `tob_char` WHERE `userId` = '" + p.userID + "';";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            rs.close();
            stm.close();
            Database.connGiftCode.free(conn);
            p.countChar = count;
            return;
        } catch (Exception ignored) {
            p.countChar = 0;
            return;
        }
    }

    public int getUserIDbyCharname(Char p) {
        try {
            Connection conn = Database.connGiftCode.getConnection();
            ResultSet rs = null;
            Statement stm = null;
            String sql = "SELECT userId FROM `tob_char` WHERE `charname` = '" + p.charname + "';";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if (rs != null && rs.next()) {
                int count = rs.getInt("userId");
                rs.close();
                stm.close();
                Database.connGiftCode.free(conn);
                return count;
            }
            rs.close();
            stm.close();
            Database.connGiftCode.free(conn);
            return -1;
        } catch (Exception ignored) {
            return -1;
        }
    }

    public String getCheckQua_Tuan(Char player) {
        String respond = "0| KHONG CO QUA";
        if (player.tichluy_tuan >= player.diemNapVip || player.diemNapVip < 8000) {
            return respond;
        }
        try {
            int a = player.diemNapVip;
            int b = player.tichluy_tuan;
            if (a - b > 8000) {
                a = b + 8000;
            }
            if ((a - b) / 8000 > 0) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|0|GEM:249:50:-1|1|má»‘c náº¡p tÃ­ch luá»¹ tuáº§n.";
                player.tichluy += 8000;
                stm.execute("update `tob_char` set `tichluy_tuan` = `tichluy_tuan` + 8000 where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            return respond;
        } catch (SQLException e) {
            return "0|LOI MYSQL: " + e.getMessage();
        }
//    
    }

    //Náº¡p 5000k " Ä‘c trá»©ng rá»“ng hoáº·c Ä‘áº¡i bÃ ng ngáº«u nhiÃªn vv Pp vv theo lv , pÃ©t Báº¡ch Thá»­ 30 ngÃ y
    //Náº¡p 2000k Ä‘c trÃºng cÃº hoáº·c dÆ¡i vv , Pp vv theo lv 
    //Náº¡p 1000k Ä‘c trá»©ng yÃªu tinh vv + pp theo lv 
    //Náº¡p 500k Ä‘c Pp vv Theo lv 
    //Náº¡p 200k Ä‘c trá»©ng yÃªu Tinh 30   ngÃ y + 10 VÃ© quay sá»‘ Ph
    public String getCheckQua(Char player) {
        String respond = "0| KHONG CO QUA";
        long daNap = (player.luongNap * 2);
        if (player.tichluy >= daNap) {
            return respond;
        }
        try {
            if (daNap >= 10000 && player.tichluy < 10000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 10000 + "|PP:0:1:43200|1|má»‘c náº¡p 10K lÆ°á»£ng";
                player.tichluy = 10000;
                stm.execute("update `tob_char` set tichluy = '10000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 20000 && player.tichluy < 20000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 20000 + "|DB:687:1:0|1|má»‘c náº¡p 20K lÆ°á»£ng";
                player.tichluy = 20000;
                stm.execute("update `tob_char` set tichluy = '20000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 40000 && player.tichluy < 40000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 40000 + "|DB:686:1:-1,PP:0:1:86400|1|má»‘c náº¡p 40k lÆ°á»£ng";
                player.tichluy = 40000;
                stm.execute("update `tob_char` set tichluy = '40000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 80000 && player.tichluy < 80000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 80000 + "|DB:684:1:-1,PP:0:1:-1,HLT:1:1|1|má»‘c náº¡p 80k lÆ°á»£ng";
                player.tichluy = 80000;
                stm.execute("update `tob_char` set tichluy = '80000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }

            if (daNap >= 120000 && player.tichluy < 120000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 120000 + "|AOSHOP:723:1:30,AOSHOP:724:1:30,GEM:249:200:-1,PET:5:1:0|1|má»‘c náº¡p 120K lÆ°á»£ng";
                player.tichluy = 120000;
                stm.execute("update `tob_char` set tichluy = '120000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;

            }
            if (daNap >= 200000 && player.tichluy < 200000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 200000 + "|DB:688:1:-1,HLT:1:1,PP:0:1:-1,BT:0:1:43200|1|má»‘c náº¡p 200k LÆ°á»£ng";
                player.tichluy = 200000;
                stm.execute("update `tob_char` set tichluy = '200000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;

            }
            
            if (daNap >= 400000 && player.tichluy < 400000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|400000|DB:688:1:-1,HLT:0:1,BT:0:1:0,GEM:249:300:-1|1|má»‘c náº¡p 400k LÆ°á»£ng";
                player.tichluy = 400000;
                stm.execute("update `tob_char` set tichluy = '400000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;

            }
            
            if (daNap >= 600000 && player.tichluy < 600000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();

                respond = "1|0|0|" + 600000 + "|DB:685:1:-1,PP8X:0:1:0:-1,GEM:249:500:-1,HLT:0:1,TRANGPHUC:725:1:30,TRANGPHUC:726:1:30|1|má»‘c náº¡p 600k LÆ°á»£ng";
                player.tichluy = 600000;
                stm.execute("update `tob_char` set tichluy = '600000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }


            if (daNap >= 1000000 && player.tichluy < 1000000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|" + 1000000 + "|DB:685:1:-1,PPV:742:1:0:1,PPV:742:1:0:0,DH:8770:1:0|1|má»‘c náº¡p 1 Triá»‡u LÆ°á»£ng.";
                player.tichluy = 1000000;
                stm.execute("update `tob_char` set tichluy = '1000000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 1200000 && player.tichluy < 1200000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();

                respond = "1|0|0|" + 1200000 + "|VKTT:0:1:43200,CHOI:619:1:0:0,PT:74:1:-1,TRANGPHUC:725:1:60,TRANGPHUC:726:1:60|1|má»‘c náº¡p 1 Triá»‡u 2 LÆ°á»£ng.";
                player.tichluy = 1200000;
                stm.execute("update `tob_char` set tichluy = '1200000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 1400000 && player.tichluy < 1400000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|1400000|PT:86:1:-1,CHOI:729:1:0:0,DB:899:1:0|1|má»‘c náº¡p 1 Triá»‡u 4 LÆ°á»£ng.";
                player.tichluy = 1400000;
                stm.execute("update `tob_char` set tichluy = '1400000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            } // tá»« Ä‘Ã¢y trá»Ÿ xuá»‘ng lÃ  lá»—i Ã  ? á»«m

            if (daNap >= 1600000 && player.tichluy < 1600000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|1600000|PT:70:1:-1,CHOI:617:1:0:0|1|má»‘c náº¡p 1 trieu 6 luong.";
                player.tichluy = 1600000;
                stm.execute("update `tob_char` set tichluy = '1600000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 1800000 && player.tichluy < 1800000) {

                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();

                respond = "1|0|0|" + 1800000 + "|TK:890:0:43200,TRANGPHUC:725:1:90,TRANGPHUC:726:1:90,GEM:249:1000:-1,CHOI:618:1:0:0|1|má»‘c náº¡p 1 Triá»‡u 8 LÆ°á»£ng.";
                player.tichluy = 1800000;
                stm.execute("update `tob_char` set tichluy = '1800000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }

            if (daNap >= 2000000 && player.tichluy < 2000000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|2000000|PT:115:1:-1,VKTT:1:1:0|1|má»‘c náº¡p 2tr LÆ°á»£ng.";
                player.tichluy = 2000000;
                stm.execute("update `tob_char` set tichluy = '2000000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }

            if (daNap >= 2200000 && player.tichluy < 2200000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|2200000|TRANGPHUC:725:1:0,TRANGPHUC:726:1:0,GEM:249:1000:-1,DB:900:1:0|1|má»‘c náº¡p 2tr2 LÆ°á»£ng.";
                player.tichluy = 2200000;
                stm.execute("update `tob_char` set tichluy = '2200000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            

            if (daNap >= 2600000 && player.tichluy < 2600000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|2600000|PET:3:1:0,TRANGPHUC:797:1:0,TRANGPHUC:801:1:0,VKMAX:0:1:-1|1|má»‘c náº¡p 2tr6 LÆ°á»£ng.";
                player.tichluy = 2600000;
                stm.execute("update `tob_char` set tichluy = '2600000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            if (daNap >= 2800000 && player.tichluy < 2800000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|2800000|SUTU:-1,DB:581:1:-1|1|má»‘c náº¡p 2tr8 LÆ°á»£ng.";
                player.tichluy = 2800000;
                stm.execute("update `tob_char` set tichluy = '2800000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            
            if (daNap >= 3000000 && player.tichluy < 3000000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|3000000|VKTB:0:1:43200,MATNA:1:1:-1,AOSHOP:839:1:-1|1|má»‘c náº¡p 3tr LÆ°á»£ng.";
                player.tichluy = 3000000;
                stm.execute("update `tob_char` set tichluy = '3000000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }

            return respond;
        } catch (SQLException e) {
            return "0|LOI MYSQL: " + e.getMessage();
        }
    }

    public String getCheckQua_Bonus(Char player) {
        String respond = "0| KHONG CO QUA";
        long daNap = (player.luongNap * 2);
        if (player.tichluy_bosung >= daNap) {
            return respond;
        }
        try {
//            if (daNap >= 20000 && player.tichluy_bosung < 20000) {
//                Connection conn = Database.connGiftCode.getConnection();
//                Statement stm = conn.createStatement();
//                respond = "1|0|0|0|DB:720:1:-1|1|má»‘c náº¡p bá»• sung 20000 luong.";
//                player.tichluy_bosung = 20000;
//                stm.execute("update `tob_char` set tichluy_bosung = '20000' where charname = '" + player.charname + "'");
//                stm.close();
//                Database.connGiftCode.free(conn);
//                return respond;
//            }
            if (daNap >= 800000 && player.tichluy_bosung < 800000) {
                Connection conn = Database.connGiftCode.getConnection();
                Statement stm = conn.createStatement();
                respond = "1|0|0|0|DH:8773:1:129600|1|má»‘c náº¡p bá»• sung 800K luong.";
                player.tichluy_bosung = 800000;
                stm.execute("update `tob_char` set tichluy_bosung = '800000' where charname = '" + player.charname + "'");
                stm.close();
                Database.connGiftCode.free(conn);
                return respond;
            }
            return respond;
        } catch (SQLException e) {
            return "0|LOI MYSQL: " + e.getMessage();
        }
    }

    public void UpdateQua_NPC(Char p) {
        Connection conn = null;
        Statement stm = null;
        try {
            conn = Database.connGiftCode.getConnection();
            stm = conn.createStatement();
            stm.execute("update `tob_char` set qua_npc = '" + p.qua_npc + "' where charname = '" + p.charname + "'");
            stm.close();
            Database.connGiftCode.free(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int TileNap = 1;

    public void getBoardNapTien(Char player) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getNapConnectionOrNull("getBoardNapTien");
            if (conn == null) {
                return;
            }
            stm = conn.createStatement();
            String sql = "select * from board_naptien where username='" + player.charname + "'";
            rs = stm.executeQuery(sql);
            String info = "";
            long oldPotion = 0L;
            int oldluong = 0;
            try {
                oldPotion = player.getxu();
                oldluong = player.getLuong();
            } catch (Exception ignored) {
            }
            if (rs.next()) {
                int xu = rs.getInt("xu");
                player.addXu(xu, "getBoardNapTien db");
                if (xu > 0) {
                    info = "SV Get xu: " + xu + " | ";
                }
                int luong = rs.getInt("luong") * TileNap;
                player.addLuong(luong);
                int luongKhoa = rs.getInt("luongKhoa") * TileNap;
                if (luongKhoa > 0) {
                    player.addLuongLock(luongKhoa);
                }
                
                player.vetangluong += rs.getInt("ve");
                player.luongNap += (luong / TileNap) / 2;

                Database.instance.updateLuongNap(luong, player);
                player.diemNapVip += luong / TileNap;
                // Database.instance.addCharXaiNapLuong(player, 0);
                if (luong > 0) {
                    info = info + "SV Get luong: " + luong;
                }
                if (rs.getInt("ve") > 0) {
                    info = info + "| SV Get ve: " + rs.getInt("ve");
                }
                String sqlUpdate = "DELETE FROM `board_naptien` WHERE `id` ='" + rs.getInt("id") + "'";
                stm.execute(sqlUpdate);
                if (luong >= 10) {
                    Database.instance.saveLogThongKeChiTieu("nap luong", 0, 1, luong, "luong nap");
                }
            }
            try {
                if (!info.equals("")) {
                    sql = "insert tob_get_board_nap set charname='" + player.charname + "',info='" + info + " X>" + oldPotion + " L>" + oldluong + "',dateGet=now()";
                    stm.execute(sql);
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPoolNap.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getBoardCreated(Char player) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getNapConnectionOrNull("getBoardCreated");
            if (conn == null) {
                return;
            }
            stm = conn.createStatement();
            String sql = "select * from board_created where username='" + player.userID + "'";
            rs = stm.executeQuery(sql);
            String info = "";
            long oldPotion = 0L;
            int oldluong = 0;
            try {
                oldPotion = player.getxu();
                oldluong = player.getLuong();
            } catch (Exception ignored) {
            }
            if (rs.next()) {
                int xu = rs.getInt("xu");
                player.addXu(xu, "board_created db");
                if (xu > 0) {
                    info = "SV Get xu: " + xu + " | ";
                }
                int luong = rs.getInt("luong");
                player.addLuong(luong);
                if (true) { // Khuyáº¿n mÃ£i lÆ°á»£ng khoÃ¡.
                    int lk = rs.getInt("luongK");
                    if (lk <= 0) {
                        lk = 1;
                    }
                    player.lockLuong += lk;
                }
                //player.vetangluong += rs.getInt("ve");
                //player.luongNap += luong / 2;

                //Database.instance.updateLuongNap(luong, player);
                //player.diemNapVip += luong;
                //  Database.instance.addCharXaiNapLuong(player, 0);
                if (luong > 0) {
                    info = info + "SV Get luong: " + luong;
                }
                if (rs.getInt("ve") > 0) {
                    info = info + "| SV Get ve: " + rs.getInt("ve");
                }
                String sqlUpdate = "DELETE FROM `board_created` WHERE `id` ='" + rs.getInt("id") + "'";
                stm.execute(sqlUpdate);
//                if (luong >= 10) {
//                    Database.instance.saveLogThongKeChiTieu("nap luong", 0, 1, luong, "luong nap");
//                }
            }
            try {
                if (!info.equals("")) {
                    sql = "insert tob_get_board_nap set charname='" + player.charname + "',info='" + info + " X>" + oldPotion + " L>" + oldluong + "',dateGet=now()";
                    stm.execute(sql);
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPoolNap.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPoolNap.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveAllChar(Char p) {
    }

    public Char clone(Char copythis) {
        return new Char(null);
    }

    public void doAddNewColumInfoPart() {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            try {
                final String sql = "alter table tob_qua_ct add `tlog` datetime  NULL";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_qua_ct add `tset` datetime  NULL";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_qua_ct_top add `tlog` datetime  NULL";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_qua_ct_top add `tset` datetime  NULL";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_char add `tuido` text";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_event add `lixi` varchar(100) DEFAULT '100,18,30'";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "alter table tob_event add `vip` varchar(100) DEFAULT '500,100,5'";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                String[] name = {"KhÃ¡ng hoáº£", "KhÃ¡ng thuá»·", "KhÃ¡ng má»™c", "KhÃ¡ng kim", "KhÃ¡ng thá»•", "GiÃ¡ lÆ°á»£ng", "Sá»‘ lÆ°á»£ng"};
                if (TeamServer.isServerLienDau()) {
                    name = new String[]{"KhÃ¡ng hoáº£", "KhÃ¡ng thuá»·", "KhÃ¡ng má»™c", "KhÃ¡ng kim", "KhÃ¡ng thá»•"};
                }
                int[] id = {120, 121, 122, 123, 124, 125, 126};
                int[] idcolor = {1, 1, 1, 1, 1, 2, 2, 2};
                int[] ispercent = {2, 2, 2, 2, 2, 0, 0, 0};
                final String sql2 = "insert into data_attribute(id,name,idcolor,ispercent,decript,namein) values(@,'#',^,&,'#','')";
                for (int i = 0; i < name.length; ++i) {
                    try {
                        String sql3 = sql2.replace("@", String.valueOf(id[i])).replace("#", name[i]).replace("^", String.valueOf(idcolor[i])).replace("&", String.valueOf(ispercent[i]));
                        conn = this.getConnection();
                        pre = conn.prepareStatement(sql3);
                        pre.execute();
                        try {
                            pre.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            Database.connPool.free(conn);
                        } catch (Exception ignored) {
                        }
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                String[] data = {"750, 'Phi Phong Huyá»n VÅ©', 19, 15, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 626, 0, -1, 10080, -1, '', 8806,0", "751, 'PhÃ¹ phu thÃª', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 627, 0, -1, 0, -1, '', -1,0", "752, 'RÆ°Æ¡ng nguyÃªn liá»‡u cao cáº¥p', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 596, 0, -1, 0, -1, '', -1,0", "753, 'RÆ°Æ¡ng nguyÃªn liá»‡u sÆ¡ cáº¥p', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 596, 0, -1, 0, -1, '', -1,0", "754, 'Ão dÃ i nam \nTÄƒng 100% bÄƒng ,sÃ©t ,Ä‘á»™c ,lá»­a lan', 0, 0, 0, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 630, 0, -1, 10080, -1, '', -1, 0", "755, 'Ão dÃ i ná»¯ \nTÄƒng 100% bÄƒng ,sÃ©t ,Ä‘á»™c ,lá»­a lan', 0, 0, 0, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 631, 0, -1, 10080, -1, '', -1, 0"};
                for (String datum : data) {
                    try {
                        String sql4 = "insert into data_item values(" + datum + " )";
                        conn = this.getConnection();
                        pre = conn.prepareStatement(sql4);
                        pre.execute();
                    } catch (Exception ignored) {
                    } finally {
                        try {
                            pre.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            Database.connPool.free(conn);
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        pre.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        Database.connPool.free(conn);
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_dau_gia2` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(100) NOT NULL,`luongbid` int(10) unsigned NOT NULL DEFAULT '0',`luongcoc` int(10) unsigned NOT NULL DEFAULT '0',`timebid` datetime DEFAULT NULL,`hang` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0 la chua xep hang, 1 la dc quyen mua, 2 la nhan lai tien',PRIMARY KEY (`id`),UNIQUE KEY `Index_2` (`charname`)) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_qua_loi_dai` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(100) NOT NULL,`qua` int(10) unsigned NOT NULL DEFAULT '0',`typeQua` int(10) unsigned NOT NULL DEFAULT '0',`nhom` int(10) unsigned NOT NULL DEFAULT '0',`diem` int(10) unsigned NOT NULL DEFAULT '0',PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_char_vip` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(100) NOT NULL,`vip` int(10) unsigned DEFAULT '0',`timeRcv` datetime NOT NULL,`reset` int(10) unsigned DEFAULT '0',PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_buy_ruong` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(100) NOT NULL,`dateLog` varchar(100) DEFAULT NULL,`timeBuy` datetime DEFAULT NULL,`soluong` int(10) unsigned NOT NULL DEFAULT '0',PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_qua_ct_bk` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(45) NOT NULL,`soluong` int(10) unsigned NOT NULL DEFAULT '1',`nhan` int(10) unsigned NOT NULL DEFAULT '0',`tlog` datetime DEFAULT NULL,`tset` datetime DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=250 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE  `tob_qua_ct_top_bk` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(45) NOT NULL,`soluong` int(10) unsigned NOT NULL DEFAULT '1',`nhan` int(10) unsigned NOT NULL DEFAULT '0',`tlog` datetime DEFAULT NULL,`tset` datetime DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=250 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                final String sql = "CREATE TABLE `tob_nap_xai_luong` (`int` int(10) unsigned NOT NULL AUTO_INCREMENT,`charname` varchar(100) NOT NULL,`luongxai` int(10) unsigned NOT NULL DEFAULT '0',`luongnap` int(10) unsigned NOT NULL DEFAULT '0',`tnap` datetime DEFAULT NULL,`txai` datetime DEFAULT NULL,PRIMARY KEY (`int`),UNIQUE KEY `Index_2` (`charname`),KEY `Index_3` (`luongxai`),KEY `Index_4` (`luongnap`)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                String sql = "CREATE TABLE  `tob_code_lixi` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`code` varchar(100) NOT NULL,`nhan` int(10) unsigned DEFAULT '0',`timeNhan` datetime DEFAULT NULL,`charnhan` varchar(100) DEFAULT NULL,PRIMARY KEY (`id`),UNIQUE KEY `Index_2` (`code`) USING BTREE ,KEY `Index_3` (`charnhan`),KEY `Index_4` (`nhan`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
                if (TeamServer.isServerLienDau() || TeamServer.isServerLocal()) {
                    int j = 0;
                    while (j < 1000) {
                        try {
                            sql = "insert into tob_code_lixi(code) values('" + genCodeLixi() + "')";
                            conn = this.getConnection();
                            pre = conn.prepareStatement(sql);
                            pre.execute();
                            try {
                                pre.close();
                            } catch (Exception ignored) {
                            }
                            try {
                                Database.connPool.free(conn);
                            } catch (Exception ignored) {
                            }
                            ++j;
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                String sql = "CREATE TABLE  `tob_code_lixi` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT,`code` varchar(100) NOT NULL,`nhan` int(10) unsigned DEFAULT '0',`timeNhan` datetime DEFAULT NULL,`charnhan` varchar(100) DEFAULT NULL,PRIMARY KEY (`id`),UNIQUE KEY `Index_2` (`code`) USING BTREE ,KEY `Index_3` (`charnhan`),KEY `Index_4` (`nhan`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
                conn = this.getConnection();
                pre = conn.prepareStatement(sql);
                pre.execute();
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
                if (TeamServer.isServerLienDau() || TeamServer.isServerLocal()) {
                    int j = 0;
                    while (j < 1000) {
                        try {
                            sql = "insert into tob_code_lixi(code) values('" + genCodeLixi() + "')";
                            conn = this.getConnection();
                            pre = conn.prepareStatement(sql);
                            pre.execute();
                            try {
                                pre.close();
                            } catch (Exception ignored) {
                            }
                            try {
                                Database.connPool.free(conn);
                            } catch (Exception ignored) {
                            }
                            ++j;
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public boolean updateValidCodeLixi(String code, String charname) {
        charname = charname + "_" + Database.nameServer[TeamServer.server];
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "update tob_code_lixi set nhan=1, charnhan='" + charname + "', timeNhan=now() WHERE code='" + code + "'";
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean checkValidCodeLixi(String code) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "SELECT * FROM tob_code_lixi WHERE code='" + code + "' and nhan=0";
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean checkCanInputCodeLixi(String charname) {
        charname = charname + "_" + Database.nameServer[TeamServer.server];
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "SELECT * FROM tob_code_lixi WHERE charnhan='" + charname + "'";
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return false;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return true;
    }

    public static String genCodeLixi() {
        String info;
        String code;
        for (info = "0123456789", code = ""; code.length() < 12; code = code + info.charAt(Map.r.nextInt(info.length()))) {
        }
        code = code + "kpah";
        return code;
    }

    public static void startSaveChar() {
        MessageCreator.doInitItemNpcSell();
        Database.instance.doAddNewColumInfoPart();
        Map.loadAllTemplate();
   
        if (Char.getDayTime(0L).compareTo("2020-10-28 10:30:00") <= 0) {
            Database.instance.resetTopEvent();
        }
        if (Char.getDayTime(0L).compareTo("2021-02-02 15:45:00") <= 0) {
            Database.instance.resetCooking();
            Database.instance.resetTopEvent();
            Database.instance.doResetCharBuyRuong();
        }
        if (Database.instance.checkTableTobQuaLoiDai()) {
            Database.instance.backupTobThachDau();
            for (int i = 0; i < 5; ++i) {
                Database.instance.getTopQuaLoiDai(i);
            }
        }
        Database.instance.loadAllCharBuyRuong();
        Database.instance.doGetAllCharVip();
        Database.instance.loadAllQuestTemplate(0);
        Database.instance.loadAllQuestTemplate(1);
        Database.instance.loadAllQuestTemplate(2);
        
        Database.instance.loadSMS(0);
        Database.instance.loadSMS(1);
        Database.instance.check();
        Database.instance.loadTimeX2Server();
        Database.instance.loadAllClaninfo();
        Database.instance.loadAllFruitTemplate();
        Database.instance.loadAllTubinhTemplate();
        NewClan.checkDelClanAuto();
        NewClan.startThreadSaveClan();
        Map.loadClanPropertyTown();
        Database.instance.loadEvent();

        Database.instance.loadBadWords();
      

        Char.loadAllItemLucky();
      
        Database.instance.loadNameAttributeItem();
   

        if (Database.createRace == 1) {
            Database.instance.createTableRace(Database.tableRace);
            Database.instance.initDataRace(Database.tableRace, Database.dayCreateRace);
        }
       

        System.gc();
        // TODO báº­t láº¡i
        if (!Constant.Checker.LOCAL_SERVER) {
            Database.instance.checkDropTable();
        }

        Database.instance.loadAllXoso();
      
        Database.instance.loadAllXosoLow();
        

        Database.instance.loadCharChienTruong();
        for (int i = 0; i < 6; ++i) {
            Database.instance.loadAllCharThiDau(i);
        }
        if (Char.getDayTime(0L).compareTo("2025-05-05 10:00:00") <= 0) {
            Map.MAX_LI_XI = 100;
            Map.hourGiveLixi = 15;
            Map.minuteGiveLixi = 30;
            Database.instance.updateInfoLixiEvent();
        }
    }

    private Database() {
        this.connections = new Vector<>();
        this.busy = new Vector<>();
        this.curWeekMonthYear = "";
        this.curMonthYear = "";
        this.sqlUpdate = new Vector<>();
        this.stop = false;
        this.templateTest = new Vector<>();
        this.sid = 0;
        this.mid = 0;
        this.notFalse = false;
        this.update = 0;
        this.hack = new Hashtable<>();
    }

   
   

    

    public void updateDelChar(int charDBID, int del) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            final String sql = "update tob_char set del=?,tDelChar=? where id=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, del);
            pre.setLong(2, System.currentTimeMillis());
            pre.setInt(3, charDBID);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public static void setLink(String host, String dbname, String user, String pwd, String maxCo) {
        String jdbcUrl = buildJdbcUrl(host, dbname);
        Database.LINK = jdbcUrl + "&user=" + user + "&password=" + pwd;
        Database.connPool = new ConnPool(jdbcUrl, user, pwd, Integer.parseInt(maxCo));
    }

    public static void setLink1(String host, String dbname, String user, String pwd, String maxCo) {
        String jdbcUrl = buildJdbcUrl(host, dbname);
        Database.LINK1 = jdbcUrl + "&user=" + user + "&password=" + pwd;
        Database.connPool1 = new ConnPool(jdbcUrl, user, pwd, Integer.parseInt(maxCo));
    }

    public static void setLinkBoardNap(String host, String dbname, String user, String pwd, String maxCo) {
        Database.connPoolNap = new ConnPool(buildJdbcUrl(host, dbname), user, pwd, Integer.parseInt(maxCo));
    }

    public static void setLinkGiftCode(String host, String dbname, String user, String pwd, String maxCo) {
        Database.connGiftCode = new ConnPool(buildJdbcUrl(host, dbname), user, pwd, Integer.parseInt(maxCo));
    }

    private Connection getNapConnectionOrNull(String context) {
        try {
            if (Database.connPoolNap == null) {
                System.out.println("[DB_NAP] pool is null at " + context);
                return null;
            }
            return Database.connPoolNap.getConnection();
        } catch (Exception e) {
            System.out.println("[DB_NAP] skip " + context + " because " + e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    private static String buildJdbcUrl(String host, String dbname) {
        return "jdbc:mysql://" + host + "/" + dbname
                + "?useUnicode=true"
                + "&characterEncoding=UTF-8"
                + "&connectionCollation=utf8_general_ci"
                + "&useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=Asia/Ho_Chi_Minh";
    }

    public Connection getConnection() throws SQLException {
        if (Database.connPool == null) {
            throw new SQLException("Primary DB connection pool is not initialized.");
        }
        try {
            return Database.connPool.getConFromPool();
        } catch (NoSuchElementException e) {
            if (Database.LINK == null || Database.LINK.isEmpty()) {
                throw new SQLException("Primary DB connection pool is empty and fallback JDBC URL is unavailable.", e);
            }
            System.out.println("[DB] Primary pool empty, fallback to direct JDBC connection.");
            return DriverManager.getConnection(Database.LINK);
        }
    }

    public Connection getConnection1() throws SQLException {
        if (Database.connPool1 == null) {
            throw new SQLException("Secondary DB connection pool is not initialized.");
        }
        try {
            return Database.connPool1.getConFromPool();
        } catch (NoSuchElementException e) {
            if (Database.LINK1 == null || Database.LINK1.isEmpty()) {
                throw new SQLException("Secondary DB connection pool is empty and fallback JDBC URL is unavailable.", e);
            }
            System.out.println("[DB] Secondary pool empty, fallback to direct JDBC connection.");
            return DriverManager.getConnection(Database.LINK1);
        }
    }

   

    public String addSlashes(String st) {
        StringBuffer sbuff = new StringBuffer();
        for (int i = 0; i < st.length(); ++i) {
            if (st.charAt(i) == '\'') {
                sbuff.append("'");
            } else if (st.charAt(i) == '\\') {
                sbuff.append("\\\\");
            } else {
                sbuff.append(st.charAt(i));
            }
        }
        return sbuff.toString();
    }

    public synchronized boolean checkUsername(String uname) {
        String text = "-1";
        Socket sc = null;
        InputStream in = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream ba = null;
        try {
            sc = new Socket("27.0.14.75", 8008);
            dos = new DataOutputStream(sc.getOutputStream());
            dos.writeBytes("?u=" + uname + "&p=0" + "&t=1" + "&g=21\r\n");
            dos.flush();
            in = sc.getInputStream();
            ba = new ByteArrayOutputStream();
            while (true) {
                byte[] b = {0};
                int x = in.read(b);
                if (x == -1) {
                    return true;
                }
                if (b[0] == 13) {
                    text = ba.toString("UTF-8");
                    try {
                        text = text.substring(0, text.indexOf("HTTP/1.1"));
                    } catch (Exception ignored) {
                    }
                    break;
                }
                ba.write(b);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                sc.close();
            } catch (Exception ignored) {
            }
            try {
                ba.close();
            } catch (Exception ignored) {
            }
            try {
                in.close();
            } catch (Exception ignored) {
            }
            try {
                dos.close();
            } catch (Exception ignored) {
            }
        }
        try {
            sc.close();
        } catch (Exception ignored) {
        }
        try {
            ba.close();
        } catch (Exception ignored) {
        }
        try {
            in.close();
        } catch (Exception ignored) {
        }
        try {
            dos.close();
        } catch (Exception ignored) {
        }
        sc = null;
        ba = null;
        in = null;
        dos = null;
        return Integer.parseInt(text) == -1;
    }

    public String getDateReg(int userid) {
        return Net.getHttp("http://27.0.14.75/usersv/getusername.php?username=teamobi&password=qwertyasdf&u=" + userid);
    }

    public synchronized boolean checkUsernameVTC(String uname) {
        String st = Net.getHttp("http://27.0.14.75/vtc/checkuser_vtc.php?uname=" + uname);
        try {
            return Integer.parseInt(st) == 3;
        } catch (Exception ex) {
            return false;
        }
    }

    public int[] getUserID(String provider, String uname, String pwd) {
        int[] userid = {-1, 0};
        String url = "http://27.0.14.75/vtc/login_vtc.php?uname=" + uname + "&pass=" + pwd + "&provider=" + provider;
        String st = Net.getHttp(url);
        String[] info = Char.split(st.trim(), "|");
        userid[0] = Integer.parseInt(info[0]);
        userid[1] = Integer.parseInt(info[1]);
        return userid;
    }

    public int getUserIDTest(String uname, String pass) {
        String text = "-3";
        String url = "http://game.teamobi.com/app/?for=user&do=login&user=" + uname + "&password=" + pass + "&code=ueh63jn";
        text = Net.getHttp(url);
        return Integer.parseInt(text);
    }

    public int getUserID(String uname, String pwd, int t) {
        String text = "-3";
        Socket sc = null;
        InputStream in = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream ba = null;
        try {
            sc = new Socket("27.0.14.75", 8008);
            dos = new DataOutputStream(sc.getOutputStream());
            in = sc.getInputStream();
            dos.writeBytes("?u=" + uname + "&p=" + pwd + "&t=" + t + "&g=21\r\n");
            dos.flush();
            ba = new ByteArrayOutputStream();
            byte[] b;
            do {
                b = new byte[]{0};
                int x = in.read(b);
                if (x == -1) {
                    return -3;
                }
                if (b[0] == 13) {
                    break;
                }
                ba.write(b);
            } while (b.length <= 1024);
            text = ba.toString("UTF-8");
        } catch (Exception e) {
            System.out.print(" f ");
        } finally {
            try {
                ba.close();
            } catch (Exception ignored) {
            }
            try {
                in.close();
            } catch (Exception ignored) {
            }
            try {
                dos.close();
            } catch (Exception ignored) {
            }
            try {
                sc.close();
            } catch (Exception ignored) {
            }
        }
        try {
            ba.close();
        } catch (Exception ignored) {
        }
        try {
            in.close();
        } catch (Exception ignored) {
        }
        try {
            dos.close();
        } catch (Exception ignored) {
        }
        try {
            sc.close();
        } catch (Exception ignored) {
        }
        return Integer.parseInt(text);
    }

    public void updateAgentTeamUser(int userid, String agent) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String sql = "update team_user set newagent=" + agent + " where userid=" + userid;
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateAgentTeamUser1(int userid, String agent) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String sql = "update team_user_1 set newagent=" + agent + " where userid=" + userid;
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateTeamUserID(int userid, String provider, String agent) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String sql = "update team_user set newprovider='" + provider + "',newagent='" + agent + "' where userid=" + userid;
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateTeamUserID1(int userid, String provider, String agent) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String sql = "update team_user_1 set newprovider='" + provider + "',newagent='" + agent + "' where userid=" + userid;
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addTeamUserID(int userid, String provider, String agent) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String sql = "insert team_user set userid=" + userid + ",newprovider='" + provider + "',newagent='" + agent + "'" + ",provider=0,agent=0";
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public String[] getProviderFromTeamUser1(int userid, String provider, String agent) {
        String[] result = {provider, agent};
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select * from team_user_1 where userid=" + userid;
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                int result2 = rs.getInt("provider");
                int result3 = rs.getInt("agent");
                result[0] = rs.getString("newprovider");
                result[1] = rs.getString("newagent");
                if (result[0] == null || result[1] == null || result[0].equals("-1") || result[1].equals("-1") || result[0].equals("") || result[1].equals("")) {
                    result[0] = String.valueOf(result2);
                    result[1] = String.valueOf(result3);
                    this.updateTeamUserID1(userid, result[0], result[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return result;
    }

    public String[] getProviderFromTeamUser(int userid, String provider, String agent) {
        String[] result = {provider, agent};
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select * from team_user where userid=" + userid;
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                int result2 = rs.getInt("provider");
                int result3 = rs.getInt("agent");
                result[0] = rs.getString("newprovider");
                result[1] = rs.getString("newagent");
                if (result[0] == null || result[1] == null || result[0].equals("-1") || result[1].equals("-1") || result[0].equals("") || result[1].equals("")) {
                    result[0] = String.valueOf(result2);
                    result[1] = String.valueOf(result3);
                    this.updateTeamUserID(userid, result[0], result[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return result;
    }

    public void updateNewClanData(Vector<NewClan> clan) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            for (NewClan c : clan) {
                if (c.money < 0L) {
                    c.money = 0L;
                }
                String sql = "update tob_clan set xp=" + c.xp + ",lvClan=" + c.level + ",money=" + c.money + ",town=" + c.town + ",tax=" + c.tax + ",skillclan='" + c.getSkillClan() + "'" + ",devote=" + c.pDevote + ",country=" + c.country + " where id_clan=" + c.id;
                pre.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getMemberNewClan(NewClan clan) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select charname,pInfo,xp,basepoint from tob_char where idclan=" + clan.id + " and ban=0";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("charname").toLowerCase();
                Char c = new Char(null);
                String dbinfo = rs.getString("pInfo");
                String[] data = Char.split(dbinfo, ",");
                int oldLv = 0;
                try {
                    oldLv = Integer.parseInt(data[20]);
                } catch (Exception ignored) {
                }
                c.charname = name;
                c.lvDetail.setExp(rs.getLong("xp"), oldLv, c.getName(), "database");
                c.rankClan = rs.getByte("basepoint");
                clan.addMember(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public byte getCountryClan(String master) {
        byte idcountry = -1;
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select pInfo from tob_char where charname='" + master + "'";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                String info = rs.getString("pInfo");
                String[] data = Char.split(info, ",");
                try {
                    idcountry = Byte.parseByte(data[39]);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return idcountry;
    }

    public Vector<ClanData> loadClanData() {
        if (Map.isUseNewClan) {
            return new Vector<>();
        }
        return new Vector<>();
    }

    public void loadSellerVip(Hashtable<Byte, CharSellVip> charSeller) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_clan";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadAllQuestTemplate(int type) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from data_quest where mainsub=" + type;
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            short id = 0;
            while (rs.next()) {
                InfoQuestTemplate info = new InfoQuestTemplate();
                info.id = id;
                info.lv = rs.getShort("lv");
                if (!TeamServer.isServerIndo()) {
                    info.name = rs.getString("name");
                    info.content = rs.getString("content");
                    info.shortContent = rs.getString("scontent");
                    info.resContent = rs.getString("rescontent");
                    info.infofinish = rs.getString("infofinish");
                    info.infoGift = rs.getString("infogift");
                } else {
                    info.name = rs.getString("namein");
                    info.content = rs.getString("contentin");
                    info.shortContent = rs.getString("scontentin");
                    info.resContent = rs.getString("rescontentin");
                    info.infofinish = rs.getString("infofinishin");
                    info.infoGift = rs.getString("infogiftin");
                }
                info.idNpcRcv = rs.getShort("npcrcv");
                info.idNpcRes = rs.getShort("npcres");
                info.type = rs.getByte("type");
                info.deltaLv = rs.getShort("deltaLv");
                info.pAvtive = rs.getShort("pactive");
                info.main_sub = rs.getByte("mainsub");
                String[] content = Char.split(rs.getString("idItemget"), ",");
                for (String element : content) {
                    info.itemget.add(Short.parseShort(element));
                }
                content = Char.split(rs.getString("idMonskill"), ",");
                for (String item : content) {
                    info.monsterKill.add(Short.parseShort(item));
                }
                content = Char.split(rs.getString("nitemget"), ",");
                for (String value : content) {
                    info.totalitemget.add(Short.parseShort(value));
                }
                content = Char.split(rs.getString("nmonskill"), ",");
                for (String s : content) {
                    info.totalMonsKilled.add(Short.parseShort(s));
                }
                content = Char.split(rs.getString("potiongift"), ",");
                info.potiongift = new short[content.length];
                for (int i = 0; i < content.length; ++i) {
                    info.potiongift[i] = Short.parseShort(content[i]);
                }
                content = Char.split(rs.getString("npotion"), ",");
                info.npotiongift = new short[content.length];
                for (int i = 0; i < content.length; ++i) {
                    info.npotiongift[i] = Short.parseShort(content[i]);
                }
                content = Char.split(rs.getString("itemgift"), ",");
                info.idItemgift = new short[content.length];
                for (int i = 0; i < content.length; ++i) {
                    info.idItemgift[i] = Short.parseShort(content[i]);
                }
                info.gold = rs.getInt("gold");
                info.luong = rs.getInt("luong");
                info.luongLock = rs.getInt("luongLock");
                info.exp = rs.getInt("exp");
                if (type == 0) {
                    Map.allMainQuest.put(id, info);
                } else if (type == 1) {
                    Map.allSubQuest.put(id, info);
                } else if (type == 2) {
                    Map.allClanQuest.put(id, info);
                }
                ++id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadAllTubinhTemplate() {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_tubinh";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                TuBinhTemplate tb = new TuBinhTemplate();
                if (!TeamServer.isServerIndo()) {
                    tb.name = rs.getString("name");
                } else {
                    tb.name = rs.getString("namein");
                }
                tb.attack = rs.getShort("attack");
                tb.def = rs.getShort("def");
                tb.crit = rs.getShort("crit");
                tb.dog = rs.getShort("dog");
                tb.hp = rs.getShort("hp");
                tb.mp = rs.getShort("mp");
                tb.nmanh = rs.getByte("nmanh");
                tb.level = rs.getByte("level");
                TuBinhTemplate.ALL_TU_BINH.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadAllFruitTemplate() {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_fruit";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                FruitTemplate fruit = new FruitTemplate();
                fruit.id = rs.getShort("id");
                if (!TeamServer.isServerIndo()) {
                    fruit.name = rs.getString("name");
                } else {
                    fruit.name = rs.getString("namein");
                }
                fruit.type = rs.getByte("tfruit");
                fruit.decript = rs.getString("decript");
                fruit.idImage[0] = rs.getShort("id1");
                fruit.idImage[1] = rs.getShort("id2");
                fruit.time[0] = rs.getShort("time1");
                fruit.time[1] = rs.getShort("time2");
                fruit.time[2] = rs.getShort("time3");
                FruitTemplate.ALL_FRUIT_TEMPLATE.add(fruit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadAllNewClaninfo() {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_clan";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                NewClan clanInfo = new NewClan();
                clanInfo.id = rs.getShort("id_clan");
                clanInfo.name = rs.getString("clanName");
                clanInfo.master = rs.getString("charMaster");
                clanInfo.level = rs.getShort("lvClan");
                this.getMemberNewClan(clanInfo);
                clanInfo.money = rs.getLong("money");
                clanInfo.xp = rs.getLong("xp");
                clanInfo.date = rs.getDate("datecreate");
                clanInfo.slogan = rs.getString("slogan");
                clanInfo.isDel = (rs.getInt("isdel") == 1);
                clanInfo.timeStartDel = rs.getLong("tcheckdel");
                clanInfo.country = rs.getByte("country");
                clanInfo.initOtherInfo(rs.getString("otherinfo"));
                clanInfo.level = rs.getShort("lvClan");
                clanInfo.town = rs.getByte("town");
                clanInfo.tax = rs.getByte("tax");
                clanInfo.pDevote = rs.getInt("devote");
                clanInfo.initSkillClan(rs.getString("skillclan"));
                clanInfo.country = rs.getByte("country");
                NewClan.calLevelClan(clanInfo);
                NewClan.addClan(clanInfo);
                NewClan.getSubLeader(clanInfo.id, 1);
                NewClan.getSubLeader(clanInfo.id, 2);
                Database.allClanDB.add(clanInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public static void addClanToMap() {
        Database.allClanDB.removeAllElements();
    }

    public void loadAllClaninfo() {
        if (Map.isUseNewClan) {
            this.loadAllNewClaninfo();
        }
    }

    public short[] getIDClanProTown() {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        short[] result = {-1, -1, -1};
        try {
            final String sql = "select * from tob_clan where town>0";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            final int i = 0;
            while (rs.next()) {
                byte country = rs.getByte("country");
                if (country > -1) {
                    Map.taxOfClan[country] = rs.getByte("tax");
                    result[country] = rs.getShort("id_clan");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return result;
    }

    public Vector<Integer> getIDClanUsed() {
        Vector<Integer> idClan = new Vector<>();
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select id_clan from tob_clan ";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                idClan.add(rs.getInt("id_clan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return idClan;
    }

    public void resetMemberClan() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            final String sql = "select * from tob_clan";
            rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count = 0;
                int id_clan = rs.getInt("id_clan");
                String[] mem = Char.split(rs.getString("member"), ",");
                System.out.println("SO LUONG THANH VIEN " + mem.length);
                StringBuilder newmember = new StringBuilder(mem[0]);
                for (int i = 1; i < mem.length; ++i) {
                    String sql2 = "select * from tob_char where charname='" + mem[i] + "' and idClan=" + id_clan;
                    ResultSet rs2 = conn.createStatement().executeQuery(sql2);
                    if (rs2.next()) {
                        System.out.print(mem[i] + " >> ");
                        ++count;
                        newmember.append(",").append(mem[i]);
                    } else {
                        System.err.println(mem[i]);
                    }
                    try {
                        rs2.close();
                    } catch (Exception ignored) {
                    }
                }
                System.out.println(newmember + " " + id_clan + " SO LUONG SAU KHI UPDATE " + count);
                String sqlUpdate = "update tob_clan set member='" + newmember + "' where id_clan=" + id_clan;
                conn.createStatement().execute(sqlUpdate);
            }
            System.out.println("SO LUONG CHO " + count);
            try {
                Thread.sleep(10000L);
            } catch (Exception ignored) {
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reUpdateClan() {
    }

    public void reupdateIdCLanChar(String charname, int idClan) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_char where charname='" + charname + "'";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                int charDBID = rs.getInt("id");
                Statement stmt2 = null;
                try {
                    stmt2 = conn.createStatement();
                    sql = "update tob_char set idClan=" + idClan + " where id=" + charDBID;
                    stmt2.executeUpdate(sql);
                } catch (Exception e) {
                    try {
                        stmt2.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public NewClan getInfoClan(int idClan) {
        if (idClan == -1) {
            return null;
        }
        return Map.getClaninfoByID(idClan);
    }

    public void getCharPet(Char p, Connection conn) {
        String sql = "";
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            sql = "select * from tob_pet WHERE owner=" + p.charDBID;
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            Vector<Pet> pdelete = new Vector<>();
            while (rs.next()) {
                Pet pet = new Pet(rs.getString("name"), rs.getByte("idImg"), rs.getInt("owner"), rs.getString("info"), rs.getString("itPet"), p.lastLog);
                pet.idDB = rs.getInt("id");
                pet.place = rs.getByte("place");
                pet.id = p.getIDItem();
                String lastLog = Char.split(p.lastLog.trim(), ".")[0].trim();
                if (pet.getTimeExpire() <= 0 || (lastLog.compareTo("2018-11-20 10:46:00") < 0 && pet.isPetLongDenvinhVien())) {
                    if (lastLog.compareTo("2018-11-20 10:46:00") < 0 && pet.isPetLongDenvinhVien()) {
                        p.addLuong(1000);
                    }
                    pdelete.add(pet);
                    this.saveOrtherLog("tob_log_other_item", p.charname, pet.getDBInfo(), "pd");
                    p.sendMessage(MessageCreator.createServerAlertAutoOffMessage(pet.getName() + " Ä‘Ã£ háº¿t thá»i gian sá»­ dá»¥ng"));
                } else {
                    if (pet.place == 1) {
                        p.petUsing = pet;
                    }
                    p.pet.add(pet);
                }
            }
            for (Pet pet : pdelete) {
                this.deletePet(pet.idDB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
    }

    public void getInfoCharClan(Char p) {
        if (p.idClan == -1) {
            return;
        }
    }

    public void delClan(int idClan) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            String sql = "delete from tob_clan where id_clan=" + idClan;
            conn = this.getConnection();
            pre1 = conn.prepareStatement(sql);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public boolean checkClanName(String clanName) {
        Connection conn = null;
        Statement pre1 = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_clan where clanName='" + clanName + "'";
            conn = this.getConnection();
            pre1 = conn.createStatement();
            rs = pre1.executeQuery(sql);
            return !rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public boolean addNewClan(NewClan clan) {
        if (clan.id == -1) {
            return false;
        }
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            final String sql = "insert into tob_clan (id_clan,clanName,charMaster,lvClan,member,money,xp,datecreate,slogan,country,tcheckdel) values (?,?,?,1,?,0,0,now(),?,?,?)";
            conn = this.getConnection();
            pre1 = conn.prepareStatement(sql);
            pre1.setInt(1, clan.id);
            pre1.setString(2, clan.name);
            pre1.setString(3, clan.master);
            pre1.setString(4, clan.master);
            pre1.setString(5, "");
            pre1.setInt(6, clan.country);
            pre1.setLong(7, clan.timeStartDel);
            pre1.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void updateTimeCheckDelClan(int clanID, long time) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            String sql = "UPDATE tob_clan SET tcheckdel=" + time + " where id_clan=" + clanID;
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void banchar() {
        Connection conn = null;
        Statement pre = null;
        Vector<String> charname = readText("hackdo.txt");
        Vector<String> b = new Vector<>();
        try {
            conn = this.getConnection();
            for (String s : charname) {
                String sql = "UPDATE tob_char SET ban=0 where charname='" + s + "'";
                pre = conn.createStatement();
                pre.execute(sql);
                Char pp = CharManager.instance.getCharByCharName(s);
                if (pp != null) {
                    CharManager.instance.kickPlayer(pp, "banchar database");
                }
                System.out.println(s);
                if (!b.contains(s)) {
                    b.add(s);
                    Logger.logString(s, "hack.txt");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            Thread.sleep(30000L);
        } catch (Exception ignored) {
        }
        System.exit(0);
    }

    public void banChar(String charName, int ban) {
        return;
        /*
        Connection conn = null;
        Statement pre = null;
        try {
            String sql = "UPDATE tob_char SET ban=" + ban + " where charname='" + charName + "'";
            conn = this.getConnection();
            pre = conn.createStatement();
            pre.execute(sql);
            if (ban == 1) {
                Char pp = CharManager.instance.getCharByCharName(charName);
                if (pp != null) {
                    CharManager.instance.kickPlayer(pp, "banchar database 2");
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
         */
    }

    public void updateCharNewClan(NewClan clan) {
        if (clan == null || clan.id == -1) {
            return;
        }
        String mem = clan.getMem();
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            final String sql = "UPDATE tob_clan SET charMaster=?,clanName=?,member=?,slogan=?,isdel=?,timedel=?,otherinfo=? where id_clan=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, clan.master);
            pre.setString(2, clan.name);
            pre.setString(3, mem);
            pre.setString(4, clan.slogan);
            pre.setInt(5, clan.isDel ? 1 : 0);
            pre.setLong(6, clan.timeStartDel);
            pre.setString(7, clan.getOtherInfo());
            pre.setInt(8, clan.id);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }

    }

    public void saveVeso(Char player) {
        try {
            String info = player.allNumberBuy();
            String sql = "insert into tob_xoso(owner,vsxu,vsl) values (" + player.charDBID + ",'" + info + "','" + info + "') ON DUPLICATE KEY UPDATE vsxu='" + info + "',vsl='" + player.oldNumber() + "'";
            Vector<String> data = new Vector<>();
            data.add(sql);
            this.saveAllLog(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 

    public void saveLogXoso() {
        String sql = "insert into tob_log_xoso (win,timelog) values (" + Map.winNumber + ",now())";
        Vector<String> data = new Vector<>();
        data.add(sql);
        this.saveAllLog(data);
    }

    public void saveLogThongKeChiTieu(String nameItem, int idtemplate, int soluong, long money, String moneytype) {
        nameItem = BadWord.removeAccent(nameItem);
        String sql = "INSERT INTO tob_log_use_luong" + this.curWeekMonthYear + " set name ='" + nameItem + "', idtemplate=" + idtemplate + ",soluong=" + soluong + ",totalmoney=" + money + ",moneytype='" + moneytype + "' ON DUPLICATE KEY UPDATE soluong=soluong+" + soluong + ",totalmoney=totalmoney+" + money + ",solanmua=solanmua+1";
        Logdata.query.add(sql);
    }

    public void saveOrtherLog(String table, String charname, String info, String action) {
        try {
            info = BadWord.removeAccent(info.toLowerCase());
            String sql = "insert into tob_log_all_item (charname,info,timelog,aclog) values ('" + charname + "','" + info + "',now(),'" + action + "')";
            Logdata.query.add(sql);
            UserLogger.addLog(charname, info, action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTableRace(String tableName) {
        String sql = "CREATE TABLE  " + tableName + "(charid int(10) unsigned NOT NULL," + "charname varchar(45) NOT NULL," + "exp bigint(20) unsigned NOT NULL DEFAULT 0," + "lastlv int(10) unsigned NOT NULL DEFAULT 1," + "idgroup int(10) NOT NULL DEFAULT -1," + "lvchar int(10) unsigned NOT NULL," + "infotop varchar(500) NOT NULL DEFAULT ''," + "headStyle tinyint(3) unsigned NOT NULL DEFAULT 0," + "rcvGift tinyint(3) unsigned NOT NULL DEFAULT 0," + "PRIMARY KEY (charid)," + "KEY Index_2 (idgroup)," + "KEY Index_3 (exp)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println("SQL ERROR=" + sql);
            return;
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            Database.connPool.free(conn);
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        Database.connPool.free(conn);
    }

    public String checkDangKy(String uname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from reg_acount where username=?";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uname);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int userid = rs.getInt("userid");
                return ((userid == 0) ? "90" : "") + rs.getString("ngaysinh");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return "18 11 1984";
    }

    public int getLocalTeamUserId(String username) {
        if (username == null) {
            return -1;
        }
        String normalizedUsername = username.trim().toLowerCase();
        if (normalizedUsername.equals("")) {
            return -1;
        }
        this.ensureLocalTeamUserSchema();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement("SELECT userid FROM team_user WHERE username=? LIMIT 1");
            pstmt.setString(1, normalizedUsername);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userid");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return -1;
    }

    public void addInfoAcount(String uname, String info, String born, int userid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String normalizedUsername = uname == null ? "" : uname.trim().toLowerCase();
            int resolvedUserId = userid;
            if (resolvedUserId <= 0) {
                resolvedUserId = this.getLocalTeamUserId(normalizedUsername);
            }
            if (resolvedUserId < 0) {
                resolvedUserId = 0;
            }
            final String sql = "INSERT INTO reg_acount(username,info,ngaysinh,dateReg,userid) values(?,?,?,now(),?) ON DUPLICATE KEY UPDATE info=?,ngaysinh=?,userid=VALUES(userid)";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, normalizedUsername);
            pstmt.setString(2, info);
            pstmt.setString(3, born);
            pstmt.setInt(4, resolvedUserId);
            pstmt.setString(5, info);
            pstmt.setString(6, born);
            pstmt.setInt(7, resolvedUserId);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateInfoAcount(String uname, int userid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update reg_acount set userid=? where username=?";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            pstmt.setString(2, uname);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addData2Race(Connection conn, String tableName, String charname, int charid, int groupID, int lvchar, String infotop, byte headStyle) {
        Statement stmt = null;
        try {
            String sql = "insert into " + tableName + "(charid,charname,idgroup,lvchar,infotop,headStyle) values(" + charid + ",'" + charname + "'," + groupID + "," + lvchar + ",'" + infotop + "'," + headStyle + ")";
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
    }

    public void initDataRace(String tableName, String dayBegin) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select * from tob_char where lastLog>='" + dayBegin + "' and lastLv>=40 order by lastLv desc";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int count = 0;
            int id = 0;
            while (rs.next()) {
                this.addData2Race(conn, tableName, rs.getString("charname"), rs.getInt("id"), id, rs.getInt("lastlv"), rs.getString("infotop"), rs.getByte("headStyle"));
                System.out.println(rs.getString("charname") + "_" + rs.getInt("lastlv"));
                if (++count % 50 == 0) {
                    ++id;
                }
            }
            System.out.println("TONG CHAR THAM GIA " + count);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            Database.connPool.free(conn);
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        Database.connPool.free(conn);
    }

    public boolean checkTableTobQuaLoiDai() {
        Calendar cl = Calendar.getInstance();
        int w = cl.get(Calendar.DATE);
        int m = cl.get(Calendar.MONTH) + 1;
        int year = cl.get(Calendar.YEAR);
        String rs = "_" + m + year;
        if (w == 16) {
            Database.nameTableTopLoiDai = Database.nameTableTopLoiDai + rs;
            Database.nameTableThachDau = Database.nameTableThachDau + rs;
            System.out.println(Database.nameTableThachDau + " >> " + Database.nameTableTopLoiDai);
            this.createTable("tob_char_thach_dau" + rs, "tob_char_thach_dau");
            return this.createTable("tob_qua_loi_dai" + rs, "tob_qua_loi_dai");
        }
        return false;
    }

    public boolean createTable(String newTable, String oldTable) {
        String sql = "CREATE TABLE " + newTable + " LIKE " + oldTable;
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
            System.err.println("SQL ERROR=" + sql);
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            Database.connPool.free(conn);
        }
        return false;
    }

    public void check() {
        String now = getWeekMonthYear();
        if (!now.equals(this.curWeekMonthYear)) {
            this.curWeekMonthYear = now;
            for (byte i = 0; i < Database.tableNameWeek_month_year.length; ++i) {
                this.createTable(Database.tableNameWeek_month_year[i] + this.curWeekMonthYear, Database.tableNameWeek_month_year[i]);
            }
        }
        now = getMonthYear();
        if (!now.equals(this.curMonthYear)) {
            this.curMonthYear = now;
            for (byte i = 0; i < Database.tableNamemonth_year.length; ++i) {
                this.createTable(Database.tableNamemonth_year[i] + this.curMonthYear, Database.tableNamemonth_year[i]);
            }
        }
    }

    public void checkDropTable() {
        int ndrop = 1000;
        int nday = 90;
        while (ndrop > 0) {
            String now = Database.curWeekMonthYearDrop = getWeekMonthYear1(nday);
            int count = 0;
            for (byte i = 0; i < Database.tableNameWeek_month_year.length; ++i) {
                count += this.DropTable(Database.tableNameWeek_month_year[i] + Database.curWeekMonthYearDrop, Database.tableNameWeek_month_year[i]);
            }
            for (byte i = 0; i < Database.tableNamemonth_year.length; ++i) {
                count += this.DropTable(Database.tableNamemonth_year[i] + Database.curMonthYearDrop, Database.tableNamemonth_year[i]);
            }
            ++nday;
            --ndrop;

        }
    }

    public int DropTable(String newTable, String oldTable) {
        String sql = "drop table " + newTable;
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            if (stmt.execute(sql)) {
                System.out.println(sql);
            }
            return 1;
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            Database.connPool.free(conn);
        }
        return 0;
    }

    public static String getWeekMonthYear1(int d) {
        String y = Char.getYear(-(long) d * 24L * 60L * 60000L);
        String month = Char.getMonth(-(long) d * 24L * 60L * 60000L);
        String day = Char.getDay(-(long) d * 24L * 60L * 60000L);
        Calendar cl = Calendar.getInstance();
        cl.set(Integer.parseInt(y), Integer.parseInt(month) - 1, Integer.parseInt(day));
        int w = cl.get(Calendar.WEEK_OF_MONTH);
        int m = cl.get(Calendar.MONTH) + 1;
        int year = cl.get(Calendar.YEAR);
        String rs = "_" + w + "_" + m + "_" + year;
        Database.curMonthYearDrop = "_" + m + "_" + year;
        return rs;
    }

    public static String getWeekMonthYear() {
        Calendar cl = Calendar.getInstance();
        int w = cl.get(Calendar.WEEK_OF_MONTH);
        int m = cl.get(Calendar.MONTH) + 1;
        int year = cl.get(Calendar.YEAR);
        String rs = "_" + w + "_" + m + "_" + year;
        Database.curMonthYearDrop = "_" + m + "_" + year;
        return rs;
    }

    public static String getMonthYear() {
        Calendar cl = Calendar.getInstance();
        int m = cl.get(Calendar.MONTH) + 1;
        int year = cl.get(Calendar.YEAR);
        return "_" + m + year;
    }

    public void saveAllLog(Vector<String> data) {
        this.check();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            while (data.size() > 0) {
                String st = data.remove(0);
                try {
                    stmt.execute(st);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("LOI SAVE LOG " + st);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveLogClan(String charname, String actioninfo, String info) {
        try {
            String sql = "insert into tob_log_clan (charname,actioninfo,info,timeLog) values ('" + charname + "','" + actioninfo + "','" + info + "',Now())";
            Logdata.query.add(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addInfoWebChar(Char player) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String sql = "INSERT INTO tob_web_info_char set charname=?,infoWearing=?,otherInfo=?,userid=?,lv=?,acountname=? ON DUPLICATE KEY UPDATE infoWearing=?,otherInfo=?,lv=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, player.charname);
            pre.setString(2, player.getInfoWebWearing());
            pre.setString(3, "");
            pre.setInt(4, player.userID);
            pre.setInt(5, player.lvDetail.lv);
            pre.setString(6, player.getAccountName());
            pre.setString(7, player.getInfoWebWearing());
            pre.setString(8, "");
            pre.setInt(9, player.lvDetail.lv);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

   
  

    public void saveLogActiveUser(String charname, String info) {
    }

 

    public void saveLogLearnSkill(String charname, String info) {
        try {
            String sql = "insert into tob_log_all_item (charname,charname2,info,timelog,aclog) values ('" + charname + "','" + charname + "','" + info + "',Now(),'hocskill')";
            Logdata.query.add(sql);
            UserLogger.addLog(charname, info, "hocskill");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLogNapMoney(String charname, String info) {
        try {
            String sql = "insert into tob_log_all_item (charname,charname2,info,timelog,aclog) values ('" + charname + "','" + charname + "','" + info + "',Now(),'naptien')";
            Logdata.query.add(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLogTradeItem(String from, String to, String info) {
        try {
            String sql = "insert into tob_log_all_item(charname,charname2,info,timelog,aclog) values ('" + from + "','" + to + "','" + info + "',Now(),'trade')";
            Logdata.query.add(sql);
            UserLogger.addLog(from, to + "_" + info, "ftrade");
            UserLogger.addLog(to, from + "_" + info, "ttrade");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLogItemSellShop(String seller, int charID, String info) {
        try {
            info = BadWord.removeAccent(info);
            String sql = "insert into tob_log_all_item (idchar,charname,info,timelog,aclog) values (" + charID + ",'" + seller + "','" + info + "',Now(),'sitems')";
            Logdata.query.add(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLogSellItem(String seller, String customer, String info) {
        try {
            info = BadWord.removeAccent(info);
            String sql = "insert into tob_log_all_item (charname,charname2,info,timelog,aclog) values ('" + seller + "','" + customer + "','" + info + "',Now(),'sitemu')";
            Logdata.query.add(sql);
            UserLogger.addLog(seller, customer + "_" + info, "sitemu");
            UserLogger.addLog(customer, seller + "_" + info, "bitemu");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI LOG MUA ITEM USER");
        }
    }


    public void saveLogBuyItemShop(String seller, String info, int type, int price, String money) {
        try {
            String sql = "insert into tob_log_buy_shop (charname,info,timeBuy,typeitem,price,money) values ('" + seller + "','" + info + "',Now()," + type + "," + price + ",'" + money + "')";
            Logdata.query.add(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateClanQuest(Char p) {
    }

    public ClanQuest loadCCharQuest(Char p) {
        Connection conn = null;
        Statement stmt = null;
        ClanQuest cq = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tob_quest_clan WHERE id_char=" + p.charDBID;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                cq = new ClanQuest();
                cq.idQuest = rs.getByte("idquest");
                cq.rcv = rs.getByte("rcv");
                cq.finish = rs.getByte("finish");
                cq.item_mons[3] = (short) rs.getInt("nmons1");
                cq.item_mons[4] = (short) rs.getInt("nmons2");
                cq.item_mons[5] = (short) rs.getInt("nmons3");
                cq.item_mons[0] = (short) rs.getInt("nitem1");
                cq.item_mons[1] = (short) rs.getInt("nitem2");
                cq.item_mons[2] = (short) rs.getInt("nitem3");
                cq.count = rs.getByte("n");
                try {
                    cq.timeEndQClan = rs.getLong("timeEnd");
                } catch (Exception ignored) {
                }
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("KHONG TON TAI CHAR QUEST");
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return cq;
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return cq;
    }

    public void isCheckCharQuest(int idChar) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "update top_char_quest set ischeck=1 WHERE id_char=" + idChar;
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("KHONG TON TAI CHAR QUEST");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public CharQuest loadCharQuest(int idChar) {
        Connection conn = null;
        Statement stmt = null;
        CharQuest cq = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM top_char_quest WHERE id_char=" + idChar + " and ischeck=0";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                cq = new CharQuest();
                cq.id_quest = rs.getInt("id_quest");
                cq.receive = (byte) rs.getInt("receive");
                cq.finish = (byte) rs.getInt("finish");
                cq.nMonster[0] = (short) rs.getInt("nmonster1");
                cq.nMonster[1] = (short) rs.getInt("nmonster2");
                cq.nMonster[2] = (short) rs.getInt("nmonster3");
                cq.nItem[0] = (short) rs.getInt("nitem1");
                cq.nItem[1] = (short) rs.getInt("nitem2");
                cq.nItem[2] = (short) rs.getInt("nitem3");
                cq.currentNpc = (short) rs.getInt("currentnpc");
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("KHONG TON TAI CHAR QUEST");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return cq;
    }

    public void getCharQuest(Char p) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tob_char_quest WHERE id_char=" + p.charDBID;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String itemget = rs.getString("itemget");
                String monskilled = rs.getString("monskilled");
                String finish = rs.getString("isfinish");
                String itquest = rs.getString("itemquest");
                p.itemget = itemget;
                p.monskilled = monskilled;
                p.finish = finish;
                p.itquest = itquest;
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("KHONG TON TAI CHAR QUEST");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<TuBinhTemplate> loadCharTubinh0610(Char p) {
        Vector<TuBinhTemplate> all = new Vector<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_char_tubinh_0610 where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.charDBID);
            rs = stmt.executeQuery();
            if (rs.next() && !rs.getString("listid").equals("")) {
                String[] id = Char.split(rs.getString("listid"), ",");
                String[] manh = Char.split(rs.getString("listmanh"), "|");
                for (int i = 0; i < id.length; ++i) {
                    TuBinhTemplate tb = new TuBinhTemplate();
                    tb.id = Byte.parseByte(id[i]);
                    String[] manh2 = Char.split(manh[i], ",");
                    for (int j = 0; j < manh2.length; ++j) {
                        tb.manh[j] = Byte.parseByte(manh2[j]);
                    }
                    all.add(tb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return all;
    }

    public Vector<TuBinhTemplate> loadCharTubinh(Char p) {
        Vector<TuBinhTemplate> all = new Vector<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_char_tubinh where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.charDBID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("listid").equals("")) {
                    p.createTubinh();
                    this.saveCharTubinh(p.charDBID, p.getInfoSaveTubinh());
                    return p.tubinh;
                }
                String[] id = Char.split(rs.getString("listid"), ",");
                String[] manh = Char.split(rs.getString("listmanh"), "|");
                for (int i = 0; i < id.length; ++i) {
                    TuBinhTemplate tb = new TuBinhTemplate();
                    tb.id = Byte.parseByte(id[i]);
                    String[] manh2 = Char.split(manh[i], ",");
                    for (int j = 0; j < manh2.length; ++j) {
                        tb.manh[j] = Byte.parseByte(manh2[j]);
                    }
                    all.add(tb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return all;
    }

    public void saveCharTubinh(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char_tubinh set listid=?,listmanh=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setInt(3, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE FRUIT ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharTubinh(int idDBChar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "insert into tob_char_tubinh(id_char,listid,listmanh) values(?,?,?) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDBChar);
            stmt.setString(2, "");
            stmt.setString(3, "");
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public FruitTemplate[] loadCharFruit(Char p) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_char_fruit where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.charDBID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                FruitTemplate[] fr = new FruitTemplate[FruitTemplate.MAX_FRUIT];
                if (!rs.getString("listidfruit").equals("")) {
                    {
                        try {
                            String[] id = Char.split(rs.getString("listidfruit"), ",");
                            String[] status = Char.split(rs.getString("stFruit"), ",");
                            String[] time = Char.split(rs.getString("tFruit"), ",");
                            String[] gift = Char.split(rs.getString("gift"), ",");
                            String[] theo = Char.split(rs.getString("theo"), ",");
                            String[] tkho = Char.split(rs.getString("tkho"), ",");
                            for (int i = 0; i < FruitTemplate.MAX_FRUIT; ++i) {
                                FruitTemplate f = new FruitTemplate();
                                f.id = Short.parseShort(id[i]);
                                f.status = Byte.parseByte(status[i]);
                                f.tcount = Long.parseLong(time[i]);
                                f.gift = Integer.parseInt(gift[i]);
                                f.tcountTangtoc = Long.parseLong(theo[i]);
                                f.tcountKho = Long.parseLong(tkho[i]);
                                f.type = FruitTemplate.ALL_FRUIT_TEMPLATE.get(f.id).type;
                                fr[i] = f;
                            }
                        } catch (Exception e) {
                            p.fruit = new FruitTemplate[FruitTemplate.MAX_FRUIT];
                            p.createFruit();
                            this.saveCharFruit(p.charDBID, p.getInfoSaveFruit());
                            return p.fruit;
                        }
                    }
                    return fr;
                }
                p.createFruit();
                this.saveCharFruit(p.charDBID, p.getInfoSaveFruit());
                return p.fruit;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void saveCharFruit(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char_fruit set listidfruit=?,stFruit=?,tFruit=?,gift=?,theo=?,tkho=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setString(3, info[2]);
            stmt.setString(4, info[3]);
            stmt.setString(5, info[4]);
            stmt.setString(6, info[5]);
            stmt.setInt(7, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE FRUIT ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharFruit(int idDBChar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "insert into tob_char_fruit(id_char,listidfruit,stFruit,tFruit,gift,theo,tkho) values(?,?,?,?,?,?,?) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDBChar);
            stmt.setString(2, "");
            stmt.setString(3, "");
            stmt.setString(4, "");
            stmt.setString(5, "");
            stmt.setString(6, "");
            stmt.setString(7, "");
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharQuest(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char_quest set itemget=?,monskilled=?,isfinish=?, itemquest=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setString(3, info[2]);
            stmt.setString(4, info[3]);
            stmt.setInt(5, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE QUEST ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    

    public void addCharQuest(int idDBChar, String[] info) {
        if (info == null) {
            return;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "insert into tob_char_quest(id_char,itemget,monskilled,isfinish) values(?,?,?,?) "
                    + "on duplicate key update itemget=values(itemget), monskilled=values(monskilled), isfinish=values(isfinish)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDBChar);
            stmt.setString(2, info[0]);
            stmt.setString(3, info[1]);
            stmt.setString(4, info[2]);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE QUEST ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharQuest(int idDBChar, CharQuest c) {
        if (c == null) {
            return;
        }
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "update top_char_quest set id_quest=" + c.id_quest + ", receive=" + c.receive + ",finish=" + c.finish + ", nmonster1=" + c.nMonster[0] + ",nmonster2=" + c.nMonster[1] + ",nmonster3=" + c.nMonster[2] + ",nitem1=" + c.nItem[0] + ",nitem2=" + c.nItem[1] + ",nitem3=" + c.nItem[2] + ",currentnpc=" + c.currentNpc + " where id_char=" + idDBChar;
            if (stmt.executeUpdate(sql) == 0) {
                sql = "insert top_char_quest (id_char,id_quest,receive,finish,nmonster1,nmonster2,nmonster3,nitem1,nitem2,nitem3,currentnpc) values (" + idDBChar + "," + c.id_quest + "," + c.receive + "," + c.finish + "," + c.nMonster[0] + "," + c.nMonster[1] + "," + c.nMonster[2] + "," + c.nItem[0] + "," + c.nItem[1] + "," + c.nItem[2] + "," + c.currentNpc + ")";
                stmt.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE QUEST ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    

    public int getMoneyFromNameGem(String name) {
        for (int i = 0; i < Map.gemTemplate.length; ++i) {
            if (name.equals(Map.gemTemplate[i].name) && i != 13) {
                return Map.gemTemplate[i].getPrice(Map.isSale);
            }
        }
        for (ItemTemplates item : this.templateTest) {
            if (item.name.equals(name)) {
                System.out.println(name + " >> " + item.price);
                return item.price;
            }
        }
        return 0;
    }

    public void updateMoney() {
        new Thread(() -> {
            Connection conn = null;
            Statement pre = null;
            ResultSet rs = null;
            try {
                conn = Database.this.getConnection();
                pre = conn.createStatement();
                while (!Database.this.stop) {
                    while (Database.this.sqlUpdate.size() > 0) {
                        pre.execute(Database.this.sqlUpdate.remove(0));
                    }
                    Thread.sleep(1L);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("LOI UPDATE MONEY");
            } finally {
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }).start();
    }


  

    public int checkLoginIndo(String name, String pass) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pre = conn.createStatement();
            String query = "select * from tob_acount where username='" + name + "' and pass=password('" + pass + "')";
            rs = pre.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public synchronized void ensureLocalTeamUserSchema() {
        if (LOCAL_TEAM_USER_SCHEMA_READY) {
            return;
        }
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS team_user ("
                            + "id int NOT NULL AUTO_INCREMENT,"
                            + "userid int unsigned NOT NULL,"
                            + "provider int unsigned NOT NULL DEFAULT 0,"
                            + "agent int NOT NULL DEFAULT -1,"
                            + "newagent varchar(45) NOT NULL DEFAULT '-1',"
                            + "newprovider varchar(45) NOT NULL DEFAULT '-1',"
                            + "username varchar(20) DEFAULT NULL,"
                            + "password varchar(100) DEFAULT NULL,"
                            + "phone varchar(20) NOT NULL DEFAULT '',"
                            + "regdate datetime DEFAULT NULL,"
                            + "ban tinyint(1) NOT NULL DEFAULT 0,"
                            + "fromgame tinyint NOT NULL DEFAULT 0,"
                            + "email varchar(100) DEFAULT NULL,"
                            + "PRIMARY KEY (id),"
                            + "UNIQUE KEY uk_team_user_userid (userid),"
                            + "UNIQUE KEY uk_team_user_username (username)"
                            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci"
            );
            String[] alterStatements = new String[]{
                "ALTER TABLE team_user ADD COLUMN username varchar(20) DEFAULT NULL",
                "ALTER TABLE team_user ADD COLUMN password varchar(100) DEFAULT NULL",
                "ALTER TABLE team_user ADD COLUMN phone varchar(20) NOT NULL DEFAULT ''",
                "ALTER TABLE team_user ADD COLUMN regdate datetime DEFAULT NULL",
                "ALTER TABLE team_user ADD COLUMN ban tinyint(1) NOT NULL DEFAULT 0",
                "ALTER TABLE team_user ADD COLUMN fromgame tinyint NOT NULL DEFAULT 0",
                "ALTER TABLE team_user ADD COLUMN email varchar(100) DEFAULT NULL"
            };
            for (String sql : alterStatements) {
                try {
                    stmt.execute(sql);
                } catch (Exception ignored) {
                }
            }
            try {
                stmt.execute("CREATE UNIQUE INDEX uk_team_user_username ON team_user(username)");
            } catch (Exception ignored) {
            }
            LOCAL_TEAM_USER_SCHEMA_READY = true;
            System.out.println("Local account schema ready on kpah1.team_user");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    public int checkLoginLocalTeamUser(String username, String password) {
        this.ensureLocalTeamUserSchema();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement("SELECT userid, password, ban FROM team_user WHERE username=? LIMIT 1");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            if (rs.getInt("ban") > 0) {
                return -2;
            }
            String storedPassword = rs.getString("password");
            if (!isLocalTeamUserPasswordMatch(storedPassword, password)) {
                return -1;
            }
            return rs.getInt("userid");
        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    public int registerLocalTeamUser(String username, String password) {
        this.ensureLocalTeamUserSchema();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement("SELECT userid FROM team_user WHERE username=? LIMIT 1");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return -2;
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }

            int nextUserId = 1;
            pstmt = conn.prepareStatement("SELECT COALESCE(MAX(userid), 0) + 1 AS next_user_id FROM team_user");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nextUserId = rs.getInt("next_user_id");
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }

            pstmt = conn.prepareStatement(
                    "INSERT INTO team_user(userid,provider,agent,newagent,newprovider,username,password,phone,regdate,ban,fromgame,email) "
                            + "VALUES(?,0,-1,'0','0',?,?,'',NOW(),0,0,NULL)"
            );
            pstmt.setInt(1, nextUserId);
            pstmt.setString(2, username);
            pstmt.setString(3, hashLocalAccountPassword(password));
            pstmt.executeUpdate();
            return nextUserId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return -2;
            }
            e.printStackTrace();
            return -3;
        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    public static String hashLocalAccountPassword(String input) {
        try {
            java.security.MessageDigest sha1 = java.security.MessageDigest.getInstance("SHA-1");
            byte[] first = sha1.digest(input.getBytes("UTF-8"));
            byte[] second = sha1.digest(first);
            StringBuilder sb = new StringBuilder("*");
            for (byte b : second) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Cannot hash password", e);
        }
    }

    private boolean isLocalTeamUserPasswordMatch(String storedPassword, String inputPassword) {
        if (storedPassword == null || inputPassword == null) {
            return false;
        }
        if (storedPassword.equals(inputPassword)) {
            return true;
        }
        return storedPassword.equalsIgnoreCase(hashLocalAccountPassword(inputPassword));
    }

    public int addNewAcountAuto(String uname, String pass) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            String sql = "insert into tob_acount (username,pass,timereg) values('" + uname.toLowerCase() + "',password('" + pass + "'),now())";
            pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.execute();
            ResultSet rss = pre.getGeneratedKeys();
            rss.next();
            int charId = rss.getInt(1);
            try {
                rss.close();
            } catch (Exception ignored) {
            }
            return charId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return -1;
    }

  
    public List<CharData> getCharList(int userID, Session s) {
        List<CharData> list = null;
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pre = null;
        StringBuilder listChar = new StringBuilder();
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tob_char WHERE userId=? and idserver=" + s.idServer;
            if (TeamServer.isServerLienDau()) {
                sql = "SELECT * FROM tob_char WHERE userId=?";
            }
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userID);
            ResultSet rs = pre.executeQuery();
            ResultSet rsItem = null;
            list = new Vector<>();
            while (rs.next()) {
                CharData ch = new CharData();
                if (rs.getInt("ban") == 0) {
                    ch.id = rs.getInt("id");
                    ch.charname = rs.getString("charname");
                    ch.headStyle = rs.getByte("headStyle");
                    ch.lv = rs.getShort("lastLv");
                    if (ch.lv == 0) {
                        ch.lv = 1;
                    }
                    ch.using = rs.getInt("del");
                    ch.timeDell = rs.getLong("tDelChar");
                    ch.charClass = rs.getByte("class");
                    if (ch.using == 0 && 7 - (int) ((System.currentTimeMillis() - ch.timeDell) / 1000L / 60L / 60L / 24L) <= 0) {
                        this.delChar(ch.id);
                    } else {
                        byte slot = 0;
                        int timeXP = rs.getShort("y");
                        try {
                            String[] data = Char.split(rs.getString("pInfo"), ",");
                            ch.gender = Byte.parseByte(data[1]);
                            slot = Byte.parseByte(data[36]);
                            ch.myCountry = Byte.parseByte(data[39]);
                            String dayLogin = data[43].trim();
                            if (!dayLogin.equals(Char.getDayOpen(0L))) {
                                timeXP = 0;
                            }
                        } catch (Exception ignored) {
                        }
                        if (s.timeXp < timeXP) {
                            s.timeXp = timeXP;
                        }
                        listChar.append(ch.charname).append("_").append(ch.id).append(" >> ");
                        String info = rs.getString("infotop");
                        try {
                            String[] data2 = Char.split(info, ",");
                            for (short i = 1; i < data2.length; i += 4) {
                                Item item = new Item();
                                item.setTemplate(Integer.parseInt(data2[i]), Integer.parseInt(data2[i + 1]), Integer.parseInt(data2[i + 1]), Integer.parseInt(data2[i]));
                                ch.item.add(item);
                                if (item.isPhiPhongKhongTuoc()) {
                                    ch.idBigAvtPP = Char.ID_BIG_AVT_PP_KHONG_TUOC;
                                    ch.indexXyPP = 0;
                                    ch.nFramePP = 3;
                                } else if (item.isPhiPhongHuyenVu()) {
                                    ch.idBigAvtPP = Char.ID_BIG_AVT_PP_HUYEN_VU;
                                    ch.indexXyPP = 1;
                                    ch.nFramePP = 3;
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        list.add(ch);
                    }
                }
            }
            try {
                rsItem.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            System.out.print("LOI KHI LOAD CHARLIST");
            s.sendMessage(MessageCreator.createServerAlertMessage("MÃ¡y chá»§ Ä‘ang quÃ¡ táº£i. Xin quay láº¡i sau Ã­t phÃºt. Xin cáº£m Æ¡n", ""));
        } finally {
            try {
                stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
       
        return list;
    }

    

    public ItemTemplates copyItem(ItemTemplates it, int type) {
        int[] percent = {20, 50, 10, 70, 20};
        ItemTemplates item = new ItemTemplates();
        item.kind_class = type;
        item.id = it.id;
        item.name = it.name;
        item.type = it.type;
        item.style = it.style;
        item.he = it.he;
        item.gender = it.gender;
        item.level = it.level;
        item.durable = it.durable;
        item.clazz = it.clazz;
        item.idBigImgAvatar = it.idBigImgAvatar;
        boolean isModelClothes = Map.isModelClothes(it.atb[9]);
        if (it.type != 26 && it.type != 27) {
            item.atb[0] = it.atb[0];
            item.atb[1] = (short) (it.atb[1] + (isModelClothes ? 0 : (it.atb[1] * percent[type] / 100)));
            item.atb[2] = (short) (it.atb[2] + (isModelClothes ? 0 : (it.atb[2] * percent[type] / 100)));
            item.atb[3] = (short) (it.atb[3] + (isModelClothes ? 0 : (it.atb[3] * percent[type] / 100)));
        } else {
            item.atb[0] = it.atb[0];
            item.atb[1] = it.atb[1];
            item.atb[2] = it.atb[2];
            item.atb[3] = it.atb[3];
        }
        item.atb[4] = it.atb[4];
        item.atb[5] = it.atb[5];
        item.atb[6] = it.atb[6];
        if (!isModelClothes) {
            item.atb[6] = item.atb[1];
        }
        item.atb[7] = it.atb[7];
        item.atb[8] = it.atb[8];
        item.atb[9] = it.atb[9];
        item.price = it.price;
        item.idIcon = it.idIcon;
        item.ystart = it.ystart;
        item.color = it.color;
        item.ndayLoan = it.ndayLoan;
        item.idItemUpLevel = it.idItemUpLevel;
        item.idEff = it.idEff;
        return item;
    }

    public Vector<Hashtable<Integer, ItemTemplates>> loadItemTemplate() {
        Vector<Hashtable<Integer, ItemTemplates>> itemTemplates = new Vector<>();
        Hashtable<Integer, ItemTemplates> itemNhe = new Hashtable<>();
        Hashtable<Integer, ItemTemplates> itemNheCung = new Hashtable<>();
        Hashtable<Integer, ItemTemplates> itemNangCB = new Hashtable<>();
        Hashtable<Integer, ItemTemplates> itemVai = new Hashtable<>();
        Hashtable<Integer, ItemTemplates> itemNangDS = new Hashtable<>();
        Hashtable<Integer, ItemTemplates> itemBasic = new Hashtable<>();
        Connection conn = null;
        Statement stmt = null;
        final int i = 0;
        System.out.println("LOAD TEMPLATE ");
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            final String sql = "SELECT * FROM data_item order by id ";
            ResultSet rs = stmt.executeQuery(sql);
            int iii = 0;
            while (rs.next()) {
                ++iii;
                ItemTemplates item = new ItemTemplates();
                item.id = rs.getInt("id");
                if (!TeamServer.isServerIndo()) {
                    item.name = rs.getString("name");
                } else {
                    item.name = rs.getString("namein");
                }
                item.type = rs.getByte("type");
                item.style = rs.getByte("style");
                item.he = rs.getByte("he");
                item.gender = rs.getByte("gender");
                item.level = rs.getByte("level");
                item.durable = rs.getShort("durable");
                item.atb[0] = rs.getShort("atb0");
                item.atb[1] = rs.getShort("atb1");
                item.atb[2] = rs.getShort("atb2");
                item.atb[3] = rs.getShort("atb3");
                item.atb[4] = rs.getShort("atb4");
                item.atb[5] = rs.getShort("atb5");
                item.atb[6] = rs.getShort("atb6");
                item.atb[7] = rs.getShort("atb7");
                item.atb[8] = rs.getShort("atb8");
                item.atb[9] = rs.getShort("atb9");
                item.price = rs.getInt("price");
                item.clazz = rs.getByte("clazz");
                item.idIcon = rs.getShort("xstart");
                item.color = rs.getByte("colortype");
                item.ndayLoan = rs.getShort("nloan");
                item.idItemUpLevel = rs.getShort("idUpLevel");
                item.idEff = rs.getInt("ideff");
                item.idBigImgAvatar = rs.getShort("ystart");
                if (!Map.isModelClothes(item.atb[9])) {
                    item.atb[6] = item.atb[1];
                }
                itemNhe.put(item.id, this.copyItem(item, 0));
                itemNangCB.put(item.id, this.copyItem(item, 1));
                itemVai.put(item.id, this.copyItem(item, 2));
                itemNangDS.put(item.id, this.copyItem(item, 3));
                itemNheCung.put(item.id, this.copyItem(item, 4));
                itemBasic.put(item.id, item);
            }
            itemTemplates.add(itemNhe);
            itemTemplates.add(itemNangCB);
            itemTemplates.add(itemVai);
            itemTemplates.add(itemNangDS);
            itemTemplates.add(itemNheCung);
            itemTemplates.add(itemBasic);
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
       
        return itemTemplates;
    }

    

    public void loadMonsterTemplate(Hashtable<Integer, MonsterTemplate> list) {
      
        Connection conn = null;
        Statement stmt = null;
        try {
            list.put(0, new MonsterTemplate());
            conn = this.getConnection();
            stmt = conn.createStatement();
            final String sql = "SELECT * FROM data_monster";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MonsterTemplate item = new MonsterTemplate();
                item.id = rs.getInt("id");
                if (!TeamServer.isServerIndo()) {
                    item.name = rs.getString("name");
                } else {
                    item.name = rs.getString("namein");
                }
                item.type = rs.getShort("style");
                item.he = rs.getByte("he");
                item.level = rs.getByte("level");
                item.maxhp = rs.getInt("maxhp");
                item.attack = rs.getInt("attack");
                item.defend = rs.getInt("defent");
                item.accurate = rs.getShort("accurate");
                item.dodge = rs.getShort("dodge");
                item.rcvGold = rs.getInt("rcvgold");
                item.rcvXp = rs.getInt("rcvxp");
                item.active = (rs.getInt("active") == 1);
                item.palate = rs.getShort("palate");
                item.spalate = rs.getByte("spalate");
                item.typeMons = rs.getByte("isuse");
                item.move = rs.getByte("move");
                item.isNewMonster = rs.getByte("isnew");
                list.put(item.id, item);
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
       
    }

    public Vector<ShopTemplate> loadShopTemplate() {
        Vector<ShopTemplate> shopTemplates = new Vector<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            final String sql = "SELECT * FROM data_shop";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int value = rs.getInt("value");
                int price = rs.getInt("price");
                int priceSale = rs.getInt("priceSale");
                if (id >= LOCKED_MATERIAL_SHOP_MIN_ID && id <= LOCKED_MATERIAL_SHOP_MAX_ID) {
                    value = LOCKED_MATERIAL_SHOP_AMOUNT;
                    price *= LOCKED_MATERIAL_SHOP_AMOUNT;
                    priceSale *= LOCKED_MATERIAL_SHOP_AMOUNT;
                }

                ShopTemplate item = new ShopTemplate(
                    id,
                    rs.getInt("idImg"),
                    rs.getString("name"),
                    rs.getString("decript"),
                    value,
                    price,
                    rs.getInt("type"),
                    rs.getInt("tab"),
                    rs.getInt("sell"),
                    priceSale
                );
                shopTemplates.add(item);
            }
            try { rs.close(); } catch (Exception ignored) {}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception ignored) {}
            try { Database.connPool.free(conn); } catch (Exception ignored) {}
        }
        return shopTemplates;
    }
    

    public Vector<PotionTemplate2> loadPotionTemplate() {
        Vector<PotionTemplate2> potionTemplates2 = new Vector<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            final String sql = "SELECT * FROM data_potion";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PotionTemplate2 potion = new PotionTemplate2(
                    rs.getShort("id"),
                    rs.getShort("idImage"),
                    rs.getShort("delay"),
                    rs.getBoolean("isTrade"),
                    rs.getString("name"),
                    rs.getString("name2"),
                    rs.getInt("price"),
                    rs.getInt("recovered")
                );
                potionTemplates2.add(potion);
            }
            try { rs.close(); } catch (Exception ignored) {}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception ignored) {}
            try { Database.connPool.free(conn); } catch (Exception ignored) {}
        }
        return potionTemplates2;
    }

    
   

    public static boolean isPhiPhong(int id) {
        return (id >= 584 && id <= 597) || id == 608;
    }

   

   




    public void getTopPhaoHoa() {
        if (Char.getDayTime(0L).compareTo("2017-10-20 10:50:00") >= 0) {
            return;
        }
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            int next = 100000;
            int start = 0;
            int count = -1;
            int stop = 0;
            while (true) {
                if (count == 0) {
                    if (stop == 20) {
                        break;
                    }
                    ++stop;
                }
                if (count > 0) {
                    count = 0;
                }
                String sql = "select lastlog, pInfo,id,charname,lastlv,ban,potion from tob_char where id>=" + start + " and id<" + next + " and ban=0";
                System.out.println(start + " >> " + next);
                start = next;
                next += 100000;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ++count;
                    String lastlog = rs.getString("lastlog");
                    try {
                        String[] data = Char.split(rs.getString("pInfo"), ",");
                        String[] potion = Char.split(rs.getString("potion"), ",");
                        int bi = 0;
                        try {
                            bi = Integer.parseInt(data[90]);
                        } catch (Exception ignored) {
                        }
                        if (bi <= 0) {
                            continue;
                        }
                        System.out.println(rs.getString("charname") + " " + bi);
                        Logger.logString(rs.getString("charname"), "longdenchar.txt");
                        Logger.logString(String.valueOf(bi), "longden.txt");
                        Logger.logString(lastlog, "longdendaylog.txt");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    public void updateGemItem2Char(int chardbid, int[] idGem, int[] nItem) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from tob_gem_new where owner='" + chardbid + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String[] data = Char.split(rs.getString("soluong"), ",");
                String[] data2 = Char.split(rs.getString("slock"), ",");
                int[] totalGem = new int[Map.gemTemplate.length];
                int[] totalGemLock = new int[Map.gemTemplate.length];
                for (int i = 0; i < data.length; ++i) {
                    try {
                        totalGem[i] = Integer.parseInt(data[i]);
                    } catch (Exception ignored) {
                    }
                    try {
                        totalGemLock[i] = Integer.parseInt(data2[i]);
                    } catch (Exception ignored) {
                    }
                }
                for (int i = 0; i < idGem.length; ++i) {
                    int n = idGem[i];
                    totalGem[n] += nItem[i];
                }
                StringBuilder soluong = new StringBuilder(String.valueOf(totalGem[0]));
                StringBuilder soluonglock = new StringBuilder(String.valueOf(totalGemLock[0]));
                for (int j = 1; j < totalGem.length; ++j) {
                    soluong.append(",").append(totalGem[j]);
                    soluonglock.append(",").append(totalGemLock[j]);
                }
                this.saveCharGem(soluong.toString(), rs.getInt("owner"), soluonglock.toString());
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void giveGemItem() {
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            int count = 0;
            int i = 0;
            Vector<String> name = readText("trangan.txt");
            int[] idGem = {73, 80, 87, 94, 101, 108, 115, 122, 129, 136};
            int[] soluong = {5, 5, 5, 5, 5, 1, 1, 1, 1, 1};
            while (i < name.size()) {
                String charname = name.get(i);
                String sql1 = "select * from tob_char where charname='" + charname + "'";
                ResultSet rs2 = stmt2.executeQuery(sql1);
                if (rs2.next()) {
                    System.out.println(name.get(i) + " >> " + sql1);
                    ++count;
                    this.updateGemItem2Char(rs2.getInt("id"), idGem, soluong);
                    Logger.logString(name.get(i), "datra4.txt");
                } else {
                    Logger.logString(name.get(i), "saitenchar4.txt");
                }
                try {
                    rs2.close();
                } catch (Exception ignored) {
                }
                ++i;
            }
            System.out.println("SO LUONG CHO " + count);
            try {
                Thread.sleep(10000L);
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        } finally {
            try {
                Database.connPool.free(conn);
                Database.connPool.closeIdleConnection();
            } catch (Exception ignored) {
            }
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                stmt2.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
            Database.connPool.closeIdleConnection();
        } catch (Exception ignored) {
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            stmt2.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        System.exit(0);
    }

    
    public void giveItem(String charname) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from tob_char where charname='" + charname + "'";
            rs = stmt.executeQuery(sql);
            Char p = null;
            if (rs.next()) {
                p = new Char(null);
                p.charDBID = rs.getInt("id");
                for (int k = 0; k < 9; ++k) {
                    int[] templateThue = {19, 45, 71, 21, 47, 73, 23, 49, 75};
                    int[] template = {19, 45, 71, 21, 47, 73, 23, 49, 75};
                    int[] maintm = {3, 3, 3, 3, 3, 3, 3, 3, 3};
                    ItemTemplates itTemp = Map.itemTemplates.get(maintm[k]).get(templateThue[k]);
                    Item item = new Item(itTemp, false, 2, maintm[k], template[k]);
                    for (int i = 0; i < 10; ++i) {
                        short[] atb = item.atb;
                        atb[i] += (short) (item.atb[i] * 20 / 100);
                    }
                    int[] plus = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10, 10};
                    for (int j = 0; j < 7; ++j) {
                        ++item.plus;
                        for (int l = 0; l < 10; ++l) {
                            short[] atb2 = item.atb;
                            atb2[l] += (short) ((l > 0) ? 2 : plus[item.plus]);
                        }
                    }
                    item.place = 0;
                    item.owner = p.charDBID;
                    item.colorName = 4;
                    item.plus = 15;
                    this.saveItem(conn, item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giveItem() {
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            int next = 100000;
            int start = 0;
            while (true) {
                String sql = "select * from tob_char where id>=" + start + " and id<" + next;
                start = next;
                next += 100000;
                rs = stmt.executeQuery(sql);
                Char p = null;
                while (rs.next()) {
                    p = new Char(null);
                    p.charDBID = rs.getInt("id");
                    System.out.println(p.charDBID + " >>>");
                    p.dbPotion = rs.getString("potion");
                    if (p.dbPotion != null) {
                        String[] data = Char.split(p.dbPotion, ",");
                        for (int i = 0; i < data.length; ++i) {
                            try {
                                p.potions[i] = Integer.parseInt(data[i]);
                            } catch (Exception ignored) {
                            }
                        }
                        p.potions[25] = 1;
                        p.potions[26] = 1;
                        StringBuilder pinfo = new StringBuilder(String.valueOf(p.getxu()));
                        for (int j = 1; j < p.potions.length; ++j) {
                            pinfo.append(",").append(p.potions[j]);
                        }
                        String sqla = "update tob_char set potion='" + pinfo + "' where id=" + p.charDBID;
                        stmt2.executeUpdate(sqla);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giveSpecialItem2Char(String charname, int idItem, int nItem) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from tob_char where charname='" + charname + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                for (int i = 0; i < nItem; ++i) {
                    ShopTemplate item = Map.getShopTemplate(idItem);
                    ShopTemplate spItem = new ShopTemplate();
                    spItem.coppy(spItem, item);
                    spItem.ownerId = rs.getInt("id");
                    this.saveSpecialItem(conn, spItem);
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void giveSpecialItem() {
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            int next = 200000;
            int start = 100000;
            int count = -1;
            while (count != 0) {
                if (count > 0) {
                    count = 0;
                }
                String sql = "select * from tob_char where id>=" + start + " and id<" + next;
                System.out.println(start + " >> " + next);
                start = next;
                next += 100000;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ++count;
                    int charId = rs.getInt("id");
                    System.out.println(charId);
                    for (int i = 0; i < 1; ++i) {
                        ShopTemplate item = Map.getShopTemplate(0);
                        ShopTemplate spItem = new ShopTemplate();
                        spItem.coppy(spItem, item);
                        spItem.ownerId = charId;
                        this.saveSpecialItem(conn, spItem);
                    }
                }
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPotionFromFile() {
        Vector<String> data = TeamServer.readData("map/data.txt", "data.txt");
        Connection conn = null;
        Statement stmt = null;
        Char p = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            for (String datum : data) {
                String sql = "SELECT * FROM tob_char where charname='" + datum + "'";
                System.out.println(datum + ">>");
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    p = new Char(null);
                    p.charDBID = rs.getInt("id");
                    String[] ps = Char.split(rs.getString("potion"), ",");
                    for (int j = 0; j < ps.length; ++j) {
                        try {
                            p.potions[j] = Integer.parseInt(ps[j]);
                        } catch (Exception ignored) {
                        }
                    }
                    int[] potions = p.potions;
                    final int n = 27;
                    ++potions[n];
                    StringBuilder cpos = new StringBuilder(String.valueOf(p.getxu()));
                    for (int k = 1; k < p.potions.length; ++k) {
                        cpos.append(",").append(p.potions[k]);
                    }
                    Statement stmt2 = null;
                    stmt2 = conn.createStatement();
                    sql = "update tob_char set potion='" + cpos + "' where id=" + p.charDBID;
                    stmt2.executeUpdate(sql);
                    try {
                        stmt2.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                }
            }
            try {
                Thread.sleep(3000L);
            } catch (Exception ignored) {
            }
            System.exit(0);
        } catch (Exception ignored) {
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }
    
    public void setPotion() {
        Char p = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            final int start = 0;
            final int next = 100000;
            int count = -1;
            while (true) {
                String sql = "SELECT * FROM tob_char where lastlv>30";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ++count;
                    p = new Char(null);
                    p.charDBID = rs.getInt("id");
                    String[] ps = Char.split(rs.getString("potion"), ",");
                    for (int i = 0; i < ps.length; ++i) {
                        try {
                            p.potions[i] = Integer.parseInt(ps[i]);
                        } catch (Exception ignored) {
                        }
                    }
                    int[] potions = p.potions;
                    final int n = 27;
                    ++potions[n];
                    StringBuilder cpos = new StringBuilder(String.valueOf(p.getxu()));
                    for (int j = 1; j < p.potions.length; ++j) {
                        cpos.append(",").append(p.potions[j]);
                    }
                    Statement stmt2 = null;
                    stmt2 = conn.createStatement();
                    sql = "update tob_char set potion='" + cpos + "' where id=" + p.charDBID;
                    stmt2.executeUpdate(sql);
                    System.out.println(count + " " + start);
                    try {
                        stmt2.close();
                    } catch (Exception ignored) {
                    }
                }
                System.out.println();
                System.err.println(count + " ><><> " + start);
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("LOI GET CHAR");
            this.notFalse = true;
        } finally {
            System.out.println("DONG");
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    public boolean bakupChar() {
        Char p = null;
        byte[] he = {4, 2, 0, 3, 1};
        short[] basic = {400, 400, 500, 350, 350};
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            Vector<String> nameHack = new Vector<>();
            nameHack = readText("hack.txt");
            while (nameHack.size() > 0) {
                String name = nameHack.remove(0);
                System.out.println(name);
                String sql = "SELECT * FROM tob_char where charname='" + name + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    p = new Char(null);
                    p.charDBID = rs.getInt("id");
                    p.headStyle = rs.getByte("headStyle");
                    byte gender = rs.getByte("gender");
                    if (gender > 0) {
                        p.gender = gender;
                        p.isNewClient = true;
                    } else if (p.headStyle % 2 == 0) {
                        p.gender = 1;
                    } else {
                        p.gender = 2;
                    }
                    p.charname = rs.getString("charname");
                    p.charClass = rs.getByte("class");
                    p.lastLV = rs.getShort("lastLv");
                    Vector<Date> nt = new Vector<>();
                    Vector<Item> item = this.getChar(p, p.charDBID, nt);
                    while (item.size() > 0) {
                        Item it = item.remove(0);
                        this.deleteItem(it.dbid);
                    }
                    while (p.gemItem.size() > 0) {
                        this.deleteGemItem(p.gemItem.remove(0).dbid);
                    }
                    while (p.specialItem.size() > 0) {
                        this.deleteGemItem(p.specialItem.remove(0).dbid);
                    }
                    this.delQuest(p.charDBID);
                    this.resetChar(p.headStyle, p.charDBID, p.charClass, p.gender, p.charname, p.lastLV, conn);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("LOI GET CHAR");
            this.notFalse = true;
            return false;
        } finally {
            System.out.println("DONG");
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        System.out.println("DONG");
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        Logger.logString(String.valueOf(this.sid), "update.txt");
        System.exit(0);
        return true;
    }

    public void delInboxMsg(int id) {
        Connection conn = null;
        Statement pre = null;
        try {
            String sql = "delete from tob_inbox where id=" + id;
            conn = this.getConnection();
            pre = conn.createStatement();
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void delInboxMsg(String charname) {
        Connection conn = null;
        Statement pre = null;
        try {
            String sql = "delete from tob_inbox where charname='" + charname + "'";
            conn = this.getConnection();
            pre = conn.createStatement();
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void delMsgClan(int id) {
        Connection conn = null;
        Statement pre = null;
        try {
            String sql = "delete from tob_clan_msg where id=" + id;
            conn = this.getConnection();
            pre = conn.createStatement();
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<MsgClanInfo> getMsgClan(int id) {
        Vector<MsgClanInfo> msg = null;
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_clan_msg where id_clan=" + id;
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                if (msg == null) {
                    msg = new Vector<>();
                }
                MsgClanInfo m = new MsgClanInfo();
                m.id = rs.getInt("id");
                m.sender = rs.getString("charname");
                m.info = rs.getString("info");
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
                m.date = dayFormat.format(rs.getDate("datesend").getTime());
                msg.add(m);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return msg;
    }

    public void addNewMsgClan(Char player, String msg) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            String sql = "insert into tob_clan_msg (charname,info,id_clan,dateSend) values ('" + player.charname + "','" + msg + "','" + player.idClan + "',Now())";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public int addNewMessage(String charname, CharInboxMessage newMessage) {
        return 0;
    }

    public Vector<CharInboxMessage> getInbox(String charname) {
        Vector<CharInboxMessage> inbox = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_inbox WHERE charname=? order by dateSend desc";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            while (rs.next()) {
                CharInboxMessage ib = new CharInboxMessage();
                ib.sender = rs.getString("sender");
                ib.info = rs.getString("info");
                ib.idMsg = rs.getInt("id");
                inbox.add(ib);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return inbox;
    }

    public void getFriendlist(Char p) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "SELECT * FROM tob_friendlist WHERE charId=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, p.charDBID);
            rs = pre.executeQuery();
            if (rs.next()) {
                String[] fid = Char.split(rs.getString("friendId"), ",");
                p.friendListID.removeAllElements();
                p.friendListName.removeAllElements();
                int ischange = rs.getByte("ischange");
                for (String s : fid) {
                    if (!s.trim().equals("")) {
                        int id = Integer.parseInt(s.trim());
                        if (ischange == 0) {
                            if (Map.openLog) {
                                if (TeamServer.server == 3) {
                                    id += 2407770;
                                } else if (TeamServer.server == 2) {
                                    id += 2886804;
                                }
                            } else if (TeamServer.server == 1) {
                                id += 1497885;
                            }
                        }
                        CharInfo info = this.getInfoChar(id);
                        if (info != null) {
                            info.idDB = id;
                            p.myFriend.add(info);
                            p.friendListID.add(id);
                            p.friendListName.add(info.name);
                        }
                    }
                }
                if (ischange == 0) {
                    this.saveFriendList(p);
                }
            }
            if (p.charname.trim().equals("cungday")) {
                System.out.println("TONG SO BAN " + p.friendListID.size() + " >> " + p.friendListName.size());
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateLienTram() {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char_luong set nPk=0";
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                conn.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            conn.close();
        } catch (Exception ignored) {
        }
    }

    public void saveTopienTram(Vector<CharInfo> top) {
        Connection conn = null;
        try {
            conn = this.getConnection();
            for (CharInfo c : top) {
                String sql = "update tob_char_luong set nPK=" + c.pk + " where charname='" + c.name + "'";
                PreparedStatement pre = conn.prepareStatement(sql);
                System.out.println(sql);
                pre.execute();
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<CharInfo> getTopCountry(int country, int typeTop) {
        Vector<CharInfo> top = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_char_luong where charname <> 'chienthan' and country=" + country + " order by exp desc limit 50";
            if (typeTop == 1) {
                sql = "select * from tob_char_luong where charname <> 'chienthan' and country=" + country + " order by honor desc limit 50";
            } else if (typeTop == 2) {
                sql = "select * from tob_char_luong where charname <> 'chienthan' and country=" + country + " order by nPK desc limit 10";
            }
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            int count = 0;
            while (rs.next()) {
                ++count;
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.money = rs.getLong("gold");
                charinfo.idClan = rs.getShort("idClan");
                charinfo.luong = rs.getInt("luong");
                charinfo.honor = rs.getInt("honor");
                charinfo.pk = rs.getInt("nPK");
                String info = rs.getString("wearing");
                if (!info.equals("-1")) {
                    try {
                        if (info == null || info.trim().equals("")) {
                            String[] data = Char.split(info.trim(), ",");
                            if (data.length > 0) {
                                charinfo.country = Byte.parseByte(data[0]);
                                for (short i = 1; i < data.length; i += 4) {
                                    ItemInfo item = new ItemInfo();
                                    item.idTemplate = Short.parseShort(data[i]);
                                    item.charClass = Byte.parseByte(data[i + 1]);
                                    item.level = Byte.parseByte(data[i + 2]);
                                    item.plus = Byte.parseByte(data[i + 3]);
                                    charinfo.wearingItem.add(item);
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
                top.add(charinfo);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        return top;
    }

    public void updateCharRaceRvcGift(Char p) {
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        try {
            String sql = "update " + Database.tableRace + " set rcvGift=1 where charid=" + p.charDBID;
            conn = this.getConnection();
            stm = conn.createStatement();
            stm.executeUpdate(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public byte checkReceiveGiftGroup(Char p) {
        if (p.idGropRace == -1) {
            return -1;
        }
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        Label_0308:
        {
            try {
                String sql = "select * from " + Database.tableRace + " where idgroup=" + p.idGropRace + " order by exp desc";
                conn = this.getConnection();
                stm = conn.createStatement();
                rs = stm.executeQuery(sql);
                byte i = 0;
                Block_7:
                {
                    while (rs.next()) {
                        if (i >= 3) {
                            break;
                        }
                        if (p.charname.equalsIgnoreCase(rs.getString("charname"))) {
                            break Block_7;
                        }
                        ++i;
                    }
                    break Label_0308;
                }
                if (rs.getByte("rcvGift") == 1) {
                    return -1;
                }
                return i;
            } catch (Exception ignored) {
            } finally {
                try {
                    stm.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public Vector<CharInfo> getTopGroup(int idGroup, boolean sort) {
        Vector<CharInfo> top = new Vector<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        try {
            conn = this.getConnection();
            String sql = "select * from " + Database.tableRace + " where idgroup=" + idGroup + (sort ? " order by exp desc" : "");
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.level = rs.getByte("lastlv");
                String info = rs.getString("infotop");
                if (!info.equals("-1")) {
                    try {
                        String[] data = Char.split(info, ",");
                        charinfo.country = Byte.parseByte(data[0]);
                        for (short i = 1; i < data.length; i += 4) {
                            ItemInfo item = new ItemInfo();
                            item.idTemplate = Short.parseShort(data[i]);
                            item.charClass = Byte.parseByte(data[i + 1]);
                            item.level = Byte.parseByte(data[i + 2]);
                            item.plus = Byte.parseByte(data[i + 3]);
                            charinfo.wearingItem.add(item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                top.add(charinfo);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return top;
        } finally {
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            Database.connPool.free(conn);
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        Database.connPool.free(conn);
        return top;
    }

    public Vector<CharInfo> getTopLV() {
        Vector<CharInfo> top = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Statement stm = null;
        ResultSet rss = null;
        try {
            String sql = "select charname,id,gold,lastLv,headStyle,idClan,luong,pInfo,infotop,xp from tob_char where charname <> 'chienthan' and ban=0 order by xp desc limit 100";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            int count = 0;
            while (rs.next()) {
                ++count;
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lastLv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.idDB = rs.getInt("id");
                charinfo.money = rs.getLong("gold");
                charinfo.idClan = rs.getShort("idClan");
                charinfo.luong = rs.getInt("luong");
                charinfo.exp = rs.getLong("xp");
                String info = rs.getString("infotop");
                if (!info.equals("-1")) {
                    try {

                        String[] data = Char.split(info, ",");
                        charinfo.country = Byte.parseByte(data[0]);
                        for (short i = 1; i < data.length; i += 4) {
                            ItemInfo item = new ItemInfo();
                            item.idTemplate = Short.parseShort(data[i]);
                            item.charClass = Byte.parseByte(data[i + 1]);
                            item.level = Byte.parseByte(data[i + 2]);
                            item.plus = Byte.parseByte(data[i + 3]);
                            charinfo.wearingItem.add(item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String[] data = Char.split(rs.getString("pInfo"), ",");
                    try {
                        charinfo.country = Byte.parseByte(data[39]);
                    } catch (Exception ignored) {
                    }
                    try {
                        sql = "select dbbasic from tob_item where owner=" + charinfo.idDB + " and place=1";
                        stm = conn.createStatement();
                        rss = stm.executeQuery(sql);
                        StringBuilder infoWearing = new StringBuilder(String.valueOf(charinfo.country));
                        byte[] iditem = new byte[13];
                        while (rss.next()) {
                            ItemInfo item2 = new ItemInfo();
                            Item it = new Item();
                            it.dbInfo = rss.getString("dbbasic");
                            it.initInfoFromDB();
                            if (it.getType() != 13) {
                                infoWearing.append(",").append(it.getTemplate().id).append(",").append(it.clazz).append(",").append(it.level).append(",").append(it.plus);
                                if (iditem[it.getType()] != 0) {
                                    continue;
                                }
                                item2.charClass = it.clazz;
                                item2.level = (byte) it.level;
                                item2.plus = it.plus;
                                item2.idTemplate = (short) it.getTemplate().id;
                                charinfo.wearingItem.add(item2);
                                short type = it.getType();
                                ++iditem[type];
                            }
                        }
                        this.updateInfoChar(charinfo.name, infoWearing.toString());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    } finally {
                        try {
                            stm.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            rss.close();
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        stm.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rss.close();
                    } catch (Exception ignored) {
                    }
                }
                top.add(charinfo);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return top;
    }

    public Vector<CharInfo> getTopRich() {
        Vector<CharInfo> top = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Statement stm = null;
        ResultSet rss = null;
        try {
            String sql = "select charname,id,gold,lastLv,headStyle,idClan,luong,pInfo,infotop from tob_char where charname <> 'chienthan' and ban=0 order by gold desc limit 100";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            int count = 0;
            while (rs.next()) {
                ++count;
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lastLv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.idDB = rs.getInt("id");
                charinfo.money = rs.getLong("gold");
                charinfo.idClan = rs.getShort("idClan");
                charinfo.luong = rs.getInt("luong");
                String info = rs.getString("infotop");
                if (!info.equals("-1")) {
                    try {
                        String[] data = Char.split(info, ",");
                        charinfo.country = Byte.parseByte(data[0]);
                        for (short i = 1; i < data.length; i += 4) {
                            ItemInfo item = new ItemInfo();
                            item.idTemplate = Short.parseShort(data[i]);
                            item.charClass = Byte.parseByte(data[i + 1]);
                            item.level = Byte.parseByte(data[i + 2]);
                            item.plus = Byte.parseByte(data[i + 3]);
                            charinfo.wearingItem.add(item);
                        }
                    } catch (Exception ignored) {
                    }
                } else {
                    try {
                        String[] data = Char.split(rs.getString("pInfo"), ",");
                        try {
                            charinfo.country = Byte.parseByte(data[39]);
                        } catch (Exception ignored) {
                        }
                        sql = "select dbbasic from tob_item where owner=" + charinfo.idDB + " and place=1";
                        stm = conn.createStatement();
                        rss = stm.executeQuery(sql);
                        StringBuilder infoWearing = new StringBuilder(String.valueOf(charinfo.country));
                        byte[] iditem = new byte[13];
                        while (rss.next()) {
                            ItemInfo item2 = new ItemInfo();
                            Item it = new Item();
                            it.dbInfo = rss.getString("dbbasic");
                            it.initInfoFromDB();
                            if (it.getType() != 13) {
                                infoWearing.append(",").append(it.getTemplate().id).append(",").append(it.clazz).append(",").append(it.level).append(",").append(it.plus);
                                if (iditem[it.getType()] != 0) {
                                    continue;
                                }
                                item2.charClass = it.clazz;
                                item2.level = (byte) it.level;
                                item2.plus = it.plus;
                                item2.idTemplate = (short) it.getTemplate().id;
                                charinfo.wearingItem.add(item2);
                                short type = it.getType();
                                ++iditem[type];
                            }
                        }
                        this.updateInfoChar(charinfo.name, infoWearing.toString());
                    } catch (Exception ignored) {
                    } finally {
                        try {
                            stm.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            rss.close();
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        stm.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rss.close();
                    } catch (Exception ignored) {
                    }
                }
                top.add(charinfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return top;
    }

    public int getIDClanChar(Connection conn, String nameChar) {
        Char p = CharManager.instance.getCharByCharName(nameChar.toLowerCase());
        if (p != null) {
            return p.idClan;
        }
        final int idClan = -1;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select idClan from tob_char where charname='" + nameChar + "'";
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("idClan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        return idClan;
    }

    public Vector<NewClan> getTopClan() {
        Vector<NewClan> top = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_clan order by xp desc limit 100";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                NewClan clanInfo = new NewClan();
                clanInfo.id = rs.getShort("id_clan");
                clanInfo.name = rs.getString("clanName");
                clanInfo.master = rs.getString("charMaster");
                clanInfo.level = rs.getByte("lvClan");
                clanInfo.money = rs.getLong("money");
                String[] mb = Char.split(rs.getString("member"), ",");
                for (String s : mb) {
                    int idCharClan = this.getIDClanChar(conn, s);
                    if (idCharClan == clanInfo.id) {
                        clanInfo.addMember(s, 0);
                    }
                }
                clanInfo.slogan = rs.getString("slogan");
                clanInfo.country = rs.getByte("country");
                top.add(clanInfo);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return top;
    }

    public Vector<String> getTopHoa() {
        Vector<String> top = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_char order by ticket desc limit 5";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String st = rs.getString("charname") + "_" + rs.getInt("ticket");
                top.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return top;
    }

    public CharInfo getInfoChar(int charDBID, String charnameGet) {
        final String st = "";
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Statement stm = null;
        ResultSet rss = null;
        try {
            final String sql = "select * from tob_char where id=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, charDBID);
            rs = pre.executeQuery();
            if (rs.next()) {
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lastLv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.idClan = rs.getByte("idClan");
                try {
                    String info = rs.getString("infotop");
                    if (!info.equals("-1")) {
                        try {
                            String[] data = Char.split(info, ",");
                            charinfo.country = Byte.parseByte(data[0]);
                            for (short i = 1; i < data.length; i += 4) {
                                ItemInfo item = new ItemInfo();
                                item.idTemplate = Short.parseShort(data[i]);
                                item.charClass = Byte.parseByte(data[i + 1]);
                                item.level = Byte.parseByte(data[i + 2]);
                                item.plus = Byte.parseByte(data[i + 3]);
                                charinfo.wearingItem.add(item);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                } catch (Exception e) {
                    if (charnameGet.equals("chienthan")) {
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        stm.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rss.close();
                    } catch (Exception ignored) {
                    }
                }
                try {
                    stm.close();
                } catch (Exception ignored) {
                }
                try {
                    rss.close();
                } catch (Exception ignored) {
                }
                return charinfo;
            }
            if (charnameGet.equals("chienthan")) {
            }
            System.out.println("KHONG TIM THAY " + charDBID);
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return null;
    }

    public CharInfo getInfoChar(String charname) {
        final String st = "";
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Statement stm = null;
        ResultSet rss = null;
        try {
            String sql = "select * from tob_char where charname=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            rs = pre.executeQuery();
            if (rs.next()) {
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lastLv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.titlesClan = rs.getByte("basepoint");
                charinfo.idClan = rs.getShort("idClan");
                String info = rs.getString("infotop");
                if (!info.equals("-1")) {
                    try {
                        String[] data = Char.split(info, ",");
                        charinfo.country = Byte.parseByte(data[0]);
                        for (short i = 1; i < data.length; i += 4) {
                            ItemInfo item = new ItemInfo();
                            item.idTemplate = Short.parseShort(data[i]);
                            item.charClass = Byte.parseByte(data[i + 1]);
                            item.level = Byte.parseByte(data[i + 2]);
                            item.plus = Byte.parseByte(data[i + 3]);
                            charinfo.wearingItem.add(item);
                        }
                    } catch (Exception ignored) {
                    }
                } else {
                    String[] data = Char.split(rs.getString("pInfo"), ",");
                    try {
                        charinfo.country = Byte.parseByte(data[39]);
                    } catch (Exception ignored) {
                    }
                    try {
                        sql = "select * from tob_item where owner=" + rs.getInt("id") + " and place=1";
                        stm = conn.createStatement();
                        rss = stm.executeQuery(sql);
                        StringBuilder infoWearing = new StringBuilder(String.valueOf(charinfo.country));
                        byte[] iditem = new byte[16];
                        while (rss.next()) {
                            ItemInfo item2 = new ItemInfo();
                            Item it = new Item();
                            it.dbInfo = rss.getString("dbbasic");
                            it.initInfoFromDB();
                            if (it.getType() != 13) {
                                infoWearing.append(",").append(it.getTemplate().id).append(",").append(it.clazz).append(",").append(it.level).append(",").append(it.plus);
                                if (iditem[it.getType()] != 0) {
                                    continue;
                                }
                                item2.charClass = it.clazz;
                                item2.level = (byte) it.level;
                                item2.plus = it.plus;
                                item2.idTemplate = (short) it.getTemplate().id;
                                charinfo.wearingItem.add(item2);
                                short type = it.getType();
                                ++iditem[type];
                            }
                        }
                        this.updateInfoChar(charname, infoWearing.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            stm.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            rss.close();
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        stm.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rss.close();
                    } catch (Exception ignored) {
                    }
                }
                return charinfo;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return null;
    }

    public CharInfo getInfoChar(int chardbid) {
        final String st = "";
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Statement stm = null;
        ResultSet rss = null;
        try {
            String sql = "select id,charname,lastLv,headStyle,basepoint,idClan,infotop,pInfo from tob_char where id=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, chardbid);
            rs = pre.executeQuery();
            if (rs.next()) {
                CharInfo charinfo = new CharInfo();
                charinfo.name = rs.getString("charname");
                charinfo.level = rs.getByte("lastLv");
                charinfo.headStyle = rs.getByte("headStyle");
                charinfo.titlesClan = rs.getByte("basepoint");
                charinfo.idClan = rs.getShort("idClan");
                String info = rs.getString("infotop");
                if (!info.equals("-1")) {
                    try {
                        String[] data = Char.split(info, ",");
                        charinfo.country = Byte.parseByte(data[0]);
                        for (short i = 1; i < data.length; i += 4) {
                            ItemInfo item = new ItemInfo();
                            item.idTemplate = Short.parseShort(data[i]);
                            item.charClass = Byte.parseByte(data[i + 1]);
                            item.level = Byte.parseByte(data[i + 2]);
                            item.plus = Byte.parseByte(data[i + 3]);
                            charinfo.wearingItem.add(item);
                        }
                    } catch (Exception ignored) {
                    }
                } else {
                    String[] data = Char.split(rs.getString("pInfo"), ",");
                    try {
                        charinfo.country = Byte.parseByte(data[39]);
                    } catch (Exception ignored) {
                    }
                    try {
                        sql = "select * from tob_item where owner=" + rs.getInt("id") + " and place=1";
                        stm = conn.createStatement();
                        rss = stm.executeQuery(sql);
                        StringBuilder infoWearing = new StringBuilder(String.valueOf(charinfo.country));
                        byte[] iditem = new byte[16];
                        while (rss.next()) {
                            ItemInfo item2 = new ItemInfo();
                            Item it = new Item();
                            it.dbInfo = rss.getString("dbbasic");
                            it.initInfoFromDB();
                            if (it.getType() != 13) {
                                infoWearing.append(",").append(it.getTemplate().id).append(",").append(it.clazz).append(",").append(it.level).append(",").append(it.plus);
                                if (iditem[it.getType()] != 0) {
                                    continue;
                                }
                                item2.charClass = it.clazz;
                                item2.level = (byte) it.level;
                                item2.plus = it.plus;
                                item2.idTemplate = (short) it.getTemplate().id;
                                charinfo.wearingItem.add(item2);
                                short type = it.getType();
                                ++iditem[type];
                            }
                        }
                        this.updateInfoChar(charinfo.name, infoWearing.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            stm.close();
                        } catch (Exception ignored) {
                        }
                        try {
                            rss.close();
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        stm.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rss.close();
                    } catch (Exception ignored) {
                    }
                }
                return charinfo;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void updateCharclass() {
        this.updateMoney();
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tob_char where id>18634";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            int charId = 0;
            int count = 0;
            while (rs.next()) {
                Vector<Date> nt = new Vector<>();
                ++count;
                Char p = new Char(null);
                Vector<Item> pItem = new Vector<>();
                p.charDBID = rs.getInt("id");
                charId = p.charDBID;
                p.setLuong(rs.getInt("luong"));
                p.dbInfo = rs.getString("pInfo");
                p.using = rs.getByte("del");
                p.dbPotion = rs.getString("potion");
                p.dbSkill = rs.getString("skill");
                p.dbBasic = rs.getString("basic");
                p.idClan = rs.getShort("idClan");
                nt.add(rs.getDate("lastlog"));
                this.getFriendlist(p);
                sql = "SELECT * FROM tob_item WHERE owner=? and place=1";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                ResultSet rss = pre.executeQuery();
                int nItem = 0;
                while (rss.next()) {
                    ++nItem;
                    Item item = new Item();
                    item.dbid = rss.getInt("id");
                    int int1 = rss.getInt("owner");
                    item.dbowner = int1;
                    item.owner = int1;
                    item.dbInfo = rss.getString("dbbasic");
                    if (item.dbInfo == null) {
                        int itemTemplate = rss.getInt("item_template");
                        item.setTemplate(itemTemplate, item.clazz = rss.getByte("class"), rss.getByte("class"), itemTemplate);
                        item.place = rss.getByte("place");
                        item.durable = rss.getShort("durable");
                        item.plus = rss.getByte("plus");
                        item.level = rss.getShort("lvItem");
                        if (item.level == -1 || item.level == 0) {
                            item.level = item.getTemplate().level;
                        }
                        item.mDurable = rss.getShort("mDurable");
                    }
                    item.dbAttribute = rss.getString("allAtt");
                    if (item.dbAttribute == null) {
                        item.atb[0] = rss.getShort("atb0");
                        item.atb[1] = rss.getShort("atb1");
                        item.atb[2] = rss.getShort("atb2");
                        item.atb[3] = rss.getShort("atb3");
                        item.atb[4] = rss.getShort("atb4");
                        item.atb[5] = rss.getShort("atb5");
                        item.atb[6] = rss.getShort("atb6");
                        item.atb[7] = rss.getShort("atb7");
                        item.atb[8] = rss.getShort("atb8");
                        item.atb[9] = rss.getShort("atb9");
                    }
                    pItem.add(item);
                    try {
                        Thread.sleep(1L);
                    } catch (Exception ignored) {
                    }
                }
                try {
                    p.initInfoFromDB(pItem, nt);
                    try {
                        Statement preu = conn.createStatement();
                        String sqlupdate = "update tob_char set pInfo='" + p.getDBInfo() + "' where charname='" + p.charname + "'";
                        this.sqlUpdate.add(sqlupdate);
                        System.out.println(count + " update " + p.charname);
                        try {
                            preu.close();
                            continue;
                        } catch (Exception ex3) {
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println(p.charname);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        System.exit(0);
    }

    public void getVeso(Char p, Connection conn) {
        ResultSet rs = null;
        Statement st = null;
        try {
            String sql = "select * from tob_xoso where owner=" + p.charDBID;
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String[] da = Char.split(rs.getString("vsxu"), "|");
                if (da.length > 0) {
                    p.dayOpenXoso = da[1];
                    String[] data = Char.split(da[0], ",");
                    if (data.length > 1) {
                        for (byte i = 0; i < data.length; i += 2) {
                            Veso vs = new Veso();
                            vs.number = Byte.parseByte(data[i]);
                            vs.soluong = Byte.parseByte(data[i + 1]);
                            p.vesoxu.add(vs);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                st.close();
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            st.close();
        } catch (Exception ignored) {
        }
    }

    public void getIdGroupRaceChar(Connection conn, Char p) {
        ResultSet rs = null;
        Statement stm = null;
        try {
            String sql = "select * from " + Database.tableRace + " where charid=" + p.charDBID;
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                p.idGropRace = rs.getShort("idgroup");
                p.expRace = rs.getLong("exp");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
    }

    public int getIDClanChar(String name) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select * from tob_char where charname='" + name + "'";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("idClan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return -1;
    }

    public void resetStateMarried(String charname, String charname1) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                String[] data = Char.split(rs.getString("pInfo"), ",");
                if (data[51].equals(charname1)) {
                    data[49] = "0";
                    data[50] = "-1";
                    data[51] = "";
                    StringBuilder info = new StringBuilder(data[0]);
                    for (int i = 1; i < data.length; ++i) {
                        info.append(",").append(data[i]);
                    }
                    sql = "update tob_char set pInfo='" + info + "' WHERE charname='" + charname + "'";
                    pre = conn.prepareStatement(sql);
                    pre.execute();
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
    }

    public Vector<Item> getChar(Char p, String charname, Vector<Date> lastlog) {
        Vector<Item> pItem = new Vector<>();
        Connection conn = null;
        int charId = 0;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tob_char WHERE charname=? and ban=0";
            pre = conn.prepareStatement(sql);
            pre.setString(1, charname);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                charId = rs.getInt("userid");

                

                p.charDBID = charId;
                p.userID = charId;
                p.setxu(rs.getLong("gold"));
                p.setLuong(rs.getInt("luong"));
                p.tichluy = rs.getInt("tichluy");
                p.qua_npc = rs.getInt("qua_npc");
                p.tichluy_tuan = rs.getInt("tichluy_tuan");
                p.tichluy_bosung = rs.getInt("tichluy_bosung");
                p.dbInfo = rs.getString("pInfo");
                p.using = rs.getByte("del");
                p.dbPotion = rs.getString("potion");
                p.dbSkill = rs.getString("skill");
                p.dbBasic = rs.getString("basic");
                p.idClan = rs.getShort("idClan");
                p.reclass = rs.getByte("reupclass");
                p.flower = rs.getShort("ticket");
                p.gotWages = (rs.getShort("x") == 1);
                p.timeXP = rs.getShort("y");
                p.gifMoonFestival = (rs.getShort("book") == 1);
                lastlog.add(rs.getDate("lastlog"));
                p.giftTop = rs.getByte("nAr1");
                if (!NewClan.isTimeOfferWages()) {
                    p.gotWages = false;
                }
                try {
                    p.rankClan = rs.getByte("basepoint");
                    if (p.rankClan < 0) {
                        p.rankClan = 3;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.initListKillMe(rs.getString("killme"));
                this.getFriendlist(p);
                p.equip = rs.getString("equip");
                p.invent = rs.getString("inven");
                p.bag = rs.getString("bag");
                p.tuido = rs.getString("tuido");
                if (p.equip == null) {
                    sql = "SELECT * FROM tob_item WHERE owner=?";
                    try {
                        pre.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                    pre = conn.prepareStatement(sql);
                    pre.setInt(1, charId);
                    rs = pre.executeQuery();
                    int nItem = 0;
                    while (rs.next()) {
                        ++nItem;
                        Item item = new Item();
                        item.dbid = rs.getInt("id");
                        int int1 = rs.getInt("owner");
                        item.dbowner = int1;
                        item.owner = int1;
                        item.dbInfo = rs.getString("dbbasic");
                        item.dbAttribute = rs.getString("allAtt");
                        item.timeLoan = rs.getLong("timeLoan");
                        try {
                            item.identify = rs.getInt("atb0");
                            item.switchAttNguhanh = rs.getShort("atb1");
                            item.resetItem = rs.getShort("atb2");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        pItem.add(item);
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                boolean haveNew = false;
                sql = "SELECT * FROM tob_gem_new WHERE owner=?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                if (rs.next()) {
                    haveNew = true;
                    String[] soluong = Char.split(rs.getString("soluong"), ",");
                    int chardbid = rs.getInt("owner");
                    for (int i = 0; i < p.listGemitem.length; ++i) {
                        try {
                            int temsl = Integer.parseInt(soluong[i]);
                            if (temsl > 0) {
                                GemItem gem = new GemItem(i);
                                gem.ownerId = chardbid;
                                gem.realId = p.getIDItem();
                                p.gemItem.add(gem);
                                p.listGemitem[i] = temsl;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    String[] slock = Char.split(rs.getString("slock"), ",");
                    for (int j = 0; j < p.listGemitemLock.length; ++j) {
                        try {
                            int temsl2 = Integer.parseInt(slock[j]);
                            if (temsl2 > 0) {
                                p.addLockGem(j, temsl2);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                boolean haveSpNew = false;
                sql = "SELECT * FROM tob_sp_new WHERE owner=?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                if (rs.next()) {
                    haveSpNew = true;
                    String[] soluong2 = Char.split(rs.getString("soluong"), ",");
                    int chardbid2 = rs.getInt("owner");
                    for (int j = 0; j < soluong2.length; ++j) {
                        try {
                            int temsl2 = Integer.parseInt(soluong2[j]);
                            if (temsl2 > 0) {
                                ShopTemplate gem2 = new ShopTemplate();
                                ShopTemplate g = Map.getShopTemplate(j);
                                gem2.coppy(gem2, g);
                                gem2.ownerId = chardbid2;
                                gem2.realId = p.getIDItem();
                                p.specialItem.add(gem2);
                                p.listSpItem[j] = temsl2;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                String lastlogout = null;
                try {
                    lastlogout = Char.split(p.lastLog.trim(), ".")[0].trim();
                } catch (Exception ignored) {
                }
                sql = "SELECT * FROM tob_animal WHERE owner=? and del=0";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                while (rs.next()) {
                    Animal animal = new Animal();
                    animal.place = rs.getByte("place");
                    animal.ownerId = rs.getInt("owner");
                    animal.dbid = rs.getInt("id");
                    animal.idImg = rs.getByte("id_img");
                    animal.dbownerId = animal.ownerId;
                    animal.initAtt(rs.getString("att"));
                    animal.id = p.getIDItem();
                    animal.level = rs.getByte("lv");
                    animal.name = rs.getString("name");
                    animal.texpire = rs.getLong("texpire");
                    animal.isHoaHinh = rs.getInt("isHoaHinh");
                    animal.timeHoaHinh =rs.getLong("timeHoaHinh");
                    if (lastlogout != null && lastlogout.compareTo("2017-12-28 11:40:00") < 0 && animal.idImg == Animal.TUAN_LOC) {
                        animal.texpire += 1296000000L;
                    }
                    if (TeamServer.isServerHoaLu() && lastlogout != null && lastlogout.compareTo("2020-04-13 10:25:00") < 0 && (animal.idImg == Animal.PHUONG_HOANG || animal.idImg == Animal.PHUONG_HOANG_7MAU || animal.idImg == Animal.PHUONG_HOANG_BANG || animal.idImg == Animal.HEO || animal.idImg == Animal.NGUA_XUONG) && animal.texpire > 0L) {
                        long[] day = {3L, 5L, 7L};
                        animal.texpire = System.currentTimeMillis() + day[Map.r.nextInt(day.length)] * 24L * 60L * 60000L;
                        this.saveAnimal(animal);
                    }

                    if (animal.timeExpire()) {
                        this.delAnimal(animal.dbid);
                    } else {
                        p.animal.add(animal);
                        if (animal.place != 1) {
                            continue;
                        }
                        p.animalRide = animal;
                    }
                }
                this.getInfoCharClan(p);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } else {
                System.out.println("FAIL");
            }
            this.getCharPet(p, conn);
            this.getVeso(p, conn);
            this.getIdGroupRaceChar(conn, p);
            this.getCharQuest(p);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return pItem;
    }

    public void addCharDanhHieu(Char p) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_danh_hieu(charname,iddh) values ('" + p.charname + "','" + p.getAllDanhHieuSave() + "' )ON DUPLICATE KEY UPDATE iddh='" + p.getAllDanhHieuSave() + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getCharDanhHieu(Char p) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String sql = "SELECT * FROM tob_danh_hieu WHERE charname=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.charname);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p.setDanhHieu(rs.getString("iddh"));
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<Item> getChar(Char p, int charId, Vector<Date> lastlog) {
        Vector<Item> pItem = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();//cáº·c
            String sql = "SELECT * FROM tob_char WHERE id=? and ban=0";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, charId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p.charDBID = charId;
                p.userID = rs.getInt("userid");
                p.tichluy = rs.getInt("tichluy");
                p.qua_npc = rs.getInt("qua_npc");
                p.tichluy_tuan = rs.getInt("tichluy_tuan");
                p.tichluy_bosung = rs.getInt("tichluy_bosung");
                
                
                if (rs.getByte("isPP") == 0) {
                    p.setBatPhiPhong();
                } else {
                    p.setTatPhiPhong();
                }
                
                if (rs.getByte("isMN") == 0) {
                    p.setBatMatNa();
                } else {
                    p.setTatMatNa();
                }
                
                 
                
                p.setLuong(rs.getInt("luong"));
                p.dbInfo = rs.getString("pInfo");
                p.using = rs.getByte("del");
                p.charname = rs.getString("charname").toLowerCase();
                p.dbPotion = rs.getString("potion");
                p.dbSkill = rs.getString("skill");
                p.dbBasic = rs.getString("basic");
                p.idClan = rs.getShort("idClan");
                p.reclass = rs.getByte("reupclass");
                p.flower = rs.getShort("ticket");
                p.gotWages = (rs.getShort("x") == 1);
                p.timeXP = rs.getShort("y");
                p.gifMoonFestival = (rs.getShort("book") == 20);
                lastlog.add(rs.getDate("lastlog"));
                p.lastLog = rs.getString("lastlog");
                p.giftTop = rs.getByte("nAr1");
                p.setxu(rs.getLong("gold"));
                if (!NewClan.isTimeOfferWages()) {
                    p.gotWages = false;
                }
                try {
                    p.rankClan = rs.getByte("basepoint");
                    if (p.rankClan < 0) {
                        p.rankClan = 3;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.initListKillMe(rs.getString("killme"));
                this.getFriendlist(p);
                p.equip = rs.getString("equip");
                p.invent = rs.getString("inven");
                p.bag = rs.getString("bag");
                p.tuido = rs.getString("tuido");
                if (p.equip == null) {
                    sql = "SELECT * FROM tob_item WHERE owner=?";
                    try {
                        pre.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                    pre = conn.prepareStatement(sql);
                    pre.setInt(1, charId);
                    rs = pre.executeQuery();
                    int nItem = 0;
                    while (rs.next()) {
                        ++nItem;
                        Item item = new Item();
                        item.dbid = rs.getInt("id");
                        int int1 = rs.getInt("owner");
                        item.dbowner = int1;
                        item.owner = int1;
                        item.dbInfo = rs.getString("dbbasic");
                        item.dbAttribute = rs.getString("allAtt");
                        item.timeLoan = rs.getLong("timeLoan");
                        try {
                            item.identify = rs.getInt("atb0");
                            item.switchAttNguhanh = rs.getShort("atb1");
                            item.resetItem = rs.getShort("atb2");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        pItem.add(item);
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                boolean haveNew = false;
                sql = "SELECT * FROM tob_gem_new WHERE owner=?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                if (rs.next()) {
                    haveNew = true;
                    String[] soluong = Char.split(rs.getString("soluong"), ",");
                    int chardbid = rs.getInt("owner");
                    for (int i = 0; i < p.listGemitem.length; ++i) {
                        try {
                            int temsl = Integer.parseInt(soluong[i]);
                            if (temsl > 0) {
                                GemItem gem = new GemItem(i);
                                gem.ownerId = chardbid;
                                gem.realId = p.getIDItem();
                                p.gemItem.add(gem);
                                p.listGemitem[i] = temsl;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    String[] slock = Char.split(rs.getString("slock"), ",");
                    for (int j = 0; j < p.listGemitemLock.length; ++j) {
                        try {
                            int temsl2 = Integer.parseInt(slock[j]);
                            if (temsl2 > 0) {
                                p.addLockGem(j, temsl2);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                boolean haveSpNew = false;
                sql = "SELECT * FROM tob_sp_new WHERE owner=?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                if (rs.next()) {
                    haveSpNew = true;
                    String[] soluong2 = Char.split(rs.getString("soluong"), ",");
                    int chardbid2 = rs.getInt("owner");
                    for (int j = 0; j < soluong2.length; ++j) {
                        try {
                            int temsl2 = Integer.parseInt(soluong2[j]);
                            if (temsl2 > 0) {
                                ShopTemplate gem2 = new ShopTemplate();
                                ShopTemplate g = Map.getShopTemplate(j);
                                gem2.coppy(gem2, g);
                                gem2.ownerId = chardbid2;
                                gem2.realId = p.getIDItem();
                                p.specialItem.add(gem2);
                                p.listSpItem[j] = temsl2;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                try {
                    pre.close();
                } catch (Exception ignored) {
                }
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                String lastlogout = null;
                try {
                    lastlogout = Char.split(p.lastLog.trim(), ".")[0].trim();
                } catch (Exception ignored) {
                }
                sql = "SELECT * FROM tob_animal WHERE owner=? and del=0";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, charId);
                rs = pre.executeQuery();
                while (rs.next()) {
                    Animal animal = new Animal();
                    animal.place = rs.getByte("place");
                    animal.ownerId = rs.getInt("owner");
                    animal.dbid = rs.getInt("id");
                    animal.idImg = rs.getByte("id_img");
                    animal.dbownerId = animal.ownerId;
                    animal.initAtt(rs.getString("att"));
                    animal.id = p.getIDItem();
                    animal.level = rs.getByte("lv");
                    animal.name = rs.getString("name");
                    animal.texpire = rs.getLong("texpire");
                    animal.isHoaHinh = rs.getInt("isHoaHinh");
                    animal.timeHoaHinh =rs.getLong("timeHoaHinh");
                    if (lastlogout != null && lastlogout.compareTo("2017-12-28 11:40:00") < 0 && animal.idImg == Animal.TUAN_LOC) {
                        continue;
                    }
                    if (TeamServer.isServerHoaLu() && lastlogout != null && lastlogout.compareTo("2020-04-13 10:26:00") < 0 && (animal.idImg == Animal.PHUONG_HOANG || animal.idImg == Animal.PHUONG_HOANG_7MAU || animal.idImg == Animal.PHUONG_HOANG_BANG || animal.idImg == Animal.HEO || animal.idImg == Animal.NGUA_XUONG) && animal.texpire > 0L) {
                        long[] day = {3L, 5L, 7L};
                        animal.texpire = System.currentTimeMillis() + day[Map.r.nextInt(day.length)] * 24L * 60L * 60000L;
                        this.saveAnimal(animal);
                    }
                    if (animal.timeExpire()) {
                        this.delAnimal(animal.dbid);
                    } else {
                        p.animal.add(animal);
                        if (animal.place != 1) {
                            continue;
                        }
                        p.animalRide = animal;
                    }
                }
                this.getInfoCharClan(p);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            }
            this.getCharPet(p, conn);
            this.getVeso(p, conn);
            this.getIdGroupRaceChar(conn, p);
            this.getCharQuest(p);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return pItem;
    }

    public void traGemNgude(Char p) {
        boolean isExist = false;
        for (int i = 0; i < Database.namechar.length; ++i) {
            if (p.charname.equals(Database.namechar[i].trim().toLowerCase())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            return;
        }
        Connection conn = null;
        PreparedStatement pre = null;
        StringBuilder infoTra = new StringBuilder("tra gem= ");
        try {
            conn = this.getConnection();
            final String sql = "SELECT * FROM tob_gem_new_2511 WHERE owner=?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, p.charDBID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                String[] soluong = Char.split(rs.getString("soluong"), ",");
                for (int j = 0; j < p.listGemitem.length; ++j) {
                    try {
                        int temsl = Integer.parseInt(soluong[j]);
                        if (temsl > 0) {
                            p.doAddGemItem(j, temsl, false);
                            infoTra.append(Map.gemTemplate[j].name).append(":").append(temsl).append(",");
                        }
                    } catch (Exception ignored) {
                    }
                }
                infoTra.append("tra gem lock= ");
                String[] slock = Char.split(rs.getString("slock"), ",");
                for (int k = 0; k < p.listGemitemLock.length; ++k) {
                    try {
                        int temsl2 = Integer.parseInt(slock[k]);
                        if (temsl2 > 0) {
                            p.addLockGem(k, temsl2);
                            infoTra.append(Map.gemTemplate[k].name).append(":").append(temsl2).append(",");
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
            this.saveOrtherLog("", p.charname, infoTra.toString(), "tragem");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void delAnimal(int idAnimal) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            String sql = "UPDATE tob_animal SET del=1 where id=" + idAnimal;
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateTimeJoinClan(String charname) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select pInfo from tob_char where charname='" + charname + "'";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                Char p = new Char(null);
                p.dbInfo = rs.getString("pInfo");
                p.initPinfo();
                this.updatePInfoChar(p);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updatePInfoChar(Char p) {
        final String sql = "UPDATE tob_char SET pInfo=? where charname=?";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.getDBInfo());
            pre.setString(2, p.charname);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateCharIDClan(String charname, int idClan) {
        final String sql = "UPDATE tob_char SET idClan=? where charname=?";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, idClan);
            pre.setString(2, charname);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<CharClan> getTopMemberClan(int idClan) {
        Vector<CharClan> topMem = new Vector<>();
        final String sql = "select charname,basepoint from tob_char where idClan=? and basepoint<3 order by basepoint";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, idClan);
            ResultSet rs = pre.executeQuery();
            int phobang = 0;
            int truonglao = 0;
            while (rs.next()) {
                CharClan cl = new CharClan();
                cl.charname = rs.getString("charname").toLowerCase();
                cl.rankClan = rs.getByte("basepoint");
                if (phobang < 2 && cl.rankClan == 1) {
                    ++phobang;
                    topMem.add(cl);
                } else if (truonglao < 7 && cl.rankClan == 2) {
                    ++truonglao;
                    topMem.add(cl);
                } else {
                    Char p = CharManager.instance.getCharByCharName(cl.charname);
                    if (p != null) {
                        p.rankClan = 3;
                    } else {
                        this.updateRankClan(cl.charname, 3);
                    }
                }
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return topMem;
    }

    public void updateAllTitleClanMaster() {
        final String sql = "select * from tob_clan";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String charMaster = rs.getString("charmaster");
                this.updateRankClan(charMaster, 0);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateRankClan(String charname, int titles) {
        final String sql = "UPDATE tob_char SET basepoint=? where charname=?";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, titles);
            pre.setString(2, charname);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateInfoAdmin(String info) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            String sql = "update admin_msg set msg='" + info + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            TeamServer.haveMsgAdmin = true;
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getInfoAdmin() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from admin_msg";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ((AdminHandler) RealController.getHandler()).adminRequest(rs.getString("msg").toLowerCase(), false);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateAllTimeXP() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char set y=0";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateInfoChar(String charname, String info) {
        final String sql = "UPDATE tob_char SET infotop=? where charname=?";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, info);
            pre.setString(2, charname);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void savecharLuong(Connection conn, Char p) {
        PreparedStatement pre = null;
        PreparedStatement pre2 = null;
        try {
            String sql = "insert into tob_char_luong(charname,country,exp,idclan,honor,lv,wearing,luong,gold,headStyle) values(?,?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.charname);
            pre.setByte(2, p.myCountry);
            pre.setLong(3, p.lvDetail.getExp());
            pre.setShort(4, p.idClan);
            pre.setInt(5, p.honor);
            pre.setInt(6, p.lastLV);
            pre.setString(7, p.infoWearing);
            pre.setInt(8, (int) p.luonglost);
            pre.setByte(9, p.headStyle);
            pre.setLong(10, p.getxu());
            try {
                pre.execute();
            } catch (Exception e) {
                sql = "update tob_char_luong set country=?,exp=?,idclan=?,honor=?,lv=?,wearing=?,luong=?,gold=?,headStyle=? where charname=?";
                pre2 = conn.prepareStatement(sql);
                pre2.setByte(1, p.myCountry);
                pre2.setLong(2, p.lvDetail.getExp());
                pre2.setShort(3, p.idClan);
                pre2.setInt(4, p.honor);
                pre2.setInt(5, p.lastLV);
                pre2.setString(6, p.infoWearing);
                pre2.setInt(7, (int) p.luonglost);
                pre2.setLong(8, p.getxu());
                pre2.setByte(9, p.headStyle);
                pre2.setString(10, p.charname);
                pre2.execute();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre2.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
    }

    public void saveCharBackup(Connection conn, Char p) {
        StringBuilder equip = new StringBuilder();
        StringBuilder invent = new StringBuilder();
        StringBuilder bag = new StringBuilder();
        try {
            Vector<Item> wearing = new Vector<>();
            wearing = p.getCharWearing(0);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.getCharWearing(1);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.awItems;
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            if (!equip.toString().equals("")) {
                equip = new StringBuilder(equip.substring(0, equip.length() - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (Item item2 : p.iItems) {
                invent.append(item2.getInfoSave()).append(">");
            }
            if (!invent.toString().equals("")) {
                invent = new StringBuilder(invent.substring(0, invent.length() - 1));
            }
            String infomodel = p.wModel.getInfoModelWearing();
            if (!infomodel.equals("")) {
                invent.append(">").append(infomodel);
            }
        } catch (Exception ignored) {
        }
        try {
            for (Item item2 : p.bItem) {
                bag.append(item2.getInfoSave()).append(">");
            }
            if (!bag.toString().equals("")) {
                bag = new StringBuilder(bag.substring(0, bag.length() - 1));
            }
        } catch (Exception ignored) {
        }
        PreparedStatement pre = null;
        PreparedStatement pre2 = null;
        try {
            String sql = "INSERT INTO tob_char2(pInfo,basic,skill,potion,totalTimePlay,luong,lastLv,xp,gold,hp, idClan, reupclass,headStyle,ticket,x,y,book,infotop,id,charname,userid,class,gender,equip,inven,bag) values(?,?,?,?,totalTimePlay+?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            StringBuilder potion = new StringBuilder(String.valueOf(p.getxu()));
            StringBuilder skill = new StringBuilder(String.valueOf(p.skill[0]));
            String basic = String.valueOf(p.strength) + "," + p.agitity + "," + p.spirit + "," + p.health + "," + p.luck + "," + p.basepoint + "," + p.skillpoint;
            for (int j = 1; j < p.potions.length; ++j) {
                potion.append(",").append(p.potions[j]);
            }
            for (int j = 1; j < p.skill.length; ++j) {
                skill.append(",").append(p.skill[j]);
            }
            for (int j = 0; j < p.skillClan.length; ++j) {
                skill.append(",").append(p.skillClan[j]);
            }
            for (int j = 0; j < p.skillLvClan.length; ++j) {
                skill.append(",").append(p.skillLvClan[j]);
            }
            String dbInfo = p.getDBInfo();
            int totalPlay = (int) (System.currentTimeMillis() - p.timePlay) / 60000;
            pre2 = conn.prepareStatement(sql);
            pre2.setString(1, dbInfo);
            pre2.setString(2, basic);
            pre2.setString(3, skill.toString());
            pre2.setString(4, potion.toString());
            pre2.setLong(5, totalPlay);
            pre2.setInt(6, Math.max(p.getLuong(), 0));
            pre2.setInt(7, p.lastLV);
            pre2.setLong(8, p.lvDetail.getExp());
            try {
                pre2.setLong(9, p.getxu());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            pre2.setInt(10, p.health);
            pre2.setInt(11, p.idClan);
            pre2.setInt(12, p.reclass);
            pre2.setInt(13, p.headStyle);
            pre2.setInt(14, p.flower);
            pre2.setInt(15, p.gotWages ? 1 : 0);
            pre2.setInt(16, p.timeXP);
            pre2.setInt(17, p.gifMoonFestival ? 1 : 0);
            pre2.setString(18, p.infoWearing);
            pre2.setInt(19, p.charDBID);
            pre2.setString(20, p.charname);
            pre2.setInt(21, p.userID);
            pre2.setInt(22, p.charClass);
            pre2.setInt(23, p.gender);
            pre2.setString(24, equip.toString());
            pre2.setString(25, invent.toString());
            pre2.setString(26, bag.toString());
            try {
                pre2.execute();
            } catch (Exception e2) {
                sql = "UPDATE tob_char2 SET pInfo=?,basic=?,skill=?,potion=?,totalTimePlay=totalTimePlay+?, luong=?, lastLv=?,xp=?,gold=?,hp=?, idClan=?, reupclass=?,headStyle=?,ticket=?,x=?,y=?,book=?,lastLog=Now(),infotop=?,equip=?,inven=?,bag=?,killme=? where charname=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, dbInfo);
                pre.setString(2, basic);
                pre.setString(3, skill.toString());
                pre.setString(4, potion.toString());
                pre.setLong(5, totalPlay);
                pre.setInt(6, Math.max(p.getLuong(), 0));
                pre.setInt(7, p.lastLV);
                pre.setLong(8, p.lvDetail.getExp());
                try {
                    pre.setLong(9, p.getxu());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                pre.setInt(10, p.health);
                pre.setInt(11, p.idClan);
                pre.setInt(12, p.reclass);
                pre.setInt(13, p.headStyle);
                pre.setInt(14, p.flower);
                pre.setInt(15, p.gotWages ? 1 : 0);
                pre.setInt(16, p.timeXP);
                pre.setInt(17, p.gifMoonFestival ? 1 : 0);
                pre.setString(18, p.infoWearing);
                pre.setString(19, equip.toString());
                pre.setString(20, invent.toString());
                pre.setString(21, bag.toString());
                pre.setString(22, p.getSaveListKillMe());
                pre.setString(23, p.charname);
                pre.execute();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            pre2.close();
        } catch (Exception ignored) {
        }
    }

    public void updateCharRace(Connection conn, Char p) {
        PreparedStatement pre = null;
        try {
            String sql = "update " + Database.tableRace + " set exp=?,lastlv=?,infotop=?,lvchar=? where charid=?";
            pre = conn.prepareStatement(sql);
            pre.setLong(1, p.expRace);
            pre.setInt(2, LevelDetail.getLevelFromExp(p.expRace));
            pre.setString(3, p.infoWearing);
            pre.setInt(4, p.lvDetail.lv);
            pre.setInt(5, p.charDBID);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
    }

    public void updateCharPotion(Char p) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            final String sql = "UPDATE tob_char SET potion=? where id=?";
            StringBuilder potion = new StringBuilder(String.valueOf(p.getxu()));
            for (int i = 1; i < p.potions.length; ++i) {
                potion.append(",").append(p.potions[i]);
            }
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, potion.toString());
            pre.setInt(2, p.charDBID);
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateRcvGiftTop(String charName) {
        Connection conn = null;
        Statement pre = null;
        try {
            String sql = "UPDATE tob_char SET nAr1=0 where charname='" + charName + "'";
            conn = this.getConnection();
            pre = conn.createStatement();
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public static void loadAllCharSell() {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            final String sql = "select * from tob_market ";
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                String charname = rs.getString("charname").toLowerCase();
                long money = rs.getLong("money");
                String info = rs.getString("itemsell");
                String items = rs.getString("items");
                String itembid = rs.getString("listBid");
                ItemMarket.setItemSell(info, money, charname.toLowerCase().trim(), items, itembid);
            }
            ItemMarket.sortAllItemSell();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateMoneyCharSell(CharSellItem p) {
        Connection conn = null;
        Statement pre = null;
        try {
            conn = this.getConnection();
            String sql = "update tob_market set money=" + p.money + " where charname='" + p.charname + "'";
            pre = conn.createStatement();
            pre.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveAllCharSell(CharSellItem p) {
        String allItem = p.getAllItemSell();
        String allItemBack = p.getAllItemExpireSave();
        String allItemBid = p.getListItemBidSave();
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String sql = "INSERT INTO tob_market set charname=?,itemsell=?,money=?,items=?,listBid=? ON DUPLICATE KEY UPDATE itemsell=?,money=?,items=?,listBid=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.charname);
            pre.setString(2, allItem);
            pre.setLong(3, p.money);
            pre.setString(4, allItemBack);
            pre.setString(5, allItemBid);
            pre.setString(6, allItem);
            pre.setLong(7, p.money);
            pre.setString(8, allItemBack);
            pre.setString(9, allItemBid);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadAllMonsterVantieu() {
        int[][] idMonster = {{95, 96, 97, 98, 99, 100}, {101, 102, 103, 104, 105, 106}, {107, 108, 109, 110, 111, 112}};
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_vantieu";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                int idchar = rs.getInt("idchar");
                String namemaster = rs.getString("namechar");
                int typetieu = rs.getInt("typetieu");
                long timefinish = rs.getLong("tReceive");
                int lv = rs.getInt("lv");
                int mapid = rs.getInt("mapid");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int hp = rs.getInt("hp");
                int maxhp = rs.getInt("maxhp");
                int country = rs.getInt("idcountry");
                Map map = RealController.mapList.get(mapid);
                MonsterVantieu monster = new MonsterVantieu(map, Map.monsterTemplates.get(idMonster[typetieu / 6][typetieu % 6]), x, y, country);
                monster.charDbid = idchar;
                monster.level = lv;
                monster.maxhp = maxhp;
                monster.hp = hp;
                if (maxhp <= 0) {
                    monster.maxhp = Char.HP_MONSTER_VANTIEU[(lv - 40) / 10] + Char.HP_MONSTER_VANTIEU[(lv - 40) / 10] * Char.UP_HP_MONSTER_VANTIEU[(lv - 40) / 10] / 100;
                    monster.hp = monster.maxhp;
                }
                monster.defend_magic = Char.DEF_MONSTER_VANTIEU[(lv - 40) / 10] + Char.DEF_MONSTER_VANTIEU[(lv - 40) / 10] * Char.UP_DEF_MONSTER_VANTIEU[(lv - 40) / 10] / 100;
                monster.defend_physic = monster.defend_magic;
                monster.typeTieu = (byte) typetieu;
                monster.setTimeFinish(timefinish);
                monster.namemaster = namemaster;
                if (!monster.isFinish()) {
                    monster.id = RealController.intance.idGen.getID(1, "");
                    MonsterVantieu.addMonster(monster);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            st.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveMonsterVantieu(MonsterVantieu mons) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String sql = "INSERT INTO tob_vantieu set idchar=?,namechar=?,typetieu=?,tReceive=?,lv=?,mapid=?,x=?,y=?,idcountry=?,hp=?,maxhp=? ON DUPLICATE KEY UPDATE typetieu=?,lv=?,mapid=?,x=?,y=?,hp=?,maxhp=?,tReceive=?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, mons.charDbid);
            pre.setString(2, mons.namemaster);
            pre.setByte(3, mons.typeTieu);
            pre.setLong(4, mons.timeFinish);
            pre.setShort(5, (short) mons.level);
            pre.setShort(6, (short) mons.map.mapId);
            pre.setShort(7, (short) mons.x);
            pre.setShort(8, (short) mons.y);
            pre.setByte(9, mons.inCountry);
            pre.setLong(10, mons.hp);
            pre.setLong(11, mons.maxhp);
            pre.setByte(12, mons.typeTieu);
            pre.setShort(13, (short) mons.level);
            pre.setShort(14, (short) mons.map.mapId);
            pre.setShort(15, (short) mons.x);
            pre.setShort(16, (short) mons.y);
            pre.setLong(17, mons.hp);
            pre.setLong(18, mons.maxhp);
            pre.setLong(19, mons.timeFinish);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public int getDiemLuongXaiNhanDiem(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_top_event where charname='" + charname + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("luong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int getRankLoiDai(int point, String timeUpdate, int idNhom) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select count(id) as a from tob_char_thach_dau where nhom=" + idNhom + " and point>" + point;
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("a") + 1;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public Vector<CharInfo> getTopLoiDai(int idNhom) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Vector<CharInfo> top = new Vector<>();
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tob_char_thach_dau where nhom=" + idNhom + " order by point desc limit 10";
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return top;
    }

    public void loadAllCharThiDau(int index) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_char_thach_dau where nhom=" + index + " order by point desc";
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            while (rs.next()) {
                CharThiDau c = new CharThiDau(rs.getString("charname"), rs.getInt("lv"), rs.getInt("id"));
                c.nhom = index;
                c.point = rs.getInt("point");
                Map.ALL_CHAR_LOI_DAI.get(index).put(c.charDbId, c);
                Map.H_ALL_CHAR_LOI_DAI.put(c.charDbId, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public int doGetNhomLoiDai(int charDBID) {
        Connection conn = null;
        Statement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_char_thach_dau where id=" + charDBID;
            conn = this.getConnection();
            pre = conn.createStatement();
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return rs.getByte("nhom");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return -1;
    }

    public void doAddCharThachDau(CharThiDau player, int nhom, boolean updatePointRank) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            String sql = "INSERT INTO tob_char_thach_dau set id=?,charname=?,point=?,lv=?,timeupdate=now(),timeReg=now(),nhom=?,pointrank=? ON DUPLICATE KEY UPDATE point=?,timeupdate=now()";
            if (updatePointRank) {
                sql = "INSERT INTO tob_char_thach_dau set id=?,charname=?,point=?,lv=?,timeupdate=now(),timeReg=now(),nhom=?,pointrank=? ON DUPLICATE KEY UPDATE point=?,timeupdate=now(),pointrank=?";
            }
            pre = conn.prepareStatement(sql);
            pre.setInt(1, player.charDbId);
            pre.setString(2, player.name);
            pre.setInt(3, player.point);
            pre.setInt(4, player.level);
            pre.setInt(5, player.nhom);
            pre.setInt(6, player.point);
            pre.setInt(7, player.point);
            if (updatePointRank) {
                pre.setInt(8, player.point);
            }
            pre.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void getDiemNapXaiLuong(Char p) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_nap_xai_luong" + this.curMonthYear + " where charname='" + p.charname + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                p.diemNapVip = rs.getInt("luongnap");
                if (UtilKPAH.getDayOfMonth() != 3) {
                    p.diemxaiVip = rs.getInt("luongxai");
                }
            } else {
                p.diemNapVip = 0;
                if (UtilKPAH.getDayOfMonth() != 3) {
                    p.diemxaiVip = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharXaiNapLuong(Char p, int type) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_nap_xai_luong" + this.curMonthYear + " (charname,luongnap,tnap) values ('" + p.charname + "'," + p.diemNapVip + ",now()) ON DUPLICATE KEY UPDATE luongnap=" + p.diemNapVip + ",tnap=now()";
            if (type == 1) {
                sql = "insert into tob_nap_xai_luong" + this.curMonthYear + " (charname,luongxai,txai) values ('" + p.charname + "'," + p.diemxaiVip + ",now()) ON DUPLICATE KEY UPDATE luongxai=" + p.diemxaiVip + ",txai=now()";
            }
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharWinThanh(String tenBang, Char bangchu, int type, long xu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_chiemthanh(clan,bangchu,win,xu) values ('" + tenBang + "'," + bangchu.charname + ",1," + xu + ") ON DUPLICATE KEY UPDATE win = win + '1',xu = xu + " + xu + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharVT(Char p, int type, int xu, int luong) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "UPDATE tob_vantieu set count = count + 1 WHERE namechar = '" + p.charname + "'";

            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharEvent_ND(Char p, int type, int xu, int luong) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + p.charname + "'," + p.banphaohoa + ",now()) ON DUPLICATE KEY UPDATE point1=" + p.banphaohoa + ",timeupdate=now()";
            if (type == 1) {
                sql = "insert into tob_top_event(charname,xu,datexu) values ('" + p.charname + "'," + xu + ",now()) ON DUPLICATE KEY UPDATE xu=xu+" + xu + ",datexu=now()";
            } else if (type == 2) {
                sql = "insert into tob_top_event(charname,luong,dateluong) values ('" + p.charname + "'," + luong + ",now()) ON DUPLICATE KEY UPDATE luong=luong+" + luong + ",dateluong=now()";
            } else if (type == 3) {
                sql = "insert into tob_top_event(charname,xu,datexu,point1,timeupdate,luong,dateluong) values ('" + p.charname + "'," + xu + ",now()," + p.banphaohoa + "',now()," + luong + ",now()) ON DUPLICATE KEY UPDATE xu=xu+" + xu + ",datexu=now(),point1=" + p.banphaohoa + ",timeupdate=now(),luong=luong+" + luong + ",dateluong=now()";
            }
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharEvent_MiniCN(Char p, int type, int xu, int luong) {
        if (!Char.isSuKienMiniChucNu()) {
            return;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,luong,dateluong) values ('" + p.charname + "'," + luong + ",now()) ON DUPLICATE KEY UPDATE luong=luong+" + luong + ",dateluong=now()";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }
    public void addCharEventHLW(Char p, int type, int xu, int luong, int point) {
        if (!Char.isSuKienHaloween2016()) {
            return;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + p.charname + "'," + p.banphaohoa + ",now()) ON DUPLICATE KEY UPDATE point1=" + p.banphaohoa + ",timeupdate=now()";
            if (type == 2) {
                sql = "insert into tob_top_event(charname,luong,dateluong,point1) values ('" + p.charname + "'," + luong + ",now()) ON DUPLICATE KEY UPDATE luong=luong+" + luong + ",point1="+point+"dateluong=now()";
            }
            
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }
    
    public void addCharEvent(Char p, int type, int xu, int luong) {
        if (!Char.isSuKienHaloween2016()) {
            return;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + p.charname + "'," + p.banphaohoa + ",now()) ON DUPLICATE KEY UPDATE point1=" + p.banphaohoa + ",timeupdate=now()";
            if (type == 1) {
                sql = "insert into tob_top_event(charname,xu,datexu) values ('" + p.charname + "'," + xu + ",now()) ON DUPLICATE KEY UPDATE xu=xu+" + xu + ",datexu=now()";
            } else if (type == 2) {
                sql = "insert into tob_top_event(charname,luong,dateluong,point1) values ('" + p.charname + "'," + luong + ",now()) ON DUPLICATE KEY UPDATE luong=luong+" + luong + ",point1="+(luong/100)+"dateluong=now()";
            } else if (type == 3) {
                sql = "insert into tob_top_event(charname,xu,datexu,point1,timeupdate,luong,dateluong) values ('" + p.charname + "'," + xu + ",now()," + p.banphaohoa + "',now()," + luong + ",now()) ON DUPLICATE KEY UPDATE xu=xu+" + xu + ",datexu=now(),point1=" + p.banphaohoa + ",timeupdate=now(),luong=luong+" + luong + ",dateluong=now()";
            }
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<CharInfo> getTopEvent() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<CharInfo> result = new Vector<>();
        try {
            final String sql = "select * from tob_top_event order by point1 desc limit 10";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CharInfo c = new CharInfo();
                c.name = rs.getString("charname");
                c.honor = rs.getInt("point1");
                result.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return result;
    }

    public void updateHangDauGia(String charname, int hang) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update tob_dau_gia set hang=" + hang + " where charname='" + charname + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void loadSMS(int provider) {
        Vector<String> decript = new Vector<>();
        Vector<String> syntax = new Vector<>();
        Vector<String> center = new Vector<>();
        Vector<String> agent = new Vector<>();
        Vector<String> pro = new Vector<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_smsnap where provider=" + provider;
            if (provider != 1) {
                sql = "select * from tob_smsnap where provider<>1";
            }
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                decript.add(rs.getString("text"));
                syntax.add(rs.getString("syntax"));
                center.add(rs.getString("port"));
                agent.add(rs.getString("agent"));
                pro.add(rs.getString("provider"));
            }
            if (decript.size() > 0) {
                if (provider == 1) {
                    Database.smsNapVTC.reset();
                    Database.smsNapVTC.setData(decript, syntax, center, agent, pro);
                } else {
                    Database.smsNapMe.reset();
                    Database.smsNapMe.setData(decript, syntax, center, agent, pro);
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        Database.allProvider.put("0", new CCUProvider("0"));
    }

    public void updateHangDauGia2(String charname, int hang) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "update tob_dau_gia2 set hang=" + hang + " where charname='" + charname + "'";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public int getMoneyDauGia(String charname, int hang) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_dau_gia where charname='" + charname + "' and hang=" + hang;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (hang == 1) {
                    return rs.getInt("luongbid");
                }
                if (hang == 2) {
                    return rs.getInt("luongcoc");
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int getMoneyDauGia2(String charname, int hang) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_dau_gia2 where charname='" + charname + "' and hang=" + hang;
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (hang == 1) {
                    return rs.getInt("luongbid");
                }
                if (hang == 2) {
                    return rs.getInt("luongcoc");
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public boolean doAddCharDauGia(Char p, int luong) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_dau_gia(charname,luongbid,luongcoc,timebid) values ('" + p.charname + "'," + luong + "," + luong / 2 + ",now())";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            return true;
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public boolean doAddCharDauGia2(Char p, int luong) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_dau_gia2(charname,luongbid,luongcoc,timebid) values ('" + p.charname + "'," + luong + "," + luong / 2 + ",now())";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            return true;
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public boolean doAddCharMuaDoThoiTrang(Char p, int luong) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_mua_thoi_trang(charname,timebid) values ('" + p.charname + "',now())";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            return true;
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void addCharEventTrungthu(Char p) {
        if (!Char.isSuKienTrungThul2016()) {
            return;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + p.charname + "'," + p.banphaohoa + ",now()) ON DUPLICATE KEY UPDATE point1=" + p.banphaohoa + ",timeupdate=now()";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetTopEvent() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "delete from tob_top_event";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetChienTruongMoba() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "delete from tob_chien_truong";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Vector<String> getTopChienTruongMoba(String colum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tob_chien_truong where " + colum + ">0 order by " + colum + " desc limit 3";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            Vector<String> result = new Vector<>();
            while (rs.next()) {
                result.add(rs.getString("charname"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        return new Vector<>();
    }

    public void loadCharChienTruong() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "select * from tob_reg_chientruong";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String charname = rs.getString("charname");
                CharChienTruong c = new CharChienTruong();
                c.name = charname;
                MapChienTruongMoba.all_char_chien_truong.put(c.name, c);
                MapChienTruongMoba.v_all_char_chien_truong.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetCharRegChienTruongMoba(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_reg_chientruong_bk SELECT * FROM tob_reg_chientruong";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            try {
                pstmt.close();
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            sql = "delete FROM tob_reg_chientruong";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetCharQuaKhuCT(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_qua_ct_bk SELECT * FROM tob_qua_ct";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            try {
                pstmt.close();
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            conn = this.getConnection();
            sql = "delete FROM tob_qua_ct";
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void resetCharQuaTopCT(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_qua_ct_top_bk SELECT * FROM tob_qua_ct_top";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            try {
                pstmt.close();
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            conn = this.getConnection();
            sql = "delete FROM tob_qua_ct_top";
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception ignored) {
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharRegChienTruongMoba(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_reg_chientruong(charname,timeReg) values ('" + charname + "',now() ) ON DUPLICATE KEY UPDATE timeReg=now()";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void doAddCharWinCT(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_qua_ct(charname,tset) values ('" + charname + "',now()) ON DUPLICATE KEY UPDATE soluong=soluong+1";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void doAddCharTopCT(String charname, int top) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_qua_ct_top(charname,soluong,tset) values ('" + charname + "'," + top + ",now())";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharChienTruongMoba(String charname, String colum, int point) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_chien_truong(charname," + colum + ") values ('" + charname + "'," + point + " ) ON DUPLICATE KEY UPDATE " + colum + "=" + colum + "+" + point;
            if (colum.equals("lientram")) {
                sql = "insert into tob_chien_truong(charname," + colum + ") values ('" + charname + "'," + point + " ) ON DUPLICATE KEY UPDATE " + colum + "=" + point;
            }
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharEventWC(Char p) {
//        if (!Char.isSuKienTet2017()) {
//            return;
//        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event_wc (charname,point1,timeupdate,crtime) values ('" + p.charname + "'," + 1 + ",now()," + System.currentTimeMillis() + " ) ON DUPLICATE KEY UPDATE point1=point1+" + 1 + ",timeupdate=now(),crtime=" + System.currentTimeMillis();
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharEventTet(Char p) {
//        if (!Char.isSuKienTet2017()) {
//            return;
//        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate,crtime) values ('" + p.charname + "'," + 1 + ",now()," + System.currentTimeMillis() + " ) ON DUPLICATE KEY UPDATE point1=point1+" + 1 + ",timeupdate=now(),crtime=" + System.currentTimeMillis();
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void banChar(Char p, String lydo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            this.saveOrtherLog("", p.charname, "BANCHAR", lydo);
            String sql = "UPDATE tob_char SET ban=1 where charname='" + p.charname + "'";
            conn = this.getConnection();
            Statement pree = conn.createStatement();
            pree.execute(sql);
            pree.close();
        } catch (Exception ignored) {
        }
    }

    public void addCharEvent83(Char p) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + p.charname + "',1,now()) ON DUPLICATE KEY UPDATE point1=point1+1,timeupdate=now()";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    /**
     * todo LÆ°u dá»¯ liá»‡u player vÃ o database
     *
     * @param p
     * @param admindExit
     * @return
     */
    public boolean saveChar(Char p, boolean admindExit) {
        if (p == null) {
            return false;
        }
        if (p.isBot != -1) {
            return false;
        }
        if (p.receiveQuest) {
            p.createCharQuest();
            this.saveCharQuest(p.charDBID, p.char_quest);
        }
        try {
            NewClan clanInfo = Map.getClaninfoByID(p.idClan);
            if (clanInfo != null && clanInfo.master.equalsIgnoreCase(p.charname)) {
                clanInfo.updateNewClandata2DB();
            }
        } catch (Exception ignored) {
        }
        int totalPlay = (int) (System.currentTimeMillis() - p.timePlay) / 60000;
        StringBuilder potion = new StringBuilder(String.valueOf(p.getxu()));
        StringBuilder skill = new StringBuilder(String.valueOf(p.skill[0]));
        String basic = String.valueOf(p.strength) + "," + p.agitity + "," + p.spirit + "," + p.health + "," + p.luck + "," + p.basepoint + "," + p.skillpoint;
        for (int i = 1; i < p.potions.length; ++i) {
            potion.append(",").append(p.potions[i]);
        }
        for (int i = 1; i < p.skill.length; ++i) {
            skill.append(",").append(p.skill[i]);
        }
        for (int i = 0; i < p.skillClan.length; ++i) {
            skill.append(",").append(p.skillClan[i]);
        }
        for (int i = 0; i < p.skillLvClan.length; ++i) {
            skill.append(",").append(p.skillLvClan[i]);
        }
        String dbInfo = p.getDBInfo();
        StringBuilder equip = new StringBuilder();
        StringBuilder invent = new StringBuilder();
        StringBuilder bag = new StringBuilder();
        StringBuilder tuido = new StringBuilder();
        try {
            Vector<Item> wearing = new Vector<>();
            wearing = p.getCharWearing(0);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.getCharWearing(1);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.awItems;
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            if (!equip.toString().equals("")) {
                equip = new StringBuilder(equip.substring(0, equip.length() - 1));
            }
        } catch (Exception ignored) {
        }
        try {
            if (p.itemHoaKyLan != null) {
                invent.append(p.itemHoaKyLan.getInfoSave()).append(">");
            }
            if (p.itemVukhiThoiTrang != null) {
                invent.append(p.itemVukhiThoiTrang.getInfoSave()).append(">");
            }
            for (Item item2 : p.iItems) {
                invent.append(item2.getInfoSave()).append(">");
            }
            if (!invent.toString().equals("")) {
                invent = new StringBuilder(invent.substring(0, invent.length() - 1));
            }
            String infomodel = p.wModel.getInfoModelWearing();
            if (!infomodel.equals("")) {
                invent.append(">").append(infomodel);
            }
        } catch (Exception ignored) {
        }
        try {
            for (Item item2 : p.bItem) {
                bag.append(item2.getInfoSave()).append(">");
            }
            if (!bag.toString().equals("")) {
                bag = new StringBuilder(bag.substring(0, bag.length() - 1));
            }
        } catch (Exception ignored) {
        }
        try {
            for (int k = 0; k < p.bagItems.size(); ++k) {
                tuido.append(p.bagItems.get(k).getInfoSave()).append(">");
            }
            if (!tuido.toString().equals("")) {
                tuido = new StringBuilder(tuido.substring(0, tuido.length() - 1));
            }
        } catch (Exception ignored) {
        }
        Connection conn = null;
        PreparedStatement pre = null;
        String sql = "UPDATE tob_char SET pInfo=?,basic=?,skill=?,potion=?,totalTimePlay=totalTimePlay+?, luong=?, lastLv=?,xp=?,gold=?,hp=?, idClan=?, reupclass=?,headStyle=?,ticket=?,x=?,y=?,book=?,lastLog=Now(),infotop=?,equip=?,"
                + "inven=?,bag=?,killme=?,luonglock=?,tuido=?,isPP=?,isMN=? where id=?";
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dbInfo);
            pre.setString(2, basic);
            pre.setString(3, skill.toString());
            pre.setString(4, potion.toString());
            pre.setLong(5, totalPlay);
            pre.setInt(6, Math.max(p.getLuong(), 0));
            pre.setInt(7, p.lastLV);
            pre.setLong(8, p.lvDetail.getExp());
            try {
                pre.setLong(9, p.getxu());
            } catch (Exception e) {
                e.printStackTrace();
            }
            pre.setInt(10, p.health);
            pre.setInt(11, p.idClan);
            pre.setInt(12, p.reclass);
            pre.setInt(13, p.headStyle);
            pre.setInt(14, p.flower);
            pre.setInt(15, p.gotWages ? 1 : 0);
            pre.setInt(16, p.timeXP);
            pre.setInt(17, p.gifMoonFestival ? 20 : 0);
            pre.setString(18, p.infoWearing);
            pre.setString(19, equip.toString());
            pre.setString(20, invent.toString());
            pre.setString(21, bag.toString());
            pre.setString(22, p.getSaveListKillMe());
            pre.setInt(23, p.getLuongLock());
            pre.setString(24, tuido.toString());
            pre.setByte(25, (byte) (p.isTatPhiPhong() ? 1 : 0));
            pre.setByte(26, (byte) (p.isTatMatNa() ? 1 : 0));
            pre.setInt(27, p.charDBID);
            try {
                pre.execute();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    this.saveOrtherLog("", p.charname, "banchar khi loi invent", "loiinvent");
                    sql = "UPDATE tob_char SET ban=1 where charname='" + p.charname + "'";
                    conn = this.getConnection();
                    Statement pree = conn.createStatement();
                    pree.execute(sql);
                    pree.close();
                } catch (Exception ignored) {
                }
                System.out.println("LOI excute char " + e);
            }
            try {
                if (p.charDBID > 0) {
                    StringBuilder soluong = new StringBuilder(String.valueOf(p.listGemitem[0]));
                    for (int l = 1; l < p.listGemitem.length; ++l) {
                        soluong.append(",").append(p.listGemitem[l]);
                    }
                    StringBuilder soluong2 = new StringBuilder(String.valueOf(p.listGemitemLock[0]));
                    for (int m = 1; m < p.listGemitemLock.length; ++m) {
                        soluong2.append(",").append(p.listGemitemLock[m]);
                    }
                    this.saveCharGem(soluong.toString(), p.charDBID, soluong2.toString());
                }
            } catch (Exception ignored) {
            }
            try {
                do {
                    Animal gem = p.animal.remove(0);
                    if (gem.ownerId != p.charDBID) {
                        continue;
                    }
                    this.saveAnimal(conn, gem);
                } while (p.animal.size() > 0);
            } catch (Exception ignored) {
            }
            try {
                if (p.charDBID > 0) {
                    StringBuilder soluong = new StringBuilder(String.valueOf(p.listSpItem[0]));
                    for (int l = 1; l < p.listSpItem.length; ++l) {
                        soluong.append(",").append(p.listSpItem[l]);
                    }
                    this.saveCharSP(soluong.toString(), p.charDBID);
                }
            } catch (Exception ignored) {
            }
            try {
                for (int i2 = 0; i2 < p.seedsItem.size(); ++i2) {
                    this.saveFarm(p.seedsItem.get(i2), conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                do {
                    Pet gem2 = p.pet.remove(0);
                    if (gem2.ownerid != p.charDBID) {
                        continue;
                    }
                    this.savePet(gem2);
                } while (p.pet.size() > 0);
            } catch (Exception ignored) {
            }
            this.savecharLuong(conn, p);
            if (p.idGropRace > -1 && Database.createRace < 2) {
                this.updateCharRace(conn, p);
            }
            this.saveCharQuest(p.charDBID, p.getInfoQuestSave());
            this.saveCharFruit(p.charDBID, p.getInfoSaveFruit());
            this.saveCharTubinh(p.charDBID, p.getInfoSaveTubinh());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.logError(ex, p.charname + ".txt");
            return false;
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        ++TeamServer.update;
        return true;
    }

    public boolean saveCharAuto(Char p) {
        if (p == null) {
            return false;
        }
        if (p.isBot != -1) {
            return false;
        }
        if (p.receiveQuest) {
            p.createCharQuest();
            this.saveCharQuest(p.charDBID, p.char_quest);
        }
        try {
            NewClan clanInfo = Map.getClaninfoByID(p.idClan);
            if (clanInfo != null && clanInfo.master.equalsIgnoreCase(p.charname)) {
                clanInfo.updateNewClandata2DB();
            }
        } catch (Exception ignored) {
        }
        int totalPlay = (int) (System.currentTimeMillis() - p.timePlay) / 60000;
        StringBuilder potion = new StringBuilder(String.valueOf(p.getxu()));
        StringBuilder skill = new StringBuilder(String.valueOf(p.skill[0]));
        String basic = String.valueOf(p.strength) + "," + p.agitity + "," + p.spirit + "," + p.health + "," + p.luck + "," + p.basepoint + "," + p.skillpoint;
        for (int i = 1; i < p.potions.length; ++i) {
            potion.append(",").append(p.potions[i]);
        }
        for (int i = 1; i < p.skill.length; ++i) {
            skill.append(",").append(p.skill[i]);
        }
        for (int i = 0; i < p.skillClan.length; ++i) {
            skill.append(",").append(p.skillClan[i]);
        }
        for (int i = 0; i < p.skillLvClan.length; ++i) {
            skill.append(",").append(p.skillLvClan[i]);
        }
        String dbInfo = p.getDBInfo();
        StringBuilder equip = new StringBuilder();
        StringBuilder invent = new StringBuilder();
        StringBuilder bag = new StringBuilder();
        StringBuilder tuido = new StringBuilder();
        try {
            Vector<Item> wearing = new Vector<>();
            wearing = p.getCharWearing(0);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.getCharWearing(1);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.awItems;
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            if (!equip.toString().equals("")) {
                equip = new StringBuilder(equip.substring(0, equip.length() - 1));
            }
        } catch (Exception ignored) {
        }
        try {
            if (p.itemHoaKyLan != null) {
                invent.append(p.itemHoaKyLan.getInfoSave()).append(">");
            }
            if (p.itemVukhiThoiTrang != null) {
                invent.append(p.itemVukhiThoiTrang.getInfoSave()).append(">");
            }
            for (Item item2 : p.iItems) {
                invent.append(item2.getInfoSave()).append(">");
            }
            if (!invent.toString().equals("")) {
                invent = new StringBuilder(invent.substring(0, invent.length() - 1));
            }
            String infomodel = p.wModel.getInfoModelWearing();
            if (!infomodel.equals("")) {
                invent.append(">").append(infomodel);
            }
        } catch (Exception ignored) {
        }
        try {
            for (Item item2 : p.bItem) {
                bag.append(item2.getInfoSave()).append(">");
            }
            if (!bag.toString().equals("")) {
                bag = new StringBuilder(bag.substring(0, bag.length() - 1));
            }
        } catch (Exception ignored) {
        }
        try {
            for (int k = 0; k < p.bagItems.size(); ++k) {
                tuido.append(p.bagItems.get(k).getInfoSave()).append(">");
            }
            if (!tuido.toString().equals("")) {
                tuido = new StringBuilder(tuido.substring(0, tuido.length() - 1));
            }
        } catch (Exception ignored) {
        }
        final String sql = "UPDATE tob_char SET pInfo=?,basic=?,skill=?,potion=?,totalTimePlay=totalTimePlay+?, luong=?, lastLv=?,xp=?,gold=?,hp=?, idClan=?, reupclass=?,headStyle=?,ticket=?,x=?,y=?,book=?,lastLog=Now(),infotop=?,equip=?,inven=?,bag=?,luonglock=?,tuido=? where id=?";
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dbInfo);
            pre.setString(2, basic);
            pre.setString(3, skill.toString());
            pre.setString(4, potion.toString());
            pre.setLong(5, totalPlay);
            pre.setInt(6, Math.max(p.getLuong(), 0));
            pre.setInt(7, p.lastLV);
            pre.setLong(8, p.lvDetail.getExp());
            try {
                pre.setLong(9, p.getxu());
            } catch (Exception e) {
                e.printStackTrace();
            }
            pre.setInt(10, p.health);
            pre.setInt(11, p.idClan);
            pre.setInt(12, p.reclass);
            pre.setInt(13, p.headStyle);
            pre.setInt(14, p.flower);
            pre.setInt(15, p.gotWages ? 1 : 0);
            pre.setInt(16, p.timeXP);
            pre.setInt(17, p.gifMoonFestival ? 1 : 0);
            pre.setString(18, p.infoWearing);
            pre.setString(19, equip.toString());
            pre.setString(20, invent.toString());
            pre.setString(21, bag.toString());
            pre.setInt(22, p.getLuongLock());
            pre.setString(23, tuido.toString());
            pre.setInt(24, p.charDBID);
            try {
                pre.execute();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("LOI excute char " + e);
            }
            try {
                if (p.charDBID > 0) {
                    StringBuilder soluong = new StringBuilder(String.valueOf(p.listGemitem[0]));
                    for (int l = 1; l < p.listGemitem.length; ++l) {
                        soluong.append(",").append(p.listGemitem[l]);
                    }
                    StringBuilder soluong2 = new StringBuilder(String.valueOf(p.listGemitemLock[0]));
                    for (int m = 1; m < p.listGemitemLock.length; ++m) {
                        soluong2.append(",").append(p.listGemitemLock[m]);
                    }
                    this.saveCharGem(soluong.toString(), p.charDBID, soluong2.toString());
                }
            } catch (Exception ignored) {
            }
            try {
                int size = p.animal.size();
                for (short i2 = 0; i2 < size; ++i2) {
                    Animal gem = p.animal.get(i2);
                    if (gem.ownerId == p.charDBID) {
                        if (gem.dbownerId == 0) {
                            this.saveAnimal(conn, gem);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
            try {
                for (int i3 = 0; i3 < p.seedsItem.size(); ++i3) {
                    this.saveFarm(p.seedsItem.get(i3), conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(" LOI SAVE CHAR " + p.charname + " | " + p.lvDetail.getExp());
            String logError = "UPDATE tob_char SET pInfo=" + dbInfo + ",basic=" + basic + ",skill=" + skill + ",potion=" + potion + ",totalTimePlay=totalTimePlay+" + totalPlay + ", luong=" + p.getLuong() + ", lastLv=" + p.lastLV + ",xp=" + p.lvDetail.getExp() + ",gold=" + p.getxu() + ",hp=" + p.hp + ",idClan=" + p.idClan + ",headStyle=" + p.headStyle + " where id=" + p.charDBID;
            Logger.logString(logError, "savechar.txt");
            return false;
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        ++TeamServer.update;
        return true;
    }

    public void saveCharSP(String allGem, int chardbid) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            final String listtemplate = "0,1";
            final String sql = "insert tob_sp_new(owner,listtemplate,soluong) values (?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, chardbid);
            pre.setString(2, listtemplate);
            pre.setString(3, allGem);
            pre.execute();
        } catch (Exception e) {
            try {
                final String sql = "update tob_sp_new set soluong=? where owner=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, allGem);
                pre.setInt(2, chardbid);
                pre.execute();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharGemBackup(Connection conn, String allGem, int chardbid, String allGemLock) {
    }

    public void saveCharGem(String allGem, int chardbid, String allGemLock) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection();
            StringBuilder listtemplate = new StringBuilder("0");
            this.saveCharGemBackup(conn, allGem, chardbid, allGemLock);
            for (int i = 1; i < Map.gemTemplate.length; ++i) {
                listtemplate.append(",").append(i);
            }
            final String sql = "insert tob_gem_new(owner,listtemplate,soluong,slock) values (?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, chardbid);
            pre.setString(2, listtemplate.toString());
            pre.setString(3, allGem);
            pre.setString(4, allGemLock);
            pre.execute();
        } catch (Exception e) {
            try {
                final String sql = "update tob_gem_new set soluong=?,slock=? where owner=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, allGem);
                pre.setString(2, allGemLock);
                pre.setInt(3, chardbid);
                pre.execute();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveGemItem(GemItem gem) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            if (gem.dbownerId == 0) {
                conn = this.getConnection();
                final String sql = "INSERT tob_gemitem(owner,place,gemtemplate) VALUES (?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerId);
                pre.setInt(2, gem.place);
                pre.setInt(3, gem.idGemTemplate);
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.dbid = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        gem.dbownerId = gem.ownerId;
    }

    private void saveGemItem(Connection conn, GemItem gem) throws SQLException {
        PreparedStatement pre = null;
        if (gem.dbownerId == 0) {
            final String sql = "INSERT tob_gemitem(owner,place,gemtemplate) VALUES (?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, gem.ownerId);
            pre.setInt(2, gem.place);
            pre.setInt(3, gem.idGemTemplate);
            pre.execute();
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        gem.dbownerId = gem.ownerId;
    }

    public void saveAnimal(Animal gem) {
        if (gem == null) {
            return;
        }
        Connection conn = null;
        PreparedStatement pre = null;
        Statement pre2 = null;
        try {
            conn = this.getConnection();
            if (gem.isHoaHinh == 1 && gem.timeHoaHinh >= 0L && System.currentTimeMillis() - gem.timeHoaHinh >= 0L) {
                gem.isHoaHinh = 0;
                gem.timeHoaHinh = 0L;
            }

            if (gem.dbownerId == 0) {
                final String sql = "INSERT tob_animal(owner,place,att,id_img,lv,name,texpire,isHoaHinh,timeHoaHinh) VALUES (?,?,?,?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerId);
                pre.setInt(2, gem.place);
                pre.setString(3, gem.getAttribute());
                pre.setInt(4, gem.idImg);
                pre.setInt(5, gem.level);
                pre.setString(6, gem.name);
                pre.setLong(7, gem.texpire);
                pre.setInt(8, gem.isHoaHinh);
                pre.setLong(9, gem.timeHoaHinh);
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.dbid = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } else {
                String sql = "UPDATE tob_animal SET owner=" + gem.ownerId + ",place=" + gem.place + ",att='" + gem.getAttribute() + "', id_img=" + gem.idImg + ",lv=" + gem.level + ",name='" + gem.name + "',texpire=" + gem.texpire + ",isHoaHinh=" + gem.isHoaHinh + ",timeHoaHinh=" + gem.timeHoaHinh + " WHERE id=" + gem.dbid + ";";
                pre2 = conn.createStatement();
                pre2.execute(sql);
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            gem.dbownerId = gem.ownerId;
        } catch (Exception e) {
            Logger.logError(e, "errorAnima.txt");
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            pre2.close();
        } catch (Exception ignored) {
        }
    }

    public void updateShopRuongMM() {
        Connection conn = null;
        Statement pre1 = null;
        try {
            String allUser = Map.getAllUserCountry();
            conn = this.getConnection();
            String sql = "UPDATE tob_event SET ruongmm='" + Map.getInfoSaveShopRuongMM() + "' where id=1";
            pre1 = conn.createStatement();
            pre1.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void updateInfoLixiEvent() {
        Connection conn = null;
        Statement pre1 = null;
        try {
            conn = this.getConnection();
            String sql = "UPDATE tob_event SET lixi='" + Map.MAX_LI_XI + "," + Map.hourGiveLixi + "," + Map.minuteGiveLixi + "' where id=1";
            pre1 = conn.createStatement();
            pre1.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void saveEvent(String info) {
        Connection conn = null;
        Statement pre1 = null;
        try {
            String allUser = Map.getAllUserCountry();
            conn = this.getConnection();
            String sql = "UPDATE tob_event SET part1='" + info + "',part2='" + allUser + "',part3='" + Map.getInfoWar() + "',part4='" + Map.getInfoT() + "'," + "shield='" + Map.getShield() + "'" + ",gov1='" + Map.getGov(0) + "',gov2='" + Map.getGov(1) + "',gov3='" + Map.getGov(2) + "',quayso='" + Map.getQuaySoPhuongHoang() + "',topluong='" + Char.getTopCharUseLuong() + "',topNapLuong='" + Char.getInfoTopLuongSave() + "',phaohoa='" + Map.totalDotPhao + "," + Map.x3ExpDotPhao + "',iditemsell=" + ItemSell.ID_ITEM_SELL_AUTO_INCREMENT + ",openphuonghoang='" + Map.getInfoSavePhuongHoang() + "',trung='" + Map.getInfoSavetrungpet() + "' where id=1";
            pre1 = conn.createStatement();
            pre1.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void loadEvent() {
        Connection conn = null;
        Statement pre1 = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_event where id=1";
            pre1 = conn.createStatement();
            ResultSet rs = pre1.executeQuery(sql);
            if (rs.next()) {
                Char.initTongKim(rs.getString("topluong"));
                Char.initTopNapLuong(rs.getString("topNapLuong"));
                String[] data = Char.split(rs.getString("part1"), ",");
                try {
                    if (data.length > 0) {
                        for (int i = 0; i < Map.event.value.length; ++i) {
                            Map.event.value[i] = Byte.parseByte(data[i]);
                        }
                        Map.event.timeBossAppear[0] = Long.parseLong(data[3]);
                        Map.event.timeBossAppear[1] = Long.parseLong(data[4]);
                        Map.event.timeBossAppear[2] = Long.parseLong(data[5]);
                        Map.event.timeBossAppear[3] = Long.parseLong(data[6]);
                        Map.event.timeBossAppear[4] = Long.parseLong(data[7]);
                        Map.event.timeBossAppear[5] = Long.parseLong(data[8]);
                        Map.event.timeBossAppear[6] = Long.parseLong(data[9]);
                    }
                } catch (Exception ignored) {
                }
                try {
                    Map.setInfoShopRuongMayMan(rs.getString("ruongmm"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    data = Char.split(rs.getString("part2"), ",");
                    if (data.length > 0) {
                        for (int i = 0; i < Map.allUser.length; ++i) {
                            Map.allUser[i] = Byte.parseByte(data[i]);
                            Map.allLV[i + 3] = Byte.parseByte(data[i + 3]);
                        }
                        Map.allXuLucky = Long.parseLong(data[6]);
                        Map.allLuongLucky = Long.parseLong(data[7]);
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("lixi"), ",");
                    if (data.length > 0) {
                        Map.MAX_LI_XI = Integer.parseInt(data[0]);
                        Map.hourGiveLixi = Integer.parseInt(data[1]);
                        Map.minuteGiveLixi = Integer.parseInt(data[2]);
                        if (UtilKPAH.getHour() == 5 || UtilKPAH.getHour() == 6) {
                            Map.MAX_LI_XI = 100;
                            Map.hourGiveLixi = 18;
                            Map.minuteGiveLixi = 30;
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("vip"), ",");
                    if (data.length > 0) {
                        Map.MAX_VIP1 = Short.parseShort(data[0]);
                        Map.MAX_VIP2 = Short.parseShort(data[1]);
                        Map.MAX_VIP3 = Short.parseShort(data[2]);
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("part3"), ",");
                    if (data.length > 0) {
                        for (int i = 0; i < 3; ++i) {
                            RegisterAttack reg = Map.nationWar.get(i);
                            reg.dayAttack = data[i * 3];
                            reg.idMyAttack = Byte.parseByte(data[i * 3 + 1]);
                            reg.idNationAttackMe = Byte.parseByte(data[i * 3 + 2]);
                            if (reg.dayAttack.compareTo(Char.getDayOpen(0L)) < 0 || reg.dayAttack.equals("")) {
                                reg.reset();
                            }
                            if (reg.idMyAttack > -1 && Map.idClanTown[reg.idMyAttack] == -1) {
                                reg.idMyAttack = -1;
                            }
                        }
                        Map.nationBeAttack[0] = Byte.parseByte(data[9]);
                        Map.nationBeAttack[1] = Byte.parseByte(data[10]);
                        Map.nationBeAttack[2] = Byte.parseByte(data[11]);
                        if (Map.idClanTown[0] == -1) {
                            Map.nationBeAttack[0] = -1;
                        }
                        if (Map.idClanTown[1] == -1) {
                            Map.nationBeAttack[1] = -1;
                        }
                        if (Map.idClanTown[2] == -1) {
                            Map.nationBeAttack[2] = -1;
                        }
                        Map.minuteX2Country[0] = Long.parseLong(data[12]);
                        Map.minuteX2Country[1] = Long.parseLong(data[13]);
                        Map.minuteX2Country[2] = Long.parseLong(data[14]);
                        Map.x2Country[0] = Byte.parseByte(data[15]);
                        Map.x2Country[1] = Byte.parseByte(data[16]);
                        Map.x2Country[2] = Byte.parseByte(data[17]);
                        RegisterAttack reg2 = Map.nationWar.get(0);
                        reg2.win = Byte.parseByte(data[18]);
                        reg2.lose = Byte.parseByte(data[19]);
                        reg2.timeX2 = Byte.parseByte(data[20]);
                        RegisterAttack reg3 = Map.nationWar.get(1);
                        reg3.win = Byte.parseByte(data[21]);
                        reg3.lose = Byte.parseByte(data[22]);
                        reg3.timeX2 = Byte.parseByte(data[23]);
                        RegisterAttack reg4 = Map.nationWar.get(2);
                        reg4.win = Byte.parseByte(data[24]);
                        reg4.lose = Byte.parseByte(data[25]);
                        reg4.timeX2 = Byte.parseByte(data[26]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    data = Char.split(rs.getString("part4"), ",");
                    if (data.length > 0) {
                        Map.event.maxT = Byte.parseByte(data[0]);
                        Map.event.nTDay = Byte.parseByte(data[1]);
                        Map.event.countT = Byte.parseByte(data[2]);
                        Map.winNumber = Byte.parseByte(data[3]);
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("shield"), ",");
                    if (data.length > 0) {
                        for (int i = 0; i < 4; ++i) {
                            Map.SHIELD[0][i] = Byte.parseByte(data[i]);
                            Map.SHIELD[1][i] = Byte.parseByte(data[i + 4]);
                            Map.SHIELD[2][i] = Byte.parseByte(data[i + 8]);
                        }
                        NewClan.timeDelayOpenShield[0] = Byte.parseByte(data[12]);
                        NewClan.timeDelayOpenShield[1] = Byte.parseByte(data[13]);
                        NewClan.timeDelayOpenShield[2] = Byte.parseByte(data[14]);
                        Map.newDay = data[15];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (Map.idClanTown[0] > -1) {
                        data = Char.split(rs.getString("gov1"), ",");
                        if (data.length > 0) {
                            Char.gov[0].addMember(0, data[0]);
                            Char.gov[0].addMember(1, data[1]);
                            Char.gov[0].addMember(1, data[2]);
                            Char.gov[0].addMember(2, data[3]);
                            Char.gov[0].addMember(2, data[4]);
                            Char.gov[0].addMember(3, data[5]);
                            Char.gov[0].addMember(3, data[6]);
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    if (Map.idClanTown[1] > -1) {
                        data = Char.split(rs.getString("gov2"), ",");
                        if (data.length > 0) {
                            Char.gov[1].addMember(0, data[0]);
                            Char.gov[1].addMember(1, data[1]);
                            Char.gov[1].addMember(1, data[2]);
                            Char.gov[1].addMember(2, data[3]);
                            Char.gov[1].addMember(2, data[4]);
                            Char.gov[1].addMember(3, data[5]);
                            Char.gov[1].addMember(3, data[6]);
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    if (Map.idClanTown[2] > -1) {
                        data = Char.split(rs.getString("gov3"), ",");
                        if (data.length > 0) {
                            Char.gov[2].addMember(0, data[0]);
                            Char.gov[2].addMember(1, data[1]);
                            Char.gov[2].addMember(1, data[2]);
                            Char.gov[2].addMember(2, data[3]);
                            Char.gov[2].addMember(2, data[4]);
                            Char.gov[2].addMember(3, data[5]);
                            Char.gov[2].addMember(3, data[6]);
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("quayso"), ",");
                    if (data.length > 0) {
                        Map.dayQuaySo = data[0];
                        Map.nPhuongHoang = 0;
                        Map.max_phuong_hoang = 0;
                        Map.countQuaySo = 0;
                        Map.MAX_QUAY = 5000;
                    } else {
                        Map.dayQuaySo = Char.getDayOpen(0L);
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("phaohoa"), ",");
                    if (data.length > 0) {
                        Map.totalDotPhao = Integer.parseInt(data[0]);
                        Map.x3ExpDotPhao = Byte.parseByte(data[1]);
                    } else {
                        Map.dayQuaySo = Char.getDayOpen(0L);
                    }
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("openphuonghoang"), ",");
                    Map.minOpenHopqua = Integer.parseInt(data[0]);
                    Map.totalPhuongHoang = 0;
                    Map.winPhuongHoang = 0;
                    Map.countOpenHopqua = 0;
                } catch (Exception ignored) {
                }
                try {
                    data = Char.split(rs.getString("trung"), "|");
                    Char.total_open = Integer.parseInt(data[0]);
                    Char.numberLucky = Integer.parseInt(data[1]);
                    Char.numberRandom = Integer.parseInt(data[2]);
                } catch (Exception ignored) {
                }
                ItemSell.ID_ITEM_SELL_AUTO_INCREMENT = rs.getInt("iditemsell");
                System.out.println("IDITEM SELL HIEN TAI: " + ItemSell.ID_ITEM_SELL_AUTO_INCREMENT);
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public int countCharVip(int vip) {
        return 1;

//        Connection conn = null;
//        PreparedStatement pre1 = null;
//        ResultSet rs = null;
//        try {
//            conn = this.getConnection();
//            String sql = "select count(id) as a from tob_char_vip where vip=" + vip + " and reset=0";
//            pre1 = conn.prepareStatement(sql);
//            rs = pre1.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("a");
//            }
//        } catch (Exception ignored) {
//        } finally {
//            try {
//                Database.connPool.free(conn);
//            } catch (Exception ignored) {
//            }
//            try {
//                pre1.close();
//            } catch (Exception ignored) {
//            }
//        }
//        try {
//            Database.connPool.free(conn);
//        } catch (Exception ignored) {
//        }
//        try {
//            pre1.close();
//        } catch (Exception ignored) {
//        }
//        return 1000;
    }

    public void doAddCharVip(String name, int vip) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            final String sql = "insert into tob_char_vip(charname,timeRcv,vip) values(?,now(),?)";
            pre1 = conn.prepareStatement(sql);
            pre1.setString(1, name);
            pre1.setInt(2, vip);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doresetCharVip() {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            final String sql = "update tob_char_vip set reset=1";
            pre1 = conn.prepareStatement(sql);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doUpdateCharVip(String name, int vip) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            String sql = "update tob_char_vip set vip=" + vip + ", timeRcv=now() where charname='" + name + "'";
            pre1 = conn.prepareStatement(sql);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doresetCharVip(String name) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            String sql = "update tob_char_vip set reset=1 where charname='" + name + "'";
            pre1 = conn.prepareStatement(sql);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doGetAllCharVip() {
        Connection conn = null;
        PreparedStatement pre1 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_char_vip where reset=0";
            pre1 = conn.prepareStatement(sql);
            rs = pre1.executeQuery();
            while (rs.next()) {
                Map.ALL_CHAR_VIP.put(rs.getString("charname"), rs.getInt("vip"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doGetAllCharVip(int type) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String sql = "select * from tob_char_vip where reset=0 and vip=" + type;
            pre1 = conn.prepareStatement(sql);
            rs = pre1.executeQuery();
            while (rs.next()) {
                Map.ALL_CHAR_VIP.put(rs.getString("charname"), rs.getInt("vip"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doUpdateCharBuyRuong(CharInfo cinfo) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            String sql = "update tob_buy_ruong set timeBuy=now(), soluong=" + cinfo.luong + " where charname='" + cinfo.name + "' and dateLog='" + Char.getDayOpen(0L) + "'";
            pre1 = conn.prepareStatement(sql);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void doResetCharBuyRuong() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            final String sql = "delete from tob_buy_ruong";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void doAddCharBuyRuong(CharInfo cinfo) {
        Connection conn = null;
        PreparedStatement pre1 = null;
        try {
            conn = this.getConnection();
            final String sql = "insert into tob_buy_ruong(charname,datelog,timeBuy,soluong) values(?,?,now(),?)";
            pre1 = conn.prepareStatement(sql);
            pre1.setString(1, cinfo.name);
            pre1.setString(2, Char.getDayOpen(0L));
            pre1.setInt(3, 1);
            pre1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void loadAllCharBuyRuong() {
        Connection conn = null;
        Statement pre1 = null;
        try {
            conn = this.getConnection();
            String sql = "select * from tob_buy_ruong where dateLog='" + Char.getDayOpen(0L) + "'";
            pre1 = conn.createStatement();
            ResultSet rs = pre1.executeQuery(sql);
            while (rs.next()) {
                CharInfo cinfo = new CharInfo();
                cinfo.name = rs.getString("charname");
                cinfo.luong = rs.getInt("soluong");
                Map.ALL_CHAR_BUY_RUONG.put(cinfo.name, cinfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre1.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre1.close();
        } catch (Exception ignored) {
        }
    }

    public void savePet(Pet gem) {
        if (gem == null) {
            return;
        }
        Connection conn = null;
        PreparedStatement pre = null;
        Statement pre2 = null;
        try {
            conn = this.getConnection();
            if (gem.idDB == 0) {
                final String sql = "INSERT tob_pet(owner,place,info,idImg,name,itPet) VALUES (?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerid);
                pre.setInt(2, gem.place);
                pre.setString(3, gem.getDBInfo());
                pre.setInt(4, gem.idImg);
                pre.setString(5, gem.getNameSave());
                pre.setString(6, gem.getInfoItem());
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.idDB = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } else {
                String sql = "UPDATE tob_pet SET owner=" + gem.ownerid + ",place=" + gem.place + ",info='" + gem.getDBInfo() + "', idImg=" + gem.idImg + ",name='" + gem.getNameSave() + "',itPet='" + gem.getInfoItem() + "' WHERE id=" + gem.idDB + ";";
                pre2 = conn.createStatement();
                pre2.execute(sql);
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logError(e, "errorAnima.txt");
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            pre2.close();
        } catch (Exception ignored) {
        }
    }

    private void saveAnimal(Connection conn, Animal gem) {
        PreparedStatement pre = null;
        Statement pre2 = null;
        try {
            if (gem.isHoaHinh == 1 && gem.timeHoaHinh >= 0L && System.currentTimeMillis() - gem.timeHoaHinh >= 0L) {
                gem.isHoaHinh = 0;
                gem.timeHoaHinh = 0L;
            }

            if (gem.dbownerId == 0) {
                final String sql = "INSERT tob_animal(owner,place,att,id_img,lv,name,isHoaHinh,timeHoaHinh) VALUES (?,?,?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerId);
                pre.setInt(2, gem.place);
                pre.setString(3, gem.getAttribute());
                pre.setInt(4, gem.idImg);
                pre.setInt(5, gem.level);
                pre.setString(6, gem.name);
                pre.setInt(7,gem.isHoaHinh);
                pre.setLong(8,gem.timeHoaHinh);
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.dbid = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } else {

                String sql = "UPDATE tob_animal SET owner=" + gem.ownerId + 
                ",place=" + gem.place + 
                ",att='" + gem.getAttribute() + "'" +
                ",id_img=" + gem.idImg + 
                ",lv=" + gem.level + 
                ",name='" + gem.name + "'" +
                ",isHoaHinh=" + gem.isHoaHinh +
                ",timeHoaHinh=" + gem.timeHoaHinh + 
                " WHERE id=" + gem.dbid + ";";

                pre2 = conn.createStatement();
                pre2.execute(sql);
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
            gem.dbownerId = gem.ownerId;
        } catch (Exception e) {
            Logger.logError(e, "errorAnima.txt");
        }
    }

    private void saveAnimal1(Connection conn, Animal gem, String idPrivateSv) {
        PreparedStatement pre = null;
        Statement pre2 = null;
        try {
            try {
                final String sql = "INSERT tob_animal(owner,place,att,id_img,lv,name,idPrivateSv) VALUES (?,?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerId);
                pre.setInt(2, gem.place);
                pre.setString(3, gem.getAttribute());
                pre.setInt(4, gem.idImg);
                pre.setInt(5, gem.level);
                pre.setString(6, gem.name);
                pre.setString(7, idPrivateSv);
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.dbid = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } catch (Exception e) {
                String sql = "UPDATE tob_animal SET owner=" + gem.ownerId + ",place=" + gem.place + ",att='" + gem.getAttribute() + "', id_img=" + gem.idImg + ",lv=" + gem.level + ",name='" + gem.name + "' WHERE idPrivateSv='" + idPrivateSv + "';";
                pre2 = conn.createStatement();
                pre2.execute(sql);
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
            gem.dbownerId = gem.ownerId;
        } catch (Exception e) {
            Logger.logError(e, "errorAnima.txt");
        }
    }

    public void saveFarm(SeedItem s, Connection conn) {
        String sql = "";
        PreparedStatement pre = null;
        try {
            if (s.idDB != 0) {
                sql = "UPDATE tob_farm SET farmId=?,treeId=?,lv=?,timeBegin=?,buy=?,lvPlot=?,rank=?  WHERE id=?";
                pre = conn.prepareStatement(sql);
                pre.setByte(1, s.idFarm);
                pre.setByte(2, s.idTemplate);
                pre.setByte(3, s.lvTree);
                pre.setLong(4, s.timeLive);
                pre.setInt(5, s.buy);
                pre.setByte(6, s.lvPlot);
                pre.setByte(7, s.rankTree);
                pre.setInt(8, s.idDB);
                pre.executeUpdate();
            } else {
                sql = "insert tob_farm(owner,farmId,treeId,timeBegin,lv,buy) values(" + s.ownerID + "," + s.idFarm + "," + s.idTemplate + "," + s.timeLive + "," + s.lvTree + "," + s.buy + ")";
                pre = conn.prepareStatement(sql);
                pre.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
    }

    public void getFarm(Char p, Connection conn) {
        String sql = "";
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            sql = "select * from tob_farm WHERE owner=" + p.charDBID;
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            boolean have = false;
            while (rs.next()) {
                have = true;
                SeedItem s = new SeedItem(rs.getInt("farmID"), rs.getInt("treeId"), rs.getByte("buy"));
                s.ownerID = rs.getInt("owner");
                s.idDB = rs.getInt("id");
                s.timeLive = rs.getLong("timeBegin");
                s.lvPlot = rs.getByte("lvPlot");
                s.rankTree = rs.getByte("rank");
                p.seedsItem.add(s);
            }
            if (!have) {
                for (int i = 0; i < Map.MAX_FARM; ++i) {
                    SeedItem s2 = new SeedItem(i, -1, (byte) 0);
                    s2.ownerID = p.charDBID;
                    p.seedsItem.add(s2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
    }

    private void saveSpecialItem(Connection conn, ShopTemplate item) throws SQLException {
        PreparedStatement pre = null;
        if (item.dbownerId == 0) {
            final String sql = "INSERT tob_gemitem(owner,place,gemtemplate,typeItem) VALUES (?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, item.ownerId);
            pre.setInt(2, item.place);
            pre.setInt(3, item.id);
            pre.setInt(4, 1);
            pre.execute();
        } else {
            final String sql = "UPDATE tob_gemitem SET owner=?,place=?,gemtemplate=? WHERE id=?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, item.ownerId);
            pre.setInt(2, item.place);
            pre.setInt(3, item.id);
            pre.setInt(4, item.dbid);
            pre.execute();
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        item.dbownerId = item.ownerId;
    }

    public void saveFriendList(Char p) {
        PreparedStatement pre = null;
        Connection conn = null;
        try {
            StringBuilder frInfo = new StringBuilder();
            StringBuilder frInfoName = new StringBuilder();
            if (p.friendListID.size() > 0) {
                frInfo.append(p.friendListID.elementAt(0));
                frInfoName.append(p.friendListName.elementAt(0));
                for (int i = 1; i < p.friendListID.size(); ++i) {
                    frInfo.append(",").append(p.friendListID.get(i));
                    frInfoName.append(",").append(p.friendListName.get(i));
                }
            }
            String sql = "update tob_friendlist set friendId=?,name=?,ischange=1 where charId=?";
            conn = this.getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, frInfo.toString());
            pre.setString(2, frInfoName.toString());
            pre.setInt(3, p.charDBID);
            if (!pre.execute()) {
                sql = "insert tob_friendlist(charId,friendId,name) values(?,?,?)";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, p.charDBID);
                pre.setString(2, frInfo.toString());
                pre.setString(3, frInfoName.toString());
                pre.execute();
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveItemBackup(Connection conn, Item item, boolean insert) {
    }

    public void getListMemJoinWedding() {
        String[] typeWedding = {"don gian", "Nao nhiet", "hoanh trang"};
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_wedding";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Wedding wd = new Wedding();
                wd.idP1 = rs.getInt("idhusband");
                wd.idP2 = rs.getInt("idwife");
                String sql2 = "select * from tob_char where id=" + wd.idP1 + " or id=" + wd.idP2;
                ResultSet rs2 = null;
                Statement stm2 = null;
                stm2 = conn.createStatement();
                rs2 = stm2.executeQuery(sql2);
                StringBuilder info = new StringBuilder();
                while (rs2.next()) {
                    info.append(rs2.getString("charname")).append("_");
                }
                if (!info.toString().equals("")) {
                    info.append(typeWedding[rs.getByte("typeparty")]);
                    Logger.logString(info.toString(), "charWedding.txt");
                }
                rs2.close();
                stm2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public Hashtable<Integer, Wedding> loadWedding() {
        Hashtable<Integer, Wedding> wedding = new Hashtable<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        try {
            conn = this.getConnection();
            final String sql = "select * from tob_wedding where married=0";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Wedding wd = new Wedding();
                wd.idParty = rs.getInt("idwedding");
                wd.idP1 = rs.getInt("idhusband");
                wd.idP2 = rs.getInt("idwife");
                wd.action = rs.getByte("married");
                wd.time = rs.getByte("timeParty");
                wd.dayParty = rs.getString("dayparty");
                wd.typeParty = rs.getByte("typeparty");
                String day = Char.getDayOpen(0L);
                if (!day.equals(wd.dayParty)) {
                    this.updateStatusWedding(wd.idParty, 1);
                } else {
                    wedding.put(wd.idParty, wd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                stm.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            stm.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
       
        return wedding;
    }

    public void updateStatusWedding(int idWedding, int status) {
        PreparedStatement pre = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "update tob_wedding set married=" + status + " where idwedding=" + idWedding;
            pre = conn.prepareStatement(sql);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

   

    public void saveWedding(Char p1, Char p2, byte time, String day, byte type) {
        PreparedStatement pre = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            final String sql = "INSERT tob_wedding(idhusband,idwife,timeParty,dayparty,typeparty,idwedding) VALUES(?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.setInt(1, (p1.gender == 1) ? p1.charDBID : p2.charDBID);
            pre.setInt(2, (p2.gender == 2) ? p2.charDBID : p1.charDBID);
            pre.setByte(3, time);
            pre.setString(4, day);
            pre.setByte(5, type);
            pre.setInt(6, p1.idWedding);
            pre.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveItem(Item item) {
    }

    private void saveItem(Connection conn, Item item) {
        String att = item.getAttribute();
        String basic = item.getDBInfo();
        PreparedStatement pre = null;
        try {
            if (item.dbowner == 0) {
                final String sql = "INSERT tob_item(owner,allAtt,dbbasic,place,item_template,class,plus,timeLoan,atb0,atb1,atb2,atb3) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, item.owner);
                pre.setString(2, att);
                pre.setString(3, basic);
                pre.setInt(4, item.place);
                pre.setInt(5, item.getTemplate().id);
                pre.setInt(6, item.clazz);
                pre.setInt(7, item.plus);
                pre.setLong(8, item.timeLoan);
                pre.setInt(9, item.identify);
                pre.setInt(10, item.switchAttNguhanh);
                pre.setInt(11, item.resetItem);
                pre.setInt(12, item.slotWearing);
                pre.execute();
                item.dbowner = item.owner;
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                item.dbid = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
                this.saveItemBackup(conn, item, true);
            } else {
                final String sql = "UPDATE tob_item SET owner=?,allAtt=?,dbbasic=?,place=?,item_template=?,class=?,plus=?,timeLoan=?,atb0=?,atb1=?,atb2=?,atb3=? WHERE id=?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, item.owner);
                pre.setString(2, att);
                pre.setString(3, basic);
                pre.setInt(4, item.place);
                pre.setInt(5, item.getTemplate().id);
                pre.setInt(6, item.clazz);
                pre.setInt(7, item.plus);
                pre.setLong(8, item.timeLoan);
                pre.setInt(9, item.identify);
                pre.setInt(10, item.switchAttNguhanh);
                pre.setInt(11, item.resetItem);
                pre.setInt(12, item.slotWearing);
                pre.setInt(13, item.dbid);
                pre.execute();
                this.saveItemBackup(conn, item, false);
            }
            try {
                pre.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.dbowner = item.owner;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetChar(int headStyle, int charDBID, int classID, int gender, String charName, int lastLv, Connection conn) {
        PreparedStatement pre = null;
        try {
            CharTemplate ct;
            if (gender != -1) {
                ct = Database.charTemplates[classID][gender - 1];
            } else {
                ct = Database.charTemplatesOld[classID];
            }
            StringBuilder potion = new StringBuilder("0," + ct.nHP1 + ",0,0," + ct.nMP1 + ",0,0");
            StringBuilder skill = new StringBuilder("0,0,0,0,0,0,0");
            ct.strength = Database.basicPoint[classID][0];
            ct.agitity = Database.basicPoint[classID][1];
            ct.spirit = Database.basicPoint[classID][2];
            ct.dextrity = Database.basicPoint[classID][3];
            ct.luck = Database.basicPoint[classID][4];
            String basic = ct.strength + "," + ct.agitity + "," + ct.spirit + "," + ct.dextrity + "," + ct.luck + ",0,0";
            for (int i = 7; i < 25; ++i) {
                potion.append(",0");
                if (i < 15) {
                    skill.append(",0");
                }
            }
            int maxhp = 0;
            int maxmp = 0;
            switch (classID) {
                case 0: {
                    maxhp = ct.dextrity * 80;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 1:
                case 4:
                case 3: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 2: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 50;
                    break;
                }
            }
            String pInfo = headStyle + "," + gender + "," + charName + "," + classID + ",0,1," + maxhp + "," + maxmp + ",0,0,0,0,0,0,0,0,0";
            final String sql = "UPDATE tob_char SET pInfo=?,basic=?,skill=?,potion=?,totalTimePlay=0,luong=0,lastLv=1,del=1,tDelChar=0, gold=0,xp=0 where id=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, pInfo);
            pre.setString(2, basic);
            pre.setString(3, skill.toString());
            pre.setString(4, potion.toString());
            pre.setInt(5, charDBID);
            pre.execute();
            int aoTemplate = (ct.gender == 1) ? 1 : 2;
            int quanTemplate = (ct.gender == 1) ? 27 : 28;
            int[] defaultVK = {79, 86, 93, 100, 107, 79, 86, 93, 100, 107};
            int vkTemplate = defaultVK[classID];
            Item ao = new Item(Map.itemTemplates.get(classID).get(aoTemplate), false, classID, classID, aoTemplate);
            Item quan = new Item(Map.itemTemplates.get(classID).get(quanTemplate), false, classID, classID, quanTemplate);
            Item vk = new Item(Map.itemTemplates.get(classID).get(vkTemplate), false, classID, classID, vkTemplate);
            ao.place = 1;
            quan.place = 1;
            vk.place = 1;
            this.saveItem(conn, ao);
            this.saveItem(conn, quan);
            this.saveItem(conn, vk);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
    }

    public void delChar(int charid) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "update tob_char set ban=1 where id=" + charid;
            stmt.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addBackupChar(Connection conn, String sql) {
        Statement st = null;
        try {
            st = conn.createStatement();
            st.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                st.close();
            } catch (Exception ignored) {
            }
        }
        try {
            st.close();
        } catch (Exception ignored) {
        }
    }

    public int addChar(Session s, String charname, int headStyle, int classID, int gender, int nation) {
        int userID = s.userID;
        if (gender + 1 < 1 || gender + 1 > 2) {
            return -1;
        }
        int charId = -1;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            if (stmt.execute(sql)) {
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    try {
                        stmt.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        Database.connPool.free(conn);
                    } catch (Exception ignored) {
                    }
                    return -1;
                }
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sql = "SELECT * FROM tob_char WHERE userId=" + userID + " and idserver=" + s.idServer + " and ban=0";
            if (TeamServer.isServerLienDau()) {
                sql = "SELECT * FROM tob_char WHERE userId=" + userID + " and ban=0";
            }
            boolean first = true;
            int countChar = 0;
            try {
                ResultSet rs2 = stmt.executeQuery(sql);
                while (rs2.next()) {
                    first = false;
                    countChar++;
                }
                try {
                    rs2.close();
                } catch (Exception ignored) {
                }
            } catch (Exception ignored) {
            }
            CharTemplate ct;
            if (gender != -1) {
                ct = Database.charTemplates[classID][gender];
            } else {
                ct = Database.charTemplatesOld[classID];
            }
            int[] tempPotion = new int[162];
            tempPotion[4] = (tempPotion[1] = 1);
            long xu = 0L;
            StringBuilder potion = new StringBuilder("0," + ct.nHP1 + ",0,0," + ct.nMP1 + ",0,0");
            if (first) {
                if (s.privateProvider.equals("1") && s.bigProvider.equals("1")) {
                    xu = 100000L;
                } else if (s.bigProvider.equals("0")) {
                    xu = 100000L;
                }
            }
            if (countChar >= 1) { // táº¡o nhÃ¢n váº­t
                return -1;
            }
            potion = new StringBuilder(String.valueOf(xu));
            for (int i = 1; i < tempPotion.length; ++i) {
                potion.append(",").append(tempPotion[i]);
            }
            final String stSkill = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
            int[] cSkill = {6, 6, 8, 6, 6};
            String[] data = Char.split(stSkill, ",");
            int[] mainSkill = new int[15];
            for (int j = 0; j < data.length; ++j) {
                mainSkill[j] = -1;
                try {
                    if (j < cSkill[classID]) {
                        mainSkill[j] = Byte.parseByte(data[j]);
                    }
                } catch (Exception ignored) {
                }
            }
            StringBuilder skill = new StringBuilder(String.valueOf(mainSkill[0]));
            ct.strength = Database.basicPoint[classID][0];
            ct.agitity = Database.basicPoint[classID][1];
            ct.spirit = Database.basicPoint[classID][2];
            ct.dextrity = Database.basicPoint[classID][3];
            ct.luck = Database.basicPoint[classID][4];
            String basic = ct.strength + "," + ct.agitity + "," + ct.spirit + "," + ct.dextrity + "," + ct.luck + ",0,0";
            for (int k = 7; k < 25; ++k) {
                potion.append(",0");
                if (k < 21) {
                    skill.append(",").append(mainSkill[k - 7]);
                }
            }
            potion.append(",0,0,0,0,0,0,0,0,0,0,0,0,0");
            int maxhp = 0;
            int maxmp = 0;
            switch (classID) {
                case 0: {
                    maxhp = ct.dextrity * 80;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 1:
                case 4:
                case 3: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 2: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 50;
                    break;
                }
            }
      
            ct.hp = maxhp;
            ct.mp = maxmp;
            int aoTemplate = (ct.gender == 1) ? 1 : 2;
            int quanTemplate = (ct.gender == 1) ? 27 : 28;
            int[] defaultVK = {79, 86, 93, 100, 107, 79, 86, 93, 100, 107};
            int vkhi = defaultVK[classID];
            String pInfo = ct.getDbInfo(headStyle, gender + 1, charname, classID, maxhp, maxmp, 105, nation);
            int idAo = Map.itemTemplates.get(classID).get(aoTemplate).id;
            int quanB = Map.itemTemplates.get(classID).get(quanTemplate).id;
            int vukhi = Map.itemTemplates.get(classID).get(vkhi).id;
            int vkTemplate = defaultVK[classID];
            Item ao = new Item(Map.itemTemplates.get(classID).get(aoTemplate), false, classID, classID, aoTemplate);
            Item quan = new Item(Map.itemTemplates.get(classID).get(quanTemplate), false, classID, classID, quanTemplate);
            Item vk = new Item(Map.itemTemplates.get(classID).get(vkTemplate), false, classID, classID, vkTemplate);
            ao.place = 1;
            quan.place = 1;
            vk.place = 1;
            String equip = ao.getInfoSave() + ">" + quan.getInfoSave() + ">" + vk.getInfoSave();
            String infoTop = nation + "," + idAo + "," + classID + ",1,0," + quanB + "," + classID + ",1,0," + vukhi + "," + classID + ",1,0";
            sql = "INSERT INTO tob_char(charname,userId,headStyle,class,pInfo,potion,skill,basic,gender,infotop,gold,equip,idserver) VALUES('" + charname + "'," + userID + "," + headStyle + "," + classID + ",'" + pInfo + "','" + potion + "','" + skill + "','" + basic + "'," + (gender + 1) + ",'" + infoTop + "'," + xu + ",'" + equip + "'," + s.idServer + ")";
            stmt.execute(sql);
            sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            if (stmt.execute(sql)) {
                ResultSet rs3 = stmt.getResultSet();
                if (rs3.next()) {
                    charId = rs3.getInt("id");
                }
                try {
                    rs3.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String sqlCopy = "INSERT INTO tob_char2(id,charname,userId,headStyle,class,pInfo,potion,skill,basic,gender) VALUES(" + charId + ",'" + charname + "'," + userID + "," + headStyle + "," + classID + ",'" + pInfo + "','" + potion + "','" + skill + "','" + basic + "'," + (gender + 1) + ")";
                this.addBackupChar(conn, sqlCopy);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        return charId;
    }

    public void delQuest(int charDBID) {
        Connection conn = null;
        Statement stmt = null;
        CharQuest cq = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "delete FROM top_char_quest WHERE id_char=" + charDBID;
            stmt.execute(sql);
        } catch (Exception ignored) {
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void deleteItem2(Connection conn, int dbid) {
    }

    public void deleteItem(int dbid) {
    }

    public void deletePet(int dbid) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "DELETE FROM tob_pet where id=" + dbid;
            stmt.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void deleteGemItem(int dbid) {
    }

  

    public static Vector<String> readText(String st) {
        String test = null;
        InputStream is = null;
        try {
            is = new FileInputStream(st);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        Vector<String> listWord = new Vector<>();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        test = "";
        try {
            int ch;
            while ((ch = isr.read()) > -1) {
                if (ch == 13) {
                    listWord.addElement(test);
                    System.out.println(test);
                    test = "";
                } else {
                    if (ch == 10) {
                        continue;
                    }
                    test = test + (char) ch;
                }
            }
            if (!listWord.contains(test)) {
                listWord.add(test);
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        System.err.println(test);
        if (!test.equals("") && !test.equals(" ") && !listWord.contains(test)) {
            listWord.add(test);
            System.out.println(test);
        }
        for (int i = 0; i < listWord.size(); ++i) {
            CharInfo c = new CharInfo();
            try {
                c.name = listWord.get(i);
                c.money = i;
                Database.all_char_top.add(c);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        System.out.println("TONG SO CHAR TOP " + listWord.size() + " > " + Database.all_char_top.size());
        return listWord;
    }

    public static Vector<String> cloneVectorString(Vector<String> v) {
        int size = v.size();
        int i = 0;
        Vector<String> data = new Vector<>();
        while (i < size) {
            try {
                data.add(v.remove(0));
            } catch (Exception ignored) {
            }
            ++i;
        }
        return data;
    }

 

   

        public static void checkDetailGems(String gemIds, String quantity, String compareType, 
                                    String charNameSearch, String fileName, String gemType) {
        StringBuilder result = new StringBuilder();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new Database().getConnection();
            String sql = "SELECT t1.owner, t1.listtemplate, t1.soluong, t1.slock, t2.charname " +
                        "FROM tob_gem_new t1 " +
                        "LEFT JOIN tob_char t2 ON t1.owner = t2.id";
            
            if (charNameSearch != null && !charNameSearch.isEmpty()) {
                sql += " WHERE t2.charname LIKE ?";
            }
            
            stmt = conn.prepareStatement(sql);
            
            if (charNameSearch != null && !charNameSearch.isEmpty()) {
                stmt.setString(1, "%" + charNameSearch + "%");
            }
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                int owner = rs.getInt("owner");
                String charName = rs.getString("charname");
                String[] templates = rs.getString("listtemplate").split(",");
                String[] amounts = rs.getString("soluong").split(",");
                String[] slocks = rs.getString("slock").split(",");

                boolean foundMatch = false;
                for (int i = 0; i < templates.length; i++) {
                    int gemId = Integer.parseInt(templates[i]);
                    int amount;
                    
                    // XÃ¡c Ä‘á»‹nh sá»‘ lÆ°á»£ng dá»±a vÃ o loáº¡i gem cáº§n kiá»ƒm tra
                    if (gemType.equals("soluong")) {
                        amount = Integer.parseInt(amounts[i]);
                    } else if (gemType.equals("slock")) {
                        amount = Integer.parseInt(slocks[i]);
                    } else { // both
                        amount = Integer.parseInt(amounts[i]) + Integer.parseInt(slocks[i]);
                    }

                    if (gemIds.isEmpty() || gemIds.contains(String.valueOf(gemId))) {
                        boolean matchCondition = false;
                        int targetQuantity = Integer.parseInt(quantity);
                        
                        switch (compareType) {
                            case "greater":
                                matchCondition = amount >= targetQuantity;
                                break;
                            case "less":
                                matchCondition = amount <= targetQuantity;
                                break;
                            case "equal":
                                matchCondition = amount == targetQuantity;
                                break;
                        }

                        if (matchCondition) {
                            if (!foundMatch) {
                                result.append("\n=== Character: ").append(charName)
                                    .append(" (ID: ").append(owner).append(") ===\n");
                                foundMatch = true;
                            }
                            result.append(Map.gemTemplate[gemId].name)
                                .append(" (ID: ").append(gemId).append("): ")
                                .append(amount);
                            
                            // ThÃªm chi tiáº¿t vá» gem khÃ³a/má»Ÿ
                            if (gemType.equals("both")) {
                                result.append(" (Má»Ÿ: ").append(amounts[i])
                                    .append(", KhÃ³a: ").append(slocks[i]).append(")");
                            }
                            result.append("\n");
                        }
                    }
                }
            }

            
            // LÆ°u káº¿t quáº£ ra file
            if (fileName != null && !fileName.isEmpty()) {
                Logger.logString(result.toString(), fileName + ".txt");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

 

   
    public void setCharReceiveOffline() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Vector<String> allchar = readText("offline.txt");
            conn = this.getConnection();
            for (String s : allchar) {
                try {
                    String sql = "insert into tob_qua(charname,qua) values('" + s + "',0)";
                    stmt = conn.createStatement();
                    stmt.execute(sql);
                    try {
                        stmt.close();
                    } catch (Exception ignored) {
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.close(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.close(conn);
        } catch (Exception ignored) {
        }
    }

    public void updateNameMonsterIndo() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            for (int i = 0; i < Database.nameItem_indo.length; ++i) {
                conn = this.getConnection();
                String sql = "update data_attribute set namein='" + Database.name_att_in[i] + "' where id=" + i;
                stmt = conn.createStatement();
                stmt.execute(sql);
                try {
                    stmt.close();
                } catch (Exception ignored) {
                }
                try {
                    Database.connPool.close(conn);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void saveCharLienDau(Char p, int[] infoCharLiendau) {
        if (p == null) {
            return;
        }
        if (p.isBot != -1) {
            return;
        }
        int charid = infoCharLiendau[0];
        int totalPlay = (int) (System.currentTimeMillis() - p.timePlay) / 60000;
        StringBuilder potion = new StringBuilder(String.valueOf(p.getxu()));
        StringBuilder skill = new StringBuilder(String.valueOf(p.skill[0]));
        String basic = String.valueOf(p.strength) + "," + p.agitity + "," + p.spirit + "," + p.health + "," + p.luck + "," + p.basepoint + "," + p.skillpoint;
        for (int i = 1; i < p.potions.length; ++i) {
            potion.append(",").append(p.potions[i]);
        }
        for (int i = 1; i < p.skill.length; ++i) {
            skill.append(",").append(p.skill[i]);
        }
        for (int i = 0; i < p.skillClan.length; ++i) {
            skill.append(",").append(p.skillClan[i]);
        }
        for (int i = 0; i < p.skillLvClan.length; ++i) {
            skill.append(",").append(p.skillLvClan[i]);
        }
        String dbInfo = p.getDBInfo();
        StringBuilder equip = new StringBuilder();
        StringBuilder invent = new StringBuilder();
        StringBuilder bag = new StringBuilder();
        try {
            Vector<Item> wearing = new Vector<>();
            wearing = p.getCharWearing(0);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.getCharWearing(1);
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            wearing = p.awItems;
            for (Item item : wearing) {
                equip.append(item.getInfoSave()).append(">");
            }
            if (!equip.toString().equals("")) {
                equip = new StringBuilder(equip.substring(0, equip.length() - 1));
            }
        } catch (Exception ignored) {
        }
        try {
            if (p.itemHoaKyLan != null) {
                invent.append(p.itemHoaKyLan.getInfoSave()).append(">");
            }
            if (p.itemVukhiThoiTrang != null) {
                invent.append(p.itemVukhiThoiTrang.getInfoSave()).append(">");
            }
            for (Item item2 : p.iItems) {
                invent.append(item2.getInfoSave()).append(">");
            }
            if (!invent.toString().equals("")) {
                invent = new StringBuilder(invent.substring(0, invent.length() - 1));
            }
            String infomodel = p.wModel.getInfoModelWearing();
            if (!infomodel.equals("")) {
                invent.append(">").append(infomodel);
            }
        } catch (Exception ignored) {
        }
        try {
            for (Item item2 : p.bItem) {
                bag.append(item2.getInfoSave()).append(">");
            }
            if (!bag.toString().equals("")) {
                bag = new StringBuilder(bag.substring(0, bag.length() - 1));
            }
        } catch (Exception ignored) {
        }
        Connection conn = null;
        PreparedStatement pre = null;
        final String sql = "UPDATE tob_char SET pInfo=?,basic=?,skill=?,potion=?,totalTimePlay=totalTimePlay+?, luong=?, lastLv=?,xp=?,gold=?,hp=?, idClan=?, reupclass=?,headStyle=?,ticket=?,x=?,y=?,book=?,lastLog=Now(),infotop=?,equip=?,inven=?,bag=?,killme=?,luonglock=? where id=?";
        try {
            conn = this.getConnection1();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dbInfo);
            pre.setString(2, basic);
            pre.setString(3, skill.toString());
            pre.setString(4, potion.toString());
            pre.setLong(5, totalPlay);
            pre.setInt(6, Math.max(p.getLuong(), 0));
            pre.setInt(7, p.lastLV);
            pre.setLong(8, p.lvDetail.getExp());
            try {
                pre.setLong(9, p.getxu());
            } catch (Exception e) {
                e.printStackTrace();
            }
            pre.setInt(10, p.health);
            pre.setInt(11, p.idClan);
            pre.setInt(12, p.reclass);
            pre.setInt(13, p.headStyle);
            pre.setInt(14, p.flower);
            pre.setInt(15, p.gotWages ? 1 : 0);
            pre.setInt(16, p.timeXP);
            pre.setInt(17, p.gifMoonFestival ? 20 : 0);
            pre.setString(18, p.infoWearing);
            pre.setString(19, equip.toString());
            pre.setString(20, invent.toString());
            pre.setString(21, bag.toString());
            pre.setString(22, p.getSaveListKillMe());
            pre.setInt(23, p.getLuongLock());
            pre.setInt(24, charid);
            try {
                pre.execute();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("LOI excute char " + e);
            }
            try {
                if (charid > 0) {
                    StringBuilder soluong = new StringBuilder(String.valueOf(p.listGemitem[0]));
                    for (int k = 1; k < p.listGemitem.length; ++k) {
                        soluong.append(",").append(p.listGemitem[k]);
                    }
                    StringBuilder soluong2 = new StringBuilder(String.valueOf(p.listGemitemLock[0]));
                    for (int l = 1; l < p.listGemitemLock.length; ++l) {
                        soluong2.append(",").append(p.listGemitemLock[l]);
                    }
                    this.saveCharGem1(soluong.toString(), charid, soluong2.toString());
                }
            } catch (Exception ignored) {
            }
            try {
                for (int m = 0; m < p.animal.size(); ++m) {
                    Animal gem = p.animal.get(m);
                    if (gem.ownerId == p.charDBID) {
                        gem = gem.clone(charid);
                        if (infoCharLiendau[1] > -1) {
                            gem.dbownerId = gem.ownerId;
                        }
                        this.saveAnimal1(conn, gem, p.animal.get(m).dbid + "_" + Database.nameServer[TeamServer.server]);
                    }
                }
            } catch (Exception ignored) {
            }
            try {
                if (charid > 0) {
                    StringBuilder soluong = new StringBuilder(String.valueOf(p.listSpItem[0]));
                    for (int k = 1; k < p.listSpItem.length; ++k) {
                        soluong.append(",").append(p.listSpItem[k]);
                    }
                    this.saveCharSP1(soluong.toString(), charid);
                }
            } catch (Exception ignored) {
            }
            try {
                for (int m = 0; m < p.pet.size(); ++m) {
                    Pet gem2 = p.pet.get(m);
                    if (gem2.ownerid == p.charDBID) {
                        gem2 = gem2.clone(charid);
                        this.savePet1(gem2, p.pet.get(m).idDB + "_" + Database.nameServer[TeamServer.server]);
                    }
                }
            } catch (Exception ignored) {
            }
            this.saveCharQuest1(charid, p.getInfoQuestSave());
            this.saveCharFruit1(charid, p.getInfoSaveFruit());
            this.saveCharTubinh1(charid, p.getInfoSaveTubinh());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        ++TeamServer.update;
    }

    public void doUpdateReceiveGiftCT(String charname) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String name = charname + "_" + Database.nameServer[TeamServer.server];
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "update tob_qua_ct set nhan=1, tlog=now() WHERE charname='" + name + "'";
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
    }

    public void doUpdateReceiveGiftTopCT(String charname, int type) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String name = charname + "_" + Database.nameServer[TeamServer.server];
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "update tob_qua_ct_top set nhan=1, tlog=now() WHERE charname='" + name + "' and soluong=" + type;
            pre.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
    }

    public int getCharWinLiendau(String name) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String charname = name + "_" + Database.nameServer[TeamServer.server];
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "SELECT * FROM tob_qua_ct WHERE charname='" + charname + "' and nhan=0";
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("soluong");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int getCharTopLiendau(String name, int type) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String charname = name + "_" + Database.nameServer[TeamServer.server];
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "SELECT * FROM tob_qua_ct_top WHERE charname='" + charname + "' and nhan=0 and soluong=" + type;
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("soluong");
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return -1;
    }

    public boolean isCharRegisterLiendau(String name) {
        Statement pre = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String charname = name + "_" + Database.nameServer[TeamServer.server];
            conn = this.getConnection1();
            pre = conn.createStatement();
            String sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            rs = pre.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        return false;
    }

    public InfoCharLienDau addChar1(Session s, String charname, int headStyle, int classID, int gender, int nation) {
        InfoCharLienDau info = new InfoCharLienDau();
        charname = charname + "_" + Database.nameServer[TeamServer.server];
        info.name = charname;
        int userID = s.userID;
        --gender;
        System.out.println("CHUAN BI DANG KY LIEN DAU " + charname);
        int charId = -1;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.getConnection1();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            if (stmt.execute(sql)) {
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    charId = rs.getInt("id");
                    try {
                        stmt.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        Database.connPool1.free(conn);
                    } catch (Exception ignored) {
                    }
                    System.out.println("DA CO ROI");
                    info.data = new int[]{charId, charId};
                    return info;
                }
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sql = "SELECT * FROM tob_char WHERE userId=" + userID;
            final boolean first = false;
            try {
                ResultSet rs2 = stmt.executeQuery(sql);
                if (rs2.next()) {
                    charId = rs2.getInt("id");
                    try {
                        stmt.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        rs2.close();
                    } catch (Exception ignored) {
                    }
                    try {
                        Database.connPool1.free(conn);
                    } catch (Exception ignored) {
                    }
                    System.out.println("DA CO USERID ROi: " + charId);
                    info.data = new int[]{charId, charId};
                    return info;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            CharTemplate ct;
            if (gender != -1) {
                ct = Database.charTemplates[classID][gender];
            } else {
                ct = Database.charTemplatesOld[classID];
            }
            int[] tempPotion = new int[162];
            tempPotion[4] = (tempPotion[1] = 1);
            long xu = 0L;
            StringBuilder potion = new StringBuilder("0," + ct.nHP1 + ",0,0," + ct.nMP1 + ",0,0");
            if (first) {
                if (s.privateProvider.equals("1") && s.bigProvider.equals("1")) {
                    xu = 100000L;
                } else if (s.bigProvider.equals("0")) {
                    xu = 100000L;
                }
            }
            potion = new StringBuilder(String.valueOf(xu));
            for (int i = 1; i < tempPotion.length; ++i) {
                potion.append(",").append(tempPotion[i]);
            }
            final String stSkill = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
            int[] cSkill = {6, 6, 8, 6, 6};
            String[] data = Char.split(stSkill, ",");
            int[] mainSkill = new int[15];
            for (int j = 0; j < data.length; ++j) {
                mainSkill[j] = -1;
                try {
                    if (j < cSkill[classID]) {
                        mainSkill[j] = Byte.parseByte(data[j]);
                    }
                } catch (Exception ignored) {
                }
            }
            StringBuilder skill = new StringBuilder(String.valueOf(mainSkill[0]));
            ct.strength = Database.basicPoint[classID][0];
            ct.agitity = Database.basicPoint[classID][1];
            ct.spirit = Database.basicPoint[classID][2];
            ct.dextrity = Database.basicPoint[classID][3];
            ct.luck = Database.basicPoint[classID][4];
            String basic = ct.strength + "," + ct.agitity + "," + ct.spirit + "," + ct.dextrity + "," + ct.luck + ",0,0";
            for (int k = 7; k < 25; ++k) {
                potion.append(",0");
                if (k < 21) {
                    skill.append(",").append(mainSkill[k - 7]);
                }
            }
            potion.append(",0,0,0,0,0,0,0,0,0,0,0,0,0");
            int maxhp = 0;
            int maxmp = 0;
            switch (classID) {
                case 0: {
                    maxhp = ct.dextrity * 80;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 1:
                case 4:
                case 3: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 20;
                    break;
                }
                case 2: {
                    maxhp = ct.dextrity * 60;
                    maxmp = ct.spirit * 50;
                    break;
                }
            }
 
            ct.hp = maxhp;
            ct.mp = maxmp;
            int aoTemplate = (ct.gender == 1) ? 1 : 2;
            int quanTemplate = (ct.gender == 1) ? 27 : 28;
            int[] defaultVK = {79, 86, 93, 100, 107, 79, 86, 93, 100, 107};
            int vkhi = defaultVK[classID];
            String pInfo = ct.getDbInfo(headStyle, gender + 1, charname, classID, maxhp, maxmp, 105, nation);
            int idAo = Map.itemTemplates.get(classID).get(aoTemplate).id;
            int quanB = Map.itemTemplates.get(classID).get(quanTemplate).id;
            int vukhi = Map.itemTemplates.get(classID).get(vkhi).id;
            int vkTemplate = defaultVK[classID];
            Item ao = new Item(Map.itemTemplates.get(classID).get(aoTemplate), false, classID, classID, aoTemplate);
            Item quan = new Item(Map.itemTemplates.get(classID).get(quanTemplate), false, classID, classID, quanTemplate);
            Item vk = new Item(Map.itemTemplates.get(classID).get(vkTemplate), false, classID, classID, vkTemplate);
            ao.place = 1;
            quan.place = 1;
            vk.place = 1;
            String equip = ao.getInfoSave() + ">" + quan.getInfoSave() + ">" + vk.getInfoSave();
            String infoTop = nation + "," + idAo + "," + classID + ",1,0," + quanB + "," + classID + ",1,0," + vukhi + "," + classID + ",1,0";
            sql = "INSERT INTO tob_char(charname,userId,headStyle,class,pInfo,potion,skill,basic,gender,infotop,gold,equip,idserver) VALUES('" + charname + "'," + userID + "," + headStyle + "," + classID + ",'" + pInfo + "','" + potion + "','" + skill + "','" + basic + "'," + (gender + 1) + ",'" + infoTop + "'," + xu + ",'" + equip + "'," + s.idServer + ")";
            System.out.println(sql);
            System.out.println("THUC THI CAU LENH: " + stmt.execute(sql));
            sql = "SELECT * FROM tob_char WHERE charname='" + charname + "'";
            if (stmt.execute(sql)) {
                ResultSet rs3 = stmt.getResultSet();
                if (rs3.next()) {
                    charId = rs3.getInt("id");
                    System.out.println("CHAR DANG KY THANH CONG: " + charId);
                }
                try {
                    rs3.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
        info.data = new int[]{charId, -1};
        return info;
    }

    public void saveCharQuest1(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection1();
            final String sql = "update tob_char_quest set itemget=?,monskilled=?,isfinish=?, itemquest=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setString(3, info[2]);
            stmt.setString(4, info[3]);
            stmt.setInt(5, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE QUEST ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharQuest1(int idDBChar, String[] info) {
        if (info == null) {
            return;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection1();
            final String sql = "insert into tob_char_quest(id_char,itemget,monskilled,isfinish) values(?,?,?,?) "
                    + "on duplicate key update itemget=values(itemget), monskilled=values(monskilled), isfinish=values(isfinish)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDBChar);
            stmt.setString(2, info[0]);
            stmt.setString(3, info[1]);
            stmt.setString(4, info[2]);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE QUEST ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void addCharTubinh1(int idDBChar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection1();
            final String sql = "insert into tob_char_tubinh(id_char,listid,listmanh) values(?,?,?) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDBChar);
            stmt.setString(2, "");
            stmt.setString(3, "");
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharFruit1(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection1();
            final String sql = "update tob_char_fruit set listidfruit=?,stFruit=?,tFruit=?,gift=?,theo=?,tkho=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setString(3, info[2]);
            stmt.setString(4, info[3]);
            stmt.setString(5, info[4]);
            stmt.setString(6, info[5]);
            stmt.setInt(7, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE FRUIT ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharGem1(String allGem, int chardbid, String allGemLock) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection1();
            StringBuilder listtemplate = new StringBuilder("0");
            this.saveCharGemBackup(conn, allGem, chardbid, allGemLock);
            for (int i = 1; i < Map.gemTemplate.length; ++i) {
                listtemplate.append(",").append(i);
            }
            final String sql = "insert tob_gem_new(owner,listtemplate,soluong,slock) values (?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, chardbid);
            pre.setString(2, listtemplate.toString());
            pre.setString(3, allGem);
            pre.setString(4, allGemLock);
            pre.execute();
        } catch (Exception e) {
            try {
                final String sql = "update tob_gem_new set soluong=?,slock=? where owner=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, allGem);
                pre.setString(2, allGemLock);
                pre.setInt(3, chardbid);
                pre.execute();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharTubinh1(int idDBChar, String[] info) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection1();
            final String sql = "update tob_char_tubinh set listid=?,listmanh=? where id_char=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info[0]);
            stmt.setString(2, info[1]);
            stmt.setInt(3, idDBChar);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI SAVE FRUIT ROI");
        } finally {
            try {
                stmt.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            stmt.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void saveCharSP1(String allGem, int chardbid) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = this.getConnection1();
            final String listtemplate = "0,1";
            final String sql = "insert tob_sp_new(owner,listtemplate,soluong) values (?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, chardbid);
            pre.setString(2, listtemplate);
            pre.setString(3, allGem);
            pre.execute();
        } catch (Exception e) {
            try {
                final String sql = "update tob_sp_new set soluong=? where owner=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, allGem);
                pre.setInt(2, chardbid);
                pre.execute();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool1.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool1.free(conn);
        } catch (Exception ignored) {
        }
    }

    public void savePet1(Pet gem, String iddbPrivate) {
        if (gem == null) {
            return;
        }
        Connection conn = null;
        PreparedStatement pre = null;
        Statement pre2 = null;
        try {
            conn = this.getConnection1();
            try {
                final String sql = "INSERT tob_pet(owner,place,info,idImg,name,itPet,idPrivateSv) VALUES (?,?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pre.setInt(1, gem.ownerid);
                pre.setInt(2, gem.place);
                pre.setString(3, gem.getDBInfo());
                pre.setInt(4, gem.idImg);
                pre.setString(5, gem.getNameSave());
                pre.setString(6, gem.getInfoItem());
                pre.setString(7, iddbPrivate);
                pre.execute();
                ResultSet rs = pre.getGeneratedKeys();
                rs.next();
                gem.idDB = rs.getInt(1);
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            } catch (Exception e) {
                String sql = "UPDATE tob_pet SET owner=" + gem.ownerid + ",place=" + gem.place + ",info='" + gem.getDBInfo() + "', idImg=" + gem.idImg + ",name='" + gem.getNameSave() + "',itPet='" + gem.getInfoItem() + "' WHERE idPrivateSv='" + iddbPrivate + "';";
                pre2 = conn.createStatement();
                pre2.execute(sql);
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logError(e, "errorAnima.txt");
        } finally {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
            try {
                pre.close();
            } catch (Exception ignored) {
            }
            try {
                pre2.close();
            } catch (Exception ignored) {
            }
        }
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
        try {
            pre.close();
        } catch (Exception ignored) {
        }
        try {
            pre2.close();
        } catch (Exception ignored) {
        }
    }

    

    public void addCharEvent83(String charname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into tob_top_event(charname,point1,timeupdate) values ('" + charname + "',1,now()) ON DUPLICATE KEY UPDATE point1=point1+1,timeupdate=now()";
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
        try {
            pstmt.close();
        } catch (Exception ignored) {
        }
        try {
            rs.close();
        } catch (Exception ignored) {
        }
        try {
            Database.connPool.free(conn);

        } catch (Exception ignored) {
        }
    }

    public class infoHack {

        public String charname;
        public int totalSell;
        public Vector<String> buy;

        public infoHack() {
            this.totalSell = 0;
            this.buy = new Vector<>();
        }
    }
}
