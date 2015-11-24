package com.kspt.orange.frameworks.adapters;

import com.kspt.orange.application.entities.bounded.DataCollectionWithBounds;
import com.kspt.orange.application.entities.bounded.QueryWithBounds;
import com.kspt.orange.application.entities.cursored.DataCollectionWithCursor;
import com.kspt.orange.application.entities.cursored.QueryWithCursor;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import static java.util.Comparator.comparingLong;
import java.util.Optional;

public class QueryDelimiters {

  public static <Q extends Query> QueryDelimiter<Q, QueryWithBounds<Q>>
  newDownBoundedQueryDelimiter(final int granularity) {
    return (q, dc) -> {
      final Optional<Long> last;
      if (dc instanceof DataCollectionWithBounds) {
        last = Optional.ofNullable(((DataCollectionWithBounds) dc).max());
      } else {
        last = dc.data().stream().map(Data::id).min(comparingLong(l -> l));
      }
      return new QueryWithBounds<>(q, Optional.empty(), last, granularity);
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, QueryWithBounds<Q>>
  newUpBoundedQueryDelimiter(final int granularity) {
    return (q, dc) -> {
      final Optional<Long> first;
      if (dc instanceof DataCollectionWithBounds) {
        first = Optional.ofNullable(((DataCollectionWithBounds) dc).min());
      } else {
        first = dc.data().stream().map(Data::id).max(comparingLong(l -> l));
      }
      return new QueryWithBounds<>(q, first, Optional.empty(), granularity);
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, QueryWithCursor<Q>>
  newCursorSourceDelimiter(final int granularity) {
    return (q, dc) -> {
      if (dc instanceof DataCollectionWithCursor) {
        return new QueryWithCursor<>(q, granularity, ((DataCollectionWithCursor) dc).cursor());
      } else if (dc.data().isEmpty()) {
        return new QueryWithCursor<>(q, granularity, -1);
      } else {
        throw new RuntimeException();
      }
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, Q> newIdentityDelimiter() {
    return (q, dc) -> q;
  }
}
