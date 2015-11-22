package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.DataSource;
import com.kspt.orange.core.entities.location.Location;
import com.kspt.orange.core.entities.location.LocationQuery;
import com.kspt.orange.frameworks.sources.oauth.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.ApiBuilder;
import com.kspt.orange.frameworks.twitter.api.TwitterDataApi;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class TwitterDataSource implements DataSource<LocationQuery, Location> {

  static final String HOST = "https://api.twitter.com";

  private final TwitterDataApi api;

  private final int granularity;

  private TwitterDataSource(final TwitterDataApi api, final int granularity) {
    this.api = api;
    this.granularity = granularity;
  }

  @Override
  public Collection<Location> get(final LocationQuery query) {
    return null;
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    final TwitterRequestInterceptor interceptor = new TwitterRequestInterceptor(credentials);
    TwitterDataApi api = ApiBuilder.build(HOST, TwitterDataApi.class, interceptor);
    return new TwitterDataSource(api, 10);
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
    final String url = TwitterDataSource.HOST + template.url();
    return new OAuthRequest(Verb.valueOf(template.method()), url + template.queryLine());
  }
}
