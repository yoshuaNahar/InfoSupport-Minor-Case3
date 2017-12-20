package nl.kantilever.webwinkel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tinne on 18-12-2017.
 */

@Entity
@Table(name ="articles")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artikel {
  @Id
  @Column(name = "artikelnummer")
  @JsonProperty("Artikelnummer")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long artikelnummer;

  @JsonProperty("Naam")
  @Column(name = "naam")
  String naam;

  @JsonProperty("Beschrijving")
  @Column(name = "beschrijving")
  String beschrijving;

  @JsonProperty("Prijs")
  @Column(name = "prijs")
  double prijs;

  @JsonProperty("AfbeeldingUrl")
  @Column(name = "afbeeldingURL")
  String afbeeldingURL;

  @JsonProperty("LeverbaarVanaf")
  @Column(name = "leverbaarVanaf")
  Date leverbaarVanaf;

  @JsonProperty("LeverbaarTot")
  @Column(name = "leverbaarTot")
  Date leverbaarTot;

  @JsonProperty("Leveranciercode")
  @Column(name = "leverancierCode")
  String leverancierCode;

  @JsonProperty("Leverancier")
  @Column(name = "leverancier")
  String leverancier;

  @JsonProperty("Categorieen")
  @Column(name = "categorieen")
  ArrayList<String> categorieen;

}
