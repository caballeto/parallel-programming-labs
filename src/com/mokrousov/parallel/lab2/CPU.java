package com.mokrousov.parallel.lab2;

public class CPU {
  private CPUProcess process;
  private int executionTime;
  boolean isSecondary;
  
  public void run() {
    if (process != null) {
      executionTime++;
    }
  }
  
  public boolean isBusySecondary() {
    return isBusy() && isSecondary;
  }
  
  public boolean isBusy() {
    return process != null && process.length - executionTime > 0;
  }
  
  public CPUProcess getProcess() {
    return process;
  }
  
  public void setProcess(CPUProcess process, boolean isSecondary) {
    this.process = process;
    this.isSecondary = isSecondary;
    this.executionTime = 0;
  }
}
