package com.mokrousov.parallel.lab1;

import java.util.ArrayList;
import java.util.List;

public class Main {
  private static double[] getVec(int size) {
    double[] vec = new double[size];
    for (int i = 0; i < size; i++)
      vec[i] = Math.random() * 1000;
    return vec;
  }
  
  public static double runSerial(double[] vec) {
    double result = 0;
    for (double x : vec)
      result += x * x;
    return Math.sqrt(result);
  }
  
  public static double runParallel(double[] vec, int threadNum) throws InterruptedException {
    double parallelResult = 0;
    NormCalcThread[] threads = new NormCalcThread[threadNum];
    for (int i = 0; i < threads.length; i++) {
      int startIndex = vec.length / threads.length * i;
      int endIndex = i == threads.length - 1 ? vec.length : vec.length / threads.length * (i + 1);
      threads[i] = new NormCalcThread(vec, startIndex, endIndex);
      threads[i].start();
    }
  
    for (int i = 0; i < threads.length; i++)
      threads[i].join();
    for (int i = 0; i < threads.length; i++)
      parallelResult += threads[i].getResult();
    return Math.sqrt(parallelResult);
  }
  
  public static void main(String[] args) throws InterruptedException {
    int[] sizes = { 100000, 1000000, 10000000, 100000000 };
    int[] threadNums = { 2, 4, 6, 8, 16 };
    
    System.out.println("Variant #5");
    
    for (int size : sizes) {
      List<Long> times = new ArrayList<>();
      long serialTime = 0;
      for (int threadNum : threadNums) {
        double[] vec = getVec(size);
        double result;
  
        System.out.println("-------");
        System.out.println(String.format("Running size: %d, threads: %d", size, threadNum));

        long startTime = System.currentTimeMillis();
        result = runSerial(vec);
        long runTime = System.currentTimeMillis() - startTime;
        serialTime = runTime;
        System.out.println(String.format("Serial run: %.4f, time: %dms.", result, runTime));
      
        startTime = System.currentTimeMillis();
        result = runParallel(vec, threadNum);
        runTime = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Parallel run: %.4f, time: %dms.", result, runTime));
        
        times.add(runTime);
      }
      
      System.out.println("*******");
      System.out.println("Speedup coefficients for size: " + size);
      
      for (int i = 0; i < threadNums.length; i++) {
        double coef = serialTime / (double) times.get(i);
        System.out.println(String.format("Speedup coefficient for %d threads: %f", threadNums[i], coef));
        System.out.println(String.format("Efficiency coefficient for %d threads: %f", threadNums[i], coef / threadNums[i]));
      }
      
      System.out.println();
    }
  }
}
