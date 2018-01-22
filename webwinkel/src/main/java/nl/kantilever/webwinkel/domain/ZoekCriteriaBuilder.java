package nl.kantilever.webwinkel.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Maakt een WHERE clause in voorbereiding voor een query in de vorm van een Predicate
 */
public class ZoekCriteriaBuilder {

  private List<ZoekCriterium> zoekCriteria;

  private List<Categorie> categorieen;

  private int minPrice = 0;
  private int maxPrice = 999999999;

  public ZoekCriteriaBuilder() {
    zoekCriteria = new ArrayList<>();
    categorieen = new ArrayList<>();
  }

  public static Specification<Artikel> isArtikelInCategorieen(List<Categorie> categorieen) {
    return (root, query, builder) -> {
      final Path<Categorie> group = root.get("categorieen");
      return group.in(categorieen);
    };
  }

  public static Specification<Artikel> isArtikelMinimumPrice(int minPrice) {
    return (root, query, builder) -> builder
      .lessThanOrEqualTo(root.<Integer>get("prijs"), minPrice);
  }

  public static Specification<Artikel> isArtikelMaximumPricePrice(int maxPrice) {
    return (root, query, builder) -> builder
      .greaterThanOrEqualTo(root.<Integer>get("prijs"), maxPrice);
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

  public Specification<Artikel> build() {
    if (zoekCriteria.isEmpty()) {
      return null;
    }

    List<Specification<Artikel>> specs = new ArrayList<>();
    for (ZoekCriterium param : zoekCriteria) {
      specs.add(new ArtikelSpecificatie(param));
    }

    Specification<Artikel> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).or(specs.get(i));
    }

    for (Categorie currentCategorie : categorieen) {
      result = Specifications.where(result).and(isArtikelInCategorieen(categorieen));
    }

    if (minPrice != 0) {
      result = Specifications.where(result).and(isArtikelMinimumPrice(minPrice));
    }

    if (maxPrice != 999999999) {
      result = Specifications.where(result).and(isArtikelMaximumPricePrice(maxPrice));
    }

    return result;
  }
}
