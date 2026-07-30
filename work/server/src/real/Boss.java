package real;

public class Boss extends Monster {
    public boolean isOpen = true;
    public boolean isCopy;
    public long timeLive = 0L;
    public boolean randomMap = true;
    public long timeLife = System.currentTimeMillis();

    public Boss(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public void checkTimeLife() {
    }

    public int getTimeReborn() {
        return -1;
    }
}
