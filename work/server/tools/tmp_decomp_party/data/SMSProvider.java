/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.util.Vector;

public class SMSProvider {
    public Vector<String> decript = new Vector();
    public Vector<String> syntax = new Vector();
    public Vector<String> center = new Vector();
    public Vector<String> agent = new Vector();
    public Vector<String> provider = new Vector();

    public SMSProvider(int type) {
        this.decript.add("sms 15K: 10.000 xu");
        this.decript.add("sms 10K: 6.000 xu");
        this.decript.add("sms 15K: 10 l\u01b0\u1ee3ng");
        this.syntax.add("TEAM NAP9 ");
        this.syntax.add("TEAM NAP9 ");
        this.syntax.add("TEAM LUONG9 ");
        this.center.add("8733");
        this.center.add("8633");
        this.center.add("8733");
        this.agent.add("0");
        this.agent.add("0");
        this.agent.add("0");
    }

    public void reset() {
        this.decript.removeAll(this.decript);
        this.syntax.removeAll(this.syntax);
        this.center.removeAll(this.center);
        this.agent.removeAll(this.agent);
    }

    public void setData(Vector<String> dec, Vector<String> syn, Vector<String> center, Vector<String> agent, Vector<String> provider) {
        this.decript = dec;
        this.syntax = syn;
        this.center = center;
        this.agent = agent;
        this.provider = provider;
    }
}

