package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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



  public List<Artikel> findArtikelenByBeschrijving(String beschrijving) {
    return artikelRepository.findArtikelenByBeschrijving(beschrijving);
  }

  public List<Artikel> findArtikelenInPriceRange(double minPrice, double maxPrice) {
    return artikelRepository.findArtikelenInPriceRange(minPrice, maxPrice);
  }

  public List<Artikel> findArtikelenLeverbaarVanaf(Date leverbaar_vanaf) {
    return artikelRepository.findArtikelenLeverbaarVanaf(leverbaar_vanaf);
  }

  public List<Artikel> findArtikelenLeverbaarTot(Date leverbaar_tot) {
    return artikelRepository.findArtikelenLeverbaarTot(leverbaar_tot);
  }
}
