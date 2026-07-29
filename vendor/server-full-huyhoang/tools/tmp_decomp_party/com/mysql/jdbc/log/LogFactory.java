/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.log;

import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.log.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class LogFactory {
    static /* synthetic */ Class class$com$mysql$jdbc$log$Log;
    static /* synthetic */ Class class$java$lang$String;

    public static Log getLogger(String className, String instanceName) throws SQLException {
        if (className == null) {
            throw SQLError.createSQLException("Logger class can not be NULL", "S1009");
        }
        if (instanceName == null) {
            throw SQLError.createSQLException("Logger instance name can not be NULL", "S1009");
        }
        try {
            Class<?> loggerClass = null;
            try {
                loggerClass = Class.forName(className);
            }
            catch (ClassNotFoundException nfe) {
                loggerClass = Class.forName((class$com$mysql$jdbc$log$Log == null ? (class$com$mysql$jdbc$log$Log = LogFactory.class$("com.mysql.jdbc.log.Log")) : class$com$mysql$jdbc$log$Log).getPackage().getName() + "." + className);
            }
            Constructor<?> constructor = loggerClass.getConstructor(class$java$lang$String == null ? (class$java$lang$String = LogFactory.class$("java.lang.String")) : class$java$lang$String);
            return (Log)constructor.newInstance(instanceName);
        }
        catch (ClassNotFoundException cnfe) {
            throw SQLError.createSQLException("Unable to load class for logger '" + className + "'", "S1009");
        }
        catch (NoSuchMethodException nsme) {
            throw SQLError.createSQLException("Logger class does not have a single-arg constructor that takes an instance name", "S1009");
        }
        catch (InstantiationException inse) {
            throw SQLError.createSQLException("Unable to instantiate logger class '" + className + "', exception in constructor?", "S1009");
        }
        catch (InvocationTargetException ite) {
            throw SQLError.createSQLException("Unable to instantiate logger class '" + className + "', exception in constructor?", "S1009");
        }
        catch (IllegalAccessException iae) {
            throw SQLError.createSQLException("Unable to instantiate logger class '" + className + "', constructor not public", "S1009");
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Logger class '" + className + "' does not implement the '" + (class$com$mysql$jdbc$log$Log == null ? (class$com$mysql$jdbc$log$Log = LogFactory.class$("com.mysql.jdbc.log.Log")) : class$com$mysql$jdbc$log$Log).getName() + "' interface", "S1009");
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
}

