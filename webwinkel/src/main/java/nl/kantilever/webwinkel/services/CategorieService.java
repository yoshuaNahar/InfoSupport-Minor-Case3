package nl.kantilever.webwinkel.services;

import nl.kantilever.webwinkel.domain.Categorie;
import nl.kantilever.webwinkel.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategorieService {
  private CategorieRepository categorieRepository;

  @Transactional
  public void save(Categorie categorie) {
    this.categorieRepository.save(categorie);
  }

  @Autowired
  public void setCategorieRepository(CategorieRepository categorieRepository) {
    this.categorieRepository = categorieRepository;
  }

  public List<Categorie> findAll() {
    return (List<Categorie>)categorieRepository.findAll();
  }

  public Categorie findCategorieByNaam(String naam) {
    return categorieRepository.findCategorieByNaam(naam);
  }
}
