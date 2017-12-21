package nl.kantilever.bestellingservice.entities;

import java.sql.Date;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="articles")
public class Artikel {

  @Id
  @Column(name = "artikelnummer")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long artikelnummer;

  @Column(name = "naam")
  private String naam;

  @Column(name = "beschrijving")
  private String beschrijving;

  @Column(name = "prijs")
  private Double prijs;

  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @Column(name = "leverbaarVanaf")
  private Date leverbaarVanaf;

  @Column(name = "leverbaarTot")
  private Date leverbaarTot;

  @Column(name = "leverancierCode")
  private String leverancierCode;

  @Column(name = "leverancier")
  private String leverancier;

  @Column(name = "categorieen")
  private ArrayList<String> categorieen;

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

  public Date getLeverbaarVanaf() {
    return leverbaarVanaf;
  }

  public void setLeverbaarVanaf(Date leverbaarVanaf) {
    this.leverbaarVanaf = leverbaarVanaf;
  }

  public Date getLeverbaarTot() {
    return leverbaarTot;
  }

  public void setLeverbaarTot(Date leverbaarTot) {
    this.leverbaarTot = leverbaarTot;
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

  public ArrayList<String> getCategorieen() {
    return categorieen;
  }

  public void setCategorieen(ArrayList<String> categorieen) {
    this.categorieen = categorieen;
  }

  @Override
  public String toString() {
    return "Artikel{" +
      "artikelnummer=" + artikelnummer +
      ", naam='" + naam + '\'' +
      ", beschrijving='" + beschrijving + '\'' +
      ", prijs=" + prijs +
      ", afbeeldingURL='" + afbeeldingURL + '\'' +
      ", leverbaarVanaf=" + leverbaarVanaf +
      ", leverbaarTot=" + leverbaarTot +
      ", leverancierCode='" + leverancierCode + '\'' +
      ", leverancier='" + leverancier + '\'' +
      ", categorieen=" + categorieen +
      '}';
  }

}
