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
import com.pgvpt.dto.PatrimoineUpdate;
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
 * MonumentUpdate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MonumentUpdate extends PatrimoineUpdate {

  private String styleArchitectural;

  private Integer anneeConstruction;

  private String identiteArchitecte;

  private NatureMonument natureMonument;

  public MonumentUpdate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MonumentUpdate(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public MonumentUpdate styleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
    return this;
  }

  /**
   * Get styleArchitectural
   * @return styleArchitectural
   */
  
  @Schema(name = "styleArchitectural", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("styleArchitectural")
  public String getStyleArchitectural() {
    return styleArchitectural;
  }

  public void setStyleArchitectural(String styleArchitectural) {
    this.styleArchitectural = styleArchitectural;
  }

  public MonumentUpdate anneeConstruction(Integer anneeConstruction) {
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

  public MonumentUpdate identiteArchitecte(String identiteArchitecte) {
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

  public MonumentUpdate natureMonument(NatureMonument natureMonument) {
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


  public MonumentUpdate id(UUID id) {
    super.id(id);
    return this;
  }

  public MonumentUpdate code(String code) {
    super.code(code);
    return this;
  }

  public MonumentUpdate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public MonumentUpdate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public MonumentUpdate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public MonumentUpdate type(String type) {
    super.type(type);
    return this;
  }

  public MonumentUpdate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public MonumentUpdate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public MonumentUpdate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public MonumentUpdate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public MonumentUpdate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public MonumentUpdate description(String description) {
    super.description(description);
    return this;
  }

  public MonumentUpdate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public MonumentUpdate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public MonumentUpdate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public MonumentUpdate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public MonumentUpdate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public MonumentUpdate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public MonumentUpdate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public MonumentUpdate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public MonumentUpdate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public MonumentUpdate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public MonumentUpdate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public MonumentUpdate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public MonumentUpdate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public MonumentUpdate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public MonumentUpdate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public MonumentUpdate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public MonumentUpdate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public MonumentUpdate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public MonumentUpdate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public MonumentUpdate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public MonumentUpdate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public MonumentUpdate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public MonumentUpdate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public MonumentUpdate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public MonumentUpdate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public MonumentUpdate email(String email) {
    super.email(email);
    return this;
  }

  public MonumentUpdate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public MonumentUpdate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public MonumentUpdate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public MonumentUpdate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public MonumentUpdate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public MonumentUpdate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public MonumentUpdate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public MonumentUpdate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public MonumentUpdate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public MonumentUpdate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public MonumentUpdate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public MonumentUpdate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public MonumentUpdate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public MonumentUpdate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public MonumentUpdate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public MonumentUpdate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public MonumentUpdate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public MonumentUpdate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public MonumentUpdate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public MonumentUpdate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public MonumentUpdate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public MonumentUpdate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public MonumentUpdate publishedAt(OffsetDateTime publishedAt) {
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
    MonumentUpdate monumentUpdate = (MonumentUpdate) o;
    return Objects.equals(this.styleArchitectural, monumentUpdate.styleArchitectural) &&
        Objects.equals(this.anneeConstruction, monumentUpdate.anneeConstruction) &&
        Objects.equals(this.identiteArchitecte, monumentUpdate.identiteArchitecte) &&
        Objects.equals(this.natureMonument, monumentUpdate.natureMonument) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(styleArchitectural, anneeConstruction, identiteArchitecte, natureMonument, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonumentUpdate {\n");
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

