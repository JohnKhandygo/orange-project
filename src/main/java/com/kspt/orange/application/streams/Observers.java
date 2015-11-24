package com.kspt.orange.application.streams;

import static com.google.common.collect.Queues.newLinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Observers {
  public static <T1, T2> Observer<T1, T2> newObserverWithSingleExecutingThread(
      final Handler<T1, T2> handler,
      final long period,
      final long timeout) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    return newObserver(handler, executor, period, timeout);
  }

  private static <T1, T2> Observer<T1, T2> newObserver(
      final Handler<T1, T2> handler,
      final ExecutorService executor,
      final long period,
      final long timeout) {
    final BlockingQueue<T1> input = newLinkedBlockingQueue();
    final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    final Observable<T2> output = Observable.newOne();
    return new Observer<>(handler, input, schedule, period, timeout, executor, output);
  }

  public static <T1, T2> Observer<T1, T2> newObserverWithFixedExecutorsPool(
      final Handler<T1, T2> handler,
      final int threads,
      final long period,
      final long timeout) {
    final ExecutorService executor = Executors.newFixedThreadPool(threads);
    return newObserver(handler, executor, period, timeout);
  }

  public static <T1, T2> Observer<T1, T2> newObserverWithCachedExecutorsPool(
      final Handler<T1, T2> handler,
      final long period,
      final long timeout) {
    final ExecutorService executor = Executors.newCachedThreadPool();
    return newObserver(handler, executor, period, timeout);
  }
}
