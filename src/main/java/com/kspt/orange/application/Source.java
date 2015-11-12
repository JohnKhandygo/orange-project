package com.kspt.orange.application;

import com.kspt.orange.core.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;

public interface Source<Q extends Query, D extends Data> extends Observable<D> {
  void filter(final Q query);
}
