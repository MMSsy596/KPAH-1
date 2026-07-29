package real;

public class ShopTemplate extends OtherItem {
    public byte tab;
    public short id;
    public short realId;
    public int dbid;
    public int dbownerId;
    public int ownerId;
    private int price;
    private int priceSale;
    public String name = "";
    public String decript = "";
    public short value;
    public int idImage = 0;
    public byte type = 0;
    public byte sell;
    public int belongUser;
    public byte place;

    public ShopTemplate() {
    }

    public ShopTemplate(int id, int idImg, String name, String dec, int value, int price, int type, int tab, int sell, int priceSale) 
    {
        this.id = (short)id;
        this.name = name;
        this.decript = dec;
        this.value = (short)value;
        this.idImage = (int)idImg;
        this.price = price;
        this.type = (byte)type;
        this.tab = (byte)tab;
        this.sell = (byte)sell;
        this.priceSale = priceSale;
    }

    public void coppy(ShopTemplate g1, ShopTemplate g2) {
        g1.id = g2.id;
        g1.name = g2.name;
        g1.decript = g2.decript;
        g1.value = g2.value;
        g1.idImage = g2.idImage;
        g1.price = g2.price;
    }

    public int getPrice(boolean isSale) {
        if (Char.isSuKienBlackFriday() && this.priceSale != this.price) {
            int pr = this.price / 2;
            if (pr <= 0) {
                pr = 1;
            }

            return pr;
        } else {
            return isSale ? this.priceSale : this.price;
        }
    }
}
