package com.kspt.orange.frameworks.api.twitter.sources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.kspt.orange.application.streams.Consumer;
import com.kspt.orange.application.streams.Handler;
import com.kspt.orange.application.streams.Stream;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.twitter.endpoints.stream.TwitterStreamApi;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.DeletionNotification;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.Event;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.FriendsList;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.StallNotification;
import org.scribe.model.Token;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class TwitterStreamSourceExample {

  public static void main(String[] args) {
    final TwitterStreamSource source = newTwitterStreamSource();
    final Consumer<TwitterStreamedData> consumer = newConsumer();
    source.output().register(consumer);
    source.output().start(0, 1000L, 500L);
    source.start(0L, 1000L, 500L);
  }

  public static Consumer<TwitterStreamedData> newConsumer() {
    final ExecutorService executor = Executors.newFixedThreadPool(2);
    final Handler<TwitterStreamedData> handler = buildHandler();
    return new Consumer<>(handler, executor);
  }

  public static Handler<TwitterStreamedData> buildHandler() {
    return new Handler<TwitterStreamedData>() {
      @Override
      public void onError(final Exception exception) {
        exception.printStackTrace();
      }

      @Override
      public void onObserve(final TwitterStreamedData element) {
        if (element == null) {
          System.out.println("Got null");
        } else if (element instanceof FriendsList) {
          onFriendsList((FriendsList) element);
        } else if (element instanceof DeletionNotification) {
          onDeletionNotification((DeletionNotification) element);
        } else if (element instanceof Event) {
          onEvent((Event) element);
        } else if (element instanceof StallNotification) {
          onStallNotification((StallNotification) element);
        } else if (element instanceof TwitterData) {
          onTwitterData((TwitterData) element);
        } else {
          onUnknown(element);
        }
      }

      private void onFriendsList(final FriendsList friendsList) {
        System.out.println("Got " + friendsList.friends().size() + " friends");
      }

      private void onDeletionNotification(final DeletionNotification notification) {
        System.out.println(notification);
      }

      private void onEvent(final Event event) {
        System.out.println(event);
      }

      private void onStallNotification(final StallNotification notification) {
        System.out.println(notification);
      }

      private void onTwitterData(final TwitterData data) {
        if (data.coordinates() != null) {
          System.out.println("Got data with geo!");
          System.out.println("  " + data);
        } else {
          System.out.println("Just data " + data);
        }
      }

      private void onUnknown(final TwitterStreamedData data) {
        System.out.println("Got unknown " + data);
      }
    };
  }

  public static TwitterStreamSource newTwitterStreamSource() {
    final Stream<TwitterStreamedData> output = newStream();
    final TwitterStreamApi api = newTwitterStreamApi();
    final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    final ObjectMapper mapper = new ObjectMapper();
    return new TwitterStreamSource(output, api, schedule, mapper);
  }

  public static Stream<TwitterStreamedData> newStream() {
    //also may ne created with capacity
    final BlockingQueue<TwitterStreamedData> queue = Queues.newLinkedBlockingQueue();
    //these is default
    final ArrayList<Consumer<TwitterStreamedData>> consumers = Lists.newArrayList();
    //these is default
    final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    //for unblocked queue
    final AtomicBoolean blocked = new AtomicBoolean(false);
    return new Stream<TwitterStreamedData>(queue, consumers, schedule, blocked);
  }

  public static TwitterStreamApi newTwitterStreamApi() {
    return TwitterStreamApi.build(getCredentials());
  }

  public static AuthenticationCredentials getCredentials() {
    final Token consumerToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token accessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    return new AuthenticationCredentials(consumerToken, accessToken);
  }
}