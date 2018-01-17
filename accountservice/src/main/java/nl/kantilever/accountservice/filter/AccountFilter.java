package nl.kantilever.accountservice.filter;

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

@Order(1)
public class AccountFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(AccountFilter.class);

  private final String accessTokenSecret;

  public AccountFilter(String accessTokenSecret) {
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

      if (!claims.getBody().get("role", String.class).equals("ADMIN")) {
        logger.info("role not admin");
        ((HttpServletResponse) response).setStatus(401);
      } else {
        logger.info("role is admin, so doFilter, means: go to next Filter or Controller");
        chain.doFilter(request, response);
      }
    } catch (SignatureException e) {
      logger.info("SignatureException: {}", e.getMessage());
      ((HttpServletResponse) response).setStatus(401);
    } catch (Exception e) {
      logger.info("Exception: {}", e.getMessage());
      ((HttpServletResponse) response).setStatus(500);
    }
  }

  private String determineRoleBasedOnRequestedUrl(String url) {
    String role;
    if (isCommercieelMedewerkerUrl(url)) {
      role = "COMMERCIEEL_MEDEWERKER";
    } else {
      role = "USER";
    }
    return role;
  }

  private boolean isCommercieelMedewerkerUrl(String url) {
    return url.matches("/gebruiker/[0-9]+");
  }

}
