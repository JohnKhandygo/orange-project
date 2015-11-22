package com.kspt.orange.frameworks.sources.oauth;

import com.kspt.orange.application.DataSource;
import com.kspt.orange.core.entities.location.Location;
import com.kspt.orange.core.entities.location.LocationQuery;

public interface OAuthDataSource extends DataSource<LocationQuery, Location> {

  void authenticate(final AuthenticationCredentials credentials);
}
