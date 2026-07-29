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
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import data.Animal;
import io.Message;
import io.SessionManager;
import real.AdminHandler;
import real.CharManager;
import real.MessageCreator;
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
            "nhapet", "nhathu", "nhacaythan", "Xaphu", "lequan","admin"
        };
        for (String name : npcNames) NPC_NAMES.add(name.toLowerCase());
    }

    public AdminPanel() {
        setTitle("Quản Lý Máy Chủ");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
        initEventHandlers();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
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

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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

        JPanel serverPanel = createButtonPanel("Quản Lý Server", maintenanceButton, stopLoginButton, cleanMemoryButton, announceButton, updateButton, listOnlineButton);
        JPanel accountPanel = createButtonPanel("Tài Khoản", banAccountButton, unbanAccountButton, lockAccountButton, changePasswordButton);
        JPanel playerPanel = createButtonPanel("Player", listOnlineButton, gemManagerButton);

        // Đặt cùng chiều cao cho các panel
        Dimension panelSize = new Dimension(220, 220); // hoặc cao hơn nếu cần
        serverPanel.setPreferredSize(panelSize);
        accountPanel.setPreferredSize(panelSize);
        playerPanel.setPreferredSize(panelSize);

        // Đảm bảo các panel căn top khi dùng BoxLayout
        serverPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        accountPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        playerPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // ... existing code ...
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.add(serverPanel);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(accountPanel);
        controlsPanel.add(Box.createHorizontalStrut(10));
controlsPanel.add(playerPanel);

        // Thêm đoạn này:
        JPanel controlsWrapper = new JPanel(new BorderLayout());
        controlsWrapper.add(controlsPanel, BorderLayout.NORTH);

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

            int result = JOptionPane.showConfirmDialog(
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
                
                int option = JOptionPane.showConfirmDialog(
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
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng cần kiểm tra!");
                        return;
                    }
                    
                    try {
                        Integer.parseInt(quantity);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải là số!");
                        return;
                    }
                    
                    // Xác định điều kiện so sánh
                    String compareType = "";
                    switch(compareBox.getSelectedIndex()) {
                        case 0: compareType = "greater"; break;
                        case 1: compareType = "less"; break;
                        case 2: compareType = "equal"; break;
                    }
                    
                    // Xác định loại gem cần kiểm tra
                    String gemType = "";
                    if (unlockedGemButton.isSelected()) gemType = "soluong";
                    else if (lockedGemButton.isSelected()) gemType = "slock";
                    else gemType = "both";
                    
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
                
                int option = JOptionPane.showConfirmDialog(
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
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân vật!");
                        return;
                    }
                    if (itemIds.length != quantities.length) {
                        JOptionPane.showMessageDialog(this, "Số lượng ID và số lượng vật phẩm không khớp!");
                        return;
                    }
                    
                    try {
                        // Kiểm tra người chơi có online không
                        Char player = CharManager.instance.getCharByCharName(charName);
                        if (player != null) {
                            JOptionPane.showMessageDialog(this, "Người chơi đang online. Vui lòng chờ người chơi offline!");
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
                                String updateSql = "UPDATE tob_gem_new SET " + 
                                                 (isLockedGem ? "slock" : "soluong") + 
                                                 "='" + String.join(",", amounts) + 
                                                 "' WHERE owner IN (SELECT id FROM tob_char WHERE charname='" + charName + "')";
                                stmt.executeUpdate(updateSql);
                                
                                // Log kết quả
                                addLog(logMsg.toString());
                                JOptionPane.showMessageDialog(this, "Đã thu hồi gem thành công!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu gem của nhân vật: " + charName);
                            }
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            stmt.close();
                            Database.connPool.free(conn);
                        }
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "ID gem và số lượng phải là số!");
                    } catch (Exception ex) {
                        addLog("Lỗi khi thu hồi gem: " + ex.getMessage());
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
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
                JOptionPane.showMessageDialog(this, "Không có người chơi nào đang online.", "Danh Sách Người Chơi Online", JOptionPane.INFORMATION_MESSAGE);
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

            JOptionPane.showMessageDialog(this, scroll, "Danh Sách Người Chơi Online (" + rows.size() + ")", JOptionPane.PLAIN_MESSAGE);
        });

        changePasswordButton.addActionListener(e -> handleChangePassword());
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

        int result = JOptionPane.showConfirmDialog(this, panel, "Đổi mật khẩu tài khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String pass1 = passwordField.getText();
            String pass2 = retypeField.getText();

            if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
                return;
            }
            try {
                String hash = sqlPassword(pass1);
                Connection conn = Database.instance.getConnection();
                Statement stmt = conn.createStatement();
                int updated = stmt.executeUpdate("UPDATE team_user SET password='" + hash + "' WHERE username='" + username + "'");
                stmt.close();
                Database.connPool.free(conn);
                if (updated > 0) {
                    addLog("Đã đổi mật khẩu cho tài khoản: " + username);
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
                }
            } catch (Exception ex) {
                addLog("Lỗi đổi mật khẩu: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng nhập số phút hợp lệ (1-60)",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
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
        int confirm = JOptionPane.showConfirmDialog(
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
        logArea.append("[" + timestamp + "] " + message + "\n");
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
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(2, 5, 2, 5)); // Add padding for better appearance
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        return button;
    }

    private TitledBorder createGroupBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            title
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel panel = new AdminPanel();
            panel.setVisible(true);
        });
    }

    private void handleKickPlayer() {
        String playerName = promptInput("Nhập tên nhân vật cần kick:", "Kick Player");
        if (playerName == null) return;
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
        if (playerName == null) return;
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
        if (playerName == null) return;
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




