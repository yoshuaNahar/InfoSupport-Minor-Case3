package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.BestellingView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellingViewRepository extends CrudRepository<BestellingView, Long> {
}
