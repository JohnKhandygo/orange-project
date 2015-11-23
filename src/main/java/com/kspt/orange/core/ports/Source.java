package com.kspt.orange.core.ports;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.entities.Query;

public interface Source<Q extends Query, D extends Data> {
  DataCollection<D> get(final Q query);
}
