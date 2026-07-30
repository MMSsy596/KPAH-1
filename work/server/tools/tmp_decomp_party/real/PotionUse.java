/*
 * Decompiled with CFR 0.152.
 */
package real;

public class PotionUse {
    public long timeUse = 0L;
    public int timeDelay;
    int id = 0;

    public PotionUse(int id, int delay) {
        this.id = id;
        this.timeDelay = delay;
    }

    public boolean checkCooldown() {
        long time = System.currentTimeMillis() - this.timeUse;
        if (time < (long)this.timeDelay) {
            try {
                Thread.sleep((long)this.timeDelay - time);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.timeUse = System.currentTimeMillis();
        return true;
    }

    public boolean doUsePotion() {
        if (System.currentTimeMillis() - this.timeUse <= (long)this.timeDelay) {
            return false;
        }
        this.timeUse = System.currentTimeMillis();
        return true;
    }

    public void setCoolDown(int time) {
        this.timeDelay = time;
        this.timeUse = System.currentTimeMillis();
    }

    public void setCoolDown() {
        this.timeDelay = 350;
        this.timeUse = System.currentTimeMillis();
    }

    public void setCoolDownTown() {
        this.timeDelay = 400;
        this.timeUse = System.currentTimeMillis();
    }
}

