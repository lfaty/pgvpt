package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.StatutContenu;
import com.pgvpt.dto.TypeContenu;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Contenu
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-05T09:51:25.206125500Z[Africa/Dakar]", comments = "Generator version: 7.25.0")
public class Contenu {

  private @Nullable UUID id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private UUID patrimoineId;

  private TypeContenu type;

  private String langue;

  private String titre;

  private @Nullable String resume;

  private String corps;

  private @Nullable UUID auteurActeurId;

  private @Nullable List<String> motsCles;

  private @Nullable StatutContenu statut;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime datePublication;

  public Contenu() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Contenu(UUID patrimoineId, TypeContenu type, String langue, String titre, String corps) {
    this.patrimoineId = patrimoineId;
    this.type = type;
    this.langue = langue;
    this.titre = titre;
    this.corps = corps;
  }

  public Contenu id(@Nullable UUID id) {
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

  public Contenu createdAt(@Nullable OffsetDateTime createdAt) {
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

  public Contenu updatedAt(@Nullable OffsetDateTime updatedAt) {
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

  public Contenu patrimoineId(UUID patrimoineId) {
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

  public Contenu type(TypeContenu type) {
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
  public TypeContenu getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(TypeContenu type) {
    this.type = type;
  }

  public Contenu langue(String langue) {
    this.langue = langue;
    return this;
  }

  /**
   * Get langue
   * @return langue
   */
  @NotNull @Size(min = 2) 
  @Schema(name = "langue", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("langue")
  public String getLangue() {
    return langue;
  }

  @JsonProperty("langue")
  public void setLangue(String langue) {
    this.langue = langue;
  }

  public Contenu titre(String titre) {
    this.titre = titre;
    return this;
  }

  /**
   * Get titre
   * @return titre
   */
  @NotNull 
  @Schema(name = "titre", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("titre")
  public String getTitre() {
    return titre;
  }

  @JsonProperty("titre")
  public void setTitre(String titre) {
    this.titre = titre;
  }

  public Contenu resume(@Nullable String resume) {
    this.resume = resume;
    return this;
  }

  /**
   * Get resume
   * @return resume
   */
  
  @Schema(name = "resume", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("resume")
  public @Nullable String getResume() {
    return resume;
  }

  @JsonProperty("resume")
  public void setResume(@Nullable String resume) {
    this.resume = resume;
  }

  public Contenu corps(String corps) {
    this.corps = corps;
    return this;
  }

  /**
   * Get corps
   * @return corps
   */
  @NotNull 
  @Schema(name = "corps", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("corps")
  public String getCorps() {
    return corps;
  }

  @JsonProperty("corps")
  public void setCorps(String corps) {
    this.corps = corps;
  }

  public Contenu auteurActeurId(@Nullable UUID auteurActeurId) {
    this.auteurActeurId = auteurActeurId;
    return this;
  }

  /**
   * Get auteurActeurId
   * @return auteurActeurId
   */
  @Valid 
  @Schema(name = "auteurActeurId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auteurActeurId")
  public @Nullable UUID getAuteurActeurId() {
    return auteurActeurId;
  }

  @JsonProperty("auteurActeurId")
  public void setAuteurActeurId(@Nullable UUID auteurActeurId) {
    this.auteurActeurId = auteurActeurId;
  }

  public Contenu motsCles(@Nullable List<String> motsCles) {
    this.motsCles = motsCles;
    return this;
  }

  public Contenu addMotsClesItem(String motsClesItem) {
    if (this.motsCles == null) {
      this.motsCles = new ArrayList<>();
    }
    this.motsCles.add(motsClesItem);
    return this;
  }

  /**
   * Get motsCles
   * @return motsCles
   */
  
  @Schema(name = "motsCles", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("motsCles")
  public @Nullable List<String> getMotsCles() {
    return motsCles;
  }

  @JsonProperty("motsCles")
  public void setMotsCles(@Nullable List<String> motsCles) {
    this.motsCles = motsCles;
  }

  public Contenu statut(@Nullable StatutContenu statut) {
    this.statut = statut;
    return this;
  }

  /**
   * Get statut
   * @return statut
   */
  @Valid 
  @Schema(name = "statut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statut")
  public @Nullable StatutContenu getStatut() {
    return statut;
  }

  @JsonProperty("statut")
  public void setStatut(@Nullable StatutContenu statut) {
    this.statut = statut;
  }

  public Contenu datePublication(@Nullable OffsetDateTime datePublication) {
    this.datePublication = datePublication;
    return this;
  }

  /**
   * Get datePublication
   * @return datePublication
   */
  @Valid 
  @Schema(name = "datePublication", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("datePublication")
  public @Nullable OffsetDateTime getDatePublication() {
    return datePublication;
  }

  @JsonProperty("datePublication")
  public void setDatePublication(@Nullable OffsetDateTime datePublication) {
    this.datePublication = datePublication;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Contenu contenu = (Contenu) o;
    return Objects.equals(this.id, contenu.id) &&
        Objects.equals(this.createdAt, contenu.createdAt) &&
        Objects.equals(this.updatedAt, contenu.updatedAt) &&
        Objects.equals(this.patrimoineId, contenu.patrimoineId) &&
        Objects.equals(this.type, contenu.type) &&
        Objects.equals(this.langue, contenu.langue) &&
        Objects.equals(this.titre, contenu.titre) &&
        Objects.equals(this.resume, contenu.resume) &&
        Objects.equals(this.corps, contenu.corps) &&
        Objects.equals(this.auteurActeurId, contenu.auteurActeurId) &&
        Objects.equals(this.motsCles, contenu.motsCles) &&
        Objects.equals(this.statut, contenu.statut) &&
        Objects.equals(this.datePublication, contenu.datePublication);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, patrimoineId, type, langue, titre, resume, corps, auteurActeurId, motsCles, statut, datePublication);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contenu {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    patrimoineId: ").append(toIndentedString(patrimoineId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    langue: ").append(toIndentedString(langue)).append("\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    resume: ").append(toIndentedString(resume)).append("\n");
    sb.append("    corps: ").append(toIndentedString(corps)).append("\n");
    sb.append("    auteurActeurId: ").append(toIndentedString(auteurActeurId)).append("\n");
    sb.append("    motsCles: ").append(toIndentedString(motsCles)).append("\n");
    sb.append("    statut: ").append(toIndentedString(statut)).append("\n");
    sb.append("    datePublication: ").append(toIndentedString(datePublication)).append("\n");
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

