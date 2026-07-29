/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.NonRegisteringDriver;
import com.mysql.jdbc.PingTarget;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LoadBalancingConnectionProxy
implements InvocationHandler,
PingTarget {
    private static Method getLocalTimeMethod;
    private Connection currentConn;
    private List hostList;
    private Map liveConnections;
    private Map connectionsToHostsMap;
    private long[] responseTimes;
    private Map hostsToListIndexMap;
    boolean inTransaction = false;
    long transactionStartTime = 0L;
    Properties localProps;
    boolean isClosed = false;
    BalanceStrategy balancer;
    static /* synthetic */ Class class$java$lang$System;

    LoadBalancingConnectionProxy(List hosts, Properties props) throws SQLException {
        this.hostList = hosts;
        int numHosts = this.hostList.size();
        this.liveConnections = new HashMap(numHosts);
        this.connectionsToHostsMap = new HashMap(numHosts);
        this.responseTimes = new long[numHosts];
        this.hostsToListIndexMap = new HashMap(numHosts);
        for (int i = 0; i < numHosts; ++i) {
            this.hostsToListIndexMap.put(this.hostList.get(i), new Integer(i));
        }
        this.localProps = (Properties)props.clone();
        this.localProps.remove("HOST");
        this.localProps.remove("PORT");
        this.localProps.setProperty("useLocalSessionState", "true");
        String strategy = this.localProps.getProperty("loadBalanceStrategy", "random");
        if ("random".equals(strategy)) {
            this.balancer = new RandomBalanceStrategy();
        } else if ("bestResponseTime".equals(strategy)) {
            this.balancer = new BestResponseTimeBalanceStrategy();
        } else {
            throw SQLError.createSQLException(Messages.getString("InvalidLoadBalanceStrategy", new Object[]{strategy}), "S1009");
        }
        this.pickNewConnection();
    }

    private synchronized Connection createConnectionForHost(String hostPortSpec) throws SQLException {
        Properties connProps = (Properties)this.localProps.clone();
        String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(hostPortSpec);
        if (hostPortPair[1] == null) {
            hostPortPair[1] = "3306";
        }
        connProps.setProperty("HOST", hostPortSpec);
        connProps.setProperty("PORT", hostPortPair[1]);
        Connection conn = new Connection(hostPortSpec, Integer.parseInt(hostPortPair[1]), connProps, connProps.getProperty("DBNAME"), "jdbc:mysql://" + hostPortPair[0] + ":" + hostPortPair[1] + "/");
        this.liveConnections.put(hostPortSpec, conn);
        this.connectionsToHostsMap.put(conn, hostPortSpec);
        return conn;
    }

    void dealWithInvocationException(InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
        Throwable t = e.getTargetException();
        if (t != null) {
            String sqlState;
            if (t instanceof SQLException && (sqlState = ((SQLException)t).getSQLState()) != null && sqlState.startsWith("08")) {
                this.invalidateCurrentConnection();
            }
            throw t;
        }
        throw e;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void invalidateCurrentConnection() throws SQLException {
        try {
            if (!this.currentConn.isClosed()) {
                this.currentConn.close();
            }
            Object var2_1 = null;
            this.liveConnections.remove(this.connectionsToHostsMap.get(this.currentConn));
        }
        catch (Throwable throwable) {
            Object var2_2 = null;
            this.liveConnections.remove(this.connectionsToHostsMap.get(this.currentConn));
            this.connectionsToHostsMap.remove(this.currentConn);
            throw throwable;
        }
        this.connectionsToHostsMap.remove(this.currentConn);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("close".equals(methodName)) {
            Map map = this.liveConnections;
            synchronized (map) {
                Iterator allConnections = this.liveConnections.values().iterator();
                while (true) {
                    if (!allConnections.hasNext()) {
                        this.liveConnections.clear();
                        this.connectionsToHostsMap.clear();
                        return null;
                    }
                    ((Connection)allConnections.next()).close();
                }
            }
        }
        if ("isClosed".equals(methodName)) {
            return this.isClosed;
        }
        if (this.isClosed) {
            throw SQLError.createSQLException("No operations allowed after connection closed.", "08003");
        }
        if (!this.inTransaction) {
            this.inTransaction = true;
            this.transactionStartTime = LoadBalancingConnectionProxy.getLocalTimeBestResolution();
        }
        Object result = null;
        try {
            try {
                result = method.invoke((Object)this.currentConn, args);
                if (result != null) {
                    if (result instanceof Statement) {
                        ((Statement)result).setPingTarget(this);
                    }
                    result = this.proxyIfInterfaceIsJdbc(result, result.getClass());
                }
            }
            catch (InvocationTargetException e) {
                this.dealWithInvocationException(e);
                Object var9_10 = null;
                if (!"commit".equals(methodName)) {
                    if (!"rollback".equals(methodName)) return result;
                }
                this.inTransaction = false;
                int hostIndex = (Integer)this.hostsToListIndexMap.get(this.connectionsToHostsMap.get(this.currentConn));
                long[] lArray = this.responseTimes;
                synchronized (this.responseTimes) {
                    this.responseTimes[hostIndex] = LoadBalancingConnectionProxy.getLocalTimeBestResolution() - this.transactionStartTime;
                    // ** MonitorExit[var11_16] (shouldn't be in output)
                    this.pickNewConnection();
                    return result;
                }
            }
            Object var9_9 = null;
            if (!"commit".equals(methodName)) {
                if (!"rollback".equals(methodName)) return result;
            }
            this.inTransaction = false;
            int hostIndex = (Integer)this.hostsToListIndexMap.get(this.connectionsToHostsMap.get(this.currentConn));
            long[] lArray = this.responseTimes;
        }
        catch (Throwable throwable) {
            Object var9_11 = null;
            if (!"commit".equals(methodName)) {
                if (!"rollback".equals(methodName)) throw throwable;
            }
            this.inTransaction = false;
            int hostIndex = (Integer)this.hostsToListIndexMap.get(this.connectionsToHostsMap.get(this.currentConn));
            long[] lArray = this.responseTimes;
            synchronized (this.responseTimes) {
                this.responseTimes[hostIndex] = LoadBalancingConnectionProxy.getLocalTimeBestResolution() - this.transactionStartTime;
                // ** MonitorExit[var11_17] (shouldn't be in output)
                this.pickNewConnection();
                throw throwable;
            }
        }
        synchronized (this.responseTimes) {
            this.responseTimes[hostIndex] = LoadBalancingConnectionProxy.getLocalTimeBestResolution() - this.transactionStartTime;
            // ** MonitorExit[var11_15] (shouldn't be in output)
            this.pickNewConnection();
            return result;
        }
    }

    private synchronized void pickNewConnection() throws SQLException {
        if (this.currentConn == null) {
            this.currentConn = this.balancer.pickConnection();
            return;
        }
        Connection newConn = this.balancer.pickConnection();
        newConn.setTransactionIsolation(this.currentConn.getTransactionIsolation());
        newConn.setAutoCommit(this.currentConn.getAutoCommit());
        this.currentConn = newConn;
    }

    Object proxyIfInterfaceIsJdbc(Object toProxy, Class clazz) {
        int i = 0;
        Class<?>[] interfaces = clazz.getInterfaces();
        if (i < interfaces.length) {
            String packageName = interfaces[i].getPackage().getName();
            if ("java.sql".equals(packageName) || "javax.sql".equals(packageName)) {
                return Proxy.newProxyInstance(toProxy.getClass().getClassLoader(), interfaces, (InvocationHandler)new ConnectionErrorFiringInvocationHandler(toProxy));
            }
            return this.proxyIfInterfaceIsJdbc(toProxy, interfaces[i]);
        }
        return toProxy;
    }

    private static long getLocalTimeBestResolution() {
        if (getLocalTimeMethod != null) {
            try {
                return (Long)getLocalTimeMethod.invoke(null, null);
            }
            catch (IllegalArgumentException e) {
            }
            catch (IllegalAccessException e) {
            }
            catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
        }
        return System.currentTimeMillis();
    }

    public synchronized void doPing() throws SQLException {
        Iterator allConns = this.liveConnections.values().iterator();
        while (allConns.hasNext()) {
            ((Connection)allConns.next()).ping();
        }
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static {
        try {
            getLocalTimeMethod = (class$java$lang$System == null ? (class$java$lang$System = LoadBalancingConnectionProxy.class$("java.lang.System")) : class$java$lang$System).getMethod("nanoTime", new Class[0]);
        }
        catch (SecurityException e) {
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    class RandomBalanceStrategy
    implements BalanceStrategy {
        RandomBalanceStrategy() {
        }

        public Connection pickConnection() throws SQLException {
            int random = (int)(Math.random() * (double)LoadBalancingConnectionProxy.this.hostList.size());
            if (random == LoadBalancingConnectionProxy.this.hostList.size()) {
                --random;
            }
            String hostPortSpec = (String)LoadBalancingConnectionProxy.this.hostList.get(random);
            SQLException ex = null;
            for (int attempts = 0; attempts < 1200; ++attempts) {
                Connection conn = (Connection)LoadBalancingConnectionProxy.this.liveConnections.get(hostPortSpec);
                if (conn == null) {
                    try {
                        conn = LoadBalancingConnectionProxy.this.createConnectionForHost(hostPortSpec);
                    }
                    catch (SQLException sqlEx) {
                        ex = sqlEx;
                        if (sqlEx instanceof CommunicationsException || "08S01".equals(sqlEx.getSQLState())) {
                            try {
                                Thread.sleep(250L);
                            }
                            catch (InterruptedException e) {}
                            continue;
                        }
                        throw sqlEx;
                    }
                }
                return conn;
            }
            if (ex != null) {
                throw ex;
            }
            return null;
        }
    }

    protected class ConnectionErrorFiringInvocationHandler
    implements InvocationHandler {
        Object invokeOn = null;

        public ConnectionErrorFiringInvocationHandler(Object toInvokeOn) {
            this.invokeOn = toInvokeOn;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            try {
                result = method.invoke(this.invokeOn, args);
                if (result != null) {
                    result = LoadBalancingConnectionProxy.this.proxyIfInterfaceIsJdbc(result, result.getClass());
                }
            }
            catch (InvocationTargetException e) {
                LoadBalancingConnectionProxy.this.dealWithInvocationException(e);
            }
            return result;
        }
    }

    class BestResponseTimeBalanceStrategy
    implements BalanceStrategy {
        BestResponseTimeBalanceStrategy() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Connection pickConnection() throws SQLException {
            long minResponseTime = Long.MAX_VALUE;
            int bestHostIndex = 0;
            long[] localResponseTimes = new long[LoadBalancingConnectionProxy.this.responseTimes.length];
            long[] lArray = LoadBalancingConnectionProxy.this.responseTimes;
            synchronized (lArray) {
                System.arraycopy(LoadBalancingConnectionProxy.this.responseTimes, 0, localResponseTimes, 0, LoadBalancingConnectionProxy.this.responseTimes.length);
            }
            SQLException ex = null;
            for (int attempts = 0; attempts < 1200; ++attempts) {
                for (int i = 0; i < localResponseTimes.length; ++i) {
                    long candidateResponseTime = localResponseTimes[i];
                    if (candidateResponseTime >= minResponseTime) continue;
                    if (candidateResponseTime == 0L) {
                        bestHostIndex = i;
                        break;
                    }
                    bestHostIndex = i;
                    minResponseTime = candidateResponseTime;
                }
                if (bestHostIndex == localResponseTimes.length - 1) {
                    long[] i = LoadBalancingConnectionProxy.this.responseTimes;
                    synchronized (i) {
                        System.arraycopy(LoadBalancingConnectionProxy.this.responseTimes, 0, localResponseTimes, 0, LoadBalancingConnectionProxy.this.responseTimes.length);
                        continue;
                    }
                }
                String bestHost = (String)LoadBalancingConnectionProxy.this.hostList.get(bestHostIndex);
                Connection conn = (Connection)LoadBalancingConnectionProxy.this.liveConnections.get(bestHost);
                if (conn == null) {
                    try {
                        conn = LoadBalancingConnectionProxy.this.createConnectionForHost(bestHost);
                    }
                    catch (SQLException sqlEx) {
                        ex = sqlEx;
                        if (sqlEx instanceof CommunicationsException || "08S01".equals(sqlEx.getSQLState())) {
                            localResponseTimes[bestHostIndex] = Long.MAX_VALUE;
                            try {
                                Thread.sleep(250L);
                            }
                            catch (InterruptedException e) {}
                            continue;
                        }
                        throw sqlEx;
                    }
                }
                return conn;
            }
            if (ex != null) {
                throw ex;
            }
            return null;
        }
    }

    static interface BalanceStrategy {
        public Connection pickConnection() throws SQLException;
    }
}

