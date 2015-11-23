package com.kspt.orange.application;

import com.kspt.orange.core.entities.Query;
import java.util.Optional;

public class BoundedQuery<Q extends Query> implements Query {

  private final Q query;

  private final Optional<Long> first;

  private final Optional<Long> last;

  private final int count;

  public BoundedQuery(
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

  public static <Q extends Query> BoundedQuery<Q> newOne(final Q query, final int count) {
    return new BoundedQuery<>(query, Optional.empty(), Optional.empty(), count);
  }
}
