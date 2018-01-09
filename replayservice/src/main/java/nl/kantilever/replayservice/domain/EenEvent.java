package nl.kantilever.replayservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EenEvent {


  private Integer id;
  private Long timestamp;
  private String correlationId;
  private String routingKey;
  private String eventType;
  private String jsonMessage;

  @JsonProperty("Id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("CorrelationId")
  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  @JsonProperty("Timestamp")
  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @JsonProperty("RoutingKey")
  public String getRoutingKey() {
    return routingKey;
  }

  public void setRoutingKey(String routingKey) {
    this.routingKey = routingKey;
  }

  @JsonProperty("EventType")
  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  @JsonProperty("JsonMessage")
  public String getJsonMessage() {
    return jsonMessage;
  }

  public void setJsonMessage(String jsonMessage) {
    this.jsonMessage = jsonMessage;
  }

  @Override
  public String toString() {
    return "EenEvent{" +
      "id=" + id +
      ", timestamp=" + timestamp +
      ", correlationId='" + correlationId + '\'' +
      ", routingKey='" + routingKey + '\'' +
      ", eventType='" + eventType + '\'' +
      ", jsonMessage='" + jsonMessage + '\'' +
      '}';
  }
}
