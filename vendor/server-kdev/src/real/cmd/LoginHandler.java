// 
// Decompiled by Procyon v0.6.0
// 
package real.cmd;

import real.Item;
import real.RegisterAttack;
import real.RealController;
import real.CharManager;
import java.io.IOException;
import java.io.DataInputStream;
import logNQSH.DBUserLogger;
import java.util.Date;
import java.text.SimpleDateFormat;
import data.Net;
import io.LogInData;
import io.LogInController;
import server.InfoClientConnect;
import real.MessageCreator;
import data.Database;
import io.SessionManager;
import real.AdminHandler;
import io.Session;
import real.Char;
import data.DataGame;
import data.ItemQuest;
import real.Map;

import server.TeamServer;
import real.BadWord;
import io.Message;
import data.Text;
import java.util.Vector;
import real.PotionTemplate2;

public class LoginHandler implements ICommandHandler {

    public static Vector<String> platform;
    public static String nickTest;
    private static LoginHandler instance;
    public static int versionCode;
    public static int versionTile;
    public static boolean stopLogin;
    public static String[] PORTION_NAME;
    public static String[] KHAM_NAME;

    public static String passAdmin;
    public static String KichHoatTaiKhoan = "Vui lòng kích hoạt tài khoản tại trang chủ ...";
   

    static {
        LoginHandler.platform = new Vector<String>();
        LoginHandler.nickTest = "lamkckc";
        LoginHandler.versionCode = 0;
        LoginHandler.versionTile = 0;
        LoginHandler.stopLogin = false;
        LoginHandler.PORTION_NAME = new String[]{"Xu", "HP nhỏ", "HP vừa", "HP to", "MP nhỏ", "MP vừa", "MP to", "Bình tăng tốc thường", "Bình tăng tốc vàng.\nGiảm 20% thời gian chín của trái vàng", "Nhân sâm", "Tiên dược thường: cộng 100k exp", "Tiên dược cao cấp: cộng 500k exp", "Tiên dược đặc biệt: cộng 1t exp", "Bình tăng tốc bạc.\nGiảm 20% thời gian chín của trái bạc", "Khăn đỏ", "Khăn xanh", "Khăn vàng", "Khăn xanh lá", "Khăn tím", "Thổ địa phù", "Vé tàu", "HP đ.biệt vừa", "HP đ.biệt to", "MP đ.biệt vừa", "MP đ.biệt to", "Thuốc khôi phục tiềm năng", "Thuốc khôi phục", "Thần hành phù", "Thẻ cấm chat", "Phiếu công ích", "Thiên lý mã", "Kim bài", "Thẻ đấu trường", "Thẻ chiếm thành", "Xích thố", "Vé giờ vàng 100, x2 kinh nghiệm trong một giờ chơi. Tính theo thời gian chơi", "Tuần lộc", "Đầu đinh", "Tóc búi", "Tóc cột cao", "Tóc chéo", "Tóc xù", "Tóc ngang vai", "Tóc đinh so le", "Tóc so le", "Tóc thư sinh", "Tóc cài hoa", "Tóc bờm ngựa", "Tóc tiên đồng", "Tóc dựng đứng", "Hoa mai", "Hoa mai", "Hoa mai", "Hoa mai", "Hoa đào", "Hoa đào", "Hoa đào", "Hoa đào", "Hoa hồng", "Hoa hồng", "Hoa hồng", "Hoa hồng", "Bí ma", "Hộp may mắn", "Hắc Ngưu", "Mãnh hổ", "Sói xám", "Tiên Hạc", "Bạch mã", "Vé quay số", "Trứng phượng hoàng băng", "Trứng Hoả kỳ lân", "Trứng đương khang", "Túi nhặt ngọc", "Trứng bạch cốt", "Vé giờ vàng 100, x2 kinh nghiệm trong 3 giờ chơi. Tính theo thời gian chơi", "Vé quay số đặc biệt", "Mảnh bản đồ 1", "Thẻ liên hoa", "Hộp thần kỳ", "Bình tăng lực 5%/n. Tăng 5% các chỉ số trong 3 ngày", "Vé giờ vàng tăng 150% kinh nghiệm.", "Thẻ vận tiêu. Thêm một lượt vận tiêu khi sử dụng", "Tụ hồn đan. Tăng 1% kinh nghiệm cho thú nuôi khi cho ăn.", "Thuốc cường hoá", "Thẻ triệu hồi", "Phượng hoàng", "Thẻ bài phong chức", "Nhẫn Bách niên giai lão 2%pt", "Nhẫn Vĩnh kết đồng tâm 4%pt", "Nhẫn Loan phụng hòa minh 7%pt", "Vé vào mỏ", "Thiệp cưới", "HP7k", "HP15k", "MP7k", "MP15k", "Lệnh bài đổi tiêu", "Phù phu thê", "Pháo hoa", "loa", "Rương vàng", "Rương bạc", "Lá vàng", "Dây thừng. Dùng để bắt thú nuôi", "Mảnh bản đồ 2", "", "", "", "", "", "", "Thức ăn tươi", "Thức ăn khô", "Thức ăn tổng hợp", "Trứng Lân sư tử", "Lệnh bài đổi tiêu cao cấp", "Mảnh bản đồ 3", "Vé phượng hoàng.\nDùng quay số phượng hoàng", "Tiên đan\nHồi sinh bản thân.", "Thịt", "Trứng", "Đậu xanh", "Thuốc cường hóa siêu cấp", "Bánh dẻo", "Bánh nướng", "Hp chiến trường.\nHồi 10.000 hp", "Đoạn cốt.\nHồi 10.000 mp", "Thuốc cường hoá chiến trường", "Hỗn độn.\nTăng 20% giáp trong 90s", "Hỗn Nguyên.\nTăng 20% sát thương.", "Hoá công.\nTăng 50% sát thương boss.", "Hoả dược. Gây sát thương theo x10% nội lực bản thân lên toàn bộ mục tiêu trong bán kính 5m.", "Hộp bánh thập cẩm", "Hộp bánh đặc biệt", "Hạt mầm", "Nến", "Kẹo", "Bí ngô", "Hộp quà", "Hộp quà halloween", "Hộp quà tặng halloween", "Bột mỳ", "Hộp quà bí mật", "Sao vàng", "Dưa Hấu", "Kem", "Túi quà may mắn", "Gạo nếp", "Thịt mỡ", "Lá dong", "Bánh chưng", "Bánh tét", "Giấy", "Thuốc pháo", "Pháo hoa tết", "Lửa thường", "Lửa vừa", "Lửa to", "Bao lì xì", "Quả bóng bạc", "Quả bóng vàng"};
        LoginHandler.KHAM_NAME = new String[]{"Tăng sức mạnh", "Tăng nhanh nhẹn", "Tăng tinh thần", "Tăng sức khoẻ", "Gây mù ", "Đóng băng ", "Trúng độc", "Gây choáng", "Hoá thạch", "Giảm tốc ", "Kháng giảm tốc", "Kháng trúng độc", "Kháng gây mù", "Kháng đóng băng", "Kháng gây choáng", "Kháng hoá thạch", "Tăng tỷ lệ x2 mỗi lần đánh", "Tăng tỷ lệ rớt bảo vật"};
       
        LoginHandler.passAdmin = "000000";
    }


