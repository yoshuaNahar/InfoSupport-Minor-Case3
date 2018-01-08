package nl.kantilever.webwinkel.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.services.ArtikelService;
import nl.kantilever.webwinkel.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Tinne on 20-12-2017.
 */

@CrossOrigin
@RestController
public class ArtikelRestController {
  private ArtikelService artikelService;
  private CategorieService categorieService;

  @Autowired
  public ArtikelRestController(ArtikelService artikelService, CategorieService categorieService) {
    this.artikelService = artikelService;
    this.categorieService = categorieService;

    try { //TEMP SAVE CODE
      categorieService.save(new Categorie("Onderdelen", "fork_small.gif"));
      categorieService.save(new Categorie("Roestvrijstaal", "no_image_available_small.gif"));
      artikelService.save(
        new Artikel(115L,
          "Fietsketting",
          "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.",
          79.50,
          "silver_chain_small.gif",
          new Date(5),
          new Date(1999999999),
          1989,
          "Henk & Nagel B.V.",
          Arrays.asList(categorieService.findAll().get(0), categorieService.findAll().get(1))));
    } catch (Exception e) {
      System.out.println("Temp data is al aangemaakt");
    } //TEMP SAVE CODE
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
      artikelLijst = categorieService.findCategorieByNaam(categorie).getArtikellen();
    } else {
      System.out.println("Categorie not found");
    }

    return artikelLijst;
  }

  @RequestMapping(value = "categorie/naam/{naam}", method = RequestMethod.GET)
  public Categorie findCategorieByNaam(Model model, @PathVariable String naam) {
    return categorieService.findCategorieByNaam(naam);
  }
}
