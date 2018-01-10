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
  public GebruikerService(
    GebruikerRepository gebruikerRepository) {
    this.gebruikerRepository = gebruikerRepository;
  }

  // TODO: You were here. Set de gebruiker in de bestellingService.
  // What Esther sees...
  public List<Gebruiker> getGebruikersMetBestellingenBoven500() {
    List<Gebruiker> gebruikers = (List<Gebruiker>) gebruikerRepository.findAll();
    for (Gebruiker gebruiker : gebruikers) {
    }
  }

}
