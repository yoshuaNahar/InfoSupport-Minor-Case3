package nl.kantilever.accountservice.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Gebruiker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

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

  private double maxKrediet = 500.0; // default value

  private double huidigKrediet = 0.0; // default value

  @OneToOne
  @Cascade({org.hibernate.annotations.CascadeType.ALL})
  private Account account;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public void setStraatnaam(String staatnaam) {
    this.straatnaam = staatnaam;
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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public double getMaxKrediet() {
    return maxKrediet;
  }

  public void setMaxKrediet(double maxKrediet) {
    this.maxKrediet = maxKrediet;
  }

  public double getHuidigKrediet() {
    return huidigKrediet;
  }

  public void setHuidigKrediet(double huidigKrediet) {
    this.huidigKrediet = huidigKrediet;
  }

  @Override
  public String toString() {
    return "Gebruiker{" +
      "id='" + id + '\'' +
      ", voornaam='" + voornaam + '\'' +
      ", tussenvoegsel='" + tussenvoegsel + '\'' +
      ", achternaam='" + achternaam + '\'' +
      ", straatnaam='" + straatnaam + '\'' +
      ", huisnummer=" + huisnummer +
      ", postcode='" + postcode + '\'' +
      ", stad='" + stad + '\'' +
      ", land='" + land + '\'' +
      ", telefoonnummer='" + telefoonnummer + '\'' +
      ", account=" + account +
      '}';
  }

}
