package com.kspt.orange.frameworks.sources;

import static com.google.common.collect.Lists.newArrayList;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Source;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.function.Predicate;

interface QueryToPredicateConverter<Q extends Query, D extends Data> {
  Predicate<D> convert(final Q query);
}

public class CachedSource<Q extends Query, D extends Data> implements Source<Q, D> {

  final List<D> cached;

  private final QueryToPredicateConverter<Q, D> converter;

  protected CachedSource(
      final List<D> cached,
      final QueryToPredicateConverter<Q, D> converter) {
    this.cached = cached;
    this.converter = converter;
  }

  @Override
  public DataCollection<D> get(final Q query) {
    final List<D> data = cached.stream().filter(converter.convert(query)).collect(toList());
    return () -> data;
  }

  public static <Q extends Query, D extends Data>
  CachedSource<Q, D> emptyOne(final QueryToPredicateConverter<Q, D> converter) {
    return new CachedSource<>(newArrayList(), converter);
  }
}
