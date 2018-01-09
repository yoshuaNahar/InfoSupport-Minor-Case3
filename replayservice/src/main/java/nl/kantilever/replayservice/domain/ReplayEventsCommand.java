package nl.kantilever.replayservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplayEventsCommand {
  private String exchangeName;
  private long fromTimestamp;
  private long toTimestamp;
  private String eventType;
  private String topic;

  public ReplayEventsCommand(String exchangeName, long fromTimestamp, long toTimestamp, String eventType, String topic) {
    this.exchangeName = exchangeName;
    this.fromTimestamp = fromTimestamp;
    this.toTimestamp = toTimestamp;
    this.eventType = eventType;
    this.topic = topic;
  }
  public ReplayEventsCommand() {
    this.exchangeName = "KantileverBus";
    this.fromTimestamp = 0;
    this.toTimestamp= 999999999999999999L;
    this.eventType = "Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd";
    this.topic = "#";
  }
}
