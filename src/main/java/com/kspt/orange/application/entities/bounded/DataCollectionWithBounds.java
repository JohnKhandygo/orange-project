package com.kspt.orange.application.entities.bounded;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;

public interface DataCollectionWithBounds<D extends Data> extends DataCollection<D> {

  long min();

  long max();
}
