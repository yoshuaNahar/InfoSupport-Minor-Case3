package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtikelServiceTest {

  @Autowired
  private ArtikelService artikelService;

  @MockBean
  private ArtikelRepository artikelRepository;

  @Test
  public void saveMaintenanceAssignment() {
    Artikel artikel = new Artikel();
    this.artikelService.save(artikel);

    verify(artikelRepository, times(1)).save(any(Artikel.class));
  }


}
