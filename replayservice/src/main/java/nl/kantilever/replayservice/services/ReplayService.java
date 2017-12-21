package nl.kantilever.replayservice.services;

import nl.kantilever.replayservice.domain.ReplayEventsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ReplayService {

  private RestTemplate restTemplate;
  private static final String REST_SERVICE_URI = "http://dormin02:8922/api/ReplayEvents";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ReplayService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<String> replayEvents(){
    ReplayEventsCommand replayEventsCommand = new ReplayEventsCommand();
    ResponseEntity<String> response = restTemplate.postForEntity(REST_SERVICE_URI, replayEventsCommand, String.class);
    return response;
  }

  public  ResponseEntity<List<String>> getAllEvents(){
    ResponseEntity<List<String>> response = null;
    try {
      response = restTemplate
        .exchange(new URI(REST_SERVICE_URI),
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<List<String>>() {
          });
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return response;
  }
}
