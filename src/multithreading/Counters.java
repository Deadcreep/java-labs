package multithreading;

import java.util.concurrent.atomic.AtomicLong;

abstract class Counter {
    abstract void increaseCounter();

    abstract long getCounter();
}

class AtomicCounter extends Counter
{
    private AtomicLong counter = new AtomicLong( 0 );

    void increaseCounter() {
        counter.incrementAndGet();
    }

    long getCounter() {
        return counter.get();
    }
}

class SynchronizedCounter extends Counter
{
    private long counter = 0L;

    public synchronized void increaseCounter() {
        counter++;
    }

    public long getCounter() {
        return counter;
    }
}