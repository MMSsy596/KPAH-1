/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 */
package real;

import data.NewClan;
import real.Char;

public class Skill_Clan {
    public static String[] SKILL_NAME_CLAN = new String[]{"Th\u0103ng c\u1ea5p hi\u1ec7u qu\u1ea3", "T\u1ea5n c\u00f4ng hi\u1ec7u qu\u1ea3", "Thu\u1eadt c\u01b0\u1eddng th\u00e2n", "B\u1ed9c ph\u00e1t ti\u1ec1m n\u0103ng"};
    public static String[] SKILL_DECRIPTION_CLAN = new String[]{"T\u0103ng %xp nh\u1eadn \u0111\u01b0\u1ee3c khi \u0111\u00e1nh qu\u00e1i ", "T\u0103ng % ch\u00ed m\u1ea1ng khi \u0111\u00e1nh qu\u00e1i ", "T\u0103ng HP cho nh\u00e2n v\u1eadt ", "B\u1ed9c ph\u00e1t ti\u1ec1m n\u0103ng "};
    public static String[] SKILL_DECRIPTION_CLAN_MEMBER = new String[]{"T\u0103ng %xu nh\u1eb7t \u0111\u01b0\u1ee3c khi \u0111\u00e1nh qu\u00e1i ", "T\u0103ng \u0111i\u1ec3m ph\u00f2ng th\u1ee7 v\u1eadt khi \u0111\u00e1nh qu\u00e1i ", "T\u0103ng \u0111i\u1ec3m ph\u00f2ng th\u1ee7 ma khi \u0111\u00e1nh qu\u00e1i ", "T\u0103ng % khi s\u1eed d\u1ee5ng HP "};
    public static String[] SKILL_NAME_CLAN_MEMBER = new String[]{"Th\u1ea7n t\u00e0i h\u1ed7 tr\u1ee3", "Ph\u00f2ng th\u1ee7 v\u1eadt l\u00fd hi\u1ec7u qu\u1ea3", "Ph\u00f2ng th\u1ee7 ma ph\u00e1p hi\u1ec7u qu\u1ea3", "Tr\u1ecb li\u1ec7u th\u1ea7n th\u00e1nh"};
    public static byte[][] ID_IMAGE_SKILL_CLAN;
    public static short[][] SKILL_CLAN;
    public static short[][] SKILL_CLAN_MEMBER;
    public static byte[][] LEVEL_SKILL_CLAN;
    public static byte[][] LEVEL_SKILL_CLAN_MEMBER;
    public static short[][] TIME_SKILL_CLAN;
    public static short[][] TIME_SKILL_CLAN_MEMBER;
    public static short[][] DEVOTE_BUY_SKILL_CLAN;
    public static short[][] DEVOTE_BUY_SKILL_CLAN_MEMBER;

    static {
        byte[][] byArrayArray = new byte[2][];
        byte[] byArray = new byte[11];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{4, 5, 6, 7, 4, 4, 4, 4, 4, 4, 4};
        ID_IMAGE_SKILL_CLAN = byArrayArray;
        SKILL_CLAN = new short[][]{{2, 4, 6, 8}, {1, 2, 3, 4}, {1000, 2000, 3000, 4000}, {1, 2, 3, 4}};
        SKILL_CLAN_MEMBER = new short[][]{{5, 7, 10, 15}, {5, 10, 15, 20}, {5, 10, 15, 20}, {3, 5, 7, 10}};
        LEVEL_SKILL_CLAN = new byte[][]{{10, 15, 30, 50}, {15, 20, 35, 45}, {20, 25, 35, 40}, {25, 30, 40, 45}};
        LEVEL_SKILL_CLAN_MEMBER = new byte[][]{{15, 20, 25, 45}, {25, 30, 35, 45}, {30, 35, 40, 50}, {10, 15, 20, 40}};
        TIME_SKILL_CLAN = new short[][]{{330, 360, 390, 420, 450, 480, 510, 540, 570, 600}, {330, 360, 390, 420, 450, 480, 510, 540, 570, 600}, {330, 360, 390, 420, 450, 480, 510, 540, 570, 600}, {330, 360, 390, 420, 450, 480, 510, 540, 570, 600}};
        TIME_SKILL_CLAN_MEMBER = new short[][]{{120, 120, 120, 120, 120, 120, 120, 120, 120, 120}, {120, 120, 120, 120, 120, 120, 120, 120, 120, 120}, {120, 120, 120, 120, 120, 120, 120, 120, 120, 120}, {120, 120, 120, 120, 120, 120, 120, 120, 120, 120}};
        DEVOTE_BUY_SKILL_CLAN = new short[][]{{1000, 3000, 5000, 7000}, {1000, 3000, 5000, 7000}, {1000, 3000, 5000, 7000}, {1000, 3000, 5000, 7000}};
        DEVOTE_BUY_SKILL_CLAN_MEMBER = new short[][]{{200, 300, 400, 500}, {200, 300, 400, 500}, {200, 300, 400, 500}, {200, 300, 400, 500}};
    }

    public static int getEffectSkill(byte idSkill, byte lvSkill, byte type) {
        if (type == 0) {
            return SKILL_CLAN[idSkill][lvSkill];
        }
        return SKILL_CLAN_MEMBER[idSkill][lvSkill];
    }

    public static String[] getListSkillClan(int type, int idSkill) {
        String[] st = new String[]{""};
        if (type == 0) {
            st = new String[SKILL_CLAN[idSkill].length];
            int i = 0;
            while (i < st.length) {
                st[i] = String.valueOf(SKILL_NAME_CLAN[idSkill]) + " lv" + LEVEL_SKILL_CLAN[idSkill][i] + " ch:" + DEVOTE_BUY_SKILL_CLAN[idSkill][i] + " tg:" + TIME_SKILL_CLAN[idSkill][i] + "p";
                ++i;
            }
        } else {
            st = new String[SKILL_CLAN[idSkill].length];
            int i = 0;
            while (i < st.length) {
                st[i] = String.valueOf(SKILL_NAME_CLAN_MEMBER[idSkill]) + " lv" + LEVEL_SKILL_CLAN_MEMBER[idSkill][i] + " ch:" + DEVOTE_BUY_SKILL_CLAN_MEMBER[idSkill][i] + " tg:" + TIME_SKILL_CLAN_MEMBER[idSkill][i] + "p";
                ++i;
            }
        }
        return st;
    }

    public static boolean canLearnSkill(Char p, int idSkill, int levelSkill, int type) {
        if (type == 0) {
            NewClan clan = NewClan.getClan(p.idClan);
            if (clan != null && clan.pDevote >= (long)DEVOTE_BUY_SKILL_CLAN[idSkill][levelSkill]) {
                clan.setSkillClan(idSkill, TIME_SKILL_CLAN[idSkill][levelSkill], DEVOTE_BUY_SKILL_CLAN[idSkill][levelSkill], levelSkill);
                return true;
            }
        } else if (p.pointCongHienClan >= DEVOTE_BUY_SKILL_CLAN_MEMBER[idSkill][levelSkill]) {
            p.setSkillClan(idSkill + 10, TIME_SKILL_CLAN_MEMBER[idSkill][levelSkill], (int)DEVOTE_BUY_SKILL_CLAN_MEMBER[idSkill][levelSkill], (byte)levelSkill);
            return true;
        }
        return false;
    }
}

