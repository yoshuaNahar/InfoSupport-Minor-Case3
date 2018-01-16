package nl.kantilever.accountservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Value("${tokens.accessToken.secret}")
  private String accessTokenSecret;

  // Now, confgure the Filter implemented above in Spring configuration class. Take a look
  @Bean
  public FilterRegistrationBean myFilterBean() {
    System.out.println("SDAsaDSDASDAgghsdavsdnhfkhgfkWEJHFVKVHDKSDAHFBKDSFGBSD");
    final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
    filterRegBean.setFilter(new IsAdminFilter(this.accessTokenSecret));
    filterRegBean.addUrlPatterns("/test");
    filterRegBean.setEnabled(Boolean.TRUE);
    filterRegBean.setName("Meu Filter");
    filterRegBean.setAsyncSupported(Boolean.TRUE);
    return filterRegBean;
  }
}
