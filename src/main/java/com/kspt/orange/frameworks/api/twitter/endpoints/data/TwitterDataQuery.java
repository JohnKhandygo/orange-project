package com.kspt.orange.frameworks.api.twitter.endpoints.data;

import com.kspt.orange.core.entities.Query;
import static java.lang.String.format;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class TwitterDataQuery implements Query {
  private final Optional<String> query;

  private final String geo;

  private final String lang;

  private final String result;

  private TwitterDataQuery(
      final Optional<String> query,
      final String geo,
      final String lang,
      final String result) {
    this.query = query;
    this.geo = geo;
    this.lang = lang;
    this.result = result;
  }

  public Optional<String> query() {
    return query;
  }

  public String geo() {
    return geo;
  }

  public String lang() {
    return lang;
  }

  public String result() {
    return result;
  }

  public static TwitterDataQuery newOne(
      final String query,
      final double latitude,
      final double longitude,
      final double radius,
      final String lang,
      final String result) {
    Objects.requireNonNull(query, "Query string cannot be null!");
    final String formattedLocation = formatLocation(latitude, longitude, radius);
    return new TwitterDataQuery(Optional.of(query), formattedLocation, lang, result);
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
      final String lang,
      final String result) {
    final String formattedLocation = formatLocation(latitude, longitude, radius);
    return new TwitterDataQuery(Optional.empty(), formattedLocation, lang, result);
  }
}
