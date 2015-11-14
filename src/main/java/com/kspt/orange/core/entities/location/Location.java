package com.kspt.orange.core.entities.location;

import com.kspt.orange.core.entities.Data;

public class Location implements Data {

  private final String id;

  private final double latitude;

  private final double longitude;

  public Location(final String id, final double latitude, final double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public String id() {
    return id;
  }

  public double latitude() {
    return latitude;
  }

  public double longitude() {
    return longitude;
  }
}
