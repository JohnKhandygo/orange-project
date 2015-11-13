package com.kspt.orange.frameworks.sources;

import com.kspt.orange.application.Source;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import java.util.Collection;

public class TwitterSource<Q extends Query, D extends Data> implements Source<Q, D> {

  @Override
  public Collection<D> get(final Q query) {
    return null;
  }
}
