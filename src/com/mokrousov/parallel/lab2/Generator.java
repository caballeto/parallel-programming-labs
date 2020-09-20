package com.mokrousov.parallel.lab2;

public class Generator {
  private static final int DELAY_MIN = 10;
  private static final int DELAY_MAX = 100;
  private static final int LENGTH_MIN = 10;
  private static final int LENGTH_MAX = 100;
  
  int n;
  int time;
  int delayTime;
  int[] delays;
  int[] lengths;
  
  public Generator(int processNum) {
    this.n = 0;
    this.time = 0;
    this.delayTime = 0;
    this.delays = new int[processNum];
    this.lengths = new int[processNum];
    for (int i = 0; i < processNum; i++) {
      delays[i] = DELAY_MIN + (int)(Math.random() * ((DELAY_MAX - DELAY_MIN) + 1));
      lengths[i] = LENGTH_MIN + (int)(Math.random() * ((LENGTH_MAX - LENGTH_MIN) + 1));
    }
  }
  
  public boolean hasNext() {
    return n < delays.length;
  }
  
  public int nextTime() {
    return delayTime + delays[n];
  }
  
  public CPUProcess next() {
    int processStartTime = time + delays[n];
    time += delays[n] + lengths[n];
    delayTime += delays[n];
    return new CPUProcess(lengths[n++], processStartTime);
  }
}
