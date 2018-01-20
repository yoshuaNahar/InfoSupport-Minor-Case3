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

@Order(1)
public class BestellingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(BestellingFilter.class);

  private final String accessTokenSecret;

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
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    String url = ((HttpServletRequest) request).getRequestURI();
    logger.info("Url: {}", url);

    String role = determineRoleBasedOnRequestedUrl(url);
    logger.info("Role: {}", role);

    String accessToken = ((HttpServletRequest) request).getHeader("Access-Token");
    logger.info("access: {}", accessToken);

    try {
      Jws<Claims> claims = Jwts
        .parser()
        .setSigningKey(this.accessTokenSecret)
        .parseClaimsJws(accessToken);
      if (!claims.getBody().get("role", String.class).equals(role)) {
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

  private String determineRoleBasedOnRequestedUrl(String url) {
    String role;
    if (isMagazijnMedewerkerUrl(url)) {
      role = "MAGAZIJN_MEDEWERKER";
    } else if (isCommercieelMedewerkerUrl(url)) {
      role = "COMMERCIEEL_MEDEWERKER";
    } else {
      role = "USER";
    }
    return role;
  }

  private boolean isMagazijnMedewerkerUrl(String url) {
    return "/bestelling?status=goedgekeurd&limit=1".equals(url) ||
      url.matches("/bestelling/[0-9]+/setStatus/ingepakt");
  }

  private boolean isCommercieelMedewerkerUrl(String url) {
    return "/bestelling/gebruikercontrole".equals(url) ||
      url.matches("/bestelling/[0-9]+/setStatus/goedgekeurd") ||
      url.matches("/bestelling/[0-9]+/setStatus/afgekeurd") ||
      url.matches("/bestelling/gebruiker/[0-9]+");
  }

}
