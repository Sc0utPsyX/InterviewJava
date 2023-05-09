package homework3.second;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counter {
    ReentrantReadWriteLock rwl;
    Integer count;

    public Counter() {
    count = 0;
    rwl = new ReentrantReadWriteLock();
    }

    public Integer getCount() {
        Lock readLock = rwl.readLock();
        try {
            readLock.lock();
            return count;
        } finally {
            readLock.unlock();
        }
    }

    void increment(){
        Lock writeLock = rwl.writeLock();
        try {
            writeLock.lock();
            count++;
        } finally {
            writeLock.unlock();
        }
    }

    void decrement(){
        Lock writeLock = rwl.writeLock();
        try {
            writeLock.lock();
            count--;
        } finally {
            writeLock.unlock();
        }
    }
}

