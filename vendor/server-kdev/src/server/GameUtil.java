package server;

public class GameUtil {

    public static void sleep(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {

        }
    }
}
