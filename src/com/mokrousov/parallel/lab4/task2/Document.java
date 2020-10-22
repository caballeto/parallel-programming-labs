package com.mokrousov.parallel.lab4.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Document {
  private String value;
  
  public Lock lock = new ReentrantLock();
  public Condition writer = lock.newCondition();
  
  void write(String value) {
    this.value = value;
  }
  
  String read() {
    return value;
  }
}
