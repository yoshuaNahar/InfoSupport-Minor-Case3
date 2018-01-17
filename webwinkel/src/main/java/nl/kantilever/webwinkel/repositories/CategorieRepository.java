package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, String> {

  @Query(value = "select * from categorieen where naam = :naam", nativeQuery = true)
  Categorie findCategorieByNaam(@Param("naam") String naam);

}
