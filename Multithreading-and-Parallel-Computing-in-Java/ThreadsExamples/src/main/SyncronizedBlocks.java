package main;

public class SyncronizedBlocks {

    private static int counter1 = 0;
    private static int counter2 = 0;

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

//    public synchronized static void add() {
//        counter1++;
//    }

    public static void add() {
//        synchronized (SyncronizedBlocks.class) {
        synchronized (lock1) {
            counter1++;
        }
    }

    public synchronized static void addAgain() {
//        synchronized (SyncronizedBlocks.class) {
        synchronized (lock2) {
            counter2++;
        }
    }

    public static void computer() {
        for (int i = 0; i < 100; i++) {
            add();
            addAgain();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                computer();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                computer();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counte1: " + counter1);
        System.out.println("Counte2: " + counter2);
    }

}
