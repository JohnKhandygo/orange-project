package com.kspt.orange.frameworks.api.twitter.endpoints.home;

import com.kspt.orange.frameworks.api.twitter.entities.TwitterData;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("1.1/statuses/home_timeline.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterHomeDataApi {

  @GET
  List<TwitterData> search(
      final @QueryParam("count") int count,
      final @QueryParam("since_id") Long last,
      final @QueryParam("max_id") Long first,
      final @QueryParam("trim_user") boolean trimUser,
      final @QueryParam("exclude_replies") boolean excludeReplies,
      final @QueryParam("contributor_details") boolean contributorDetails,
      final @QueryParam("include_rts") boolean includeRts);
}
