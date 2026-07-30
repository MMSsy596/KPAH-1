package real;

import data.Database;
import data.Text;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.IOException;
import real.cmd.ICommandHandler;
import server.TeamServer;

/** Local replacement for the legacy Teamobi HTTP/SMS account registration. */
public class RequestRegisterHandler implements ICommandHandler {

    public static boolean checkInfoLogin(String username) {
        String value = username == null ? "" : username.trim();
        if (value.length() < 5 || value.length() > 16) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (!((ch >= '0' && ch <= '9')
                    || (ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || ch == '@' || ch == '_' || ch == '.')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void process(Session session, Message message) throws IOException {
        DataInputStream input = message.dis;
        String username = input.readUTF().trim();
        String password = input.readUTF();

        if (!checkInfoLogin(username)) {
            session.sendMessage(MessageCreator.createServerAlertMessage(Text.REGIST_ERR_ACOUNT, ""));
            return;
        }
        if (password.length() < 4) {
            session.sendMessage(MessageCreator.createServerAlertMessage(Text.REGIST_ERR_PASS, ""));
            return;
        }
        if (!TeamServer.isServerLocal()) {
            session.sendMessage(MessageCreator.createServerAlertMessage(
                    "Đăng ký trực tiếp chỉ được bật trên máy chủ local.", ""));
            return;
        }

        int accountId = Database.instance.registerLocalAccount(username, password);
        if (accountId == -2) {
            session.sendMessage(MessageCreator.createServerAlertMessage(Text.REGIST_ERR_PHONE_NUMBER, ""));
            return;
        }
        if (accountId < 0) {
            session.sendMessage(MessageCreator.createServerAlertMessage(
                    "Không thể tạo tài khoản do lỗi cơ sở dữ liệu. Vui lòng thử lại.", ""));
            return;
        }

        session.usernameReg = username;
        session.sendMessage(MessageCreator.createServerAlertMessage(
                Text.REGIST_CHUC_MUNG + " " + username + ". " + Text.REGIST_BAO_MAT, ""));
    }

    public static void doProcessInput(Session session, Message message) {
        // Local registration completes immediately and never opens the old SMS flow.
    }
}
