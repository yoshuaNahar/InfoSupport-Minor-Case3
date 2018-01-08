package nl.kantilever.webwinkel.domain;

import lombok.AllArgsConstructor;
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
//@AllArgsConstructor
public class Artikel {
  @Id
  @Column(name = "artikelnummer", unique = true)
  private long artikelnummer;

  @Column(name = "naam")
  private String naam;

  @Column(name = "beschrijving")
  private String beschrijving;

  @Column(name = "prijs")
  private double prijs;

  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @Column(name = "leverbaarVanaf")
  private Date leverbaarVanaf;

  @Column(name = "leverbaarTot")
  private Date leverbaarTot;

  @Column(name = "leverancierCode")
  private int leverancierCode;

  @Column(name = "leverancier")
  private String leverancier;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Categorie> categorieen;

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

  public int getLeverancierCode() {
    return leverancierCode;
  }

  public void setLeverancierCode(int leverancierCode) {
    this.leverancierCode = leverancierCode;
  }

  public String getLeverancier() {
    return leverancier;
  }

  public void setLeverancier(String leverancier) {
    this.leverancier = leverancier;
  }

  public List<Categorie> getCategorieen() {
    return categorieen;
  }

  public void setCategorieen(List<Categorie> categorieen) {
    this.categorieen = categorieen;
  }

  public Artikel(long artikelnummer, String naam, String beschrijving, double prijs, String afbeeldingURL, Date leverbaarVanaf, Date leverbaarTot, int leverancierCode, String leverancier, List<Categorie> categorieen) {
    this.artikelnummer = artikelnummer;
    this.naam = naam;
    this.beschrijving = beschrijving;
    this.prijs = prijs;
    this.afbeeldingURL = afbeeldingURL;
    this.leverbaarVanaf = leverbaarVanaf;
    this.leverbaarTot = leverbaarTot;
    this.leverancierCode = leverancierCode;
    this.leverancier = leverancier;
    this.categorieen = categorieen;
  }
}

