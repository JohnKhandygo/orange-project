package com.kspt.orange.frameworks.twitter.api.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "place_type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = City.class, name = "city"),
    @JsonSubTypes.Type(value = Country.class, name = "country")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
  private final String id;

  private final String name;

  public Place(final String id, final String name) {
    this.id = id;
    this.name = name;
  }
}

class City extends Place {
  @JsonCreator
  public City(
      final @JsonProperty("id") String id,
      final @JsonProperty("name") String name) {
    super(id, name);
  }
}

class Country extends Place {
  private final String code;

  @JsonCreator
  public Country(
      final @JsonProperty("id") String id,
      final @JsonProperty("name") String name,
      final @JsonProperty("country_code") String code) {
    super(id, name);
    this.code = code;
  }
}
