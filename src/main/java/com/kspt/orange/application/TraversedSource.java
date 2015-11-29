package com.kspt.orange.application;

import com.kspt.orange.application.entities.DataCollectionPortion;
import com.kspt.orange.application.entities.QueryWithCursor;
import com.kspt.orange.core.Storage;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import java.util.List;

public abstract class TraversedSource<Q extends QueryWithCursor, D1 extends Data, D2 extends Data> {

  private final Storage<Q, D1> storage;

  public TraversedSource(final Storage<Q, D1> storage) {
    this.storage = storage;
  }

  public DataCollectionPortion<D2> get(Q queryWithCursor) {
    final DataCollection<D1> found = storage.search(queryWithCursor);
    return formDataCollection(queryWithCursor, found);
  }

  protected DataCollectionPortion<D2> formDataCollection(
      final Q previousQuery,
      final DataCollection<D1> answerTopreviousQuery) {
    final Q nextPortionQuery = nextPortionQuery(previousQuery, answerTopreviousQuery);
    final List<D2> filtered = filter(answerTopreviousQuery);
    return new DataCollectionPortion<>(filtered, nextPortionQuery);
  }

  protected abstract List<D2> filter(final DataCollection<D1> dataColection);

  protected abstract Q nextPortionQuery(
      final Q previousQuery,
      final DataCollection<D1> answerToPreviousQuery);
}
