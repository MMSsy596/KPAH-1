package adminlocal;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.plaf.FontUIResource;

public final class AdminLocalApp extends JFrame {

    private static final Color APP_BG = new Color(245, 247, 251);
    private static final Color PANEL_BG = Color.WHITE;
    private static final Color INPUT_BG = new Color(250, 252, 255);
    private static final Color BORDER_COLOR = new Color(214, 221, 232);
    private static final Color ACCENT = new Color(34, 107, 255);
    private static final Color TEXT_PRIMARY = new Color(24, 32, 44);
    private static final Color TEXT_MUTED = new Color(92, 103, 120);
    private static final Color SUCCESS = new Color(25, 135, 84);
    private static final Color DANGER = new Color(198, 40, 40);
    private static final Font BASE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 13);
    private static final Font LOG_FONT = new Font("Consolas", Font.PLAIN, 12);

    private final AdminApiClient client = new AdminApiClient();
    private final ExecutorService networkExecutor = Executors.newSingleThreadExecutor(new NamedThreadFactory());

    private JPanel mainPanel;
    private JLabel statusLabel;
    private JLabel playersLabel;
    private JLabel memoryLabel;
    private JLabel uptimeLabel;
    private JLabel portLabel;

    private JButton maintenanceButton;
    private JButton stopLoginButton;
    private JButton cleanMemoryButton;
    private JButton announceButton;
    private JButton updateButton;
    private JButton lockAccountButton;
    private JButton banAccountButton;
    private JButton unbanAccountButton;
    private JButton changePasswordButton;
    private JButton gemManagerButton;
    private JButton listOnlineButton;
    private JButton ambientBotsButton;
    private JButton luongSon108Button;
    private JButton buffCharacterButton;

    private JButton eventApplyButton;
    private JButton eventReloadButton;
    private JPanel eventContentPanel;

    private JButton blackMarketApplyButton;
    private JButton blackMarketReloadButton;
    private JPanel blackMarketContentPanel;

    private JSpinner luckyBagDropRateSpinner;
    private JButton luckyBagDropApplyButton;
    private JSpinner luckyWeightLuong;
    private JSpinner luckyWeightLuongKhoa;
    private JSpinner luckyWeightXu;
    private JSpinner luckyWeightHp;
    private JSpinner luckyWeightMp;
    private JButton luckyRewardApplyButton;
    private JSpinner luckyAmountLuongMin;
    private JSpinner luckyAmountLuongMax;
    private JSpinner luckyAmountLuongKhoaMin;
    private JSpinner luckyAmountLuongKhoaMax;
    private JSpinner luckyAmountXuMin;
    private JSpinner luckyAmountXuMax;
    private JSpinner luckyAmountHpMin;
    private JSpinner luckyAmountHpMax;
    private JSpinner luckyAmountMpMin;
    private JSpinner luckyAmountMpMax;
    private JButton luckyAmountApplyButton;
    private JSpinner luckyMaxOpenPerDay;

    private JTextArea logArea;
    private JScrollPane logScrollPane;

    private final List<EventRow> eventRows = new ArrayList<EventRow>();
    private BlackMarketRareSlotControls[] blackMarketRareSlotControls;
    private BlackMarketMiscCategoryControls[] blackMarketMiscCategoryControls;

    private Timer pollTimer;
    private Timer runtimeLogTimer;
    private volatile boolean statusRefreshInFlight;
    private volatile boolean managementLoaded;
    private volatile AdminApiClient.ServerStatus currentStatus;
    private final List<RuntimeLogSource> runtimeLogSources = new ArrayList<RuntimeLogSource>();

    public AdminLocalApp() {
        loadLookAndFeel();
        loadLocalAdminSettingsFromServerIni();

        setTitle("Quản Lý Máy Chủ");
        setSize(1180, 760);
        setMinimumSize(new Dimension(980, 700));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        loadWindowIcon();

        initComponents();
        layoutComponents();
        initEventHandlers();
        setButtonTooltips();
        setConnectedState(false);
        updateOfflineStatus("Đang kết nối local admin...");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = showConfirmDialog(
                        AdminLocalApp.this,
                        "Bạn có chắc chắn muốn đóng bảng điều khiển?",
                        "Xác Nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    shutdown();
                    dispose();
                }
            }
        });

        addLog("Đã nạp local admin từ server.ini: " + client.getBaseUrl());
        initializeRuntimeLogSources();
        pollRuntimeLogs();
        refreshStatusAsync(true);
        pollTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStatusAsync(false);
            }
        });
        pollTimer.setInitialDelay(2500);
        pollTimer.start();
        runtimeLogTimer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pollRuntimeLogs();
            }
        });
        runtimeLogTimer.setInitialDelay(1200);
        runtimeLogTimer.start();
    }

    private void loadLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        applyThemeDefaults();
    }

    private void applyThemeDefaults() {
        FontUIResource baseFont = new FontUIResource(BASE_FONT);
        FontUIResource titleFont = new FontUIResource(TITLE_FONT);
        UIManager.put("Label.font", baseFont);
        UIManager.put("Button.font", titleFont);
        UIManager.put("TextField.font", baseFont);
        UIManager.put("TextArea.font", baseFont);
        UIManager.put("ComboBox.font", baseFont);
        UIManager.put("TabbedPane.font", titleFont);
        UIManager.put("Table.font", baseFont);
        UIManager.put("TableHeader.font", titleFont);
        UIManager.put("OptionPane.messageFont", baseFont);
        UIManager.put("OptionPane.buttonFont", titleFont);
        UIManager.put("Panel.background", APP_BG);
        UIManager.put("OptionPane.background", APP_BG);
        UIManager.put("TabbedPane.background", APP_BG);
        UIManager.put("TabbedPane.foreground", TEXT_PRIMARY);
    }

    private void loadWindowIcon() {
        File[] candidates = new File[]{
                new File("images.png"),
                new File("dist/admin_local/images.png"),
                new File(System.getProperty("user.home"), "Desktop\\images.png")
        };
        for (int i = 0; i < candidates.length; i++) {
            File candidate = candidates[i];
            if (!candidate.isFile()) {
                continue;
            }
            BufferedInputStream input = null;
            try {
                input = new BufferedInputStream(new FileInputStream(candidate));
                Image image = ImageIO.read(input);
                if (image != null) {
                    setIconImage(image);
                    return;
                }
            } catch (Exception ignored) {
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private void loadLocalAdminSettingsFromServerIni() {
        Properties props = new Properties();
        File configFile = new File("server.ini");
        if (!configFile.isFile()) {
            return;
        }
        FileInputStream input = null;
        try {
            input = new FileInputStream(configFile);
            props.load(input);
        } catch (Exception ignored) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception ignored) {
            }
        }
        client.setHost(props.getProperty("sv.localAdminHost", "127.0.0.1"));
        client.setPort(parseInt(props.getProperty("sv.localAdminPort"), 18023));
        client.setToken(props.getProperty("sv.localAdminToken", ""));
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(APP_BG);

        statusLabel = createStatusLabel("Trạng thái: Đang kết nối...");
        playersLabel = createStatusLabel("Người chơi: 0/0");
        memoryLabel = createStatusLabel("Bộ nhớ: 0 MB");
        uptimeLabel = createStatusLabel("Thời gian chạy: 00:00:00");
        portLabel = createStatusLabel("Cổng game: 0");

        maintenanceButton = createButton("Bảo Trì");
        stopLoginButton = createButton("Dừng Đăng Nhập");
        cleanMemoryButton = createButton("Dọn Bộ Nhớ");
        announceButton = createButton("Thông Báo");
        updateButton = createButton("Làm Mới");
        lockAccountButton = createButton("Kick Người Chơi");
        banAccountButton = createButton("Khóa Vĩnh Viễn");
        unbanAccountButton = createButton("Mở Khóa");
        changePasswordButton = createButton("Đổi Mật Khẩu");
        gemManagerButton = createButton("Quản Lý Gem");
        listOnlineButton = createButton("Người Chơi Online");
        ambientBotsButton = createButton("Bot Môi Trường");
        luongSon108Button = createButton("108 Lương Sơn");
        buffCharacterButton = createButton("Buff Nhân Vật");

        eventApplyButton = createButton("Áp Dụng");
        eventReloadButton = createButton("Tải Lại");
        eventContentPanel = new JPanel(new BorderLayout());
        eventContentPanel.add(createLoadingLabel("Đang tải cấu hình sự kiện..."), BorderLayout.CENTER);

        blackMarketApplyButton = createButton("Áp Dụng");
        blackMarketReloadButton = createButton("Tải Lại");
        blackMarketContentPanel = new JPanel(new BorderLayout());
        blackMarketContentPanel.add(createLoadingLabel("Đang tải cấu hình Chợ Đen..."), BorderLayout.CENTER);

        initLuckyBagControls();

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(LOG_FONT);
        logArea.setBackground(new Color(248, 250, 254));
        logArea.setForeground(TEXT_PRIMARY);
        logArea.setBorder(new EmptyBorder(8, 10, 8, 10));
        logScrollPane = new JScrollPane(logArea);
        styleScrollPane(logScrollPane);
        applyComponentStyles();
    }

    private void initLuckyBagControls() {
        luckyBagDropRateSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(100.0, 0.0, 100.0, 0.1));
        luckyBagDropRateSpinner.setEditor(new JSpinner.NumberEditor(luckyBagDropRateSpinner, "0.0"));
        luckyBagDropApplyButton = createButton("Áp Dụng");

        luckyWeightLuong = new JSpinner(new javax.swing.SpinnerNumberModel(10, 0, 1000000, 1));
        luckyWeightLuongKhoa = new JSpinner(new javax.swing.SpinnerNumberModel(10, 0, 1000000, 1));
        luckyWeightXu = new JSpinner(new javax.swing.SpinnerNumberModel(10, 0, 1000000, 1));
        luckyWeightHp = new JSpinner(new javax.swing.SpinnerNumberModel(10, 0, 1000000, 1));
        luckyWeightMp = new JSpinner(new javax.swing.SpinnerNumberModel(10, 0, 1000000, 1));
        luckyRewardApplyButton = createButton("Áp Dụng");

        luckyAmountLuongMin = new JSpinner(new javax.swing.SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountLuongMax = new JSpinner(new javax.swing.SpinnerNumberModel(30, 0, 1000000000, 1));
        luckyAmountLuongKhoaMin = new JSpinner(new javax.swing.SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountLuongKhoaMax = new JSpinner(new javax.swing.SpinnerNumberModel(30, 0, 1000000000, 1));
        luckyAmountXuMin = new JSpinner(new javax.swing.SpinnerNumberModel(10000, 0, 1000000000, 1000));
        luckyAmountXuMax = new JSpinner(new javax.swing.SpinnerNumberModel(100000, 0, 1000000000, 1000));
        luckyAmountHpMin = new JSpinner(new javax.swing.SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountHpMax = new JSpinner(new javax.swing.SpinnerNumberModel(3, 0, 1000000000, 1));
        luckyAmountMpMin = new JSpinner(new javax.swing.SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountMpMax = new JSpinner(new javax.swing.SpinnerNumberModel(3, 0, 1000000000, 1));
        luckyAmountApplyButton = createButton("Áp Dụng");
        luckyMaxOpenPerDay = new JSpinner(new javax.swing.SpinnerNumberModel(50, 1, 1000000, 1));
        styleSpinner(luckyBagDropRateSpinner, luckyWeightLuong, luckyWeightLuongKhoa, luckyWeightXu, luckyWeightHp, luckyWeightMp,
                luckyAmountLuongMin, luckyAmountLuongMax, luckyAmountLuongKhoaMin, luckyAmountLuongKhoaMax,
                luckyAmountXuMin, luckyAmountXuMax, luckyAmountHpMin, luckyAmountHpMax, luckyAmountMpMin, luckyAmountMpMax,
                luckyMaxOpenPerDay);
    }

    private void layoutComponents() {
        JPanel statusPanel = new JPanel(new GridLayout(5, 1, 0, 2));
        stylePanel(statusPanel);
        statusPanel.setBorder(createGroupBorder("Tổng Quan Máy Chủ"));
        statusPanel.add(statusLabel);
        statusPanel.add(playersLabel);
        statusPanel.add(memoryLabel);
        statusPanel.add(uptimeLabel);
        statusPanel.add(portLabel);

        JPanel logPanel = new JPanel(new BorderLayout());
        stylePanel(logPanel);
        logPanel.setBorder(createGroupBorder("Nhật Ký Hoạt Động"));
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        Dimension panelWidth = new Dimension(320, 150);
        statusPanel.setPreferredSize(panelWidth);
        logPanel.setPreferredSize(panelWidth);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(APP_BG);
        topPanel.add(statusPanel);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(logPanel);

        JPanel serverPanel = createButtonPanel("Điều Khiển Máy Chủ",
                maintenanceButton, stopLoginButton, cleanMemoryButton, announceButton, updateButton);
        JPanel accountPanel = createButtonPanel("Tài Khoản",
                banAccountButton, unbanAccountButton, lockAccountButton, changePasswordButton);
        JPanel playerPanel = createPlayerPanel();
        JPanel eventPanel = createEventPanel();
        JPanel blackMarketPanel = createBlackMarketPanel();

        Dimension panelSize = new Dimension(360, 620);
        serverPanel.setPreferredSize(panelSize);
        accountPanel.setPreferredSize(panelSize);
        playerPanel.setPreferredSize(panelSize);
        eventPanel.setPreferredSize(panelSize);
        blackMarketPanel.setPreferredSize(panelSize);

        JTabbedPane controlsTabs = new JTabbedPane();
        controlsTabs.setBackground(APP_BG);
        controlsTabs.setForeground(TEXT_PRIMARY);
        controlsTabs.addTab("Máy Chủ", wrapScrollable(serverPanel));
        controlsTabs.addTab("Tài Khoản", wrapScrollable(accountPanel));
        controlsTabs.addTab("Người Chơi", wrapScrollable(playerPanel));
        controlsTabs.addTab("Sự Kiện", wrapScrollable(eventPanel));
        controlsTabs.addTab("Chợ Đen", wrapScrollable(blackMarketPanel));

        mainPanel.removeAll();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(controlsTabs, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void initEventHandlers() {
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStatus != null && (currentStatus.maintenanceScheduled || isMaintenanceState(currentStatus.serverState))) {
                    cancelMaintenanceMode();
                } else {
                    startMaintenanceMode();
                }
            }
        });

        stopLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStatus == null) {
                    addLog("Chưa có trạng thái máy chủ.");
                    return;
                }
                submitCommand("đổi đăng nhập", new CommandTask() {
                    @Override
                    public AdminApiClient.CommandResponse run() throws Exception {
                        return client.setStopLogin(!currentStatus.stopLogin);
                    }
                }, null);
            }
        });

        cleanMemoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitCommand("dọn bộ nhớ", new CommandTask() {
                    @Override
                    public AdminApiClient.CommandResponse run() throws Exception {
                        return client.cleanMemory();
                    }
                }, null);
            }
        });

        announceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnnounceDialog();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStatusAsync(true);
                refreshAllManagementDataAsync(true);
            }
        });

        lockAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleKickPlayer();
            }
        });

        banAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBanAccount();
            }
        });

        unbanAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUnbanAccount();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePassword();
            }
        });

        listOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshOnlinePlayersAndShow();
            }
        });

        gemManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGemManagerDialog();
            }
        });

        ambientBotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAmbientBotDashboard();
            }
        });

        luongSon108Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLuongSon108Dashboard();
            }
        });

        buffCharacterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNamedCharacterBuffDialog("");
            }
        });

        luckyBagDropApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyLuckyBagSettings("tỷ lệ rơi túi quà");
            }
        });

        luckyRewardApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyLuckyBagSettings("tỷ lệ phần thưởng túi quà");
            }
        });

        luckyAmountApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyLuckyBagSettings("số lượng túi quà");
            }
        });

        eventReloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEventSettingsAsync(true);
            }
        });

        eventApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyEventSettings();
            }
        });

        blackMarketReloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBlackMarketSettingsAsync(true);
            }
        });

        blackMarketApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyBlackMarketSettings();
            }
        });
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        stylePanel(panel);
        panel.setBorder(createGroupBorder("Người Chơi"));

        addButtonToPanel(panel, listOnlineButton);
        addButtonToPanel(panel, gemManagerButton);
        addButtonToPanel(panel, ambientBotsButton);
        addButtonToPanel(panel, luongSon108Button);
        addButtonToPanel(panel, buffCharacterButton);

        panel.add(Box.createVerticalStrut(6));

        JLabel label = new JLabel("Tỷ lệ rơi túi quà may mắn (%)");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(4));

        JPanel rateRow = new JPanel();
        rateRow.setLayout(new BoxLayout(rateRow, BoxLayout.X_AXIS));
        Dimension spSize = luckyBagDropRateSpinner.getPreferredSize();
        luckyBagDropRateSpinner.setMaximumSize(new Dimension(80, spSize.height));
        rateRow.add(luckyBagDropRateSpinner);
        rateRow.add(Box.createHorizontalStrut(6));
        rateRow.add(luckyBagDropApplyButton);
        rateRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(rateRow);

        panel.add(Box.createVerticalStrut(8));

        JLabel rewardLabel = new JLabel("Tỷ lệ phần thưởng túi quà may mắn");
        rewardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(rewardLabel);
        panel.add(Box.createVerticalStrut(4));

        JPanel rewardGrid = new JPanel(new GridLayout(5, 2, 4, 4));
        rewardGrid.add(new JLabel("Lượng"));
        rewardGrid.add(luckyWeightLuong);
        rewardGrid.add(new JLabel("Lượng khóa"));
        rewardGrid.add(luckyWeightLuongKhoa);
        rewardGrid.add(new JLabel("Xu"));
        rewardGrid.add(luckyWeightXu);
        rewardGrid.add(new JLabel("HP15k"));
        rewardGrid.add(luckyWeightHp);
        rewardGrid.add(new JLabel("MP15k"));
        rewardGrid.add(luckyWeightMp);
        panel.add(rewardGrid);
        panel.add(Box.createVerticalStrut(4));
        luckyRewardApplyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(luckyRewardApplyButton);

        panel.add(Box.createVerticalStrut(8));

        JLabel amountLabel = new JLabel("Số lượng phần thưởng (min / max)");
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amountLabel);
        panel.add(Box.createVerticalStrut(4));

        JPanel amountGrid = new JPanel(new GridLayout(6, 3, 4, 4));
        amountGrid.add(new JLabel(""));
        amountGrid.add(new JLabel("Min"));
        amountGrid.add(new JLabel("Max"));
        amountGrid.add(new JLabel("Lượng"));
        amountGrid.add(luckyAmountLuongMin);
        amountGrid.add(luckyAmountLuongMax);
        amountGrid.add(new JLabel("Lượng khóa"));
        amountGrid.add(luckyAmountLuongKhoaMin);
        amountGrid.add(luckyAmountLuongKhoaMax);
        amountGrid.add(new JLabel("Xu"));
        amountGrid.add(luckyAmountXuMin);
        amountGrid.add(luckyAmountXuMax);
        amountGrid.add(new JLabel("HP15k"));
        amountGrid.add(luckyAmountHpMin);
        amountGrid.add(luckyAmountHpMax);
        amountGrid.add(new JLabel("MP15k"));
        amountGrid.add(luckyAmountMpMin);
        amountGrid.add(luckyAmountMpMax);
        panel.add(amountGrid);
        panel.add(Box.createVerticalStrut(4));

        JPanel maxOpenRow = new JPanel();
        maxOpenRow.setLayout(new BoxLayout(maxOpenRow, BoxLayout.X_AXIS));
        maxOpenRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        maxOpenRow.add(new JLabel("Tối đa farm/mở mỗi ngày"));
        maxOpenRow.add(Box.createHorizontalStrut(8));
        Dimension maxOpenSize = luckyMaxOpenPerDay.getPreferredSize();
        luckyMaxOpenPerDay.setMaximumSize(new Dimension(100, maxOpenSize.height));
        maxOpenRow.add(luckyMaxOpenPerDay);
        panel.add(maxOpenRow);
        panel.add(Box.createVerticalStrut(4));

        luckyAmountApplyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(luckyAmountApplyButton);

        return panel;
    }

    private JPanel createEventPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        stylePanel(outer);
        outer.setBorder(createGroupBorder("Quản Lý Sự Kiện"));
        outer.add(eventContentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        buttonPanel.add(eventApplyButton);
        buttonPanel.add(eventReloadButton);
        outer.add(buttonPanel, BorderLayout.SOUTH);
        return outer;
    }

    private JPanel createBlackMarketPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        stylePanel(outer);
        outer.setBorder(createGroupBorder("Quản Lý Chợ Đen"));
        outer.add(blackMarketContentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        buttonPanel.add(blackMarketApplyButton);
        buttonPanel.add(blackMarketReloadButton);
        outer.add(buttonPanel, BorderLayout.SOUTH);
        return outer;
    }

    private void refreshStatusAsync(final boolean manual) {
        if (statusRefreshInFlight) {
            return;
        }
        statusRefreshInFlight = true;
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.ServerStatus status = client.fetchStatus();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            statusRefreshInFlight = false;
                            currentStatus = status;
                            setConnectedState(true);
                            updateServerStatus(status);
                            if (manual) {
                                addLog("Đã cập nhật trạng thái máy chủ.");
                            }
                            if (!managementLoaded) {
                                managementLoaded = true;
                                refreshAllManagementDataAsync(false);
                            }
                        }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            statusRefreshInFlight = false;
                            setConnectedState(false);
                            updateOfflineStatus("Mất kết nối local admin");
                            if (manual) {
                                addLog("Không kết nối được local admin: " + ex.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void refreshAllManagementDataAsync(boolean manual) {
        refreshEventSettingsAsync(manual);
        refreshLuckyBagSettingsAsync(manual);
        refreshBlackMarketSettingsAsync(manual);
    }

    private void refreshEventSettingsAsync(final boolean manual) {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<AdminApiClient.EventSetting> events = client.fetchEventSettings();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            reloadEventSettings(events);
                            if (manual) {
                                addLog("Đã tải lại cấu hình sự kiện.");
                            }
                        }
                    });
                } catch (final Exception ex) {
                    if (manual) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                addLog("Không tải được cấu hình sự kiện: " + ex.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void refreshLuckyBagSettingsAsync(final boolean manual) {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.LuckyBagSettings settings = client.fetchLuckyBagSettings();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            reloadLuckyBagSettings(settings);
                            if (manual) {
                                addLog("Đã tải lại cấu hình túi quà may mắn.");
                            }
                        }
                    });
                } catch (final Exception ex) {
                    if (manual) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                addLog("Không tải được túi quà may mắn: " + ex.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void refreshBlackMarketSettingsAsync(final boolean manual) {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.BlackMarketSettings settings = client.fetchBlackMarketSettings();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            reloadBlackMarketSettings(settings);
                            if (manual) {
                                addLog("Đã tải lại cấu hình Chợ Đen.");
                            }
                        }
                    });
                } catch (final Exception ex) {
                    if (manual) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                addLog("Không tải được cấu hình Chợ Đen: " + ex.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void reloadEventSettings(List<AdminApiClient.EventSetting> events) {
        eventRows.clear();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < events.size(); i++) {
            AdminApiClient.EventSetting event = events.get(i);
            EventRow row = new EventRow();
            row.key = event.key;
            row.label = event.label;
            row.combo = new JComboBox<String>(new String[]{"Tự động", "Bật", "Tắt"});
            row.combo.setSelectedIndex(eventValueToIndex(event.value));
            eventRows.add(row);

            JPanel rowPanel = new JPanel(new BorderLayout(5, 0));
            rowPanel.add(new JLabel(event.label), BorderLayout.CENTER);
            rowPanel.add(row.combo, BorderLayout.EAST);
            listPanel.add(rowPanel);
            listPanel.add(Box.createVerticalStrut(4));
        }
        eventContentPanel.removeAll();
        eventContentPanel.add(new JScrollPane(listPanel), BorderLayout.CENTER);
        eventContentPanel.revalidate();
        eventContentPanel.repaint();
    }

    private void reloadLuckyBagSettings(AdminApiClient.LuckyBagSettings settings) {
        luckyBagDropRateSpinner.setValue(Double.valueOf(settings.dropRatePercent));
        luckyWeightLuong.setValue(Integer.valueOf(settings.weightLuong));
        luckyWeightLuongKhoa.setValue(Integer.valueOf(settings.weightLuongLock));
        luckyWeightXu.setValue(Integer.valueOf(settings.weightXu));
        luckyWeightHp.setValue(Integer.valueOf(settings.weightHp));
        luckyWeightMp.setValue(Integer.valueOf(settings.weightMp));
        luckyAmountLuongMin.setValue(Integer.valueOf(settings.amountLuongMin));
        luckyAmountLuongMax.setValue(Integer.valueOf(settings.amountLuongMax));
        luckyAmountLuongKhoaMin.setValue(Integer.valueOf(settings.amountLuongLockMin));
        luckyAmountLuongKhoaMax.setValue(Integer.valueOf(settings.amountLuongLockMax));
        luckyAmountXuMin.setValue(Integer.valueOf(settings.amountXuMin));
        luckyAmountXuMax.setValue(Integer.valueOf(settings.amountXuMax));
        luckyAmountHpMin.setValue(Integer.valueOf(settings.amountHpMin));
        luckyAmountHpMax.setValue(Integer.valueOf(settings.amountHpMax));
        luckyAmountMpMin.setValue(Integer.valueOf(settings.amountMpMin));
        luckyAmountMpMax.setValue(Integer.valueOf(settings.amountMpMax));
        luckyMaxOpenPerDay.setValue(Integer.valueOf(settings.maxOpenPerDay));
    }

    private void reloadBlackMarketSettings(AdminApiClient.BlackMarketSettings settings) {
        blackMarketRareSlotControls = null;
        blackMarketMiscCategoryControls = null;

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));
        stylePanel(corePanel);
        corePanel.setBorder(createGroupBorder("Chợ Đen Chính"));

        JSpinner shardPriceSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(settings.shardPriceAn, 0, 1000000, 1));
        JSpinner shardLimitSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(settings.shardMaxBuyPerPeriod, 1, 1000, 1));
        JSpinner hotCrateSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(settings.hotCratePriceAn, 0, 1000000, 1));
        corePanel.add(createBlackMarketNumberRow("Giá Mảnh Cổ Vật", shardPriceSpinner, "Ẩn"));
        corePanel.add(Box.createVerticalStrut(4));
        corePanel.add(createBlackMarketNumberRow("Giới hạn mua mỗi kỳ", shardLimitSpinner, "mảnh"));
        corePanel.add(Box.createVerticalStrut(4));
        corePanel.add(createBlackMarketNumberRow("Giá Rương Hàng Nóng", hotCrateSpinner, "Ẩn"));
        content.add(corePanel);
        content.add(Box.createVerticalStrut(8));

        blackMarketRareSlotControls = new BlackMarketRareSlotControls[settings.rareSlots.size()];
        JPanel rarePanel = new JPanel();
        rarePanel.setLayout(new BoxLayout(rarePanel, BoxLayout.Y_AXIS));
        stylePanel(rarePanel);
        rarePanel.setBorder(createGroupBorder("Thương Nhân Quý Hiếm"));
        for (int i = 0; i < settings.rareSlots.size(); i++) {
            AdminApiClient.BlackMarketRareSlot slotData = settings.rareSlots.get(i);
            BlackMarketRareSlotControls slot = new BlackMarketRareSlotControls();
            slot.slotLabel = slotData.slotLabel;
            slot.optionCombo = new JComboBox<AdminApiClient.BlackMarketOption>(
                    settings.rareOptions.toArray(new AdminApiClient.BlackMarketOption[0]));
            selectBlackMarketOption(slot.optionCombo, slotData.optionIndex);
            slot.priceSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(slotData.priceAnHacThi, 0, 1000000, 1));
            blackMarketRareSlotControls[i] = slot;
            rarePanel.add(createBlackMarketRareRow(slot));
            if (i + 1 < settings.rareSlots.size()) {
                rarePanel.add(Box.createVerticalStrut(4));
            }
        }
        content.add(rarePanel);
        content.add(Box.createVerticalStrut(8));

        blackMarketMiscCategoryControls = new BlackMarketMiscCategoryControls[settings.miscCategories.size()];
        JPanel miscWrapper = new JPanel();
        miscWrapper.setLayout(new BoxLayout(miscWrapper, BoxLayout.Y_AXIS));
        stylePanel(miscWrapper);
        miscWrapper.setBorder(createGroupBorder("Tạp Hóa Hắc Thị"));
        for (int categoryIndex = 0; categoryIndex < settings.miscCategories.size(); categoryIndex++) {
            AdminApiClient.BlackMarketMiscCategory categoryData = settings.miscCategories.get(categoryIndex);
            BlackMarketMiscCategoryControls category = new BlackMarketMiscCategoryControls();
            category.categoryIndex = categoryData.categoryIndex;
            category.label = categoryData.label;
            category.slots = new BlackMarketMiscSlotControls[categoryData.slots.size()];
            for (int slotIndex = 0; slotIndex < categoryData.slots.size(); slotIndex++) {
                AdminApiClient.BlackMarketMiscSlot slotData = categoryData.slots.get(slotIndex);
                BlackMarketMiscSlotControls slot = new BlackMarketMiscSlotControls();
                slot.slotLabel = slotData.slotLabel;
                slot.optionCombo = new JComboBox<AdminApiClient.BlackMarketOption>(
                        categoryData.options.toArray(new AdminApiClient.BlackMarketOption[0]));
                selectBlackMarketOption(slot.optionCombo, slotData.optionIndex);
                slot.amountSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(slotData.amount, 1, 1000000, 1));
                slot.priceSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(slotData.priceAnHacThi, 0, 1000000, 1));
                category.slots[slotIndex] = slot;
            }
            blackMarketMiscCategoryControls[categoryIndex] = category;
            miscWrapper.add(createBlackMarketMiscCategoryPanel(category));
            if (categoryIndex + 1 < settings.miscCategories.size()) {
                miscWrapper.add(Box.createVerticalStrut(6));
            }
        }
        content.add(miscWrapper);

        blackMarketContentPanel.removeAll();
        JPanel north = new JPanel(new BorderLayout());
        north.add(content, BorderLayout.NORTH);
        blackMarketContentPanel.add(new JScrollPane(north), BorderLayout.CENTER);
        blackMarketContentPanel.revalidate();
        blackMarketContentPanel.repaint();

        blackMarketMetaShardPrice = shardPriceSpinner;
        blackMarketMetaShardLimit = shardLimitSpinner;
        blackMarketMetaHotCratePrice = hotCrateSpinner;
    }

    private JSpinner blackMarketMetaShardPrice;
    private JSpinner blackMarketMetaShardLimit;
    private JSpinner blackMarketMetaHotCratePrice;

    private void applyEventSettings() {
        if (eventRows.isEmpty()) {
            addLog("Chưa có dữ liệu sự kiện.");
            return;
        }
        submitCommand("áp dụng sự kiện", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.applyEventSettings(collectEventSettingsFromUi());
            }
        }, new Runnable() {
            @Override
            public void run() {
                refreshEventSettingsAsync(false);
            }
        });
    }

    private void applyLuckyBagSettings(final String actionName) {
        submitCommand(actionName, new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.applyLuckyBagSettings(collectLuckyBagSettingsFromUi());
            }
        }, new Runnable() {
            @Override
            public void run() {
                refreshLuckyBagSettingsAsync(false);
            }
        });
    }

    private void applyBlackMarketSettings() {
        if (blackMarketMetaShardPrice == null || blackMarketRareSlotControls == null) {
            addLog("Chưa có dữ liệu Chợ Đen.");
            return;
        }
        submitCommand("áp dụng Chợ Đen", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.applyBlackMarketSettings(collectBlackMarketSettingsFromUi());
            }
        }, new Runnable() {
            @Override
            public void run() {
                refreshBlackMarketSettingsAsync(false);
            }
        });
    }

    private List<AdminApiClient.EventSetting> collectEventSettingsFromUi() {
        List<AdminApiClient.EventSetting> settings = new ArrayList<AdminApiClient.EventSetting>();
        for (int i = 0; i < eventRows.size(); i++) {
            EventRow row = eventRows.get(i);
            settings.add(new AdminApiClient.EventSetting(row.key, row.label, comboIndexToEventValue(row.combo.getSelectedIndex())));
        }
        return settings;
    }

    private AdminApiClient.LuckyBagSettings collectLuckyBagSettingsFromUi() {
        AdminApiClient.LuckyBagSettings settings = new AdminApiClient.LuckyBagSettings();
        settings.dropRatePercent = ((Number) luckyBagDropRateSpinner.getValue()).doubleValue();
        settings.weightLuong = ((Number) luckyWeightLuong.getValue()).intValue();
        settings.weightLuongLock = ((Number) luckyWeightLuongKhoa.getValue()).intValue();
        settings.weightXu = ((Number) luckyWeightXu.getValue()).intValue();
        settings.weightHp = ((Number) luckyWeightHp.getValue()).intValue();
        settings.weightMp = ((Number) luckyWeightMp.getValue()).intValue();
        settings.amountLuongMin = ((Number) luckyAmountLuongMin.getValue()).intValue();
        settings.amountLuongMax = ((Number) luckyAmountLuongMax.getValue()).intValue();
        settings.amountLuongLockMin = ((Number) luckyAmountLuongKhoaMin.getValue()).intValue();
        settings.amountLuongLockMax = ((Number) luckyAmountLuongKhoaMax.getValue()).intValue();
        settings.amountXuMin = ((Number) luckyAmountXuMin.getValue()).intValue();
        settings.amountXuMax = ((Number) luckyAmountXuMax.getValue()).intValue();
        settings.amountHpMin = ((Number) luckyAmountHpMin.getValue()).intValue();
        settings.amountHpMax = ((Number) luckyAmountHpMax.getValue()).intValue();
        settings.amountMpMin = ((Number) luckyAmountMpMin.getValue()).intValue();
        settings.amountMpMax = ((Number) luckyAmountMpMax.getValue()).intValue();
        settings.maxOpenPerDay = ((Number) luckyMaxOpenPerDay.getValue()).intValue();
        return settings;
    }

    private AdminApiClient.BlackMarketSettings collectBlackMarketSettingsFromUi() {
        AdminApiClient.BlackMarketSettings settings = new AdminApiClient.BlackMarketSettings();
        settings.shardPriceAn = ((Number) blackMarketMetaShardPrice.getValue()).intValue();
        settings.shardMaxBuyPerPeriod = ((Number) blackMarketMetaShardLimit.getValue()).intValue();
        settings.hotCratePriceAn = ((Number) blackMarketMetaHotCratePrice.getValue()).intValue();

        if (blackMarketRareSlotControls != null) {
            for (int i = 0; i < blackMarketRareSlotControls.length; i++) {
                BlackMarketRareSlotControls slot = blackMarketRareSlotControls[i];
                AdminApiClient.BlackMarketOption option = (AdminApiClient.BlackMarketOption) slot.optionCombo.getSelectedItem();
                settings.rareSlots.add(new AdminApiClient.BlackMarketRareSlot(
                        slot.slotLabel,
                        option == null ? 0 : option.optionIndex,
                        ((Number) slot.priceSpinner.getValue()).intValue()
                ));
            }
        }

        if (blackMarketMiscCategoryControls != null) {
            for (int categoryIndex = 0; categoryIndex < blackMarketMiscCategoryControls.length; categoryIndex++) {
                BlackMarketMiscCategoryControls category = blackMarketMiscCategoryControls[categoryIndex];
                AdminApiClient.BlackMarketMiscCategory snapshot =
                        new AdminApiClient.BlackMarketMiscCategory(category.categoryIndex, category.label);
                for (int slotIndex = 0; slotIndex < category.slots.length; slotIndex++) {
                    BlackMarketMiscSlotControls slot = category.slots[slotIndex];
                    snapshot.options.addAll(readOptionsFromCombo(slot.optionCombo));
                    break;
                }
                for (int slotIndex = 0; slotIndex < category.slots.length; slotIndex++) {
                    BlackMarketMiscSlotControls slot = category.slots[slotIndex];
                    AdminApiClient.BlackMarketOption option = (AdminApiClient.BlackMarketOption) slot.optionCombo.getSelectedItem();
                    snapshot.slots.add(new AdminApiClient.BlackMarketMiscSlot(
                            slot.slotLabel,
                            option == null ? 0 : option.optionIndex,
                            ((Number) slot.amountSpinner.getValue()).intValue(),
                            ((Number) slot.priceSpinner.getValue()).intValue()
                    ));
                }
                settings.miscCategories.add(snapshot);
            }
        }
        settings.rareOptions = readOptionsFromRareControls();
        return settings;
    }

    private List<AdminApiClient.BlackMarketOption> readOptionsFromRareControls() {
        List<AdminApiClient.BlackMarketOption> options = new ArrayList<AdminApiClient.BlackMarketOption>();
        if (blackMarketRareSlotControls == null || blackMarketRareSlotControls.length == 0) {
            return options;
        }
        options.addAll(readOptionsFromCombo(blackMarketRareSlotControls[0].optionCombo));
        return options;
    }

    private List<AdminApiClient.BlackMarketOption> readOptionsFromCombo(JComboBox<AdminApiClient.BlackMarketOption> combo) {
        List<AdminApiClient.BlackMarketOption> options = new ArrayList<AdminApiClient.BlackMarketOption>();
        for (int i = 0; i < combo.getItemCount(); i++) {
            AdminApiClient.BlackMarketOption option = combo.getItemAt(i);
            if (option != null) {
                options.add(option);
            }
        }
        return options;
    }

    private void showAnnounceDialog() {
        final JTextArea messageArea = new JTextArea(4, 25);
        final JRadioButton topRadio = new JRadioButton("Thông Báo Trên");
        final JRadioButton middleRadio = new JRadioButton("Thông Báo Giữa");
        final JRadioButton bottomRadio = new JRadioButton("Thông Báo Dưới");
        ButtonGroup group = new ButtonGroup();
        group.add(topRadio);
        group.add(middleRadio);
        group.add(bottomRadio);
        topRadio.setSelected(true);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(topRadio);
        radioPanel.add(middleRadio);
        radioPanel.add(bottomRadio);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Nhập nội dung thông báo:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        panel.add(radioPanel, BorderLayout.SOUTH);

        int result = showConfirmDialog(this, panel, "Gửi Thông Báo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        final String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        final String type = topRadio.isSelected() ? "top" : (middleRadio.isSelected() ? "middle" : "bottom");
        submitCommand("gửi thông báo", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.announce(type, message);
            }
        }, null);
    }

    private void startMaintenanceMode() {
        String input = JOptionPane.showInputDialog(this, "Nhập số phút trước khi bảo trì (1-60):", "5");
        if (input == null) {
            return;
        }
        final int minutes;
        try {
            minutes = Integer.parseInt(input.trim());
            if (minutes < 1 || minutes > 60) {
                throw new NumberFormatException();
            }
        } catch (Exception ex) {
            showMessageDialog(this, "Vui lòng nhập số phút hợp lệ (1-60).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = showConfirmDialog(this,
                "Xác nhận bảo trì sau " + minutes + " phút?",
                "Xác Nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        submitCommand("đặt bảo trì", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.scheduleMaintenance(minutes);
            }
        }, null);
    }

    private void cancelMaintenanceMode() {
        int confirm = showConfirmDialog(this,
                "Bạn có chắc muốn hủy bảo trì?",
                "Xác Nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        submitCommand("hủy bảo trì", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.cancelMaintenance();
            }
        }, null);
    }

    private void handleKickPlayer() {
        final String name = JOptionPane.showInputDialog(this, "Nhập tên nhân vật cần kick:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        submitCommand("kick người chơi", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.kickPlayer(name.trim());
            }
        }, null);
    }

    private void handleBanAccount() {
        final String name = JOptionPane.showInputDialog(this, "Nhập tên nhân vật cần khóa vĩnh viễn:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        submitCommand("khóa tài khoản", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.banAccount(name.trim());
            }
        }, null);
    }

    private void handleUnbanAccount() {
        final String name = JOptionPane.showInputDialog(this, "Nhập tên nhân vật cần mở khóa:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        submitCommand("mở khóa tài khoản", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.unbanAccount(name.trim());
            }
        }, null);
    }

    private void handleChangePassword() {
        final JTextField usernameField = new JTextField();
        final JTextField passwordField = new JTextField();
        final JTextField retypeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Tài khoản:"));
        panel.add(usernameField);
        panel.add(new JLabel("Mật khẩu mới:"));
        panel.add(passwordField);
        panel.add(new JLabel("Nhập lại mật khẩu:"));
        panel.add(retypeField);

        int result = showConfirmDialog(this, panel, "Đổi Mật Khẩu Tài Khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        final String username = usernameField.getText().trim();
        final String pass1 = passwordField.getText();
        final String pass2 = retypeField.getText();
        if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
            showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!pass1.equals(pass2)) {
            showMessageDialog(this, "Mật khẩu nhập lại không khớp.");
            return;
        }

        submitCommand("đổi mật khẩu", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.changePassword(username, pass1);
            }
        }, null);
    }

    private void showGemManagerDialog() {
        Object[] options = {"Kiểm Tra Gem", "Thu Hồi Gem", "Hủy"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Chọn chức năng quản lý gem:",
                "Quản Lý Gem",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == 0) {
            showGemCheckDialog();
        } else if (choice == 1) {
            showGemRevokeDialog();
        }
    }

    private void showGemCheckDialog() {
        final JTextField charNameField = new JTextField(15);
        final JTextField gemIdsField = new JTextField(15);
        final JTextField quantityField = new JTextField(15);
        final JComboBox<String> compareBox = new JComboBox<String>(new String[]{
                "Lớn hơn hoặc bằng", "Nhỏ hơn hoặc bằng", "Bằng"
        });
        final JRadioButton unlockedGemButton = new JRadioButton("Gem Mở");
        final JRadioButton lockedGemButton = new JRadioButton("Gem Khóa");
        final JRadioButton bothGemButton = new JRadioButton("Cả Hai");
        ButtonGroup gemTypeGroup = new ButtonGroup();
        gemTypeGroup.add(unlockedGemButton);
        gemTypeGroup.add(lockedGemButton);
        gemTypeGroup.add(bothGemButton);
        unlockedGemButton.setSelected(true);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Tên nhân vật:"));
        inputPanel.add(charNameField);
        inputPanel.add(new JLabel("ID gem (để trống = tất cả):"));
        inputPanel.add(gemIdsField);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(quantityField);
        JPanel gemTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gemTypePanel.add(unlockedGemButton);
        gemTypePanel.add(lockedGemButton);
        gemTypePanel.add(bothGemButton);
        inputPanel.add(new JLabel("Loại gem:"));
        inputPanel.add(gemTypePanel);
        inputPanel.add(new JLabel("Điều kiện:"));
        inputPanel.add(compareBox);

        int result = showConfirmDialog(this, inputPanel, "Kiểm Tra Gem", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        final String charName = charNameField.getText().trim();
        final String gemIds = gemIdsField.getText().trim();
        final String quantity = quantityField.getText().trim();
        if (quantity.isEmpty()) {
            showMessageDialog(this, "Vui lòng nhập số lượng cần kiểm tra.");
            return;
        }
        final String compareType = compareBox.getSelectedIndex() == 1 ? "less"
                : (compareBox.getSelectedIndex() == 2 ? "equal" : "greater");
        final String gemType = unlockedGemButton.isSelected() ? "soluong"
                : (lockedGemButton.isSelected() ? "slock" : "both");

        submitCommand("kiểm tra gem", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.checkGems(charName, gemIds, quantity, compareType, gemType);
            }
        }, null);
    }

    private void showGemRevokeDialog() {
        final JTextField charNameField = new JTextField(15);
        final JTextField gemIdsField = new JTextField(15);
        final JTextField amountField = new JTextField(15);
        final JRadioButton lockedGemButton = new JRadioButton("Gem Khóa");
        final JRadioButton unlockedGemButton = new JRadioButton("Gem Mở");
        ButtonGroup gemTypeGroup = new ButtonGroup();
        gemTypeGroup.add(lockedGemButton);
        gemTypeGroup.add(unlockedGemButton);
        unlockedGemButton.setSelected(true);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Tên nhân vật:"));
        inputPanel.add(charNameField);
        inputPanel.add(new JLabel("ID gem:"));
        inputPanel.add(gemIdsField);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(amountField);
        inputPanel.add(lockedGemButton);
        inputPanel.add(unlockedGemButton);

        int result = showConfirmDialog(this, inputPanel, "Thu Hồi Gem", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        final String charName = charNameField.getText().trim();
        final String gemIds = gemIdsField.getText().trim();
        final String amounts = amountField.getText().trim();
        final boolean locked = lockedGemButton.isSelected();
        if (charName.isEmpty() || gemIds.isEmpty() || amounts.isEmpty()) {
            showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        submitCommand("thu hồi gem", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.revokeGems(charName, gemIds, amounts, locked);
            }
        }, null);
    }

    private void refreshOnlinePlayersAndShow() {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<AdminApiClient.OnlinePlayerInfo> players = client.fetchOnlinePlayers();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            showOnlinePlayersDialog(players);
                        }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            addLog("Không lấy được danh sách online: " + ex.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void showOnlinePlayersDialog(List<AdminApiClient.OnlinePlayerInfo> players) {
        if (players == null || players.isEmpty()) {
            showMessageDialog(this, "Không có người chơi nào đang online.", "Danh Sách Người Chơi Online", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        final List<AdminApiClient.OnlinePlayerInfo> rows = new ArrayList<AdminApiClient.OnlinePlayerInfo>(players);
        DefaultTableModel model = new DefaultTableModel(new Object[]{
                "#", "Tên", "Tài Khoản", "Cấp Độ", "Xu", "Lượng", "Lượng Khóa", "Tọa Độ", "Thao Tác"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        for (int i = 0; i < rows.size(); i++) {
            AdminApiClient.OnlinePlayerInfo info = rows.get(i);
            model.addRow(new Object[]{
                    Integer.valueOf(info.index),
                    info.name,
                    info.username,
                    info.levelText,
                    Long.valueOf(info.xu),
                    Integer.valueOf(info.luong),
                    Integer.valueOf(info.luongLock),
                    info.location,
                    "Chỉnh sửa"
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.getColumnModel().getColumn(8).setCellRenderer(new OnlineActionButtonRenderer());
        table.getColumnModel().getColumn(8).setCellEditor(new OnlineActionButtonEditor(table, rows, this));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(900, 360));
        showMessageDialog(this, scroll, "Danh Sách Người Chơi Online (" + rows.size() + ")", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAmbientBotDashboard() {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.AmbientBotSnapshot snapshot = client.fetchAmbientBots();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JPanel panel = new JPanel(new BorderLayout(8, 8));

                            JTextArea summaryArea = new JTextArea(snapshot.summary);
                            summaryArea.setEditable(false);
                            summaryArea.setOpaque(false);
                            summaryArea.setLineWrap(true);
                            summaryArea.setWrapStyleWord(true);
                            panel.add(summaryArea, BorderLayout.NORTH);

                            DefaultTableModel model = new DefaultTableModel(new Object[]{
                                    "Ten", "Lv", "Vai tro", "Tinh cach", "Trang thai", "Map", "Ca", "Binh", "Clan"
                            }, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false;
                                }
                            };
                            for (int i = 0; i < snapshot.rows.size(); i++) {
                                AdminApiClient.AmbientBotRow row = snapshot.rows.get(i);
                                model.addRow(new Object[]{
                                        row.name, row.level, row.role, row.personality, row.status, row.map, row.ca, row.binh, row.clan
                                });
                            }

                            JTable table = new JTable(model);
                            table.setAutoCreateRowSorter(true);
                            table.setRowHeight(22);
                            JScrollPane scrollPane = new JScrollPane(table);
                            scrollPane.setPreferredSize(new Dimension(760, 380));
                            panel.add(scrollPane, BorderLayout.CENTER);

                            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
                            bottom.add(new JLabel("So bot muc tieu (0-10000):"));
                            final JSpinner targetSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(snapshot.targetCount, 0, 10000, 10));
                            bottom.add(targetSpinner);
                            bottom.add(new JLabel("Đang online: " + snapshot.currentCount + " | Danh sách bot: " + snapshot.rosterCount));
                            panel.add(bottom, BorderLayout.SOUTH);

                            int confirm = showConfirmDialog(AdminLocalApp.this, panel, "Bot Môi Trường", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if (confirm != JOptionPane.OK_OPTION) {
                                return;
                            }

                            final int value = ((Number) targetSpinner.getValue()).intValue();
                            submitCommand("dat ambient bot", new CommandTask() {
                                @Override
                                public AdminApiClient.CommandResponse run() throws Exception {
                                    return client.setAmbientBotTarget(value);
                                }
                            }, null);
                        }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            addLog("Không tải được bot môi trường: " + ex.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void showLuongSon108Dashboard() {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.LuongSon108Snapshot snapshot = client.fetchLuongSon108();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JPanel panel = new JPanel(new BorderLayout(8, 8));

                            String summaryText = snapshot.summary == null || snapshot.summary.trim().isEmpty()
                                    ? "108 anh hùng Lương Sơn chưa được kích hoạt."
                                    : snapshot.summary;
                            JTextArea summaryArea = new JTextArea(summaryText
                                    + "\nCấu hình: chỉ chặn map Trường Giang | NPC thu phí ở Tứ quán (40,39) | phí bảo kê 10 lượng / 1 giờ.");
                            summaryArea.setEditable(false);
                            summaryArea.setOpaque(false);
                            summaryArea.setLineWrap(true);
                            summaryArea.setWrapStyleWord(true);
                            panel.add(summaryArea, BorderLayout.NORTH);

                            DefaultTableModel model = new DefaultTableModel(new Object[]{
                                    "Tên", "Lv", "Môn phái", "Trạng thái", "Mục tiêu", "Vị trí"
                            }, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false;
                                }
                            };
                            for (int i = 0; i < snapshot.rows.size(); i++) {
                                AdminApiClient.LuongSon108Row row = snapshot.rows.get(i);
                                model.addRow(new Object[]{row.name, row.level, row.charClass, row.status, row.target, row.location});
                            }

                            JTable table = new JTable(model);
                            table.setAutoCreateRowSorter(true);
                            table.setRowHeight(22);
                            JScrollPane scrollPane = new JScrollPane(table);
                            scrollPane.setPreferredSize(new Dimension(860, 380));
                            panel.add(scrollPane, BorderLayout.CENTER);

                            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
                            bottom.add(new JLabel("Tuyến chặn: Tứ quán -> Trường Giang"));
                            bottom.add(new JLabel("Đang online: " + snapshot.onlineCount + "/" + snapshot.heroCount));
                            panel.add(bottom, BorderLayout.SOUTH);

                            Object[] options = {"Khởi Động Chặn Đường", "Thu Hồi", "Đóng"};
                            int action = JOptionPane.showOptionDialog(
                                    AdminLocalApp.this,
                                    panel,
                                    "108 Lương Sơn",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    options,
                                    options[0]
                            );
                            if (action == 0) {
                                submitCommand("kích hoạt 108 Lương Sơn", new CommandTask() {
                                    @Override
                                    public AdminApiClient.CommandResponse run() throws Exception {
                                        return client.deployLuongSon108();
                                    }
                                }, null);
                            } else if (action == 1) {
                                submitCommand("thu hồi 108 Lương Sơn", new CommandTask() {
                                    @Override
                                    public AdminApiClient.CommandResponse run() throws Exception {
                                        return client.clearLuongSon108();
                                    }
                                }, null);
                            }
                        }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            addLog("Không tải được 108 Lương Sơn: " + ex.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void showNamedCharacterBuffDialog(String defaultName) {
        final JTextField nameField = new JTextField(defaultName == null ? "" : defaultName);
        final JTextField targetLevelField = new JTextField("0");
        final JTextField xuField = new JTextField("0");
        final JTextField luongField = new JTextField("0");
        final JTextField luongLockField = new JTextField("0");
        final JTextField skillPointField = new JTextField("0");
        final JTextField basePointField = new JTextField("0");

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.add(new JLabel("Tên nhân vật:"));
        panel.add(nameField);
        panel.add(new JLabel("Level mục tiêu (0 = bỏ qua):"));
        panel.add(targetLevelField);
        panel.add(new JLabel("Cộng thêm Xu:"));
        panel.add(xuField);
        panel.add(new JLabel("Cộng thêm Lượng:"));
        panel.add(luongField);
        panel.add(new JLabel("Cộng thêm Lượng khóa:"));
        panel.add(luongLockField);
        panel.add(new JLabel("Cộng thêm điểm kỹ năng:"));
        panel.add(skillPointField);
        panel.add(new JLabel("Cộng thêm điểm tiềm năng:"));
        panel.add(basePointField);
        panel.add(new JLabel("Hỗ trợ:"));
        panel.add(new JLabel("Nhân vật online và offline"));

        int result = showConfirmDialog(this, panel, "Buff Nhân Vật", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        final String playerName = nameField.getText().trim();
        if (playerName.isEmpty()) {
            showMessageDialog(this, "Vui lòng nhập tên nhân vật.");
            return;
        }

        submitCommand("buff nhân vật", new CommandTask() {
            @Override
            public AdminApiClient.CommandResponse run() throws Exception {
                return client.buffNamedCharacter(
                        playerName,
                        targetLevelField.getText().trim(),
                        xuField.getText().trim(),
                        luongField.getText().trim(),
                        luongLockField.getText().trim(),
                        skillPointField.getText().trim(),
                        basePointField.getText().trim()
                );
            }
        }, null);
    }

    private void submitCommand(final String action, final CommandTask task, final Runnable successFollowUp) {
        networkExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final AdminApiClient.CommandResponse response = task.run();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (response.status != null) {
                                currentStatus = response.status;
                                updateServerStatus(response.status);
                            }
                            setConnectedState(true);
                            addLog((response.ok ? "OK" : "LỖI") + " [" + action + "] " + response.message);
                            if (response.ok && successFollowUp != null) {
                                successFollowUp.run();
                            }
                        }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            setConnectedState(false);
                            addLog("LỖI [" + action + "] " + ex.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void updateServerStatus(AdminApiClient.ServerStatus status) {
        if (status == null) {
            updateOfflineStatus("Không có dữ liệu");
            return;
        }
        String state = "Đang Chạy";
        if ("maintenance".equalsIgnoreCase(status.serverState)) {
            state = "Đang Bảo Trì";
        } else if ("scheduled-maintenance".equalsIgnoreCase(status.serverState)) {
            state = "Chờ Bảo Trì";
        } else if ("offline".equalsIgnoreCase(status.serverState)) {
            state = "Ngoại Tuyến";
        }

        statusLabel.setText("Trạng thái: " + state);
        playersLabel.setText("Người chơi: " + status.onlinePlayers + "/" + status.playerLimit);
        memoryLabel.setText("Bộ nhớ: " + status.memoryUsedMb + " MB / " + status.memoryTotalMb + " MB");
        uptimeLabel.setText("Thời gian chạy: " + status.uptimeText);
        portLabel.setText("Cổng game: " + status.serverPort);

        maintenanceButton.setText(status.maintenanceScheduled || isMaintenanceState(status.serverState) ? "Hủy Bảo Trì" : "Bảo Trì");
        stopLoginButton.setText(status.stopLogin ? "Cho Phép Đăng Nhập" : "Dừng Đăng Nhập");
        enableCommandButtons(true);
    }

    private boolean isMaintenanceState(String state) {
        return "maintenance".equalsIgnoreCase(state) || "scheduled-maintenance".equalsIgnoreCase(state);
    }

    private void updateOfflineStatus(String label) {
        statusLabel.setText("Trạng thái: " + label);
        playersLabel.setText("Người chơi: -");
        memoryLabel.setText("Bộ nhớ: -");
        uptimeLabel.setText("Thời gian chạy: -");
        portLabel.setText("Cổng game: " + client.getPort());
        enableCommandButtons(false);
        updateButton.setEnabled(true);
    }

    private void setConnectedState(boolean connected) {
        if (connected) {
            statusLabel.setForeground(SUCCESS);
        } else {
            statusLabel.setForeground(DANGER);
        }
    }

    private void enableCommandButtons(boolean enabled) {
        JButton[] buttons = new JButton[]{
                maintenanceButton, stopLoginButton, cleanMemoryButton, announceButton, updateButton,
                lockAccountButton, banAccountButton, unbanAccountButton, changePasswordButton,
                gemManagerButton, listOnlineButton, ambientBotsButton, luongSon108Button, buffCharacterButton,
                luckyBagDropApplyButton, luckyRewardApplyButton, luckyAmountApplyButton,
                eventApplyButton, eventReloadButton, blackMarketApplyButton, blackMarketReloadButton
        };
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                buttons[i].setEnabled(enabled || buttons[i] == updateButton);
            }
        }
    }

    private int eventValueToIndex(int value) {
        if (value == 1) {
            return 1;
        }
        if (value == 0) {
            return 2;
        }
        return 0;
    }

    private int comboIndexToEventValue(int index) {
        if (index == 1) {
            return 1;
        }
        if (index == 2) {
            return 0;
        }
        return -1;
    }

    private void setButtonTooltips() {
        maintenanceButton.setToolTipText("Bật hoặc tắt chế độ bảo trì");
        stopLoginButton.setToolTipText("Dừng hoặc cho phép đăng nhập mới");
        cleanMemoryButton.setToolTipText("Dọn dẹp bộ nhớ server");
        announceButton.setToolTipText("Gửi thông báo đến toàn bộ người chơi");
        updateButton.setToolTipText("Làm mới trạng thái máy chủ");
        lockAccountButton.setToolTipText("Kick người chơi khỏi server");
        banAccountButton.setToolTipText("Khóa tài khoản vĩnh viễn");
        unbanAccountButton.setToolTipText("Mở khóa tài khoản");
        gemManagerButton.setToolTipText("Kiểm tra hoặc thu hồi gem của người chơi");
        listOnlineButton.setToolTipText("Liệt kê danh sách người chơi đang online");
        changePasswordButton.setToolTipText("Đổi mật khẩu tài khoản");
        ambientBotsButton.setToolTipText("Mở bảng bot môi trường và đặt lại số lượng mục tiêu");
        luongSon108Button.setToolTipText("Mở bảng 108 Lương Sơn chặn Trường Giang và thu hồi khi cần");
        buffCharacterButton.setToolTipText("Buff level, xu, lượng, điểm kỹ năng và điểm tiềm năng theo tên nhân vật");
        luckyBagDropApplyButton.setToolTipText("Cập nhật tỷ lệ rơi túi quà may mắn");
        luckyRewardApplyButton.setToolTipText("Cập nhật tỷ lệ phần thưởng túi quà may mắn");
        luckyAmountApplyButton.setToolTipText("Cập nhật min/max và giới hạn farm hoặc mở mỗi ngày");
    }

    private void addLog(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        logArea.append("[" + timestamp + "] " + message + "\n");
        String[] lines = logArea.getText().split("\n");
        int maxLines = 220;
        if (lines.length > maxLines) {
            StringBuilder sb = new StringBuilder();
            for (int i = lines.length - maxLines; i < lines.length; i++) {
                sb.append(lines[i]).append("\n");
            }
            logArea.setText(sb.toString());
        }
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void initializeRuntimeLogSources() {
        runtimeLogSources.clear();
        runtimeLogSources.add(new RuntimeLogSource("KHỞI ĐỘNG", new File("logs/runtime/server_launcher.log")));
        runtimeLogSources.add(new RuntimeLogSource("LOGIN", new File("logs/runtime/login_server_stdout.log")));
        runtimeLogSources.add(new RuntimeLogSource("LOGIN-ERR", new File("logs/runtime/login_server_stderr.log")));
        runtimeLogSources.add(new RuntimeLogSource("GAME", new File("logs/runtime/game_server_stdout.log")));
        runtimeLogSources.add(new RuntimeLogSource("GAME-ERR", new File("logs/runtime/game_server_stderr.log")));
        for (int i = 0; i < runtimeLogSources.size(); i++) {
            seedRuntimeLogSource(runtimeLogSources.get(i), 12);
        }
    }

    private void seedRuntimeLogSource(RuntimeLogSource source, int maxLines) {
        if (!source.file.isFile()) {
            source.position = 0L;
            source.pending = "";
            return;
        }
        List<String> lines = readLastLines(source.file, maxLines);
        for (int i = 0; i < lines.size(); i++) {
            String line = normalizeRuntimeLogLine(lines.get(i));
            if (!line.isEmpty()) {
                addLog("[" + source.label + "] " + line);
            }
        }
        source.position = source.file.length();
        source.pending = "";
    }

    private void pollRuntimeLogs() {
        for (int i = 0; i < runtimeLogSources.size(); i++) {
            pollRuntimeLog(runtimeLogSources.get(i));
        }
    }

    private void pollRuntimeLog(RuntimeLogSource source) {
        try {
            if (!source.file.isFile()) {
                source.position = 0L;
                source.pending = "";
                return;
            }

            long length = source.file.length();
            if (length < source.position) {
                source.position = 0L;
                source.pending = "";
            }
            if (length == source.position) {
                return;
            }

            if (length - source.position > 262144L) {
                source.position = Math.max(0L, length - 262144L);
                source.pending = "";
                addLog("[" + source.label + "] Bỏ qua một đoạn log cũ để tiếp tục theo dõi log mới.");
            }

            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(source.file, "r");
                raf.seek(source.position);
                int size = (int) (length - source.position);
                byte[] data = new byte[size];
                raf.readFully(data);
                source.position = length;

                String text = source.pending + new String(data, StandardCharsets.UTF_8);
                text = text.replace("\r\n", "\n").replace('\r', '\n');
                boolean endsWithNewLine = text.endsWith("\n");
                String[] parts = text.split("\n", -1);
                int limit = endsWithNewLine ? parts.length : parts.length - 1;
                for (int i = 0; i < limit; i++) {
                    String line = normalizeRuntimeLogLine(parts[i]);
                    if (!line.isEmpty()) {
                        addLog("[" + source.label + "] " + line);
                    }
                }
                source.pending = endsWithNewLine || parts.length == 0 ? "" : parts[parts.length - 1];
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private List<String> readLastLines(File file, int maxLines) {
        List<String> result = new ArrayList<String>();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long length = raf.length();
            long start = Math.max(0L, length - 32768L);
            raf.seek(start);
            byte[] data = new byte[(int) (length - start)];
            raf.readFully(data);
            String text = new String(data, StandardCharsets.UTF_8).replace("\r\n", "\n").replace('\r', '\n');
            String[] lines = text.split("\n");
            int begin = Math.max(0, lines.length - maxLines);
            for (int i = begin; i < lines.length; i++) {
                String line = normalizeRuntimeLogLine(lines[i]);
                if (!line.isEmpty()) {
                    result.add(line);
                }
            }
        } catch (Exception ignored) {
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception ignored) {
                }
            }
        }
        return result;
    }

    private String normalizeRuntimeLogLine(String line) {
        if (line == null) {
            return "";
        }
        return line.trim();
    }

    private JLabel createStatusLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BASE_FONT);
        label.setForeground(TEXT_PRIMARY);
        label.setBorder(new EmptyBorder(2, 5, 2, 5));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(TITLE_FONT);
        button.setBackground(PANEL_BG);
        button.setForeground(TEXT_PRIMARY);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return button;
    }

    private TitledBorder createGroupBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                title
        );
        border.setTitleFont(TITLE_FONT);
        border.setTitleColor(TEXT_MUTED);
        return border;
    }

    private void stylePanel(JPanel panel) {
        panel.setBackground(PANEL_BG);
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(PANEL_BG);
    }

    private void styleSpinner(JSpinner... spinners) {
        for (int i = 0; i < spinners.length; i++) {
            JSpinner spinner = spinners[i];
            if (spinner != null) {
                spinner.setFont(BASE_FONT);
            }
        }
    }

    private void applyComponentStyles() {
        styleButton(updateButton, ACCENT, Color.WHITE);
        styleButton(eventApplyButton, ACCENT, Color.WHITE);
        styleButton(blackMarketApplyButton, ACCENT, Color.WHITE);
        styleButton(luckyBagDropApplyButton, ACCENT, Color.WHITE);
        styleButton(luckyRewardApplyButton, ACCENT, Color.WHITE);
        styleButton(luckyAmountApplyButton, ACCENT, Color.WHITE);
        styleButton(announceButton, new Color(88, 101, 242), Color.WHITE);
        styleButton(maintenanceButton, new Color(224, 133, 36), Color.WHITE);
        styleButton(banAccountButton, DANGER, Color.WHITE);
        styleButton(lockAccountButton, new Color(231, 76, 60), Color.WHITE);
        styleButton(unbanAccountButton, SUCCESS, Color.WHITE);
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        if (button == null) {
            return;
        }
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(background.darker()),
                new EmptyBorder(8, 12, 8, 12)
        ));
    }

    private JPanel createButtonPanel(String title, JButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        stylePanel(panel);
        panel.setBorder(createGroupBorder(title));
        for (int i = 0; i < buttons.length; i++) {
            addButtonToPanel(panel, buttons[i]);
        }
        return panel;
    }

    private void addButtonToPanel(JPanel panel, JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = button.getPreferredSize();
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, size.height));
        button.setMinimumSize(new Dimension(0, size.height));
        button.setPreferredSize(new Dimension(0, size.height));
        button.setMargin(new Insets(4, 10, 4, 10));
        panel.add(button);
        panel.add(Box.createVerticalStrut(5));
    }

    private JScrollPane wrapScrollable(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        styleScrollPane(scrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createBlackMarketNumberRow(String label, JSpinner spinner, String suffix) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(new JLabel(label));
        row.add(Box.createHorizontalStrut(8));
        Dimension size = spinner.getPreferredSize();
        spinner.setMaximumSize(new Dimension(110, size.height));
        row.add(spinner);
        if (suffix != null && !suffix.isEmpty()) {
            row.add(Box.createHorizontalStrut(6));
            row.add(new JLabel(suffix));
        }
        row.add(Box.createHorizontalGlue());
        return row;
    }

    private JPanel createBlackMarketRareRow(BlackMarketRareSlotControls slot) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(new JLabel(slot.slotLabel));
        row.add(Box.createHorizontalStrut(8));
        slot.optionCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, slot.optionCombo.getPreferredSize().height));
        row.add(slot.optionCombo);
        row.add(Box.createHorizontalStrut(8));
        row.add(new JLabel("Giá"));
        row.add(Box.createHorizontalStrut(4));
        Dimension size = slot.priceSpinner.getPreferredSize();
        slot.priceSpinner.setMaximumSize(new Dimension(100, size.height));
        row.add(slot.priceSpinner);
        row.add(Box.createHorizontalStrut(4));
        row.add(new JLabel("Ẩn"));
        return row;
    }

    private JPanel createBlackMarketMiscCategoryPanel(BlackMarketMiscCategoryControls category) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        stylePanel(panel);
        panel.setBorder(createGroupBorder(category.label));
        for (int i = 0; i < category.slots.length; i++) {
            BlackMarketMiscSlotControls slot = category.slots[i];
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.add(new JLabel(slot.slotLabel));
            row.add(Box.createHorizontalStrut(8));
            slot.optionCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, slot.optionCombo.getPreferredSize().height));
            row.add(slot.optionCombo);
            row.add(Box.createHorizontalStrut(8));
            row.add(new JLabel("SL"));
            row.add(Box.createHorizontalStrut(4));
            Dimension amountSize = slot.amountSpinner.getPreferredSize();
            slot.amountSpinner.setMaximumSize(new Dimension(80, amountSize.height));
            row.add(slot.amountSpinner);
            row.add(Box.createHorizontalStrut(8));
            row.add(new JLabel("Giá"));
            row.add(Box.createHorizontalStrut(4));
            Dimension priceSize = slot.priceSpinner.getPreferredSize();
            slot.priceSpinner.setMaximumSize(new Dimension(90, priceSize.height));
            row.add(slot.priceSpinner);
            row.add(Box.createHorizontalStrut(4));
            row.add(new JLabel("Ẩn"));
            panel.add(row);
            if (i + 1 < category.slots.length) {
                panel.add(Box.createVerticalStrut(3));
            }
        }
        return panel;
    }

    private void selectBlackMarketOption(JComboBox<AdminApiClient.BlackMarketOption> combo, int optionIndex) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            AdminApiClient.BlackMarketOption option = combo.getItemAt(i);
            if (option != null && option.optionIndex == optionIndex) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    private JLabel createLoadingLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(TEXT_MUTED);
        label.setBorder(new EmptyBorder(16, 8, 16, 8));
        return label;
    }

    private void showMessageDialog(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }

    private void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }

    private int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
    }

    private int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
    }

    private void shutdown() {
        if (pollTimer != null) {
            pollTimer.stop();
        }
        if (runtimeLogTimer != null) {
            runtimeLogTimer.stop();
        }
        networkExecutor.shutdownNow();
    }

    private static int parseInt(String raw, int fallback) {
        if (raw == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ignored) {
            return fallback;
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            logStartupFailure("Không thể mở panel admin riêng trong phiên headless. Hãy mở bằng tài khoản Windows đang đăng nhập desktop.", null);
            return;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new AdminLocalApp().setVisible(true);
                } catch (Throwable ex) {
                    logStartupFailure("Khởi động panel admin riêng thất bại.", ex);
                }
            }
        });
    }

    private static void logStartupFailure(String message, Throwable ex) {
        PrintWriter writer = null;
        try {
            File logDir = new File("logs", "runtime");
            if (!logDir.isDirectory()) {
                logDir.mkdirs();
            }
            File logFile = new File(logDir, "admin_panel_clone.err.log");
            writer = new PrintWriter(new FileWriter(logFile, true));
            writer.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] " + message);
            if (ex != null) {
                ex.printStackTrace(writer);
            }
        } catch (Exception ignored) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private interface CommandTask {
        AdminApiClient.CommandResponse run() throws Exception;
    }

    private static final class EventRow {
        private String key;
        private String label;
        private JComboBox<String> combo;
    }

    private static final class BlackMarketRareSlotControls {
        private String slotLabel;
        private JComboBox<AdminApiClient.BlackMarketOption> optionCombo;
        private JSpinner priceSpinner;
    }

    private static final class BlackMarketMiscSlotControls {
        private String slotLabel;
        private JComboBox<AdminApiClient.BlackMarketOption> optionCombo;
        private JSpinner amountSpinner;
        private JSpinner priceSpinner;
    }

    private static final class BlackMarketMiscCategoryControls {
        private int categoryIndex;
        private String label;
        private BlackMarketMiscSlotControls[] slots;
    }

    private static final class OnlineActionButtonRenderer extends JButton implements TableCellRenderer {
        private OnlineActionButtonRenderer() {
            setText("Chỉnh sửa");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private static final class OnlineActionButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private final JTable table;
        private final List<AdminApiClient.OnlinePlayerInfo> rows;
        private final AdminLocalApp app;
        private int selectedRow = -1;

        private OnlineActionButtonEditor(JTable table, List<AdminApiClient.OnlinePlayerInfo> rows, AdminLocalApp app) {
            this.table = table;
            this.rows = rows;
            this.app = app;
            this.button = new JButton("Chỉnh sửa");
            this.button.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return "Chỉnh sửa";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.selectedRow = row;
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            if (selectedRow < 0 || selectedRow >= table.getRowCount()) {
                return;
            }
            int modelRow = table.convertRowIndexToModel(selectedRow);
            final AdminApiClient.OnlinePlayerInfo info = rows.get(modelRow);
            Object[] options = {"Buff Nhân Vật", "Kick", "Đóng"};
            int choice = JOptionPane.showOptionDialog(
                    app,
                    "Chọn thao tác cho " + info.name,
                    "Người Chơi Online",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == 0) {
                app.showNamedCharacterBuffDialog(info.name);
            } else if (choice == 1) {
                app.submitCommand("kick người chơi", new CommandTask() {
                    @Override
                    public AdminApiClient.CommandResponse run() throws Exception {
                        return app.client.kickPlayer(info.name);
                    }
                }, null);
            }
        }
    }

    private static final class NamedThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "AdminLocalNetwork");
            thread.setDaemon(true);
            return thread;
        }
    }

    private static final class RuntimeLogSource {
        private final String label;
        private final File file;
        private long position;
        private String pending;

        private RuntimeLogSource(String label, File file) {
            this.label = label;
            this.file = file;
            this.position = 0L;
            this.pending = "";
        }
    }
}
