package com.kspt.orange.frameworks.twitter.api.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Point.class, name = "Point")})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Geo {
  double latitude();

  double longitude();
}

class Point implements Geo {
  private final double latitude;

  private final double longitude;

  public Point(final double latitude, final double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public double latitude() {
    return latitude;
  }

  @Override
  public double longitude() {
    return longitude;
  }

  @JsonCreator
  public static Geo fromJson(final @JsonProperty("coordinates") List<Double> coordinates) {
    Preconditions.checkState(coordinates.size() == 2);
    return new Point(coordinates.get(0), coordinates.get(1));
  }
}
