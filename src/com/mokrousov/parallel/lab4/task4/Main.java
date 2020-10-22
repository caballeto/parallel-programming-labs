package com.mokrousov.parallel.lab4.task4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  public static int CLIENT_NUMBER = 10;
  public static int WAITING_ROOM_SIZE = 5;
  
  public static void main(String[] args) throws InterruptedException {
    String name = "barber-clients";
    ThreadGroup group = new ThreadGroup(name);
    Lock waitingRoomLock = new ReentrantLock();
    Lock barberLock = new ReentrantLock();
    Lock clientLock = new ReentrantLock();
    Condition clientAvailable = clientLock.newCondition();
    Condition barberAvailable = barberLock.newCondition();
    
    WaitingRoom waitingRoom = new WaitingRoom(WAITING_ROOM_SIZE);
    Barber barber = new Barber(group, name, waitingRoom, waitingRoomLock, barberLock, clientLock, clientAvailable, barberAvailable);
    Client[] clients = new Client[CLIENT_NUMBER];
    
    barber.start();
    
    for (int i = 0; i < clients.length; i++) {
      clients[i] = new Client(group, name, waitingRoom, waitingRoomLock, barberLock, clientLock, clientAvailable, barberAvailable);
      clients[i].start();
    }
    
    Thread.sleep(2000);
    group.interrupt();
  }
}
