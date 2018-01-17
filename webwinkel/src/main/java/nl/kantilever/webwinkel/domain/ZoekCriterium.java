package nl.kantilever.webwinkel.domain;

public class ZoekCriterium {
  private String key;
  private String operator;
  private Object waarde;

  public ZoekCriterium(String key, Object waarde) {
    this.key = key;
    this.operator = ":";
    this.waarde = waarde;
  }

  public ZoekCriterium(String key, String operator, Object waarde) {
    this.key = key;
    this.operator = operator;
    this.waarde = waarde;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Object getWaarde() {
    return waarde;
  }

  public void setWaarde(Object waarde) {
    this.waarde = waarde;
  }
}
