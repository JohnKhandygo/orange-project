package com.kspt.orange.frameworks.api.twitter.endpoints.users;

import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.ports.Source;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.User;
import static java.util.Collections.singletonList;

public class TwitterUserInfoSource implements Source<TwitterUserInfoQuery, User> {

  private final TwitterUserInfoApi api;

  private TwitterUserInfoSource(final TwitterUserInfoApi api) {
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

  public static TwitterUserInfoSource newOne(final AuthenticationCredentials credentials) {
    TwitterUserInfoApi api = TwitterApiBuilder.build(TwitterUserInfoApi.class, credentials);
    return new TwitterUserInfoSource(api);
  }
}
