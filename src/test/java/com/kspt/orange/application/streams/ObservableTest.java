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
public class ObservableTest {

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
    mockExecutorAsDirectCaller();
  }

  private void mockExecutorAsDirectCaller() {
    doAnswer(invocation -> {
      invocation.getArgumentAt(0, Runnable.class).run();
      return null;
    }).when(executor).submit(any(Runnable.class));
  }

  @Test
  public void whenRegistered_nothingEmittedInAndOut() {
    observer.subscribeOn(input);
    verifyZeroInteractions(executor, handler);
  }

  @Theory
  public void whenSomethingEmittedInSeveralTimes_HandlerCalledTheSameNumberOfTimes(
      final @ForAll(sampleSize = 5) @InRange(min = "1", max = "5") int emittingTimes)
  throws Exception {
    runAndWaitUntilNHandlerCalls(emittingTimes);
    verify(executor, times(emittingTimes)).submit(any(Runnable.class));
    verify(handler, times(emittingTimes)).observe(any(Object.class));
  }

  private void runAndWaitUntilNHandlerCalls(final int handlerCallTimes)
  throws InterruptedException {
    CountDownLatch latch = setUpTerminateConditionOnHandler(handlerCallTimes);
    registerObserverAndEmitObjects(handlerCallTimes);
    latch.await();
  }

  private void registerObserverAndEmitObjects(final int emittingTimes) {
    input.register(observer);
    IntStream.range(0, emittingTimes).mapToObj(i -> mock(Object.class)).forEach(input::emit);
  }

  private CountDownLatch setUpTerminateConditionOnHandler(final int handlerCallTimes) {
    final CountDownLatch latch = new CountDownLatch(handlerCallTimes);
    doAnswer(countDownAndReturnNull(latch)).when(handler).observe(any(Object.class));
    return latch;
  }

  private Answer countDownAndReturnNull(final CountDownLatch latch) {
    return invocation -> {
      latch.countDown();
      return null;
    };
  }

  @Theory
  public void whenHandlerProducesResultSeveralTimes_ItsEmittedOutTheSameNUmberOfTimes(
      final @ForAll(sampleSize = 5) @InRange(min = "1", max = "5") int emittingTimes)
  throws Exception {
    doAnswer(identity()).when(handler).observe(anyObject());
    runAndWaitUntilNEmitsOut(emittingTimes);
    verify(executor, times(emittingTimes)).submit(any(Runnable.class));
    verify(handler, times(emittingTimes)).observe(any(Object.class));
    verify(output, times(emittingTimes)).emit(anyObject());
  }

  private void runAndWaitUntilNEmitsOut(final int emitTimes)
  throws InterruptedException {
    final CountDownLatch latch = setUpTerminateOnEmittingOut(emitTimes);
    registerObserverAndEmitObjects(emitTimes);
    latch.await();
  }

  private CountDownLatch setUpTerminateOnEmittingOut(final int emitOutTimes) {
    final CountDownLatch latch = new CountDownLatch(emitOutTimes);
    doAnswer(countDownAndReturnNull(latch)).when(output).emit(any(Object.class));
    return latch;
  }

  private Answer identity() {
    return invocation -> invocation.getArgumentAt(0, Object.class);
  }

  @After
  public void tearDown()
  throws Exception {
    scheduler.shutdown();
  }
}