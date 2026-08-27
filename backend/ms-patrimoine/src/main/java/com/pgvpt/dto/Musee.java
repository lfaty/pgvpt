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
 * Musee
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Musee extends Patrimoine {

  private Integer nombreCollections;

  private Integer nombreOeuvres;

  @Valid
  private List<@Valid CollectionMusee> collections = new ArrayList<>();

  @Valid
  private List<@Valid Exposition> expositions = new ArrayList<>();

  @Valid
  private List<String> typesCollections = new ArrayList<>();

  @Valid
  private List<String> servicesMusee = new ArrayList<>();

  private Integer capaciteAccueil;

  private String museographie;

  public Musee() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Musee(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public Musee nombreCollections(Integer nombreCollections) {
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

  public Musee nombreOeuvres(Integer nombreOeuvres) {
    this.nombreOeuvres = nombreOeuvres;
    return this;
  }

  /**
   * Get nombreOeuvres
   * minimum: 0
   * @return nombreOeuvres
   */
  @Min(0) 
  @Schema(name = "nombreOeuvres", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombreOeuvres")
  public Integer getNombreOeuvres() {
    return nombreOeuvres;
  }

  public void setNombreOeuvres(Integer nombreOeuvres) {
    this.nombreOeuvres = nombreOeuvres;
  }

  public Musee collections(List<@Valid CollectionMusee> collections) {
    this.collections = collections;
    return this;
  }

  public Musee addCollectionsItem(CollectionMusee collectionsItem) {
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

  public Musee expositions(List<@Valid Exposition> expositions) {
    this.expositions = expositions;
    return this;
  }

  public Musee addExpositionsItem(Exposition expositionsItem) {
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

  public Musee typesCollections(List<String> typesCollections) {
    this.typesCollections = typesCollections;
    return this;
  }

  public Musee addTypesCollectionsItem(String typesCollectionsItem) {
    if (this.typesCollections == null) {
      this.typesCollections = new ArrayList<>();
    }
    this.typesCollections.add(typesCollectionsItem);
    return this;
  }

  /**
   * Get typesCollections
   * @return typesCollections
   */
  
  @Schema(name = "typesCollections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("typesCollections")
  public List<String> getTypesCollections() {
    return typesCollections;
  }

  public void setTypesCollections(List<String> typesCollections) {
    this.typesCollections = typesCollections;
  }

  public Musee servicesMusee(List<String> servicesMusee) {
    this.servicesMusee = servicesMusee;
    return this;
  }

  public Musee addServicesMuseeItem(String servicesMuseeItem) {
    if (this.servicesMusee == null) {
      this.servicesMusee = new ArrayList<>();
    }
    this.servicesMusee.add(servicesMuseeItem);
    return this;
  }

  /**
   * Get servicesMusee
   * @return servicesMusee
   */
  
  @Schema(name = "servicesMusee", example = "[\"Visite guidée\",\"Boutique\",\"Cafétéria\",\"Centre de documentation\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("servicesMusee")
  public List<String> getServicesMusee() {
    return servicesMusee;
  }

  public void setServicesMusee(List<String> servicesMusee) {
    this.servicesMusee = servicesMusee;
  }

  public Musee capaciteAccueil(Integer capaciteAccueil) {
    this.capaciteAccueil = capaciteAccueil;
    return this;
  }

  /**
   * Get capaciteAccueil
   * minimum: 0
   * @return capaciteAccueil
   */
  @Min(0) 
  @Schema(name = "capaciteAccueil", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("capaciteAccueil")
  public Integer getCapaciteAccueil() {
    return capaciteAccueil;
  }

  public void setCapaciteAccueil(Integer capaciteAccueil) {
    this.capaciteAccueil = capaciteAccueil;
  }

  public Musee museographie(String museographie) {
    this.museographie = museographie;
    return this;
  }

  /**
   * Get museographie
   * @return museographie
   */
  
  @Schema(name = "museographie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("museographie")
  public String getMuseographie() {
    return museographie;
  }

  public void setMuseographie(String museographie) {
    this.museographie = museographie;
  }


  public Musee id(UUID id) {
    super.id(id);
    return this;
  }

  public Musee code(String code) {
    super.code(code);
    return this;
  }

  public Musee nom(String nom) {
    super.nom(nom);
    return this;
  }

  public Musee nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public Musee nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public Musee type(String type) {
    super.type(type);
    return this;
  }

  public Musee categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public Musee sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public Musee periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public Musee siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public Musee dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public Musee description(String description) {
    super.description(description);
    return this;
  }

  public Musee descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public Musee historique(String historique) {
    super.historique(historique);
    return this;
  }

  public Musee importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public Musee importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public Musee importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public Musee valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public Musee valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public Musee traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public Musee addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public Musee langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public Musee addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public Musee etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public Musee statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public Musee classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public Musee referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public Musee dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public Musee inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public Musee nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public Musee dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public Musee protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public Musee organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public Musee proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public Musee gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public Musee contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public Musee siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public Musee email(String email) {
    super.email(email);
    return this;
  }

  public Musee telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public Musee accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public Musee tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public Musee horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public Musee addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public Musee photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public Musee addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public Musee medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public Musee addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public Musee conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public Musee menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public Musee addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public Musee recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public Musee addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public Musee reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public Musee addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public Musee activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public Musee addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public Musee meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public Musee dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public Musee createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public Musee updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public Musee publishedAt(OffsetDateTime publishedAt) {
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
    Musee musee = (Musee) o;
    return Objects.equals(this.nombreCollections, musee.nombreCollections) &&
        Objects.equals(this.nombreOeuvres, musee.nombreOeuvres) &&
        Objects.equals(this.collections, musee.collections) &&
        Objects.equals(this.expositions, musee.expositions) &&
        Objects.equals(this.typesCollections, musee.typesCollections) &&
        Objects.equals(this.servicesMusee, musee.servicesMusee) &&
        Objects.equals(this.capaciteAccueil, musee.capaciteAccueil) &&
        Objects.equals(this.museographie, musee.museographie) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombreCollections, nombreOeuvres, collections, expositions, typesCollections, servicesMusee, capaciteAccueil, museographie, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Musee {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nombreCollections: ").append(toIndentedString(nombreCollections)).append("\n");
    sb.append("    nombreOeuvres: ").append(toIndentedString(nombreOeuvres)).append("\n");
    sb.append("    collections: ").append(toIndentedString(collections)).append("\n");
    sb.append("    expositions: ").append(toIndentedString(expositions)).append("\n");
    sb.append("    typesCollections: ").append(toIndentedString(typesCollections)).append("\n");
    sb.append("    servicesMusee: ").append(toIndentedString(servicesMusee)).append("\n");
    sb.append("    capaciteAccueil: ").append(toIndentedString(capaciteAccueil)).append("\n");
    sb.append("    museographie: ").append(toIndentedString(museographie)).append("\n");
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

