package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;

public interface LimitedQuery<Q extends Query> {

  Q query();

  int limit();
}
