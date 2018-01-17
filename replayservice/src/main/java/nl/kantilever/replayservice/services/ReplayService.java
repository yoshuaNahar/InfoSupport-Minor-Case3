package nl.kantilever.replayservice.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import nl.kantilever.replayservice.domain.ReplayEventsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReplayService {

  private static final String REST_SERVICE_URI = "http://192.168.178.158:8922/api/ReplayEvents";
  private RestTemplate restTemplate;

  @Autowired
  public ReplayService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<String> replayEvents() {
    ReplayEventsCommand replayEventsCommand = new ReplayEventsCommand();
    return restTemplate.postForEntity(REST_SERVICE_URI, replayEventsCommand, String.class);
  }

  public ResponseEntity<List<String>> getAllEvents() {
    ResponseEntity<List<String>> response;
    try {
      response = restTemplate
        .exchange(new URI(REST_SERVICE_URI),
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
