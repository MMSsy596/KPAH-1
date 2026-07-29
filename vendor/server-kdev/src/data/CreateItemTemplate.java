package data;

import Constant.Checker;
import java.util.Vector;
import real.Map;

public class CreateItemTemplate {

    public static byte[][][] pcADD2RankItem = new byte[][][]{
        {
            new byte[1],
            {10, 10, 10, 10, 10, 10, 11, 11, 11, 12, 12},
            {7, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9},
            {4, 4, 4, 4, 4, 4, 5, 5, 5, 6, 6},
            {1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3},
            new byte[1]
        },
        {
            new byte[1],
            {13, 13, 13, 13, 13, 13, 14, 14, 15, 14, 15},
            {10, 10, 10, 10, 10, 10, 11, 11, 12, 11, 12},
            {7, 7, 7, 7, 7, 7, 8, 8, 9, 8, 9},
            {4, 4, 4, 4, 4, 4, 5, 5, 6, 5, 6},
            new byte[1]
        },
        {
            new byte[1],
            {16, 16, 16, 16, 16, 16, 17, 17, 18, 17, 18},
            {13, 13, 13, 13, 13, 13, 14, 14, 15, 14, 15},
            {10, 10, 10, 10, 10, 10, 11, 11, 12, 11, 12},
            {7, 7, 7, 7, 7, 7, 8, 8, 9, 8, 9},
            new byte[1]
        },
        {
            new byte[1],
            {19, 19, 19, 19, 19, 19, 20, 20, 21, 20, 21},
            {16, 17, 18, 16, 16, 16, 16, 16, 17, 16, 18},
            {13, 14, 15, 13, 13, 13, 13, 13, 14, 14, 15},
            {10, 11, 12, 10, 10, 10, 10, 10, 11, 11, 12},
            new byte[1]
        },
        {
            new byte[1],
            {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24},
            {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21},
            {16, 17, 18, 16, 16, 16, 16, 16, 17, 17, 18},
            {13, 14, 15, 13, 13, 13, 13, 13, 14, 14, 15},
            new byte[1]
        },
        {
            new byte[1],
            {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27},
            {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24},
            {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21},
            {16, 17, 18, 16, 16, 16, 16, 16, 17, 17, 18},
            new byte[1]
        },
        {
            new byte[1],
            {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30},
            {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27},
            {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24},
            {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21},
            new byte[1]
        },
        {
            new byte[1],
            {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33},
            {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30},
            {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27},
            {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24},
            new byte[1]
        },
        {
            new byte[1],
            {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36},
            {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33},
            {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30},
            {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27},
            new byte[1]
        },
        {
            new byte[1],
            {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39},
            {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36},
            {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33},
            {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30},
            new byte[1]
        },
        {
            new byte[1],
            {40, 41, 42, 40, 40, 40, 40, 40, 41, 41, 42},
            {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39},
            {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36},
            {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33},
            new byte[1]
        },
        {
            new byte[1],
            {43, 44, 45, 43, 43, 43, 43, 43, 44, 44, 45},
            {40, 41, 42, 40, 40, 40, 40, 40, 41, 41, 42},
            {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39},
            {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36},
            new byte[1]
        },
        {
            new byte[1],
            {55, 56, 57, 55, 55, 55, 55, 55, 56, 56, 57},
            {52, 53, 54, 52, 52, 52, 52, 52, 53, 53, 54},
            {49, 50, 51, 49, 49, 49, 49, 49, 50, 50, 51},
            {46, 47, 48, 46, 46, 46, 46, 46, 47, 47, 48},
            new byte[1]
        },
        {
            new byte[1],
            {67, 68, 69, 67, 67, 67, 67, 67, 68, 68, 69},
            {64, 65, 66, 64, 64, 64, 64, 64, 65, 65, 66},
            {61, 62, 63, 61, 61, 61, 61, 61, 62, 62, 63},
            {58, 59, 60, 58, 58, 58, 58, 58, 59, 59, 60},
            new byte[1]
        },
        {
            new byte[1],
            {79, 80, 81, 79, 79, 79, 79, 79, 80, 80, 81},
            {76, 77, 78, 76, 76, 76, 76, 76, 77, 77, 78},
            {73, 74, 75, 73, 73, 73, 73, 73, 74, 74, 75},
            {70, 71, 72, 70, 70, 70, 70, 70, 71, 71, 72},
            new byte[1]
        }
    };

    private static final byte chiSoCongThem = 12;

