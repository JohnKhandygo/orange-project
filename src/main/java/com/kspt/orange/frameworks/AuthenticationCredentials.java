package com.kspt.orange.frameworks;

import org.scribe.model.Token;

public class AuthenticationCredentials {

  private final Token consumerToken;

  private final Token accessToken;

  public AuthenticationCredentials(
      final Token consumerToken,
      final Token accessToken) {
    this.consumerToken = consumerToken;
    this.accessToken = accessToken;
  }

  public Token consumerToken() {
    return consumerToken;
  }

  public Token accessToken() {
    return accessToken;
  }
}
