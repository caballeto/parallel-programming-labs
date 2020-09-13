package com.mokrousov.parallel.lab1;

public class NormCalcThread extends Thread {
  private double[] vec;
  private int startIndex;
  private int endIndex;
  private double result;
  
  NormCalcThread(double[] vec, int startIndex, int endIndex) {
    this.vec = vec;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }
  
  public double getResult() {
    return result;
  }
  
  @Override
  public void run() {
    for (int i = startIndex; i < endIndex; i++) {
      result += vec[i] * vec[i];
    }
  }
}
