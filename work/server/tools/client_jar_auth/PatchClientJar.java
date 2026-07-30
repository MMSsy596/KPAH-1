import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public final class PatchClientJar {

    private static final String GO_CLASS_ENTRY = "go.class";
    private static final String SERVER_LIST_CLASS_ENTRY = "yv.class";
    private static final String CANVAS_CLASS_ENTRY = "acv.class";
    private static final String AUTH_CLASS_NAME = "kpahauth";
    private static final String AUTH_CLASS_ENTRY = AUTH_CLASS_NAME + ".class";
    private static final String MENU_ICON_ENTRY = "MrQuyet/mid.png";
    private static final String MENU_ICON_ALIAS_ENTRY = "MrQuyet/MrQuyet.png";

    private PatchClientJar() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: PatchClientJar <input.jar> <output.jar> <clientId> [defaultHost] [serverListUrl] [measurementOverride] [defaultPort]");
            System.exit(1);
        }
        Path inputJar = Paths.get(args[0]).toAbsolutePath().normalize();
        Path outputJar = Paths.get(args[1]).toAbsolutePath().normalize();
        String clientId = args[2].trim();
        String defaultHost = args.length >= 4 ? args[3].trim() : "";
        String serverListUrl = args.length >= 5 ? args[4].trim() : "";
        String measurementOverride = args.length >= 6 && !"-".equals(args[5].trim())
                ? normalizeHex(args[5])
                : "";
        int defaultPort = args.length >= 7 ? Integer.parseInt(args[6]) : 19129;
        if (clientId.isEmpty()) {
            throw new IllegalArgumentException("clientId is empty");
        }
        if (defaultHost.isEmpty() != serverListUrl.isEmpty()) {
            throw new IllegalArgumentException("defaultHost va serverListUrl phai cung co hoac cung rong");
        }
        if (!measurementOverride.isEmpty() && measurementOverride.length() != 64) {
            throw new IllegalArgumentException("measurementOverride phai la SHA-256 hex 64 ky tu");
        }
        if (defaultPort < 1 || defaultPort > 65535) {
            throw new IllegalArgumentException("defaultPort phai nam trong khoang 1-65535");
        }
        Map<String, EntryData> entries = readJar(inputJar);
        ensureLauncherResources(entries);
        if (!defaultHost.isEmpty()) {
            EntryData serverListEntry = entries.get(SERVER_LIST_CLASS_ENTRY);
            if (serverListEntry == null) {
                throw new IllegalStateException("Khong tim thay " + SERVER_LIST_CLASS_ENTRY + " trong " + inputJar);
            }
            entries.put(SERVER_LIST_CLASS_ENTRY, serverListEntry.withBytes(
                    patchServerListClass(serverListEntry.bytes, defaultHost, serverListUrl, defaultPort)
            ));
            EntryData canvasEntry = entries.get(CANVAS_CLASS_ENTRY);
            if (canvasEntry == null) {
                throw new IllegalStateException("Khong tim thay " + CANVAS_CLASS_ENTRY + " trong " + inputJar);
            }
            entries.put(CANVAS_CLASS_ENTRY, canvasEntry.withBytes(
                    patchCanvasConnectClass(canvasEntry.bytes, defaultPort)
            ));
        }
        EntryData goEntry = entries.get(GO_CLASS_ENTRY);
        if (goEntry == null) {
            throw new IllegalStateException("Khong tim thay " + GO_CLASS_ENTRY + " trong " + inputJar);
        }

        List<String> monitoredEntries = buildMonitoredEntries(entries);
        String measurement = measurementOverride.isEmpty()
                ? computeMeasurement(entries, monitoredEntries)
                : measurementOverride;
        String platformToken = buildPlatformToken(clientId, measurement);
        entries.put(GO_CLASS_ENTRY, goEntry.withBytes(patchGoClass(goEntry.bytes, platformToken)));

        Files.createDirectories(outputJar.getParent());
        writeJar(outputJar, entries);

        String fileHash = toHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(outputJar)));
        System.out.println("CLIENT_ID=" + clientId);
        if (!defaultHost.isEmpty()) {
            System.out.println("DEFAULT_HOST=" + defaultHost);
            System.out.println("DEFAULT_PORT=" + defaultPort);
            System.out.println("SERVER_LIST_URL=" + serverListUrl);
        }
        System.out.println("MEASUREMENT=" + measurement);
        System.out.println("OUTPUT_SHA256=" + fileHash);
        System.out.println("OUTPUT_JAR=" + outputJar);
    }

    private static Map<String, EntryData> readJar(Path jarPath) throws IOException {
        Map<String, EntryData> entries = new LinkedHashMap<String, EntryData>();
        try (JarInputStream input = new JarInputStream(Files.newInputStream(jarPath))) {
            JarEntry jarEntry;
            while ((jarEntry = input.getNextJarEntry()) != null) {
                if (jarEntry.isDirectory()) {
                    continue;
                }
                entries.put(jarEntry.getName(), new EntryData(
                        jarEntry.getName(),
                        readAllBytes(input),
                        jarEntry.getTime(),
                        jarEntry.getMethod(),
                        jarEntry.getComment(),
                        jarEntry.getExtra()
                ));
            }
        }
        return entries;
    }

    private static void ensureLauncherResources(Map<String, EntryData> entries) {
        if (entries.containsKey(MENU_ICON_ALIAS_ENTRY)) {
            return;
        }
        EntryData menuIcon = entries.get(MENU_ICON_ENTRY);
        if (menuIcon == null) {
            return;
        }
        entries.put(MENU_ICON_ALIAS_ENTRY, menuIcon.withName(MENU_ICON_ALIAS_ENTRY));
    }

    private static byte[] patchGoClass(byte[] sourceBytes, final String platformToken) {
        ClassReader reader = new ClassReader(sourceBytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        final boolean[] replaced = new boolean[]{false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM8, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!"a".equals(name) || !"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V".equals(descriptor)) {
                    return delegate;
                }
                return new MethodVisitor(Opcodes.ASM8, delegate) {
                    private boolean pendingPlatformLiteral;

                    private void flushPendingLiteral() {
                        if (this.pendingPlatformLiteral) {
                            super.visitLdcInsn("microedition.platform");
                            this.pendingPlatformLiteral = false;
                        }
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        if (value instanceof String && "microedition.platform".equals(value)) {
                            this.pendingPlatformLiteral = true;
                            return;
                        }
                        this.flushPendingLiteral();
                        super.visitLdcInsn(value);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (this.pendingPlatformLiteral
                                && opcode == Opcodes.INVOKESTATIC
                                && "java/lang/System".equals(owner)
                                && "getProperty".equals(name)
                                && "(Ljava/lang/String;)Ljava/lang/String;".equals(descriptor)) {
                            this.pendingPlatformLiteral = false;
                            replaced[0] = true;
                            super.visitLdcInsn(platformToken);
                            return;
                        }
                        this.flushPendingLiteral();
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        this.flushPendingLiteral();
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        this.flushPendingLiteral();
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
                        this.flushPendingLiteral();
                        super.visitVarInsn(opcode, varIndex);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        this.flushPendingLiteral();
                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        this.flushPendingLiteral();
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, jdk.internal.org.objectweb.asm.Label label) {
                        this.flushPendingLiteral();
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitIincInsn(int varIndex, int increment) {
                        this.flushPendingLiteral();
                        super.visitIincInsn(varIndex, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, jdk.internal.org.objectweb.asm.Label defaultLabel, jdk.internal.org.objectweb.asm.Label... labels) {
                        this.flushPendingLiteral();
                        super.visitTableSwitchInsn(min, max, defaultLabel, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(jdk.internal.org.objectweb.asm.Label defaultLabel, int[] keys, jdk.internal.org.objectweb.asm.Label[] labels) {
                        this.flushPendingLiteral();
                        super.visitLookupSwitchInsn(defaultLabel, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
                        this.flushPendingLiteral();
                        super.visitMultiANewArrayInsn(descriptor, numDimensions);
                    }

                    @Override
                    public void visitEnd() {
                        this.flushPendingLiteral();
                        super.visitEnd();
                    }
                };
            }
        };
        reader.accept(visitor, 0);
        if (!replaced[0]) {
            throw new IllegalStateException("Khong patch duoc go.class");
        }
        return writer.toByteArray();
    }

    private static byte[] patchServerListClass(
            byte[] sourceBytes,
            final String defaultHost,
            final String serverListUrl,
            final int defaultPort) {
        ClassReader reader = new ClassReader(sourceBytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        final boolean[] replacedHost = new boolean[]{false};
        final boolean[] replacedUrl = new boolean[]{false};
        final boolean[] replacedPort = new boolean[]{false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM8, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                final String methodName = name == null ? "" : name;
                if ("f".equals(methodName) && "()V".equals(descriptor)) {
                    replacedUrl[0] = true;
                    delegate.visitCode();
                    delegate.visitInsn(Opcodes.RETURN);
                    delegate.visitMaxs(0, 1);
                    delegate.visitEnd();
                    return new MethodVisitor(Opcodes.ASM8) {
                    };
                }
                return new MethodVisitor(Opcodes.ASM8, delegate) {
                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        if (isHostInitMethod(methodName)
                                && opcode == Opcodes.SIPUSH
                                && operand == 19129) {
                            replacedPort[0] = true;
                            super.visitIntInsn(opcode, (short) defaultPort);
                            return;
                        }
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        if (value instanceof String) {
                            String literal = (String) value;
                            if (isHostInitMethod(methodName) && isIpv4Literal(literal)) {
                                replacedHost[0] = true;
                                super.visitLdcInsn(defaultHost);
                                return;
                            }
                            if ("f".equals(methodName) && isServerListUrlLiteral(literal)) {
                                replacedUrl[0] = true;
                                super.visitLdcInsn(serverListUrl);
                                return;
                            }
                        }
                        super.visitLdcInsn(value);
                    }
                };
            }
        };
        reader.accept(visitor, 0);
        if (!replacedHost[0]) {
            throw new IllegalStateException("Khong patch duoc host mac dinh trong yv.class");
        }
        if (!replacedUrl[0]) {
            throw new IllegalStateException("Khong patch duoc server list url trong yv.class");
        }
        if (!replacedPort[0]) {
            throw new IllegalStateException("Khong patch duoc port mac dinh trong yv.class");
        }
        return writer.toByteArray();
    }

    private static byte[] patchCanvasConnectClass(byte[] sourceBytes, final int defaultPort) {
        ClassReader reader = new ClassReader(sourceBytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        final boolean[] replacedPort = new boolean[]{false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM8, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!"b".equals(name) || !"()V".equals(descriptor)) {
                    return delegate;
                }
                return new MethodVisitor(Opcodes.ASM8, delegate) {
                    private int pendingPortRead;

                    private void flushPendingPortRead() {
                        if (this.pendingPortRead >= 1) {
                            super.visitFieldInsn(Opcodes.GETSTATIC, "yv", "d", "[S");
                        }
                        if (this.pendingPortRead >= 2) {
                            super.visitFieldInsn(Opcodes.GETSTATIC, "yv", "a", "I");
                        }
                        this.pendingPortRead = 0;
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String fieldName, String fieldDescriptor) {
                        if (this.pendingPortRead == 0
                                && opcode == Opcodes.GETSTATIC
                                && "yv".equals(owner)
                                && "d".equals(fieldName)
                                && "[S".equals(fieldDescriptor)) {
                            this.pendingPortRead = 1;
                            return;
                        }
                        if (this.pendingPortRead == 1
                                && opcode == Opcodes.GETSTATIC
                                && "yv".equals(owner)
                                && "a".equals(fieldName)
                                && "I".equals(fieldDescriptor)) {
                            this.pendingPortRead = 2;
                            return;
                        }
                        this.flushPendingPortRead();
                        super.visitFieldInsn(opcode, owner, fieldName, fieldDescriptor);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        if (this.pendingPortRead == 2 && opcode == Opcodes.SALOAD) {
                            this.pendingPortRead = 0;
                            replacedPort[0] = true;
                            super.visitLdcInsn(Integer.valueOf(defaultPort));
                            return;
                        }
                        this.flushPendingPortRead();
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitEnd() {
                        this.flushPendingPortRead();
                        super.visitEnd();
                    }
                };
            }
        };
        reader.accept(visitor, 0);
        if (!replacedPort[0]) {
            throw new IllegalStateException("Khong patch duoc port ket noi trong acv.class");
        }
        return writer.toByteArray();
    }

    private static boolean isHostInitMethod(String methodName) {
        return "<clinit>".equals(methodName) || "cinitclone".equals(methodName);
    }

    private static boolean isIpv4Literal(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        int dots = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '.') {
                dots++;
                continue;
            }
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return dots == 3;
    }

    private static boolean isServerListUrlLiteral(String value) {
        return value != null
                && value.startsWith("http://")
                && value.endsWith("/NQSH2.txt");
    }

    private static List<String> buildMonitoredEntries(Map<String, EntryData> entries) {
        List<String> monitored = new ArrayList<String>();
        for (String name : entries.keySet()) {
            if (AUTH_CLASS_ENTRY.equals(name)) {
                continue;
            }
            if (GO_CLASS_ENTRY.equals(name)) {
                continue;
            }
            if (name.startsWith("META-INF/")) {
                continue;
            }
            monitored.add(name);
        }
        Collections.sort(monitored);
        return monitored;
    }

    private static String buildPlatformToken(String clientId, String measurement) {
        return "nokia/1/1|kpah-jar-v1|" + clientId + "|" + measurement;
    }

    private static String computeMeasurement(Map<String, EntryData> entries, List<String> monitoredEntries) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (int i = 0; i < monitoredEntries.size(); i++) {
            String name = monitoredEntries.get(i);
            EntryData entryData = entries.get(name);
            if (entryData == null) {
                throw new IllegalStateException("Missing monitored entry " + name);
            }
            digest.update(name.getBytes(StandardCharsets.UTF_8));
            digest.update((byte) 0);
            digest.update(entryData.bytes);
            digest.update((byte) 0xFF);
        }
        return toHex(digest.digest());
    }

    private static byte[] compileAuthClass(String clientId, List<String> monitoredEntries) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Khong tim thay JavaCompiler");
        }
        Path workDir = Files.createTempDirectory("kpahauth-build");
        Path sourceFile = workDir.resolve(AUTH_CLASS_NAME + ".java");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(classesDir);
        Files.write(sourceFile, buildAuthSource(clientId, monitoredEntries).getBytes(StandardCharsets.UTF_8));

        StringWriter compilerOut = new StringWriter();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8)) {
            List<String> options = new ArrayList<String>();
            options.add("-encoding");
            options.add("UTF-8");
            options.add("-source");
            options.add("1.8");
            options.add("-target");
            options.add("1.8");
            options.add("-d");
            options.add(classesDir.toString());
            boolean ok = compiler.getTask(
                    new PrintWriter(compilerOut),
                    fileManager,
                    null,
                    options,
                    null,
                    fileManager.getJavaFileObjects(sourceFile.toFile())
            ).call().booleanValue();
            if (!ok) {
                throw new IllegalStateException("Compile kpahauth.java that bai:\n" + compilerOut.toString());
            }
        }
        return Files.readAllBytes(classesDir.resolve(AUTH_CLASS_ENTRY));
    }

    private static String buildAuthSource(String clientId, List<String> monitoredEntries) {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.io.ByteArrayOutputStream;\n");
        sb.append("import java.io.InputStream;\n");
        sb.append("import java.security.MessageDigest;\n");
        sb.append("public final class ").append(AUTH_CLASS_NAME).append(" {\n");
        sb.append("    private static final String CLIENT_ID = \"").append(escapeJava(clientId)).append("\";\n");
        sb.append("    private static final String[] ENTRIES = new String[]{\n");
        for (int i = 0; i < monitoredEntries.size(); i++) {
            sb.append("        \"").append(escapeJava(monitoredEntries.get(i))).append("\"");
            if (i < monitoredEntries.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("    };\n");
        sb.append("    private ").append(AUTH_CLASS_NAME).append("() {}\n");
        sb.append("    public static String platform() {\n");
        sb.append("        return \"nokia/1/1|kpah-jar-v1|\" + CLIENT_ID + \"|\" + digestAll();\n");
        sb.append("    }\n");
        sb.append("    private static String digestAll() {\n");
        sb.append("        try {\n");
        sb.append("            MessageDigest digest = MessageDigest.getInstance(\"SHA-256\");\n");
        sb.append("            for (int i = 0; i < ENTRIES.length; i++) {\n");
        sb.append("                updateUtf8(digest, ENTRIES[i]);\n");
        sb.append("                digest.update((byte)0);\n");
        sb.append("                byte[] data = readResource(ENTRIES[i]);\n");
        sb.append("                if (data == null) {\n");
        sb.append("                    return \"MISSING\";\n");
        sb.append("                }\n");
        sb.append("                digest.update(data);\n");
        sb.append("                digest.update((byte)255);\n");
        sb.append("            }\n");
        sb.append("            return toHex(digest.digest());\n");
        sb.append("        } catch (Throwable t) {\n");
        sb.append("            return \"ERROR\";\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("    private static void updateUtf8(MessageDigest digest, String value) throws Exception {\n");
        sb.append("        digest.update(value.getBytes(\"UTF-8\"));\n");
        sb.append("    }\n");
        sb.append("    private static byte[] readResource(String name) throws Exception {\n");
        sb.append("        InputStream input = ").append(AUTH_CLASS_NAME).append(".class.getResourceAsStream(\"/\" + name);\n");
        sb.append("        if (input == null) {\n");
        sb.append("            return null;\n");
        sb.append("        }\n");
        sb.append("        try {\n");
        sb.append("            ByteArrayOutputStream output = new ByteArrayOutputStream();\n");
        sb.append("            byte[] buffer = new byte[4096];\n");
        sb.append("            int read;\n");
        sb.append("            while ((read = input.read(buffer)) != -1) {\n");
        sb.append("                output.write(buffer, 0, read);\n");
        sb.append("            }\n");
        sb.append("            return output.toByteArray();\n");
        sb.append("        } finally {\n");
        sb.append("            input.close();\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("    private static String toHex(byte[] data) {\n");
        sb.append("        StringBuffer sb = new StringBuffer(data.length * 2);\n");
        sb.append("        for (int i = 0; i < data.length; i++) {\n");
        sb.append("            int value = data[i] & 255;\n");
        sb.append("            if (value < 16) {\n");
        sb.append("                sb.append('0');\n");
        sb.append("            }\n");
        sb.append("            sb.append(Integer.toHexString(value).toUpperCase());\n");
        sb.append("        }\n");
        sb.append("        return sb.toString();\n");
        sb.append("    }\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static void writeJar(Path outputJar, Map<String, EntryData> entries) throws IOException {
        List<String> names = new ArrayList<String>(entries.keySet());
        Collections.sort(names);
        try (OutputStream fileOut = Files.newOutputStream(outputJar);
             JarOutputStream jarOut = new JarOutputStream(fileOut)) {
            for (int i = 0; i < names.size(); i++) {
                EntryData entryData = entries.get(names.get(i));
                JarEntry entry = new JarEntry(entryData.name);
                if (entryData.time > 0L) {
                    entry.setTime(entryData.time);
                }
                if (entryData.comment != null) {
                    entry.setComment(entryData.comment);
                }
                if (entryData.extra != null) {
                    entry.setExtra(entryData.extra);
                }
                jarOut.putNextEntry(entry);
                jarOut.write(entryData.bytes);
                jarOut.closeEntry();
            }
        }
    }

    private static byte[] readAllBytes(JarInputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int read;
        while ((read = input.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }
        return output.toByteArray();
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xFF;
            if (value < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }

    private static String normalizeHex(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }

    private static String escapeJava(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '"':
                    sb.append("\\\"");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (ch < 32 || ch > 126) {
                        String hex = Integer.toHexString(ch).toUpperCase();
                        while (hex.length() < 4) {
                            hex = "0" + hex;
                        }
                        sb.append("\\u").append(hex);
                    } else {
                        sb.append(ch);
                    }
                    break;
            }
        }
        return sb.toString();
    }

    private static final class EntryData {
        final String name;
        final byte[] bytes;
        final long time;
        final int method;
        final String comment;
        final byte[] extra;

        EntryData(String name, byte[] bytes, long time, int method, String comment, byte[] extra) {
            this.name = name;
            this.bytes = bytes;
            this.time = time;
            this.method = method;
            this.comment = comment;
            this.extra = extra;
        }

        EntryData withBytes(byte[] newBytes) {
            return new EntryData(this.name, newBytes, this.time, this.method, this.comment, this.extra);
        }

        EntryData withName(String newName) {
            return new EntryData(newName, this.bytes, this.time, this.method, this.comment, this.extra);
        }
    }
}
