package com.mokrousov.parallel.lab4.task4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Barber extends Thread {
  private WaitingRoom waitingRoom;
  private Lock waitingRoomLock;
  private Lock barberLock;
  private Lock clientLock;
  private Condition clientAvailable;
  private Condition barberAvailable;
  
  public Barber(ThreadGroup group, String name, WaitingRoom waitingRoom, Lock waitingRoomLock, Lock barberLock,
                Lock clientLock, Condition clientAvailable, Condition barberAvailable) {
    super(group, name);
    this.waitingRoom = waitingRoom;
    this.waitingRoomLock = waitingRoomLock;
    this.barberLock = barberLock;
    this.clientLock = clientLock;
    this.barberAvailable = barberAvailable;
    this.clientAvailable = clientAvailable;
  }
  
  @Override
  public void run() {
    while (!isInterrupted()) {
      nextClient();
      if (isInterrupted()) return;
      serviceClient();
    }
  }
  
  private void nextClient() {
    waitingRoomLock.lock();
    if (waitingRoom.isEmpty()) {
      try {
        System.out.println("Waiting for client.");
        waitingRoomLock.unlock();
        clientLock.lock();
        clientAvailable.await();
        clientLock.unlock();
        System.out.println("Client is available.");
      } catch (InterruptedException e) {
        interrupt();
        return;
      }
      
      waitingRoomLock.lock();
    }
    
    waitingRoom.removeClient();
    waitingRoomLock.unlock();
  }
  
  private void serviceClient() {
    System.out.println("Processed customer");
    barberLock.lock();
    barberAvailable.signal();
    barberLock.unlock();
  }
}
