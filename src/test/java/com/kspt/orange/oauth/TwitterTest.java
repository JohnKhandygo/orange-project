package com.kspt.orange.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kspt.orange.frameworks.twitter.api.data.TwitterDataObject;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TwitterTest {

  @Test
  public void test()
  throws Exception {
    OAuthService service = new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey("NtGAPn05Q74N0sQrAnjvJrU7z")
        .apiSecret("7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV")
        .build();

    Token requestToken = service.getRequestToken();
    String authUrl = service.getAuthorizationUrl(requestToken);

    /*Verifier v = new Verifier("8915314");
    Token accessToken = service.getAccessToken(requestToken, v);*/

    final Token accessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");

    OAuthRequest request = new OAuthRequest(Verb.GET,
        "https://api.twitter.com/1.1/search/tweets.json?q=twitterapi");
    service.signRequest(accessToken, request);
    Response response = request.send();
    final String body = response.getBody();
    final ObjectMapper mapper = new ObjectMapper();
    final TwitterDataObject data = mapper.readValue(body, TwitterDataObject.class);

    final int a = 1;
  }
}
