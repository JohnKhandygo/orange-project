package com.kspt.orange.application.streams;

import java.util.concurrent.ExecutorService;

public class Consumer<T> {

  private final Handler<T> handler;

  private final ExecutorService executor;

  public Consumer(
      final Handler<T> handler,
      final ExecutorService executor) {
    this.handler = handler;
    this.executor = executor;
  }

  void consume(final T element) {
    executor.submit(new Runnable() {
      @Override
      public void run() {
        try {
          handler.onObserve(element);
        } catch (Exception any) {
          handler.onError(any);
        }
      }
    });
  }

  void terminate() {
    executor.shutdown();
  }
}