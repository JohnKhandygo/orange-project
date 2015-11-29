package com.kspt.orange.application.entities;

public class QueryWithCursor extends QueryWithLimit {

  private final Long cursor;

  public QueryWithCursor(final Integer limit, final Long cursor) {
    super(limit);
    this.cursor = cursor;
  }

  public Long cursor() {
    return cursor;
  }
}
