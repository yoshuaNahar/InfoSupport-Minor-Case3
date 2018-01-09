package nl.kantilever.bestellingservice.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import nl.kantilever.bestellingservice.config.Config;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import nl.kantilever.bestellingservice.repositories.BestellingSnapshotRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

// Aangezien we geen heel complexe dingen doen in de bestellingRepository voeg
// ik hier gewoon IntegrationTests uit tussen de BestellingService, BestellingRepository en H2 uit.
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(Config.class)
//@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BestellingServiceTest {

  private BestellingService bestellingService;

  @Autowired
  private BestellingRepository bestellingRepository;

  @Autowired
  private BestellingSnapshotRepository bestellingSnapshotRepository;

  @Autowired
  private ArtikelenRepository artikelenRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private TestEntityManager entityManager;

  private Bestelling bestelling;

  @Before
  public void setup() {
    bestellingService = new BestellingService(
      bestellingRepository,
      bestellingSnapshotRepository,
      artikelenRepository,
      restTemplate);

    bestelling = new Bestelling();
    bestelling.setGebruikerId(1L);
    bestelling.setArtikelenIds(Arrays.asList(1L, 2L));
  }

  @Test
  public void addBestellingGivenBestellingExpectBestellingViewSavedCorrectly() {
    bestellingService.addBestelling(bestelling);
    entityManager.flush();
    entityManager.clear();

    Bestelling bestellingFromDb = bestellingService.findBestellingById(1L);

    assertBestelling(bestellingFromDb, bestelling);
  }

  @Test
  @Ignore("Should mock the RestTemplate but do this later....")
  public void saveBestellingViewGivenBestellingExpectArtikelenAndBestellingViewSaved() {
    bestellingService.saveBestellingSnapshot(bestelling);
    entityManager.flush();
    entityManager.clear();
  }

  // We should create our own matcher that does this.
  private void assertBestelling(BestellingSnapshot expected, Bestelling actual) {
    assertThat(expected.getId(), is(actual.getGebruikerId()));
    assertThat(expected.getGebruikerId(), is(actual.getGebruikerId()));
  }

  private void assertBestelling(Bestelling expected, Bestelling actual) {
    assertThat(expected.getId(), is(actual.getGebruikerId()));
    assertThat(expected.getGebruikerId(), is(actual.getGebruikerId()));
  }

}
