package com.kspt.orange.oauth;

import com.kspt.orange.frameworks.twitter.api.TwitterDataApi;
import com.kspt.orange.frameworks.twitter.api.data.TwitterDataObject;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import java.util.Map;

public class FeignTest {

  @Test
  public void test() {
    final Token consumerToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token accessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    TwitterDataApi dataApi = Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .requestInterceptor(new MyInterceptor(consumerToken, accessToken))
        .contract(new JAXRSContract())
        .target(TwitterDataApi.class, "https://api.twitter.com");
    final TwitterDataObject data = dataApi.search("twitterapi");

    final int a = 1;
  }
}

class MyInterceptor implements RequestInterceptor {

  private final Token consumerToken;

  private final Token authToken;

  public MyInterceptor(final Token consumerToken, final Token authToken) {
    this.consumerToken = consumerToken;
    this.authToken = authToken;
  }

  @Override
  public void apply(final RequestTemplate template) {
    final String host = "https://api.twitter.com";
    final String url = template.url();
    final String baseUrl = host + url;

    final Verb verb = Verb.valueOf(template.method());
    OAuthRequest request = new OAuthRequest(
        verb,
        baseUrl + template.queryLine());
    OAuthService service = new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey(consumerToken.getToken())
        .apiSecret(consumerToken.getSecret())
        .build();
    service.signRequest(authToken, request);
    final Map<String, String> authHeaders = request.getHeaders();
    authHeaders.entrySet().stream()
        .forEach(e -> template.header(e.getKey(), e.getValue()));

    final int a = 1;
  }
}

