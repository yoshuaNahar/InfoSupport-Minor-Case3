package nl.kantilever.accountservice.services;

import nl.kantilever.accountservice.entities.Gebruiker;
import nl.kantilever.accountservice.repositories.GebruikerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class GebruikerServiceTest {

  private GebruikerService gebruikerService;

  @MockBean
  private GebruikerRepository gebruikerRepository;

  @Before
  public void setup() {
    this.gebruikerService = new GebruikerService();
    this.gebruikerService.setGebruikerRepository(gebruikerRepository);
  }

  @Test
  public void saveWithNewGebruikerShouldCallSave() {
   Gebruiker gebruiker = new Gebruiker();

    gebruikerService.save(gebruiker);

    verify(gebruikerRepository, times(1)).save(gebruiker);
  }

}
