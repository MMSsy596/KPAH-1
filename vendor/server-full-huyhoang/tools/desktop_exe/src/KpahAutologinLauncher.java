import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class KpahAutologinLauncher {
    private static final String APP_NAME = "KPAH Auto Tool";
    private static final String BUILD_LABEL = "build 2026-04-21.6";
    private static final String CONFIG_FILE = "kpah-auto.properties";
    private static final String INVENTORY_CACHE_FILE = "inventory-cache.properties";
    private static final String ERROR_LOG_FILE = "kpah-auto-error.log";
    private static final String[] SERVER_OPTIONS = {
        "Server 0 - Ti\u00ean Du",
        "Server 1 - \u0110\u00f4ng S\u01a1n",
        "Server 2 - Phong Ch\u00e2u",
        "Server 3",
        "Server 4",
        "Server 5"
    };
    private static final String[] CHARACTER_OPTIONS = {
        "Nh\u00e2n v\u1eadt 1",
        "Nh\u00e2n v\u1eadt 2",
        "Nh\u00e2n v\u1eadt 3"
    };
    private static final String[] CHARACTER_CLASS_OPTIONS = {
        "Ki\u1ebfm Kh\u00e1ch",
        "Chi\u1ebfn Binh",
        "Ph\u00e1p S\u01b0",
        "\u0110\u1ea5u S\u0129",
        "Cung Th\u1ee7"
    };
    private static final String[] AUTO_MODE_OPTIONS = {
        "Auto \u0111\u00e1nh",
        "Auto b\u01a1m m\u00e1u",
        "Auto \u0111\u00e1nh + b\u01a1m m\u00e1u",
        "T\u1eaft auto"
    };
    private static final String[] TARGET_MODE_OPTIONS = {
        "Ch\u1ecdn to\u00e0n b\u1ed9",
        "\u0110\u00e1nh qu\u00e1i v\u00e0 \u0111\u1ed1i th\u1ee7",
        "Ch\u1ec9 ch\u1ecdn NPC",
        "Ch\u1ec9 ch\u1ecdn kh\u00e1c bang",
        "Ch\u1ec9 ch\u1ecdn kh\u00e1c qu\u1ed1c gia"
    };
    private static final String[] TEAM_MODE_OPTIONS = {
        "T\u1eaft t\u1ed5 \u0111\u1ed9i",
        "Nh\u00f3m tr\u01b0\u1edfng",
        "Theo nh\u00f3m tr\u01b0\u1edfng"
    };
    private static final String[] RESOURCE_MODE_OPTIONS = {
        "B\u00ecnh th\u01b0\u1eddng",
        "M\u00e1y y\u1ebfu",
        "Treo l\u00e2u ng\u00e0y"
    };
    private static final String[] POTENTIAL_OPTIONS = {
        "S\u1ee9c m\u1ea1nh",
        "Nhanh nh\u1eb9n",
        "Tinh th\u1ea7n",
        "S\u1ee9c kh\u1ecfe",
        "May m\u1eafn"
    };
    private static final String[][] CLASS_SKILL_NAMES = {
        {
            "Chem",
            "Kim tinh phap",
            "Loi dien phap",
            "Kinh loi bat thu",
            "Ho sat tien",
            "Di luc dao cong",
            "Thien loi dien tram",
            "Sam dong duong gian",
            "Kiem phi kinh thien"
        },
        {
            "Chem",
            "Hoa kinh thien",
            "Nhat hoa long",
            "Bat dai hoa long",
            "Cuong than giap",
            "Ho cong tien",
            "Thien long bao kich",
            "Liet hoa bao kich",
            "Sao bang giang the"
        },
        {
            "Danh",
            "Thuy giang minh",
            "Than long thuy",
            "Bat dai hai long",
            "Hoi cong luc dan",
            "Hoi luc tien",
            "Hoi sinh",
            "Song ho cong thu",
            "Hai long xuat the",
            "Song long thi uy",
            "Han bang vu"
        },
        {
            "Dap",
            "Tho Tu",
            "Kim son thuy",
            "Khong kinh bat vi",
            "Bat di bien",
            "Ho thu tien",
            "Kinh thien dong dia",
            "Son Tinh bo thien",
            "Thach nhu cong tam"
        },
        {
            "Ban",
            "Nhat hon tien",
            "Phi thien tien",
            "Bat kim tien dao",
            "Doc luu tien",
            "Ho doc tien",
            "Thap dien tam tien",
            "Thang thien loan tien",
            "Van tien quy tam"
        }
    };
    private static final int[][] AUTO_ATTACK_SKILL_IDS = {
        {0, 1, 2, 3, 6, 7, 8},
        {0, 1, 2, 3, 6, 7, 8},
        {0, 1, 2, 3, 8, 9, 10},
        {0, 1, 2, 3, 6, 7, 8},
        {0, 1, 2, 3, 6, 7, 8}
    };
    private static final int[][] AUTO_BUFF_SKILL_IDS = {
        {4, 5},
        {4, 5},
        {4, 5, 7},
        {4, 5},
        {4, 5}
    };
    private static final String[] CLIENT_CANDIDATES = {
        "grinding2.jar",
        "vanphong18x5.jar",
        "v\u0103n ph\u00f2ng 18+.x5.jar",
        "grinding.jar",
        "client.jar",
        "KPAH2_autologin.jar",
        "KPAH2.ME_autologin.jar",
        "kpah_local_19129.jar",
        "client.bin"
    };
    private static final String PLATFORM_BASE = "nokia/1/1";
    private static final String CLIENT_AUTH_MARKER = "kpah-auth-v1";
    private static final String CLIENT_AUTH_SECRET = "9F3A8C731D6B4E8EAB2C6F5D11A8B7C9E4F2D6A1C3B5870F96E2C14B5A8D3F71";
    private static final int TRAIN_CLUSTER_LOW = 0;
    private static final int TRAIN_CLUSTER_HIGH = 1;
    private static final int ESCORT_MONSTER_TEMPLATE_MIN = 95;
    private static final int ESCORT_MONSTER_TEMPLATE_MAX = 112;
    private static final int[] ESCORT_MONSTER_TEMPLATE_EXTRA = {113, 115, 116, 117};
    private static final int ESCORT_RECEIVE_NPC_ID = -50;
    private static final int ESCORT_RETURN_NPC_ID = -51;
    private static final int ESCORT_RETURN_X = 880;
    private static final int ESCORT_RETURN_Y = 320;
    private static final int ESCORT_DETECT_DISTANCE_HOME = 96;
    private static final int ESCORT_DETECT_DISTANCE_ROUTE = 160;
    private static final int ESCORT_DETECT_DISTANCE_RETURN = 176;
    private static final int ESCORT_TRANSFER_SAFE_DISTANCE = 104;
    private static final int ESCORT_REJOIN_STOP_DISTANCE = 56;
    private static final int ESCORT_ROUTE_STEP_DISTANCE = 80;
    private static final int ESCORT_ROUTE_STEP_DISTANCE_LAGGING = 56;
    private static final int ESCORT_GATE_READY_DISTANCE = 40;
    private static final int ESCORT_GATE_TRANSFER_DISTANCE = 72;
    private static final long ESCORT_TRANSFER_TIMEOUT_MS = 12000L;
    private static final long ESCORT_POST_TRANSFER_SETTLE_MS = 1400L;
    private static final TrainSpot[] TRAIN_SPOTS = {
        new TrainSpot(TRAIN_CLUSTER_LOW, 1, 8, 1, "dao_chau"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 9, 12, 2, "tien_du"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 13, 16, 3, "phu_liet"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 17, 20, 4, "ky_bo"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 21, 24, 5, "ham_tu"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 25, 28, 6, "thach_giang"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 29, 32, 7, "dong_son"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 33, 36, 8, "tu_quan"),
        new TrainSpot(TRAIN_CLUSTER_LOW, 37, 45, 9, "truong_giang"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 40, 45, 10, "loc_tri"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 46, 50, 13, "phong_chau"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 51, 55, 14, "binh_luc"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 56, 60, 15, "bao_thai"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 61, 65, 18, "hoi_ho"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 66, 70, 19, "te_giang"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 71, 75, 20, "bach_hac"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 76, 82, 23, "ham_toi"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 83, 90, 24, "sieu_loai"),
        new TrainSpot(TRAIN_CLUSTER_HIGH, 91, 120, 26, "binh_kieu")
    };
    private static final int[][] MAP_GROUPS = {
        {0, 301, 302, 303, 304},
        {70, 1701, 1702, 1703, 1704},
        {80, 1901, 1902, 1903, 1904},
        {1, 321, 322, 323, 324},
        {2, 341, 342, 343, 344},
        {3, 361, 362, 363, 364},
        {4, 381, 382, 383, 384},
        {5, 401, 402, 403, 404},
        {6, 421, 422, 423, 424},
        {7, 441, 442, 443, 444},
        {8, 461, 462, 463, 464},
        {9, 481, 482, 483, 484},
        {10, 501, 502, 503, 504},
        {11, 521, 522, 523, 524},
        {12, 541, 542, 543, 544},
        {13, 561, 562, 563, 564},
        {14, 581, 582, 583, 584},
        {15, 601, 602, 603, 604},
        {18, 661, 662, 663, 664},
        {19, 681, 682, 683, 684},
        {20, 701, 702, 703, 704},
        {23, 761, 762, 763, 764},
        {24, 781, 782, 783, 784},
        {26, 821, 822, 823, 824},
        {106, 2421, 2422, 2423, 2424},
        {110, 2501, 2502, 2503, 2504},
        {111, 2521, 2522, 2523, 2524},
        {112, 2541, 2542, 2543, 2544},
        {113, 2561, 2562, 2563, 2564},
        {114, 2581, 2582, 2583, 2584},
        {118, 118, 118, 118, 118},
        {201, 201, 201, 201, 201},
        {202, 202, 202, 202, 202},
        {206, 206, 206, 206, 206}
    };
    private static final TravelEdge[] TRAVEL_EDGES = {
        TravelEdge.gate(0, 1, 1, 1, 61, 321, 1, 61, 322, 1, 61, 323, 1, 61, 324, 1, 61),
        TravelEdge.gate(0, 2, 2, 15, 1, 341, 15, 1, 342, 15, 1, 343, 15, 1, 344, 15, 1),
        TravelEdge.gate(0, 12, 12, 46, 13, 541, 46, 13, 542, 46, 13, 543, 46, 13, 544, 46, 13),
        TravelEdge.gate(1, 0, 0, 17, 2, 301, 17, 2, 302, 17, 2, 303, 17, 2, 304, 17, 2),
        TravelEdge.gate(1, 4, 4, 1, 12, 381, 1, 12, 382, 1, 12, 383, 1, 12, 384, 1, 12),
        TravelEdge.gate(2, 0, 0, 23, 58, 301, 23, 58, 302, 23, 58, 303, 23, 58, 304, 23, 58),
        TravelEdge.gate(2, 3, 3, 1, 22, 361, 1, 22, 362, 1, 22, 363, 1, 22, 364, 1, 22),
        TravelEdge.gate(3, 2, 2, 126, 21, 341, 126, 21, 342, 126, 21, 343, 126, 21, 344, 126, 21),
        TravelEdge.gate(3, 4, 4, 93, 98, 381, 93, 98, 382, 93, 98, 383, 93, 98, 384, 93, 98),
        TravelEdge.gate(3, 5, 5, 71, 1, 401, 71, 1, 402, 71, 1, 403, 71, 1, 404, 71, 1),
        TravelEdge.gate(4, 1, 1, 78, 11, 321, 78, 11, 322, 78, 11, 323, 78, 11, 324, 78, 11),
        TravelEdge.gate(4, 3, 3, 91, 3, 361, 91, 3, 362, 91, 3, 363, 91, 3, 364, 91, 3),
        TravelEdge.gate(5, 3, 3, 91, 97, 361, 91, 97, 362, 91, 97, 363, 91, 97, 364, 91, 97),
        TravelEdge.gate(5, 6, 6, 40, 2, 421, 40, 2, 422, 40, 2, 423, 40, 2, 424, 40, 2),
        TravelEdge.gate(6, 5, 5, 65, 77, 401, 65, 77, 402, 65, 77, 403, 65, 77, 404, 65, 77),
        TravelEdge.gate(6, 7, 7, 2, 91, 441, 2, 91, 442, 2, 91, 443, 2, 91, 444, 2, 91),
        TravelEdge.gate(7, 6, 6, 45, 44, 421, 45, 44, 422, 45, 44, 423, 45, 44, 424, 45, 44),
        TravelEdge.gate(7, 8, 8, 27, 77, 461, 27, 77, 462, 27, 77, 463, 27, 77, 464, 27, 77),
        TravelEdge.gate(8, 7, 7, 32, 2, 441, 32, 2, 442, 32, 2, 443, 32, 2, 444, 32, 2),
        TravelEdge.gate(8, 9, 9, 28, 98, 481, 28, 98, 482, 28, 98, 483, 28, 98, 484, 28, 98),
        TravelEdge.gate(9, 8, 8, 48, 2, 461, 48, 2, 462, 48, 2, 463, 48, 2, 464, 48, 2),
        TravelEdge.gate(9, 201, 201, 39, 90, 118, 10, 20, 30, 39, 90, 463, 39, 77, 464, 39, 77),
        TravelEdge.gate(10, 11, 11, 77, 67, 521, 77, 67, 522, 77, 67, 523, 77, 67, 524, 77, 67),
        TravelEdge.gate(10, 13, 13, 5, 51, 561, 5, 51, 562, 5, 51, 563, 5, 51, 564, 5, 51),
        TravelEdge.gate(10, 14, 14, 77, 29, 581, 77, 29, 582, 77, 29, 583, 77, 29, 584, 77, 29),
        TravelEdge.gate(11, 10, 10, 97, 2, 501, 97, 2, 502, 97, 2, 503, 97, 2, 504, 97, 2),
        TravelEdge.gate(11, 15, 15, 4, 22, 601, 4, 22, 602, 4, 22, 603, 4, 22, 604, 4, 22),
        TravelEdge.gate(12, 0, 0, 2, 32, 301, 2, 32, 302, 2, 32, 303, 2, 32, 304, 2, 32),
        TravelEdge.gate(13, 10, 10, 127, 43, 501, 127, 43, 502, 127, 43, 503, 127, 43, 504, 127, 43),
        TravelEdge.gate(13, 20, 20, 25, 1, 701, 25, 1, 702, 25, 1, 703, 25, 1, 704, 25, 1),
        TravelEdge.gate(14, 10, 10, 1, 52, 501, 1, 52, 502, 1, 52, 503, 1, 52, 504, 1, 52),
        TravelEdge.gate(14, 23, 23, 54, 2, 761, 54, 2, 762, 54, 2, 763, 54, 2, 764, 54, 2),
        TravelEdge.gate(14, 18, 18, 34, 58, 661, 34, 58, 662, 34, 58, 663, 34, 58, 664, 34, 58),
        TravelEdge.gate(15, 11, 11, 31, 4, 521, 31, 4, 522, 31, 4, 523, 31, 4, 524, 31, 4),
        TravelEdge.gate(15, 19, 19, 1, 22, 681, 1, 22, 682, 1, 22, 683, 1, 22, 684, 1, 22),
        TravelEdge.gate(15, 24, 24, 28, 3, 781, 28, 3, 782, 28, 3, 783, 28, 3, 784, 28, 3),
        TravelEdge.gate(15, 26, 26, 22, 55, 821, 22, 55, 822, 22, 55, 823, 22, 55, 824, 22, 55),
        TravelEdge.gate(18, 14, 14, 36, 1, 581, 36, 1, 582, 36, 1, 583, 36, 1, 584, 36, 1),
        TravelEdge.gate(18, 19, 19, 43, 58, 681, 43, 58, 682, 43, 58, 683, 43, 58, 684, 43, 58),
        TravelEdge.gate(19, 18, 18, 1, 33, 661, 1, 33, 662, 1, 33, 663, 1, 33, 664, 1, 33),
        TravelEdge.gate(19, 15, 15, 98, 78, 601, 98, 78, 602, 98, 78, 603, 98, 78, 604, 98, 78),
        TravelEdge.gate(19, 20, 20, 1, 47, 701, 1, 47, 702, 1, 47, 703, 1, 47, 704, 1, 47),
        TravelEdge.gate(20, 19, 19, 75, 27, 681, 75, 27, 682, 75, 27, 683, 75, 27, 684, 75, 27),
        TravelEdge.gate(20, 13, 13, 37, 98, 561, 37, 98, 562, 37, 98, 563, 37, 98, 564, 37, 98),
        TravelEdge.gate(23, 118, 118, 2, 20, 2661, 2, 20, 2662, 2, 20, 2663, 2, 20, 2664, 2, 20),
        TravelEdge.gate(23, 14, 14, 71, 77, 581, 71, 77, 582, 71, 77, 583, 71, 77, 584, 71, 77),
        TravelEdge.gate(24, 118, 118, 68, 2, 2661, 68, 2, 2662, 68, 2, 2663, 68, 2, 2664, 68, 2),
        TravelEdge.gate(24, 15, 15, 48, 97, 601, 48, 97, 602, 48, 97, 603, 48, 97, 604, 48, 97),
        TravelEdge.gate(26, 15, 15, 65, 4, 601, 65, 4, 602, 65, 4, 603, 65, 4, 604, 65, 4),
        TravelEdge.gate(201, 9, 9, 33, 1, 481, 33, 1, 482, 33, 1, 483, 33, 1, 484, 33, 1),
        TravelEdge.gate(202, 0, 0, 24, 39, 301, 24, 39, 302, 24, 39, 303, 24, 39, 304, 24, 39),
        TravelEdge.xaphu(0, 3),
        TravelEdge.xaphu(0, 4),
        TravelEdge.xaphu(0, 5),
        TravelEdge.xaphu(3, 6),
        TravelEdge.xaphu(3, 7),
        TravelEdge.xaphu(4, 5),
        TravelEdge.xaphu(4, 6),
        TravelEdge.xaphu(5, 4),
        TravelEdge.xaphu(5, 7),
        TravelEdge.xaphu(5, 8),
        TravelEdge.xaphu(6, 3),
        TravelEdge.xaphu(6, 4),
        TravelEdge.xaphu(6, 8),
        TravelEdge.xaphu(6, 9),
        TravelEdge.xaphu(7, 5),
        TravelEdge.xaphu(7, 9),
        TravelEdge.xaphu(8, 5),
        TravelEdge.xaphu(8, 6),
        TravelEdge.xaphu(9, 7),
        TravelEdge.xaphu(9, 6),
        TravelEdge.xaphu(10, 24)
    };
    private static final EscortGateSpec[] ESCORT_GATE_SPECS = {
        new EscortGateSpec(0, 1, 18, 5),
        new EscortGateSpec(0, 2, 25, 54),
        new EscortGateSpec(70, 1, 22, 4),
        new EscortGateSpec(70, 2, 22, 55),
        new EscortGateSpec(80, 1, 47, 4),
        new EscortGateSpec(80, 2, 34, 54),
        new EscortGateSpec(1, 0, 5, 60),
        new EscortGateSpec(1, 4, 74, 10),
        new EscortGateSpec(2, 0, 17, 5),
        new EscortGateSpec(2, 3, 125, 20),
        new EscortGateSpec(3, 2, 5, 23),
        new EscortGateSpec(3, 4, 92, 5),
        new EscortGateSpec(3, 5, 92, 95),
        new EscortGateSpec(4, 1, 5, 13),
        new EscortGateSpec(4, 3, 90, 95),
        new EscortGateSpec(5, 3, 72, 5),
        new EscortGateSpec(5, 6, 66, 75),
        new EscortGateSpec(6, 5, 40, 5),
        new EscortGateSpec(6, 7, 44, 44),
        new EscortGateSpec(7, 6, 5, 87),
        new EscortGateSpec(7, 8, 32, 5),
        new EscortGateSpec(8, 7, 28, 75),
        new EscortGateSpec(8, 9, 47, 5),
        new EscortGateSpec(9, 8, 29, 94),
        new EscortGateSpec(9, 201, 34, 5),
        new EscortGateSpec(9, 118, 34, 5)
    };

    private KpahAutologinLauncher() {
    }

    public static void main(String[] args) {
        LaunchOptions launchOptions = LaunchOptions.parse(args);
        try {
            String machineUserHome = System.getProperty("user.home");
            Path appDir = getAppDir();
            if (launchOptions.skipUi) {
                KpahLicenseSupport.ensureStoredLicense(appDir);
            } else if (KpahLicenseSupport.ensureInteractiveLicense(appDir, APP_NAME, null) == null) {
                return;
            }
            Path clientJar = launchOptions.clientJar != null ? launchOptions.clientJar : findClientJar(appDir);
            ClassLoader appLoader = KpahAutologinLauncher.class.getClassLoader();
            Path configPath = launchOptions.configPath != null ? launchOptions.configPath : appDir.resolve(CONFIG_FILE);

            if (clientJar == null || !Files.exists(clientJar)) {
                throw new IllegalStateException("Khong tim thay client jar trong " + appDir);
            }
            prepareParentDirectory(configPath);
            AutoConfig config = AutoConfig.load(configPath);
            if (applySharedServerDefaults(config, configPath, appDir)) {
                config.store(configPath);
            }
            config.applyDetectedLogin(findRememberedLogin(machineUserHome));
            if (launchOptions.profileName.length() > 0) {
                config.profileName = launchOptions.profileName;
            }

            Path dataDir = launchOptions.dataDir != null ? launchOptions.dataDir : getDataDir();
            Files.createDirectories(dataDir);
            System.setProperty("user.home", dataDir.toString());
            System.setProperty("user.dir", dataDir.toString());

            if (!launchOptions.skipUi) {
                config = promptForConfig(config, clientJar, dataDir, launchOptions.configMode);
                if (config == null) {
                    return;
                }
                config.store(configPath);
                if (launchOptions.reloadAfterSave && launchOptions.commandFile != null) {
                    queueManagerCommand(launchOptions.commandFile, "reload_config");
                }
                if (launchOptions.configureOnly) {
                    return;
                }
            }
            ensureFreeJ2meAvailable(appLoader, appDir);
            Path emulatorClientJar = prepareStableClientJar(clientJar, dataDir);
            prepareJ2meSystemProperties(emulatorClientJar);
            prepareFreeJ2meBootstrapConfig(appDir, emulatorClientJar, config);
            Path statusPath = launchOptions.statusFile != null ? launchOptions.statusFile : dataDir.resolve("status.properties");
            Path commandPath = launchOptions.commandFile != null ? launchOptions.commandFile : dataDir.resolve("command.properties");
            prepareParentDirectory(statusPath);
            prepareParentDirectory(commandPath);
            String[] emulatorArgs = buildEmulatorArgs(emulatorClientJar, launchOptions.emulatorArgs);
            Throwable[] emulatorError = new Throwable[1];

            Thread emulatorThread = new Thread(new EmulatorMain(appLoader, emulatorArgs, emulatorError), "kpah-freej2me");
            emulatorThread.setContextClassLoader(appLoader);
            emulatorThread.start();

            Thread controllerThread = new Thread(
                new AutoController(appLoader, config, configPath, statusPath, commandPath, appDir),
                "kpah-auto-controller"
            );
            controllerThread.setDaemon(true);
            controllerThread.start();

            emulatorThread.join();
            if (emulatorError[0] != null) {
                throw emulatorError[0];
            }
        } catch (Throwable error) {
            error.printStackTrace();
            writeErrorLog(error);
            if (!launchOptions.skipUi) {
                showError(error);
            }
            System.exit(1);
        }
    }

    private static void ensureFreeJ2meAvailable(ClassLoader classLoader, Path appDir) throws Exception {
        try {
            Class.forName("org.recompile.freej2me.FreeJ2ME", false, classLoader);
        } catch (ClassNotFoundException error) {
            throw new IllegalStateException("Khong tim thay freej2me.jar trong " + appDir, error);
        }
    }

    private static void prepareFreeJ2meBootstrapConfig(Path appDir, Path clientJar, AutoConfig config) {
        try {
            Path runtimeRoot = getRuntimeRoot(appDir);
            String suiteName = detectMidletSuiteName(clientJar);
            Path gameConfig = runtimeRoot.resolve("config").resolve(suiteName).resolve("game.conf");
            Path systemConfig = runtimeRoot.resolve("freej2me_system").resolve("freej2me.conf");
            writeGameConfig(gameConfig, config);
            writeSystemConfigIfMissing(systemConfig);
        } catch (Exception ignored) {
        }
    }

    private static void prepareJ2meSystemProperties(Path clientJar) throws Exception {
        System.setProperty("microedition.platform", buildPlatformSignature(clientJar));
        ensureSystemProperty("microedition.configuration", "CLDC-1.1");
        ensureSystemProperty("microedition.profiles", "MIDP-2.0");
        ensureSystemProperty("device.model", "KPAH-Auto");
        ensureSystemProperty("device.vendor", "Nokia");
        System.out.println("[launcher] microedition.platform=" + System.getProperty("microedition.platform"));
        System.out.println("[launcher] microedition.configuration=" + System.getProperty("microedition.configuration"));
        System.out.println("[launcher] microedition.profiles=" + System.getProperty("microedition.profiles"));
    }

    private static boolean applySharedServerDefaults(AutoConfig config, Path configPath, Path appDir) throws IOException {
        if (config == null || configPath == null || appDir == null) {
            return false;
        }

        Path sharedConfigPath = appDir.resolve(CONFIG_FILE);
        if (sameNormalizedPath(configPath, sharedConfigPath) || !Files.isRegularFile(sharedConfigPath)) {
            return false;
        }

        AutoConfig shared = AutoConfig.load(sharedConfigPath);
        boolean changed = false;
        if (config.customHost.length() == 0 && shared.customHost.length() > 0) {
            config.customHost = shared.customHost;
            config.customPort = shared.customPort;
            changed = true;
        }
        if (config.customServerName.length() == 0 && shared.customServerName.length() > 0) {
            config.customServerName = shared.customServerName;
            changed = true;
        }
        if (config.serverIndex == 0 && shared.serverIndex > 0) {
            config.serverIndex = shared.serverIndex;
            changed = true;
        }
        if (changed) {
            config.normalize();
        }
        return changed;
    }

    private static boolean sameNormalizedPath(Path left, Path right) {
        if (left == null || right == null) {
            return false;
        }
        return left.toAbsolutePath().normalize().equals(right.toAbsolutePath().normalize());
    }

    private static String buildPlatformSignature(Path clientJar) throws Exception {
        long issuedAtSeconds = System.currentTimeMillis() / 1000L;
        String clientHash = sha256Hex(clientJar);
        String payload = CLIENT_AUTH_MARKER + "|" + issuedAtSeconds + "|" + clientHash;
        String signature = hmacSha256Hex(CLIENT_AUTH_SECRET, payload);
        return PLATFORM_BASE + "|" + payload + "|" + signature;
    }

    private static String sha256Hex(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        try (InputStream input = Files.newInputStream(path)) {
            int read;
            while ((read = input.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        return toHex(digest.digest());
    }

    private static String hmacSha256Hex(String secret, String payload) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return toHex(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 255;
            if (value < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(value).toUpperCase(Locale.ROOT));
        }
        return sb.toString();
    }

    private static void ensureSystemProperty(String key, String defaultValue) {
        String current = System.getProperty(key);
        if (current == null || current.trim().length() == 0) {
            System.setProperty(key, defaultValue);
        }
    }

    private static Path getRuntimeRoot(Path appDir) {
        Path parent = appDir.getParent();
        return parent != null ? parent : appDir;
    }

    private static String detectMidletSuiteName(Path clientJar) {
        try (JarFile jarFile = new JarFile(clientJar.toFile())) {
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes attributes = manifest.getMainAttributes();
                String midletEntry = attributes.getValue("MIDlet-1");
                String midletName = extractManifestName(midletEntry);
                if (midletName.length() > 0) {
                    return sanitizePathPart(midletName);
                }
                String manifestName = attributes.getValue("MIDlet-Name");
                if (manifestName != null && manifestName.trim().length() > 0) {
                    return sanitizePathPart(manifestName.trim());
                }
            }
        } catch (Exception ignored) {
        }

        String fileName = clientJar.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return sanitizePathPart(fileName);
    }

    private static String extractManifestName(String value) {
        if (value == null) {
            return "";
        }
        int commaIndex = value.indexOf(',');
        String name = commaIndex >= 0 ? value.substring(0, commaIndex) : value;
        return name.trim();
    }

    private static String sanitizePathPart(String value) {
        String cleaned = value.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
        return cleaned.length() == 0 ? "default" : cleaned;
    }

    private static void writeGameConfig(Path gameConfig, AutoConfig config) throws IOException {
        int resourceMode = config == null ? 0 : clamp(config.resourceMode, 0, RESOURCE_MODE_OPTIONS.length - 1);
        LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
        values.put("backlightcolor", "Disabled");
        values.put("compatfantasyzonefix", "off");
        values.put("compatignorevolumechanges", "off");
        values.put("compatimmediaterepaints", "off");
        values.put("compatoverrideplatchecks", "off");
        values.put("compatsiemensfriendlydrawing", "off");
        values.put("compattranstooriginonreset", "off");
        values.put("dojaversion", "200");
        values.put("fontoffset", "0");
        values.put("fps", resourceMode == 2 ? "12" : (resourceMode == 1 ? "18" : "0"));
        values.put("fpshack", "Disabled");
        values.put("phone", "Standard");
        values.put("rotate", "0");
        values.put("scrheight", "320");
        values.put("scrwidth", "240");
        values.put("sound", resourceMode == 0 ? "on" : "off");
        values.put("soundfont", "Default");
        values.put("spdhackm3ghalfres", "off");
        values.put("spdhacknoalpha", "off");
        values.put("textfont", "Default");

        mergeExistingConfig(gameConfig, values);

        Files.createDirectories(gameConfig.getParent());
        ArrayList<String> lines = new ArrayList<String>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            lines.add(entry.getKey() + ":" + entry.getValue());
        }
        Files.write(gameConfig, lines, StandardCharsets.UTF_8);
    }

    private static void writeSystemConfigIfMissing(Path systemConfig) throws IOException {
        LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
        values.put("M3GUntextured", "off");
        values.put("M3GWireframe", "off");
        values.put("deleteTempKJXFiles", "on");
        values.put("dumpAudioStreams", "off");
        values.put("dumpGraphicsObjects", "off");
        values.put("fpsCounterPosition", "Off");
        values.put("input_ArrowDown", "40");
        values.put("input_ArrowLeft", "37");
        values.put("input_ArrowRight", "39");
        values.put("input_ArrowUp", "38");
        values.put("input_CLR", "65");
        values.put("input_FastForward", "32");
        values.put("input_Fire", "10");
        values.put("input_LeftSoft", "112");
        values.put("input_Num0", "96");
        values.put("input_Num1", "97");
        values.put("input_Num2", "98");
        values.put("input_Num3", "99");
        values.put("input_Num4", "100");
        values.put("input_Num5", "101");
        values.put("input_Num6", "102");
        values.put("input_Num7", "103");
        values.put("input_Num8", "104");
        values.put("input_Num9", "105");
        values.put("input_PauseResume", "88");
        values.put("input_Pound", "82");
        values.put("input_RightSoft", "113");
        values.put("input_Screenshot", "67");
        values.put("input_Star", "69");
        values.put("logLevel", "2");

        mergeExistingConfig(systemConfig, values);
        values.put("input_LeftSoft", "112");
        values.put("input_RightSoft", "113");

        Files.createDirectories(systemConfig.getParent());
        ArrayList<String> lines = new ArrayList<String>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            lines.add(entry.getKey() + ":" + entry.getValue());
        }
        Files.write(systemConfig, lines, StandardCharsets.UTF_8);
    }

    private static void mergeExistingConfig(Path configPath, LinkedHashMap<String, String> values) throws IOException {
        if (!Files.exists(configPath)) {
            return;
        }

        List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.length() == 0 || line.startsWith("#")) {
                continue;
            }
            int separator = line.indexOf(':');
            if (separator <= 0) {
                continue;
            }
            String key = line.substring(0, separator).trim();
            String value = line.substring(separator + 1).trim();
            if (!values.containsKey(key)) {
                values.put(key, value);
            }
        }
    }

    private static DetectedLogin findRememberedLogin(String machineUserHome) {
        if (machineUserHome == null || machineUserHome.trim().length() == 0) {
            return null;
        }
        Path userHome = Paths.get(machineUserHome);
        DetectedLogin login = findMicroEmulatorLogin(userHome.resolve(".microemulator"));
        if (login != null) {
            return login;
        }
        return null;
    }

    private static DetectedLogin findMicroEmulatorLogin(Path rootDir) {
        if (!Files.isDirectory(rootDir)) {
            return null;
        }
        DirectoryStream<Path> suites = null;
        try {
            suites = Files.newDirectoryStream(rootDir, "suite-*");
            for (Path suiteDir : suites) {
                Path loginFile = suiteDir.resolve("nqshlogin.rs");
                DetectedLogin login = parseMicroEmulatorLogin(loginFile);
                if (login != null) {
                    return login;
                }
            }
        } catch (IOException ignored) {
            return null;
        } finally {
            if (suites != null) {
                try {
                    suites.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    private static DetectedLogin parseMicroEmulatorLogin(Path loginFile) {
        if (!Files.isRegularFile(loginFile)) {
            return null;
        }
        try {
            byte[] raw = Files.readAllBytes(loginFile);
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(raw));
            String storeName = input.readUTF();
            if (!"nqshlogin".equals(storeName)) {
                return null;
            }
            if (input.available() < 20) {
                return null;
            }
            input.readInt();
            input.readLong();
            input.readInt();
            int recordCount = input.readInt();
            for (int i = 0; i < recordCount && input.available() >= 4; i++) {
                int recordLength = input.readInt();
                if (recordLength <= 0 || recordLength > input.available()) {
                    return null;
                }
                byte[] record = new byte[recordLength];
                input.readFully(record);
                DetectedLogin login = parseLoginRecord(record, loginFile);
                if (login != null) {
                    return login;
                }
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    private static DetectedLogin parseLoginRecord(byte[] record, Path sourcePath) {
        try {
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(record));
            boolean remembered = input.readBoolean();
            if (!remembered || input.available() <= 0) {
                return null;
            }
            String username = input.readUTF().trim();
            String password = input.readUTF();
            if (username.length() == 0 && password.length() == 0) {
                return null;
            }
            return new DetectedLogin(username, password, sourcePath.toString());
        } catch (Exception ignored) {
            return null;
        }
    }

    private static Path findClientJar(Path appDir) throws IOException {
        for (int i = 0; i < CLIENT_CANDIDATES.length; i++) {
            Path candidate = appDir.resolve(CLIENT_CANDIDATES[i]);
            if (Files.exists(candidate)) {
                return candidate;
            }
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(appDir, "*.jar")) {
            for (Path path : stream) {
                String name = path.getFileName().toString();
                if (!"freej2me.jar".equalsIgnoreCase(name)
                    && !"kpah-launcher.jar".equalsIgnoreCase(name)
                    && !"kpah-manager.jar".equalsIgnoreCase(name)) {
                    return path;
                }
            }
        }
        return null;
    }

    private static Path getAppDir() throws Exception {
        Path launcherPath = Paths.get(
            KpahAutologinLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI()
        );
        Path appDir = launcherPath.getParent();
        if (appDir == null) {
            throw new IllegalStateException("Khong xac dinh duoc thu muc app");
        }
        return appDir;
    }

    private static Path getDataDir() {
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null && localAppData.length() > 0) {
            return Paths.get(localAppData, "KPAH_Auto_Tool");
        }
        return Paths.get(System.getProperty("user.home"), ".kpah_auto_tool");
    }

    private static Path prepareStableClientJar(Path clientJar, Path dataDir) throws IOException {
        Path runtimeRoot = resolveStableRuntimeRoot(dataDir);
        Path clientCacheDir = runtimeRoot.resolve("client-cache");
        Files.createDirectories(clientCacheDir);

        String fileName = sanitizePathPart(clientJar.getFileName().toString());
        long size = Files.size(clientJar);
        long modifiedAt = Files.getLastModifiedTime(clientJar).toMillis();
        Path stagedJar = clientCacheDir.resolve(size + "_" + modifiedAt + "_" + fileName);
        if (Files.exists(stagedJar) && Files.size(stagedJar) == size) {
            return stagedJar;
        }

        Path tempJar = stagedJar.resolveSibling(stagedJar.getFileName().toString() + ".tmp");
        Files.copy(clientJar, tempJar, StandardCopyOption.REPLACE_EXISTING);
        try {
            Files.move(tempJar, stagedJar, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ignored) {
            Files.move(tempJar, stagedJar, StandardCopyOption.REPLACE_EXISTING);
        }
        return stagedJar;
    }

    private static Path resolveStableRuntimeRoot(Path dataDir) throws IOException {
        ArrayList<Path> candidates = new ArrayList<Path>();
        String systemDrive = System.getenv("SystemDrive");
        if (systemDrive != null && systemDrive.matches("[A-Za-z]:")) {
            candidates.add(Paths.get(systemDrive + "\\KPAH_Auto_Runtime"));
        }
        candidates.add(Paths.get("C:\\KPAH_Auto_Runtime"));
        if (dataDir != null) {
            candidates.add(dataDir.resolve(".runtime"));
        }

        IOException lastError = null;
        for (int i = 0; i < candidates.size(); i++) {
            Path candidate = candidates.get(i);
            try {
                Files.createDirectories(candidate);
                return candidate;
            } catch (IOException error) {
                lastError = error;
            }
        }
        if (lastError != null) {
            throw lastError;
        }
        throw new IOException("Kh\u00f4ng t\u1ea1o \u0111\u01b0\u1ee3c th\u01b0 m\u1ee5c runtime cho client.");
    }

    private static String[] buildEmulatorArgs(Path clientJar, String[] args) {
        String clientUri = clientJar.toUri().toASCIIString();
        ArrayList<String> finalArgs = new ArrayList<String>();
        finalArgs.add(clientUri);
        int droppedJarArgs = 0;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String value = args[i];
                if (value == null) {
                    continue;
                }
                String normalized = value.trim();
                if (normalized.length() == 0) {
                    continue;
                }
                if (isJarArgument(normalized, clientUri)) {
                    droppedJarArgs++;
                    continue;
                }
                finalArgs.add(normalized);
            }
        }
        if (droppedJarArgs > 0) {
            System.out.println("[launcher] bo qua " + droppedJarArgs + " tham so jar cu cua FreeJ2ME.");
        }
        return finalArgs.toArray(new String[finalArgs.size()]);
    }

    private static boolean isJarArgument(String value, String clientUri) {
        if (value == null) {
            return false;
        }
        String normalized = value.trim();
        if (normalized.length() == 0) {
            return false;
        }
        if (normalized.equalsIgnoreCase(clientUri)) {
            return true;
        }
        String lower = normalized.toLowerCase(Locale.ROOT);
        return lower.endsWith(".jar") || (lower.startsWith("file:/") && lower.contains(".jar"));
    }

    private static void prepareParentDirectory(Path path) throws IOException {
        if (path == null) {
            return;
        }
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    private static void queueManagerCommand(Path commandPath, String command) throws IOException {
        if (commandPath == null || command == null || command.trim().length() == 0) {
            return;
        }
        Properties properties = new Properties();
        properties.setProperty("command", command.trim());
        properties.setProperty("updated_at", Long.toString(System.currentTimeMillis()));
        writePropertiesAtomically(commandPath, properties, "KPAH auto command");
    }

    private static void deleteFileQuietly(Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    private static void writePropertiesAtomically(Path path, Properties properties, String comments) throws IOException {
        prepareParentDirectory(path);
        String fileName = path.getFileName() == null ? "tmp.properties" : path.getFileName().toString();
        try {
            Path tempFile = createWritableTempFile(path, fileName);
            try (OutputStream output = Files.newOutputStream(tempFile)) {
                properties.store(output, comments);
            }
            try {
                Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                return;
            } catch (IOException ignored) {
            }
            try {
                Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING);
                return;
            } catch (IOException ignored) {
            }
            try {
                Files.copy(tempFile, path, StandardCopyOption.REPLACE_EXISTING);
                return;
            } finally {
                deletePathQuietly(tempFile);
            }
        } catch (IOException ignored) {
        }
        try (OutputStream output = Files.newOutputStream(path)) {
            properties.store(output, comments);
            return;
        }
    }

    private static Path createWritableTempFile(Path path, String fileName) throws IOException {
        Path parent = path == null ? null : path.getParent();
        String prefix = sanitizeTempPrefix(fileName);
        if (parent != null) {
            try {
                return Files.createTempFile(parent, prefix, ".tmp");
            } catch (IOException ignored) {
            }
        }
        return Files.createTempFile(prefix, ".tmp");
    }

    private static String sanitizeTempPrefix(String fileName) {
        StringBuilder builder = new StringBuilder();
        String safe = fileName == null ? "kpah-auto" : fileName;
        for (int i = 0; i < safe.length(); i++) {
            char ch = safe.charAt(i);
            if ((ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9')) {
                builder.append(ch);
            } else {
                builder.append('_');
            }
            if (builder.length() >= 24) {
                break;
            }
        }
        while (builder.length() < 3) {
            builder.append('_');
        }
        return builder.toString();
    }

    private static void deletePathQuietly(Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    private static void showError(Throwable error) {
        String message = error.getMessage();
        if (message == null || message.length() == 0) {
            message = error.toString();
        }
        message = message + "\n\nChi tiet: kpah-auto-error.log";
        JOptionPane.showMessageDialog(
            null,
            message,
            APP_NAME,
            JOptionPane.ERROR_MESSAGE
        );
    }

    private static void writeErrorLog(Throwable error) {
        try {
            Path logPath = getAppDir().resolve(ERROR_LOG_FILE);
            StringWriter buffer = new StringWriter();
            PrintWriter writer = new PrintWriter(buffer);
            error.printStackTrace(writer);
            writer.flush();
            Files.write(logPath, buffer.toString().getBytes("UTF-8"));
        } catch (Exception ignored) {
        }
    }

    private static final class SkillOption {
        private final int skillId;
        private final String label;

        private SkillOption(int skillId, String label) {
            this.skillId = skillId;
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    private static final class PotentialOption {
        private final int statIndex;
        private final String label;

        private PotentialOption(int statIndex, String label) {
            this.statIndex = statIndex;
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    private static final class InventoryChoice {
        private final int templateId;
        private final String label;

        private InventoryChoice(int templateId, String label) {
            this.templateId = templateId;
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    private static String skillName(int classIndex, int skillId) {
        int safeClass = clamp(classIndex, 0, CLASS_SKILL_NAMES.length - 1);
        String[] skills = CLASS_SKILL_NAMES[safeClass];
        if (skillId < 0 || skillId >= skills.length) {
            return "Skill " + skillId;
        }
        return skills[skillId];
    }

    private static int[] fallbackSkillSelection(int[] values) {
        return values == null ? new int[]{-1, -1, -1} : values;
    }

    private static SkillOption createSkillOption(int classIndex, int skillId) {
        if (skillId < 0) {
            return new SkillOption(-1, "Khong dung");
        }
        return new SkillOption(skillId, skillName(classIndex, skillId) + " [" + skillId + "]");
    }

    private static void populateSkillCombo(JComboBox<SkillOption> box, int classIndex, int[] skillIds, int selectedSkillId) {
        box.removeAllItems();
        box.addItem(createSkillOption(classIndex, -1));
        int selectedIndex = 0;
        for (int i = 0; i < skillIds.length; i++) {
            int skillId = skillIds[i];
            SkillOption option = createSkillOption(classIndex, skillId);
            box.addItem(option);
            if (skillId == selectedSkillId) {
                selectedIndex = i + 1;
            }
        }
        box.setSelectedIndex(selectedIndex);
    }

    private static void populateAllSkillCombo(JComboBox<SkillOption> box, int classIndex, int selectedSkillId) {
        box.removeAllItems();
        box.addItem(createSkillOption(classIndex, -1));
        int selectedIndex = 0;
        int safeClass = clamp(classIndex, 0, CLASS_SKILL_NAMES.length - 1);
        for (int i = 0; i < CLASS_SKILL_NAMES[safeClass].length; i++) {
            SkillOption option = createSkillOption(safeClass, i);
            box.addItem(option);
            if (i == selectedSkillId) {
                selectedIndex = i + 1;
            }
        }
        box.setSelectedIndex(selectedIndex);
    }

    private static JPanel createSkillSelectorPanel(JComboBox<?>[] boxes) {
        JPanel panel = new JPanel(new GridLayout(boxes.length, 1, 0, 4));
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].setMaximumRowCount(12);
            panel.add(boxes[i]);
        }
        return panel;
    }

    private static int selectedSkillId(JComboBox<SkillOption> box) {
        Object selected = box.getSelectedItem();
        if (selected instanceof SkillOption) {
            return ((SkillOption)selected).skillId;
        }
        return -1;
    }

    private static int[] selectedSkillIds(JComboBox<SkillOption>[] boxes) {
        int[] values = new int[boxes.length];
        for (int i = 0; i < boxes.length; i++) {
            values[i] = selectedSkillId(boxes[i]);
        }
        return values;
    }

    private static void refreshSkillSelectors(int classIndex, JComboBox<SkillOption>[] attackBoxes, JComboBox<SkillOption>[] buffBoxes, int[] selectedAttack, int[] selectedBuff) {
        int safeClass = clamp(classIndex, 0, CHARACTER_CLASS_OPTIONS.length - 1);
        int[] safeAttack = fallbackSkillSelection(selectedAttack);
        int[] safeBuff = fallbackSkillSelection(selectedBuff);
        for (int i = 0; i < attackBoxes.length; i++) {
            populateSkillCombo(attackBoxes[i], safeClass, AUTO_ATTACK_SKILL_IDS[safeClass], safeAttack[Math.min(i, safeAttack.length - 1)]);
        }
        for (int i = 0; i < buffBoxes.length; i++) {
            populateSkillCombo(buffBoxes[i], safeClass, AUTO_BUFF_SKILL_IDS[safeClass], safeBuff[Math.min(i, safeBuff.length - 1)]);
        }
    }

    private static void refreshUpgradeSkillSelectors(int classIndex, JComboBox<SkillOption>[] upgradeBoxes, int[] selectedSkills) {
        int safeClass = clamp(classIndex, 0, CHARACTER_CLASS_OPTIONS.length - 1);
        int[] safeSelected = fallbackSkillSelection(selectedSkills);
        for (int i = 0; i < upgradeBoxes.length; i++) {
            populateAllSkillCombo(upgradeBoxes[i], safeClass, safeSelected[Math.min(i, safeSelected.length - 1)]);
        }
    }

    private static String buildSkillConfigText(JComboBox<SkillOption>[] boxes) {
        int[] values = selectedSkillIds(boxes);
        boolean hasSelectedSkill = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(values[i]);
            if (values[i] >= 0) {
                hasSelectedSkill = true;
            }
        }
        return hasSelectedSkill ? builder.toString() : "";
    }

    private static PotentialOption[] buildPotentialChoices() {
        PotentialOption[] values = new PotentialOption[POTENTIAL_OPTIONS.length + 1];
        values[0] = new PotentialOption(-1, "Khong cong");
        for (int i = 0; i < POTENTIAL_OPTIONS.length; i++) {
            values[i + 1] = new PotentialOption(i, POTENTIAL_OPTIONS[i]);
        }
        return values;
    }

    private static void populatePotentialCombo(JComboBox<PotentialOption> box, int selectedIndex) {
        box.removeAllItems();
        PotentialOption[] options = buildPotentialChoices();
        int selected = 0;
        for (int i = 0; i < options.length; i++) {
            box.addItem(options[i]);
            if (options[i].statIndex == selectedIndex) {
                selected = i;
            }
        }
        box.setSelectedIndex(selected);
    }

    private static int selectedPotentialIndex(JComboBox<PotentialOption> box) {
        Object selected = box.getSelectedItem();
        if (selected instanceof PotentialOption) {
            return ((PotentialOption)selected).statIndex;
        }
        return -1;
    }

    private static int[] parseIdList(String raw, int limit) {
        int size = Math.max(1, limit);
        int[] values = new int[size];
        for (int i = 0; i < values.length; i++) {
            values[i] = -1;
        }
        String safe = raw == null ? "" : raw.trim();
        if (safe.length() == 0) {
            return values;
        }
        String[] parts = safe.split("[,;\\s]+");
        int index = 0;
        for (int i = 0; i < parts.length && index < values.length; i++) {
            String token = parts[i].trim();
            if (token.length() == 0) {
                continue;
            }
            try {
                values[index] = Integer.parseInt(token);
                index++;
            } catch (NumberFormatException ignored) {
            }
        }
        return values;
    }

    private static List<InventoryChoice> loadInventoryChoices(Path dataDir) {
        ArrayList<InventoryChoice> choices = new ArrayList<InventoryChoice>();
        if (dataDir == null) {
            return choices;
        }
        Path cachePath = resolveInventoryCachePath(dataDir);
        if (!Files.isRegularFile(cachePath)) {
            return choices;
        }
        Properties properties = new Properties();
        try {
            InputStream input = Files.newInputStream(cachePath);
            try {
                properties.load(input);
            } finally {
                input.close();
            }
        } catch (IOException ignored) {
            return choices;
        }

        int count = 0;
        try {
            count = Integer.parseInt(properties.getProperty("count", "0").trim());
        } catch (NumberFormatException ignored) {
            count = 0;
        }
        LinkedHashMap<Integer, InventoryChoice> deduped = new LinkedHashMap<Integer, InventoryChoice>();
        for (int i = 0; i < count; i++) {
            int templateId = -1;
            int itemType = Integer.MIN_VALUE;
            try {
                templateId = Integer.parseInt(properties.getProperty("item." + i + ".template_id", "-1").trim());
            } catch (NumberFormatException ignored) {
                templateId = -1;
            }
            try {
                itemType = Integer.parseInt(properties.getProperty("item." + i + ".type", Integer.toString(Integer.MIN_VALUE)).trim());
            } catch (NumberFormatException ignored) {
                itemType = Integer.MIN_VALUE;
            }
            if (templateId < 0 || deduped.containsKey(Integer.valueOf(templateId))) {
                continue;
            }
            if (!isAutoUseInventoryTemplateType(itemType)) {
                continue;
            }
            String name = properties.getProperty("item." + i + ".name", "").trim();
            if (name.length() == 0) {
                name = "V\u1eadt ph\u1ea9m";
            }
            deduped.put(Integer.valueOf(templateId), new InventoryChoice(templateId, name + " [" + templateId + "]"));
        }
        choices.addAll(deduped.values());
        return choices;
    }

    private static boolean isAutoUseInventoryTemplateType(int itemType) {
        return itemType > 19;
    }

    private static Path resolveInventoryCachePath(Path dataDir) {
        if (dataDir == null) {
            return Paths.get(INVENTORY_CACHE_FILE);
        }
        ArrayList<Path> candidates = new ArrayList<Path>();
        candidates.add(dataDir.resolve(INVENTORY_CACHE_FILE));
        Path parent = dataDir.getParent();
        if (parent != null) {
            candidates.add(parent.resolve(INVENTORY_CACHE_FILE));
        }
        Path best = candidates.get(0);
        long bestModifiedAt = Long.MIN_VALUE;
        for (int i = 0; i < candidates.size(); i++) {
            Path candidate = candidates.get(i);
            if (!Files.isRegularFile(candidate)) {
                continue;
            }
            try {
                long modifiedAt = Files.getLastModifiedTime(candidate).toMillis();
                if (bestModifiedAt == Long.MIN_VALUE || modifiedAt > bestModifiedAt) {
                    best = candidate;
                    bestModifiedAt = modifiedAt;
                }
            } catch (IOException ignored) {
                if (bestModifiedAt == Long.MIN_VALUE) {
                    best = candidate;
                }
            }
        }
        return best;
    }

    private static void populateInventoryCombo(JComboBox<InventoryChoice> box, List<InventoryChoice> cachedItems, int selectedTemplateId) {
        box.removeAllItems();
        box.addItem(new InventoryChoice(-1, "Kh\u00f4ng d\u00f9ng"));
        int selectedIndex = 0;
        for (int i = 0; i < cachedItems.size(); i++) {
            InventoryChoice option = cachedItems.get(i);
            box.addItem(option);
            if (option.templateId == selectedTemplateId) {
                selectedIndex = i + 1;
            }
        }
        if (selectedTemplateId >= 0 && selectedIndex == 0) {
            box.addItem(new InventoryChoice(selectedTemplateId, "V\u1eadt ph\u1ea9m \u0111\u00e3 l\u01b0u [" + selectedTemplateId + "]"));
            selectedIndex = box.getItemCount() - 1;
        }
        if (cachedItems.isEmpty() && selectedTemplateId < 0) {
            box.addItem(new InventoryChoice(-2, "Ch\u01b0a c\u00f3 d\u1eef li\u1ec7u h\u00e0nh trang"));
        }
        box.setSelectedIndex(selectedIndex);
    }

    private static int selectedInventoryTemplateId(JComboBox<InventoryChoice> box) {
        Object selected = box.getSelectedItem();
        if (selected instanceof InventoryChoice) {
            int templateId = ((InventoryChoice)selected).templateId;
            return templateId >= 0 ? templateId : -1;
        }
        return -1;
    }

    private static String buildInventoryConfigText(JComboBox<InventoryChoice>[] boxes) {
        StringBuilder builder = new StringBuilder();
        boolean hasValue = false;
        for (int i = 0; i < boxes.length; i++) {
            int templateId = selectedInventoryTemplateId(boxes[i]);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(templateId);
            if (templateId >= 0) {
                hasValue = true;
            }
        }
        return hasValue ? builder.toString() : "";
    }

    private static void setComponentsEnabled(boolean enabled, JComponent[] components) {
        for (int i = 0; i < components.length; i++) {
            if (components[i] != null) {
                components[i].setEnabled(enabled);
            }
        }
    }

    private static boolean isAttackAutoEnabled(int autoMode) {
        return autoMode == 0 || autoMode == 2;
    }

    private static boolean isHealAutoEnabled(int autoMode) {
        return autoMode == 1 || autoMode == 2;
    }

    private static int buildAutoMode(boolean attackEnabled, boolean healEnabled) {
        if (attackEnabled && healEnabled) {
            return 2;
        }
        if (attackEnabled) {
            return 0;
        }
        if (healEnabled) {
            return 1;
        }
        return 3;
    }

    private static JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(214, 222, 232)), title),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return panel;
    }

    private static JPanel createCheckGrid(int columns, JComponent[] components) {
        int safeColumns = Math.max(1, columns);
        int rows = Math.max(1, (components.length + safeColumns - 1) / safeColumns);
        JPanel panel = new JPanel(new GridLayout(rows, safeColumns, 10, 10));
        panel.setOpaque(false);
        for (int i = 0; i < components.length; i++) {
            panel.add(components[i]);
        }
        return panel;
    }

    private static JScrollPane wrapDialogTab(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        return scrollPane;
    }

    private static void addDialogRow(JPanel panel, GridBagConstraints gbc, String label, Component component) {
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(component, gbc);
        gbc.gridy++;
    }

    private static void addDialogWide(JPanel panel, GridBagConstraints gbc, Component component) {
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        panel.add(component, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
    }

    private static GridBagConstraints dialogGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        return gbc;
    }

    private static void addCompactAutoTabs(
        JTabbedPane tabs,
        AutoConfig config,
        List<InventoryChoice> cachedInventory,
        JCheckBox enabledBox,
        JCheckBox autoAttackBox,
        JCheckBox autoHealBox,
        JCheckBox autoPartyBox,
        JCheckBox autoComeHomeBox,
        JCheckBox autoTrainByLevelBox,
        JCheckBox autoVanTieuBox,
        JCheckBox autoSellFarmGearBox,
        JCheckBox autoRepairGearBox,
        JCheckBox reloadNoDamageBox,
        JCheckBox pickupPotionBox,
        JCheckBox pickupEquipmentBox,
        JCheckBox pickupMaterialBox,
        JCheckBox pickupAllBox,
        JCheckBox autoUpgradeSkillBox,
        JCheckBox autoUpgradePotentialBox,
        JCheckBox autoUseInventoryItemsBox,
        JComboBox<String> characterClassBox,
        JComboBox<String> targetModeBox,
        JComboBox<String> teamModeBox,
        JComboBox<String> resourceModeBox,
        JTextField leaderNameField,
        JTextField memberNamesField,
        JComboBox<SkillOption>[] attackSkillBoxes,
        JComboBox<SkillOption>[] buffSkillBoxes,
        JComboBox<SkillOption>[] upgradeSkillBoxes,
        JComboBox<PotentialOption> potentialTargetBox,
        JComboBox<InventoryChoice>[] autoItemBoxes,
        JSpinner followDistanceSpinner,
        JSpinner hpSpinner,
        JSpinner mpSpinner,
        JSpinner repairDurabilitySpinner,
        JSpinner noDamageSpinner,
        JSpinner skillUpgradeIntervalSpinner,
        JSpinner potentialAmountSpinner,
        JSpinner potentialIntervalSpinner,
        JSpinner inventoryItemIntervalSpinner
    ) {
        JPanel autoTab = createSectionPanel("\u0110i\u1ec1u khi\u1ec3n");
        GridBagConstraints autoGbc = dialogGbc();
        addDialogWide(autoTab, autoGbc, createCheckGrid(2, new JComponent[]{
            enabledBox,
            autoAttackBox,
            autoHealBox,
            autoPartyBox,
            autoComeHomeBox,
            autoTrainByLevelBox,
            autoVanTieuBox
        }));
        addDialogRow(autoTab, autoGbc, "H\u1ec7 nh\u00e2n v\u1eadt", characterClassBox);
        addDialogRow(autoTab, autoGbc, "Ki\u1ec3u focus", targetModeBox);
        addDialogRow(autoTab, autoGbc, "T\u1ed5 \u0111\u1ed9i", teamModeBox);
        addDialogRow(autoTab, autoGbc, "Nh\u00f3m tr\u01b0\u1edfng", leaderNameField);
        addDialogRow(autoTab, autoGbc, "Th\u00e0nh vi\u00ean", memberNamesField);
        addDialogRow(autoTab, autoGbc, "Kho\u1ea3ng c\u00e1ch b\u00e1m", followDistanceSpinner);
        addDialogRow(autoTab, autoGbc, "Ch\u1ebf \u0111\u1ed9 m\u00e1y", resourceModeBox);
        addDialogRow(autoTab, autoGbc, "B\u01a1m HP %", hpSpinner);
        addDialogRow(autoTab, autoGbc, "B\u01a1m MP %", mpSpinner);
        addDialogRow(autoTab, autoGbc, "S\u1eeda \u0111\u1ed3 <= \u0111\u1ed9 b\u1ec1n", repairDurabilitySpinner);
        addDialogRow(autoTab, autoGbc, "Reload n\u1ebfu \u0111\u1ee9ng damage", noDamageSpinner);
        tabs.addTab("T\u1ed5ng qu\u00e1t", wrapDialogTab(autoTab));

        JPanel skillTab = new JPanel(new GridLayout(2, 1, 0, 8));
        skillTab.setOpaque(false);
        JPanel attackSection = createSectionPanel("K\u1ef9 n\u0103ng \u0111\u00e1nh");
        attackSection.setLayout(new BorderLayout(0, 6));
        attackSection.add(new JLabel("Ch\u1ecdn t\u1ed1i \u0111a 3 k\u1ef9 n\u0103ng \u0111\u00e1nh."), BorderLayout.NORTH);
        attackSection.add(createSkillSelectorPanel(attackSkillBoxes), BorderLayout.CENTER);
        JPanel buffSection = createSectionPanel("K\u1ef9 n\u0103ng buff");
        buffSection.setLayout(new BorderLayout(0, 6));
        buffSection.add(new JLabel("Ch\u1ecdn t\u1ed1i \u0111a 3 k\u1ef9 n\u0103ng buff."), BorderLayout.NORTH);
        buffSection.add(createSkillSelectorPanel(buffSkillBoxes), BorderLayout.CENTER);
        skillTab.add(attackSection);
        skillTab.add(buffSection);
        tabs.addTab("K\u1ef9 n\u0103ng", wrapDialogTab(skillTab));

        JPanel upgradeTab = new JPanel(new GridLayout(3, 1, 0, 8));
        upgradeTab.setOpaque(false);
        JPanel skillUpgradeSection = createSectionPanel("Auto c\u1ed9ng k\u1ef9 n\u0103ng");
        GridBagConstraints skillUpgradeGbc = dialogGbc();
        addDialogWide(skillUpgradeSection, skillUpgradeGbc, autoUpgradeSkillBox);
        addDialogWide(skillUpgradeSection, skillUpgradeGbc, new JLabel("Th\u1ee9 t\u1ef1 \u01b0u ti\u00ean c\u1ed9ng k\u1ef9 n\u0103ng."));
        addDialogWide(skillUpgradeSection, skillUpgradeGbc, createSkillSelectorPanel(upgradeSkillBoxes));
        addDialogRow(skillUpgradeSection, skillUpgradeGbc, "M\u1ed7i l\u1ea7n c\u1ed9ng (gi\u00e2y)", skillUpgradeIntervalSpinner);
        JPanel potentialSection = createSectionPanel("Auto c\u1ed9ng ti\u1ec1m n\u0103ng");
        GridBagConstraints potentialGbc = dialogGbc();
        addDialogWide(potentialSection, potentialGbc, autoUpgradePotentialBox);
        addDialogRow(potentialSection, potentialGbc, "Ch\u1ec9 s\u1ed1 \u01b0u ti\u00ean", potentialTargetBox);
        addDialogRow(potentialSection, potentialGbc, "S\u1ed1 \u0111i\u1ec3m m\u1ed7i l\u1ea7n", potentialAmountSpinner);
        addDialogRow(potentialSection, potentialGbc, "M\u1ed7i l\u1ea7n c\u1ed9ng (gi\u00e2y)", potentialIntervalSpinner);
        JPanel potentialHint = createSectionPanel("Th\u1ee9 t\u1ef1 ch\u1ec9 s\u1ed1");
        GridBagConstraints potentialHintGbc = dialogGbc();
        addDialogWide(potentialHint, potentialHintGbc, new JLabel("1. S\u1ee9c m\u1ea1nh  2. Nhanh nh\u1eb9n  3. Tinh th\u1ea7n"));
        addDialogWide(potentialHint, potentialHintGbc, new JLabel("4. S\u1ee9c kh\u1ecfe  5. May m\u1eafn"));
        upgradeTab.add(skillUpgradeSection);
        upgradeTab.add(potentialSection);
        upgradeTab.add(potentialHint);
        tabs.addTab("N\u00e2ng c\u1ea5p", wrapDialogTab(upgradeTab));

        JPanel itemTab = new JPanel(new GridLayout(2, 1, 0, 8));
        itemTab.setOpaque(false);
        JPanel inventorySection = createSectionPanel("Auto d\u00f9ng v\u1eadt ph\u1ea9m");
        GridBagConstraints inventoryGbc = dialogGbc();
        addDialogWide(inventorySection, inventoryGbc, autoUseInventoryItemsBox);
        addDialogWide(inventorySection, inventoryGbc, new JLabel(
            cachedInventory.isEmpty()
                ? "Ch\u01b0a c\u00f3 cache v\u1eadt ph\u1ea9m th\u01b0\u1eddng. H\u00e3y m\u1edf acc \u0111\u1ec3 launcher c\u1eadp nh\u1eadt."
                : "Ch\u1ecdn t\u1ed1i \u0111a 3 v\u1eadt ph\u1ea9m th\u01b0\u1eddng t\u1eeb cache h\u00e0nh trang."
        ));
        addDialogWide(inventorySection, inventoryGbc, createSkillSelectorPanel(autoItemBoxes));
        addDialogRow(inventorySection, inventoryGbc, "M\u1ed7i l\u1ea7n d\u00f9ng (gi\u00e2y)", inventoryItemIntervalSpinner);
        JPanel supportSection = createSectionPanel("Nh\u1eb7t \u0111\u1ed3 v\u00e0 b\u1ea3o tr\u00ec");
        GridBagConstraints supportGbc = dialogGbc();
        addDialogWide(supportSection, supportGbc, createCheckGrid(2, new JComponent[]{
            reloadNoDamageBox,
            autoSellFarmGearBox,
            autoRepairGearBox,
            pickupPotionBox,
            pickupEquipmentBox,
            pickupMaterialBox,
            pickupAllBox
        }));
        itemTab.add(inventorySection);
        itemTab.add(supportSection);
        tabs.addTab("V\u1eadt ph\u1ea9m", wrapDialogTab(itemTab));
    }

    private static void applyCompactAutoConfig(
        AutoConfig config,
        JCheckBox autoAttackBox,
        JCheckBox autoHealBox,
        JCheckBox autoPartyBox,
        JCheckBox autoComeHomeBox,
        JCheckBox autoTrainByLevelBox,
        JCheckBox autoVanTieuBox,
        JCheckBox autoSellFarmGearBox,
        JCheckBox autoRepairGearBox,
        JCheckBox reloadNoDamageBox,
        JCheckBox pickupPotionBox,
        JCheckBox pickupEquipmentBox,
        JCheckBox pickupMaterialBox,
        JCheckBox pickupAllBox,
        JCheckBox autoUpgradeSkillBox,
        JCheckBox autoUpgradePotentialBox,
        JCheckBox autoUseInventoryItemsBox,
        JComboBox<String> characterClassBox,
        JComboBox<String> targetModeBox,
        JComboBox<String> teamModeBox,
        JComboBox<String> resourceModeBox,
        JTextField leaderNameField,
        JTextField memberNamesField,
        JComboBox<SkillOption>[] attackSkillBoxes,
        JComboBox<SkillOption>[] buffSkillBoxes,
        JComboBox<SkillOption>[] upgradeSkillBoxes,
        JComboBox<PotentialOption> potentialTargetBox,
        JComboBox<InventoryChoice>[] autoItemBoxes,
        JSpinner followDistanceSpinner,
        JSpinner hpSpinner,
        JSpinner mpSpinner,
        JSpinner repairDurabilitySpinner,
        JSpinner noDamageSpinner,
        JSpinner skillUpgradeIntervalSpinner,
        JSpinner potentialAmountSpinner,
        JSpinner potentialIntervalSpinner,
        JSpinner inventoryItemIntervalSpinner
    ) {
        config.characterClass = characterClassBox.getSelectedIndex();
        config.autoMode = buildAutoMode(autoAttackBox.isSelected(), autoHealBox.isSelected());
        config.focusMode = targetModeBox.getSelectedIndex();
        config.teamMode = teamModeBox.getSelectedIndex();
        config.leaderName = leaderNameField.getText().trim();
        config.teamMembers = memberNamesField.getText().trim();
        config.attackSkills = buildSkillConfigText(attackSkillBoxes);
        config.buffSkills = buildSkillConfigText(buffSkillBoxes);
        config.followDistance = ((Integer)followDistanceSpinner.getValue()).intValue();
        config.resourceMode = resourceModeBox.getSelectedIndex();
        config.hpPercent = ((Integer)hpSpinner.getValue()).intValue();
        config.mpPercent = ((Integer)mpSpinner.getValue()).intValue();
        config.autoParty = autoPartyBox.isSelected();
        config.autoComeHome = autoComeHomeBox.isSelected();
        config.autoTravelByLevel = autoTrainByLevelBox.isSelected();
        config.autoVanTieu = autoVanTieuBox.isSelected();
        config.autoSellFarmGear = autoSellFarmGearBox.isSelected();
        config.autoRepairGear = autoRepairGearBox.isSelected();
        config.repairDurabilityThreshold = ((Integer)repairDurabilitySpinner.getValue()).intValue();
        config.reloadWhenNoDamage = reloadNoDamageBox.isSelected();
        config.noDamageTimeoutSeconds = ((Integer)noDamageSpinner.getValue()).intValue();
        config.pickupPotion = pickupPotionBox.isSelected();
        config.pickupEquipment = pickupEquipmentBox.isSelected();
        config.pickupMaterial = pickupMaterialBox.isSelected();
        config.pickupAll = pickupAllBox.isSelected();
        config.autoUpgradeSkill = autoUpgradeSkillBox.isSelected();
        config.upgradeSkills = buildSkillConfigText(upgradeSkillBoxes);
        config.skillUpgradeIntervalSeconds = ((Integer)skillUpgradeIntervalSpinner.getValue()).intValue();
        config.autoUpgradePotential = autoUpgradePotentialBox.isSelected();
        config.potentialTargetIndex = selectedPotentialIndex(potentialTargetBox);
        config.potentialPointsPerAdd = ((Integer)potentialAmountSpinner.getValue()).intValue();
        config.potentialIntervalSeconds = ((Integer)potentialIntervalSpinner.getValue()).intValue();
        config.autoUseInventoryItems = autoUseInventoryItemsBox.isSelected();
        config.inventoryUseItems = buildInventoryConfigText(autoItemBoxes);
        config.inventoryItemIntervalSeconds = ((Integer)inventoryItemIntervalSpinner.getValue()).intValue();
    }

    private enum ConfigMode {
        ALL,
        LOGIN,
        AUTO;

        private static ConfigMode parse(String raw) {
            String safe = raw == null ? "" : raw.trim().toLowerCase(Locale.ROOT);
            if ("login".equals(safe)) {
                return LOGIN;
            }
            if ("auto".equals(safe)) {
                return AUTO;
            }
            return ALL;
        }
    }

    private static AutoConfig promptForConfig(AutoConfig config, Path clientJar, Path dataDir, ConfigMode mode) {
        return promptForConfigCompact(config, clientJar, dataDir, mode);
    }

    private static AutoConfig promptForConfigLegacy(AutoConfig config, Path clientJar, Path dataDir, ConfigMode mode) {
        boolean editingLogin = mode != ConfigMode.AUTO;
        boolean editingAuto = mode != ConfigMode.LOGIN;
        JCheckBox enabledBox = new JCheckBox("Bat profile tu dong", config.enabled);
        JCheckBox loginLockedBox = new JCheckBox("Khoa cau hinh dang nhap", config.loginSettingsLocked);
        JCheckBox autoAttackBox = new JCheckBox("Tu dong danh", isAttackAutoEnabled(config.autoMode));
        JCheckBox autoHealBox = new JCheckBox("Tu bom HP/MP", isHealAutoEnabled(config.autoMode));
        JTextField userField = new JTextField(config.username, 18);
        JPasswordField passField = new JPasswordField(config.password, 18);
        JComboBox<String> serverBox = new JComboBox<String>(SERVER_OPTIONS);
        JTextField hostField = new JTextField(config.customHost, 18);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(config.customPort, 1, 65535, 1));
        JTextField serverNameField = new JTextField(config.customServerName, 18);
        JComboBox<String> characterBox = new JComboBox<String>(CHARACTER_OPTIONS);
        JComboBox<String> characterClassBox = new JComboBox<String>(CHARACTER_CLASS_OPTIONS);
        JComboBox<String> targetModeBox = new JComboBox<String>(TARGET_MODE_OPTIONS);
        JComboBox<String> teamModeBox = new JComboBox<String>(TEAM_MODE_OPTIONS);
        JComboBox<String> resourceModeBox = new JComboBox<String>(RESOURCE_MODE_OPTIONS);
        JTextField leaderNameField = new JTextField(config.leaderName, 18);
        JTextField memberNamesField = new JTextField(config.teamMembers, 18);
        @SuppressWarnings("unchecked")
        JComboBox<SkillOption>[] attackSkillBoxes = new JComboBox[]{
            new JComboBox<SkillOption>(),
            new JComboBox<SkillOption>(),
            new JComboBox<SkillOption>()
        };
        @SuppressWarnings("unchecked")
        JComboBox<SkillOption>[] buffSkillBoxes = new JComboBox[]{
            new JComboBox<SkillOption>(),
            new JComboBox<SkillOption>(),
            new JComboBox<SkillOption>()
        };
        JPanel attackSkillPanel = createSkillSelectorPanel(attackSkillBoxes);
        JPanel buffSkillPanel = createSkillSelectorPanel(buffSkillBoxes);
        JSpinner hpSpinner = new JSpinner(new SpinnerNumberModel(config.hpPercent, 1, 100, 1));
        JSpinner mpSpinner = new JSpinner(new SpinnerNumberModel(config.mpPercent, 1, 100, 1));
        JSpinner followDistanceSpinner = new JSpinner(new SpinnerNumberModel(config.followDistance, 16, 256, 4));
        JSpinner repairDurabilitySpinner = new JSpinner(new SpinnerNumberModel(config.repairDurabilityThreshold, 1, 50, 1));
        JSpinner noDamageSpinner = new JSpinner(new SpinnerNumberModel(config.noDamageTimeoutSeconds, 3, 20, 1));
        JSpinner reconnectSpinner = new JSpinner(new SpinnerNumberModel((int)(config.reconnectDelayMs / 1000L), 1, 60, 1));
        JCheckBox autoPartyBox = new JCheckBox("Tu vao nhom", config.autoParty);
        JCheckBox autoComeHomeBox = new JCheckBox("Tu ve lang", config.autoComeHome);
        JCheckBox autoTrainByLevelBox = new JCheckBox("Tu tim bai theo level (+/-5)", config.autoTravelByLevel);
        JCheckBox autoVanTieuBox = new JCheckBox("Tu dong van tieu", config.autoVanTieu);
        JCheckBox autoSellFarmGearBox = new JCheckBox("Tu ban do nhat duoc khi day tui", config.autoSellFarmGear);
        JCheckBox autoRepairGearBox = new JCheckBox("Tu sua do tai bai", config.autoRepairGear);
        JCheckBox reloadNoDamageBox = new JCheckBox("Khong danh duoc 3-4s thi reload acc", config.reloadWhenNoDamage);
        JCheckBox pickupPotionBox = new JCheckBox("Nhat HP + MP", config.pickupPotion);
        JCheckBox pickupEquipmentBox = new JCheckBox("Nhat trang bi", config.pickupEquipment);
        JCheckBox pickupMaterialBox = new JCheckBox("Nhat nguyen lieu", config.pickupMaterial);
        JCheckBox pickupAllBox = new JCheckBox("Nhat het", config.pickupAll);

        serverBox.setSelectedIndex(clamp(config.serverIndex, 0, SERVER_OPTIONS.length - 1));
        characterBox.setSelectedIndex(clamp(config.characterIndex, 0, CHARACTER_OPTIONS.length - 1));
        characterClassBox.setSelectedIndex(clamp(config.characterClass, 0, CHARACTER_CLASS_OPTIONS.length - 1));
        targetModeBox.setSelectedIndex(clamp(config.focusMode, 0, TARGET_MODE_OPTIONS.length - 1));
        teamModeBox.setSelectedIndex(clamp(config.teamMode, 0, TEAM_MODE_OPTIONS.length - 1));
        resourceModeBox.setSelectedIndex(clamp(config.resourceMode, 0, RESOURCE_MODE_OPTIONS.length - 1));
        refreshSkillSelectors(characterClassBox.getSelectedIndex(), attackSkillBoxes, buffSkillBoxes, config.parseAttackSkills(), config.parseBuffSkills());
        final JComponent[] loginEditors = new JComponent[]{
            userField,
            passField,
            serverBox,
            hostField,
            portSpinner,
            serverNameField,
            characterBox,
            reconnectSpinner
        };
        setComponentsEnabled(!loginLockedBox.isSelected(), loginEditors);
        loginLockedBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setComponentsEnabled(!loginLockedBox.isSelected(), loginEditors);
            }
        });
        characterClassBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                refreshSkillSelectors(characterClassBox.getSelectedIndex(), attackSkillBoxes, buffSkillBoxes, selectedSkillIds(attackSkillBoxes), selectedSkillIds(buffSkillBoxes));
            }
        });
        pickupAllBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!pickupAllBox.isSelected()) {
                    return;
                }
                pickupPotionBox.setSelected(true);
                pickupEquipmentBox.setSelected(true);
                pickupMaterialBox.setSelected(true);
            }
        });

        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font subtitleFont = new Font("Segoe UI", Font.PLAIN, 12);
        Color hero = new Color(31, 59, 96);
        Color heroMuted = new Color(224, 232, 242);
        String profileLabel = config.profileName.length() > 0 ? config.profileName : (config.username.length() > 0 ? config.username : "Profile moi");

        JPanel heroPanel = new JPanel(new BorderLayout(16, 10));
        heroPanel.setBackground(hero);
        heroPanel.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        JLabel titleLabel = new JLabel(mode == ConfigMode.LOGIN ? "Cau hinh dang nhap" : (mode == ConfigMode.AUTO ? "Cau hinh auto" : "KPAH Auto Tool"));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleFont);
        JTextArea subtitle = new JTextArea(
            mode == ConfigMode.LOGIN
                ? "Sua thong tin tai khoan, server, nhan vat va reconnect cho profile dang chon."
                : (mode == ConfigMode.AUTO
                    ? "Bat/tat tu dong danh, bom HP/MP, chon ky nang va cac tuy chon farm/bao tri."
                    : "Chia cau hinh theo tung nhom de de treo acc, de doc va de sua nhanh hon.")
        );
        subtitle.setEditable(false);
        subtitle.setOpaque(false);
        subtitle.setLineWrap(true);
        subtitle.setWrapStyleWord(true);
        subtitle.setForeground(heroMuted);
        subtitle.setFont(subtitleFont);
        JPanel heroText = new JPanel(new BorderLayout(0, 6));
        heroText.setOpaque(false);
        heroText.add(titleLabel, BorderLayout.NORTH);
        heroText.add(subtitle, BorderLayout.CENTER);

        JPanel heroMeta = new JPanel(new GridLayout(0, 1, 0, 4));
        heroMeta.setOpaque(false);
        JLabel profileMeta = new JLabel("Profile: " + profileLabel);
        profileMeta.setForeground(Color.WHITE);
        JLabel clientMeta = new JLabel("Client: " + clientJar.getFileName());
        clientMeta.setForeground(heroMuted);
        JLabel dataMeta = new JLabel("Data: " + dataDir.getFileName());
        dataMeta.setForeground(heroMuted);
        heroMeta.add(profileMeta);
        heroMeta.add(clientMeta);
        heroMeta.add(dataMeta);

        heroPanel.add(heroText, BorderLayout.CENTER);
        heroPanel.add(heroMeta, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();

        if (editingLogin) {
            JPanel loginTab = createSectionPanel("Dang nhap va ket noi");
            GridBagConstraints loginGbc = new GridBagConstraints();
            loginGbc.insets = new Insets(6, 6, 6, 6);
            loginGbc.anchor = GridBagConstraints.WEST;
            loginGbc.fill = GridBagConstraints.HORIZONTAL;
            loginGbc.gridx = 0;
            loginGbc.gridy = 0;
            addDialogWide(loginTab, loginGbc, loginLockedBox);
            addDialogRow(loginTab, loginGbc, "Tai khoan", userField);
            addDialogRow(loginTab, loginGbc, "Mat khau", passField);
            if (config.machineLoginSource.length() > 0) {
                addDialogRow(loginTab, loginGbc, "Acc tren may", new JLabel(config.username + " (da tu nhan)"));
            }
            addDialogRow(loginTab, loginGbc, "Server", serverBox);
            addDialogRow(loginTab, loginGbc, "IP server", hostField);
            addDialogRow(loginTab, loginGbc, "Port", portSpinner);
            addDialogRow(loginTab, loginGbc, "Ten server", serverNameField);
            addDialogRow(loginTab, loginGbc, "Nhan vat", characterBox);
            addDialogRow(loginTab, loginGbc, "Delay reconnect (s)", reconnectSpinner);
            tabs.addTab("Dang nhap", wrapDialogTab(loginTab));
        }

        if (editingAuto) {
            JPanel autoTab = createSectionPanel("Dieu khien auto");
            GridBagConstraints autoGbc = new GridBagConstraints();
            autoGbc.insets = new Insets(6, 6, 6, 6);
            autoGbc.anchor = GridBagConstraints.WEST;
            autoGbc.fill = GridBagConstraints.HORIZONTAL;
            autoGbc.gridx = 0;
            autoGbc.gridy = 0;

            JPanel autoSwitches = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
            autoSwitches.setOpaque(false);
            autoSwitches.add(enabledBox);
            autoSwitches.add(autoAttackBox);
            autoSwitches.add(autoHealBox);
            addDialogWide(autoTab, autoGbc, autoSwitches);
            addDialogRow(autoTab, autoGbc, "He nhan vat", characterClassBox);
            addDialogRow(autoTab, autoGbc, "Kieu focus", targetModeBox);
            addDialogRow(autoTab, autoGbc, "To doi", teamModeBox);
            addDialogRow(autoTab, autoGbc, "Nhom truong", leaderNameField);
            addDialogRow(autoTab, autoGbc, "Thanh vien", memberNamesField);
            addDialogRow(autoTab, autoGbc, "Bam leader", followDistanceSpinner);
            addDialogRow(autoTab, autoGbc, "Che do may", resourceModeBox);
            addDialogRow(autoTab, autoGbc, "Bom HP %", hpSpinner);
            addDialogRow(autoTab, autoGbc, "Bom MP %", mpSpinner);
            addDialogRow(autoTab, autoGbc, "Sua do <= do ben", repairDurabilitySpinner);
            addDialogRow(autoTab, autoGbc, "Reload neu khong damage", noDamageSpinner);
            tabs.addTab("Auto", wrapDialogTab(autoTab));

            JPanel skillTab = new JPanel(new GridLayout(2, 1, 0, 12));
            skillTab.setOpaque(false);
            JPanel attackSection = createSectionPanel("Ky nang danh");
            attackSection.setLayout(new BorderLayout(0, 10));
            attackSection.add(new JLabel("Chon toi da 3 skill danh de dat vao hang auto."), BorderLayout.NORTH);
            attackSection.add(attackSkillPanel, BorderLayout.CENTER);
            JPanel buffSection = createSectionPanel("Ky nang buff");
            buffSection.setLayout(new BorderLayout(0, 10));
            buffSection.add(new JLabel("Skill buff se duoc cap nhat lai dinh ky khi vao game."), BorderLayout.NORTH);
            buffSection.add(buffSkillPanel, BorderLayout.CENTER);
            skillTab.add(attackSection);
            skillTab.add(buffSection);
            tabs.addTab("Ky nang", wrapDialogTab(skillTab));

            JPanel supportTab = createSectionPanel("Vat pham va bao tri");
            GridBagConstraints supportGbc = new GridBagConstraints();
            supportGbc.insets = new Insets(6, 6, 6, 6);
            supportGbc.anchor = GridBagConstraints.WEST;
            supportGbc.fill = GridBagConstraints.HORIZONTAL;
            supportGbc.gridx = 0;
            supportGbc.gridy = 0;
            addDialogWide(
                supportTab,
                supportGbc,
                createCheckGrid(
                    2,
                    new JComponent[]{
                        autoPartyBox,
                        autoComeHomeBox,
                        autoTrainByLevelBox,
                        autoVanTieuBox,
                        reloadNoDamageBox,
                        autoSellFarmGearBox,
                        autoRepairGearBox,
                        pickupPotionBox,
                        pickupEquipmentBox,
                        pickupMaterialBox,
                        pickupAllBox
                    }
                )
            );
            tabs.addTab("Vat pham", wrapDialogTab(supportTab));
        }

        JPanel infoPanel = createSectionPanel("Thong tin bo chay");
        GridBagConstraints infoGbc = new GridBagConstraints();
        infoGbc.insets = new Insets(6, 6, 6, 6);
        infoGbc.anchor = GridBagConstraints.WEST;
        infoGbc.fill = GridBagConstraints.HORIZONTAL;
        infoGbc.gridx = 0;
        infoGbc.gridy = 0;
        addDialogRow(infoPanel, infoGbc, "Profile", new JLabel(profileLabel));
        addDialogRow(infoPanel, infoGbc, "Client jar", new JLabel(clientJar.getFileName().toString()));
        addDialogRow(infoPanel, infoGbc, "Data", new JLabel(dataDir.getFileName().toString()));
        tabs.addTab("Thong tin", wrapDialogTab(infoPanel));

        String noteText;
        if (mode == ConfigMode.LOGIN) {
            noteText =
                "Dang nhap duoc tach rieng de tranh sua nham luc dang treo. " +
                "Neu can doi tai khoan, server, IP hoac nhan vat thi mo tab Dang nhap va bo khoa truoc khi sua.";
        } else if (mode == ConfigMode.AUTO) {
            noteText =
                "Tab Auto dung de bat/tat tu dong danh va bom HP/MP ro rang. " +
                "Tab Ky nang cho chon skill theo he nhan vat, tab Vat pham gom nhat do, sua do, ban do va watchdog.";
        } else {
            noteText =
                "Giao dien moi tach tung nhom cau hinh theo tab de de nhin hon. " +
                "Neu dang treo acc, ban co the mo phan Auto de sua nhanh va ap dung lai ma khong can tat profile.";
        }
        JTextArea note = new JTextArea(noteText);
        note.setEditable(false);
        note.setOpaque(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setFont(subtitleFont);
        note.setBorder(BorderFactory.createEmptyBorder(6, 4, 0, 4));

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setPreferredSize(new Dimension(980, mode == ConfigMode.AUTO ? 760 : 720));
        content.add(heroPanel, BorderLayout.NORTH);
        content.add(tabs, BorderLayout.CENTER);
        content.add(note, BorderLayout.SOUTH);

        Object[] options = new Object[]{"Luu cau hinh", "Huy"};
        int result = JOptionPane.showOptionDialog(
            null,
            content,
            APP_NAME + (mode == ConfigMode.LOGIN ? " - Dang nhap" : (mode == ConfigMode.AUTO ? " - Auto" : "")),
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        if (result != 0) {
            return null;
        }

        if (editingAuto || mode == ConfigMode.ALL) {
            config.enabled = enabledBox.isSelected();
        }
        if (editingLogin) {
            config.loginSettingsLocked = loginLockedBox.isSelected();
            config.username = userField.getText().trim();
            config.password = new String(passField.getPassword());
            config.serverIndex = serverBox.getSelectedIndex();
            config.customHost = hostField.getText().trim();
            config.customPort = ((Integer)portSpinner.getValue()).intValue();
            config.customServerName = serverNameField.getText().trim();
            config.characterIndex = characterBox.getSelectedIndex();
            config.reconnectDelayMs = ((Integer)reconnectSpinner.getValue()).intValue() * 1000L;
        }
        if (editingAuto) {
            config.characterClass = characterClassBox.getSelectedIndex();
            config.autoMode = buildAutoMode(autoAttackBox.isSelected(), autoHealBox.isSelected());
            config.focusMode = targetModeBox.getSelectedIndex();
            config.teamMode = teamModeBox.getSelectedIndex();
            config.leaderName = leaderNameField.getText().trim();
            config.teamMembers = memberNamesField.getText().trim();
            config.attackSkills = buildSkillConfigText(attackSkillBoxes);
            config.buffSkills = buildSkillConfigText(buffSkillBoxes);
            config.followDistance = ((Integer)followDistanceSpinner.getValue()).intValue();
            config.resourceMode = resourceModeBox.getSelectedIndex();
            config.hpPercent = ((Integer)hpSpinner.getValue()).intValue();
            config.mpPercent = ((Integer)mpSpinner.getValue()).intValue();
            config.autoParty = autoPartyBox.isSelected();
            config.autoComeHome = autoComeHomeBox.isSelected();
            config.autoTravelByLevel = autoTrainByLevelBox.isSelected();
            config.autoVanTieu = autoVanTieuBox.isSelected();
            config.autoSellFarmGear = autoSellFarmGearBox.isSelected();
            config.autoRepairGear = autoRepairGearBox.isSelected();
            config.repairDurabilityThreshold = ((Integer)repairDurabilitySpinner.getValue()).intValue();
            config.reloadWhenNoDamage = reloadNoDamageBox.isSelected();
            config.noDamageTimeoutSeconds = ((Integer)noDamageSpinner.getValue()).intValue();
            config.pickupPotion = pickupPotionBox.isSelected();
            config.pickupEquipment = pickupEquipmentBox.isSelected();
            config.pickupMaterial = pickupMaterialBox.isSelected();
            config.pickupAll = pickupAllBox.isSelected();
        }
        config.normalize();
        return config;
    }

    private static AutoConfig promptForConfigCompact(AutoConfig config, Path clientJar, Path dataDir, ConfigMode mode) {
        boolean editingLogin = mode != ConfigMode.AUTO;
        boolean editingAuto = mode != ConfigMode.LOGIN;
        List<InventoryChoice> cachedInventory = loadInventoryChoices(dataDir);
        JCheckBox enabledBox = new JCheckBox("B\u1eadt h\u1ed3 s\u01a1 t\u1ef1 \u0111\u1ed9ng", config.enabled);
        JCheckBox loginLockedBox = new JCheckBox("Kh\u00f3a c\u1ea5u h\u00ecnh \u0111\u0103ng nh\u1eadp", config.loginSettingsLocked);
        JCheckBox autoAttackBox = new JCheckBox("T\u1ef1 \u0111\u1ed9ng \u0111\u00e1nh", isAttackAutoEnabled(config.autoMode));
        JCheckBox autoHealBox = new JCheckBox("T\u1ef1 b\u01a1m HP/MP", isHealAutoEnabled(config.autoMode));
        JCheckBox autoPartyBox = new JCheckBox("T\u1ef1 v\u00e0o nh\u00f3m", config.autoParty);
        JCheckBox autoComeHomeBox = new JCheckBox("T\u1ef1 v\u1ec1 l\u00e0ng", config.autoComeHome);
        JCheckBox autoTrainByLevelBox = new JCheckBox("T\u1ef1 t\u00ecm b\u00e3i theo level (+/-5)", config.autoTravelByLevel);
        JCheckBox autoVanTieuBox = new JCheckBox("T\u1ef1 \u0111\u1ed9ng v\u1eadn ti\u00eau", config.autoVanTieu);
        JCheckBox autoSellFarmGearBox = new JCheckBox("T\u1ef1 b\u00e1n \u0111\u1ed3 nh\u1eb7t \u0111\u01b0\u1ee3c khi \u0111\u1ea7y t\u00fai", config.autoSellFarmGear);
        JCheckBox autoRepairGearBox = new JCheckBox("T\u1ef1 s\u1eeda \u0111\u1ed3 t\u1ea1i b\u00e3i", config.autoRepairGear);
        JCheckBox reloadNoDamageBox = new JCheckBox("Kh\u00f4ng \u0111\u00e1nh \u0111\u01b0\u1ee3c th\u00ec reload", config.reloadWhenNoDamage);
        JCheckBox pickupPotionBox = new JCheckBox("Nh\u1eb7t HP + MP", config.pickupPotion);
        JCheckBox pickupEquipmentBox = new JCheckBox("Nh\u1eb7t trang b\u1ecb", config.pickupEquipment);
        JCheckBox pickupMaterialBox = new JCheckBox("Nh\u1eb7t nguy\u00ean li\u1ec7u", config.pickupMaterial);
        JCheckBox pickupAllBox = new JCheckBox("Nh\u1eb7t h\u1ebft", config.pickupAll);
        JCheckBox autoUpgradeSkillBox = new JCheckBox("Auto c\u1ed9ng k\u1ef9 n\u0103ng", config.autoUpgradeSkill);
        JCheckBox autoUpgradePotentialBox = new JCheckBox("Auto c\u1ed9ng ti\u1ec1m n\u0103ng", config.autoUpgradePotential);
        JCheckBox autoUseInventoryItemsBox = new JCheckBox("Auto d\u00f9ng v\u1eadt ph\u1ea9m trong h\u00e0nh trang", config.autoUseInventoryItems);
        JTextField userField = new JTextField(config.username, 18);
        JPasswordField passField = new JPasswordField(config.password, 18);
        JTextField hostField = new JTextField(config.customHost, 18);
        JTextField serverNameField = new JTextField(config.customServerName, 18);
        JTextField leaderNameField = new JTextField(config.leaderName, 18);
        JTextField memberNamesField = new JTextField(config.teamMembers, 18);
        JComboBox<String> serverBox = new JComboBox<String>(SERVER_OPTIONS);
        JComboBox<String> characterBox = new JComboBox<String>(CHARACTER_OPTIONS);
        JComboBox<String> characterClassBox = new JComboBox<String>(CHARACTER_CLASS_OPTIONS);
        JComboBox<String> targetModeBox = new JComboBox<String>(TARGET_MODE_OPTIONS);
        JComboBox<String> teamModeBox = new JComboBox<String>(TEAM_MODE_OPTIONS);
        JComboBox<String> resourceModeBox = new JComboBox<String>(RESOURCE_MODE_OPTIONS);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(config.customPort, 1, 65535, 1));
        JSpinner hpSpinner = new JSpinner(new SpinnerNumberModel(config.hpPercent, 1, 100, 1));
        JSpinner mpSpinner = new JSpinner(new SpinnerNumberModel(config.mpPercent, 1, 100, 1));
        JSpinner followDistanceSpinner = new JSpinner(new SpinnerNumberModel(config.followDistance, 16, 256, 4));
        JSpinner repairDurabilitySpinner = new JSpinner(new SpinnerNumberModel(config.repairDurabilityThreshold, 1, 50, 1));
        JSpinner noDamageSpinner = new JSpinner(new SpinnerNumberModel(config.noDamageTimeoutSeconds, 3, 20, 1));
        JSpinner reconnectSpinner = new JSpinner(new SpinnerNumberModel((int)(config.reconnectDelayMs / 1000L), 1, 60, 1));
        JSpinner skillUpgradeIntervalSpinner = new JSpinner(new SpinnerNumberModel(config.skillUpgradeIntervalSeconds, 2, 60, 1));
        JSpinner potentialAmountSpinner = new JSpinner(new SpinnerNumberModel(config.potentialPointsPerAdd, 1, 20, 1));
        JSpinner potentialIntervalSpinner = new JSpinner(new SpinnerNumberModel(config.potentialIntervalSeconds, 2, 60, 1));
        JSpinner inventoryItemIntervalSpinner = new JSpinner(new SpinnerNumberModel(config.inventoryItemIntervalSeconds, 3, 600, 1));
        @SuppressWarnings("unchecked")
        JComboBox<SkillOption>[] attackSkillBoxes = new JComboBox[]{new JComboBox<SkillOption>(), new JComboBox<SkillOption>(), new JComboBox<SkillOption>()};
        @SuppressWarnings("unchecked")
        JComboBox<SkillOption>[] buffSkillBoxes = new JComboBox[]{new JComboBox<SkillOption>(), new JComboBox<SkillOption>(), new JComboBox<SkillOption>()};
        @SuppressWarnings("unchecked")
        JComboBox<SkillOption>[] upgradeSkillBoxes = new JComboBox[]{new JComboBox<SkillOption>(), new JComboBox<SkillOption>(), new JComboBox<SkillOption>()};
        JComboBox<PotentialOption> potentialTargetBox = new JComboBox<PotentialOption>();
        @SuppressWarnings("unchecked")
        JComboBox<InventoryChoice>[] autoItemBoxes = new JComboBox[]{new JComboBox<InventoryChoice>(), new JComboBox<InventoryChoice>(), new JComboBox<InventoryChoice>()};
        serverBox.setSelectedIndex(clamp(config.serverIndex, 0, SERVER_OPTIONS.length - 1));
        characterBox.setSelectedIndex(clamp(config.characterIndex, 0, CHARACTER_OPTIONS.length - 1));
        characterClassBox.setSelectedIndex(clamp(config.characterClass, 0, CHARACTER_CLASS_OPTIONS.length - 1));
        targetModeBox.setSelectedIndex(clamp(config.focusMode, 0, TARGET_MODE_OPTIONS.length - 1));
        teamModeBox.setSelectedIndex(clamp(config.teamMode, 0, TEAM_MODE_OPTIONS.length - 1));
        resourceModeBox.setSelectedIndex(clamp(config.resourceMode, 0, RESOURCE_MODE_OPTIONS.length - 1));
        refreshSkillSelectors(characterClassBox.getSelectedIndex(), attackSkillBoxes, buffSkillBoxes, config.parseAttackSkills(), config.parseBuffSkills());
        refreshUpgradeSkillSelectors(characterClassBox.getSelectedIndex(), upgradeSkillBoxes, config.parseUpgradeSkills());
        populatePotentialCombo(potentialTargetBox, config.potentialTargetIndex);
        int[] savedInventoryIds = config.parseInventoryTemplateIds();
        for (int i = 0; i < autoItemBoxes.length; i++) {
            populateInventoryCombo(autoItemBoxes[i], cachedInventory, savedInventoryIds[Math.min(i, savedInventoryIds.length - 1)]);
        }
        final JComponent[] loginEditors = new JComponent[]{userField, passField, serverBox, hostField, portSpinner, serverNameField, characterBox, reconnectSpinner};
        setComponentsEnabled(!loginLockedBox.isSelected(), loginEditors);
        loginLockedBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setComponentsEnabled(!loginLockedBox.isSelected(), loginEditors);
            }
        });
        characterClassBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int classIndex = characterClassBox.getSelectedIndex();
                refreshSkillSelectors(classIndex, attackSkillBoxes, buffSkillBoxes, selectedSkillIds(attackSkillBoxes), selectedSkillIds(buffSkillBoxes));
                refreshUpgradeSkillSelectors(classIndex, upgradeSkillBoxes, selectedSkillIds(upgradeSkillBoxes));
            }
        });
        pickupAllBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (pickupAllBox.isSelected()) {
                    pickupPotionBox.setSelected(true);
                    pickupEquipmentBox.setSelected(true);
                    pickupMaterialBox.setSelected(true);
                }
            }
        });
        String profileLabel = config.profileName.length() > 0 ? config.profileName : (config.username.length() > 0 ? config.username : "h\u1ed3 s\u01a1 m\u1edbi");
        JTabbedPane tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        if (editingLogin) {
            JPanel loginTab = createSectionPanel("\u0110\u0103ng nh\u1eadp");
            GridBagConstraints loginGbc = dialogGbc();
            addDialogWide(loginTab, loginGbc, loginLockedBox);
            addDialogRow(loginTab, loginGbc, "T\u00e0i kho\u1ea3n", userField);
            addDialogRow(loginTab, loginGbc, "M\u1eadt kh\u1ea9u", passField);
            if (config.machineLoginSource.length() > 0) {
                addDialogRow(loginTab, loginGbc, "Acc tr\u00ean m\u00e1y", new JLabel(config.username + " (\u0111\u00e3 t\u1ef1 nh\u1eadn)"));
            }
            addDialogRow(loginTab, loginGbc, "Server", serverBox);
            addDialogRow(loginTab, loginGbc, "IP server", hostField);
            addDialogRow(loginTab, loginGbc, "Port", portSpinner);
            addDialogRow(loginTab, loginGbc, "T\u00ean server", serverNameField);
            addDialogRow(loginTab, loginGbc, "Nh\u00e2n v\u1eadt", characterBox);
            addDialogRow(loginTab, loginGbc, "Reconnect (gi\u00e2y)", reconnectSpinner);
            tabs.addTab("\u0110\u0103ng nh\u1eadp", wrapDialogTab(loginTab));
        }
        if (editingAuto) {
            addCompactAutoTabs(tabs, config, cachedInventory, enabledBox, autoAttackBox, autoHealBox, autoPartyBox, autoComeHomeBox, autoTrainByLevelBox, autoVanTieuBox, autoSellFarmGearBox, autoRepairGearBox, reloadNoDamageBox, pickupPotionBox, pickupEquipmentBox, pickupMaterialBox, pickupAllBox, autoUpgradeSkillBox, autoUpgradePotentialBox, autoUseInventoryItemsBox, characterClassBox, targetModeBox, teamModeBox, resourceModeBox, leaderNameField, memberNamesField, attackSkillBoxes, buffSkillBoxes, upgradeSkillBoxes, potentialTargetBox, autoItemBoxes, followDistanceSpinner, hpSpinner, mpSpinner, repairDurabilitySpinner, noDamageSpinner, skillUpgradeIntervalSpinner, potentialAmountSpinner, potentialIntervalSpinner, inventoryItemIntervalSpinner);
        }
        JPanel infoPanel = createSectionPanel("Th\u00f4ng tin");
        GridBagConstraints infoGbc = dialogGbc();
        addDialogRow(infoPanel, infoGbc, "H\u1ed3 s\u01a1", new JLabel(profileLabel));
        addDialogRow(infoPanel, infoGbc, "Client", new JLabel(clientJar.getFileName().toString()));
        addDialogRow(infoPanel, infoGbc, "Th\u01b0 m\u1ee5c data", new JLabel(dataDir.getFileName().toString()));
        addDialogRow(infoPanel, infoGbc, "Cache v\u1eadt ph\u1ea9m", new JLabel(cachedInventory.isEmpty() ? "Ch\u01b0a c\u00f3" : (cachedInventory.size() + " v\u1eadt ph\u1ea9m")));
        tabs.addTab("Th\u00f4ng tin", wrapDialogTab(infoPanel));
        JPanel content = new JPanel(new BorderLayout(8, 8));
        JPanel summaryPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        summaryPanel.setOpaque(false);
        summaryPanel.add(new JLabel("H\u1ed3 s\u01a1: " + profileLabel));
        summaryPanel.add(new JLabel("Client: " + clientJar.getFileName() + " | D\u1eef li\u1ec7u: " + dataDir.getFileName()));
        summaryPanel.add(new JLabel("Ban va: " + BUILD_LABEL));
        JTextArea note = new JTextArea(
            editingAuto
                ? "Form da thu gon, co thanh cuon. Tu tim bai theo level dung muc +/-5, tu ban do nhat duoc khi day tui."
                : "S\u1eeda th\u00f4ng tin \u0111\u0103ng nh\u1eadp cho h\u1ed3 s\u01a1."
        );
        note.setEditable(false);
        note.setOpaque(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        content.setPreferredSize(new Dimension(mode == ConfigMode.AUTO ? 720 : 760, 560));
        content.add(summaryPanel, BorderLayout.NORTH);
        content.add(tabs, BorderLayout.CENTER);
        content.add(note, BorderLayout.SOUTH);
        Object[] options = new Object[]{"L\u01b0u c\u1ea5u h\u00ecnh", "H\u1ee7y"};
        int result = JOptionPane.showOptionDialog(
            null,
            content,
            APP_NAME + (mode == ConfigMode.LOGIN ? " - \u0110\u0103ng nh\u1eadp" : (mode == ConfigMode.AUTO ? " - Auto" : "")),
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        if (result != 0) {
            return null;
        }
        if (editingAuto || mode == ConfigMode.ALL) {
            config.enabled = enabledBox.isSelected();
        }
        if (editingLogin) {
            config.loginSettingsLocked = loginLockedBox.isSelected();
            config.username = userField.getText().trim();
            config.password = new String(passField.getPassword());
            config.serverIndex = serverBox.getSelectedIndex();
            config.customHost = hostField.getText().trim();
            config.customPort = ((Integer)portSpinner.getValue()).intValue();
            config.customServerName = serverNameField.getText().trim();
            config.characterIndex = characterBox.getSelectedIndex();
            config.reconnectDelayMs = ((Integer)reconnectSpinner.getValue()).intValue() * 1000L;
        }
        if (editingAuto) {
            applyCompactAutoConfig(config, autoAttackBox, autoHealBox, autoPartyBox, autoComeHomeBox, autoTrainByLevelBox, autoVanTieuBox, autoSellFarmGearBox, autoRepairGearBox, reloadNoDamageBox, pickupPotionBox, pickupEquipmentBox, pickupMaterialBox, pickupAllBox, autoUpgradeSkillBox, autoUpgradePotentialBox, autoUseInventoryItemsBox, characterClassBox, targetModeBox, teamModeBox, resourceModeBox, leaderNameField, memberNamesField, attackSkillBoxes, buffSkillBoxes, upgradeSkillBoxes, potentialTargetBox, autoItemBoxes, followDistanceSpinner, hpSpinner, mpSpinner, repairDurabilitySpinner, noDamageSpinner, skillUpgradeIntervalSpinner, potentialAmountSpinner, potentialIntervalSpinner, inventoryItemIntervalSpinner);
        }
        config.normalize();
        return config;
    }

    private static final class EmulatorMain implements Runnable {
        private final ClassLoader classLoader;
        private final String[] args;
        private final Throwable[] errorHolder;

        private EmulatorMain(ClassLoader classLoader, String[] args, Throwable[] errorHolder) {
            this.classLoader = classLoader;
            this.args = args;
            this.errorHolder = errorHolder;
        }

        public void run() {
            try {
                Thread.currentThread().setContextClassLoader(classLoader);
                Class<?> mainClass = Class.forName("org.recompile.freej2me.FreeJ2ME", true, classLoader);
                Method mainMethod = mainClass.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object)args);
            } catch (Throwable error) {
                errorHolder[0] = unwrap(error);
            }
        }
    }

    private static final class TrainSpot {
        private final int cluster;
        private final int minLevel;
        private final int maxLevel;
        private final int targetGroup;
        private final String note;

        private TrainSpot(int cluster, int minLevel, int maxLevel, int targetGroup, String note) {
            this.cluster = cluster;
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.targetGroup = targetGroup;
            this.note = note;
        }

        private boolean containsLevel(int level) {
            return level >= minLevel && level <= maxLevel;
        }

        private boolean matchesLevelWindow(int level, int tolerance) {
            return level >= minLevel - tolerance && level <= maxLevel + tolerance;
        }

        private int distanceToRange(int level) {
            if (level < minLevel) {
                return minLevel - level;
            }
            if (level > maxLevel) {
                return level - maxLevel;
            }
            return 0;
        }

        private int distanceToCenter(int level) {
            int center = (minLevel + maxLevel) / 2;
            return Math.abs(center - level);
        }

        private int width() {
            return maxLevel - minLevel;
        }
    }

    private static final class TravelEdge {
        private static final int TYPE_GATE = 0;
        private static final int TYPE_XAPHU = 1;
        private final int fromGroup;
        private final int toGroup;
        private final int type;
        private final int[] gateTriples;

        private TravelEdge(int fromGroup, int toGroup, int type, int[] gateTriples) {
            this.fromGroup = fromGroup;
            this.toGroup = toGroup;
            this.type = type;
            this.gateTriples = gateTriples;
        }

        private static TravelEdge gate(int fromGroup, int toGroup, int... gateTriples) {
            return new TravelEdge(fromGroup, toGroup, TYPE_GATE, gateTriples);
        }

        private static TravelEdge xaphu(int fromGroup, int toGroup) {
            return new TravelEdge(fromGroup, toGroup, TYPE_XAPHU, null);
        }
    }

    private static final class TravelDestination {
        private final int mapId;
        private final int x;
        private final int y;

        private TravelDestination(int mapId, int x, int y) {
            this.mapId = mapId;
            this.x = x;
            this.y = y;
        }
    }

    private static final class EscortGateSpec {
        private final int fromGroup;
        private final int toGroup;
        private final int gateX;
        private final int gateY;

        private EscortGateSpec(int fromGroup, int toGroup, int gateTileX, int gateTileY) {
            this.fromGroup = fromGroup;
            this.toGroup = toGroup;
            this.gateX = gateTileX * 16;
            this.gateY = gateTileY * 16;
        }

        private boolean matches(int fromGroup, int toGroup) {
            return this.fromGroup == fromGroup && this.toGroup == toGroup;
        }
    }

    private static final class EscortTravelPlan {
        private final TravelEdge edge;
        private final TravelDestination destination;
        private final EscortGateSpec gate;

        private EscortTravelPlan(TravelEdge edge, TravelDestination destination, EscortGateSpec gate) {
            this.edge = edge;
            this.destination = destination;
            this.gate = gate;
        }
    }

    private static TrainSpot chooseTrainSpot(int level, int currentGroup) {
        int normalizedCurrentGroup = normalizeRouteGroup(currentGroup);
        TrainSpot bestInWindow = null;
        TrainSpot nearest = null;
        for (int i = 0; i < TRAIN_SPOTS.length; i++) {
            TrainSpot spot = TRAIN_SPOTS[i];
            if (spot.matchesLevelWindow(level, 5)) {
                if (isBetterTrainSpotCandidate(spot, bestInWindow, level, normalizedCurrentGroup)) {
                    bestInWindow = spot;
                }
            }
            if (isBetterTrainSpotCandidate(spot, nearest, level, normalizedCurrentGroup)) {
                nearest = spot;
            }
        }
        return bestInWindow != null ? bestInWindow : nearest;
    }

    private static boolean isBetterTrainSpotCandidate(TrainSpot candidate, TrainSpot currentBest, int level, int currentGroup) {
        if (candidate == null) {
            return false;
        }
        if (currentBest == null) {
            return true;
        }
        int candidateRangeDistance = candidate.distanceToRange(level);
        int currentRangeDistance = currentBest.distanceToRange(level);
        if (candidateRangeDistance != currentRangeDistance) {
            return candidateRangeDistance < currentRangeDistance;
        }
        boolean candidateCurrentGroup = normalizeRouteGroup(candidate.targetGroup) == currentGroup;
        boolean currentBestGroup = normalizeRouteGroup(currentBest.targetGroup) == currentGroup;
        if (candidateCurrentGroup != currentBestGroup) {
            return candidateCurrentGroup;
        }
        int candidateCenterDistance = candidate.distanceToCenter(level);
        int currentCenterDistance = currentBest.distanceToCenter(level);
        if (candidateCenterDistance != currentCenterDistance) {
            return candidateCenterDistance < currentCenterDistance;
        }
        if (candidate.width() != currentBest.width()) {
            return candidate.width() < currentBest.width();
        }
        return candidate.minLevel < currentBest.minLevel;
    }

    private static int trainClusterForGroup(int currentGroup) {
        int normalized = normalizeRouteGroup(currentGroup);
        switch (normalized) {
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 26:
            case 113:
            case 114:
            case 118:
            case 206:
                return TRAIN_CLUSTER_HIGH;
            default:
                return TRAIN_CLUSTER_LOW;
        }
    }

    private static int normalizeRouteGroup(int group) {
        if (group == 70 || group == 80) {
            return 0;
        }
        return group;
    }

    private static int findMapGroup(int mapId) {
        for (int i = 0; i < MAP_GROUPS.length; i++) {
            int[] group = MAP_GROUPS[i];
            for (int j = 0; j < group.length; j++) {
                if (group[j] == mapId) {
                    return group[0];
                }
            }
        }
        return mapId;
    }

    private static int findMapSlot(int mapId) {
        for (int i = 0; i < MAP_GROUPS.length; i++) {
            int[] group = MAP_GROUPS[i];
            for (int j = 0; j < group.length; j++) {
                if (group[j] == mapId) {
                    return j;
                }
            }
        }
        return 0;
    }

    private static int resolveMapIdForGroup(int groupId, int slot) {
        int normalizedGroup = normalizeRouteGroup(groupId);
        for (int i = 0; i < MAP_GROUPS.length; i++) {
            int[] group = MAP_GROUPS[i];
            if (normalizeRouteGroup(group[0]) != normalizedGroup) {
                continue;
            }
            int safeSlot = clamp(slot, 0, group.length - 1);
            return group[safeSlot];
        }
        return normalizedGroup;
    }

    private static int xaphuIndexForGroup(int groupId) {
        switch (normalizeRouteGroup(groupId)) {
            case 0:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            case 8:
                return 6;
            case 9:
                return 7;
            case 10:
                return 8;
            default:
                return -1;
        }
    }

    private static boolean isEscortMonsterTemplate(int templateId) {
        if (templateId >= ESCORT_MONSTER_TEMPLATE_MIN && templateId <= ESCORT_MONSTER_TEMPLATE_MAX) {
            return true;
        }
        for (int i = 0; i < ESCORT_MONSTER_TEMPLATE_EXTRA.length; i++) {
            if (ESCORT_MONSTER_TEMPLATE_EXTRA[i] == templateId) {
                return true;
            }
        }
        return false;
    }

    private static TravelDestination resolveTravelDestination(int[] gateTriples, int currentMapId) {
        if (gateTriples == null || gateTriples.length < 3) {
            return null;
        }
        int slot = findMapSlot(currentMapId);
        int tripleCount = gateTriples.length / 3;
        int safeSlot = clamp(slot, 0, tripleCount - 1);
        int offset = safeSlot * 3;
        return new TravelDestination(gateTriples[offset], gateTriples[offset + 1], gateTriples[offset + 2]);
    }

    private static TravelEdge findGateEdge(int fromGroup, int toGroup) {
        int normalizedFrom = normalizeRouteGroup(fromGroup);
        int normalizedTo = normalizeRouteGroup(toGroup);
        for (int i = 0; i < TRAVEL_EDGES.length; i++) {
            TravelEdge edge = TRAVEL_EDGES[i];
            if (edge.type != TravelEdge.TYPE_GATE) {
                continue;
            }
            if (normalizeRouteGroup(edge.fromGroup) == normalizedFrom
                && normalizeRouteGroup(edge.toGroup) == normalizedTo) {
                return edge;
            }
        }
        return null;
    }

    private static boolean isEscortPreferredReceiveMap(int mapId) {
        if (mapId < 0) {
            return false;
        }
        return normalizeRouteGroup(findMapGroup(mapId)) == 0 && findMapSlot(mapId) == 1;
    }

    private static boolean isEscortPreferredRouteMap(int mapId) {
        if (mapId == 118) {
            return true;
        }
        if (mapId < 0 || findMapSlot(mapId) != 1) {
            return false;
        }
        switch (normalizeRouteGroup(findMapGroup(mapId))) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private static TravelEdge findNextEscortEdge(int currentMapId) {
        if (!isEscortPreferredRouteMap(currentMapId) || currentMapId == 118) {
            return null;
        }
        switch (normalizeRouteGroup(findMapGroup(currentMapId))) {
            case 0:
                return findGateEdge(0, 2);
            case 2:
                return findGateEdge(2, 3);
            case 3:
                return findGateEdge(3, 5);
            case 5:
                return findGateEdge(5, 6);
            case 6:
                return findGateEdge(6, 7);
            case 7:
                return findGateEdge(7, 8);
            case 8:
                return findGateEdge(8, 9);
            case 9:
                return findGateEdge(9, 201);
            default:
                return null;
        }
    }

    private static TravelEdge findNextTravelEdge(int currentGroup, int targetGroup) {
        int startGroup = normalizeRouteGroup(currentGroup);
        int desiredGroup = normalizeRouteGroup(targetGroup);
        if (startGroup == desiredGroup) {
            return null;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        HashMap<Integer, Integer> parentGroup = new HashMap<Integer, Integer>();
        HashMap<Integer, TravelEdge> parentEdge = new HashMap<Integer, TravelEdge>();
        queue.add(Integer.valueOf(startGroup));
        parentGroup.put(Integer.valueOf(startGroup), Integer.valueOf(startGroup));

        while (!queue.isEmpty()) {
            int group = queue.removeFirst().intValue();
            if (group == desiredGroup) {
                break;
            }
            for (int i = 0; i < TRAVEL_EDGES.length; i++) {
                TravelEdge edge = TRAVEL_EDGES[i];
                if (normalizeRouteGroup(edge.fromGroup) != group) {
                    continue;
                }
                int nextGroup = normalizeRouteGroup(edge.toGroup);
                Integer key = Integer.valueOf(nextGroup);
                if (parentGroup.containsKey(key)) {
                    continue;
                }
                parentGroup.put(key, Integer.valueOf(group));
                parentEdge.put(key, edge);
                queue.addLast(key);
            }
        }

        Integer cursor = Integer.valueOf(desiredGroup);
        if (!parentEdge.containsKey(cursor)) {
            return null;
        }
        while (cursor != null) {
            TravelEdge result = parentEdge.get(cursor);
            if (result == null) {
                return null;
            }
            Integer parent = parentGroup.get(cursor);
            if (parent == null || parent.intValue() == startGroup) {
                return result;
            }
            cursor = parent;
        }
        return null;
    }

    private static TravelEdge findNextTravelEdgeForMap(int currentMapId, int targetMapId, boolean allowXaphu) {
        if (currentMapId < 0 || targetMapId < 0 || currentMapId == targetMapId) {
            return null;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        HashMap<Integer, Integer> parentMap = new HashMap<Integer, Integer>();
        HashMap<Integer, TravelEdge> parentEdge = new HashMap<Integer, TravelEdge>();
        Integer startKey = Integer.valueOf(currentMapId);
        Integer targetKey = Integer.valueOf(targetMapId);
        queue.add(startKey);
        parentMap.put(startKey, startKey);

        while (!queue.isEmpty()) {
            int mapId = queue.removeFirst().intValue();
            if (mapId == targetMapId) {
                break;
            }
            int currentGroup = normalizeRouteGroup(findMapGroup(mapId));
            int currentSlot = findMapSlot(mapId);
            for (int i = 0; i < TRAVEL_EDGES.length; i++) {
                TravelEdge edge = TRAVEL_EDGES[i];
                if (normalizeRouteGroup(edge.fromGroup) != currentGroup) {
                    continue;
                }
                if (!allowXaphu && edge.type == TravelEdge.TYPE_XAPHU) {
                    continue;
                }
                int nextMapId;
                if (edge.type == TravelEdge.TYPE_XAPHU) {
                    nextMapId = resolveMapIdForGroup(edge.toGroup, currentSlot);
                } else {
                    TravelDestination destination = resolveTravelDestination(edge.gateTriples, mapId);
                    if (destination == null) {
                        continue;
                    }
                    nextMapId = destination.mapId;
                }
                if (nextMapId <= 0 || nextMapId == mapId) {
                    continue;
                }
                Integer nextKey = Integer.valueOf(nextMapId);
                if (parentMap.containsKey(nextKey)) {
                    continue;
                }
                parentMap.put(nextKey, Integer.valueOf(mapId));
                parentEdge.put(nextKey, edge);
                queue.addLast(nextKey);
            }
        }

        if (!parentEdge.containsKey(targetKey)) {
            return null;
        }
        Integer cursor = targetKey;
        while (cursor != null) {
            TravelEdge result = parentEdge.get(cursor);
            if (result == null) {
                return null;
            }
            Integer parent = parentMap.get(cursor);
            if (parent == null || parent.intValue() == currentMapId) {
                return result;
            }
            cursor = parent;
        }
        return null;
    }

    private static EscortTravelPlan resolveEscortTravelPlan(int currentMapId, TravelEdge edge) {
        if (edge == null || edge.type != TravelEdge.TYPE_GATE) {
            return null;
        }
        TravelDestination destination = resolveTravelDestination(edge.gateTriples, currentMapId);
        EscortGateSpec gate = resolveEscortGateSpec(currentMapId, destination);
        if (destination == null || gate == null) {
            return null;
        }
        return new EscortTravelPlan(edge, destination, gate);
    }

    private static EscortGateSpec resolveEscortGateSpec(int currentMapId, TravelDestination destination) {
        if (currentMapId < 0 || destination == null || destination.mapId < 0) {
            return null;
        }
        int fromGroup = findMapGroup(currentMapId);
        int toGroup = findMapGroup(destination.mapId);
        for (int i = 0; i < ESCORT_GATE_SPECS.length; i++) {
            EscortGateSpec spec = ESCORT_GATE_SPECS[i];
            if (spec.matches(fromGroup, toGroup)) {
                return spec;
            }
        }
        return null;
    }

    private static int escortAdvanceStepDistance(int escortDistance) {
        if (escortDistance >= 88) {
            return ESCORT_ROUTE_STEP_DISTANCE_LAGGING;
        }
        return ESCORT_ROUTE_STEP_DISTANCE;
    }

    private static final class AutoController implements Runnable {
        private final ClassLoader freej2meLoader;
        private final AutoConfig config;
        private final Path configPath;
        private final Path statusFile;
        private final Path commandFile;
        private final Path appDir;
        private final Path inventoryCachePath;
        private ReflectionContext ctx;
        private boolean wasInGame;
        private long lastLoginAttemptAt;
        private long lastCharSelectAttemptAt;
        private long lastRecoverAttemptAt;
        private long lastAutoApplyAt;
        private long lastSkillApplyAt;
        private long midletReadyAt;
        private long nextLoginUiStepAt;
        private long loginSubmitPendingUntil;
        private int runtimeAutoMode = Integer.MIN_VALUE;
        private int loginAttempts;
        private String lastScreen = "starting";
        private String commandNote = "";
        private boolean useBundledServerFallback;
        private PendingCommandSequence pendingSequence;
        private long nextRouteActionAt;
        private long escortReceivePendingUntil;
        private long lastEscortSeenAt;
        private int escortBaselineMonsterId = Integer.MIN_VALUE;
        private int lastEscortMonsterId = Integer.MIN_VALUE;
        private long escortTransferRequestedAt;
        private int escortTransferTargetMapId = Integer.MIN_VALUE;
        private long lastNoDamageAt;
        private long lastNoDamageReloadAt;
        private int lastNoDamageTargetId = Integer.MIN_VALUE;
        private int lastNoDamageTargetHp = Integer.MIN_VALUE;
        private int lastNoDamageMapId = Integer.MIN_VALUE;
        private long nextSkillUpgradeAt;
        private long nextPotentialUpgradeAt;
        private long nextInventoryUseAt;
        private long nextInventoryCacheAt;
        private long disconnectedSinceAt;
        private long sellBurstUntilAt;
        private long lastStatusWriteAt;
        private String lastStatusState = "";
        private String lastStatusScreen = "";
        private String lastStatusNote = "";
        private boolean lastStatusConnected;
        private boolean lastStatusInGame;
        private long nextLicenseCheckAt;

        private AutoController(
            ClassLoader freej2meLoader,
            AutoConfig config,
            Path configPath,
            Path statusFile,
            Path commandFile,
            Path appDir
        ) {
            this.freej2meLoader = freej2meLoader;
            this.config = config;
            this.configPath = configPath;
            this.statusFile = statusFile;
            this.commandFile = commandFile;
            this.appDir = appDir;
            this.inventoryCachePath = configPath == null || configPath.getParent() == null ? null : configPath.getParent().resolve(INVENTORY_CACHE_FILE);
        }

        public void run() {
            while (true) {
                try {
                    enforceLicenseHeartbeat();
                    handleExternalCommand();
                    if (!config.enabled) {
                        writeStatus("disabled", "disabled", false, false, "launcher_disabled");
                        sleep(2000L);
                        continue;
                    }
                    if (ctx == null && !tryInit()) {
                        writeStatus("booting", "loading", false, false, "waiting_for_midlet");
                        sleep(1000L);
                        continue;
                    }
                    tick();
                    sleep(config.pollIntervalMs);
                } catch (Throwable error) {
                    error.printStackTrace();
                    writeStatus("error", lastScreen, false, wasInGame, error.toString());
                    sleep(2000L);
                }
            }
        }

        private void enforceLicenseHeartbeat() {
            long now = System.currentTimeMillis();
            if (now < nextLicenseCheckAt) {
                return;
            }
            try {
                KpahLicenseSupport.StoredLicenseStatus status = KpahLicenseSupport.ensureStoredLicense(appDir, true);
                long untilExpiry = status.expiresAtMillis <= 0L ? 15000L : (status.expiresAtMillis - now) + 1000L;
                nextLicenseCheckAt = now + Math.max(1000L, Math.min(15000L, untilExpiry));
            } catch (Throwable error) {
                String message = error.getMessage();
                if (message == null || message.trim().length() == 0) {
                    message = "license_invalid";
                }
                writeStatus("license_expired", lastScreen, false, wasInGame, message);
                System.err.println("[license] " + message);
                sleep(500L);
                System.exit(0);
            }
        }

        private boolean tryInit() throws Exception {
            Class<?> mobileClass = Class.forName("org.recompile.mobile.Mobile", false, freej2meLoader);
            Field midletField = mobileClass.getField("midlet");
            Object midlet = midletField.get(null);
            if (midlet == null) {
                midletReadyAt = 0L;
                return false;
            }

            Method getPlatform = mobileClass.getMethod("getPlatform");
            Object platform = getPlatform.invoke(null);
            if (platform == null) {
                return false;
            }

            Method getDisplay = mobileClass.getMethod("getDisplay");
            Object display = getDisplay.invoke(null);
            long now = System.currentTimeMillis();
            if (display == null) {
                if (midletReadyAt == 0L) {
                    midletReadyAt = now;
                }
                return false;
            }
            if (midletReadyAt == 0L) {
                midletReadyAt = now;
            }
            if (now - midletReadyAt < 2500L) {
                return false;
            }

            Field loaderField = platform.getClass().getField("loader");
            Object loaderObject = loaderField.get(platform);
            if (!(loaderObject instanceof ClassLoader)) {
                return false;
            }

            try {
                ctx = new ReflectionContext((ClassLoader)loaderObject);
                return true;
            } catch (LinkageError error) {
                return false;
            }
        }

        private void tick() throws Exception {
            long now = System.currentTimeMillis();
            handlePendingSequence(now);

            int effectiveAutoMode = effectiveAutoMode();
            ctx.applyConfigState(config, effectiveAutoMode, useBundledServerFallback);

            Object currentScreen = ctx.currentScreen.get(null);
            Object gameScreen = ctx.gameScreen.get(null);
            Object loginScreen = ctx.loginScreen.get(null);
            Object serverScreen = ctx.serverScreen();
            Object charScreen = ctx.charScreen();
            boolean connected = ctx.connected();
            String screenName = describeScreen(currentScreen, gameScreen, loginScreen, serverScreen, charScreen);
            lastScreen = screenName;
            boolean gameActive = same(currentScreen, gameScreen);
            updateDisconnectState(connected, gameActive, now);
            boolean stableConnected = effectiveConnected(connected, gameActive, now);

            if (gameActive) {
                loginAttempts = 0;
                nextLoginUiStepAt = 0L;
                loginSubmitPendingUntil = 0L;
                if (!stableConnected && wasInGame && now - lastRecoverAttemptAt >= disconnectGraceMs()) {
                    ctx.resetToServerList();
                    resetEscortState();
                    lastLoginAttemptAt = now;
                    nextLoginUiStepAt = now + 1200L;
                    lastRecoverAttemptAt = now;
                    writeStatus("recovering", screenName, stableConnected, true, "reset_to_server_list");
                    return;
                }
                wasInGame = true;
                ctx.ensureAutoMode(config, effectiveAutoMode, now, lastAutoApplyAt);
                lastAutoApplyAt = now;
                ctx.ensureSkillConfig(config, now, lastSkillApplyAt);
                lastSkillApplyAt = now;
                boolean automationActive = effectiveAutoMode != 3;
                if (!automationActive || !config.shouldAutoVanTieu()) {
                    resetEscortState();
                }
                if (automationActive) {
                    ctx.handleTeamAutomation(config, now);
                }
                if (inventoryCachePath != null && now >= nextInventoryCacheAt) {
                    try {
                        ctx.refreshInventoryCache(inventoryCachePath);
                        nextInventoryCacheAt = now + 5000L;
                    } catch (Exception ignored) {
                        nextInventoryCacheAt = now + 15000L;
                    }
                }
                String watchdogNote = handleNoDamageWatchdog(now);
                if (watchdogNote.length() > 0) {
                    commandNote = watchdogNote;
                    writeStatus("recovering", screenName, stableConnected, true, watchdogNote);
                    return;
                }
                String escortNote = automationActive ? handleAutoVanTieu(now) : "";
                if (escortNote.length() > 0) {
                    commandNote = escortNote;
                    writeStatus("routing", screenName, stableConnected, true, escortNote);
                    return;
                }
                String routeNote = automationActive ? handleAutoTrainByLevel(now) : "";
                if (routeNote.length() > 0) {
                    commandNote = routeNote;
                    writeStatus("routing", screenName, stableConnected, true, routeNote);
                    return;
                }
                String runningNote = currentNote();
                if (automationActive && now >= nextSkillUpgradeAt) {
                    runningNote = appendStatusNote(runningNote, ctx.tryAutoUpgradeSkill(config));
                    nextSkillUpgradeAt = now + (long)config.skillUpgradeIntervalSeconds * 1000L;
                }
                if (automationActive && now >= nextPotentialUpgradeAt) {
                    runningNote = appendStatusNote(runningNote, ctx.tryAutoUpgradePotential(config));
                    nextPotentialUpgradeAt = now + (long)config.potentialIntervalSeconds * 1000L;
                }
                if (automationActive && now >= nextInventoryUseAt) {
                    runningNote = appendStatusNote(runningNote, ctx.tryAutoUseInventoryItems(config));
                    nextInventoryUseAt = now + (long)config.inventoryItemIntervalSeconds * 1000L;
                }
                String maintenanceNote = automationActive ? ctx.handleMaintenance(config, now) : "";
                if (maintenanceNote.length() > 0) {
                    commandNote = maintenanceNote;
                    runningNote = appendStatusNote(runningNote, maintenanceNote);
                }
                writeStatus("running", screenName, stableConnected, true, runningNote);
                return;
            }

            if (connected) {
                resetEscortState();
                resetNoDamageWatchdog();
                loginAttempts = 0;
                nextLoginUiStepAt = 0L;
                loginSubmitPendingUntil = 0L;
                if (now - lastCharSelectAttemptAt >= 1500L && ctx.selectCharacterIfReady(config.characterIndex)) {
                    lastCharSelectAttemptAt = now;
                    writeStatus("selecting_character", screenName, true, false, appendStatusNote(currentNote(), "force_char_select"));
                    return;
                }
                writeStatus("waiting", screenName, true, false, appendStatusNote(currentNote(), "connected_wait"));
                return;
            }

            if (same(currentScreen, charScreen)) {
                resetEscortState();
                resetNoDamageWatchdog();
                loginAttempts = 0;
                nextLoginUiStepAt = 0L;
                loginSubmitPendingUntil = 0L;
                if (now - lastCharSelectAttemptAt >= 1500L) {
                    ctx.selectCharacter(config.characterIndex);
                    lastCharSelectAttemptAt = now;
                }
                writeStatus("selecting_character", screenName, stableConnected, false, currentNote());
                return;
            }

            if (!stableConnected && same(currentScreen, serverScreen)) {
                resetEscortState();
                resetNoDamageWatchdog();
                long loginAttemptWindowMs = Math.max(config.reconnectDelayMs, 8000L);
                if (loginSubmitPendingUntil > now) {
                    writeStatus("logging_in", screenName, stableConnected, false, appendStatusNote(currentNote(), "wait_submit"));
                    return;
                }
                if (loginAttempts > 0 && now - lastLoginAttemptAt >= loginAttemptWindowMs) {
                    ctx.resetToServerList();
                    loginAttempts = 0;
                    lastLoginAttemptAt = now;
                    loginSubmitPendingUntil = 0L;
                    nextLoginUiStepAt = now + 1200L;
                    lastRecoverAttemptAt = now;
                    writeStatus("recovering", screenName, stableConnected, false, "reset_to_server_list");
                    return;
                }
                if (now >= nextLoginUiStepAt && now - lastLoginAttemptAt >= config.reconnectDelayMs) {
                    ctx.submitLogin(config, effectiveAutoMode, useBundledServerFallback);
                    lastLoginAttemptAt = now;
                    loginSubmitPendingUntil = now + loginAttemptWindowMs;
                    loginAttempts++;
                    writeStatus("logging_in", screenName, stableConnected, false, appendStatusNote(currentNote(), "submit_from_server_list"));
                    return;
                }
                if (now >= nextLoginUiStepAt) {
                    ctx.prepareLogin(config, effectiveAutoMode, useBundledServerFallback);
                    nextLoginUiStepAt = now + 1200L;
                }
                writeStatus("logging_in", screenName, stableConnected, false, appendStatusNote(currentNote(), "open_login_screen"));
                return;
            }

            if (!stableConnected && same(currentScreen, loginScreen)) {
                resetEscortState();
                resetNoDamageWatchdog();
                long loginAttemptWindowMs = Math.max(config.reconnectDelayMs, 8000L);
                if (loginSubmitPendingUntil > now) {
                    writeStatus("logging_in", screenName, stableConnected, false, currentNote());
                    return;
                }
                if (loginAttempts > 0 && now - lastLoginAttemptAt >= loginAttemptWindowMs) {
                    ctx.resetToServerList();
                    loginAttempts = 0;
                    lastLoginAttemptAt = now;
                    loginSubmitPendingUntil = 0L;
                    nextLoginUiStepAt = now + 1200L;
                    lastRecoverAttemptAt = now;
                    writeStatus("recovering", screenName, stableConnected, false, "reset_to_server_list");
                    return;
                }
                if (now >= nextLoginUiStepAt && now - lastLoginAttemptAt >= config.reconnectDelayMs) {
                    ctx.submitLogin(config, effectiveAutoMode, useBundledServerFallback);
                    lastLoginAttemptAt = now;
                    loginSubmitPendingUntil = now + loginAttemptWindowMs;
                    loginAttempts++;
                    writeStatus("logging_in", screenName, stableConnected, false, currentNote());
                    return;
                }
                writeStatus("waiting", screenName, stableConnected, false, currentNote());
                return;
            }

            if (!stableConnected && wasInGame && now - lastRecoverAttemptAt >= disconnectGraceMs()) {
                resetNoDamageWatchdog();
                ctx.resetToServerList();
                resetEscortState();
                loginAttempts = 0;
                lastLoginAttemptAt = now;
                nextLoginUiStepAt = now + 1200L;
                loginSubmitPendingUntil = 0L;
                lastRecoverAttemptAt = now;
                writeStatus("recovering", screenName, stableConnected, false, "reset_to_server_list");
                return;
            }

            resetNoDamageWatchdog();
            writeStatus("waiting", screenName, stableConnected, wasInGame && stableConnected, currentNote());
        }

        private void updateDisconnectState(boolean connected, boolean gameActive, long now) {
            if (connected) {
                disconnectedSinceAt = 0L;
                return;
            }
            if (gameActive || wasInGame) {
                if (disconnectedSinceAt == 0L) {
                    disconnectedSinceAt = now;
                }
                return;
            }
            disconnectedSinceAt = 0L;
        }

        private long disconnectGraceMs() {
            return Math.max(config.reconnectDelayMs, 15000L);
        }

        private boolean effectiveConnected(boolean connected, boolean gameActive, long now) {
            if (connected) {
                return true;
            }
            if (!(gameActive || wasInGame)) {
                return false;
            }
            return disconnectedSinceAt == 0L || now - disconnectedSinceAt < disconnectGraceMs();
        }

        private String handleAutoTrainByLevel(long now) throws Exception {
            if (!config.shouldAutoTravelByLevel() || now < nextRouteActionAt || ctx == null) {
                return "";
            }
            int currentMapId = ctx.currentMapId();
            int currentGroup = normalizeRouteGroup(findMapGroup(currentMapId));
            int level = ctx.mainCharLevel();
            if (level <= 0 || currentMapId < 0) {
                return "";
            }

            TrainSpot targetSpot = chooseTrainSpot(level, currentGroup);
            if (targetSpot == null) {
                return "";
            }
            if (currentGroup == normalizeRouteGroup(targetSpot.targetGroup)) {
                if (commandNote.startsWith("route_")) {
                    commandNote = "";
                }
                return "";
            }
            if (ctx.hasCloseMonsterTarget(144)) {
                nextRouteActionAt = now + 2000L;
                return "";
            }
            if (currentGroup == 118 || currentGroup == 206) {
                if (ctx.sendGameCommand("return_home")) {
                    nextRouteActionAt = now + 7000L;
                    return "route_return_home";
                }
                nextRouteActionAt = now + 3000L;
                return "";
            }

            TravelEdge edge = findNextTravelEdge(currentGroup, targetSpot.targetGroup);
            if (edge == null) {
                if (currentGroup != 0 && currentGroup != 1 && currentGroup != 2 && ctx.sendGameCommand("return_home")) {
                    nextRouteActionAt = now + 7000L;
                    return "route_return_home";
                }
                nextRouteActionAt = now + 4000L;
                return "";
            }
            if (!ctx.travel(edge, currentMapId)) {
                nextRouteActionAt = now + 3000L;
                return "";
            }
            nextRouteActionAt = now + 7000L;
            return "route_" + targetSpot.note;
        }

        private String handleAutoVanTieu(long now) throws Exception {
            if (!config.shouldAutoVanTieu() || now < nextRouteActionAt || ctx == null) {
                return "";
            }
            int currentMapId = ctx.currentMapId();
            int currentGroup = normalizeRouteGroup(findMapGroup(currentMapId));
            if (currentMapId < 0) {
                return "";
            }

            boolean inHomeBase = currentGroup == 0 || currentGroup == 1 || currentGroup == 2;
            int escortDetectDistance = inHomeBase ? ESCORT_DETECT_DISTANCE_HOME : ESCORT_DETECT_DISTANCE_ROUTE;
            if (currentGroup == 118) {
                escortDetectDistance = ESCORT_DETECT_DISTANCE_RETURN;
            }
            int escortMonsterId = ctx.currentNearbyEscortMonsterId(escortDetectDistance);
            if (escortMonsterId >= 0) {
                if (lastEscortMonsterId == Integer.MIN_VALUE) {
                    if (!inHomeBase || escortReceivePendingUntil > now || escortMonsterId != escortBaselineMonsterId) {
                        lastEscortMonsterId = escortMonsterId;
                        lastEscortSeenAt = now;
                        escortReceivePendingUntil = 0L;
                        escortBaselineMonsterId = Integer.MIN_VALUE;
                    }
                } else if (escortMonsterId == lastEscortMonsterId || !inHomeBase) {
                    lastEscortMonsterId = escortMonsterId;
                    lastEscortSeenAt = now;
                    escortReceivePendingUntil = 0L;
                } else if (escortReceivePendingUntil > now && escortMonsterId != escortBaselineMonsterId) {
                    lastEscortMonsterId = escortMonsterId;
                    lastEscortSeenAt = now;
                    escortReceivePendingUntil = 0L;
                    escortBaselineMonsterId = Integer.MIN_VALUE;
                }
            } else if (escortReceivePendingUntil > 0L && now >= escortReceivePendingUntil) {
                escortReceivePendingUntil = 0L;
                escortBaselineMonsterId = Integer.MIN_VALUE;
            }

            if (escortTransferTargetMapId >= 0) {
                if (currentMapId != escortTransferTargetMapId) {
                    if (now - escortTransferRequestedAt > ESCORT_TRANSFER_TIMEOUT_MS) {
                        escortTransferTargetMapId = Integer.MIN_VALUE;
                        escortTransferRequestedAt = 0L;
                        lastEscortSeenAt = 0L;
                        lastEscortMonsterId = Integer.MIN_VALUE;
                        nextRouteActionAt = now + 1200L;
                        return "vantieu_transfer_timeout";
                    }
                    nextRouteActionAt = now + 900L;
                    return "vantieu_wait_map_change";
                }
                if (escortMonsterId >= 0) {
                    escortTransferTargetMapId = Integer.MIN_VALUE;
                    escortTransferRequestedAt = 0L;
                    lastEscortSeenAt = now;
                    nextRouteActionAt = now + ESCORT_POST_TRANSFER_SETTLE_MS;
                    return "vantieu_transfer_synced";
                }
                if (now - escortTransferRequestedAt <= ESCORT_TRANSFER_TIMEOUT_MS) {
                    nextRouteActionAt = now + 900L;
                    return "vantieu_wait_escort_spawn";
                }
                escortTransferTargetMapId = Integer.MIN_VALUE;
                escortTransferRequestedAt = 0L;
                lastEscortSeenAt = 0L;
                lastEscortMonsterId = Integer.MIN_VALUE;
                nextRouteActionAt = now + 1200L;
                return "vantieu_wait_escort_lost";
            }

            boolean escortActive = lastEscortSeenAt > 0L && now - lastEscortSeenAt < 12000L;
            int escortDistance = Integer.MAX_VALUE;
            if (escortActive) {
                escortDistance = ctx.currentNearbyEscortDistance(escortDetectDistance);
                if (escortDistance > ESCORT_TRANSFER_SAFE_DISTANCE) {
                    if (escortDistance < Integer.MAX_VALUE) {
                        boolean escortCloseEnough = ctx.moveMainCharNearEscort(escortDetectDistance, ESCORT_REJOIN_STOP_DISTANCE);
                        nextRouteActionAt = now + 900L;
                        return escortCloseEnough ? "vantieu_wait_escort" : "vantieu_rejoin_escort";
                    }
                    nextRouteActionAt = now + 1200L;
                    return "vantieu_wait_escort_visibility";
                }
            }
            if (escortActive && currentGroup == 118) {
                if (ctx.moveMainCharToward(ESCORT_RETURN_X, ESCORT_RETURN_Y, escortAdvanceStepDistance(escortDistance), 72)) {
                    if (ctx.selectNpcMenu(ESCORT_RETURN_NPC_ID, 0, 0)) {
                        nextRouteActionAt = now + 2500L;
                        return "vantieu_return_request";
                    }
                    nextRouteActionAt = now + 1200L;
                    return "vantieu_return_retry";
                }
                nextRouteActionAt = now + 1200L;
                return "vantieu_move_return";
            }

            if (escortActive) {
                if (!isEscortPreferredRouteMap(currentMapId)) {
                    nextRouteActionAt = now + 2500L;
                    return "vantieu_wrong_start_slot";
                }
                if (ctx.hasCloseMonsterTarget(144)) {
                    nextRouteActionAt = now + 2000L;
                    return "vantieu_hold_combat";
                }
                TravelEdge edge = findNextEscortEdge(currentMapId);
                if (edge == null) {
                    nextRouteActionAt = now + 4000L;
                    return "vantieu_safe_route_missing";
                }
                EscortTravelPlan plan = resolveEscortTravelPlan(currentMapId, edge);
                if (plan == null) {
                    nextRouteActionAt = now + 3000L;
                    return "vantieu_gate_missing";
                }
                int gateDistance = ctx.distanceMainCharTo(plan.gate.gateX, plan.gate.gateY);
                if (gateDistance > ESCORT_GATE_READY_DISTANCE) {
                    boolean gateReady = ctx.moveMainCharToward(
                        plan.gate.gateX,
                        plan.gate.gateY,
                        escortAdvanceStepDistance(escortDistance),
                        ESCORT_GATE_READY_DISTANCE
                    );
                    nextRouteActionAt = now + 700L;
                    return gateReady ? "vantieu_gate_ready" : "vantieu_gate_step";
                }
                if (escortDistance > ESCORT_GATE_TRANSFER_DISTANCE) {
                    nextRouteActionAt = now + 700L;
                    return "vantieu_wait_gate_sync";
                }
                if (!ctx.travel(plan.edge, currentMapId)) {
                    nextRouteActionAt = now + 1500L;
                    return "vantieu_route_retry";
                }
                escortTransferTargetMapId = plan.destination.mapId;
                escortTransferRequestedAt = now;
                nextRouteActionAt = now + 1800L;
                return "vantieu_gate_transfer";
            }

            if (!isEscortPreferredReceiveMap(currentMapId)) {
                if (ctx.sendGameCommand("return_home")) {
                    nextRouteActionAt = now + 7000L;
                    return "vantieu_seek_start_map";
                }
                nextRouteActionAt = now + 3000L;
                return "vantieu_seek_start_map_retry";
            }

            if (escortReceivePendingUntil > now) {
                nextRouteActionAt = now + 1000L;
                return "vantieu_wait_receive";
            }

            escortBaselineMonsterId = escortMonsterId;
            lastEscortMonsterId = Integer.MIN_VALUE;
            lastEscortSeenAt = 0L;
            if (ctx.selectNpcMenu(ESCORT_RECEIVE_NPC_ID, 0, 0)) {
                escortReceivePendingUntil = now + 6000L;
                nextRouteActionAt = now + 2000L;
                return "vantieu_receive_request";
            }
            nextRouteActionAt = now + 2000L;
            return "vantieu_receive_retry";
        }

        private String handleNoDamageWatchdog(long now) throws Exception {
            if (!config.reloadWhenNoDamage || ctx == null) {
                resetNoDamageWatchdog();
                return "";
            }
            if (!ctx.isAutoFightEnabled()) {
                resetNoDamageWatchdog();
                return "";
            }
            int currentMapId = ctx.currentMapId();
            Object target = ctx.currentMonsterTarget();
            if (target == null || ctx.distanceToMainChar(target) > 160) {
                resetNoDamageWatchdog();
                return "";
            }

            int targetId = ctx.actorId(target);
            int targetHp = ctx.actorHp(target);
            if (targetId < 0 || targetHp <= 0) {
                resetNoDamageWatchdog();
                return "";
            }
            if (currentMapId != lastNoDamageMapId || targetId != lastNoDamageTargetId) {
                lastNoDamageMapId = currentMapId;
                lastNoDamageTargetId = targetId;
                lastNoDamageTargetHp = targetHp;
                lastNoDamageAt = now;
                return "";
            }
            if (targetHp != lastNoDamageTargetHp) {
                lastNoDamageTargetHp = targetHp;
                lastNoDamageAt = now;
                return "";
            }
            long timeoutMs = config.noDamageTimeoutMs();
            if (lastNoDamageAt == 0L || now - lastNoDamageAt < timeoutMs) {
                return "";
            }
            long minRecoverGap = Math.max(timeoutMs * 2L, config.reconnectDelayMs);
            if (now - lastNoDamageReloadAt < minRecoverGap) {
                return "";
            }
            loginAttempts = 0;
            useBundledServerFallback = false;
            lastLoginAttemptAt = now;
            nextLoginUiStepAt = now + 1200L;
            loginSubmitPendingUntil = 0L;
            lastNoDamageReloadAt = now;
            lastRecoverAttemptAt = now;
            ctx.resetToServerList();
            resetEscortState();
            resetNoDamageWatchdog();
            return "reload_no_damage";
        }

        private void resetEscortState() {
            escortReceivePendingUntil = 0L;
            lastEscortSeenAt = 0L;
            escortBaselineMonsterId = Integer.MIN_VALUE;
            lastEscortMonsterId = Integer.MIN_VALUE;
            escortTransferRequestedAt = 0L;
            escortTransferTargetMapId = Integer.MIN_VALUE;
            if (commandNote != null && commandNote.startsWith("vantieu_")) {
                commandNote = "";
            }
        }

        private void resetNoDamageWatchdog() {
            lastNoDamageAt = 0L;
            lastNoDamageTargetId = Integer.MIN_VALUE;
            lastNoDamageTargetHp = Integer.MIN_VALUE;
            lastNoDamageMapId = Integer.MIN_VALUE;
        }

        private void handleExternalCommand() throws Exception {
            String command = pollCommand();
            if (command.length() == 0) {
                return;
            }
            if ("reload_config".equalsIgnoreCase(command) || "reload_auto_config".equalsIgnoreCase(command)) {
                reloadConfigFromDisk();
                return;
            }
            if ("exit".equalsIgnoreCase(command) || "stop".equalsIgnoreCase(command)) {
                System.exit(0);
            }
            if ("auto_on".equalsIgnoreCase(command) || "resume_auto".equalsIgnoreCase(command)) {
                boolean launcherWasDisabled = !config.enabled;
                config.enabled = true;
                runtimeAutoMode = config.autoMode;
                lastAutoApplyAt = 0L;
                lastSkillApplyAt = 0L;
                nextRouteActionAt = 0L;
                if (launcherWasDisabled) {
                    persistConfigQuietly();
                }
                commandNote = launcherWasDisabled ? "launcher_enabled" : "auto_resumed";
                if (ctx != null) {
                    int autoMode = effectiveAutoMode();
                    ctx.applyConfigState(config, autoMode, useBundledServerFallback);
                    ctx.setAutoModeNow(config, autoMode);
                    ctx.ensureSkillConfig(config, System.currentTimeMillis(), 0L);
                }
                return;
            }
            if ("auto_off".equalsIgnoreCase(command) || "pause_auto".equalsIgnoreCase(command)) {
                runtimeAutoMode = 3;
                resetEscortState();
                commandNote = "auto_paused";
                if (ctx != null) {
                    ctx.setAutoModeNow(config, 3);
                }
                return;
            }
            if (ctx == null) {
                return;
            }
            if ("return_home".equalsIgnoreCase(command)) {
                if (ctx.sendGameCommand("return_home")) {
                    runtimeAutoMode = 3;
                    nextRouteActionAt = System.currentTimeMillis() + 8000L;
                    resetEscortState();
                    resetNoDamageWatchdog();
                    ctx.setAutoModeNow(config, 3);
                    commandNote = "return_home_auto_paused";
                } else {
                    commandNote = "return_home_not_available";
                }
                return;
            }
            if ("repair".equalsIgnoreCase(command)) {
                commandNote = ctx.repairAllGear(true, config) ? "repair_sent" : "repair_not_ready";
                return;
            }
            if ("sell_items".equalsIgnoreCase(command) || "sell_gear".equalsIgnoreCase(command)) {
                int soldCount = ctx.sellFarmedGear(true, config);
                commandNote = soldCount > 0 ? "sell_sent_" + soldCount : "sell_no_farmed_gear";
                return;
            }
            if ("repair_cycle".equalsIgnoreCase(command)) {
                int soldCount = ctx.sellFarmedGear(true, config);
                boolean repaired = ctx.repairAllGear(true, config);
                commandNote = repaired || soldCount > 0 ? "repair_cycle_" + (repaired ? "repair" : "skip") + "_" + soldCount : "repair_cycle_idle";
                return;
            }
            if ("restart_session".equalsIgnoreCase(command) || "reconnect".equalsIgnoreCase(command)) {
                loginAttempts = 0;
                useBundledServerFallback = false;
                resetEscortState();
                lastLoginAttemptAt = System.currentTimeMillis();
                nextLoginUiStepAt = lastLoginAttemptAt + 1200L;
                loginSubmitPendingUntil = 0L;
                ctx.resetToServerList();
                commandNote = "reconnect_requested";
                return;
            }
        }

        private void reloadConfigFromDisk() throws Exception {
            if (configPath == null || !Files.exists(configPath)) {
                commandNote = "config_missing";
                return;
            }
            AutoConfig refreshed = AutoConfig.load(configPath);
            config.copyFrom(refreshed);
            runtimeAutoMode = config.autoMode;
            lastAutoApplyAt = 0L;
            lastSkillApplyAt = 0L;
            nextRouteActionAt = 0L;
            resetEscortState();
            resetNoDamageWatchdog();
            if (ctx != null) {
                int autoMode = effectiveAutoMode();
                ctx.applyConfigState(config, autoMode, useBundledServerFallback);
                ctx.setAutoModeNow(config, autoMode);
                ctx.ensureSkillConfig(config, System.currentTimeMillis(), 0L);
            }
            commandNote = "config_reloaded";
        }

        private void persistConfigQuietly() {
            if (configPath == null) {
                return;
            }
            try {
                config.store(configPath);
            } catch (IOException ignored) {
            }
        }

        private void handlePendingSequence(long now) throws Exception {
            if (pendingSequence == null || now < pendingSequence.executeAt) {
                return;
            }
            if ("repair_cycle".equals(pendingSequence.name)) {
                int soldCount = ctx == null ? 0 : ctx.sellFarmedGear(true, config);
                boolean repaired = ctx != null && ctx.repairAllGear(true, config);
                commandNote = repaired || soldCount > 0 ? "repair_cycle_" + (repaired ? "repair" : "skip") + "_" + soldCount : "repair_cycle_idle";
                runtimeAutoMode = config.autoMode;
                ctx.setAutoModeNow(config, effectiveAutoMode());
                pendingSequence = null;
            }
        }

        private String pollCommand() {
            if (commandFile == null || !Files.isRegularFile(commandFile)) {
                return "";
            }
            Properties properties = new Properties();
            InputStream input = null;
            try {
                input = Files.newInputStream(commandFile);
                properties.load(input);
            } catch (Exception ignored) {
                return "";
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ignored) {
                    }
                }
                deleteFileQuietly(commandFile);
            }
            return properties.getProperty("command", "").trim();
        }

        private int effectiveAutoMode() {
            return runtimeAutoMode == Integer.MIN_VALUE ? config.autoMode : runtimeAutoMode;
        }

        private String currentNote() {
            if (pendingSequence != null) {
                return pendingSequence.name + ":" + pendingSequence.step;
            }
            return commandNote == null ? "" : commandNote;
        }

        private String appendStatusNote(String base, String extra) {
            String safeBase = base == null ? "" : base.trim();
            String safeExtra = extra == null ? "" : extra.trim();
            if (safeExtra.length() == 0) {
                return safeBase;
            }
            if (safeBase.length() == 0) {
                return safeExtra;
            }
            if (safeBase.equals(safeExtra)) {
                return safeBase;
            }
            return safeBase + " | " + safeExtra;
        }

        private void writeStatus(String state, String screen, boolean connected, boolean inGame, String note) {
            if (statusFile == null) {
                return;
            }
            String safeState = state == null ? "" : state;
            String safeScreen = screen == null ? "" : screen;
            String safeNote = note == null ? "" : note;
            long now = System.currentTimeMillis();
            if (safeState.equals(lastStatusState)
                && safeScreen.equals(lastStatusScreen)
                && safeNote.equals(lastStatusNote)
                && connected == lastStatusConnected
                && inGame == lastStatusInGame
                && now - lastStatusWriteAt < 2000L) {
                return;
            }
            lastStatusWriteAt = now;
            lastStatusState = safeState;
            lastStatusScreen = safeScreen;
            lastStatusNote = safeNote;
            lastStatusConnected = connected;
            lastStatusInGame = inGame;
            Properties properties = new Properties();
            properties.setProperty("state", safeState);
            properties.setProperty("screen", safeScreen);
            properties.setProperty("connected", Boolean.toString(connected));
            properties.setProperty("in_game", Boolean.toString(inGame));
            properties.setProperty("username", config.username);
            properties.setProperty("profile_name", config.profileName.length() > 0 ? config.profileName : config.username);
            properties.setProperty("server_index", Integer.toString(config.serverIndex));
            properties.setProperty("character_index", Integer.toString(config.characterIndex));
            properties.setProperty("requested_auto_mode", Integer.toString(config.autoMode));
            properties.setProperty("effective_auto_mode", Integer.toString(effectiveAutoMode()));
            properties.setProperty("auto_enabled", Boolean.toString(effectiveAutoMode() != 3));
            properties.setProperty("pid", Long.toString(ProcessHandle.current().pid()));
            properties.setProperty("updated_at", Long.toString(now));
            properties.setProperty("note", safeNote);
            try {
                writePropertiesAtomically(statusFile, properties, "KPAH auto status");
            } catch (IOException ignored) {
            }
        }

        private static String describeScreen(Object currentScreen, Object gameScreen, Object loginScreen, Object serverScreen, Object charScreen) {
            if (same(currentScreen, gameScreen)) {
                return "game";
            }
            if (same(currentScreen, loginScreen)) {
                return "login";
            }
            if (same(currentScreen, serverScreen)) {
                return "server_list";
            }
            if (same(currentScreen, charScreen)) {
                return "character_select";
            }
            return currentScreen == null ? "null" : "other";
        }

        private static boolean same(Object left, Object right) {
            return left != null && left == right;
        }

        private static final class PendingCommandSequence {
            private final String name;
            private int step;
            private long executeAt;

            private PendingCommandSequence(String name, int step, long executeAt) {
                this.name = name;
                this.step = step;
                this.executeAt = executeAt;
            }

            private static PendingCommandSequence repairCycle() {
                return new PendingCommandSequence("repair_cycle", 0, System.currentTimeMillis());
            }
        }
    }

    private static final class ReflectionContext {
        private final Class<?> acvClass;
        private final Class<?> yvClass;
        private final Class<?> xwClass;
        private final Class<?> bzClass;
        private final Class<?> juClass;
        private final Class<?> apClass;
        private final Class<?> abjClass;
        private final Class<?> bbClass;
        private final Class<?> zvClass;
        private final Class<?> bqClass;
        private final Class<?> vhClass;
        private final Class<?> hwClass;
        private final Class<?> scClass;
        private final Class<?> gdClass;
        private final Class<?> qlClass;
        private final Class<?> yiClass;
        private final Class<?> ycClass;
        private final Class<?> aaiClass;
        private final Class<?> xzClass;
        private final Class<?> acoClass;
        private final Class<?> goClass;
        private final Class<?> qzClass;
        private final Class<?> wbClass;
        private final Class<?> aaoClass;
        private final Class<?> cgClass;
        private final Field currentScreen;
        private final Field gameScreen;
        private final Field loginScreen;
        private final Field autoScreen;
        private final Field gameCanvas;
        private final Field serverIndexField;
        private final Field serverNamesField;
        private final Field serverHostsField;
        private final Field serverPortsField;
        private final Field targetModeField;
        private final Field autoFightEnabled;
        private final Field autoHealEnabled;
        private final Field hpThreshold;
        private final Field mpThreshold;
        private final Field characterIndexField;
        private final Field characterEntriesField;
        private final Field characterIdField;
        private final Field autoPartyEnabled;
        private final Field autoPickupPotionEnabled;
        private final Field autoPickupEquipmentEnabled;
        private final Field autoPickupMaterialEnabled;
        private final Field autoPickupAllEnabled;
        private final Field autoComeHomeEnabled;
        private final Field autoAttackSkillsField;
        private final Field autoBuffSkillsField;
        private final Field loginUserField;
        private final Field loginPassField;
        private final Field connectedField;
        private final Field gameServiceField;
        private final Field actorListField;
        private final Field mainCharField;
        private final Field currentTargetField;
        private final Field currentMapIdField;
        private final Field actorCategoryField;
        private final Field actorIdField;
        private final Field actorPartyIdField;
        private final Field actorPartyMasterField;
        private final Field actorXField;
        private final Field actorYField;
        private final Field actorHpField;
        private final Field actorNameField;
        private final Field monsterTemplateIdField;
        private final Field escortVisualTemplateIdField;
        private final Field charLevelField;
        private final Field basePointField;
        private final Field skillPointField;
        private final Field movePathField;
        private final Field moveIndexField;
        private final Field pendingInviteActorField;
        private final Field partyMembersField;
        private final Field partyMemberIdField;
        private final Field partyMemberNameField;
        private final Field learnedSkillsField;
        private final Field inventoryItemsField;
        private final Field[] inventoryCandidateFields;
        private final Field inventoryPageCountField;
        private final Field wornItemsField;
        private final Field moneyField;
        private final Field itemIdField;
        private final Field itemTemplateIdField;
        private final Field itemDurabilityField;
        private final Field itemLockField;
        private final Field itemDropFlagField;
        private final Field itemColorField;
        private final Field itemUseDurationField;
        private final Field templateNameField;
        private final Method serverSingleton;
        private final Method charSingleton;
        private final Method acoSingleton;
        private final Method goSingleton;
        private final Method loginMethod;
        private final Method showLoginScreenMethod;
        private final Method setTextMethod;
        private final Method refreshTextMethod;
        private final Method autoModeMethod;
        private final Constructor<?> resetToServerListConstructor;
        private final Method resetToServerListRun;
        private final Constructor<?> selectCharacterConstructor;
        private final Method selectCharacterRun;
        private final Constructor<?> returnHomeConstructor;
        private final Method returnHomeRun;
        private final Method moveToMethod;
        private final Method npcMenuSelectMethod;
        private final Method createPartyMethod;
        private final Method invitePartyMethod;
        private final Method initQuickSlotsMethod;
        private final Method quickSlotSetSkillMethod;
        private final Method quickSlotClearMethod;
        private final Method saveQuickSlotsMethod;
        private final Method repairCostMethod;
        private final Method repairAllMethod;
        private final Method sellItemMethod;
        private final Method farmedGearListMethod;
        private final Method itemTemplateMethod;
        private final Method skillRequiredLevelMethod;
        private final Method addSkillPointMethod;
        private final Method addPotentialMethod;
        private final Method useInventoryItemMethod;
        private final Method inventoryUsageMethod;
        private final Method refreshMainCharDataMethod;
        private final Method xaphuMoveMethod;
        private final Method changeMapMethod;
        private final Method inventoryFullMethod;
        private final Field templateTypeField;
        private String[] originalServerNames;
        private String[] originalServerHosts;
        private short[] originalServerPorts;
        private boolean originalServerListCaptured;
        private final HashMap<String, ResolvedServerEndpoint> serverEndpointCache = new HashMap<String, ResolvedServerEndpoint>();
        private String lastServerEndpointNotice = "";
        private long nextLeaderActionAt;
        private long nextFollowActionAt;
        private long nextSellActionAt;
        private long sellBurstUntilAt;
        private long nextRepairActionAt;
        private long nextInventoryRefreshRequestAt;

        private ReflectionContext(ClassLoader gameLoader) throws Exception {
            this.acvClass = Class.forName("acv", false, gameLoader);
            this.yvClass = Class.forName("yv", false, gameLoader);
            this.xwClass = Class.forName("xw", false, gameLoader);
            this.bzClass = Class.forName("bz", false, gameLoader);
            this.juClass = Class.forName("ju", false, gameLoader);
            this.apClass = Class.forName("ap", false, gameLoader);
            this.abjClass = Class.forName("abj", false, gameLoader);
            this.bbClass = Class.forName("bb", false, gameLoader);
            this.zvClass = Class.forName("zv", false, gameLoader);
            this.bqClass = Class.forName("bq", false, gameLoader);
            this.vhClass = Class.forName("vh", false, gameLoader);
            this.hwClass = Class.forName("hw", false, gameLoader);
            this.scClass = Class.forName("sc", false, gameLoader);
            this.gdClass = Class.forName("gd", false, gameLoader);
            this.qlClass = Class.forName("ql", false, gameLoader);
            this.yiClass = Class.forName("yi", false, gameLoader);
            this.ycClass = Class.forName("yc", false, gameLoader);
            this.aaiClass = Class.forName("aai", false, gameLoader);
            this.xzClass = Class.forName("xz", false, gameLoader);
            this.acoClass = Class.forName("aco", false, gameLoader);
            this.goClass = Class.forName("go", false, gameLoader);
            this.qzClass = Class.forName("qz", false, gameLoader);
            this.wbClass = Class.forName("wb", false, gameLoader);
            this.aaoClass = Class.forName("aao", false, gameLoader);
            this.cgClass = Class.forName("cg", false, gameLoader);
            this.currentScreen = publicField(acvClass, "q");
            this.gameScreen = publicField(acvClass, "s");
            this.loginScreen = publicField(acvClass, "v");
            this.autoScreen = publicField(acvClass, "I");
            this.gameCanvas = publicField(acvClass, "a");
            this.serverIndexField = publicField(yvClass, "a");
            this.serverNamesField = declaredField(yvClass, "b");
            this.serverHostsField = publicField(yvClass, "c");
            this.serverPortsField = publicField(yvClass, "d");
            this.targetModeField = publicField(abjClass, "at");
            this.autoFightEnabled = publicField(abjClass, "az");
            this.autoHealEnabled = publicField(abjClass, "aB");
            this.hpThreshold = publicField(juClass, "a");
            this.mpThreshold = publicField(juClass, "b");
            this.characterIndexField = publicField(bqClass, "d");
            this.characterEntriesField = publicField(bqClass, "b");
            this.characterIdField = publicField(hwClass, "ad");
            this.autoPartyEnabled = publicField(juClass, "d");
            this.autoPickupPotionEnabled = declaredField(juClass, "A");
            this.autoPickupEquipmentEnabled = declaredField(juClass, "B");
            this.autoPickupMaterialEnabled = declaredField(juClass, "C");
            this.autoPickupAllEnabled = declaredField(juClass, "D");
            this.autoComeHomeEnabled = publicField(juClass, "i");
            this.autoAttackSkillsField = declaredField(juClass, "q");
            this.autoBuffSkillsField = declaredField(juClass, "p");
            this.loginUserField = publicField(xwClass, "a");
            this.loginPassField = publicField(xwClass, "b");
            this.connectedField = publicField(acoClass, "c");
            this.gameServiceField = publicField(abjClass, "G");
            this.actorListField = publicField(abjClass, "o");
            this.mainCharField = publicField(abjClass, "t");
            this.currentTargetField = publicField(abjClass, "u");
            this.currentMapIdField = publicField(abjClass, "aL");
            this.actorCategoryField = publicField(vhClass, "cG");
            this.actorIdField = publicField(vhClass, "cH");
            this.actorPartyIdField = publicField(vhClass, "cJ");
            this.actorPartyMasterField = publicField(vhClass, "cK");
            this.actorXField = publicField(vhClass, "cL");
            this.actorYField = publicField(vhClass, "cM");
            this.actorHpField = publicField(apClass, "v");
            this.actorNameField = publicField(hwClass, "an");
            this.monsterTemplateIdField = publicField(bbClass, "l");
            this.escortVisualTemplateIdField = declaredField(zvClass, "am");
            this.charLevelField = publicField(hwClass, "N");
            this.basePointField = publicField(hwClass, "aA");
            this.skillPointField = publicField(hwClass, "aB");
            this.movePathField = publicField(scClass, "s");
            this.moveIndexField = publicField(scClass, "r");
            this.pendingInviteActorField = publicField(abjClass, "K");
            this.partyMembersField = publicField(hwClass, "bx");
            this.partyMemberIdField = publicField(xzClass, "a");
            this.partyMemberNameField = publicField(xzClass, "b");
            this.learnedSkillsField = publicField(hwClass, "aT");
            this.inventoryItemsField = publicField(hwClass, "bv");
            this.inventoryCandidateFields = discoverInventoryCandidateFields(hwClass, this.inventoryItemsField);
            this.inventoryPageCountField = declaredField(hwClass, "e");
            this.wornItemsField = publicField(hwClass, "aU");
            this.moneyField = publicField(hwClass, "bs");
            this.itemIdField = publicField(qlClass, "i");
            this.itemTemplateIdField = publicField(qlClass, "r");
            this.itemDurabilityField = publicField(qlClass, "u");
            this.itemLockField = publicField(qlClass, "C");
            this.itemDropFlagField = publicField(qlClass, "h");
            this.itemColorField = publicField(qlClass, "K");
            this.itemUseDurationField = publicField(qlClass, "w");
            this.templateNameField = publicField(ycClass, "a");
            this.serverSingleton = publicMethod(yvClass, "e");
            this.charSingleton = publicMethod(bqClass, "e");
            this.acoSingleton = publicMethod(acoClass, "a");
            this.goSingleton = publicMethod(goClass, "a");
            this.loginMethod = declaredMethod(xwClass, "e");
            this.showLoginScreenMethod = publicMethod(xwClass, "a");
            this.setTextMethod = publicMethod(bzClass, "a", String.class);
            this.refreshTextMethod = publicMethod(bzClass, "b");
            this.autoModeMethod = publicMethod(juClass, "b", int.class);
            this.resetToServerListConstructor = declaredConstructor(wbClass, acvClass);
            this.resetToServerListRun = publicMethod(wbClass, "a");
            this.selectCharacterConstructor = declaredConstructor(aaoClass, bqClass);
            this.selectCharacterRun = publicMethod(aaoClass, "a");
            this.returnHomeConstructor = declaredConstructor(cgClass, abjClass);
            this.returnHomeRun = publicMethod(cgClass, "a");
            this.moveToMethod = publicMethod(abjClass, "d", int.class, int.class);
            this.npcMenuSelectMethod = publicMethod(goClass, "a", int.class, Byte.TYPE, int.class);
            this.createPartyMethod = publicMethod(goClass, "g", int.class);
            this.invitePartyMethod = publicMethod(goClass, "e", int.class, int.class);
            this.initQuickSlotsMethod = publicMethod(scClass, "Q");
            this.quickSlotSetSkillMethod = publicMethod(gdClass, "a", int.class, boolean.class);
            this.quickSlotClearMethod = publicMethod(gdClass, "d");
            this.saveQuickSlotsMethod = publicMethod(aaiClass, "b");
            this.repairCostMethod = publicMethod(hwClass, "g", int.class);
            this.repairAllMethod = publicMethod(goClass, "d", int.class);
            this.sellItemMethod = publicMethod(goClass, "f", short.class);
            this.farmedGearListMethod = publicMethod(qlClass, "g");
            this.itemTemplateMethod = publicMethod(yiClass, "b", int.class);
            this.skillRequiredLevelMethod = publicMethod(qzClass, "c", int.class, int.class);
            this.addSkillPointMethod = publicMethod(goClass, "m", int.class);
            this.addPotentialMethod = publicMethod(goClass, "k", int.class, int.class);
            this.useInventoryItemMethod = publicMethod(goClass, "g", short.class);
            this.inventoryUsageMethod = publicMethod(hwClass, "u");
            this.refreshMainCharDataMethod = publicMethod(goClass, "f");
            this.xaphuMoveMethod = publicMethod(goClass, "a", byte.class, int.class);
            this.changeMapMethod = publicMethod(goClass, "b", int.class, int.class, int.class);
            this.inventoryFullMethod = publicMethod(hwClass, "s");
            this.templateTypeField = publicField(ycClass, "c");
        }

        private static Field[] discoverInventoryCandidateFields(Class<?> ownerClass, Field preferredField) {
            ArrayList<Field> fields = new ArrayList<Field>();
            if (preferredField != null) {
                preferredField.setAccessible(true);
                fields.add(preferredField);
            }
            Field[] declaredFields = ownerClass.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                if (!Modifier.isStatic(field.getModifiers()) || !List.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                field.setAccessible(true);
                if (containsField(fields, field)) {
                    continue;
                }
                fields.add(field);
            }
            return fields.toArray(new Field[fields.size()]);
        }

        private static boolean containsField(List<Field> fields, Field candidate) {
            if (candidate == null) {
                return false;
            }
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                if (field != null && field.getDeclaringClass() == candidate.getDeclaringClass() && field.getName().equals(candidate.getName())) {
                    return true;
                }
            }
            return false;
        }

        private Object serverScreen() throws Exception {
            return serverSingleton.invoke(null);
        }

        private Object charScreen() throws Exception {
            return charSingleton.invoke(null);
        }

        private boolean connected() throws Exception {
            Object network = acoSingleton.invoke(null);
            return network != null && connectedField.getBoolean(network);
        }

        private void prepareLogin(AutoConfig config, int autoMode, boolean preferBundledServer) throws Exception {
            Object login = loginScreen.get(null);
            if (login == null) {
                return;
            }
            applyConfigState(config, autoMode, preferBundledServer);
            showLoginScreenMethod.invoke(login);
            applyCredentials(login, config);
        }

        private void submitLogin(AutoConfig config, int autoMode, boolean preferBundledServer) throws Exception {
            Object login = loginScreen.get(null);
            if (login == null) {
                return;
            }
            prepareLogin(config, autoMode, preferBundledServer);
            loginMethod.invoke(login);
        }

        private void applyConfigState(AutoConfig config, int autoMode, boolean preferBundledServer) throws Exception {
            int safeServerIndex = applyServerOverride(config, preferBundledServer);
            serverIndexField.setInt(null, safeServerIndex);
            targetModeField.setByte(null, (byte)clamp(config.focusMode, 0, TARGET_MODE_OPTIONS.length - 1));
            hpThreshold.setInt(null, config.hpPercent);
            mpThreshold.setInt(null, config.mpPercent);
            applyExtraAutoFlags(config);
        }

        private int applyServerOverride(AutoConfig config, boolean preferBundledServer) throws Exception {
            int safeServerIndex = resolveServerIndex(config.serverIndex);
            restoreBundledServerList();
            Object names = serverNamesField.get(null);
            Object hosts = serverHostsField.get(null);
            Object ports = serverPortsField.get(null);
            if (!preferBundledServer && config.customServerName.length() > 0) {
                setArrayValue(names, safeServerIndex, config.customServerName);
            }
            String requestedHost = stringArrayValue(hosts, safeServerIndex);
            int requestedPort = shortArrayValue(ports, safeServerIndex, 19129);
            if (!preferBundledServer && config.customHost.length() > 0) {
                requestedHost = config.customHost;
                requestedPort = config.customPort;
            }
            ResolvedServerEndpoint endpoint = resolveServerEndpoint(requestedHost, requestedPort);
            if (endpoint.host.length() > 0) {
                setArrayValue(hosts, safeServerIndex, endpoint.host);
            }
            setArrayValue(ports, safeServerIndex, Integer.valueOf(endpoint.port));
            announceServerEndpoint(safeServerIndex, requestedHost, requestedPort, endpoint);
            return safeServerIndex;
        }

        private void ensureAutoMode(AutoConfig config, int autoMode, long now, long lastAutoApplyAt) throws Exception {
            if (now - lastAutoApplyAt < 2000L) {
                return;
            }
            boolean attack = autoFightEnabled.getBoolean(null);
            boolean heal = autoHealEnabled.getBoolean(null);
            if (matchesAutoMode(autoMode, attack, heal)) {
                return;
            }
            setAutoModeNow(config, autoMode);
        }

        private void setAutoModeNow(AutoConfig config, int autoMode) throws Exception {
            Object auto = autoScreen.get(null);
            if (auto == null) {
                return;
            }
            targetModeField.setByte(null, (byte)clamp(config.focusMode, 0, TARGET_MODE_OPTIONS.length - 1));
            hpThreshold.setInt(null, config.hpPercent);
            mpThreshold.setInt(null, config.mpPercent);
            applyExtraAutoFlags(config);
            autoModeMethod.invoke(auto, Integer.valueOf(autoMode));
        }

        private void applyExtraAutoFlags(AutoConfig config) throws Exception {
            autoComeHomeEnabled.setBoolean(null, config.autoComeHome);
            Object auto = autoScreen.get(null);
            if (auto == null) {
                return;
            }
            autoPartyEnabled.setBoolean(auto, config.shouldEnableAutoParty());
            autoPickupPotionEnabled.setBoolean(auto, config.pickupPotion);
            autoPickupEquipmentEnabled.setBoolean(auto, config.pickupEquipment);
            autoPickupMaterialEnabled.setBoolean(auto, config.pickupMaterial);
            autoPickupAllEnabled.setBoolean(auto, config.pickupAll);
        }

        private void ensureSkillConfig(AutoConfig config, long now, long lastSkillApplyAt) throws Exception {
            if (!config.hasSkillOverride() || now - lastSkillApplyAt < 5000L) {
                return;
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return;
            }
            Object mainChar = mainCharField.get(game);
            Object auto = autoScreen.get(null);
            if (mainChar == null || auto == null) {
                return;
            }
            if (scClass == null) {
                return;
            }

            Object quickSlots = scClass.getField("a").get(null);
            if (quickSlots == null) {
                initQuickSlotsMethod.invoke(mainChar);
                quickSlots = scClass.getField("a").get(null);
            }
            if (quickSlots == null || !quickSlots.getClass().isArray()) {
                return;
            }

            Object learned = learnedSkillsField.get(null);
            boolean changed = false;
            int[] attackSkills = config.parseAttackSkills();
            int[] buffSkills = config.parseBuffSkills();
            Object attackRow = Array.getLength(quickSlots) > 0 ? Array.get(quickSlots, 0) : null;
            Object buffRow = Array.getLength(quickSlots) > 1 ? Array.get(quickSlots, 1) : null;

            if (attackSkills != null) {
                changed |= applySkillSlots(autoAttackSkillsField.get(auto), attackRow, attackSkills, false, learned);
            }
            if (buffSkills != null) {
                changed |= applySkillSlots(autoBuffSkillsField.get(auto), buffRow, buffSkills, true, learned);
            }
            if (changed) {
                saveQuickSlotsMethod.invoke(null);
            }
        }

        private String tryAutoUpgradeSkill(AutoConfig config) throws Exception {
            if (!config.autoUpgradeSkill) {
                return "";
            }
            int[] upgradeSkills = config.parseUpgradeSkills();
            if (upgradeSkills == null) {
                return "";
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return "";
            }
            Object mainChar = mainCharField.get(game);
            Object service = resolveGameService(game);
            if (mainChar == null || service == null) {
                return "";
            }
            int skillPoints = ((Number)skillPointField.get(mainChar)).intValue();
            if (skillPoints <= 0) {
                return "";
            }
            Object learned = learnedSkillsField.get(null);
            if (!(learned instanceof byte[])) {
                return "";
            }
            byte[] learnedSkills = (byte[])learned;
            int charLevel = ((Number)charLevelField.get(mainChar)).intValue();
            for (int i = 0; i < upgradeSkills.length; i++) {
                int skillId = upgradeSkills[i];
                if (skillId < 0 || skillId >= learnedSkills.length) {
                    continue;
                }
                int currentSkillLevel = learnedSkills[skillId];
                if (currentSkillLevel < 0 || currentSkillLevel >= 10) {
                    continue;
                }
                int requiredLevel = ((Number)skillRequiredLevelMethod.invoke(null, Integer.valueOf(config.characterClass), Integer.valueOf(currentSkillLevel))).intValue();
                if (charLevel < requiredLevel) {
                    continue;
                }
                addSkillPointMethod.invoke(service, Integer.valueOf(skillId));
                return "auto_skill_" + skillId;
            }
            return "";
        }

        private String tryAutoUpgradePotential(AutoConfig config) throws Exception {
            if (!config.autoUpgradePotential || config.potentialTargetIndex < 0) {
                return "";
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return "";
            }
            Object mainChar = mainCharField.get(game);
            Object service = resolveGameService(game);
            if (mainChar == null || service == null) {
                return "";
            }
            int basePoints = ((Number)basePointField.get(mainChar)).intValue();
            if (basePoints <= 0) {
                return "";
            }
            int spend = Math.max(1, Math.min(basePoints, config.potentialPointsPerAdd));
            addPotentialMethod.invoke(service, Integer.valueOf(config.potentialTargetIndex), Integer.valueOf(spend));
            return "auto_tiem_nang_" + (config.potentialTargetIndex + 1);
        }

        private String tryAutoUseInventoryItems(AutoConfig config) throws Exception {
            if (!config.autoUseInventoryItems) {
                return "";
            }
            int[] templateIds = config.parseInventoryTemplateIds();
            if (templateIds == null) {
                return "";
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return "";
            }
            Object service = resolveGameService(game);
            if (service == null) {
                return "";
            }
            StringBuilder note = new StringBuilder();
            ArrayList<Integer> usedTemplates = new ArrayList<Integer>();
            for (int i = 0; i < templateIds.length; i++) {
                int templateId = templateIds[i];
                if (templateId < 0 || usedTemplates.contains(Integer.valueOf(templateId))) {
                    continue;
                }
                Object item = findInventoryItemByTemplateId(templateId);
                if (item == null) {
                    continue;
                }
                short itemId = ((Number)itemIdField.get(item)).shortValue();
                useInventoryItemMethod.invoke(service, Short.valueOf(itemId));
                usedTemplates.add(Integer.valueOf(templateId));
                if (note.length() > 0) {
                    note.append("+");
                }
                note.append("item_").append(templateId);
            }
            return note.toString();
        }

        private Object findInventoryItemByTemplateId(int templateId) throws Exception {
            List<?> inventory = inventoryItemsSnapshot();
            for (int i = 0; i < inventory.size(); i++) {
                Object item = inventory.get(i);
                if (!isAutoUseInventoryCandidate(item)) {
                    continue;
                }
                if (((Number)itemTemplateIdField.get(item)).intValue() == templateId) {
                    return item;
                }
            }
            return null;
        }

        private void refreshInventoryCache(Path cachePath) throws Exception {
            if (cachePath == null) {
                return;
            }
            List<?> inventory = inventoryItemsSnapshot();
            if (inventory.isEmpty()) {
                requestInventoryRefresh();
                return;
            }
            Properties properties = new Properties();
            properties.setProperty("updated_at", Long.toString(System.currentTimeMillis()));
            properties.setProperty("count", Integer.toString(inventory.size()));
            for (int i = 0; i < inventory.size(); i++) {
                Object item = inventory.get(i);
                if (item == null) {
                    continue;
                }
                int templateId = ((Number)itemTemplateIdField.get(item)).intValue();
                int itemId = ((Number)itemIdField.get(item)).intValue();
                String itemName = "Vat pham";
                int itemType = -1;
                Object template = itemTemplateMethod.invoke(null, Integer.valueOf(templateId));
                if (template != null) {
                    Object rawName = templateNameField.get(template);
                    if (rawName != null) {
                        itemName = String.valueOf(rawName).trim();
                    }
                    itemType = ((Number)templateTypeField.get(template)).intValue();
                }
                properties.setProperty("item." + i + ".template_id", Integer.toString(templateId));
                properties.setProperty("item." + i + ".item_id", Integer.toString(itemId));
                properties.setProperty("item." + i + ".name", itemName);
                properties.setProperty("item." + i + ".type", Integer.toString(itemType));
            }
            writePropertiesAtomically(cachePath, properties, "KPAH inventory cache");
        }

        private List<?> inventoryItemsSnapshot() throws Exception {
            ArrayList<Object> bestItems = new ArrayList<Object>();
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < inventoryCandidateFields.length; i++) {
                Field field = inventoryCandidateFields[i];
                ArrayList<Object> items = readInventoryItems(field);
                int score = scoreInventoryField(field, items);
                if (score > bestScore) {
                    bestScore = score;
                    bestItems = items;
                }
            }
            return bestItems;
        }

        private ArrayList<Object> readInventoryItems(Field sourceField) throws Exception {
            ArrayList<Object> items = new ArrayList<Object>();
            if (sourceField == null) {
                return items;
            }
            Object raw = sourceField.get(null);
            if (!(raw instanceof List<?>)) {
                return items;
            }
            List<?> list = (List<?>)raw;
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                if (isLikelyInventoryItem(item)) {
                    items.add(item);
                }
            }
            return items;
        }

        private boolean isLikelyInventoryItem(Object item) throws Exception {
            if (item == null || !qlClass.isInstance(item)) {
                return false;
            }
            int templateId = ((Number)itemTemplateIdField.get(item)).intValue();
            if (templateId <= 0) {
                return false;
            }
            int itemId = ((Number)itemIdField.get(item)).intValue();
            if (itemId > 0) {
                return true;
            }
            Object template = itemTemplateMethod.invoke(null, Integer.valueOf(templateId));
            return template != null;
        }

        private int scoreInventoryField(Field field, List<?> items) throws Exception {
            int score = items.size() * 100;
            if (field == inventoryItemsField) {
                score += 20000;
            }
            String fieldName = field == null ? "" : field.getName();
            if ("bv".equals(fieldName)) {
                score += 5000;
            } else if ("by".equals(fieldName)) {
                score += 1000;
            } else if ("bw".equals(fieldName) || "bx".equals(fieldName)) {
                score -= 500;
            }
            for (int i = 0; i < items.size(); i++) {
                Object item = items.get(i);
                if (item == null) {
                    continue;
                }
                if (((Number)itemIdField.get(item)).intValue() > 0) {
                    score += 5;
                }
                if (((Number)itemTemplateIdField.get(item)).intValue() > 0) {
                    score += 10;
                }
            }
            return score;
        }

        private void requestInventoryRefresh() throws Exception {
            long now = System.currentTimeMillis();
            if (now < nextInventoryRefreshRequestAt) {
                return;
            }
            Object game = gameScreen.get(null);
            if (game == null || mainCharField.get(game) == null) {
                return;
            }
            Object service = resolveGameService(game);
            if (service == null || !goClass.isInstance(service)) {
                service = goSingleton.invoke(null);
            }
            if (service == null) {
                return;
            }
            refreshMainCharDataMethod.invoke(service);
            nextInventoryRefreshRequestAt = now + 8000L;
        }

        private boolean applySkillSlots(Object autoSlots, Object quickSlots, int[] desired, boolean buff, Object learned) throws Exception {
            if (autoSlots == null || quickSlots == null || !autoSlots.getClass().isArray() || !quickSlots.getClass().isArray()) {
                return false;
            }
            int length = Math.min(Math.min(Array.getLength(autoSlots), Array.getLength(quickSlots)), desired.length);
            boolean changed = false;
            for (int i = 0; i < length; i++) {
                int desiredSkill = desired[i];
                if (desiredSkill >= 0 && !isLearnedSkill(learned, desiredSkill)) {
                    continue;
                }
                int currentSkill = ((Number)Array.get(autoSlots, i)).intValue();
                if (currentSkill == desiredSkill) {
                    continue;
                }
                Object quickSlot = Array.get(quickSlots, i);
                Array.setInt(autoSlots, i, desiredSkill);
                if (desiredSkill >= 0) {
                    quickSlotSetSkillMethod.invoke(quickSlot, Integer.valueOf(desiredSkill), Boolean.valueOf(buff));
                } else {
                    quickSlotClearMethod.invoke(quickSlot);
                }
                changed = true;
            }
            return changed;
        }

        private boolean isLearnedSkill(Object learned, int skillId) {
            if (skillId < 0 || learned == null || !learned.getClass().isArray()) {
                return false;
            }
            int length = Array.getLength(learned);
            if (skillId >= length) {
                return false;
            }
            Object value = Array.get(learned, skillId);
            return value instanceof Number && ((Number)value).intValue() > 0;
        }

        private void handleTeamAutomation(AutoConfig config, long now) throws Exception {
            if (config.teamMode == AutoConfig.TEAM_MODE_DISABLED) {
                return;
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return;
            }
            String mainName = normalizeName(actorNameField.get(mainChar));
            if (mainName.length() == 0) {
                return;
            }
            if (config.teamMode == AutoConfig.TEAM_MODE_LEADER) {
                handleLeaderTeamAutomation(game, mainChar, config, now, mainName);
            } else if (config.teamMode == AutoConfig.TEAM_MODE_FOLLOWER) {
                handleFollowerTeamAutomation(game, mainChar, config, now, mainName);
            }
        }

        private void handleLeaderTeamAutomation(Object game, Object mainChar, AutoConfig config, long now, String mainName) throws Exception {
            if (now < nextLeaderActionAt) {
                return;
            }
            List<String> members = config.teamMemberNames();
            if (members.isEmpty()) {
                return;
            }
            Object candidate = findVisibleInviteCandidate(game, members, mainName);
            if (candidate == null) {
                return;
            }
            int mainId = actorIdField.getShort(mainChar);
            int partyId = actorPartyIdField.getShort(mainChar);
            Object gameService = gameServiceField.get(game);
            if (gameService == null) {
                return;
            }
            int candidateId = actorIdField.getShort(candidate);
            if (partyId < 0) {
                pendingInviteActorField.setInt(null, candidateId);
                createPartyMethod.invoke(gameService, Integer.valueOf(mainId));
                nextLeaderActionAt = now + 2500L;
                return;
            }
            int masterId = actorPartyMasterField.getShort(mainChar);
            if (masterId != mainId) {
                return;
            }
            invitePartyMethod.invoke(gameService, Integer.valueOf(candidateId), Integer.valueOf(0));
            nextLeaderActionAt = now + 2500L;
        }

        private void handleFollowerTeamAutomation(Object game, Object mainChar, AutoConfig config, long now, String mainName) throws Exception {
            if (now < nextFollowActionAt) {
                return;
            }
            String leaderName = config.resolveLeaderName();
            if (leaderName.length() == 0 || leaderName.equals(mainName)) {
                return;
            }
            Object leader = findVisibleActorByName(game, leaderName, actorIdField.getShort(mainChar));
            if (leader == null) {
                return;
            }
            int followSlot = resolveFollowSlot(config, mainName, leaderName);
            int[] offsetX = new int[]{0, -24, 24, 0, 0};
            int[] offsetY = new int[]{0, 0, 0, -24, 24};
            int safeSlot = clamp(followSlot, 0, offsetX.length - 1);
            int targetX = actorXField.getShort(leader) + offsetX[safeSlot];
            int targetY = actorYField.getShort(leader) + offsetY[safeSlot];
            int selfX = actorXField.getShort(mainChar);
            int selfY = actorYField.getShort(mainChar);
            int stopDistance = Math.max(24, config.followDistance / 2);
            if (distance(selfX, selfY, targetX, targetY) <= stopDistance) {
                nextFollowActionAt = now + 500L;
                return;
            }
            Object currentPath = movePathField.get(mainChar);
            if (currentPath == null || distance(selfX, selfY, targetX, targetY) >= config.followDistance) {
                moveToMethod.invoke(game, Integer.valueOf(targetX), Integer.valueOf(targetY));
                moveIndexField.setInt(mainChar, 0);
            }
            nextFollowActionAt = now + 500L;
        }

        private int currentMapId() throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return -1;
            }
            return ((Number)currentMapIdField.get(game)).intValue();
        }

        private int mainCharLevel() throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return 0;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return 0;
            }
            return ((Number)charLevelField.get(mainChar)).intValue();
        }

        private boolean isAutoFightEnabled() throws Exception {
            return autoFightEnabled.getBoolean(null);
        }

        private Object currentMonsterTarget() throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return null;
            }
            Object target = currentTargetField.get(game);
            if (!bbClass.isInstance(target) || isEscortActor(target)) {
                return null;
            }
            return target;
        }

        private boolean hasCloseMonsterTarget(int maxDistance) throws Exception {
            Object target = currentMonsterTarget();
            return target != null && distanceToMainChar(target) <= maxDistance;
        }

        private int currentNearbyEscortMonsterId(int maxDistance) throws Exception {
            Object escort = currentNearbyEscortMonster(maxDistance);
            return escort == null ? -1 : escortTrackingId(escort);
        }

        private int currentNearbyEscortDistance(int maxDistance) throws Exception {
            Object escort = currentNearbyEscortMonster(maxDistance);
            return escort == null ? Integer.MAX_VALUE : distanceToMainChar(escort);
        }

        private boolean moveMainCharNearEscort(int maxDistance, int stopDistance) throws Exception {
            Object escort = currentNearbyEscortMonster(maxDistance);
            if (escort == null) {
                return false;
            }
            int targetX = actorXField.getShort(escort);
            int targetY = actorYField.getShort(escort);
            return moveMainCharTo(targetX, targetY, stopDistance);
        }

        private Object currentNearbyEscortMonster(int maxDistance) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return null;
            }
            Object actors = actorListField.get(game);
            if (!(actors instanceof List<?>)) {
                return null;
            }
            List<?> actorList = (List<?>)actors;
            Object bestActor = null;
            int bestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < actorList.size(); i++) {
                Object actor = actorList.get(i);
                if (!isEscortActor(actor)) {
                    continue;
                }
                int distance = distanceToMainChar(actor);
                if (distance > maxDistance || distance >= bestDistance) {
                    continue;
                }
                bestActor = actor;
                bestDistance = distance;
            }
            return bestActor;
        }

        private boolean isEscortActor(Object actor) throws Exception {
            if (actor == null) {
                return false;
            }
            if (!bbClass.isInstance(actor) && !zvClass.isInstance(actor)) {
                return false;
            }
            return isEscortMonsterTemplate(escortTemplateId(actor));
        }

        private int escortTemplateId(Object actor) throws Exception {
            if (actor == null) {
                return -1;
            }
            if (zvClass.isInstance(actor)) {
                return ((Number)escortVisualTemplateIdField.get(actor)).intValue();
            }
            if (bbClass.isInstance(actor)) {
                return ((Number)monsterTemplateIdField.get(actor)).intValue();
            }
            return -1;
        }

        private int escortTrackingId(Object actor) throws Exception {
            if (actor == null) {
                return -1;
            }
            int actorId = actorId(actor);
            if (actorId >= 0) {
                return actorId;
            }
            int templateId = escortTemplateId(actor);
            if (templateId < 0) {
                return -1;
            }
            return 1000000 + templateId;
        }

        private int actorId(Object actor) throws Exception {
            if (actor == null) {
                return -1;
            }
            return ((Number)actorIdField.get(actor)).intValue();
        }

        private int actorHp(Object actor) throws Exception {
            if (actor == null) {
                return -1;
            }
            return ((Number)actorHpField.get(actor)).intValue();
        }

        private int distanceToMainChar(Object actor) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null || actor == null) {
                return Integer.MAX_VALUE;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return Integer.MAX_VALUE;
            }
            int selfX = ((Number)actorXField.get(mainChar)).intValue();
            int selfY = ((Number)actorYField.get(mainChar)).intValue();
            int targetX = ((Number)actorXField.get(actor)).intValue();
            int targetY = ((Number)actorYField.get(actor)).intValue();
            return distance(selfX, selfY, targetX, targetY);
        }

        private boolean moveMainCharTo(int targetX, int targetY, int stopDistance) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return false;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return false;
            }
            int selfX = actorXField.getShort(mainChar);
            int selfY = actorYField.getShort(mainChar);
            int safeStopDistance = Math.max(16, stopDistance);
            int distance = distance(selfX, selfY, targetX, targetY);
            if (distance <= safeStopDistance) {
                return true;
            }
            Object currentPath = movePathField.get(mainChar);
            if (currentPath == null || distance >= Math.max(48, safeStopDistance * 2)) {
                moveToMethod.invoke(game, Integer.valueOf(targetX), Integer.valueOf(targetY));
                moveIndexField.setInt(mainChar, 0);
            }
            return false;
        }

        private int distanceMainCharTo(int targetX, int targetY) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return Integer.MAX_VALUE;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return Integer.MAX_VALUE;
            }
            int selfX = actorXField.getShort(mainChar);
            int selfY = actorYField.getShort(mainChar);
            return distance(selfX, selfY, targetX, targetY);
        }

        private boolean moveMainCharToward(int targetX, int targetY, int maxStepDistance, int stopDistance) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return false;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return false;
            }
            int selfX = actorXField.getShort(mainChar);
            int selfY = actorYField.getShort(mainChar);
            int safeStopDistance = Math.max(16, stopDistance);
            int remainingDistance = distance(selfX, selfY, targetX, targetY);
            if (remainingDistance <= safeStopDistance) {
                return true;
            }
            if (maxStepDistance <= safeStopDistance || remainingDistance <= maxStepDistance) {
                return moveMainCharTo(targetX, targetY, stopDistance);
            }
            int maxNodes = Math.max(2, (maxStepDistance + 15) / 16);
            moveToMethod.invoke(game, Integer.valueOf(targetX), Integer.valueOf(targetY));
            moveIndexField.setInt(mainChar, 0);
            trimMainCharPath(mainChar, maxNodes);
            return false;
        }

        private void trimMainCharPath(Object mainChar, int maxNodes) throws Exception {
            if (mainChar == null || maxNodes <= 0) {
                return;
            }
            Object rawPath = movePathField.get(mainChar);
            if (!(rawPath instanceof short[])) {
                return;
            }
            short[] path = (short[])rawPath;
            int remainingNodes = 0;
            for (int i = 0; i < path.length; i++) {
                if (path[i] > 0) {
                    remainingNodes++;
                }
            }
            if (remainingNodes <= maxNodes) {
                return;
            }
            ArrayList<Short> tail = new ArrayList<Short>(maxNodes);
            for (int i = path.length - 1; i >= 0 && tail.size() < maxNodes; i--) {
                if (path[i] > 0) {
                    tail.add(Short.valueOf(path[i]));
                }
            }
            if (tail.isEmpty()) {
                return;
            }
            short[] trimmed = new short[tail.size()];
            for (int i = 0; i < tail.size(); i++) {
                trimmed[trimmed.length - 1 - i] = tail.get(i).shortValue();
            }
            movePathField.set(mainChar, trimmed);
            moveIndexField.setInt(mainChar, 0);
        }

        private boolean travel(TravelEdge edge, int currentMapId) throws Exception {
            if (edge == null) {
                return false;
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return false;
            }
            Object service = resolveGameService(game);
            if (service == null) {
                return false;
            }
            if (edge.type == TravelEdge.TYPE_XAPHU) {
                int currentGroup = normalizeRouteGroup(findMapGroup(currentMapId));
                int xaphuIndex = xaphuIndexForGroup(currentGroup);
                if (xaphuIndex < 0) {
                    return false;
                }
                int slot = findMapSlot(currentMapId);
                int targetMapId = resolveMapIdForGroup(edge.toGroup, slot);
                if (targetMapId <= 0) {
                    return false;
                }
                xaphuMoveMethod.invoke(service, Byte.valueOf((byte)xaphuIndex), Integer.valueOf(targetMapId));
                return true;
            }
            TravelDestination destination = resolveTravelDestination(edge.gateTriples, currentMapId);
            if (destination == null || destination.mapId < 0) {
                return false;
            }
            changeMapMethod.invoke(service, Integer.valueOf(destination.mapId), Integer.valueOf(destination.x), Integer.valueOf(destination.y));
            return true;
        }

        private Object findVisibleInviteCandidate(Object game, List<String> members, String mainName) throws Exception {
            for (int i = 0; i < members.size(); i++) {
                String memberName = normalizeName(members.get(i));
                if (memberName.length() == 0 || memberName.equals(mainName) || isInParty(memberName)) {
                    continue;
                }
                Object actor = findVisibleActorByName(game, memberName, -1);
                if (actor != null) {
                    return actor;
                }
            }
            return null;
        }

        private Object findVisibleActorByName(Object game, String targetName, int excludeId) throws Exception {
            if (targetName.length() == 0) {
                return null;
            }
            Object actors = actorListField.get(game);
            if (!(actors instanceof List<?>)) {
                return null;
            }
            List<?> actorList = (List<?>)actors;
            for (int i = 0; i < actorList.size(); i++) {
                Object actor = actorList.get(i);
                if (actor == null || actorCategoryField.getByte(actor) != 0) {
                    continue;
                }
                int actorId = actorIdField.getShort(actor);
                if (actorId == excludeId) {
                    continue;
                }
                String actorName = normalizeName(actorNameField.get(actor));
                if (targetName.equals(actorName)) {
                    return actor;
                }
            }
            return null;
        }

        private boolean isInParty(String name) throws Exception {
            Object members = partyMembersField.get(null);
            if (!(members instanceof List<?>)) {
                return false;
            }
            List<?> party = (List<?>)members;
            for (int i = 0; i < party.size(); i++) {
                Object member = party.get(i);
                if (member == null) {
                    continue;
                }
                String partyName = normalizeName(partyMemberNameField.get(member));
                if (name.equals(partyName)) {
                    return true;
                }
            }
            return false;
        }

        private int resolveFollowSlot(AutoConfig config, String mainName, String leaderName) {
            List<String> members = config.teamMemberNames();
            if (members.isEmpty()) {
                return 1;
            }
            ArrayList<String> normalized = new ArrayList<String>();
            if (!members.contains(leaderName)) {
                normalized.add(leaderName);
            }
            for (int i = 0; i < members.size(); i++) {
                String value = normalizeName(members.get(i));
                if (value.length() > 0 && !normalized.contains(value)) {
                    normalized.add(value);
                }
            }
            for (int i = 0; i < normalized.size(); i++) {
                if (mainName.equals(normalized.get(i))) {
                    return i;
                }
            }
            return 1;
        }

        private String handleMaintenance(AutoConfig config, long now) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return "";
            }
            Object mainChar = mainCharField.get(game);
            boolean inventoryFull = isInventoryFull(mainChar);
            boolean autoSellBurstActive = inventoryFull || now < sellBurstUntilAt;
            String note = "";
            if (config.autoSellFarmGear && autoSellBurstActive && now >= nextSellActionAt) {
                int soldCount = sellFarmedGear(true, config);
                if (soldCount > 0) {
                    sellBurstUntilAt = now + 8000L;
                } else if (inventoryFull) {
                    sellBurstUntilAt = now + 1500L;
                } else {
                    sellBurstUntilAt = 0L;
                }
                nextSellActionAt = now + (soldCount > 0 ? 900L : (inventoryFull ? 1200L : 2000L));
                if (soldCount > 0) {
                    note = "auto_sell_" + soldCount;
                }
            } else if (!inventoryFull && !config.autoSellFarmGear) {
                sellBurstUntilAt = 0L;
            }
            if (config.autoRepairGear && now >= nextRepairActionAt) {
                boolean repaired = repairAllGear(false, config);
                nextRepairActionAt = now + (repaired ? 180000L : 15000L);
                if (repaired) {
                    note = note.length() > 0 ? note + "+auto_repair" : "auto_repair";
                }
            }
            return note;
        }

        private boolean repairAllGear(boolean force, AutoConfig config) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return false;
            }
            Object mainChar = mainCharField.get(game);
            if (mainChar == null) {
                return false;
            }
            if (!force && !shouldRepairGear(mainChar, config.repairDurabilityThreshold)) {
                return false;
            }
            int repairCost = ((Number)repairCostMethod.invoke(mainChar, Integer.valueOf(2))).intValue();
            if (repairCost <= 0) {
                return false;
            }
            long money = ((Number)moneyField.get(mainChar)).longValue();
            if (money < (long)repairCost) {
                return false;
            }
            Object service = resolveGameService(game);
            if (service == null) {
                return false;
            }
            repairAllMethod.invoke(service, Integer.valueOf(2));
            return true;
        }

        private int sellFarmedGear(boolean force, AutoConfig config) throws Exception {
            if (!force && !config.autoSellFarmGear) {
                return 0;
            }
            Object game = gameScreen.get(null);
            if (game == null) {
                return 0;
            }
            Object service = resolveGameService(game);
            if (service == null) {
                return 0;
            }
            int limit = force ? 24 : 12;
            int soldCount = 0;
            Object farmedItems = farmedGearListMethod.invoke(null);
            if (farmedItems instanceof List<?>) {
                soldCount += sellItemsFromList((List<?>)farmedItems, service, limit - soldCount, false);
            }
            if (soldCount < limit) {
                Object mainChar = mainCharField.get(game);
                if (force || isInventoryFull(mainChar)) {
                    soldCount += sellItemsFromList(inventoryItemsSnapshot(), service, limit - soldCount, true);
                }
            }
            return soldCount;
        }

        private int sellItemsFromList(List<?> items, Object service, int limit, boolean requireFarmedMarker) throws Exception {
            if (items == null || service == null || limit <= 0) {
                return 0;
            }
            int soldCount = 0;
            for (int i = 0; i < items.size(); i++) {
                Object item = items.get(i);
                if (!isSellCandidate(item, requireFarmedMarker)) {
                    continue;
                }
                short itemId = ((Number)itemIdField.get(item)).shortValue();
                sellItemMethod.invoke(service, Short.valueOf(itemId));
                soldCount++;
                if (soldCount >= limit) {
                    break;
                }
            }
            return soldCount;
        }

        private boolean isSellCandidate(Object item, boolean requireFarmedMarker) throws Exception {
            if (item == null || ((Number)itemLockField.get(item)).intValue() != 0) {
                return false;
            }
            if (((Number)itemUseDurationField.get(item)).intValue() != 0) {
                return false;
            }
            int itemType = itemType(item);
            if (!isSellableGearType(itemType)) {
                return false;
            }
            if (!requireFarmedMarker) {
                return true;
            }
            int dropFlag = ((Number)itemDropFlagField.get(item)).intValue();
            int colorName = ((Number)itemColorField.get(item)).intValue();
            return dropFlag == 1 || colorName != 0;
        }

        private boolean isInventoryFull(Object mainChar) throws Exception {
            if (mainChar == null) {
                return false;
            }
            if (((Boolean)inventoryFullMethod.invoke(mainChar)).booleanValue()) {
                return true;
            }
            int usedSlots = ((Number)inventoryUsageMethod.invoke(mainChar)).intValue();
            int pageCount = Math.max(1, ((Number)inventoryPageCountField.get(mainChar)).intValue());
            return usedSlots >= 42 * pageCount;
        }

        private Object resolveGameService(Object game) throws Exception {
            Object service = gameServiceField.get(game);
            if (service != null) {
                return service;
            }
            return goSingleton.invoke(null);
        }

        private boolean shouldRepairGear(Object mainChar, int threshold) throws Exception {
            Object worn = wornItemsField.get(mainChar);
            if (!(worn instanceof List<?>)) {
                return false;
            }
            List<?> wornItems = (List<?>)worn;
            for (int i = 0; i < wornItems.size(); i++) {
                Object item = wornItems.get(i);
                if (item == null) {
                    continue;
                }
                int itemType = itemType(item);
                if (!isRepairableGearType(itemType)) {
                    continue;
                }
                int durability = ((Number)itemDurabilityField.get(item)).intValue();
                if (durability <= threshold) {
                    return true;
                }
            }
            return false;
        }

        private int itemType(Object item) throws Exception {
            int templateId = ((Number)itemTemplateIdField.get(item)).intValue();
            Object template = itemTemplateMethod.invoke(null, Integer.valueOf(templateId));
            if (template == null) {
                return -1;
            }
            return ((Number)templateTypeField.get(template)).intValue();
        }

        private boolean isAutoUseInventoryCandidate(Object item) throws Exception {
            return isLikelyInventoryItem(item) && isAutoUseInventoryTemplateType(itemType(item));
        }

        private boolean isSellableGearType(int itemType) {
            return itemType >= 0 && itemType < 13;
        }

        private boolean isRepairableGearType(int itemType) {
            return itemType >= 0 && itemType < 8;
        }

        private int distance(int x1, int y1, int x2, int y2) {
            int dx = x1 - x2;
            int dy = y1 - y2;
            return (int)Math.sqrt((double)(dx * dx + dy * dy));
        }

        private String normalizeName(Object value) {
            if (value == null) {
                return "";
            }
            return String.valueOf(value).trim().toLowerCase(Locale.ROOT);
        }

        private boolean selectCharacterIfReady(int characterIndex) throws Exception {
            Object charScreen = charScreen();
            if (charScreen == null) {
                return false;
            }
            Object chars = characterEntriesField.get(charScreen);
            if (chars == null || !chars.getClass().isArray()) {
                return false;
            }
            int length = Array.getLength(chars);
            if (length <= 0) {
                return false;
            }
            int safeIndex = clamp(characterIndex, 0, length - 1);
            characterIndexField.setInt(charScreen, safeIndex);
            Object selected = Array.get(chars, safeIndex);
            if (selected == null) {
                return false;
            }
            int characterId = characterIdField.getInt(selected);
            if (characterId <= 0) {
                return false;
            }
            Object action = selectCharacterConstructor.newInstance(charScreen);
            selectCharacterRun.invoke(action);
            return true;
        }

        private void selectCharacter(int characterIndex) throws Exception {
            selectCharacterIfReady(characterIndex);
        }

        private void resetToServerList() throws Exception {
            Object canvas = gameCanvas.get(null);
            if (canvas == null) {
                return;
            }
            Object action = resetToServerListConstructor.newInstance(canvas);
            resetToServerListRun.invoke(action);
        }

        private void applyCredentials(Object login, AutoConfig config) throws Exception {
            if (config.username.length() == 0 && config.password.length() == 0) {
                return;
            }
            Object userFieldObject = loginUserField.get(login);
            Object passFieldObject = loginPassField.get(login);
            setTextMethod.invoke(userFieldObject, config.username);
            refreshTextMethod.invoke(userFieldObject);
            setTextMethod.invoke(passFieldObject, config.password);
            refreshTextMethod.invoke(passFieldObject);
        }

        private boolean sendGameCommand(String command) throws Exception {
            String normalized = command == null ? "" : command.trim().toLowerCase();
            if (normalized.length() == 0) {
                return false;
            }
            if ("return_home".equals(normalized) || "velang".equals(normalized)) {
                Object game = gameScreen.get(null);
                if (game == null) {
                    return false;
                }
                Object action = returnHomeConstructor.newInstance(game);
                returnHomeRun.invoke(action);
                return true;
            }
            return false;
        }

        private boolean selectNpcMenu(int npcId, int menuId, int optionIndex) throws Exception {
            Object game = gameScreen.get(null);
            if (game == null) {
                return false;
            }
            Object service = resolveGameService(game);
            if (service == null) {
                return false;
            }
            npcMenuSelectMethod.invoke(service, Integer.valueOf(npcId), Byte.valueOf((byte)menuId), Integer.valueOf(optionIndex));
            return true;
        }

        private void restoreBundledServerList() throws Exception {
            ensureOriginalServerListCaptured();
            restoreArray(serverNamesField.get(null), originalServerNames);
            restoreArray(serverHostsField.get(null), originalServerHosts);
            restoreArray(serverPortsField.get(null), originalServerPorts);
        }

        private void ensureOriginalServerListCaptured() throws Exception {
            if (originalServerListCaptured) {
                return;
            }
            originalServerNames = copyStringArray(serverNamesField.get(null));
            originalServerHosts = copyStringArray(serverHostsField.get(null));
            originalServerPorts = copyShortArray(serverPortsField.get(null));
            originalServerListCaptured = true;
        }

        private int resolveServerIndex(int preferredIndex) throws Exception {
            int maxIndex = maxServerIndex();
            if (maxIndex < 0) {
                return Math.max(preferredIndex, 0);
            }
            return clamp(preferredIndex, 0, maxIndex);
        }

        private int maxServerIndex() throws Exception {
            int minLength = minDefinedLength(-1, arrayLength(serverNamesField.get(null)));
            minLength = minDefinedLength(minLength, arrayLength(serverHostsField.get(null)));
            minLength = minDefinedLength(minLength, arrayLength(serverPortsField.get(null)));
            if (minLength <= 0) {
                return -1;
            }
            return minLength - 1;
        }

        private static boolean matchesAutoMode(int autoMode, boolean attack, boolean heal) {
            if (autoMode == 0) {
                return attack && !heal;
            }
            if (autoMode == 1) {
                return !attack && heal;
            }
            if (autoMode == 2) {
                return attack && heal;
            }
            return !attack && !heal;
        }

        private static int clamp(int value, int min, int max) {
            if (value < min) {
                return min;
            }
            if (value > max) {
                return max;
            }
            return value;
        }

        private static int arrayLength(Object array) {
            if (array == null || !array.getClass().isArray()) {
                return -1;
            }
            return Array.getLength(array);
        }

        private static int minDefinedLength(int current, int candidate) {
            if (candidate < 0) {
                return current;
            }
            if (current < 0) {
                return candidate;
            }
            return Math.min(current, candidate);
        }

        private static void setArrayValue(Object array, int index, Object value) {
            if (array == null || !array.getClass().isArray()) {
                return;
            }
            int length = Array.getLength(array);
            if (index < 0 || index >= length) {
                return;
            }
            Class<?> componentType = array.getClass().getComponentType();
            if (componentType == String.class) {
                Array.set(array, index, String.valueOf(value));
                return;
            }
            if (componentType == Integer.TYPE || componentType == Integer.class) {
                Array.setInt(array, index, toInt(value));
                return;
            }
            if (componentType == Short.TYPE || componentType == Short.class) {
                Array.setShort(array, index, (short)toInt(value));
                return;
            }
            if (componentType == Byte.TYPE || componentType == Byte.class) {
                Array.setByte(array, index, (byte)toInt(value));
                return;
            }
            if (componentType == Long.TYPE || componentType == Long.class) {
                Array.setLong(array, index, (long)toInt(value));
                return;
            }
            Array.set(array, index, value);
        }

        private static void restoreArray(Object array, Object values) {
            if (array == null || values == null || !array.getClass().isArray() || !values.getClass().isArray()) {
                return;
            }
            int length = Math.min(Array.getLength(array), Array.getLength(values));
            for (int i = 0; i < length; i++) {
                Array.set(array, i, Array.get(values, i));
            }
        }

        private ResolvedServerEndpoint resolveServerEndpoint(String host, int port) {
            String normalizedHost = normalizeHost(host);
            int safePort = port > 0 ? port : 19129;
            String cacheKey = normalizedHost.toLowerCase(Locale.ROOT) + ":" + safePort;
            ResolvedServerEndpoint cached = serverEndpointCache.get(cacheKey);
            if (cached != null) {
                return cached;
            }
            ResolvedServerEndpoint resolved = new ResolvedServerEndpoint(normalizedHost, safePort, false);
            if (shouldRouteToLoopback(normalizedHost, safePort)) {
                resolved = new ResolvedServerEndpoint("127.0.0.1", safePort, true);
            }
            serverEndpointCache.put(cacheKey, resolved);
            return resolved;
        }

        private void announceServerEndpoint(int serverIndex, String requestedHost, int requestedPort, ResolvedServerEndpoint endpoint) {
            String safeRequestedHost = normalizeHost(requestedHost);
            String notice = "[launcher] route server[" + serverIndex + "] "
                + safeRequestedHost + ":" + requestedPort
                + " -> " + endpoint.host + ":" + endpoint.port
                + (endpoint.loopbackReroute ? " (local-loopback)" : "");
            if (!notice.equals(lastServerEndpointNotice)) {
                System.out.println(notice);
                lastServerEndpointNotice = notice;
            }
        }

        private static boolean shouldRouteToLoopback(String host, int port) {
            // Keep the client on the exact configured host. Some Grinding builds
            // fail the initial handshake when the launcher silently rewrites the
            // public server IP to loopback on the same machine.
            return false;
        }

        private static boolean matchesLocalAddress(InetAddress[] targetAddresses) throws Exception {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces == null) {
                return false;
            }
            while (interfaces.hasMoreElements()) {
                NetworkInterface network = interfaces.nextElement();
                if (network == null || !network.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = network.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress localAddress = addresses.nextElement();
                    if (localAddress == null) {
                        continue;
                    }
                    for (int i = 0; i < targetAddresses.length; i++) {
                        InetAddress targetAddress = targetAddresses[i];
                        if (targetAddress != null && localAddress.equals(targetAddress)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private static boolean isTcpReachable(String host, int port, int timeoutMs) {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), timeoutMs);
                return true;
            } catch (Exception ignored) {
                return false;
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        private static boolean isLoopbackHost(String host) {
            String normalized = normalizeHost(host).toLowerCase(Locale.ROOT);
            return "127.0.0.1".equals(normalized) || "localhost".equals(normalized) || "::1".equals(normalized);
        }

        private static String normalizeHost(String host) {
            return host == null ? "" : host.trim();
        }

        private static String stringArrayValue(Object array, int index) {
            if (array == null || !array.getClass().isArray()) {
                return "";
            }
            int length = Array.getLength(array);
            if (index < 0 || index >= length) {
                return "";
            }
            Object value = Array.get(array, index);
            return value == null ? "" : String.valueOf(value).trim();
        }

        private static int shortArrayValue(Object array, int index, int defaultValue) {
            if (array == null || !array.getClass().isArray()) {
                return defaultValue;
            }
            int length = Array.getLength(array);
            if (index < 0 || index >= length) {
                return defaultValue;
            }
            Object value = Array.get(array, index);
            if (!(value instanceof Number)) {
                return defaultValue;
            }
            int parsed = ((Number)value).intValue();
            return parsed > 0 ? parsed : defaultValue;
        }

        private static String[] copyStringArray(Object array) {
            if (array == null || !array.getClass().isArray()) {
                return new String[0];
            }
            int length = Array.getLength(array);
            String[] copy = new String[length];
            for (int i = 0; i < length; i++) {
                Object value = Array.get(array, i);
                copy[i] = value == null ? null : String.valueOf(value);
            }
            return copy;
        }

        private static short[] copyShortArray(Object array) {
            if (array == null || !array.getClass().isArray()) {
                return new short[0];
            }
            int length = Array.getLength(array);
            short[] copy = new short[length];
            for (int i = 0; i < length; i++) {
                copy[i] = ((Number)Array.get(array, i)).shortValue();
            }
            return copy;
        }

        private static int toInt(Object value) {
            if (value instanceof Number) {
                return ((Number)value).intValue();
            }
            return Integer.parseInt(String.valueOf(value).trim());
        }

        private static final class ResolvedServerEndpoint {
            private final String host;
            private final int port;
            private final boolean loopbackReroute;

            private ResolvedServerEndpoint(String host, int port, boolean loopbackReroute) {
                this.host = normalizeHost(host);
                this.port = port > 0 ? port : 19129;
                this.loopbackReroute = loopbackReroute;
            }
        }

        private static Field publicField(Class<?> type, String name) throws Exception {
            Field field = type.getField(name);
            field.setAccessible(true);
            return field;
        }

        private static Field declaredField(Class<?> type, String name) throws Exception {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }

        private static Method publicMethod(Class<?> type, String name, Class<?>... parameterTypes) throws Exception {
            Method method = type.getMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        }

        private static Method declaredMethod(Class<?> type, String name, Class<?>... parameterTypes) throws Exception {
            Method method = type.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        }

        private static Constructor<?> declaredConstructor(Class<?> type, Class<?>... parameterTypes) throws Exception {
            Constructor<?> constructor = type.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        }
    }

    private static final class AutoConfig {
        private static final int TEAM_MODE_DISABLED = 0;
        private static final int TEAM_MODE_LEADER = 1;
        private static final int TEAM_MODE_FOLLOWER = 2;
        private boolean enabled = true;
        private String username = "";
        private String password = "";
        private String machineLoginSource = "";
        private String profileName = "";
        private boolean loginSettingsLocked;
        private int serverIndex = 0;
        private int characterIndex = 0;
        private int characterClass = 0;
        private int autoMode = 2;
        private int focusMode = 0;
        private int teamMode = TEAM_MODE_DISABLED;
        private String leaderName = "";
        private String teamMembers = "";
        private String attackSkills = "";
        private String buffSkills = "";
        private int followDistance = 56;
        private int resourceMode = 0;
        private int hpPercent = 50;
        private int mpPercent = 50;
        private long pollIntervalMs = 1000L;
        private long reconnectDelayMs = 5000L;
        private String customHost = "";
        private int customPort = 19129;
        private String customServerName = "";
        private boolean autoParty;
        private boolean autoComeHome;
        private boolean autoTravelByLevel = true;
        private boolean autoVanTieu;
        private boolean autoSellFarmGear = true;
        private boolean autoRepairGear = true;
        private int repairDurabilityThreshold = 5;
        private boolean reloadWhenNoDamage = true;
        private int noDamageTimeoutSeconds = 4;
        private boolean autoUpgradeSkill;
        private String upgradeSkills = "";
        private int skillUpgradeIntervalSeconds = 5;
        private boolean autoUpgradePotential;
        private int potentialTargetIndex = -1;
        private int potentialPointsPerAdd = 1;
        private int potentialIntervalSeconds = 5;
        private boolean autoUseInventoryItems;
        private String inventoryUseItems = "";
        private int inventoryItemIntervalSeconds = 30;
        private boolean pickupPotion;
        private boolean pickupEquipment;
        private boolean pickupMaterial;
        private boolean pickupAll;

        private static AutoConfig load(Path path) throws IOException {
            AutoConfig config = new AutoConfig();
            Properties properties = new Properties();
            if (Files.exists(path)) {
                InputStream input = Files.newInputStream(path);
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            }

            config.enabled = parseBoolean(properties, "enabled", config.enabled);
            config.username = properties.getProperty("login_username", "").trim();
            config.password = properties.getProperty("login_password", "");
            config.profileName = properties.getProperty("profile_name", "").trim();
            config.loginSettingsLocked = parseBoolean(properties, "login_settings_locked", config.loginSettingsLocked);
            config.serverIndex = parseInt(properties, "server_index", config.serverIndex);
            config.characterIndex = parseInt(properties, "character_index", config.characterIndex);
            config.characterClass = parseInt(properties, "character_class", config.characterClass);
            config.autoMode = parseInt(properties, "auto_mode", config.autoMode);
            config.focusMode = parseInt(properties, "focus_mode", config.focusMode);
            config.teamMode = parseInt(properties, "team_mode", config.teamMode);
            config.leaderName = properties.getProperty("leader_name", "").trim();
            config.teamMembers = properties.getProperty("team_members", "").trim();
            config.attackSkills = properties.getProperty("attack_skills", "").trim();
            config.buffSkills = properties.getProperty("buff_skills", "").trim();
            config.followDistance = parseInt(properties, "follow_distance", config.followDistance);
            config.resourceMode = parseInt(properties, "resource_mode", config.resourceMode);
            config.hpPercent = parseInt(properties, "hp_percent", config.hpPercent);
            config.mpPercent = parseInt(properties, "mp_percent", config.mpPercent);
            config.pollIntervalMs = parseLong(properties, "poll_interval_ms", config.pollIntervalMs);
            config.reconnectDelayMs = parseLong(properties, "reconnect_delay_ms", config.reconnectDelayMs);
            config.customHost = properties.getProperty("custom_host", "").trim();
            config.customPort = parseInt(properties, "custom_port", config.customPort);
            config.customServerName = properties.getProperty("custom_server_name", "").trim();
            config.autoParty = parseBoolean(properties, "auto_party", config.autoParty);
            config.autoComeHome = parseBoolean(properties, "auto_come_home", config.autoComeHome);
            config.autoTravelByLevel = parseBoolean(properties, "auto_travel_by_level", config.autoTravelByLevel);
            config.autoVanTieu = parseBoolean(properties, "auto_van_tieu", config.autoVanTieu);
            config.autoSellFarmGear = parseBoolean(properties, "auto_sell_farm_gear", config.autoSellFarmGear);
            config.autoRepairGear = parseBoolean(properties, "auto_repair_gear", config.autoRepairGear);
            config.repairDurabilityThreshold = parseInt(properties, "repair_durability_threshold", config.repairDurabilityThreshold);
            config.reloadWhenNoDamage = parseBoolean(properties, "reload_when_no_damage", config.reloadWhenNoDamage);
            config.noDamageTimeoutSeconds = parseInt(properties, "no_damage_timeout_seconds", config.noDamageTimeoutSeconds);
            config.autoUpgradeSkill = parseBoolean(properties, "auto_upgrade_skill", config.autoUpgradeSkill);
            config.upgradeSkills = properties.getProperty("upgrade_skills", "").trim();
            config.skillUpgradeIntervalSeconds = parseInt(properties, "skill_upgrade_interval_seconds", config.skillUpgradeIntervalSeconds);
            config.autoUpgradePotential = parseBoolean(properties, "auto_upgrade_potential", config.autoUpgradePotential);
            config.potentialTargetIndex = parseInt(properties, "potential_target_index", config.potentialTargetIndex);
            config.potentialPointsPerAdd = parseInt(properties, "potential_points_per_add", config.potentialPointsPerAdd);
            config.potentialIntervalSeconds = parseInt(properties, "potential_interval_seconds", config.potentialIntervalSeconds);
            config.autoUseInventoryItems = parseBoolean(properties, "auto_use_inventory_items", config.autoUseInventoryItems);
            config.inventoryUseItems = properties.getProperty("inventory_use_items", "").trim();
            config.inventoryItemIntervalSeconds = parseInt(properties, "inventory_item_interval_seconds", config.inventoryItemIntervalSeconds);
            config.pickupPotion = parseBoolean(properties, "pickup_potion", config.pickupPotion);
            config.pickupEquipment = parseBoolean(properties, "pickup_equipment", config.pickupEquipment);
            config.pickupMaterial = parseBoolean(properties, "pickup_material", config.pickupMaterial);
            config.pickupAll = parseBoolean(properties, "pickup_all", config.pickupAll);
            config.normalize();

            if (!Files.exists(path)) {
                config.store(path);
            }
            return config;
        }

        private void store(Path path) throws IOException {
            Properties properties = new Properties();
            properties.setProperty("enabled", Boolean.toString(enabled));
            properties.setProperty("login_username", username);
            properties.setProperty("login_password", password);
            properties.setProperty("profile_name", profileName);
            properties.setProperty("login_settings_locked", Boolean.toString(loginSettingsLocked));
            properties.setProperty("server_index", Integer.toString(serverIndex));
            properties.setProperty("character_index", Integer.toString(characterIndex));
            properties.setProperty("character_class", Integer.toString(characterClass));
            properties.setProperty("auto_mode", Integer.toString(autoMode));
            properties.setProperty("focus_mode", Integer.toString(focusMode));
            properties.setProperty("team_mode", Integer.toString(teamMode));
            properties.setProperty("leader_name", leaderName);
            properties.setProperty("team_members", teamMembers);
            properties.setProperty("attack_skills", attackSkills);
            properties.setProperty("buff_skills", buffSkills);
            properties.setProperty("follow_distance", Integer.toString(followDistance));
            properties.setProperty("resource_mode", Integer.toString(resourceMode));
            properties.setProperty("hp_percent", Integer.toString(hpPercent));
            properties.setProperty("mp_percent", Integer.toString(mpPercent));
            properties.setProperty("poll_interval_ms", Long.toString(pollIntervalMs));
            properties.setProperty("reconnect_delay_ms", Long.toString(reconnectDelayMs));
            properties.setProperty("custom_host", customHost);
            properties.setProperty("custom_port", Integer.toString(customPort));
            properties.setProperty("custom_server_name", customServerName);
            properties.setProperty("auto_party", Boolean.toString(autoParty));
            properties.setProperty("auto_come_home", Boolean.toString(autoComeHome));
            properties.setProperty("auto_travel_by_level", Boolean.toString(autoTravelByLevel));
            properties.setProperty("auto_van_tieu", Boolean.toString(autoVanTieu));
            properties.setProperty("auto_sell_farm_gear", Boolean.toString(autoSellFarmGear));
            properties.setProperty("auto_repair_gear", Boolean.toString(autoRepairGear));
            properties.setProperty("repair_durability_threshold", Integer.toString(repairDurabilityThreshold));
            properties.setProperty("reload_when_no_damage", Boolean.toString(reloadWhenNoDamage));
            properties.setProperty("no_damage_timeout_seconds", Integer.toString(noDamageTimeoutSeconds));
            properties.setProperty("auto_upgrade_skill", Boolean.toString(autoUpgradeSkill));
            properties.setProperty("upgrade_skills", upgradeSkills);
            properties.setProperty("skill_upgrade_interval_seconds", Integer.toString(skillUpgradeIntervalSeconds));
            properties.setProperty("auto_upgrade_potential", Boolean.toString(autoUpgradePotential));
            properties.setProperty("potential_target_index", Integer.toString(potentialTargetIndex));
            properties.setProperty("potential_points_per_add", Integer.toString(potentialPointsPerAdd));
            properties.setProperty("potential_interval_seconds", Integer.toString(potentialIntervalSeconds));
            properties.setProperty("auto_use_inventory_items", Boolean.toString(autoUseInventoryItems));
            properties.setProperty("inventory_use_items", inventoryUseItems);
            properties.setProperty("inventory_item_interval_seconds", Integer.toString(inventoryItemIntervalSeconds));
            properties.setProperty("pickup_potion", Boolean.toString(pickupPotion));
            properties.setProperty("pickup_equipment", Boolean.toString(pickupEquipment));
            properties.setProperty("pickup_material", Boolean.toString(pickupMaterial));
            properties.setProperty("pickup_all", Boolean.toString(pickupAll));
            writePropertiesAtomically(path, properties, "KPAH auto config");
        }

        private void copyFrom(AutoConfig other) {
            if (other == null) {
                return;
            }
            enabled = other.enabled;
            username = other.username;
            password = other.password;
            if (other.machineLoginSource.length() > 0 || machineLoginSource.length() == 0) {
                machineLoginSource = other.machineLoginSource;
            }
            profileName = other.profileName;
            loginSettingsLocked = other.loginSettingsLocked;
            serverIndex = other.serverIndex;
            characterIndex = other.characterIndex;
            characterClass = other.characterClass;
            autoMode = other.autoMode;
            focusMode = other.focusMode;
            teamMode = other.teamMode;
            leaderName = other.leaderName;
            teamMembers = other.teamMembers;
            attackSkills = other.attackSkills;
            buffSkills = other.buffSkills;
            followDistance = other.followDistance;
            resourceMode = other.resourceMode;
            hpPercent = other.hpPercent;
            mpPercent = other.mpPercent;
            pollIntervalMs = other.pollIntervalMs;
            reconnectDelayMs = other.reconnectDelayMs;
            customHost = other.customHost;
            customPort = other.customPort;
            customServerName = other.customServerName;
            autoParty = other.autoParty;
            autoComeHome = other.autoComeHome;
            autoTravelByLevel = other.autoTravelByLevel;
            autoVanTieu = other.autoVanTieu;
            autoSellFarmGear = other.autoSellFarmGear;
            autoRepairGear = other.autoRepairGear;
            repairDurabilityThreshold = other.repairDurabilityThreshold;
            reloadWhenNoDamage = other.reloadWhenNoDamage;
            noDamageTimeoutSeconds = other.noDamageTimeoutSeconds;
            autoUpgradeSkill = other.autoUpgradeSkill;
            upgradeSkills = other.upgradeSkills;
            skillUpgradeIntervalSeconds = other.skillUpgradeIntervalSeconds;
            autoUpgradePotential = other.autoUpgradePotential;
            potentialTargetIndex = other.potentialTargetIndex;
            potentialPointsPerAdd = other.potentialPointsPerAdd;
            potentialIntervalSeconds = other.potentialIntervalSeconds;
            autoUseInventoryItems = other.autoUseInventoryItems;
            inventoryUseItems = other.inventoryUseItems;
            inventoryItemIntervalSeconds = other.inventoryItemIntervalSeconds;
            pickupPotion = other.pickupPotion;
            pickupEquipment = other.pickupEquipment;
            pickupMaterial = other.pickupMaterial;
            pickupAll = other.pickupAll;
            normalize();
        }

        private void normalize() {
            username = username == null ? "" : username.trim();
            password = password == null ? "" : password;
            profileName = profileName == null ? "" : profileName.trim();
            if (serverIndex < 0) {
                serverIndex = 0;
            }
            if (characterIndex < 0) {
                characterIndex = 0;
            }
            characterClass = clamp(characterClass, 0, CHARACTER_CLASS_OPTIONS.length - 1);
            if (autoMode < 0 || autoMode > 3) {
                autoMode = 2;
            }
            focusMode = clamp(focusMode, 0, TARGET_MODE_OPTIONS.length - 1);
            teamMode = clamp(teamMode, 0, TEAM_MODE_OPTIONS.length - 1);
            resourceMode = clamp(resourceMode, 0, RESOURCE_MODE_OPTIONS.length - 1);
            leaderName = normalizeNameText(leaderName);
            teamMembers = normalizeListText(teamMembers);
            attackSkills = normalizeSkillText(attackSkills);
            buffSkills = normalizeSkillText(buffSkills);
            upgradeSkills = normalizeSkillText(upgradeSkills);
            followDistance = clamp(followDistance, 16, 256);
            repairDurabilityThreshold = clamp(repairDurabilityThreshold, 1, 50);
            noDamageTimeoutSeconds = clamp(noDamageTimeoutSeconds, 3, 20);
            hpPercent = clamp(hpPercent, 1, 100);
            mpPercent = clamp(mpPercent, 1, 100);
            potentialTargetIndex = clamp(potentialTargetIndex, -1, POTENTIAL_OPTIONS.length - 1);
            potentialPointsPerAdd = clamp(potentialPointsPerAdd, 1, 20);
            skillUpgradeIntervalSeconds = clamp(skillUpgradeIntervalSeconds, 2, 60);
            potentialIntervalSeconds = clamp(potentialIntervalSeconds, 2, 60);
            inventoryUseItems = normalizeItemIdText(inventoryUseItems);
            inventoryItemIntervalSeconds = clamp(inventoryItemIntervalSeconds, 3, 600);
            customHost = customHost == null ? "" : customHost.trim();
            customServerName = customServerName == null ? "" : customServerName.trim();
            customPort = clamp(customPort, 1, 65535);
            if (pickupAll) {
                pickupPotion = true;
                pickupEquipment = true;
                pickupMaterial = true;
            }
            long minPoll = resourceMode == 2 ? 1500L : (resourceMode == 1 ? 1000L : 250L);
            if (pollIntervalMs < minPoll) {
                pollIntervalMs = minPoll;
            }
            if (reconnectDelayMs < 1000L) {
                reconnectDelayMs = 1000L;
            }
        }

        private void applyDetectedLogin(DetectedLogin login) {
            if (login == null) {
                return;
            }
            if (username.length() == 0) {
                username = login.username;
            }
            if (password.length() == 0) {
                password = login.password;
            }
            if (machineLoginSource.length() == 0) {
                machineLoginSource = login.sourcePath;
            }
        }

        private boolean hasCustomServerOverride() {
            return customHost.length() > 0 || customServerName.length() > 0;
        }

        private boolean hasSkillOverride() {
            return attackSkills.length() > 0 || buffSkills.length() > 0;
        }

        private boolean shouldEnableAutoParty() {
            return autoParty || teamMode == TEAM_MODE_FOLLOWER;
        }

        private boolean shouldAutoVanTieu() {
            return autoVanTieu && teamMode == TEAM_MODE_DISABLED;
        }

        private boolean shouldAutoTravelByLevel() {
            return autoTravelByLevel && !autoVanTieu && teamMode == TEAM_MODE_DISABLED;
        }

        private long noDamageTimeoutMs() {
            return (long)clamp(noDamageTimeoutSeconds, 3, 20) * 1000L;
        }

        private String resolveLeaderName() {
            if (leaderName.length() > 0) {
                return leaderName.toLowerCase(Locale.ROOT);
            }
            List<String> members = teamMemberNames();
            if (!members.isEmpty()) {
                return members.get(0);
            }
            return "";
        }

        private int[] parseAttackSkills() {
            return parseSkillList(attackSkills);
        }

        private int[] parseBuffSkills() {
            return parseSkillList(buffSkills);
        }

        private int[] parseUpgradeSkills() {
            return parseSkillList(upgradeSkills);
        }

        private int[] parseInventoryTemplateIds() {
            return parseIdList(inventoryUseItems, 3);
        }

        private List<String> teamMemberNames() {
            ArrayList<String> values = new ArrayList<String>();
            if (teamMembers.length() == 0) {
                return values;
            }
            String[] parts = teamMembers.split("[,;\\n\\r]+");
            for (int i = 0; i < parts.length; i++) {
                String value = parts[i].trim().toLowerCase(Locale.ROOT);
                if (value.length() > 0 && !values.contains(value)) {
                    values.add(value);
                }
            }
            return values;
        }

        private static int[] parseSkillList(String raw) {
            String safe = raw == null ? "" : raw.trim();
            if (safe.length() == 0) {
                return null;
            }
            return parseIdList(safe, 3);
        }

        private static String normalizeNameText(String value) {
            return value == null ? "" : value.trim();
        }

        private static String normalizeListText(String value) {
            if (value == null || value.trim().length() == 0) {
                return "";
            }
            String[] parts = value.split("[,;\\n\\r]+");
            ArrayList<String> values = new ArrayList<String>();
            for (int i = 0; i < parts.length; i++) {
                String item = parts[i].trim();
                if (item.length() > 0 && !values.contains(item)) {
                    values.add(item);
                }
            }
            return String.join(", ", values);
        }

        private static String normalizeSkillText(String value) {
            if (value == null || value.trim().length() == 0) {
                return "";
            }
            int[] skills = parseIdList(value, 3);
            if (skills == null) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < skills.length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(skills[i]);
            }
            return builder.toString();
        }

        private static String normalizeItemIdText(String value) {
            if (value == null || value.trim().length() == 0) {
                return "";
            }
            int[] ids = parseIdList(value, 3);
            StringBuilder builder = new StringBuilder();
            boolean hasValue = false;
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(ids[i]);
                if (ids[i] >= 0) {
                    hasValue = true;
                }
            }
            return hasValue ? builder.toString() : "";
        }

        private static boolean parseBoolean(Properties properties, String key, boolean defaultValue) {
            String value = properties.getProperty(key);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value.trim());
        }

        private static int parseInt(Properties properties, String key, int defaultValue) {
            String value = properties.getProperty(key);
            if (value == null || value.trim().length() == 0) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }

        private static long parseLong(Properties properties, String key, long defaultValue) {
            String value = properties.getProperty(key);
            if (value == null || value.trim().length() == 0) {
                return defaultValue;
            }
            try {
                return Long.parseLong(value.trim());
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }

        private static int clamp(int value, int min, int max) {
            if (value < min) {
                return min;
            }
            if (value > max) {
                return max;
            }
            return value;
        }
    }

    private static final class DetectedLogin {
        private final String username;
        private final String password;
        private final String sourcePath;

        private DetectedLogin(String username, String password, String sourcePath) {
            this.username = username == null ? "" : username;
            this.password = password == null ? "" : password;
            this.sourcePath = sourcePath == null ? "" : sourcePath;
        }
    }

    private static final class LaunchOptions {
        private boolean skipUi;
        private boolean configureOnly;
        private boolean reloadAfterSave;
        private ConfigMode configMode = ConfigMode.ALL;
        private Path configPath;
        private Path dataDir;
        private Path clientJar;
        private Path statusFile;
        private Path commandFile;
        private String profileName = "";
        private String[] emulatorArgs = new String[0];

        private static LaunchOptions parse(String[] args) {
            LaunchOptions options = new LaunchOptions();
            if (args == null || args.length == 0) {
                return options;
            }

            ArrayList<String> emulatorArgs = new ArrayList<String>();
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if ("--no-ui".equalsIgnoreCase(arg)) {
                    options.skipUi = true;
                } else if ("--configure-only".equalsIgnoreCase(arg)) {
                    options.configureOnly = true;
                } else if ("--reload-after-save".equalsIgnoreCase(arg)) {
                    options.reloadAfterSave = true;
                } else if (arg.startsWith("--config-mode=")) {
                    options.configMode = ConfigMode.parse(arg.substring("--config-mode=".length()));
                } else if ("--config-mode".equalsIgnoreCase(arg)) {
                    options.configMode = ConfigMode.parse(readNextValue(args, ++i, "--config-mode"));
                } else if (arg.startsWith("--config=")) {
                    options.configPath = parsePath(arg.substring("--config=".length()));
                } else if ("--config".equalsIgnoreCase(arg)) {
                    options.configPath = parsePath(readNextValue(args, ++i, "--config"));
                } else if (arg.startsWith("--data-dir=")) {
                    options.dataDir = parsePath(arg.substring("--data-dir=".length()));
                } else if ("--data-dir".equalsIgnoreCase(arg)) {
                    options.dataDir = parsePath(readNextValue(args, ++i, "--data-dir"));
                } else if (arg.startsWith("--client=")) {
                    options.clientJar = parsePath(arg.substring("--client=".length()));
                } else if ("--client".equalsIgnoreCase(arg)) {
                    options.clientJar = parsePath(readNextValue(args, ++i, "--client"));
                } else if (arg.startsWith("--status-file=")) {
                    options.statusFile = parsePath(arg.substring("--status-file=".length()));
                } else if ("--status-file".equalsIgnoreCase(arg)) {
                    options.statusFile = parsePath(readNextValue(args, ++i, "--status-file"));
                } else if (arg.startsWith("--command-file=")) {
                    options.commandFile = parsePath(arg.substring("--command-file=".length()));
                } else if ("--command-file".equalsIgnoreCase(arg)) {
                    options.commandFile = parsePath(readNextValue(args, ++i, "--command-file"));
                } else if (arg.startsWith("--profile-name=")) {
                    options.profileName = arg.substring("--profile-name=".length()).trim();
                } else if ("--profile-name".equalsIgnoreCase(arg)) {
                    options.profileName = readNextValue(args, ++i, "--profile-name").trim();
                } else {
                    emulatorArgs.add(arg);
                }
            }
            options.emulatorArgs = emulatorArgs.toArray(new String[emulatorArgs.size()]);
            return options;
        }

        private static String readNextValue(String[] args, int index, String optionName) {
            if (index < 0 || index >= args.length) {
                throw new IllegalArgumentException("Thieu gia tri cho " + optionName);
            }
            return args[index];
        }

        private static Path parsePath(String value) {
            String normalized = value == null ? "" : value.trim();
            if (normalized.length() == 0) {
                return null;
            }
            return Paths.get(normalized).toAbsolutePath().normalize();
        }
    }

    private static Throwable unwrap(Throwable error) {
        Throwable current = error;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
