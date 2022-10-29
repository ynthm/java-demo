package com.ynthm.json.benchmark.benchmark.queue;

/**
 * @author kirito.moe@foxmail.com Date 2018-08-30
 */
public interface Queue<E> {

  boolean offer(E e);

  E poll();
}
