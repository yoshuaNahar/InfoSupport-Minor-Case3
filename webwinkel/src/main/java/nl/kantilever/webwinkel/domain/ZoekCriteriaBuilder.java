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

  public void setMinPrice(String minPrice) {
    this.minPrice = tryParseInt(minPrice);
  }

  public void setMaxPrice(String maxPrice) {
    this.maxPrice = tryParseInt(maxPrice);
  }

  int tryParseInt(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public Specification<Artikel> build() { //Bouwt een specificatie met zoekcriteria en bijbehorend compositietype (AND/OR)
    Specification<Artikel> result = null;

    List<Specification<Artikel>> specs = new ArrayList<Specification<Artikel>>();
    for (ZoekCriterium param : zoekCriteria) {
      specs.add(new ArtikelSpecificatie(param));
    }


    if (!zoekCriteria.isEmpty()) {
      result = specs.get(0);
      for (int i = 1; i < specs.size(); i++) {
        result = Specifications.where(result).or(specs.get(i));
      }

      Specification<Artikel> categorieResults = null;
      for (Categorie currentCategorie : categorieen) {
        result = Specifications.where(result).and(isArtikelInCategorieen(categorieen));
      }
    }

    if (!specs.isEmpty()) {
      result = Specifications.where(result).and(isArtikelMinimumPrice(minPrice)); //Controle van pricerange
      result = Specifications.where(result).and(isArtikelMaximumPricePrice(maxPrice)); //Controle van pricerange
    } else {
      result = Specifications.where(result).or(isArtikelMinimumPrice(minPrice)); //Controle van pricerange
      result = Specifications.where(result).and(isArtikelMaximumPricePrice(maxPrice)); //Controle van pricerange
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

  public static Specification<Artikel> isArtikelMinimumPrice(int minPrice) {
    return new Specification<Artikel>() {
      public Predicate toPredicate(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.greaterThanOrEqualTo(root.<Integer>get("prijs"), minPrice);
      }
    };
  }

  public static Specification<Artikel> isArtikelMaximumPricePrice(int maxPrice) {
    return new Specification<Artikel>() {
      public Predicate toPredicate(Root<Artikel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.lessThanOrEqualTo(root.<Integer>get("prijs"), maxPrice);
      }
    };
  }
}
