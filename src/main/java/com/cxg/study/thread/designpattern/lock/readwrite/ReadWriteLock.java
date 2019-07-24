package com.cxg.study.thread.designpattern.lock.readwrite;   // Administrator 于 2019/7/23 创建;

public class ReadWriteLock {
    private int readingReaders;
    private int waitingReaders;
    private int writingWriters;
    private int waitingWriters;

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            while (this.writingWriters > 0) {
                this.wait();
            }
            this.readingReaders++;
        } finally {
            this.waitingReaders--;
        }
    }
}
