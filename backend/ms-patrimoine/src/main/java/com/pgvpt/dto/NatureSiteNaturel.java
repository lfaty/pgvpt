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
 * Gets or Sets NatureSiteNaturel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum NatureSiteNaturel {
  
  PARC_NATIONAL("PARC_NATIONAL"),
  
  RESERVE_NATURELLE("RESERVE_NATURELLE"),
  
  RESERVE_COMMUNAUTAIRE("RESERVE_COMMUNAUTAIRE"),
  
  AIRE_MARINE_PROTEGEE("AIRE_MARINE_PROTEGEE"),
  
  FORET("FORET"),
  
  MANGROVE("MANGROVE"),
  
  LAC("LAC"),
  
  LAGON("LAGON"),
  
  FLEUVE("FLEUVE"),
  
  CASCADE("CASCADE"),
  
  PLAGE("PLAGE"),
  
  FALAISE("FALAISE"),
  
  DESERT("DESERT"),
  
  SAVANE("SAVANE"),
  
  ZONE_HUMIDE("ZONE_HUMIDE"),
  
  DELTA("DELTA"),
  
  AUTRE("AUTRE");

  private String value;

  NatureSiteNaturel(String value) {
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
  public static NatureSiteNaturel fromValue(String value) {
    for (NatureSiteNaturel b : NatureSiteNaturel.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

