package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets TypePatrimoine
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum TypePatrimoine {
  
  SITE_NATUREL("SITE_NATUREL"),
  
  MUSEE("MUSEE"),
  
  MONUMENT("MONUMENT");

  private String value;

  TypePatrimoine(String value) {
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
  public static TypePatrimoine fromValue(String value) {
    for (TypePatrimoine b : TypePatrimoine.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

