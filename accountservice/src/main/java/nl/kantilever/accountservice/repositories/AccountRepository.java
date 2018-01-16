package nl.kantilever.accountservice.repositories;

import nl.kantilever.accountservice.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  Account findByUsername(String username);

  Account findById(long id);

}
