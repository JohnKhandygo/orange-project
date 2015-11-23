package com.kspt.orange.frameworks.twitter.api.queries;

import com.kspt.orange.core.entities.Query;
import static java.lang.String.format;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class TwitterDataQuery implements Query {
  private final Optional<String> query;

  private final String location;

  private final String result;

  private TwitterDataQuery(
      final Optional<String> query,
      final String location,
      final String result) {
    this.query = query;
    this.location = location;
    this.result = result;
  }

  public static TwitterDataQuery newOne(
      final String query,
      final double latitude,
      final double longitude,
      final double radius,
      final String result) {
    Objects.requireNonNull(query, "Query string cannot be null!");
    final String formattedLocation = formatLocation(latitude, longitude, radius);
    return new TwitterDataQuery(Optional.of(query), formattedLocation, result);
  }

  static String formatLocation(
      final double latitude,
      final double longitude,
      final double radius) {
    return format(Locale.ENGLISH, "%.2f,%.2f,%.2fkm", latitude, longitude, radius);
  }

  public static TwitterDataQuery newOne(
      final double latitude,
      final double longitude,
      final double radius,
      final String result) {
    final String formattedLocation = formatLocation(latitude, longitude, radius);
    return new TwitterDataQuery(Optional.empty(), formattedLocation, result);
  }
}
