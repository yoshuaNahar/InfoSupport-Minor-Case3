package nl.kantilever.replayservice.services;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplayServiceTest {

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private ReplayService replayService;

  @Test
  public void GetReplayEventsTest() {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String1");
    stringList.add("String2");
    stringList.add("String3");
    ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(stringList,
      responseHeaders, HttpStatus.OK);

    Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(), Mockito.any(),
      Matchers.any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
    ResponseEntity<List<String>> response = replayService.getAllEvents();

    Assert.assertEquals(response.getBody().get(0), responseEntity.getBody().get(0));
  }

  @Test
  public void replayPostTest() {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    ResponseEntity responseEntity = new ResponseEntity("TestBody", responseHeaders, HttpStatus.OK);

    Mockito
      .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.anyObject(), Mockito.any()))
      .thenReturn(responseEntity);

    ResponseEntity<String> response = replayService.replayEvents();
    Assert.assertEquals(response.getBody(), responseEntity.getBody().toString());
  }

}