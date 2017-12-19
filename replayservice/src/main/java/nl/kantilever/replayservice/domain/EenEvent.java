package nl.kantilever.replayservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EenEvent {

  private Integer Id;
  private String CorrelationId;

  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
  }

  public String getCorrelationId() {
    return CorrelationId;
  }

  public void setCorrelationId(String correlationId) {
    CorrelationId = correlationId;
  }

  @Override
  public String toString() {
    return "EenEvent{" +
      "Id=" + Id +
      ", CorrelationId='" + CorrelationId + '\'' +
      '}';
  }
}
