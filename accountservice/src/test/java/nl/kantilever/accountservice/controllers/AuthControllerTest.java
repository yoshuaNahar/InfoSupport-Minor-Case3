package nl.kantilever.accountservice.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import nl.kantilever.accountservice.entities.Account;
import nl.kantilever.accountservice.services.AccountService;
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
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

  @MockBean
  private AccountService accountService;

  @MockBean
  private BCryptPasswordEncoder bCrypt;

  @MockBean
  private JwtParser jwtParser;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void authenticateWithAccountInDBAndCorrectPasswordShouldReturnOkReponseEntity()
    throws Exception {
    Account account = new Account();
    account.setUsername("test@mail.com");
    account.setPassword("somePass");

    doReturn(account).when(accountService).findByUsername(any(String.class));
    doReturn(true).when(bCrypt).matches(eq(account.getPassword()), any(String.class));

    mockMvc.perform(post("/authenticate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(account))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    verify(accountService, times(1)).findByUsername(account.getUsername());
    verify(bCrypt, times(1)).matches(eq(account.getPassword()), any(String.class));
  }

  @Test
  public void authenticateWithNonExstingAccountShouldReturnNotFoundReponseEntity()
    throws Exception {
    Account account = new Account();
    account.setUsername("test@mail.com");

    doReturn(null).when(accountService).findByUsername(any(String.class));

    mockMvc.perform(post("/authenticate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(account))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isNotFound());

    verify(accountService, times(1)).findByUsername(account.getUsername());
  }

  @Test
  public void authenticateWithAccountInDBAndIncorrectPasswordShouldReturnUnauthorizedReponseEntity()
    throws Exception {
    Account account = new Account();
    account.setUsername("test@mail.com");

    doReturn(account).when(accountService).findByUsername(any(String.class));
    doReturn(false).when(bCrypt).matches(eq(account.getPassword()), any(String.class));

    mockMvc.perform(post("/authenticate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(account))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isUnauthorized());

    verify(accountService, times(1)).findByUsername(account.getUsername());
    verify(bCrypt, times(1)).matches(eq(account.getPassword()), any(String.class));
  }

  @Test
  public void authenticateWithAccountThrowsExceptionShouldReturnBadRequestResponseEntity()
    throws Exception {
    Account account = new Account();
    account.setUsername("test@mail.com");

    doThrow(Exception.class).when(accountService).findByUsername(any(String.class));

    mockMvc.perform(post("/authenticate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(account))
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest());

    verify(accountService, times(1)).findByUsername(account.getUsername());
  }

  @Test
  public void refreshWithRefreshTokenAndAccountInDBShouldReturnOkResponseEntity() throws Exception {
    String refreshToken = "a.b.c";

    Jws jws = mock(Jws.class);

    doReturn(jwtParser).when(jwtParser).setSigningKey(any(String.class));
    doReturn(jws).when(jwtParser).parseClaimsJws(refreshToken);

    DefaultClaims claims = new DefaultClaims();
    claims.setSubject("1");

    doReturn(claims).when(jws).getBody();

    Account account = new Account();
    account.setRole("USER");
    doReturn(account).when(accountService).findById(1);

    mockMvc.perform(post("/refresh")
      .contentType(MediaType.APPLICATION_JSON)
      .header("Refresh-Token", refreshToken)
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    verify(accountService, times(1)).findById(1);
  }

  @Test
  public void refreshWithRefreshTokenAndNonExistingAccountShouldReturnUnauthorizedResponseEntity()
    throws Exception {
    String refreshToken = "a.b.c";

    Jws jws = mock(Jws.class);

    doReturn(jwtParser).when(jwtParser).setSigningKey(any(String.class));
    doReturn(jws).when(jwtParser).parseClaimsJws(refreshToken);

    DefaultClaims claims = new DefaultClaims();
    claims.setSubject("1");

    doReturn(claims).when(jws).getBody();

    mockMvc.perform(post("/refresh")
      .contentType(MediaType.APPLICATION_JSON)
      .header("Refresh-Token", refreshToken)
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isUnauthorized());

    verify(accountService, times(1)).findById(1);
  }

  @Test
  public void refreshWithThrownSignatureExceptionShouldReturnUnauthorizedResponseEntity()
    throws Exception {
    doThrow(SignatureException.class).when(jwtParser).setSigningKey(any(String.class));

    mockMvc.perform(post("/refresh")
      .contentType(MediaType.APPLICATION_JSON)
      .header("Refresh-Token", "")
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isUnauthorized());
  }

  @Test
  public void refreshWithThrownExceptionShouldReturnInternalServerErrorResponseEntity()
    throws Exception {
    doThrow(Exception.class).when(jwtParser).setSigningKey(any(String.class));

    mockMvc.perform(post("/refresh")
      .contentType(MediaType.APPLICATION_JSON)
      .header("Refresh-Token", "")
      .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isInternalServerError());
  }

}

