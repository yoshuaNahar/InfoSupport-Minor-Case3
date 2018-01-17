package nl.kantilever.accountservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Value("${tokens.accessToken.secret}")
  private String accessTokenSecret;

  @Bean
  public FilterRegistrationBean myFilterBean() {
    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
    filterRegBean.setFilter(new IsAdminFilter(accessTokenSecret));
    filterRegBean.addUrlPatterns("/test");
    filterRegBean.setEnabled(true);
    filterRegBean.setAsyncSupported(true);
    return filterRegBean;
  }

}
