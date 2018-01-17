package nl.kantilever.webwinkel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;

public class Leverancier {

  @JsonProperty("leverancierCode")
  @Column(name = "leverancierCode")
  private String leverancierCode;

  @JsonProperty("leverancier")
  @Column(name = "leverancier")
  private String leverancier;

  public Leverancier(String leverancier, String leverancierCode) {
    this.leverancier = leverancier;
    this.leverancierCode = leverancierCode;
  }

}
