package server.Panel;

import javax.swing.*;

public class PotionComboItem {
    public String name;
    public int index;
    public ImageIcon icon;

    public PotionComboItem(String name, int index, ImageIcon icon) {
        this.name = name;
        this.index = index;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name;
    }
}