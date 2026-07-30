/*
 * Decompiled with CFR 0.152.
 */
package real;

public class LocationMap {
    int x;
    int y;
    int tilex;
    int tiley;
    int mapout;
    int xout;
    int yout;

    public LocationMap(int x, int y, int tilex, int tiley, int mapout, int xout, int yout) {
        this.x = x;
        this.y = y;
        this.tilex = tilex;
        this.tiley = tiley;
        this.mapout = mapout;
        this.xout = xout;
        this.yout = yout;
    }

    public boolean checkCanChangeMap(int xc, int yc, int xout, int yout, int mapout) {
        if (xout != this.xout || yout != this.yout) {
            return false;
        }
        return xc >= this.x * 16 && xc <= (this.x + this.tilex) * 16 && yc >= this.y * 16 && yc <= (this.y + this.tiley) * 16 && this.mapout == mapout;
    }
}

