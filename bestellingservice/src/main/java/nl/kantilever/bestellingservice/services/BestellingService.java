package nl.kantilever.bestellingservice.services;

import java.util.ArrayList;
import java.util.List;
import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.entities.Bestelling;
import nl.kantilever.bestellingservice.entities.BestellingView;
import nl.kantilever.bestellingservice.repositories.ArtikellenRepository;
import nl.kantilever.bestellingservice.repositories.BestellingRepository;
import nl.kantilever.bestellingservice.repositories.BestellingViewRepository;
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

  private BestellingViewRepository bestellingViewRepository;
  private ArtikellenRepository artikellenRepository;

  private RestTemplate restTemplate;

  @Value("${urls.webwinkel}")
  private String webwinkelUrl;

  @Autowired
  public BestellingService(
    BestellingRepository bestellingRepository,
    BestellingViewRepository bestellingViewRepository,
    ArtikellenRepository artikellenRepository,
    RestTemplate restTemplate) {
    this.bestellingRepository = bestellingRepository;
    this.bestellingViewRepository = bestellingViewRepository;
    this.artikellenRepository = artikellenRepository;
    this.restTemplate = restTemplate;
  }

  public void addBestelling(Bestelling bestelling) {
    bestellingRepository.save(bestelling);
  }

  public Bestelling findBestellingById(Long bestellingId) {
    return bestellingRepository.findOne(bestellingId);
  }

  public BestellingView findById(Long bestellingId) {
    return bestellingViewRepository.findOne(bestellingId);
  }

  public void saveBestellingView(Bestelling bestelling) {
    List<Artikel> artikellen = new ArrayList<>();

    bestelling.getArtikelenIds().forEach(id ->
      artikellen.add(restTemplate.getForObject("http://" + webwinkelUrl + "artikel/artikelnummer/" + id, Artikel.class)
    ));

    logger.info("artikkelen list: {}", artikellen);

    artikellenRepository.save(artikellen);

    BestellingView bestellingView = new BestellingView();
    bestellingView.setId(bestelling.getId());
    bestellingView.setGebruikerId(bestelling.getGebruikerId());
    bestellingView.setGeplaatstOp(bestelling.getGeplaatstOp());
    bestellingView.setArtikellen(artikellen);

    bestellingViewRepository.save(bestellingView);

    logger.info("artikkelen hier ophalen obv artikellenId, {}", bestelling);
  }

}
