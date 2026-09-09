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
 * Gets or Sets TypeMedia
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-05T09:55:45.031775700Z[Africa/Dakar]", comments = "Generator version: 7.25.0")
public enum TypeMedia {
  
  PHOTO("PHOTO"),
  
  VIDEO("VIDEO"),
  
  AUDIO("AUDIO"),
  
  DOCUMENT("DOCUMENT"),
  
  PANORAMA_360("PANORAMA_360"),
  
  MODELE_3_D("MODELE_3D"),
  
  CARTE("CARTE"),
  
  ARCHIVE_NUMERISEE("ARCHIVE_NUMERISEE");

  private final String value;

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

