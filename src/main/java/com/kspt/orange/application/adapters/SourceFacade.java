package com.kspt.orange.application.adapters;

import com.kspt.orange.application.Source;
import com.kspt.orange.core.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Gateway;

public class SourceFacade<D extends Data, Q extends Query> implements Gateway<D, Q> {

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
    source.filter(query).stream().forEach(output::emit);
  }

  @Override
  public Observable<D> output() {
    return output;
  }
}
