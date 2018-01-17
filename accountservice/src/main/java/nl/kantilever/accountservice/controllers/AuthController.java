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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private BCryptPasswordEncoder bCrypt;

  @Value("${tokens.refreshToken.secret}")
  private String refreshTokenSecret;

  @Value("${tokens.accessToken.secret}")
  private String accessTokenSecret;

  @Autowired
  private JwtParser jwtParser;

  @PostMapping("/refresh")
  public ResponseEntity refresh(@RequestHeader("Refresh-Token") String refreshToken) {
    logger.debug("refresh: {}", refreshToken);

    try {
      Jws<Claims> parsedRefreshToken = jwtParser.setSigningKey(this.refreshTokenSecret)
        .parseClaimsJws(refreshToken);

      // Retrieve user from database.
      long id = Integer.valueOf(parsedRefreshToken.getBody().getSubject());
      Account account = accountService.findById(id);

      if (account == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED); // No account for this subject.
      }

      // Configure claims
      HashMap<String, Object> claims = new HashMap<>();
      claims.put("role", account.getRole());

      // Build AccessToken
      String accessToken = Jwts.builder()
        .setSubject(account.getId() + "")
        .addClaims(claims)
        .signWith(SignatureAlgorithm.HS256, this.accessTokenSecret)
        .compact();

      return ResponseEntity.ok().body(accessToken);
    } catch (SignatureException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      //don't trust the JWT!
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Returns a refreshToken if the user is authenticated
   */
  @PostMapping("/authenticate")
  public ResponseEntity authenticate(@RequestBody Account account) {
    logger.debug("authenticate: {}", account.getUsername());

    try {
      Account accountFromDB = accountService.findByUsername(account.getUsername());

      if (accountFromDB == null) {
        return new ResponseEntity(HttpStatus.NOT_FOUND); // 404 Not Found
      }

      if (!bCrypt.matches(account.getPassword(), accountFromDB.getPassword())) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }

      // Build RefreshToken
      String refreshToken = Jwts.builder()
        .setSubject(accountFromDB.getId() + "")
        .signWith(SignatureAlgorithm.HS256, this.refreshTokenSecret)
        .compact();

      return ResponseEntity.ok().body(refreshToken); // 200 OK
    } catch (Exception e) {
      logger.info("Exception: {}", e.getMessage());
      return new ResponseEntity(HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
  }

}
