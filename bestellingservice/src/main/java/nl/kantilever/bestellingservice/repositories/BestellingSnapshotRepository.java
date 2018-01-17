package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.BestellingSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestellingSnapshotRepository extends PagingAndSortingRepository<BestellingSnapshot, Long> {
  @Query(value = "select * from bestelling_snapshot where gebruiker_id = :gebruikerId", nativeQuery = true)
  List<BestellingSnapshot> findBestellingenByGebruiker(@Param("gebruikerId") int gebruikerId);

  BestellingSnapshot findFirstById(long id);

  Page<BestellingSnapshot> findAllByStatus(String status, Pageable pageable);

  @Modifying
  @Query("UPDATE BestellingSnapshot bestellingSnapshot SET bestellingSnapshot.status = :status WHERE bestellingSnapshot.id = :bestellingId")
  void setStatus(@Param("bestellingId") Long bestellingId, @Param("status") String status);
}
