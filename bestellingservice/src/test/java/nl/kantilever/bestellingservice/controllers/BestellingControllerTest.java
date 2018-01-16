package nl.kantilever.bestellingservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.services.BestellingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BestellingController.class)
public class BestellingControllerTest {
  @MockBean
  private BestellingService bestellingService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void addBestellingWithNewBestellingShouldCallAddBestellingAndSaveBestellingSnapshotAndShouldReturnCreatedResponseEntity() throws Exception {
    Bestelling bestelling = new Bestelling();
    bestelling.setArtikelenIds(Arrays.asList(1L, 2L));
    bestelling.setGebruikerId(1L);

    mockMvc.perform(post("/bestelling")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(bestelling))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isCreated());

    verify(bestellingService, times(1)).addBestelling(any(Bestelling.class));
    verify(bestellingService, times(1)).saveBestellingSnapshot(any(Bestelling.class));
  }

  @Test
  public void addBestellingWithNoBestellingShouldReturnBadRequestResponseEntity() throws Exception {
    mockMvc.perform(post("/bestelling")).andExpect(status().isBadRequest());
  }

  @Test
  public void setBestellingIngepaktWithBestellingSnapshotIdShouldCallSetBestellingIngepaktAndReturnOkResponseEntity() throws Exception {
    long bestellingSnapshotId = 1L;

    mockMvc.perform(put("/bestelling/" + bestellingSnapshotId + "/setIngepakt")).andExpect(status().isOk());

    verify(bestellingService, times(1)).setBestellingIngepakt(bestellingSnapshotId);
  }

  @Test
  public void setBestellingIngepaktWithNoBestellingSnapshotIdShouldReturnBadRequestResponseEntity() throws Exception {
    mockMvc.perform(put("/bestelling/null/setIngepakt")).andExpect(status().isBadRequest());
  }

  @Test
  public void getBestellingByIdWithBestellingSnapshotIdAndExistingBestellingShouldCallFindByIdAndReturnOkResponseEntity() throws Exception {
    BestellingSnapshot bestellingSnapshot = new BestellingSnapshot();
    bestellingSnapshot.setId(1L);

    doReturn(bestellingSnapshot).when(bestellingService).findById(bestellingSnapshot.getId());

    mockMvc.perform(get("/bestelling/" + bestellingSnapshot.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    verify(bestellingService,times(1)).findById(bestellingSnapshot.getId());
  }

  @Test
  public void getBestellingByIdWithBestellingSnapshotIdAndNonExistingShouldReturnNotFoundResponseEntity() throws Exception {
    mockMvc.perform(get("/bestelling/1")).andExpect(status().isNotFound());
  }

  @Test
  public void getBestellingByIdWithNoBestellingSnapshotIdgShouldReturnBadRequestResponseEntity() throws Exception {
    mockMvc.perform(get("/bestelling/null")).andExpect(status().isBadRequest());
  }

  @Test
  public void getAllBestellingWithNoStatusShouldCallFindAllAndReturnOkResponseEntity() throws Exception {
    mockMvc.perform(get("/bestelling")).andExpect(status().isOk());
    verify(bestellingService,times(1)).findAll(null);
  }

  @Test
  public void getAllBestellingWithGeplaatstStatusShouldCallFindAllByStatusAndReturnOkResponseEntity() throws Exception {
    String status = "geplaatst";

    mockMvc.perform(get("/bestelling?status=" + status)).andExpect(status().isOk());
    verify(bestellingService,times(1)).findAllByStatus(status, null);
  }

  @Test
  public void getBestellingenGebruikerWithGebruikerIdShouldCallGetBestellingenGebruikerAndReturnOkResponseEntity() throws Exception {
    int gebruikerId = 1;

    mockMvc.perform(get("/bestelling/gebruiker/" + gebruikerId)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    verify(bestellingService,times(1)).getBestellingenGebruiker(gebruikerId);
  }

  @Test
  public void getBestellingenGebruikerWithNoGebruikerIdShouldReturnBadRequestResponseEntity() throws Exception {
    mockMvc.perform(get("/bestelling/gebruiker/null")).andExpect(status().isBadRequest());
  }

  @Test
  public void getTotaalwaardeBestellingenWithGebruikerIdShouldReturnOkResponseEntity() throws Exception {
    int gebruikerId = 1;

    mockMvc.perform(get("/bestelling/gebruiker/" + gebruikerId + "/totaalwaarde")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }

  @Test
  public void getTotaalwaardeBestellingenWithNoGebruikerIdShouldReturnBadRequestResponseEntity() throws Exception {
    mockMvc.perform(get("/bestelling/gebruiker/null/totaalwaarde")).andExpect(status().isBadRequest());
  }
}

