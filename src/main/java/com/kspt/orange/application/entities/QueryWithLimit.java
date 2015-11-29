package com.kspt.orange.application.entities;

import com.kspt.orange.core.entities.Query;

public class QueryWithLimit implements Query {

  private final Integer limit;

  public QueryWithLimit(final Integer limit) {
    this.limit = limit;
  }

  public Integer limit() {
    return limit;
  }
}
