package server.Panel;

import javax.swing.*;

public class GemComboItem {
    public String name;
    public int id;
    public ImageIcon icon;

    public GemComboItem(String name, int id, ImageIcon icon) {
        this.name = name;
        this.id = id;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}