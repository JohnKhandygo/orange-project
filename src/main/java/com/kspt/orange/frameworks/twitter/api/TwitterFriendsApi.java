package com.kspt.orange.frameworks.twitter.api;

import com.kspt.orange.frameworks.twitter.api.data.TwitterDataObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/friends/list.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterFriendsApi {

  @GET
  TwitterDataObject searchById(
      final @QueryParam("user_id") String userId,
      final @QueryParam("cursor") String cursor,
      final @QueryParam("count") int count,
      final @QueryParam("skip_status") boolean skipStatus,
      final @QueryParam("include_user_entities") boolean includeUserEntities);

  @GET
  TwitterDataObject searchByScreenName(
      final @QueryParam("screen_name") String screenName,
      final @QueryParam("cursor") String cursor,
      final @QueryParam("count") int count,
      final @QueryParam("skip_status") boolean skipStatus,
      final @QueryParam("include_user_entities") boolean includeUserEntities);
}
