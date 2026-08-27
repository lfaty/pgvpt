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
 * SiteNaturelCreate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SiteNaturelCreate extends PatrimoineCreate {

  private Double superficie;

  private NatureSiteNaturel natureSite;

  private String ecosysteme;

  @Valid
  private List<@Valid EspeceProtegee> especesProtegees = new ArrayList<>();

  public SiteNaturelCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SiteNaturelCreate(Double superficie, NatureSiteNaturel natureSite, String ecosysteme, String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
    this.superficie = superficie;
    this.natureSite = natureSite;
    this.ecosysteme = ecosysteme;
  }

  public SiteNaturelCreate superficie(Double superficie) {
    this.superficie = superficie;
    return this;
  }

  /**
   * Get superficie
   * @return superficie
   */
  @NotNull 
  @Schema(name = "superficie", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("superficie")
  public Double getSuperficie() {
    return superficie;
  }

  public void setSuperficie(Double superficie) {
    this.superficie = superficie;
  }

  public SiteNaturelCreate natureSite(NatureSiteNaturel natureSite) {
    this.natureSite = natureSite;
    return this;
  }

  /**
   * Get natureSite
   * @return natureSite
   */
  @NotNull @Valid 
  @Schema(name = "natureSite", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("natureSite")
  public NatureSiteNaturel getNatureSite() {
    return natureSite;
  }

  public void setNatureSite(NatureSiteNaturel natureSite) {
    this.natureSite = natureSite;
  }

  public SiteNaturelCreate ecosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
    return this;
  }

  /**
   * Get ecosysteme
   * @return ecosysteme
   */
  @NotNull 
  @Schema(name = "ecosysteme", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ecosysteme")
  public String getEcosysteme() {
    return ecosysteme;
  }

  public void setEcosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
  }

  public SiteNaturelCreate especesProtegees(List<@Valid EspeceProtegee> especesProtegees) {
    this.especesProtegees = especesProtegees;
    return this;
  }

  public SiteNaturelCreate addEspecesProtegeesItem(EspeceProtegee especesProtegeesItem) {
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


  public SiteNaturelCreate id(UUID id) {
    super.id(id);
    return this;
  }

  public SiteNaturelCreate code(String code) {
    super.code(code);
    return this;
  }

  public SiteNaturelCreate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public SiteNaturelCreate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public SiteNaturelCreate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public SiteNaturelCreate type(String type) {
    super.type(type);
    return this;
  }

  public SiteNaturelCreate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public SiteNaturelCreate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public SiteNaturelCreate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public SiteNaturelCreate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public SiteNaturelCreate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public SiteNaturelCreate description(String description) {
    super.description(description);
    return this;
  }

  public SiteNaturelCreate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public SiteNaturelCreate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public SiteNaturelCreate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public SiteNaturelCreate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public SiteNaturelCreate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public SiteNaturelCreate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public SiteNaturelCreate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public SiteNaturelCreate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public SiteNaturelCreate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public SiteNaturelCreate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public SiteNaturelCreate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public SiteNaturelCreate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public SiteNaturelCreate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public SiteNaturelCreate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public SiteNaturelCreate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public SiteNaturelCreate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public SiteNaturelCreate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public SiteNaturelCreate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public SiteNaturelCreate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public SiteNaturelCreate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public SiteNaturelCreate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public SiteNaturelCreate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public SiteNaturelCreate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public SiteNaturelCreate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public SiteNaturelCreate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public SiteNaturelCreate email(String email) {
    super.email(email);
    return this;
  }

  public SiteNaturelCreate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public SiteNaturelCreate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public SiteNaturelCreate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public SiteNaturelCreate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public SiteNaturelCreate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public SiteNaturelCreate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public SiteNaturelCreate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public SiteNaturelCreate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public SiteNaturelCreate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public SiteNaturelCreate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public SiteNaturelCreate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public SiteNaturelCreate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public SiteNaturelCreate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public SiteNaturelCreate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public SiteNaturelCreate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public SiteNaturelCreate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public SiteNaturelCreate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public SiteNaturelCreate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public SiteNaturelCreate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public SiteNaturelCreate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public SiteNaturelCreate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public SiteNaturelCreate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public SiteNaturelCreate publishedAt(OffsetDateTime publishedAt) {
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
    SiteNaturelCreate siteNaturelCreate = (SiteNaturelCreate) o;
    return Objects.equals(this.superficie, siteNaturelCreate.superficie) &&
        Objects.equals(this.natureSite, siteNaturelCreate.natureSite) &&
        Objects.equals(this.ecosysteme, siteNaturelCreate.ecosysteme) &&
        Objects.equals(this.especesProtegees, siteNaturelCreate.especesProtegees) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(superficie, natureSite, ecosysteme, especesProtegees, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SiteNaturelCreate {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    superficie: ").append(toIndentedString(superficie)).append("\n");
    sb.append("    natureSite: ").append(toIndentedString(natureSite)).append("\n");
    sb.append("    ecosysteme: ").append(toIndentedString(ecosysteme)).append("\n");
    sb.append("    especesProtegees: ").append(toIndentedString(especesProtegees)).append("\n");
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

