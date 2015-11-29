package com.kspt.orange.core;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.entities.Query;

public interface Storage<Q extends Query, D extends Data> {
  DataCollection<D> search(final Q query);
}
