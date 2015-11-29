package com.kspt.orange.application;

import com.kspt.orange.application.streams.Stream;

public class StreamSource<D> {

  private final Stream<D> output;

  public StreamSource(final Stream<D> output) {
    this.output = output;
  }

  public Stream<D> output() {
    return output;
  }
}
