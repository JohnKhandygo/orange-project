package com.kspt.orange.frameworks.api.twitter.endpoints.home;

import com.kspt.orange.core.Storage;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import java.util.List;

public class TwitterHomeDataStorage
    implements Storage<TwitterHomeDataQuery, TwitterData> {

  private final TwitterHomeDataApi api;

  public TwitterHomeDataStorage(final TwitterHomeDataApi api) {
    this.api = api;
  }

  @Override
  public DataCollection<TwitterData> search(final TwitterHomeDataQuery query) {
    final List<TwitterData> found = api.search(
        query.limit(),
        query.cursor(),
        query.trimUser(),
        query.excludeReplies(),
        query.contributorsDetails(),
        query.includeRts());
    return new DataCollection<>(found);
  }

  public static TwitterHomeDataStorage newOne(final AuthenticationCredentials credentials) {
    TwitterHomeDataApi api = TwitterApiBuilder.build(TwitterHomeDataApi.class, credentials);
    return new TwitterHomeDataStorage(api);
  }
}
