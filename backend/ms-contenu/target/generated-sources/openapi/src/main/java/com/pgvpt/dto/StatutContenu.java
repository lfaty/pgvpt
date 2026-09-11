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
 * Gets or Sets StatutContenu
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-11T17:00:04.820143400Z[Atlantic/Reykjavik]", comments = "Generator version: 7.25.0")
public enum StatutContenu {
  
  BROUILLON("BROUILLON"),
  
  EN_REVISION("EN_REVISION"),
  
  VALIDE("VALIDE"),
  
  PUBLIE("PUBLIE"),
  
  ARCHIVE("ARCHIVE");

  private final String value;

  StatutContenu(String value) {
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
  public static StatutContenu fromValue(String value) {
    for (StatutContenu b : StatutContenu.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

