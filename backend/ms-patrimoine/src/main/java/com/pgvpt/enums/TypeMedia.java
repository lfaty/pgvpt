package com.pgvpt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;

/**
 * Gets or Sets TypeMedia
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public enum TypeMedia {
  
  PHOTO("PHOTO"),
  
  VIDEO("VIDEO"),
  
  AUDIO("AUDIO"),
  
  DOCUMENT("DOCUMENT"),
  
  VISITE_VIRTUELLE("VISITE_VIRTUELLE"),
  
  MODELE_3_D("MODELE_3D");

  private String value;

  TypeMedia(String value) {
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
  public static TypeMedia fromValue(String value) {
    for (TypeMedia b : TypeMedia.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

