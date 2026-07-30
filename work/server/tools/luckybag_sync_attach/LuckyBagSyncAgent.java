package maint;

import data.Database;
import real.Char;
import real.CharManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;

public final class LuckyBagSyncAgent {

    private LuckyBagSyncAgent() {
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        run(agentArgs);
    }

    private static void run(String agentArgs) {
        Path resultFile = null;
        try {
            Properties properties = loadConfig(agentArgs);
            resultFile = optionalPath(properties.getProperty("resultFile"));
            String resetKey = safeTrim(properties.getProperty("resetKey"));
            int limit = Char.getLuckyBagFarmLimitPerDay();
            Map<String, Integer> expectedByName = parseExpectedCounts(properties, limit);
            if (expectedByName.isEmpty()) {
                throw new IllegalArgumentException("Khong co du lieu dong bo lucky bag.");
            }

            Vector<Char> onlinePlayers = (Vector<Char>) CharManager.instance.vChars.clone();
            List<String> adjusted = new ArrayList<String>();
            List<String> skipped = new ArrayList<String>();
            for (Char player : onlinePlayers) {
                if (player == null || player.charname == null) {
                    continue;
                }
                String key = player.charname.trim().toLowerCase();
                Integer expected = expectedByName.get(key);
                if (expected == null) {
                    continue;
                }

                int liveCount = clampCount(player.soHopQuaNongDanNgay, limit);
                if (liveCount > expected.intValue()) {
                    player.soHopQuaNongDanNgay = expected.intValue();
                    if (expected.intValue() < limit || player.countBaoMayManOpen < Char.getLuckyBagOpenLimitPerDay()) {
                        player.timeDat50HopQuaNongDan = 0L;
                        player.timeOnlineNongDanNgay = 0L;
                    }
                    if (!resetKey.isEmpty()) {
                        player.dayLuckyBagReset = resetKey;
                    }
                    Database.instance.saveCharAuto(player);
                    adjusted.add(player.charname + ":" + liveCount + "->" + expected.intValue());
                } else {
                    skipped.add(player.charname + ":" + liveCount + "->" + expected.intValue());
                }
            }

            StringBuilder output = new StringBuilder();
            output.append("ok=1\n");
            output.append("limit=").append(limit).append('\n');
            output.append("tracked=").append(expectedByName.size()).append('\n');
            output.append("adjusted=").append(adjusted.size()).append('\n');
            for (String line : adjusted) {
                output.append("adjusted_item=").append(line).append('\n');
            }
            for (String line : skipped) {
                output.append("skipped_item=").append(line).append('\n');
            }
            writeResult(resultFile, output.toString());
            System.out.println("[luckybag-sync-agent] adjusted=" + adjusted.size());
        } catch (Throwable throwable) {
            writeFailure(resultFile, throwable);
            System.err.println("[luckybag-sync-agent] failed: " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private static Properties loadConfig(String agentArgs) throws IOException {
        if (agentArgs == null || agentArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("Thieu duong dan config cho lucky bag sync agent.");
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

    private static Map<String, Integer> parseExpectedCounts(Properties properties, int limit) {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (String key : properties.stringPropertyNames()) {
            if (!key.startsWith("count.")) {
                continue;
            }
            String name = safeTrim(key.substring("count.".length())).toLowerCase();
            if (name.isEmpty()) {
                continue;
            }
            int expected = clampCount(parseIntSafe(properties.getProperty(key)), limit);
            result.put(name, expected);
        }
        return result;
    }

    private static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(safeTrim(value));
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static int clampCount(int value, int limit) {
        if (value < 0) {
            return 0;
        }
        if (value > limit) {
            return limit;
        }
        return value;
    }

    private static Path optionalPath(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Paths.get(value.trim());
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

    private static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }
        return value.replace('\r', ' ').replace('\n', ' ');
    }
}
