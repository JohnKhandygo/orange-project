package com.kspt.orange.application.adapters;

import com.kspt.orange.application.Source;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Gateway;
import com.kspt.orange.core.streams.Observable;

public class SourceFacade<Q extends Query, D extends Data> implements Gateway<Q, D> {

  private final Source<Q, D> source;

  private final Observable<D> output;

  public SourceFacade(
      final Source<Q, D> source,
      final Observable<D> output) {
    this.source = source;
    this.output = output;
  }

  @Override
  public void forward(final Q query) {
    source.get(query).stream().forEach(output::emit);
  }

  @Override
  public Observable<D> output() {
    return output;
  }
}