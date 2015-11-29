package com.kspt.orange.application.streams;

public interface Handler<T> {
  void onError(final Exception exception);

  void onObserve(final T element);
}
