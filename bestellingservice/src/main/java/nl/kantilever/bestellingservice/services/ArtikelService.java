package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.entities.Artikel;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtikelService {
  private static final Logger logger = LoggerFactory.getLogger(BestellingService.class);

  private ArtikelenRepository artikelenRepository;

  @Autowired
  public ArtikelService(ArtikelenRepository artikelenRepository){
    this.artikelenRepository = artikelenRepository;
  }

  public void saveArtikelen(List<Artikel> artikelen){
    this.artikelenRepository.save(artikelen);
  }

}
