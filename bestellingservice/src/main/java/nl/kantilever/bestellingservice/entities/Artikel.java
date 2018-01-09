package nl.kantilever.bestellingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artikel")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artikel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "artikelnummer")
  @JsonProperty("Artikelnummer")
  private Long artikelnummer;

  @Column(name = "naam")
  @JsonProperty("Naam")
  private String naam;

  @JsonProperty("Beschrijving")
  @Column(name = "beschrijving")
  private String beschrijving;

  @JsonProperty("Prijs")
  @Column(name = "prijs")
  private Double prijs;

  @JsonProperty("AfbeeldingUrl")
  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @JsonProperty("Leveranciercode")
  @Column(name = "leverancierCode")
  private String leverancierCode;

  @JsonProperty("Leverancier")
  @Column(name = "leverancier")
  private String leverancier;

  public Artikel() {
    // Needed by JPA
  }

  public long getArtikelnummer() {
    return artikelnummer;
  }

  public void setArtikelnummer(long artikelnummer) {
    this.artikelnummer = artikelnummer;
  }

  public String getNaam() {
    return naam;
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public String getBeschrijving() {
    return beschrijving;
  }

  public void setBeschrijving(String beschrijving) {
    this.beschrijving = beschrijving;
  }

  public double getPrijs() {
    return prijs;
  }

  public void setPrijs(double prijs) {
    this.prijs = prijs;
  }

  public String getAfbeeldingURL() {
    return afbeeldingURL;
  }

  public void setAfbeeldingURL(String afbeeldingURL) {
    this.afbeeldingURL = afbeeldingURL;
  }

  public String getLeverancierCode() {
    return leverancierCode;
  }

  public void setLeverancierCode(String leverancierCode) {
    this.leverancierCode = leverancierCode;
  }

  public String getLeverancier() {
    return leverancier;
  }

  public void setLeverancier(String leverancier) {
    this.leverancier = leverancier;
  }

  @Override
  public String toString() {
    return "Artikel{" +
      "artikelnummer=" + artikelnummer +
      ", naam='" + naam + '\'' +
      ", beschrijving='" + beschrijving + '\'' +
      ", prijs=" + prijs +
      ", afbeeldingURL='" + afbeeldingURL + '\'' +
      ", leverancierCode='" + leverancierCode + '\'' +
      ", leverancier='" + leverancier + '\'' +
      '}';
  }

}
