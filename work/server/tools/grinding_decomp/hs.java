/*
 * Decompiled with CFR 0.152.
 */
final class hs
implements Runnable {
    private aco a;

    hs(aco aco2) {
        this.a = aco2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void run() {
        while (true) {
            block22: {
                try lbl-1000:
                // 3 sources

                {
                    while (true) {
                        if (!this.a.b()) break;
                        var1_1 = this;
                        ++aco.k;
                        var2_2 = var1_1.a.a.readByte();
                        if (var1_1.a.g) {
                            var2_2 = aco.a(var1_1.a, var2_2);
                        }
                        if (var1_1.a.g) {
                            if (var2_2 == -128) {
                                var2_2 = var1_1.a.a.readByte();
                                var2_2 = aco.a(var1_1.a, var2_2);
                                var4_7 = new byte[]{var1_1.a.a.readByte(), var1_1.a.a.readByte(), var1_1.a.a.readByte(), var1_1.a.a.readByte()};
                                var3_4 = aco.a(var1_1.a, var4_7[3]) & 255 | (aco.a(var1_1.a, var4_7[2]) & 255) << 8 | (aco.a(var1_1.a, var4_7[1]) & 255) << 16 | (aco.a(var1_1.a, var4_7[0]) & 255) << 24;
                            } else {
                                var4_8 = var1_1.a.a.readByte();
                                var5_10 = var1_1.a.a.readByte();
                                var3_4 = (aco.a(var1_1.a, var4_8) & 255) << 8 | aco.a(var1_1.a, (byte)var5_10) & 255;
                            }
                        } else {
                            var3_4 = var1_1.a.a.readUnsignedShort();
                        }
                        var4_7 = new byte[var3_4];
                        var5_10 = 0;
                        var6_11 = 0;
                        while (true) {
                            block23: {
                                if (var5_10 != -1 && var6_11 < var3_4) break block23;
                                if (!var1_1.a.g) break block22;
                                var3_4 = 0;
                                if (true) ** GOTO lbl52
                            }
                            var5_10 = var1_1.a.a.read(var4_7, var6_11, var3_4 - var6_11);
                            if (var5_10 <= 0) continue;
                            var1_1.a.f += (var6_11 += var5_10) + 5;
                        }
                        break;
                    }
                }
                catch (Exception v0) {}
                if (this.a.c) {
                    if (this.a.b != null) {
                        if (System.currentTimeMillis() - this.a.i > 500L) {
                            var1_1 = this.a.b;
                            acv.s.n();
                        } else {
                            var1_1 = this.a.b;
                            acv.s.m();
                        }
                    }
                    if (aco.a(this.a) != null) {
                        aco.c(this.a);
                    }
                }
                return;
                do {
                    var4_7[var3_4] = aco.a(var1_1.a, var4_7[var3_4]);
                    ++var3_4;
lbl52:
                    // 2 sources

                } while (var3_4 < var4_7.length);
            }
            var3_5 = new abs(var2_2, var4_7);
            var1_1 = var3_5;
            try {
                block24: {
                    if (var1_1.a != -40) break block24;
                    var2_3 = var1_1;
                    var1_1 = this;
                    var3_6 = var2_3.b().readByte();
                    var1_1.a.h = new byte[var3_6];
                    var4_9 = 0;
                    if (true) ** GOTO lbl76
                }
                this.a.b.a((abs)var1_1);
            }
            catch (Exception v1) {
                var1_1 = v1;
                v1.printStackTrace();
                break;
            }
        }
        ** GOTO lbl-1000
        do {
            var1_1.a.h[var4_9] = var2_3.b().readByte();
            ++var4_9;
lbl76:
            // 2 sources

        } while (var4_9 < var3_6);
        var4_9 = 0;
        while (var4_9 < var1_1.a.h.length - 1) {
            v2 = var4_9 + 1;
            var1_1.a.h[v2] = (byte)(var1_1.a.h[v2] ^ var1_1.a.h[var4_9]);
            ++var4_9;
        }
        var1_1.a.g = true;
        ** while (true)
    }
}