    public static Message createMsgLogin(final int vsTile) {
        final Message msgLoginSuccess = new Message(1);
        try {
            msgLoginSuccess.dos.writeByte(4);
            msgLoginSuccess.dos.writeShort(15000);
            msgLoginSuccess.dos.writeByte(Map.potionTemplates2.size());
            for (PotionTemplate2 potion : Map.potionTemplates2) {
                msgLoginSuccess.dos.writeByte(potion.idImage);
                msgLoginSuccess.dos.writeUTF(potion.name);
                msgLoginSuccess.dos.writeUTF(potion.name2);
                msgLoginSuccess.dos.writeShort(potion.delay);
                msgLoginSuccess.dos.writeBoolean(potion.isTrade);
            }
            String[][] decript = null;
            String[][] decript2 = {{"Dùng sức mạnh của sấm sét tấn công nhiều mục tiêu trong 1 phạm vi nhất định"}, {"Dùng sức mạnh của rồng lửa tấn công nhiều mục tiêu trong 1 phạm vi nhất định", "Vận nội kình tạo ra liệt hỏa phát tán ra xung quanh, đốt cháy bất kỳ đối tượng nào trên đường đi của liệt hỏa."}, {"Dùng sức mạnh của rồng nước tấn công nhiều mục tiêu trong 1 phạm vi nhất định"}, {"Tạo ra những cơn địa chấn tấn công nhiều mục tiêu trong phạm vi nhất định"}, {"Tẩm cực độc vào mũi tên và triệt hạ nhiều mục tiêu cùng lúc"}};
            String[][] decript3 = {{"Vận nội lực tạo thành tám luồng sét đánh liên tiếp vào đối thủ.", "Dùng sức mạnh của sấm sét tấn công nhiều mục tiêu trong 1 phạm vi nhất định", " Dùng vũ khí làm cầu nối dẫn truyền sấm sét vào cơ thể đối thủ, tia sét sẽ di chuyển thành một chuỗi liên hoàn vào các đối tượng tập trung gần nhau.", "Vận kình lực tạo thành những thanh kiếm khổng lồ giáng từ trên trời xuống đối phương"}, {"Triệu hồi bầy rồng lửa tấn công mục tiêu trong phạm vi nhất định.", "Dùng sức mạnh của rồng lửa tấn công nhiều mục tiêu trong 1 phạm vi nhất định", "Vận nội kình tạo ra liệt hỏa phát tán ra xung quanh, đốt cháy bất kỳ đối tượng nào trên đường đi của liệt hỏa.", " Khuấy động bầu trời, tạo ra những cơn mưa sao băng trút xuống mặt đất"}, {"Triệu hồi bầy hải long tấn công mục tiêu trong phạm vi nhất định.", "Dùng sức mạnh của rồng nước tấn công nhiều mục tiêu trong 1 phạm vi nhất định", " Vận nội kình vào hai tay đánh ra hai con rồng theo hình trôn ốc, tạo nên một lực công phá khủng khiếp.", " Gọi gió hô mưa rồi truyền vào đó một luồng hàn băng cực mạnh tạo nên những trận mưa băng khủng khiếp"}, {"Vận sức mạnh tạo ra tám đợt xung kích tấn công đối thủ.", "Tạo ra những cơn địa chấn tấn công nhiều mục tiêu trong phạm vi nhất định", "Dùng búa tạo ra những trận mưa đá tảng tấn công tới tấp vào đối thủ.", "Giáng một búa mạnh xuống đất tạo nên những cột thạch nhũ lao lên khỏi mặt đất công kích đối phương"}, {"Bắn liên hoàn nhiều mũi tên vàng công kích mục tiêu.", "Tẩm cực độc vào mũi tên và triệt hạ nhiều mục tiêu cùng lúc", "Hất tung đối phương lên không trung và bắn liên hoàn năm mũi tên vào đối thủ dẫn đến tử vong.", "Mo ta skill moi"}};
        
            msgLoginSuccess.dos.writeByte(9);
            msgLoginSuccess.dos.writeByte(9);
            msgLoginSuccess.dos.writeByte(11);
            msgLoginSuccess.dos.writeByte(9);
            msgLoginSuccess.dos.writeByte(9);
            decript = decript3;
            for (int j = 0; j < 5; ++j) {
                msgLoginSuccess.dos.writeByte(Map.idSkill[j].length);
                for (int k = 0; k < Map.idSkill[j].length; ++k) {
                    msgLoginSuccess.dos.writeByte(Map.idSkill[j][k]);
                    msgLoginSuccess.dos.writeUTF(Text.nameSkillLearn[j][k]);
                    msgLoginSuccess.dos.writeUTF(decript[j][k]);
                    msgLoginSuccess.dos.writeInt(Map.priceLearnSkill[j][k]);
                }
            }
            msgLoginSuccess.dos.writeShort(Map.idMapDautruong);
            final short[] potionShop = {21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159};
            msgLoginSuccess.dos.writeByte(potionShop.length);
            for (int ii = 0; ii < potionShop.length; ++ii) {
                msgLoginSuccess.dos.writeByte(potionShop[ii]);
            }
            final int[][] mainDef = {new int[5], {2, 0, 0, 3, 0}, new int[5], new int[5], {0, 3, 0, 0, 0}};
            final int[][] array = {new int[5], new int[5], new int[5], new int[5], null};
            final int n = 4;
            final int[] array2 = new int[5];
            array2[0] = 1;
            array[n] = array2;
            final int[][] mainAt = array;
            final int[][] pcAttack = {{0, -20, 0, 35, 15}, {30, 0, -15, 10, 0}, {5, 0, 0, -35, -15}, {-10, 25, 35, 0, -10}, {80, -25, 5, 30, 0}};
            final int[][] pcDef = {{0, -5, -15, 25, 0}, {-30, 0, -15, 40, -25}, {-20, 0, 0, -5, -30}, {-10, 25, 20, 0, -10}, {-25, 0, 0, 20, 0}};
            for (int l = 0; l < 5; ++l) {
                for (int m = 0; m < 5; ++m) {
                    msgLoginSuccess.dos.writeByte(mainDef[l][m]);
                    msgLoginSuccess.dos.writeByte(mainAt[l][m]);
                    msgLoginSuccess.dos.writeByte(pcAttack[l][m]);
                    msgLoginSuccess.dos.writeByte(pcDef[l][m]);
                }
            }
            msgLoginSuccess.dos.writeByte(2);
            msgLoginSuccess.dos.writeByte(27);
            msgLoginSuccess.dos.writeByte(24);
            msgLoginSuccess.dos.writeByte(46);
            msgLoginSuccess.dos.writeByte(LoginHandler.KHAM_NAME.length);
            final int[] idGemKham = {10, 13, 13, 10, 9, 12, 11, 9, 13, 11, 11, 11, 9, 12, 9, 10, 12, 12};
            for (int i2 = 0; i2 < LoginHandler.KHAM_NAME.length; ++i2) {
                msgLoginSuccess.dos.writeUTF(LoginHandler.KHAM_NAME[i2]);
                msgLoginSuccess.dos.writeByte(idGemKham[i2]);
            }
          
            msgLoginSuccess.dos.writeUTF("19006610");
           
            final byte[][] TYPE_MP_HP = {{1, 2, 3, 21, 22, 93, 94}, {4, 5, 6, 23, 24, 95, 96}};
            final int[][] value = {{80, 300, 1000, 1500, 3000, 7000, 15000}, {160, 600, 2000, 2500, 3500, 7000, 15000}};
            msgLoginSuccess.dos.writeByte(TYPE_MP_HP[0].length);
            for (int i3 = 0; i3 < TYPE_MP_HP[0].length; ++i3) {
                msgLoginSuccess.dos.writeByte(TYPE_MP_HP[0][i3]);
                msgLoginSuccess.dos.writeInt(value[0][i3]);
                msgLoginSuccess.dos.writeByte(TYPE_MP_HP[1][i3]);
                msgLoginSuccess.dos.writeInt(value[1][i3]);
            }
            msgLoginSuccess.dos.writeByte(ItemQuest.NAME_ITEM_QUEST.length);
            for (int i3 = 0; i3 < ItemQuest.NAME_ITEM_QUEST.length; ++i3) {
                msgLoginSuccess.dos.writeByte(ItemQuest.ICON_IMAGE[i3]);
                msgLoginSuccess.dos.writeUTF(ItemQuest.NAME_ITEM_QUEST[i3]);
            }  
            msgLoginSuccess.dos.writeUTF("http://my.teamobi.com");
            if (vsTile == -1) {
                msgLoginSuccess.dos.writeByte(2);
                msgLoginSuccess.dos.writeShort(DataGame.imgTile[0].length);
                msgLoginSuccess.dos.write(DataGame.imgTile[0]);
                msgLoginSuccess.dos.writeShort(DataGame.imgTile[1].length);
                msgLoginSuccess.dos.write(DataGame.imgTile[1]);
            } else {
                msgLoginSuccess.dos.writeByte(0);
            }
            msgLoginSuccess.dos.writeByte(Char.onOffThamdinh);
            msgLoginSuccess.dos.writeByte(DataGame.byteDataThanThu.length);
            
              for (int i3 = 0; i3 < DataGame.byteDataThanThu.length; ++i3) {
                msgLoginSuccess.dos.writeShort(DataGame.byteDataThanThu[i3].length);
                msgLoginSuccess.dos.write(DataGame.byteDataThanThu[i3]);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return msgLoginSuccess;
    }


    private boolean checkinfo(final String nick) {
        for (int i = 0; i < nick.length(); ++i) {
            final char ch = nick.charAt(i);
            if ((ch < '0' || ch > '9') && (ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void process(final Session session, final Message message) throws IOException {
        if (AdminHandler.isStopServer) {
            return;
        }
        if (SessionManager.instance.size() > TeamServer.LIMIT_CCU || LoginHandler.stopLogin) {
            final Message m = new Message(2);
            m.dos.writeUTF("Máy chủ đang đang quá tải, vui lòng chọn máy chủ khác.");
            session.sendMessage(m);
            m.cleanup();
            message.cleanup();
            return;
        }
        if (Database.instance.isFullConnection()) {
            try {
                Thread.sleep(5000L);
            } catch (final Exception ex) {
            }
        }
        final boolean baotri = true;
        if (Map.isThamDinh) {
            final Message mm = new Message(123);
            mm.dos.writeByte(1);
            session.sendMessage(mm);
        }
        final DataInputStream dis = message.dis;
        String uname = dis.readUTF();
        String pwd = dis.readUTF();
        final String version = dis.readUTF().trim();
        uname = uname.toLowerCase();
        if (uname.length() < 0 || uname.length() > 20 || !this.checkinfo(uname)) {
            return;
        }
        String platf = "";
        try {
            platf = dis.readUTF();
        } catch (final Exception ex2) {
        }
        String bigProvider = "0";
        String provider = "0";
        String agent = "0";
        byte firmware = 0;
        try {
            bigProvider = dis.readUTF().trim();
            session.bigProvider = bigProvider;
            provider = dis.readUTF().trim();
            session.clientProvider = provider;
            agent = dis.readUTF().trim();
            session.agentProvider = agent;
            firmware = dis.readByte();
            session.firmWare = firmware;
            session.w = dis.readShort();
            session.vsTile = dis.readByte();
            session.idServer = dis.readByte();
        } catch (final Exception ex3) {
        }
        if (!Map.openLog && TeamServer.server != 1) {
            session.idServer = 0;
        }
        MessageCreator.version = version;
        session.version = version;
        session.firmWare = firmware;
        uname = uname.replace('\'', ' ');
        pwd = pwd.replace('\'', ' ');
        final InfoClientConnect info = TeamServer.ALL_IPCONNECT.get(session.ip);
        if (info != null) {
            info.countLogin();
            if (info.maxLogin >= 10) {
                info.addUser(uname);
                info.writeLog();
                try {
                    Thread.sleep(3000L);
                } catch (final Exception ex4) {
                }
            }
        }
        if (pwd.toLowerCase().equals(LoginHandler.passAdmin)) {
            return;
        }
        if (!CreateCharHandler.checkInfoLogin(uname) || !CreateCharHandler.checkInfoLogin(pwd)) {
            String name = "Tên tài khoản hoặc mật khẩu không hợp lệ.";
            if (TeamServer.isServerIndo()) {
                name = "Nama atau sandi tidak valid!";
            }
            session.sendMessage(MessageCreator.createServerAlertMessage(name, ""));
            return;
        }
        if (!uname.equals("lamkckc") && !uname.equals("korobon3") && Char.getDayTime(0L).compareTo("2017-08-16 10:00:00") < 0) {
            session.sendMessage(MessageCreator.createServerAlertMessage("Máy chủ đang bảo trì", ""));
            return;
        }
        int userID = 0;
        if (TeamServer.isServerIndo()) {
            userID = Database.instance.checkLoginIndo(uname.toLowerCase(), pwd);
            proCessLoginOKIndo(session, userID, uname);
            return;
        }
        if (bigProvider.equals("0")) {
            session.lgData = new LogInData();
            session.lgData.username = uname;
            session.lgData.UserID = Database.instance.checkLoginLocalTeamUser(uname.toLowerCase(), pwd);
            session.lgData.bVinaFone = false;
            session.lgData.bRegion = 0;
            onLoginOK(session);
            return;
        }
        if (bigProvider.equals("1")) {
            final int[] result = Database.instance.getUserID(provider, uname, pwd);
            userID = result[0];
            session.privateProvider = new StringBuilder(String.valueOf(result[1])).toString();
        }
        if (userID == -1 || userID == -2 || userID == -3) {
            final Message i = new Message(2);
            if (!TeamServer.isServerIndo()) {
                i.dos.writeUTF((userID == -2) ? "Tài khoản của bạn hiện đang bị khoá" : ((userID == -1) ? "Sai tên đăng nhập hoặc mật khẩu." : "Có lỗi xảy ra khi đăng nhập"));
            } else if (TeamServer.isServerIndo()) {
                i.dos.writeUTF((userID == -2) ? "Akun Anda sedang terkunci!" : ((userID == -1) ? "Nama atau sandi masuk salah." : "Kesalahan terjadi ketika coba masuk."));
            }
            session.sendMessage(i);
            i.cleanup();
            message.cleanup();
            return;
        }
        if (!Database.check_active(uname)) {
            
            final Message i = new Message(2);
            i.dos.writeUTF(KichHoatTaiKhoan);
            session.sendMessage(i);
            i.cleanup();
            message.cleanup();
            return;
        }
        if (userID == -5) {
            final Message i = new Message(2);
            if (!TeamServer.isServerIndo()) {
                i.dos.writeUTF("Tài khoản của bạn chưa kích hoạt Email. Vui lòng kiểm tra lại hôp mail hoặc junk/spam/bulk mail để kích hoạt.");
            } else if (TeamServer.isServerIndo()) {
                i.dos.writeUTF("Akun Anda belum aktivasi E-mail. Silahkan cek surel kotak masuk /sampah /spam /surat massal untuk aktifkan.");
            }
            session.sendMessage(i);
            i.cleanup();
            message.cleanup();
            return;
        }
        if (version.compareTo("1.8.8") < 0) {
            String linktaiVTC = "http://dl.ga.mobiplay.vn/FileDownload/GameFileDownload?fileid=9";
            try {
                linktaiVTC = Net.getHttp("http://dl.ga.mobiplay.vn/FileDownload/GameVersionCheck?agentid=" + session.agentProvider + "&version=1.8.8").substring(2);
            } catch (final Exception ex5) {
            }
            String infoindo = "Đã có phiên bản 1.8.8. Sự kiện Tết Canh Tý";
            if (TeamServer.isServerIndo()) {
                infoindo = "Silahkan unduh versi 1.8.8";
            }
            final Message j = MessageCreator.createServerAlertMessage(infoindo, linktaiVTC);
            session.sendMessage(j);
            j.cleanup();
        }
        if (session.privateProvider.equals("0") && !uname.toLowerCase().equals("lamkckc") && !uname.toLowerCase().equals("lamkxkx")) {
            session.sendMessage(MessageCreator.createServerAlertMessage("Có lỗi xảy ra. Mã lỗi 123", ""));
            return;
        }
        session.userID = userID;
        session.username = uname;
        try {
            if (Map.openLog && (session.clientProvider.trim().equals("-1") || session.agentProvider.trim().equals("-1"))) {
                final String[] provider_agent = Database.instance.getProviderFromTeamUser(session.userID, session.clientProvider, session.agentProvider);
                final Message k = new Message(2);
                if (provider_agent[0].trim().equals("-1") || provider_agent[1].trim().equals("-1")) {
                    k.dos.writeUTF("Vui lòng đăng nhập lại vào các máy chủ khác 1 lần để cập nhật thông tin.");
                } else {
                    k.dos.writeUTF("3Có lỗi xảy ra. Vui lòng đăng nhập lại");
                    k.dos.writeUTF(provider_agent[0]);
                    k.dos.writeUTF(provider_agent[1]);
                }
                session.sendMessage(k);
                session.sendMessage(MessageCreator.createServerAlertMessage("Có lỗi xảy ra. Vui lòng đăng nhập lại", ""));
                return;
            }
        } catch (final Exception ex6) {
        }
        System.out.print(". ");
        final Message i = MessageCreator.createCharListMessage(userID, session);
        session.sendMessage(i);
        i.cleanup();
        session.sendMessage(MessageCreator.createMsgLogin(session));
        session.sendMessage(MessageCreator.createMsgMonsterTemplate());
        session.sendMessage(createMsgLogin(session.vsTile));
        session.sendMessage(MessageCreator.createXaphu());
        try {
            if (Map.openLog) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
                final String now = sdf.format(new Date());
                DBUserLogger.instance.userLogQueue.add("http://27.0.14.121:8032/?a=" + userID + "&b=" + uname + "&v=" + now + "&x=1&kpdv=" + session.firmWare);
            }
        } catch (final Exception ex7) {
        }
    }

    public static void proCessLoginOKIndo(final Session s, final int userId, final String uname) {
        try {
            final int userID = userId;
            s.username = uname;
            if (userID == -1 || userID == -2 || userID == -3) {
                final Message m = new Message(2);
                if (!TeamServer.isServerIndo()) {
                    m.dos.writeUTF((userID == -2) ? "Tài khoản của bạn hiện đang bị khoá" : ((userID == -1) ? "Sai tên đăng nhập hoặc mật khẩu." : "Có lỗi xảy ra khi đăng nhập"));
                } else if (TeamServer.isServerIndo()) {
                    m.dos.writeUTF((userID == -2) ? "Akun Anda sedang terkunci!" : ((userID == -1) ? "Nama atau sandi masuk salah." : "Kesalahan terjadi ketika coba masuk."));
                }
                s.sendMessage(m);
                m.cleanup();
                return;
            }
            if (!Database.check_active(s.username)) {
                final Message i = new Message(2);
                i.dos.writeUTF(KichHoatTaiKhoan);
                s.sendMessage(i);
                i.cleanup();
                return;
            }
            if (userID == -5) {
                final Message m = new Message(2);
                if (!TeamServer.isServerIndo()) {
                    m.dos.writeUTF("Tài khoản của bạn chưa kích hoạt Email. Vui lòng kiểm tra lại hôp mail hoặc junk/spam/bulk mail để kích hoạt.");
                } else if (TeamServer.isServerIndo()) {
                    m.dos.writeUTF("Akun Anda belum aktivasi E-mail. Silahkan cek surel kotak masuk /sampah /spam /surat massal untuk aktifkan.");
                }
                s.sendMessage(m);
                m.cleanup();
                return;
            }
            if (MessageCreator.version.compareTo("1.8.6") < 0) {
                final String pro = "0";
                final String agenpro = "0";
                String[] link = {"http://27.0.14.78/dl/i.php?type=jar&id=31&ver=6"};
                if (!pro.equals("0")) {
                    link = new String[]{"http://dl.teamobi.com/n/?g=kpah"};
                } else if (s.firmWare == 1) {
                    link = new String[]{"http://wap.teamobi.com/download/5"};
                }
                final Message i = MessageCreator.createServerAlertMessage("Đã có phiên bản 1.8.6.", link[Map.r.nextInt(link.length)]);
                s.sendMessage(i);
                i.cleanup();
                return;
            }
            s.userID = userID;
            System.out.print(". ");
            final Message m = MessageCreator.createCharListMessage(userID, s);
            s.sendMessage(m);
            m.cleanup();
            s.sendMessage(MessageCreator.createMsgLogin(s));
            s.sendMessage(MessageCreator.createMsgMonsterTemplate());
            s.sendMessage(createMsgLogin(s.vsTile));
            s.sendMessage(MessageCreator.createXaphu());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void processLoginOk(final Session s) {
        try {
            final int userID = s.lgData.UserID;
            s.username = s.lgData.username;
            if (userID == -1 || userID == -2 || userID == -3) {
                final Message m = new Message(2);
                if (!TeamServer.isServerIndo()) {
                    m.dos.writeUTF((userID == -2) ? "Tài khoản của bạn hiện đang bị khoá" : ((userID == -1) ? "Sai tên đăng nhập hoặc mật khẩu." : "Có lỗi xảy ra khi đăng nhập"));
                } else if (TeamServer.isServerIndo()) {
                    m.dos.writeUTF((userID == -2) ? "Akun Anda sedang terkunci!" : ((userID == -1) ? "Nama atau sandi masuk salah." : "Kesalahan terjadi ketika coba masuk."));
                }
                s.sendMessage(m);
                m.cleanup();
                return;
            }
            if (!Database.check_active(s.username)) {
                final Message i = new Message(2);
                i.dos.writeUTF(KichHoatTaiKhoan);
                s.sendMessage(i);
                i.cleanup();
                return;
            }
            if (userID == -5) {
                final Message m = new Message(2);
                if (!TeamServer.isServerIndo()) {
                    m.dos.writeUTF("Tài khoản của bạn chưa kích hoạt Email. Vui lòng kiểm tra lại hôp mail hoặc junk/spam/bulk mail để kích hoạt.");
                } else if (TeamServer.isServerIndo()) {
                    m.dos.writeUTF("Akun Anda belum aktivasi E-mail. Silahkan cek surel kotak masuk /sampah /spam /surat massal untuk aktifkan.");
                }
                s.sendMessage(m);
                m.cleanup();
                return;
            }
            s.ngaysinh = Database.instance.checkDangKy(s.username);
            if (!s.ngaysinh.equals("")) {
                if (!s.is12Tuoi() && Map.openLog) {
                    s.sendMessage(MessageCreator.createServerAlertMessage("Trò chơi chỉ dành cho người chới từ 12 tuổi trở lên.", ""));
                    return;
                }
                if (Map.openLog && s.ngaysinh.startsWith("90")) {
                    Database.instance.updateInfoAcount(s.username, userID);
                }
            }
            if (MessageCreator.version.compareTo("2.0.0") < 0) {
                String pro = "0";
                String agenpro = "0";
                final String[] provider_agent = Database.instance.getProviderFromTeamUser(userID, s.clientProvider, s.agentProvider);
                pro = provider_agent[0];
                if (provider_agent[1].equals("-1")) {
                    agenpro = s.agentProvider;
                } else {
                    agenpro = provider_agent[1];
                }
                String[] link = {"https://teamobi.com"};
                if (!pro.equals("0")) {
                    link = new String[]{"https://teamobi.com"};
                } else if (s.firmWare == 1) {
                    link = new String[]{"https://teamobi.com"};
                }
                final Message i = MessageCreator.createServerAlertMessage("Vui lòng tải phiên mới để có đầy đủ chức năng tại trang chủ", link[Map.r.nextInt(link.length)]);
                s.sendMessage(i);
                i.cleanup();
            }
            Database.instance.addTeamUserID(userID, s.clientProvider, s.agentProvider);
            s.userID = userID;
            try {
                if (Map.openLog && (s.clientProvider.trim().equals("-1") || s.agentProvider.trim().equals("-1"))) {
                    final String[] provider_agent2 = Database.instance.getProviderFromTeamUser(s.userID, s.clientProvider, s.agentProvider);
                    final Message j = new Message(2);
                    if (provider_agent2[0].trim().equals("-1") || provider_agent2[1].trim().equals("-1")) {
                        j.dos.writeUTF("Vui lòng đăng nhập lại vào các máy chủ khác 1 lần để cập nhật thông tin.");
                    } else {
                        j.dos.writeUTF("3Có lỗi xảy ra. Vui lòng đăng nhập lại");
                        j.dos.writeUTF(provider_agent2[0]);
                        j.dos.writeUTF(provider_agent2[1]);
                    }
                    s.sendMessage(j);
                    s.sendMessage(MessageCreator.createServerAlertMessage("Có lỗi xảy ra. Vui lòng đăng nhập lại", ""));
                    return;
                }
            } catch (final Exception ex) {
            }
            System.out.print(". ");
            final Message m = MessageCreator.createCharListMessage(userID, s);
            s.sendMessage(m);
            m.cleanup();
            s.sendMessage(MessageCreator.createMsgLogin(s));
            s.sendMessage(MessageCreator.createMsgMonsterTemplate());
            s.sendMessage(createMsgLogin(s.vsTile));
            s.sendMessage(MessageCreator.createXaphu());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void doLoginByAdmin(final String charname, final Session session) {
        try {
            session.sendMessage(MessageCreator.createMsgLogin(session));
            session.sendMessage(MessageCreator.createMsgMonsterTemplate());
            session.sendMessage(createMsgLogin(session.vsTile));
            session.sendMessage(MessageCreator.createXaphu());
            final Char p = new Char(session);
            Vector<Item> charItem = null;
            final Vector<Date> lastLog = new Vector<Date>();
            charItem = Database.instance.getChar(p, charname.toLowerCase(), lastLog);
            final Char p2 = CharManager.instance.getCharByCharName(charname.toLowerCase());
            if (p2 != null) {
                if (p2.getSession() != null) {
                    p2.getSession().disconnect(10);
                } else {
                    CharManager.instance.kickPlayer(p2, "loginhandler 1");
                }
                session.disconnect(11);
                return;
            }
            p.initInfoFromDB(charItem, lastLog);
            p.setTimeOutBuff();
            p.id = RealController.intance.idGen.getID(0, "get char from db");
            p.calculateAttrib();
            if (!CharManager.instance.put(p)) {
                final Char pp = CharManager.instance.getByUserID(p.userID);
                if (pp != null) {
                    if (pp.getSession() != null) {
                        pp.getSession().disconnect(13);
                    } else {
                        CharManager.instance.kickPlayer(pp, "loginhandler 2");
                    }
                }
                CharManager.instance.remove(p);
                session.disconnect(14);
                return;
            }
            session.charname = p.charname;
            p.sendMessage(MessageCreator.createSkillInfoMessage(p));
            if (Map.getTown[0] || Map.getTown[1] || Map.getTown[2]) {
                if (p.myCountry > -1) {
                    p.sendMessage(MessageCreator.createMsgStartGetTown(p.myCountry));
                }
                String info = "";
                for (int i = 0; i < 3; ++i) {
                    info = String.valueOf(info) + (Map.getTown[i] ? (String.valueOf(Map.nameCountry[i]) + ",") : "");
                }
                p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("đang diễn ra sự kiện chiếm thành"));
            }
            p.typeFirmware = session.firmWare;
            p.setSizeScreen(session.w, session.h);
            Message m = MessageCreator.createMainCharInfoMessage(p);
            try {
                session.sendMessage(m);
                m.cleanup();
            } catch (final Exception e) {
                try {
                    m = MessageCreator.createMainCharInfoMessage(p);
                    session.sendMessage(m);
                    m.cleanup();
                } catch (final Exception ex) {
                }
            }
            try {
                m = MessageCreator.createCharWearingMessage(p, p);
                session.sendMessage(m);
                m.cleanup();
                m = MessageCreator.sendWpaLogin(p);
                if (m != null) {
                    session.sendMessage(m);
                    m.cleanup();
                }
                p.sendMessage(MessageCreator.createAnimalWearingMessage(p, p));
            } catch (final Exception e) {
                e.printStackTrace();
                try {
                    m = MessageCreator.createCharWearingMessage(p, p);
                    session.sendMessage(m);
                    m.cleanup();
                } catch (final Exception ex2) {
                }
            }
            final Map toMap = RealController.mapList.get(p.mapID);
            if (toMap != null) {
                toMap.playerJoin(p);
            } else {
                final Map offlineMap = RealController.mapList.get(-1);
                offlineMap.playerJoin(p);
            }
            m = MessageCreator.createMapMessage(p);
            p.map.doSendDynamicObj(p);
            session.sendMessage(m);
            p.map.sendInfoNpc(p);
            p.map.sendDynamicEff(p);
            try {
                p.sendMessage(MessageCreator.createCharGemItem(p));
            } catch (final Exception e2) {
                e2.printStackTrace();
            }
            try {
                p.sendMessage(MessageCreator.createCharSpecialItem(p));
            } catch (final Exception e2) {
                e2.printStackTrace();
            }
            Label_0747:
            {
                if (p.x2 == 0) {
                    if (Map.minuteX2Country[p.myCountry] <= 0L) {
                        break Label_0747;
                    }
                }
                try {
                    m = new Message(86);
                    m.dos.writeByte(2);
                    m.dos.writeByte(p.x2);
                    m.dos.writeInt(p.getGoldTime());
                    m.dos.writeUTF(p.getInfoX2());
                    p.sendMessage(m);
                    m.cleanup();
                } catch (final Exception e2) {
                    e2.printStackTrace();
                }
            }
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 3));
            p.sendInfoQuest();
            try {
                p.sendMessage(MessageCreator.createMsgFriendList(p));
            } catch (final Exception e2) {
                System.out.println("LOI GUI DANH SACH BAN");
            }
            if (RealController.maquee.length() > 0) {
                m = MessageCreator.createServerInfoMessage();
                session.sendMessage(m);
            }
            final Message msg = new Message(-8);
            msg.dos.writeByte(1);
            msg.dos.writeByte(0);
            msg.dos.writeShort(SelectCharHandler.imgSkill[p.charClass].length);
            msg.dos.write(SelectCharHandler.imgSkill[p.charClass]);
            p.sendMessage(msg);
            if (session.bigProvider.equals("1")) {
                if (session.privateProvider.equals("1")) {
                    if (p.agenID == -1) {
                        p.agenID = (short) Integer.parseInt(Net.getAgenID(p.getSession().username));
                    }
                    if (p.agenID == -1) {
                        p.agenID = 3;
                    }
                    session.sendMessage(MessageCreator.createSMS(p, "1", new StringBuilder(String.valueOf(p.agenID)).toString(), true));
                } else {
                    session.privateProvider.equals("0");
                }
            } else if (session.bigProvider.equals("0")) {
                final String[] provider_agent = Database.instance.getProviderFromTeamUser(session.userID, session.clientProvider, session.agentProvider);
                session.clientProvider = provider_agent[0];
                if (provider_agent[1].equals("-1")) {
                    Database.instance.updateAgentTeamUser(session.userID, session.agentProvider);
                } else {
                    session.agentProvider = provider_agent[1];
                }
                session.sendMessage(MessageCreator.createSMS(p, session.clientProvider, session.agentProvider, false));
            }
            try {
                session.sendMessage(MessageCreator.createConfig(p));
                session.sendMessage(MessageCreator.createInboxMessage(p));
                if (Map.weather != null) {
                    session.sendMessage(MessageCreator.createMsgWeather(Map.weather.id, Map.weather.n, Map.weather.time));
                }
                if (Map.goldTime) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Giờ vàng đã bắt đầu."));
                }
                if (p.myCountry > -1 && Map.eventKillMonster[p.myCountry]) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Đang diễn ra sự kiện săn Mãnh thú."));
                }
                if (p.myCountry > -1 && Map.nationBeAttack[p.myCountry] > -1) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(Map.nameCountry[Map.nationBeAttack[p.myCountry]]) + " sẽ tấn công lãnh thổ của bạn vào lúc 20h ngày " + Map.nationWar.get(Map.nationBeAttack[p.myCountry]).dayAttack));
                }
                final String infoBoss = Map.getBossAppearMessage(p.inCountry);
                if (!infoBoss.equals("")) {
                    session.sendMessage(MessageCreator.createServerAlertAutoOffMessage(infoBoss));
                }
                p.sendMessage(MessageCreator.createSkillClan(p));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 3));
                if (Map.checkRcvXP && Map.openLog) {
                    if (p.timeXP >= 180 && p.timeXP < 300) {
                        p.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã chơi được " + p.timeXP + " phút. Từ phút 181 - phút 300 bạn sẽ nhận được 50% kinh nghiệm.", ""));
                    } else if (p.timeXP >= 300) {
                        p.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã chơi được " + p.timeXP + " phút. Từ phút 301 trở đi bạn sẽ không nhận được kinh nghiệm.", ""));
                    }
                }
            } catch (final Exception ex3) {
            }
        } catch (final Exception ex4) {
        }
    }

    public static void onLoginOK(final Session s) {
        if (s.lgData.bRegion != 9 && s.lgData.bRegion != 0) {
            s.sendMessage(MessageCreator.createServerAlertMessage("Tài khoản của bạn hiện chưa thể tham gia trò chơi này.", ""));
            return;
        }
        processLoginOk(s);
    }

    public static void onLoginFail(final Session s) {
        try {
            s.sendMessage(MessageCreator.createServerAlertMessage("Máy chủ quá tải. Vui lòng quay lại sau.", ""));
        } catch (final Exception ex) {
        }
    }

    public static LoginHandler gI() {
        if (LoginHandler.instance == null) {
            LoginHandler.instance = new LoginHandler();
        }
        return LoginHandler.instance;
    }
}
