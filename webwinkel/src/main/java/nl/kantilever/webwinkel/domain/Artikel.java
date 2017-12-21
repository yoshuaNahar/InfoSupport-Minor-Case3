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
@AllArgsConstructor
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
}

