package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterDataApi;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterDataQuery;
import java.util.Collection;

public class TwitterDataSource
    implements Source<BoundedQuery<TwitterDataQuery>, TwitterData> {

  private final TwitterDataApi api;

  private TwitterDataSource(final TwitterDataApi api) {
    this.api = api;
  }

  @Override
  public Collection<TwitterData> get(final BoundedQuery<TwitterDataQuery> boundedQuery) {
    final TwitterDataQuery query = boundedQuery.query();
    return api.search(
        query.query().orElse(null),
        query.geo(),
        boundedQuery.count(),
        boundedQuery.last().orElse(null),
        boundedQuery.first().orElse(null),
        query.result()).dataCollection();
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterDataApi api = TwitterApiBuilder.build(TwitterDataApi.class, credentials);
    return new TwitterDataSource(api);
  }
}

