package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.TypeMedia;
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
 * Media
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Media {

  private UUID id;

  private TypeMedia type;

  private String titre;

  private String description;

  private URI url;

  private URI miniatureUrl;

  private Integer duree;

  private String auteur;

  private String licence;

  public Media id(UUID id) {
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

  public Media type(TypeMedia type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @Valid 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeMedia getType() {
    return type;
  }

  public void setType(TypeMedia type) {
    this.type = type;
  }

  public Media titre(String titre) {
    this.titre = titre;
    return this;
  }

  /**
   * Get titre
   * @return titre
   */
  
  @Schema(name = "titre", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("titre")
  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public Media description(String description) {
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

  public Media url(URI url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   */
  @Valid 
  @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("url")
  public URI getUrl() {
    return url;
  }

  public void setUrl(URI url) {
    this.url = url;
  }

  public Media miniatureUrl(URI miniatureUrl) {
    this.miniatureUrl = miniatureUrl;
    return this;
  }

  /**
   * Get miniatureUrl
   * @return miniatureUrl
   */
  @Valid 
  @Schema(name = "miniatureUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("miniatureUrl")
  public URI getMiniatureUrl() {
    return miniatureUrl;
  }

  public void setMiniatureUrl(URI miniatureUrl) {
    this.miniatureUrl = miniatureUrl;
  }

  public Media duree(Integer duree) {
    this.duree = duree;
    return this;
  }

  /**
   * Durée en secondes pour une vidéo ou un audio
   * @return duree
   */
  
  @Schema(name = "duree", description = "Durée en secondes pour une vidéo ou un audio", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("duree")
  public Integer getDuree() {
    return duree;
  }

  public void setDuree(Integer duree) {
    this.duree = duree;
  }

  public Media auteur(String auteur) {
    this.auteur = auteur;
    return this;
  }

  /**
   * Get auteur
   * @return auteur
   */
  
  @Schema(name = "auteur", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auteur")
  public String getAuteur() {
    return auteur;
  }

  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  public Media licence(String licence) {
    this.licence = licence;
    return this;
  }

  /**
   * Get licence
   * @return licence
   */
  
  @Schema(name = "licence", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("licence")
  public String getLicence() {
    return licence;
  }

  public void setLicence(String licence) {
    this.licence = licence;
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
        Objects.equals(this.type, media.type) &&
        Objects.equals(this.titre, media.titre) &&
        Objects.equals(this.description, media.description) &&
        Objects.equals(this.url, media.url) &&
        Objects.equals(this.miniatureUrl, media.miniatureUrl) &&
        Objects.equals(this.duree, media.duree) &&
        Objects.equals(this.auteur, media.auteur) &&
        Objects.equals(this.licence, media.licence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, titre, description, url, miniatureUrl, duree, auteur, licence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Media {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    miniatureUrl: ").append(toIndentedString(miniatureUrl)).append("\n");
    sb.append("    duree: ").append(toIndentedString(duree)).append("\n");
    sb.append("    auteur: ").append(toIndentedString(auteur)).append("\n");
    sb.append("    licence: ").append(toIndentedString(licence)).append("\n");
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

