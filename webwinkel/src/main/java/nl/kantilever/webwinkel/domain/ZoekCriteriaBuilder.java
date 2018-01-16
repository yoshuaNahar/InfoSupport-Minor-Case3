package nl.kantilever.webwinkel.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Maakt een WHERE clause in voorbereiding voor een query in de vorm van een Predicate
 */
public class ZoekCriteriaBuilder {

  private List<ZoekCriterium> zoekCriteria;

  private List<Categorie> categorieen;

  private int minPrice = 0;
  private int maxPrice = 999999999;

  public ZoekCriteriaBuilder() {
    zoekCriteria = new ArrayList<ZoekCriterium>();
    categorieen = new ArrayList<Categorie>();
  }

  public void voegZoekCriteriumToe(String key, String operation, Object value) {
    zoekCriteria.add(new ZoekCriterium(key, operation, value));
  }

  public void voegCategorieToe(Categorie categorie) {
    categorieen.add(categorie);
  }

  public Specification<Artikel> build() { //Bouwt een specificatie met zoekcriteria en bijbehorend compositietype (AND/OR)
    if (zoekCriteria.size() == 0) {
      return null;
    }

    List<Specification<Artikel>> specs = new ArrayList<Specification<Artikel>>();
    for (ZoekCriterium param : zoekCriteria) {
      specs.add(new ArtikelSpecificatie(param));
    }

    Specification<Artikel> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      if (categorieen.size() > 0) {
        result = Specifications.where(result).or(specs.get(i)).and(isArtikelInCategorieen(categorieen));
      } else {
        result = Specifications.where(result).or(specs.get(i));
      }
    }
    return result;
  }

  public static Specification<Artikel> isArtikelInCategorieen(List<Categorie> categorieen) {
    return new Specification<Artikel>() {
      public Predicate toPredicate(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        final Path<Categorie> group = root.get("categorieen");
        return group.in(categorieen);
      }
    };
  }
//
//  public static Specification<Artikel> isArtikelInPriceRange(int minPrice, int maxPrice) {
//    return new Specification<Artikel>() {
//      public Predicate toPredicate(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//        final Path<Categorie> group = root.get("categorieen");
//        return group.(categorieen);
//      }
//    };
//  }
}
