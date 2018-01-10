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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    return bestellingSnapshotRepository.findFirstById(bestellingId);
  }

  public List<BestellingSnapshot> findAll(Integer limit) {
    Pageable pageLimit = new PageRequest(0, Integer.MAX_VALUE);
    if (limit != null) {
      pageLimit = new PageRequest(0, limit);
    }
    return bestellingSnapshotRepository.findAll(pageLimit).getContent();
  }

  public List<BestellingSnapshot> findAllByStatus(String status, Integer limit) {
    Pageable pageLimit = new PageRequest(0, Integer.MAX_VALUE);
    if (limit != null) {
      pageLimit = new PageRequest(0, limit);
    }
    return bestellingSnapshotRepository.findAllByStatus(status, pageLimit).getContent();
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

  @Transactional
  public void setBestellingIngepakt(Long bestellingId) {
    bestellingSnapshotRepository.setStatusIngepakt(bestellingId);
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
