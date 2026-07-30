package maint;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ScheduleMaintenanceAgent {

    private ScheduleMaintenanceAgent() {
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    private static void run(String agentArgs) {
        Request request = parseRequest(agentArgs);
        try {
            Class<?> serviceClass = Class.forName("server.localadmin.LocalAdminControlService");
            Method scheduleMaintenance = serviceClass.getMethod("scheduleMaintenance", int.class);
            Object result = scheduleMaintenance.invoke(null, request.minutes);
            boolean ok = readBooleanField(result, "ok");
            String message = readStringField(result, "message");
            if (!ok) {
                throw new IllegalStateException(message);
            }
            writeResult(request.resultFile, "ok=1\nmessage=" + sanitize(message) + "\nminutes=" + request.minutes + "\n");
        } catch (Throwable throwable) {
            Throwable actual = unwrap(throwable);
            writeFailure(request.resultFile, actual);
            throw new RuntimeException(actual);
        }
    }

    private static Request parseRequest(String agentArgs) {
        String raw = agentArgs == null ? "" : agentArgs.trim();
        if (raw.isEmpty()) {
            return new Request(1, null);
        }

        String[] parts = raw.split("\\|", 2);
        int minutes = 1;
        try {
            minutes = Integer.parseInt(parts[0].trim());
        } catch (Exception ignored) {
        }
        if (minutes < 1) {
            minutes = 1;
        }
        if (minutes > 60) {
            minutes = 60;
        }

        Path resultFile = null;
        if (parts.length > 1 && !parts[1].trim().isEmpty()) {
            resultFile = Paths.get(parts[1].trim());
        }
        return new Request(minutes, resultFile);
    }

    private static boolean readBooleanField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getField(fieldName);
        return field.getBoolean(target);
    }

    private static String readStringField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getField(fieldName);
        Object value = field.get(target);
        return value == null ? "" : value.toString();
    }

    private static Throwable unwrap(Throwable throwable) {
        if (throwable instanceof InvocationTargetException) {
            Throwable target = ((InvocationTargetException) throwable).getTargetException();
            if (target != null) {
                return target;
            }
        }
        return throwable;
    }

    private static void writeResult(Path resultFile, String content) {
        if (resultFile == null) {
            return;
        }
        try {
            Path absolute = resultFile.toAbsolutePath();
            Path parent = absolute.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(absolute, content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ignored) {
        }
    }

    private static void writeFailure(Path resultFile, Throwable throwable) {
        if (resultFile == null) {
            return;
        }
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println("ok=0");
            pw.println("message=" + sanitize(throwable.toString()));
            throwable.printStackTrace(pw);
            pw.flush();
            writeResult(resultFile, sw.toString());
        } catch (Exception ignored) {
        }
    }

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }
        return value.replace('\r', ' ').replace('\n', ' ');
    }

    private static final class Request {
        private final int minutes;
        private final Path resultFile;

        private Request(int minutes, Path resultFile) {
            this.minutes = minutes;
            this.resultFile = resultFile;
        }
    }
}
