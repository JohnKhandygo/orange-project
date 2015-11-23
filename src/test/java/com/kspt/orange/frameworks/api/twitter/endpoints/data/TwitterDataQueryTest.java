package com.kspt.orange.frameworks.api.twitter.endpoints.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TwitterDataQueryTest {

  @Test
  public void twitterGeoFormatVerification() {
    final String formattedGeo = TwitterDataQuery.formatLocation(1.2, 1.23, 2.22222);
    assertThat(formattedGeo).hasSize(16);
    assertThat(formattedGeo).isEqualTo(formattedGeo.trim());
    assertThat(formattedGeo.split(",")).hasSize(3);
    assertThat(formattedGeo.split("\\.")).hasSize(4);
  }
}