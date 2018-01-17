package nl.kantilever.bestellingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gebruiker")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gebruiker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long gebruikerId;

  @Column(name = "voornaam")
  private String voornaam;

  @Column(name = "achternaam")
  private String achternaam;

  @Column(name = "adres")
  private String adres;

  @Column(name = "bedrijfNaam")
  private String bedrijfNaam;

  @Column(name = "telefoonNummer")
  private String telefoonNummer;

  @Column(name = "maxCrediet")
  private int maxCrediet;

  @Column (name = "huidigCrediet")
  private int huidigCrediet;

  public long getGebruikerId() {
    return gebruikerId;
  }

  public void setGebruikerId(long gebruikerId) {
    this.gebruikerId = gebruikerId;
  }

  public String getVoornaam() {
    return voornaam;
  }

  public void setVoornaam(String voornaam) {
    this.voornaam = voornaam;
  }

  public String getAchternaam() {
    return achternaam;
  }

  public void setAchternaam(String achternaam) {
    this.achternaam = achternaam;
  }

  public String getAdres() {
    return adres;
  }

  public void setAdres(String adres) {
    this.adres = adres;
  }

  public String getBedrijfNaam() {
    return bedrijfNaam;
  }

  public void setBedrijfNaam(String bedrijfNaam) {
    this.bedrijfNaam = bedrijfNaam;
  }

  public String getTelefoonNummer() {
    return telefoonNummer;
  }

  public void setTelefoonNummer(String telefoonNummer) {
    this.telefoonNummer = telefoonNummer;
  }

  public int getMaxCrediet() {
    return maxCrediet;
  }

  public void setMaxCrediet(int maxCrediet) {
    this.maxCrediet = maxCrediet;
  }

  public int getHuidigCrediet() {
    return huidigCrediet;
  }

  public void setHuidigCrediet(int huidigCrediet) {
    this.huidigCrediet = huidigCrediet;
  }

  public Gebruiker(long gebruikerId, String voornaam, String achternaam) {
    this.gebruikerId = gebruikerId;
    this.voornaam = voornaam;
    this.achternaam = achternaam;
  }

  public Gebruiker() {

  }

  @Override
  public String toString() {
    return "Gebruiker{" +
      "gebruikerId=" + gebruikerId +
      ", voornaam='" + voornaam + '\'' +
      ", achternaam='" + achternaam + '\'' +
      ", adres='" + adres + '\'' +
      ", bedrijfNaam='" + bedrijfNaam + '\'' +
      ", telefoonNummer='" + telefoonNummer + '\'' +
      ", maxCrediet=" + maxCrediet +
      ", huidigCrediet=" + huidigCrediet +
      '}';
  }
}
