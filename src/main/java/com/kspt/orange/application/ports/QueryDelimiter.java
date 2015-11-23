package com.kspt.orange.application.ports;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import java.util.Collection;

@FunctionalInterface
public interface QueryDelimiter<Q extends Query> {

  BoundedQuery<Q> next(final Q query, final Collection<? extends Data> data);
}
