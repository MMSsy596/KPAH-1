/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.ItemLuckyDraw
 *  real.Char
 *  real.Item
 *  real.Map
 */
package real;

import data.ItemLuckyDraw;
import java.util.Vector;
import real.Char;
import real.InfoItemAttribute;
import real.Item;
import real.ItemTemplates;
import real.Map;
import real.UtilKPAH;

public class ItemShop
extends Item {
    public int luong = 0;
    public int xu = 0;
    public int soluong = 1;
    public int timeExpire = 0;

    public ItemShop() {
    }

    public ItemShop(ItemTemplates template, boolean isAddRandomValue, int clazz, int classTemplate, int idtemplate) {
        super(template, isAddRandomValue, clazz, classTemplate, idtemplate);
    }

    public String getName() {
        return super.getName();
    }

    public String getCharSeal() {
        if (this.minuteExist > 0 && this.timeLoan > 0L) {
            long time = this.minuteExist;
            String[] info = Char.split((String)Char.getDayTimeByTime((long)(this.timeLoan + time * 60000L)), (String)" ");
            if (!this.charSeal.equals("")) {
                return String.valueOf(this.charSeal) + "\nhh: " + info[0] + "\n-" + info[1];
            }
            return "hh: " + info[0] + "\n-" + info[1];
        }
        String info = this.charSeal;
        if (this.isRuongNguyenLieu()) {
            info = String.valueOf(info) + "- M\u1edf ra ng\u1eabu nhi\u00ean nguy\u00ean li\u1ec7u c\u1ea5p 4,5.";
        }
        if (this.xu > 0) {
            info = String.valueOf(info) + "\n2- Gi\u00e1 xu: " + UtilKPAH.getDotNumber(this.xu);
        }
        return info;
    }

    public void setTimeExpire(int timeExpire) {
        this.timeExpire = timeExpire;
    }

    public int getTimeExpire() {
        return this.timeExpire;
    }

    public Vector<InfoItemAttribute> getInfoAtbItem() {
        Vector<InfoItemAttribute> allAtb = new Vector<InfoItemAttribute>();
        if (Map.isModelClothes((int)this.getTemplate().atb[9])) {
            if (this.atb[0] > 0) {
                allAtb.add(new InfoItemAttribute(58, this.atb[0]));
            }
            if (this.atb[1] > 0) {
                allAtb.add(new InfoItemAttribute(59, this.atb[1]));
                allAtb.add(new InfoItemAttribute(60, this.atb[1]));
            }
            if (this.atb[2] > 0) {
                allAtb.add(new InfoItemAttribute(33, this.atb[2] / 1000));
            }
            if (this.atb[3] > 0) {
                allAtb.add(new InfoItemAttribute(34, this.atb[3] / 1000));
            }
            if (this.tempateID == 577 || this.tempateID == 578) {
                allAtb.add(new InfoItemAttribute(69, 500));
                allAtb.add(new InfoItemAttribute(70, 500));
                allAtb.add(new InfoItemAttribute(71, 500));
                allAtb.add(new InfoItemAttribute(90, 500));
            }
            if (this.tempateID == 725 || this.tempateID == 726) {
                allAtb.add(new InfoItemAttribute(118, 500));
            }
            if (this.tempateID == 743 || this.tempateID == 744) {
                allAtb.add(new InfoItemAttribute(119, 100));
            }
            if (ItemLuckyDraw.isChoi((int)this.tempateID) || this.isMatna()) {
                short i = 0;
                while (i < this.atb.length) {
                    if (i < 10) {
                        if (attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                            allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                            this.allAtb.put(i, new InfoItemAttribute(i, this.atb[i]));
                        }
                    } else if (this.atb[i] > 0) {
                        allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                    }
                    i = (short)(i + 1);
                }
                int count = this.atb.length;
                int i2 = 0;
                while (i2 < this.newAtb.length) {
                    if (this.newAtb[i2] > 0) {
                        allAtb.add(new InfoItemAttribute((short)(i2 + count), this.newAtb[i2]));
                    }
                    i2 = (short)(i2 + 1);
                }
                count += this.newAtb.length;
                i2 = 0;
                while (i2 < this.addMoreLevelSkill.length) {
                    if (this.addMoreLevelSkill[i2] > 0) {
                        allAtb.add(new InfoItemAttribute((short)(i2 + count), this.addMoreLevelSkill[i2]));
                    }
                    i2 = (short)(i2 + 1);
                }
                count += this.addMoreLevelSkill.length;
                i2 = 0;
                while (i2 < this.lockAtb.length) {
                    if (this.lockAtb[i2] > 0) {
                        allAtb.add(new InfoItemAttribute((short)(i2 + count), this.lockAtb[i2]));
                    }
                    i2 = (short)(i2 + 1);
                }
                count += this.lockAtb.length;
                i2 = 0;
                while (i2 < this.otherAtt.length) {
                    if (this.otherAtt[i2] > 0) {
                        allAtb.add(new InfoItemAttribute((short)(i2 + count), this.otherAtt[i2]));
                    }
                    i2 = (short)(i2 + 1);
                }
            }
        } else {
            short i = 0;
            while (i < this.atb.length) {
                if (i < 10) {
                    if (attribShowedForType[this.getType()][i] && this.atb[i] > 0) {
                        allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                        this.allAtb.put(i, new InfoItemAttribute(i, this.atb[i]));
                    }
                } else if (this.atb[i] > 0) {
                    allAtb.add(new InfoItemAttribute(i, this.atb[i]));
                }
                i = (short)(i + 1);
            }
            int count = this.atb.length;
            int i3 = 0;
            while (i3 < this.newAtb.length) {
                if (this.newAtb[i3] > 0) {
                    allAtb.add(new InfoItemAttribute((short)(i3 + count), this.newAtb[i3]));
                }
                i3 = (short)(i3 + 1);
            }
            count += this.newAtb.length;
            i3 = 0;
            while (i3 < this.addMoreLevelSkill.length) {
                if (this.addMoreLevelSkill[i3] > 0) {
                    allAtb.add(new InfoItemAttribute((short)(i3 + count), this.addMoreLevelSkill[i3]));
                }
                i3 = (short)(i3 + 1);
            }
            count += this.addMoreLevelSkill.length;
            i3 = 0;
            while (i3 < this.lockAtb.length) {
                if (this.lockAtb[i3] > 0) {
                    allAtb.add(new InfoItemAttribute((short)(i3 + count), this.lockAtb[i3]));
                }
                i3 = (short)(i3 + 1);
            }
            count += this.lockAtb.length;
            i3 = 0;
            while (i3 < this.otherAtt.length) {
                if (this.otherAtt[i3] > 0) {
                    allAtb.add(new InfoItemAttribute((short)(i3 + count), this.otherAtt[i3]));
                }
                i3 = (short)(i3 + 1);
            }
        }
        allAtb.add(new InfoItemAttribute(126, this.soluong));
        if (this.luong > 0) {
            allAtb.add(new InfoItemAttribute(125, this.luong));
        }
        return allAtb;
    }

    public void setSoluong(int sl) {
        this.soluong = sl;
    }

    public void setXuSell(int xu) {
        this.xu = xu;
    }

    public void setLuongSell(int l) {
        this.luong = l;
    }

    public int getSoluong() {
        return this.soluong;
    }

    public int getXuSell() {
        return this.xu;
    }

    public int getLuongSell() {
        return this.luong;
    }
}

