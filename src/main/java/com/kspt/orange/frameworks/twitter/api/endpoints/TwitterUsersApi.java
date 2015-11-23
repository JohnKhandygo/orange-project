package com.kspt.orange.frameworks.twitter.api.endpoints;

import com.kspt.orange.frameworks.twitter.api.data.User;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/users")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterUsersApi {

  @Path("/show.json")
  @GET
  User search(
      final @QueryParam("user_id") long id,
      final @QueryParam("include_entities") boolean includeEntities);

  /*@Path("/show.json")
  @GET
  User searchByScreenName(
      final @QueryParam("screen_name") String screenName,
      final @QueryParam("include_entities") boolean includeEntities);*/

  /*@Path("/lookup.json")
  @GET
  List<User> search(
      final @QueryParam("user_id") String userIds,
      final @QueryParam("screen_name") String screenNames,
      final @QueryParam("include_entities") boolean includeEntities);*/
}