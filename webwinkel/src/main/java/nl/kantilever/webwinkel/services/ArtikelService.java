package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

  public Artikel findArtikelByArtikelnummer(int artikelnummer) {
    return artikelRepository.findArtikelByArtikelnummer(artikelnummer);
  }

  public List<Artikel> findArtikelenByNaam(String naam) {
    return artikelRepository.findArtikelenByNaam(naam);
  }

  public List<Artikel> findArtikelenByLeverancier(String leverancier) {
    return artikelRepository.findArtikelenByLeverancier(leverancier);
  }
}
