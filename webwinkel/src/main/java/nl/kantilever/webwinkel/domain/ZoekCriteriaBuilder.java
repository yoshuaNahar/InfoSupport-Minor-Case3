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

  public void voegZoekCriteriumToe(String key, String operation, Object value, boolean isAnd) {
    zoekCriteria.add(new ZoekCriterium(key, operation, value, isAnd));
  }

  public Specification<Artikel> build() { //Bouwt een specificatie met zoekcriteria en bijbehorend compositietype (AND/OR)
    if (zoekCriteria.size() == 0) {
      return null;
    }

    List<Specification<Artikel>> specs = new ArrayList<Specification<Artikel>>();
    for (ZoekCriterium param : zoekCriteria) {
      specs.add(new ArtikelSpecificatie(param));
    }

    Specification<Artikel> result = null;
    for (int i = 0; i < specs.size(); i++) {
      result = Specifications.where(result).or(specs.get(i));
    }


    return result;
  }
}
