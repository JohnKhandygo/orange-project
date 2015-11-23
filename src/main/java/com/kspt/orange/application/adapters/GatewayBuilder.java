package com.kspt.orange.application.adapters;

import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import static java.util.Objects.requireNonNull;

public class GatewayBuilder<Q1 extends Query, Q2 extends Query, D extends Data> {

  private QueryDelimiter<Q1, Q2> queryDelimiter;

  private Source<Q2, D> source;

  public GatewayBuilder withQueryIterator(final QueryDelimiter<Q1, Q2> querying) {
    this.queryDelimiter = querying;
    return this;
  }

  public GatewayBuilder onSource(final Source<Q2, D> source) {
    this.source = source;
    return this;
  }

  public Gateway<Q1, Q2, D> build() {
    return GatewayBuilder.newOne(queryDelimiter, source);
  }

  public static <Q1 extends Query, Q2 extends Query, D extends Data> Gateway<Q1, Q2, D> newOne(
      final QueryDelimiter<Q1, Q2> queryDelimiter,
      final Source<Q2, D> source) {
    requireNonNull(queryDelimiter, "Query delimiter cannot be null!");
    requireNonNull(source, "Source strategy cannot be null!");
    return new Gateway<>(queryDelimiter, source, Observable.newOne());
  }
}