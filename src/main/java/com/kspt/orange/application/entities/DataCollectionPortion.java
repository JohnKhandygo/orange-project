package com.kspt.orange.application.entities;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import java.util.List;

public class DataCollectionPortion<D extends Data> extends DataCollection<D> {

  private final QueryWithCursor nextQuery;

  public DataCollectionPortion(
      final List<D> dataList,
      final QueryWithCursor nextQuery) {
    super(dataList);
    this.nextQuery = nextQuery;
  }

  public QueryWithCursor nextQuery() {
    return nextQuery;
  }
}
