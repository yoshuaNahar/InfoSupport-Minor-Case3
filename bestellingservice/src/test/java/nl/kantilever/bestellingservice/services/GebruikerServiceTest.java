package nl.kantilever.bestellingservice.services;

import nl.kantilever.bestellingservice.config.AppConfig;
import nl.kantilever.bestellingservice.entities.Gebruiker;
import nl.kantilever.bestellingservice.repositories.GebruikerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@Import(AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class GebruikerServiceTest {

  GebruikerService gebruikerService;

  @MockBean
  GebruikerRepository gebruikerRepository;

  @Before
  public void setup(){
    this.gebruikerService = new GebruikerService(gebruikerRepository);
  }

  @Test
  public void createGebruikerShouldCallSave() throws Exception {
    gebruikerService.createGebruiker(Mockito.any(Gebruiker.class));
    verify(gebruikerRepository, times(1)).save(Mockito.any(Gebruiker.class));
  }

  @Test
  public void getGebruikersBoven500ShouldCallFindGebruikersBoven500() throws Exception {
    gebruikerService.getGebruikersBoven500();
    verify(gebruikerRepository, times(1)).findGebruikersBoven500();
  }

  @Test
  public void getGebruikerByIdShouldCallFindOne() throws Exception {
    gebruikerService.getGebruikerById(Mockito.anyLong());
    verify(gebruikerRepository, times(1)).findOne(Mockito.anyLong());
  }

  @Test
  public void updateGebruikerShouldCallSave() throws Exception {
    gebruikerService.updateGebruiker(Mockito.any(Gebruiker.class));
    verify(gebruikerRepository, times(1)).save(Mockito.any(Gebruiker.class));
  }

  @Test
  public void saveShouldCallSave() throws Exception {
    gebruikerService.save(Mockito.any(Gebruiker.class));
    verify(gebruikerRepository, times(1)).save(Mockito.any(Gebruiker.class));
  }

}
