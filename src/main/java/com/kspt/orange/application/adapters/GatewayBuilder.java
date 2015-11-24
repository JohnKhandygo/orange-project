package com.kspt.orange.application.adapters;

import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import static java.util.Objects.requireNonNull;

public class GatewayBuilder<Q1 extends Query, Q2 extends Query, D extends Data> {

  private QueryDelimiter<Q1, Q2> delimiter;

  private Source<Q2, D> source;

  public GatewayBuilder<Q1, Q2, D> withQueryDelimiter(final QueryDelimiter<Q1, Q2> delimiter) {
    this.delimiter = delimiter;
    return this;
  }

  public GatewayBuilder<Q1, Q2, D> onSource(final Source<Q2, D> source) {
    this.source = source;
    return this;
  }

  public Gateway<Q1, Q2, D> build() {
    return GatewayBuilder.newOne(delimiter, source);
  }

  private static <Q1 extends Query, Q2 extends Query, D extends Data> Gateway<Q1, Q2, D> newOne(
      final QueryDelimiter<Q1, Q2> queryDelimiter,
      final Source<Q2, D> source) {
    requireNonNull(queryDelimiter, "Query delimiter cannot be null!");
    requireNonNull(source, "Source strategy cannot be null!");
    return new Gateway<>(queryDelimiter, source, Observable.newOne());
  }
}

