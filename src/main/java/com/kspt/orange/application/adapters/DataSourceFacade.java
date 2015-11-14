package com.kspt.orange.application.adapters;

import com.kspt.orange.application.DataSource;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Gateway;
import com.kspt.orange.core.streams.Observable;

public class DataSourceFacade<Q extends Query, D extends Data> implements Gateway<Q, D> {

  private final DataSource<Q, D> dataSource;

  private final Observable<D> output;

  public DataSourceFacade(
      final DataSource<Q, D> dataSource,
      final Observable<D> output) {
    this.dataSource = dataSource;
    this.output = output;
  }

  @Override
  public void forward(final Q query) {
    dataSource.get(query).stream().forEach(output::emit);
  }

  @Override
  public Observable<D> output() {
    return output;
  }
}
