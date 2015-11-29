package com.kspt.orange.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Queues;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.UserstreamEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.junit.Test;
import org.scribe.model.Token;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class HbcTest {

  @Test
  public void test()
  throws Exception {
    // Create an appropriately sized blocking queue
    BlockingQueue<String> queue = Queues.newLinkedBlockingQueue();

    // Define our endpoint: By default, delimited=length is set (we need this for our processor)
    // and stall warnings are on.
    UserstreamEndpoint endpoint = new UserstreamEndpoint();
    endpoint.stallWarnings(false);

    final Token appToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token accessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");
    Authentication auth = new OAuth1(
        appToken.getToken(),
        appToken.getSecret(),
        accessToken.getToken(),
        accessToken.getSecret());
    //Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

    final BlockingQueue<Event> events = Queues.newLinkedBlockingQueue();
    // Create a new BasicClient. By default gzip is enabled.
    BasicClient client = new ClientBuilder()
        .name("sampleExampleClient")
        .hosts(Constants.USERSTREAM_HOST)
        .endpoint(endpoint)
        .authentication(auth)
        .processor(new StringDelimitedProcessor(queue))
        .eventMessageQueue(events)
        .build();

    // Establish a connection
    client.connect();

    // Do whatever needs to be done with messages
    for (int msgRead = 0; msgRead < 5; msgRead++) {
      if (client.isDone()) {
        System.out.println(
            "Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
        break;
      }

      String msg = queue.poll(5, TimeUnit.SECONDS);
      if (msg == null) {
        System.out.println("Did not receive a message in 5 seconds");
      } else if (msgRead != 0) {
        final ObjectMapper mapper = new ObjectMapper();
        final TwitterData twitterData = mapper.readValue(msg, TwitterData.class);
        System.out.println(msg);
      }
    }

    client.stop();

    // Print some stats
    System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
  }
}