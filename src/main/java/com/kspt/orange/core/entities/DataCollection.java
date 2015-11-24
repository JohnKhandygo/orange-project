package com.kspt.orange.core.entities;

import java.util.Collection;

@FunctionalInterface
public interface DataCollection<D extends Data> {

  /*private final Collection<D> data;

  public DataCollection(final Collection<D> data) {
    this.data = data;
  }

  public Collection<D> data() {
    return data;
  }*/

  Collection<D> data();
}
