package nl.kantilever.accountservice.repositories;

import nl.kantilever.accountservice.entities.Account;
import nl.kantilever.accountservice.entities.Gebruiker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {

  Gebruiker findById(long gebruikerId);

}
