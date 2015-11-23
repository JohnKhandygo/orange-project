package com.kspt.orange.frameworks.adapters;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import static java.util.Comparator.comparingLong;
import java.util.Optional;

public class QueryDelimiters {

  public static <Q extends Query> QueryDelimiter<Q, BoundedQuery<Q>>
  newIteratingQueryDelimiter(final int granularity) {
    return (q, d) -> {
      final Optional<Long> last = d.stream().map(Data::id).min(comparingLong(l -> l));
      return new BoundedQuery<>(q, Optional.empty(), last, granularity);
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, BoundedQuery<Q>>
  newUpdatingQueryDelimiter(final int granularity) {
    return (q, d) -> {
      final Optional<Long> first = d.stream().map(Data::id).max(comparingLong(l -> l));
      return new BoundedQuery<>(q, first, Optional.empty(), granularity);
    };
  }
}
