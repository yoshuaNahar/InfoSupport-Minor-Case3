package nl.kantilever.replayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class config {

  @Bean
  public RestTemplate simpleRestTemplate(){
    return new RestTemplate();
  }

}
