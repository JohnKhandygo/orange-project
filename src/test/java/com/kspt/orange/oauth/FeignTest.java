package com.kspt.orange.oauth;

import com.google.common.collect.Lists;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.TwitterApiBuilder;
import com.kspt.orange.frameworks.twitter.api.data.User;
import com.kspt.orange.frameworks.twitter.api.endpoints.TwitterUsersApi;
import org.junit.Test;
import org.scribe.model.Token;
import java.util.ArrayList;
import java.util.List;

public class FeignTest {

  @Test
  public void test() {
    final Token applicationToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token userToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    TwitterUsersApi api = TwitterApiBuilder.build(
        TwitterUsersApi.class,
        new AuthenticationCredentials(applicationToken, userToken));
    final ArrayList<String> names = Lists.<String>newArrayList("twitterapi", "twitter");
    final String initial = names.isEmpty() ? "" : names.get(0);
    final String namesAsString = names.stream().skip(1)
        .reduce(initial, (a, e) -> a + "," + e, (s1, s2) -> s1 + "," + s2);
    final List<User> data = api.search(
        "",
        namesAsString,
        false);

    final int a = 1;
  }
}