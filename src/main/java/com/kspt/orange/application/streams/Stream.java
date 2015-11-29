package com.kspt.orange.application.streams;

import com.google.common.base.Preconditions;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Stream<E> {

  private final BlockingQueue<E> queue;

  private final Collection<Consumer<E>> registered;

  private final ScheduledExecutorService schedule;

  private final AtomicBoolean blocked;

  public Stream(
      final BlockingQueue<E> queue,
      final Collection<Consumer<E>> registered,
      final ScheduledExecutorService schedule,
      final AtomicBoolean blocked) {
    this.queue = queue;
    this.registered = registered;
    this.schedule = schedule;
    this.blocked = blocked;
  }

  public void register(final Consumer<E> consumer) {
    registered.add(consumer);
  }

  public void feed(final E element) {
    if (blocked.compareAndSet(false, false)) {
      if (queue.remainingCapacity() == 0) {
        queue.poll();
      }
      final boolean successfullyOffered = queue.offer(element);
      Preconditions.checkState(successfullyOffered);
    }
  }

  public void start(
      final long delayMs,
      final long consumingRateMs,
      final long pollingTimeoutMs) {
    blocked.compareAndSet(true, false);
    schedule.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        final E polled;
        try {
          polled = queue.poll(pollingTimeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
          return;
        }
        if (polled != null) {
          for (final Consumer<E> consumer : registered) {
            consumer.consume(polled);
          }
        }
      }
    }, delayMs, consumingRateMs, MILLISECONDS);
  }

  public void terminate() {
    blocked.compareAndSet(true, false);
    schedule.shutdown();
    for (final Consumer<E> c : registered) {
      c.terminate();
    }
  }
}