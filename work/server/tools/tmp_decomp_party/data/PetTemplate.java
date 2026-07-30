/*
 * Decompiled with CFR 0.152.
 */
package data;

public class PetTemplate {
    public String name = "";
    public int timeExpire;
    public byte idImg = 0;
    public byte nFrame;
    public byte wFrame;
    public byte hFrame;
    public byte idImgPaint;
    public byte idTemplate = 0;
    public byte typeMoney = 0;
    public byte type_move = 0;
    public int value = 0;

    public PetTemplate(byte idTemplate, String name, int timeExpire, byte idImg, byte nFrame, byte wFrame, byte hFrame, int value, byte typeMoney, byte idImgPaint, int typemove) {
        this.idTemplate = idTemplate;
        this.name = name;
        this.timeExpire = timeExpire;
        this.idImg = idImg;
        this.value = value;
        this.typeMoney = typeMoney;
        this.nFrame = nFrame;
        this.wFrame = wFrame;
        this.hFrame = hFrame;
        this.idImgPaint = idImgPaint;
        this.type_move = (byte)typemove;
    }
}

