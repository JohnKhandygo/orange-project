package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;
import java.util.OptionalLong;

public class BoundedQuery<Q extends Query> implements Query {

  private final Q query;

  private final OptionalLong first;

  private final OptionalLong last;

  private final int count;

  public BoundedQuery(final Q query, final OptionalLong first, final OptionalLong last,
      final int count) {
    this.query = query;
    this.first = first;
    this.last = last;
    this.count = count;
  }

  public Q query() {
    return query;
  }

  public OptionalLong first() {
    return first;
  }

  public OptionalLong last() {
    return last;
  }

  public int count() {
    return count;
  }
}
