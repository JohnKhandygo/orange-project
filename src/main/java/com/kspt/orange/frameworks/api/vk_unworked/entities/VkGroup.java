package com.kspt.orange.frameworks.api.vk_unworked.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkGroup {

  private final long id;

  private final String name;

  private final String photo50;

  private final String photo100;

  public VkGroup(
      final @JsonProperty("id") long id,
      final @JsonProperty("name") String name,
      final @JsonProperty("photo_50") String photo50,
      final @JsonProperty("photo_100") String photo100) {
    this.id = id;
    this.name = name;
    this.photo50 = photo50;
    this.photo100 = photo100;
  }
}
