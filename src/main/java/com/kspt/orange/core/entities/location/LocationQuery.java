package com.kspt.orange.core.entities.location;

import com.kspt.orange.core.entities.Query;

public class LocationQuery implements Query {

  private final double latitude;

  private final double longitude;

  public LocationQuery(final double latitude, final double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double latitude() {
    return latitude;
  }

  public double longitude() {
    return longitude;
  }
}
