package nl.kantilever.replayservice.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import nl.kantilever.replayservice.domain.ReplayEventsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReplayService {

  @Value("${rest.service.url}")
  private String restServiceUrl;

  private RestTemplate restTemplate;

  @Autowired
  public ReplayService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<String> replayEvents() {
    ReplayEventsCommand replayEventsCommand = new ReplayEventsCommand();
    return restTemplate.postForEntity(restServiceUrl, replayEventsCommand, String.class);
  }

  public ResponseEntity<List<String>> getAllEvents() {
    ResponseEntity<List<String>> response;
    try {
      response = restTemplate
        .exchange(new URI(restServiceUrl),
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<List<String>>() {
          });
    } catch (URISyntaxException e) {
      throw new UnsupportedOperationException(e);
    }
    return response;
  }

}
