package main.concurrentcollections;



import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchExample {


    //* This is used to synchronize one or more tasks by forcing them to wait for the completion of a set of operations being performed by other tasks
//        * You give an initial count to a COuntDownLAtch object, and any task that calls await()
//        * On that object will block until the count reaches zero
//
//        * Other tasks may call countDown() on the object to reduce the count, presumably when a task finishes its job
//        * a CountDownLAtch --> the count cannot be reset!!
//        * If you need a version that resets the count, you can use a CycliBarrier instead
//
//        * The tasks that call countDown() are not blocked when they make that call. Only the call to await() is blocked until the count
//        * A typical use is to divide a problem into n independently solvable tasks and create a CountDownLAtch with a value of N
//        * When each task is finished it calls countDown() on the latch. TAsks waiting for the problem to be solved call await()
//        * on the latch to hold themselves back until it is completed
//
//        * For example: you want to trigger something after 10 000 users download an image!!

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            executorService.execute(new Worker(i, latch));
        }
        try {
            //WAIT FOR EVERY WORKER
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All the prerequisites are done...");

        new HashMap<>();
    }

}

class Worker implements Runnable {

    private int id;
    private CountDownLatch countDownLatch;
    private Random random;

    public Worker(int id, CountDownLatch countDownLatch) {
        this.id = id;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        doWork();
        //IT WILL COUNT DOWN THE VALUE INSTANTIATED
        countDownLatch.countDown();
    }

    private void doWork() {
        System.out.println("Thread with id " + this.id + " starts working...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}