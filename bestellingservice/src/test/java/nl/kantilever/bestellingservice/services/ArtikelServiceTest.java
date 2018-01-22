package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.config.AppConfig;
import nl.kantilever.bestellingservice.domain.Artikel;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@Import(AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class ArtikelServiceTest {

  private ArtikelService artikelService;

  @MockBean
  private ArtikelenRepository artikelenRepository;

  @Before
  public void setup(){
    this.artikelService = new ArtikelService(artikelenRepository);
  }

  @Test
  public void saveArtikelenShouldCallSave() throws Exception {
    artikelService.saveArtikelen(Mockito.anyListOf(Artikel.class));
    verify(artikelenRepository, times(1)).save(Mockito.anyListOf(Artikel.class));
  }
}
