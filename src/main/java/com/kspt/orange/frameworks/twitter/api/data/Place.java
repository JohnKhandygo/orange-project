package com.kspt.orange.frameworks.twitter.api.data;

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

  private final String type;

  private final long id;

  private final String name;

  private final String code;

  @JsonCreator
  public Place(
      final @JsonProperty("place_type") String type,
      final @JsonProperty("id") long id,
      final @JsonProperty("name") String name,
      final @JsonProperty("country_code") String code) {
    this.type = type;
    this.id = id;
    this.name = name;
    this.code = code;
  }
}