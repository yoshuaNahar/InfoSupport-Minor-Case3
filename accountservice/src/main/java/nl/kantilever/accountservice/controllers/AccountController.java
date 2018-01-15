package nl.kantilever.accountservice.controllers;

import io.jsonwebtoken.*;
import nl.kantilever.accountservice.entities.Account;
import nl.kantilever.accountservice.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class AccountController {
  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private BCryptPasswordEncoder bCrypt;

  /**
   * Register a new account
   *
   * @param account
   * @return
   */
  @PostMapping("/account")
  public ResponseEntity createAccount(@RequestBody Account account) {
    logger.debug("account: {}", account.getUsername());

    try {
      if (accountService.findByUsername(account.getUsername()) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UsernameTaken"); // 400 Bad Request
      }

      account.setPassword(bCrypt.encode(account.getPassword()));
      long accountId = accountService.save(account);

      return ResponseEntity.status(HttpStatus.OK).body(accountId);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }


}
