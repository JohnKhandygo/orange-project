package com.kspt.orange.frameworks.api.twitter.sources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kspt.orange.application.StreamSource;
import com.kspt.orange.application.streams.Stream;
import com.kspt.orange.frameworks.api.twitter.endpoints.stream.TwitterStreamApi;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.DeletionNotification;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.Event;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.FriendsList;
import com.kspt.orange.frameworks.api.twitter.entities.streamed.StallNotification;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwitterStreamSource extends StreamSource<TwitterStreamedData> {

  private final TwitterStreamApi api;

  private final ScheduledExecutorService schedule;

  private final ObjectMapper mapper;

  public TwitterStreamSource(
      final Stream<TwitterStreamedData> output,
      final TwitterStreamApi api,
      final ScheduledExecutorService schedule,
      final ObjectMapper mapper) {
    super(output);
    this.api = api;
    this.schedule = schedule;
    this.mapper = mapper;
  }

  public void start(
      final long delayMs,
      final long consumingRateMs,
      final long pollingTimeoutMs) {
    final BlockingQueue<String> queue = api.queue();
    schedule.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        final String polled;
        try {
          polled = queue.poll(pollingTimeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
          exception.printStackTrace();
          return;
        }
        if (polled != null) {
          final TwitterStreamedData food;
          if (tryParseAs(polled, FriendsList.class)) {
            food = parseAs(polled, FriendsList.class);
          } else if (tryParseAs(polled, DeletionNotification.class)) {
            food = parseAs(polled, DeletionNotification.class);
          } else if (tryParseAs(polled, Event.class)) {
            food = parseAs(polled, Event.class);
          } else if (tryParseAs(polled, StallNotification.class)) {
            food = parseAs(polled, StallNotification.class);
          } else {
            food = parseAs(polled, TwitterData.class);
          }
          output().feed(food);
        }
      }
    }, delayMs, consumingRateMs, TimeUnit.MILLISECONDS);
    api.start();
  }

  private boolean tryParseAs(
      final String raw,
      final Class<? extends TwitterStreamedData> clazz) {
    try {
      final TwitterStreamedData parsed = mapper.readValue(raw, clazz);
      return true;
    } catch (IOException exception) {
      return false;
    }
  }

  private TwitterStreamedData parseAs(
      final String raw,
      final Class<? extends TwitterStreamedData> clazz) {
    try {
      final TwitterStreamedData parsed = mapper.readValue(raw, clazz);
      return parsed;
    } catch (IOException exception) {
      return null;
    }
  }
}
