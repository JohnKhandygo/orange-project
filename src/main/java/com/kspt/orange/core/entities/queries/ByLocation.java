package com.kspt.orange.core.entities.queries;

import com.kspt.orange.core.entities.Query;

public class ByLocation implements Query {

  private final double latitude;

  private final double longitude;

  private final double radius;

  public ByLocation(final double latitude, final double longitude, final double radius) {
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
