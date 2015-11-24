package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;
import java.util.Optional;

public class QueryWithBounds<Q extends Query> implements Query {

  private final Q query;

  private final Optional<Long> first;

  private final Optional<Long> last;

  private final int count;

  public QueryWithBounds(
      final Q query,
      final Optional<Long> first,
      final Optional<Long> last,
      final int count) {
    this.query = query;
    this.first = first;
    this.last = last;
    this.count = count;
  }

  public Q query() {
    return query;
  }

  public Optional<Long> first() {
    return first;
  }

  public Optional<Long> last() {
    return last;
  }

  public int count() {
    return count;
  }

  public static <Q extends Query> QueryWithBounds<Q> newOne(final Q query, final int count) {
    return new QueryWithBounds<>(query, Optional.empty(), Optional.empty(), count);
  }
}
