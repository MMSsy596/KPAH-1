/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ConnectionProperties;
import com.mysql.jdbc.ConnectionPropertiesTransform;
import com.mysql.jdbc.LoadBalancingConnectionProxy;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.ReplicationConnection;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class NonRegisteringDriver
implements Driver {
    private static final String REPLICATION_URL_PREFIX = "jdbc:mysql:replication://";
    private static final String URL_PREFIX = "jdbc:mysql://";
    private static final String MXJ_URL_PREFIX = "jdbc:mysql:mxj://";
    private static final String LOADBALANCE_URL_PREFIX = "jdbc:mysql:loadbalance://";
    public static final String DBNAME_PROPERTY_KEY = "DBNAME";
    public static final boolean DEBUG = false;
    public static final int HOST_NAME_INDEX = 0;
    public static final String HOST_PROPERTY_KEY = "HOST";
    public static final String PASSWORD_PROPERTY_KEY = "password";
    public static final int PORT_NUMBER_INDEX = 1;
    public static final String PORT_PROPERTY_KEY = "PORT";
    public static final String PROPERTIES_TRANSFORM_KEY = "propertiesTransform";
    public static final boolean TRACE = false;
    public static final String USE_CONFIG_PROPERTY_KEY = "useConfigs";
    public static final String USER_PROPERTY_KEY = "user";
    static /* synthetic */ Class class$java$sql$Connection;

    static int getMajorVersionInternal() {
        return NonRegisteringDriver.safeIntParse("5");
    }

    static int getMinorVersionInternal() {
        return NonRegisteringDriver.safeIntParse("0");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static String[] parseHostPortPair(String hostPortPair) throws SQLException {
        int portIndex = hostPortPair.indexOf(":");
        String[] splitValues = new String[2];
        String hostname = null;
        if (portIndex != -1) {
            if (portIndex + 1 >= hostPortPair.length()) throw SQLError.createSQLException(Messages.getString("NonRegisteringDriver.37"), "01S00");
            String portAsString = hostPortPair.substring(portIndex + 1);
            splitValues[0] = hostname = hostPortPair.substring(0, portIndex);
            splitValues[1] = portAsString;
            return splitValues;
        } else {
            splitValues[0] = hostPortPair;
            splitValues[1] = null;
        }
        return splitValues;
    }

    private static int safeIntParse(String intAsString) {
        try {
            return Integer.parseInt(intAsString);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public boolean acceptsURL(String url) throws SQLException {
        return this.parseURL(url, null) != null;
    }

    public java.sql.Connection connect(String url, Properties info) throws SQLException {
        if (url != null) {
            if (StringUtils.startsWithIgnoreCase(url, LOADBALANCE_URL_PREFIX)) {
                return this.connectLoadBalanced(url, info);
            }
            if (StringUtils.startsWithIgnoreCase(url, REPLICATION_URL_PREFIX)) {
                return this.connectReplicationConnection(url, info);
            }
        }
        Properties props = null;
        props = this.parseURL(url, info);
        if (props == null) {
            return null;
        }
        try {
            Connection newConn = new Connection(this.host(props), this.port(props), props, this.database(props), url);
            return newConn;
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(Messages.getString("NonRegisteringDriver.17") + ex.toString() + Messages.getString("NonRegisteringDriver.18"), "08001");
        }
    }

    private java.sql.Connection connectLoadBalanced(String url, Properties info) throws SQLException {
        Properties parsedProps = this.parseURL(url, info);
        if (parsedProps == null) {
            return null;
        }
        String hostValues = parsedProps.getProperty(HOST_PROPERTY_KEY);
        ArrayList<String> hostList = null;
        if (hostValues != null) {
            hostList = StringUtils.split(hostValues, ",", true);
        }
        if (hostList == null) {
            hostList = new ArrayList<String>();
            hostList.add("localhost:3306");
        }
        LoadBalancingConnectionProxy proxyBal = new LoadBalancingConnectionProxy(hostList, parsedProps);
        return (java.sql.Connection)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{class$java$sql$Connection == null ? (class$java$sql$Connection = NonRegisteringDriver.class$("java.sql.Connection")) : class$java$sql$Connection}, (InvocationHandler)proxyBal);
    }

    private java.sql.Connection connectReplicationConnection(String url, Properties info) throws SQLException {
        Properties parsedProps = this.parseURL(url, info);
        if (parsedProps == null) {
            return null;
        }
        Properties masterProps = (Properties)parsedProps.clone();
        Properties slavesProps = (Properties)parsedProps.clone();
        slavesProps.setProperty("com.mysql.jdbc.ReplicationConnection.isSlave", "true");
        String hostValues = parsedProps.getProperty(HOST_PROPERTY_KEY);
        if (hostValues != null) {
            StringTokenizer st = new StringTokenizer(hostValues, ",");
            StringBuffer masterHost = new StringBuffer();
            StringBuffer slaveHosts = new StringBuffer();
            if (st.hasMoreTokens()) {
                String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(st.nextToken());
                if (hostPortPair[0] != null) {
                    masterHost.append(hostPortPair[0]);
                }
                if (hostPortPair[1] != null) {
                    masterHost.append(":");
                    masterHost.append(hostPortPair[1]);
                }
            }
            boolean firstSlaveHost = true;
            while (st.hasMoreTokens()) {
                String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(st.nextToken());
                if (!firstSlaveHost) {
                    slaveHosts.append(",");
                } else {
                    firstSlaveHost = false;
                }
                if (hostPortPair[0] != null) {
                    slaveHosts.append(hostPortPair[0]);
                }
                if (hostPortPair[1] == null) continue;
                slaveHosts.append(":");
                slaveHosts.append(hostPortPair[1]);
            }
            if (slaveHosts.length() == 0) {
                throw SQLError.createSQLException("Must specify at least one slave host to connect to for master/slave replication load-balancing functionality", "01S00");
            }
            masterProps.setProperty(HOST_PROPERTY_KEY, masterHost.toString());
            slavesProps.setProperty(HOST_PROPERTY_KEY, slaveHosts.toString());
        }
        return new ReplicationConnection(masterProps, slavesProps);
    }

    public String database(Properties props) {
        return props.getProperty(DBNAME_PROPERTY_KEY);
    }

    public int getMajorVersion() {
        return NonRegisteringDriver.getMajorVersionInternal();
    }

    public int getMinorVersion() {
        return NonRegisteringDriver.getMinorVersionInternal();
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        if (info == null) {
            info = new Properties();
        }
        if (url != null && url.startsWith(URL_PREFIX)) {
            info = this.parseURL(url, info);
        }
        DriverPropertyInfo hostProp = new DriverPropertyInfo(HOST_PROPERTY_KEY, info.getProperty(HOST_PROPERTY_KEY));
        hostProp.required = true;
        hostProp.description = Messages.getString("NonRegisteringDriver.3");
        DriverPropertyInfo portProp = new DriverPropertyInfo(PORT_PROPERTY_KEY, info.getProperty(PORT_PROPERTY_KEY, "3306"));
        portProp.required = false;
        portProp.description = Messages.getString("NonRegisteringDriver.7");
        DriverPropertyInfo dbProp = new DriverPropertyInfo(DBNAME_PROPERTY_KEY, info.getProperty(DBNAME_PROPERTY_KEY));
        dbProp.required = false;
        dbProp.description = "Database name";
        DriverPropertyInfo userProp = new DriverPropertyInfo(USER_PROPERTY_KEY, info.getProperty(USER_PROPERTY_KEY));
        userProp.required = true;
        userProp.description = Messages.getString("NonRegisteringDriver.13");
        DriverPropertyInfo passwordProp = new DriverPropertyInfo(PASSWORD_PROPERTY_KEY, info.getProperty(PASSWORD_PROPERTY_KEY));
        passwordProp.required = true;
        passwordProp.description = Messages.getString("NonRegisteringDriver.16");
        DriverPropertyInfo[] dpi = ConnectionProperties.exposeAsDriverPropertyInfo(info, 5);
        dpi[0] = hostProp;
        dpi[1] = portProp;
        dpi[2] = dbProp;
        dpi[3] = userProp;
        dpi[4] = passwordProp;
        return dpi;
    }

    public String host(Properties props) {
        return props.getProperty(HOST_PROPERTY_KEY, "localhost");
    }

    public boolean jdbcCompliant() {
        return false;
    }

    public Properties parseURL(String url, Properties defaults) throws SQLException {
        String propertiesTransformClassName;
        int index;
        Properties urlProps;
        Properties properties = urlProps = defaults != null ? new Properties(defaults) : new Properties();
        if (url == null) {
            return null;
        }
        if (!(StringUtils.startsWithIgnoreCase(url, URL_PREFIX) || StringUtils.startsWithIgnoreCase(url, MXJ_URL_PREFIX) || StringUtils.startsWithIgnoreCase(url, LOADBALANCE_URL_PREFIX) || StringUtils.startsWithIgnoreCase(url, REPLICATION_URL_PREFIX))) {
            return null;
        }
        int beginningOfSlashes = url.indexOf("//");
        if (StringUtils.startsWithIgnoreCase(url, MXJ_URL_PREFIX)) {
            urlProps.setProperty("socketFactory", "com.mysql.management.driverlaunched.ServerLauncherSocketFactory");
        }
        if ((index = url.indexOf("?")) != -1) {
            String paramString = url.substring(index + 1, url.length());
            url = url.substring(0, index);
            StringTokenizer queryParams = new StringTokenizer(paramString, "&");
            while (queryParams.hasMoreTokens()) {
                String parameterValuePair = queryParams.nextToken();
                int indexOfEquals = StringUtils.indexOfIgnoreCase(0, parameterValuePair, "=");
                String parameter = null;
                String value = null;
                if (indexOfEquals != -1) {
                    parameter = parameterValuePair.substring(0, indexOfEquals);
                    if (indexOfEquals + 1 < parameterValuePair.length()) {
                        value = parameterValuePair.substring(indexOfEquals + 1);
                    }
                }
                if (value == null || value.length() <= 0 || parameter == null || parameter.length() <= 0) continue;
                try {
                    urlProps.put(parameter, URLDecoder.decode(value, "UTF-8"));
                }
                catch (UnsupportedEncodingException badEncoding) {
                    urlProps.put(parameter, URLDecoder.decode(value));
                }
                catch (NoSuchMethodError nsme) {
                    urlProps.put(parameter, URLDecoder.decode(value));
                }
            }
        }
        url = url.substring(beginningOfSlashes + 2);
        String hostStuff = null;
        int slashIndex = url.indexOf("/");
        if (slashIndex != -1) {
            hostStuff = url.substring(0, slashIndex);
            if (slashIndex + 1 < url.length()) {
                urlProps.put(DBNAME_PROPERTY_KEY, url.substring(slashIndex + 1, url.length()));
            }
        } else {
            hostStuff = url;
        }
        if (hostStuff != null && hostStuff.length() > 0) {
            urlProps.put(HOST_PROPERTY_KEY, hostStuff);
        }
        if ((propertiesTransformClassName = urlProps.getProperty(PROPERTIES_TRANSFORM_KEY)) != null) {
            try {
                ConnectionPropertiesTransform propTransformer = (ConnectionPropertiesTransform)Class.forName(propertiesTransformClassName).newInstance();
                urlProps = propTransformer.transformProperties(urlProps);
            }
            catch (InstantiationException e) {
                throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00");
            }
            catch (IllegalAccessException e) {
                throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00");
            }
            catch (ClassNotFoundException e) {
                throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00");
            }
        }
        if (Util.isColdFusion() && urlProps.getProperty("autoConfigureForColdFusion", "true").equalsIgnoreCase("true")) {
            String configs = urlProps.getProperty(USE_CONFIG_PROPERTY_KEY);
            StringBuffer newConfigs = new StringBuffer();
            if (configs != null) {
                newConfigs.append(configs);
                newConfigs.append(",");
            }
            newConfigs.append("coldFusion");
            urlProps.setProperty(USE_CONFIG_PROPERTY_KEY, newConfigs.toString());
        }
        String configNames = null;
        if (defaults != null) {
            configNames = defaults.getProperty(USE_CONFIG_PROPERTY_KEY);
        }
        if (configNames == null) {
            configNames = urlProps.getProperty(USE_CONFIG_PROPERTY_KEY);
        }
        if (configNames != null) {
            List splitNames = StringUtils.split(configNames, ",", true);
            Properties configProps = new Properties();
            Iterator namesIter = splitNames.iterator();
            while (namesIter.hasNext()) {
                String configName = (String)namesIter.next();
                try {
                    InputStream configAsStream = this.getClass().getResourceAsStream("configs/" + configName + ".properties");
                    if (configAsStream == null) {
                        throw SQLError.createSQLException("Can't find configuration template named '" + configName + "'", "01S00");
                    }
                    configProps.load(configAsStream);
                }
                catch (IOException ioEx) {
                    throw SQLError.createSQLException("Unable to load configuration template '" + configName + "' due to underlying IOException: " + ioEx, "01S00");
                }
            }
            Iterator<Object> propsIter = urlProps.keySet().iterator();
            while (propsIter.hasNext()) {
                String key = propsIter.next().toString();
                String property = urlProps.getProperty(key);
                configProps.setProperty(key, property);
            }
            urlProps = configProps;
        }
        if (defaults != null) {
            Iterator<Object> propsIter = defaults.keySet().iterator();
            while (propsIter.hasNext()) {
                String key = propsIter.next().toString();
                String property = defaults.getProperty(key);
                urlProps.setProperty(key, property);
            }
        }
        return urlProps;
    }

    public int port(Properties props) {
        return Integer.parseInt(props.getProperty(PORT_PROPERTY_KEY, "3306"));
    }

    public String property(String name, Properties props) {
        return props.getProperty(name);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