    public static byte[][][] BUCONG_AMOR = new byte[][][]{
        new byte[0][],
        {new byte[2], {6, 3}, {8, 6}, {12, 9}, {15, 12}, {17, 15}, {21, 18}, {23, 21}, {27, 24}, {30, 27}, {32, 30}, {36, 33}, {39, 36}, {42, 39}, {45, 42}},
        {new byte[2], {6, 3}, {9, 6}, {13, 9}, {15, 12}, {18, 15}, {21, 18}, {24, 21}, {28, 24}, {30, 37}, {33, 30}, {36, 33}, {39, 36}, {42, 39}, {45, 42}},
        {new byte[2], {7, 3}, {9, 6}, {13, 9}, {16, 12}, {19, 15}, {22, 28}, {24, 21}, {28, 24}, {31, 27}, {34, 30}, {37, 33}, {40, 36}, {43, 39}, {46, 42}}
    };
    public static byte[][][] BUCONG_RING = new byte[][][]{
        new byte[0][],
        {new byte[2], {6, 0}, {6, 0}, {5, 0}, {6, 0}, {5, 0}, {6, 0}, {6, 0}, {5, 0}, {6, 0}, {5, 0}, {6, 0}, {6, 0}, {6, 0}, {7, 0}},
        {new byte[2], {7, 0}, {6, 0}, {6, 0}, {6, 0}, {6, 0}, {7, 0}, {6, 0}, {6, 0}, {6, 0}, {6, 0}, {7, 0}, {7, 0}, {7, 0}, {8, 0}},
        {new byte[2], {7, 0}, {7, 0}, {7, 0}, {7, 0}, {6, 0}, {7, 0}, {7, 0}, {7, 0}, {7, 0}, {6, 0}, {7, 0}, {7, 0}, {7, 0}, {8, 0}}
    };
    public static byte[][][] BUCONG_JEWELRY = new byte[][][]{
        new byte[0][],
        {new byte[2], {5, 0}, {6, 0}, {5, 0}, {6, 0}, {6, 0}, {5, 0}, {6, 0}, {5, 0}, {6, 0}, {6, 0}, {5, 0}, {5, 0}, {5, 0}, {6, 0}},
        {new byte[2], {6, 0}, {6, 0}, {6, 0}, {7, 0}, {6, 0}, {6, 0}, {6, 0}, {6, 0}, {7, 0}, {6, 0}, {6, 0}, {6, 0}, {6, 0}, {7, 0}},
        {new byte[2], {7, 0}, {7, 0}, {6, 0}, {7, 0}, {7, 0}, {7, 0}, {7, 0}, {6, 0}, {7, 0}, {7, 0}, {7, 0}, {7, 0}, {7, 0}, {8, 0}}
    };
    public static byte[][][] BUCONG_GEM = new byte[][][]{
        new byte[0][],
        {new byte[2], {11, 5}, {18, 12}, {24, 18}, {31, 25}, {38, 32}, {44, 38}, {50, 44}, {57, 51}, {64, 58}, {71, 65}, {76, 70}, {81, 75}, {86, 80}, {91, 85}},
        {new byte[2], {11, 15}, {18, 12}, {24, 18}, {31, 25}, {38, 32}, {44, 38}, {50, 44}, {57, 51}, {64, 58}, {71, 65}, {76, 70}, {81, 75}, {86, 80}, {91, 85}},
        {new byte[2], {12, 6}, {19, 13}, {25, 19}, {32, 26}, {39, 33}, {45, 39}, {51, 45}, {58, 52}, {65, 59}, {72, 66}, {77, 71}, {82, 76}, {87, 81}, {92, 86}}
    };
    public static byte[] ADDMORE_GREEN = new byte[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 8, 8, 8, 8, 8, 8, 12, 12};
    public static final byte TYPE_AO = 0;
    public static final byte TYPE_QUAN = 1;
    public static final byte TYPE_NON = 2;
    public static final byte TYPE_KIEM = 3;
    public static final byte TYPE_DAIDAO = 4;
    public static final byte TYPE_DUA = 5;
    public static final byte TYPE_BUA = 6;
    public static final byte TYPE_CUNG = 7;
    public static final byte TYPE_NHAN = 8;
    public static final byte TYPE_DAYCHUYEN = 9;
    public static final byte TYPE_GIAY = 10;
    public static final byte TYPE_GANG = 11;
    public static final byte TYPE_NGOC = 12;
    public static byte[][] ALL_MATERIAL = Checker.LOCAL_SERVER ? new byte[][]{
        {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}}
            : new byte[][]{
                {1, 1}, {2, 1}, {3, 1}, {4, 2}, {4, 3}, {5, 3}, {5, 4}, {5, 4}, {5, 5}, {7, 7}, {9, 9}, {11, 11}, {13, 13}, {15, 15}
            };

