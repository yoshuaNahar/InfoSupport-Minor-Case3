package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Artikel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, String> {
  @Query(value = "select * from artikelen where artikelnummer = :artikelnummer group by (artikelnummer)", nativeQuery = true)
  Artikel findArtikelenByArtikelnummer(@Param("artikelnummer") int artikelnummer);

  @Query(value = "select * from artikelen where naam = :naam group by (artikelnummer)", nativeQuery = true)
  List<Artikel> findArtikelenByNaam(@Param("naam") String naam);

  @Query(value = "select * from artikelen where leverancier = :leverancier group by (artikelnummer)", nativeQuery = true)
  List<Artikel> findArtikelenByLeverancier(@Param("leverancier") String leverancier);

  @Query(value = "select * from artikelen where categorie = :categorie group by (artikelnummer)", nativeQuery = true)
  List<Artikel> findArtikelenByCategorie(@Param("categorie") String categorie);
}
