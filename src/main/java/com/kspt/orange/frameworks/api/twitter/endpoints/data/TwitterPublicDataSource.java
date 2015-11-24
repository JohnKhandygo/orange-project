package com.kspt.orange.frameworks.api.twitter.endpoints.data;

import com.kspt.orange.application.entities.bounded.QueryWithBounds;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterDataObject;

public class TwitterPublicDataSource
    implements Source<QueryWithBounds<TwitterPublicDataQuery>, TwitterData> {

  private final TwitterPublicDataApi api;

  private TwitterPublicDataSource(final TwitterPublicDataApi api) {
    this.api = api;
  }

  @Override
  public TwitterDataObject get(final QueryWithBounds<TwitterPublicDataQuery> boundedQuery) {
    final TwitterPublicDataQuery query = boundedQuery.query();
    return api.search(
        query.query().orElse(null),
        query.geo(),
        boundedQuery.count(),
        boundedQuery.last().orElse(null),
        boundedQuery.first().orElse(null),
        query.lang(),
        query.result());
  }

  public static TwitterPublicDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterPublicDataApi api = TwitterApiBuilder.build(TwitterPublicDataApi.class, credentials);
    return new TwitterPublicDataSource(api);
  }
}

