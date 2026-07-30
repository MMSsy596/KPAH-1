/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Map
 */
package real;

import data.Database;
import java.util.Vector;
import real.Char;
import real.Map;

public class CharChienTruong {
    public String name = "";
    public int team = 0;
    public int totalDam = 0;
    public int lientram = 0;
    public int currentKill = 0;
    public int region = 0;
    public int totalKill = 0;
    public int maxSkill = 0;
    public int noi_luc = 1;
    public int dam_tru = 1;
    public int dam_quai = 1;
    public int pointKillMons = 0;
    public long timeCamLang = 0L;
    public long timeCuongNo;
    public long timeHoaCong;
    public Vector<String> pLientram = new Vector();
    public long timeBuffHeKim = 0L;
    public long timeBuffHeHoa = 0L;
    public long timeBuffHeThuy = 0L;
    public long timeBuffHeTho = 0L;
    public long timeBuffHeMoc = 0L;
    public long timeHonDon;
    public long timeHonNguyen;
    public long timeBuffNoiLuc;
    public int timeRevival = 10;
    public int pcBuffNoiluc = 1;

    public void resetTimeBuff() {
        this.timeBuffHeKim = 0L;
        this.timeBuffHeHoa = 0L;
        this.timeBuffHeThuy = 0L;
        this.timeBuffHeTho = 0L;
        this.timeBuffHeMoc = 0L;
        this.timeHonDon = 0L;
        this.timeHonNguyen = 0L;
        this.timeBuffNoiLuc = 0L;
        this.pcBuffNoiluc = 1;
    }

    public int getNoiLuc() {
        if (this.getTimeBuffNoiLuc() > 0L) {
            return this.noi_luc * this.pcBuffNoiluc;
        }
        return this.noi_luc;
    }

    public void addNoiLuc(int p) {
        this.noi_luc += p;
        if (this.noi_luc > 10) {
            this.noi_luc = 10;
        }
    }

    public long getTimeBuffNoiLuc() {
        long time;
        if (this.timeBuffNoiLuc > 0L && (time = (this.timeBuffNoiLuc - System.currentTimeMillis()) / 1000L) > 0L) {
            return time;
        }
        return 0L;
    }

    public boolean isCuongNo() {
        return System.currentTimeMillis() - this.timeCuongNo < 0L;
    }

    public int isHoaCong() {
        return System.currentTimeMillis() - this.timeHoaCong < 0L ? 50 : 0;
    }

    public int isHonDon() {
        return System.currentTimeMillis() - this.timeHonDon < 0L ? 20 : 0;
    }

    public int isHonNguyen() {
        return System.currentTimeMillis() - this.timeHonNguyen < 0L ? 20 : 0;
    }

    public int getBuffHeKim() {
        if (System.currentTimeMillis() - this.timeBuffHeKim < 0L) {
            return 50;
        }
        return 0;
    }

    public int getBuffHeHoa() {
        if (System.currentTimeMillis() - this.timeBuffHeHoa < 0L) {
            return 50;
        }
        return 0;
    }

    public int getBuffHeThuy() {
        if (System.currentTimeMillis() - this.timeBuffHeThuy < 0L) {
            return 5;
        }
        return 0;
    }

    public int getBuffHeTho() {
        long a = (System.currentTimeMillis() - this.timeBuffHeTho) / 1000L;
        if (a >= 0L) {
            return 0;
        }
        if (Map.abs((int)((int)a)) > 60) {
            return 1;
        }
        if (a < 0L) {
            return 2;
        }
        return 0;
    }

    public int getBuffHeMoc() {
        if (System.currentTimeMillis() - this.timeBuffHeMoc < 0L) {
            return 20;
        }
        return 0;
    }

    public boolean isCamLang() {
        return System.currentTimeMillis() - this.timeCamLang < 0L;
    }

    public int setTimeBuff(int he, int time) {
        int id = -1;
        if (he == 2) {
            this.timeBuffHeHoa = System.currentTimeMillis() + (long)(time * 1000);
            id = Char.ID_HON_DON;
        } else if (he == 4) {
            this.timeBuffHeKim = System.currentTimeMillis() + (long)(time * 1000);
            id = Char.ID_BACH_TIEN;
        } else if (he == 1) {
            this.timeBuffHeMoc = System.currentTimeMillis() + (long)(time * 1000);
            id = Char.ID_DINH_CHU;
        } else if (he == 3) {
            this.timeBuffHeTho = System.currentTimeMillis() + (long)(time * 1000);
            id = Char.ID_HON_NGUYEN;
        } else if (he == 0) {
            this.timeBuffHeThuy = System.currentTimeMillis() + (long)(time * 1000);
            id = Char.ID_HOI_HON;
        }
        return id;
    }

    public void resetLienTran() {
        this.lientram = 0;
    }

    public void checkLienTram(String charname) {
        int i = 0;
        while (i < this.pLientram.size()) {
            if (charname.equals(this.pLientram.get(i))) {
                return;
            }
            ++i;
        }
        this.pLientram.add(charname);
        if (this.pLientram.size() > 5) {
            this.pLientram.remove(0);
        }
        ++this.lientram;
        if (this.maxSkill < this.lientram) {
            this.maxSkill = this.lientram;
            Database.instance.addCharChienTruongMoba(this.name, "lientram", this.maxSkill);
        }
    }
}

