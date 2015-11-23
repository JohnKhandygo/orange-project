package com.kspt.orange.frameworks.adapters.strategies;

import com.kspt.orange.application.ports.CompletionStrategy;
import com.kspt.orange.core.entities.queries.ByLocation;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterDataQuery;

public class Completions {

  public static CompletionStrategy<ByLocation, TwitterDataQuery>
  newTwitterDataQueryCompletionStrategy(final String result) {
    return baseQuery -> TwitterDataQuery
        .newOne(baseQuery.latitude(), baseQuery.longitude(), baseQuery.radius(), result);
  }

  public static CompletionStrategy<ByLocation, TwitterDataQuery>
  newTwitterDataQueryCompletionStrategy() {
    return baseQuery -> TwitterDataQuery
        .newOne(baseQuery.latitude(), baseQuery.longitude(), baseQuery.radius(), "mixed");
  }
}