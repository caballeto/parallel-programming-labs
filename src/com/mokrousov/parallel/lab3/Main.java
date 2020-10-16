package com.mokrousov.parallel.lab3;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Main {
  public static int ARRAY_SIZE = 100;
  public static Random random = new Random();
  
  public static int countElementsByPredicate(int[] array, Predicate<Integer> predicate) {
    AtomicInteger count = new AtomicInteger(0);
  
    IntStream.of(array).parallel().forEach(x -> {
      if (predicate.test(x)) {
        int oldValue;
        int newValue;
        do {
          oldValue = count.get();
          newValue = oldValue + 1;
        } while (!count.compareAndSet(oldValue, newValue));
      }
    });
    
    return count.get();
  }
  
  public static int[] findMinMaxElementWithIndexes(int[] array) {
    AtomicInteger minIndex = new AtomicInteger(0);
    AtomicInteger maxIndex = new AtomicInteger(0);
    
    IntStream.range(0, array.length).parallel().forEach(index -> {
      int oldValue;
      do {
        oldValue = maxIndex.get();
      } while (array[index] > array[oldValue] && !maxIndex.compareAndSet(oldValue, index));
      
      do {
        oldValue = minIndex.get();
      } while (array[index] < array[oldValue] && !minIndex.compareAndSet(oldValue, index));
    });
    
    return new int[]{ minIndex.get(), maxIndex.get() };
  }
  
  public static int computeHashSum(int[] array) {
    AtomicInteger hashSum = new AtomicInteger(0);
    
    IntStream.of(array).parallel().forEach(x -> {
      int oldValue;
      int newValue;
      do {
        oldValue = hashSum.get();
        newValue = oldValue ^ x;
      } while (!hashSum.compareAndSet(oldValue, newValue));
    });
    
    return hashSum.get();
  }
  
  public static int[] randomArray(int size) {
    int[] array = new int[size];
    for (int i = 0; i < array.length; i++)
      array[i] = random.nextInt();
    return array;
  }
  
  public static void main(String[] args) {
    System.out.println("IP-71 Vladyslav Mokrousov");
    
    int[] array = randomArray(ARRAY_SIZE);
    System.out.println("Array: " + Arrays.toString(array));
    System.out.println();
    
    System.out.println("#1 Counting elements");
    System.out.println("Elements by predicate: " + countElementsByPredicate(array, x -> x % 5 == 0));
    System.out.println();
    
    System.out.println("#2 Computing min and max values");
    
    int[] indexes = findMinMaxElementWithIndexes(array);
    System.out.println(String.format("Min index: %d, min value: %d", indexes[0], array[indexes[0]]));
    System.out.println(String.format("Max index: %d, max value: %d", indexes[1], array[indexes[1]]));
    System.out.println();
   
    System.out.println("#3 Computing hash sum");
    System.out.println("Hash: " + computeHashSum(array));
  }
}
