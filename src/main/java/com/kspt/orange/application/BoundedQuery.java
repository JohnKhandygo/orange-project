package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;
import java.util.OptionalLong;

public interface BoundedQuery<Q extends Query> extends Query {

  Q query();

  OptionalLong first();

  OptionalLong last();

  int count();
}
