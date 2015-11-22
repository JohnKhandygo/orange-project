package com.kspt.orange.frameworks.twitter;

import com.kspt.orange.application.DataSource;
import com.kspt.orange.frameworks.sources.oauth.AuthenticationCredentials;
import com.kspt.orange.frameworks.twitter.api.ApiBuilder;
import com.kspt.orange.frameworks.twitter.api.TwitterDataApi;
import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import static java.lang.String.format;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class TwitterDataSource implements DataSource<TwitterDataQuery, TwitterData> {

  static final String HOST = "https://api.twitter.com";

  private final TwitterDataApi api;

  private TwitterDataSource(final TwitterDataApi api) {
    this.api = api;
  }

  @Override
  public Collection<TwitterData> get(final TwitterDataQuery query) {
    final String geo = formatGeo(query);
    return query.query().map(q ->
        api.search(q, geo, query.resultType(), query.count(), query.min().get(), query.max().get())
    ).orElse(
        api.search(geo, query.resultType(), query.count(), query.min().get(), query.max().get())
    ).statuses();
  }

  private String formatGeo(final TwitterDataQuery query) {
    return format("%f.2,%f.2,%f.2km", query.latitude(), query.longitude(), query.radius());
  }

  public static TwitterDataSource newOne(final AuthenticationCredentials credentials) {
    final TwitterRequestInterceptor interceptor = new TwitterRequestInterceptor(credentials);
    TwitterDataApi api = ApiBuilder.build(HOST, TwitterDataApi.class, interceptor);
    return new TwitterDataSource(api);
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
