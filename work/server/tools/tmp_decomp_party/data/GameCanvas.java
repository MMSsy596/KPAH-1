/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;

public class GameCanvas
extends Canvas {
    public static String test = "";
    public static int count = 0;

    public GameCanvas() {
    }

    public GameCanvas(GraphicsConfiguration config) {
        super(config);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (!test.equals("")) {
            g.setColor(Color.black);
            System.out.println("goi paint chu ra man hinh");
            g.drawString(test, 100, 100);
        }
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }
}

