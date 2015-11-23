package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterDataApi;
import static java.lang.String.format;
import java.util.Collection;

public class TwitterDataSource
    implements Source<BoundedQuery<TwitterDataQuery>, TwitterData> {

  private final TwitterDataApi api;

  private TwitterDataSource(final TwitterDataApi api) {
    this.api = api;
  }

  @Override
  public Collection<TwitterData> get(final BoundedQuery<TwitterDataQuery> query) {
    return null;
  }

  private String formatGeo(final TwitterDataQuery query) {
    return format("%f.2,%f.2,%f.2km", query.latitude(), query.longitude(), query.radius());
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterDataApi api = TwitterApiBuilder.build(TwitterDataApi.class, credentials);
    return new TwitterDataSource(api);
  }
}

