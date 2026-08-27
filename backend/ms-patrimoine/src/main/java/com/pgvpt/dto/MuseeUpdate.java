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
 * MuseeUpdate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MuseeUpdate extends PatrimoineUpdate {

  private Integer nombreCollections;

  @Valid
  private List<@Valid CollectionMusee> collections = new ArrayList<>();

  @Valid
  private List<@Valid Exposition> expositions = new ArrayList<>();

  public MuseeUpdate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MuseeUpdate(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public MuseeUpdate nombreCollections(Integer nombreCollections) {
    this.nombreCollections = nombreCollections;
    return this;
  }

  /**
   * Get nombreCollections
   * minimum: 0
   * @return nombreCollections
   */
  @Min(0) 
  @Schema(name = "nombreCollections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombreCollections")
  public Integer getNombreCollections() {
    return nombreCollections;
  }

  public void setNombreCollections(Integer nombreCollections) {
    this.nombreCollections = nombreCollections;
  }

  public MuseeUpdate collections(List<@Valid CollectionMusee> collections) {
    this.collections = collections;
    return this;
  }

  public MuseeUpdate addCollectionsItem(CollectionMusee collectionsItem) {
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

  public MuseeUpdate expositions(List<@Valid Exposition> expositions) {
    this.expositions = expositions;
    return this;
  }

  public MuseeUpdate addExpositionsItem(Exposition expositionsItem) {
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


  public MuseeUpdate id(UUID id) {
    super.id(id);
    return this;
  }

  public MuseeUpdate code(String code) {
    super.code(code);
    return this;
  }

  public MuseeUpdate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public MuseeUpdate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public MuseeUpdate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public MuseeUpdate type(String type) {
    super.type(type);
    return this;
  }

  public MuseeUpdate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public MuseeUpdate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public MuseeUpdate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public MuseeUpdate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public MuseeUpdate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public MuseeUpdate description(String description) {
    super.description(description);
    return this;
  }

  public MuseeUpdate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public MuseeUpdate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public MuseeUpdate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public MuseeUpdate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public MuseeUpdate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public MuseeUpdate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public MuseeUpdate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public MuseeUpdate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public MuseeUpdate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public MuseeUpdate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public MuseeUpdate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public MuseeUpdate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public MuseeUpdate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public MuseeUpdate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public MuseeUpdate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public MuseeUpdate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public MuseeUpdate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public MuseeUpdate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public MuseeUpdate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public MuseeUpdate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public MuseeUpdate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public MuseeUpdate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public MuseeUpdate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public MuseeUpdate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public MuseeUpdate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public MuseeUpdate email(String email) {
    super.email(email);
    return this;
  }

  public MuseeUpdate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public MuseeUpdate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public MuseeUpdate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public MuseeUpdate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public MuseeUpdate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public MuseeUpdate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public MuseeUpdate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public MuseeUpdate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public MuseeUpdate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public MuseeUpdate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public MuseeUpdate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public MuseeUpdate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public MuseeUpdate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public MuseeUpdate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public MuseeUpdate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public MuseeUpdate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public MuseeUpdate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public MuseeUpdate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public MuseeUpdate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public MuseeUpdate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public MuseeUpdate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public MuseeUpdate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public MuseeUpdate publishedAt(OffsetDateTime publishedAt) {
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
    MuseeUpdate museeUpdate = (MuseeUpdate) o;
    return Objects.equals(this.nombreCollections, museeUpdate.nombreCollections) &&
        Objects.equals(this.collections, museeUpdate.collections) &&
        Objects.equals(this.expositions, museeUpdate.expositions) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombreCollections, collections, expositions, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MuseeUpdate {\n");
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

