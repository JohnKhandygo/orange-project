package com.kspt.orange.core.ports;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.streams.Observable;

public interface Gateway<Q extends Query, D extends Data> {
  void forward(final Q query);

  Observable<D> output();
}
