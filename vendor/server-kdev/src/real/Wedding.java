// 
// Decompiled by Procyon v0.6.0
// 

package real;

import java.util.Hashtable;

public class Wedding
{
    public int idP1;
    public int idP2;
    public int idParty;
    public byte action;
    public byte time;
    public byte typeParty;
    public boolean start;
    public String dayParty;
    public Hashtable<Integer, Integer> listMember;
    
    public Wedding() {
        this.action = 0;
        this.start = false;
        this.dayParty = "";
        this.listMember = new Hashtable<Integer, Integer>();
    }
}
