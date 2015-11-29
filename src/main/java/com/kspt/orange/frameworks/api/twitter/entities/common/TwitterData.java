package com.kspt.orange.frameworks.api.twitter.entities.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.frameworks.api.twitter.sources.TwitterStreamedData;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterData implements Data, TwitterStreamedData {

  private final Coordinates coordinates;

  private final String createdAt;

  private final Integer favoriteCount;

  private final long id;

  private final Place place;

  private final String source;

  private final String text;

  private final TwitterUser user;

  @JsonCreator
  public TwitterData(
      final @JsonProperty("coordinates") Coordinates coordinates,
      final @JsonProperty("created_at") String createdAt,
      final @JsonProperty("favorite_count") Integer favoriteCount,
      final @JsonProperty("id") long id,
      final @JsonProperty("place") Place place,
      final @JsonProperty("source") String source,
      final @JsonProperty("text") String text,
      final @JsonProperty("user") TwitterUser user) {
    this.coordinates = coordinates;
    this.createdAt = createdAt;
    this.favoriteCount = favoriteCount;
    this.id = id;
    this.place = place;
    this.source = source;
    this.text = text;
    this.user = user;
  }

  public Coordinates coordinates() {
    return coordinates;
  }

  public String createdAt() {
    return createdAt;
  }

  public Integer favoriteCount() {
    return favoriteCount;
  }

  public long id() {
    return id;
  }

  public Place place() {
    return place;
  }

  public String source() {
    return source;
  }

  public String text() {
    return text;
  }

  public TwitterUser user() {
    return user;
  }

  @Override
  public String toString() {
    return "TwitterData{" +
        "coordinates=" + coordinates +
        ", createdAt='" + createdAt + '\'' +
        ", favoriteCount=" + favoriteCount +
        ", id=" + id +
        ", place=" + place +
        ", source='" + source + '\'' +
        ", text='" + text + '\'' +
        ", user=" + user +
        '}';
  }
}

