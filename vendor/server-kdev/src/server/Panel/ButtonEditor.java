package server.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import server.AdminPanel; // Để dùng adminPanel.addLog(...)
import real.CharManager;
import real.Char;
import real.MessageCreator;
import io.Message;
import real.Map;
import real.cmd.LoginHandler;
import data.Animal;
import data.Pet;
import real.Item;
import javax.swing.table.JTableHeader;
import data.Database;
import real.PotionTemplate2;


public class ButtonEditor extends DefaultCellEditor {
    private AdminPanel adminPanel;
    private JButton button;
    private JTable table;
    private List<Object[]> rows;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, JTable table, List<Object[]> rows, AdminPanel adminPanel) {
        super(checkBox);
        this.adminPanel = adminPanel;
        this.table = table;
        this.rows = rows;
        button = new JButton("Chỉnh sửa");
        button.addActionListener(e -> showEditDialog());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        selectedRow = row;
        return button;
    }

    private void showEditDialog() {
        String playerName = (String) table.getValueAt(selectedRow, 1);
        JPopupMenu popup = new JPopupMenu();
        JMenuItem buffCoinItem = new JMenuItem("Coin");
        JMenuItem buffGemItem = new JMenuItem("Item");
        JMenuItem buffExpItem = new JMenuItem("Buff Exp");
        JMenuItem viewInventoryItem = new JMenuItem("Rương");
        JMenuItem kickPlayerItem = new JMenuItem("Kick");
        JMenuItem notifyPlayerItem = new JMenuItem("Thông báo");
        JMenuItem teleportPlayerItem = new JMenuItem("Dịch chuyển"); // Thêm nút
        JMenuItem chestPassManageItem = new JMenuItem("Quản lý mật khẩu rương");

        // Thêm icon cho menu item Coin
        try {
            ImageIcon icon = new ImageIcon("map/iconclan/140.png");
            buffCoinItem.setIcon(icon);
        } catch (Exception ex) {}
        
        // Thêm icon cho menu item Item
        try {
            ImageIcon icon = new ImageIcon("map/iconclan/53.png");
            buffGemItem.setIcon(icon);
        } catch (Exception ex) {}

        // Thêm icon cho menu item Rương
        try {
            ImageIcon icon = new ImageIcon("map/images_potion/144.png");
            viewInventoryItem.setIcon(icon);
        } catch (Exception ex) {}

        // Thêm icon cho menu item Kick (nếu muốn)
        try {
            ImageIcon icon = new ImageIcon("map/iconclan/136.png");
            kickPlayerItem.setIcon(icon);
        } catch (Exception ex) {}

        // Thêm icon cho menu item Thông báo (nếu muốn)
        try {
            ImageIcon icon = new ImageIcon("map/iconclan/244.png");
            notifyPlayerItem.setIcon(icon);
        } catch (Exception ex) {}
        
        try {
            buffExpItem.setIcon(new ImageIcon("map/images_potion/26.png"));
        } catch (Exception ex) {}

        // Thêm icon cho nút "Dịch chuyển"
        try {
            ImageIcon icon = new ImageIcon("map/images_potion/158.png");
            teleportPlayerItem.setIcon(icon);
        } catch (Exception ex) {}
        
        try {
            ImageIcon icon = new ImageIcon("map/icon/89.png");
            chestPassManageItem.setIcon(icon);
        } catch (Exception ex) {}

        buffCoinItem.addActionListener(e -> showBuffCoinDialog(playerName));
        buffGemItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Char p = CharManager.instance.getCharByCharName(playerName);
                if (p == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật!");
                    return;
                }

                // Loại item
                String[] itemTypes = {"Gem", "Potion"};
                JComboBox<String> typeBox = new JComboBox<>(itemTypes);

                // --- Panel cho Gem ---
                java.util.List<GemComboItem> gemItems = new java.util.ArrayList<>();
                for (int i = 0; i < Map.gemTemplate.length; i++) {
                    if (Map.gemTemplate[i] != null) {
                        int id = Map.gemTemplate[i].id;
                        String name = Map.gemTemplate[i].name;
                        int idImage = Map.gemTemplate[i].idImage;
                        ImageIcon icon = null;
                        try { icon = new ImageIcon("map/images_gem/" + idImage + ".png"); } catch (Exception ignore) {}
                        gemItems.add(new GemComboItem(name, id, icon));
                    }
                }
                JComboBox<GemComboItem> gemBox = new JComboBox<>(gemItems.toArray(new GemComboItem[0]));
                gemBox.setRenderer(new javax.swing.ListCellRenderer<GemComboItem>() {
                    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
                    @Override
                    public java.awt.Component getListCellRendererComponent(
                            javax.swing.JList<? extends GemComboItem> list, GemComboItem value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value != null) {
                            renderer.setText(value.toString());
                            renderer.setIcon(value.icon);
                        }
                        return renderer;
                    }
                });
                JTextField gemSlField = new JTextField();
                String[] lockOptions = {"Không", "Có"};
                JComboBox<String> lockBox = new JComboBox<>(lockOptions);

                JPanel gemPanel = new JPanel(new GridLayout(3, 2, 5, 5));
                gemPanel.add(new JLabel("Chọn Gem:"));
                gemPanel.add(gemBox);
                gemPanel.add(new JLabel("Số lượng:"));
                gemPanel.add(gemSlField);
                gemPanel.add(new JLabel("Lock:"));
                gemPanel.add(lockBox);

                // --- Panel cho Potion ---
                String[] potionNames = LoginHandler.PORTION_NAME;
                java.util.List<PotionComboItem> potionItems = new java.util.ArrayList<>();
                for (int i = 0; i < Map.potionTemplates2.size(); i++) {
                    PotionTemplate2 potion = Map.potionTemplates2.get(i);
                    ImageIcon icon = null;
                    try {
                        icon = new ImageIcon("map/images_potion/" + potion.idImage + ".png");
                    } catch (Exception ignore) {}
                    potionItems.add(new PotionComboItem(potion.name, i, icon));
                }
                JComboBox<PotionComboItem> potionBox = new JComboBox<>(potionItems.toArray(new PotionComboItem[0]));
                potionBox.setRenderer(new javax.swing.ListCellRenderer<PotionComboItem>() {
                    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
                    @Override
                    public java.awt.Component getListCellRendererComponent(
                            javax.swing.JList<? extends PotionComboItem> list, PotionComboItem value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value != null) {
                            renderer.setText(value.toString());
                            renderer.setIcon(value.icon);
                        }
                        return renderer;
                    }
                });
                JTextField potionSlField = new JTextField();

                JPanel potionPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                potionPanel.add(new JLabel("Chọn Potion:"));
                potionPanel.add(potionBox);
                potionPanel.add(new JLabel("Số lượng:"));
                potionPanel.add(potionSlField);

                // --- CardLayout để chuyển đổi giữa các loại ---
                JPanel cardPanel = new JPanel(new CardLayout());
                cardPanel.add(gemPanel, "Gem");
                cardPanel.add(potionPanel, "Potion");

                // --- Panel tổng ---
                JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
                JPanel typePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                typePanel.add(new JLabel("Loại:"));
                typePanel.add(typeBox);
                mainPanel.add(typePanel, BorderLayout.NORTH);
                mainPanel.add(cardPanel, BorderLayout.CENTER);

                // Đặt kích thước nhỏ lại cho popup
                mainPanel.setPreferredSize(new Dimension(320, 160));

                // --- Sự kiện chuyển loại ---
                typeBox.addActionListener(ev -> {
                    CardLayout cl = (CardLayout) (cardPanel.getLayout());
                    cl.show(cardPanel, (String) typeBox.getSelectedItem());
                });

                // --- Hiện dialog ---
                int result = JOptionPane.showConfirmDialog(
                    null, mainPanel, "Buff Item cho " + playerName,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );
                if (result == JOptionPane.OK_OPTION) {
                    String selectedType = (String) typeBox.getSelectedItem();
                    if ("Gem".equals(selectedType)) {
                        String slStr = gemSlField.getText().trim();
                        int lock = lockBox.getSelectedIndex();
                        GemComboItem selectedGem = (GemComboItem) gemBox.getSelectedItem();
                        if (selectedGem != null && !slStr.isEmpty()) {
                            try {
                                int idGem = selectedGem.id;
                                int sl = Integer.parseInt(slStr);
                                boolean isLock = (lock == 1);
                                p.doAddGemItem(idGem, sl, isLock);
                                p.sendMessage(MessageCreator.createCharGemItem(p));
                                // Thông báo cho người chơi
                                String lockStr = isLock ? " (Khóa)" : "";
                                p.sendMessage(MessageCreator.createServerAlertMessage(
                                    "Bạn vừa được nhận được " + sl + " " + selectedGem.name + lockStr + " bởi Admin.", ""
                                ));
                                JOptionPane.showMessageDialog(null, "Buff gem thành công cho " + playerName);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Có lỗi: " + ex.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Vui lòng chọn gem và nhập số lượng!");
                        }
                    } else if ("Potion".equals(selectedType)) {
                        PotionComboItem selectedPotion = (PotionComboItem) potionBox.getSelectedItem();
                        String slStr = potionSlField.getText().trim();
                        if (selectedPotion != null && !slStr.isEmpty()) {
                            int potionIdx = selectedPotion.index;
                            int sl = Integer.parseInt(slStr);
                            p.potions[potionIdx] += sl;
                            p.calculateAttrib();
                            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                            // Thông báo cho người chơi
                            p.sendMessage(MessageCreator.createServerAlertMessage(
                                "Bạn vừa được nhận được " + sl + " " + selectedPotion.name + " bởi Admin.", ""
                            ));
                            JOptionPane.showMessageDialog(null, "Buff potion thành công cho " + playerName);
                        } else {
                            JOptionPane.showMessageDialog(null, "Vui lòng chọn potion và nhập số lượng!");
                        }
                    }
                }
            }
        });
        viewInventoryItem.addActionListener(e -> showInventoryTable(playerName));
        kickPlayerItem.addActionListener(e -> kickPlayer(playerName));
        notifyPlayerItem.addActionListener(e -> notifyPlayer(playerName));
        teleportPlayerItem.addActionListener(e -> {
            JTextField mapIdField = new JTextField();
            JTextField xField = new JTextField();
            JTextField yField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Map ID:"));
            panel.add(mapIdField);
            panel.add(new JLabel("X:"));
            panel.add(xField);
            panel.add(new JLabel("Y:"));
            panel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Dịch chuyển người chơi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int mapId = Integer.parseInt(mapIdField.getText());
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    Char p = CharManager.instance.getCharByCharName(playerName);
                    if (p != null) {
                        p.map.move2Map(p, x, y, mapId, p.inCountry);
                        JOptionPane.showMessageDialog(null, "Đã dịch chuyển " + playerName + " tới map " + mapId + " (" + x + ", " + y + ")");
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        chestPassManageItem.addActionListener(e -> {
            Char player = CharManager.instance.getCharByCharName(playerName);
            if (player == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
                return;
            }

            JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
            contentPanel.setBorder(new javax.swing.border.EmptyBorder(16, 20, 12, 20));

            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0; gbc.gridy = 0;

            formPanel.add(new JLabel("Mật khẩu hiện tại:"), gbc);
            gbc.gridx = 1;
            JTextField currentPassField = new JTextField();
            currentPassField.setEditable(false);
            currentPassField.setBackground(new Color(245,245,245));
            currentPassField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
            ));
            currentPassField.setText(
                (player.passWord == null || player.passWord.isEmpty()) ? "(Chưa có)" : player.passWord
            );
            formPanel.add(currentPassField, gbc);

            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("Mật khẩu mới:"), gbc);
            gbc.gridx = 1;
            JTextField newPassField = new JTextField();
            newPassField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
            ));
            formPanel.add(newPassField, gbc);

            gbc.gridx = 0; gbc.gridy++;
            formPanel.add(new JLabel("Nhập lại mật khẩu mới:"), gbc);
            gbc.gridx = 1;
            JTextField retypeField = new JTextField();
            retypeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
            ));
            formPanel.add(retypeField, gbc);

            gbc.gridx = 0; gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton deleteButton = new JButton("Xóa mật khẩu rương");
            formPanel.add(deleteButton, gbc);

            contentPanel.add(formPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            JButton okButton = new JButton("Đổi mật khẩu");
            JButton cancelButton = new JButton("Hủy");
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            JDialog dialog = new JDialog((Frame) null, "Quản lý mật khẩu rương", true);
            dialog.setContentPane(contentPanel);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);

            // Đổi mật khẩu
            okButton.addActionListener(ev -> {
                String newPass = newPassField.getText();
                String retype = retypeField.getText();
                if (newPass.isEmpty() || retype.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ mật khẩu mới!");
                    return;
                }
                if (!newPass.equals(retype)) {
                    JOptionPane.showMessageDialog(dialog, "Mật khẩu mới nhập lại không khớp!");
                    return;
                }
                if (newPass.length() < 4 || newPass.length() > 25) {
                    JOptionPane.showMessageDialog(dialog, "Mật khẩu mới phải từ 4 đến 25 ký tự!");
                    return;
                }
                player.passWord = newPass;
                JOptionPane.showMessageDialog(dialog, "Đổi mật khẩu rương thành công!");
                // Thông báo cho người chơi
                try {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Mật khẩu rương của bạn đã được đổi bởi Admin.", ""));
                } catch (Exception ex) {}
                dialog.dispose();
            });

            // Xóa mật khẩu
            deleteButton.addActionListener(ev -> {
                int confirm = JOptionPane.showConfirmDialog(dialog, "Bạn có chắc muốn xóa mật khẩu rương?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    player.passWord = "";
                    JOptionPane.showMessageDialog(dialog, "Đã xóa mật khẩu rương thành công!");
                    // Thông báo cho người chơi
                    try {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Mật khẩu rương của bạn đã bị xóa bởi Admin.", ""));
                    } catch (Exception ex) {}
                    dialog.dispose();
                }
            });

            cancelButton.addActionListener(ev -> dialog.dispose());

            dialog.setVisible(true);
        });

        popup.add(buffCoinItem);
        popup.add(buffGemItem);
        popup.add(buffExpItem);
        popup.add(viewInventoryItem);
        popup.add(kickPlayerItem);
        popup.add(notifyPlayerItem);
        popup.add(teleportPlayerItem); // Thêm vào menu
        popup.add(chestPassManageItem);

        popup.show(button, button.getWidth()/2, button.getHeight()/2);
        fireEditingStopped();

        buffExpItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            String charname = (String) table.getValueAt(selectedRow, 1); // hoặc id, tùy cấu trúc bảng
            showBuffExpDialog(charname);
        });
    }

    private void showBuffExpDialog(String playerName) {
        String[] buffTypes = {"Buff EXP trực tiếp", "Lên Level/Phần trăm"};
        JComboBox<String> typeBox = new JComboBox<>(buffTypes);

        // --- Panel cho Buff EXP trực tiếp ---
        JPanel expPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        expPanel.add(new JLabel("EXP:"));
        JTextField expField = new JTextField();
        expPanel.add(expField);

        // --- Panel cho Lên Level/Phần trăm ---
        JPanel levelPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        levelPanel.add(new JLabel("Level:"));
        JTextField targetLevelField = new JTextField();
        levelPanel.add(targetLevelField);
        levelPanel.add(new JLabel("(%):"));
        JTextField targetPercentField = new JTextField();
        levelPanel.add(targetPercentField);

        // --- CardLayout để chuyển đổi ---
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.add(expPanel, "Buff EXP trực tiếp");
        cardPanel.add(levelPanel, "Lên Level/Phần trăm");

        // --- Panel tổng ---
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        JPanel typePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        typePanel.add(new JLabel("Tùy chọn:"));
        typePanel.add(typeBox);
        mainPanel.add(typePanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // Sự kiện chuyển loại buff
        typeBox.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cardPanel.getLayout());
            cl.show(cardPanel, (String) typeBox.getSelectedItem());
        });

        int result = JOptionPane.showConfirmDialog(
            null, mainPanel, "Buff EXP cho " + playerName,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            Char player = CharManager.instance.getCharByCharName(playerName);
            if (player == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
                return;
            }
            if (typeBox.getSelectedIndex() == 0) {
                // Buff EXP trực tiếp
                String expStr = expField.getText().trim();
                try {
                    long exp = Long.parseLong(expStr);
                    if (exp <= 0) throw new Exception();
                    Map.addXPForChar(player, exp, true, "adminpanel");
                    JOptionPane.showMessageDialog(null, "Buff EXP thành công cho " + playerName + "!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Số EXP không hợp lệ!");
                }
            } else {
                // Buff lên Level/Phần trăm
                try {
                    int targetLevel = Integer.parseInt(targetLevelField.getText().trim());
                    double targetPercent = Double.parseDouble(targetPercentField.getText().trim());
                    int currentLevel = player.lvDetail.lv;
                    double currentPercent = player.lvDetail.percent / 10.0;
                    long expToBuff = calculateExpToBuff(currentLevel, currentPercent, targetLevel, targetPercent);
                    if (expToBuff < 0) {
                        JOptionPane.showMessageDialog(null, "Level không hợp lệ!");
                        return;
                    }
                    int confirm = JOptionPane.showConfirmDialog(null, "EXP cần buff: " + expToBuff + "\nBạn có muốn buff cho " + playerName + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Map.addXPForChar(player, expToBuff, true, "adminpanel");
                        JOptionPane.showMessageDialog(null, "Buff EXP thành công cho " + playerName + "!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu nhập không hợp lệ!");
                }
            }
        }
    }
    
    private long calculateExpToBuff(int currentLevel, double currentPercent, int targetLevel, double targetPercent) {
        long[] expMain = real.LevelDetail.expMain;
        long[] expRequire = real.LevelDetail.expRequire;
        if (currentLevel < 1 || currentLevel >= expMain.length || targetLevel < 1 || targetLevel >= expMain.length) {
            return -1;
        }
        // Chuyển % sang số thực (0-1)
        double expCurrent = expMain[currentLevel - 1] + expRequire[currentLevel] * (currentPercent / 100.0);
        double expTarget = expMain[targetLevel - 1] + expRequire[targetLevel] * (targetPercent / 100.0);
        return (long) Math.max(0, expTarget - expCurrent);
    }

    private void showBuffCoinDialog(String playerName) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField amountField = new JTextField();
        String[] types = {"Xu", "Lượng", "Lượng Khóa"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        String[] ops = {"Cộng", "Trừ"};
        JComboBox<String> opBox = new JComboBox<>(ops);

        panel.add(new JLabel("Số lượng:"));
        panel.add(amountField);
        panel.add(new JLabel("Loại:"));
        panel.add(typeBox);
        panel.add(new JLabel("Tùy chọn:"));
        panel.add(opBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Buff Coin cho " + playerName, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String amountStr = amountField.getText().trim();
            int amount = 0;
            try {
                amount = Integer.parseInt(amountStr);
                if (amount <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
                return;
            }
            String type = (String) typeBox.getSelectedItem();
            String op = (String) opBox.getSelectedItem();

            // Tìm player đang online
            Char player = CharManager.instance.getCharByCharName(playerName);
            if (player == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
                return;
            }

            // Thực hiện buff
            switch (type) {
                case "Xu":
                    if (op.equals("Cộng")) {
                        player.addXu(amount, "AdminPanel");
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        player.subXu(amount, false, "AdminPanel");
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    }
                    break;
                case "Lượng":
                    if (op.equals("Cộng")) {
                        player.addLuong(amount);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        player.subLuong(amount);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    }
                    break;
                case "Lượng Khóa":
                    if (op.equals("Cộng")) {
                        player.addLuongLock(amount);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        player.subLuongLock(amount);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    }
                    break;
            }

            // Gửi thông báo cho player
            try {
                player.sendMessage(MessageCreator.createServerAlertMessage(
                    "Tài khoản của bạn vừa được " + (op.equals("Cộng") ? "cộng" : "trừ") + " " + amount + " " + type + " bởi Admin.",
                    ""
                ));
            } catch (Exception ex) {
                // ignore
            }

            JOptionPane.showMessageDialog(null, "Đã " + op + " " + amount + " " + type + " cho " + playerName);
        }
    }

    private void showInventoryTable(String playerName) {
        Char player = CharManager.instance.getCharByCharName(playerName);
        if (player == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
            return;
        }

        java.util.List<Object[]> data = new java.util.ArrayList<>();

        // --- HIỂN THỊ POTION ---
        boolean hasPotion = false;
        if (player.potions != null && Map.potionTemplates2 != null) {
            for (int i = 0; i < player.potions.length && i < Map.potionTemplates2.size(); i++) {
                int sl = player.potions[i];
                if (sl > 0) {
                    if (!hasPotion) {
                        data.add(new Object[]{"", "Potion", "", "", ""});
                        hasPotion = true;
                    }
                    PotionTemplate2 potion = Map.potionTemplates2.get(i);
                    String name = potion.name;
                    ImageIcon icon = null;
                    try {
                        icon = new ImageIcon("map/images_potion/" + potion.idImage + ".png");
                    } catch (Exception ignore) {}
                    data.add(new Object[]{icon, name, sl, "Potion", "Vĩnh viễn"});
                }
            }
        }

        // --- HIỂN THỊ GEM ---
        boolean hasGem = false;
        // Gem mở
        if (player.listGemitem != null && Map.gemTemplate != null) {
            for (int i = 0; i < player.listGemitem.length && i < Map.gemTemplate.length; i++) {
                int sl = player.listGemitem[i];
                if (sl > 0 && Map.gemTemplate[i] != null) {
                    if (!hasGem) {
                        data.add(new Object[]{"", "Gem", "", "", ""});
                        hasGem = true;
                    }
                    String name = Map.gemTemplate[i].name;
                    int idImage = Map.gemTemplate[i].idImage;
                    ImageIcon icon = null;
                    try { icon = new ImageIcon("map/images_gem/" + idImage + ".png"); } catch (Exception ignore) {}
                    data.add(new Object[]{icon, name, sl, "Mở", "Vĩnh viễn"});
                }
            }
        }
        // Gem khóa
        if (player.listGemitemLock != null && Map.gemTemplate != null) {
            for (int i = 0; i < player.listGemitemLock.length && i < Map.gemTemplate.length; i++) {
                int sl = player.listGemitemLock[i];
                if (sl > 0 && Map.gemTemplate[i] != null) {
                    if (!hasGem) {
                        data.add(new Object[]{"", "Gem", "", "", ""});
                        hasGem = true;
                    }
                    String name = Map.gemTemplate[i].name;
                    int idImage = Map.gemTemplate[i].idImage;
                    ImageIcon icon = null;
                    try { icon = new ImageIcon("map/images_gem/" + idImage + ".png"); } catch (Exception ignore) {}
                    data.add(new Object[]{icon, name, sl, "Khóa", "Vĩnh viễn"});
                }
            }
        }

        
        // --- HIỂN THỊ ANIMAL ---
        boolean hasAnimal = false;
        if (player.animal != null && !player.animal.isEmpty()) {
            for (data.Animal animal : player.animal) {
                if (!hasAnimal) {
                    data.add(new Object[]{"", "Animal", "", "", ""});
                    hasAnimal = true;
                }
                String name = animal.name;
                int idIcon = 0;
                try {
                    idIcon = Animal.idImgIcon[animal.idImg];
                } catch (Exception ignore) {}
                ImageIcon icon = null;
                try { icon = new ImageIcon("map/images_animal/" + idIcon + ".png"); } catch (Exception ignore) {}
                String level = "Lv." + animal.level;
                String timeStr = "Vĩnh viễn";
                if (animal.texpire > 0L) {
                    long expire = animal.texpire;
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    timeStr = sdf.format(new java.util.Date(expire));
                }
                data.add(new Object[]{icon, name, level, "Animal", timeStr});
            }
        }

        boolean hasPet = false;
        if (player.pet != null && !player.pet.isEmpty()) {
            for (data.Pet pet : player.pet) {
                if (!hasPet) {
                    data.add(new Object[]{"", "Pet", "", "", ""});
                    hasPet = true;
                }
                String name = pet.getNamePet();
                int idImgPaint = pet.getIdIconPaint();
                ImageIcon icon = null;
                try { icon = new ImageIcon("map/images_animal/" + idImgPaint + ".png"); } catch (Exception ignore) {}
                String type = "Cấp " + pet.getType();
                String info = pet.getInfoSendUser();
                String timeStr = "Vĩnh viễn";
                int timeExpire = pet.getTimeExpire();
                if (timeExpire > 0) {
                    long expire = System.currentTimeMillis() + timeExpire * 1000L;
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    timeStr = sdf.format(new java.util.Date(expire));
                }
                data.add(new Object[]{icon, name, type, "Pet", timeStr});
            }
        }

        // --- HIỂN THỊ ITEM ---
        boolean hasItem = false;
        for (Item item : player.iItems) {
            if (!hasItem) {
                data.add(new Object[]{"", "Item", "", "", ""});
                hasItem = true;
            }
            String name = item.getName();
            int quantity = item.quanlity;
            int idIcon = 0;
            try { idIcon = item.getTemplate().idIcon; } catch (Exception ignore) {}
            ImageIcon icon = null;
            try { icon = new ImageIcon("map/icon/" + idIcon + ".png"); } catch (Exception ignore) {}
            String status = (item.lock == 1) ? "Khóa" : "Mở";
            String timeStr = "Vĩnh viễn";
            if (item.minuteExist > 0 && item.timeLoan > 0L) {
                long expire = item.timeLoan + item.minuteExist * 60000L;
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                timeStr = sdf.format(new java.util.Date(expire));
            }
            data.add(new Object[]{icon, name, quantity, "Item", timeStr});
        }


        Object[][] tableData = data.toArray(new Object[0][]);
        String[] columnNames = {"ID", "Tên", "Số lượng", "Loại", "Time"};

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                if (column == 2) return Integer.class;
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Xác định dòng header nhóm (Item, Potion, Gem)
                boolean isSectionHeader = false;
                Object iconVal = getValueAt(row, 0);
                Object nameVal = getValueAt(row, 1);
                Object slVal = getValueAt(row, 2);
                Object typeVal = getValueAt(row, 3);
                if ((iconVal instanceof String && ((String)iconVal).isEmpty())
                    && (slVal == null || "".equals(slVal.toString()))
                    && (typeVal == null || "".equals(typeVal.toString()))
                    && (nameVal != null && (
                        "Potion".equals(nameVal) ||
                        "Gem".equals(nameVal) ||
                        "Item".equals(nameVal)
                        // Thêm dòng này nếu muốn header nhóm cho Pet/Animal
                        || "Pet".equals(nameVal)
                        || "Animal".equals(nameVal)
                    ))) {
                    isSectionHeader = true;
                }

                if (isSectionHeader) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD, 14f));
                    c.setBackground(new Color(255, 230, 180)); // Màu vàng nhạt nổi bật
                    c.setForeground(new Color(80, 40, 0));     // Màu chữ nâu đậm
                    if (c instanceof JLabel) {
                        ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                    }
                } else {
                    // Tô màu theo loại
                    String type = (typeVal != null) ? typeVal.toString() : "";
                    if ("Potion".equals(type)) c.setBackground(new Color(240, 255, 240));
                    else if ("Mở".equals(type) || "Khóa".equals(type)) c.setBackground(new Color(255, 250, 240));
                    // Thêm màu cho Animal và Pet
                    else if ("Animal".equals(type)) c.setBackground(new Color(255, 240, 255)); // tím nhạt
                    else if ("Pet".equals(type)) c.setBackground(new Color(240, 240, 255));    // xanh nhạt
                    else c.setBackground(Color.white);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                    if (isRowSelected(row)) {
                        c.setBackground(new Color(180, 200, 255));
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        };

        // Căn giữa icon
        table.getColumnModel().getColumn(0).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                } else {
                    // Dòng header nhóm
                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
            }
        });

        // Header nổi bật
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(200, 200, 220));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        // Không cho resize cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }

        // Tooltip cho từng dòng
        table.setToolTipText("Nhấn vào từng dòng để xem chi tiết (nếu có)");

        // Chiều rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(36);  // Icon
        table.getColumnModel().getColumn(1).setPreferredWidth(140); // Tên
        table.getColumnModel().getColumn(2).setPreferredWidth(70);  // Số lượng
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Loại
        table.getColumnModel().getColumn(4).setPreferredWidth(90);  // Time

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(370, 350));

        // Thêm MouseListener để bắt sự kiện click dòng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0 && evt.getClickCount() == 2) { // double click
                    Object name = table.getValueAt(row, 1);
                    Object type = table.getValueAt(row, 3);
                    Object quantity = table.getValueAt(row, 2);
                    Object time = table.getValueAt(row, 4);

                    // Sửa điều kiện chặn header nhóm:
                    if (name != null && (quantity == null || "".equals(quantity.toString()))
                        && (type == null || "".equals(type.toString()))
                        && ("Item".equals(name.toString()) || "Potion".equals(name.toString()) || "Gem".equals(name.toString()))) {
                        return;
                    }

                   
                    showEditItemDialog(playerName, name, type, quantity, time, row, table, model);
                }
            }
        });

        JOptionPane.showMessageDialog(null, scroll, "Rương của " + playerName, JOptionPane.PLAIN_MESSAGE);
    }

  

 
    
    private void showEditItemDialog(String playerName, Object name, Object type, Object quantity, Object time, int row, JTable table, DefaultTableModel model) {
       
        String typeStr = type != null ? type.toString() : "";
        String nameStr = name != null ? name.toString() : "";
        int currentQuantity = 0;
        try {
            // Chỉ ép kiểu nếu không phải Pet
            if (!"Pet".equals(typeStr) && !"Animal".equals(typeStr)) {
                currentQuantity = (quantity instanceof Integer) ? (Integer) quantity : Integer.parseInt(quantity.toString());
            }
        } catch (Exception ex) {
            System.out.println("Lỗi ép kiểu quantity: " + ex.getMessage());
            return;
        }

        Char player = CharManager.instance.getCharByCharName(playerName);
        if (player == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật!");
            return;
        }

        if ("Potion".equals(typeStr)) {
            // Xác định index potion
            int potionIndex = -1;
            for (int i = 0; i < Map.potionTemplates2.size(); i++) {
                if (Map.potionTemplates2.get(i).name.equals(nameStr)) {
                    potionIndex = i;
                    break;
                }
            }
            if (potionIndex == -1) return;

            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField quantityField = new JTextField(String.valueOf(currentQuantity));
            panel.add(new JLabel("Số lượng:"));
            panel.add(quantityField);

            int oldQuantity = player.potions[potionIndex];
       
            int result = JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa " + nameStr, JOptionPane.OK_CANCEL_OPTION);
          
            if (result == JOptionPane.OK_OPTION) {
                int newQuantity;
                try {
                    newQuantity = Integer.parseInt(quantityField.getText().trim());
                    if (newQuantity < 0) throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
                    return;
                }
                String action = (newQuantity > oldQuantity) ? "cộng" : "trừ";
                int diff = Math.abs(newQuantity - oldQuantity);
                StringBuilder detailMsg = new StringBuilder("Admin đã thay đổi vật phẩm: ");
                detailMsg.append(nameStr)
                         .append(" - Số lượng: ").append(oldQuantity).append(" → ").append(newQuantity);

                String newTime = time != null ? time.toString() : "Vĩnh viễn";
                String timeMsg = "";
                if ("0".equals(newTime) || "Vĩnh viễn".equalsIgnoreCase(newTime)) {
                    timeMsg = " - Thời gian: Vĩnh viễn";
                } else {
                    timeMsg = " - Thời gian: " + newTime;
                }
                detailMsg.append(timeMsg);

                if (newQuantity == 0) {
                    player.potions[potionIndex] = 0;
                } else {
                    player.potions[potionIndex] = newQuantity;
                }
                model.setValueAt(newQuantity, row, 2);
                player.calculateAttrib();

                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                player.sendMessage(MessageCreator.createServerAlertMessage(detailMsg.toString(), ""));
                adminPanel.addLog("[" + playerName + "] " + detailMsg);
            }
        } else if ("Mở".equals(typeStr) || "Khóa".equals(typeStr)) {
            // Gem
            boolean isLock = "Khóa".equals(typeStr);
            int gemIndex = -1;
            for (int i = 0; i < Map.gemTemplate.length; i++) {
                if (Map.gemTemplate[i] != null && Map.gemTemplate[i].name.equals(nameStr)) {
                    gemIndex = i;
                    break;
                }
            }
            if (gemIndex == -1) {
                System.out.println("Không tìm thấy gemIndex!");
                return;
            }

            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField quantityField = new JTextField(String.valueOf(currentQuantity));
            panel.add(new JLabel("Số lượng:"));
            panel.add(quantityField);

            int oldQuantity = isLock ? player.listGemitemLock[gemIndex] : player.listGemitem[gemIndex];
           
            int result = JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa " + nameStr, JOptionPane.OK_CANCEL_OPTION);
          
            if (result == JOptionPane.OK_OPTION) {
                int newQuantity;
                try {
                    newQuantity = Integer.parseInt(quantityField.getText().trim());
                    if (newQuantity < 0) throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
                    return;
                }
                String action = (newQuantity > oldQuantity) ? "cộng" : "trừ";
                int diff = Math.abs(newQuantity - oldQuantity);
                StringBuilder detailMsg = new StringBuilder("Admin đã thay đổi vật phẩm: ");
                detailMsg.append(nameStr)
                         .append(" - Số lượng: ").append(oldQuantity).append(" → ").append(newQuantity);

                if (isLock) {
                    if (newQuantity == 0) {
                        player.delGem(gemIndex, player.listGemitemLock[gemIndex], true);
                    } else {
                        player.listGemitemLock[gemIndex] = newQuantity;
                    }
                } else {
                    if (newQuantity == 0) {
                        player.delGem(gemIndex, player.listGemitem[gemIndex], false);
                    } else {
                        player.listGemitem[gemIndex] = newQuantity;
                    }
                }
                model.setValueAt(newQuantity, row, 2);
                player.sendMessage(MessageCreator.createCharGemItem(player));
                player.sendMessage(MessageCreator.createServerAlertMessage(detailMsg.toString(), ""));
                adminPanel.addLog("[" + playerName + "] " + detailMsg);
            }
        } else if ("Item".equals(typeStr)) {
            // Item
            int itemIndex = -1;
            for (int i = 0; i < player.iItems.size(); i++) {
                Item item = player.iItems.get(i);
                if (item.getName().equals(nameStr)) {
                    if (item.quanlity == currentQuantity) {
                        itemIndex = i;
                        break;
                    }
                    if (itemIndex == -1) {
                        itemIndex = i;
                    }
                }
            }
            if (itemIndex == -1) {
                System.out.println("Không tìm thấy itemIndex!");
                return;
            }

            Item item = player.iItems.get(itemIndex);
            if (item == null) {
                System.out.println("Item null tại index=" + itemIndex);
                return;
            }

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            JTextField quantityField = new JTextField(String.valueOf(currentQuantity));
            JTextField timeField = new JTextField(time != null ? time.toString() : "Vĩnh viễn");

            panel.add(new JLabel("Số lượng:"));
            panel.add(quantityField);
            panel.add(new JLabel("Thời gian (dd-MM-yyyy HH:mm:ss hoặc 'Vĩnh viễn'):"));
            panel.add(timeField);

            int oldQuantity = item.quanlity;
          
            int result = JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa " + nameStr, JOptionPane.OK_CANCEL_OPTION);
       
            if (result == JOptionPane.OK_OPTION) {
                int newQuantity;
                try {
                    newQuantity = Integer.parseInt(quantityField.getText().trim());
                    if (newQuantity < 0) throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
                    return;
                }
                StringBuilder detailMsg = new StringBuilder("Admin đã thay đổi vật phẩm: ");
                detailMsg.append(nameStr)
                         .append(" - Số lượng: ").append(oldQuantity).append(" → ").append(newQuantity);

                if (newQuantity == 0) {
                    player.iItems.remove(itemIndex);
                    model.removeRow(row);
                } else {
                    item.quanlity = newQuantity;
                    // Xử lý thời gian
                    String newTime = timeField.getText().trim();
                    String timeMsg = "";
                    if ("0".equals(newTime) || "Vĩnh viễn".equalsIgnoreCase(newTime)) {
                        item.minuteExist = 0;
                        item.timeLoan = 0L;
                        timeMsg = " - Thời gian: Vĩnh viễn";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            long expire = sdf.parse(newTime).getTime();
                            long now = System.currentTimeMillis();
                            if (expire <= now) {
                                JOptionPane.showMessageDialog(null, "Thời gian hết hạn phải lớn hơn thời gian hiện tại!");
                                return;
                            }
                            item.timeLoan = now;
                            item.minuteExist = (int) ((expire - now) / 60000L);
                            timeMsg = " - Thời gian: " + newTime;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Thời gian không hợp lệ!");
                            return;
                        }
                    }
                    detailMsg.append(timeMsg);
                    model.setValueAt(newQuantity, row, 2);
                    model.setValueAt(newTime, row, 4);
                }
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                player.sendMessage(MessageCreator.createServerAlertMessage(detailMsg.toString(), ""));
                adminPanel.addLog("[" + playerName + "] " + detailMsg);
            }
        } else if ("Pet".equals(typeStr)) {
            // Tìm pet theo tên
            int petIndex = -1;
            for (int i = 0; i < player.pet.size(); i++) {
                data.Pet pet = player.pet.get(i);
                if (pet.getNamePet().equals(nameStr)) {
                    petIndex = i;
                    break;
                }
            }
            if (petIndex == -1) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy Pet!");
                return;
            }
            final int petIndexFinal = petIndex; // <-- Add this line
            data.Pet pet = player.pet.get(petIndexFinal);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            JTextField timeField = new JTextField(time != null ? time.toString() : "Vĩnh viễn");
            JButton deleteButton = new JButton("Xóa Pet này");

            panel.add(new JLabel("HSD (dd-MM-yyyy HH:mm:ss hoặc 'Vĩnh viễn'):"));
            panel.add(timeField);
            panel.add(new JLabel(""));
            panel.add(deleteButton);

            // Tạo dialog
            JDialog dialog = new JDialog((Frame) null, "Chỉnh sửa Pet", true);
            dialog.setLayout(new BorderLayout());
            dialog.add(panel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("Lưu");
            JButton cancelButton = new JButton("Hủy");
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            // Xử lý xóa Pet
            deleteButton.addActionListener(ev -> {
                if (pet.place == 1) {
                    JOptionPane.showMessageDialog(dialog, "Không thể xóa Pet đang sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(dialog, "Bạn có chắc muốn xóa Pet này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    player.pet.remove(petIndexFinal); // <-- Use petIndexFinal here
                    System.out.println("pet.idDB: " + pet.idDB + " petIndexFinal: " + petIndexFinal);
                    Database.instance.deletePet(pet.idDB);
                    model.removeRow(row);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                    player.sendMessage(MessageCreator.createServerAlertMessage("Admin đã xóa Pet: " + nameStr, ""));
                    adminPanel.addLog("[" + playerName + "] Admin đã xóa Pet: " + nameStr);
                    dialog.dispose();
                }
            });

            // Xử lý lưu thay đổi hạn sử dụng
            okButton.addActionListener(ev -> {
                String newTime = timeField.getText().trim();
                String timeMsg = "";
                if ("0".equals(newTime) || "Vĩnh viễn".equalsIgnoreCase(newTime)) {
                    pet.setTimeExpire(0);
                    timeMsg = " - Thời gian: Vĩnh viễn";
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                } else {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        long expire = sdf.parse(newTime).getTime();
                        long now = System.currentTimeMillis();
                        if (expire <= now) {
                            JOptionPane.showMessageDialog(dialog, "Thời gian hết hạn phải lớn hơn thời gian hiện tại!");
                            return;
                        }
                        int seconds = (int) ((expire - now) / 1000L);
                        pet.setTimeExpire(seconds);
                        timeMsg = " - Thời gian: " + newTime;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Thời gian không hợp lệ!");
                        return;
                    }
                }
                model.setValueAt(newTime, row, 4);
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                player.sendMessage(MessageCreator.createServerAlertMessage("Admin đã thay đổi hạn sử dụng Pet: " + nameStr + timeMsg, ""));
                adminPanel.addLog("[" + playerName + "] Admin đã thay đổi hạn sử dụng Pet: " + nameStr + timeMsg);
                dialog.dispose();
            });

            cancelButton.addActionListener(ev -> dialog.dispose());

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else if ("Animal".equals(typeStr)) {
            // Tìm animal theo tên
            int animalIndex = -1;
            for (int i = 0; i < player.animal.size(); i++) {
                data.Animal animal = player.animal.get(i);
                if (animal.getName().equals(nameStr)) {
                    animalIndex = i;
                    break;
                }
            }
            if (animalIndex == -1) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy Animal!");
                return;
            }
            final int animalIndexFinal = animalIndex;
            data.Animal animal = player.animal.get(animalIndexFinal);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            JTextField timeField = new JTextField(time != null ? time.toString() : "Vĩnh viễn");
            JButton deleteButton = new JButton("Xóa Animal này");

            panel.add(new JLabel("Hạn sử dụng (dd-MM-yyyy HH:mm:ss hoặc 'Vĩnh viễn'):"));
            panel.add(timeField);
            panel.add(new JLabel(""));
            panel.add(deleteButton);

            // Tạo dialog
            JDialog dialog = new JDialog((Frame) null, "Chỉnh sửa Animal", true);
            dialog.setLayout(new BorderLayout());
            dialog.add(panel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("Lưu");
            JButton cancelButton = new JButton("Hủy");
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            // Xử lý xóa Animal
            deleteButton.addActionListener(ev -> {
                if (animal.place == 1) {
                    JOptionPane.showMessageDialog(dialog, "Không thể xóa Animal đang sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(dialog, "Bạn có chắc muốn xóa Animal này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    player.animal.remove(animalIndexFinal);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                    Database.instance.delAnimal(animal.dbid);
                    model.removeRow(row);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                    player.sendMessage(MessageCreator.createServerAlertMessage("Admin đã xóa Animal: " + nameStr, ""));
                    adminPanel.addLog("[" + playerName + "] Admin đã xóa Animal: " + nameStr);
                    dialog.dispose();
                }
            });

            // Xử lý lưu thay đổi hạn sử dụng
            okButton.addActionListener(ev -> {
                String newTime = timeField.getText().trim();
                String timeMsg = "";
                if ("0".equals(newTime) || "Vĩnh viễn".equalsIgnoreCase(newTime)) {
                    animal.texpire = 0L;
                    timeMsg = " - Thời gian: Vĩnh viễn";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        long expire = sdf.parse(newTime).getTime();
                        long now = System.currentTimeMillis();
                        if (expire <= now) {
                            JOptionPane.showMessageDialog(dialog, "Thời gian hết hạn phải lớn hơn thời gian hiện tại!");
                            return;
                        }
                        animal.texpire = expire;
                        timeMsg = " - Thời gian: " + newTime;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Thời gian không hợp lệ!");
                        return;
                    }
                }
                model.setValueAt(newTime, row, 4);
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                player.sendMessage(MessageCreator.createServerAlertMessage("Admin đã thay đổi hạn sử dụng Animal: " + nameStr + timeMsg, ""));
                adminPanel.addLog("[" + playerName + "] Admin đã thay đổi hạn sử dụng Animal: " + nameStr + timeMsg);
                dialog.dispose();
            });

            cancelButton.addActionListener(ev -> dialog.dispose());

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    private void kickPlayer(String playerName) {
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Bạn có chắc chắn muốn kick " + playerName + " khỏi server?",
            "Xác nhận Kick",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            Char player = CharManager.instance.getCharByCharName(playerName);
            if (player != null) {
                try {
                    player.sendMessage(MessageCreator.createServerAlertMessage(
                        "Tài khoản của bạn tạm bị kick trong giây lát để admin giải quyết. Xin đăng nhập sau ít phút nữa", 
                        ""
                    ));
                    player.getSession().disconnect(8);
                    adminPanel.addLog("Đã kick tài khoản: " + playerName);
                    JOptionPane.showMessageDialog(null, "Đã kick " + playerName + " khỏi server!");
                } catch (Exception ex) {
                    adminPanel. addLog("Lỗi khi kick tài khoản: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
            }
        }
    }

    private void notifyPlayer(String playerName) {
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
            null,
            panel,
            "Gửi thông báo cho " + playerName,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            String message = messageArea.getText().trim();
            if (!message.isEmpty()) {
                Char player = CharManager.instance.getCharByCharName(playerName);
                if (player != null) {
                    try {
                        Message msg;
                        if (topRadio.isSelected()) {
                            msg = MessageCreator.createthongbao(message);
                        } else if (bottomRadio.isSelected()) {
                            msg = MessageCreator.createServerAlertAutoOffMessage(message);
                        } else {
                            msg = MessageCreator.createServerAlertMessage(message, "");
                        }
                        player.sendMessage(msg);
                        adminPanel.addLog("Đã gửi thông báo riêng cho " + playerName + ": " + message);
                        JOptionPane.showMessageDialog(null, "Đã gửi thông báo cho " + playerName);
                    } catch (Exception ex) {
                        adminPanel.addLog("Lỗi gửi thông báo: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Lỗi gửi thông báo: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân vật hoặc nhân vật đã offline!");
                }
            }
        }
    }
}