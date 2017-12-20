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
  @Column(name = "artikelnummer")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long artikelnummer;

  @Column(name = "naam")
  String naam;

  @Column(name = "beschrijving")
  String beschrijving;

  @Column(name = "prijs")
  double prijs;

  @Column(name = "afbeeldingURL")
  String afbeeldingURL;

  @Column(name = "leverbaarVanaf")
  Date leverbaarVanaf;

  @Column(name = "leverbaarTot")
  Date leverbaarTot;

  @Column(name = "leverancierCode")
  int leverancierCode;

  @Column(name = "leverancier")
  String leverancier;

  @OneToMany
  @JoinColumn(name = "categorie_naam")
  List<Categorie> categorieen;
}

