package com.kspt.orange.frameworks.sources.oauth;

import org.scribe.model.Token;

public class AuthenticationCredentials {

  private final Token applicationToken;

  private final Token userToken;

  public AuthenticationCredentials(
      final Token applicationToken,
      final Token userToken) {
    this.applicationToken = applicationToken;
    this.userToken = userToken;
  }

  public Token applicationToken() {
    return applicationToken;
  }

  public Token userToken() {
    return userToken;
  }
}
