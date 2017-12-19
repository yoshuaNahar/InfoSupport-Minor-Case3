package nl.kantilever.replayservice;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class testGetReplayEvents {

  @Test
  public void test() throws URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<String>> z = restTemplate
      .exchange(new URI("https://kantilever-auditlog.netminor.infosupport.net/api/ReplayEvents"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<String>>() {
        });

    for (String a: z.getBody()) {
      System.out.println(a);
    }
  }

}
