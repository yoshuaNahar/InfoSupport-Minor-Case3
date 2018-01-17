package nl.kantilever.replayservice.domain;

public class ReplayEventsCommand {

  private String exchangeName;
  private long fromTimestamp;
  private long toTimestamp;
  private String eventType;
  private String topic;

  public ReplayEventsCommand() {
    this.exchangeName = "KantileverBus";
    this.fromTimestamp = 0;
    this.toTimestamp = 999999999999999999L;
    this.eventType = "Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd";
    this.topic = "#";
  }

  public String getExchangeName() {
    return exchangeName;
  }

  public void setExchangeName(String exchangeName) {
    this.exchangeName = exchangeName;
  }

  public long getFromTimestamp() {
    return fromTimestamp;
  }

  public void setFromTimestamp(long fromTimestamp) {
    this.fromTimestamp = fromTimestamp;
  }

  public long getToTimestamp() {
    return toTimestamp;
  }

  public void setToTimestamp(long toTimestamp) {
    this.toTimestamp = toTimestamp;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  @Override
  public String toString() {
    return "ReplayEventsCommand{" +
      "exchangeName='" + exchangeName + '\'' +
      ", fromTimestamp=" + fromTimestamp +
      ", toTimestamp=" + toTimestamp +
      ", eventType='" + eventType + '\'' +
      ", topic='" + topic + '\'' +
      '}';
  }

}
