package com.kspt.orange.frameworks.api.vk_unworked;

import com.kspt.orange.frameworks.AuthenticationCredentials;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;

public class VkApiBuilder {

  static final String HOST = "https://api.vk.com";

  public static <T> T build(
      final Class<T> clazz,
      final AuthenticationCredentials credentials) {
    return builder()
        .requestInterceptor(new VkRequestInterceptor(credentials))
        .target(clazz, HOST);
  }

  private static Builder builder() {
    return Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new JAXRSContract());
  }
}

class VkRequestInterceptor implements RequestInterceptor {

  private final AuthenticationCredentials credentials;

  public VkRequestInterceptor(
      final AuthenticationCredentials credentials) {
    this.credentials = credentials;
  }

  @Override
  public void apply(final RequestTemplate template) {
    template.query("access_token", credentials.accessToken().getToken());
    template.query("v", "5.40");
  }
}
