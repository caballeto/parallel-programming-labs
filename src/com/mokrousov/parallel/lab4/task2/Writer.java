package com.mokrousov.parallel.lab4.task2;

public class Writer extends Thread {
  private int numMessages;
  private Document document;
  
  public Writer(ThreadGroup group, String name, int numMessages, Document document) {
    super(group, name);
    this.numMessages = numMessages;
    this.document = document;
  }
  
  @Override
  public void run() {
    while (!isInterrupted() && numMessages > 0) {
      String message = "Writing message " + numMessages;
      System.out.println(message);
      write(message);
      numMessages--;
    }
  }
  
  private void write(String message) {
    document.lock.lock();
    try {
      document.write(message);
      document.writer.signalAll();
      document.writer.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      document.lock.unlock();
    }
  }
}
