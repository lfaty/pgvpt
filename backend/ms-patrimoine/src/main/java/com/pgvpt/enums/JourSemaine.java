package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets JourSemaine
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum JourSemaine {
  
  LUNDI("LUNDI"),
  
  MARDI("MARDI"),
  
  MERCREDI("MERCREDI"),
  
  JEUDI("JEUDI"),
  
  VENDREDI("VENDREDI"),
  
  SAMEDI("SAMEDI"),
  
  DIMANCHE("DIMANCHE");

  private String value;

  JourSemaine(String value) {
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
  public static JourSemaine fromValue(String value) {
    for (JourSemaine b : JourSemaine.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

