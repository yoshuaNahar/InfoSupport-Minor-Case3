package nl.kantilever.bestellingservice.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gebruiker")
public class Gebruiker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String gebruikerId;

  private String voornaam;

  private String achternaam;

  private String adres;

  private String bedrijfNaam;

  private String telefoonNummer;

  @OneToMany
  private List<BestellingSnapshot> bestellingSnapshots;

  public String getGebruikerId() {
    return gebruikerId;
  }

  public void setGebruikerId(String gebruikerId) {
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

  public List<BestellingSnapshot> getBestellingSnapshots() {
    return bestellingSnapshots;
  }

  public void setBestellingSnapshots(
    List<BestellingSnapshot> bestellingSnapshots) {
    this.bestellingSnapshots = bestellingSnapshots;
  }

  @Override
  public String toString() {
    return "Gebruiker{" +
      "gebruikerId='" + gebruikerId + '\'' +
      ", voornaam='" + voornaam + '\'' +
      ", achternaam='" + achternaam + '\'' +
      ", adres='" + adres + '\'' +
      ", bedrijfNaam='" + bedrijfNaam + '\'' +
      ", telefoonNummer='" + telefoonNummer + '\'' +
      ", bestellingSnapshots=" + bestellingSnapshots +
      '}';
  }
}
