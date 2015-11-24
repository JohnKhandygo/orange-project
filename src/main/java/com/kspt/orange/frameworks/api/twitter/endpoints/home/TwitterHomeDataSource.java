package com.kspt.orange.frameworks.api.twitter.endpoints.home;

import com.kspt.orange.application.entities.bounded.QueryWithBounds;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import java.util.List;

public class TwitterHomeDataSource
    implements Source<QueryWithBounds<TwitterHomeDataQuery>, TwitterData> {

  private final TwitterHomeDataApi api;

  private TwitterHomeDataSource(final TwitterHomeDataApi api) {
    this.api = api;
  }

  @Override
  public DataCollection<TwitterData> get(final QueryWithBounds<TwitterHomeDataQuery> boundedQuery) {
    final TwitterHomeDataQuery query = boundedQuery.query();
    final List<TwitterData> found = api.search(
        boundedQuery.count(),
        boundedQuery.last().orElse(null),
        boundedQuery.first().orElse(null),
        query.trimUser(),
        query.excludeReplies(),
        query.contributorsDetails(),
        query.includeRts());
    return formDataCollection(found);
  }

  private DataCollection<TwitterData> formDataCollection(final List<TwitterData> data) {
    final List<TwitterData> geotaggedData = data.stream().filter(d -> nonNull(d.geo()))
        .collect(toList());
    return () -> geotaggedData;
  }

  public static TwitterHomeDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterHomeDataApi api = TwitterApiBuilder.build(TwitterHomeDataApi.class, credentials);
    return new TwitterHomeDataSource(api);
  }
}
