package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.repositories.CategorieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class CategorieServiceTest {

  CategorieService categorieService;

  @MockBean
  private CategorieRepository categorieRepository;

  private Categorie categorie;

  @Before
  public void setup() {
    categorieService = new CategorieService(categorieRepository);
    this.categorie = new Categorie("Categorie1");
  }

  @Test
  public void saveCategorieShouldReturnCategorieWhenFindingByNaam() throws Exception {
    categorieService.save(categorie);
    Mockito.when(categorieRepository.findCategorieByNaam(Mockito.anyString())).thenReturn(categorie);
    Categorie categorie1 = categorieService.findCategorieByNaam("Categorie1");
    assertThat(categorieService.findCategorieByNaam("Categorie1"), is(categorie1));
  }

  @Test
  public void findAllShouldReturnListOfAllCategorieen() throws Exception {
    Mockito.when(categorieRepository.findAll()).thenReturn(Arrays.asList(categorie, new Categorie("Categorie2")));
    List<Categorie> categorieList = categorieService.findAll();
    assertThat(categorieService.findAll(), is(categorieList));
  }
}
