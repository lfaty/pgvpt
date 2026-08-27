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
import com.pgvpt.dto.PatrimoineCreate;
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
 * MonumentCreate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MonumentCreate extends PatrimoineCreate {

  private String styleArchitectural;

  private Integer anneeConstruction;

  private String identiteArchitecte;

  private NatureMonument natureMonument;

  public MonumentCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MonumentCreate(String styleArchitectural, NatureMonument natureMonument, String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
    this.styleArchitectural = styleArchitectural;
    this.natureMonument = natureMonument;
  }

  public MonumentCreate styleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
    return this;
  }

  /**
   * Get styleArchitectural
   * @return styleArchitectural
   */
  @NotNull 
  @Schema(name = "styleArchitectural", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("styleArchitectural")
  public String getStyleArchitectural() {
    return styleArchitectural;
  }

  public void setStyleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
  }

  public MonumentCreate anneeConstruction(Integer anneeConstruction) {
    this.anneeConstruction = anneeConstruction;
    return this;
  }

  /**
   * Get anneeConstruction
   * @return anneeConstruction
   */
  
  @Schema(name = "anneeConstruction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("anneeConstruction")
  public Integer getAnneeConstruction() {
    return anneeConstruction;
  }

  public void setAnneeConstruction(Integer anneeConstruction) {
    this.anneeConstruction = anneeConstruction;
  }

  public MonumentCreate identiteArchitecte(String identiteArchitecte) {
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

  public MonumentCreate natureMonument(NatureMonument natureMonument) {
    this.natureMonument = natureMonument;
    return this;
  }

  /**
   * Get natureMonument
   * @return natureMonument
   */
  @NotNull @Valid 
  @Schema(name = "natureMonument", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("natureMonument")
  public NatureMonument getNatureMonument() {
    return natureMonument;
  }

  public void setNatureMonument(NatureMonument natureMonument) {
    this.natureMonument = natureMonument;
  }


  public MonumentCreate id(UUID id) {
    super.id(id);
    return this;
  }

  public MonumentCreate code(String code) {
    super.code(code);
    return this;
  }

  public MonumentCreate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public MonumentCreate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public MonumentCreate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public MonumentCreate type(String type) {
    super.type(type);
    return this;
  }

  public MonumentCreate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public MonumentCreate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public MonumentCreate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public MonumentCreate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public MonumentCreate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public MonumentCreate description(String description) {
    super.description(description);
    return this;
  }

  public MonumentCreate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public MonumentCreate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public MonumentCreate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public MonumentCreate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public MonumentCreate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public MonumentCreate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public MonumentCreate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public MonumentCreate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public MonumentCreate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public MonumentCreate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public MonumentCreate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public MonumentCreate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public MonumentCreate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public MonumentCreate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public MonumentCreate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public MonumentCreate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public MonumentCreate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public MonumentCreate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public MonumentCreate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public MonumentCreate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public MonumentCreate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public MonumentCreate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public MonumentCreate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public MonumentCreate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public MonumentCreate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public MonumentCreate email(String email) {
    super.email(email);
    return this;
  }

  public MonumentCreate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public MonumentCreate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public MonumentCreate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public MonumentCreate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public MonumentCreate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public MonumentCreate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public MonumentCreate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public MonumentCreate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public MonumentCreate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public MonumentCreate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public MonumentCreate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public MonumentCreate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public MonumentCreate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public MonumentCreate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public MonumentCreate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public MonumentCreate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public MonumentCreate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public MonumentCreate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public MonumentCreate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public MonumentCreate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public MonumentCreate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public MonumentCreate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public MonumentCreate publishedAt(OffsetDateTime publishedAt) {
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
    MonumentCreate monumentCreate = (MonumentCreate) o;
    return Objects.equals(this.styleArchitectural, monumentCreate.styleArchitectural) &&
        Objects.equals(this.anneeConstruction, monumentCreate.anneeConstruction) &&
        Objects.equals(this.identiteArchitecte, monumentCreate.identiteArchitecte) &&
        Objects.equals(this.natureMonument, monumentCreate.natureMonument) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(styleArchitectural, anneeConstruction, identiteArchitecte, natureMonument, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonumentCreate {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    styleArchitectural: ").append(toIndentedString(styleArchitectural)).append("\n");
    sb.append("    anneeConstruction: ").append(toIndentedString(anneeConstruction)).append("\n");
    sb.append("    identiteArchitecte: ").append(toIndentedString(identiteArchitecte)).append("\n");
    sb.append("    natureMonument: ").append(toIndentedString(natureMonument)).append("\n");
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

