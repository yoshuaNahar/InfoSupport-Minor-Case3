package nl.kantilever.accountservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class IsAdminFilter implements Filter {

  private static final Logger logger2 = LoggerFactory.getLogger(IsAdminFilter.class);

  public IsAdminFilter(String accessTokenSecret) {
    this.accessTokenSecret = accessTokenSecret;
  }

  private String accessTokenSecret;

  @Override
  public void destroy() {
    logger2.info("destroy");
  }

  @Override
  public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
    logger2.info("init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    String accessToken = ((HttpServletRequest) request).getHeader("Access-Token");
    logger2.info("access: {}", accessToken);

    try {
      Jws<Claims> claims = Jwts
        .parser()
        .setSigningKey(this.accessTokenSecret)
        .parseClaimsJws(accessToken);

      if (!claims.getBody().get("role", String.class).equals("ADMIN")) {
        logger2.info("if");
        ((HttpServletResponse) response).setStatus(401);
        // maybe here should be stopped from going to the controller
      } else {
        logger2.info("else");
        chain.doFilter(request, response);
      }

    } catch (SignatureException e) {
      e.printStackTrace();
      ((HttpServletResponse) response).setStatus(401);
    } catch (Exception e) {
      e.printStackTrace();
      ((HttpServletResponse) response).setStatus(500);
    }
  }
}
