package nl.kantilever.bestellingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "gebruiker")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gebruiker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long gebruikerId;

  private String aanhef;

  private String voornaam;

  private String tussenvoegsel;

  private String achternaam;

  private String straatnaam;

  private int huisnummer;

  private String postcode;

  private String stad;

  private String land;

  private String telefoonnummer;

  private int maxKrediet = 500; // default value

  private int huidigKrediet = 0; // default value


  public Gebruiker(long gebruikerId, String voornaam, String achternaam) {
    this.gebruikerId = gebruikerId;
    this.voornaam = voornaam;
    this.achternaam = achternaam;
  }

  public Gebruiker() {

  }

  public long getGebruikerId() {
    return gebruikerId;
  }

  public void setGebruikerId(long gebruikerId) {
    this.gebruikerId = gebruikerId;
  }

  public String getAanhef() {
    return aanhef;
  }

  public void setAanhef(String aanhef) {
    this.aanhef = aanhef;
  }

  public String getVoornaam() {
    return voornaam;
  }

  public void setVoornaam(String voornaam) {
    this.voornaam = voornaam;
  }

  public String getTussenvoegsel() {
    return tussenvoegsel;
  }

  public void setTussenvoegsel(String tussenvoegsel) {
    this.tussenvoegsel = tussenvoegsel;
  }

  public String getAchternaam() {
    return achternaam;
  }

  public void setAchternaam(String achternaam) {
    this.achternaam = achternaam;
  }

  public String getStraatnaam() {
    return straatnaam;
  }

  public void setStraatnaam(String straatnaam) {
    this.straatnaam = straatnaam;
  }

  public int getHuisnummer() {
    return huisnummer;
  }

  public void setHuisnummer(int huisnummer) {
    this.huisnummer = huisnummer;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getStad() {
    return stad;
  }

  public void setStad(String stad) {
    this.stad = stad;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getTelefoonnummer() {
    return telefoonnummer;
  }

  public void setTelefoonnummer(String telefoonnummer) {
    this.telefoonnummer = telefoonnummer;
  }

  public int getMaxKrediet() {
    return maxKrediet;
  }

  public void setMaxKrediet(int maxKrediet) {
    this.maxKrediet = maxKrediet;
  }

  public int getHuidigKrediet() {
    return huidigKrediet;
  }

  public void setHuidigKrediet(int huidigKrediet) {
    this.huidigKrediet = huidigKrediet;
  }

  @Override
  public String toString() {
    return "Gebruiker{" +
      "gebruikerId=" + gebruikerId +
      ", aanhef='" + aanhef + '\'' +
      ", voornaam='" + voornaam + '\'' +
      ", tussenvoegsel='" + tussenvoegsel + '\'' +
      ", achternaam='" + achternaam + '\'' +
      ", straatnaam='" + straatnaam + '\'' +
      ", huisnummer=" + huisnummer +
      ", postcode='" + postcode + '\'' +
      ", stad='" + stad + '\'' +
      ", land='" + land + '\'' +
      ", telefoonnummer='" + telefoonnummer + '\'' +
      ", maxKrediet=" + maxKrediet +
      ", huidigKrediet=" + huidigKrediet +
      '}';
  }
}
