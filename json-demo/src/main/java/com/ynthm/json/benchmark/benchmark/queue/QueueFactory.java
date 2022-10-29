package com.ynthm.json.benchmark.benchmark.queue;

import org.jctools.queues.MpscArrayQueue;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.CompilerControl.Mode;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author kirito.moe@foxmail.com Date 2018-08-30
 */
public class QueueFactory {

  public enum QueueType {
    ArrayBlockingQueue,
    LinkedBlockingQueue,
    ConcurrentLinkedQueue,
    MpscArrayQueue
  }

  static Queue<Integer> build(QueueType type, int capacity) {
    switch (type) {
      case ArrayBlockingQueue:
        return new ArrayBlockingQueueAdapter<>(capacity);
      case MpscArrayQueue:
        return new MpscArrayQueueAdapter<>(capacity);
      case LinkedBlockingQueue:
        return new LinkedBlockingQueueAdapter<>();
      case ConcurrentLinkedQueue:
        return new ConcurrentLinkedQueueAdapter<>();
      default:
        throw new RuntimeException("unexpected queue type");
    }
  }

  static class ArrayBlockingQueueAdapter<E> extends ArrayBlockingQueue<E> implements Queue<E> {

    private static final long serialVersionUID = -2229036365665373570L;

    public ArrayBlockingQueueAdapter(int capacity) {
      super(capacity);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public boolean offer(E e) {
      return super.offer(e);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public E poll() {
      return super.poll();
    }
  }

  static class LinkedBlockingQueueAdapter<E> extends LinkedBlockingQueue<E> implements Queue<E> {

    private static final long serialVersionUID = 801928570848592532L;

    @Override
    @CompilerControl(Mode.INLINE)
    public boolean offer(E e) {
      return super.offer(e);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public E poll() {
      return super.poll();
    }
  }

  static class ConcurrentLinkedQueueAdapter<E> extends ConcurrentLinkedQueue<E>
      implements Queue<E> {

    private static final long serialVersionUID = 3743730376632205925L;

    @Override
    @CompilerControl(Mode.INLINE)
    public boolean offer(E e) {
      return super.offer(e);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public E poll() {
      return super.poll();
    }
  }

  static class MpscArrayQueueAdapter<E> extends MpscArrayQueue<E> implements Queue<E> {

    public MpscArrayQueueAdapter(int capacity) {
      super(capacity);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public boolean offer(E e) {
      return super.offer(e);
    }

    @Override
    @CompilerControl(Mode.INLINE)
    public E poll() {
      return super.poll();
    }
  }
}
