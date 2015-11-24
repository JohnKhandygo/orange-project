package com.kspt.orange.frameworks.api.twitter.endpoints.authored;

import com.kspt.orange.application.QueryWithBounds;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import java.util.List;

public class TwitterUserDataSource
    implements Source<QueryWithBounds<TwitterUserDataQuery>, TwitterData> {

  private final TwitterUsersDataApi api;

  private TwitterUserDataSource(final TwitterUsersDataApi api) {
    this.api = api;
  }

  @Override
  public DataCollection<TwitterData> get(
      final QueryWithBounds<TwitterUserDataQuery> boundedQuery) {
    final TwitterUserDataQuery query = boundedQuery.query();
    final List<TwitterData> found = api.search(
        query.userId(),
        boundedQuery.count(),
        boundedQuery.first().orElse(null),
        boundedQuery.last().orElse(null),
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

  public static TwitterUserDataSource newOne(final AuthenticationCredentials credentials) {
    TwitterUsersDataApi api = TwitterApiBuilder.build(TwitterUsersDataApi.class, credentials);
    return new TwitterUserDataSource(api);
  }
}