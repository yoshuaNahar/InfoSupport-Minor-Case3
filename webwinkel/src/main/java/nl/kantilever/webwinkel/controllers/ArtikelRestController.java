package nl.kantilever.webwinkel.controllers;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.services.ArtikelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

/**
 * Created by Tinne on 20-12-2017.
 */

@RestController
public class ArtikelRestController {
  private ArtikelService artikelService;

  @Autowired
  public ArtikelRestController(ArtikelService artikelService) {
    this.artikelService = artikelService;
  }

  @RequestMapping(value = "artikel/{artikelnummer}", method = RequestMethod.GET)
  public Artikel getSpecificArtikel(Model model, @PathVariable int artikelnummer) {
    //Artikel artikel = artikelService.findArtikelenByArtikelnummer(artikelnummer);
    Artikel artikel = new Artikel(115, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(55000), new Date(333333), 1989, "Henk & Nagel B.V.", Arrays.asList(new Categorie("Onderdelen"), new Categorie("roestvrijstaal"), new Categorie("sup3")));

    return artikel;
  }

  @RequestMapping(value = "artikel/all}", method = RequestMethod.GET)
  public List<Artikel> getAllArtikelen(Model model, @PathVariable int artikelnummer) {
    //Artikel artikel = artikelService.findArtikelenByArtikelnummer(artikelnummer);
    Artikel artikel = new Artikel(115, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(55000), new Date(333333), 1989, "Henk & Nagel B.V.", Arrays.asList(new Categorie("Onderdelen"), new Categorie("roestvrijstaal"), new Categorie("sup3")));

    List<Artikel> artikelList = new ArrayList<Artikel>();
    artikelList.add(artikel);
    artikelList.add(artikel);
    artikelList.add(artikel);
    artikelList.add(artikel);

    return artikelList;
  }
}
