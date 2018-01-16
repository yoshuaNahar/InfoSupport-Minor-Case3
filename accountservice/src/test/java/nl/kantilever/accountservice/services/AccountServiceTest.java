package nl.kantilever.accountservice.services;

import nl.kantilever.accountservice.entities.Gebruiker;
import nl.kantilever.accountservice.repositories.AccountRepository;
import nl.kantilever.accountservice.repositories.GebruikerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

  private AccountService accountService;

  @MockBean
  private AccountRepository accountRepository;

  @Before
  public void setup() {
    this.accountService = new AccountService();
    this.accountService.setAccountRepository(accountRepository);
  }

  @Test
  public void findByUsernameWithStringGivenShouldCallFindByUsernameOnRepository() {
    String username = "username";

    accountService.findByUsername(username);

    verify(accountRepository, times(1)).findByUsername(username);
  }

  @Test
  public void findByIdWithIdGivenShouldCallFindByIdOnRepository() {
    long id = 12;

    accountService.findById(id);

    verify(accountRepository, times(1)).findById(id);
  }

}
