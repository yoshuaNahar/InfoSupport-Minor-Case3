package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellingSnapshotRepository extends CrudRepository<BestellingSnapshot, Long> {
}
