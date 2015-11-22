package com.kspt.orange.frameworks.twitter.api.data;

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
  private final String id;

  private final String createdAt;

  private final String text;

  private final User user;

  private final Geo geo;

  private final Place place;

  private final Entity entity;

  private final String lang;

  @JsonCreator
  public TwitterData(
      final @JsonProperty("id_str") String id,
      final @JsonProperty("created_at") String createdAt,
      final @JsonProperty("text") String text,
      final @JsonProperty("user") User user,
      final @JsonProperty("geo") Geo geo,
      final @JsonProperty("place") Place place,
      final @JsonProperty("entities") Entity entity,
      final @JsonProperty("lang") String lang) {
    this.id = id;
    this.createdAt = createdAt;
    this.text = text;
    this.user = user;
    this.geo = geo;
    this.place = place;
    this.entity = entity;
    this.lang = lang;
  }

  public String id() {
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

  public Geo geo() {
    return geo;
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
