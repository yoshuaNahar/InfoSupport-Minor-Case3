package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.Bestelling;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellingRepository extends CrudRepository<Bestelling, Long> {
}
