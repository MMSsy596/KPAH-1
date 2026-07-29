package data;

import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.Item;
import real.ItemTemplates;
import real.Map;
import real.MessageCreator;

public class ItemLuckyDraw {
    public byte cat;
    public byte idIconPet;
    public byte isSelect = 1;
    public int id;
    public int value = 1;
    public String name;
    static short[] PC_UP_ATK_CHAR_CHOI = new short[]{10, 10, 10};
    static short[] PC_UP_ATK_EFF_CHOI = new short[]{10, 10, 10};
    public static String[] nameAnimal = new String[]{"Ngựa", "Hổ", "Trâu", "Sói", "Hạc", "Phượng hoàng", "Hắc thố", "Tuần lộc"};

    public ItemLuckyDraw() {
    }

    public boolean isPhuongHoang() {
        return this.cat == 12 && (this.id == 16 || this.id == 17);
    }

    public boolean isPPWorldCup() {
        return this.cat == 3 && (this.id == 616 || this.id == 608 || this.id >= 613 && this.id <= 615 || this.id >= 617 && this.id <= 619 || this.id >= 898 && this.id <= 902);
    }

    public void addGif(Char p, int time) {
        String info = "";
        if (this.cat == 12) {
            if (this.id != 16 && this.id != 17) {
                Pet pet = new Pet(Map.petTemplate[this.id].idTemplate, Map.petTemplate[this.id].idImg, p.getIDItem(), 0, p.charDBID, Map.petTemplate[this.id].name);
                if (time == 0) {
                    time = 20160;
                }

                pet.totalMinute = time;
                pet.type = 2;
                pet.timeBuy = System.currentTimeMillis();
                p.pet.add(pet);
                Database.instance.savePet(pet);
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 2));
                info = "trúng " + pet.getName() + " thoi han " + time;
            } else {
                info = "trúng phượng hoàng";
                Database.instance.saveOrtherLog("", p.getName(), "Trung phuong hoang " + this.id, "phuonghoang");
                p.createTrungPhuongHoang(30L, this.id);

                try {
                    Map.sendAllCharServer(17, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.getName() + " cấp độ " + p.lvDetail.lv + " đã trúng được Linh Thú Phượng Hoàng."));
                } catch (Exception var8) {
                }
            }
        } else if (this.cat == 6) {
            p.addLockGem(this.id, 1);
            p.sendMessage(MessageCreator.createCharGemItem(p));
            info = "trúng lkd 30";
        } else if (this.cat == 3) {
            int idTemplate = this.id;
            Item newItem = null;
            if (idTemplate >= 898 && idTemplate <= 902) {
                newItem = new Item((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate), false, 0, 0, idTemplate);
                newItem.id = p.getIDItem();
                newItem.owner = p.charDBID;
                newItem.level = newItem.getTemplate().level;
                newItem.clazz = p.charClass;
  
                if (idTemplate == 898) {
                    // Range 1-10
                    newItem.createOptionCanh(0, Map.r.nextInt(10) + 1);
                } else if (idTemplate == 899) {
                    // Range 1-10
                    newItem.createOptionCanh(1, Map.r.nextInt(10) + 1);
                } else if (idTemplate == 900) {
                    // Range 1-20
                    newItem.createOptionCanh(2, Map.r.nextInt(20) + 1);
                } else if (idTemplate == 901) {
                    // Range 1-50
                    newItem.createOptionCanh(3, Map.r.nextInt(50) + 1);
                } else if (idTemplate == 902) {
                    // Range 10-100
                    newItem.createOptionCanh(4, Map.r.nextInt(91) + 10);
                }

                int pc1 = Map.r.nextInt(100);
                if (pc1 <= 1) {
                    time = 43200;
                } else if (pc1 <= 4) {
                    time = 7200;
                } else if (pc1 <= 7) {
                    time = 4320;
                } else {
                    time = 1440;
                }
                
            }
            else if (isChoi(idTemplate)) {
                newItem = new Item((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate), false, 0, 0, idTemplate);
                newItem.id = p.getIDItem();
                newItem.owner = p.charDBID;
                newItem.level = newItem.getTemplate().level;
                newItem.identify = p.charDBID;
                newItem.clazz = p.charClass;
                newItem.createAttChoi();
                int pc1 = Map.r.nextInt(100);
                if (pc1 <= 1) {
                    time = 43200;
                } else if (pc1 <= 4) {
                    time = 7200;
                } else if (pc1 <= 7) {
                    time = 4320;
                } else {
                    time = 1440;
                }
            } else if (idTemplate != 608 && idTemplate != 616) {
                newItem = new Item((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate), false, 0, 0, idTemplate);
                newItem.id = p.getIDItem();
                newItem.owner = p.charDBID;
                newItem.level = newItem.getTemplate().level;
                newItem.identify = p.charDBID;
                newItem.clazz = p.charClass;
                newItem.createAttributeItemCoat(false, (byte)-1, -1);
                
            } else {
                if (time == 0) {
                    time = 43200;
                }

                if (idTemplate == 616) {
                    int pc1 = Map.r.nextInt(100);
                    if (pc1 <= 1) {
                        time = 43200;
                    } else if (pc1 <= 4) {
                        time = 7200;
                    } else if (pc1 <= 7) {
                        time = 4320;
                    } else {
                        time = 1440;
                    }
                }

                newItem = createMaxItemCoatLucky(idTemplate, p, -1);
            }

            newItem.dateCreate = Char.getDayTime(0L);
            if (time > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = time;
            }

            p.iItems.add(newItem);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
            info = newItem.getDBInfo() + "|" + newItem.getAttribute() + "_" + newItem.level + " thoi han " + time;
            if (time == 0) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.getName() + " đã nhận được " + newItem.getTemplate().name + " " + newItem.level + " vĩnh viễn"));
                    Database.instance.saveOrtherLog("", p.getName(), newItem.getTemplate().name + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute() + " " + newItem.level, "vv");
                } catch (Exception var7) {
                }
            }
        }

        Database.instance.saveOrtherLog("", p.getName(), info, "quayso");
    }

    public static Item createMaxItemCoatLucky(int idTemplate, Char p, int ma_vat) {
        int[] id = new int[]{584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = p.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }

        int idtemp = id[(lv - 35) / 5];
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idtemp);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = item.level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.dateCreate = Char.getDayTime(0L);
        newItem.createAttributeItemCoat(true, (byte)ma_vat, -1);
        return newItem;
    }

    public static Item createItemCoat(Char p, int mavat, boolean ismax, int time) {
        int[] id = new int[]{584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = p.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }

        int idtemp = id[(lv - 35) / 5];
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idtemp);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idtemp, p.charClass, p.charClass, idtemp);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = item.level;
        newItem.identify = p.charDBID;
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = time;
        }

        newItem.lock = 1;
        newItem.clazz = p.charClass;
        newItem.dateCreate = Char.getDayTime(0L);
        newItem.createAttributeItemCoat(ismax, (byte)mavat, -1);
        return newItem;
    }

    public static Item createMaxItemCoatLucky(int idTemplate, Char p, int mavat, boolean ismax) {
        int[] id = new int[]{584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = p.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }

        int var10000 = id[(lv - 35) / 5];
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = item.level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.createAttributeItemCoat(ismax, (byte)mavat, -1);
        return newItem;
    }

    public static Item createtemCoat(int idTemplate, Char p, int mavat, boolean ismax, int time) {
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.minuteExist = time;
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
        }

        newItem.createAttributeItemCoat(ismax, (byte)mavat, -1);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        System.out.println(idTemplate + "_" + (mavat == 0 ? "maphap" : "vat ly ") + newItem.getInfoSave());
        return newItem;
    }

    public static Item createtemCoatHaveKhang(int idTemplate, Char p, int mavat, boolean ismax, int time, int idKhang) {
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.minuteExist = time;
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
        }

        newItem.createAttributeItemCoat(ismax, (byte)mavat, idKhang);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        System.out.println(idTemplate + "_" + (mavat == 0 ? "maphap" : "vat ly ") + newItem.getInfoSave());
        return newItem;
    }

    public static void createMaxItemCoat(int idTemplate, Char p) {
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(idTemplate);

        for(int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short)(item.atb[i] + item.atb[i] / 4);
            }
        }

        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.timeLoan = System.currentTimeMillis();
        newItem.minuteExist = 1440;
        newItem.lock = 1;
        newItem.createAttributeItemCoat(true, (byte)(p.isMagic() ? 0 : 1), -1);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
    }

    public static boolean isChoi(int id) {
        return id >= 613 && id <= 615 || id >= 617 && id <= 619;
    }

    public Vector<String> getInfo(Char p) {
        int[] idPP = new int[]{584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = p.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }

        int idtemp = idPP[(lv - 35) / 5];
        Vector<String> result = new Vector();
        int tempid = this.id;
        switch (this.cat) {
            case 3:
                if (isChoi(tempid)) {
                    String[] nameatt = new String[]{"băng lan", "sét lan", "độc lan"};
                    ItemTemplates it = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(tempid);
                    String info = "Tăng 10% tấn công";
                    String info1 = "cơ bản của nhân vật";
                    String info2 = "Tăng 10% st " + nameatt[tempid - 617];
                    String info3 = "Tăng thêm 1-4% tỷ lệ";
                    if (tempid == 613) {
                        info3 = "Tăng thêm 2-6% tỷ lệ";
                    }

                    String info4 = "xuất hiện " + nameatt[tempid - 617];
                    String info5 = "5% biến đối phương ";
                    String info6 = "thành người tuyết mũ xanh";
                    String info7 = " trong 8s";
                    result.add("Bạn có thể trúng");
                    result.add(it.name);
                    if (tempid != 617) {
                        result.add(info);
                        result.add(info1);
                    }

                    result.add(info2);
                    result.add(info3);
                    result.add(info4);
                    result.add(info5);
                    result.add(info6);
                    result.add(info7);
                } else if (tempid == 898 || tempid == 899 || tempid == 900 || tempid == 901 || tempid == 902) {
                    ItemTemplates it = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(tempid);
                    result.add("Bạn có thể trúng");
                    result.add(it.name);
                   if(tempid == 898) {
                    result.add("Tăng 1-10% tấn công");
                   } else if (tempid == 899) {
                    result.add("Tăng 1-10% thủ vật");
                    result.add("Tăng 1-10% thủ ma");
                   } else if (tempid == 900) {
                    result.add("Tăng 1-20% exp");
                   } else if (tempid == 901) {
                    result.add("Tăng 1.0% -3.0% bạo kích");
                   } else if (tempid == 902) {
                    result.add("Tăng 1.0-10.0% độc lan");
                    result.add("Tăng 1.0-10.0% sét lan");
                    result.add("Tăng 1.0-10.0% băng lan");
                    result.add("Tăng 1.0-10.0% lửa lan");
                   }
                    
                } else {
                    if (tempid == 616) {
                        tempid = idtemp;
                    }

                    ItemTemplates it = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(tempid);
                    int pt = it.atb[1] + it.atb[1] / 4;
                    String info = "Thủ :" + pt;
                    String info1 = "Thủ trang bị tăng: " + (Item.PC_DEF_COAT[(it.level - 35) / 5] + 5) + "%";
                    String info2 = "Bạo kích: 3%";
                    result.add("Bạn có thể trúng");
                    result.add((this.id == 616 ? ((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(616)).name : it.name) + " " + it.level);
                    result.add(info);
                    result.add(info1);
                    result.add(info2);
                }
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            default:
                break;
            case 6:
                result.add(Map.gemTemplate[this.id].name);
                result.add(Map.gemTemplate[this.id].decript);
                break;
            case 8:
                result.add("Linh Thú " + nameAnimal[this.id]);
                break;
            case 12:
                if (this.id != 9) {
                    result.add(Map.petTemplate[this.id].name);
                    result.add(Pet.decript[Map.petTemplate[this.id].idImg]);
                } else {
                    result.add(Map.petTemplate[this.id].name);
                }
        }

        return result;
    }
}
