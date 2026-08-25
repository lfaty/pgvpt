package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

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

