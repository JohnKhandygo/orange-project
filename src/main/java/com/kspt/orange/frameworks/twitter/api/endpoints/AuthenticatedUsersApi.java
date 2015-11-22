package com.kspt.orange.frameworks.twitter.api.endpoints;

import com.kspt.orange.frameworks.twitter.api.data.User;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/account/verify_credentials.json")
@Produces(MediaType.APPLICATION_JSON)
public interface AuthenticatedUsersApi {

  @GET
  User authenticatedUser(
      @QueryParam("include_entities") boolean includeEntities,
      @QueryParam("skip_status") boolean skipStatus);
}
