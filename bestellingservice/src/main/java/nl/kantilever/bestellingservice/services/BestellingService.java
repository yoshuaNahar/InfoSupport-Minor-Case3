package nl.kantilever.bestellingservice.services;

import java.util.ArrayList;
import java.util.List;
import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import nl.kantilever.bestellingservice.repositories.BestellingSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BestellingService {

  private static final Logger logger = LoggerFactory.getLogger(BestellingService.class);

  private BestellingRepository bestellingRepository;

  private BestellingSnapshotRepository bestellingSnapshotRepository;
  private ArtikelenRepository artikelenRepository;

  private RestTemplate restTemplate;

  @Value("${urls.webwinkel}")
  private String webwinkelUrl;

  @Autowired
  public BestellingService(
    BestellingRepository bestellingRepository,
    BestellingSnapshotRepository bestellingSnapshotRepository,
    ArtikelenRepository artikelenRepository,
    RestTemplate restTemplate) {
    this.bestellingRepository = bestellingRepository;
    this.bestellingSnapshotRepository = bestellingSnapshotRepository;
    this.artikelenRepository = artikelenRepository;
    this.restTemplate = restTemplate;
  }

  public void addBestelling(Bestelling bestelling) {
    bestellingRepository.save(bestelling);
  }

  public Bestelling findBestellingById(Long bestellingId) {
    return bestellingRepository.findOne(bestellingId);
  }

  public BestellingSnapshot findById(Long bestellingId) {
    return bestellingSnapshotRepository.findOne(bestellingId);
  }

  public Iterable<BestellingSnapshot> findAll() {
    return bestellingSnapshotRepository.findAll();
  }

  public void saveBestellingView(Bestelling bestelling) {
    List<Artikel> artikelen = new ArrayList<>();

    bestelling.getArtikelenIds().forEach(id ->
      artikelen.add(restTemplate.getForObject("http://" + webwinkelUrl + "artikel/artikelnummer/" + id, Artikel.class)
    ));

    logger.info("artikkelen list: {}", artikelen);

    artikelenRepository.save(artikelen);

    BestellingSnapshot bestellingView = new BestellingSnapshot();
    bestellingView.setId(bestelling.getId());
    bestellingView.setGebruikerId(bestelling.getGebruikerId());
    bestellingView.setGeplaatstOp(bestelling.getGeplaatstOp());
    bestellingView.setArtikelen(artikelen);

    bestellingSnapshotRepository.save(bestellingView);

    logger.info("artikelen hier ophalen obv artikellenId, {}", bestelling);
  }

}
