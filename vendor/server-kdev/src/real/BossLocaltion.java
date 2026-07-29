package real;

public class BossLocaltion {
    public short mapID = 0;
    public byte x;
    public byte y;
    public byte type;
    public byte tox;
    public byte toy;

    public BossLocaltion(int map, int x, int y, int type) {
        this.x = (byte)x;
        this.y = (byte)y;
        this.mapID = (short)map;
        this.type = (byte)type;
        if (type == 1) {
            this.tox = 59;
            this.toy = 32;
        } else if (type == 0) {
            this.tox = 24;
            this.toy = 28;
        }

    }
}
