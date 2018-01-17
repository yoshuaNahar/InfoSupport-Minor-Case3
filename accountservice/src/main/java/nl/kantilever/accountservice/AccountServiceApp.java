package nl.kantilever.accountservice;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AccountServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(AccountServiceApp.class);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtParser jwtParser() {
    return Jwts.parser();
  }

}