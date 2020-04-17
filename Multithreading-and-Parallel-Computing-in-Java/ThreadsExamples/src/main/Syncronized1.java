package main;

public class Syncronized1 {

    private static int counter = 0;

    //SYNCRONIZED MEANS THAT WHO IS GOING TO USE THIS METHOD IS GOING TO BE IN ORDER
    //SYNCRONIZED
    //THE TWO THREADS WILL INCREMENT CORRECTLY THE VARIABLES
    //IT IS NOT GOING TO BE A GOOD PERFORMANCE
    public static synchronized void increment() {
        ++counter;
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
//                    ++counter;
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
//                    ++counter;
                    increment();
                }
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
    }

    public static void main(String[] args) {
        process();
        //NOT ALWAYS THE RESULT IS GOING TO BE 200, BECAUSE BOTH THREADS ARE LEADING WITH A VOLATILE VARIABLE
        System.out.println(counter);
    }

}
