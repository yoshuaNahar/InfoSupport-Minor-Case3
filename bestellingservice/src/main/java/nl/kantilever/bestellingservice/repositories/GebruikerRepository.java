package nl.kantilever.bestellingservice.repositories;

import nl.kantilever.bestellingservice.entities.Gebruiker;
import org.springframework.data.repository.CrudRepository;

public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {

}
