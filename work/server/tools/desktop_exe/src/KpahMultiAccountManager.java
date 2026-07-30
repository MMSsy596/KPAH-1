import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class KpahMultiAccountManager {
    private static final String APP_NAME = "KPAH Multi Account Manager";
    private static final String LAUNCHER_JAR_NAME = "kpah-launcher.jar";
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
    private static final String PROFILE_ROOT_DIR = "profiles";
    private static final String CONFIG_FILE = "kpah-auto.properties";
    private static final String STATUS_FILE = "status.properties";
    private static final String COMMAND_FILE = "command.properties";
    private static final String RUNTIME_DIR = "runtime";
    private static final String LOG_FILE = "launcher.log";
    private static final String ERROR_LOG_FILE = "launcher-error.log";
    private static final String HOTKEYS_FILE = "manager-hotkeys.properties";
    private static final long STATUS_STALE_MS = 15000L;
    private static final Color APP_BACKGROUND = new Color(242, 245, 250);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    private static final Color HERO_BACKGROUND = new Color(31, 59, 96);
    private static final Color HERO_MUTED = new Color(221, 230, 241);
    private static final Color BORDER_COLOR = new Color(218, 225, 235);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 58);
    private static final Color TEXT_MUTED = new Color(95, 108, 128);
    private static final Color ACCENT_GREEN = new Color(35, 132, 89);
    private static final Color ACCENT_ORANGE = new Color(214, 124, 36);
    private static final Color ACCENT_RED = new Color(197, 64, 64);
    private static final Color ACCENT_BLUE = new Color(53, 104, 186);

    private final Path appDir;
    private final Path launcherJar;
    private final Path clientJar;
    private final Path profileRoot;
    private final Path hotkeyFile;
    private final JFrame frame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JLabel footerLabel;
    private final JLabel totalProfilesLabel;
    private final JLabel runningProfilesLabel;
    private final JLabel connectedProfilesLabel;
    private final JLabel autoOffProfilesLabel;
    private final JTextArea detailArea;
    private KpahLicenseSupport.StoredLicenseStatus licenseStatus;
    private final ArrayList<String> rowProfileIds = new ArrayList<String>();
    private final LinkedHashMap<String, ManagedSession> sessions = new LinkedHashMap<String, ManagedSession>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Properties hotkeyProperties;
    private Timer refreshTimer;
    private long nextLicenseHeartbeatAt;
    private boolean closingForLicense;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Path appDir = resolveAppDir();
                    if (KpahLicenseSupport.ensureInteractiveLicense(appDir, APP_NAME, null) == null) {
                        return;
                    }
                    new KpahMultiAccountManager(appDir).show();
                } catch (Throwable error) {
                    error.printStackTrace();
                    showError(null, error);
                }
            }
        });
    }

    private KpahMultiAccountManager(Path appDir) throws Exception {
        this.appDir = appDir;
        this.launcherJar = appDir.resolve(LAUNCHER_JAR_NAME);
        this.clientJar = findClientJar(appDir);
        this.profileRoot = appDir.resolve(PROFILE_ROOT_DIR);
        this.hotkeyFile = appDir.resolve(HOTKEYS_FILE);
        Files.createDirectories(profileRoot);
        this.hotkeyProperties = loadHotkeys();
        this.licenseStatus = KpahLicenseSupport.readStatus(appDir);

        this.frame = new JFrame(APP_NAME);
        this.tableModel = new DefaultTableModel(
            new Object[]{
                "H\u1ed3 s\u01a1",
                "T\u00e0i kho\u1ea3n",
                "Tr\u1ea1ng th\u00e1i",
                "M\u00e0n h\u00ecnh",
                "Auto",
                "K\u1ebft n\u1ed1i",
                "PID",
                "C\u1eadp nh\u1eadt",
                "Ghi ch\u00fa"
            },
            0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        this.footerLabel = new JLabel();
        this.totalProfilesLabel = new JLabel("0");
        this.runningProfilesLabel = new JLabel("0");
        this.connectedProfilesLabel = new JLabel("0");
        this.autoOffProfilesLabel = new JLabel("0");
        this.detailArea = new JTextArea();

        buildUi();
        reloadProfiles();
        refreshStatuses();
    }

    private void show() {
        refreshTimer = new Timer(1500, event -> refreshStatusesSafely());
        refreshTimer.start();
        frame.setVisible(true);
    }

    private void buildUi() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(680, 450));
        frame.setSize(760, 520);
        frame.setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(3, 3));
        root.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        root.setBackground(APP_BACKGROUND);
        frame.setContentPane(root);

        root.add(buildSimpleTopBar(), BorderLayout.NORTH);
        root.add(buildSimpleCenterPanel(), BorderLayout.CENTER);
        root.add(buildSimpleBottomTabs(), BorderLayout.SOUTH);

        applyTableTheme();
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                updateSelectionDetails();
            }
        });
        updateFooter();
        installHotkeys();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent event) {
                if (refreshTimer != null) {
                    refreshTimer.stop();
                }
            }
        });
    }

    private JButton button(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        button.setHorizontalAlignment(JButton.LEFT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        button.setPreferredSize(new Dimension(110, 24));
        return button;
    }

    private JPanel buildSimpleTopBar() {
        JPanel panel = new JPanel(new BorderLayout(6, 2));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        JLabel title = new JLabel("KPAH Auto Portable");
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));
        title.setForeground(Color.BLACK);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
        stats.setOpaque(false);
        stats.add(createPlainStat("T\u1ed5ng", totalProfilesLabel));
        stats.add(createPlainStat("Ch\u1ea1y", runningProfilesLabel));
        stats.add(createPlainStat("K\u1ebft n\u1ed1i", connectedProfilesLabel));
        stats.add(createPlainStat("T\u1eaft auto", autoOffProfilesLabel));

        panel.add(title, BorderLayout.WEST);
        panel.add(stats, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildSimpleCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        JLabel label = new JLabel("Danh s\u00e1ch h\u1ed3 s\u01a1");
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        panel.add(label, BorderLayout.NORTH);

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(85);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(75);
        table.getColumnModel().getColumn(5).setPreferredWidth(65);
        table.getColumnModel().getColumn(6).setPreferredWidth(55);
        table.getColumnModel().getColumn(7).setPreferredWidth(110);
        table.getColumnModel().getColumn(8).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JTabbedPane buildSimpleBottomTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.setPreferredSize(new Dimension(0, 118));

        tabs.addTab("H\u1ed3 s\u01a1", wrapTabScroll(createSimpleButtonPanel(
            button("Th\u00eam h\u1ed3 s\u01a1", event -> createProfile()),
            button("S\u1eeda \u0111\u0103ng nh\u1eadp", event -> configureSelectedProfileLogin()),
            button("S\u1eeda auto", event -> configureSelectedProfile()),
            button("M\u1edf th\u01b0 m\u1ee5c", event -> openSelectedFolder()),
            button("X\u00f3a h\u1ed3 s\u01a1", event -> deleteSelectedProfile()),
            button("M\u1edf ph\u00edm t\u1eaft", event -> openHotkeyFile()),
            button("Nh\u1eadp m\u00e3", event -> renewLicense())
        )));
        tabs.addTab("\u0110i\u1ec1u khi\u1ec3n", wrapTabScroll(createSimpleButtonPanel(
            button("B\u1eaft \u0111\u1ea7u", event -> startSelectedProfile()),
            button("D\u1eebng", event -> stopSelectedProfile()),
            button("Kh\u1edfi \u0111\u1ed9ng l\u1ea1i", event -> restartSelectedProfile()),
            button("B\u1eadt auto", event -> sendCommandToSelection("resume_auto")),
            button("T\u1eaft auto", event -> sendCommandToSelection("pause_auto")),
            button("L\u00e0m m\u1edbi", event -> refreshStatusesSafely())
        )));
        tabs.addTab("B\u1ea3o tr\u00ec", wrapTabScroll(createSimpleButtonPanel(
            button("V\u1ec1 l\u00e0ng", event -> sendCommandToSelection("return_home")),
            button("S\u1eeda \u0111\u1ed3", event -> sendCommandToSelection("repair")),
            button("S\u1eeda \u0111\u1ed3 l\u1eb7p", event -> sendCommandToSelection("repair_cycle")),
            button("B\u00e1n \u0111\u1ed3", event -> sendCommandToSelection("sell_items")),
            button("Ch\u1ea1y t\u1ea5t c\u1ea3", event -> startAllProfiles()),
            button("D\u1eebng t\u1ea5t c\u1ea3", event -> stopAllProfiles())
        )));

        JTextArea detailText = detailArea;
        detailText.setEditable(false);
        detailText.setLineWrap(true);
        detailText.setWrapStyleWord(true);
        detailText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        detailText.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        detailText.setBackground(Color.WHITE);
        tabs.addTab("Chi ti\u1ebft", wrapTextArea(detailText));

        JTextArea note = new JTextArea(
            "Giao di\u1ec7n \u0111\u00e3 thu g\u1ecdn v\u1ec1 d\u1ea1ng tab nh\u1ecf, d\u1ec5 d\u00f9ng.\n\n" +
            "S\u1eeda auto khi h\u1ed3 s\u01a1 \u0111ang ch\u1ea1y v\u1eabn ho\u1ea1t \u0111\u1ed9ng: sau khi l\u01b0u, launcher s\u1ebd n\u1ea1p l\u1ea1i c\u1ea5u h\u00ecnh auto v\u00e0o phi\u00ean hi\u1ec7n t\u1ea1i.\n\n" +
            "Ph\u00edm t\u1eaft: Ctrl+N t\u1ea1o h\u1ed3 s\u01a1, Ctrl+L \u0111\u0103ng nh\u1eadp, Ctrl+E auto, Ctrl+S b\u1eaft \u0111\u1ea7u, Ctrl+X d\u1eebng, Ctrl+R kh\u1edfi \u0111\u1ed9ng l\u1ea1i, Ctrl+P t\u1eaft auto, Ctrl+U b\u1eadt auto, F5 l\u00e0m m\u1edbi."
        );
        note.setEditable(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        note.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        note.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel(new BorderLayout(0, 4));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(wrapTextArea(note), BorderLayout.CENTER);
        footerLabel.setForeground(Color.DARK_GRAY);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        infoPanel.add(footerLabel, BorderLayout.SOUTH);
        tabs.addTab("H\u01b0\u1edbng d\u1eabn", infoPanel);
        return tabs;
    }

    private JPanel createSimpleButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new GridLayout(0, 3, 4, 4));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        for (int i = 0; i < buttons.length; i++) {
            panel.add(buttons[i]);
        }
        return panel;
    }

    private JPanel createPlainStat(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel(title + ":");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        valueLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        panel.add(valueLabel);
        return panel;
    }

    private JScrollPane wrapTabScroll(JPanel panel) {
        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(Color.WHITE);
        holder.add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(holder);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        return scrollPane;
    }

    private JScrollPane wrapTextArea(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        return scrollPane;
    }

    private JPanel buildCompactTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JLabel title = new JLabel("KPAH Auto Portable");
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel path = new JLabel("App: " + appDir);
        path.setForeground(TEXT_MUTED);
        path.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JPanel left = new JPanel(new BorderLayout(0, 2));
        left.setOpaque(false);
        left.add(title, BorderLayout.NORTH);
        left.add(path, BorderLayout.CENTER);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        stats.setOpaque(false);
        stats.add(createMiniStat("Tong", totalProfilesLabel, ACCENT_BLUE));
        stats.add(createMiniStat("Chay", runningProfilesLabel, ACCENT_GREEN));
        stats.add(createMiniStat("Ket noi", connectedProfilesLabel, ACCENT_ORANGE));
        stats.add(createMiniStat("Tat auto", autoOffProfilesLabel, ACCENT_RED));

        panel.add(left, BorderLayout.CENTER);
        panel.add(stats, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildCompactContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setOpaque(false);
        panel.add(buildCompactTablePanel(), BorderLayout.CENTER);
        panel.add(buildCompactBottomPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildCompactTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        JLabel label = new JLabel("Danh sach profile");
        label.setForeground(TEXT_PRIMARY);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.add(label, BorderLayout.NORTH);

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(65);
        table.getColumnModel().getColumn(6).setPreferredWidth(55);
        table.getColumnModel().getColumn(7).setPreferredWidth(120);
        table.getColumnModel().getColumn(8).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JSplitPane buildCompactBottomPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildControlTabs(), buildInfoTabs());
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setOpaque(false);
        splitPane.setDividerSize(8);
        splitPane.setResizeWeight(0.46d);
        splitPane.setDividerLocation(390);
        splitPane.setPreferredSize(new Dimension(0, 220));
        return splitPane;
    }

    private JTabbedPane buildControlTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabs.addTab(
            "Profile",
            createCompactActionPanel(
                button("Them profile", event -> createProfile()),
                button("Sua dang nhap", event -> configureSelectedProfileLogin()),
                button("Sua auto", event -> configureSelectedProfile()),
                button("Mo thu muc", event -> openSelectedFolder()),
                button("Xoa profile", event -> deleteSelectedProfile()),
                button("Mo hotkeys", event -> openHotkeyFile())
            )
        );
        tabs.addTab(
            "Dieu khien",
            createCompactActionPanel(
                button("Start", event -> startSelectedProfile()),
                button("Stop", event -> stopSelectedProfile()),
                button("Restart", event -> restartSelectedProfile()),
                button("Bat auto", event -> sendCommandToSelection("resume_auto")),
                button("Tat auto", event -> sendCommandToSelection("pause_auto")),
                button("Refresh", event -> refreshStatusesSafely())
            )
        );
        tabs.addTab(
            "Bao tri",
            createCompactActionPanel(
                button("Ve lang", event -> sendCommandToSelection("return_home")),
                button("Sua do", event -> sendCommandToSelection("repair")),
                button("Sua do cycle", event -> sendCommandToSelection("repair_cycle")),
                button("Ban do", event -> sendCommandToSelection("sell_items"))
            )
        );
        tabs.addTab(
            "Hang loat",
            createCompactActionPanel(
                button("Start all", event -> startAllProfiles()),
                button("Stop all", event -> stopAllProfiles())
            )
        );
        return tabs;
    }

    private JTabbedPane buildInfoTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setOpaque(true);
        detailArea.setBackground(CARD_BACKGROUND);
        detailArea.setForeground(TEXT_PRIMARY);
        detailArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        JScrollPane detailScroll = new JScrollPane(detailArea);
        detailScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        tabs.addTab("Chi tiet", detailScroll);

        JTextArea note = new JTextArea(
            "Giao dien nay da thu gon theo kieu co dien de nhin het tren man hinh.\n\n" +
            "Sua auto khi profile dang chay da duoc giu lai: sau khi luu, launcher se nap lai cau hinh auto vao phien hien tai.\n\n" +
            "Phim tat: Ctrl+N tao profile, Ctrl+L dang nhap, Ctrl+E auto, Ctrl+S start, Ctrl+X stop, Ctrl+R restart, Ctrl+P tat auto, Ctrl+U bat auto, F5 refresh."
        );
        note.setEditable(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setOpaque(false);
        note.setForeground(TEXT_MUTED);
        note.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(TEXT_MUTED);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JPanel notePanel = new JPanel(new BorderLayout(0, 6));
        notePanel.setBackground(CARD_BACKGROUND);
        notePanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        notePanel.add(note, BorderLayout.CENTER);
        notePanel.add(footerLabel, BorderLayout.SOUTH);
        tabs.addTab("Huong dan", notePanel);
        return tabs;
    }

    private JPanel createCompactActionPanel(JButton... buttons) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 4, 4));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        for (int i = 0; i < buttons.length; i++) {
            panel.add(buttons[i]);
        }
        return panel;
    }

    private JPanel createMiniStat(String title, JLabel valueLabel, Color accent) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(new Color(247, 249, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        JLabel titleLabel = new JLabel(title + ": ");
        titleLabel.setForeground(TEXT_MUTED);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        valueLabel.setForeground(accent);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JPanel inner = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        inner.setOpaque(false);
        inner.add(titleLabel);
        inner.add(valueLabel);
        panel.add(inner, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        JPanel hero = new JPanel(new BorderLayout(16, 10));
        hero.setBackground(HERO_BACKGROUND);
        hero.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("KPAH Auto Portable");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JLabel subtitle = new JLabel("Quan ly profile, giam sat trang thai va dieu khien auto trong mot cua so gon gang.");
        subtitle.setForeground(HERO_MUTED);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel titleBlock = new JPanel(new BorderLayout(0, 6));
        titleBlock.setOpaque(false);
        titleBlock.add(title, BorderLayout.NORTH);
        titleBlock.add(subtitle, BorderLayout.CENTER);

        JLabel appPath = new JLabel("App: " + appDir);
        appPath.setForeground(HERO_MUTED);
        appPath.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hero.add(titleBlock, BorderLayout.CENTER);
        hero.add(appPath, BorderLayout.SOUTH);

        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 12));
        stats.setOpaque(false);
        stats.add(createStatCard("Tong profile", totalProfilesLabel, ACCENT_BLUE));
        stats.add(createStatCard("Dang chay", runningProfilesLabel, ACCENT_GREEN));
        stats.add(createStatCard("Da ket noi", connectedProfilesLabel, ACCENT_ORANGE));
        stats.add(createStatCard("Tat auto", autoOffProfilesLabel, ACCENT_RED));

        panel.add(hero, BorderLayout.NORTH);
        panel.add(stats, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(createActionSection(
            "Profile",
            button("Them profile", event -> createProfile()),
            button("Sua dang nhap", event -> configureSelectedProfileLogin()),
            button("Sua auto", event -> configureSelectedProfile()),
            button("Mo thu muc", event -> openSelectedFolder()),
            button("Xoa profile", event -> deleteSelectedProfile())
        ));
        sidebar.add(createActionSection(
            "Dieu khien",
            button("Start", event -> startSelectedProfile()),
            button("Stop", event -> stopSelectedProfile()),
            button("Restart", event -> restartSelectedProfile()),
            button("Bat auto", event -> sendCommandToSelection("resume_auto")),
            button("Tat auto", event -> sendCommandToSelection("pause_auto")),
            button("Refresh", event -> refreshStatusesSafely())
        ));
        sidebar.add(createActionSection(
            "Bao tri",
            button("Ve lang", event -> sendCommandToSelection("return_home")),
            button("Sua do", event -> sendCommandToSelection("repair")),
            button("Sua do cycle", event -> sendCommandToSelection("repair_cycle")),
            button("Ban do", event -> sendCommandToSelection("sell_items"))
        ));
        sidebar.add(createActionSection(
            "Hang loat",
            button("Start all", event -> startAllProfiles()),
            button("Stop all", event -> stopAllProfiles()),
            button("Mo hotkeys", event -> openHotkeyFile())
        ));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(sidebar, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel buildMainPanel() {
        JPanel main = new JPanel(new BorderLayout(0, 12));
        main.setOpaque(false);

        JPanel tableCard = createCardPanel("Danh sach profile", "Theo doi state, man hinh hien tai, auto va ghi chu cua tung acc.");
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(190);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(125);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(95);
        table.getColumnModel().getColumn(6).setPreferredWidth(85);
        table.getColumnModel().getColumn(7).setPreferredWidth(170);
        table.getColumnModel().getColumn(8).setPreferredWidth(360);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(1, 2, 12, 12));
        bottom.setOpaque(false);
        bottom.setPreferredSize(new Dimension(0, 250));

        JPanel detailCard = createCardPanel("Chi tiet profile", "Tom tat nhanh acc dang chon va tinh trang auto hien tai.");
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setOpaque(false);
        detailArea.setForeground(TEXT_PRIMARY);
        detailArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detailArea.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        detailCard.add(detailArea, BorderLayout.CENTER);

        JPanel noteCard = createCardPanel("Huong dan nhanh", "Bo cuc moi tap trung vao thao tac profile, dieu khien va bao tri.");
        JTextArea note = new JTextArea(
            "Tool luu toan bo profile ngay trong thu muc app de co the copy sang may khac. " +
            "Sua auto da ho tro khi acc dang chay; sau khi luu, launcher se nap lai cau hinh auto vao phien hien tai.\n\n" +
            "Phim tat mac dinh: Ctrl+N tao profile, Ctrl+L dang nhap, Ctrl+E auto, Ctrl+S start, Ctrl+X stop, Ctrl+R restart, " +
            "Ctrl+P tat auto, Ctrl+U bat auto, Ctrl+H ve lang, Ctrl+B ban do, Ctrl+O mo thu muc, Delete xoa profile, F5 refresh."
        );
        note.setEditable(false);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setOpaque(false);
        note.setForeground(TEXT_MUTED);
        note.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        noteCard.add(note, BorderLayout.CENTER);
        footerLabel.setForeground(TEXT_MUTED);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        noteCard.add(footerLabel, BorderLayout.SOUTH);

        bottom.add(detailCard);
        bottom.add(noteCard);

        main.add(tableCard, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);
        return main;
    }

    private JPanel createCardPanel(String title, String subtitle) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(14, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(subtitleLabel, BorderLayout.CENTER);
        card.add(header, BorderLayout.NORTH);
        return card;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_MUTED);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        valueLabel.setForeground(accent);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createActionSection(String title, JButton... buttons) {
        JPanel card = createCardPanel(title, "Nhan thao tac de gui len profile dang chon.");
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        buttonPanel.setOpaque(false);
        for (int i = 0; i < buttons.length; i++) {
            buttonPanel.add(buttons[i]);
        }
        card.add(buttonPanel, BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        return card;
    }

    private void applyTableTheme() {
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(210, 210, 210));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(204, 221, 245));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void installHotkeys() {
        JComponent rootPane = frame.getRootPane();
        registerHotkey(rootPane, "create_profile", () -> createProfile());
        registerHotkey(rootPane, "configure_login", () -> configureSelectedProfileLogin());
        registerHotkey(rootPane, "configure_profile", () -> configureSelectedProfile());
        registerHotkey(rootPane, "start_profile", () -> startSelectedProfile());
        registerHotkey(rootPane, "stop_profile", () -> stopSelectedProfile());
        registerHotkey(rootPane, "restart_profile", () -> restartSelectedProfile());
        registerHotkey(rootPane, "pause_auto", () -> sendCommandToSelection("pause_auto"));
        registerHotkey(rootPane, "resume_auto", () -> sendCommandToSelection("resume_auto"));
        registerHotkey(rootPane, "return_home", () -> sendCommandToSelection("return_home"));
        registerHotkey(rootPane, "sell_items", () -> sendCommandToSelection("sell_items"));
        registerHotkey(rootPane, "start_all", () -> startAllProfiles());
        registerHotkey(rootPane, "stop_all", () -> stopAllProfiles());
        registerHotkey(rootPane, "open_folder", () -> openSelectedFolder());
        registerHotkey(rootPane, "delete_profile", () -> deleteSelectedProfile());
        registerHotkey(rootPane, "open_hotkeys", () -> openHotkeyFile());
        registerHotkey(rootPane, "refresh", () -> refreshStatusesSafely());
    }

    private void registerHotkey(JComponent rootPane, String key, Runnable action) {
        String strokeText = hotkeyProperties.getProperty(key, "").trim();
        if (strokeText.length() == 0) {
            return;
        }
        KeyStroke keyStroke = KeyStroke.getKeyStroke(strokeText);
        if (keyStroke == null) {
            return;
        }
        String actionKey = "hotkey." + key;
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
        rootPane.getActionMap().put(actionKey, new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                action.run();
            }
        });
    }

    private Properties loadHotkeys() throws IOException {
        Properties properties = new Properties();
        LinkedHashMap<String, String> defaults = defaultHotkeys();
        if (Files.isRegularFile(hotkeyFile)) {
            InputStream input = Files.newInputStream(hotkeyFile);
            try {
                properties.load(input);
            } finally {
                closeQuietly(input);
            }
        }

        boolean changed = false;
        for (java.util.Map.Entry<String, String> entry : defaults.entrySet()) {
            String current = properties.getProperty(entry.getKey(), "").trim();
            if (current.length() == 0) {
                properties.setProperty(entry.getKey(), entry.getValue());
                changed = true;
            }
        }
        if (!Files.exists(hotkeyFile) || changed) {
            writePropertiesAtomically(hotkeyFile, properties, "KPAH manager hotkeys");
        }
        return properties;
    }

    private LinkedHashMap<String, String> defaultHotkeys() {
        LinkedHashMap<String, String> defaults = new LinkedHashMap<String, String>();
        defaults.put("create_profile", "ctrl N");
        defaults.put("configure_login", "ctrl L");
        defaults.put("configure_profile", "ctrl E");
        defaults.put("start_profile", "ctrl S");
        defaults.put("stop_profile", "ctrl X");
        defaults.put("restart_profile", "ctrl R");
        defaults.put("pause_auto", "ctrl P");
        defaults.put("resume_auto", "ctrl U");
        defaults.put("return_home", "ctrl H");
        defaults.put("sell_items", "ctrl B");
        defaults.put("start_all", "ctrl shift A");
        defaults.put("stop_all", "ctrl shift X");
        defaults.put("open_folder", "ctrl O");
        defaults.put("delete_profile", "DELETE");
        defaults.put("open_hotkeys", "ctrl K");
        defaults.put("refresh", "F5");
        return defaults;
    }

    private void createProfile() {
        String rawName = JOptionPane.showInputDialog(
            frame,
            "Nh\u1eadp t\u00ean h\u1ed3 s\u01a1",
            "T\u1ea1o h\u1ed3 s\u01a1",
            JOptionPane.PLAIN_MESSAGE
        );
        if (rawName == null) {
            return;
        }
        String displayName = rawName.trim();
        if (displayName.length() == 0) {
            showMessage("T\u00ean h\u1ed3 s\u01a1 kh\u00f4ng \u0111\u01b0\u1ee3c \u0111\u1ec3 tr\u1ed1ng.");
            return;
        }

        String profileId = uniqueProfileId(displayName);
        ProfilePaths profile = new ProfilePaths(profileId, profileRoot.resolve(profileId));
        try {
            Files.createDirectories(profile.dir);
            Files.createDirectories(profile.dataDir);
            reloadProfiles();
            refreshStatuses();
            selectProfile(profile.id);
            launchLauncher(profile, true, displayName, "login");
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void configureSelectedProfileLogin() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        if (session.isRunning()) {
            showMessage("H\u00e3y d\u1eebng h\u1ed3 s\u01a1 tr\u01b0\u1edbc khi s\u1eeda \u0111\u0103ng nh\u1eadp.");
            return;
        }
        try {
            launchLauncher(session.profile, true, session.displayProfileName(), "login");
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void configureSelectedProfile() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        try {
            launchLauncher(session.profile, true, session.displayProfileName(), "auto", session.isRunning());
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void startSelectedProfile() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        try {
            startProfile(session);
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void stopSelectedProfile() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        try {
            sendCommand(session, "stop");
            refreshStatuses();
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void restartSelectedProfile() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        restartProfile(session);
    }

    private void sendCommandToSelection(String command) {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        try {
            sendCommand(session, command);
            refreshStatuses();
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void startAllProfiles() {
        ArrayList<ManagedSession> values = new ArrayList<ManagedSession>(sessions.values());
        for (int i = 0; i < values.size(); i++) {
            try {
                startProfile(values.get(i));
            } catch (Exception error) {
                showError(frame, error);
                return;
            }
        }
        refreshStatusesSafely();
    }

    private void stopAllProfiles() {
        ArrayList<ManagedSession> values = new ArrayList<ManagedSession>(sessions.values());
        for (int i = 0; i < values.size(); i++) {
            try {
                sendCommand(values.get(i), "stop");
            } catch (Exception error) {
                showError(frame, error);
                return;
            }
        }
        refreshStatusesSafely();
    }

    private void openSelectedFolder() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        try {
            if (!Desktop.isDesktopSupported()) {
                throw new IllegalStateException("Desktop API kh\u00f4ng h\u1ed7 tr\u1ee3 m\u1edf th\u01b0 m\u1ee5c tr\u00ean m\u00e1y n\u00e0y.");
            }
            Desktop.getDesktop().open(session.profile.dir.toFile());
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void openHotkeyFile() {
        try {
            if (!Desktop.isDesktopSupported()) {
                throw new IllegalStateException("Desktop API kh\u00f4ng h\u1ed7 tr\u1ee3 m\u1edf file ph\u00edm t\u1eaft tr\u00ean m\u00e1y n\u00e0y.");
            }
            Desktop.getDesktop().open(hotkeyFile.toFile());
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void renewLicense() {
        try {
            KpahLicenseSupport.StoredLicenseStatus updated =
                KpahLicenseSupport.promptForActivation(appDir, APP_NAME, frame);
            if (updated != null) {
                licenseStatus = updated;
                refreshStatuses();
            }
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void deleteSelectedProfile() {
        ManagedSession session = selectedSession();
        if (session == null) {
            showMessage("H\u00e3y ch\u1ecdn 1 h\u1ed3 s\u01a1.");
            return;
        }
        if (session.isRunning()) {
            showMessage("H\u00e3y d\u1eebng h\u1ed3 s\u01a1 tr\u01b0\u1edbc khi x\u00f3a.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            frame,
            "X\u00f3a h\u1ed3 s\u01a1 " + session.displayProfileName() + "?",
            APP_NAME,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        if (confirm != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            deleteDirectoryRecursively(session.profile.dir);
            sessions.remove(session.profile.id);
            refreshStatuses();
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void startProfile(ManagedSession session) throws Exception {
        licenseStatus = KpahLicenseSupport.ensureStoredLicense(appDir);
        ensureLauncherFiles();
        if (session.isRunning()) {
            return;
        }
        Files.createDirectories(session.profile.dir);
        Files.createDirectories(session.profile.dataDir);
        if (!Files.exists(session.profile.configPath)) {
            launchLauncher(session.profile, true, session.displayProfileName(), "login");
            return;
        }

        ConfigSummary config = readConfigSummary(session.profile.configPath, session.profile.id);
        trimLogFile(session.profile.logFile, 2L * 1024L * 1024L);
        trimLogFile(session.profile.errorLogFile, 512L * 1024L);
        ArrayList<String> command = baseJavaCommand(config);
        command.add("-jar");
        command.add(launcherJar.toString());
        command.add("--no-ui");
        addProfileArguments(command, session.profile, session.displayProfileName());

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(appDir.toFile());
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(session.profile.logFile.toFile()));
        processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(session.profile.errorLogFile.toFile()));
        session.process = processBuilder.start();
        refreshStatuses();
    }

    private void restartProfile(final ManagedSession session) {
        try {
            if (!session.isRunning()) {
                startProfile(session);
                return;
            }
            sendCommand(session, "stop");
            Thread waiter = new Thread(new Runnable() {
                public void run() {
                    waitForStop(session, 12000L);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                startProfile(session);
                            } catch (Exception error) {
                                showError(frame, error);
                            }
                        }
                    });
                }
            }, "kpah-restart-" + session.profile.id);
            waiter.setDaemon(true);
            waiter.start();
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void waitForStop(ManagedSession session, long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (!session.isRunning()) {
                return;
            }
            sleep(500L);
            refreshProcessState(session);
        }
    }

    private void launchLauncher(ProfilePaths profile, boolean configureOnly, String profileName, String configMode) throws Exception {
        launchLauncher(profile, configureOnly, profileName, configMode, false);
    }

    private void launchLauncher(ProfilePaths profile, boolean configureOnly, String profileName, String configMode, boolean reloadAfterSave) throws Exception {
        ensureLauncherFiles();
        Files.createDirectories(profile.dir);
        Files.createDirectories(profile.dataDir);

        ConfigSummary config = readConfigSummary(profile.configPath, profile.id);
        trimLogFile(profile.logFile, 2L * 1024L * 1024L);
        trimLogFile(profile.errorLogFile, 512L * 1024L);
        ArrayList<String> command = baseJavaCommand(config);
        command.add("-jar");
        command.add(launcherJar.toString());
        if (configureOnly) {
            command.add("--configure-only");
        }
        if (configMode != null && configMode.trim().length() > 0) {
            command.add("--config-mode");
            command.add(configMode.trim());
        }
        if (reloadAfterSave) {
            command.add("--reload-after-save");
        }
        addProfileArguments(command, profile, profileName);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(appDir.toFile());
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(profile.logFile.toFile()));
        processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(profile.errorLogFile.toFile()));
        processBuilder.start();
    }

    private void addProfileArguments(List<String> command, ProfilePaths profile, String profileName) {
        command.add("--config");
        command.add(profile.configPath.toString());
        command.add("--data-dir");
        command.add(profile.dataDir.toString());
        command.add("--status-file");
        command.add(profile.statusFile.toString());
        command.add("--command-file");
        command.add(profile.commandFile.toString());
        command.add("--profile-name");
        command.add(profileName == null ? profile.id : profileName);
        if (clientJar != null) {
            command.add("--client");
            command.add(clientJar.toString());
        }
    }

    private ArrayList<String> baseJavaCommand(ConfigSummary config) {
        int resourceMode = config == null ? 0 : config.resourceMode;
        int xmsMb = resourceMode == 2 ? 64 : (resourceMode == 1 ? 96 : 128);
        int xmxMb = resourceMode == 2 ? 160 : (resourceMode == 1 ? 224 : 256);
        ArrayList<String> command = new ArrayList<String>();
        command.add(resolveJavaExecutable().toString());
        command.add("-Xms" + xmsMb + "m");
        command.add("-Xmx" + xmxMb + "m");
        command.add("-Dsun.java2d.uiScale=1.0");
        command.add("-Dsun.java2d.noddraw=true");
        command.add("-Dsun.java2d.d3d=false");
        command.add("-Dsun.java2d.ddoffscreen=false");
        command.add("-Dsun.java2d.opengl=false");
        command.add("-Dfile.encoding=ISO_8859_1");
        return command;
    }

    private Path resolveJavaExecutable() {
        Path bundledRuntime = appDir.getParent();
        if (bundledRuntime != null) {
            Path bundledJavaw = bundledRuntime.resolve("runtime").resolve("bin").resolve("javaw.exe");
            if (Files.exists(bundledJavaw)) {
                return bundledJavaw;
            }
            Path bundledJava = bundledRuntime.resolve("runtime").resolve("bin").resolve("java.exe");
            if (Files.exists(bundledJava)) {
                return bundledJava;
            }
            Path bundledUnixJava = bundledRuntime.resolve("runtime").resolve("bin").resolve("java");
            if (Files.exists(bundledUnixJava)) {
                return bundledUnixJava;
            }
        }
        Path javaHome = Paths.get(System.getProperty("java.home"));
        Path javaw = javaHome.resolve("bin").resolve("javaw.exe");
        if (Files.exists(javaw)) {
            return javaw;
        }
        Path java = javaHome.resolve("bin").resolve("java.exe");
        if (Files.exists(java)) {
            return java;
        }
        Path unixJava = javaHome.resolve("bin").resolve("java");
        if (Files.exists(unixJava)) {
            return unixJava;
        }
        return Paths.get("java");
    }

    private void sendCommand(ManagedSession session, String command) throws Exception {
        Files.createDirectories(session.profile.dir);
        Properties properties = new Properties();
        properties.setProperty("command", command);
        properties.setProperty("updated_at", Long.toString(System.currentTimeMillis()));
        writePropertiesAtomically(session.profile.commandFile, properties, "KPAH manager command");
    }

    private void refreshStatusesSafely() {
        try {
            enforceLicenseHeartbeat();
            refreshStatuses();
        } catch (Exception error) {
            showError(frame, error);
        }
    }

    private void refreshStatuses() throws Exception {
        ManagedSession selected = selectedSession();
        String selectedProfileId = selected != null ? selected.profile.id : null;
        reloadProfiles();
        rowProfileIds.clear();
        tableModel.setRowCount(0);

        ArrayList<ManagedSession> values = new ArrayList<ManagedSession>(sessions.values());
        Collections.sort(values, Comparator.comparing(ManagedSession::displayProfileName, String.CASE_INSENSITIVE_ORDER));
        for (int i = 0; i < values.size(); i++) {
            ManagedSession session = values.get(i);
            session.status = readStatus(session);
            rowProfileIds.add(session.profile.id);
            tableModel.addRow(toRow(session));
        }
        if (selectedProfileId != null && selectedProfileId.length() > 0) {
            selectProfile(selectedProfileId);
        }
        updateFooter();
        updateSelectionDetails();
    }

    private Object[] toRow(ManagedSession session) {
        StatusSnapshot status = session.status;
        return new Object[]{
            session.displayProfileName(),
            status.username,
            localizeState(status.state),
            localizeScreen(status.screen),
            formatAutoStatus(status),
            localizeBoolean(status.connected),
            status.pid > 0 ? Long.toString(status.pid) : "",
            formatTime(status.updatedAt),
            status.note
        };
    }

    private StatusSnapshot readStatus(ManagedSession session) {
        refreshProcessState(session);
        ConfigSummary config = readConfigSummary(session.profile.configPath, session.profile.id);
        Properties properties = new Properties();
        if (Files.isRegularFile(session.profile.statusFile)) {
            InputStream input = null;
            try {
                input = Files.newInputStream(session.profile.statusFile);
                properties.load(input);
            } catch (Exception ignored) {
            } finally {
                closeQuietly(input);
            }
        }

        StatusSnapshot status = new StatusSnapshot();
        status.profileName = fallback(properties.getProperty("profile_name"), config.profileName);
        status.username = fallback(properties.getProperty("username"), config.username);
        status.state = fallback(properties.getProperty("state"), session.isRunning() ? "starting" : (config.enabled ? "stopped" : "disabled"));
        status.screen = fallback(properties.getProperty("screen"), session.isRunning() ? "booting" : "");
        status.connected = parseBoolean(properties.getProperty("connected"), false);
        status.inGame = parseBoolean(properties.getProperty("in_game"), false);
        status.requestedAutoMode = (int)parseLong(properties.getProperty("requested_auto_mode"), config.autoMode);
        status.effectiveAutoMode = (int)parseLong(properties.getProperty("effective_auto_mode"), status.requestedAutoMode);
        status.autoEnabled = parseBoolean(properties.getProperty("auto_enabled"), status.effectiveAutoMode != 3 && config.enabled);
        status.note = fallback(properties.getProperty("note"), session.lastNote);
        status.pid = parseLong(properties.getProperty("pid"), session.pidOrZero());
        status.updatedAt = parseLong(properties.getProperty("updated_at"), 0L);

        if (status.updatedAt > 0L && System.currentTimeMillis() - status.updatedAt > STATUS_STALE_MS) {
            status.note = joinNote(status.note, "stale_status");
            if (!session.isRunning()) {
                status.state = config.enabled ? "stopped" : "disabled";
                status.connected = false;
                status.inGame = false;
            }
        }

        if (status.updatedAt == 0L) {
            status.state = session.isRunning() ? "starting" : (config.enabled ? "stopped" : "disabled");
            status.screen = session.isRunning() ? "booting" : "";
            status.connected = false;
            status.inGame = false;
            status.requestedAutoMode = config.autoMode;
            status.effectiveAutoMode = config.autoMode;
            status.autoEnabled = config.enabled && config.autoMode != 3;
            status.pid = session.pidOrZero();
        }

        session.lastNote = status.note;
        return status;
    }

    private void reloadProfiles() throws IOException {
        LinkedHashMap<String, ManagedSession> next = new LinkedHashMap<String, ManagedSession>();
        ArrayList<Path> dirs = new ArrayList<Path>();
        if (Files.isDirectory(profileRoot)) {
            try (java.util.stream.Stream<Path> stream = Files.list(profileRoot)) {
                stream.filter(Files::isDirectory).forEach(dirs::add);
            }
        }
        Collections.sort(dirs, Comparator.comparing(Path::toString, String.CASE_INSENSITIVE_ORDER));
        for (int i = 0; i < dirs.size(); i++) {
            Path dir = dirs.get(i);
            String profileId = dir.getFileName().toString();
            ManagedSession existing = sessions.get(profileId);
            if (existing == null) {
                existing = new ManagedSession(new ProfilePaths(profileId, dir));
            }
            next.put(profileId, existing);
        }
        sessions.clear();
        sessions.putAll(next);
    }

    private void refreshProcessState(ManagedSession session) {
        if (session.process != null && !session.process.isAlive()) {
            session.process = null;
        }
    }

    private ConfigSummary readConfigSummary(Path configPath, String profileId) {
        ConfigSummary summary = new ConfigSummary();
        summary.profileName = profileId;
        summary.enabled = true;
        if (!Files.isRegularFile(configPath)) {
            return summary;
        }

        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = Files.newInputStream(configPath);
            properties.load(input);
            summary.profileName = fallback(properties.getProperty("profile_name"), profileId);
            summary.username = properties.getProperty("login_username", "").trim();
            summary.enabled = parseBoolean(properties.getProperty("enabled"), true);
            summary.resourceMode = parseLong(properties.getProperty("resource_mode"), 0L) > 1L ? 2 : (parseLong(properties.getProperty("resource_mode"), 0L) > 0L ? 1 : 0);
            summary.autoMode = (int)parseLong(properties.getProperty("auto_mode"), summary.autoMode);
        } catch (Exception ignored) {
        } finally {
            closeQuietly(input);
        }
        return summary;
    }

    private void ensureLauncherFiles() {
        if (!Files.exists(launcherJar)) {
            throw new IllegalStateException("Kh\u00f4ng t\u00ecm th\u1ea5y " + LAUNCHER_JAR_NAME + " trong " + appDir);
        }
        if (clientJar == null || !Files.exists(clientJar)) {
            throw new IllegalStateException("Kh\u00f4ng t\u00ecm th\u1ea5y client jar trong " + appDir);
        }
    }

    private void selectProfile(String profileId) {
        for (int i = 0; i < rowProfileIds.size(); i++) {
            if (profileId.equals(rowProfileIds.get(i))) {
                table.getSelectionModel().setSelectionInterval(i, i);
                return;
            }
        }
    }

    private ManagedSession selectedSession() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= rowProfileIds.size()) {
            return null;
        }
        return sessions.get(rowProfileIds.get(row));
    }

    private void updateFooter() {
        String client = clientJar == null ? "thi\u1ebfu" : clientJar.getFileName().toString();
        int running = 0;
        int connected = 0;
        int autoOff = 0;
        ArrayList<ManagedSession> values = new ArrayList<ManagedSession>(sessions.values());
        for (int i = 0; i < values.size(); i++) {
            ManagedSession session = values.get(i);
            if (session.isRunning()) {
                running++;
            }
            if (session.status != null && session.status.connected) {
                connected++;
            }
            if (session.status != null && !session.status.autoEnabled) {
                autoOff++;
            }
        }
        totalProfilesLabel.setText(Integer.toString(sessions.size()));
        runningProfilesLabel.setText(Integer.toString(running));
        connectedProfilesLabel.setText(Integer.toString(connected));
        autoOffProfilesLabel.setText(Integer.toString(autoOff));
        footerLabel.setText(
            "Client: " + client
                + " | " + KpahLicenseSupport.footerSummary(licenseStatus)
                + " | Th\u01b0 m\u1ee5c app: " + appDir
                + " | S\u1ed1 h\u1ed3 s\u01a1: " + sessions.size()
        );
    }

    private void enforceLicenseHeartbeat() {
        if (closingForLicense) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now < nextLicenseHeartbeatAt) {
            return;
        }
        try {
            licenseStatus = KpahLicenseSupport.ensureStoredLicense(appDir, true);
            long untilExpiry = licenseStatus.expiresAtMillis <= 0L ? 10000L : (licenseStatus.expiresAtMillis - now) + 1000L;
            nextLicenseHeartbeatAt = now + Math.max(1000L, Math.min(10000L, untilExpiry));
        } catch (Exception error) {
            closeForExpiredLicense(error);
        }
    }

    private void closeForExpiredLicense(Exception error) {
        if (closingForLicense) {
            return;
        }
        closingForLicense = true;
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        stopAllProfilesQuietly();
        JOptionPane.showMessageDialog(
            frame,
            messageOf(error) + "\nTool se tu dong thoat.",
            APP_NAME,
            JOptionPane.WARNING_MESSAGE
        );
        frame.dispose();
        System.exit(0);
    }

    private void stopAllProfilesQuietly() {
        ArrayList<ManagedSession> values = new ArrayList<ManagedSession>(sessions.values());
        for (int i = 0; i < values.size(); i++) {
            try {
                sendCommand(values.get(i), "stop");
            } catch (Exception ignored) {
            }
        }
    }

    private void updateSelectionDetails() {
        ManagedSession session = selectedSession();
        if (session == null) {
            detailArea.setText(
                "Ch\u1ecdn m\u1ed9t h\u1ed3 s\u01a1 trong b\u1ea3ng \u0111\u1ec3 xem nhanh t\u00ean acc, tr\u1ea1ng th\u00e1i, auto \u0111ang b\u1eadt/t\u1eaft, \u0111\u01b0\u1eddng d\u1eabn h\u1ed3 s\u01a1 v\u00e0 ghi ch\u00fa runtime."
            );
            detailArea.setCaretPosition(0);
            return;
        }

        ConfigSummary config = readConfigSummary(session.profile.configPath, session.profile.id);
        StatusSnapshot status = session.status == null ? new StatusSnapshot() : session.status;
        StringBuilder builder = new StringBuilder();
        builder.append("H\u1ed3 s\u01a1: ").append(session.displayProfileName()).append('\n');
        builder.append("T\u00e0i kho\u1ea3n: ").append(status.username.length() > 0 ? status.username : config.username).append('\n');
        builder.append("Tr\u1ea1ng th\u00e1i: ").append(localizeState(status.state)).append('\n');
        builder.append("M\u00e0n h\u00ecnh: ").append(localizeScreen(status.screen)).append('\n');
        builder.append("Auto hi\u1ec7n t\u1ea1i: ").append(formatAutoStatus(status)).append('\n');
        builder.append("K\u1ebft n\u1ed1i: ").append(localizeBoolean(status.connected)).append('\n');
        builder.append("PID: ").append(status.pid > 0 ? Long.toString(status.pid) : "-").append('\n');
        builder.append("C\u1eadp nh\u1eadt: ").append(formatTime(status.updatedAt)).append('\n');
        builder.append("Th\u01b0 m\u1ee5c h\u1ed3 s\u01a1: ").append(session.profile.dir).append('\n');
        if (status.note != null && status.note.trim().length() > 0) {
            builder.append("Ghi ch\u00fa: ").append(status.note);
        } else {
            builder.append("Ghi ch\u00fa: ch\u01b0a c\u00f3.");
        }
        detailArea.setText(builder.toString());
        detailArea.setCaretPosition(0);
    }

    private String uniqueProfileId(String displayName) {
        String base = sanitizeProfileId(displayName);
        String candidate = base;
        int suffix = 2;
        while (sessions.containsKey(candidate) || Files.exists(profileRoot.resolve(candidate))) {
            candidate = base + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

    private static String sanitizeProfileId(String value) {
        String normalized = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        normalized = normalized.replaceAll("[^a-z0-9._-]+", "-");
        normalized = normalized.replaceAll("(^-+)|(-+$)", "");
        if (normalized.length() == 0) {
            return "profile";
        }
        return normalized;
    }

    private static Path resolveAppDir() throws Exception {
        Path jarPath = Paths.get(
            KpahMultiAccountManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()
        );
        Path parent = jarPath.getParent();
        if (parent == null) {
            throw new IllegalStateException("Kh\u00f4ng x\u00e1c \u0111\u1ecbnh \u0111\u01b0\u1ee3c th\u01b0 m\u1ee5c app.");
        }
        return parent;
    }

    private static Path findClientJar(Path appDir) {
        for (int i = 0; i < CLIENT_CANDIDATES.length; i++) {
            Path candidate = appDir.resolve(CLIENT_CANDIDATES[i]);
            if (Files.exists(candidate)) {
                return candidate;
            }
        }
        try {
            java.nio.file.DirectoryStream<Path> stream = Files.newDirectoryStream(appDir, "*.jar");
            try {
                for (Path path : stream) {
                    String name = path.getFileName().toString();
                    if (!LAUNCHER_JAR_NAME.equalsIgnoreCase(name) && !"kpah-manager.jar".equalsIgnoreCase(name)) {
                        return path;
                    }
                }
            } finally {
                stream.close();
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    private static String fallback(String primary, String backup) {
        String safePrimary = primary == null ? "" : primary.trim();
        return safePrimary.length() > 0 ? safePrimary : (backup == null ? "" : backup.trim());
    }

    private static boolean parseBoolean(String value, boolean defaultValue) {
        if (value == null || value.trim().length() == 0) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    private static long parseLong(String value, long defaultValue) {
        if (value == null || value.trim().length() == 0) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    private static String describeAutoMode(int autoMode, boolean enabled) {
        if (!enabled || autoMode == 3) {
            return "t\u1eaft";
        }
        if (autoMode == 0) {
            return "\u0111\u00e1nh";
        }
        if (autoMode == 1) {
            return "b\u01a1m m\u00e1u";
        }
        if (autoMode == 2) {
            return "\u0111\u00e1nh + m\u00e1u";
        }
        return enabled ? "b\u1eadt" : "t\u1eaft";
    }

    private String formatAutoStatus(StatusSnapshot status) {
        int effectiveMode = status.effectiveAutoMode == Integer.MIN_VALUE ? status.requestedAutoMode : status.effectiveAutoMode;
        return describeAutoMode(effectiveMode, status.autoEnabled);
    }

    private static String localizeBoolean(boolean value) {
        return value ? "c\u00f3" : "kh\u00f4ng";
    }

    private static String localizeState(String state) {
        String normalized = state == null ? "" : state.trim().toLowerCase(Locale.ROOT);
        if ("running".equals(normalized)) {
            return "\u0111ang ch\u1ea1y";
        }
        if ("stopped".equals(normalized)) {
            return "\u0111\u00e3 d\u1eebng";
        }
        if ("starting".equals(normalized)) {
            return "\u0111ang kh\u1edfi \u0111\u1ed9ng";
        }
        if ("disabled".equals(normalized)) {
            return "\u0111\u00e3 t\u1eaft";
        }
        if ("login".equals(normalized)) {
            return "\u0111\u0103ng nh\u1eadp";
        }
        if ("config".equals(normalized) || "configuring".equals(normalized)) {
            return "\u0111ang c\u1ea5u h\u00ecnh";
        }
        if ("error".equals(normalized)) {
            return "l\u1ed7i";
        }
        return state == null ? "" : state;
    }

    private static String localizeScreen(String screen) {
        String normalized = screen == null ? "" : screen.trim().toLowerCase(Locale.ROOT);
        if ("game".equals(normalized)) {
            return "trong game";
        }
        if ("booting".equals(normalized)) {
            return "\u0111ang m\u1edf";
        }
        if ("login".equals(normalized)) {
            return "m\u00e0n \u0111\u0103ng nh\u1eadp";
        }
        if ("config".equals(normalized)) {
            return "m\u00e0n c\u1ea5u h\u00ecnh";
        }
        return screen == null ? "" : screen;
    }

    private String formatTime(long millis) {
        if (millis <= 0L) {
            return "";
        }
        return timeFormat.format(new Date(millis));
    }

    private static String joinNote(String first, String second) {
        if (first == null || first.trim().length() == 0) {
            return second == null ? "" : second;
        }
        if (second == null || second.trim().length() == 0) {
            return first;
        }
        return first + ", " + second;
    }

    private static void writePropertiesAtomically(Path path, Properties properties, String comments) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path tempFile = path.resolveSibling(path.getFileName().toString() + ".tmp");
        OutputStream output = Files.newOutputStream(tempFile);
        try {
            properties.store(output, comments);
        } finally {
            closeQuietly(output);
        }
        try {
            Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ignored) {
            Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void deleteDirectoryRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                deletePathWithRetries(file);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path directory, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                deletePathWithRetries(directory);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deletePathWithRetries(Path path) throws IOException {
        IOException lastError = null;
        for (int attempt = 0; attempt < 4; attempt++) {
            try {
                clearWindowsFileFlags(path);
                Files.deleteIfExists(path);
                return;
            } catch (IOException error) {
                lastError = error;
                sleep(120L);
            }
        }
        if (lastError != null) {
            throw lastError;
        }
    }

    private static void clearWindowsFileFlags(Path path) {
        if (path == null || !Files.exists(path)) {
            return;
        }
        try {
            Files.setAttribute(path, "dos:readonly", Boolean.FALSE);
        } catch (Exception ignored) {
        }
        try {
            Files.setAttribute(path, "dos:hidden", Boolean.FALSE);
        } catch (Exception ignored) {
        }
        try {
            Files.setAttribute(path, "dos:system", Boolean.FALSE);
        } catch (Exception ignored) {
        }
        try {
            path.toFile().setWritable(true, false);
        } catch (Exception ignored) {
        }
    }

    private static void closeQuietly(java.io.Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }

    private static void trimLogFile(Path file, long maxBytes) {
        try {
            if (!Files.exists(file) || Files.size(file) <= maxBytes) {
                return;
            }
            Files.write(file, new byte[0]);
        } catch (IOException ignored) {
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, APP_NAME, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showError(JFrame frame, Throwable error) {
        JOptionPane.showMessageDialog(frame, messageOf(error), APP_NAME, JOptionPane.ERROR_MESSAGE);
    }

    private static String messageOf(Throwable error) {
        String message = error == null ? "L\u1ed7i kh\u00f4ng x\u00e1c \u0111\u1ecbnh" : error.getMessage();
        if (message == null || message.trim().length() == 0) {
            message = String.valueOf(error);
        }
        return message;
    }

    private static final class ManagedSession {
        private final ProfilePaths profile;
        private Process process;
        private StatusSnapshot status = new StatusSnapshot();
        private String lastNote = "";

        private ManagedSession(ProfilePaths profile) {
            this.profile = profile;
        }

        private boolean isRunning() {
            if (process != null && process.isAlive()) {
                return true;
            }
            long pid = pidOrZero();
            if (pid <= 0L) {
                return false;
            }
            return ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
        }

        private long pidOrZero() {
            if (process != null && process.isAlive()) {
                return process.pid();
            }
            return status.pid;
        }

        private String displayProfileName() {
            String value = status.profileName;
            if (value != null && value.trim().length() > 0) {
                return value.trim();
            }
            return profile.id;
        }
    }

    private static final class ProfilePaths {
        private final String id;
        private final Path dir;
        private final Path configPath;
        private final Path dataDir;
        private final Path statusFile;
        private final Path commandFile;
        private final Path logFile;
        private final Path errorLogFile;

        private ProfilePaths(String id, Path dir) {
            this.id = id;
            this.dir = dir;
            this.configPath = dir.resolve(CONFIG_FILE);
            this.dataDir = dir.resolve(RUNTIME_DIR);
            this.statusFile = dir.resolve(STATUS_FILE);
            this.commandFile = dir.resolve(COMMAND_FILE);
            this.logFile = dir.resolve(LOG_FILE);
            this.errorLogFile = dir.resolve(ERROR_LOG_FILE);
        }
    }

    private static final class ConfigSummary {
        private String profileName = "";
        private String username = "";
        private boolean enabled = true;
        private int resourceMode;
        private int autoMode = 2;
    }

    private static final class StatusSnapshot {
        private String profileName = "";
        private String username = "";
        private String state = "";
        private String screen = "";
        private boolean connected;
        private boolean inGame;
        private boolean autoEnabled = true;
        private int requestedAutoMode = Integer.MIN_VALUE;
        private int effectiveAutoMode = Integer.MIN_VALUE;
        private long pid;
        private long updatedAt;
        private String note = "";
    }
}
