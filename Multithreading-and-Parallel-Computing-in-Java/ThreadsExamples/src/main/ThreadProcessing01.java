package main;


class Runner1Par implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Runner 1: " + i);
        }
    }
}

class Runner2Par implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Runner 2: " + i);
        }
    }
}

public class ThreadProcessing01 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1Par());
        Thread t2 = new Thread(new Runner2Par());

        t1.start();
        t2.start();
    }


}
