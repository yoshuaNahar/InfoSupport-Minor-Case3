package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestellingSnapshotRepository extends CrudRepository<BestellingSnapshot, Long> {
  @Query(value = "select * from bestelling_snapshot where gebruiker_id = :gebruikerId", nativeQuery = true)
  List<BestellingSnapshot> findBestellingenByGebruiker(@Param("gebruikerId") int gebruikerId);

  BestellingSnapshot findById(long id);
}
