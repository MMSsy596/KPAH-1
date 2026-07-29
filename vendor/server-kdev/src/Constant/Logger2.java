
package Constant;

/**
 *
 * @author TOM
 */
public class Logger2 {
      public static void DebugLogic(String str) {
        if (!Checker.LOCAL_SERVER) {
            return;
        }
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            StackTraceElement caller = stackTrace[2];
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(caller.getClassName())
                    .append(".")
                    .append(caller.getMethodName())
                    .append("(")
                    .append(caller.getFileName())
                    .append(":")
                    .append(caller.getLineNumber())
                    .append(")");
            str += "  ==> from: " + strBuild.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\033[0;34mLogic>>>                     " + str + "\033[0m");
    }

}
