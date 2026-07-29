package real.cmd;

import data.Database;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.IOException;
import real.Map;
import real.MessageCreator;
import server.InfoClientConnect;
import server.TeamServer;

public class CreateCharHandler implements ICommandHandler {
    private static final int MIN_CHAR_NAME_LENGTH = 4;
    private static final int MAX_CHAR_NAME_LENGTH = 12;

    @Override
    public void process(Session session, Message message) throws IOException {
        DataInputStream dis = message.dis;
        String rawCharName = dis.readUTF();
        String charName = rawCharName == null ? "" : rawCharName.trim();
        int classID = dis.readByte();
        int clientGender = dis.readByte();
        int gender = normalizeGender(clientGender);
        int headStyle = -1;
        int nation = 0;

        try {
            headStyle = dis.readByte();
            nation = dis.readByte();
        } catch (Exception ignored) {
        }

        if (TeamServer.isServerLienDau()) {
            session.sendMessage(MessageCreator.createServerAlertMessage("Không thể tạo nhân vật trong máy chủ liên đấu", ""));
            return;
        }

        if (LoginHandler.stopLogin) {
            Message msg = new Message(2);
            msg.dos.writeUTF("1Máy chủ đang quá tải, vui lòng chọn máy chủ khác.");
            session.sendMessage(msg);
            msg.cleanup();
            message.cleanup();
            return;
        }

        if (session.userID == -1) {
            return;
        }

        InfoClientConnect connectInfo = TeamServer.ALL_IPCONNECT.get(session.ip);
        if (connectInfo != null) {
            connectInfo.countTaoChar();
            if (connectInfo.maxTaoChar >= 10) {
                System.out.println("[CREATE_CHAR] blocked by rate limit ip=" + session.ip + " rawName=" + rawCharName);
                try {
                    Thread.sleep(3000L);
                } catch (Exception ignored) {
                }
                return;
            }
        }

        if (nation == 2) {
            session.sendMessage(MessageCreator.createServerAlertMessage("Lãnh thổ Huyền Vũ đã đóng. Vui lòng chọn lãnh thổ Thanh Long hoặc Hắc Hổ.", ""));
            return;
        }

        String invalidReason = getInvalidCharNameReason(charName);
        if (invalidReason != null) {
            System.out.println("[CREATE_CHAR] invalid name userId=" + session.userID + " ip=" + session.ip + " rawName=" + rawCharName + " normalized=" + charName + " reason=" + invalidReason);
            session.sendMessage(MessageCreator.createServerAlertMessage("Tên nhân vật không hợp lệ", ""));
            return;
        }

        if (Map.checkFullNation(1, 1, nation)) {
            session.sendMessage(MessageCreator.createServerAlertMessage(Map.nameCountry[nation] + " lãnh thổ đã quá đông. Vui lòng chọn lãnh thổ khác.", ""));
            return;
        }

        int result = Database.instance.addChar(session, charName, headStyle, classID, gender, nation);
        System.out.println("[CREATE_CHAR] result=" + result + " userId=" + session.userID + " ip=" + session.ip + " charName=" + charName + " class=" + classID + " clientGender=" + clientGender + " gender=" + gender + " nation=" + nation);
        if (result == -1) {
            Message msg = new Message(14);
            session.sendMessage(msg);
            msg.cleanup();
            return;
        }

        Message msg = MessageCreator.createCharListMessage(session.userID, session);
        session.sendMessage(msg);
        msg.cleanup();
    }

    private static String getInvalidCharNameReason(String charName) {
        if (charName == null || charName.length() == 0) {
            return "empty";
        }
        if (charName.length() < MIN_CHAR_NAME_LENGTH || charName.length() > MAX_CHAR_NAME_LENGTH) {
            return "length";
        }

        for (int i = 0; i < charName.length(); i++) {
            char ch = charName.charAt(i);
            boolean isDigit = ch >= '0' && ch <= '9';
            boolean isLower = ch >= 'a' && ch <= 'z';
            boolean isUpper = ch >= 'A' && ch <= 'Z';
            if (!isDigit && !isLower && !isUpper) {
                return "invalid_char_" + (int) ch;
            }
        }

        return null;
    }

    public static boolean checkInfo(String text) {
        return getInvalidCharNameReason(text == null ? null : text.trim()) == null;
    }

    private static int normalizeGender(int clientGender) {
        if (clientGender <= 0) {
            return 0;
        }
        if (clientGender == 1 || clientGender == 2) {
            return clientGender - 1;
        }
        return clientGender;
    }

    public static boolean checkInfoLogin(String text) {
        if (text == null || text.equals("")) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            boolean isDigit = ch >= '0' && ch <= '9';
            boolean isLower = ch >= 'a' && ch <= 'z';
            boolean isUpper = ch >= 'A' && ch <= 'Z';
            if (!isDigit && !isLower && !isUpper) {
                return false;
            }
        }

        return true;
    }
}
