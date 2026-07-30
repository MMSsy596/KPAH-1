/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.event;

import com.khanhdz.runtime.runtimeCharProperties;
import java.sql.ResultSet;
import data.Database;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import real.Char;

/**
 *
 * @author TOM
 */
public class TichLuyOnline {

    public static final TichLuyOnline Instance = new TichLuyOnline();

    public void onlineGame(Char player) {
        int playerId = player.charDBID;
        try {
            Connection conn = null;
            try {
                conn = Database.instance.getConnection();

                resetIfNeeded(conn, playerId);

                String query = "INSERT INTO even_online_game (playerId, lastLoginTime, lastResetTime) "
                        + "VALUES (?, NOW(), ?) "
                        + "ON DUPLICATE KEY UPDATE lastLoginTime = VALUES(lastLoginTime)";

                try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
                    stmt2.setInt(1, playerId);
                    stmt2.setString(2, getLastResetTime());
                    stmt2.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setDataOnline(playerId, player.runtimeCharProperties);
    }

    private static void resetIfNeeded(Connection conn, int playerId) throws Exception {
        String query = "UPDATE even_online_game "
                + "SET totalOnlineTimeInSeconds = 0, lastResetTime = ? , nhanQuaOnline= '0,0,0,0'"
                + "WHERE playerId = ? AND ("
                + "   DATE(lastResetTime) < CURDATE() OR "
                + "   (DATE(lastResetTime) >= CURDATE() AND lastResetTime < ?) "
                + ")";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String lastReset = getLastResetTime();
            stmt.setString(1, lastReset);
            stmt.setInt(2, playerId);
            stmt.setString(3, lastReset);
            stmt.executeUpdate();
        }
    }

    public void exitGame(int playerId) {
        try {
            try {
                Connection conn = null;
                try {
                    conn = Database.instance.getConnection();

                    String query = "UPDATE even_online_game "
                            + "SET totalOnlineTimeInSeconds = totalOnlineTimeInSeconds + TIMESTAMPDIFF(SECOND, lastLoginTime, NOW())"
                            + "WHERE playerId = ?";

                    try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
                        stmt2.setInt(1, playerId);
                        stmt2.executeUpdate();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    try {
                        Database.connPool.free(conn);
                    } catch (Exception ignored) {
                    }
                }
                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getLastResetTime() {
        LocalDate today = LocalDate.now();
        LocalTime resetTime = LocalTime.of(5, 0); // 05:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        calendar.set(Calendar.HOUR_OF_DAY, resetTime.getHour());
        calendar.set(Calendar.MINUTE, resetTime.getMinute());
        calendar.set(Calendar.SECOND, resetTime.getSecond());
        Date resetDate = calendar.getTime();
        return new Timestamp(resetDate.getTime()).toString(); // Chuyển đổi thành định dạng SQL TIMESTAMP
    }

    public void setDataOnline(int playerId, runtimeCharProperties dataChar) {

        try {
            Connection conn = null;
            try {
                conn = Database.instance.getConnection();

                String query = "SELECT totalOnlineTimeInSeconds,nhanQuaOnline "
                        + "FROM even_online_game "
                        + "WHERE playerId = ?";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, playerId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int totalOnline = rs.getInt("totalOnlineTimeInSeconds");
                            String nhanQuaOnline = rs.getString("nhanQuaOnline");

                            dataChar.setDataOnline( totalOnline, nhanQuaOnline);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    public int getGioHoatDong(int playerId) {

        try {
            Connection conn = null;
            try {
                conn = Database.instance.getConnection();

                String query = "SELECT lastLoginTime, totalOnlineTimeInSeconds "
                        + "FROM even_online_game "
                        + "WHERE playerId = ?";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, playerId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            Timestamp lastLoginTime = rs.getTimestamp("lastLoginTime");
                            int totalOnlineTimeInSeconds = rs.getInt("totalOnlineTimeInSeconds");

                            // Tính toán thời gian online từ lần đăng nhập gần nhất đến hiện tại
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime lastLogin = lastLoginTime.toLocalDateTime();
                            long additionalOnlineTimeInSeconds = ChronoUnit.SECONDS.between(lastLogin, now);

                            // Cộng thêm thời gian online từ lần đăng nhập gần nhất
                            totalOnlineTimeInSeconds += additionalOnlineTimeInSeconds;

                            // Chuyển đổi thời gian online từ giây sang giờ
                            return (int) (totalOnlineTimeInSeconds / 3600);
                        } else {
                            // Nếu không có dữ liệu, trả về 0 giờ
                            return 0;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void saveNhanQuaOnline(int playerId, String text) {
        try {
            Connection conn = null;
            try {
                conn = Database.instance.getConnection();

                String query = "UPDATE even_online_game "
                        + "SET nhanQuaOnline = ? "
                        + "WHERE playerId = ?";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, text);
                    stmt.setInt(2, playerId);
                    stmt.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    Database.connPool.free(conn);
                } catch (Exception ignored) {
                }
            }
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
