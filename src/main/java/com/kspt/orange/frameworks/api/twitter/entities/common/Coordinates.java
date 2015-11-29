package com.kspt.orange.frameworks.api.twitter.entities.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
  private final Double latitude;

  private final Double longitude;

  public Coordinates(final Double latitude, final Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Double latitude() {
    return latitude;
  }

  public Double longitude() {
    return longitude;
  }

  @JsonCreator
  public static Coordinates fromJson(final @JsonProperty("coordinates") List<Double> coordinates) {
    Preconditions.checkState(coordinates.size() == 2);
    return new Coordinates(coordinates.get(0), coordinates.get(1));
  }
}
