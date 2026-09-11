package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.TypeMedia;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Media
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-11T15:43:53.256279100Z[Atlantic/Reykjavik]", comments = "Generator version: 7.25.0")
public class Media {

  private @Nullable UUID id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private UUID patrimoineId;

  private TypeMedia type;

  private String nom;

  private @Nullable String description;

  private URI url;

  private @Nullable String mimeType;

  private @Nullable Long tailleOctets;

  private @Nullable String langue;

  private @Nullable String auteur;

  private @Nullable String droitsUtilisation;

  private @Nullable String credit;

  private @Nullable Double latitude;

  private @Nullable Double longitude;

  public Media() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Media(UUID patrimoineId, TypeMedia type, String nom, URI url) {
    this.patrimoineId = patrimoineId;
    this.type = type;
    this.nom = nom;
    this.url = url;
  }

  public Media id(@Nullable UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable UUID getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  public Media createdAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public @Nullable OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Media updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updatedAt")
  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Media patrimoineId(UUID patrimoineId) {
    this.patrimoineId = patrimoineId;
    return this;
  }

  /**
   * Get patrimoineId
   * @return patrimoineId
   */
  @NotNull @Valid 
  @Schema(name = "patrimoineId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("patrimoineId")
  public UUID getPatrimoineId() {
    return patrimoineId;
  }

  @JsonProperty("patrimoineId")
  public void setPatrimoineId(UUID patrimoineId) {
    this.patrimoineId = patrimoineId;
  }

  public Media type(TypeMedia type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull @Valid 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeMedia getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(TypeMedia type) {
    this.type = type;
  }

  public Media nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
   */
  @NotNull 
  @Schema(name = "nom", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  @JsonProperty("nom")
  public void setNom(String nom) {
    this.nom = nom;
  }

  public Media description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public Media url(URI url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   */
  @NotNull @Valid 
  @Schema(name = "url", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("url")
  public URI getUrl() {
    return url;
  }

  @JsonProperty("url")
  public void setUrl(URI url) {
    this.url = url;
  }

  public Media mimeType(@Nullable String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

  /**
   * Get mimeType
   * @return mimeType
   */
  
  @Schema(name = "mimeType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mimeType")
  public @Nullable String getMimeType() {
    return mimeType;
  }

  @JsonProperty("mimeType")
  public void setMimeType(@Nullable String mimeType) {
    this.mimeType = mimeType;
  }

  public Media tailleOctets(@Nullable Long tailleOctets) {
    this.tailleOctets = tailleOctets;
    return this;
  }

  /**
   * Get tailleOctets
   * minimum: 0
   * @return tailleOctets
   */
  @Min(value = 0L) 
  @Schema(name = "tailleOctets", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tailleOctets")
  public @Nullable Long getTailleOctets() {
    return tailleOctets;
  }

  @JsonProperty("tailleOctets")
  public void setTailleOctets(@Nullable Long tailleOctets) {
    this.tailleOctets = tailleOctets;
  }

  public Media langue(@Nullable String langue) {
    this.langue = langue;
    return this;
  }

  /**
   * Get langue
   * @return langue
   */
  
  @Schema(name = "langue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("langue")
  public @Nullable String getLangue() {
    return langue;
  }

  @JsonProperty("langue")
  public void setLangue(@Nullable String langue) {
    this.langue = langue;
  }

  public Media auteur(@Nullable String auteur) {
    this.auteur = auteur;
    return this;
  }

  /**
   * Get auteur
   * @return auteur
   */
  
  @Schema(name = "auteur", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auteur")
  public @Nullable String getAuteur() {
    return auteur;
  }

  @JsonProperty("auteur")
  public void setAuteur(@Nullable String auteur) {
    this.auteur = auteur;
  }

  public Media droitsUtilisation(@Nullable String droitsUtilisation) {
    this.droitsUtilisation = droitsUtilisation;
    return this;
  }

  /**
   * Get droitsUtilisation
   * @return droitsUtilisation
   */
  
  @Schema(name = "droitsUtilisation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("droitsUtilisation")
  public @Nullable String getDroitsUtilisation() {
    return droitsUtilisation;
  }

  @JsonProperty("droitsUtilisation")
  public void setDroitsUtilisation(@Nullable String droitsUtilisation) {
    this.droitsUtilisation = droitsUtilisation;
  }

  public Media credit(@Nullable String credit) {
    this.credit = credit;
    return this;
  }

  /**
   * Get credit
   * @return credit
   */
  
  @Schema(name = "credit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("credit")
  public @Nullable String getCredit() {
    return credit;
  }

  @JsonProperty("credit")
  public void setCredit(@Nullable String credit) {
    this.credit = credit;
  }

  public Media latitude(@Nullable Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * @return latitude
   */
  
  @Schema(name = "latitude", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("latitude")
  public @Nullable Double getLatitude() {
    return latitude;
  }

  @JsonProperty("latitude")
  public void setLatitude(@Nullable Double latitude) {
    this.latitude = latitude;
  }

  public Media longitude(@Nullable Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * @return longitude
   */
  
  @Schema(name = "longitude", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("longitude")
  public @Nullable Double getLongitude() {
    return longitude;
  }

  @JsonProperty("longitude")
  public void setLongitude(@Nullable Double longitude) {
    this.longitude = longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Media media = (Media) o;
    return Objects.equals(this.id, media.id) &&
        Objects.equals(this.createdAt, media.createdAt) &&
        Objects.equals(this.updatedAt, media.updatedAt) &&
        Objects.equals(this.patrimoineId, media.patrimoineId) &&
        Objects.equals(this.type, media.type) &&
        Objects.equals(this.nom, media.nom) &&
        Objects.equals(this.description, media.description) &&
        Objects.equals(this.url, media.url) &&
        Objects.equals(this.mimeType, media.mimeType) &&
        Objects.equals(this.tailleOctets, media.tailleOctets) &&
        Objects.equals(this.langue, media.langue) &&
        Objects.equals(this.auteur, media.auteur) &&
        Objects.equals(this.droitsUtilisation, media.droitsUtilisation) &&
        Objects.equals(this.credit, media.credit) &&
        Objects.equals(this.latitude, media.latitude) &&
        Objects.equals(this.longitude, media.longitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, patrimoineId, type, nom, description, url, mimeType, tailleOctets, langue, auteur, droitsUtilisation, credit, latitude, longitude);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Media {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    patrimoineId: ").append(toIndentedString(patrimoineId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    tailleOctets: ").append(toIndentedString(tailleOctets)).append("\n");
    sb.append("    langue: ").append(toIndentedString(langue)).append("\n");
    sb.append("    auteur: ").append(toIndentedString(auteur)).append("\n");
    sb.append("    droitsUtilisation: ").append(toIndentedString(droitsUtilisation)).append("\n");
    sb.append("    credit: ").append(toIndentedString(credit)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}

