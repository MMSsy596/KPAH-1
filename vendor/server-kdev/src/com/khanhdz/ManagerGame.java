/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz;

import data.Database;
import java.util.Hashtable;
import real.Char;
import real.Item;
import real.ItemTemplates;
import static real.Map.itemTemplates;
import real.MessageCreator;

/**
 *
 * @author TOM
 */
public class ManagerGame {

    public static final ManagerGame Instance = new ManagerGame();

    public static Item doBuyModelClothe(Char player, int idItemtemplate, int nday, int lock) {
        ItemTemplates template = null;
        Item newItem = null;
        switch (idItemtemplate) {
            case 723:
                template = (itemTemplates.get(5)).get((723));
                newItem = new Item(template, false, -1, 0, 723);
                break;
            case 724:
                template = (itemTemplates.get(5)).get((724));
                newItem = new Item(template, false, -1, 0, 724);
                break;
            case 725:
                template = (itemTemplates.get(5)).get((725));
                newItem = new Item(template, false, -1, 0, 725);
                break;
            case 726:
                template = (itemTemplates.get(5)).get((726));
                newItem = new Item(template, false, -1, 0, 726);
                break;
            case 736:
                template = (itemTemplates.get(5)).get((736));
                newItem = new Item(template, false, -1, 0, 736);
                break;
            case 737:
                template = (itemTemplates.get(5)).get((737));
                newItem = new Item(template, false, -1, 0, 737);
                break;
            case 740:
                template = (itemTemplates.get(5)).get((740));
                newItem = new Item(template, false, -1, 0, 740);
                break;
            case 741:
                template = (itemTemplates.get(5)).get((741));
                newItem = new Item(template, false, -1, 0, 741);
                break;
            case 743:
                template = (itemTemplates.get(5)).get((743));
                newItem = new Item(template, false, -1, 0, 743);
                break;
            case 744:
                template = (itemTemplates.get(5)).get((744));
                newItem = new Item(template, false, -1, 0, 744);
                break;
            case 754:
                template = (itemTemplates.get(5)).get((754));
                newItem = new Item(template, false, -1, 0, 754);
                break;
            case 755:
                template = (itemTemplates.get(5)).get((755));
                newItem = new Item(template, false, -1, 0, 755);
                
                
            // TODO THÊM ÁO THỜI TRANG (1)    
            case 797:
                template = (itemTemplates.get(5)).get((797));
                newItem = new Item(template, false, -1, 0, 797);
            case 798:
                template = (itemTemplates.get(5)).get((798));
                newItem = new Item(template, false, -1, 0, 798);                
            case 799:
                template = (itemTemplates.get(5)).get((799));
                newItem = new Item(template, false, -1, 0, 799);                
            case 800:
                template = (itemTemplates.get(5)).get((800));
                newItem = new Item(template, false, -1, 0, 800);                
            case 801:
                template = (itemTemplates.get(5)).get((801));
                newItem = new Item(template, false, -1, 0, 801);                
                
         
                
                
                
                
            case 834:
                template = (itemTemplates.get(5)).get((834));
                newItem = new Item(template, false, -1, 0, 834);       
            case 839:
                template = (itemTemplates.get(5)).get((839));
                newItem = new Item(template, false, -1, 0, 839);
                break;
            // noel nam 2025
       
          

            default:
                break;
        }

        if (newItem != null) {
            newItem.id = player.getIDItem();
            newItem.owner = player.charDBID;
            newItem.level = newItem.getTemplate().level;
            player.iItems.add(newItem);
            newItem.clazz = 0;
            newItem.createAttributeItemModel(false);
            newItem.identify = player.charDBID;
            if (nday > 0) {
                newItem.minuteExist = nday * 24 * 60;
                newItem.timeLoan = System.currentTimeMillis();
            }

            newItem.lock = (byte) lock;
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            Database.instance.saveOrtherLog("", player.charname, "mua  " + newItem.getName() + "|" + newItem.getInfoSave(), "dothoitrang");
            Database.instance.saveItem(newItem);
        }

        return newItem;
    }

}
