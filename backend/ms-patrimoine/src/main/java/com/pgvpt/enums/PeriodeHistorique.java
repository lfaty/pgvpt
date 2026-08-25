package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets PeriodeHistorique
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum PeriodeHistorique {
  
  PREHISTORIQUE("PREHISTORIQUE"),
  
  ANTIQUITE("ANTIQUITE"),
  
  MEDIEVALE("MEDIEVALE"),
  
  PRECOLONIALE("PRECOLONIALE"),
  
  COLONIALE("COLONIALE"),
  
  POSTCOLONIALE("POSTCOLONIALE"),
  
  CONTEMPORAINE("CONTEMPORAINE"),
  
  INDETERMINEE("INDETERMINEE");

  private String value;

  PeriodeHistorique(String value) {
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
  public static PeriodeHistorique fromValue(String value) {
    for (PeriodeHistorique b : PeriodeHistorique.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

