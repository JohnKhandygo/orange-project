package com.kspt.orange.frameworks.api.twitter.entities.streamed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.frameworks.api.twitter.sources.TwitterStreamedData;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DeletionNotification implements TwitterStreamedData {

  private final DeletionStatus status;

  @JsonCreator
  public DeletionNotification(final @JsonProperty("status") DeletionStatus status) {
    this.status = status;
  }

  public DeletedData deleted() {
    return status.deleted();
  }

  @Override
  public String toString() {
    return "DeletionNotification{" +
        "status=" + status +
        '}';
  }

  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      getterVisibility = JsonAutoDetect.Visibility.NONE,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE,
      setterVisibility = JsonAutoDetect.Visibility.NONE,
      creatorVisibility = JsonAutoDetect.Visibility.NONE)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class DeletionStatus {
    private final DeletedData deleted;

    @JsonCreator
    public DeletionStatus(final @JsonProperty("delete") DeletedData deleted) {
      this.deleted = deleted;
    }

    public DeletedData deleted() {
      return deleted;
    }

    @Override
    public String toString() {
      return "DeletionStatus{" +
          "deleted=" + deleted +
          '}';
    }
  }

  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      getterVisibility = JsonAutoDetect.Visibility.NONE,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE,
      setterVisibility = JsonAutoDetect.Visibility.NONE,
      creatorVisibility = JsonAutoDetect.Visibility.NONE)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class DeletedData {
    private final long id;

    private final long author;

    @JsonCreator
    public DeletedData(
        final @JsonProperty("id") long id,
        final @JsonProperty("user_id") long author) {
      this.id = id;
      this.author = author;
    }

    public long id() {
      return id;
    }

    public long author() {
      return author;
    }

    @Override
    public String toString() {
      return "DeletedData{" +
          "id=" + id +
          ", author=" + author +
          '}';
    }
  }
}
