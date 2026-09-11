package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.TypeContenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ContenuCreate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-11T17:00:04.820143400Z[Atlantic/Reykjavik]", comments = "Generator version: 7.25.0")
public class ContenuCreate {

  private UUID patrimoineId;

  private TypeContenu type;

  private String langue;

  private String titre;

  private @Nullable String resume;

  private String corps;

  private @Nullable UUID auteurActeurId;

  private @Nullable List<String> motsCles;

  public ContenuCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ContenuCreate(UUID patrimoineId, TypeContenu type, String langue, String titre, String corps) {
    this.patrimoineId = patrimoineId;
    this.type = type;
    this.langue = langue;
    this.titre = titre;
    this.corps = corps;
  }

  public ContenuCreate patrimoineId(UUID patrimoineId) {
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

  public ContenuCreate type(TypeContenu type) {
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

  public ContenuCreate langue(String langue) {
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

  public ContenuCreate titre(String titre) {
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

  public ContenuCreate resume(@Nullable String resume) {
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

  public ContenuCreate corps(String corps) {
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

  public ContenuCreate auteurActeurId(@Nullable UUID auteurActeurId) {
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

  public ContenuCreate motsCles(@Nullable List<String> motsCles) {
    this.motsCles = motsCles;
    return this;
  }

  public ContenuCreate addMotsClesItem(String motsClesItem) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContenuCreate contenuCreate = (ContenuCreate) o;
    return Objects.equals(this.patrimoineId, contenuCreate.patrimoineId) &&
        Objects.equals(this.type, contenuCreate.type) &&
        Objects.equals(this.langue, contenuCreate.langue) &&
        Objects.equals(this.titre, contenuCreate.titre) &&
        Objects.equals(this.resume, contenuCreate.resume) &&
        Objects.equals(this.corps, contenuCreate.corps) &&
        Objects.equals(this.auteurActeurId, contenuCreate.auteurActeurId) &&
        Objects.equals(this.motsCles, contenuCreate.motsCles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patrimoineId, type, langue, titre, resume, corps, auteurActeurId, motsCles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContenuCreate {\n");
    sb.append("    patrimoineId: ").append(toIndentedString(patrimoineId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    langue: ").append(toIndentedString(langue)).append("\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    resume: ").append(toIndentedString(resume)).append("\n");
    sb.append("    corps: ").append(toIndentedString(corps)).append("\n");
    sb.append("    auteurActeurId: ").append(toIndentedString(auteurActeurId)).append("\n");
    sb.append("    motsCles: ").append(toIndentedString(motsCles)).append("\n");
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

