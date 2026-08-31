package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * CollectionMusee
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class CollectionMusee {

  private UUID id;

  private String nom;

  private String description;

  private Integer nombreOeuvres;

  private String periode;

  private String origine;

  @Valid
  private List<String> themes = new ArrayList<>();

  public CollectionMusee id(UUID id) {
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

  public CollectionMusee nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
   */
  
  @Schema(name = "nom", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public CollectionMusee description(String description) {
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

  public CollectionMusee nombreOeuvres(Integer nombreOeuvres) {
    this.nombreOeuvres = nombreOeuvres;
    return this;
  }

  /**
   * Get nombreOeuvres
   * minimum: 0
   * @return nombreOeuvres
   */
  @Min(0) 
  @Schema(name = "nombreOeuvres", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombreOeuvres")
  public Integer getNombreOeuvres() {
    return nombreOeuvres;
  }

  public void setNombreOeuvres(Integer nombreOeuvres) {
    this.nombreOeuvres = nombreOeuvres;
  }

  public CollectionMusee periode(String periode) {
    this.periode = periode;
    return this;
  }

  /**
   * Get periode
   * @return periode
   */
  
  @Schema(name = "periode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("periode")
  public String getPeriode() {
    return periode;
  }

  public void setPeriode(String periode) {
    this.periode = periode;
  }

  public CollectionMusee origine(String origine) {
    this.origine = origine;
    return this;
  }

  /**
   * Get origine
   * @return origine
   */
  
  @Schema(name = "origine", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("origine")
  public String getOrigine() {
    return origine;
  }

  public void setOrigine(String origine) {
    this.origine = origine;
  }

  public CollectionMusee themes(List<String> themes) {
    this.themes = themes;
    return this;
  }

  public CollectionMusee addThemesItem(String themesItem) {
    if (this.themes == null) {
      this.themes = new ArrayList<>();
    }
    this.themes.add(themesItem);
    return this;
  }

  /**
   * Get themes
   * @return themes
   */
  
  @Schema(name = "themes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("themes")
  public List<String> getThemes() {
    return themes;
  }

  public void setThemes(List<String> themes) {
    this.themes = themes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionMusee collectionMusee = (CollectionMusee) o;
    return Objects.equals(this.id, collectionMusee.id) &&
        Objects.equals(this.nom, collectionMusee.nom) &&
        Objects.equals(this.description, collectionMusee.description) &&
        Objects.equals(this.nombreOeuvres, collectionMusee.nombreOeuvres) &&
        Objects.equals(this.periode, collectionMusee.periode) &&
        Objects.equals(this.origine, collectionMusee.origine) &&
        Objects.equals(this.themes, collectionMusee.themes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, description, nombreOeuvres, periode, origine, themes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionMusee {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    nombreOeuvres: ").append(toIndentedString(nombreOeuvres)).append("\n");
    sb.append("    periode: ").append(toIndentedString(periode)).append("\n");
    sb.append("    origine: ").append(toIndentedString(origine)).append("\n");
    sb.append("    themes: ").append(toIndentedString(themes)).append("\n");
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

