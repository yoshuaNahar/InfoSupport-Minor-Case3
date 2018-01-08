package nl.kantilever.webwinkel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tinne on 20-12-2017.
 */

@Entity
@Table(name = "categorieen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name = "naam", unique = true)
  private String naam;

  @Column(name = "afbeeldingURL")
  private String afbeeldingURL;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorieen")
  private List<Artikel> artikellen;

  public Categorie(String naam) {
    this.naam = naam;
  }

  public Categorie(String naam, String afbeeldingURL) {
    this.naam = naam;
    this.afbeeldingURL = afbeeldingURL;
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

  public List<Artikel> getArtikellen() {
    return artikellen;
  }

  public void setArtikellen(List<Artikel> artikellen) {
    this.artikellen = artikellen;
  }
}
