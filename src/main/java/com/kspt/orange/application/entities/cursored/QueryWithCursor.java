package com.kspt.orange.application.entities.cursored;

import com.kspt.orange.application.entities.QueryWithLimit;
import com.kspt.orange.core.entities.Query;

public class QueryWithCursor<Q extends Query> extends QueryWithLimit<Q> {

  private final long cursor;

  public QueryWithCursor(final Q query, final int limit, final long cursor) {
    super(query, limit);
    this.cursor = cursor;
  }

  public long cursor() {
    return cursor;
  }
}
