package nl.kantilever.bestellingservice;

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
@Table(name = "bestelling")
public class Bestelling {

  @Id
  @Column(name = "bestelling_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long gebruikerId;

  @ElementCollection
  private List<Long> artikelenIds;
  private LocalDateTime geplaatstOp;

  public Bestelling() {
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

  public List<Long> getArtikelenIds() {
    return artikelenIds;
  }

  public void setArtikelenIds(List<Long> artikelenIds) {
    this.artikelenIds = artikelenIds;
  }

  public LocalDateTime getGeplaatstOp() {
    return geplaatstOp;
  }

  public void setGeplaatstOp(LocalDateTime geplaatstOp) {
    this.geplaatstOp = geplaatstOp;
  }

  @Override
  public String toString() {
    return "Bestelling{" +
      "id=" + id +
      ", gebruikerId=" + gebruikerId +
      ", artikelenIds=" + artikelenIds +
      ", geplaatstOp=" + geplaatstOp +
      '}';
  }

}
