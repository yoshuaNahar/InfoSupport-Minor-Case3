package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Artikel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, String> {
}
