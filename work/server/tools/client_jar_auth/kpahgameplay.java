import java.util.Vector;

public final class kpahgameplay implements gj {
    private static final int[] HP_TYPES = {1, 2, 3, 21, 22, 93, 94};
    private static final int[] MP_TYPES = {4, 5, 6, 23, 24, 95, 96};

    private static int activePotionType = -1;
    private static long nextUseAt;
    private static int noProgressCount;

    private final int potionType;
    private final s detailCommand;

    public kpahgameplay(int potionType) {
        this.potionType = potionType;
        this.detailCommand = null;
    }

    private kpahgameplay(s detailCommand) {
        this.potionType = -1;
        this.detailCommand = detailCommand;
    }

    public void a() {
        if (this.detailCommand != null) {
            if (acv.u != null) {
                acv.u.a = false;
            }
            if (this.detailCommand.b != null) {
                this.detailCommand.b.a();
            }
            return;
        }
        begin(this.potionType);
    }

    public static boolean a(int keyCode) {
        if (keyCode == 85 || keyCode == 117) {
            return openPlayerSupportMenu();
        }
        if (keyCode != -5 && keyCode != 10) {
            return false;
        }

        // Hộp thông báo cũ đặt nút OK ở phím trái, vì vậy Enter dùng làm phím xác nhận dự phòng.
        if (acv.w != null) {
            if (acv.w.k == null && acv.w.j != null && acv.w.j.b != null) {
                acv.w.j.b.a();
                return true;
            }
            return false;
        }

        if (acv.u != null && acv.u.a) {
            return false;
        }

        if (!(acv.q instanceof nu) || nu.A == null || nu.z < 0 || nu.z >= nu.A.length ||
                nu.A[nu.z] != 0 || ((nu) acv.q).d < 0) {
            return false;
        }

        // Giữ lại lệnh xem thông tin gốc và đưa vào đầu menu thao tác.
        // Enter lần hai sẽ xem được mô tả, thuộc tính như trước khi bổ sung menu bàn phím.
        nu inventory = (nu) acv.q;
        s viewDetail = inventory.k;
        inventory.t();
        if (acv.u != null && acv.u.a && acv.u.b != null && viewDetail != null && viewDetail.b != null) {
            acv.u.b.insertElementAt(new s("Xem chi tiết", new kpahgameplay(viewDetail)), 0);
            acv.u.c = 0;
        }
        return true;
    }

    private static boolean openPlayerSupportMenu() {
        if (acv.w != null || acv.u == null || acv.u.a || acv.s == null || acv.s.t == null || acv.q != acv.s) {
            return false;
        }

        // Gửi lệnh ẩn để server mở menu native; nội dung lệnh không xuất hiện ở khung chat.
        go.a().a("/kpah-tienich");
        return true;
    }

    public static void a(Vector menu, int potionType) {
        if (menu == null || !canUseAll(potionType)) {
            return;
        }
        menu.addElement(new s("Dùng tất cả", new kpahgameplay(potionType)));
    }

    public static void b() {
        keepAutoTrainRunningBehindMenu();
        if (activePotionType < 0 || acv.s == null || acv.s.t == null) {
            return;
        }

        sc mainChar = acv.s.t;
        int potionType = activePotionType;
        if (!hasPotionSlot(mainChar, potionType) || mainChar.br[potionType] <= 0) {
            stop();
            return;
        }

        boolean hpPotion = contains(HP_TYPES, potionType);
        boolean mpPotion = contains(MP_TYPES, potionType);
        if ((hpPotion && mainChar.v >= mainChar.w) || (mpPotion && mainChar.bA >= mainChar.bz)) {
            stop();
            return;
        }

        long now = System.currentTimeMillis();
        if (now < nextUseAt) {
            return;
        }

        int beforeCount = mainChar.br[potionType];
        acv.s.b(potionType);
        int afterCount = mainChar.br[potionType];
        if (afterCount >= beforeCount) {
            noProgressCount++;
            if (noProgressCount >= 3) {
                stop();
                return;
            }
        } else {
            noProgressCount = 0;
        }

        long configuredDelay = getConfiguredDelay(potionType);
        long safeDelay = hpPotion || mpPotion
                ? Math.max(configuredDelay, 100L)
                : Math.max(configuredDelay, 400L);
        nextUseAt = now + safeDelay;

        if (afterCount <= 0) {
            stop();
        }
    }

    private static void keepAutoTrainRunningBehindMenu() {
        if (acv.s == null || acv.w != null || (!abj.az && !abj.aB)) {
            return;
        }
        boolean popupMenuOpen = acv.u != null && acv.u.a;
        if (acv.q == acv.s && !popupMenuOpen) {
            return;
        }

        // Vòng auto gốc nằm trong xử lý phím của màn hình game. Khi mở màn hình/menu khác,
        // chạy riêng vòng đó với toàn bộ phím và thao tác chạm bị che để không điều khiển nhầm nhân vật.
        boolean[] savedPressed = acv.c;
        boolean[] savedReleased = acv.d;
        boolean[] savedHeld = acv.e;
        boolean savedPointerDown = acv.f;
        boolean savedPointerClick = acv.g;
        boolean savedPointerDrag = acv.h;
        boolean savedPointerMove = acv.i;
        try {
            acv.c = new boolean[savedPressed.length];
            acv.d = new boolean[savedReleased.length];
            acv.e = new boolean[savedHeld.length];
            acv.f = false;
            acv.g = false;
            acv.h = false;
            acv.i = false;
            acv.s.c();
        } finally {
            acv.c = savedPressed;
            acv.d = savedReleased;
            acv.e = savedHeld;
            acv.f = savedPointerDown;
            acv.g = savedPointerClick;
            acv.h = savedPointerDrag;
            acv.i = savedPointerMove;
        }
    }

    private static void begin(int potionType) {
        if (!canUseAll(potionType)) {
            return;
        }
        activePotionType = potionType;
        nextUseAt = 0L;
        noProgressCount = 0;
        if (acv.u != null) {
            acv.u.a = false;
        }
    }

    private static boolean canUseAll(int potionType) {
        sc mainChar = acv.s == null ? null : acv.s.t;
        if (!hasPotionSlot(mainChar, potionType) || mainChar.br[potionType] <= 1) {
            return false;
        }

        // Các loại 10-20 có luồng đặc biệt và không trừ số lượng ngay ở client.
        return potionType < 10 || potionType > 20;
    }

    private static boolean hasPotionSlot(sc mainChar, int potionType) {
        return mainChar != null && mainChar.br != null &&
                potionType >= 0 && potionType < mainChar.br.length;
    }

    private static long getConfiguredDelay(int potionType) {
        if (sc.l == null || potionType < 0 || potionType >= sc.l.length || sc.l[potionType] == null) {
            return 0L;
        }
        return sc.l[potionType].c;
    }

    private static boolean contains(int[] values, int value) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                return true;
            }
        }
        return false;
    }

    private static void stop() {
        activePotionType = -1;
        nextUseAt = 0L;
        noProgressCount = 0;
    }
}
