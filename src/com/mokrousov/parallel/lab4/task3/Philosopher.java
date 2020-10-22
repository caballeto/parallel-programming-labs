package com.mokrousov.parallel.lab4.task3;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
  private int id;
  private Semaphore leftFork;
  private Semaphore rightFork;
  
  public Philosopher(ThreadGroup group, String name, int id, Semaphore leftFork, Semaphore rightFork) {
    super(group, name);
    this.id = id;
    this.leftFork = leftFork;
    this.rightFork = rightFork;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      try {
        think();
        leftFork.acquire();
        rightFork.acquire();
        eat();
        rightFork.release();
        leftFork.release();
      } catch (InterruptedException e) {
        return;
      }
    }
  }
  
  private void think() throws InterruptedException {
    System.out.println(System.nanoTime() + " " + id + " Thinking");
    Thread.sleep(((int) (Math.random() * 100)));
  }
  
  private void eat() throws InterruptedException {
    System.out.println(System.nanoTime() + " " + id + " Eating");
    Thread.sleep(((int) (Math.random() * 100)));
  }
}
