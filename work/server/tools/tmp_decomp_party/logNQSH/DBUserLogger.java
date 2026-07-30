/*
 * Decompiled with CFR 0.152.
 */
package logNQSH;

import java.util.concurrent.LinkedBlockingQueue;
import logNQSH.Net;

public class DBUserLogger
implements Runnable {
    public static DBUserLogger instance = new DBUserLogger();
    public LinkedBlockingQueue<String> userLogQueue = new LinkedBlockingQueue();

    @Override
    public void run() {
        Thread.currentThread().setName("DBUserLogger");
        while (true) {
            try {
                while (true) {
                    if (this.userLogQueue.size() > 100) {
                        this.userLogQueue.clear();
                    }
                    String url = this.userLogQueue.take();
                    Net.getHttpAsync(url);
                }
            }
            catch (Exception e) {
                this.userLogQueue = new LinkedBlockingQueue();
                continue;
            }
            break;
        }
    }
}

