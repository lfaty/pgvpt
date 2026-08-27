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
import com.pgvpt.dto.CollectionMusee;
import com.pgvpt.dto.Conservation;
import com.pgvpt.dto.Contact;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.Exposition;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.dto.Media;
import com.pgvpt.dto.Menace;
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
 * MuseeCreate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MuseeCreate extends PatrimoineCreate {

  private Integer nombreCollections;

  @Valid
  private List<@Valid CollectionMusee> collections = new ArrayList<>();

  @Valid
  private List<@Valid Exposition> expositions = new ArrayList<>();

  public MuseeCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MuseeCreate(Integer nombreCollections, String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
    this.nombreCollections = nombreCollections;
  }

  public MuseeCreate nombreCollections(Integer nombreCollections) {
    this.nombreCollections = nombreCollections;
    return this;
  }

  /**
   * Get nombreCollections
   * minimum: 0
   * @return nombreCollections
   */
  @NotNull @Min(0) 
  @Schema(name = "nombreCollections", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombreCollections")
  public Integer getNombreCollections() {
    return nombreCollections;
  }

  public void setNombreCollections(Integer nombreCollections) {
    this.nombreCollections = nombreCollections;
  }

  public MuseeCreate collections(List<@Valid CollectionMusee> collections) {
    this.collections = collections;
    return this;
  }

  public MuseeCreate addCollectionsItem(CollectionMusee collectionsItem) {
    if (this.collections == null) {
      this.collections = new ArrayList<>();
    }
    this.collections.add(collectionsItem);
    return this;
  }

  /**
   * Get collections
   * @return collections
   */
  @Valid 
  @Schema(name = "collections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("collections")
  public List<@Valid CollectionMusee> getCollections() {
    return collections;
  }

  public void setCollections(List<@Valid CollectionMusee> collections) {
    this.collections = collections;
  }

  public MuseeCreate expositions(List<@Valid Exposition> expositions) {
    this.expositions = expositions;
    return this;
  }

  public MuseeCreate addExpositionsItem(Exposition expositionsItem) {
    if (this.expositions == null) {
      this.expositions = new ArrayList<>();
    }
    this.expositions.add(expositionsItem);
    return this;
  }

  /**
   * Get expositions
   * @return expositions
   */
  @Valid 
  @Schema(name = "expositions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expositions")
  public List<@Valid Exposition> getExpositions() {
    return expositions;
  }

  public void setExpositions(List<@Valid Exposition> expositions) {
    this.expositions = expositions;
  }


  public MuseeCreate id(UUID id) {
    super.id(id);
    return this;
  }

  public MuseeCreate code(String code) {
    super.code(code);
    return this;
  }

  public MuseeCreate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public MuseeCreate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public MuseeCreate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public MuseeCreate type(String type) {
    super.type(type);
    return this;
  }

  public MuseeCreate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public MuseeCreate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public MuseeCreate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public MuseeCreate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public MuseeCreate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public MuseeCreate description(String description) {
    super.description(description);
    return this;
  }

  public MuseeCreate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public MuseeCreate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public MuseeCreate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public MuseeCreate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public MuseeCreate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public MuseeCreate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public MuseeCreate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public MuseeCreate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public MuseeCreate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public MuseeCreate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public MuseeCreate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public MuseeCreate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public MuseeCreate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public MuseeCreate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public MuseeCreate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public MuseeCreate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public MuseeCreate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public MuseeCreate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public MuseeCreate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public MuseeCreate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public MuseeCreate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public MuseeCreate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public MuseeCreate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public MuseeCreate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public MuseeCreate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public MuseeCreate email(String email) {
    super.email(email);
    return this;
  }

  public MuseeCreate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public MuseeCreate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public MuseeCreate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public MuseeCreate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public MuseeCreate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public MuseeCreate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public MuseeCreate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public MuseeCreate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public MuseeCreate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public MuseeCreate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public MuseeCreate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public MuseeCreate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public MuseeCreate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public MuseeCreate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public MuseeCreate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public MuseeCreate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public MuseeCreate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public MuseeCreate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public MuseeCreate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public MuseeCreate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public MuseeCreate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public MuseeCreate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public MuseeCreate publishedAt(OffsetDateTime publishedAt) {
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
    MuseeCreate museeCreate = (MuseeCreate) o;
    return Objects.equals(this.nombreCollections, museeCreate.nombreCollections) &&
        Objects.equals(this.collections, museeCreate.collections) &&
        Objects.equals(this.expositions, museeCreate.expositions) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombreCollections, collections, expositions, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MuseeCreate {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nombreCollections: ").append(toIndentedString(nombreCollections)).append("\n");
    sb.append("    collections: ").append(toIndentedString(collections)).append("\n");
    sb.append("    expositions: ").append(toIndentedString(expositions)).append("\n");
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

