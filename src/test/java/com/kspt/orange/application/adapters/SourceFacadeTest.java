package com.kspt.orange.application.adapters;

import com.kspt.orange.application.Source;
import com.kspt.orange.core.entities.Data;
import com.kspt.orange.core.entities.Query;
import com.kspt.orange.core.ports.Gateway;
import com.kspt.orange.core.streams.Observable;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@RunWith(Theories.class)
public class SourceFacadeTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  Source<Query, Data> source;

  @Mock
  Observable<Data> output;

  private Gateway<Query, Data> gateway;

  @Before
  public void setUp()
  throws Exception {
    gateway = new SourceFacade<>(source, output);
  }

  @Test
  public void whenSourceCantFoundAnything_nothingEmittedOut() {
    doReturn(emptyList()).when(source).get(any(Query.class));
    run();
    verifyZeroInteractions(output);
  }

  private void run() {
    final Query query = mock(Query.class);
    gateway.forward(query);
  }

  @Theory
  public void whenSourceFoundData_theSameCountEmittedOut(
      final @ForAll(sampleSize = 20) @InRange(min = "1", max = "5") int dataListSize)
  throws Exception {
    doReturn(dataList(dataListSize)).when(source).get(any(Query.class));
    runAndWaitUntilNEmittingsOut(dataListSize);
    verify(output, times(dataListSize)).emit(any(Data.class));
  }

  private List<Data> dataList(final int count) {
    return IntStream.range(0, count).mapToObj(i -> mock(Data.class)).collect(toList());
  }

  private void runAndWaitUntilNEmittingsOut(final int dataListSize)
  throws InterruptedException {
    final CountDownLatch latch = setupTerminateConditionAfterNEmittingOut(dataListSize);
    run();
    latch.await();
  }

  private CountDownLatch setupTerminateConditionAfterNEmittingOut(final int count) {
    final CountDownLatch latch = new CountDownLatch(count);
    doAnswer(countDownAndReturnNull(latch)).when(output).emit(any(Data.class));
    return latch;
  }

  private Answer countDownAndReturnNull(final CountDownLatch latch) {
    return invocation -> {
      latch.countDown();
      return null;
    };
  }
}