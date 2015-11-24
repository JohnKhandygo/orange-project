package com.kspt.orange.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kspt.orange.application.adapters.Gateway;
import com.kspt.orange.application.adapters.GatewayBuilder;
import com.kspt.orange.application.entities.QueryWithLimit;
import com.kspt.orange.application.entities.bounded.QueryWithBounds;
import com.kspt.orange.application.streams.Handler;
import com.kspt.orange.application.streams.Observer;
import static com.kspt.orange.application.streams.Observers.newObserverWithFixedExecutorsPool;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import static com.kspt.orange.frameworks.adapters.QueryDelimiters.newDownBoundedQueryDelimiter;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataQuery;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataSource;
import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import org.junit.Test;
import org.scribe.model.Token;
import java.util.concurrent.CountDownLatch;

public class WorkingWithTwitterHomeData {

  @Test
  public void howAskForSeveralTweets() {
    /*final Token applicationToken = new Token("app_key", "app_secret");
    final Token userAccessToken = new Token("access_key", "access_secret");*/

    final Token applicationToken = new Token(
        "NtGAPn05Q74N0sQrAnjvJrU7z",
        "7sjmEWkQ68yATYjd9GI2oJ4kebTqp0vfi9J0WHIOfFH3XaOfhV");
    final Token userAccessToken = new Token(
        "4235420180-wR8ylQuJh22hh02XJVgHeGdJxWpQIan8SOiVa7s",
        "yVeJHBYwKMB4DzQ997rG8lRZh1kymvf0F0hAoEsdJbh6X");

    //specify credentials
    final AuthenticationCredentials credentials =
        new AuthenticationCredentials(applicationToken, userAccessToken);

    //how many data extracts for one query
    final int granularity = 1;
    //specify gateway builder
    final GatewayBuilder<TwitterHomeDataQuery, QueryWithBounds<TwitterHomeDataQuery>, TwitterData>
        builder = new GatewayBuilder<>();
    //building the gateway
    final Gateway<TwitterHomeDataQuery, QueryWithBounds<TwitterHomeDataQuery>, TwitterData>
        gateway = builder
        //generate next query for previous one. for example, if we want to extract 10 data units
        //but by portions of 5, this instance is responsible for generating the first
        //query for 5 data units and the next query for remaining ones data units.
        .withQueryDelimiter(newDownBoundedQueryDelimiter(granularity))
        .onSource(TwitterHomeDataSource.newOne(credentials))
        .build();

    //how many data we want to extract for query
    final int limit = 2;
    //just for terminating test after all data proceed
    final CountDownLatch latch = new CountDownLatch(limit);
    //one that handles every unit of extracted data
    final Handler<TwitterData, Void> handler = new Handler<TwitterData, Void>() {
      @Override
      public void onError(final Exception exception) {
        exception.printStackTrace();
      }

      @Override
      public Void observe(final TwitterData element) {
        try {
          latch.countDown();
          final ObjectMapper mapper = new ObjectMapper();
          //simply map object to json and print it
          System.out.println(mapper.writeValueAsString(element));
        } catch (Exception e) {
          System.out.println("error while mapping");
          e.printStackTrace();
        }
        return null;
      }
    };
    //creating an observer for output of gateway
    //specifies handler, how many processing threads to use, how frequently try to
    //process data (1000ms = 1s in example) and how frequently wait for data
    //(500ms = 0,5s in example)
    final Observer<TwitterData, Void> observer =
        newObserverWithFixedExecutorsPool(handler, 2, 1000, 500);
    //register created observer as processor for gateway output data
    gateway.output().register(observer);

    //creating a request
    final TwitterHomeDataQuery query = new TwitterHomeDataQuery(true, true, false, false);
    //forward a query
    gateway.forward(new QueryWithLimit<>(query, limit));
    //now the query will be partitioned and every partition of the query will be sent to api
    //then data extracts and gateway puts it in own output. As data come in output, observer
    //will detect it and process, doing what described in it's handler.
    try {
      //just wait while lock will be released
      latch.await();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Booya");
  }
}
