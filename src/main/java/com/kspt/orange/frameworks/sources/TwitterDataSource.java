package com.kspt.orange.frameworks.sources;

import com.kspt.orange.application.DataSource;
import com.kspt.orange.core.entities.location.Location;
import com.kspt.orange.core.entities.location.LocationQuery;
import java.util.Collection;

public class TwitterDataSource implements DataSource<LocationQuery, Location> {

  @Override
  public Collection<Location> get(final LocationQuery query) {
    return null;
  }
}
