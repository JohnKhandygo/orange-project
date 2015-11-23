package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterUserStatusesApi;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterDataByAuthorQuery;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import java.util.Collection;
import java.util.List;

public class TwitterDataByAuthorSource
    implements Source<BoundedQuery<TwitterDataByAuthorQuery>, TwitterData> {

  private final TwitterUserStatusesApi api;

  private TwitterDataByAuthorSource(final TwitterUserStatusesApi api) {
    this.api = api;
  }

  @Override
  public Collection<TwitterData> get(final BoundedQuery<TwitterDataByAuthorQuery> boundedQuery) {
    final TwitterDataByAuthorQuery query = boundedQuery.query();
    final List<TwitterData> data = api.search(
        query.userId(),
        boundedQuery.count(),
        boundedQuery.first().orElse(null),
        boundedQuery.last().orElse(null),
        query.excludeReplies(),
        query.contributorsDetails(),
        query.includeRts());
    return data.stream().filter(d -> nonNull(d.geo())).collect(toList());
  }

  public static TwitterDataByAuthorSource newOne(final AuthenticationCredentials credentials) {
    TwitterUserStatusesApi api = TwitterApiBuilder.build(TwitterUserStatusesApi.class, credentials);
    return new TwitterDataByAuthorSource(api);
  }
}