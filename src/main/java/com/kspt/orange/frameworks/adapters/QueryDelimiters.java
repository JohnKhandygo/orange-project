package com.kspt.orange.frameworks.adapters;

import com.kspt.orange.application.QueryWithBounds;
import com.kspt.orange.application.ports.QueryDelimiter;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.frameworks.api.twitter.endpoints.data.TwitterDataQuery;
import com.kspt.orange.frameworks.api.twitter.endpoints.friends.TwitterFriendsQuery;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterDataObject;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterUsersObject;
import static java.util.Comparator.comparingLong;
import java.util.Optional;

public class QueryDelimiters {

  public static <Q extends Query> QueryDelimiter<Q, QueryWithBounds<Q>>
  newDownQueryDelimiter(final int granularity) {
    return (q, dc) -> {
      final Optional<Long> last = dc.data().stream().map(Data::id).min(comparingLong(l -> l));
      return new QueryWithBounds<>(q, Optional.empty(), last, granularity);
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, QueryWithBounds<Q>>
  newUpQueryDelimiter(final int granularity) {
    return (q, dc) -> {
      final Optional<Long> first = dc.data().stream().map(Data::id).max(comparingLong(l -> l));
      return new QueryWithBounds<>(q, first, Optional.empty(), granularity);
    };
  }

  public static QueryDelimiter<TwitterDataQuery, QueryWithBounds<TwitterDataQuery>>
  newDownTwitterDataSourceDelimiter(final int granularity) {
    return (q, dc) -> {
      final TwitterDataObject tdo = (TwitterDataObject) dc;
      final Optional<Long> last = Optional.of(tdo.meta().first());
      return new QueryWithBounds<>(q, Optional.empty(), last, granularity);
    };
  }

  public static QueryDelimiter<TwitterDataQuery, QueryWithBounds<TwitterDataQuery>>
  newUpTwitterDataSourceDelimiter(final int granularity) {
    return (q, dc) -> {
      final TwitterDataObject tdo = (TwitterDataObject) dc;
      final Optional<Long> first = Optional.of(tdo.meta().last());
      return new QueryWithBounds<>(q, first, Optional.empty(), granularity);
    };
  }

  public static QueryDelimiter<TwitterFriendsQuery, QueryWithBounds<TwitterFriendsQuery>>
  newTwitterFriendsSourceDelimiter(final int granularity) {
    return (q, dc) -> {
      final TwitterUsersObject tuo = (TwitterUsersObject) dc;
      final Optional<Long> last = Optional.of(tuo.nextCursor());
      return new QueryWithBounds<>(q, Optional.empty(), last, granularity);
    };
  }

  public static <Q extends Query> QueryDelimiter<Q, Q> newIdentityDelimiter() {
    return (q, dc) -> q;
  }
}
