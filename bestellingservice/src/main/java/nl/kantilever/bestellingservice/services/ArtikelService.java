package nl.kantilever.bestellingservice.services;

import java.util.List;
import nl.kantilever.bestellingservice.domain.Artikel;
import nl.kantilever.bestellingservice.repositories.ArtikelenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtikelService {

  private ArtikelenRepository artikelenRepository;

  @Autowired
  public ArtikelService(ArtikelenRepository artikelenRepository){
    this.artikelenRepository = artikelenRepository;
  }

  public void saveArtikelen(List<Artikel> artikelen){
    this.artikelenRepository.save(artikelen);
  }

}
