package nl.kantilever.bestellingservice.services;

import java.util.List;
import nl.kantilever.bestellingservice.entities.Gebruiker;
import nl.kantilever.bestellingservice.repositories.GebruikerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GebruikerService {

  private GebruikerRepository gebruikerRepository;

  @Autowired
  public GebruikerService(GebruikerRepository gebruikerRepository) {
    this.gebruikerRepository = gebruikerRepository;
  }

  public void createGebruiker(Gebruiker gebruiker) {
    gebruikerRepository.save(gebruiker);
  }

  public List<Gebruiker> getGebruikersBoven500() {
    return gebruikerRepository.findGebruikersBoven500();
  }

  public Gebruiker getGebruikerById(long id) {
    return gebruikerRepository.findOne(id);
  }

  public void updateGebruiker(Gebruiker gebruiker) {
    gebruikerRepository.save(gebruiker);
  }

  public Gebruiker save(Gebruiker gebruiker) {
    return gebruikerRepository.save(gebruiker);
  }

}
