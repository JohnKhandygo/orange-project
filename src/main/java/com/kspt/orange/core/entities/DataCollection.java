package com.kspt.orange.core.entities;

import java.util.Collection;

public class DataCollection<D extends Data> {

  private final Collection<D> data;

  public DataCollection(final Collection<D> data) {
    this.data = data;
  }

  public Collection<D> data() {
    return data;
  }
}
