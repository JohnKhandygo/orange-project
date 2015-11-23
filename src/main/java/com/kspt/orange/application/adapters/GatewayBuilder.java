package com.kspt.orange.application.adapters;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import static java.util.Objects.requireNonNull;

public class GatewayBuilder<Q extends Query, D extends Data> {

  private QueryDelimiter<Q> queryDelimiter;

  private Source<BoundedQuery<Q>, D> source;

  public GatewayBuilder withQueryIterator(final QueryDelimiter<Q> querying) {
    this.queryDelimiter = querying;
    return this;
  }

  public GatewayBuilder onSource(final Source<BoundedQuery<Q>, D> source) {
    this.source = source;
    return this;
  }

  public Gateway<Q, D> build() {
    return GatewayBuilder.newOne(queryDelimiter, source);
  }

  public static <Q extends Query, D extends Data> Gateway<Q, D> newOne(
      final QueryDelimiter<Q> queryDelimiter,
      final Source<BoundedQuery<Q>, D> source) {
    requireNonNull(queryDelimiter, "Query delimiter cannot be null!");
    requireNonNull(source, "Source strategy cannot be null!");
    return new Gateway<>(queryDelimiter, source, Observable.newOne());
  }
}