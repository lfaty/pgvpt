package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.Localisation;
import com.pgvpt.dto.TypePatrimoine;
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
 * PatrimoineSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class PatrimoineSummary {

  private UUID id;

  private String nom;

  private TypePatrimoine type;

  private CategoriePatrimoine categorie;

  private String descriptionCourte;

  private Localisation localisation;

  private URI photoPrincipale;

  private EtatConservation etatConservation;

  private Boolean inscritUnesco;

  public PatrimoineSummary id(UUID id) {
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

  public PatrimoineSummary nom(String nom) {
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

  public PatrimoineSummary type(TypePatrimoine type) {
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
  public TypePatrimoine getType() {
    return type;
  }

  public void setType(TypePatrimoine type) {
    this.type = type;
  }

  public PatrimoineSummary categorie(CategoriePatrimoine categorie) {
    this.categorie = categorie;
    return this;
  }

  /**
   * Get categorie
   * @return categorie
   */
  @Valid 
  @Schema(name = "categorie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categorie")
  public CategoriePatrimoine getCategorie() {
    return categorie;
  }

  public void setCategorie(CategoriePatrimoine categorie) {
    this.categorie = categorie;
  }

  public PatrimoineSummary descriptionCourte(String descriptionCourte) {
    this.descriptionCourte = descriptionCourte;
    return this;
  }

  /**
   * Get descriptionCourte
   * @return descriptionCourte
   */
  
  @Schema(name = "descriptionCourte", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("descriptionCourte")
  public String getDescriptionCourte() {
    return descriptionCourte;
  }

  public void setDescriptionCourte(String descriptionCourte) {
    this.descriptionCourte = descriptionCourte;
  }

  public PatrimoineSummary localisation(Localisation localisation) {
    this.localisation = localisation;
    return this;
  }

  /**
   * Get localisation
   * @return localisation
   */
  @Valid 
  @Schema(name = "localisation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("localisation")
  public Localisation getLocalisation() {
    return localisation;
  }

  public void setLocalisation(Localisation localisation) {
    this.localisation = localisation;
  }

  public PatrimoineSummary photoPrincipale(URI photoPrincipale) {
    this.photoPrincipale = photoPrincipale;
    return this;
  }

  /**
   * Get photoPrincipale
   * @return photoPrincipale
   */
  @Valid 
  @Schema(name = "photoPrincipale", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photoPrincipale")
  public URI getPhotoPrincipale() {
    return photoPrincipale;
  }

  public void setPhotoPrincipale(URI photoPrincipale) {
    this.photoPrincipale = photoPrincipale;
  }

  public PatrimoineSummary etatConservation(EtatConservation etatConservation) {
    this.etatConservation = etatConservation;
    return this;
  }

  /**
   * Get etatConservation
   * @return etatConservation
   */
  @Valid 
  @Schema(name = "etatConservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("etatConservation")
  public EtatConservation getEtatConservation() {
    return etatConservation;
  }

  public void setEtatConservation(EtatConservation etatConservation) {
    this.etatConservation = etatConservation;
  }

  public PatrimoineSummary inscritUnesco(Boolean inscritUnesco) {
    this.inscritUnesco = inscritUnesco;
    return this;
  }

  /**
   * Get inscritUnesco
   * @return inscritUnesco
   */
  
  @Schema(name = "inscritUnesco", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inscritUnesco")
  public Boolean getInscritUnesco() {
    return inscritUnesco;
  }

  public void setInscritUnesco(Boolean inscritUnesco) {
    this.inscritUnesco = inscritUnesco;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatrimoineSummary patrimoineSummary = (PatrimoineSummary) o;
    return Objects.equals(this.id, patrimoineSummary.id) &&
        Objects.equals(this.nom, patrimoineSummary.nom) &&
        Objects.equals(this.type, patrimoineSummary.type) &&
        Objects.equals(this.categorie, patrimoineSummary.categorie) &&
        Objects.equals(this.descriptionCourte, patrimoineSummary.descriptionCourte) &&
        Objects.equals(this.localisation, patrimoineSummary.localisation) &&
        Objects.equals(this.photoPrincipale, patrimoineSummary.photoPrincipale) &&
        Objects.equals(this.etatConservation, patrimoineSummary.etatConservation) &&
        Objects.equals(this.inscritUnesco, patrimoineSummary.inscritUnesco);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, type, categorie, descriptionCourte, localisation, photoPrincipale, etatConservation, inscritUnesco);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatrimoineSummary {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    categorie: ").append(toIndentedString(categorie)).append("\n");
    sb.append("    descriptionCourte: ").append(toIndentedString(descriptionCourte)).append("\n");
    sb.append("    localisation: ").append(toIndentedString(localisation)).append("\n");
    sb.append("    photoPrincipale: ").append(toIndentedString(photoPrincipale)).append("\n");
    sb.append("    etatConservation: ").append(toIndentedString(etatConservation)).append("\n");
    sb.append("    inscritUnesco: ").append(toIndentedString(inscritUnesco)).append("\n");
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

