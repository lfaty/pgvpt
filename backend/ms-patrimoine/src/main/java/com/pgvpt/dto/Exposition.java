package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Exposition
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Exposition {

  private UUID id;

  private String titre;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateDebut;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateFin;

  private String commissaire;

  private String lieu;

  private Boolean expositionPermanente;

  private URI imageUrl;

  public Exposition id(UUID id) {
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

  public Exposition titre(String titre) {
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

  public Exposition description(String description) {
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

  public Exposition dateDebut(LocalDate dateDebut) {
    this.dateDebut = dateDebut;
    return this;
  }

  /**
   * Get dateDebut
   * @return dateDebut
   */
  @Valid 
  @Schema(name = "dateDebut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateDebut")
  public LocalDate getDateDebut() {
    return dateDebut;
  }

  public void setDateDebut(LocalDate dateDebut) {
    this.dateDebut = dateDebut;
  }

  public Exposition dateFin(LocalDate dateFin) {
    this.dateFin = dateFin;
    return this;
  }

  /**
   * Get dateFin
   * @return dateFin
   */
  @Valid 
  @Schema(name = "dateFin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateFin")
  public LocalDate getDateFin() {
    return dateFin;
  }

  public void setDateFin(LocalDate dateFin) {
    this.dateFin = dateFin;
  }

  public Exposition commissaire(String commissaire) {
    this.commissaire = commissaire;
    return this;
  }

  /**
   * Get commissaire
   * @return commissaire
   */
  
  @Schema(name = "commissaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissaire")
  public String getCommissaire() {
    return commissaire;
  }

  public void setCommissaire(String commissaire) {
    this.commissaire = commissaire;
  }

  public Exposition lieu(String lieu) {
    this.lieu = lieu;
    return this;
  }

  /**
   * Get lieu
   * @return lieu
   */
  
  @Schema(name = "lieu", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lieu")
  public String getLieu() {
    return lieu;
  }

  public void setLieu(String lieu) {
    this.lieu = lieu;
  }

  public Exposition expositionPermanente(Boolean expositionPermanente) {
    this.expositionPermanente = expositionPermanente;
    return this;
  }

  /**
   * Get expositionPermanente
   * @return expositionPermanente
   */
  
  @Schema(name = "expositionPermanente", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expositionPermanente")
  public Boolean getExpositionPermanente() {
    return expositionPermanente;
  }

  public void setExpositionPermanente(Boolean expositionPermanente) {
    this.expositionPermanente = expositionPermanente;
  }

  public Exposition imageUrl(URI imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   */
  @Valid 
  @Schema(name = "imageUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imageUrl")
  public URI getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(URI imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exposition exposition = (Exposition) o;
    return Objects.equals(this.id, exposition.id) &&
        Objects.equals(this.titre, exposition.titre) &&
        Objects.equals(this.description, exposition.description) &&
        Objects.equals(this.dateDebut, exposition.dateDebut) &&
        Objects.equals(this.dateFin, exposition.dateFin) &&
        Objects.equals(this.commissaire, exposition.commissaire) &&
        Objects.equals(this.lieu, exposition.lieu) &&
        Objects.equals(this.expositionPermanente, exposition.expositionPermanente) &&
        Objects.equals(this.imageUrl, exposition.imageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, titre, description, dateDebut, dateFin, commissaire, lieu, expositionPermanente, imageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Exposition {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    dateDebut: ").append(toIndentedString(dateDebut)).append("\n");
    sb.append("    dateFin: ").append(toIndentedString(dateFin)).append("\n");
    sb.append("    commissaire: ").append(toIndentedString(commissaire)).append("\n");
    sb.append("    lieu: ").append(toIndentedString(lieu)).append("\n");
    sb.append("    expositionPermanente: ").append(toIndentedString(expositionPermanente)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
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

