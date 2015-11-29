/*
package com.kspt.orange.oauth;

import com.google.common.collect.Lists;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.TwitterApiBuilder;
import com.kspt.orange.frameworks.api.twitter.endpoints.data.TwitterPublicDataApi;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterDataObject;
import static java.lang.String.format;
import org.junit.Test;
import org.scribe.model.Token;
import java.util.ArrayList;
import java.util.Locale;

public class FeignTest {

  @Test
  public void test2() {
    final Locale ruLocale = Locale.forLanguageTag("RU");
    final Locale enLocale = Locale.forLanguageTag("US");
    final String ruFormat = format(ruLocale, "%.2f,%.2f,%.2f", 1.54, 1.1, 2.222222222);
    final String enFormat = format(enLocale, "%.2f,%.2f,%.2f", 1.54, 1.1, 2.222222222);
    System.out.println(ruLocale + ": " + ruFormat);
    System.out.println(enLocale + ": " + enFormat);
  }

  @Test
  public void test() {
    final Token applicationToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token userToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    TwitterPublicDataApi api = TwitterApiBuilder.build(
        TwitterPublicDataApi.class,
        new AuthenticationCredentials(applicationToken, userToken));
    final ArrayList<String> names = Lists.<String>newArrayList("twitterapi", "twitter");
    final String initial = names.isEmpty() ? "" : names.get(0);
    final TwitterDataObject search = api.search("twitt", "", 1, null, null, "en", "mixed");

    final int a = 1;
  }
}*/
