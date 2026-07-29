/*
 * Decompiled with CFR 0.152.
 */
package io;

import io.Session;
import java.util.Vector;

public class SessionManager {
    public static SessionManager instance = new SessionManager();
    public Vector<Session> sessionList = new Vector();

    private SessionManager() {
    }

    public int size() {
        return this.sessionList.size();
    }

    public Session get(int index) {
        try {
            return this.sessionList.get(index);
        }
        catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void add(Session s) {
        this.sessionList.add(s);
    }

    public void remove(Session s) {
        this.sessionList.remove(s);
    }

    public void removeSession(Session s) {
        int size = this.sessionList.size();
        int i = 0;
        while (i < size) {
            try {
                if (this.sessionList.get((int)i).userID == s.userID) {
                    this.sessionList.remove(i);
                    return;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }
}

