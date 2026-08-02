package real;

import java.util.Hashtable;
import java.util.Properties;

/**
 * Quản lý các hệ số cày cuốc có thể thay đổi khi máy chủ đang chạy.
 * Chỉ quái thường được tác động để không làm lệch cơ chế boss, sự kiện và PvP.
 */
public final class GrindingTuningService {

    public static final int DEFAULT_PERCENT = 100;
    public static final int MIN_HP_PERCENT = 10;
    public static final int MAX_PERCENT = 1000;
    public static final int MIN_DENSITY_PERCENT = 25;
    public static final int MAX_DENSITY_PERCENT = 300;

    private static volatile int dropRatePercent = DEFAULT_PERCENT;
    private static volatile int monsterDamagePercent = DEFAULT_PERCENT;
    private static volatile int monsterHpPercent = DEFAULT_PERCENT;
    private static volatile int expPercent = DEFAULT_PERCENT;
    private static volatile int monsterDensityPercent = DEFAULT_PERCENT;

    private GrindingTuningService() {
    }

    public static void load(Properties properties) {
        if (properties == null) {
            return;
        }
        dropRatePercent = readPercent(properties, "sv.grindingDropRatePercent", DEFAULT_PERCENT, 0);
        monsterDamagePercent = readPercent(properties, "sv.grindingMonsterDamagePercent", DEFAULT_PERCENT, 0);
        monsterHpPercent = readPercent(properties, "sv.grindingMonsterHpPercent", DEFAULT_PERCENT, MIN_HP_PERCENT);
        expPercent = readPercent(properties, "sv.grindingExpPercent", DEFAULT_PERCENT, 0);
        monsterDensityPercent = readPercent(
                properties,
                "sv.grindingMonsterDensityPercent",
                DEFAULT_PERCENT,
                MIN_DENSITY_PERCENT,
                MAX_DENSITY_PERCENT
        );
    }

    public static Settings snapshot() {
        return new Settings(dropRatePercent, monsterDamagePercent, monsterHpPercent, expPercent, monsterDensityPercent);
    }

    public static ApplyResult apply(Settings settings) {
        if (settings == null) {
            return new ApplyResult(0, 0, 0, 0);
        }
        dropRatePercent = clamp(settings.dropRatePercent, 0, MAX_PERCENT);
        monsterDamagePercent = clamp(settings.monsterDamagePercent, 0, MAX_PERCENT);
        monsterHpPercent = clamp(settings.monsterHpPercent, MIN_HP_PERCENT, MAX_PERCENT);
        expPercent = clamp(settings.expPercent, 0, MAX_PERCENT);
        monsterDensityPercent = clamp(settings.monsterDensityPercent, MIN_DENSITY_PERCENT, MAX_DENSITY_PERCENT);

        int[] densityChanges = refreshMonsterDensity();
        int updatedMonsters = refreshLivingMonsters();
        return new ApplyResult(updatedMonsters, densityChanges[0], densityChanges[1], densityChanges[2]);
    }

    public static boolean isGrindingMonster(Monster monster) {
        return monster != null
                && monster.getClass() == Monster.class
                && !monster.isBoss
                && !monster.isCongThanh()
                && !monster.isMaterialMons();
    }

    public static int getMonsterHpPercent() {
        return monsterHpPercent;
    }

    public static int getMonsterDensityPercent() {
        return monsterDensityPercent;
    }

    public static int scaleMonsterDamage(Monster monster, int damage) {
        if (damage <= 0 || !isGrindingMonster(monster)) {
            return Math.max(0, damage);
        }
        return scaleValue(damage, monsterDamagePercent, true);
    }

    public static int scaleMonsterExp(Monster monster, int exp) {
        if (exp <= 0 || !isGrindingMonster(monster)) {
            return Math.max(0, exp);
        }
        return scaleValue(exp, expPercent, false);
    }

    public static int scaleRegularDropChance(Monster monster, int chancePerMillion) {
        if (chancePerMillion <= 0 || !isGrindingMonster(monster)) {
            return Math.max(0, chancePerMillion);
        }
        long scaled = (long) chancePerMillion * dropRatePercent / DEFAULT_PERCENT;
        return (int) Math.min(1_000_000L, Math.max(0L, scaled));
    }

    private static int refreshLivingMonsters() {
        int updated = 0;
        try {
            for (Map map : RealController.mapList.values()) {
                if (map == null) {
                    continue;
                }
                int totalRegions = map.nRegion > 0 ? map.nRegion : 1;
                for (int region = 0; region < totalRegions; region++) {
                    for (int country = 0; country < 3; country++) {
                        Hashtable<Short, Monster> monsters;
                        try {
                            monsters = map.getAllMons(country, region);
                        } catch (Exception ignored) {
                            continue;
                        }
                        if (monsters == null) {
                            continue;
                        }
                        for (Monster monster : monsters.values()) {
                            if (monster != null && monster.refreshGrindingHpTuning()) {
                                updated++;
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            // Một bản đồ đang cập nhật không được làm hỏng cấu hình chung đã áp dụng.
        }
        return updated;
    }

    private static int[] refreshMonsterDensity() {
        int added = 0;
        int removed = 0;
        int affectedMaps = 0;
        try {
            for (Map map : RealController.mapList.values()) {
                if (map == null) {
                    continue;
                }
                int[] changes = map.refreshGrindingMonsterDensity();
                added += changes[0];
                removed += changes[1];
                if (changes[0] > 0 || changes[1] > 0) {
                    affectedMaps++;
                }
            }
        } catch (Exception ignored) {
            // Một bản đồ đang cập nhật không được làm hỏng cấu hình chung đã áp dụng.
        }
        return new int[]{added, removed, affectedMaps};
    }

    private static int scaleValue(int value, int percent, boolean keepAtLeastOne) {
        if (percent <= 0) {
            return 0;
        }
        long scaled = (long) value * percent / DEFAULT_PERCENT;
        if (keepAtLeastOne && scaled <= 0L) {
            scaled = 1L;
        }
        return (int) Math.min(Integer.MAX_VALUE, Math.max(0L, scaled));
    }

    private static int readPercent(Properties properties, String key, int defaultValue, int minValue) {
        return readPercent(properties, key, defaultValue, minValue, MAX_PERCENT);
    }

    private static int readPercent(Properties properties, String key, int defaultValue, int minValue, int maxValue) {
        try {
            return clamp(Integer.parseInt(properties.getProperty(key, Integer.toString(defaultValue)).trim()), minValue, maxValue);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static final class Settings {
        public final int dropRatePercent;
        public final int monsterDamagePercent;
        public final int monsterHpPercent;
        public final int expPercent;
        public final int monsterDensityPercent;

        public Settings(int dropRatePercent, int monsterDamagePercent, int monsterHpPercent, int expPercent, int monsterDensityPercent) {
            this.dropRatePercent = dropRatePercent;
            this.monsterDamagePercent = monsterDamagePercent;
            this.monsterHpPercent = monsterHpPercent;
            this.expPercent = expPercent;
            this.monsterDensityPercent = monsterDensityPercent;
        }
    }

    public static final class ApplyResult {
        public final int updatedMonsters;
        public final int addedMonsterPositions;
        public final int removedMonsterPositions;
        public final int affectedMaps;

        public ApplyResult(int updatedMonsters, int addedMonsterPositions, int removedMonsterPositions, int affectedMaps) {
            this.updatedMonsters = updatedMonsters;
            this.addedMonsterPositions = addedMonsterPositions;
            this.removedMonsterPositions = removedMonsterPositions;
            this.affectedMaps = affectedMaps;
        }
    }
}
