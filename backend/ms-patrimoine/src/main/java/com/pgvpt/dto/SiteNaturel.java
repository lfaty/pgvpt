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
import com.pgvpt.dto.EspeceProtegee;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.dto.Media;
import com.pgvpt.dto.Menace;
import com.pgvpt.dto.NatureSiteNaturel;
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
 * SiteNaturel
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SiteNaturel extends Patrimoine {

  private Double superficie;

  private NatureSiteNaturel natureSite;

  private String ecosysteme;

  @Valid
  private List<@Valid EspeceProtegee> especesProtegees = new ArrayList<>();

  private String biodiversite;

  private Boolean zoneProtegee;

  private String categorieProtection;

  @Valid
  private List<String> risquesEnvironnementaux = new ArrayList<>();

  @Valid
  private List<String> ressourcesNaturelles = new ArrayList<>();

  @Valid
  private List<String> activitesEcotouristiques = new ArrayList<>();

  private Integer capaciteAccueil;

  public SiteNaturel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SiteNaturel(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public SiteNaturel superficie(Double superficie) {
    this.superficie = superficie;
    return this;
  }

  /**
   * Superficie en hectares
   * minimum: 0
   * @return superficie
   */
  @DecimalMin("0") 
  @Schema(name = "superficie", description = "Superficie en hectares", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("superficie")
  public Double getSuperficie() {
    return superficie;
  }

  public void setSuperficie(Double superficie) {
    this.superficie = superficie;
  }

  public SiteNaturel natureSite(NatureSiteNaturel natureSite) {
    this.natureSite = natureSite;
    return this;
  }

  /**
   * Get natureSite
   * @return natureSite
   */
  @Valid 
  @Schema(name = "natureSite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("natureSite")
  public NatureSiteNaturel getNatureSite() {
    return natureSite;
  }

  public void setNatureSite(NatureSiteNaturel natureSite) {
    this.natureSite = natureSite;
  }

  public SiteNaturel ecosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
    return this;
  }

  /**
   * Get ecosysteme
   * @return ecosysteme
   */
  
  @Schema(name = "ecosysteme", example = "Écosystème de mangrove", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ecosysteme")
  public String getEcosysteme() {
    return ecosysteme;
  }

  public void setEcosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
  }

  public SiteNaturel especesProtegees(List<@Valid EspeceProtegee> especesProtegees) {
    this.especesProtegees = especesProtegees;
    return this;
  }

  public SiteNaturel addEspecesProtegeesItem(EspeceProtegee especesProtegeesItem) {
    if (this.especesProtegees == null) {
      this.especesProtegees = new ArrayList<>();
    }
    this.especesProtegees.add(especesProtegeesItem);
    return this;
  }

  /**
   * Get especesProtegees
   * @return especesProtegees
   */
  @Valid 
  @Schema(name = "especesProtegees", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("especesProtegees")
  public List<@Valid EspeceProtegee> getEspecesProtegees() {
    return especesProtegees;
  }

  public void setEspecesProtegees(List<@Valid EspeceProtegee> especesProtegees) {
    this.especesProtegees = especesProtegees;
  }

  public SiteNaturel biodiversite(String biodiversite) {
    this.biodiversite = biodiversite;
    return this;
  }

  /**
   * Get biodiversite
   * @return biodiversite
   */
  
  @Schema(name = "biodiversite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("biodiversite")
  public String getBiodiversite() {
    return biodiversite;
  }

  public void setBiodiversite(String biodiversite) {
    this.biodiversite = biodiversite;
  }

  public SiteNaturel zoneProtegee(Boolean zoneProtegee) {
    this.zoneProtegee = zoneProtegee;
    return this;
  }

  /**
   * Get zoneProtegee
   * @return zoneProtegee
   */
  
  @Schema(name = "zoneProtegee", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("zoneProtegee")
  public Boolean getZoneProtegee() {
    return zoneProtegee;
  }

  public void setZoneProtegee(Boolean zoneProtegee) {
    this.zoneProtegee = zoneProtegee;
  }

  public SiteNaturel categorieProtection(String categorieProtection) {
    this.categorieProtection = categorieProtection;
    return this;
  }

  /**
   * Get categorieProtection
   * @return categorieProtection
   */
  
  @Schema(name = "categorieProtection", example = "Parc national", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categorieProtection")
  public String getCategorieProtection() {
    return categorieProtection;
  }

  public void setCategorieProtection(String categorieProtection) {
    this.categorieProtection = categorieProtection;
  }

  public SiteNaturel risquesEnvironnementaux(List<String> risquesEnvironnementaux) {
    this.risquesEnvironnementaux = risquesEnvironnementaux;
    return this;
  }

  public SiteNaturel addRisquesEnvironnementauxItem(String risquesEnvironnementauxItem) {
    if (this.risquesEnvironnementaux == null) {
      this.risquesEnvironnementaux = new ArrayList<>();
    }
    this.risquesEnvironnementaux.add(risquesEnvironnementauxItem);
    return this;
  }

  /**
   * Get risquesEnvironnementaux
   * @return risquesEnvironnementaux
   */
  
  @Schema(name = "risquesEnvironnementaux", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("risquesEnvironnementaux")
  public List<String> getRisquesEnvironnementaux() {
    return risquesEnvironnementaux;
  }

  public void setRisquesEnvironnementaux(List<String> risquesEnvironnementaux) {
    this.risquesEnvironnementaux = risquesEnvironnementaux;
  }

  public SiteNaturel ressourcesNaturelles(List<String> ressourcesNaturelles) {
    this.ressourcesNaturelles = ressourcesNaturelles;
    return this;
  }

  public SiteNaturel addRessourcesNaturellesItem(String ressourcesNaturellesItem) {
    if (this.ressourcesNaturelles == null) {
      this.ressourcesNaturelles = new ArrayList<>();
    }
    this.ressourcesNaturelles.add(ressourcesNaturellesItem);
    return this;
  }

  /**
   * Get ressourcesNaturelles
   * @return ressourcesNaturelles
   */
  
  @Schema(name = "ressourcesNaturelles", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ressourcesNaturelles")
  public List<String> getRessourcesNaturelles() {
    return ressourcesNaturelles;
  }

  public void setRessourcesNaturelles(List<String> ressourcesNaturelles) {
    this.ressourcesNaturelles = ressourcesNaturelles;
  }

  public SiteNaturel activitesEcotouristiques(List<String> activitesEcotouristiques) {
    this.activitesEcotouristiques = activitesEcotouristiques;
    return this;
  }

  public SiteNaturel addActivitesEcotouristiquesItem(String activitesEcotouristiquesItem) {
    if (this.activitesEcotouristiques == null) {
      this.activitesEcotouristiques = new ArrayList<>();
    }
    this.activitesEcotouristiques.add(activitesEcotouristiquesItem);
    return this;
  }

  /**
   * Get activitesEcotouristiques
   * @return activitesEcotouristiques
   */
  
  @Schema(name = "activitesEcotouristiques", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("activitesEcotouristiques")
  public List<String> getActivitesEcotouristiques() {
    return activitesEcotouristiques;
  }

  public void setActivitesEcotouristiques(List<String> activitesEcotouristiques) {
    this.activitesEcotouristiques = activitesEcotouristiques;
  }

  public SiteNaturel capaciteAccueil(Integer capaciteAccueil) {
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


  public SiteNaturel id(UUID id) {
    super.id(id);
    return this;
  }

  public SiteNaturel code(String code) {
    super.code(code);
    return this;
  }

  public SiteNaturel nom(String nom) {
    super.nom(nom);
    return this;
  }

  public SiteNaturel nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public SiteNaturel nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public SiteNaturel type(String type) {
    super.type(type);
    return this;
  }

  public SiteNaturel categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public SiteNaturel sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public SiteNaturel periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public SiteNaturel siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public SiteNaturel dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public SiteNaturel description(String description) {
    super.description(description);
    return this;
  }

  public SiteNaturel descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public SiteNaturel historique(String historique) {
    super.historique(historique);
    return this;
  }

  public SiteNaturel importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public SiteNaturel importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public SiteNaturel importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public SiteNaturel valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public SiteNaturel valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public SiteNaturel traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public SiteNaturel addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public SiteNaturel langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public SiteNaturel addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public SiteNaturel etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public SiteNaturel statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public SiteNaturel classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public SiteNaturel referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public SiteNaturel dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public SiteNaturel inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public SiteNaturel nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public SiteNaturel dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public SiteNaturel protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public SiteNaturel organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public SiteNaturel proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public SiteNaturel gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public SiteNaturel contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public SiteNaturel siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public SiteNaturel email(String email) {
    super.email(email);
    return this;
  }

  public SiteNaturel telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public SiteNaturel accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public SiteNaturel tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public SiteNaturel horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public SiteNaturel addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public SiteNaturel photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public SiteNaturel addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public SiteNaturel medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public SiteNaturel addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public SiteNaturel conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public SiteNaturel menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public SiteNaturel addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public SiteNaturel recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public SiteNaturel addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public SiteNaturel reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public SiteNaturel addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public SiteNaturel activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public SiteNaturel addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public SiteNaturel meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public SiteNaturel dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public SiteNaturel createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public SiteNaturel updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public SiteNaturel publishedAt(OffsetDateTime publishedAt) {
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
    SiteNaturel siteNaturel = (SiteNaturel) o;
    return Objects.equals(this.superficie, siteNaturel.superficie) &&
        Objects.equals(this.natureSite, siteNaturel.natureSite) &&
        Objects.equals(this.ecosysteme, siteNaturel.ecosysteme) &&
        Objects.equals(this.especesProtegees, siteNaturel.especesProtegees) &&
        Objects.equals(this.biodiversite, siteNaturel.biodiversite) &&
        Objects.equals(this.zoneProtegee, siteNaturel.zoneProtegee) &&
        Objects.equals(this.categorieProtection, siteNaturel.categorieProtection) &&
        Objects.equals(this.risquesEnvironnementaux, siteNaturel.risquesEnvironnementaux) &&
        Objects.equals(this.ressourcesNaturelles, siteNaturel.ressourcesNaturelles) &&
        Objects.equals(this.activitesEcotouristiques, siteNaturel.activitesEcotouristiques) &&
        Objects.equals(this.capaciteAccueil, siteNaturel.capaciteAccueil) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(superficie, natureSite, ecosysteme, especesProtegees, biodiversite, zoneProtegee, categorieProtection, risquesEnvironnementaux, ressourcesNaturelles, activitesEcotouristiques, capaciteAccueil, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SiteNaturel {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    superficie: ").append(toIndentedString(superficie)).append("\n");
    sb.append("    natureSite: ").append(toIndentedString(natureSite)).append("\n");
    sb.append("    ecosysteme: ").append(toIndentedString(ecosysteme)).append("\n");
    sb.append("    especesProtegees: ").append(toIndentedString(especesProtegees)).append("\n");
    sb.append("    biodiversite: ").append(toIndentedString(biodiversite)).append("\n");
    sb.append("    zoneProtegee: ").append(toIndentedString(zoneProtegee)).append("\n");
    sb.append("    categorieProtection: ").append(toIndentedString(categorieProtection)).append("\n");
    sb.append("    risquesEnvironnementaux: ").append(toIndentedString(risquesEnvironnementaux)).append("\n");
    sb.append("    ressourcesNaturelles: ").append(toIndentedString(ressourcesNaturelles)).append("\n");
    sb.append("    activitesEcotouristiques: ").append(toIndentedString(activitesEcotouristiques)).append("\n");
    sb.append("    capaciteAccueil: ").append(toIndentedString(capaciteAccueil)).append("\n");
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

