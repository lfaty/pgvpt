package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.dto.Accessibilite;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.Conservation;
import com.pgvpt.dto.Contact;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.dto.Media;
import com.pgvpt.dto.Menace;
import com.pgvpt.dto.NatureMonument;
import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PeriodeHistorique;
import com.pgvpt.dto.Photo;
import com.pgvpt.dto.StatutPatrimoine;
import com.pgvpt.dto.Tarification;
import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Monument
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Monument extends Patrimoine {

  private String styleArchitectural;

  private Integer anneeConstruction;

  private Integer anneeRenovation;

  private String architecte;

  private String identiteArchitecte;

  private NatureMonument natureMonument;

  @Valid
  private List<String> materiauxConstruction = new ArrayList<>();

  private String dimensions;

  private String commanditaire;

  private String contexteHistorique;

  @Valid
  private List<String> personnagesAssocies = new ArrayList<>();

  public Monument() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Monument(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public Monument styleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
    return this;
  }

  /**
   * Get styleArchitectural
   * @return styleArchitectural
   */
  
  @Schema(name = "styleArchitectural", example = "Architecture coloniale", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("styleArchitectural")
  public String getStyleArchitectural() {
    return styleArchitectural;
  }

  public void setStyleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
  }

  public Monument anneeConstruction(Integer anneeConstruction) {
    this.anneeConstruction = anneeConstruction;
    return this;
  }

  /**
   * Get anneeConstruction
   * minimum: 0
   * @return anneeConstruction
   */
  @Min(0) 
  @Schema(name = "anneeConstruction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("anneeConstruction")
  public Integer getAnneeConstruction() {
    return anneeConstruction;
  }

  public void setAnneeConstruction(Integer anneeConstruction) {
    this.anneeConstruction = anneeConstruction;
  }

  public Monument anneeRenovation(Integer anneeRenovation) {
    this.anneeRenovation = anneeRenovation;
    return this;
  }

  /**
   * Get anneeRenovation
   * minimum: 0
   * @return anneeRenovation
   */
  @Min(0) 
  @Schema(name = "anneeRenovation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("anneeRenovation")
  public Integer getAnneeRenovation() {
    return anneeRenovation;
  }

  public void setAnneeRenovation(Integer anneeRenovation) {
    this.anneeRenovation = anneeRenovation;
  }

  public Monument architecte(String architecte) {
    this.architecte = architecte;
    return this;
  }

  /**
   * Get architecte
   * @return architecte
   */
  
  @Schema(name = "architecte", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("architecte")
  public String getArchitecte() {
    return architecte;
  }

  public void setArchitecte(String architecte) {
    this.architecte = architecte;
  }

  public Monument identiteArchitecte(String identiteArchitecte) {
    this.identiteArchitecte = identiteArchitecte;
    return this;
  }

  /**
   * Get identiteArchitecte
   * @return identiteArchitecte
   */
  
  @Schema(name = "identiteArchitecte", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identiteArchitecte")
  public String getIdentiteArchitecte() {
    return identiteArchitecte;
  }

  public void setIdentiteArchitecte(String identiteArchitecte) {
    this.identiteArchitecte = identiteArchitecte;
  }

  public Monument natureMonument(NatureMonument natureMonument) {
    this.natureMonument = natureMonument;
    return this;
  }

  /**
   * Get natureMonument
   * @return natureMonument
   */
  @Valid 
  @Schema(name = "natureMonument", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("natureMonument")
  public NatureMonument getNatureMonument() {
    return natureMonument;
  }

  public void setNatureMonument(NatureMonument natureMonument) {
    this.natureMonument = natureMonument;
  }

  public Monument materiauxConstruction(List<String> materiauxConstruction) {
    this.materiauxConstruction = materiauxConstruction;
    return this;
  }

  public Monument addMateriauxConstructionItem(String materiauxConstructionItem) {
    if (this.materiauxConstruction == null) {
      this.materiauxConstruction = new ArrayList<>();
    }
    this.materiauxConstruction.add(materiauxConstructionItem);
    return this;
  }

  /**
   * Get materiauxConstruction
   * @return materiauxConstruction
   */
  
  @Schema(name = "materiauxConstruction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("materiauxConstruction")
  public List<String> getMateriauxConstruction() {
    return materiauxConstruction;
  }

  public void setMateriauxConstruction(List<String> materiauxConstruction) {
    this.materiauxConstruction = materiauxConstruction;
  }

  public Monument dimensions(String dimensions) {
    this.dimensions = dimensions;
    return this;
  }

  /**
   * Get dimensions
   * @return dimensions
   */
  
  @Schema(name = "dimensions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dimensions")
  public String getDimensions() {
    return dimensions;
  }

  public void setDimensions(String dimensions) {
    this.dimensions = dimensions;
  }

  public Monument commanditaire(String commanditaire) {
    this.commanditaire = commanditaire;
    return this;
  }

  /**
   * Get commanditaire
   * @return commanditaire
   */
  
  @Schema(name = "commanditaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commanditaire")
  public String getCommanditaire() {
    return commanditaire;
  }

  public void setCommanditaire(String commanditaire) {
    this.commanditaire = commanditaire;
  }

  public Monument contexteHistorique(String contexteHistorique) {
    this.contexteHistorique = contexteHistorique;
    return this;
  }

  /**
   * Get contexteHistorique
   * @return contexteHistorique
   */
  
  @Schema(name = "contexteHistorique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contexteHistorique")
  public String getContexteHistorique() {
    return contexteHistorique;
  }

  public void setContexteHistorique(String contexteHistorique) {
    this.contexteHistorique = contexteHistorique;
  }

  public Monument personnagesAssocies(List<String> personnagesAssocies) {
    this.personnagesAssocies = personnagesAssocies;
    return this;
  }

  public Monument addPersonnagesAssociesItem(String personnagesAssociesItem) {
    if (this.personnagesAssocies == null) {
      this.personnagesAssocies = new ArrayList<>();
    }
    this.personnagesAssocies.add(personnagesAssociesItem);
    return this;
  }

  /**
   * Get personnagesAssocies
   * @return personnagesAssocies
   */
  
  @Schema(name = "personnagesAssocies", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("personnagesAssocies")
  public List<String> getPersonnagesAssocies() {
    return personnagesAssocies;
  }

  public void setPersonnagesAssocies(List<String> personnagesAssocies) {
    this.personnagesAssocies = personnagesAssocies;
  }


  public Monument id(UUID id) {
    super.id(id);
    return this;
  }

  public Monument code(String code) {
    super.code(code);
    return this;
  }

  public Monument nom(String nom) {
    super.nom(nom);
    return this;
  }

  public Monument nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public Monument nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public Monument type(String type) {
    super.type(type);
    return this;
  }

  public Monument categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public Monument sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public Monument periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public Monument siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public Monument dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public Monument description(String description) {
    super.description(description);
    return this;
  }

  public Monument descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public Monument historique(String historique) {
    super.historique(historique);
    return this;
  }

  public Monument importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public Monument importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public Monument importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public Monument valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public Monument valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public Monument traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public Monument addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public Monument langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public Monument addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public Monument etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public Monument statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public Monument classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public Monument referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public Monument dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public Monument inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public Monument nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public Monument dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public Monument protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public Monument organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public Monument proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public Monument gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public Monument contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public Monument siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public Monument email(String email) {
    super.email(email);
    return this;
  }

  public Monument telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public Monument accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public Monument tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public Monument horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public Monument addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public Monument photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public Monument addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public Monument medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public Monument addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public Monument conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public Monument menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public Monument addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public Monument recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public Monument addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public Monument reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public Monument addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public Monument activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public Monument addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public Monument meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public Monument dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public Monument createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public Monument updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public Monument publishedAt(OffsetDateTime publishedAt) {
    super.publishedAt(publishedAt);
    return this;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Monument monument = (Monument) o;
    return Objects.equals(this.styleArchitectural, monument.styleArchitectural) &&
        Objects.equals(this.anneeConstruction, monument.anneeConstruction) &&
        Objects.equals(this.anneeRenovation, monument.anneeRenovation) &&
        Objects.equals(this.architecte, monument.architecte) &&
        Objects.equals(this.identiteArchitecte, monument.identiteArchitecte) &&
        Objects.equals(this.natureMonument, monument.natureMonument) &&
        Objects.equals(this.materiauxConstruction, monument.materiauxConstruction) &&
        Objects.equals(this.dimensions, monument.dimensions) &&
        Objects.equals(this.commanditaire, monument.commanditaire) &&
        Objects.equals(this.contexteHistorique, monument.contexteHistorique) &&
        Objects.equals(this.personnagesAssocies, monument.personnagesAssocies) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(styleArchitectural, anneeConstruction, anneeRenovation, architecte, identiteArchitecte, natureMonument, materiauxConstruction, dimensions, commanditaire, contexteHistorique, personnagesAssocies, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Monument {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    styleArchitectural: ").append(toIndentedString(styleArchitectural)).append("\n");
    sb.append("    anneeConstruction: ").append(toIndentedString(anneeConstruction)).append("\n");
    sb.append("    anneeRenovation: ").append(toIndentedString(anneeRenovation)).append("\n");
    sb.append("    architecte: ").append(toIndentedString(architecte)).append("\n");
    sb.append("    identiteArchitecte: ").append(toIndentedString(identiteArchitecte)).append("\n");
    sb.append("    natureMonument: ").append(toIndentedString(natureMonument)).append("\n");
    sb.append("    materiauxConstruction: ").append(toIndentedString(materiauxConstruction)).append("\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    commanditaire: ").append(toIndentedString(commanditaire)).append("\n");
    sb.append("    contexteHistorique: ").append(toIndentedString(contexteHistorique)).append("\n");
    sb.append("    personnagesAssocies: ").append(toIndentedString(personnagesAssocies)).append("\n");
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

