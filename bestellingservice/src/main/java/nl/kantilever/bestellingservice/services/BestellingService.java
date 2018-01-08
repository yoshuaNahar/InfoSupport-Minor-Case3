package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BestellingService {

  private static final Logger logger = LoggerFactory.getLogger(BestellingService.class);

  private BestellingRepository bestellingRepository;
  private RestTemplate restTemplate;

  @Autowired
  public BestellingService(BestellingRepository bestellingRepository, RestTemplate restTemplate) {
    this.bestellingRepository = bestellingRepository;
    this.restTemplate = restTemplate;
  }

  public void addBestelling(Bestelling bestelling) {
    bestellingRepository.save(bestelling);
  }

  public Bestelling findById(Long bestellingId) {
    return bestellingRepository.findOne(bestellingId);
  }

  public void getArtikelen(Bestelling bestelling) {
    restTemplate.getForObject("http://", Artikel.class);

    logger.info("artikkelen hier ophalen obv artikellenId, {}", bestelling);



  }

}
