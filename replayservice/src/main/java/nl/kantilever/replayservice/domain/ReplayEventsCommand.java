package nl.kantilever.replayservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplayEventsCommand {
  private String ExchangeName;
  private long FromTimestamp;
  private long ToTimestamp;
  private String EventType;
  private String Topic;

  public ReplayEventsCommand(String exchangeName, long fromTimestamp, long toTimestamp, String eventType, String topic) {
    this.ExchangeName = exchangeName;
    this.FromTimestamp = fromTimestamp;
    this.ToTimestamp = toTimestamp;
    this.EventType = eventType;
    this.Topic = topic;
  }
  public ReplayEventsCommand() {
  }
}
