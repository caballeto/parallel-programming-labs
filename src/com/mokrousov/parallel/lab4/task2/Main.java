package com.mokrousov.parallel.lab4.task2;

public class Main {
  public static int NUM_MESSAGES = 25;
  
  public static void main(String[] args) throws InterruptedException {
    String groupName = "writers-readers";
    ThreadGroup group = new ThreadGroup(groupName);
    Document document = new Document();
    ReaderCounter counter = new ReaderCounter();
    
    Writer writer = new Writer(group, groupName, NUM_MESSAGES, document);
    Reader[] readers = { new Reader(group, groupName, document, counter), new Reader(group, groupName, document, counter) };
    
    writer.start();
    
    for (Reader reader : readers) {
      reader.start();
    }
    
    Thread.sleep(3000);
    group.interrupt();
  }
}
