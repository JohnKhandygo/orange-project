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
public class VkUser {

  private final long id;

  private final String firstName;

  private final String lastName;

  private final String photo50;

  private final String photo100;

  @JsonCreator
  public VkUser(
      final @JsonProperty("id") long id,
      final @JsonProperty("first_name") String firstName,
      final @JsonProperty("last_name") String lastName,
      final @JsonProperty("photo") String photo50,
      final @JsonProperty("photo_medium_rec") String photo100) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.photo50 = photo50;
    this.photo100 = photo100;
  }

  public long id() {
    return id;
  }

  public String firstName() {
    return firstName;
  }

  public String lastName() {
    return lastName;
  }

  public String photo50() {
    return photo50;
  }

  public String photo100() {
    return photo100;
  }
}
