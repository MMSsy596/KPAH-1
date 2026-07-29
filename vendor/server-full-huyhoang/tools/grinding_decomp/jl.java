/*
 * Decompiled with CFR 0.152.
 */
import game.GameMidlet;
import java.io.IOException;

public final class jl
implements Runnable {
    private int g = -1;
    private int h = -1;
    int a = -1;
    int b = -1;
    int c = -1;
    int d = -1;
    private int i = -1;
    int e = -1;
    private gj j;
    public boolean f = false;
    private byte[] k = null;

    public final void a(int n2, gj gj2, byte[] byArray) {
        this.i = n2;
        this.j = gj2;
        this.k = byArray;
        ls.a();
    }

    public final void run() {
        while (true) {
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException interruptedException) {
                InterruptedException interruptedException2 = interruptedException;
                interruptedException.printStackTrace();
            }
            if (this.g != -1 && this.h != -1) {
                yi.b(this.g, this.h);
                this.g = -1;
                this.h = -1;
            }
            if (this.a != -1 && this.b != -1) {
                yi.c(this.a, this.b, this.c);
                this.a = -1;
                this.b = -1;
            }
            if (this.d != -1) {
                try {
                    yi.g(this.d);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                this.d = -1;
            }
            if (this.i != -1) {
                int n2 = this.i;
                this.i = -1;
                ls.a(n2, this.k);
                this.j.a();
                this.k = null;
            }
            if (this.e != -1) {
                yi.e(this.e);
                this.e = -1;
            }
            if (!this.f) continue;
            GameMidlet.a.a();
            this.f = false;
        }
    }
}

