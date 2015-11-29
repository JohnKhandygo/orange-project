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
public class VkGeo {

  private final String placeId;

  private final String title;

  private final String type;

  private final String countryId;

  private final String cityId;

  private final String address;

  public VkGeo(
      final @JsonProperty("place_id") String placeId,
      final @JsonProperty("title") String title,
      final @JsonProperty("type") String type,
      final @JsonProperty("country_id") String countryId,
      final @JsonProperty("city_id") String cityId,
      final @JsonProperty("address") String address) {
    this.placeId = placeId;
    this.title = title;
    this.type = type;
    this.countryId = countryId;
    this.cityId = cityId;
    this.address = address;
  }

  public String placeId() {
    return placeId;
  }

  public String title() {
    return title;
  }

  public String type() {
    return type;
  }

  public String countryId() {
    return countryId;
  }

  public String cityId() {
    return cityId;
  }

  public String address() {
    return address;
  }
}
