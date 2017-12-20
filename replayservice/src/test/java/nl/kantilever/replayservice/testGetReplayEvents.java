package nl.kantilever.replayservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import nl.kantilever.replayservice.domain.EenEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;

public class testGetReplayEvents {

  @Test
  public void test() throws URISyntaxException, IOException {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<String>> z = restTemplate
      .exchange(new URI("http://dormin02:8922/api/ReplayEvents"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<String>>() {
        });

    System.out.println(z.getBody().get(0));

    ObjectMapper mapper = new ObjectMapper();
    String s = z.getBody().get(0);

    EenEvent obj = mapper.readValue(s, EenEvent.class);
    System.out.println(obj);

  }

}
