package nl.kantilever.accountservice.config;

import nl.kantilever.accountservice.filter.AccountFilter;
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
    filterRegBean.setFilter(new AccountFilter(accessTokenSecret));
    filterRegBean.addUrlPatterns("/gebruiker/*");
    filterRegBean.setEnabled(true);
    filterRegBean.setAsyncSupported(true);
    return filterRegBean;
  }

}
