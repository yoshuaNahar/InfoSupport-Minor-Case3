package nl.kantilever.webwinkel.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.domain.Leverancier;
import nl.kantilever.webwinkel.domain.ZoekCriteriaBuilder;
import nl.kantilever.webwinkel.services.ArtikelService;
import nl.kantilever.webwinkel.services.CategorieService;
import nl.kantilever.webwinkel.services.LeverancierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ArtikelRestController {

  private static final Logger logger = LoggerFactory.getLogger(ArtikelRestController.class);

  private ArtikelService artikelService;
  private CategorieService categorieService;
  private LeverancierService leverancierService;

  @Autowired
  public ArtikelRestController(ArtikelService artikelService, CategorieService categorieService,
    LeverancierService leverancierService) {
    this.artikelService = artikelService;
    this.categorieService = categorieService;
    this.leverancierService = leverancierService;
  }

  /**
   * De regex string split de input (key, operator, waarde(n)) op in 3 delen.
   * Deel 1 is de key, deel 2 is de operator en deel 3 zijn de waarden.
   * Eerst wordt alles (alle letters, cijfers en symbolen) lazy geselecteerd tot aan de operator.
   * Vervolgens wordt de operator zelf geselecteerd.
   * Tot slot worden alle letters, cijfers en symbolen na de operator geselecteerd.
   * Na het selecteren van de waarde(n) worden deze opnieuw gesplit om voor iedere waarde
   * een nieuw zoekcriterium toe te voegen aan de builder.
   */
  @RequestMapping(method = RequestMethod.GET, value = "/artikelen")
  @ResponseBody
  public List<Artikel> search(@RequestParam(value = "zoeken") String zoekString) {
    ZoekCriteriaBuilder builder = new ZoekCriteriaBuilder();
    String[] filters = zoekString
      .split(";"); //sla de verschillende zoekmogelijkheden op met hun waarden

    for (int i = 0; i < filters.length; i++) {
      Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w.*)");
      Matcher matcher = pattern.matcher(filters[i]); //Splits de zoekfilters op

      while (matcher.find()) { //Loop door alle keys met bijbehorende operator en waarden
        String waarden = matcher
          .group(3); //Onthoud de waarde, hier zitten mogelijk meerdere waarden in
        String[] waardenArray = waarden.split(","); //Split de waarden van de huidige key op

        for (int j = 0; j < waardenArray.length; j++) {
          if ("categorieen".equals(matcher.group(1))) {
            Categorie potentialCategorie = categorieService.findCategorieByNaam(waardenArray[j]);
            if (potentialCategorie != null) {
              potentialCategorie
                .setArtikelen(new ArrayList<Artikel>()); //Nodig om overflow te voorkomen
              builder.voegCategorieToe(potentialCategorie);
            } else {
              logger.info("Categorie in de zoekfilter kan niet worden gevonden");
            }
          } else if ("prijs".equals(matcher.group(1)) && "<".equals(matcher.group(2))) {
            builder.setMinPrice(waardenArray[j]);
          } else if ("prijs".equals(matcher.group(1)) && ">".equals(matcher.group(2))) {
            builder.setMaxPrice(waardenArray[j]);
          } else {
            builder.voegZoekCriteriumToe(matcher.group(1), matcher.group(2),
              waardenArray[j]); //Zoekfilter bestaat altijd uit 3 delen (key, operator, waarde)
          }
        }
      }
    }

    Specification<Artikel> spec = builder.build();
    return artikelService.findAllBySpec(spec);
  }

  @RequestMapping(value = "artikel/artikelnummer/{artikelnummer}", method = RequestMethod.GET)
  public Artikel findArtikelByArtikelnummer(Model model, @PathVariable int artikelnummer) {
    return artikelService.findArtikelByArtikelnummer(artikelnummer);
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
    List<Artikel> artikelLijst = new ArrayList<>();

    if (matchendeCategorie != null) {
      artikelLijst = matchendeCategorie.getArtikelen();
    } else {
      logger.info("Categorie not found");
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
  public List<Artikel> findArtikelenInPriceRange(Model model, @PathVariable final double minPrice,
    @PathVariable final double maxPrice) {
    return artikelService.findArtikelenInPriceRange(minPrice, maxPrice);
  }

  @RequestMapping(value = "artikelen/leverbaar_vanaf/{leverbaar_vanaf}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenLeverbaarVanaf(Model model,
    @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") java.util.Date leverbaar_vanaf) {
    return artikelService.findArtikelenLeverbaarVanaf(leverbaar_vanaf);
  }

  @RequestMapping(value = "artikelen/leverbaar_tot/{leverbaar_tot}", method = RequestMethod.GET)
  public List<Artikel> findArtikelenLeverbaarTot(Model model,
    @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") java.util.Date leverbaar_tot) {
    return artikelService.findArtikelenLeverbaarTot(leverbaar_tot);
  }

  @RequestMapping(value = "leveranciers", method = RequestMethod.GET)
  public List<Leverancier> findAllLeveranciers() {
    return leverancierService.findAllLeveranciers();
  }
}
