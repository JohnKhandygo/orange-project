package com.kspt.orange.core.entities;

public class Location implements Data {
  private final int id;

  private final double latitude;

  private final double longitude;

  public Location(final int id, final double latitude, final double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double latitude() {
    return latitude;
  }

  public double longitude() {
    return longitude;
  }

  @Override
  public int id() {
    return id;
  }
}
