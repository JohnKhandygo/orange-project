package com.kspt.orange.frameworks.twitter.api.endpoints;

import com.kspt.orange.frameworks.twitter.api.data.TwitterData;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("1.1/statuses/home_timeline.json")
@Produces(MediaType.APPLICATION_JSON)
public interface TwitterHomeStatusesApi {

  @GET
  List<TwitterData> search(
      final @QueryParam("count") int count,
      final @QueryParam("since_id") long min,
      final @QueryParam("max_id") long max,
      final @QueryParam("trim_user") boolean trimUser,
      final @QueryParam("exclude_replies") boolean excludeReplies,
      final @QueryParam("contributor_details") boolean contributorDetails,
      final @QueryParam("include_rts") boolean includeRts);
}
