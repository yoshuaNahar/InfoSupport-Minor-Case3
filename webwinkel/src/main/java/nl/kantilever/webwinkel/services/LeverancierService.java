package nl.kantilever.webwinkel.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import nl.kantilever.webwinkel.domain.Leverancier;
import nl.kantilever.webwinkel.repositories.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeverancierService {

  private ArtikelRepository artikelRepository;

  @Autowired
  public LeverancierService(ArtikelRepository artikelRepository) {
    this.artikelRepository = artikelRepository;
  }

  public List<Leverancier> findAllLeveranciers() {
    HashSet<Leverancier> set = new HashSet<>();

    artikelRepository.findAll()
      .forEach(artikel -> set
        .add(new Leverancier(artikel.getLeverancier(), artikel.getLeverancierCode())));
    return new ArrayList<>(set);
  }

}
