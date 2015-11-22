package com.kspt.orange.core.entities.location;

public class ByAuthor extends ByLocation {

  private final String author;

  public ByAuthor(
      final double latitude,
      final double longitude,
      final double radius,
      final String author) {
    super(latitude, longitude, radius);
    this.author = author;
  }

  public String author() {
    return author;
  }
}
