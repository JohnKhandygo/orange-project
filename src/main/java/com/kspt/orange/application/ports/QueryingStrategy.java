package com.kspt.orange.application.ports;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import java.util.Collection;

public interface QueryingStrategy<Q1 extends Query, Q2 extends Query> {

  Q2 next(final Q1 query, final Collection<? extends Data> data);
}
