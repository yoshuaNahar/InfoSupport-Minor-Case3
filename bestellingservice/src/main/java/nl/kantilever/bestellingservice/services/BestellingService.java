package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BestellingService {

  private static final Logger logger = LoggerFactory.getLogger(BestellingService.class);

  private BestellingRepository bestellingRepository;

  @Autowired
  public BestellingService(BestellingRepository bestellingRepository) {
    this.bestellingRepository = bestellingRepository;
  }

  public void addBestelling(Bestelling bestelling) {
    bestellingRepository.save(bestelling);
  }

  public Bestelling findById(Long bestellingId) {
    return bestellingRepository.findOne(bestellingId);
  }

  public void getArtikellen(Bestelling bestelling) {
    logger.info("artikkelen hier ophalen obv artikellenId");
  }

}
