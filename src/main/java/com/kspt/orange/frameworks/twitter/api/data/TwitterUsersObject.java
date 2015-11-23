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
public class TwitterUsersObject extends DataCollection<User> {

  private final long nextCursor;

  @JsonCreator
  public TwitterUsersObject(
      final @JsonProperty("users") List<User> data,
      final @JsonProperty("next_cursor") long nextCursor) {
    super(data);
    this.nextCursor = nextCursor;
  }

  public long nextCursor() {
    return nextCursor;
  }
}
