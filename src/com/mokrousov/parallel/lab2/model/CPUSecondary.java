package com.mokrousov.parallel.lab2.model;

public class CPUSecondary extends Thread {
  private final CPUQueue queue;
  
  public CPUSecondary(CPUQueue queue) {
    this.queue = queue;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      Task task = queue.get();
      task = task == null ? queue.await() : task;
      if (task != null) {
        execute(task);
      }
    }
  }
  
  private void execute(Task task) {
    try {
      Thread.sleep(task.getTime());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
