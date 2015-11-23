package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.BoundedQuery;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.TwitterUsersObject;
import com.kspt.orange.frameworks.twitter.api.data.User;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterFriendsApi;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterFriendsQuery;

public class TwitterFriendsSource implements Source<BoundedQuery<TwitterFriendsQuery>, User> {

  private final TwitterFriendsApi api;

  private TwitterFriendsSource(final TwitterFriendsApi api) {
    this.api = api;
  }

  @Override
  public TwitterUsersObject get(final BoundedQuery<TwitterFriendsQuery> boundedQuery) {
    final TwitterFriendsQuery query = boundedQuery.query();
    return api.search(
        query.id(),
        boundedQuery.count(),
        boundedQuery.last().orElse(-1L),
        query.skipStatus(),
        query.includeUserEntities());
  }

  public static TwitterFriendsSource newOne(final AuthenticationCredentials credentials) {
    TwitterFriendsApi api = TwitterApiBuilder.build(TwitterFriendsApi.class, credentials);
    return new TwitterFriendsSource(api);
  }
}
