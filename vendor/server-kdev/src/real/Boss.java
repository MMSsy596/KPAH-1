package real;

public class Boss extends Monster {
    public static final long IDLE_RANDOM_REFRESH_TIME = 1200000L;
    public boolean isOpen = true;
    public boolean isCopy;
    public long timeLive = 0L;
    public boolean randomMap = true;
    public long timeLife = System.currentTimeMillis();
    public long lastBossAttackTime = System.currentTimeMillis();
    public long lastRandomizedBossBornTime = Long.MIN_VALUE;
    public long lastRandomizedBossLifeTime = Long.MIN_VALUE;

    public Boss(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public void checkTimeLife() {
    }

    public void markBossAttacked() {
        this.lastBossAttackTime = System.currentTimeMillis();
    }

    public void markBossRandomized() {
        long now = System.currentTimeMillis();
        this.lastBossAttackTime = now;
        this.lastRandomizedBossBornTime = this.bornTime;
        this.lastRandomizedBossLifeTime = this.timeLife;
    }

    public boolean shouldRandomizeOnAppear() {
        return this.randomMap
                && !this.isCopy()
                && this.isOpen
                && !this.isDead
                && this.hp > 0
                && this.map != null
                && (this.lastRandomizedBossBornTime != this.bornTime
                || this.lastRandomizedBossLifeTime != this.timeLife);
    }

    public boolean shouldIdleRandomRefresh(long now) {
        if (!this.randomMap || this.isCopy() || !this.isOpen || this.isDead || this.hp <= 0 || this.map == null) {
            return false;
        }

        long lastAttack = this.lastBossAttackTime;
        if (lastAttack <= 0L) {
            lastAttack = this.timeLife > 0L ? this.timeLife : this.bornTime;
        }

        return lastAttack > 0L && now - lastAttack >= IDLE_RANDOM_REFRESH_TIME;
    }

    public int getTimeReborn() {
        return -1;
    }
}
