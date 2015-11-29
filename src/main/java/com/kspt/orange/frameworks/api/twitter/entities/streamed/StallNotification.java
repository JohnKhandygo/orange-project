package com.kspt.orange.frameworks.api.twitter.entities.streamed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.frameworks.api.twitter.sources.TwitterStreamedData;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class StallNotification implements TwitterStreamedData {

  private final StallMessage stallMessage;

  @JsonCreator
  public StallNotification(
      final @JsonProperty("warning") StallMessage stallMessage) {
    this.stallMessage = stallMessage;
  }

  public StallMessage stallMessage() {
    return stallMessage;
  }

  @Override
  public String toString() {
    return "StallNotification{" +
        "stallMessage=" + stallMessage +
        '}';
  }

  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      getterVisibility = JsonAutoDetect.Visibility.NONE,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE,
      setterVisibility = JsonAutoDetect.Visibility.NONE,
      creatorVisibility = JsonAutoDetect.Visibility.NONE)
  public static class StallMessage {
    private final String code;

    private final String message;

    private final int percentFull;

    @JsonCreator
    public StallMessage(
        final @JsonProperty("code") String code,
        final @JsonProperty("message") String message,
        final @JsonProperty("percent_full") int percentFull) {
      this.code = code;
      this.message = message;
      this.percentFull = percentFull;
    }

    @Override
    public String toString() {
      return "StallMessage{" +
          "code='" + code + '\'' +
          ", message='" + message + '\'' +
          ", percentFull=" + percentFull +
          '}';
    }
  }
}
