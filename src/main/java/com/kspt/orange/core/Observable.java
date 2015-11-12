package com.kspt.orange.core;

public interface Observable<D> {
  <T> Observable<T> register(final Observer<D, T> observer);
}