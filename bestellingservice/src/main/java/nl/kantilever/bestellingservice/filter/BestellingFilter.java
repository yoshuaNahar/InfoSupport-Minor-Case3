package nl.kantilever.bestellingservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Order(1)
public class BestellingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(BestellingFilter.class);

  private final String accessTokenSecret;

  private HashMap<String, String[]> urlMapping;

  public BestellingFilter(String accessTokenSecret) {
    this.accessTokenSecret = accessTokenSecret;
  }

  @Override
  public void destroy() {
    logger.info("destroy");
  }

  @Override
  public void init(javax.servlet.FilterConfig filterConfig) {
    logger.info("init");
    urlMapping = new HashMap<>();
    this.initUrlMapping();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    String url = ((HttpServletRequest) request).getRequestURI();
    logger.info("Url: {}", url);

    String[] roles = determineRolesBasedOnRequestedUrl(url);
    logger.info("Roles: {}", roles);


    String accessToken = ((HttpServletRequest) request).getHeader("Access-Token");
    logger.info("access: {}", accessToken);

    try {
      Jws<Claims> claims = Jwts
        .parser()
        .setSigningKey(this.accessTokenSecret)
        .parseClaimsJws(accessToken);

      if (!Arrays.asList(roles).contains(claims.getBody().get("role", String.class))) {
        logger.info("if");
        ((HttpServletResponse) response).setStatus(401);
        // maybe here should be stopped from going to the controller
      } else {
        logger.info("else");
        chain.doFilter(request, response);
      }
    } catch (SignatureException e) {
      logger.info("SignatureException: {}", e);
      ((HttpServletResponse) response).setStatus(401);
    } catch (Exception e) {
      logger.info("Exception: {}", e);
      ((HttpServletResponse) response).setStatus(500);
    }
  }

  private String[] determineRolesBasedOnRequestedUrl(String url) {
    String[] roles;

    Optional<String[]> result =
      this.urlMapping.entrySet()
        .stream()
        .filter((e) -> url.matches(e.getKey()))
        .map(Map.Entry::getValue)
        .findFirst();

    roles = result.orElseGet(() -> new String[]{"User"});

    return roles;
  }

  private void initUrlMapping() {
    // Magazijn medewerker specific urls
    this.urlMapping.put("/bestelling/[0-9]+/setStatus/ingepakt", new String[]{"MAGAZIJN_MEDEWERKER"});

    // Commeercieel medewerker specific urls
    this.urlMapping.put("/bestelling/[0-9]+/setStatus/goedgekeurd", new String[]{"COMMERCIEEL_MEDEWERKER"});
    this.urlMapping.put("/bestelling/[0-9]+/setStatus/afgekeurd", new String[]{"COMMERCIEEL_MEDEWERKER"});
    this.urlMapping.put("/bestelling/gebruiker/[0-9]+", new String[]{"COMMERCIEEL_MEDEWERKER"});
    this.urlMapping.put("/bestelling/gebruikercontrole", new String[]{"COMMERCIEEL_MEDEWERKER"});

    // Commeercieel medewerker and Magazijn medewerker specific urls;
    this.urlMapping.put("/bestelling\\?.*", new String[]{"MAGAZIJN_MEDEWERKER"});

    // Logged in requirement
    this.urlMapping.put("/bestelling", new String[]{"MAGAZIJN_MEDEWERKER", "USER"});
  }

}
