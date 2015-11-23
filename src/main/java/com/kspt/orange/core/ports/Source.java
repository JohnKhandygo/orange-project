package com.kspt.orange.core.ports;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import java.util.Collection;

public interface Source<Q extends Query, D extends Data> {
  Collection<D> get(final Q query);
}
