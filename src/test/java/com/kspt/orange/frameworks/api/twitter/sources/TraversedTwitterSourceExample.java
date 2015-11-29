package com.kspt.orange.frameworks.api.twitter.sources;

import com.kspt.orange.application.entities.DataCollectionPortion;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataApi;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataQuery;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataStorage;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import org.scribe.model.Token;
import java.util.List;

public class TraversedTwitterSourceExample {
  public static void main(String[] args) {
    final int iterations = 3;
    TraversedTwitterSource source = newTraversedTwitterSource();
    TwitterHomeDataQuery initialQuery = initialTwitterHomeDataQuery();
    for (int i = 0; i < iterations; ++i) {
      final DataCollectionPortion<TwitterData> retrieved = source.get(initialQuery);
      final List<TwitterData> dataList = retrieved.dataList();
      System.out.println("Found " + dataList.size() + " items.");
      for (TwitterData aDataList : dataList) {
        System.out.println("  " + aDataList);
      }
      initialQuery = (TwitterHomeDataQuery) retrieved.nextQuery();
    }
  }

  private static TraversedTwitterSource newTraversedTwitterSource() {
    final TwitterHomeDataStorage storage = newTwitterHomeDataStorage();
    return new TraversedTwitterSource(storage);
  }

  private static TwitterHomeDataStorage newTwitterHomeDataStorage() {
    final TwitterHomeDataApi api = newTwitterHomeDataApi();
    return new TwitterHomeDataStorage(api);
  }

  private static TwitterHomeDataApi newTwitterHomeDataApi() {
    return TwitterApiBuilder.build(TwitterHomeDataApi.class, getCredentials());
  }

  public static AuthenticationCredentials getCredentials() {
    final Token consumerToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token accessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    return new AuthenticationCredentials(consumerToken, accessToken);
  }

  private static TwitterHomeDataQuery initialTwitterHomeDataQuery() {
    return new TwitterHomeDataQuery(1, null, true, true, false, false);
  }
}