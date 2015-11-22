package com.kspt.orange.frameworks.twitter.api.data;

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
public class TwitterDataObject {
  private final List<TwitterData> statuses;

  private final TwitterMetadata metadata;

  @JsonCreator
  public TwitterDataObject(
      final @JsonProperty("statuses") List<TwitterData> statuses,
      final @JsonProperty("search_metadata") TwitterMetadata metadata) {
    this.statuses = statuses;
    this.metadata = metadata;
  }

  public List<TwitterData> statuses() {
    return statuses;
  }

  public TwitterMetadata metadata() {
    return metadata;
  }
}
