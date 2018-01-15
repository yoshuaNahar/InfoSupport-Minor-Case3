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
public class AuthController {
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private BCryptPasswordEncoder bCrypt;

  @Value("tokens_refreshToken_privateKey")
  private String refreshTokenPrivateKey;

  @Value("tokens_refreshToken_publicKey")
  private String refreshTokenPublicKey;

  @Value("tokens_accessToken_privateKey")
  private String accessTokenPrivateKey;

  @PostMapping
  public ResponseEntity refresh(@RequestBody String refreshToken) {
    try {
      Jws<Claims> parsedRefreshToken = Jwts.parser().setSigningKey(this.refreshTokenPublicKey).parseClaimsJws(refreshToken);
      //OK, we can trust this JWT

      System.out.println(parsedRefreshToken);

      // Retrieve user from database.
//      long id =  parsedRefreshToken.getClaims();
//      Account account = accountService.findById(id);
//
//      // Configure claims
//      Map<String, Object> claims = new HashMap();
//      claims.put("role", account.getRole());
//
//      // Build AccessToken
//      String accessToken = Jwts.builder()
//        .setSubject(account.getId() + "")
//        .addClaims(claims)
//        .signWith(SignatureAlgorithm.RS256, this.accessTokenPrivateKey)
//        .compact();
//
//      return ResponseEntity.ok().body(accessToken);
      return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    } catch (SignatureException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      //don't trust the JWT!
    }
  }

  /**
   * Returns a refreshToken if the user is authenticated
   *
   * @param account
   * @return
   */
  @PostMapping("/authenticate")
  public ResponseEntity authenticate(@RequestBody Account account) {
    logger.debug("authenticate: {}", account.getUsername());

    try {
      Account accountFromDB = accountService.findByUsername(account.getUsername());

      if (accountFromDB == null) {
        return new ResponseEntity(HttpStatus.NOT_FOUND); // 404 Not Found
      }

      System.out.println(accountFromDB);

      if (!bCrypt.matches(account.getPassword(), accountFromDB.getPassword())) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }

      // Build RefreshToken
      String refreshToken = Jwts.builder()
        .setSubject(accountFromDB.getId() + "")
        .signWith(SignatureAlgorithm.RS256, this.refreshTokenPrivateKey)
        .compact();

      return ResponseEntity.ok().body(refreshToken); // 200 OK
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }


}
