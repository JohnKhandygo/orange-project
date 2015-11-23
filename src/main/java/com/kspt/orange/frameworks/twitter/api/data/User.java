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
public class User implements Data {
  private final long id;

  private final String name;

  private final String screenName;

  @JsonCreator
  public User(
      final @JsonProperty("id") long id,
      final @JsonProperty("name") String name,
      final @JsonProperty("screen_name") String screenName) {
    this.id = id;
    this.name = name;
    this.screenName = screenName;
  }

  public long id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String screenName() {
    return screenName;
  }
}
