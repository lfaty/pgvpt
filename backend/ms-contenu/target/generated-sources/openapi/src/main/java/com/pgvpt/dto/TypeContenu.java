package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets TypeContenu
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-11T17:00:04.820143400Z[Atlantic/Reykjavik]", comments = "Generator version: 7.25.0")
public enum TypeContenu {
  
  ARTICLE("ARTICLE"),
  
  DESCRIPTION("DESCRIPTION"),
  
  HISTORIQUE("HISTORIQUE"),
  
  RECIT("RECIT"),
  
  TEMOIGNAGE("TEMOIGNAGE"),
  
  GUIDE_VISITE("GUIDE_VISITE"),
  
  ACTUALITE("ACTUALITE");

  private final String value;

  TypeContenu(String value) {
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
  public static TypeContenu fromValue(String value) {
    for (TypeContenu b : TypeContenu.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

