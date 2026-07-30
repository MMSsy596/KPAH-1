/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 */
package data;

import data.Database;
import io.Message;
import java.util.Vector;
import real.Char;
import real.Map;
import real.MessageCreator;

public class Xoso {
    public static int max = 0;
    public static int min = 9;
    public static byte[] numberBuy = new byte[100];

    public static void reset() {
        numberBuy = new byte[100];
    }

    public static String getTotalNumber() {
        String info = String.valueOf(numberBuy[0]);
        int i = 1;
        while (i < 100) {
            info = String.valueOf(info) + "," + numberBuy[i];
            i = (byte)(i + 1);
        }
        info = String.valueOf(info) + "," + Map.winNumber;
        return info;
    }

    public static boolean canBuy(byte type, byte number) {
        return numberBuy[max] != numberBuy[number] || numberBuy[max] == numberBuy[min] || numberBuy[max] - numberBuy[min] < 10;
    }

    public static synchronized String buyNumber(Char p, byte number, byte type) {
        if (numberBuy[max] == numberBuy[number] && numberBuy[max] != numberBuy[min] && numberBuy[max] - numberBuy[min] >= 10) {
            return "S\u1ed1 n\u00e0y t\u1ea1m th\u1eddi \u0111\u00e3 h\u1ebft. Xin ch\u1ecdn s\u1ed1 kh\u00e1c.";
        }
        byte by = number;
        numberBuy[by] = (byte)(numberBuy[by] + 1);
        if (numberBuy[number] > numberBuy[max]) {
            max = number;
        }
        Xoso.getMin();
        p.doBuyNumber(type, number);
        Database.instance.saveEvent(Map.event.getInfo());
        return "Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 mua \u0111\u01b0\u1ee3c v\u00e9 " + number;
    }

    public static void getMin() {
        int i = 0;
        while (i < numberBuy.length) {
            if (numberBuy[min] > numberBuy[i]) {
                min = i;
            }
            i = (byte)(i + 1);
        }
    }

    public static synchronized void doRequestBuyNumber(Char p, byte idBuy) {
        try {
            Message m = new Message(-63);
            Vector<Byte> number = Xoso.getAvaliableumber((byte)0);
            if (number.size() == 0) {
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"H\u1ebft v\u00e9.", (String)""));
                return;
            }
            m.dos.writeByte(number.size());
            int i = 0;
            while (i < number.size()) {
                m.dos.writeByte(number.get(i).byteValue());
                i = (byte)(i + 1);
            }
            p.sendMessage(m);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static Vector<Byte> getAvaliableumber(byte type) {
        Vector<Byte> number = new Vector<Byte>();
        byte i = 0;
        while (i < 100) {
            if (Xoso.canBuy(type, i)) {
                number.add(i);
            }
            i = (byte)(i + 1);
        }
        return number;
    }

    public static void selectWinNumber() {
        int count = 0;
        Vector<Byte> numberWin = new Vector<Byte>();
        byte i = 0;
        while (i < numberBuy.length) {
            count += numberBuy[i];
            i = (byte)(i + 1);
        }
        if (count > 700) {
            Map.winNumber = (byte)Map.r.nextInt(100);
        } else {
            if (count > 80 && count <= 100) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] == 0) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 100 && count <= 140) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 1) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 140 && count <= 210) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 2) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 210 && count <= 280) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 3) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 280 && count <= 350) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 4) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 350 && count <= 420) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 5) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 420 && count <= 490) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 6) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 490 && count <= 560) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 7) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 560 && count <= 630) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 8) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            } else if (count > 630 && count <= 700) {
                i = 0;
                while (i < numberBuy.length) {
                    if (numberBuy[i] <= 9) {
                        numberWin.add(i);
                    }
                    i = (byte)(i + 1);
                }
            }
            Map.winNumber = numberWin.size() > 0 ? (Byte)numberWin.get(Map.r.nextInt(numberWin.size())) : (byte)Map.r.nextInt(100);
        }
        Database.instance.saveEvent(Map.event.getInfo());
        Database.instance.saveLogXoso();
    }
}

