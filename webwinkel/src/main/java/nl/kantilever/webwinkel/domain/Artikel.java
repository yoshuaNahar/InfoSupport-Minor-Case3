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
@Table(name = "artikelen")
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
  private String naam;

  @JsonProperty("Beschrijving")
  @Column(name = "beschrijving")
  private String beschrijving;

  @JsonProperty("Prijs")
  @Column(name = "prijs")
  private double prijs;

  @JsonProperty("AfbeeldingUrl")
  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @JsonProperty("LeverbaarVanaf")
  @Column(name = "leverbaarVanaf")
  private Date leverbaarVanaf;

  @JsonProperty("LeverbaarTot")
  @Column(name = "leverbaarTot")
  private Date leverbaarTot;

  @JsonProperty("Leveranciercode")
  @Column(name = "leverancierCode")
  private int leverancierCode;

  @JsonProperty("Leverancier")
  @Column(name = "leverancier")
  String leverancier;

  @JsonProperty("Categorieen")
  @Column(name = "categorieen")
  ArrayList<String> categorieen;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Categorie> categorieen;
}

