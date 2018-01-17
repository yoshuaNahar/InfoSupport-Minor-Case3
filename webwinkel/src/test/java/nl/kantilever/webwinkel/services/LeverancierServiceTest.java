package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Leverancier;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
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
@SpringBootTest
public class LeverancierServiceTest {

  LeverancierService leverancierService;

  @MockBean
  private ArtikelRepository artikelRepository;


  @Before
  public void setup() {
    leverancierService = new LeverancierService(artikelRepository);
  }

  @Test
  public void findAllLeveranciersShouldReturnAllLeveranciers() throws Exception {
    Mockito.when(artikelRepository.findAll()).thenReturn(Arrays.asList(new Artikel("Artikel1"), new Artikel("Artikel2")));
    List<Leverancier> allLeveranciers = leverancierService.findAllLeveranciers();
    assertThat(leverancierService.findAllLeveranciers().size(), is(2));
  }
}
