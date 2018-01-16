package nl.kantilever.webwinkel.domain;

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
public class ZoekCriteriaBuilder {

  private final List<ZoekCriterium> zoekCriteria;

  public ZoekCriteriaBuilder() {
    zoekCriteria = new ArrayList<ZoekCriterium>();
  }

  public void voegZoekCriteriumToe(String key, String operation, Object value) {
    zoekCriteria.add(new ZoekCriterium(key, operation, value));
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
      result = Specifications.where(result).or(specs.get(i));
    }

//    Specification<Artikel> result = specs.get(0);
//
//    for (int i = 1; i < specs.size(); i++) {
//      ZoekCriterium currentZoekCriterium = ((ArtikelSpecificatie)result).getZoekCriterium();
//
//      if (currentZoekCriterium.getKey().equalsIgnoreCase("leverancier")) {
//        result = Specifications.where(result).or(specs.get(i));
//      } else {
//        result = Specifications.where(result).and(specs.get(i));
//      }
//    }


    return result;
  }


  public Predicate isArtikelInCategorie(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<Categorie> categorieenList) {
    return null;
  }
}
