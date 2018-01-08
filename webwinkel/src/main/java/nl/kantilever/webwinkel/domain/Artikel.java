package nl.kantilever.webwinkel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "artikelen")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artikel {
  @Id
  @Column(name = "artikelnummer")
  @JsonProperty("Artikelnummer") @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Categorie> categorieen;

  public Artikel() {
  }

  public Artikel(String naam, String beschrijving, double prijs, String afbeeldingURL,
    Date leverbaarVanaf, Date leverbaarTot, int leverancierCode, String leverancier,
    List<Categorie> categorieen) {
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

  public Artikel(Long artikelnummer, String naam, String beschrijving, double prijs, String afbeeldingURL,
    Date leverbaarVanaf, Date leverbaarTot, int leverancierCode, String leverancier,
    List<Categorie> categorieen) {
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
      ", leverancierCode=" + leverancierCode +
      ", leverancier='" + leverancier + '\'' +
      ", categorieen=" + categorieen +
      '}';
  }

}

