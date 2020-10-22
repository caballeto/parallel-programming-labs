package com.mokrousov.parallel.lab4.task4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Client extends Thread {
  private WaitingRoom waitingRoom;
  private Lock waitingRoomLock;
  private Lock barberLock;
  private Lock clientLock;
  private Condition clientAvailable;
  private Condition barberAvailable;
  
  public Client(ThreadGroup group, String name, WaitingRoom waitingRoom, Lock waitingRoomLock, Lock barberLock,
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
    System.out.println("Customer came");
    if (canEnter()) {
      barberLock.lock();
      clientLock.lock();
      clientAvailable.signal();
      clientLock.unlock();
      System.out.println("Waiting for barber to become available.");
      try {
        barberAvailable.await();
        System.out.println("Barber is available.");
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      } finally {
        barberLock.unlock();
      }
    }
    System.out.println("Customer left");
  }
  
  private boolean canEnter() {
    waitingRoomLock.lock();
    if (waitingRoom.canAddClient()) {
      System.out.println("Entering: waiting room has place for client.");
      waitingRoom.addClient();
      waitingRoomLock.unlock();
      return true;
    } else {
      System.out.println("Leaving: waiting room is full.");
      waitingRoomLock.unlock();
      return false;
    }
  }
}
