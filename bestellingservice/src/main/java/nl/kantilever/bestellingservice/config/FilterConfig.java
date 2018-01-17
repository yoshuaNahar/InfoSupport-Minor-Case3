package nl.kantilever.bestellingservice.config;

import nl.kantilever.bestellingservice.filter.BestellingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Value("${tokens.accessToken.secret}")
  private String accessTokenSecret;

  @Bean
  public FilterRegistrationBean magazijnMedewerkerFilterBean() {
    return filterBeanUrlPattern("/bestelling/*");
  }

  private FilterRegistrationBean filterBeanUrlPattern(String... urlPattern) {
    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
    filterRegBean.setFilter(new BestellingFilter(accessTokenSecret));
    filterRegBean.addUrlPatterns(urlPattern);
    filterRegBean.setEnabled(true);
    filterRegBean.setAsyncSupported(true);
    return filterRegBean;
  }

}
