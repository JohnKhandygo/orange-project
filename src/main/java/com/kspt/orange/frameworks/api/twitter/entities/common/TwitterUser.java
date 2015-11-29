package com.kspt.orange.frameworks.api.twitter.entities.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.core.entities.Data;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TwitterUser implements Data {
  private final long id;

  private final String name;

  private final String url;

  @JsonCreator
  public TwitterUser(
      final @JsonProperty("id") long id,
      final @JsonProperty("name") String name,
      final @JsonProperty("url") String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }

  public long id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String url() {
    return url;
  }

  @Override
  public String toString() {
    return "TwitterUser{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
