package com.mokrousov.parallel.lab2;

import com.mokrousov.parallel.lab2.model.CPUPrimary;
import com.mokrousov.parallel.lab2.model.CPUSecondary;
import com.mokrousov.parallel.lab2.model.CPUProcess;
import com.mokrousov.parallel.lab2.model.CPUQueue;

public class Main {
  private static final int PROCESS_NUM = 10;
  private static final int LOWER_WAIT_LIMIT = 100;
  private static final int UPPER_WAIT_LIMIT = 4000;
  private static final int LOWER_PROCESS_TIME_LIMIT = 2000;
  private static final int UPPER_PROCESS_TIME_LIMIT = 5000;
  
  public static void runSimulation(int processNum) {
    CPUQueue queue1 = new CPUQueue(), queue2 = new CPUQueue();
    
    CPUProcess cpuProcess1 = new CPUProcess(queue1, processNum, LOWER_PROCESS_TIME_LIMIT, UPPER_PROCESS_TIME_LIMIT, LOWER_WAIT_LIMIT, UPPER_WAIT_LIMIT);
    CPUProcess cpuProcess2 = new CPUProcess(queue2, processNum, LOWER_PROCESS_TIME_LIMIT, UPPER_PROCESS_TIME_LIMIT, LOWER_WAIT_LIMIT, UPPER_WAIT_LIMIT);
  
    CPUPrimary   cpu1 = new CPUPrimary(queue1, queue2);
    CPUSecondary cpu2 = new CPUSecondary(queue2);
    
    cpu1.start();
    cpu2.start();
    
    cpuProcess1.start();
    cpuProcess2.start();
    
    while (true) {
      if (!cpuProcess1.isAlive() && !cpuProcess2.isAlive()) {
        cpu1.interrupt();
        cpu2.interrupt();
        break;
      }
    }
  
    System.out.println("Total number of processes (per each CPU): " + processNum);
    System.out.println("Max size, queue 1: " + queue1.maxSize() + ", queue 2: " + queue2.maxSize());
    System.out.println("Number of processes interrupted: " + cpu1.numOfInterruptedProcesses());
  }
  
  public static void main(String[] args) {
    int[] processNums = { 5, 10, 15 };
    for (int processNum : processNums) {
      runSimulation(processNum);
    }
  }
}
