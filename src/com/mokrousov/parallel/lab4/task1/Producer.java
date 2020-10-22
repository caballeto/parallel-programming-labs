package com.mokrousov.parallel.lab4.task1;

public class Producer extends Thread {
  Queue queue;
  
  public Producer(ThreadGroup group, String name, Queue queue) {
    super(group, name);
    this.queue = queue;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      queue.push(new Task(5));
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        return;
      }
    }
    Thread.currentThread().interrupt();
  }
}
