package main.basicmultithreading;

class Runner1Par02 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("Runner 1: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Runner2Par02 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("Runner 2: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



public class ThreadProcessing02 {

    public static void main(String[] args) {
//        Thread t1 = new Thread(new Runner1Par());
//        Thread t2 = new Thread(new Runner2Par());
        Runner1Par02 t1 = new Runner1Par02();
        Runner2Par02 t2 = new Runner2Par02();
        t1.start();
        t2.start();

        try {
            //WAIT TO THREAD TO DIE
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //IT WILL RUN FIRST WITHOUT THE join METHOD
        System.out.println("Finishing the tasks....");
    }

}
