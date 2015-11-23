package com.kspt.orange.frameworks.twitter.api.queries;

import com.kspt.orange.core.entities.Query;

public class TwitterFriendsQuery implements Query {
  private final long id;

  private final boolean skipStatus;

  private final boolean includeUserEntities;

  public TwitterFriendsQuery(
      final long id,
      final boolean skipStatus,
      final boolean includeUserEntities) {
    this.id = id;
    this.skipStatus = skipStatus;
    this.includeUserEntities = includeUserEntities;
  }

  public long id() {
    return id;
  }

  public boolean skipStatus() {
    return skipStatus;
  }

  public boolean includeUserEntities() {
    return includeUserEntities;
  }
}
