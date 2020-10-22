package com.mokrousov.parallel.lab4.task1;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Queue queue = new Queue();
    
    String groupName = "producers-consumers";
    ThreadGroup group = new ThreadGroup(groupName);
    
    Producer[] producers = { new Producer(group, groupName, queue), new Producer(group, groupName, queue) };
    Consumer[] consumers = { new Consumer(group, groupName, queue), new Consumer(group, groupName, queue) };
    
    for (Producer producer : producers) {
      producer.start();
    }
    
    for (Consumer consumer : consumers) {
      consumer.start();
    }
    
    Thread.sleep(15000);
    group.interrupt();
    System.out.println("Finished");
  }
}
