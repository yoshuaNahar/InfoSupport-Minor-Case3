package nl.kantilever.accountservice.controllers;

import nl.kantilever.accountservice.entities.Account;
import nl.kantilever.accountservice.entities.Gebruiker;
import nl.kantilever.accountservice.services.AccountService;
import nl.kantilever.accountservice.services.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
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
   *
   * @param gebruiker
   * @return
   */
  @PostMapping("/gebruiker")
  public ResponseEntity createGebruikerAndAccount(@RequestBody Gebruiker gebruiker) {
    logger.debug("gebruiker: {}", gebruiker.toString());

    try {
      if (accountService.findByUsername(gebruiker.getAccount().getUsername()) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UsernameTaken"); // 400 Bad Request
      }

      gebruiker.getAccount().setPassword(bCrypt.encode(gebruiker.getAccount().getPassword()));
      gebruikerService.save(gebruiker);

      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }


}
