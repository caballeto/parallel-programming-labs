package com.mokrousov.parallel.lab2;

public class Main {
  public static void runSimulation(int processNum) {
    CPUQueue q1 = new CPUQueue(processNum), q2 = new CPUQueue(processNum);
    CPU cpu1 = new CPU(), cpu2 = new CPU();
    int maxQueue1 = 0, maxQueue2 = 0, numInterrupted = 0;
    int t = 0;
    
    for (; !q1.isFinished() || !q2.isFinished(); t++) {
      q1.update(t);
      q2.update(t);
      
      cpu1.run();
      cpu2.run();
    
      maxQueue1 = Math.max(maxQueue1, q1.size());
      maxQueue2 = Math.max(maxQueue2, q2.size());
    
      if (cpu1.isBusy()) {
        if (cpu1.isBusySecondary() && !q1.isEmpty()) {
          numInterrupted++;
          q2.addFirst(cpu1.getProcess());
          cpu1.setProcess(q1.poll(), false);
        }
      } else if (!q1.isEmpty()) {
        cpu1.setProcess(q1.poll(), false);
      } else if (!q2.isEmpty()) {
        cpu1.setProcess(q2.poll(), true);
      }
      
      if (!cpu2.isBusy() && !q2.isEmpty()) {
        cpu2.setProcess(q2.poll(), false);
      }
    }
    
    System.out.println("Total number of processes (per each CPU): " + processNum);
    System.out.println("Max size, queue 1: " + maxQueue1 + ", queue 2: " + maxQueue2);
    System.out.println("Number of processes interrupted: " + numInterrupted);
  }
  
  public static void main(String[] args) {
    int[] processNums = { 100, 1000, 10000, 100000 };
    for (int processNum : processNums) {
      runSimulation(processNum);
    }
  }
}
