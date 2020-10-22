package com.mokrousov.parallel.lab4.task1;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Queue {
  public static int MAX_SIZE = 10;
  public static Logger logger = Logger.getLogger(Queue.class.getName());
  
  List<Task> tasks = new LinkedList<>();
  
  public synchronized void push(Task task) {
    if (tasks.size() >= MAX_SIZE) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        logger.log(Level.SEVERE, null, e);
      }
    }
    logger.log(Level.INFO, "Adding task to queue");
    tasks.add(task);
    this.notifyAll();
  }
  
  public synchronized Task pull() {
    if (tasks.isEmpty()) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        Logger.getLogger(Queue.class.getName()).log(Level.SEVERE, null, e);
        return null;
      }
    }
    logger.log(Level.INFO, "Removing task from queue");
    Task result = tasks.isEmpty() ? null : tasks.remove(0);
    this.notifyAll();
    return result;
  }
}
