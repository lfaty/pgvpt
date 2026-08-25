package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import java.time.LocalDate;
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
 * PhotoCreate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class PhotoCreate {

  private URI url;

  private URI miniatureUrl;

  private String titre;

  private String description;

  private String auteur;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate datePrise;

  private String droits;

  private String licence;

  private Boolean photoPrincipale;

  private Integer ordre;

  public PhotoCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PhotoCreate(URI url) {
    this.url = url;
  }

  public PhotoCreate url(URI url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   */
  @NotNull @Valid 
  @Schema(name = "url", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("url")
  public URI getUrl() {
    return url;
  }

  public void setUrl(URI url) {
    this.url = url;
  }

  public PhotoCreate miniatureUrl(URI miniatureUrl) {
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

  public PhotoCreate titre(String titre) {
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

  public PhotoCreate description(String description) {
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

  public PhotoCreate auteur(String auteur) {
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

  public PhotoCreate datePrise(LocalDate datePrise) {
    this.datePrise = datePrise;
    return this;
  }

  /**
   * Get datePrise
   * @return datePrise
   */
  @Valid 
  @Schema(name = "datePrise", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("datePrise")
  public LocalDate getDatePrise() {
    return datePrise;
  }

  public void setDatePrise(LocalDate datePrise) {
    this.datePrise = datePrise;
  }

  public PhotoCreate droits(String droits) {
    this.droits = droits;
    return this;
  }

  /**
   * Get droits
   * @return droits
   */
  
  @Schema(name = "droits", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("droits")
  public String getDroits() {
    return droits;
  }

  public void setDroits(String droits) {
    this.droits = droits;
  }

  public PhotoCreate licence(String licence) {
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

  public PhotoCreate photoPrincipale(Boolean photoPrincipale) {
    this.photoPrincipale = photoPrincipale;
    return this;
  }

  /**
   * Get photoPrincipale
   * @return photoPrincipale
   */
  
  @Schema(name = "photoPrincipale", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photoPrincipale")
  public Boolean getPhotoPrincipale() {
    return photoPrincipale;
  }

  public void setPhotoPrincipale(Boolean photoPrincipale) {
    this.photoPrincipale = photoPrincipale;
  }

  public PhotoCreate ordre(Integer ordre) {
    this.ordre = ordre;
    return this;
  }

  /**
   * Get ordre
   * @return ordre
   */
  
  @Schema(name = "ordre", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ordre")
  public Integer getOrdre() {
    return ordre;
  }

  public void setOrdre(Integer ordre) {
    this.ordre = ordre;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhotoCreate photoCreate = (PhotoCreate) o;
    return Objects.equals(this.url, photoCreate.url) &&
        Objects.equals(this.miniatureUrl, photoCreate.miniatureUrl) &&
        Objects.equals(this.titre, photoCreate.titre) &&
        Objects.equals(this.description, photoCreate.description) &&
        Objects.equals(this.auteur, photoCreate.auteur) &&
        Objects.equals(this.datePrise, photoCreate.datePrise) &&
        Objects.equals(this.droits, photoCreate.droits) &&
        Objects.equals(this.licence, photoCreate.licence) &&
        Objects.equals(this.photoPrincipale, photoCreate.photoPrincipale) &&
        Objects.equals(this.ordre, photoCreate.ordre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, miniatureUrl, titre, description, auteur, datePrise, droits, licence, photoPrincipale, ordre);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PhotoCreate {\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    miniatureUrl: ").append(toIndentedString(miniatureUrl)).append("\n");
    sb.append("    titre: ").append(toIndentedString(titre)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    auteur: ").append(toIndentedString(auteur)).append("\n");
    sb.append("    datePrise: ").append(toIndentedString(datePrise)).append("\n");
    sb.append("    droits: ").append(toIndentedString(droits)).append("\n");
    sb.append("    licence: ").append(toIndentedString(licence)).append("\n");
    sb.append("    photoPrincipale: ").append(toIndentedString(photoPrincipale)).append("\n");
    sb.append("    ordre: ").append(toIndentedString(ordre)).append("\n");
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

