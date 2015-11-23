package com.kspt.orange.frameworks.twitter.api.queries;

import com.kspt.orange.core.entities.Query;

public class TwitterHomeDataQuery implements Query {
  private final boolean trimUser;

  private final boolean excludeReplies;

  private final boolean contributorsDetails;

  private final boolean includeRts;

  public TwitterHomeDataQuery(
      final boolean trimUser,
      final boolean excludeReplies,
      final boolean contributorsDetails,
      final boolean includeRts) {
    this.trimUser = trimUser;
    this.excludeReplies = excludeReplies;
    this.contributorsDetails = contributorsDetails;
    this.includeRts = includeRts;
  }

  public boolean trimUser() {
    return trimUser;
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
