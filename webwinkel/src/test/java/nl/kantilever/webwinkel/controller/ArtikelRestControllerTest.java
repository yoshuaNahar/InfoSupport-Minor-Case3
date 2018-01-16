package nl.kantilever.webwinkel.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import nl.kantilever.webwinkel.WebWinkelServiceApplication;
import nl.kantilever.webwinkel.controllers.ArtikelRestController;
import nl.kantilever.webwinkel.domain.*;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(ArtikelRestController.class)
public class ArtikelRestControllerTest {
  @MockBean
  private CategorieService categorieService;

  @MockBean
  private ArtikelService artikelService;

  @MockBean
  private ArtikelRepository artikelRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  private Artikel artikel1;
  private Artikel artikel2;
  private Categorie categorie1;
  private Categorie categorie2;
  private List<Artikel> artikelArray;
  private List<Categorie> categorieArray;

  @Before
  public void intialize() {
    try {
      categorie1 = new Categorie("Onderdelen", "bike_lock_small.gif");
      categorie2 = new Categorie("Roestvrijstaal", "pedal_small.gif");
      categorie1.setArtikelen(new ArrayList<>());
      categorie2.setArtikelen(new ArrayList<>());

      artikel1 = new Artikel(115L, "Fietsketting", "Robuuste fietsketting, past op vrijwel iedere fiets. Uitgerust met roestvrijstale componenten.", 79.50, "silver_chain_small.gif", new Date(5), new Date(1999999999), "KAN0LE", "Henk & Nagel B.V.", Arrays.asList(categorie1, categorie2));
      artikel2 = new Artikel(116L, "Fietstas", "Ruime fietstas die past op vrijwel iedere fiets. Uitgerust met leren gesp en waterdichte naden.", 79.50, "silver_chain_small.gif", new Date(5), new Date(1222222999), "COUDNR", "Courend B.V.", Arrays.asList(categorie1, categorie2));

      artikelArray = Arrays.asList(artikel1, artikel2);
      categorieArray = Arrays.asList(categorie1, categorie2);

    } catch (Exception e) {
      System.out.println("Temp data is al aangemaakt of er is iets foutgegaan tijdens het aanmaken");
    }
  }

  @Test
  public void getArtikelen_ByNaam_ExpectOkAnd2ArtikelenAsJson() throws Exception {
    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);

    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }

  @Test
  public void getArtikel_ByArtikelnummer_ExpectOkAnd1ArtikelAsJson() throws Exception {
    when(artikelService.findArtikelByArtikelnummer(Mockito.anyInt())).thenReturn(artikel1);

    this.mockMvc.perform(get("/artikel/artikelnummer/115")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikel1))));
  }


  @Test
  public void getArtikelen_ByLeverancier_ExpectOkAnd2ArtikelenAsJson() throws Exception {
    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);

    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }


  @Test
  public void getAllCategorieen_ExpectOkAnd2CategorieenAsJson() throws Exception {
    when(categorieService.findAll()).thenReturn(categorieArray);

    this.mockMvc.perform(get("/categorieen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorieArray))));
  }


  @Test
  public void getArtikelen_ByCategorie_ExpectOkAnd2ArtikelenAsJson() throws Exception {
    Categorie categorie = new Categorie("fietsonderdelen");
    categorie.setArtikelen(artikelArray);

    when(categorieService.findCategorieByNaam("fietsonderdelen")).thenReturn(categorie);
    this.mockMvc.perform(get("/artikelen/categorie/fietsonderdelen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorie.getArtikelen()))));
  }


  @Test
  public void getCategorie_ByNaam_ExpectOkAnd1CategorieAsJson() throws Exception {
    Categorie categorie = new Categorie("Fietsen");

    when(categorieService.findCategorieByNaam("fietsen")).thenReturn(categorie);
    this.mockMvc.perform(get("/categorie/naam/fietsen")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(categorie))));
  }


  @Test
  public void getArtikelen_ByBeschrijving_ExpectOkAnd2ArtikelenAsJson() throws Exception {
    when(artikelService.findArtikelenByBeschrijving("fiets")).thenReturn(artikelArray);

    this.mockMvc.perform(get("/artikelen/beschrijving/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }


  @Test
  public void getArtikelen_InPriceRange_ExpectOkAnd2ArtikelenAsJson() throws Exception {
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
  public void getArtikelen_AfterLeverbaarVanafDate_ExpectOkAnd2ArtikelenAsJson() throws Exception {
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

  @Test
  public void getArtikelen_BeforeLeverbaarTotDate_ExpectOkAnd2ArtikelenAsJson() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String artikel1DateString = "7-05-2013";
    String artikel2DateString = "28-07-2015";
    String searchDateString = "3-02-2010";

    Date artikel1UtilDate = formatter.parse(artikel1DateString);
    Date artikel2UtilDate = formatter.parse(artikel2DateString);
    Date searchUtilDate = formatter.parse(searchDateString);

    Artikel artikel1 = new Artikel("Fietsketting 3");
    Artikel artikel2 = new Artikel("Fietsband 17 inch");
    artikel1.setLeverbaarTot(artikel1UtilDate);
    artikel2.setLeverbaarTot(artikel2UtilDate);

    List<Artikel> artikelArray = Arrays.asList(artikel1, artikel2);

    when(artikelService.findArtikelenLeverbaarVanaf(searchUtilDate)).thenReturn(artikelArray);

    this.mockMvc.perform(get("/artikelen/leverbaar_tot/01-01-2024"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"));
  }


  @Test
  @Ignore
  public void getArtikelen_SearchSingleVERANDEREN() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(artikel2);
    System.out.println(artikel2);
    artikel2.setCategorieen(new ArrayList<>());
    ZoekCriteriaBuilder builder = new ZoekCriteriaBuilder();
    builder.voegZoekCriteriumToe("naam", ":", "fietstas"); //Zoekfilter bestaat altijd uit 3 delen (key, operator, waarde)

    Specification<Artikel> spec = builder.build();


    when(artikelRepository.findAll(spec)).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen?zoeken=naam:fietstas")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }
}

