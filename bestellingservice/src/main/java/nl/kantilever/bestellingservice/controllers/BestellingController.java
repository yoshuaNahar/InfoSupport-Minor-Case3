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
    logger.debug("sdafasfsasfas");
    logger.debug("woww");

    return bestellingService.findById(bestellingId);
  }

  @GetMapping("/bestelling")
  public ResponseEntity getAllBestellingen(@RequestParam(value = "status", required = false) String status) {
    logger.debug("getAllBestellingen");

    if (status == null || status.trim().isEmpty()) {
      return ResponseEntity.ok().body(bestellingService.findAll());
    } else {
      return ResponseEntity.ok().body(bestellingService.findAllWithStatus(status));
    }
  }

//  @GetMapping("/test")
//  public void test() {
//    bestellingService.getBestellingenGebruiker(1);
//  }

}
