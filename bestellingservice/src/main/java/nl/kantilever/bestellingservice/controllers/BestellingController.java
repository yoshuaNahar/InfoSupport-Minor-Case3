package nl.kantilever.bestellingservice.controllers;

import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.services.BestellingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    bestellingService.addBestelling(bestelling);

    bestellingService.saveBestellingSnapshot(bestelling);

    return new ResponseEntity(HttpStatus.CREATED); // 201 Created
  }

  @GetMapping("/bestelling/{id}")
  public BestellingSnapshot getBestelling(@PathVariable("id") Long bestellingId) {
    logger.debug("getBestelling: {}", bestellingId);
    return bestellingService.findById(bestellingId);
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

  @GetMapping("/bestelling/gebruiker/totaalwaarde/{id}")
  public Double getTotaalwaardeBestellingen(@PathVariable("id") int gebruikerId){
    System.out.println(bestellingService.getTotaalwaardeBestellingen(gebruikerId));
    return bestellingService.getTotaalwaardeBestellingen(gebruikerId);
  }
}
