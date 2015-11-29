package com.kspt.orange.frameworks.api.twitter.entities.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

  private final PlaceAttributes attributes;

  private final String country;

  private final String name;

  private final String type;

  @JsonCreator
  public Place(
      final @JsonProperty("attributes") PlaceAttributes attributes,
      final @JsonProperty("country") String country,
      final @JsonProperty("name") String name,
      final @JsonProperty("place_type") String type) {
    this.attributes = attributes;
    this.country = country;
    this.name = name;
    this.type = type;
  }

  public PlaceAttributes attributes() {
    return attributes;
  }

  public String country() {
    return country;
  }

  public String name() {
    return name;
  }

  public String type() {
    return type;
  }

  @Override
  public String toString() {
    return "Place{" +
        "attributes=" + attributes +
        ", country='" + country + '\'' +
        ", name='" + name + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
