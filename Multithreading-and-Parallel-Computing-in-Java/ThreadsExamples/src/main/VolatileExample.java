package main;

class Worker implements Runnable {

    //FORCE THE JAVA TO NOT CACHE THIS VARIABLE AND READ FROM THE MAIN MEMORY (RAM)
    //IT'S NOT THE BEST FOR PERFORMANCE BUT IT IS GUARANTEED THAT WE WILL USE THE VARIABLE FROM MAIN MEMORY
    private volatile boolean isTerminated = false;


    @Override
    public void run() {
        while(!isTerminated) {
            System.out.println("Hello from worker class...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}

public class VolatileExample {

    public static void main(String[] args) {
        Worker worker = new Worker();
        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.setTerminated(true);
        //VERIFYING IF THE PROCESSOR ITS NOT CACHING VARIABLE TERMINATED
        System.out.println("Finished...");

    }
}
