package com.kspt.orange.frameworks.api.vk_unworked.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
public class VkNewsItem {

  private final String type;

  private final long source;

  private final String text;

  private final List<Photo> attachments;

  private final VkGeo geo;

  public VkNewsItem(
      final @JsonProperty("type") String type,
      final @JsonProperty("source_id") long source,
      final @JsonProperty("text") String text,
      final @JsonProperty("attachments") List<Photo> attachments,
      final @JsonProperty("geo") VkGeo geo) {
    this.type = type;
    this.source = source;
    this.text = text;
    this.attachments = attachments;
    this.geo = geo;
  }

  public String type() {
    return type;
  }

  public long source() {
    return source;
  }

  public String text() {
    return text;
  }

  public List<Photo> attachments() {
    return attachments;
  }

  public VkGeo geo() {
    return geo;
  }
}
