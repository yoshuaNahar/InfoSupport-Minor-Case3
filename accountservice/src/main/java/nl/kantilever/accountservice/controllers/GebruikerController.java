package nl.kantilever.accountservice.controllers;

import nl.kantilever.accountservice.domain.Gebruiker;
import nl.kantilever.accountservice.services.AccountService;
import nl.kantilever.accountservice.services.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GebruikerController {

  private static final Logger logger = LoggerFactory.getLogger(GebruikerController.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private GebruikerService gebruikerService;

  @Autowired
  private BCryptPasswordEncoder bCrypt;

  /**
   * Registers a new user
   */
  @PostMapping("/gebruiker")
  public ResponseEntity createGebruikerAndAccount(@RequestBody Gebruiker gebruiker) {
    logger.info("gebruiker: {}", gebruiker);

    try {
      if (accountService.findByUsername(gebruiker.getAccount().getUsername()) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("UsernameTaken"); // 400 Bad Request
      }

      gebruiker.getAccount().setPassword(bCrypt.encode(gebruiker.getAccount().getPassword()));
      gebruikerService.save(gebruiker);

      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      logger.info("Exception: {}", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }

  /**
   * Retrieve a user by id
   */
  @GetMapping("/gebruiker/{id}")
  public ResponseEntity retrieveGebruikerById(@PathVariable("id") Long gebruikerId) {
    logger.debug("gebruikerId: {}", gebruikerId);

    try {
      if (gebruikerId == null) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
      }

      return ResponseEntity.ok().body(gebruikerService.findById(gebruikerId));
    } catch (Exception e) {
      logger.info(e.getMessage());
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); // 400 Bad Request
    }
  }

}
