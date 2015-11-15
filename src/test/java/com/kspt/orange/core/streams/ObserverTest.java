package com.kspt.orange.core.streams;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
    observer = new Observer<>(handler, queue, scheduler, 500, 500, executor, output);
  }

  @Test
  public void whenSubscribed_startCalledAndNothingElseHappened() {
    observer.subscribeOn(input);
    verifyZeroInteractions(executor, handler, output);
  }

  @Test
  public void whenSomethingEmittedIn_ExecutorSubmitsTask()
  throws Exception {
    mockExecutorAsDirectCaller();
    runAndWaitUntilSomethingEmittedOut();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(1)).observe(any(Object.class));
    verify(handler, times(0)).onError(any(Exception.class));
  }

  private void runAndWaitUntilSomethingEmittedOut()
  throws InterruptedException {
    CountDownLatch latch = setUpTerminateConditionOnEmittingOut();
    run();
    latch.await();
  }

  private void run() {
    final Object object = mock(Object.class);
    observer.subscribeOn(input);
    input.emit(object);
  }

  private CountDownLatch setUpTerminateConditionOnEmittingOut() {
    final CountDownLatch latch = new CountDownLatch(1);
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
    run();
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