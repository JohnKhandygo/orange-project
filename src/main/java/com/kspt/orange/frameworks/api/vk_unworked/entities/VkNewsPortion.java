package com.kspt.orange.frameworks.api.vk_unworked.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class VkNewsPortion {

  private final List<VkNewsItem> items;

  private final List<VkUser> users;

  private final List<VkGroup> groups;

  private final String nextFrom;

  @JsonCreator
  public VkNewsPortion(
      final @JsonProperty("items") List<VkNewsItem> items,
      final @JsonProperty("profiles") List<VkUser> users,
      final @JsonProperty("groups") List<VkGroup> groups,
      final @JsonProperty("next_from") String nextFrom) {
    this.items = items;
    this.users = users;
    this.groups = groups;
    this.nextFrom = nextFrom;
  }

  public List<VkNewsItem> items() {
    return items;
  }

  public List<VkUser> users() {
    return users;
  }

  public List<VkGroup> groups() {
    return groups;
  }

  public String nextFrom() {
    return nextFrom;
  }
}
