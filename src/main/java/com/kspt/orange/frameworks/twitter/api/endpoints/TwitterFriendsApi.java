package com.kspt.orange.frameworks.twitter.api.endpoints;

import com.kspt.orange.frameworks.twitter.api.data.TwitterUsersObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/friends/list.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterFriendsApi {

  @GET
  TwitterUsersObject searchById(
      final @QueryParam("user_id") long userId,
      final @QueryParam("cursor") long cursor,
      final @QueryParam("count") int count,
      final @QueryParam("skip_status") boolean skipStatus,
      final @QueryParam("include_user_entities") boolean includeUserEntities);

  @GET
  TwitterUsersObject searchByScreenName(
      final @QueryParam("screen_name") String screenName,
      final @QueryParam("cursor") long cursor,
      final @QueryParam("count") int count,
      final @QueryParam("skip_status") boolean skipStatus,
      final @QueryParam("include_user_entities") boolean includeUserEntities);
}
