package com.mokrousov.parallel.lab2.model;

public class CPUProcess extends Thread {
  private final CPUQueue queue;
  private final int processNum;
  private final int lowerWaitLimit;
  private final int upperWaitLimit;
  private final int lowerProcessTimeLimit;
  private final int upperProcessTimeLimit;
  
  public CPUProcess(CPUQueue queue, int processNum, int lowerProcessTimeLimit,
                    int upperProcessTimeLimit, int lowerWaitLimit, int upperWaitLimit) {
    this.queue = queue;
    this.processNum = processNum;
    this.lowerWaitLimit = lowerWaitLimit;
    this.upperWaitLimit = upperWaitLimit;
    this.lowerProcessTimeLimit = lowerProcessTimeLimit;
    this.upperProcessTimeLimit = upperProcessTimeLimit;
  }
  
  @Override
  public void run() {
    for (int i = 0; i < processNum && !isInterrupted(); i++) {
      waitForGeneration();
      queue.put(new Task(randomProcessTime()));
    }
  }
  
  private int randomProcessTime() {
    return randomInt(lowerProcessTimeLimit, upperProcessTimeLimit);
  }
  
  private int randomWaitTime() {
    return randomInt(lowerWaitLimit, upperWaitLimit);
  }
  
  private int randomInt(int lo, int hi) {
    return lo + (int) (Math.random() * ((hi - lo) + 1));
  }
  
  private void waitForGeneration() {
    try {
      Thread.sleep(randomWaitTime());
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}
