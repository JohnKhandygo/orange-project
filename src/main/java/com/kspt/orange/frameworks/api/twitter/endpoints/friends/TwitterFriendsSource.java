package com.kspt.orange.frameworks.api.twitter.endpoints.friends;

import com.kspt.orange.application.QueryWithBounds;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterUsersObject;
import com.kspt.orange.frameworks.api.twitter.entities.User;

public class TwitterFriendsSource implements Source<QueryWithBounds<TwitterFriendsQuery>, User> {

  private final TwitterFriendsApi api;

  private TwitterFriendsSource(final TwitterFriendsApi api) {
    this.api = api;
  }

  @Override
  public TwitterUsersObject get(final QueryWithBounds<TwitterFriendsQuery> boundedQuery) {
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
