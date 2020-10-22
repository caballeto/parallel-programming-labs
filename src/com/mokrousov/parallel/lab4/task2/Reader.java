package com.mokrousov.parallel.lab4.task2;

public class Reader extends Thread {
  private Document document;
  private ReaderCounter counter;
  
  public Reader(ThreadGroup group, String name, Document document, ReaderCounter counter) {
    super(group, name);
    this.document = document;
    this.counter = counter;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      read();
    }
  }
  
  private void startReader() {
    counter.lock.lock();
    try {
      if (counter.inc() == 1) {
        document.lock.lock();
        try {
          document.writer.await();
        } catch (InterruptedException e) {
          interrupt();
        } finally {
          document.lock.unlock();
        }
      }
    } finally {
      counter.lock.unlock();
    }
  }
  
  private void endReader() {
    counter.lock.lock();
    try {
      if (counter.dec() == 0) {
        document.lock.lock();
        try {
          document.writer.signalAll();
          counter.reader.signalAll();
        } finally {
          document.lock.unlock();
        }
      } else {
        counter.reader.await();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      counter.lock.unlock();
    }
  }
  
  private void read() {
    startReader();
    
    if (!isInterrupted()) {
      System.out.println("Read message: " + document.read());
    }
    
    endReader();
  }
}
