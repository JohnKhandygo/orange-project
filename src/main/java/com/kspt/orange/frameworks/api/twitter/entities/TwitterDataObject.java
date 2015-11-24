package com.kspt.orange.frameworks.api.twitter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.application.DataCollectionWithBounds;
import java.util.Collection;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterDataObject implements DataCollectionWithBounds<TwitterData> {

  private final List<TwitterData> data;

  private final TwitterMeta meta;

  @JsonCreator
  public TwitterDataObject(
      final @JsonProperty("statuses") List<TwitterData> data,
      final @JsonProperty("search_metadata") TwitterMeta meta) {
    this.data = data;
    this.meta = meta;
  }

  @Override
  public long min() {
    return meta.last();
  }

  @Override
  public long max() {
    return meta.first();
  }

  @Override
  public Collection<TwitterData> data() {
    return data;
  }
}
