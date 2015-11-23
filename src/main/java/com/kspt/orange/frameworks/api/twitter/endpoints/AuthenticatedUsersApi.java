package com.kspt.orange.frameworks.api.twitter.endpoints;

import com.kspt.orange.frameworks.api.twitter.entities.User;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("1.1/account/verify_credentials.json")
@Produces(MediaType.APPLICATION_JSON)
public interface AuthenticatedUsersApi {

  @GET
  User authenticatedUser(
      @QueryParam("include_entities") boolean includeEntities,
      @QueryParam("skip_status") boolean skipStatus);
}
