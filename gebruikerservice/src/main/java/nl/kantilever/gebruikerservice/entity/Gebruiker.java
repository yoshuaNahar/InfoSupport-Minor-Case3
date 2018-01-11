package nl.kantilever.gebruikerservice.entity;

import java.security.Principal;

public class Gebruiker implements Principal {

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String getName() {
    return null;
  }
}
