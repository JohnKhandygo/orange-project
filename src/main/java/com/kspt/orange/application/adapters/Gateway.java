package com.kspt.orange.application.adapters;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.application.LimitedQuery;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import static java.util.Collections.emptyList;
import java.util.Collection;

public class Gateway<Q extends Query, D extends Data> {

  private final QueryDelimiter<Q> delimiter;

  private final Source<BoundedQuery<Q>, D> source;

  private final Observable<D> output;

  Gateway(
      final QueryDelimiter<Q> delimiter,
      final Source<BoundedQuery<Q>, D> source,
      final Observable<D> output) {
    this.delimiter = delimiter;
    this.source = source;
    this.output = output;
  }

  public void forward(final LimitedQuery<Q> limitedQuery) {
    final BoundedQuery<Q> firstQuery = delimiter.next(limitedQuery.query(), emptyList());
    final Collection<D> firstPortion = extractAndEmit(firstQuery);
    int remaining = firstPortion.size() == 0 ? 0 : limitedQuery.limit() - firstPortion.size();
    while (remaining != 0) {
      final BoundedQuery<Q> next = delimiter.next(limitedQuery.query(), firstPortion);
      final Collection<D> nextPortion = extractAndEmit(next);
      remaining = nextPortion.size() == 0 ? 0 : remaining - nextPortion.size();
    }
  }

  private Collection<D> extractAndEmit(final BoundedQuery<Q> query) {
    Collection<D> retrieved = source.get(query);
    retrieved.stream().forEach(output::emit);
    return retrieved;
  }

  public Observable<D> output() {
    return output;
  }
}
