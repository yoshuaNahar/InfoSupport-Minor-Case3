package nl.kantilever.webwinkel.controllers;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.domain.Leverancier;
import nl.kantilever.webwinkel.services.ArtikelService;
import nl.kantilever.webwinkel.services.CategorieService;
import nl.kantilever.webwinkel.services.LeverancierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tinne on 20-12-2017.
 */

@CrossOrigin
@RestController
public class ArtikelRestController {
  private ArtikelService artikelService;
  private CategorieService categorieService;
  private LeverancierService leverancierService;

  @Autowired
  public ArtikelRestController(ArtikelService artikelService, CategorieService categorieService, LeverancierService leverancierService) {
    this.artikelService = artikelService;
    this.categorieService = categorieService;
    this.leverancierService = leverancierService;
  }

  @RequestMapping(value = "artikel/artikelnummer/{artikelnummer}", method = RequestMethod.GET)
  public Artikel findArtikelByArtikelnummer(Model model, @PathVariable int artikelnummer) {
    Artikel artikel = artikelService.findArtikelByArtikelnummer(artikelnummer);
    return artikel;
  }

  @RequestMapping(value = "artikelen/naam/{naam}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenByNaam(Model model, @PathVariable String naam) {
    return artikelService.findArtikelenByNaam(naam);
  }

  @RequestMapping(value = "artikelen/leverancier/{leverancier}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenByLeverancier(Model model, @PathVariable String leverancier) {
    return artikelService.findArtikelenByLeverancier(leverancier);
  }

  @RequestMapping(value = "categorieen", method = RequestMethod.GET)
  public List<Categorie> findAllCategorieen(Model model) {
    return categorieService.findAll();
  }

  @RequestMapping(value = "artikelen/categorie/{categorie}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenByCategorie(Model model, @PathVariable String categorie) {
    Categorie matchendeCategorie = categorieService.findCategorieByNaam(categorie);
    List<Artikel> artikelLijst = new ArrayList<Artikel>();

    if (matchendeCategorie != null) {
      artikelLijst = categorieService.findCategorieByNaam(matchendeCategorie.getNaam()).getArtikelen();
    } else {
      System.out.println("Categorie not found");
    }

    return artikelLijst;
  }

  @RequestMapping(value = "categorie/naam/{naam}", method = RequestMethod.GET)
  public Categorie findCategorieByNaam(Model model, @PathVariable String naam) {
    return categorieService.findCategorieByNaam(naam);
  }


  @RequestMapping(value = "artikelen/beschrijving/{beschrijving}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenByBeschrijving(Model model, @PathVariable String beschrijving) {
    return artikelService.findArtikelenByBeschrijving(beschrijving);
  }

  @RequestMapping(value = "artikelen/pricerange/{minPrice}/{maxPrice}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenInPriceRange(Model model, @PathVariable final double minPrice, @PathVariable final double maxPrice) {
    return artikelService.findArtikelenInPriceRange(minPrice, maxPrice);
  }

  @RequestMapping(value = "artikelen/leverbaar_vanaf/{leverbaar_vanaf}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenLeverbaarVanaf(Model model, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") java.util.Date leverbaar_vanaf) {
    return artikelService.findArtikelenLeverbaarVanaf(leverbaar_vanaf);
  }

  @RequestMapping(value = "artikelen/leverbaar_tot/{leverbaar_tot}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenLeverbaarTot(Model model, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") java.util.Date leverbaar_tot) {
    return artikelService.findArtikelenLeverbaarTot(leverbaar_tot);
  }

  @RequestMapping(value= "leveranciers", method=RequestMethod.GET)
  public List<Leverancier> findAllLeveranciers () {
    return leverancierService.findAllLeveranciers();
  }
}
