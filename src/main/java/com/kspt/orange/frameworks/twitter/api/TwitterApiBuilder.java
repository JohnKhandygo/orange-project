package com.kspt.orange.frameworks.twitter.api;

import com.kspt.orange.frameworks.AuthenticationCredentials;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import java.util.Map;
import java.util.Objects;

public class TwitterApiBuilder {

  static final String HOST = "https://api.twitter.com";

  public static <T> T build(
      final Class<T> clazz,
      final AuthenticationCredentials credentials) {
    return builder()
        .requestInterceptor(new TwitterRequestInterceptor(credentials))
        .target(clazz, HOST);
  }

  private static Builder builder() {
    return Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new JAXRSContract());
  }
}

class TwitterRequestInterceptor implements RequestInterceptor {

  private final AuthenticationCredentials credentials;

  public TwitterRequestInterceptor(
      final AuthenticationCredentials credentials) {
    this.credentials = credentials;
  }

  @Override
  public void apply(final RequestTemplate template) {
    final OAuthRequest request = buildAuthenticationRequest(template);
    OAuthService service = buildAuthenticationService();
    service.signRequest(credentials.userToken(), request);
    template.header("Authorization", extractAuthenticationHeader(request));
  }

  private String extractAuthenticationHeader(final OAuthRequest request) {
    final Map<String, String> headers = request.getHeaders();
    return Objects.requireNonNull(headers.get("Authorization"));
  }

  private OAuthService buildAuthenticationService() {
    return new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey(credentials.applicationToken().getToken())
        .apiSecret(credentials.applicationToken().getSecret())
        .build();
  }

  private OAuthRequest buildAuthenticationRequest(final RequestTemplate template) {
    final String url = TwitterApiBuilder.HOST + template.url();
    return new OAuthRequest(Verb.valueOf(template.method()), url + template.queryLine());
  }
}
