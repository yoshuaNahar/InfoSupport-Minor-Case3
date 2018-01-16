package nl.kantilever.webwinkel.repositories;

import nl.kantilever.webwinkel.domain.Artikel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;

import java.util.List;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, String>, JpaSpecificationExecutor<Artikel> {
  @Query(value = "select * from artikelen where artikelnummer = :artikelnummer", nativeQuery = true)
  Artikel findArtikelByArtikelnummer(@Param("artikelnummer") int artikelnummer);

  @Query(value = "select * from artikelen where naam like %:naam%", nativeQuery = true)
  List<Artikel> findArtikelenByNaam(@Param("naam") String naam);

  @Query(value = "select * from artikelen where leverancier like %:leverancier%", nativeQuery = true)
  List<Artikel> findArtikelenByLeverancier(@Param("leverancier") String leverancier);

  @Query(value = "select * FROM artikelen JOIN artikelen_categorieen ON artikelen.artikelnummer where artikelen_categorieen.artikelen_artikelnummer = :categorieID", nativeQuery = true)
  List<Artikel> findArtikelenByCategorieID(@Param("categorieID") int categorieID);

  @Query(value = "select * from artikelen where beschrijving like %:beschrijving%", nativeQuery = true)
  List<Artikel> findArtikelenByBeschrijving(@Param("beschrijving") String beschrijving);

  @Query(value = "select * from artikelen where prijs between :minPrice and :maxPrice", nativeQuery = true)
  List<Artikel> findArtikelenInPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

  @Query(value = "select * from artikelen where leverbaar_vanaf >= :leverbaar_vanaf", nativeQuery = true)
  List<Artikel> findArtikelenLeverbaarVanaf(@Param("leverbaar_vanaf") Date leverbaar_vanaf);

  @Query(value = "select * from artikelen where leverbaar_tot <= :leverbaar_tot", nativeQuery = true)
  List<Artikel> findArtikelenLeverbaarTot(@Param("leverbaar_tot") Date leverbaar_tot);
}
