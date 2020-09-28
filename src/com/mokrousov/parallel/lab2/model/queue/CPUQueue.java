package com.mokrousov.parallel.lab2.model.queue;

import com.mokrousov.parallel.lab2.model.process.Task;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CPUQueue {
  private final Deque<Task> deque  = new LinkedList<>();
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition notEmpty = lock.newCondition();
  private int maxSize = 0;
  
  public void putFirst(Task task) {
    lock.lock();
    try {
      deque.addFirst(task);
      saveMaxSize();
      notEmpty.signalAll();
    } finally {
      lock.unlock();
    }
  }
  
  public void put(Task task) {
    lock.lock();
    try {
      deque.addLast(task);
      saveMaxSize();
      notEmpty.signalAll();
    } finally {
      lock.unlock();
    }
  }
  
  public Task get() {
    lock.lock();
    try {
      return deque.poll();
    } finally {
      lock.unlock();
    }
  }
  
  public Task poll(long timeout, TimeUnit unit) throws InterruptedException {
    long nanos = unit.toNanos(timeout);
    lock.lock();
    try {
      while (deque.isEmpty()) {
        if (nanos <= 0L) return null;
        nanos = notEmpty.awaitNanos(nanos);
      }
      
      return deque.poll();
    } finally {
      lock.unlock();
    }
  }
  
  public int maxSize() {
    return maxSize;
  }
  
  public Task await() {
    lock.lock();
    try {
      notEmpty.await();
      return deque.poll();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
    
    return null;
  }
  
  private void saveMaxSize() {
    maxSize = Math.max(deque.size(), maxSize);
  }
}
