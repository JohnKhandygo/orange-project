package com.kspt.orange.application.streams;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.After;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

@RunWith(Theories.class)
public class ObserverTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  Handler<Object, Object> handler;

  @Mock
  Observable<Object> output;

  @Mock
  ExecutorService executor;

  private Observable<Object> input;

  private ScheduledExecutorService scheduler;

  private Observer<Object, Object> observer;

  @Before
  public void setUp()
  throws Exception {
    input = new Observable<>(Lists.newArrayList());
    newObserver();
  }

  private void newObserver() {
    final BlockingQueue<Object> queue = Queues.newLinkedBlockingQueue();
    scheduler = Executors.newSingleThreadScheduledExecutor();
    observer = new Observer<>(handler, queue, scheduler, 100, 100, executor, output);
  }

  @Test
  public void whenSubscribed_startCalledAndNothingElseHappened() {
    observer.subscribeOn(input);
    verifyZeroInteractions(executor, handler, output);
  }

  @Theory
  public void whenSomethingEmittedInSeveralTimes_ExecutorSubmitsTaskTheSameNumberOfTimes(
      final @ForAll(sampleSize = 5) @InRange(min = "1", max = "5") int numberOfEmits)
  throws Exception {
    mockExecutorAsDirectCaller();
    runAndWaitUntilSomethingEmittedOutNTimes(numberOfEmits);
    verify(executor, times(numberOfEmits)).submit(any(Runnable.class));
    verify(handler, times(numberOfEmits)).observe(any(Object.class));
    verify(handler, times(0)).onError(any(Exception.class));
  }

  private void runAndWaitUntilSomethingEmittedOutNTimes(final int numberOfEmits)
  throws InterruptedException {
    CountDownLatch latch = setUpTerminateConditionOnEmittingOutNTimes(numberOfEmits);
    subscribeOnStreamAndEmitNObjectsIn(numberOfEmits);
    latch.await();
  }

  private void subscribeOnStreamAndEmitNObjectsIn(final int objectsToEmit) {
    observer.subscribeOn(input);
    IntStream.range(0, objectsToEmit).mapToObj(i -> mock(Object.class)).forEach(input::emit);
  }

  private CountDownLatch setUpTerminateConditionOnEmittingOutNTimes(final int numberOfEmitsOut) {
    final CountDownLatch latch = new CountDownLatch(numberOfEmitsOut);
    doAnswer(countDownAndReturnNull(latch)).when(output).emit(any(Object.class));
    return latch;
  }

  private Answer countDownAndReturnNull(final CountDownLatch latch) {
    return invocation -> {
      latch.countDown();
      return null;
    };
  }

  private void mockExecutorAsDirectCaller() {
    doAnswer(directCallAndReturnNull()).when(executor).submit(any(Runnable.class));
  }

  private Answer directCallAndReturnNull() {
    return invocation -> {
      invocation.getArgumentAt(0, Runnable.class).run();
      return null;
    };
  }

  @Test
  public void whenExecutorThrowsException_itsHandledAndNothingIsEmitted()
  throws Exception {
    doAnswer(throwException()).when(executor).submit(any(Runnable.class));
    runAndWaitUntilExceptionOccurred();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(0)).observe(any(Object.class));
    verify(handler, times(1)).onError(any(Exception.class));
    verify(output, times(0)).emit(any(Object.class));
  }

  private Answer throwException() {
    return invocation -> {
      throw mock(Exception.class);
    };
  }

  private void runAndWaitUntilExceptionOccurred()
  throws InterruptedException {
    final CountDownLatch latch = setupTerminateConditionOnExceptionHandler();
    subscribeOnStreamAndEmitNObjectsIn(1);
    latch.await();
  }

  private CountDownLatch setupTerminateConditionOnExceptionHandler() {
    final CountDownLatch latch = new CountDownLatch(1);
    doAnswer(countDownAndReturnNull(latch)).when(handler).onError(any(Exception.class));
    return latch;
  }

  @Test
  public void whenObserveThrowsException_itsHandledAndNothingIsEmitted()
  throws Exception {
    mockExecutorAsDirectCaller();
    doAnswer(throwException()).when(handler).observe(any(Object.class));
    runAndWaitUntilExceptionOccurred();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(1)).observe(any(Object.class));
    verify(handler, times(1)).onError(any(Exception.class));
    verify(output, times(0)).emit(any(Object.class));
  }

  @Test
  public void whenEmittingOutThrowsException_itsHandledAndNothingIsEmitted()
  throws Exception {
    mockExecutorAsDirectCaller();
    doAnswer(throwException()).when(output).emit(any(Object.class));
    runAndWaitUntilExceptionOccurred();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(1)).observe(any(Object.class));
    verify(handler, times(1)).onError(any(Exception.class));
    verify(output, times(1)).emit(any(Object.class));
  }

  @After
  public void tearDown()
  throws Exception {
    scheduler.shutdown();
  }
}