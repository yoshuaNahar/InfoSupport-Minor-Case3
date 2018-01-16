package nl.kantilever.webwinkel.domain;

import nl.kantilever.webwinkel.services.ArtikelService;
import nl.kantilever.webwinkel.services.CategorieService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tinne on 9-1-2018.
 * Maakt een WHERE clause in voorbereiding voor een query in de vorm van een Predicate
 */
public class NewZoekCriteriaBuilder {
  private List<ZoekCriterium> artikelSpecifications;
  private List<ZoekCriterium> categorieSpecifications;
  private List<ZoekCriterium> priceSpecifications;

  ArtikelService artikelService;
  CategorieService categorieService;

  List<Predicate> predicates;

  CriteriaBuilder qb;
  CriteriaQuery cq;
  Root<Artikel> artikel;

  public NewZoekCriteriaBuilder(ArtikelService artikelService, CategorieService categorieService) {
    this.artikelService = artikelService;
    this.categorieService = categorieService;

    artikelSpecifications = new ArrayList<>();
    categorieSpecifications = new ArrayList<>();
    priceSpecifications = new ArrayList<>();
    predicates = new ArrayList<>();

    qb = artikelService.getEntityManager().getCriteriaBuilder();
    cq = qb.createQuery();
    artikel = cq.from(Artikel.class);
  }

  public void addArtikelSpecification(ZoekCriterium specification) {
    artikelSpecifications.add(specification);

    predicates.add(qb.equal(artikel.get(specification.getKey()), specification.getWaarde()));
  }

  public void addCategorieSpecification(ZoekCriterium specification) {
    categorieSpecifications.add(specification);

    predicates.add(qb.equal(artikel.get(specification.getKey()), specification.getWaarde()));
  }

  public void addPriceRange(ZoekCriterium specification) {
    priceSpecifications.add(specification);

    predicates.add(qb.equal(artikel.get(specification.getKey()), specification.getWaarde()));
  }

  public List<Artikel> build() { //Bouwt een specificatie met zoekcriteria en bijbehorend compositietype (AND/OR)
    cq.select(artikel).where(predicates.toArray(new Predicate[predicates.size()]));

    List<Artikel> artikelList = artikelService.getEntityManager().createQuery(cq).getResultList();

    for (Artikel currentArtikel: artikelList) {
      for(Categorie currentCategorie: currentArtikel.getCategorieen()) {
        currentCategorie.setArtikelen(new ArrayList<>());
      }
    }

    return artikelList;
  }
}
