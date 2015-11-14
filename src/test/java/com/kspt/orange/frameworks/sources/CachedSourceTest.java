package com.kspt.orange.frameworks.sources;

import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class CachedSourceTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  QueryToPredicateConverter<Query, Data> converter;

  @Mock
  Query query;

  private CachedSource<Query, Data> source;

  @Before
  public void setUp()
  throws Exception {
    source = CachedSource.emptyOne(converter);
    dataList().stream().forEach(source.cached::add);
  }

  private List<Data> dataList() {
    return IntStream.range(0, 4).mapToObj(this::generateData).collect(toList());
  }

  @Test
  public void whenAnythingSatisfiesQuery_allShouldBeFound() {
    doReturn(anything()).when(converter).convert(any(Query.class));
    final Collection<Data> found = source.get(query);
    assertThat(found).containsOnlyElementsOf(source.cached);
  }

  private Predicate<Data> anything() {
    return d -> true;
  }

  @Test
  public void whenNothingSatisfiesQuery_nothingShouldBeFound() {
    doReturn(nothing()).when(converter).convert(any(Query.class));
    final Collection<Data> found = source.get(query);
    assertThat(found).isEmpty();
  }

  private Predicate<Data> nothing() {
    return d -> false;
  }

  @Test
  public void whenOnlyOneSatisfiesQuery_thatOneShouldBeReturned() {
    for (final Data pattern : source.cached) {
      doReturn(theOnly(pattern)).when(converter).convert(any(Query.class));
      final Collection<Data> found = source.get(query);
      assertThat(found).containsOnly(pattern);
    }
  }

  private Predicate<Data> theOnly(final Data data) {
    return d -> d.id().equals(data.id());
  }

  private Data generateData(final int i) {
    final Data data = mock(Data.class);
    doReturn(String.valueOf(i)).when(data).id();
    return data;
  }
}