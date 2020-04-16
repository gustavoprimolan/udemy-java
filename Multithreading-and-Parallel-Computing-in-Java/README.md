<h1>Multithreading and Parallel Computing in Java</h1>

* link -> https://www.udemy.com/course/multithreading-and-parallel-computing-in-java

<h1>Multithreading Theory</h1>

* What is the motivation behind multithreading?
    * By default programming languages are sequential: they execute the statements and commands one by one (line by line)

    ```java

    public static void main(String[] args) {
        initializeArrays();
        downloadStocks();
        initializeTimeSeriesModels();
        makePredictions();
    }


    ```


    * In single threaded application these methdos will be called one by one: we have to wait for them to finish one by one;;;
    * Npt the best solution possible: time consuming operations may freeze the application and the users may not know what's happening
    * THe most relevant reason for multithreading is to separate multiple tasks: one or more which is time critical and might be subject to interference by the execution of other tasks.
    * For example:
        * Our stock market related software is able to download data from the web (Yahoo FInance for example)
        * It takes 2-3 mins to fetch the data BUT we want to make sure the application is not frozen
        * Solution: we create a distinct thread for the download operation and during this procedure the user can do whatever he/she wants in the application


* Multithreading
    * Multithreading is the ability of the CPU to execute multiple processes or threads concurrently;
    * BOth processes and threads are independent sequences of execution;
    * Process: a process is an instance of program execution
        * When you open a software or a web browser: these are distinct processes
        * The OS assigns distinct registers, stack memory and heap memory to every single process
    * In java o can create processes with the help of ProcessBuilder class;

    * Process
        * -> Registers
        * -> Program Counter
        * -> Stack Memory
        * -> Heap Memory
    
    * THREADS: a thread is a "light-weight" process
        * It is a unit of execution within a given process (a process may have several threads)
        * Each thread in a process shares the memory and resources and this is why programmers have to deal with concurrent programming and multithreading
        * Creating a new thread requires fewer resources than creating a new process
    

<h2>What is time-slicing algorithm?</h2>