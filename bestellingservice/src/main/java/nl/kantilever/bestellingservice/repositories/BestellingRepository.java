package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.domain.Bestelling;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellingRepository extends CrudRepository<Bestelling, Long> {
}
