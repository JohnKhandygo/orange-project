package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.entities.location.ByLocation;
import java.util.Optional;

public class TwitterDataQuery implements Query {

  private final Optional<String> query;

  private final double latitude;

  private final double longitude;

  private final double radius;

  private final String resultType;

  private final int count;

  private final Optional<Long> min;

  private final Optional<Long> max;

  private TwitterDataQuery(
      final Optional<String> query,
      final double latitude,
      final double longitude,
      final double radius,
      final String resultType,
      final int count,
      final Optional<Long> min,
      final Optional<Long> max) {
    this.query = query;
    this.latitude = latitude;
    this.longitude = longitude;
    this.radius = radius;
    this.resultType = resultType;
    this.count = count;
    this.min = min;
    this.max = max;
  }

  public Optional<String> query() {
    return query;
  }

  public double latitude() {
    return latitude;
  }

  public double longitude() {
    return longitude;
  }

  public double radius() {
    return radius;
  }

  public String resultType() {
    return resultType;
  }

  public int count() {
    return count;
  }

  public Optional<Long> min() {
    return min;
  }

  public Optional<Long> max() {
    return max;
  }

  public static TwitterDataQuery newOne(final ByLocation byLocation) {
    final Optional<String> query = Optional.empty();
    final Optional<Long> min = Optional.empty();
    final Optional<Long> max = Optional.empty();
    final String defaultResultType = "mixed";
    final int defaultCount = 15;
    return new TwitterDataQuery(
        query,
        byLocation.latitude(),
        byLocation.longitude(),
        byLocation.latitude(),
        defaultResultType,
        defaultCount,
        min,
        max);
  }
}
