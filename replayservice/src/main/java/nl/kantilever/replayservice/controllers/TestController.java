package nl.kantilever.replayservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.replayservice.domain.ReplayEventsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
  @Autowired
  RestTemplate restTemplate;

  @GetMapping("replayRequest")
  public ResponseEntity<?> getAuditLog() throws URISyntaxException, IOException {
    String response = restTemplate.getForObject(new URI("https://kantilever-auditlog.netminor.infosupport.net/api/ReplayEvents"),String.class);
    System.out.println(response);
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<ReplayEventsCommand> events = objectMapper.readValue(response, new TypeReference<List<ReplayEventsCommand>>(){});
    System.out.println(events.size());
    return null;
  }
}
