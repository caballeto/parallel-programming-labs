package com.mokrousov.parallel.lab5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {
  public static final int LOWER_BOUND = 5;
  public static final int UPPER_BOUND = 100;
  public static final int ARRAY_SIZE = 1000;
  
  public static List<Integer> randomList(int size) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(randomInt(LOWER_BOUND, UPPER_BOUND));
    }
    
    return list;
  }
  
  public static List<Integer> combineLists(List<Integer> a, List<Integer> b) {
    System.out.println("Started combine.");
    List<Integer> combined = new ArrayList<>();
    int i = 0, j = 0;
    while (i < a.size() || j < b.size()) {
      if      (i >= a.size())       combined.add(b.get(j++));
      else if (j >= b.size())       combined.add(a.get(i++));
      else if (a.get(i) < b.get(j)) combined.add(a.get(i++));
      else                          combined.add(b.get(j++));
    }
    System.out.println("Ended combine.");
    return combined;
  }
  
  public static void main(String[] args) {
    System.out.println("Var #22");
    
    CompletableFuture<List<Integer>> generatedList1 = CompletableFuture.supplyAsync(() -> randomList(ARRAY_SIZE));
    CompletableFuture<Integer> maxElement = generatedList1.thenApply(Collections::max);
    CompletableFuture<List<Integer>> list1 = generatedList1.thenCombine(
      maxElement, (list, max) -> list.stream().filter(i -> i > 0.7 * max).collect(Collectors.toList()));
    
    CompletableFuture<List<Integer>> list2 = CompletableFuture.supplyAsync(() -> randomList(ARRAY_SIZE))
      .thenApply(list -> list.stream().filter(i -> i % 3 == 0).collect(Collectors.toList()));
  
    CompletableFuture<List<Integer>> result = CompletableFuture.allOf(
      list1.thenAccept(Collections::sort), list2.thenAccept(Collections::sort))
      .thenApply(i -> combineLists(list1.join(), list2.join()));
  
    System.out.println(result.join());
  }
  
  public static int randomInt(int lo, int hi) {
    return lo + (int) (Math.random() * ((hi - lo) + 1));
  }
}
