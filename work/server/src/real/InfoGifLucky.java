
package real;

import data.Database;
import data.GemItem;
import real.cmd.LoginHandler;
import server.TeamServer;

public class InfoGifLucky {
    public byte id = 0;
    public int value = 0;
    public int idGif = -1;
    short typeBox = 0;
    public static short[][] idMaterial = new short[][]{{71, 78, 85, 92, 99}, {72, 79, 86, 93, 100}, {73, 80, 87, 94, 101}, {106, 113, 120, 127, 134}, {107, 114, 121, 128, 135}, {108, 115, 122, 129, 136}};

    public InfoGifLucky(byte id, short type) {
        this.id = id;
        this.typeBox = type;
    }

    public void createGifSilver(boolean spc, byte money) {
        // Common multiplier array for cases 0 and 1
        final byte[] multipliers = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,  // 14 ones
            2, 2, 2, 2, 2, 2,                          // 6 twos
            3, 3, 3,                                   // 3 threes
            4, 4, 4, 4, 4, 4, 4,                       // 7 fours
            5, 5, 5, 5, 5, 5                           // 6 fives
        };
    
        switch (this.id) {
            case 0:
                this.value = multipliers[Map.r.nextInt(multipliers.length)] * 10000;
                break;
                
           case 1:
           if (TeamServer.LuckyExp) {
               this.value = multipliers[Map.r.nextInt(multipliers.length)] * 100000;
            } else {
                this.value = 0; // hoặc bỏ qua, không tạo EXP
            }   
               break;
        
                
            case 2:
                if (!spc) {
                    int ran = Map.r.nextInt(100);
                    if (ran < 10) {
                        // Material gems array
                        final short[] materialGems = {69, 76, 83, 90, 97};
                        this.idGif = materialGems[Map.r.nextInt(materialGems.length)];
                        
                        // Amount based on money parameter
                        final byte[] amounts = {2, 4, 6};
                        this.value = money >= 0 && money < amounts.length ? amounts[money] : amounts[0];
                    } else {
                        // Random potion ID (61 or 62)
                        this.idGif = (short)(Map.r.nextInt(2) + 61);
                        this.value = Map.r.nextInt(2) + 2; // Random value 2-3
                    }
                } else {
                    // Special material gems array
                    final short[] specialGems = {70, 77, 84, 91, 98};
                    this.idGif = specialGems[Map.r.nextInt(specialGems.length)];
                    
                    // Special amounts based on money parameter
                    final byte[] specialAmounts = {1, 3, 4};
                    this.value = money >= 0 && money < specialAmounts.length ? specialAmounts[money] : specialAmounts[0];
                }
                break;
                
            case 3:
                // Fixed gem IDs
                final short[] specialIds = {112, 113};
                this.idGif = specialIds[Map.r.nextInt(specialIds.length)];
                this.value = 1;
                break;
        }
    }

    public void createGifGold(boolean spc, int money) {
        switch (this.id) {
            case 0:
                this.id = 4;
                int rd = Map.r.nextInt(100);
                if (rd < 90) {
                    byte[] soluong = new byte[]{30, 60, 90};
                    if (Map.r.nextInt(100) < 50) {
                        this.idGif = 93;
                    } else {
                        this.idGif = 95;
                    }

                    this.value = soluong[money];
                } else if (rd < 95) {
                    byte[] soluong = new byte[]{1, 2, 3};
                    this.idGif = 100;
                    this.value = soluong[money];
                } else {
                    byte[] soluong = new byte[]{2, 3, 4};
                    this.idGif = 84;
                    this.value = soluong[money];
                }
                break;
               
           case 1:
           if (TeamServer.LuckyExp) {
               byte[] x10 = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 4, 4, 4, 4, 4, 5, 5, 5, 5};
               this.value = x10[Map.r.nextInt(x10.length)] * 100000 + 100000;
            } else {
                this.value = 0; // hoặc bỏ qua, không tạo EXP
            }
               break;
               
            case 2:
                if (!spc) {
                    int ran = Map.r.nextInt(100);
                    if (ran < 10) {
                        short[] id = new short[]{63, 64, 65};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{1, 2, 3};
                        this.value = soluong[money];
                    } else if (ran < 35) {
                        short[] id = new short[]{105, 112, 119, 126, 133};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{2, 3, 5};
                        this.value = soluong[money];
                    } else if (ran < 90) {
                        if (Map.r.nextInt(100) <= 90) {
                            short[] id = idMaterial[3];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        } else {
                            short[] id = idMaterial[0];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        }

                        this.value = 1;
                    } else if (ran < 100) {
                        short[] id = new short[]{70, 77, 84, 91, 98};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        byte[] soluong = new byte[]{2, 3, 4};
                        this.value = soluong[money];
                    }
                } else {
                    int ran = Map.r.nextInt(100);
                    if (ran <= 25) {
                        byte[] id = new byte[]{66, 11};
                        this.idGif = id[Map.r.nextInt(id.length)];
                        this.value = 1;
                    } else if (ran <= 65) {
                        if (Map.r.nextInt(100) <= 50) {
                            short[] id = idMaterial[5];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        } else {
                            short[] id = idMaterial[2];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        }

                        this.value = 1;
                    } else if (ran < 100) {
                        if (Map.r.nextInt(100) <= 50) {
                            short[] id = idMaterial[4];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        } else {
                            short[] id = idMaterial[1];
                            this.idGif = id[Map.r.nextInt(id.length)];
                        }

                        this.value = 1;
                    }
                }
                break;
            case 3:
                this.idGif = 114;
                this.value = 1;
        }

    }

    public String removeGifLixi(Char p) {
        String info = "";
        switch (this.id) {
            case 0:
                p.addXu((long)this.value, "infogiftlucky 1");
                info = this.value + " xu";
                this.value = 0;
                break;
           case 1:
           if (TeamServer.LuckyExp) {
               Map var7 = p.map;
               Map.addXpCharEvent(p, (long)this.value, false, "InfoGifLucky removeGifLixi");
               info = this.value + " exp";
            } else {
                info = "Không có EXP";
            }
               this.value = 0;
               break;
            case 2:
                if (this.idGif > -1) {
                    if (p.listGemitem[this.idGif] == 0) {
                        GemItem gem = new GemItem(this.idGif);
                        gem.ownerId = p.charDBID;
                        gem.realId = p.getIDItem();
                        p.gemItem.add(gem);
                    }

                    int[] var5 = p.listGemitem;
                    int var9 = this.idGif;
                    var5[var9] += this.value;
                    var5 = p.allGemGet;
                    var9 = this.idGif;
                    var5[var9] += this.value;
                    info = this.value + " " + Map.gemTemplate[this.idGif].name;
                    this.value = 0;
                    this.idGif = -1;
                }
                break;
            case 3:
                if (this.idGif > -1) {
                    int[] var4 = p.potions;
                    int var8 = this.idGif;
                    var4[var8] += this.value;
                    info = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
                    this.idGif = -1;
                    this.value = 0;
                }
                break;
            case 4:
                if (this.idGif > -1) {
                    int[] var10000 = p.potions;
                    int var10001 = this.idGif;
                    var10000[var10001] += this.value;
                    info = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
                    this.value = 0;
                    this.idGif = -1;
                }
        }

        return info;
    }

    public String removeGif(Char p) {
        String info = "";
        switch (this.id) {
            case 0:
                p.addXu((long)this.value, "infogiftlucky 2");
                info = this.value + " xu";
                this.value = 0;
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                break;
           case 1:
            if (TeamServer.LuckyExp) {
               Map var7 = p.map;
               Map.addXpCharEvent(p, (long)this.value, false, "InfoGifLucky removeGif");
               info = this.value + " exp";
            } else {
                info = "Không có EXP";
            }
               this.value = 0;
               break;
            case 2:
                if (this.idGif > -1) {
                    if (p.listGemitem[this.idGif] == 0) {
                        GemItem gem = new GemItem(this.idGif);
                        gem.ownerId = p.charDBID;
                        gem.realId = p.getIDItem();
                        p.gemItem.add(gem);
                    }

                    int[] var5 = p.listGemitem;
                    int var9 = this.idGif;
                    var5[var9] += this.value;
                    var5 = p.allGemGet;
                    var9 = this.idGif;
                    var5[var9] += this.value;
                    info = this.value + " " + Map.gemTemplate[this.idGif].name;
                    this.value = 0;
                    this.idGif = -1;
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                }
                break;
            case 3:
                if (this.idGif > -1) {
                    int[] var4 = p.potions;
                    int var8 = this.idGif;
                    var4[var8] += this.value;
                    info = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
                    this.idGif = -1;
                    this.value = 0;
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                }
                break;
            case 4:
                if (this.idGif > -1) {
                    int[] var10000 = p.potions;
                    int var10001 = this.idGif;
                    var10000[var10001] += this.value;
                    info = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
                    this.value = 0;
                    this.idGif = -1;
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                }
        }

        Database.instance.saveOrtherLog("", p.getName(), info.trim().equals("") ? "Hộp không trúng thưởng" : info, "luck" + (this.typeBox == 0 ? "SILV" : "GOLD"));
        return info;
    }

    public String getInfoGif() {
        String st = "";
        switch (this.id) {
            case 0:
                st = this.value + " xu";
                break;
            case 1:
                if (TeamServer.LuckyExp) {
                    st = this.value + " exp";
                } else {
                    st = "Không có EXP";
                }
                break;
            case 2:
                st = this.value + " " + Map.gemTemplate[this.idGif].name;
                break;
            case 3:
                st = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
                break;
            case 4:
                st = this.value + " " + LoginHandler.PORTION_NAME[this.idGif];
        }

        return st;
    }
}
