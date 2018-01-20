package nl.kantilever.accountservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    String url = ((HttpServletRequest) request).getRequestURI();
    logger.info("Url: {}", url);

    String role = determineRoleBasedOnRequestedUrl(url);
    logger.info("Role: {}", role);

    String accessToken = ((HttpServletRequest) request).getHeader("Access-Token");
    logger.info("access: {}", accessToken);

    if (role == null) {
      chain.doFilter(request, response);
    } else {

      try {
        Jws<Claims> claims = Jwts
          .parser()
          .setSigningKey(this.accessTokenSecret)
          .parseClaimsJws(accessToken);

        if (claims.getBody().get("role", String.class).equals("USER") ||
          claims.getBody().get("role", String.class).equals("MAGAZIJN_MEDEWERKER") ||
          claims.getBody().get("role", String.class).equals("COMMERCIEEL_MEDEWERKER")) {
          logger.info("role is admin, so doFilter, means: go to next Filter or Controller");
          chain.doFilter(request, response);
        } else {
          logger.info("role not: {}", role);
          ((HttpServletResponse) response).setStatus(401);
        }
      } catch (SignatureException e) {
        logger.info("SignatureException: {}", e);
        ((HttpServletResponse) response).setStatus(401);
      } catch (Exception e) {
        logger.info("Exception: {}", e);
        ((HttpServletResponse) response).setStatus(500);
      }
    }
  }

  private String determineRoleBasedOnRequestedUrl(String url) {
    String role;
    if (isCommercieelMedewerkerUrl(url)) {
      role = "COMMERCIEEL_MEDEWERKER";
    } else if (isRegisterationUrl(url)) {
      role = null;
    } else {
      role = "USER";
    }
    return role;
  }

  private boolean isCommercieelMedewerkerUrl(String url) {
    return url.matches("/gebruiker/[0-9]+");
  }

  private boolean isRegisterationUrl(String url) {
    return url.matches("/gebruiker") || url.matches("/gebruiker/[0-9]+");
  }

}
