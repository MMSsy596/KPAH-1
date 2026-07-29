/*
 * Decompiled with CFR 0.152.
 */
package real;

public class QuestTemplate {
    public static final byte TYPE_KILL_MONS = 0;
    public static final byte TYPE_TRANSFORM = 1;
    public static final byte TYPE_GET_ITEM = 2;
    public static final byte TYPE_TALK = 3;
    public static final byte TYPE_KILL_MONSTER_LEVEL = 4;
    public static final int MAX_QUEST = 11;
    public static short[] QUEST_ID;
    public static short[] QUEST_TYPE;
    public static String[] title_quest;
    public static short[] npc_quest_receive;
    public static short[][] npc_quest_response;
    public static String[][][] info_response_quest;
    public static short[][] npc_quest;
    public static String[][] info_npc_quest;
    public static short[][] item_receive;
    public static short[][] item_quest;
    public static String[] nameItemQuest;
    public static short[][][] monster_item;
    public static final String[] PORTION_ITEM_NAME;
    private static final int[][][] monster_kill;
    public static String[] decription_quest;
    public static String[][] content_quest;
    public static short[][] gold_xp_gift;
    public static short[][] potionGift;
    public static String xu;
    public static String[] info_Gif;

    static {
        short[] sArray = new short[80];
        sArray[1] = 1;
        sArray[2] = 2;
        sArray[3] = 3;
        sArray[4] = 4;
        sArray[5] = 5;
        sArray[6] = 6;
        sArray[7] = 7;
        sArray[8] = 8;
        sArray[9] = 9;
        sArray[10] = 10;
        sArray[11] = 11;
        sArray[12] = 12;
        sArray[13] = 13;
        sArray[14] = 14;
        sArray[15] = 15;
        sArray[16] = 16;
        sArray[17] = 17;
        sArray[18] = 18;
        sArray[19] = 19;
        sArray[20] = 20;
        sArray[21] = 21;
        sArray[22] = 22;
        sArray[23] = 23;
        sArray[24] = 24;
        sArray[25] = 25;
        sArray[26] = 26;
        sArray[27] = 27;
        sArray[28] = 28;
        sArray[29] = 29;
        sArray[30] = 30;
        sArray[31] = 31;
        sArray[32] = 32;
        sArray[33] = 33;
        sArray[34] = 34;
        sArray[35] = 35;
        sArray[36] = 36;
        sArray[37] = 37;
        sArray[38] = 38;
        sArray[39] = 39;
        sArray[40] = 40;
        sArray[41] = 41;
        sArray[42] = 42;
        sArray[43] = 43;
        sArray[44] = 44;
        sArray[45] = 45;
        sArray[46] = 46;
        sArray[47] = 47;
        sArray[48] = 48;
        sArray[49] = 49;
        sArray[50] = 50;
        sArray[51] = 51;
        sArray[52] = 52;
        sArray[53] = 53;
        sArray[54] = 54;
        sArray[55] = 55;
        sArray[56] = 56;
        sArray[57] = 57;
        sArray[58] = 58;
        sArray[59] = 59;
        sArray[60] = 60;
        sArray[61] = 61;
        sArray[62] = 62;
        sArray[63] = 63;
        sArray[64] = 64;
        sArray[65] = 65;
        sArray[66] = 66;
        sArray[67] = 67;
        sArray[68] = 68;
        sArray[69] = 69;
        sArray[70] = 70;
        sArray[71] = 71;
        sArray[72] = 72;
        sArray[73] = 73;
        sArray[74] = 74;
        sArray[75] = 75;
        sArray[76] = 76;
        sArray[77] = 77;
        sArray[78] = 78;
        sArray[79] = 79;
        QUEST_ID = sArray;
        short[] sArray2 = new short[17];
        sArray2[0] = 3;
        sArray2[1] = 3;
        sArray2[2] = 3;
        sArray2[3] = 3;
        sArray2[4] = 3;
        sArray2[5] = 3;
        sArray2[6] = 3;
        sArray2[7] = 3;
        sArray2[8] = 3;
        sArray2[13] = 1;
        sArray2[16] = 2;
        QUEST_TYPE = sArray2;
        title_quest = new String[]{"Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft ti\u1ec1n b\u1ed1i", "Nhi\u1ec7m v\u1ee5 ra m\u1eaft th\u00e0nh ho\u00e0ng", "N.v\u1ee5 di\u1ec7t s\u00e2u ph\u00e1 h\u1ea1i m\u00f9a m\u00e0ng", "N.v\u1ee5 b\u1eaft r\u1eafn ng\u00e2m r\u01b0\u1ee3u", "N.v\u1ee5 di\u1ec7t chu\u1ed9t ph\u00e1 h\u1ea1i m\u00f9a m\u00e0ng", "N.v\u1ee5 giao th\u01b0 cho H\u1ecfa X\u00edch", "N.v\u1ee5 t\u00ecm nguy\u00ean li\u1ec7u l\u00e0m b\u00e1nh bao", "N.V\u1ee5 s\u1eeda nh\u00e0 cho tr\u01b0\u1edfng l\u00e0ng", "N.V\u1ee5 \u0111i\u1ec1u ch\u1ebf d\u01b0\u1ee3c ph\u1ea9m"};
        npc_quest_receive = new short[]{5, 2, 3, 1, 9, 6, 22, 21, 5, 1, 6, 6, 6, 3, 1, 5, 1};
        npc_quest_response = new short[][]{{5}, {2}, {3}, {1}, {9}, {6}, {22}, {21}, {5}, {1}, {6}, {6}, {6}, {3}, {1}, {5}, {1}};
        info_response_quest = new String[][][]{{new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {new String[0]}, {{"1T\u1ed1t l\u1eafm, ta c\u1ea3m \u01a1n con !"}}, {{"1Ch\u00e0,ng\u01b0\u01a1i l\u00e0m nhanh th\u1ebf!", "1Ta ngh\u0129 c\u0169ng ph\u1ea3i h\u01a1n n\u1eeda ng\u00e0y m\u1edbi xong ch\u1ee9", "1ng\u01b0\u01a1i l\u00e0m ta th\u1eadt b\u1ea5t ng\u1edd !"}}, {{"1Ch\u00e0,th\u1eadt c\u1ea3m \u01a1n ng\u01b0\u01a1i!", "1Ng\u01b0\u01a1i th\u1eadt t\u00e0i gi\u1ecfi v\u00e0 t\u1ed1t b\u1ee5ng."}}, {{"1Nh\u1edd c\u00f3 ng\u01b0\u01a1i m\u00e0 b\u1ecdn chu\u1ed9t \u0111\u00e3 b\u1edbt ph\u00e1 h\u1ea1i", "1thu ho\u1ea1ch c\u0169ng \u0111\u01b0\u1ee3c kh\u00e1 h\u01a1n", "1Th\u1eadt c\u1ea3m \u01a1n ng\u01b0\u01a1i"}}, {{"1\u0110\u01b0\u1ee3c tin l\u00e3o H\u1ecfa X\u00edch kh\u1ecfe m\u1ea1nh ta th\u1eadt an l\u00f2ng", "1Th\u1eadt v\u1ea5t v\u1ea3 cho ng\u01b0\u01a1i", "1\u0110\u00e2y ta t\u1eb7ng ng\u01b0\u01a1i c\u00e1i \u00e1o", "1\u0111\u1ec3 c\u1ea3m \u01a1n ng\u01b0\u01a1i \u0111\u00e3 gi\u00fap ta."}}, {{"1Con v\u1ec1 h\u01a1i tr\u1ec5 so v\u1edbi d\u1ef1 ki\u1ebfn c\u1ee7a ta", "1nh\u01b0ng kh\u00f4ng sao v\u1eabn c\u00f2n k\u1ecbp", "1ta c\u00f3 ch\u00fat qu\u00e0 cho con \u0111\u00e2y."}}, {{"1Th\u1eadt l\u00e0 v\u1ea5t v\u1ea3 cho con", "1Bao gi\u1edd nh\u00e0 s\u1eeda xong th\u00ec \u0111\u1ebfn \u0103n t\u00e2n gia nh\u00e0 ta nh\u00e9."}}, {{"1C\u1ea3m \u01a1n con !", "1H\u00e3y nh\u1eadn ch\u00fat qu\u00e0 c\u1ee7a ta."}}};
        npc_quest = new short[][]{new short[0], new short[0], new short[1], new short[0], new short[0], new short[1], new short[0], new short[0], new short[1], new short[0], new short[0], new short[1], new short[0], {23}, new short[1], new short[1], new short[1], new short[1], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0], new short[0]};
        info_npc_quest = new String[][]{new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], {"1Oh,Thi\u1ebft B\u00ec huynh g\u1eedi th\u01b0 cho ta \u00e0h?", "1Th\u1eadt qu\u00e1 b\u1ea5t ng\u1edd !", "1Ta c\u0169ng \u0111\u1ecbnh \u00edt b\u1eefa n\u1eefa thu x\u1ebfp \u0111\u1ebfn th\u0103m huynh \u1ea5y", "1ch\u01b0a k\u1ecbp \u0111i th\u00ec huynh \u1ea5y \u0111\u00e3 cho ng\u01b0\u1eddi \u0111\u01b0a th\u01b0 \u0111\u1ebfn h\u1ecfi th\u0103m.", "1ta \u0111\u00e3 xem xong th\u01b0 \u0111\u1ec3 ta h\u1ed3i \u00e2m cho huynh \u1ea5y v\u00e0i ch\u1eef, ng\u01b0\u01a1i \u0111\u1ee3i nh\u00e9 !", "1H\u00e3y gi\u00fap ta chuy\u1ec3n l\u1eddi c\u1ea3m \u01a1n \u0111\u1ebfn Thi\u1ebft B\u00ec huynh nh\u00e9", "1Phi\u1ec1n ng\u01b0\u01a1i nh\u1eafn v\u1edbi huynh \u1ea5y", "1v\u00e0i b\u1eefa n\u1eefa ta s\u1ebd \u0111\u1ebfn th\u0103m huynh \u1ea5y sau", "1Th\u1eadt c\u1ea3m \u01a1n ng\u01b0\u01a1i r\u1ea5t nhi\u1ec1u."}, {"15"}, {"16"}, {"17"}, new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0], new String[0]};
        item_receive = new short[][]{{1}};
        item_quest = new short[][]{{1}};
        nameItemQuest = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "L\u00e1 th\u01b0 c\u1ee7a Thi\u1ebft b\u00ec", "15", "16", "N\u1ecdc b\u00f2 c\u1ea1p", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        monster_item = new short[][][]{new short[][]{new short[0]}, new short[][]{{2, 15, 10}}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{new short[0]}, new short[][]{{16, 20, 10}}};
        PORTION_ITEM_NAME = new String[]{"L\u00e1 th\u01b0 c\u1ee7a Thi\u1ebft b\u00ec", "N\u1ecdc \u0111\u1ed9c s\u00e2u", "N\u1ecdc nh\u1ec7n", "L\u00e1 c\u00e2y", "Mai r\u00f9a", "Nanh heo", "Da r\u1eafn", "Ch\u00e2n r\u1ebft"};
        int[][][] nArrayArray = new int[17][][];
        int[][] nArrayArray2 = new int[1][];
        int[] nArray = new int[3];
        nArray[0] = 1;
        nArray[1] = 10;
        nArrayArray2[0] = nArray;
        nArrayArray[0] = nArrayArray2;
        int[][] nArrayArray3 = new int[1][];
        int[] nArray2 = new int[3];
        nArray2[0] = 1;
        nArray2[1] = 10;
        nArrayArray3[0] = nArray2;
        nArrayArray[1] = nArrayArray3;
        int[][] nArrayArray4 = new int[1][];
        int[] nArray3 = new int[3];
        nArray3[0] = 1;
        nArray3[1] = 10;
        nArrayArray4[0] = nArray3;
        nArrayArray[2] = nArrayArray4;
        int[][] nArrayArray5 = new int[1][];
        int[] nArray4 = new int[3];
        nArray4[0] = 1;
        nArray4[1] = 10;
        nArrayArray5[0] = nArray4;
        nArrayArray[3] = nArrayArray5;
        int[][] nArrayArray6 = new int[1][];
        int[] nArray5 = new int[3];
        nArray5[0] = 1;
        nArray5[1] = 10;
        nArrayArray6[0] = nArray5;
        nArrayArray[4] = nArrayArray6;
        int[][] nArrayArray7 = new int[1][];
        int[] nArray6 = new int[3];
        nArray6[0] = 1;
        nArray6[1] = 10;
        nArrayArray7[0] = nArray6;
        nArrayArray[5] = nArrayArray7;
        int[][] nArrayArray8 = new int[1][];
        int[] nArray7 = new int[3];
        nArray7[0] = 1;
        nArray7[1] = 10;
        nArrayArray8[0] = nArray7;
        nArrayArray[6] = nArrayArray8;
        int[][] nArrayArray9 = new int[1][];
        int[] nArray8 = new int[3];
        nArray8[0] = 1;
        nArray8[1] = 10;
        nArrayArray9[0] = nArray8;
        nArrayArray[7] = nArrayArray9;
        int[][] nArrayArray10 = new int[1][];
        int[] nArray9 = new int[3];
        nArray9[0] = 1;
        nArray9[1] = 10;
        nArrayArray10[0] = nArray9;
        nArrayArray[8] = nArrayArray10;
        int[][] nArrayArray11 = new int[1][];
        int[] nArray10 = new int[3];
        nArray10[0] = 1;
        nArray10[1] = 20;
        nArrayArray11[0] = nArray10;
        nArrayArray[9] = nArrayArray11;
        int[][] nArrayArray12 = new int[1][];
        int[] nArray11 = new int[3];
        nArray11[0] = 2;
        nArray11[1] = 40;
        nArrayArray12[0] = nArray11;
        nArrayArray[10] = nArrayArray12;
        int[][] nArrayArray13 = new int[1][];
        int[] nArray12 = new int[3];
        nArray12[0] = 5;
        nArray12[1] = 60;
        nArrayArray13[0] = nArray12;
        nArrayArray[11] = nArrayArray13;
        int[][] nArrayArray14 = new int[1][];
        int[] nArray13 = new int[3];
        nArray13[0] = 9;
        nArray13[1] = 60;
        nArrayArray14[0] = nArray13;
        nArrayArray[12] = nArrayArray14;
        nArrayArray[13] = new int[][]{new int[0]};
        int[][] nArrayArray15 = new int[1][];
        int[] nArray14 = new int[3];
        nArray14[0] = 15;
        nArray14[1] = 60;
        nArrayArray15[0] = nArray14;
        nArrayArray[14] = nArrayArray15;
        int[][] nArrayArray16 = new int[1][];
        int[] nArray15 = new int[3];
        nArray15[0] = 10;
        nArray15[1] = 60;
        nArrayArray16[0] = nArray15;
        nArrayArray[15] = nArrayArray16;
        nArrayArray[16] = new int[][]{new int[0]};
        monster_kill = nArrayArray;
        decription_quest = new String[]{"", "L\u1ea5y 15 Di\u00eam tinh t\u1eeb b\u1ecdn s\u00e2u mang v\u1ec1 cho H\u1eafc Ng\u01b0u", "Giao b\u1ea3ng gi\u00e1 cho d\u00ec \u00dat HP", "", "", "", "", "", "", "Gi\u1ebft 20 con nh\u00edm sau \u0111\u00f3 quay v\u1ec1 g\u1eb7p b\u00e0 T\u00e1m", "Gi\u1ebft 40 con s\u00e2u mang v\u1ec1 cho ph\u00fa \u00f4ng", "Gi\u1ebft 60 con r\u1eafn mang v\u1ec1 cho ph\u00fa \u00f4ng", "Gi\u1ebft 60 con chu\u1ed9t mang v\u1ec1 cho ph\u00fa \u00f4ng", "H\u00e3y mang l\u00e1 th\u01b0 c\u1ee7a ta cho H\u1ecfa X\u00edch \u0111ang s\u1ed1ng \u1edf K\u1ef3 b\u1ed1", "\u0110i \u0111\u1ebfn Th\u1ea1ch Giang v\u00e0 gi\u1ebft 60 con heo mang v\u1ec1 cho ta l\u00e0m nguy\u00ean li\u1ec7u b\u00e1nh bao", "\u0110i \u0111\u1ebfn Ph\u00f9 Li\u1ec7t gi\u1ebft 60 qu\u1ef7 hoa sau \u0111\u00f3 v\u1ec1 b\u00e1o cho tr\u01b0\u1edfng l\u00e0ng", "\u0110i \u0111\u1ebfn \u0110\u00f4ng S\u01a1n t\u00ecm 20 n\u1ecdc \u0111\u1ed9c c\u1ee7a b\u00f2 c\u1ea1p mang v\u1ec1 cho B\u00e0 T\u00e1m."};
        content_quest = new String[][]{{"1Ch\u00e0o con", "0Ch\u00e0o Tr\u01b0\u1edfng l\u00e0ng", "1con l\u00e0 ch\u00e1u \u00f4ng Ba L\u1ef1c tr\u00ean v\u00f9ng L\u00e2m Vi\u00ean ph\u1ea3i kh\u00f4ng", "0D\u1ea1 ph\u1ea3i \u1ea1", "1nh\u01b0 \u0111\u00e3 h\u1ee9a v\u1edbi \u00f4ng \u1ea5y", "1ta s\u1ebd gi\u1edbi thi\u1ec7u con gia nh\u1eadp l\u00e0ng ngh\u0129a s\u1ef9 S\u01a1n Nam", "1Ta lu\u00f4n hoan ngh\u00eanh c\u00e1c thanh ni\u00ean c\u00f3 nhi\u1ec7t huy\u1ebft v\u1edbi giang s\u01a1n \u0111\u1ea5t n\u01b0\u1edbc", "1b\u00eanh v\u1ef1c k\u1ebb y\u1ebfu, s\u1ed1ng v\u00ec l\u1ebd ph\u1ea3i", "1Con h\u00e3y \u0111i ch\u00e0o h\u1ecfi c\u00e1c b\u00e0 con c\u00f4 b\u00e1c trong l\u00e0ng", "1v\u00e0 l\u00e0m m\u1ed9t s\u1ed1 vi\u1ec7c gi\u00fap h\u1ecd coi nh\u01b0 l\u00e0 ch\u00e0o h\u1ecfi", "1v\u00e0 bi\u1ebft \u0111\u01b0\u1ee3c nhi\u1ec1u h\u01a1n v\u1ec1 m\u1ecdi th\u1ee9 nh\u00e9.", "1\u0110\u1ea7u ti\u00ean con h\u00e3y \u0111i g\u1eb7p anh H\u1eafc ng\u01b0u th\u1ee3 r\u00e8n.", "1Anh ta l\u00e0 chuy\u00ean gia v\u0169 kh\u00ed c\u1ee7a l\u00e0ng n\u00e0y \u0111\u00f3.", "0D\u1ea1, ch\u00e0o tr\u01b0\u1edfng l\u00e0ng con \u0111i", "1Uhm"}, {"1Kh\u00e0 kh\u00e0 !!", "1Ta \u0111\u00e3 \u0111\u01b0\u1ee3c nghe qua c\u00e2u chuy\u1ec7n c\u1ee7a ng\u01b0\u01a1i t\u1eeb tr\u01b0\u1edfng l\u00e0ng", "1ng\u01b0\u01a1i c\u00f2n tr\u1ebb m\u00e0 \u0111\u00e3 bi\u1ebft \u00fd th\u1ee9c s\u1ed1ng v\u00ec l\u1ebd ph\u1ea3i", "1\u0110\u00f3 l\u00e0 \u0111i\u1ec1u r\u1ea5t \u0111\u00e1ng qu\u00fd !", "1Ta l\u00e0 H\u1eafc Ng\u01b0u", "1ni\u1ec1m \u0111am m\u00ea c\u1ee7a ta l\u00e0 ch\u1ebf t\u1ea1o v\u00e0 bu\u00f4n b\u00e1n v\u0169 kh\u00ed", "1N\u1ebfu c\u00f3 th\u1eafc m\u1eafc \u0111i\u1ec1u g\u00ec li\u00ean quan \u0111\u1ebfn v\u0169 kh\u00ed ng\u01b0\u01a1i c\u1ee9 \u0111\u1ebfn t\u00ecm ta", "1Gi\u1edd th\u00ec ng\u01b0\u01a1i h\u00e3y \u0111\u1ebfn ra m\u1eaft l\u00e3o Thi\u1ebft B\u00ec \u0111i n\u00e0o."}, {"1Anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean, anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean !", "1Ta l\u00e0 Thi\u1ebft B\u00ec, ng\u01b0\u1eddi ch\u1ebf t\u1ea1o gi\u00e1p duy nh\u1ea5t c\u1ee7a l\u00e0ng n\u00e0y", "1m\u1ecdi lo\u1ea1i gi\u00e1p tr\u1ee5 ta \u0111\u1ec1u am hi\u1ec3u", "1C\u1ea7n g\u00ec v\u1ec1 gi\u00e1p th\u00ec c\u1ee9 \u0111\u1ebfn t\u00ecm ta nh\u00e9 !", "1C\u0169ng x\u1ebf tr\u01b0a r\u1ed3i.", "1gi\u1edd n\u00e0y ch\u1eafc b\u00e0 T\u00e1m t\u1ea1p h\u00f3a c\u0169ng \u0111ang ch\u1edd ng\u01b0\u01a1i \u0111\u1ea5y", "1h\u00e3y qua m\u00e0 ch\u00e0o h\u1ecfi b\u00e0 \u1ea5y \u0111i"}, {"1Ph\u00f9 ! Ta \u0111\u00e3 \u0111\u1ee3i con t\u1eeb s\u00e1ng s\u1edbm \u0111\u1ebfn gi\u1edd", "1Nghe chuy\u1ec7n v\u1ec1 ho\u00e0n c\u1ea3nh c\u1ee7a con m\u00e0 ta th\u01b0\u01a1ng qu\u00e1", "1Nh\u00e0 ta chuy\u00ean kinh doanh c\u00e1c lo\u1ea1i t\u1ea1p ph\u1ea9m c\u00f3 th\u1ec3 ph\u1ee5c v\u1ee5 cho c\u1ea3 l\u00e0ng.", "1. Anh B\u1ea3y th\u1ee7 kho \u0111ang h\u00e1o h\u1ee9c \u0111\u01b0\u1ee3c g\u1eb7p m\u1eb7t con", "1h\u00e3y \u0111\u1ebfn nh\u00e0 anh ta sau khi \u0103n xong nh\u00e9."}, {"1Ha ha ! Ti\u1ec3u \u0111\u1ec7 \u0111\u00e2y r\u1ed3i", "1L\u1ea1 thay, tuy ch\u01b0a g\u1eb7p m\u1eb7t l\u1ea7n n\u00e0o", "1nh\u01b0ng huynh c\u1ea3m th\u1ea5y nh\u01b0 \u0111\u00e3 quen ti\u1ec3u \u0111\u1ec7 t\u1eeb l\u00e2u l\u1eafm r\u1ed3i v\u1eady", "1Th\u1eadt l\u00e0 s\u1ea3ng kho\u00e1i", "1T\u00ednh huynh v\u1ed1n \u00edt n\u00f3i n\u00ean \u0111\u1ec7 \u0111\u1eebng ng\u1ea1i", "1Sau n\u00e0y tr\u00ean \u0111\u01b0\u1eddng b\u00f4n t\u1ea9u giang h\u1ed3", "1n\u1ebfu s\u01b0u t\u1ea7m \u0111\u01b0\u1ee3c th\u1ee9 g\u00ec tr\u00e2n qu\u00ed m\u00e0 kh\u00f4ng ti\u1ec7n mang b\u00ean m\u00ecnh", "1c\u1ee9 \u0111\u01b0a ta b\u1ea3o qu\u1ea3n", "1B\u1ea3o \u0111\u1ea3m kh\u00f4ng ai c\u00f3 th\u1ec3 l\u1ea5y \u0111\u01b0\u1ee3c c\u1ee7a \u0111\u1ec7.", "1Gi\u1edd th\u00ec \u0111\u1ec7 h\u00e3y \u0111\u1ebfn ra m\u1eaft Ph\u00fa \u00f4ng", "1c\u1ee9 t\u00ecm nh\u00e0 n\u00e0o to v\u00e0 \u0111\u1eb9p nh\u1ea5t l\u00e0ng ch\u00ednh l\u00e0 nh\u00e0 \u00f4ng \u1ea5y."}, {"1R\u1ea5t vui khi \u0111\u01b0\u1ee3c g\u1eb7p ng\u01b0\u01a1i", "1", "1Ng\u01b0\u01a1i tuy m\u1ed3 c\u00f4i nh\u01b0ng l\u1ea1i ngoan hi\u1ec1n v\u00e0 c\u00f3 ch\u00ed l\u1edbn", "1th\u1eadt l\u00e0 t\u1ed1t khi ng\u01b0\u01a1i \u0111\u1ee9ng v\u00e0o h\u00e0ng ng\u0169 ngh\u0129a s\u1ef9 S\u01a1n Nam", "1N\u1ebfu c\u1ea7n c\u00e1c lo\u1ea1i v\u1eadt li\u1ec7u luy\u1ec7n th\u1ea7n binh th\u00ec c\u1ee9 \u0111\u1ebfn t\u00ecm ta", "1Ta \u0111\u00e3 chuy\u1ec3n l\u1eddi gi\u1edbi thi\u1ec7u ng\u01b0\u01a1i v\u1edbi Nh\u1eadt th\u01b0\u01a1ng nh\u00e2n b\u1ea1n ta", "1h\u00e3y \u0111\u1ebfn ra m\u1eaft \u00f4ng ta nh\u00e9."}, {"1Ch\u00e0o ch\u00e0ng trai tr\u1ebb !", "1Ta th\u01b0\u1eddng \u0111i giao th\u01b0\u01a1ng kh\u1eafp n\u01a1i", "1Ta v\u00e0 ng\u01b0\u01a1i th\u1eadt l\u00e0 c\u00f3 duy\u00ean t\u01b0\u01a1ng ng\u1ed9", "1Ha ha ha ", "1Khi b\u00f4n ba giang h\u1ed3", "1c\u1ea7n ph\u1ea3i c\u00f3 m\u1ed9t ch\u00fat b\u1ea3n l\u0129nh.", "1H\u00e3y \u0111\u1ebfn g\u1eb7p L\u00e2m t\u01b0\u1edbng qu\u00e2n \u0111\u1ec3 h\u1ecdc h\u1ecfi nha.", "1L\u00e2m t\u01b0\u1edbng qu\u00e2n \u0111ang ch\u1edd ng\u01b0\u01a1i t\u1ea1i doanh tr\u1ea1i c\u1ee7a \u00f4ng \u1ea5y", "1H\u00e3y nhanh ch\u00e2n \u0111\u1ebfn g\u1eb7p \u00f4ng \u1ea5y \u0111i n\u00e0o."}, {"1H\u00e2y d\u00e0 ! Cu\u1ed1i c\u00f9ng c\u0169ng \u0111\u00e3 \u0111\u1ebfn !", "1C\u00f3 th\u1ef1c s\u1ef1 ng\u01b0\u01a1i mu\u1ed1n gia nh\u1eadp l\u00e0ng ngh\u0129a s\u1ef9 S\u01a1n Nam kh\u00f4ng?", "1N\u01a1i \u0111\u00e2y kh\u00f4ng c\u00f3 ch\u1ed5 dung th\u00e2n cho k\u1ebb l\u01b0\u1eddi bi\u1ebfng v\u00e0 v\u00f4 d\u1ee5ng nh\u00e9", "1H\u00e3y c\u1ed1 g\u1eafng luy\u1ec7n t\u1eadp nhi\u1ec1u", "1ta th\u1ea5y ti\u1ec1n \u0111\u1ed3 ng\u01b0\u01a1i c\u00f3 v\u1ebb s\u00e1ng l\u1ea1n \u0111\u1ea5y", "1Ta lu\u00f4n c\u00f3 nh\u1eefng nhi\u1ec7m v\u1ee5 \u0111ang c\u1ea7n ng\u01b0\u1eddi gi\u1ea3i quy\u1ebft", "1khi n\u00e0o th\u1ef1c s\u1ef1 t\u1ef1 tin \u0111\u1ec3 l\u00e0m h\u00e3y \u0111\u1ebfn t\u00ecm ta.", "1Nh\u00e2n \u0111\u00e2y ta s\u1ebd d\u1ea1y ng\u01b0\u01a1i 1 chi\u00eau th\u1ee9c", "1N\u00f3 s\u1ebd gi\u00fap ng\u01b0\u01a1i khi b\u1eaft \u0111\u1ea7u cu\u1ed9c h\u00e0nh t\u1ea9u.", "1B\u00e2y gi\u1edd ng\u01b0\u01a1i h\u00e3y quay l\u1ea1i g\u1eb7p tr\u01b0\u1edfng l\u00e0ng", "1\u00d4ng \u1ea5y c\u00f3 1 v\u00e0i \u0111i\u1ec1u mu\u1ed1n n\u00f3i v\u1edbi ng\u01b0\u01a1i tr\u01b0\u1edbc khi ng\u01b0\u01a1i l\u00ean \u0111\u01b0\u1eddng."}, {"1Ch\u00fac m\u1eebng con", "1T\u1eeb gi\u1edd con \u0111\u00e3 l\u00e0 th\u00e0nh vi\u00ean c\u1ee7a l\u00e0ng ngh\u0129a s\u1ef9 S\u01a1n Nam", "1H\u00e3y c\u1ed1 g\u1eafng \u0111\u1eebng ph\u1ee5 l\u00f2ng m\u1ecdi ng\u01b0\u1eddi k\u1ef3 v\u1ecdng v\u00e0o con", "0D\u1ea1 v\u00e2ng. Con s\u1ebd c\u1ed1 g\u1eafng."}, {"1Con bi\u1ebft kh\u00f4ng,S\u01a1n Nam l\u00e0 m\u1ed9t ng\u00f4i l\u00e0ng \u0111\u00e3 c\u00f3 l\u1ecbch s\u1eed l\u00e2u \u0111\u1eddi.>1N\u01a1i \u0111\u00e2y kh\u00f4ng ch\u1ec9 l\u00e0 n\u01a1i ta sinh s\u1ed1ng>1m\u00e0 c\u00f2n l\u00e0 n\u01a1i l\u01b0u gi\u1eef \u00fd ch\u00ed ki\u00eau h\u00f9ng v\u00e0 t\u00f4n ch\u1ec9 c\u1ee7a nh\u1eefng ng\u01b0\u1eddi \u0111\u1ea7u ti\u00ean \u0111i m\u1edf c\u00f5i v\u00e0 gi\u1eef n\u01b0\u1edbc>1N\u01a1i \u0111\u00e2y c\u0169ng l\u00e0 qu\u00ea h\u01b0\u01a1ng th\u1ee9 2 c\u1ee7a bao b\u1eadc anh h\u00f9ng h\u00e0o ki\u1ec7t nh\u00e2n s\u1ef9 t\u1ee9 ph\u01b0\u01a1ng>1ai trong h\u1ecd c\u0169ng t\u1ef1 h\u00e0o l\u00e0 th\u00e0nh vi\u00ean n\u01a1i \u0111\u00e2y v\u00e0 t\u1ef1 h\u00e0o l\u00e0 con ch\u00e1u c\u1ee7a x\u1ee9 s\u1edf R\u1ed3ng Ti\u00ean>1H\u1ecd l\u00e0 t\u1ea5m g\u01b0\u01a1ng v\u00e0 l\u00e0 ngu\u1ed3n \u0111\u1ed9ng vi\u00ean cho c\u00e1c l\u1edbp ng\u01b0\u1eddi \u0111i sau>1con h\u00e3y c\u1ed1 g\u1eafng \u0111\u1ec3 n\u1ed1i ti\u1ebfp truy\u1ec1n th\u1ed1ng l\u00e0ng ta nh\u00e9>1\u0110\u1ec3 ta l\u00e0m b\u1eefa c\u01a1m cho con c\u00fang ra m\u1eaft th\u00e0nh ho\u00e0ng l\u00e0ng>1con h\u00e3y ki\u1ebfm 20 con nh\u00edm v\u1ec1 \u0111\u00e2y cho ta nh\u00e9."}, {"1Nh\u00e0 ta c\u00f3 m\u1ea5y th\u1eeda ru\u1ed9ng tr\u01b0\u1edbc c\u1ed5ng l\u00e0ng \u0111ang b\u1ecb d\u1ecbch s\u00e2u h\u1ea1i l\u00faa", "1ng\u01b0\u01a1i h\u00e3y gi\u00fap ta di\u1ec7t s\u00e2u", "1\u0111\u1ec3 ch\u00fang kh\u00f4ng ph\u00e1 l\u00faa nh\u00e0 ta n\u1eefa nh\u00e9", "1Ch\u1eebng n\u00e0o xong th\u00ec quay v\u1ec1 \u0111\u00e2y b\u00e1o cho ta hay nh\u00e9"}, {"1Ah, ta mu\u1ed1n ng\u00e2m m\u1ed9t h\u1ee7 r\u01b0\u1ee3u r\u1eafn \u0111\u1ec3 ti\u1ebfp \u0111\u00e3i c\u00e1c anh h\u00f9ng ngh\u0129a s\u0129 khi c\u00f3 d\u1ecbp", "1Ta nghe n\u00f3i v\u00f9ng Ti\u00ean Du l\u00e2n c\u1eadn l\u00e0ng ta", "1c\u00e1ch \u0111\u00e2y ch\u1eebng 5 d\u1eb7m \u0111\u01b0\u1eddng c\u00f3 r\u1ea5t nhi\u1ec1u r\u1eafn l\u1ee5c", "1ng\u01b0\u01a1i h\u00e3y \u0111\u1ebfn \u0111\u00f3 v\u00e0 b\u1eaft d\u00f9m ta 60 con r\u1eafn", "1C\u1ea9n th\u1eadn \u0111\u1eebng \u0111\u1ec3 ch\u00fang c\u1eafn, ch\u1ebft ch\u1ee9 ch\u1ea3 ch\u01a1i !"}, {"1Haiiiiii !", "1M\u00f9a m\u00e0ng n\u0103m nay c\u1ee7a l\u00e0ng ta \u1edf ru\u1ed9ng xa Ph\u00f9 Li\u1ec7t th\u1ea5t b\u00e1t qu\u00e1", "1Do b\u1ecdn chu\u1ed9t t\u1eeb \u0111\u00e2u k\u00e9o v\u1ec1 sinh s\u00f4i n\u1ea3y n\u1edf qu\u00e1 nhi\u1ec1u", "1ng\u01b0\u01a1i h\u00e3y gi\u00fap d\u00e2n l\u00e0ng di\u1ec7t l\u0169 chu\u1ed9t \u0111\u1ec3 ch\u00fang kh\u1ecfi ph\u00e1 h\u1ea1i c\u00e2y l\u00faa."}, {"1Ta c\u00f3 m\u1ed9t ng\u01b0\u1eddi b\u1ea1n t\u00e2m giao c\u00f9ng h\u1ecdc ngh\u1ec7 n\u0103m x\u01b0a s\u1ed1ng t\u1ea1i v\u00f9ng K\u1ef3 B\u1ed1", "1l\u00e3o t\u00ean l\u00e0 H\u1ecfa X\u00edch.", "1Ta v\u00e0 l\u00e3o t\u00ecnh nh\u01b0 th\u1ee7 t\u00fac", "1l\u00e2u ng\u00e0y kh\u00f4ng g\u1eb7p sinh l\u00f2ng nh\u1edb th\u01b0\u01a1ng b\u1ea1n hi\u1ec1n", "1ta mu\u1ed1n g\u1eedi th\u01b0 h\u1ecfi th\u0103m xem l\u00e3o \u0111\u1ed9 n\u00e0y sinh s\u1ed1ng th\u1ebf n\u00e0o", "1Ng\u01b0\u01a1i h\u00e3y gi\u00fap ta mang phong th\u01b0 n\u00e0y trao t\u1eadn tay l\u00e3o", "1\u0110i nhanh r\u1ed3i v\u1ec1, ta r\u1ea5t mong tin l\u00e3o."}, {"1c\u1ea3 l\u00e0ng r\u1ea5t th\u00edch \u0103n b\u00e1nh bao do ta l\u00e0m n\u00ean h\u00f4m nay ta mu\u1ed1n \u0111\u00e3i h\u1ecd m\u1ed9t b\u1eefa", "1S\u00e1ng n\u00e0y ta ra ch\u1ee3 mua h\u1ebft th\u1ecbt heo ngo\u00e0i \u0111\u00f3 v\u1ec1 \u0111\u1ec3 l\u00e0m nh\u00e2n b\u00e1nh nh\u01b0ng v\u1eabn ch\u01b0a \u0111\u1ee7", "1con h\u00e3y t\u00ecm v\u1ec1 \u0111\u00e2y cho ta 60 con heo nh\u00e9", "1Ta nghe n\u00f3i d\u1ea1o n\u00e0y v\u00f9ng Th\u1ea1ch Giang xu\u1ea5t hi\u1ec7n r\u1ea5t nhi\u1ec1u heo r\u1eebng", "1con h\u00e3y \u0111\u1ebfn \u0111\u1ea5y th\u1eed xem.", "1\u0110i nhanh r\u1ed3i v\u1ec1 cho k\u1ecbp !"}, {"1Nh\u00e0 c\u1ee7a ta \u0111\u00e3 l\u00e2u l\u1eafm kh\u00f4ng tu s\u1eeda g\u00ec n\u00ean n\u00f3 \u0111\u00e3 qu\u00e1 c\u0169 k\u0129", "1\u0111\u00e3 c\u00f3 nhi\u1ec1u ch\u1ed5 b\u1ecb m\u1ed1i m\u1ecdt \u0103n m\u1ee5c", "1Con h\u00e3y gi\u00fap ta  s\u1eeda l\u1ea1i c\u0103n nh\u00e0 cho k\u1ecbp m\u00f9a m\u01b0a \u0111ang \u0111\u1ebfn", "1v\u00f9ng ph\u00f9 li\u1ec7t n\u1ed5i ti\u1ebfng nhi\u1ec1u g\u1ed7 t\u1ed1t", "1con h\u00e3y \u0111\u1ebfn \u0111\u1ea5y t\u00ecm g\u1ed7 gi\u00fap ta nh\u00e9", "1R\u1eebng thi\u00eang n\u01b0\u1edbc \u0111\u1ed9c", "1tr\u00ean n\u00fai c\u00f3 r\u1ea5t nhi\u1ec1u hoa \u0103n th\u1ecbt v\u00e0 qu\u00e1i th\u00fa con ph\u1ea3i h\u1ebft s\u1ee9c c\u1ea9n th\u1eadn"}, {"1Ta \u0111ang c\u1ea7n n\u1ecdc \u0111\u1ed9c c\u1ee7a b\u00f2 c\u1ea1p \u0111\u1ec3 \u0111i\u1ec1u ch\u1ebf m\u1ed9t s\u1ed1 d\u01b0\u1ee3c ph\u1ea9m", "1con h\u00e3y \u0111\u1ebfn \u0111\u00f4ng s\u01a1n t\u00ecm 40 n\u1ecdc b\u00f2 c\u1ea1p mang v\u1ec1 cho ta nh\u00e9."}};
        short[][] sArrayArray = new short[17][];
        sArrayArray[0] = new short[]{100, 100};
        sArrayArray[1] = new short[]{100, 100};
        sArrayArray[2] = new short[]{100, 100};
        sArrayArray[3] = new short[]{100, 100};
        sArrayArray[4] = new short[]{100, 100};
        sArrayArray[5] = new short[]{100, 100};
        sArrayArray[6] = new short[]{100, 100};
        sArrayArray[7] = new short[]{100, 100};
        short[] sArray3 = new short[2];
        sArray3[1] = 100;
        sArrayArray[8] = sArray3;
        sArrayArray[9] = new short[]{200, 200};
        sArrayArray[10] = new short[]{200, 300};
        sArrayArray[11] = new short[]{300, 300};
        sArrayArray[12] = new short[]{500, 800};
        sArrayArray[13] = new short[]{500, 800};
        sArrayArray[14] = new short[]{800, 1200};
        sArrayArray[15] = new short[]{1000, 1500};
        sArrayArray[16] = new short[]{2000, 2000};
        gold_xp_gift = sArrayArray;
        short[][] sArrayArray2 = new short[20][];
        sArrayArray2[0] = new short[0];
        sArrayArray2[1] = new short[0];
        sArrayArray2[2] = new short[0];
        sArrayArray2[3] = new short[0];
        sArrayArray2[4] = new short[0];
        sArrayArray2[5] = new short[0];
        sArrayArray2[6] = new short[0];
        sArrayArray2[7] = new short[0];
        sArrayArray2[8] = new short[0];
        sArrayArray2[9] = new short[0];
        sArrayArray2[10] = new short[0];
        sArrayArray2[11] = new short[0];
        sArrayArray2[12] = new short[0];
        sArrayArray2[13] = new short[0];
        sArrayArray2[14] = new short[0];
        sArrayArray2[15] = new short[0];
        sArrayArray2[16] = new short[0];
        sArrayArray2[17] = new short[0];
        sArrayArray2[18] = new short[0];
        short[] sArray4 = new short[7];
        sArray4[2] = 5;
        sArray4[6] = 5;
        sArrayArray2[19] = sArray4;
        potionGift = sArrayArray2;
        xu = "xu";
        info_Gif = new String[]{"Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 100 xu v\u00e0 100xp", "\u0110\u00e3 h\u1ecdc k\u1ef9 n\u0103ng ", "", "Nh\u1eadn \u0111\u01b0\u1ee3c 200 xu v\u00e0 200xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 200 xu v\u00e0 300xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 300 xu v\u00e0 300xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 300 xu v\u00e0 800xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 300 xu v\u00e0 800xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 800 xu v\u00e0 1200xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 1000 xu v\u00e0 1500xp", "Nh\u1eadn \u0111\u01b0\u1ee3c 2000 xu ,2000xp, 5HP v\u1eeba v\u00e0 5MP v\u1eeba"};
    }

    public static int[][] getMonsterKill(int idQuest) {
        return monster_kill[idQuest];
    }

    public static boolean isQuestOfNPC(int idQuest, int idNpc) {
        return npc_quest_receive[idQuest] == idNpc;
    }

    public static short[] getItemReceive(int idQuest) {
        return item_receive[idQuest];
    }

    public static short[] getPotionReceive(int idQuest) {
        return potionGift[idQuest];
    }

    public static short[] getItemQuest(int idQuest) {
        return item_quest[idQuest];
    }

    public static String getContent(int idQuest, int index) {
        try {
            return content_quest[idQuest][index];
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static int getTypeQuest(int idQuest) {
        if (idQuest - 1 >= npc_quest_receive.length) {
            return -1;
        }
        return QUEST_TYPE[idQuest - 1];
    }

    public static String getDecription(int idQuest) {
        return decription_quest[idQuest];
    }
}

