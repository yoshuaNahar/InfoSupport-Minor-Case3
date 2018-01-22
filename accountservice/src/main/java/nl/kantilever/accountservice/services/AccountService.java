package nl.kantilever.accountservice.services;

import nl.kantilever.accountservice.domain.Account;
import nl.kantilever.accountservice.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private AccountRepository accountRepository;

  @Autowired
  public void setAccountRepository(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account findByUsername(String username) {
    return this.accountRepository.findByUsername(username);
  }

  public Account findById(long id) {
    return this.accountRepository.findById(id);
  }

}
