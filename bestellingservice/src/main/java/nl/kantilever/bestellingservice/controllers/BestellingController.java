package nl.kantilever.bestellingservice.controllers;

import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.entities.Gebruiker;
import nl.kantilever.bestellingservice.services.BestellingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class BestellingController {

  private static final Logger logger = LoggerFactory.getLogger(BestellingController.class);

  private BestellingService bestellingService;

  @Autowired
  public BestellingController(BestellingService bestellingService) {
    this.bestellingService = bestellingService;
  }

  @PostMapping("/bestelling")
  public ResponseEntity addBestelling(@RequestBody Bestelling bestelling) {
    logger.debug("addBestelling: {}", bestelling);

    try {
      bestellingService.addBestelling(bestelling);

      bestellingService.saveBestellingSnapshot(bestelling);

      return new ResponseEntity(HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }

  @GetMapping("/bestelling/{id}")
  public ResponseEntity getBestelling(@PathVariable("id") Long bestellingId) {
    logger.debug("getBestelling: {}", bestellingId);

    if (bestellingId == null) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    BestellingSnapshot bestellingSnapshot = bestellingService.findById(bestellingId);

    if (bestellingSnapshot == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(bestellingSnapshot, HttpStatus.OK );
  }

  @GetMapping("/bestelling")
  public ResponseEntity getAllBestellingen() {
    logger.debug("getAllBestellingen");

    return ResponseEntity.ok().body(bestellingService.findAll());
  }

  @GetMapping("/bestelling/gebruiker/{id}")
  public List<BestellingSnapshot> getBestellingenGebruiker(@PathVariable("id") int gebruikerId){
    return bestellingService.getBestellingenGebruiker(gebruikerId);
  }

  @GetMapping("/bestelling/gebruiker/{id}/totaalwaarde")
  public Double getTotaalwaardeBestellingen(@PathVariable("id") int gebruikerId){
    return bestellingService.getTotaalwaardeBestellingen(gebruikerId);
  }


  @GetMapping("/bestelling/gebruikercontrole")
  public List<Gebruiker> getGebruikersMetBestellingenBoven500(){
    return bestellingService.getGebruikersMetBestellingenBoven500();
  }
}
