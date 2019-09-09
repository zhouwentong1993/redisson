package org.redisson;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import org.redisson.misc.RPromise;

public class RedissonLockEntry implements PubSubEntry<RedissonLockEntry> {

    private int counter;

    private final Semaphore latch;
    private final RPromise<RedissonLockEntry> promise;
    private final ConcurrentLinkedQueue<Runnable> listeners = new ConcurrentLinkedQueue<>();

    public RedissonLockEntry(RPromise<RedissonLockEntry> promise) {
        super();
        this.latch = new Semaphore(0);
        this.promise = promise;
    }

    public void aquire() {
        counter++;
    }

    public int release() {
        return --counter;
    }

    public RPromise<RedissonLockEntry> getPromise() {
        return promise;
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public boolean removeListener(Runnable listener) {
        return listeners.remove(listener);
    }

    public ConcurrentLinkedQueue<Runnable> getListeners() {
        return listeners;
    }

    public Semaphore getLatch() {
        return latch;
    }

}
