package nl.kantilever.webwinkel.domain;

/**
 * Created by Tinne on 9-1-2018.
 */
public class ZoekCriterium {
  private String key;
  private String operator;
  private Object waarde;
  private boolean isAnd = false;

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

  public ZoekCriterium(String key, String operator, Object waarde, boolean isAnd) {
    this.key = key;
    this.operator = operator;
    this.waarde = waarde;
    this.isAnd = isAnd;
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

  public boolean isAnd() {
    return isAnd;
  }

  public void setAnd(boolean and) {
    isAnd = and;
  }
}
