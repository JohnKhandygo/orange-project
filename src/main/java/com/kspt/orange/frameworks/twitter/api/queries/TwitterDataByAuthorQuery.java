package com.kspt.orange.frameworks.twitter.api.queries;

import com.kspt.orange.core.entities.Query;

public class TwitterDataByAuthorQuery implements Query {
  private final long userId;

  private final boolean excludeReplies;

  private final boolean contributorsDetails;

  private final boolean includeRts;

  public TwitterDataByAuthorQuery(
      final long userId,
      final boolean excludeReplies,
      final boolean contributorsDetails,
      final boolean includeRts) {
    this.userId = userId;
    this.excludeReplies = excludeReplies;
    this.contributorsDetails = contributorsDetails;
    this.includeRts = includeRts;
  }

  public long userId() {
    return userId;
  }

  public boolean excludeReplies() {
    return excludeReplies;
  }

  public boolean contributorsDetails() {
    return contributorsDetails;
  }

  public boolean includeRts() {
    return includeRts;
  }
}
