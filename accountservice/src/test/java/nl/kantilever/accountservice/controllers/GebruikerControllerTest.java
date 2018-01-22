package nl.kantilever.accountservice.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.accountservice.domain.Account;
import nl.kantilever.accountservice.domain.Gebruiker;
import nl.kantilever.accountservice.services.AccountService;
import nl.kantilever.accountservice.services.GebruikerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(GebruikerController.class)
public class GebruikerControllerTest {

  @MockBean
  private GebruikerService gebruikerService;

  @MockBean
  private AccountService accountService;

  @MockBean
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  private Gebruiker gebruiker;

  @Before
  public void setup() {
    Account account = new Account();
    account.setUsername("test@mail.com");
    account.setPassword("somePass");

    Gebruiker gebruiker = new Gebruiker();
    gebruiker.setAanhef("De heer");
    gebruiker.setVoornaam("Karel");
    gebruiker.setTussenvoegsel("de");
    gebruiker.setAchternaam("Piet");
    gebruiker.setStraatnaam("Wallstreet");
    gebruiker.setHuisnummer(11);
    gebruiker.setStad("New York");
    gebruiker.setLand("United States");
    gebruiker.setPostcode("1234AB");
    gebruiker.setTelefoonnummer("0612345678");
    gebruiker.setAccount(account);

    this.gebruiker = gebruiker;
  }

  @Test
  public void createGebruikerAndAccountWithNewGebruikerShouldReturnOkReponseEntity()
    throws Exception {
    mockMvc.perform(post("/gebruiker")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(this.gebruiker))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    verify(accountService, times(1)).findByUsername(this.gebruiker.getAccount().getUsername());
    verify(gebruikerService, times(1)).save(any(Gebruiker.class));
  }

  @Test
  public void createGebruikerAndAccountWithExistingAccountShouldReturnBadRequestReponseEntityWithUsernameTakenMessage()
    throws Exception {
    doReturn(this.gebruiker.getAccount()).when(accountService).findByUsername(any(String.class));

    mockMvc.perform(post("/gebruiker")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(this.gebruiker))
    ).andExpect(status().isBadRequest()).andExpect(content().string("UsernameTaken"));
  }

  @Test
  public void createGebruikerAndAccountWithGebruikerWithNoAccountShouldReturnBadRequestReponseEntity()
    throws Exception {
    mockMvc.perform(post("/gebruiker")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(new Gebruiker()))
    ).andExpect(status().isBadRequest());
  }

}

