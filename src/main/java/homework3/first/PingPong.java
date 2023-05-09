package homework3.first;

public class PingPong {
//1. Реализовать программу, в которой два потока поочередно пишут ping и pong.
    public static void main(String[] args) {
        Object synchronize = new Object();
    new Thread(() -> {
        while (true){
            synchronized (synchronize) {
                try {
                    System.out.println("ping");
                    synchronize.notify();
                    synchronize.wait();
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }).start();
        new Thread(() -> {
            while (true){
                synchronized (synchronize){
                    try {
                    System.out.println("pong");
                    synchronize.notify();
                    synchronize.wait();
                    Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

}
