package com.mokrousov.parallel.lab2;

import java.util.ArrayDeque;
import java.util.Deque;

public class CPUQueue {
  private Generator generator;
  private Deque<CPUProcess> processQueue;
  
  public CPUQueue(int processNum) {
    this.generator = new Generator(processNum);
    this.processQueue = new ArrayDeque<>();
  }
  
  public void update(int time) {
    if (generator.hasNext() && generator.nextTime() <= time) {
      processQueue.add(generator.next());
    }
  }
  
  public int size() {
    return processQueue.size();
  }
  
  public boolean isEmpty() {
    return processQueue.isEmpty();
  }
  
  public CPUProcess poll() {
    return processQueue.poll();
  }
  
  public void addFirst(CPUProcess process) {
    processQueue.addFirst(process);
  }
  
  public boolean isFinished() {
    return !generator.hasNext();
  }
}
