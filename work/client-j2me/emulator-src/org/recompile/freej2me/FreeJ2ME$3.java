package org.recompile.freej2me;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keeps directional taps pressed long enough for polling-based MIDlets to see
 * them. Real keyboard holds still repeat normally through FreeJ2ME.pressKey.
 */
class FreeJ2ME$3 implements KeyListener {
    final FreeJ2ME this$0;

    FreeJ2ME$3(FreeJ2ME owner) {
        this.this$0 = owner;
    }

    public void keyPressed(KeyEvent event) {
        this.this$0.pressKey(event, false);
        int keyCode = event.getKeyCode();
        if ((keyCode >= KeyEvent.VK_LEFT && keyCode <= KeyEvent.VK_DOWN)
                || (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9)) {
            try {
                Thread.sleep(160L);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        this.this$0.releaseKey(event);
    }

    public void keyTyped(KeyEvent event) {
    }
}
