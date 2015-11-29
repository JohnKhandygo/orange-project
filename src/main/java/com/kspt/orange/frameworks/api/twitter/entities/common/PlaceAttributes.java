package com.kspt.orange.frameworks.api.twitter.entities.common;

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
public class PlaceAttributes {

  private final String address;

  private final String url;

  @JsonCreator
  public PlaceAttributes(
      final @JsonProperty("street_address") String address,
      final @JsonProperty("url") String url) {
    this.address = address;
    this.url = url;
  }

  public String address() {
    return address;
  }

  public String url() {
    return url;
  }

  @Override
  public String toString() {
    return "PlaceAttributes{" +
        "address='" + address + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
