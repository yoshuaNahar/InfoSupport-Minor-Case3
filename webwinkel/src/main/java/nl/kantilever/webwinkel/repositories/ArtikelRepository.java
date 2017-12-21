package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Artikel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, String> {
  @Query(value = "select * from artikelen where artikelnummer = :artikelnummer", nativeQuery = true)
  Artikel findArtikelByArtikelnummer(@Param("artikelnummer") int artikelnummer);

  @Query(value = "select * from artikelen where naam like %:naam%", nativeQuery = true)
  List<Artikel> findArtikelenByNaam(@Param("naam") String naam);

  @Query(value = "select * from artikelen where leverancier like %:leverancier%", nativeQuery = true)
  List<Artikel> findArtikelenByLeverancier(@Param("leverancier") String leverancier);
}
