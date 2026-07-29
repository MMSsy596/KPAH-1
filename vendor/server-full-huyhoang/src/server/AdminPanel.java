package server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import data.Animal;
import io.Message;
import io.SessionManager;
import real.AmbientBotManager;
import real.AdminHandler;
import real.CharManager;
import real.MessageCreator;
import real.LuongSon108Manager;
import real.cmd.LoginHandler;
import real.Char;
import data.Database;
import real.Item;
import javax.swing.table.JTableHeader;
import real.Map;
import real.LevelDetail;
import server.Panel.*;

public class AdminPanel extends JFrame {

    private JPanel mainPanel;

    // Server status components
    private JLabel statusLabel;
    private JLabel playersLabel;
    private JLabel memoryLabel;
    private JLabel uptimeLabel;
    private JLabel portLabel;

    // Control buttons
    private JButton maintenanceButton;
    private JButton stopLoginButton;
    private JButton cleanMemoryButton;
    private JButton announceButton;
    private JButton updateButton;
    private JButton lockAccountButton;
    private JButton banAccountButton;
    private JButton unbanAccountButton;
    private JButton gemManagerButton;
    private JButton listOnlineButton;
    private JButton changePasswordButton;
    private JButton ambientBotsButton;
    private JButton luongSon108Button;
    private JButton buffCharacterButton;
    private JSpinner luckyBagDropRateSpinner;
    private JButton luckyBagDropApplyButton;
    private JSpinner luckyWeightLuong;
    private JSpinner luckyWeightLuongKhoa;
    private JSpinner luckyWeightXu;
    private JSpinner luckyWeightHp;
    private JSpinner luckyWeightMp;
    private JSpinner luckyWeightExp;
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
    private JSpinner luckyAmountExpMin;
    private JSpinner luckyAmountExpMax;
    private JButton luckyAmountApplyButton;
    private JSpinner luckyMaxOpenPerDay;
    private JSpinner blackMarketShardPriceSpinner;
    private JSpinner blackMarketShardLimitSpinner;
    private JSpinner blackMarketHotCratePriceSpinner;
    private JButton blackMarketApplyButton;
    private JButton blackMarketReloadButton;
    private BlackMarketRareSlotControls[] blackMarketRareSlotControls;
    private BlackMarketMiscCategoryControls[] blackMarketMiscCategoryControls;

    private JSpinner quanLyEvent;
    private java.util.List<EventRow> eventRows;
    private JButton eventApplyButton;
    private JButton eventReloadButton;

    // Log components
    private JTextArea logArea;
    private JScrollPane logScrollPane;

    private Timer maintenanceTimer;
    private Timer statsUpdateTimer;
    private int maintenanceMinutes = 5;

    private static final Set<String> NPC_NAMES = new HashSet<>();

    static {
        String[] npcNames = {
            "phat lo", "tho ren than bi", "tong quan", "thohopthanhsocap", "thohopthanhcaocap", "thay ngu hanh",
            "dau truong", "hoa tieu", "doi tieu", "tong tieu dau", "chuyenlanhtho", "xa phu", "ta pho thong",
            "ta thong linh", "huu thong linh", "tran thong linh", "hao duyen", "tho san", "le quan", "ky nang bang",
            "nguyet lao", "market", "dich tram", "nguyetlao", "quanly", "thoren", "trangbi", "cuahang", "nhaboss",
            "nhapet", "nhathu", "nhacaythan", "Xaphu", "lequan", "admin"
        };
        for (String name : npcNames) {
            NPC_NAMES.add(name.toLowerCase());
        }
    }

    public AdminPanel() {
        setTitle("Quản Lý Máy Chủ");
        setSize(980, 680);
        setMinimumSize(new Dimension(860, 620));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();
        initEventHandlers();
        normalizeAdminUi();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = showConfirmDialog(
                        AdminPanel.this,
                        "Bạn có chắc chắn muốn đóng bảng điều khiển?",
                        "Xác Nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private static String uiText(String text) {
        return MessageCreator.normalizeUiText(text);
    }

    private static Object normalizeDialogObject(Object value) {
        if (value instanceof String) {
            return uiText((String) value);
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            Object[] normalized = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                normalized[i] = normalizeDialogObject(values[i]);
            }
            return normalized;
        }
        if (value instanceof Component) {
            normalizeComponentTree((Component) value);
        }
        return value;
    }

