package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtikelService {

  private ArtikelRepository artikelRepository;

  @Autowired
  public void setArtikelRepository(ArtikelRepository artikelRepository) {
    this.artikelRepository = artikelRepository;
  }

  @Transactional
  public void save(Artikel artikel) {
    this.artikelRepository.save(artikel);
  }

}
