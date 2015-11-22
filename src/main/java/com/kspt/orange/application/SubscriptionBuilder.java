package com.kspt.orange.application;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Gateway;

public interface SubscriptionBuilder {
  SubscriptionBuilder onAuthor(final String id);

  <Q extends Query, D extends Data> Gateway<Q, D> from(final DataSource<Q, D> dataSource);
}
