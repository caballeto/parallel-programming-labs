package com.mokrousov.parallel.lab4.task3;

import java.util.concurrent.Semaphore;

public class Main {
  public static int PHILOSOPHER_COUNT = 10;
  
  public static void main(String[] args) throws InterruptedException {
    String groupName = "dining-philosophers";
    ThreadGroup group = new ThreadGroup(groupName);
    Philosopher[] philosophers = new Philosopher[PHILOSOPHER_COUNT];
    Semaphore[] forks = new Semaphore[PHILOSOPHER_COUNT];
    
    for (int i = 0; i < forks.length; i++) {
      forks[i] = new Semaphore(1);
    }
    
    for (int i = 0; i < philosophers.length; i++) {
      Semaphore left = forks[i], right = forks[(i + 1) % philosophers.length];
      philosophers[i] = i == philosophers.length - 1
        ? new Philosopher(group, groupName, i, right, left)
        : new Philosopher(group, groupName, i, left, right);
      philosophers[i].start();
    }
    
    Thread.sleep(10000);
    group.interrupt();
    System.out.println("--- INTERRUPT ---");
  }
}
