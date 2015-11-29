package com.kspt.orange.frameworks.api.twitter.endpoints.stream;

import com.google.common.collect.Queues;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.UserstreamEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.util.concurrent.BlockingQueue;

public class TwitterStreamApi {

  private final BasicClient client;

  private final BlockingQueue<String> queue;

  public TwitterStreamApi(
      final BasicClient client,
      final BlockingQueue<String> queue) {
    this.client = client;
    this.queue = queue;
  }

  public void start() {
    client.connect();
  }

  public void terminate() {
    client.stop();
  }

  public BlockingQueue<String> queue() {
    return queue;
  }

  public static TwitterStreamApi build(final AuthenticationCredentials credentials) {
    BlockingQueue<String> queue = Queues.newLinkedBlockingQueue();
    Authentication auth = new OAuth1(
        credentials.consumerToken().getToken(),
        credentials.consumerToken().getSecret(),
        credentials.accessToken().getToken(),
        credentials.accessToken().getSecret());
    BasicClient client = new ClientBuilder()
        .hosts(Constants.USERSTREAM_HOST)
        .endpoint(new UserstreamEndpoint())
        .authentication(auth)
        .processor(new StringDelimitedProcessor(queue))
        .build();
    return new TwitterStreamApi(client, queue);
  }
}
