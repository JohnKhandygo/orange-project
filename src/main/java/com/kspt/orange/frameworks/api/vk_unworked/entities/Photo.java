package com.kspt.orange.frameworks.api.vk_unworked.entities;

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
public class Photo {

  private final long ownerId;

  private final String url130;

  private final String url604;

  @JsonCreator
  public Photo(
      final @JsonProperty("owner_id") long ownerId,
      final @JsonProperty("photo_130") String url130,
      final @JsonProperty("photo_604") String url604) {
    this.ownerId = ownerId;
    this.url130 = url130;
    this.url604 = url604;
  }

  public long ownerId() {
    return ownerId;
  }

  public String url130() {
    return url130;
  }

  public String url604() {
    return url604;
  }
}

