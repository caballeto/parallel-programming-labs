package com.mokrousov.parallel.lab4.task1;

public class Consumer extends Thread {
  Queue queue;
  
  public Consumer(ThreadGroup group, String name, Queue queue) {
    super(group, name);
    this.queue = queue;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      Task task = queue.pull();
      if (task != null) {
        try {
          System.out.println("Working on task " + task.time);
          Thread.sleep(task.time);
        } catch (InterruptedException e) {
          return;
        }
      } else {
        return;
      }
    }
  }
}
