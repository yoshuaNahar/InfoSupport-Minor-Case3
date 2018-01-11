package nl.kantilever.webwinkel.domain;

/**
 * Created by Tinne on 9-1-2018.
 */
public class ZoekCriteria {
  private String criterium;
  private String waarde;

  public ZoekCriteria(String criterium, String waarde) {
    this.criterium = criterium;
    this.waarde = waarde;
  }

  public String getCriterium() {
    return criterium;
  }

  public void setCriterium(String criterium) {
    this.criterium = criterium;
  }

  public String getWaarde() {
    return waarde;
  }

  public void setWaarde(String waarde) {
    this.waarde = waarde;
  }
}
