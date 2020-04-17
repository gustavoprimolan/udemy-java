package main;


class Runner1 {

    public void startRunning() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner 1: " + i);
        }
    }
}

class Runner2 {

    public void startRunning() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner 2: " + i);
        }
    }
}

public class SequencialProcessing {

    public static void main(String[] args) {
        //SEQUENCIAL PROCESSING
        Runner1 runner1 = new Runner1();
        Runner2 runner2 = new Runner2();

        runner1.startRunning();
        runner2.startRunning();
    }


}
