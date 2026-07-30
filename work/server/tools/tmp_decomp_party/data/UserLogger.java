/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

public class UserLogger {
    public static String PATH = "E:/workspaceNew/log/";
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static DateFormat dateYearFormat = new SimpleDateFormat("yyyy");
    public static DateFormat dateMonthFormat = new SimpleDateFormat("MM");
    public static DateFormat dateDayFormat = new SimpleDateFormat("dd");
    private static Hashtable<String, Vector<LogItem>> playerLogs = new Hashtable();
    static UserLogger instance = new UserLogger();
    public static String[][] temp = new String[][]{{"a\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead", "\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7e", "\u00ed\u00ec\u1ec9\u0129\u1ecbi", "\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3o", "\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1u", "\u00fd\u1ef3\u1ef7\u1ef9\u1ef5", "\u0111"}, {"a", "e", "i", "o", "u", "y", "d"}};

    public static synchronized void addLog(String username, String text, String aclog) {
    }

    public static synchronized void flushToFile(String username) {
    }

    public static String switch2UnUTF(String source) {
        source = source.toLowerCase();
        String result = "";
        int i = 0;
        while (i < source.length()) {
            String st = String.valueOf(source.charAt(i));
            int j = 0;
            while (j < temp[0].length) {
                if (temp[0][j].indexOf(st) != -1) {
                    st = temp[1][j];
                    break;
                }
                ++j;
            }
            result = String.valueOf(result) + st;
            ++i;
        }
        return result;
    }

    public class LogItem {
        Date logTime;
        String text;
        String charname = "";
        String aclog = "";

        public LogItem(String text, String charname, String aclog) {
            this.text = UserLogger.switch2UnUTF(text);
            this.charname = charname;
            this.aclog = UserLogger.switch2UnUTF(aclog);
            this.logTime = new Date();
        }
    }
}

