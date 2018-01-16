package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class ArtikelService {

  private ArtikelRepository artikelRepository;
  private CategorieService categorieService;

  @Autowired
  public void setArtikelRepository(ArtikelRepository artikelRepository, CategorieService categorieService) {
    this.artikelRepository = artikelRepository;
    this.categorieService = categorieService;
  }

  public ArtikelRepository getArtikelRepository() {
    return artikelRepository;
  }

  @Autowired
  private EntityManager entityManager;

  @Transactional
  public void save(Artikel artikel) {
    List<Categorie> categorieen = categorieService.findAll();
    List<Categorie> artikelCategorieen = artikel.getCategorieen();
    for (Categorie categorie: artikelCategorieen) {
      for (Categorie allCategorieen : categorieen) {
        if(categorie.getNaam().equals(allCategorieen.getNaam())){
          categorie.setId(allCategorieen.getId());
        }
      }
    }
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

  public List<Artikel> findArtikelenByCategorieID(int categorieID) {
    return artikelRepository.findArtikelenByCategorieID(categorieID);
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

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}
