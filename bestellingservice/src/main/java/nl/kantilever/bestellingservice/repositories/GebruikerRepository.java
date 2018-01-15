package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.Gebruiker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {
  @Query("SELECT p FROM Gebruiker p WHERE (p.gebruikerId) = (:gebruikerId)")
  public Gebruiker find(@Param("gebruikerId") long gebruikerId);

  @Query(value = "SELECT * FROM gebruiker AS g WHERE gebruiker_id IN(SELECT gebruiker_id FROM bestelling_snapshot AS p  WHERE p.status = 'geplaatst' GROUP BY gebruiker_id HAVING SUM(total)> g.max_crediet)", nativeQuery = true)
  public List<Gebruiker> findGebruikersBoven500();
}

