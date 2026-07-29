/*
 * Decompiled with CFR 0.152.
 */
package common.log;

public class LoggerName {
    public static final String NAME = (class$common$log$SimpleLogger == null ? (class$common$log$SimpleLogger = LoggerName.class$("common.log.SimpleLogger")) : class$common$log$SimpleLogger).getName();
    static /* synthetic */ Class class$common$log$SimpleLogger;

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

