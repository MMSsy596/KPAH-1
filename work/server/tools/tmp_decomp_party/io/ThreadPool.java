/*
 * Decompiled with CFR 0.152.
 */
package io;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static ThreadPool instant = new ThreadPool();
    public ExecutorService executor = Executors.newFixedThreadPool(350);
    public Vector<Runnable> workList = new Vector();
    public long time = System.currentTimeMillis();

    public ThreadPool() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                int count = 0;
                block6: while (true) {
                    try {
                        while (ThreadPool.this.workList.size() > 0) {
                            if (++count % 200 == 0) {
                                System.out.println("===>Thread Snap===Works Remain=" + ThreadPool.this.workList.size() + "===200 works in " + (System.currentTimeMillis() - ThreadPool.this.time) + " ms");
                                ThreadPool.this.time = System.currentTimeMillis();
                                if (count > 1000) {
                                    count = 0;
                                }
                                Thread.sleep(100L);
                            }
                            try {
                                ThreadPool.this.executor.execute(ThreadPool.this.workList.remove(0));
                                continue block6;
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        try {
                            Thread.sleep(100L);
                            continue;
                        }
                        catch (Exception exception) {
                        }
                    }
                    catch (Exception exception) {
                        continue;
                    }
                    break;
                }
            }
        }).start();
    }
}

