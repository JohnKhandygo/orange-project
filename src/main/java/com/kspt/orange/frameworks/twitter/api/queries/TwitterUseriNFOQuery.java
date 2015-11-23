package com.kspt.orange.frameworks.twitter.api.queries;

import com.kspt.orange.core.entities.Query;

public class TwitterUserInfoQuery implements Query {

  private final long id;

  private final boolean includeEntities;

  public TwitterUserInfoQuery(final long id, final boolean includeEntities) {
    this.id = id;
    this.includeEntities = includeEntities;
  }

  public long id() {
    return id;
  }

  public boolean includeEntities() {
    return includeEntities;
  }
}
