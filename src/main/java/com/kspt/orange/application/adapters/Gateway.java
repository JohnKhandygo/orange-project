package com.kspt.orange.application.adapters;

import com.kspt.orange.application.QueryWithLimit;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import java.util.Collection;
import java.util.Collections;

public class Gateway<Q1 extends Query, Q2 extends Query, D extends Data> {

  private final QueryDelimiter<Q1, Q2> delimiter;

  private final Source<Q2, D> source;

  private final Observable<D> output;

  Gateway(
      final QueryDelimiter<Q1, Q2> delimiter,
      final Source<Q2, D> source,
      final Observable<D> output) {
    this.delimiter = delimiter;
    this.source = source;
    this.output = output;
  }

  public void forward(final QueryWithLimit<Q1> queryWithLimit) {
    final Q2 firstQuery = delimiter.next(queryWithLimit.query(), Collections::emptyList);
    final DataCollection<D> firstPortion = extractAndEmit(firstQuery);
    final Collection<D> firstPortionData = firstPortion.data();
    int remaining =
        firstPortionData.size() == 0 ? 0 : queryWithLimit.limit() - firstPortionData.size();
    while (remaining != 0) {
      final Q2 next = delimiter.next(queryWithLimit.query(), firstPortion);
      final DataCollection<D> nextPortion = extractAndEmit(next);
      remaining = nextPortion.data().size() == 0 ? 0 : remaining - nextPortion.data().size();
    }
  }

  private DataCollection<D> extractAndEmit(final Q2 query) {
    DataCollection<D> retrieved = source.get(query);
    retrieved.data().stream().forEach(output::emit);
    return retrieved;
  }

  public Observable<D> output() {
    return output;
  }
}
