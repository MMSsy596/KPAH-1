/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Pet
 */
package data;

import data.Pet;

public class SkillPet {
    public String name = "";
    public String decript = "";
    public byte id;
    public int value = 0;
    public int time = 0;
    public int time2 = 0;
    public int value2 = 0;

    public SkillPet(String name, String decript, int id, int value1, int value2) {
        this.name = name;
        this.id = (byte)id;
        this.decript = decript;
        this.value = value1;
        this.value2 = value2;
    }

    public SkillPet(String name, String decript, int id, int value1, int value2, int time1, int time2) {
        this.name = name;
        this.id = (byte)id;
        this.decript = decript;
        this.value = value1;
        this.value2 = value2;
        this.time = time1;
        this.time2 = time2;
    }

    public String getDecript(int level) {
        if (level == 1) {
            if (this.id == Pet.ID_SKILL_TIEN_KHI) {
                return "\n- " + this.decript.replace("#", String.valueOf(this.value)).replace("@", String.valueOf(this.time));
            }
            return "\n- " + this.decript.replace("#", String.valueOf(this.value));
        }
        if (level == 2) {
            if (this.id == Pet.ID_SKILL_TIEN_KHI) {
                return "\n- " + this.decript.replace("#", String.valueOf(this.value2)).replace("@", String.valueOf(this.time2));
            }
            return "\n- " + this.decript.replace("#", String.valueOf(this.value2));
        }
        return null;
    }
}

