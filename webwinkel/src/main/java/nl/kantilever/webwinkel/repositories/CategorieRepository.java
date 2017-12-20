package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.domain.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Tinne on 20-12-2017.
 */

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, String> {
  @Query(value = "select * from categorieen", nativeQuery = true)
  Artikel findAllCategorieen();

}
