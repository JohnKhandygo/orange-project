package com.kspt.orange.application.ports;

import com.kspt.orange.core.entities.Query;

public interface QueryComplementor<Q1 extends Query, Q2 extends Query> {

  Q2 complete(final Q1 query);
}
