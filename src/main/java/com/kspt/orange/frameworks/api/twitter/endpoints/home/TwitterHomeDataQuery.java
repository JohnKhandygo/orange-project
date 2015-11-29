package com.kspt.orange.frameworks.api.twitter.endpoints.home;

import com.kspt.orange.application.entities.QueryWithCursor;

public class TwitterHomeDataQuery extends QueryWithCursor {
  private final boolean trimUser;

  private final boolean excludeReplies;

  private final boolean contributorsDetails;

  private final boolean includeRts;

  public TwitterHomeDataQuery(
      final Integer limit,
      final Long cursor,
      final boolean trimUser,
      final boolean excludeReplies,
      final boolean contributorsDetails,
      final boolean includeRts) {
    super(limit, cursor);
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
