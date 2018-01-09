package nl.kantilever.webwinkel.services;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import nl.kantilever.webwinkel.repositories.CategorieRepository;
import org.hibernate.Hibernate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtikelServiceTest {

  @Autowired
  private ArtikelService artikelService;

  @Autowired
  private ArtikelRepository artikelRepository;

  @Test
  public void saveWithNewArtikelShouldCallSaveOnArtikelRepository() {
    Artikel artikel = new Artikel();
    artikelService.save(artikel);

    assertThat(artikelRepository.findArtikelByArtikelnummer(1), is(notNullValue()));
  }
}
