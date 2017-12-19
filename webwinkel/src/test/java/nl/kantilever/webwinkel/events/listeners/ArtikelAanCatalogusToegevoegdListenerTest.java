package nl.kantilever.webwinkel.events.listeners;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.services.ArtikelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ArtikelAanCatalogusToegevoegdListenerTest {

  @MockBean
  private ArtikelService apkCheckService;

  private ArtikelAanCatalogusToegevoegdListener artikelAanCatalogusToegevoegdListener;

  @Before
  public void setup() {
    this.artikelAanCatalogusToegevoegdListener = new ArtikelAanCatalogusToegevoegdListener();
  }

  @Test
  public void listenWithNewArtikelShouldCallSaveOnArtikelService() {
    this.artikelAanCatalogusToegevoegdListener.setArtikelService(apkCheckService);
    this.artikelAanCatalogusToegevoegdListener.listen(new Artikel());
    verify(apkCheckService, times(1)).save(any(Artikel.class));
  }
}
