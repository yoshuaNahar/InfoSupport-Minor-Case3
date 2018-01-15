package nl.kantilever.accountservice.services;

import nl.kantilever.accountservice.entities.Account;
import nl.kantilever.accountservice.entities.Gebruiker;
import nl.kantilever.accountservice.repositories.AccountRepository;
import nl.kantilever.accountservice.repositories.GebruikerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GebruikerService {

  private GebruikerRepository gebruikerRepository;

  @Autowired
  public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
    this.gebruikerRepository = gebruikerRepository;
  }

  @Transactional
  public void save(Gebruiker gebruiker) {
    this.gebruikerRepository.save(gebruiker);
  }

}
