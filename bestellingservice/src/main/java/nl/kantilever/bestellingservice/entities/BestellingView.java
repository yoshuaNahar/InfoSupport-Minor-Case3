package nl.kantilever.bestellingservice.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bestelling_view")
public class BestellingView {

  @Id
  @Column(name = "bestelling_view_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long gebruikerId;

  @ElementCollection
  private List<Artikel> artikelen;
  private LocalDateTime geplaatstOp;

  public BestellingView() {
    // Needed for JPA
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGebruikerId() {
    return gebruikerId;
  }

  public void setGebruikerId(Long gebruikerId) {
    this.gebruikerId = gebruikerId;
  }

  public List<Artikel> getArtikelen() {
    return artikelen;
  }

  public void setArtikelen(List<Artikel> artikelen) {
    this.artikelen = artikelen;
  }

  public LocalDateTime getGeplaatstOp() {
    return geplaatstOp;
  }

  public void setGeplaatstOp(LocalDateTime geplaatstOp) {
    this.geplaatstOp = geplaatstOp;
  }

  @Override
  public String toString() {
    return "BestellingView{" +
      "id=" + id +
      ", gebruikerId=" + gebruikerId +
      ", artikelen=" + artikelen +
      ", geplaatstOp=" + geplaatstOp +
      '}';
  }

}
