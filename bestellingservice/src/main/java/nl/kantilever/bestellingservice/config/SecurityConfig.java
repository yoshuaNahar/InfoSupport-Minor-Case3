package nl.kantilever.bestellingservice.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
    .logout().logoutSuccessUrl("/").and()
      .authorizeRequests()
        .antMatchers("/bestellingen").hasRole("ROLE_ADMIN")
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
      .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    // @formatter:on
  }

}
