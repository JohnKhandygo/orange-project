package com.kspt.orange.application;

import com.kspt.orange.core.entities.Subscription;

public interface SubscriptionBuilder {
  SubscriptionBuilder onAuthor(final int id);

  Subscription from(final Source source);
}
