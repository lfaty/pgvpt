package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URI;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * EspeceProtegee
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class EspeceProtegee {

  private UUID id;

  private String nomCommun;

  private String nomScientifique;

  private String nomLocal;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    FAUNE("FAUNE"),
    
    FLORE("FLORE");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private String statutConservation;

  private String niveauProtection;

  private String description;

  private URI photoUrl;

  public EspeceProtegee() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public EspeceProtegee(String nomCommun) {
    this.nomCommun = nomCommun;
  }

  public EspeceProtegee id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public EspeceProtegee nomCommun(String nomCommun) {
    this.nomCommun = nomCommun;
    return this;
  }

  /**
   * Get nomCommun
   * @return nomCommun
   */
  @NotNull 
  @Schema(name = "nomCommun", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nomCommun")
  public String getNomCommun() {
    return nomCommun;
  }

  public void setNomCommun(String nomCommun) {
    this.nomCommun = nomCommun;
  }

  public EspeceProtegee nomScientifique(String nomScientifique) {
    this.nomScientifique = nomScientifique;
    return this;
  }

  /**
   * Get nomScientifique
   * @return nomScientifique
   */
  
  @Schema(name = "nomScientifique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomScientifique")
  public String getNomScientifique() {
    return nomScientifique;
  }

  public void setNomScientifique(String nomScientifique) {
    this.nomScientifique = nomScientifique;
  }

  public EspeceProtegee nomLocal(String nomLocal) {
    this.nomLocal = nomLocal;
    return this;
  }

  /**
   * Get nomLocal
   * @return nomLocal
   */
  
  @Schema(name = "nomLocal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomLocal")
  public String getNomLocal() {
    return nomLocal;
  }

  public void setNomLocal(String nomLocal) {
    this.nomLocal = nomLocal;
  }

  public EspeceProtegee type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public EspeceProtegee statutConservation(String statutConservation) {
    this.statutConservation = statutConservation;
    return this;
  }

  /**
   * Get statutConservation
   * @return statutConservation
   */
  
  @Schema(name = "statutConservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statutConservation")
  public String getStatutConservation() {
    return statutConservation;
  }

  public void setStatutConservation(String statutConservation) {
    this.statutConservation = statutConservation;
  }

  public EspeceProtegee niveauProtection(String niveauProtection) {
    this.niveauProtection = niveauProtection;
    return this;
  }

  /**
   * Get niveauProtection
   * @return niveauProtection
   */
  
  @Schema(name = "niveauProtection", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("niveauProtection")
  public String getNiveauProtection() {
    return niveauProtection;
  }

  public void setNiveauProtection(String niveauProtection) {
    this.niveauProtection = niveauProtection;
  }

  public EspeceProtegee description(String description) {
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

  public EspeceProtegee photoUrl(URI photoUrl) {
    this.photoUrl = photoUrl;
    return this;
  }

  /**
   * Get photoUrl
   * @return photoUrl
   */
  @Valid 
  @Schema(name = "photoUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photoUrl")
  public URI getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(URI photoUrl) {
    this.photoUrl = photoUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EspeceProtegee especeProtegee = (EspeceProtegee) o;
    return Objects.equals(this.id, especeProtegee.id) &&
        Objects.equals(this.nomCommun, especeProtegee.nomCommun) &&
        Objects.equals(this.nomScientifique, especeProtegee.nomScientifique) &&
        Objects.equals(this.nomLocal, especeProtegee.nomLocal) &&
        Objects.equals(this.type, especeProtegee.type) &&
        Objects.equals(this.statutConservation, especeProtegee.statutConservation) &&
        Objects.equals(this.niveauProtection, especeProtegee.niveauProtection) &&
        Objects.equals(this.description, especeProtegee.description) &&
        Objects.equals(this.photoUrl, especeProtegee.photoUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nomCommun, nomScientifique, nomLocal, type, statutConservation, niveauProtection, description, photoUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EspeceProtegee {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nomCommun: ").append(toIndentedString(nomCommun)).append("\n");
    sb.append("    nomScientifique: ").append(toIndentedString(nomScientifique)).append("\n");
    sb.append("    nomLocal: ").append(toIndentedString(nomLocal)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    statutConservation: ").append(toIndentedString(statutConservation)).append("\n");
    sb.append("    niveauProtection: ").append(toIndentedString(niveauProtection)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    photoUrl: ").append(toIndentedString(photoUrl)).append("\n");
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

