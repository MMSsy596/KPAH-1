/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ConnPool {
    public String url;
    public String user;
    public String password;
    public int countConnection = 0;
    public static String driver = "com.mysql.jdbc.Driver";
    List<Connection> connsNap = new Vector<Connection>();
    int timeout = 30000;
    int maxConn;
    public volatile LinkedList<Connection> pool = new LinkedList();

    public ConnPool(String url, String user, String password, int max) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConn = max;
        System.out.println("URL: " + this.url + " | USER: " + this.user + " | PASS: " + this.password);
        this.initPool();
    }

    public static Connection newConnectBadwords() throws SQLException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection("jdbc:mysql://localhost/webnew", "root", "web1!2@3#bew");
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Connection newConnection() throws SQLException {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(this.url, this.user, this.password);
            return conn;
        }
        catch (ClassNotFoundException cnfe) {
            throw new SQLException("Can't find class for driver: " + driver);
        }
    }

    public void initPool() {
        int i = 0;
        while (i < this.maxConn) {
            try {
                Connection c = this.newConnection();
                if (c != null) {
                    this.addConToPool(c);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addConToPool(Connection con) {
        if (this.pool.size() < this.maxConn) {
            if (!this.pool.contains(con)) {
                LinkedList<Connection> linkedList = this.pool;
                synchronized (linkedList) {
                    this.pool.addLast(con);
                    this.pool.notifyAll();
                }
            }
        } else {
            try {
                con.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
            con = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeIdleConnection() {
        int poolSize = this.pool.size();
        int i = 0;
        while (i < poolSize) {
            Connection idleCon = null;
            LinkedList<Connection> linkedList = this.pool;
            synchronized (linkedList) {
                idleCon = this.pool.removeFirst();
                this.pool.notifyAll();
            }
            try {
                idleCon.close();
                idleCon = null;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            ++i;
        }
        if (poolSize > this.maxConn) {
            poolSize = this.maxConn;
        }
        i = 0;
        while (i < poolSize) {
            LinkedList<Connection> linkedList = this.pool;
            synchronized (linkedList) {
                try {
                    this.pool.add(this.newConnection());
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            ++i;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Connection getConFromPool() throws SQLException {
        if (this.pool.size() == 0) {
            this.initPool();
        }
        Connection con = null;
        LinkedList<Connection> linkedList = this.pool;
        synchronized (linkedList) {
            con = this.pool.removeFirst();
        }
        try {
            if (con != null && !con.isClosed()) {
                ++this.countConnection;
                return con;
            }
            return this.getConnection();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.getConFromPool();
    }

    public void close(Connection conn) {
        try {
            conn.close();
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
    }

    public void freeAll() {
        int i = 0;
        while (i < this.pool.size()) {
            try {
                this.pool.remove(i).close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        try {
            this.pool.removeAll(this.pool);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void free(Connection conn) {
        if (conn != null) {
            this.addConToPool(conn);
        }
        --this.countConnection;
        if (this.countConnection < 0) {
            this.countConnection = 0;
        }
    }
}

