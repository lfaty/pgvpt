package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.JourSemaine;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HoraireOuverture
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class HoraireOuverture {

  private JourSemaine jour;

  private Boolean ouvert;

  private String heureOuverture;

  private String heureFermeture;

  private String pauseDebut;

  private String pauseFin;

  private Boolean surReservation;

  private String commentaire;

  public HoraireOuverture() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HoraireOuverture(JourSemaine jour) {
    this.jour = jour;
  }

  public HoraireOuverture jour(JourSemaine jour) {
    this.jour = jour;
    return this;
  }

  /**
   * Get jour
   * @return jour
   */
  @NotNull @Valid 
  @Schema(name = "jour", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("jour")
  public JourSemaine getJour() {
    return jour;
  }

  public void setJour(JourSemaine jour) {
    this.jour = jour;
  }

  public HoraireOuverture ouvert(Boolean ouvert) {
    this.ouvert = ouvert;
    return this;
  }

  /**
   * Get ouvert
   * @return ouvert
   */
  
  @Schema(name = "ouvert", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ouvert")
  public Boolean getOuvert() {
    return ouvert;
  }

  public void setOuvert(Boolean ouvert) {
    this.ouvert = ouvert;
  }

  public HoraireOuverture heureOuverture(String heureOuverture) {
    this.heureOuverture = heureOuverture;
    return this;
  }

  /**
   * Get heureOuverture
   * @return heureOuverture
   */
  
  @Schema(name = "heureOuverture", example = "09:00:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("heureOuverture")
  public String getHeureOuverture() {
    return heureOuverture;
  }

  public void setHeureOuverture(String heureOuverture) {
    this.heureOuverture = heureOuverture;
  }

  public HoraireOuverture heureFermeture(String heureFermeture) {
    this.heureFermeture = heureFermeture;
    return this;
  }

  /**
   * Get heureFermeture
   * @return heureFermeture
   */
  
  @Schema(name = "heureFermeture", example = "18:00:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("heureFermeture")
  public String getHeureFermeture() {
    return heureFermeture;
  }

  public void setHeureFermeture(String heureFermeture) {
    this.heureFermeture = heureFermeture;
  }

  public HoraireOuverture pauseDebut(String pauseDebut) {
    this.pauseDebut = pauseDebut;
    return this;
  }

  /**
   * Get pauseDebut
   * @return pauseDebut
   */
  
  @Schema(name = "pauseDebut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pauseDebut")
  public String getPauseDebut() {
    return pauseDebut;
  }

  public void setPauseDebut(String pauseDebut) {
    this.pauseDebut = pauseDebut;
  }

  public HoraireOuverture pauseFin(String pauseFin) {
    this.pauseFin = pauseFin;
    return this;
  }

  /**
   * Get pauseFin
   * @return pauseFin
   */
  
  @Schema(name = "pauseFin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pauseFin")
  public String getPauseFin() {
    return pauseFin;
  }

  public void setPauseFin(String pauseFin) {
    this.pauseFin = pauseFin;
  }

  public HoraireOuverture surReservation(Boolean surReservation) {
    this.surReservation = surReservation;
    return this;
  }

  /**
   * Get surReservation
   * @return surReservation
   */
  
  @Schema(name = "surReservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("surReservation")
  public Boolean getSurReservation() {
    return surReservation;
  }

  public void setSurReservation(Boolean surReservation) {
    this.surReservation = surReservation;
  }

  public HoraireOuverture commentaire(String commentaire) {
    this.commentaire = commentaire;
    return this;
  }

  /**
   * Get commentaire
   * @return commentaire
   */
  
  @Schema(name = "commentaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commentaire")
  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HoraireOuverture horaireOuverture = (HoraireOuverture) o;
    return Objects.equals(this.jour, horaireOuverture.jour) &&
        Objects.equals(this.ouvert, horaireOuverture.ouvert) &&
        Objects.equals(this.heureOuverture, horaireOuverture.heureOuverture) &&
        Objects.equals(this.heureFermeture, horaireOuverture.heureFermeture) &&
        Objects.equals(this.pauseDebut, horaireOuverture.pauseDebut) &&
        Objects.equals(this.pauseFin, horaireOuverture.pauseFin) &&
        Objects.equals(this.surReservation, horaireOuverture.surReservation) &&
        Objects.equals(this.commentaire, horaireOuverture.commentaire);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jour, ouvert, heureOuverture, heureFermeture, pauseDebut, pauseFin, surReservation, commentaire);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HoraireOuverture {\n");
    sb.append("    jour: ").append(toIndentedString(jour)).append("\n");
    sb.append("    ouvert: ").append(toIndentedString(ouvert)).append("\n");
    sb.append("    heureOuverture: ").append(toIndentedString(heureOuverture)).append("\n");
    sb.append("    heureFermeture: ").append(toIndentedString(heureFermeture)).append("\n");
    sb.append("    pauseDebut: ").append(toIndentedString(pauseDebut)).append("\n");
    sb.append("    pauseFin: ").append(toIndentedString(pauseFin)).append("\n");
    sb.append("    surReservation: ").append(toIndentedString(surReservation)).append("\n");
    sb.append("    commentaire: ").append(toIndentedString(commentaire)).append("\n");
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

