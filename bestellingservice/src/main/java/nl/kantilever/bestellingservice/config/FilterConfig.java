package nl.kantilever.bestellingservice.config;

import nl.kantilever.bestellingservice.filter.BestellingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FilterConfig {

  @Value("${tokens.accessToken.secret}")
  private String accessTokenSecret;

  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }

  @Bean
  public FilterRegistrationBean myFilterBean() {
    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
    filterRegBean.setFilter(new BestellingFilter(accessTokenSecret));
    filterRegBean.addUrlPatterns("/bestelling/**");
    filterRegBean.addUrlPatterns("/bestelling/*");
    filterRegBean.setEnabled(true);
    filterRegBean.setAsyncSupported(true);
    return filterRegBean;
  }

}
