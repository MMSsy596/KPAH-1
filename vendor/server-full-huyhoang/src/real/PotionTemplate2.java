package real;

public class PotionTemplate2 {
    public short id;
    public short idImage;
    public short delay;
    public boolean isTrade;
    public String name;
    public String name2;
    public int price;
    public int recovered;

    public PotionTemplate2(short id, short idImage, short delay, boolean isTrade, String name, String name2, int price, int recovered) {
        this.id = id;
        this.idImage = idImage;
        this.delay = delay;
        this.isTrade = isTrade;
        this.name = name;
        this.name2 = name2;
        this.price = price;
        this.recovered = recovered;
    }

    public PotionTemplate2(int price, int recovered) {
       
        this.price = price;
        this.recovered = recovered;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getPrice() {
        return price;
    }

    public short getId() {
        return id;
    }
    public short getDelay() {
        return delay;
    }
    
    public boolean getIsTrade() {
        return isTrade;
    }
  
}