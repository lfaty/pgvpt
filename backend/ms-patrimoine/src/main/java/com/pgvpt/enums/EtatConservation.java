package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets EtatConservation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum EtatConservation {
  
  EXCELLENT("EXCELLENT"),
  
  BON("BON"),
  
  MOYEN("MOYEN"),
  
  MAUVAIS("MAUVAIS"),
  
  CRITIQUE("CRITIQUE"),
  
  RUINE("RUINE");

  private String value;

  EtatConservation(String value) {
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
  public static EtatConservation fromValue(String value) {
    for (EtatConservation b : EtatConservation.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

