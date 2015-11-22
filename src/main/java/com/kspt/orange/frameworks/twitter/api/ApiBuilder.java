package com.kspt.orange.frameworks.twitter.api;

import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;

public class ApiBuilder {
  public static <T> T build(
      final String host,
      final Class<T> clazz,
      final RequestInterceptor interceptor) {
    return builder()
        .requestInterceptor(interceptor)
        .target(clazz, host);
  }

  private static Builder builder() {
    return Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new JAXRSContract());
  }
}
