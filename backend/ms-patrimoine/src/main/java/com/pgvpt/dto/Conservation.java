package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.EtatConservation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Conservation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Conservation {

  private EtatConservation etat;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateEvaluation;

  private String evaluateur;

  private String descriptionEtat;

  @Valid
  private List<String> degradations = new ArrayList<>();

  @Valid
  private List<String> causesDegradation = new ArrayList<>();

  @Valid
  private List<String> travauxNecessaires = new ArrayList<>();

  private String dernierTravaux;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateDerniersTravaux;

  private String organismeConservation;

  private Double budgetEstime;

  @Valid
  private List<String> recommandations = new ArrayList<>();

  public Conservation etat(EtatConservation etat) {
    this.etat = etat;
    return this;
  }

  /**
   * Get etat
   * @return etat
   */
  @Valid 
  @Schema(name = "etat", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("etat")
  public EtatConservation getEtat() {
    return etat;
  }

  public void setEtat(EtatConservation etat) {
    this.etat = etat;
  }

  public Conservation dateEvaluation(LocalDate dateEvaluation) {
    this.dateEvaluation = dateEvaluation;
    return this;
  }

  /**
   * Get dateEvaluation
   * @return dateEvaluation
   */
  @Valid 
  @Schema(name = "dateEvaluation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateEvaluation")
  public LocalDate getDateEvaluation() {
    return dateEvaluation;
  }

  public void setDateEvaluation(LocalDate dateEvaluation) {
    this.dateEvaluation = dateEvaluation;
  }

  public Conservation evaluateur(String evaluateur) {
    this.evaluateur = evaluateur;
    return this;
  }

  /**
   * Get evaluateur
   * @return evaluateur
   */
  
  @Schema(name = "evaluateur", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("evaluateur")
  public String getEvaluateur() {
    return evaluateur;
  }

  public void setEvaluateur(String evaluateur) {
    this.evaluateur = evaluateur;
  }

  public Conservation descriptionEtat(String descriptionEtat) {
    this.descriptionEtat = descriptionEtat;
    return this;
  }

  /**
   * Get descriptionEtat
   * @return descriptionEtat
   */
  
  @Schema(name = "descriptionEtat", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("descriptionEtat")
  public String getDescriptionEtat() {
    return descriptionEtat;
  }

  public void setDescriptionEtat(String descriptionEtat) {
    this.descriptionEtat = descriptionEtat;
  }

  public Conservation degradations(List<String> degradations) {
    this.degradations = degradations;
    return this;
  }

  public Conservation addDegradationsItem(String degradationsItem) {
    if (this.degradations == null) {
      this.degradations = new ArrayList<>();
    }
    this.degradations.add(degradationsItem);
    return this;
  }

  /**
   * Get degradations
   * @return degradations
   */
  
  @Schema(name = "degradations", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("degradations")
  public List<String> getDegradations() {
    return degradations;
  }

  public void setDegradations(List<String> degradations) {
    this.degradations = degradations;
  }

  public Conservation causesDegradation(List<String> causesDegradation) {
    this.causesDegradation = causesDegradation;
    return this;
  }

  public Conservation addCausesDegradationItem(String causesDegradationItem) {
    if (this.causesDegradation == null) {
      this.causesDegradation = new ArrayList<>();
    }
    this.causesDegradation.add(causesDegradationItem);
    return this;
  }

  /**
   * Get causesDegradation
   * @return causesDegradation
   */
  
  @Schema(name = "causesDegradation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("causesDegradation")
  public List<String> getCausesDegradation() {
    return causesDegradation;
  }

  public void setCausesDegradation(List<String> causesDegradation) {
    this.causesDegradation = causesDegradation;
  }

  public Conservation travauxNecessaires(List<String> travauxNecessaires) {
    this.travauxNecessaires = travauxNecessaires;
    return this;
  }

  public Conservation addTravauxNecessairesItem(String travauxNecessairesItem) {
    if (this.travauxNecessaires == null) {
      this.travauxNecessaires = new ArrayList<>();
    }
    this.travauxNecessaires.add(travauxNecessairesItem);
    return this;
  }

  /**
   * Get travauxNecessaires
   * @return travauxNecessaires
   */
  
  @Schema(name = "travauxNecessaires", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("travauxNecessaires")
  public List<String> getTravauxNecessaires() {
    return travauxNecessaires;
  }

  public void setTravauxNecessaires(List<String> travauxNecessaires) {
    this.travauxNecessaires = travauxNecessaires;
  }

  public Conservation dernierTravaux(String dernierTravaux) {
    this.dernierTravaux = dernierTravaux;
    return this;
  }

  /**
   * Get dernierTravaux
   * @return dernierTravaux
   */
  
  @Schema(name = "dernierTravaux", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dernierTravaux")
  public String getDernierTravaux() {
    return dernierTravaux;
  }

  public void setDernierTravaux(String dernierTravaux) {
    this.dernierTravaux = dernierTravaux;
  }

  public Conservation dateDerniersTravaux(LocalDate dateDerniersTravaux) {
    this.dateDerniersTravaux = dateDerniersTravaux;
    return this;
  }

  /**
   * Get dateDerniersTravaux
   * @return dateDerniersTravaux
   */
  @Valid 
  @Schema(name = "dateDerniersTravaux", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateDerniersTravaux")
  public LocalDate getDateDerniersTravaux() {
    return dateDerniersTravaux;
  }

  public void setDateDerniersTravaux(LocalDate dateDerniersTravaux) {
    this.dateDerniersTravaux = dateDerniersTravaux;
  }

  public Conservation organismeConservation(String organismeConservation) {
    this.organismeConservation = organismeConservation;
    return this;
  }

  /**
   * Get organismeConservation
   * @return organismeConservation
   */
  
  @Schema(name = "organismeConservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("organismeConservation")
  public String getOrganismeConservation() {
    return organismeConservation;
  }

  public void setOrganismeConservation(String organismeConservation) {
    this.organismeConservation = organismeConservation;
  }

  public Conservation budgetEstime(Double budgetEstime) {
    this.budgetEstime = budgetEstime;
    return this;
  }

  /**
   * Get budgetEstime
   * @return budgetEstime
   */
  
  @Schema(name = "budgetEstime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("budgetEstime")
  public Double getBudgetEstime() {
    return budgetEstime;
  }

  public void setBudgetEstime(Double budgetEstime) {
    this.budgetEstime = budgetEstime;
  }

  public Conservation recommandations(List<String> recommandations) {
    this.recommandations = recommandations;
    return this;
  }

  public Conservation addRecommandationsItem(String recommandationsItem) {
    if (this.recommandations == null) {
      this.recommandations = new ArrayList<>();
    }
    this.recommandations.add(recommandationsItem);
    return this;
  }

  /**
   * Get recommandations
   * @return recommandations
   */
  
  @Schema(name = "recommandations", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recommandations")
  public List<String> getRecommandations() {
    return recommandations;
  }

  public void setRecommandations(List<String> recommandations) {
    this.recommandations = recommandations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Conservation conservation = (Conservation) o;
    return Objects.equals(this.etat, conservation.etat) &&
        Objects.equals(this.dateEvaluation, conservation.dateEvaluation) &&
        Objects.equals(this.evaluateur, conservation.evaluateur) &&
        Objects.equals(this.descriptionEtat, conservation.descriptionEtat) &&
        Objects.equals(this.degradations, conservation.degradations) &&
        Objects.equals(this.causesDegradation, conservation.causesDegradation) &&
        Objects.equals(this.travauxNecessaires, conservation.travauxNecessaires) &&
        Objects.equals(this.dernierTravaux, conservation.dernierTravaux) &&
        Objects.equals(this.dateDerniersTravaux, conservation.dateDerniersTravaux) &&
        Objects.equals(this.organismeConservation, conservation.organismeConservation) &&
        Objects.equals(this.budgetEstime, conservation.budgetEstime) &&
        Objects.equals(this.recommandations, conservation.recommandations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(etat, dateEvaluation, evaluateur, descriptionEtat, degradations, causesDegradation, travauxNecessaires, dernierTravaux, dateDerniersTravaux, organismeConservation, budgetEstime, recommandations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Conservation {\n");
    sb.append("    etat: ").append(toIndentedString(etat)).append("\n");
    sb.append("    dateEvaluation: ").append(toIndentedString(dateEvaluation)).append("\n");
    sb.append("    evaluateur: ").append(toIndentedString(evaluateur)).append("\n");
    sb.append("    descriptionEtat: ").append(toIndentedString(descriptionEtat)).append("\n");
    sb.append("    degradations: ").append(toIndentedString(degradations)).append("\n");
    sb.append("    causesDegradation: ").append(toIndentedString(causesDegradation)).append("\n");
    sb.append("    travauxNecessaires: ").append(toIndentedString(travauxNecessaires)).append("\n");
    sb.append("    dernierTravaux: ").append(toIndentedString(dernierTravaux)).append("\n");
    sb.append("    dateDerniersTravaux: ").append(toIndentedString(dateDerniersTravaux)).append("\n");
    sb.append("    organismeConservation: ").append(toIndentedString(organismeConservation)).append("\n");
    sb.append("    budgetEstime: ").append(toIndentedString(budgetEstime)).append("\n");
    sb.append("    recommandations: ").append(toIndentedString(recommandations)).append("\n");
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

