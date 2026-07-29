/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Messages;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.TimeZone;

public class Util {
    protected static Method systemNanoTimeMethod;
    private static boolean isColdFusion;
    private static final TimeZone DEFAULT_TIMEZONE;
    private static Util enclosingInstance;
    static /* synthetic */ Class class$java$lang$System;

    public static boolean isColdFusion() {
        return isColdFusion;
    }

    protected static boolean nanoTimeAvailable() {
        return systemNanoTimeMethod != null;
    }

    static final TimeZone getDefaultTimeZone() {
        return (TimeZone)DEFAULT_TIMEZONE.clone();
    }

    static String newCrypt(String password, String seed) {
        byte b;
        double d;
        int i;
        if (password == null || password.length() == 0) {
            return password;
        }
        long[] pw = Util.newHash(seed);
        long[] msg = Util.newHash(password);
        long max = 0x3FFFFFFFL;
        long seed1 = (pw[0] ^ msg[0]) % max;
        long seed2 = (pw[1] ^ msg[1]) % max;
        char[] chars = new char[seed.length()];
        for (i = 0; i < seed.length(); ++i) {
            seed1 = (seed1 * 3L + seed2) % max;
            seed2 = (seed1 + seed2 + 33L) % max;
            d = (double)seed1 / (double)max;
            b = (byte)Math.floor(d * 31.0 + 64.0);
            chars[i] = (char)b;
        }
        seed1 = (seed1 * 3L + seed2) % max;
        seed2 = (seed1 + seed2 + 33L) % max;
        d = (double)seed1 / (double)max;
        b = (byte)Math.floor(d * 31.0);
        i = 0;
        while (i < seed.length()) {
            int n = i++;
            chars[n] = (char)(chars[n] ^ (char)b);
        }
        return new String(chars);
    }

    static long[] newHash(String password) {
        long nr = 1345345333L;
        long add = 7L;
        long nr2 = 305419889L;
        for (int i = 0; i < password.length(); ++i) {
            if (password.charAt(i) == ' ' || password.charAt(i) == '\t') continue;
            long tmp = 0xFF & password.charAt(i);
            nr ^= ((nr & 0x3FL) + add) * tmp + (nr << 8);
            nr2 += nr2 << 8 ^ nr;
            add += tmp;
        }
        long[] result = new long[]{nr & Integer.MAX_VALUE, nr2 & Integer.MAX_VALUE};
        return result;
    }

    static String oldCrypt(String password, String seed) {
        long max = 0x1FFFFFFL;
        if (password == null || password.length() == 0) {
            return password;
        }
        long hp = Util.oldHash(seed);
        long hm = Util.oldHash(password);
        long nr = hp ^ hm;
        long s1 = nr %= max;
        long s2 = nr / 2L;
        char[] chars = new char[seed.length()];
        for (int i = 0; i < seed.length(); ++i) {
            s1 = (s1 * 3L + s2) % max;
            s2 = (s1 + s2 + 33L) % max;
            double d = (double)s1 / (double)max;
            byte b = (byte)Math.floor(d * 31.0 + 64.0);
            chars[i] = (char)b;
        }
        return new String(chars);
    }

    static long oldHash(String password) {
        long nr = 1345345333L;
        long nr2 = 7L;
        for (int i = 0; i < password.length(); ++i) {
            if (password.charAt(i) == ' ' || password.charAt(i) == '\t') continue;
            long tmp = password.charAt(i);
            nr ^= ((nr & 0x3FL) + nr2) * tmp + (nr << 8);
            nr2 += tmp;
        }
        return nr & Integer.MAX_VALUE;
    }

    private static RandStructcture randomInit(long seed1, long seed2) {
        RandStructcture randStruct = enclosingInstance.new RandStructcture();
        randStruct.maxValue = 0x3FFFFFFFL;
        randStruct.maxValueDbl = randStruct.maxValue;
        randStruct.seed1 = seed1 % randStruct.maxValue;
        randStruct.seed2 = seed2 % randStruct.maxValue;
        return randStruct;
    }

    public static Object readObject(ResultSet resultSet, int index) throws Exception {
        ObjectInputStream objIn = new ObjectInputStream(resultSet.getBinaryStream(index));
        Object obj = objIn.readObject();
        objIn.close();
        return obj;
    }

    private static double rnd(RandStructcture randStruct) {
        randStruct.seed1 = (randStruct.seed1 * 3L + randStruct.seed2) % randStruct.maxValue;
        randStruct.seed2 = (randStruct.seed1 + randStruct.seed2 + 33L) % randStruct.maxValue;
        return (double)randStruct.seed1 / randStruct.maxValueDbl;
    }

    public static String scramble(String message, String password) {
        byte[] to = new byte[8];
        String val = "";
        message = message.substring(0, 8);
        if (password != null && password.length() > 0) {
            long[] hashPass = Util.newHash(password);
            long[] hashMessage = Util.newHash(message);
            RandStructcture randStruct = Util.randomInit(hashPass[0] ^ hashMessage[0], hashPass[1] ^ hashMessage[1]);
            int msgPos = 0;
            int msgLength = message.length();
            int toPos = 0;
            while (msgPos++ < msgLength) {
                to[toPos++] = (byte)(Math.floor(Util.rnd(randStruct) * 31.0) + 64.0);
            }
            byte extra = (byte)Math.floor(Util.rnd(randStruct) * 31.0);
            int i = 0;
            while (i < to.length) {
                int n = i++;
                to[n] = (byte)(to[n] ^ extra);
            }
            val = new String(to);
        }
        return val;
    }

    public static String stackTraceToString(Throwable ex) {
        StringBuffer traceBuf = new StringBuffer();
        traceBuf.append(Messages.getString("Util.1"));
        if (ex != null) {
            traceBuf.append(ex.getClass().getName());
            String message = ex.getMessage();
            if (message != null) {
                traceBuf.append(Messages.getString("Util.2"));
                traceBuf.append(message);
            }
            StringWriter out = new StringWriter();
            PrintWriter printOut = new PrintWriter(out);
            ex.printStackTrace(printOut);
            traceBuf.append(Messages.getString("Util.3"));
            traceBuf.append(out.toString());
        }
        traceBuf.append(Messages.getString("Util.4"));
        return traceBuf.toString();
    }

    public static boolean interfaceExists(String hostname) {
        try {
            Class<?> networkInterfaceClass = Class.forName("java.net.NetworkInterface");
            return networkInterfaceClass.getMethod("getByName", null).invoke(networkInterfaceClass, hostname) != null;
        }
        catch (Throwable t) {
            return false;
        }
    }

    public static long getCurrentTimeNanosOrMillis() {
        if (systemNanoTimeMethod != null) {
            try {
                return (Long)systemNanoTimeMethod.invoke(null, null);
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

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static {
        isColdFusion = false;
        try {
            systemNanoTimeMethod = (class$java$lang$System == null ? (class$java$lang$System = Util.class$("java.lang.System")) : class$java$lang$System).getMethod("nanoTime", null);
        }
        catch (SecurityException e) {
            systemNanoTimeMethod = null;
        }
        catch (NoSuchMethodException e) {
            systemNanoTimeMethod = null;
        }
        String loadedFrom = Util.stackTraceToString(new Throwable());
        isColdFusion = loadedFrom != null ? loadedFrom.indexOf("coldfusion") != -1 : false;
        DEFAULT_TIMEZONE = TimeZone.getDefault();
        enclosingInstance = new Util();
    }

    class RandStructcture {
        long maxValue;
        double maxValueDbl;
        long seed1;
        long seed2;

        RandStructcture() {
        }
    }
}

