package nl.kantilever.webwinkel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tinne on 20-12-2017.
 */

@Entity
@Table(name = "categorien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
  @Id
  @Column(name = "naam", unique = true)
  String naam;
}
