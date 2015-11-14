package com.kspt.orange.application;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;

public interface MetadataSource<Q extends Query, D extends Data> extends DataSource<Q, D> {
  Data get(final String id);
}
