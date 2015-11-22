package com.kspt.orange.frameworks.twitter.api;

import com.kspt.orange.frameworks.twitter.api.data.TwitterDataObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("1.1/search/tweets.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterDataApi {

  @GET
  List<TwitterDataObject> search(
      final @QueryParam("q") String query,
      final @QueryParam("geocode") String geocode,
      final @QueryParam("result_type") String resultType,
      final @QueryParam("count") int count,
      final @QueryParam("until") String until,
      final @QueryParam("since_id") String min,
      final @QueryParam("max_id") String max);

  @GET
  TwitterDataObject search(final @QueryParam("q") String query);
}

