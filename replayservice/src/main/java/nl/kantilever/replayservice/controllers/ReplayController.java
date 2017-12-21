package nl.kantilever.replayservice.controllers;

import nl.kantilever.replayservice.services.ReplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReplayController {

  private RestTemplate restTemplate;
  private ReplayService replayService;
  private static final String REST_SERVICE_URI = "http://dormin02:8922/api/ReplayEvents";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ReplayController(RestTemplate restTemplate, ReplayService replayService) {
    this.restTemplate = restTemplate;
    this.replayService = replayService;
  }

  @GetMapping("replayRequest")
  public ResponseEntity<?> getAuditLog() {
    replayService.getAllEvents();
    return null;
  }

  @GetMapping("replayPostRequest")
  public String replayPostRequest() {
    replayService.replayEvents();
    return "Succes";
  }
}
