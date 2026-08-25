package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Tarification
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Tarification {

  private Boolean gratuit;

  private Double tarifNormal;

  private String devise = "XOF";

  private Double tarifEnfant;

  private Double tarifEtudiant;

  private Double tarifResident;

  private Double tarifGroupe;

  private Double tarifGuide;

  private String conditionsTarifaires;

  public Tarification gratuit(Boolean gratuit) {
    this.gratuit = gratuit;
    return this;
  }

  /**
   * Get gratuit
   * @return gratuit
   */
  
  @Schema(name = "gratuit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gratuit")
  public Boolean getGratuit() {
    return gratuit;
  }

  public void setGratuit(Boolean gratuit) {
    this.gratuit = gratuit;
  }

  public Tarification tarifNormal(Double tarifNormal) {
    this.tarifNormal = tarifNormal;
    return this;
  }

  /**
   * Get tarifNormal
   * minimum: 0
   * @return tarifNormal
   */
  @DecimalMin("0") 
  @Schema(name = "tarifNormal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifNormal")
  public Double getTarifNormal() {
    return tarifNormal;
  }

  public void setTarifNormal(Double tarifNormal) {
    this.tarifNormal = tarifNormal;
  }

  public Tarification devise(String devise) {
    this.devise = devise;
    return this;
  }

  /**
   * Get devise
   * @return devise
   */
  
  @Schema(name = "devise", example = "XOF", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("devise")
  public String getDevise() {
    return devise;
  }

  public void setDevise(String devise) {
    this.devise = devise;
  }

  public Tarification tarifEnfant(Double tarifEnfant) {
    this.tarifEnfant = tarifEnfant;
    return this;
  }

  /**
   * Get tarifEnfant
   * minimum: 0
   * @return tarifEnfant
   */
  @DecimalMin("0") 
  @Schema(name = "tarifEnfant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifEnfant")
  public Double getTarifEnfant() {
    return tarifEnfant;
  }

  public void setTarifEnfant(Double tarifEnfant) {
    this.tarifEnfant = tarifEnfant;
  }

  public Tarification tarifEtudiant(Double tarifEtudiant) {
    this.tarifEtudiant = tarifEtudiant;
    return this;
  }

  /**
   * Get tarifEtudiant
   * minimum: 0
   * @return tarifEtudiant
   */
  @DecimalMin("0") 
  @Schema(name = "tarifEtudiant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifEtudiant")
  public Double getTarifEtudiant() {
    return tarifEtudiant;
  }

  public void setTarifEtudiant(Double tarifEtudiant) {
    this.tarifEtudiant = tarifEtudiant;
  }

  public Tarification tarifResident(Double tarifResident) {
    this.tarifResident = tarifResident;
    return this;
  }

  /**
   * Get tarifResident
   * minimum: 0
   * @return tarifResident
   */
  @DecimalMin("0") 
  @Schema(name = "tarifResident", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifResident")
  public Double getTarifResident() {
    return tarifResident;
  }

  public void setTarifResident(Double tarifResident) {
    this.tarifResident = tarifResident;
  }

  public Tarification tarifGroupe(Double tarifGroupe) {
    this.tarifGroupe = tarifGroupe;
    return this;
  }

  /**
   * Get tarifGroupe
   * minimum: 0
   * @return tarifGroupe
   */
  @DecimalMin("0") 
  @Schema(name = "tarifGroupe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifGroupe")
  public Double getTarifGroupe() {
    return tarifGroupe;
  }

  public void setTarifGroupe(Double tarifGroupe) {
    this.tarifGroupe = tarifGroupe;
  }

  public Tarification tarifGuide(Double tarifGuide) {
    this.tarifGuide = tarifGuide;
    return this;
  }

  /**
   * Get tarifGuide
   * minimum: 0
   * @return tarifGuide
   */
  @DecimalMin("0") 
  @Schema(name = "tarifGuide", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarifGuide")
  public Double getTarifGuide() {
    return tarifGuide;
  }

  public void setTarifGuide(Double tarifGuide) {
    this.tarifGuide = tarifGuide;
  }

  public Tarification conditionsTarifaires(String conditionsTarifaires) {
    this.conditionsTarifaires = conditionsTarifaires;
    return this;
  }

  /**
   * Get conditionsTarifaires
   * @return conditionsTarifaires
   */
  
  @Schema(name = "conditionsTarifaires", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conditionsTarifaires")
  public String getConditionsTarifaires() {
    return conditionsTarifaires;
  }

  public void setConditionsTarifaires(String conditionsTarifaires) {
    this.conditionsTarifaires = conditionsTarifaires;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tarification tarification = (Tarification) o;
    return Objects.equals(this.gratuit, tarification.gratuit) &&
        Objects.equals(this.tarifNormal, tarification.tarifNormal) &&
        Objects.equals(this.devise, tarification.devise) &&
        Objects.equals(this.tarifEnfant, tarification.tarifEnfant) &&
        Objects.equals(this.tarifEtudiant, tarification.tarifEtudiant) &&
        Objects.equals(this.tarifResident, tarification.tarifResident) &&
        Objects.equals(this.tarifGroupe, tarification.tarifGroupe) &&
        Objects.equals(this.tarifGuide, tarification.tarifGuide) &&
        Objects.equals(this.conditionsTarifaires, tarification.conditionsTarifaires);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gratuit, tarifNormal, devise, tarifEnfant, tarifEtudiant, tarifResident, tarifGroupe, tarifGuide, conditionsTarifaires);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tarification {\n");
    sb.append("    gratuit: ").append(toIndentedString(gratuit)).append("\n");
    sb.append("    tarifNormal: ").append(toIndentedString(tarifNormal)).append("\n");
    sb.append("    devise: ").append(toIndentedString(devise)).append("\n");
    sb.append("    tarifEnfant: ").append(toIndentedString(tarifEnfant)).append("\n");
    sb.append("    tarifEtudiant: ").append(toIndentedString(tarifEtudiant)).append("\n");
    sb.append("    tarifResident: ").append(toIndentedString(tarifResident)).append("\n");
    sb.append("    tarifGroupe: ").append(toIndentedString(tarifGroupe)).append("\n");
    sb.append("    tarifGuide: ").append(toIndentedString(tarifGuide)).append("\n");
    sb.append("    conditionsTarifaires: ").append(toIndentedString(conditionsTarifaires)).append("\n");
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

