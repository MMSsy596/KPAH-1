/*
 * Decompiled with CFR 0.152.
 */
package common;

import common.log.LoggerName;
import common.log.SimpleLogger;
import java.security.AccessControlException;

public abstract class Logger {
    private static Logger logger = null;

    public static final Logger getLogger(Class cl) {
        if (logger == null) {
            Logger.initializeLogger();
        }
        return logger.getLoggerImpl(cl);
    }

    private static synchronized void initializeLogger() {
        if (logger != null) {
            return;
        }
        String loggerName = LoggerName.NAME;
        try {
            loggerName = System.getProperty("logger");
            if (loggerName == null) {
                loggerName = LoggerName.NAME;
            }
            logger = (Logger)Class.forName(loggerName).newInstance();
        }
        catch (IllegalAccessException e) {
            logger = new SimpleLogger();
            logger.warn("Could not instantiate logger " + loggerName + " using default");
        }
        catch (InstantiationException e) {
            logger = new SimpleLogger();
            logger.warn("Could not instantiate logger " + loggerName + " using default");
        }
        catch (AccessControlException e) {
            logger = new SimpleLogger();
            logger.warn("Could not instantiate logger " + loggerName + " using default");
        }
        catch (ClassNotFoundException e) {
            logger = new SimpleLogger();
            logger.warn("Could not instantiate logger " + loggerName + " using default");
        }
    }

    protected Logger() {
    }

    public abstract void debug(Object var1);

    public abstract void debug(Object var1, Throwable var2);

    public abstract void error(Object var1);

    public abstract void error(Object var1, Throwable var2);

    public abstract void fatal(Object var1);

    public abstract void fatal(Object var1, Throwable var2);

    public abstract void info(Object var1);

    public abstract void info(Object var1, Throwable var2);

    public abstract void warn(Object var1);

    public abstract void warn(Object var1, Throwable var2);

    protected abstract Logger getLoggerImpl(Class var1);

    public void setSuppressWarnings(boolean w) {
    }
}

