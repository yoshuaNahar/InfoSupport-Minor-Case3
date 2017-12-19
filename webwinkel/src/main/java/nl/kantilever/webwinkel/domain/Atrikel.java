package nl.kantilever.webwinkel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Tinne on 18-12-2017.
 */

@Entity
@Table(name ="articles")
@Getter
@Setter
@NoArgsConstructor
public class Atrikel {
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
  String leverancierCode;

  @Column(name = "leverancier")
  String leverancier;

  @Column(name = "categorieen")
  List<String> categorieen;

}
