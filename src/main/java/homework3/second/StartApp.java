package homework3.second;

public class StartApp {
    public static void main(String[] args) throws InterruptedException {
        //2. Реализовать потокобезопасный счетчик с помощью интерфейса Lock.
        Counter counter = new Counter();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 12; i++) {
                counter.decrement();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(counter.getCount());
    }
}

