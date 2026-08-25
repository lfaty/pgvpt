package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Accessibilite
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Accessibilite {

  private Boolean accessiblePublic;

  private Boolean accessibilitePMR;

  private Boolean accesFauteuilRoulant;

  private Boolean accesTransportPublic;

  private Boolean parking;

  private Boolean guideDisponible;

  private Boolean guideAudio;

  @Valid
  private List<String> langueVisite = new ArrayList<>();

  private Boolean accesEnfant;

  private String conditionsAcces;

  @Valid
  private List<String> restrictions = new ArrayList<>();

  public Accessibilite accessiblePublic(Boolean accessiblePublic) {
    this.accessiblePublic = accessiblePublic;
    return this;
  }

  /**
   * Get accessiblePublic
   * @return accessiblePublic
   */
  
  @Schema(name = "accessiblePublic", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accessiblePublic")
  public Boolean getAccessiblePublic() {
    return accessiblePublic;
  }

  public void setAccessiblePublic(Boolean accessiblePublic) {
    this.accessiblePublic = accessiblePublic;
  }

  public Accessibilite accessibilitePMR(Boolean accessibilitePMR) {
    this.accessibilitePMR = accessibilitePMR;
    return this;
  }

  /**
   * Get accessibilitePMR
   * @return accessibilitePMR
   */
  
  @Schema(name = "accessibilitePMR", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accessibilitePMR")
  public Boolean getAccessibilitePMR() {
    return accessibilitePMR;
  }

  public void setAccessibilitePMR(Boolean accessibilitePMR) {
    this.accessibilitePMR = accessibilitePMR;
  }

  public Accessibilite accesFauteuilRoulant(Boolean accesFauteuilRoulant) {
    this.accesFauteuilRoulant = accesFauteuilRoulant;
    return this;
  }

  /**
   * Get accesFauteuilRoulant
   * @return accesFauteuilRoulant
   */
  
  @Schema(name = "accesFauteuilRoulant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accesFauteuilRoulant")
  public Boolean getAccesFauteuilRoulant() {
    return accesFauteuilRoulant;
  }

  public void setAccesFauteuilRoulant(Boolean accesFauteuilRoulant) {
    this.accesFauteuilRoulant = accesFauteuilRoulant;
  }

  public Accessibilite accesTransportPublic(Boolean accesTransportPublic) {
    this.accesTransportPublic = accesTransportPublic;
    return this;
  }

  /**
   * Get accesTransportPublic
   * @return accesTransportPublic
   */
  
  @Schema(name = "accesTransportPublic", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accesTransportPublic")
  public Boolean getAccesTransportPublic() {
    return accesTransportPublic;
  }

  public void setAccesTransportPublic(Boolean accesTransportPublic) {
    this.accesTransportPublic = accesTransportPublic;
  }

  public Accessibilite parking(Boolean parking) {
    this.parking = parking;
    return this;
  }

  /**
   * Get parking
   * @return parking
   */
  
  @Schema(name = "parking", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parking")
  public Boolean getParking() {
    return parking;
  }

  public void setParking(Boolean parking) {
    this.parking = parking;
  }

  public Accessibilite guideDisponible(Boolean guideDisponible) {
    this.guideDisponible = guideDisponible;
    return this;
  }

  /**
   * Get guideDisponible
   * @return guideDisponible
   */
  
  @Schema(name = "guideDisponible", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("guideDisponible")
  public Boolean getGuideDisponible() {
    return guideDisponible;
  }

  public void setGuideDisponible(Boolean guideDisponible) {
    this.guideDisponible = guideDisponible;
  }

  public Accessibilite guideAudio(Boolean guideAudio) {
    this.guideAudio = guideAudio;
    return this;
  }

  /**
   * Get guideAudio
   * @return guideAudio
   */
  
  @Schema(name = "guideAudio", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("guideAudio")
  public Boolean getGuideAudio() {
    return guideAudio;
  }

  public void setGuideAudio(Boolean guideAudio) {
    this.guideAudio = guideAudio;
  }

  public Accessibilite langueVisite(List<String> langueVisite) {
    this.langueVisite = langueVisite;
    return this;
  }

  public Accessibilite addLangueVisiteItem(String langueVisiteItem) {
    if (this.langueVisite == null) {
      this.langueVisite = new ArrayList<>();
    }
    this.langueVisite.add(langueVisiteItem);
    return this;
  }

  /**
   * Get langueVisite
   * @return langueVisite
   */
  
  @Schema(name = "langueVisite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("langueVisite")
  public List<String> getLangueVisite() {
    return langueVisite;
  }

  public void setLangueVisite(List<String> langueVisite) {
    this.langueVisite = langueVisite;
  }

  public Accessibilite accesEnfant(Boolean accesEnfant) {
    this.accesEnfant = accesEnfant;
    return this;
  }

  /**
   * Get accesEnfant
   * @return accesEnfant
   */
  
  @Schema(name = "accesEnfant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accesEnfant")
  public Boolean getAccesEnfant() {
    return accesEnfant;
  }

  public void setAccesEnfant(Boolean accesEnfant) {
    this.accesEnfant = accesEnfant;
  }

  public Accessibilite conditionsAcces(String conditionsAcces) {
    this.conditionsAcces = conditionsAcces;
    return this;
  }

  /**
   * Get conditionsAcces
   * @return conditionsAcces
   */
  
  @Schema(name = "conditionsAcces", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conditionsAcces")
  public String getConditionsAcces() {
    return conditionsAcces;
  }

  public void setConditionsAcces(String conditionsAcces) {
    this.conditionsAcces = conditionsAcces;
  }

  public Accessibilite restrictions(List<String> restrictions) {
    this.restrictions = restrictions;
    return this;
  }

  public Accessibilite addRestrictionsItem(String restrictionsItem) {
    if (this.restrictions == null) {
      this.restrictions = new ArrayList<>();
    }
    this.restrictions.add(restrictionsItem);
    return this;
  }

  /**
   * Get restrictions
   * @return restrictions
   */
  
  @Schema(name = "restrictions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("restrictions")
  public List<String> getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(List<String> restrictions) {
    this.restrictions = restrictions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Accessibilite accessibilite = (Accessibilite) o;
    return Objects.equals(this.accessiblePublic, accessibilite.accessiblePublic) &&
        Objects.equals(this.accessibilitePMR, accessibilite.accessibilitePMR) &&
        Objects.equals(this.accesFauteuilRoulant, accessibilite.accesFauteuilRoulant) &&
        Objects.equals(this.accesTransportPublic, accessibilite.accesTransportPublic) &&
        Objects.equals(this.parking, accessibilite.parking) &&
        Objects.equals(this.guideDisponible, accessibilite.guideDisponible) &&
        Objects.equals(this.guideAudio, accessibilite.guideAudio) &&
        Objects.equals(this.langueVisite, accessibilite.langueVisite) &&
        Objects.equals(this.accesEnfant, accessibilite.accesEnfant) &&
        Objects.equals(this.conditionsAcces, accessibilite.conditionsAcces) &&
        Objects.equals(this.restrictions, accessibilite.restrictions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessiblePublic, accessibilitePMR, accesFauteuilRoulant, accesTransportPublic, parking, guideDisponible, guideAudio, langueVisite, accesEnfant, conditionsAcces, restrictions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Accessibilite {\n");
    sb.append("    accessiblePublic: ").append(toIndentedString(accessiblePublic)).append("\n");
    sb.append("    accessibilitePMR: ").append(toIndentedString(accessibilitePMR)).append("\n");
    sb.append("    accesFauteuilRoulant: ").append(toIndentedString(accesFauteuilRoulant)).append("\n");
    sb.append("    accesTransportPublic: ").append(toIndentedString(accesTransportPublic)).append("\n");
    sb.append("    parking: ").append(toIndentedString(parking)).append("\n");
    sb.append("    guideDisponible: ").append(toIndentedString(guideDisponible)).append("\n");
    sb.append("    guideAudio: ").append(toIndentedString(guideAudio)).append("\n");
    sb.append("    langueVisite: ").append(toIndentedString(langueVisite)).append("\n");
    sb.append("    accesEnfant: ").append(toIndentedString(accesEnfant)).append("\n");
    sb.append("    conditionsAcces: ").append(toIndentedString(conditionsAcces)).append("\n");
    sb.append("    restrictions: ").append(toIndentedString(restrictions)).append("\n");
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