    private static void normalizeComponentTree(Component component) {
        if (component == null) {
            return;
        }
        if (component instanceof Frame) {
            ((Frame) component).setTitle(uiText(((Frame) component).getTitle()));
        }
        if (component instanceof Dialog) {
            ((Dialog) component).setTitle(uiText(((Dialog) component).getTitle()));
        }
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setText(uiText(button.getText()));
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            label.setText(uiText(label.getText()));
        }
        if (component instanceof JTabbedPane) {
            JTabbedPane tabbedPane = (JTabbedPane) component;
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setTitleAt(i, uiText(tabbedPane.getTitleAt(i)));
            }
        }
        if (component instanceof JTable) {
            JTable table = (JTable) component;
            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                Object headerValue = table.getColumnModel().getColumn(i).getHeaderValue();
                if (headerValue instanceof String) {
                    table.getColumnModel().getColumn(i).setHeaderValue(uiText((String) headerValue));
                }
            }
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                header.repaint();
            }
        }
        if (component instanceof JComponent) {
            javax.swing.border.Border border = ((JComponent) component).getBorder();
            if (border instanceof TitledBorder) {
                ((TitledBorder) border).setTitle(uiText(((TitledBorder) border).getTitle()));
            }
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                normalizeComponentTree(child);
            }
        }
    }

    private void normalizeAdminUi() {
        normalizeComponentTree(this);
        revalidate();
        repaint();
    }

    private void showMessageDialog(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, normalizeDialogObject(message));
    }

    private void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, normalizeDialogObject(message), uiText(title), messageType);
    }

    private int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, normalizeDialogObject(message), uiText(title), optionType);
    }

    private int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(parentComponent, normalizeDialogObject(message), uiText(title), optionType, messageType);
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpinnerNumberModel model = new SpinnerNumberModel(
                5, // giá trị mặc định
                1, // min
                60, // max
                1 // bước nhảy
        );

        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Server status components
        statusLabel = createStatusLabel("Trạng Thái: Đang chạy");
        playersLabel = createStatusLabel("Người chơi: 0/" + TeamServer.LIMIT_CCU);
        memoryLabel = createStatusLabel("Bộ Nhớ: 0MB");
        uptimeLabel = createStatusLabel("Thời Gian: 00:00:00");
        portLabel = createStatusLabel("Cổng: " + TeamServer.PORT);

        // Control buttons
        maintenanceButton = createButton("Bảo Trì");
        stopLoginButton = createButton("Dừng Đăng Nhập");
        cleanMemoryButton = createButton("Dọn Bộ Nhớ");
        announceButton = createButton("Thông Báo");
        updateButton = createButton("Cập Nhật");
        lockAccountButton = createButton("Kick Player");
        banAccountButton = createButton("Khóa Vĩnh Viễn");
        unbanAccountButton = createButton("Mở Khóa");
        gemManagerButton = createButton("Quản Lý Gem");
        listOnlineButton = createButton("Member Online");
        changePasswordButton = createButton("Đổi mật khẩu");
        ambientBotsButton = createButton("Ambient Bot");
        luongSon108Button = createButton("108 Luong Son");
        buffCharacterButton = createButton("Buff Nhan Vat");
        quanLyEvent = new JSpinner(model);
        initEventRows();
        initLuckyBagDropControls();
        initLuckyBagRewardControls();
        initLuckyBagAmountControls();
        initBlackMarketControls();
        loadLuckyBagSettingsFromMap();
        loadBlackMarketSettingsFromMap();

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logScrollPane = new JScrollPane(logArea);

        // Do an initial update of the status
        updateServerStatus();
        updateMemoryStatus();

        // Thêm tooltip cho các nút
        setButtonTooltips();

    }

    private void layoutComponents() {
        // Status panel
        JPanel statusPanel = new JPanel(new GridLayout(5, 1, 0, 2));
        statusPanel.setBorder(createGroupBorder("Thông Tin Máy Chủ"));
        statusPanel.add(statusLabel);
        statusPanel.add(playersLabel);
        statusPanel.add(memoryLabel);
        statusPanel.add(uptimeLabel);
        statusPanel.add(portLabel);

        // Log panel
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(createGroupBorder("Nhật Ký Hoạt Động"));
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        // Đặt chiều ngang cho statusPanel và logPanel bằng serverControlPanel
        Dimension panelWidth = new Dimension(250, 120);
        statusPanel.setPreferredSize(panelWidth);
        logPanel.setPreferredSize(panelWidth);

        // Panel ngang chứa status và log
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(statusPanel);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(logPanel);

        JPanel serverPanel = createButtonPanel("Quản Lý Server", maintenanceButton, stopLoginButton, cleanMemoryButton, announceButton, updateButton);
        JPanel accountPanel = createButtonPanel("Tài Khoản", banAccountButton, unbanAccountButton, lockAccountButton, changePasswordButton);
        JPanel playerPanel = createPlayerPanel();
        JPanel eventPanel = createEventPanel();
        JPanel blackMarketPanel = createBlackMarketPanel();
        Dimension panelSize = new Dimension(320, 560); // hoặc cao hơn nếu cần
        serverPanel.setPreferredSize(panelSize);
        accountPanel.setPreferredSize(panelSize);
        playerPanel.setPreferredSize(panelSize);
        eventPanel.setPreferredSize(panelSize);
        blackMarketPanel.setPreferredSize(panelSize);

        // Đảm bảo các panel căn top khi dùng BoxLayout
        serverPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        accountPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        playerPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        eventPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        blackMarketPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // ... existing code ...
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.add(serverPanel);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(accountPanel);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(playerPanel);
        controlsPanel.add(eventPanel);
        controlsPanel.add(blackMarketPanel);

        JTabbedPane controlsTabs = new JTabbedPane();
        controlsTabs.addTab("Server", wrapScrollable(serverPanel));
        controlsTabs.addTab("Tài Khoản", wrapScrollable(accountPanel));
        controlsTabs.addTab("Player", wrapScrollable(playerPanel));
        controlsTabs.addTab("Sự Kiện", wrapScrollable(eventPanel));
        controlsTabs.addTab("Chợ Đen", wrapScrollable(blackMarketPanel));

        JPanel controlsWrapper = new JPanel(new BorderLayout());
        controlsWrapper.add(controlsTabs, BorderLayout.CENTER);

        // Main layout
        mainPanel.removeAll();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(controlsWrapper, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void initEventHandlers() {
        maintenanceButton.addActionListener(e -> {
            if (!AdminHandler.isStopServer) {
                startMaintenanceMode();
            } else {
                cancelMaintenanceMode();
            }
        });

        stopLoginButton.addActionListener(e -> {
            boolean stopLogin = !LoginHandler.stopLogin;
            LoginHandler.stopLogin = stopLogin;
            stopLoginButton.setText(stopLogin ? "Cho Phép Đăng Nhập" : "Dừng Đăng Nhập");
            String message = stopLogin ? "Đã tắt đăng nhập mới" : "Đã bật đăng nhập mới";
            addLog(message);
        });

        cleanMemoryButton.addActionListener(e -> {
            System.gc();
            updateMemoryStatus();
            addLog("Đã dọn dẹp bộ nhớ");
        });

        announceButton.addActionListener(e -> {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JTextArea messageArea = new JTextArea(4, 25);
            JScrollPane scrollPane = new JScrollPane(messageArea);

            JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JRadioButton topRadio = new JRadioButton("Thông Báo Trên");
            JRadioButton middleRadio = new JRadioButton("Thông Báo Giữa");
            JRadioButton bottomRadio = new JRadioButton("Thông Báo Dưới");
            ButtonGroup group = new ButtonGroup();
            group.add(topRadio);
            group.add(middleRadio);
            group.add(bottomRadio);
            topRadio.setSelected(true);

            radioPanel.add(topRadio);
            radioPanel.add(middleRadio);
            radioPanel.add(bottomRadio);

            panel.add(new JLabel("Nhập nội dung thông báo:"), BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(radioPanel, BorderLayout.SOUTH);

            int result = showConfirmDialog(
                    this,
                    panel,
                    "Gửi Thông Báo",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String message = messageArea.getText().trim();
                if (!message.isEmpty()) {
                    try {
                        Message msg;
                        if (topRadio.isSelected()) {
                            msg = MessageCreator.createthongbao(message);
                        } else if (bottomRadio.isSelected()) {
                            msg = MessageCreator.createServerAlertAutoOffMessage(message);
                        } else {
                            msg = MessageCreator.createServerAlertMessage(message, "");
                        }
                        for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                            CharManager.instance.vChars.elementAt(j).sendMessage(msg);
                        }
                        String type = topRadio.isSelected() ? "Thông Báo Trên" : (middleRadio.isSelected() ? "Thông Báo Giữa" : "Thông Báo Dưới");
                        addLog("Đã gửi " + type + ": " + message);
                    } catch (IOException ex) {
                        addLog("Lỗi gửi thông báo: " + ex.getMessage());
                    }
                }
            }
        });

        updateButton.addActionListener(e -> {
            updateServerStatus();
            updateMemoryStatus();
            addLog("Đã cập nhật trạng thái máy chủ");
        });

        lockAccountButton.addActionListener(e -> handleKickPlayer());

        banAccountButton.addActionListener(e -> handleBanAccount());

        unbanAccountButton.addActionListener(e -> handleUnbanAccount());

        if (eventApplyButton != null) {
            eventApplyButton.addActionListener(e -> applyEventSettings());
        }
        if (eventReloadButton != null) {
            eventReloadButton.addActionListener(e -> reloadEventSettings());
        }
        if (blackMarketApplyButton != null) {
            blackMarketApplyButton.addActionListener(e -> applyBlackMarketSettings());
        }
        if (blackMarketReloadButton != null) {
            blackMarketReloadButton.addActionListener(e -> reloadBlackMarketSettings());
        }

        gemManagerButton.addActionListener(e -> {
            String[] options = {"Kiểm Tra Gem", "Thu Hồi Gem", "Hủy"};
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
                // --- Đoạn code cũ của checkHackButton ---
                // Tạo dialog nhập liệu
                JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

                // Components
                JTextField charNameField = new JTextField(15);
                JTextField gemIdsField = new JTextField(15);
                JTextField quantityField = new JTextField(15);

                // Radio buttons cho loại gem
                JRadioButton unlockedGemButton = new JRadioButton("Gem Mở");
                JRadioButton lockedGemButton = new JRadioButton("Gem Khóa");
                JRadioButton bothGemButton = new JRadioButton("Cả Hai");
                ButtonGroup gemTypeGroup = new ButtonGroup();
                gemTypeGroup.add(unlockedGemButton);
                gemTypeGroup.add(lockedGemButton);
                gemTypeGroup.add(bothGemButton);
                unlockedGemButton.setSelected(true);

                // Combobox cho điều kiện so sánh
                JComboBox<String> compareBox = new JComboBox<>(new String[]{
                    "Lớn hơn hoặc bằng", "Nhỏ hơn hoặc bằng", "Bằng"
                });

                // Layout
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

                int option = showConfirmDialog(
                        this,
                        inputPanel,
                        "Kiểm Tra Gem",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                    String charName = charNameField.getText().trim();
                    String gemIds = gemIdsField.getText().trim();
                    String quantity = quantityField.getText().trim();

                    // Validate input
                    if (quantity.isEmpty()) {
                        showMessageDialog(this, "Vui lòng nhập số lượng cần kiểm tra!");
                        return;
                    }

                    try {
                        Integer.parseInt(quantity);
                    } catch (NumberFormatException ex) {
                        showMessageDialog(this, "Số lượng phải là số!");
                        return;
                    }

                    // Xác định điều kiện so sánh
                    String compareType = "";
                    switch (compareBox.getSelectedIndex()) {
                        case 0:
                            compareType = "greater";
                            break;
                        case 1:
                            compareType = "less";
                            break;
                        case 2:
                            compareType = "equal";
                            break;
                    }

                    // Xác định loại gem cần kiểm tra
                    String gemType = "";
                    if (unlockedGemButton.isSelected()) {
                        gemType = "soluong";
                    } else if (lockedGemButton.isSelected()) {
                        gemType = "slock";
                    } else {
                        gemType = "both";
                    }

                    try {
                        Database.checkDetailGems(gemIds, quantity, compareType, charName, "check_gem", gemType);
                        addLog("Đã kiểm tra gem thành công. Xem kết quả trong file check_gem.txt");
                    } catch (Exception ex) {
                        addLog("Lỗi khi kiểm tra gem: " + ex.getMessage());
                    }
                }
            } else if (choice == 1) {
                // --- Đoạn code cũ của revokeItemButton ---
                // Tạo dialog nhập liệu
                JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

                // Components
                JTextField charNameField = new JTextField(15);
                JTextField gemIdsField = new JTextField(15);
                JTextField amountField = new JTextField(15);
                JRadioButton lockedGemButton = new JRadioButton("Gem Khóa");
                JRadioButton unlockedGemButton = new JRadioButton("Gem Mở");

                ButtonGroup gemTypeGroup = new ButtonGroup();
                gemTypeGroup.add(lockedGemButton);
                gemTypeGroup.add(unlockedGemButton);
                unlockedGemButton.setSelected(true);

                // Layout
                inputPanel.add(new JLabel("Tên nhân vật:"));
                inputPanel.add(charNameField);
                inputPanel.add(new JLabel("ID gem:"));
                inputPanel.add(gemIdsField);
                inputPanel.add(new JLabel("Số lượng:"));
                inputPanel.add(amountField);
                inputPanel.add(lockedGemButton);
                inputPanel.add(unlockedGemButton);

                int option = showConfirmDialog(
                        this,
                        inputPanel,
                        "Thu Hồi Gem",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                    String charName = charNameField.getText().trim();
                    String[] itemIds = gemIdsField.getText().trim().split(",");
                    String[] quantities = amountField.getText().trim().split(",");
                    boolean isLockedGem = lockedGemButton.isSelected();

                    // Validate input
                    if (charName.isEmpty()) {
                        showMessageDialog(this, "Vui lòng nhập tên nhân vật!");
                        return;
                    }
                    if (itemIds.length != quantities.length) {
                        showMessageDialog(this, "Số lượng ID và số lượng vật phẩm không khớp!");
                        return;
                    }

                    try {
                        // Kiểm tra người chơi có online không
                        Char player = CharManager.instance.getCharByCharName(charName);
                        if (player != null) {
                            showMessageDialog(this, "Người chơi đang online. Vui lòng chờ người chơi offline!");
                            return;
                        }

                        Connection conn = Database.instance.getConnection();
                        Statement stmt = conn.createStatement();

                        // Thu hồi từng gem
                        StringBuilder logMsg = new StringBuilder();
                        logMsg.append("Thu hồi ").append(isLockedGem ? "gem khóa" : "gem mở")
                                .append(" từ người chơi: ").append(charName).append("\n");

                        // Lấy dữ liệu gem hiện tại
                        String sql = "SELECT listtemplate, soluong, slock FROM tob_gem_new WHERE owner IN (SELECT id FROM tob_char WHERE charname='" + charName + "')";
                        ResultSet rs = null;
                        try {
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                String[] templates = rs.getString("listtemplate").split(",");
                                String[] amounts = (isLockedGem ? rs.getString("slock") : rs.getString("soluong")).split(",");

                                // Cập nhật số lượng gem
                                for (int i = 0; i < itemIds.length; i++) {
                                    int gemId = Integer.parseInt(itemIds[i].trim());
                                    int quantity = Integer.parseInt(quantities[i].trim());

                                    // Tìm vị trí của gem trong listtemplate
                                    for (int j = 0; j < templates.length; j++) {
                                        if (Integer.parseInt(templates[j].trim()) == gemId) {
                                            int currentAmount = Integer.parseInt(amounts[j].trim());
                                            amounts[j] = String.valueOf(Math.max(0, currentAmount - quantity));
                                            logMsg.append("- Đã thu hồi Gem ID: ").append(gemId)
                                                    .append(", Số lượng: ").append(quantity)
                                                    .append("\n");
                                            break;
                                        }
                                    }
                                }

                                // Cập nhật vào database
                                String updateSql = "UPDATE tob_gem_new SET "
                                        + (isLockedGem ? "slock" : "soluong")
                                        + "='" + String.join(",", amounts)
                                        + "' WHERE owner IN (SELECT id FROM tob_char WHERE charname='" + charName + "')";
                                stmt.executeUpdate(updateSql);

                                // Log kết quả
                                addLog(logMsg.toString());
                                showMessageDialog(this, "Đã thu hồi gem thành công!");
                            } else {
                                showMessageDialog(this, "Không tìm thấy dữ liệu gem của nhân vật: " + charName);
                            }
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            stmt.close();
                            Database.connPool.free(conn);
                        }

                    } catch (NumberFormatException ex) {
                        showMessageDialog(this, "ID gem và số lượng phải là số!");
                    } catch (Exception ex) {
                        addLog("Lỗi khi thu hồi gem: " + ex.getMessage());
                        showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        listOnlineButton.addActionListener(e -> {
            String[] columnNames = {"#", "Tên", "Cấp Độ", "Xu", "Lượng", "Lượng Khóa", "Tọa Độ", "Thao tác"};
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            int idx = 1;
            for (int i = 0; i < CharManager.instance.vChars.size(); i++) {
                real.Char c = CharManager.instance.vChars.elementAt(i);
                if (AmbientBotManager.isAmbientBot(c)) {
                    continue;
                }
                // Kiểm tra nếu tên không chứa @ và không có dấu cách và không phải là NPC
                if (!c.charname.contains("@") && !c.charname.contains(" ") && !NPC_NAMES.contains(c.charname.toLowerCase())) {
                    String levelStr = "";
                    if (c.lvDetail != null) {
                        double percent = c.lvDetail.percent / 10.0;
                        levelStr = c.lvDetail.lv + " + " + String.format("%.1f", percent) + "%";
                    }
                    String toado = "Map " + c.mapID + " (" + (c.x / 16) + ", " + (c.y / 16) + ")";
                    rows.add(new Object[]{
                        idx++,
                        c.charname,
                        levelStr,
                        c.getxu(),
                        c.getLuong(),
                        c.getLuongLock(),
                        toado,
                        "Chỉnh sửa" // Placeholder cho nút
                    });
                }
            }
            if (rows.isEmpty()) {
                showMessageDialog(this, "Không có người chơi nào đang online.", "Danh Sách Người Chơi Online", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Object[][] data = rows.toArray(new Object[0][]);
            // Sử dụng DefaultTableModel để cho phép chỉnh sửa cột cuối
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 7; // Chỉ cột "Thao tác" cho phép bấm nút
                }
            };

            JTable table = new JTable(model);

            // Đặt chiều rộng cho từng cột
            table.getColumnModel().getColumn(0).setPreferredWidth(36);   // #
            table.getColumnModel().getColumn(1).setPreferredWidth(120);  // Tên
            table.getColumnModel().getColumn(2).setPreferredWidth(80);   // Cấp Độ
            table.getColumnModel().getColumn(3).setPreferredWidth(90);   // Xu
            table.getColumnModel().getColumn(4).setPreferredWidth(90);   // Lượng
            table.getColumnModel().getColumn(5).setPreferredWidth(90);   // Lượng Khóa
            table.getColumnModel().getColumn(6).setPreferredWidth(120);  // Tọa Độ
            table.getColumnModel().getColumn(7).setPreferredWidth(90);   // Thao tác

            // GÁN LẠI renderer và editor cho cột "Thao tác"
            table.getColumn("Thao tác").setCellRenderer(new ButtonRenderer());
            table.getColumn("Thao tác").setCellEditor(new ButtonEditor(new JCheckBox(), table, rows, this));

            int totalWidth = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                totalWidth += table.getColumnModel().getColumn(i).getPreferredWidth();
            }
            int intercell = table.getIntercellSpacing().width * (table.getColumnCount() - 1);
            int scrollWidth = totalWidth + intercell + 2;

            JScrollPane scroll = new JScrollPane(table);
            scroll.setPreferredSize(new Dimension(scrollWidth, 350));

            showMessageDialog(this, scroll, "Danh Sách Người Chơi Online (" + rows.size() + ")", JOptionPane.PLAIN_MESSAGE);
        });

        ambientBotsButton.addActionListener(e -> showAmbientBotDashboard());
        luongSon108Button.addActionListener(e -> showLuongSon108Dashboard());
        buffCharacterButton.addActionListener(e -> showNamedCharacterBuffDialog());
        changePasswordButton.addActionListener(e -> handleChangePassword());
        luckyBagDropApplyButton.addActionListener(e -> applyLuckyBagDropRate());
        luckyRewardApplyButton.addActionListener(e -> applyLuckyBagRewardWeights());
        luckyAmountApplyButton.addActionListener(e -> applyLuckyBagRewardAmounts());
    }

    private void showNamedCharacterBuffDialog() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField nameField = new JTextField();
        JTextField targetLevelField = new JTextField("0");
        JTextField xuField = new JTextField("0");
        JTextField luongField = new JTextField("0");
        JTextField skillPointField = new JTextField("0");
        JTextField basePointField = new JTextField("0");

        panel.add(new JLabel("Ten nhan vat:"));
        panel.add(nameField);
        panel.add(new JLabel("Level muc tieu (0 = bo qua):"));
        panel.add(targetLevelField);
        panel.add(new JLabel("Cong them Xu:"));
        panel.add(xuField);
        panel.add(new JLabel("Cong them Luong:"));
        panel.add(luongField);
        panel.add(new JLabel("Cong them diem ky nang:"));
        panel.add(skillPointField);
        panel.add(new JLabel("Cong them diem tiem nang:"));
        panel.add(basePointField);
        panel.add(new JLabel("Ho tro:"));
        panel.add(new JLabel("Nhan vat online va offline"));

        int result = showConfirmDialog(
                this,
                panel,
                "Buff Nhan Vat Theo Ten",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String charName = requireNonEmpty(nameField.getText(), "Ten nhan vat");
            int targetLevel = parseNonNegativeInt(targetLevelField.getText(), "Level muc tieu");
            long addXu = parseNonNegativeLong(xuField.getText(), "Xu");
            int addLuong = parseNonNegativeInt(luongField.getText(), "Luong");
            int addSkillPoint = parseNonNegativeInt(skillPointField.getText(), "Diem ky nang");
            int addBasePoint = parseNonNegativeInt(basePointField.getText(), "Diem tiem nang");
            ensureRestrictedMoneyGrantDisabled(addLuong);

            if (targetLevel == 0 && addXu == 0L && addLuong == 0 && addSkillPoint == 0 && addBasePoint == 0) {
                throw new Exception("Chua nhap thong so buff.");
            }

            AdminBuffOutcome outcome = applyNamedCharacterBuff(
                    charName,
                    targetLevel,
                    addXu,
                    addLuong,
                    addSkillPoint,
                    addBasePoint
            );

            String summary = "Buff thanh cong cho " + outcome.charName
                    + " | level " + outcome.levelBefore + " -> " + outcome.levelAfter
                    + " | +" + outcome.xuAdded + " xu"
                    + " | +" + outcome.luongAdded + " luong"
                    + " | +" + outcome.skillPointAdded + " diem ky nang"
                    + " | +" + outcome.basePointAdded + " diem tiem nang"
                    + " | " + (outcome.online ? "online" : "offline");
            addLog(summary);
            showMessageDialog(this, summary);
        } catch (Exception ex) {
            addLog("Loi buff nhan vat: " + ex.getMessage());
            showMessageDialog(this, ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private AdminBuffOutcome applyNamedCharacterBuff(
            String charName,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        Char onlineChar = findOnlineCharByName(charName);
        if (onlineChar != null) {
            return applyBuffToOnlineChar(onlineChar, targetLevel, addXu, addLuong, addSkillPoint, addBasePoint);
        }
        return applyBuffToOfflineChar(charName, targetLevel, addXu, addLuong, addSkillPoint, addBasePoint);
    }

    private AdminBuffOutcome applyBuffToOnlineChar(
            Char player,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        ensureRestrictedMoneyGrantDisabled(addLuong);
        AdminBuffOutcome outcome = new AdminBuffOutcome();
        outcome.charName = player.charname;
        outcome.online = true;
        outcome.levelBefore = player.lvDetail.lv;

        if (targetLevel > 0) {
            validateTargetLevel(targetLevel);
            if (targetLevel < player.lvDetail.lv) {
                throw new Exception("Khong the buff level thap hon level hien tai.");
            }
            if (targetLevel > player.lvDetail.lv) {
                long targetXp = LevelDetail.getXpFromLevel(targetLevel);
                if (targetXp <= player.lvDetail.getExp()) {
                    throw new Exception("Khong tinh duoc muc EXP cho level muc tieu.");
                }
                Map.addXPForChar(player, targetXp - player.lvDetail.getExp(), false, "adminpanel_namedbuff");
            }
        }

        if (addXu > 0L) {
            player.addXu(addXu, "AdminPanelNamedBuff");
        }
        if (addLuong > 0) {
            player.addLuong(addLuong);
        }
        if (addSkillPoint > 0) {
            player.skillpoint = (short) Math.min(Short.MAX_VALUE, player.skillpoint + addSkillPoint);
        }
        if (addBasePoint > 0) {
            player.basepoint = (short) Math.min(Short.MAX_VALUE, player.basepoint + addBasePoint);
        }

        if (addXu > 0L || addLuong > 0) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        }
        if (targetLevel > 0 || addSkillPoint > 0 || addBasePoint > 0) {
            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
        }

        try {
            player.sendMessage(MessageCreator.createServerAlertMessage(
                    "Admin da buff cho ban: "
                            + (targetLevel > 0 ? "level muc tieu " + Math.max(targetLevel, player.lvDetail.lv) + " | " : "")
                            + "+" + addXu + " xu | +"
                            + addLuong + " luong | +"
                            + addSkillPoint + " diem ky nang | +"
                            + addBasePoint + " diem tiem nang",
                    ""
            ));
        } catch (Exception ignored) {
        }

        Database.instance.saveCharAuto(player);
        Database.instance.saveOrtherLog(
                "",
                player.charname,
                "online buff lvTarget=" + targetLevel
                        + ",xu=" + addXu
                        + ",luong=" + addLuong
                        + ",skill=" + addSkillPoint
                        + ",base=" + addBasePoint,
                "adminbuff"
        );

        outcome.levelAfter = player.lvDetail.lv;
        outcome.xuAdded = addXu;
        outcome.luongAdded = addLuong;
        outcome.skillPointAdded = addSkillPoint;
        outcome.basePointAdded = addBasePoint;
        return outcome;
    }

    private AdminBuffOutcome applyBuffToOfflineChar(
            String charName,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        ensureRestrictedMoneyGrantDisabled(addLuong);
        Connection conn = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            select = conn.prepareStatement(
                    "SELECT id,charname,pInfo,basic,gold,luong,xp,lastLv FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1"
            );
            select.setString(1, charName.trim());
            rs = select.executeQuery();
            if (!rs.next()) {
                throw new Exception("Khong tim thay nhan vat: " + charName);
            }

            int charId = rs.getInt("id");
            String actualName = rs.getString("charname");
            String pInfo = rs.getString("pInfo");
            String basic = rs.getString("basic");

            if (pInfo == null || pInfo.trim().isEmpty() || basic == null || basic.trim().isEmpty()) {
                throw new Exception("Du lieu nhan vat khong hop le de buff.");
            }

            String[] infoParts = Char.split(pInfo, ",");
            String[] basicParts = Char.split(basic, ",");
            if (infoParts.length < 6 || basicParts.length < 7) {
                throw new Exception("Du lieu nhan vat dang bi thieu cot.");
            }

            long currentXp = Math.max(parseLongSafe(infoParts[4]), rs.getLong("xp"));
            int currentLevel = Math.max(LevelDetail.getLevelFromExp(currentXp), parseIntSafe(infoParts[5]));
            int currentLastLevel = Math.max(parseIntSafe(infoParts[5]), rs.getInt("lastLv"));
            int strength = parseIntSafe(basicParts[0]);
            int agitity = parseIntSafe(basicParts[1]);
            int spirit = parseIntSafe(basicParts[2]);
            int health = parseIntSafe(basicParts[3]);
            int luck = parseIntSafe(basicParts[4]);
            int basepoint = parseIntSafe(basicParts[5]);
            int skillpoint = parseIntSafe(basicParts[6]);
            long newXp = currentXp;
            int newLastLevel = currentLastLevel;

            if (targetLevel > 0) {
                validateTargetLevel(targetLevel);
                if (targetLevel < currentLevel) {
                    throw new Exception("Khong the buff level thap hon level hien tai.");
                }
                if (targetLevel > currentLevel) {
                    newXp = LevelDetail.getXpFromLevel(targetLevel);
                    if (newXp <= 0L) {
                        throw new Exception("Khong tinh duoc muc EXP cho level muc tieu.");
                    }
                }
                if (targetLevel > currentLastLevel) {
                    int deltaLevel = targetLevel - currentLastLevel;
                    strength += deltaLevel;
                    agitity += deltaLevel;
                    spirit += deltaLevel;
                    health += deltaLevel;
                    luck += deltaLevel;
                    basepoint += deltaLevel * 5;
                    skillpoint += deltaLevel;
                    newLastLevel = targetLevel;
                }
            }

            long newGold = Math.max(0L, rs.getLong("gold") + addXu);
            int newLuong = Math.max(0, rs.getInt("luong") + addLuong);
            basepoint = Math.max(0, basepoint + addBasePoint);
            skillpoint = Math.max(0, skillpoint + addSkillPoint);

            basicParts[0] = Integer.toString(strength);
            basicParts[1] = Integer.toString(agitity);
            basicParts[2] = Integer.toString(spirit);
            basicParts[3] = Integer.toString(health);
            basicParts[4] = Integer.toString(luck);
            basicParts[5] = Integer.toString(basepoint);
            basicParts[6] = Integer.toString(skillpoint);
            infoParts[4] = Long.toString(newXp);
            infoParts[5] = Integer.toString(newLastLevel);

            update = conn.prepareStatement(
                    "UPDATE tob_char SET pInfo=?, basic=?, gold=?, luong=?, xp=?, lastLv=? WHERE id=?"
            );
            update.setString(1, joinCsv(infoParts));
            update.setString(2, joinCsv(basicParts));
            update.setLong(3, newGold);
            update.setInt(4, newLuong);
            update.setLong(5, newXp);
            update.setInt(6, newLastLevel);
            update.setInt(7, charId);
            update.executeUpdate();

            Database.instance.saveOrtherLog(
                    "",
                    actualName,
                    "offline buff lvTarget=" + targetLevel
                            + ",xu=" + addXu
                            + ",luong=" + addLuong
                            + ",skill=" + addSkillPoint
                            + ",base=" + addBasePoint,
                    "adminbuff"
            );

            AdminBuffOutcome outcome = new AdminBuffOutcome();
            outcome.charName = actualName;
            outcome.online = false;
            outcome.levelBefore = currentLevel;
            outcome.levelAfter = Math.max(currentLevel, targetLevel > 0 ? targetLevel : currentLevel);
            outcome.xuAdded = addXu;
            outcome.luongAdded = addLuong;
            outcome.skillPointAdded = addSkillPoint;
            outcome.basePointAdded = addBasePoint;
            return outcome;
        } finally {
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            try {
                select.close();
            } catch (Exception ignored) {
            }
            try {
                update.close();
            } catch (Exception ignored) {
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    private Char findOnlineCharByName(String charName) {
        if (charName == null) {
            return null;
        }
        String normalized = charName.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        Char found = CharManager.instance.getCharByCharName(normalized.toLowerCase());
        if (found != null) {
            return found;
        }
        for (Char player : CharManager.instance.vChars) {
            if (player != null && player.charname != null && player.charname.equalsIgnoreCase(normalized)) {
                return player;
            }
        }
        return null;
    }

    private void validateTargetLevel(int targetLevel) throws Exception {
        if (targetLevel < 1 || targetLevel >= LevelDetail.expMain.length) {
            throw new Exception("Level muc tieu phai trong khoang 1-" + (LevelDetail.expMain.length - 1) + ".");
        }
    }

    private String requireNonEmpty(String value, String label) throws Exception {
        String normalized = value == null ? "" : value.trim();
        if (normalized.isEmpty()) {
            throw new Exception(label + " khong duoc de trong.");
        }
        return normalized;
    }

    private void ensureRestrictedMoneyGrantDisabled(int addLuong) throws Exception {
        if (addLuong > 0) {
            throw new Exception("Luong chi duoc nhan tu Bao may man. Admin panel da khoa cong luong truc tiep.");
        }
    }

    private int parseNonNegativeInt(String value, String label) throws Exception {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 0) {
                throw new NumberFormatException();
            }
            return parsed;
        } catch (Exception ex) {
            throw new Exception(label + " phai la so nguyen >= 0.");
        }
    }

    private long parseNonNegativeLong(String value, String label) throws Exception {
        try {
            long parsed = Long.parseLong(value.trim());
            if (parsed < 0L) {
                throw new NumberFormatException();
            }
            return parsed;
        } catch (Exception ex) {
            throw new Exception(label + " phai la so >= 0.");
        }
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception ignored) {
            return 0;
        }
    }

    private long parseLongSafe(String value) {
        try {
            return Long.parseLong(value.trim());
        } catch (Exception ignored) {
            return 0L;
        }
    }

    private String joinCsv(String[] values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(values[i] == null ? "" : values[i]);
        }
        return builder.toString();
    }

    private void handleAmbientBots() {
        int current = AmbientBotManager.instance.getCurrentCount();
        int target = AmbientBotManager.instance.getTargetCount();
        String input = JOptionPane.showInputDialog(
                this,
                "Nhập số lượng ambient bot (0-10000)\nHiện tại: " + current + " bot, mục tiêu đang đặt: " + target,
                String.valueOf(target)
        );
        if (input == null) {
            return;
        }
        try {
            int value = Integer.parseInt(input.trim());
            if (value < 0 || value > 10000) {
                showMessageDialog(this, "Số lượng phải nằm trong khoảng 0-10000");
                return;
            }
            AmbientBotManager.instance.setTargetCount(value);
            addLog("Đặt ambient bot mục tiêu = " + value + " (đang có " + AmbientBotManager.instance.getCurrentCount() + ")");
        } catch (NumberFormatException ex) {
            showMessageDialog(this, "Vui lòng nhập số hợp lệ");
        }
    }

    private void showAmbientBotDashboard() {
        int current = AmbientBotManager.instance.getCurrentCount();
        int target = AmbientBotManager.instance.getTargetCount();

        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JTextArea summaryArea = new JTextArea(AmbientBotManager.instance.getAdminSummary());
        summaryArea.setEditable(false);
        summaryArea.setOpaque(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        panel.add(summaryArea, BorderLayout.NORTH);

        String[] columns = {"Ten", "Lv", "Vai tro", "Tinh cach", "Trang thai", "Map", "Ca", "Binh", "Clan"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        java.util.List<String[]> rows = AmbientBotManager.instance.getAdminOverviewRows();
        for (String[] row : rows) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(760, 380));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bottom.add(new JLabel("So bot muc tieu (0-10000):"));
        JSpinner targetSpinner = new JSpinner(new SpinnerNumberModel(target, 0, 10000, 10));
        bottom.add(targetSpinner);
        bottom.add(new JLabel("Dang online: " + current + " | Roster: " + AmbientBotManager.instance.getRosterCount()));
        panel.add(bottom, BorderLayout.SOUTH);

        int confirm = showConfirmDialog(
                this,
                panel,
                "Ambient Bot",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (confirm != JOptionPane.OK_OPTION) {
            return;
        }

        int value = ((Number) targetSpinner.getValue()).intValue();
        if (value < 0 || value > 10000) {
            showMessageDialog(this, "So luong phai nam trong khoang 0-10000");
            return;
        }

        AmbientBotManager.instance.setTargetCount(value);
        addLog("Dat ambient bot muc tieu = " + value + " (dang co " + AmbientBotManager.instance.getCurrentCount()
                + ", roster " + AmbientBotManager.instance.getRosterCount() + ")");
    }

    private void showLuongSon108Dashboard() {
        LuongSon108Manager.Snapshot snapshot = LuongSon108Manager.instance.snapshot();
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        String summaryText = snapshot.summary == null || snapshot.summary.trim().isEmpty()
                ? "108 anh hung Luong Son chua duoc kich hoat."
                : snapshot.summary;
        JTextArea summaryArea = new JTextArea(summaryText
                + "\nCau hinh: chi chan map Truong giang | NPC thu phi o Tu quan (40,39) | phi bao ke 10 luong / 1 gio | con han thi qua lai khong bi danh.");
        summaryArea.setEditable(false);
        summaryArea.setOpaque(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        panel.add(summaryArea, BorderLayout.NORTH);

        String[] columns = {"Ten", "Lv", "Mon phai", "Trang thai", "Muc tieu", "Vi tri"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < snapshot.rows.size(); i++) {
            model.addRow(snapshot.rows.get(i));
        }

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(860, 380));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bottom.add(new JLabel("Tuyen chan: Tu quan -> Truong giang"));
        bottom.add(new JLabel("Online: " + snapshot.onlineCount + "/" + snapshot.heroCount));
        panel.add(bottom, BorderLayout.SOUTH);

        Object[] options = {"Khoi dong chan duong", "Thu hoi", "Dong"};
        int action = JOptionPane.showOptionDialog(
                this,
                panel,
                "108 Luong Son",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (action == 0) {
            try {
                String result = LuongSon108Manager.instance.deployTruongGiangGuard();
                addLog(result);
                showMessageDialog(this, result);
            } catch (Exception ex) {
                addLog("Loi tha 108 Luong Son: " + ex.getMessage());
                showMessageDialog(this, ex.getMessage());
            }
        } else if (action == 1) {
            String result = LuongSon108Manager.instance.clear();
            addLog(result);
            showMessageDialog(this, result);
        }
    }

    private void handleChangePassword() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField retypeField = new JTextField();

        panel.add(new JLabel("Tài khoản:"));
        panel.add(usernameField);
        panel.add(new JLabel("Mật khẩu mới:"));
        panel.add(passwordField);
        panel.add(new JLabel("Nhập lại mật khẩu:"));
        panel.add(retypeField);

        int result = showConfirmDialog(this, panel, "Đổi mật khẩu tài khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String pass1 = passwordField.getText();
            String pass2 = retypeField.getText();

            if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            if (!pass1.equals(pass2)) {
                showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
                return;
            }
            try {
                String hash = sqlPassword(pass1);
                Connection conn = Database.instance.getConnection();
                Statement stmt = conn.createStatement();
                int updated = stmt.executeUpdate("UPDATE account.team_user SET password='" + hash + "' WHERE username='" + username + "'");
                stmt.close();
                Database.connPool.free(conn);
                if (updated > 0) {
                    addLog("Đã đổi mật khẩu cho tài khoản: " + username);
                    showMessageDialog(this, "Đổi mật khẩu thành công!");
                } else {
                    showMessageDialog(this, "Không tìm thấy tài khoản!");
                }
            } catch (Exception ex) {
                addLog("Lỗi đổi mật khẩu: " + ex.getMessage());
                showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // Hàm mã hóa mật khẩu giống PHP MySQL
    private String sqlPassword(String input) throws Exception {
        try {
            java.security.MessageDigest sha1 = java.security.MessageDigest.getInstance("SHA-1");
            byte[] first = sha1.digest(input.getBytes("UTF-8"));
            byte[] second = sha1.digest(first);
            StringBuilder sb = new StringBuilder();
            for (byte b : second) {
                sb.append(String.format("%02X", b));
            }
            return "*" + sb.toString();
        } catch (Exception ex) {
            throw new Exception("Lỗi mã hóa mật khẩu: " + ex.getMessage());
        }
    }

    private void startMaintenanceMode() {
        String input = JOptionPane.showInputDialog(
                this,
                "Nhập số phút trước khi bảo trì (1-60):",
                "5"
        );

        try {
            maintenanceMinutes = Integer.parseInt(input);
            if (maintenanceMinutes < 1 || maintenanceMinutes > 60) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showMessageDialog(
                    this,
                    "Vui lòng nhập số phút hợp lệ (1-60)",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirm = showConfirmDialog(
                this,
                "Xác nhận bảo trì sau " + maintenanceMinutes + " phút?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            final int[] remainingMinutes = {maintenanceMinutes};
            maintenanceTimer = new Timer(60000, evt -> {
                remainingMinutes[0]--;
                String countdownMsg = "Hệ thống sẽ bảo trì sau " + remainingMinutes[0] + " phút.";

                if (remainingMinutes[0] == 2) {
                    LoginHandler.stopLogin = true;
                }
                if (remainingMinutes[0] <= 1) {
                    AdminHandler.isStopServer = true;

                    countdownMsg = "Hệ thống sẽ bảo trì sau 1 phút. Vui lòng thoát game để tránh mất dữ liệu!";
                }

                try {
                    Message m = MessageCreator.createthongbao(countdownMsg);
                    for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                        CharManager.instance.vChars.elementAt(j).sendMessage(m);
                    }
                } catch (IOException ex) {
                    addLog("Lỗi gửi thông báo bảo trì: " + ex.getMessage());
                }

                if (remainingMinutes[0] <= 0) {
                    maintenanceTimer.stop();
                    new AdminHandler().stopServer();
                }
            });
            maintenanceTimer.start();

            // Gửi thông báo đầu tiên
            String initialMsg = "Hệ thống sẽ bảo trì sau " + maintenanceMinutes + " phút.";
            try {
                Message m = MessageCreator.createthongbao(initialMsg);
                for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                    CharManager.instance.vChars.elementAt(j).sendMessage(m);
                }
            } catch (IOException ex) {
                addLog("Lỗi gửi thông báo bảo trì: " + ex.getMessage());
            }

            maintenanceButton.setText("Hủy Bảo Trì");
            updateServerStatus();
            addLog("Bắt đầu bảo trì sau " + maintenanceMinutes + " phút");
        }
    }

    private void cancelMaintenanceMode() {
        int confirm = showConfirmDialog(
                this,
                "Bạn có chắc muốn hủy bảo trì?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (maintenanceTimer != null) {
                maintenanceTimer.stop();
            }

            AdminHandler.isStopServer = false;
            LoginHandler.stopLogin = false;

            // Send cancellation notice to all players
            try {
                Message m = MessageCreator.createthongbao("Thông báo: Lịch bảo trì đã được hủy bỏ.");
                for (int j = 0; j < CharManager.instance.vChars.size(); ++j) {
                    CharManager.instance.vChars.elementAt(j).sendMessage(m);
                }
                addLog("Đã gửi thông báo hủy bảo trì");
            } catch (IOException ex) {
                addLog("Lỗi gửi thông báo hủy bảo trì: " + ex.getMessage());
            }

            maintenanceButton.setText("Bảo Trì");
            updateServerStatus();
            addLog("Đã hủy bảo trì");
        }
    }

    private void updateServerStatus() {
        String status = AdminHandler.isStopServer ? "Đang Bảo Trì" : "Đang Hoạt Động";
        statusLabel.setText("Trạng Thái: " + status);
        playersLabel.setText("Người Chơi: " + SessionManager.instance.size() + "/" + TeamServer.LIMIT_CCU);
        portLabel.setText("Cổng: " + TeamServer.PORT);
        maintenanceButton.setText(AdminHandler.isStopServer ? "Hủy Bảo Trì" : "Bảo Trì");

        stopLoginButton.setText(LoginHandler.stopLogin ? "Cho Phép Đăng Nhập" : "Dừng Đăng Nhập");

        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long hours = uptime / (60 * 60 * 1000);
        long minutes = (uptime / (60 * 1000)) % 60;
        long seconds = (uptime / 1000) % 60;
        uptimeLabel.setText(String.format("Thời Gian: %02d:%02d:%02d", hours, minutes, seconds));
    }

    private void updateMemoryStatus() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        memoryLabel.setText("Bộ Nhớ: " + usedMemory + "MB");
    }

    public void addLog(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        logArea.append("[" + timestamp + "] " + uiText(message) + "\n");
        // Giới hạn số dòng log (ví dụ 500 dòng)
        int maxLines = 100;
        String[] lines = logArea.getText().split("\n");
        if (lines.length > maxLines) {
            StringBuilder sb = new StringBuilder();
            for (int i = lines.length - maxLines; i < lines.length; i++) {
                sb.append(lines[i]).append("\n");
            }
            logArea.setText(sb.toString());
        }
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private JLabel createStatusLabel(String text) {
        JLabel label = new JLabel(uiText(text));
        label.setBorder(new EmptyBorder(2, 5, 2, 5)); // Add padding for better appearance
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(uiText(text));
        button.setFocusPainted(false);
        return button;
    }

    private TitledBorder createGroupBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                uiText(title)
        );
        return border;
    }

    private JPanel createButtonPanel(String title, JButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(createGroupBorder(title));
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            Dimension size = btn.getPreferredSize();
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, size.height));
            btn.setMinimumSize(new Dimension(0, size.height));
            btn.setPreferredSize(new Dimension(0, size.height));
            btn.setMargin(new Insets(4, 10, 4, 10));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(5));
        }
        return panel;
    }

    private void addButtonToPanel(JPanel panel, JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = btn.getPreferredSize();
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, size.height));
        btn.setMinimumSize(new Dimension(0, size.height));
        btn.setPreferredSize(new Dimension(0, size.height));
        btn.setMargin(new Insets(4, 10, 4, 10));
        panel.add(btn);
        panel.add(Box.createVerticalStrut(5));
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(createGroupBorder("Player"));

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

    private JScrollPane wrapScrollable(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createSpinnerPanel(String title, JSpinner... spinners) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(createGroupBorder(title));

        for (JSpinner spinner : spinners) {
            spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

            Dimension size = spinner.getPreferredSize();
            spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, size.height));
            spinner.setMinimumSize(new Dimension(0, size.height));
            spinner.setPreferredSize(new Dimension(0, size.height));

            panel.add(spinner);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }

    private void initLuckyBagDropControls() {
        // Avoid touching Map here to prevent triggering Map static init before DB is ready.
        SpinnerNumberModel model = new SpinnerNumberModel(100.0, 0.0, 100.0, 0.1);
        luckyBagDropRateSpinner = new JSpinner(model);
        luckyBagDropRateSpinner.setEditor(new JSpinner.NumberEditor(luckyBagDropRateSpinner, "0.0"));
        luckyBagDropApplyButton = createButton("Áp Dụng");
    }

    private void initLuckyBagRewardControls() {
        // Default weights (avoid reading Map here).
        luckyWeightLuong = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
        luckyWeightLuongKhoa = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
        luckyWeightXu = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
        luckyWeightHp = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
        luckyWeightMp = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
        luckyWeightExp = new JSpinner(new SpinnerNumberModel(0, 0, 0, 0));
        luckyWeightExp.setEnabled(false);
        luckyRewardApplyButton = createButton("Áp Dụng");
    }

    private void initLuckyBagAmountControls() {
        luckyAmountLuongMin = new JSpinner(new SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountLuongMax = new JSpinner(new SpinnerNumberModel(30, 0, 1000000000, 1));
        luckyAmountLuongKhoaMin = new JSpinner(new SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountLuongKhoaMax = new JSpinner(new SpinnerNumberModel(30, 0, 1000000000, 1));
        luckyAmountXuMin = new JSpinner(new SpinnerNumberModel(10000, 0, 1000000000, 1000));
        luckyAmountXuMax = new JSpinner(new SpinnerNumberModel(100000, 0, 1000000000, 1000));
        luckyAmountHpMin = new JSpinner(new SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountHpMax = new JSpinner(new SpinnerNumberModel(3, 0, 1000000000, 1));
        luckyAmountMpMin = new JSpinner(new SpinnerNumberModel(1, 0, 1000000000, 1));
        luckyAmountMpMax = new JSpinner(new SpinnerNumberModel(3, 0, 1000000000, 1));
        luckyAmountExpMin = new JSpinner(new SpinnerNumberModel(0, 0, 0, 0));
        luckyAmountExpMax = new JSpinner(new SpinnerNumberModel(0, 0, 0, 0));
        luckyAmountExpMin.setEnabled(false);
        luckyAmountExpMax.setEnabled(false);
        luckyAmountApplyButton = createButton("Áp Dụng");
        luckyMaxOpenPerDay = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 1));
    }

    private void loadLuckyBagSettingsFromMap() {
        luckyBagDropRateSpinner.setValue(Math.max(0D, Math.min(100D, Map.luckyBagDropRate / 10000D)));

        int[] weights = Map.luckyBagRewardWeights;
        if (weights != null) {
            if (weights.length > 0) {
                luckyWeightLuong.setValue(weights[0]);
            }
            if (weights.length > 1) {
                luckyWeightLuongKhoa.setValue(weights[1]);
            }
            if (weights.length > 2) {
                luckyWeightXu.setValue(weights[2]);
            }
            if (weights.length > 3) {
                luckyWeightHp.setValue(weights[3]);
            }
            if (weights.length > 4) {
                luckyWeightMp.setValue(weights[4]);
            }
        }
        luckyWeightExp.setValue(0);

        int[] minValues = Map.luckyBagRewardMin;
        if (minValues != null) {
            if (minValues.length > 0) {
                luckyAmountLuongMin.setValue(minValues[0]);
            }
            if (minValues.length > 1) {
                luckyAmountLuongKhoaMin.setValue(minValues[1]);
            }
            if (minValues.length > 2) {
                luckyAmountXuMin.setValue(minValues[2]);
            }
            if (minValues.length > 3) {
                luckyAmountHpMin.setValue(minValues[3]);
            }
            if (minValues.length > 4) {
                luckyAmountMpMin.setValue(minValues[4]);
            }
        }
        luckyAmountExpMin.setValue(0);

        int[] maxValues = Map.luckyBagRewardMax;
        if (maxValues != null) {
            if (maxValues.length > 0) {
                luckyAmountLuongMax.setValue(maxValues[0]);
            }
            if (maxValues.length > 1) {
                luckyAmountLuongKhoaMax.setValue(maxValues[1]);
            }
            if (maxValues.length > 2) {
                luckyAmountXuMax.setValue(maxValues[2]);
            }
            if (maxValues.length > 3) {
                luckyAmountHpMax.setValue(maxValues[3]);
            }
            if (maxValues.length > 4) {
                luckyAmountMpMax.setValue(maxValues[4]);
            }
        }
        luckyAmountExpMax.setValue(0);

        luckyMaxOpenPerDay.setValue(Math.max(1, Map.luckyBagMaxOpenPerDay));
    }

    private void initBlackMarketControls() {
        blackMarketShardPriceSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000000, 1));
        blackMarketShardLimitSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 1000, 1));
        blackMarketHotCratePriceSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000000, 1));
        blackMarketApplyButton = createButton("Áp Dụng");
        blackMarketReloadButton = createButton("Tải Lại");

        Map.BlackMarketAdminOption[] rareOptions = Map.getBlackMarketRareAdminOptions();
        blackMarketRareSlotControls = new BlackMarketRareSlotControls[3];
        for (int i = 0; i < blackMarketRareSlotControls.length; i++) {
            BlackMarketRareSlotControls controls = new BlackMarketRareSlotControls();
            controls.slotLabel = "Ô VIP " + (i + 1);
            controls.options = rareOptions;
            controls.optionCombo = new JComboBox<>(rareOptions);
            controls.priceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
            blackMarketRareSlotControls[i] = controls;
        }

        int categoryCount = Map.getBlackMarketAdminMiscCategoryCount();
        blackMarketMiscCategoryControls = new BlackMarketMiscCategoryControls[categoryCount];
        for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
            BlackMarketMiscCategoryControls categoryControls = new BlackMarketMiscCategoryControls();
            categoryControls.categoryIndex = categoryIndex;
            categoryControls.label = Map.getBlackMarketAdminMiscCategoryLabel(categoryIndex);
            categoryControls.options = Map.getBlackMarketMiscAdminOptions(categoryIndex);
            categoryControls.slots = new BlackMarketMiscSlotControls[categoryControls.options.length];
            for (int slotIndex = 0; slotIndex < categoryControls.slots.length; slotIndex++) {
                BlackMarketMiscSlotControls slotControls = new BlackMarketMiscSlotControls();
                slotControls.slotLabel = "Ô " + (slotIndex + 1);
                slotControls.optionCombo = new JComboBox<>(categoryControls.options);
                slotControls.amountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000000, 1));
                slotControls.priceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
                categoryControls.slots[slotIndex] = slotControls;
            }
            blackMarketMiscCategoryControls[categoryIndex] = categoryControls;
        }
    }

    private void loadBlackMarketSettingsFromMap() {
        Map.BlackMarketAdminState state = Map.snapshotBlackMarketAdminState();
        if (state == null) {
            return;
        }
        blackMarketShardPriceSpinner.setValue(Math.max(0, state.shardPriceAn));
        blackMarketShardLimitSpinner.setValue(Math.max(1, state.shardMaxBuyPerPeriod));
        blackMarketHotCratePriceSpinner.setValue(Math.max(0, state.hotCratePriceAn));

        if (state.rareSlots != null) {
            for (int i = 0; i < blackMarketRareSlotControls.length && i < state.rareSlots.length; i++) {
                Map.BlackMarketAdminRareSlot slot = state.rareSlots[i];
                if (slot == null) {
                    continue;
                }
                selectBlackMarketOption(blackMarketRareSlotControls[i].optionCombo, slot.optionIndex);
                blackMarketRareSlotControls[i].priceSpinner.setValue(Math.max(0, slot.priceAnHacThi));
            }
        }

        if (state.miscCategories != null) {
            for (int categoryIndex = 0; categoryIndex < blackMarketMiscCategoryControls.length && categoryIndex < state.miscCategories.length; categoryIndex++) {
                Map.BlackMarketAdminMiscCategory category = state.miscCategories[categoryIndex];
                if (category == null || category.slots == null) {
                    continue;
                }
                BlackMarketMiscCategoryControls uiCategory = blackMarketMiscCategoryControls[categoryIndex];
                for (int slotIndex = 0; slotIndex < uiCategory.slots.length && slotIndex < category.slots.length; slotIndex++) {
                    Map.BlackMarketAdminMiscSlot slot = category.slots[slotIndex];
                    if (slot == null) {
                        continue;
                    }
                    selectBlackMarketOption(uiCategory.slots[slotIndex].optionCombo, slot.optionIndex);
                    uiCategory.slots[slotIndex].amountSpinner.setValue(Math.max(1, slot.amount));
                    uiCategory.slots[slotIndex].priceSpinner.setValue(Math.max(0, slot.priceAnHacThi));
                }
            }
        }
    }

    private void selectBlackMarketOption(JComboBox<Map.BlackMarketAdminOption> combo, int optionIndex) {
        ComboBoxModel<Map.BlackMarketAdminOption> model = combo.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Map.BlackMarketAdminOption option = model.getElementAt(i);
            if (option != null && option.optionIndex == optionIndex) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (model.getSize() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    private JPanel createBlackMarketPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(createGroupBorder("Quan ly cho den"));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));
        corePanel.setBorder(createGroupBorder("Chủ chợ đen"));
        corePanel.add(createBlackMarketNumberRow("Giá Mảnh Cổ Vật", blackMarketShardPriceSpinner, "Ấn"));
        corePanel.add(Box.createVerticalStrut(4));
        corePanel.add(createBlackMarketNumberRow("Giới hạn mua mỗi kỳ", blackMarketShardLimitSpinner, "mảnh"));
        corePanel.add(Box.createVerticalStrut(4));
        corePanel.add(createBlackMarketNumberRow("Giá Rương Hàng Nóng", blackMarketHotCratePriceSpinner, "Ấn"));
        content.add(corePanel);
        content.add(Box.createVerticalStrut(8));

        JPanel rarePanel = new JPanel();
        rarePanel.setLayout(new BoxLayout(rarePanel, BoxLayout.Y_AXIS));
        rarePanel.setBorder(createGroupBorder("Thương nhân quý hiếm"));
        for (int i = 0; i < blackMarketRareSlotControls.length; i++) {
            BlackMarketRareSlotControls slot = blackMarketRareSlotControls[i];
            rarePanel.add(createBlackMarketRareRow(slot));
            if (i + 1 < blackMarketRareSlotControls.length) {
                rarePanel.add(Box.createVerticalStrut(4));
            }
        }
        content.add(rarePanel);
        content.add(Box.createVerticalStrut(8));

        JPanel miscWrapper = new JPanel();
        miscWrapper.setLayout(new BoxLayout(miscWrapper, BoxLayout.Y_AXIS));
        miscWrapper.setBorder(createGroupBorder("Tạp hóa hắc thị"));
        for (int categoryIndex = 0; categoryIndex < blackMarketMiscCategoryControls.length; categoryIndex++) {
            miscWrapper.add(createBlackMarketMiscCategoryPanel(blackMarketMiscCategoryControls[categoryIndex]));
            if (categoryIndex + 1 < blackMarketMiscCategoryControls.length) {
                miscWrapper.add(Box.createVerticalStrut(6));
            }
        }
        content.add(miscWrapper);

        outer.add(content, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        buttonPanel.add(blackMarketApplyButton);
        buttonPanel.add(blackMarketReloadButton);
        outer.add(buttonPanel, BorderLayout.SOUTH);

        return outer;
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
        row.add(new JLabel("Ấn"));
        return row;
    }

    private JPanel createBlackMarketMiscCategoryPanel(BlackMarketMiscCategoryControls category) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
            row.add(new JLabel("Ấn"));
            panel.add(row);
            if (i + 1 < category.slots.length) {
                panel.add(Box.createVerticalStrut(3));
            }
        }
        return panel;
    }

    private void initEventRows() {
        eventRows = new ArrayList<>();
        addEventRow("noel", "Noel (cu)");
        addEventRow("noel2023", "Noel 2023");
        addEventRow("tet2017", "Tet 2017");
        addEventRow("tetduonglich2024", "Tet duong lich 2024");
        addEventRow("gioto2016", "Gio To 2016");
        addEventRow("trungthu2016", "Trung Thu 2016");
        addEventRow("he2017", "He 2017");
        addEventRow("worldcup2017", "World Cup 2017");
        addEventRow("minichucnu", "Mini Chuc Nu");
        addEventRow("mini", "Mini");
        addEventRow("mininuichaubau", "Mini Nui Chau Bau");
        addEventRow("blackfriday", "Black Friday");
        addEventRow("haloween2016", "Halloween 2016");
        addEventRow("sukien83", "Su kien 8/3");
        addEventRow("choden", "Chợ Đen");
        try {
            TeamServer.reloadEventOverridesFromFile();
        } catch (Exception ignored) {
        }
        loadEventStatesToUI();
    }

    private void addEventRow(String key, String label) {
        JComboBox<String> combo = new JComboBox<>(new String[]{"Tu dong", "Bat", "Tat"});
        eventRows.add(new EventRow(key, label, combo));
    }

    private JPanel createEventPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(createGroupBorder("Quan ly su kien"));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (EventRow row : eventRows) {
            JPanel rowPanel = new JPanel(new BorderLayout(5, 0));
            rowPanel.add(new JLabel(row.label), BorderLayout.CENTER);
            rowPanel.add(row.combo, BorderLayout.EAST);
            listPanel.add(rowPanel);
            listPanel.add(Box.createVerticalStrut(4));
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        outer.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        eventApplyButton = new JButton("Ap dung");
        eventReloadButton = new JButton("Tai lai");
        buttonPanel.add(eventApplyButton);
        buttonPanel.add(eventReloadButton);
        outer.add(buttonPanel, BorderLayout.SOUTH);

        return outer;
    }

    private void loadEventStatesToUI() {
        if (eventRows == null) {
            return;
        }
        for (EventRow row : eventRows) {
            byte val = TeamServer.getEventOverride(row.key);
            row.combo.setSelectedIndex(overrideToComboIndex(val));
        }
    }

    private int overrideToComboIndex(byte value) {
        if (value == TeamServer.EVENT_ON) {
            return 1;
        }
        if (value == TeamServer.EVENT_OFF) {
            return 2;
        }
        return 0;
    }

    private byte comboIndexToOverride(int idx) {
        if (idx == 1) {
            return TeamServer.EVENT_ON;
        }
        if (idx == 2) {
            return TeamServer.EVENT_OFF;
        }
        return TeamServer.EVENT_AUTO;
    }

    private void applyEventSettings() {
        java.util.Map<String, Byte> overrides = new LinkedHashMap<>();
        for (EventRow row : eventRows) {
            byte val = comboIndexToOverride(row.combo.getSelectedIndex());
            TeamServer.setEventOverride(row.key, val);
            overrides.put(row.key, val);
        }
        try {
            TeamServer.saveEventOverrides(overrides);
            addLog("Da ap dung cau hinh su kien");
        } catch (Exception ex) {
            addLog("Loi luu cau hinh su kien: " + ex.getMessage());
        }
    }

    private void reloadEventSettings() {
        try {
            TeamServer.reloadEventOverridesFromFile();
            loadEventStatesToUI();
            addLog("Da tai lai cau hinh su kien");
        } catch (Exception ex) {
            addLog("Loi tai cau hinh su kien: " + ex.getMessage());
        }
    }

    private void applyBlackMarketSettings() {
        Map.BlackMarketAdminState state = new Map.BlackMarketAdminState();
        state.shardPriceAn = ((Number) blackMarketShardPriceSpinner.getValue()).intValue();
        state.shardMaxBuyPerPeriod = ((Number) blackMarketShardLimitSpinner.getValue()).intValue();
        state.hotCratePriceAn = ((Number) blackMarketHotCratePriceSpinner.getValue()).intValue();

        state.rareSlots = new Map.BlackMarketAdminRareSlot[blackMarketRareSlotControls.length];
        for (int i = 0; i < blackMarketRareSlotControls.length; i++) {
            BlackMarketRareSlotControls controls = blackMarketRareSlotControls[i];
            Map.BlackMarketAdminOption option = (Map.BlackMarketAdminOption) controls.optionCombo.getSelectedItem();
            Map.BlackMarketAdminRareSlot slot = new Map.BlackMarketAdminRareSlot();
            slot.optionIndex = option != null ? option.optionIndex : 0;
            slot.priceAnHacThi = ((Number) controls.priceSpinner.getValue()).intValue();
            state.rareSlots[i] = slot;
        }

        state.miscCategories = new Map.BlackMarketAdminMiscCategory[blackMarketMiscCategoryControls.length];
        for (int categoryIndex = 0; categoryIndex < blackMarketMiscCategoryControls.length; categoryIndex++) {
            BlackMarketMiscCategoryControls controls = blackMarketMiscCategoryControls[categoryIndex];
            Map.BlackMarketAdminMiscCategory category = new Map.BlackMarketAdminMiscCategory();
            category.categoryIndex = controls.categoryIndex;
            category.slots = new Map.BlackMarketAdminMiscSlot[controls.slots.length];
            for (int slotIndex = 0; slotIndex < controls.slots.length; slotIndex++) {
                BlackMarketMiscSlotControls slotControls = controls.slots[slotIndex];
                Map.BlackMarketAdminOption option = (Map.BlackMarketAdminOption) slotControls.optionCombo.getSelectedItem();
                Map.BlackMarketAdminMiscSlot slot = new Map.BlackMarketAdminMiscSlot();
                slot.optionIndex = option != null ? option.optionIndex : 0;
                slot.amount = ((Number) slotControls.amountSpinner.getValue()).intValue();
                slot.priceAnHacThi = ((Number) slotControls.priceSpinner.getValue()).intValue();
                category.slots[slotIndex] = slot;
            }
            state.miscCategories[categoryIndex] = category;
        }

        Map.applyBlackMarketAdminState(state);
        String saveError = persistBlackMarketSettings();
        addLog("Da cap nhat cau hinh Cho Den"
                + " | Manh=" + Map.blackMarketArtifactShardPriceAn
                + " | Gioi han=" + Map.blackMarketArtifactShardMaxBuyPerPeriod
                + " | Ruong=" + Map.blackMarketHotCratePriceAn
                + (saveError == null ? " | da luu server.ini" : " | chua luu server.ini: " + saveError));
    }

    private void reloadBlackMarketSettings() {
        try {
            TeamServer.reloadBlackMarketSettingsFromFile();
            loadBlackMarketSettingsFromMap();
            addLog("Da tai lai cau hinh Cho Den");
        } catch (Exception ex) {
            addLog("Loi tai cau hinh Cho Den: " + ex.getMessage());
        }
    }

    private String persistBlackMarketSettings() {
        try {
            TeamServer.saveBlackMarketSettingsFromMap();
            loadBlackMarketSettingsFromMap();
            return null;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void applyLuckyBagDropRate() {
        double percent = ((Number) luckyBagDropRateSpinner.getValue()).doubleValue();
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }
        int perMillion = (int) Math.round(percent * 10000);
        Map.luckyBagDropRate = perMillion;
        String saveError = persistLuckyBagSettings();
        double savedPercent = Map.luckyBagDropRate / 10000D;
        addLog("Da cap nhat ty le roi tui qua may man: " + String.format("%.1f", savedPercent) + "% (" + Map.luckyBagDropRate + "/1,000,000)"
                + (saveError == null ? " | da luu server.ini" : " | chua luu server.ini: " + saveError));
    }

    private void applyLuckyBagRewardWeights() {
        int wLuong = ((Number) luckyWeightLuong.getValue()).intValue();
        int wLuongKhoa = ((Number) luckyWeightLuongKhoa.getValue()).intValue();
        int wXu = ((Number) luckyWeightXu.getValue()).intValue();
        int wHp = ((Number) luckyWeightHp.getValue()).intValue();
        int wMp = ((Number) luckyWeightMp.getValue()).intValue();
        Map.luckyBagRewardWeights = new int[]{
                Math.max(0, wLuong),
                Math.max(0, wLuongKhoa),
                Math.max(0, wXu),
                Math.max(0, wHp),
                Math.max(0, wMp),
                0
        };
        String saveError = persistLuckyBagSettings();
        int[] weights = Map.luckyBagRewardWeights;
        addLog("Da cap nhat ty le phan thuong tui qua may man (luong, luong khoa, xu, hp15k, mp15k) = "
                + weights[0] + "," + weights[1] + "," + weights[2] + "," + weights[3] + "," + weights[4] + " | exp=0"
                + (saveError == null ? " | da luu server.ini" : " | chua luu server.ini: " + saveError));
    }

    private void applyLuckyBagRewardAmounts() {
        int luongMin = ((Number) luckyAmountLuongMin.getValue()).intValue();
        int luongMax = ((Number) luckyAmountLuongMax.getValue()).intValue();
        int luongKhoaMin = ((Number) luckyAmountLuongKhoaMin.getValue()).intValue();
        int luongKhoaMax = ((Number) luckyAmountLuongKhoaMax.getValue()).intValue();
        int xuMin = ((Number) luckyAmountXuMin.getValue()).intValue();
        int xuMax = ((Number) luckyAmountXuMax.getValue()).intValue();
        int hpMin = ((Number) luckyAmountHpMin.getValue()).intValue();
        int hpMax = ((Number) luckyAmountHpMax.getValue()).intValue();
        int mpMin = ((Number) luckyAmountMpMin.getValue()).intValue();
        int mpMax = ((Number) luckyAmountMpMax.getValue()).intValue();
        int maxOpen = ((Number) luckyMaxOpenPerDay.getValue()).intValue();

        Map.luckyBagRewardMin = new int[]{
                Math.max(0, luongMin),
                Math.max(0, luongKhoaMin),
                Math.max(0, xuMin),
                Math.max(0, hpMin),
                Math.max(0, mpMin),
                0
        };
        Map.luckyBagRewardMax = new int[]{
                Math.max(0, luongMax),
                Math.max(0, luongKhoaMax),
                Math.max(0, xuMax),
                Math.max(0, hpMax),
                Math.max(0, mpMax),
                0
        };
        Map.luckyBagMaxOpenPerDay = Math.max(1, maxOpen);

        String saveError = persistLuckyBagSettings();
        int[] minValues = Map.luckyBagRewardMin;
        int[] maxValues = Map.luckyBagRewardMax;
        addLog("Da cap nhat so luong tui qua may man (min/max) = "
                + minValues[0] + "/" + maxValues[0] + ", "
                + minValues[1] + "/" + maxValues[1] + ", "
                + minValues[2] + "/" + maxValues[2] + ", "
                + minValues[3] + "/" + maxValues[3] + ", "
                + minValues[4] + "/" + maxValues[4]
                + " | Max mo/ngay=" + Map.luckyBagMaxOpenPerDay
                + (saveError == null ? " | da luu server.ini" : " | chua luu server.ini: " + saveError));
    }

    private String persistLuckyBagSettings() {
        try {
            TeamServer.saveLuckyBagSettingsFromMap();
            loadLuckyBagSettingsFromMap();
            return null;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void setButtonTooltips() {
        maintenanceButton.setToolTipText("Bật/Tắt chế độ bảo trì");
        stopLoginButton.setToolTipText("Dừng hoặc cho phép đăng nhập mới");
        cleanMemoryButton.setToolTipText("Dọn dẹp bộ nhớ server");
        announceButton.setToolTipText("Gửi thông báo đến toàn bộ người chơi");
        updateButton.setToolTipText("Cập nhật trạng thái server");
        lockAccountButton.setToolTipText("Kick người chơi khỏi server");
        banAccountButton.setToolTipText("Khóa tài khoản vĩnh viễn");
        unbanAccountButton.setToolTipText("Mở khóa tài khoản");
        gemManagerButton.setToolTipText("Kiểm tra hoặc thu hồi gem của người chơi");
        listOnlineButton.setToolTipText("Liệt kê danh sách người chơi đang online");
        changePasswordButton.setToolTipText("Đổi mật khẩu tài khoản");
        ambientBotsButton.setToolTipText("Mo bang roster ambient bot, xem vai tro/ca online va dat lai so luong muc tieu");
        luongSon108Button.setToolTipText("Mo bang 108 Luong Son chan Truong giang, NPC thu phi tai Tu quan va thu hoi khi can");
        buffCharacterButton.setToolTipText("Buff level, xu, luong, diem ky nang va diem tiem nang theo ten nhan vat");
        luckyBagDropApplyButton.setToolTipText("Cập nhật tỷ lệ rơi túi quà may mắn khi farm quái +-5 cấp");
        luckyRewardApplyButton.setToolTipText("Cập nhật tỷ lệ phần thưởng của túi quà may mắn");
        luckyAmountApplyButton.setToolTipText("Cập nhật số lượng (min/max) và tối đa farm/mở mỗi ngày của túi quà may mắn; đạt trần sẽ chuyển sang điểm nông dân");
    }

    private static class AdminBuffOutcome {
        String charName;
        boolean online;
        int levelBefore;
        int levelAfter;
        long xuAdded;
        int luongAdded;
        int skillPointAdded;
        int basePointAdded;
    }

    private static class BlackMarketRareSlotControls {
        String slotLabel;
        JComboBox<Map.BlackMarketAdminOption> optionCombo;
        JSpinner priceSpinner;
        Map.BlackMarketAdminOption[] options;
    }

    private static class BlackMarketMiscSlotControls {
        String slotLabel;
        JComboBox<Map.BlackMarketAdminOption> optionCombo;
        JSpinner amountSpinner;
        JSpinner priceSpinner;
    }

    private static class BlackMarketMiscCategoryControls {
        int categoryIndex;
        String label;
        Map.BlackMarketAdminOption[] options;
        BlackMarketMiscSlotControls[] slots;
    }

    private static class EventRow {
        final String key;
        final String label;
        final JComboBox<String> combo;

        EventRow(String key, String label, JComboBox<String> combo) {
            this.key = key;
            this.label = label;
            this.combo = combo;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel panel = new AdminPanel();
            panel.setVisible(true);
        });
    }

    private void handleKickPlayer() {
        String playerName = promptInput("Nhập tên nhân vật cần kick:", "Kick Player");
        if (playerName == null) {
            return;
        }
        Char player = CharManager.instance.getCharByCharName(playerName);
        if (player != null) {
            try {
                player.sendMessage(MessageCreator.createServerAlertMessage(
                        "Tài khoản của bạn tạm bị kick trong giây lát để admin giải quyết. Xin đăng nhập sau ít phút nữa",
                        ""
                ));
                player.getSession().disconnect(8);
                addLog("Đã kick tài khoản: " + playerName);
            } catch (Exception ex) {
                addLog("Lỗi khi kick tài khoản: " + ex.getMessage());
            }
        } else {
            addLog("Không tìm thấy nhân vật: " + playerName);
        }
    }

    private void handleBanAccount() {
        String playerName = promptInput("Nhập tên nhân vật cần khóa vĩnh viễn:", "Khóa Vĩnh Viễn");
        if (playerName == null) {
            return;
        }
        try {
            Connection conn = Database.instance.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "UPDATE tob_char SET ban=1 where charname='" + playerName + "'";
            stmt.execute(sql);

            // Kick player if online
            Char player = CharManager.instance.getCharByCharName(playerName);
            if (player != null) {
                player.sendMessage(MessageCreator.createServerAlertMessage(
                        "Tài khoản của bạn đã bị khóa vĩnh viễn.",
                        ""
                ));
                player.getSession().disconnect(8);
            }

            addLog("Đã khóa vĩnh viễn tài khoản: " + playerName);
            stmt.close();
            Database.connPool.free(conn);
        } catch (Exception ex) {
            addLog("Lỗi khi khóa tài khoản: " + ex.getMessage());
        }
    }

    private void handleUnbanAccount() {
        String playerName = promptInput("Nhập tên nhân vật cần mở khóa:", "Mở Khóa Tài Khoản");
        if (playerName == null) {
            return;
        }
        try {
            Connection conn = Database.instance.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "UPDATE tob_char SET ban=0 where charname='" + playerName + "'";
            stmt.execute(sql);
            addLog("Đã mở khóa tài khoản: " + playerName);
            stmt.close();
            Database.connPool.free(conn);
        } catch (Exception ex) {
            addLog("Lỗi khi mở khóa tài khoản: " + ex.getMessage());
        }
    }

    // Add this utility method inside your AdminPanel class:
    private String promptInput(String message, String title) {
        String input = JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        return (input != null && !input.trim().isEmpty()) ? input.trim() : null;
    }
}
