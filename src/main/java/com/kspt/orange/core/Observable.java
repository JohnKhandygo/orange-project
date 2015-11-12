package com.kspt.orange.core;

public interface Observable<D> {
  default <T> Observable<T> register(final Observer<D, T> observer) {
    final Observable<T> output = observer.subscribeOn(this);
    observer.subscribeOn(this);
    return output;
  }

  void emit(final D element);
}