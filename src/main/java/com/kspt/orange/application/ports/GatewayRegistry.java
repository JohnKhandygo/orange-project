package com.kspt.orange.application.ports;

import com.kspt.orange.application.SubscriptionBuilder;

public interface GatewayRegistry {
  SubscriptionBuilder subscribe();
}
