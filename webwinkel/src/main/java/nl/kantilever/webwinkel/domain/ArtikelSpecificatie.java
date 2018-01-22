package nl.kantilever.webwinkel.domain;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Deze klasse voorziet de builder van query specificaties.
 */
public class ArtikelSpecificatie implements Specification<Artikel> {

  private ZoekCriterium zoekCriterium;

  public ArtikelSpecificatie(ZoekCriterium zoekCriterium) {
    this.zoekCriterium = zoekCriterium;
  }

  @Override
  public Predicate toPredicate(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (">".equalsIgnoreCase(zoekCriterium.getOperator())) {
      return builder.greaterThanOrEqualTo(root.<String>get(zoekCriterium.getKey()), zoekCriterium.getWaarde().toString());
    } else if ("<".equalsIgnoreCase(zoekCriterium.getOperator())) {
      return builder.lessThanOrEqualTo(root.<String>get(zoekCriterium.getKey()), zoekCriterium.getWaarde().toString());
    } else if (":".equalsIgnoreCase(zoekCriterium.getOperator())) {
      if (root.get(zoekCriterium.getKey()).getJavaType() == String.class) {
        return builder.like(root.<String>get(zoekCriterium.getKey()), "%" + zoekCriterium.getWaarde() + "%");
      } else {
        return builder.equal(root.get(zoekCriterium.getKey()), zoekCriterium.getWaarde().toString());
      }
    }
    return null;
  }
}
