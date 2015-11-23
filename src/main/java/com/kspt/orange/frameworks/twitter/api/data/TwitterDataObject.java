package com.kspt.orange.frameworks.twitter.api.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.core.entities.DataCollection;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterDataObject extends DataCollection<TwitterData> {

  private final TwitterMeta meta;

  @JsonCreator
  public TwitterDataObject(
      final @JsonProperty("statuses") List<TwitterData> data,
      final @JsonProperty("search_metadata") TwitterMeta meta) {
    super(data);
    this.meta = meta;
  }

  public TwitterMeta meta() {
    return meta;
  }
}
