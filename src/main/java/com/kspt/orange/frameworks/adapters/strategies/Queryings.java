package com.kspt.orange.frameworks.adapters.strategies;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.application.ports.QueryingStrategy;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.queries.ByLocation;
import java.util.OptionalLong;

public class Queryings {

  public static QueryingStrategy<ByLocation, BoundedQuery<ByLocation>>
  newGranularBoundingQueryingStrategy(final int granularity) {
    return (q, d) -> {
      final OptionalLong last = d.stream().mapToLong(Data::id).min();
      return new BoundedQuery<>(q, OptionalLong.empty(), last, granularity);
    };
  }

  public static QueryingStrategy<ByLocation, BoundedQuery<ByLocation>>
  newUpdatingBoundingQueryingStrategy(final int granularity) {
    return (q, d) -> {
      final OptionalLong first = d.stream().mapToLong(Data::id).max();
      return new BoundedQuery<>(q, first, OptionalLong.empty(), granularity);
    };
  }
}
