package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets CategoriePatrimoine
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum CategoriePatrimoine {
  
  PATRIMOINE_NATUREL("PATRIMOINE_NATUREL"),
  
  PATRIMOINE_HISTORIQUE("PATRIMOINE_HISTORIQUE"),
  
  PATRIMOINE_CULTUREL("PATRIMOINE_CULTUREL"),
  
  PATRIMOINE_ARCHITECTURAL("PATRIMOINE_ARCHITECTURAL"),
  
  PATRIMOINE_RELIGIEUX("PATRIMOINE_RELIGIEUX"),
  
  PATRIMOINE_COLONIAL("PATRIMOINE_COLONIAL"),
  
  PATRIMOINE_ARCHEOLOGIQUE("PATRIMOINE_ARCHEOLOGIQUE"),
  
  PATRIMOINE_MUSEAL("PATRIMOINE_MUSEAL"),
  
  PATRIMOINE_MEMORIEL("PATRIMOINE_MEMORIEL"),
  
  PATRIMOINE_MARITIME("PATRIMOINE_MARITIME"),
  
  PATRIMOINE_RURAL("PATRIMOINE_RURAL"),
  
  AUTRE("AUTRE");

  private String value;

  CategoriePatrimoine(String value) {
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
  public static CategoriePatrimoine fromValue(String value) {
    for (CategoriePatrimoine b : CategoriePatrimoine.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

