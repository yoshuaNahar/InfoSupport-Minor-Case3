package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.config.Config;
import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.entities.Gebruiker;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import nl.kantilever.bestellingservice.repositories.BestellingSnapshotRepository;
import nl.kantilever.bestellingservice.repositories.GebruikerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringRunner.class)
@Import(Config.class)
//@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class BestellingServiceTest {


  private BestellingService bestellingService;

  @MockBean
  private BestellingRepository bestellingRepository;

  @MockBean
  private GebruikerRepository gebruikerRepository;

  @MockBean
  private BestellingSnapshotRepository bestellingSnapshotRepository;

  @MockBean
  private GebruikerService gebruikerService;

  @MockBean
  private ArtikelService artikelService;

  @MockBean
  private ArtikelenRepository artikelenRepository;

  @MockBean
  private RestTemplate restTemplate;

  private Bestelling bestelling;

  private BestellingSnapshot bestellingSnapshot;

  @Before
  public void setup() {
    bestellingService = new BestellingService(bestellingRepository,
      bestellingSnapshotRepository,
      restTemplate,
      gebruikerService,
      artikelService);
    bestelling = new Bestelling();
    bestelling.setGebruikerId(1L);
    bestelling.setArtikelenIds(Arrays.asList(1L, 2L));
    bestellingSnapshot = new BestellingSnapshot();
    bestellingSnapshot.setStatus("geplaatst");
    bestellingSnapshot.setId(1L);
    bestellingSnapshot.setGebruikerId(1L);
    bestellingSnapshot.setTotal(50.00);
  }

  @Test
  public void addBestellingGivenBestellingExpectBestellingViewSavedCorrectly() {
    Mockito.when(bestellingRepository.findOne(Mockito.anyLong())).thenReturn(bestelling);
    bestellingService.addBestelling(bestelling);
    Bestelling bestellingFromDb = bestellingService.findBestellingById(Mockito.anyLong());
    assertBestelling(bestellingFromDb, bestelling);
  }

  @Test
  public void saveBestellingSnapshotGivenBestellingExpectBestellingAndBestellingsnapshotSavedAndSameGebruikerId() {
    Mockito.when(bestellingRepository.findOne(Mockito.anyLong())).thenReturn(bestelling);
    Mockito.when(bestellingSnapshotRepository.findFirstById(Mockito.anyLong())).thenReturn(bestellingSnapshot);
    Mockito.when(gebruikerService.getGebruikerById(Mockito.anyLong())).thenReturn(new Gebruiker(Mockito.anyLong(), "Thom", "van Oorschot"));

    bestellingService.addBestelling(bestelling);
    Bestelling bestellingFromDb = bestellingService.findBestellingById(Mockito.anyLong());

    Artikel a = new Artikel();
    a.setPrijs(12.0);
    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));
    bestellingService.saveBestellingSnapshot(bestellingFromDb);

    BestellingSnapshot bestellingSnapshotFromDb = bestellingService.findById(Mockito.anyLong());
    assertBestelling(bestellingSnapshotFromDb, bestelling);
  }

  // We should create our own matcher that does this.
  private void assertBestelling(BestellingSnapshot expected, Bestelling actual) {
    assertThat(expected.getGebruikerId(), is(actual.getGebruikerId()));
  }

  private void assertBestelling(Bestelling expected, Bestelling actual) {
    assertThat(expected.getGebruikerId(), is(actual.getGebruikerId()));
  }

  @Test
  public void findAllWith2BestellingenAndNoLimitShouldReturn2Bestellingen() {
    Artikel a = new Artikel();
    a.setPrijs(10.0);
    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));
    BestellingSnapshot bestellingSnapshot2 = new BestellingSnapshot();
    bestellingSnapshot2.setGebruikerId(2L);
    bestellingSnapshot2.setArtikelen(Arrays.asList(a));

    ArrayList bestellingenList = new ArrayList();
    bestellingenList.add(bestellingSnapshot);
    bestellingenList.add(bestellingSnapshot2);
    Page<BestellingSnapshot> page = new PageImpl(bestellingenList);
    Mockito.when(bestellingSnapshotRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
    Mockito.when(gebruikerService.getGebruikerById(Mockito.anyLong())).thenReturn(new Gebruiker(1, "Thom", "van Oorschot"));
    assertThat(bestellingService.findAll(null).size(), is(2));
  }

  @Test
  public void findAllWith2BestellingenAndLimit1ShouldReturn1Bestelling() {
    Artikel a = new Artikel();
    a.setPrijs(10.0);
    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    ArrayList bestellingenList = new ArrayList();
    bestellingenList.add(bestellingSnapshot);
    Page<BestellingSnapshot> page = new PageImpl(bestellingenList);
    Pageable pageRequest = new PageRequest(0, 1);
    Mockito.when(bestellingSnapshotRepository.findAll(pageRequest)).thenReturn(page);
    Mockito.when(gebruikerService.getGebruikerById(Mockito.anyLong())).thenReturn(new Gebruiker(1, "Thom", "van Oorschot"));
    assertThat(bestellingService.findAll(1).size(),is(1));
  }

  @Test
  public void findAllByStatusWithStatusGeplaatstAnd2MatchingBestellingenAndNoLimitShouldReturn2Bestellingen() {
    Artikel a = new Artikel();
    a.setPrijs(10.0);
    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    BestellingSnapshot bestellingSnapshot2 = new BestellingSnapshot();
    bestellingSnapshot2.setId(2L);
    bestellingSnapshot2.setGebruikerId(2L);
    bestellingSnapshot2.setArtikelen(Arrays.asList(a));

    BestellingSnapshot bestellingSnapshot3 = new BestellingSnapshot();
    bestellingSnapshot2.setId(3L);
    bestellingSnapshot3.setGebruikerId(3L);
    bestellingSnapshot3.setArtikelen(Arrays.asList(a));


    ArrayList bestellingenList = new ArrayList();
    bestellingenList.add(bestellingSnapshot);
    bestellingenList.add(bestellingSnapshot2);
    bestellingenList.add(bestellingSnapshot3);

    Page<BestellingSnapshot> page = new PageImpl(bestellingenList);
    Mockito.when(bestellingSnapshotRepository.findAllByStatus(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);

    bestellingService.setBestellingIngepakt(1L);
    bestellingService.setBestellingIngepakt(2L);
    bestellingService.setBestellingIngepakt(3L);

    assertThat(bestellingService.findAllByStatus("geplaatst", null).size(), is(3));
  }

  @Test
  public void findAllBestellingenPerGebruikerShouldReturnAllBestellingen() throws Exception {
    List<BestellingSnapshot> bestellingSnapshots = new ArrayList<>();
    bestellingSnapshots.add(bestellingSnapshot);
    Mockito.when(bestellingSnapshotRepository.findBestellingenByGebruiker(Mockito.anyInt())).thenReturn(bestellingSnapshots);

    assertThat(bestellingService.getBestellingenGebruiker(1).size(),is(1));
  }

  @Test
  public void getTotaalwarrdeBestellingVanGebruikerReturnsTotaalwaarde() throws Exception {
    Artikel a = new Artikel();
    a.setPrijs(10.0);
    doReturn(a).when(restTemplate).getForObject(any(String.class), eq(Artikel.class));

    List<BestellingSnapshot> bestellingSnapshots = new ArrayList<>();
    bestellingSnapshots.add(bestellingSnapshot);
    BestellingSnapshot bestellingSnapshot2 = new BestellingSnapshot();
    bestellingSnapshot2.setId(2L);
    bestellingSnapshot2.setGebruikerId(1L);
    bestellingSnapshot2.setStatus("geplaatst");
    bestellingSnapshot2.setTotal(50.0);
    bestellingSnapshots.add(bestellingSnapshot2);

    Mockito.when(bestellingSnapshotRepository.findBestellingenByGebruiker(Mockito.anyInt())).thenReturn(bestellingSnapshots);

    assertThat(bestellingService.getTotaalwaardeBestellingen(1), is(100.0));
  }

  @Test
  public void getGebruikersMetBestellingenBoven500ReturnsGebruikers() throws Exception {
    List<Gebruiker> gebruikers = new ArrayList<>();
    Gebruiker gebruiker = new Gebruiker();
    gebruiker.setMaxCrediet(500);
    gebruikers.add(gebruiker);
    Mockito.when(gebruikerService.getGebruikersBoven500()).thenReturn(gebruikers);

    assertThat(bestellingService.getGebruikersMetBestellingenBoven500().size(), is(1));
  }
}
