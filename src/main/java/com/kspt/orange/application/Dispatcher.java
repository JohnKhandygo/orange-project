package com.kspt.orange.application;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;

interface QueryingStrategy<Q extends Query> {

  int granularity();

  Q next(final Q query);
}

public class Dispatcher<Q extends Query, D extends Data> {

  private Source<Q, D> source;

  void dispatch(final Q query) {

  }
}
