package com.kspt.orange.frameworks.api.twitter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.core.entities.Data;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterData implements Data {
  private final long id;

  private final String createdAt;

  private final String text;

  private final User user;

  private final Coordinates coordinates;

  private final Place place;

  private final Entity entity;

  private final String lang;

  @JsonCreator
  public TwitterData(
      final @JsonProperty("id") long id,
      final @JsonProperty("created_at") String createdAt,
      final @JsonProperty("text") String text,
      final @JsonProperty("user") User user,
      final @JsonProperty("coordinates") Coordinates coordinates,
      final @JsonProperty("place") Place place,
      final @JsonProperty("entities") Entity entity,
      final @JsonProperty("lang") String lang) {
    this.id = id;
    this.createdAt = createdAt;
    this.text = text;
    this.user = user;
    this.coordinates = coordinates;
    this.place = place;
    this.entity = entity;
    this.lang = lang;
  }

  public long id() {
    return id;
  }

  public String origin() {
    return createdAt;
  }

  public String text() {
    return text;
  }

  public User user() {
    return user;
  }

  public Coordinates geo() {
    return coordinates;
  }

  public Place place() {
    return place;
  }

  public Entity entity() {
    return entity;
  }

  public String lang() {
    return lang;
  }
}
