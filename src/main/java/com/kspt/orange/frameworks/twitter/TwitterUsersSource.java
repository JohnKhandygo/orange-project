package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.User;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterUsersApi;
import com.kspt.orange.frameworks.twitter.api.queries.TwitterUserInfoQuery;
import static java.util.Collections.singletonList;

public class TwitterUsersSource implements Source<TwitterUserInfoQuery, User> {

  private final TwitterUsersApi api;

  private TwitterUsersSource(final TwitterUsersApi api) {
    this.api = api;
  }

  @Override
  public DataCollection<User> get(final TwitterUserInfoQuery query) {
    final User found = api.search(query.id(), query.includeEntities());
    return formDataCollection(found);
  }

  private DataCollection<User> formDataCollection(final User user) {
    return new DataCollection<>(singletonList(user));
  }

  public static TwitterUsersSource newOne(final AuthenticationCredentials credentials) {
    TwitterUsersApi api = TwitterApiBuilder.build(TwitterUsersApi.class, credentials);
    return new TwitterUsersSource(api);
  }
}
