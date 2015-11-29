package com.kspt.orange.frameworks.api.vk_unworked.endpoints;

import com.kspt.orange.frameworks.api.vk_unworked.entities.VkDataObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("method/newsfeed.get")
@Produces(MediaType.APPLICATION_JSON)
public interface VkNewsApi {

  @GET
  VkDataObject search(
      final @QueryParam("filters") String filters,
      final @QueryParam("start_time") Integer startTime,
      final @QueryParam("end_time") Integer endTime,
      final @QueryParam("start_from") String startFrom,
      final @QueryParam("count") int count);
}