    public static byte[][] ID_MATERIAL = new byte[][]{
        {4, 9, 1, 6}, {0, 5}, {4, 9}, {3, 6}, {3, 6}, {1, 8}, {3, 6}, {1, 8}, {2, 7}, {2, 7}, {4, 9}, {0, 5}, {2, 7}
    };
    public static short[] startIDMaterial = new short[]{68, 75, 82, 89, 96, 103, 110, 117, 124, 131};
    public static String[] nameMaterial = new String[]{"Vải", "Sắt", "Ngọc", "Gỗ thường", "Da mềm", "Tơ lụa", "Bạc", "Thuỷ tinh", "Gỗ sưa", "Da cứng"};
    private static byte LEVEL_START_UP = 35;
    private static byte[][][] ALL_MATERIALUP = new byte[][][]{
        {new byte[2], new byte[2], new byte[2], new byte[2], {2, 2}, {3, 2}, {3, 3}, {4, 3}, {4, 4}, {5, 4}, {5, 5}, {6, 5}, {6, 6}, {7, 6}, {7, 7}, {9, 9}},
        {new byte[2], new byte[2], new byte[2], new byte[2], {2, 1}, {3, 1}, {3, 1}, {4, 1}, {4, 1}, {5, 1}, {5, 1}, {6, 1}, {6, 2}, {7, 2}, {7, 2}, {8, 2}}
    };
    private static byte ALL_MATERIAL_LOCK = 1;
    private static byte[][] ID_MATERIAL_LOCK = new byte[][]{{9, 6}, {5}, {9}, {6}, {6}, {8}, {6}, {8}, {7}, {7}, {9}, {5}, {7}};

    public static byte getPointAddForRank(int lvItem, int rankItem) {
        byte[] temp = pcADD2RankItem[(lvItem - 20) / 5][rankItem];
        if (lvItem % 5 != 0) {
            temp = pcADD2RankItem[(lvItem - 21) / 5][rankItem];
        }

        return temp[Map.r.nextInt(temp.length)];
    }

    public static byte getPointAddForRankReset(int lvItem, int rankItem) {
        byte[] temp = pcADD2RankItem[(lvItem - 20) / 5][rankItem];
        if (lvItem % 5 != 0) {
            temp = pcADD2RankItem[(lvItem - 21) / 5][rankItem];
        }

        return temp[0];
    }

    public static byte getBuCong(int lvItem, int colorItem, int rankItem, int type) {
        if (rankItem == 1 || rankItem == 2) {
            if ((type < 3 || type > 7) && type != 13 && type != 9 && type != 8 && type != 12) {
                return BUCONG_AMOR[colorItem][(lvItem - 20) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 9) {
                return BUCONG_JEWELRY[colorItem][(lvItem - 20) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 8) {
                return BUCONG_RING[colorItem][(lvItem - 20) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 12) {
                return BUCONG_GEM[colorItem][(lvItem - 20) / 5][rankItem == 1 ? 1 : 0];
            }
        }

        return 0;
    }

    public static byte getAddMorGreen(int rankItem) {
        return rankItem == 1 ? ADDMORE_GREEN[Map.r.nextInt(ADDMORE_GREEN.length)] : 0;
    }

    public static byte[] getIDMAterial(int type, int magic_physic, int option) {
        return option < 3 ? getIDMAterial(type, magic_physic) : getIDMAterialLOCK(type, magic_physic);
    }

    public static Vector<Byte> getTotalMaterial(int level, int option) {
        if (option == 0) {
            return getTotalMaterial(level);
        } else if (option == 1) {
            return getTotalMaterialUP(level, 0);
        } else if (option == 2) {
            return getTotalMaterialUP(level, 1);
        } else if (option == 3) {
            return getTotalMaterialLOCK(level);
        } else {
            return option == 4 ? getTotalMaterialChangeAtt(level) : getTotalMaterialLOCK(level);
        }
    }

    private static byte[] getIDMAterial(int type, int magic_physic) {
        if (type == 0) {
            return magic_physic == 0 ? new byte[]{4, 9} : new byte[]{1, 6};
        } else {
            return ID_MATERIAL[type];
        }
    }

    private static Vector<Byte> getTotalMaterial(int level) {
        Vector<Byte> n = new Vector();
        if (ALL_MATERIAL[level][0] > 0) {
            n.add(ALL_MATERIAL[level][0]);
        }

        if (ALL_MATERIAL[level][1] > 0) {
            n.add(ALL_MATERIAL[level][1]);
        }

        return n;
    }

    private static byte[] getIDMAterialUp(int type, int level, int magic_physic) {
        if (type == 0) {
            return magic_physic == 0 ? new byte[]{4, 9} : new byte[]{1, 6};
        } else {
            return ID_MATERIAL[type];
        }
    }

    private static Vector<Byte> getTotalMaterialUP(int level, int type) {
        Vector<Byte> n = new Vector();
        if (ALL_MATERIALUP[type][level][0] > 0) {
            n.add(ALL_MATERIALUP[type][level][0]);
        }

        if (ALL_MATERIALUP[type][level][1] > 0) {
            n.add(ALL_MATERIALUP[type][level][1]);
        }

        return n;
    }

    private static byte[] getIDMAterialLOCK(int type, int magic_physic) {
        if (type == 0) {
            return magic_physic == 0 ? new byte[]{9} : new byte[]{6};
        } else {
            return ID_MATERIAL_LOCK[type];
        }
    }

    private static Vector<Byte> getTotalMaterialLOCK(int level) {
        Vector<Byte> n = new Vector();
        n.add((byte) 1);
        return n;
    }

    private static Vector<Byte> getTotalMaterialChangeAtt(int level) {
        Vector<Byte> n = new Vector();
        n.add((byte) 2);
        return n;
    }
}
