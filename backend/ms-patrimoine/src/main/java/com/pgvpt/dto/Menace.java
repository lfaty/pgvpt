package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Menace
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Menace {

  private String type;

  /**
   * Gets or Sets niveau
   */
  public enum NiveauEnum {
    FAIBLE("FAIBLE"),
    
    MOYEN("MOYEN"),
    
    ELEVE("ELEVE"),
    
    CRITIQUE("CRITIQUE");

    private String value;

    NiveauEnum(String value) {
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
    public static NiveauEnum fromValue(String value) {
      for (NiveauEnum b : NiveauEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private NiveauEnum niveau;

  private String description;

  private String mesuresPrevention;

  public Menace type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", example = "Erosion côtière", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Menace niveau(NiveauEnum niveau) {
    this.niveau = niveau;
    return this;
  }

  /**
   * Get niveau
   * @return niveau
   */
  
  @Schema(name = "niveau", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("niveau")
  public NiveauEnum getNiveau() {
    return niveau;
  }

  public void setNiveau(NiveauEnum niveau) {
    this.niveau = niveau;
  }

  public Menace description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Menace mesuresPrevention(String mesuresPrevention) {
    this.mesuresPrevention = mesuresPrevention;
    return this;
  }

  /**
   * Get mesuresPrevention
   * @return mesuresPrevention
   */
  
  @Schema(name = "mesuresPrevention", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mesuresPrevention")
  public String getMesuresPrevention() {
    return mesuresPrevention;
  }

  public void setMesuresPrevention(String mesuresPrevention) {
    this.mesuresPrevention = mesuresPrevention;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Menace menace = (Menace) o;
    return Objects.equals(this.type, menace.type) &&
        Objects.equals(this.niveau, menace.niveau) &&
        Objects.equals(this.description, menace.description) &&
        Objects.equals(this.mesuresPrevention, menace.mesuresPrevention);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, niveau, description, mesuresPrevention);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Menace {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    niveau: ").append(toIndentedString(niveau)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    mesuresPrevention: ").append(toIndentedString(mesuresPrevention)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

