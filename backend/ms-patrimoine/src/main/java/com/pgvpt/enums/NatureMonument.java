package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets NatureMonument
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum NatureMonument {
  
  MONUMENT_HISTORIQUE("MONUMENT_HISTORIQUE"),
  
  MONUMENT_COMMEMORATIF("MONUMENT_COMMEMORATIF"),
  
  EDIFICE_RELIGIEUX("EDIFICE_RELIGIEUX"),
  
  EDIFICE_COLONIAL("EDIFICE_COLONIAL"),
  
  EDIFICE_ADMINISTRATIF("EDIFICE_ADMINISTRATIF"),
  
  FORTIFICATION("FORTIFICATION"),
  
  PHARE("PHARE"),
  
  PORTE_HISTORIQUE("PORTE_HISTORIQUE"),
  
  MAUSOLEE("MAUSOLEE"),
  
  STATUE("STATUE"),
  
  AUTRE("AUTRE");

  private String value;

  NatureMonument(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static NatureMonument fromValue(String value) {
    for (NatureMonument b : NatureMonument.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

