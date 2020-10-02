package com.mokrousov.parallel.lab2.model;

import java.util.concurrent.TimeUnit;

public class CPUPrimary extends Thread {
  private final CPUQueue primaryQueue;
  private final CPUQueue secondaryQueue;
  private int numInterrupted;
  
  public CPUPrimary(CPUQueue primaryQueue, CPUQueue secondaryQueue) {
    this.primaryQueue = primaryQueue;
    this.secondaryQueue = secondaryQueue;
    this.numInterrupted = 0;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      Task task = primaryQueue.get();
      if (task != null) {
        execute(task, true);
      } else {
        task = secondaryQueue.get();
        if (task != null) {
          execute(task, false);
        }
      }
    }
  }
  
  public int numOfInterruptedProcesses() {
    return numInterrupted;
  }
  
  private void execute(Task task, boolean isFromPrimaryQueue) {
    if (isFromPrimaryQueue) {
      executePrimary(task);
    } else {
      executeSecondary(task);
    }
  }
  
  private void executeSecondary(Task task) {
    try {
      Task newTask = primaryQueue.poll(task.getTime(), TimeUnit.MILLISECONDS);
      if (newTask != null) {
        secondaryQueue.putFirst(task);
        numInterrupted++;
        executePrimary(newTask);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
  
  private void executePrimary(Task task) {
    try {
      Thread.sleep(task.getTime());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
