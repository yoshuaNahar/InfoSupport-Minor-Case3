package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtikelServiceTest {


  private ArtikelService artikelService;

  @MockBean
  private ArtikelRepository artikelRepository;

  @MockBean
  private CategorieService categorieService;

  private Artikel artikel;

  @Before
  public void setup() {
    artikelService = new ArtikelService(artikelRepository, categorieService);
    this.artikel = new Artikel("ArtikelNaam", "Beschrijving", 50.0, "Afbeelding", new Date(1516184038445l), new Date(1516284038445L), "leverancierCode", "Leverancier",Arrays.asList(new Categorie("CategorieNaam")));
  }

  @Test
  public void saveNewArtikelShouldReturnArtikelWhenFindArtikelByNummer() {
    List<Categorie> categories = Arrays.asList(new Categorie("categorie1"), new Categorie("categorie2"));
    Artikel artikel = new Artikel();
    artikel.setCategorieen(categories);
    Mockito.when(categorieService.findAll()).thenReturn(categories);
    Mockito.when(artikelRepository.findArtikelByArtikelnummer(0)).thenReturn(artikel);
    artikelService.save(artikel);
    assertThat(artikelService.findArtikelByArtikelnummer(0), is(artikel));
  }

  @Test
  public void findArtikelenByNaamShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(new Artikel("artikelNaam"), new Artikel("artikelNaam"), new Artikel("artikelNaam"));
    Mockito.when(artikelRepository.findArtikelenByNaam(Mockito.anyString())).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenByNaam("artikelNaam");
    assertThat(artikelService.findArtikelenByNaam("artikelNaam"), is(artikelen));
  }

  @Test
  public void findArtikelenByLeverancierShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(artikel);
    Mockito.when(artikelRepository.findArtikelenByLeverancier(Mockito.anyString())).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenByLeverancier("Leverancier");
    assertThat(artikelService.findArtikelenByLeverancier("Leverancier"), is(artikelen));
  }

  @Test
  public void findArtikelenByBeschrijvingShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(artikel);
    Mockito.when(artikelRepository.findArtikelenByBeschrijving(Mockito.anyString())).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenByBeschrijving("Beschrijving");
    assertThat(artikelService.findArtikelenByBeschrijving("Beschrijving"), is(artikelen));
  }

  @Test
  public void findArtikelenByPriceRangeShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(artikel);
    Mockito.when(artikelRepository.findArtikelenInPriceRange(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenInPriceRange(40,60);
    assertThat(artikelService.findArtikelenInPriceRange(40,60), is(artikelen));
  }

  @Test
  public void findArtikelenByLeverbaarVanafShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(artikel);
    Mockito.when(artikelRepository.findArtikelenLeverbaarVanaf(Mockito.any(java.util.Date.class))).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenLeverbaarVanaf(new Date(1516184038445l));
    assertThat(artikelService.findArtikelenLeverbaarVanaf(new Date(1516184038445l)), is(artikelen));
  }

  @Test
  public void findArtikelenByLeverbaarTotShouldReturnArtikelenThatMatch() throws Exception {
    List<Artikel> artikelenList = Arrays.asList(artikel);
    Mockito.when(artikelRepository.findArtikelenLeverbaarTot(Mockito.any(java.util.Date.class))).thenReturn(artikelenList);
    List<Artikel> artikelen = artikelService.findArtikelenLeverbaarTot(new Date(1516184038445l));
    assertThat(artikelService.findArtikelenLeverbaarTot(new Date(1516184038445l)), is(artikelen));
  }

}
