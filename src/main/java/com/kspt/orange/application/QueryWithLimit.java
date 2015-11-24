package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;

public class QueryWithLimit<Q extends Query> implements Query {

  private final Q query;

  private final int limit;

  public QueryWithLimit(final Q query, final int limit) {
    this.query = query;
    this.limit = limit;
  }

  public Q query() {
    return query;
  }

  public int limit() {
    return limit;
  }
}
