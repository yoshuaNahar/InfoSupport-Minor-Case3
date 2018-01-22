package nl.kantilever.webwinkel.events.listeners;

import nl.kantilever.webwinkel.services.ArtikelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ArtikelAanCatalogusToegevoegdListenerTest {

  private ArtikelAanCatalogusToegevoegdListener artikelAanCatalogusToegevoegdListener;

  @MockBean
  private ArtikelService artikelService;

  @Before
  public void setup() {
    this.artikelAanCatalogusToegevoegdListener = new ArtikelAanCatalogusToegevoegdListener(artikelService);
  }

  @Test
  public void listenShouldCallSave() throws Exception {
    artikelAanCatalogusToegevoegdListener.listen(new byte[10]);
    verify(artikelService, times(1)).save(Mockito.any());
  }

  @Test
  public void name() throws Exception {

  }
}
