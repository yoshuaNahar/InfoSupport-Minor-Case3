package nl.kantilever.bestellingservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BestellingSnapshot is used because we want a snapshot of the "artikelen" with the price
 * of that moment and not the current price.
 */
@Entity
@Table(name = "bestelling_snapshot")
public class BestellingSnapshot {

  @Id
  @Column(name = "bestelling_snapshot_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long gebruikerId;

  @ElementCollection
  private List<Artikel> artikelen;

  private String status;

  private Double total;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "geplaatstOp", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime geplaatstOp;

  public BestellingSnapshot() {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public LocalDateTime getGeplaatstOp() {
    return geplaatstOp;
  }

  @Override
  public String toString() {
    return "BestellingSnapshot{" +
      "id=" + id +
      ", gebruikerId=" + gebruikerId +
      ", artikelen=" + artikelen +
      ", status='" + status + '\'' +
      ", total=" + total +
      ", geplaatstOp=" + geplaatstOp +
      '}';
  }

}
