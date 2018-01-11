package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.config.Config;
import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import nl.kantilever.bestellingservice.repositories.BestellingSnapshotRepository;
import nl.kantilever.bestellingservice.repositories.GebruikerRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

// Aangezien we geen heel complexe dingen doen in de bestellingRepository voeg
// ik hier gewoon IntegrationTests uit tussen de BestellingService, BestellingRepository en H2 uit.
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(Config.class)
//@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BestellingServiceTest {

  @Autowired
  private BestellingService bestellingService;

  @Autowired
  private BestellingRepository bestellingRepository;

  @Autowired
  private GebruikerRepository gebruikerRepository;
  @Autowired
  private BestellingSnapshotRepository bestellingSnapshotRepository;

  @Autowired
  private GebruikerService gebruikerService;

  @Autowired
  private ArtikelenRepository artikelenRepository;

  @MockBean
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
      restTemplate,
      gebruikerService,
      gebruikerRepository);

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
  public void saveBestellingSnapshotGivenBestellingExpectArtikelenAndBestellingSnapshotSaved() {
    Artikel a = new Artikel();
    a.setPrijs(12.0);

    bestellingService.addBestelling(bestelling);
    entityManager.flush();
    Bestelling bestellingFromDb = bestellingService.findBestellingById(1L);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));
    bestellingService.saveBestellingSnapshot(bestellingFromDb);

    Bestelling bestellingSnapshotFromDb = bestellingService.findBestellingById(1L);
    assertBestelling(bestellingSnapshotFromDb, bestelling);
    assertThat(bestelling.getArtikelenIds(), is(bestellingSnapshotFromDb.getArtikelenIds()));
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

  @Test
  public void findAllWith2BestellingenAndNoLimitShouldReturn2Bestellingen() {
    Bestelling bestelling2 = new Bestelling();
    bestelling2.setGebruikerId(2L);
    bestelling2.setArtikelenIds(Arrays.asList(2L, 3L, 4L));

    Artikel a = new Artikel();
    a.setPrijs(10.0);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    bestellingService.saveBestellingSnapshot(bestelling);
    bestellingService.saveBestellingSnapshot(bestelling2);

    assertThat(2, is(bestellingService.findAll(null).size()));
  }

  @Test
  public void findAllWith2BestellingenAndLimit1ShouldReturn1Bestelling() {
    Bestelling bestelling2 = new Bestelling();
    bestelling2.setGebruikerId(2L);
    bestelling2.setArtikelenIds(Arrays.asList(2L, 3L, 4L));

    Artikel a = new Artikel();
    a.setPrijs(10.0);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    bestellingService.saveBestellingSnapshot(bestelling);
    bestellingService.saveBestellingSnapshot(bestelling2);

    assertThat(1, is(bestellingService.findAll(1).size()));
  }

  @Test
  public void findAllByStatusWithStatusGeplaatstAnd2MatchingBestellingenAndNoLimitShouldReturn2Bestellingen() {
    Bestelling bestelling2 = new Bestelling();
    bestelling2.setGebruikerId(2L);
    bestelling2.setArtikelenIds(Arrays.asList(2L, 3L, 4L));

    Bestelling bestelling3 = new Bestelling();
    bestelling3.setGebruikerId(3L);
    bestelling3.setArtikelenIds(Arrays.asList(5L, 6L, 7L));

    Artikel a = new Artikel();
    a.setPrijs(10.0);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    bestellingService.saveBestellingSnapshot(bestelling);
    bestellingService.saveBestellingSnapshot(bestelling2);
    bestellingService.saveBestellingSnapshot(bestelling3);

    bestellingService.setBestellingIngepakt(1L);

    assertThat(2, is(bestellingService.findAllByStatus("geplaatst", null).size()));
  }

  @Test
  public void findAllByStatusWithStatusGeplaatstAnd2MatchingBestellingenAndLimit1ShouldReturn1Bestellingen() {
    Bestelling bestelling2 = new Bestelling();
    bestelling2.setGebruikerId(2L);
    bestelling2.setArtikelenIds(Arrays.asList(2L, 3L, 4L));

    Bestelling bestelling3 = new Bestelling();
    bestelling3.setGebruikerId(3L);
    bestelling3.setArtikelenIds(Arrays.asList(5L, 6L, 7L));

    Artikel a = new Artikel();
    a.setPrijs(10.0);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    bestellingService.saveBestellingSnapshot(bestelling);
    bestellingService.saveBestellingSnapshot(bestelling2);
    bestellingService.saveBestellingSnapshot(bestelling3);

    bestellingService.setBestellingIngepakt(1L);

    assertThat(1, is(bestellingService.findAllByStatus("geplaatst", 1).size()));
  }

  @Test
  public void setBestellingIngepaktWithId1AndBestellingInDatabaseShouldSetBestellingStatusIngepakt() {
    Artikel a = new Artikel();
    a.setPrijs(10.0);

    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));
    bestellingService.saveBestellingSnapshot(bestelling);

    bestellingService.setBestellingIngepakt(1L);
    entityManager.clear();

    BestellingSnapshot bestelling = bestellingService.findById(1L);
    assertThat("ingepakt", is(bestelling.getStatus()));
  }
}
