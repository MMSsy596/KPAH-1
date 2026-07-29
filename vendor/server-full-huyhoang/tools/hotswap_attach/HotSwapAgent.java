package maint;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class HotSwapAgent {

    private HotSwapAgent() {
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs, instrumentation);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs, instrumentation);
    }

    private static void run(String agentArgs, Instrumentation instrumentation) {
        Path resultFile = null;
        try {
            if (!instrumentation.isRedefineClassesSupported()) {
                throw new IllegalStateException("JVM khong ho tro redefineClasses.");
            }

            Properties properties = loadConfig(agentArgs);
            Path classesDir = requirePath(properties, "classesDir");
            resultFile = optionalPath(properties.getProperty("resultFile"));
            List<String> classNames = parseClassNames(properties.getProperty("classes"));
            if (classNames.isEmpty()) {
                throw new IllegalArgumentException("Khong co class nao de hotswap.");
            }

            List<ClassDefinition> definitions = new ArrayList<ClassDefinition>();
            for (String className : classNames) {
                Class<?> loadedClass = findLoadedClass(instrumentation, className);
                if (loadedClass == null) {
                    throw new ClassNotFoundException("Class chua duoc load: " + className);
                }
                Path classFile = classesDir.resolve(className.replace('.', '/') + ".class");
                if (!Files.exists(classFile)) {
                    throw new IOException("Khong tim thay file class: " + classFile.toAbsolutePath());
                }
                byte[] bytecode = Files.readAllBytes(classFile);
                definitions.add(new ClassDefinition(loadedClass, bytecode));
            }

            instrumentation.redefineClasses(definitions.toArray(new ClassDefinition[0]));
            writeResult(resultFile,
                    "ok=1\nclasses=" + join(classNames) + "\nclasses_dir=" + classesDir.toAbsolutePath() + "\n");
            System.out.println("[hotswap-agent] redefined " + classNames.size() + " classes");
        } catch (Throwable throwable) {
            writeFailure(resultFile, throwable);
            System.err.println("[hotswap-agent] failed: " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private static Properties loadConfig(String agentArgs) throws IOException {
        if (agentArgs == null || agentArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("Thieu duong dan config cho hot swap agent.");
        }
        Path configPath = Paths.get(agentArgs.trim());
        if (!Files.exists(configPath)) {
            throw new IOException("Khong tim thay file config: " + configPath.toAbsolutePath());
        }
        Properties properties = new Properties();
        InputStream input = Files.newInputStream(configPath);
        try {
            properties.load(input);
        } finally {
            input.close();
        }
        return properties;
    }

    private static Path requirePath(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Thieu gia tri " + key + " trong config.");
        }
        return Paths.get(value.trim());
    }

    private static Path optionalPath(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Paths.get(value.trim());
    }

    private static List<String> parseClassNames(String raw) {
        List<String> result = new ArrayList<String>();
        if (raw == null) {
            return result;
        }
        String[] parts = raw.split(",");
        for (String part : parts) {
            String trimmed = part == null ? "" : part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    private static Class<?> findLoadedClass(Instrumentation instrumentation, String className) {
        Class<?>[] loadedClasses = instrumentation.getAllLoadedClasses();
        for (Class<?> loadedClass : loadedClasses) {
            if (loadedClass != null && className.equals(loadedClass.getName())) {
                return loadedClass;
            }
        }
        return null;
    }

    private static void writeFailure(Path resultFile, Throwable throwable) {
        if (resultFile == null) {
            return;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            printWriter.println("ok=0");
            printWriter.println("message=" + sanitize(String.valueOf(throwable)));
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writeResult(resultFile, stringWriter.toString());
        } catch (Exception ignored) {
        }
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

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }
        return value.replace('\r', ' ').replace('\n', ' ');
    }

    private static String join(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(values.get(i));
        }
        return builder.toString();
    }
}
