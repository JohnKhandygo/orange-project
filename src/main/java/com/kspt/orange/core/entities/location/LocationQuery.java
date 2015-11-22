package com.kspt.orange.core.entities.location;

import com.kspt.orange.core.entities.Query;

public class LocationQuery implements Query {

  private final double latitude;

  private final double longitude;

  private final double radius;

  public LocationQuery(final double latitude, final double longitude, final double radius) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.radius = radius;
  }

  public double latitude() {
    return latitude;
  }

  public double longitude() {
    return longitude;
  }

  public double radius() {
    return radius;
  }
}
