package real;

import data.Database;
import io.Message;
import io.Session;
import real.cmd.ICommandHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class RequestRegisterHandler implements ICommandHandler {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 50;
    private static final long REGISTER_COOLDOWN_MS = 5000L;
    private static final ConcurrentHashMap<String, Long> LAST_REGISTER_BY_IP = new ConcurrentHashMap<>();

    private enum RegisterResult {
        SUCCESS,
        DUPLICATE,
        ERROR
    }

    @Override
    public void process(Session session, Message message) throws IOException {
        String username = message.dis.readUTF().trim().toLowerCase();
        String password = message.dis.readUTF();
        System.out.println("[REGISTER] packet from ip=" + safe(session.ip) + " user=" + username);

        if (!isValidUsername(username)) {
            System.out.println("[REGISTER] invalid username user=" + username);
            sendAlert(session, "Ten tai khoan phai tu 5-20 ky tu va chi gom chu hoac so.");
            return;
        }

        if (!isValidPassword(password)) {
            System.out.println("[REGISTER] invalid password user=" + username);
            sendAlert(session, "Mat khau phai tu 4-50 ky tu, khong chua dau nhay don hoac ky tu trang.");
            return;
        }

        String ipKey = session.ip == null ? "" : session.ip.trim();
        long now = System.currentTimeMillis();
        Long lastAttempt = LAST_REGISTER_BY_IP.put(ipKey, now);
        if (lastAttempt != null && now - lastAttempt < REGISTER_COOLDOWN_MS) {
            System.out.println("[REGISTER] rate limited ip=" + safe(ipKey) + " user=" + username);
            sendAlert(session, "Ban thao tac qua nhanh. Vui long doi vai giay roi thu dang ky lai.");
            return;
        }

        RegisterResult result = registerAccount(username, password);
        System.out.println("[REGISTER] result=" + result + " ip=" + safe(ipKey) + " user=" + username);
        switch (result) {
            case SUCCESS:
                session.usernameReg = username;
                session.unameReg = username;
                sendAlert(
                        session,
                        "Chuc mung ban da dang ky thanh cong tai khoan " + username
                                + " voi mat khau " + password
                                + ". Tai khoan da duoc tao truc tiep trong kpah1.team_user. Vui long bao mat thong tin tai khoan."
                );
                break;
            case DUPLICATE:
                sendAlert(session, "Ten tai khoan da ton tai. Vui long chon ten khac.");
                break;
            default:
                sendAlert(session, "Dang ky that bai. Vui long thu lai sau.");
                break;
        }
    }

    public static boolean checkInfoLogin(String login) {
        return isValidUsername(login == null ? "" : login.trim().toLowerCase());
    }

    private static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        int length = username.length();
        if (length < MIN_USERNAME_LENGTH || length > MAX_USERNAME_LENGTH) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char ch = username.charAt(i);
            boolean isDigit = ch >= '0' && ch <= '9';
            boolean isLower = ch >= 'a' && ch <= 'z';
            boolean isUpper = ch >= 'A' && ch <= 'Z';
            if (!isDigit && !isLower && !isUpper) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        int length = password.length();
        if (length < MIN_PASSWORD_LENGTH || length > MAX_PASSWORD_LENGTH) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char ch = password.charAt(i);
            if (ch == '\'' || Character.isWhitespace(ch) || Character.isISOControl(ch)) {
                return false;
            }
        }
        return true;
    }

    private static RegisterResult registerAccount(String username, String password) {
        int result = Database.instance.registerLocalTeamUser(username, password);
        if (result > 0) {
            return RegisterResult.SUCCESS;
        }
        if (result == -2) {
            return RegisterResult.DUPLICATE;
        }
        return RegisterResult.ERROR;
    }

    private static void sendAlert(Session session, String text) throws IOException {
        Message alert = MessageCreator.createServerAlertMessage(text, "");
        session.sendMessage(alert);
        alert.cleanup();
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
