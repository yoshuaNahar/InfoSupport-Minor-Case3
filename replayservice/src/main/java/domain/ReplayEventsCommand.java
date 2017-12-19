package domain;

public class ReplayEventsCommand {
  public String ExchangeName;
  public long FromTimestamp;
  public long ToTimestamp;
  public String EventType;
  public String Topic;

  public String getExchangeName() {
    return ExchangeName;
  }

  public void setExchangeName(String exchangeName) {
    ExchangeName = exchangeName;
  }

  public long getFromTimestamp() {
    return FromTimestamp;
  }

  public void setFromTimestamp(long fromTimestamp) {
    FromTimestamp = fromTimestamp;
  }

  public long getToTimestamp() {
    return ToTimestamp;
  }

  public void setToTimestamp(long toTimestamp) {
    ToTimestamp = toTimestamp;
  }

  public String getEventType() {
    return EventType;
  }

  public void setEventType(String eventType) {
    EventType = eventType;
  }

  public String getTopic() {
    return Topic;
  }

  public void setTopic(String topic) {
    Topic = topic;
  }

}
