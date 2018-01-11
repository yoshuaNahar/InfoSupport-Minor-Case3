package nl.kantilever.gebruikerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@SessionAttributes("authorizationRequest")
public class OAuthLoginConfig extends WebMvcConfigurerAdapter {

  /*
   * Calls to the login will be done in the frontend.
   * I can basically call the login from the frontend.
   * So remove this when the Polymer post to /login has been implemented.
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/oauth/confirm_access").setViewName("authorize");
  }

}
