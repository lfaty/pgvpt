package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets StatutPatrimoine
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum StatutPatrimoine {
  
  BROUILLON("BROUILLON"),
  
  EN_VALIDATION("EN_VALIDATION"),
  
  VALIDE("VALIDE"),
  
  PUBLIE("PUBLIE"),
  
  ARCHIVE("ARCHIVE");

  private String value;

  StatutPatrimoine(String value) {
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
  public static StatutPatrimoine fromValue(String value) {
    for (StatutPatrimoine b : StatutPatrimoine.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

