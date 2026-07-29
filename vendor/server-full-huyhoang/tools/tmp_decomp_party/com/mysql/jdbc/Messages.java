/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "com.mysql.jdbc.LocalizedErrorMessages";
    private static final ResourceBundle RESOURCE_BUNDLE;
    static /* synthetic */ Class class$com$mysql$jdbc$Messages;

    public static String getString(String key) {
        if (RESOURCE_BUNDLE == null) {
            throw new RuntimeException("Localized messages from resource bundle 'com.mysql.jdbc.LocalizedErrorMessages' not loaded during initialization of driver.");
        }
        try {
            if (key == null) {
                throw new IllegalArgumentException("Message key can not be null");
            }
            String message = RESOURCE_BUNDLE.getString(key);
            if (message == null) {
                message = "Missing error message for key '" + key + "'";
            }
            return message;
        }
        catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, Object[] args) {
        return MessageFormat.format(Messages.getString(key), args);
    }

    private Messages() {
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        ResourceBundle temp = null;
        try {
            temp = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault(), (class$com$mysql$jdbc$Messages == null ? (class$com$mysql$jdbc$Messages = Messages.class$("com.mysql.jdbc.Messages")) : class$com$mysql$jdbc$Messages).getClassLoader());
        }
        catch (Throwable t) {
            try {
                temp = ResourceBundle.getBundle(BUNDLE_NAME);
            }
            catch (Throwable t2) {
                throw new RuntimeException("Can't load resource bundle due to underlying exception " + t.toString());
            }
        }
        finally {
            RESOURCE_BUNDLE = temp;
        }
    }
}

