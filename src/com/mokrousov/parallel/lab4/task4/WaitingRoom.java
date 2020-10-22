package com.mokrousov.parallel.lab4.task4;

public class WaitingRoom {
  private int maxClients;
  private int numClients;
  
  public WaitingRoom(int maxClients) {
    this.maxClients = maxClients;
    this.numClients = 0;
  }
  
  public boolean isEmpty() {
    return numClients == 0;
  }
  
  public boolean canAddClient() {
    return numClients + 1 <= maxClients;
  }
  
  public void addClient() {
    numClients++;
  }
  
  public void removeClient() {
    numClients--;
  }
}
