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

import java.lang.reflect.Array;
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
 * Tests for the ArtikelRestController
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

  @Test
  public void getArtikelenByNaamExpectOkAnd2Artikelen() throws Exception {
    Artikel artikel1 = new Artikel("Fietsketting 3");
    Artikel artikel2 = new Artikel("Fietsband 17 inch");
    List<Artikel> artikelArray = Arrays.asList(artikel1, artikel2);

    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }

  @Test
  public void getArtikelByArtikelnummerExpectOkAnd1Artikel() throws Exception {
    Artikel response = new Artikel("Batavus C7");
    response.setArtikelnummer(115);

    when(artikelService.findArtikelByArtikelnummer(Mockito.anyInt())).thenReturn(response);
    this.mockMvc.perform(get("/artikel/artikelnummer/115")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString( response))));
  }



  @Test
  public void getArtikelenByLeverancierExpectOkAnd2Artikelen() throws Exception {
    List<Artikel> artikelArray = Arrays.asList(new Artikel("Fietsketting 3"), new Artikel("Fietsband 17 inch"));

    when(artikelService.findArtikelenByNaam("fiets")).thenReturn(artikelArray);
    this.mockMvc.perform(get("/artikelen/naam/fiets")
    ).andDo(print()).andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json((mapper.writeValueAsString(artikelArray))));
  }
//  @RequestMapping(value = "artikelen/leverancier/{leverancier}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenByLeverancier(Model model, @PathVariable String leverancier) {
//    return artikelService.findArtikelenByLeverancier(leverancier);
//  }




  @Test
  public void getAllCategorieenExpectOkAnd2Categorieen() throws Exception {

  }
//  @RequestMapping(value = "categorieen", method = RequestMethod.GET)
//  public List<Categorie> findAllCategorieen(Model model) {
//    return categorieService.findAll();
//  }


  @Test
  public void getArtikelenByCategorieExpectOkAnd2Artikelen() throws Exception {

  }
//  @RequestMapping(value = "artikelen/categorie/{categorie}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenByCategorie(Model model, @PathVariable String categorie) {
//    Categorie matchendeCategorie = categorieService.findCategorieByNaam(categorie);
//    List<Artikel> artikelLijst = new ArrayList<Artikel>();
//
//    if (matchendeCategorie != null) {
//      artikelLijst = categorieService.findCategorieByNaam(categorie).getArtikelen();
//    } else {
//      System.out.println("Categorie not found");
//    }
//
//    return artikelLijst;
//  }


  @Test
  public void getCategorieByNaamExpectOkAnd1Categorie() throws Exception {

  }
//  @RequestMapping(value = "categorie/naam/{naam}", method = RequestMethod.GET)
//  public Categorie findCategorieByNaam(Model model, @PathVariable String naam) {
//    return categorieService.findCategorieByNaam(naam);
//  }


  @Test
  public void getArtikelenByBeschrijvingExpectOkAnd2Artikelen() throws Exception {

  }
//  @RequestMapping(value = "artikelen/beschrijving/{beschrijving}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenByBeschrijving(Model model, @PathVariable String beschrijving) {
//    return artikelService.findArtikelenByBeschrijving(beschrijving);
//  }


  @Test
  public void getArtikelenInPriceRangeExpectOkAnd2Artikelen() throws Exception {

  }
//  @RequestMapping(value = "artikelen/pricerange/{minPrice}/{maxPrice}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenInPriceRange(Model model, @PathVariable final double minPrice, @PathVariable final double maxPrice) {
//    return artikelService.findArtikelenInPriceRange(minPrice, maxPrice);
//  }


  @Test
  public void getArtikelenAfterLeverbaarVanafDateExpectOkAnd2Artikelen() throws Exception {

  }
//  @RequestMapping(value = "artikelen/leverbaar_vanaf/{leverbaar_vanaf}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenLeverbaarVanaf(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date leverbaar_vanaf) {
//    return artikelService.findArtikelenLeverbaarVanaf(leverbaar_vanaf);
//  }

  @Test
  public void getArtikelenBeforeLeverbaarTotDateExpectOkAnd2Artikelen() throws Exception {

  }
//  @RequestMapping(value = "artikelen/leverbaar_tot/{leverbaar_tot}", method = RequestMethod.GET)
//  public List<Artikel> findArtikelenLeverbaarTot(Model model, @PathVariable Date leverbaar_tot) {
//    return artikelService.findArtikelenLeverbaarTot(leverbaar_tot);
//  }
}

