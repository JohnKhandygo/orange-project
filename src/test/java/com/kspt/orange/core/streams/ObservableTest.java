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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
    observer = new Observer<>(handler, queue, scheduler, 500, 500, executor, output);
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

  @Test
  public void whenSomethingEmittedIn_HandlerCalled()
  throws Exception {
    CountDownLatch latch = setUpTerminateOnHandler();
    run();
    latch.await();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(1)).observe(any(Object.class));
  }

  private void run() {
    final Object object = mock(Object.class);
    input.register(observer);
    input.emit(object);
  }

  private CountDownLatch setUpTerminateOnHandler() {
    final CountDownLatch latch = new CountDownLatch(1);
    doAnswer(invocation -> {
      latch.countDown();
      return null;
    }).when(handler).observe(any(Object.class));
    return latch;
  }

  @Test
  public void whenSomethingEmittedInAndHandlerProducesIt_ItsEmittedOut()
  throws Exception {
    doAnswer(invocation -> invocation.getArgumentAt(0, Object.class))
        .when(handler).observe(anyObject());
    final CountDownLatch latch = setUpTerminateOnEmittingOut();
    run();
    latch.await();
    verify(executor, times(1)).submit(any(Runnable.class));
    verify(handler, times(1)).observe(any(Object.class));
    verify(output, times(1)).emit(anyObject());
  }

  private CountDownLatch setUpTerminateOnEmittingOut() {
    final CountDownLatch latch = new CountDownLatch(1);
    doAnswer(invocation -> {
      latch.countDown();
      return null;
    }).when(output).emit(any(Object.class));
    return latch;
  }

  @After
  public void tearDown()
  throws Exception {
    scheduler.shutdown();
  }
}