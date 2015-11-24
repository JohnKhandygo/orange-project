package com.kspt.orange.frameworks.api.twitter.endpoints.data;

import com.kspt.orange.application.QueryWithBounds;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterDataObject;

public class TwitterDataSource
    implements Source<QueryWithBounds<TwitterDataQuery>, TwitterData> {

  private final TwitterDataApi api;

  private TwitterDataSource(final TwitterDataApi api) {
    this.api = api;
  }

  @Override
  public TwitterDataObject get(final QueryWithBounds<TwitterDataQuery> boundedQuery) {
    final TwitterDataQuery query = boundedQuery.query();
    return api.search(
        query.query().orElse(null),
        query.geo(),
        boundedQuery.count(),
        boundedQuery.last().orElse(null),
        boundedQuery.first().orElse(null),
        query.result());
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterDataApi api = TwitterApiBuilder.build(TwitterDataApi.class, credentials);
    return new TwitterDataSource(api);
  }
}

