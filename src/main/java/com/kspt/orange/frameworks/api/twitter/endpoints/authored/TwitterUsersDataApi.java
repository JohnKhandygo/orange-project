package com.kspt.orange.frameworks.api.twitter.endpoints.authored;

import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("1.1/statuses/user_timeline.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterUsersDataApi {

  @GET
  List<TwitterData> search(
      final @QueryParam("user_id") long userId,
      final @QueryParam("count") int count,
      final @QueryParam("since_id") long last,
      final @QueryParam("max_id") long first,
      final @QueryParam("exclude_replies") boolean excludeReplies,
      final @QueryParam("contributor_details") boolean contributorDetails,
      final @QueryParam("include_rts") boolean includeRts);

  /*@GET
  List<TwitterData> searchByScreenName(
      final @QueryParam("screen_name") String screenName,
      final @QueryParam("count") int count,
      final @QueryParam("since_id") long min,
      final @QueryParam("max_id") long max,
      final @QueryParam("exclude_replies") boolean excludeReplies,
      final @QueryParam("contributor_details") boolean contributorDetails,
      final @QueryParam("include_rts") boolean includeRts);*/
}
