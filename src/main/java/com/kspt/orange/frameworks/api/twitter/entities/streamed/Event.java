package com.kspt.orange.frameworks.api.twitter.entities.streamed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.frameworks.api.twitter.sources.TwitterStreamedData;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class Event implements TwitterStreamedData {

  private final String name;

  private final String createdAt;

  private final String target;

  private final String source;

  private final String tarobject;

  @JsonCreator
  public Event(
      final @JsonProperty("event") String name,
      final @JsonProperty("created_at") String createdAt,
      final @JsonProperty("target") String target,
      final @JsonProperty("source") String source,
      final @JsonProperty("tar_object") String targetObject) {
    this.name = name;
    this.createdAt = createdAt;
    this.target = target;
    this.source = source;
    this.tarobject = targetObject;
  }

  public String name() {
    return name;
  }

  public String createdAt() {
    return createdAt;
  }

  public String target() {
    return target;
  }

  public String source() {
    return source;
  }

  public String tarobject() {
    return tarobject;
  }

  @Override
  public String toString() {
    return "Event{" +
        "name='" + name + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", target='" + target + '\'' +
        ", source='" + source + '\'' +
        ", tarobject='" + tarobject + '\'' +
        '}';
  }
}
