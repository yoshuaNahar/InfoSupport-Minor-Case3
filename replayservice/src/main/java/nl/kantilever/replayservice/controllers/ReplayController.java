package nl.kantilever.replayservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.replayservice.domain.ReplayEventsCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReplayController {
  @Autowired
  RestTemplate restTemplate;

  public static final String REST_SERVICE_URI = "http://dormin02:8922/api/ReplayEvents";

  @GetMapping("replayRequest")
  public ResponseEntity<?> getAuditLog() throws URISyntaxException, IOException {
    ResponseEntity<List<String>> z = restTemplate
      .exchange(new URI(REST_SERVICE_URI),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<String>>() {
        });
    for (String a : z.getBody()) {
      System.out.println(a);
    }
    return null;
  }

  @GetMapping("replayPostRequest")
  public void test() {
    ReplayEventsCommand replayEventsCommand = new ReplayEventsCommand();
    ResponseEntity<String> response = restTemplate.postForEntity(REST_SERVICE_URI, replayEventsCommand, String.class);
    HttpStatus status = response.getStatusCode();
    HttpHeaders restCall = response.getHeaders();
    System.out.println(restCall.toString());
    System.out.println(status);
    System.out.println(restCall);
  }


/*  {
    "exchangeName": "KantileverBus",
    "fromTimestamp": 0,
    "toTimestamp": 999999999999999999,
    "eventType": "Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd",
    "topic": "#"
  }*/

}
