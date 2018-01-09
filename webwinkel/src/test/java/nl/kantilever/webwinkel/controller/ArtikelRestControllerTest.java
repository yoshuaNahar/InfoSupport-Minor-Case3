package nl.kantilever.webwinkel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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

import java.lang.reflect.Array;
import java.util.Date;
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

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Before
  public void intialize() {
    try {
      categorieService.save(new Categorie("Onderdelen", "fork_small.gif"));
      categorieService.save(new Categorie("Roestvrijstaal", "no_image_available_small.gif"));
      artikelService.save(new Artikel(115L, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(5), new Date(1999999999), "1989", "Henk & Nagel B.V.", Arrays.asList(categorieService.findAll().get(0), categorieService.findAll().get(1))));
    } catch (Exception e) {
      System.out.println("Temp data is al aangemaakt");
    }
  }

  @Test
  public void getArtikelenByNaamExpectOkAnd2ArtikelenAsJson() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(new Artikel("Fietsketting 3"), new Artikel("Fietsband 17 inch"));

    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }

  @Test
  public void getArtikelByArtikelnummerExpectOkAnd1ArtikelAsJson() throws Exception {
    Artikel response = new Artikel("Batavus C7");
    response.setArtikelnummer(115);

    when(artikelService.findArtikelByArtikelnummer(Mockito.anyInt())).thenReturn(response);
    this.mockMvc.perform(get("/artikel/artikelnummer/115")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(response))));
  }


  @Test
  public void getArtikelenByLeverancierExpectOkAnd2ArtikelenAsJson() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(new Artikel("Fietsketting 3"), new Artikel("Fietsband 17 inch"));

    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }


  @Test
  public void getAllCategorieenExpectOkAnd2CategorieenAsJson() throws Exception {
    List<Categorie> categorieArray = Arrays.asList(new Categorie("Roestvrijstaal"), new Categorie("Fietsen"));

    when(categorieService.findAll()).thenReturn(categorieArray);
    this.mockMvc.perform(get("/categorieen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorieArray))));
  }


  @Test
  public void getArtikelenByCategorieExpectOkAnd2ArtikelenAsJson() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(new Artikel("Fietsketting 3"), new Artikel("Fietsband 17 inch"));
    Categorie categorie = new Categorie("fietsonderdelen");
    categorie.setArtikelen(artikelArray);

    when(categorieService.findCategorieByNaam("fietsonderdelen")).thenReturn(categorie);
    this.mockMvc.perform(get("/artikelen/categorie/fietsonderdelen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorie.getArtikelen()))));
  }


  @Test
  public void getCategorieByNaamExpectOkAnd1CategorieAsJson() throws Exception {
    Categorie categorie = new Categorie("Fietsen");

    when(categorieService.findCategorieByNaam("fietsen")).thenReturn(categorie);
    this.mockMvc.perform(get("/categorie/naam/fietsen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorie))));
  }


  @Test
  public void getArtikelenByBeschrijvingExpectOkAnd2ArtikelenAsJson() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(new Artikel("Fietsketting 3"), new Artikel("Fietsband 17 inch"));

    when(artikelService.findArtikelenByBeschrijving("fiets")).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/beschrijving/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }


  @Test
  public void getArtikelenInPriceRangeExpectOkAnd2ArtikelenAsJson() throws Exception {
    Artikel artikel1 = new Artikel("Fietsketting 3");
    Artikel artikel2 = new Artikel("Fietsband 17 inch");
    artikel1.setPrijs(5.50);
    artikel2.setPrijs(9.00);
    List<Artikel> artikelArray = Arrays.asList(artikel1, artikel2);

    when(artikelService.findArtikelenInPriceRange(5, 10)).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/pricerange/5/10")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }


  @Test
  @Ignore
  public void getArtikelenAfterLeverbaarVanafDateExpectOkAnd2ArtikelenAsJson() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String artikel1DateString = "7-05-2013";
    String artikel2DateString = "28-07-2015";
    String searchDateString = "3-02-2010";

    Date artikel1UtilDate = formatter.parse(artikel1DateString);
    Date artikel2UtilDate = formatter.parse(artikel2DateString);
    Date searchUtilDate = formatter.parse(searchDateString);

    Artikel artikel1 = new Artikel("Fietsketting 3");
    Artikel artikel2 = new Artikel("Fietsband 17 inch");
    artikel1.setLeverbaarVanaf(artikel1UtilDate);
    artikel2.setLeverbaarVanaf(artikel2UtilDate);

    List<Artikel> artikelArray = Arrays.asList(artikel1, artikel2);

    when(artikelService.findArtikelenLeverbaarVanaf(searchUtilDate)).thenReturn(artikelArray);

    this.mockMvc.perform(get("/artikelen/leverbaar_vanaf/01-01-1960"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"));
  }
//  @RequestMapping(value = "artikelen/leverbaar_vanaf/{leverbaar_vanaf}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenLeverbaarVanaf(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date leverbaar_vanaf) {
//    return artikelService.findArtikelenLeverbaarVanaf(leverbaar_vanaf);
//  }


  @Test
  public void getArtikelenBeforeLeverbaarTotDateExpectOkAnd2ArtikelenAsJson() throws Exception {

  }
//  @RequestMapping(value = "artikelen/leverbaar_tot/{leverbaar_tot}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenLeverbaarTot(Model model, @PathVariable Date leverbaar_tot) {
//    return artikelService.findArtikelenLeverbaarTot(leverbaar_tot);
//  }
}

