package nl.kantilever.replayservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.net.URLDecoder;
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
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    String testttt = "{\"Id\":0,\"Timestamp\":636482961425298193,\"CorrelationId\":\"cb5084e2-a908-4e1c-9eb0-391f7e8a86d6\",\"RoutingKey\":\"Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd\",\"EventType\":\"Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd\",\"JsonMessage\":\"{\\\"Artikelnummer\\\":1,\\\"Naam\\\":\\\"HL Road Frame - Black, 58\\\",\\\"Beschrijving\\\":\\\"Our lightest and best quality aluminum frame made from the newest alloy; it is welded and heat-treated for strength. Our innovative design results in maximum comfort and performance.\\\",\\\"Prijs\\\":1431.5000,\\\"AfbeeldingUrl\\\":\\\"no_image_available_small.gif\\\",\\\"LeverbaarVanaf\\\":\\\"1998-06-01T00:00:00\\\",\\\"LeverbaarTot\\\":null,\\\"Leveranciercode\\\":\\\"FR-R92B-58\\\",\\\"Leverancier\\\":\\\"Koga Miyata\\\",\\\"Categorieen\\\":[\\\"Components\\\",\\\"Road Frames\\\"],\\\"RoutingKey\\\":\\\"Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd\\\",\\\"Timestamp\\\":636482961425298193,\\\"ID\\\":\\\"cb5084e2-a908-4e1c-9eb0-391f7e8a86d6\\\"}\"}";
    System.out.println(response);
    String test = URLDecoder.decode(testttt,"UTF-8");
    System.out.println(test);
    //ArrayList<ReplayEventsCommand> events = objectMapper.readValue(response, new TypeReference<List<ReplayEventsCommand>>(){});
    //System.out.println(events.size());
    return null;
  }
}
