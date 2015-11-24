package com.kspt.orange.frameworks.api.twitter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.application.DataCollectionWithCursor;
import java.util.Collection;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUsersObject implements DataCollectionWithCursor<User> {

  private final List<User> users;

  private final long nextCursor;

  @JsonCreator
  public TwitterUsersObject(
      final @JsonProperty("users") List<User> users,
      final @JsonProperty("next_cursor") long nextCursor) {
    this.users = users;
    this.nextCursor = nextCursor;
  }

  @Override
  public long cursor() {
    return nextCursor;
  }

  @Override
  public Collection<User> data() {
    return users;
  }
}
