package com.kspt.orange.core;

public interface Observer<D1, D2> {

  Observable<D2> subscribeOn(final Observable<D1> observable);
}
