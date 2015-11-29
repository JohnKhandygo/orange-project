package com.kspt.orange.frameworks.api.twitter.entities.streamed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.frameworks.api.twitter.sources.TwitterStreamedData;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class FriendsList implements TwitterStreamedData {

  private final List<Long> friends;

  @JsonCreator
  public FriendsList(final @JsonProperty("friends") List<Long> friends) {
    this.friends = friends;
  }

  public List<Long> friends() {
    return friends;
  }
}
