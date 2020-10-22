package com.mokrousov.parallel.lab4.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderCounter {
  private int readersCount;
  
  public Lock lock = new ReentrantLock();
  public Condition reader = lock.newCondition();
  
  public int inc() {
    return ++readersCount;
  }
  
  public int dec() {
    return --readersCount;
  }
}
