package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.domain.Artikel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtikelenRepository extends CrudRepository<Artikel, Long> {
}
