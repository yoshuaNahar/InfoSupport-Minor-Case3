/*
package nl.kantilever.bestellingservice.controllers;

import nl.kantilever.bestellingservice.entities.Gebruiker;
import nl.kantilever.bestellingservice.services.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GebruikerController {

  private static final Logger logger = LoggerFactory.getLogger(BestellingController.class);

  private GebruikerService gebruikerService;

  @Autowired
  public GebruikerController(GebruikerService gebruikerService) {
    this.gebruikerService = gebruikerService;
  }

  @PostMapping("/gebruiker")
  public ResponseEntity createGebruiker(@RequestBody Gebruiker gebruiker) {
    logger.debug("createGebruiker: {}", gebruiker);
    try {
      gebruikerService.createGebruiker(gebruiker);
      return new ResponseEntity(HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }
}
*/
