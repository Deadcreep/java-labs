package multithreading;


public class Synchronization
{
    public static void main(String[] args) throws InterruptedException {

        long atomicStartTime = System.currentTimeMillis();
        AtomicCounter atomicCounter = new AtomicCounter();
        for(int i = 0; i < 20000; i++) {
            CounterThread ct = new CounterThread( atomicCounter );
            ct.start();
        }
        Thread.sleep(10000);
        long atomicStopTime = System.currentTimeMillis();
        long atomicElapsedTime = atomicStopTime - atomicStartTime - 10000;
        System.out.println("AtomicCounter:" + atomicCounter.getCounter());
        System.out.println("Work time:" + atomicElapsedTime);

        long syncStartTime = System.currentTimeMillis();

        SynchronizedCounter syncCounter = new SynchronizedCounter();
        for(int i = 0; i < 20000; i++) {
            CounterThread ct = new CounterThread( syncCounter );
            ct.start();
        }
        Thread.sleep(10000);
        long syncStopTime = System.currentTimeMillis();
        long syncElapsedTime = syncStopTime - syncStartTime - 10000;
        System.out.println("\nSyncCounter:" + syncCounter.getCounter());
        System.out.println("Work time:" + syncElapsedTime);
        long deltaTime = atomicElapsedTime - syncElapsedTime;
        System.out.println("Difference:" + deltaTime);
    }
}



class CounterThread extends Thread
{
    private Counter ct;

    CounterThread(Counter counter) {
        this.ct = counter;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10000; i++) {
            ct.increaseCounter();
        }
    }
}