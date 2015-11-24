package com.kspt.orange.application.streams;

public interface Handler<T1, T2> {
  void onError(final Exception exception);

  T2 observe(final T1 element);
}
