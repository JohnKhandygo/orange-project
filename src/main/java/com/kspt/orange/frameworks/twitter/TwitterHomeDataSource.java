package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterHomeStatusesApi;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterHomeDataQuery;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import java.util.List;

public class TwitterHomeDataSource
    implements Source<BoundedQuery<TwitterHomeDataQuery>, TwitterData> {

  private final TwitterHomeStatusesApi api;

  private TwitterHomeDataSource(final TwitterHomeStatusesApi api) {
    this.api = api;
  }

  @Override
  public DataCollection<TwitterData> get(final BoundedQuery<TwitterHomeDataQuery> boundedQuery) {
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
    return new DataCollection<>(geotaggedData);
  }

  public static TwitterHomeDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterHomeStatusesApi api = TwitterApiBuilder.build(TwitterHomeStatusesApi.class, credentials);
    return new TwitterHomeDataSource(api);
  }
}
