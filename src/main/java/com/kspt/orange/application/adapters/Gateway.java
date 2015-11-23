package com.kspt.orange.application.adapters;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.application.LimitedQuery;
import com.kspt.orange.application.ports.CompletionStrategy;
import com.kspt.orange.application.ports.QueryingStrategy;
import com.kspt.orange.application.streams.Observable;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import java.util.Collection;

public class Gateway<Q1 extends Query, Q2 extends Query, D extends Data> {

  private final QueryingStrategy<Q1, BoundedQuery<Q1>> querying;

  private final CompletionStrategy<Q1, Q2> completion;

  private final Source<Q2, D> source;

  private final Observable<D> output;

  public Gateway(
      final QueryingStrategy<Q1, BoundedQuery<Q1>> querying,
      final CompletionStrategy<Q1, Q2> completion,
      final Source<Q2, D> source,
      final Observable<D> output) {
    this.querying = querying;
    this.completion = completion;
    this.source = source;
    this.output = output;
  }

  public void forward(final LimitedQuery<Q1> limitedQuery) {
    final Collection<D> firstPortion = extractAndEmit(limitedQuery.query());
    int remaining = firstPortion.size() == 0 ? 0 : limitedQuery.limit() - firstPortion.size();
    while (remaining != 0) {
      final BoundedQuery<Q1> next = querying.next(limitedQuery.query(), firstPortion);
      final Collection<D> nextPortion = extractAndEmit(limitedQuery.query());
      remaining = nextPortion.size() == 0 ? 0 : remaining - nextPortion.size();
    }
  }

  private Collection<D> extractAndEmit(final Q1 query) {
    Q2 completed = completion.complete(query);
    Collection<D> retrieved = source.get(completed);
    retrieved.stream().forEach(output::emit);
    return retrieved;
  }

  public Observable<D> output() {
    return output;
  }

  public static <Q1 extends Query, Q2 extends Query, D extends Data> Gateway<Q1, Q2, D>
  newOne(
      final CompletionStrategy<Q1, Q2> completion,
      final QueryingStrategy<Q1, BoundedQuery<Q1>> querying,
      final Source<Q2, D> source) {
    return new Gateway<>(querying, completion, source, Observable.newOne());
  }
}
