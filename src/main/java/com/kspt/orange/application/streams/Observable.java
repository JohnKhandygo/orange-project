package com.kspt.orange.application.streams;

import java.util.Collection;

public class Observable<D> {

  private final Collection<Observer<D, ?>> registered;

  protected Observable(final Collection<Observer<D, ?>> registered) {
    this.registered = registered;
  }

  public <T> Observable<T> register(final Observer<D, T> observer) {
    registered.add(observer);
    observer.start();
    return observer.output();
  }

  public void emit(final D element) {
    registered.stream().forEach(observer -> observer.input.add(element));
  }
}