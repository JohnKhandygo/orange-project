package com.kspt.orange.core.ports;

import com.kspt.orange.core.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;

public interface Gateway<D extends Data, Q extends Query> extends Observable<D> {
  void forward(final Q query);
}
