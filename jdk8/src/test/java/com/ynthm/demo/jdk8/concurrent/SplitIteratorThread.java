package com.ynthm.demo.jdk8.concurrent;

import java.util.Spliterator;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class SplitIteratorThread<T> extends Thread {

  private final Spliterator<T> spliterator;

  public SplitIteratorThread(Spliterator<T> spliterator) {
    this.spliterator = spliterator;
  }

  @Override
  public void run() {
    spliterator.forEachRemaining(
        i -> {
          System.out.println(i);
        });
  }
}
