package maint;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SetAmbientBotTargetAgent {

    private static final String RESULT_FILE = "runtime/ambient_bot_attach_result.txt";

    private SetAmbientBotTargetAgent() {
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    private static void run(String agentArgs) {
        int target = parseTarget(agentArgs);
        String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        StringBuilder output = new StringBuilder();
        output.append(timestamp).append(System.lineSeparator());
        output.append("requested_target=").append(target).append(System.lineSeparator());

        try {
            Class<?> managerClass = Class.forName("real.AmbientBotManager");
            Field instanceField = managerClass.getField("instance");
            Object manager = instanceField.get(null);
            if (manager == null) {
                throw new IllegalStateException("real.AmbientBotManager.instance is null");
            }

            Method setTargetCount = managerClass.getMethod("setTargetCount", int.class);
            Method getTargetCount = managerClass.getMethod("getTargetCount");
            Method getCurrentCount = managerClass.getMethod("getCurrentCount");
            Method getRosterCount = managerClass.getMethod("getRosterCount");
            Method getAdminSummary = managerClass.getMethod("getAdminSummary");

            setTargetCount.invoke(manager, target);
            Thread.sleep(2000L);

            int appliedTarget = ((Number) getTargetCount.invoke(manager)).intValue();
            int currentCount = ((Number) getCurrentCount.invoke(manager)).intValue();
            int rosterCount = ((Number) getRosterCount.invoke(manager)).intValue();
            String summary = String.valueOf(getAdminSummary.invoke(manager));

            output.append("ok=1").append(System.lineSeparator());
            output.append("applied_target=").append(appliedTarget).append(System.lineSeparator());
            output.append("current_count=").append(currentCount).append(System.lineSeparator());
            output.append("roster_count=").append(rosterCount).append(System.lineSeparator());
            output.append("summary=").append(summary).append(System.lineSeparator());
        } catch (Throwable throwable) {
            output.append("ok=0").append(System.lineSeparator());
            output.append("error=").append(throwable.getClass().getName()).append(": ")
                    .append(String.valueOf(throwable.getMessage())).append(System.lineSeparator());
            for (StackTraceElement element : throwable.getStackTrace()) {
                output.append("at ").append(element.toString()).append(System.lineSeparator());
            }
        }

        writeResult(output.toString());
    }

    private static int parseTarget(String agentArgs) {
        if (agentArgs == null) {
            return 100;
        }
        try {
            return Integer.parseInt(agentArgs.trim());
        } catch (Exception ignored) {
            return 100;
        }
    }

    private static void writeResult(String text) {
        File file = new File(RESULT_FILE);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            writer.print(text);
        } catch (Exception ignored) {
        }
    }
}
