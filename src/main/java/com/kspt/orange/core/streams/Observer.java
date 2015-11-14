package com.kspt.orange.core.streams;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

interface Handler<T1, T2> {
  abstract void onError(final Exception exception);

  abstract T2 observe(final T1 element);
}

public class Observer<T1, T2> {

  final BlockingQueue<T1> input;

  private final Handler<T1, T2> handler;

  private final ScheduledExecutorService schedule;

  private final long period;

  private final long timeout;

  private final ExecutorService executor;

  private final Observable<T2> output;

  public Observer(
      final Handler<T1, T2> handler,
      final BlockingQueue<T1> input,
      final ScheduledExecutorService schedule,
      final long period,
      final long timeout,
      final ExecutorService executor,
      final Observable<T2> output) {
    this.handler = handler;
    this.input = input;
    this.schedule = schedule;
    this.period = period;
    this.timeout = timeout;
    this.executor = executor;
    this.output = output;
  }

  public Observable<T2> subscribeOn(final Observable<T1> observable) {
    observable.register(this);
    return output;
  }

  public Observable<T2> output() {
    return output;
  }

  void start() {
    schedule.scheduleAtFixedRate(() -> {
      try {
        final T1 polled = input.poll(timeout, MILLISECONDS);
        if (nonNull(polled)) {
          executor.submit((Runnable) () -> tryObserveAndEmit(polled));
        }
      } catch (Exception any) {
        handler.onError(any);
      }
    }, 0, period, MILLISECONDS);
  }

  private void tryObserveAndEmit(final T1 element) {
    try {
      final T2 observed = handler.observe(element);
      output.emit(observed);
    } catch (Exception any) {
      handler.onError(any);
    }
  }
}