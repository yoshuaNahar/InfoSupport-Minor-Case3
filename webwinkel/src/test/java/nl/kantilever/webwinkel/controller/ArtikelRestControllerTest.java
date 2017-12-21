package nl.kantilever.webwinkel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.webwinkel.controllers.ArtikelRestController;
import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.services.ArtikelService;
import nl.kantilever.webwinkel.services.CategorieService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Tinne on 21-12-2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ArtikelRestController.class)
public class ArtikelRestControllerTest {
  @MockBean
  private CategorieService categorieService;

  @MockBean
  private ArtikelService artikelService;

  @InjectMocks
  ArtikelRestController artikelRestController;


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Before
  public void intialize() {
    try {
      categorieService.save(new Categorie("Onderdelen", "fork_small.gif"));
      categorieService.save(new Categorie("Roestvrijstaal", "no_image_available_small.gif"));
      artikelService.save(new Artikel(115, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(5), new Date(1999999999), 1989, "Henk & Nagel B.V.", Arrays.asList(categorieService.findAll().get(0), categorieService.findAll().get(1))));
    } catch (Exception e) {
      System.out.println("Temp data is al aangemaakt");
    }
  }

  @Test
  public void test() throws Exception {
    Artikel response = new Artikel();
    response.setArtikelnummer(115);

    when(artikelService.findArtikelByArtikelnummer(Mockito.anyInt())).thenReturn(response);
    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"));
  }

  @Test
  @Ignore
  public void test2() throws Exception {
    System.out.println(categorieService.findAll().get(0).getArtikellen());

    mockMvc.perform(get("/artikelen")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0]", is("Fietsketting")));
  }

  @Test
  @Ignore
  public void test3() throws Exception {
    Iterable<Artikel> testList = Arrays.asList(new Artikel(115, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(5), new Date(1999999999), 1989, "Henk & Nagel B.V.", Arrays.asList(new Categorie("Roestvrijstaal", "Image_URL"), new Categorie("Onderdelen", "Image_URL"))), new Artikel());
    when(categorieService.findAll().get(0).getArtikellen()).thenReturn((List<Artikel>) testList);
    mockMvc.perform(MockMvcRequestBuilders
      .get("/artikel/artikelnummer/115"))

      .andExpect(status().isOk())
      .andExpect(content().json(mapper.writeValueAsString(testList)));
    System.out.println(mapper.writeValueAsString(testList));
  }
}

