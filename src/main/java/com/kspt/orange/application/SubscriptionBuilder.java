package com.kspt.orange.application;

import com.kspt.orange.application.adapters.Gateway;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;

public interface SubscriptionBuilder {
  SubscriptionBuilder onAuthor(final long id);

  <Q1 extends Query, Q2 extends Query, D extends Data>
  Gateway<Q1, Q2, D> from(final Source<Q2, D> source);
}
