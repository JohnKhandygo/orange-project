package com.kspt.orange.core.entities;

import java.util.List;

public class DataCollection<D extends Data> {

  private final List<D> dataList;

  public DataCollection(final List<D> dataList) {
    this.dataList = dataList;
  }

  public List<D> dataList() {
    return dataList;
  }
}
