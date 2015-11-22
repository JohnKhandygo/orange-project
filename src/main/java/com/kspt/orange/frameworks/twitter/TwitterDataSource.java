package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.Source;
import com.kspt.orange.frameworks.sources.oauth.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterDataApi;
import static java.lang.String.format;
import java.util.Collection;

public class TwitterDataSource implements Source<TwitterDataQuery, TwitterData> {

  private final TwitterDataApi api;

  private TwitterDataSource(final TwitterDataApi api) {
    this.api = api;
  }

  @Override
  public Collection<TwitterData> get(final TwitterDataQuery query) {
    final String geo = formatGeo(query);
    return query.query().map(q ->
        api.search(q, geo, query.resultType(), query.count(), query.min().get(), query.max().get())
    ).orElse(
        api.search(geo, query.resultType(), query.count(), query.min().get(), query.max().get())
    ).statuses();
  }

  private String formatGeo(final TwitterDataQuery query) {
    return format("%f.2,%f.2,%f.2km", query.latitude(), query.longitude(), query.radius());
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterDataApi api = TwitterApiBuilder.build(TwitterDataApi.class, credentials);
    return new TwitterDataSource(api);
  }
}

