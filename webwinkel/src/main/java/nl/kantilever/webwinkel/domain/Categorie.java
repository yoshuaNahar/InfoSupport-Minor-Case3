package nl.kantilever.webwinkel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorieen")
public class Categorie {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "naam", unique = true)
  private String naam;

  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorieen")
  private List<Artikel> artikelen;

  public Categorie() {
  }

  public Categorie(String naam) {
    this.naam = naam;
  }

  public Categorie(String naam, String afbeeldingURL) {
    this.naam = naam;
    this.afbeeldingURL = afbeeldingURL;
  }

  public Categorie(String naam, String afbeeldingURL,
    List<Artikel> artikelen) {
    this.naam = naam;
    this.afbeeldingURL = afbeeldingURL;
    this.artikelen = artikelen;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNaam() {
    return naam;
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public String getAfbeeldingURL() {
    return afbeeldingURL;
  }

  public void setAfbeeldingURL(String afbeeldingURL) {
    this.afbeeldingURL = afbeeldingURL;
  }

  public List<Artikel> getArtikelen() {
    return artikelen;
  }

  public void setArtikelen(List<Artikel> artikelen) {
    this.artikelen = artikelen;
  }

  @Override
  public String toString() {
    return "Categorie{" +
      "id=" + id +
      ", naam='" + naam + '\'' +
      ", afbeeldingURL='" + afbeeldingURL + '\'' +
      '}';
  }

}
