package nl.kantilever.bestellingservice.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

// Aangezien we geen heel complexe dingen doen in de bestellingRepository voeg
// ik hier gewoon IntegrationTests uit tussen de BestellingService, BestellingRepository en H2 uit.
@RunWith(SpringRunner.class)
@DataJpaTest
//@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BestellingServiceTest {

  private BestellingService bestellingService;

  @Autowired
  private BestellingRepository bestellingRepository;

  @Autowired
  private TestEntityManager entityManager;

  private Bestelling bestelling;

  @Before
  public void setup() {
    bestellingService = new BestellingService(bestellingRepository);

    bestelling = new Bestelling();
    bestelling.setGeplaatstOp(LocalDateTime.now());
    bestelling.setGebruikerId(1L);
    bestelling.setArtikelenIds(Arrays.asList(1L, 2L));
  }

  @Test
  public void addBestellingGivenBestellingExpectBestellingSavedCorrectly() {
    bestellingService.addBestelling(bestelling);
    entityManager.flush();
    entityManager.clear();

    Bestelling bestellingFromDb = bestellingService.findById(1L);

    assertBestelling(bestellingFromDb, bestelling);
  }

  // We should create our own matcher that does this.
  private void assertBestelling(Bestelling expected, Bestelling actual) {
    assertThat(expected.getId(), is(actual.getGebruikerId()));
    assertThat(expected.getGebruikerId(), is(actual.getGebruikerId()));
    assertThat(expected.getGeplaatstOp(), is(actual.getGeplaatstOp()));
  }
}
