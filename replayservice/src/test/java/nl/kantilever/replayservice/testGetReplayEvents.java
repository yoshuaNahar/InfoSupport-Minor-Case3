package nl.kantilever.replayservice;

import nl.kantilever.replayservice.services.ReplayService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class testGetReplayEvents {

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private ReplayService replayService;

  @Test
  public void GetReplayEventsTest() throws Exception {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    ArrayList stringList = new ArrayList();
    stringList.add("String1");
    stringList.add("String2");
    stringList.add("String3");
    ResponseEntity<List<String>> responseEntity = new ResponseEntity<List<String >>(stringList,responseHeaders,HttpStatus.OK);
    Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(),Mockito.any(),Matchers.any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
    ResponseEntity<List<String>> response = replayService.getAllEvents();
    Assert.assertEquals(response.getBody().get(0),responseEntity.getBody().get(0));
  }

  @Test
  public void replayPostTest() throws Exception {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    ResponseEntity responseEntity = new ResponseEntity("TestBody", responseHeaders, HttpStatus.OK);
    Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.anyObject(), Mockito.any())).thenReturn(responseEntity);
    ResponseEntity<String> response = replayService.replayEvents();
    Assert.assertEquals(response.getBody(),responseEntity.getBody().toString() );
  }
}
