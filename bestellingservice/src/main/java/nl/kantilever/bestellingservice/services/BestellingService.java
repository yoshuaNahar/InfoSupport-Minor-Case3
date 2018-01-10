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
    return bestellingSnapshotRepository.findById(bestellingId);
  }

  public Iterable<BestellingSnapshot> findAll() {
    return bestellingSnapshotRepository.findAll();
  }

  public Iterable<BestellingSnapshot> findAllWithStatus(String status) {
    return bestellingSnapshotRepository.findAllByStatus(status);
  }

  public void saveBestellingSnapshot(Bestelling bestelling) {
    List<Artikel> artikelen = new ArrayList<>();

    // NOTE: webwinkel + replayservice draaien
    bestelling.getArtikelenIds().forEach(id ->
      artikelen.add(restTemplate
        .getForObject("http://" + webwinkelUrl + "artikel/artikelnummer/" + id, Artikel.class)
      ));

    logger.info("artikkelen list: {}", artikelen);

    artikelenRepository.save(artikelen);

    Double bestellingTotal = artikelen.stream().mapToDouble(Artikel::getPrijs).sum();

    BestellingSnapshot bestellingSnapshot = new BestellingSnapshot();
    bestellingSnapshot.setId(bestelling.getId());
    bestellingSnapshot.setGebruikerId(bestelling.getGebruikerId());
    bestellingSnapshot.setArtikelen(artikelen);
    bestellingSnapshot.setTotal(bestellingTotal);
    bestellingSnapshot.setStatus("geplaatst");

    bestellingSnapshotRepository.save(bestellingSnapshot);

    logger.info("artikelen hier ophalen obv artikellenId, {}", bestelling);
  }

  public List<BestellingSnapshot> getBestellingenGebruiker(int id){
    List<BestellingSnapshot> bestellingenByGebruiker = bestellingSnapshotRepository.findBestellingenByGebruiker(id);
    if(bestellingenByGebruiker != null){
       return bestellingenByGebruiker;
    }
    return null;
  }

  public Double getTotaalwaardeBestellingen(int id){
    Double totaalWaarde = 0.0;
    List<BestellingSnapshot> bestellingenByGebruiker = bestellingSnapshotRepository.findBestellingenByGebruiker(id);
    if(bestellingenByGebruiker != null){
      for (BestellingSnapshot bestellingSnapshot : bestellingenByGebruiker) {
        if(!bestellingSnapshot.getStatus().equals("betaald")){
          totaalWaarde = totaalWaarde + bestellingSnapshot.getTotal();
        }
      }
    }
    return totaalWaarde;
  }


}
