package com.kspt.orange.frameworks.api.twitter.endpoints.data;

import com.kspt.orange.frameworks.api.twitter.entities.TwitterDataObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/search/tweets.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterPublicDataApi {

  @GET
  TwitterDataObject search(
      final @QueryParam("q") String query,
      final @QueryParam("geocode") String geocode,
      final @QueryParam("count") int count,
      final @QueryParam("since_id") Long last,
      final @QueryParam("max_id") Long first,
      final @QueryParam("lang") String lang,
      final @QueryParam("result_type") String resultType);
}

