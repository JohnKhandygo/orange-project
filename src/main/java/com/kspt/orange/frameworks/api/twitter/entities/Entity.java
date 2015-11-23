package com.kspt.orange.frameworks.api.twitter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {
  private final List<Url> urls;

  @JsonCreator
  Entity(final @JsonProperty("urls") List<Url> urls) {
    this.urls = urls;
  }
}

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
class Url {
  private final String url;

  private final String expanded;

  private final String display;

  @JsonCreator
  public Url(
      final @JsonProperty("url") String url,
      final @JsonProperty("expanded_url") String expanded,
      final @JsonProperty("display_url") String display) {
    this.url = url;
    this.expanded = expanded;
    this.display = display;
  }
}
