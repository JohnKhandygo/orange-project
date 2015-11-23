package com.kspt.orange.frameworks.twitter.api.endpoints;

import com.kspt.orange.frameworks.twitter.api.data.TwitterDataObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/search/tweets.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterDataApi {

  @GET
  TwitterDataObject search(
      final @QueryParam("q") String query,
      final @QueryParam("geocode") String geocode,
      final @QueryParam("count") int count,
      final @QueryParam("since_id") long last,
      final @QueryParam("max_id") long first,
      final @QueryParam("result_type") String resultType);
}

