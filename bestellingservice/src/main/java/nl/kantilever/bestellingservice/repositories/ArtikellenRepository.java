package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.Artikel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtikellenRepository extends CrudRepository<Artikel, Long> {
}
