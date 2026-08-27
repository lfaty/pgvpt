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
 * SiteNaturelUpdate
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SiteNaturelUpdate extends PatrimoineUpdate {

  private Double superficie;

  private NatureSiteNaturel natureSite;

  private String ecosysteme;

  @Valid
  private List<@Valid EspeceProtegee> especesProtegees = new ArrayList<>();

  public SiteNaturelUpdate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SiteNaturelUpdate(String nom, String type, CategoriePatrimoine categorie, String description) {
    super(nom, type, categorie, description);
  }

  public SiteNaturelUpdate superficie(Double superficie) {
    this.superficie = superficie;
    return this;
  }

  /**
   * Get superficie
   * @return superficie
   */
  
  @Schema(name = "superficie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("superficie")
  public Double getSuperficie() {
    return superficie;
  }

  public void setSuperficie(Double superficie) {
    this.superficie = superficie;
  }

  public SiteNaturelUpdate natureSite(NatureSiteNaturel natureSite) {
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

  public SiteNaturelUpdate ecosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
    return this;
  }

  /**
   * Get ecosysteme
   * @return ecosysteme
   */
  
  @Schema(name = "ecosysteme", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ecosysteme")
  public String getEcosysteme() {
    return ecosysteme;
  }

  public void setEcosysteme(String ecosysteme) {
    this.ecosysteme = ecosysteme;
  }

  public SiteNaturelUpdate especesProtegees(List<@Valid EspeceProtegee> especesProtegees) {
    this.especesProtegees = especesProtegees;
    return this;
  }

  public SiteNaturelUpdate addEspecesProtegeesItem(EspeceProtegee especesProtegeesItem) {
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


  public SiteNaturelUpdate id(UUID id) {
    super.id(id);
    return this;
  }

  public SiteNaturelUpdate code(String code) {
    super.code(code);
    return this;
  }

  public SiteNaturelUpdate nom(String nom) {
    super.nom(nom);
    return this;
  }

  public SiteNaturelUpdate nomLocal(String nomLocal) {
    super.nomLocal(nomLocal);
    return this;
  }

  public SiteNaturelUpdate nomHistorique(String nomHistorique) {
    super.nomHistorique(nomHistorique);
    return this;
  }

  public SiteNaturelUpdate type(String type) {
    super.type(type);
    return this;
  }

  public SiteNaturelUpdate categorie(CategoriePatrimoine categorie) {
    super.categorie(categorie);
    return this;
  }

  public SiteNaturelUpdate sousCategorie(String sousCategorie) {
    super.sousCategorie(sousCategorie);
    return this;
  }

  public SiteNaturelUpdate periode(PeriodeHistorique periode) {
    super.periode(periode);
    return this;
  }

  public SiteNaturelUpdate siecle(String siecle) {
    super.siecle(siecle);
    return this;
  }

  public SiteNaturelUpdate dateOuverture(LocalDate dateOuverture) {
    super.dateOuverture(dateOuverture);
    return this;
  }

  public SiteNaturelUpdate description(String description) {
    super.description(description);
    return this;
  }

  public SiteNaturelUpdate descriptionCourte(String descriptionCourte) {
    super.descriptionCourte(descriptionCourte);
    return this;
  }

  public SiteNaturelUpdate historique(String historique) {
    super.historique(historique);
    return this;
  }

  public SiteNaturelUpdate importanceHistorique(String importanceHistorique) {
    super.importanceHistorique(importanceHistorique);
    return this;
  }

  public SiteNaturelUpdate importanceCulturelle(String importanceCulturelle) {
    super.importanceCulturelle(importanceCulturelle);
    return this;
  }

  public SiteNaturelUpdate importanceTouristique(String importanceTouristique) {
    super.importanceTouristique(importanceTouristique);
    return this;
  }

  public SiteNaturelUpdate valeurPatrimoniale(String valeurPatrimoniale) {
    super.valeurPatrimoniale(valeurPatrimoniale);
    return this;
  }

  public SiteNaturelUpdate valeurSpirituelle(String valeurSpirituelle) {
    super.valeurSpirituelle(valeurSpirituelle);
    return this;
  }

  public SiteNaturelUpdate traditionsAssociees(List<String> traditionsAssociees) {
    super.traditionsAssociees(traditionsAssociees);
    return this;
  }

  public SiteNaturelUpdate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    super.addTraditionsAssocieesItem(traditionsAssocieesItem);
    return this;
  }

  public SiteNaturelUpdate langues(List<String> langues) {
    super.langues(langues);
    return this;
  }

  public SiteNaturelUpdate addLanguesItem(String languesItem) {
    super.addLanguesItem(languesItem);
    return this;
  }

  public SiteNaturelUpdate etatConservation(EtatConservation etatConservation) {
    super.etatConservation(etatConservation);
    return this;
  }

  public SiteNaturelUpdate statut(StatutPatrimoine statut) {
    super.statut(statut);
    return this;
  }

  public SiteNaturelUpdate classePatrimoine(Boolean classePatrimoine) {
    super.classePatrimoine(classePatrimoine);
    return this;
  }

  public SiteNaturelUpdate referenceClassement(String referenceClassement) {
    super.referenceClassement(referenceClassement);
    return this;
  }

  public SiteNaturelUpdate dateClassement(LocalDate dateClassement) {
    super.dateClassement(dateClassement);
    return this;
  }

  public SiteNaturelUpdate inscritUnesco(Boolean inscritUnesco) {
    super.inscritUnesco(inscritUnesco);
    return this;
  }

  public SiteNaturelUpdate nomSiteUnesco(String nomSiteUnesco) {
    super.nomSiteUnesco(nomSiteUnesco);
    return this;
  }

  public SiteNaturelUpdate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    super.dateInscriptionUnesco(dateInscriptionUnesco);
    return this;
  }

  public SiteNaturelUpdate protectionJuridique(String protectionJuridique) {
    super.protectionJuridique(protectionJuridique);
    return this;
  }

  public SiteNaturelUpdate organismeGestionnaire(String organismeGestionnaire) {
    super.organismeGestionnaire(organismeGestionnaire);
    return this;
  }

  public SiteNaturelUpdate proprietaire(String proprietaire) {
    super.proprietaire(proprietaire);
    return this;
  }

  public SiteNaturelUpdate gestionnaire(String gestionnaire) {
    super.gestionnaire(gestionnaire);
    return this;
  }

  public SiteNaturelUpdate contact(Contact contact) {
    super.contact(contact);
    return this;
  }

  public SiteNaturelUpdate siteWeb(URI siteWeb) {
    super.siteWeb(siteWeb);
    return this;
  }

  public SiteNaturelUpdate email(String email) {
    super.email(email);
    return this;
  }

  public SiteNaturelUpdate telephone(String telephone) {
    super.telephone(telephone);
    return this;
  }

  public SiteNaturelUpdate accessibilite(Accessibilite accessibilite) {
    super.accessibilite(accessibilite);
    return this;
  }

  public SiteNaturelUpdate tarification(Tarification tarification) {
    super.tarification(tarification);
    return this;
  }

  public SiteNaturelUpdate horaires(List<@Valid HoraireOuverture> horaires) {
    super.horaires(horaires);
    return this;
  }

  public SiteNaturelUpdate addHorairesItem(HoraireOuverture horairesItem) {
    super.addHorairesItem(horairesItem);
    return this;
  }

  public SiteNaturelUpdate photos(List<@Valid Photo> photos) {
    super.photos(photos);
    return this;
  }

  public SiteNaturelUpdate addPhotosItem(Photo photosItem) {
    super.addPhotosItem(photosItem);
    return this;
  }

  public SiteNaturelUpdate medias(List<@Valid Media> medias) {
    super.medias(medias);
    return this;
  }

  public SiteNaturelUpdate addMediasItem(Media mediasItem) {
    super.addMediasItem(mediasItem);
    return this;
  }

  public SiteNaturelUpdate conservation(Conservation conservation) {
    super.conservation(conservation);
    return this;
  }

  public SiteNaturelUpdate menaces(List<@Valid Menace> menaces) {
    super.menaces(menaces);
    return this;
  }

  public SiteNaturelUpdate addMenacesItem(Menace menacesItem) {
    super.addMenacesItem(menacesItem);
    return this;
  }

  public SiteNaturelUpdate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    super.recommandationsVisiteur(recommandationsVisiteur);
    return this;
  }

  public SiteNaturelUpdate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    super.addRecommandationsVisiteurItem(recommandationsVisiteurItem);
    return this;
  }

  public SiteNaturelUpdate reglesVisite(List<String> reglesVisite) {
    super.reglesVisite(reglesVisite);
    return this;
  }

  public SiteNaturelUpdate addReglesVisiteItem(String reglesVisiteItem) {
    super.addReglesVisiteItem(reglesVisiteItem);
    return this;
  }

  public SiteNaturelUpdate activitesTouristiques(List<String> activitesTouristiques) {
    super.activitesTouristiques(activitesTouristiques);
    return this;
  }

  public SiteNaturelUpdate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    super.addActivitesTouristiquesItem(activitesTouristiquesItem);
    return this;
  }

  public SiteNaturelUpdate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    super.meilleurePeriodeVisite(meilleurePeriodeVisite);
    return this;
  }

  public SiteNaturelUpdate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    super.dureeVisiteEstimeeMinutes(dureeVisiteEstimeeMinutes);
    return this;
  }

  public SiteNaturelUpdate createdAt(OffsetDateTime createdAt) {
    super.createdAt(createdAt);
    return this;
  }

  public SiteNaturelUpdate updatedAt(OffsetDateTime updatedAt) {
    super.updatedAt(updatedAt);
    return this;
  }

  public SiteNaturelUpdate publishedAt(OffsetDateTime publishedAt) {
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
    SiteNaturelUpdate siteNaturelUpdate = (SiteNaturelUpdate) o;
    return Objects.equals(this.superficie, siteNaturelUpdate.superficie) &&
        Objects.equals(this.natureSite, siteNaturelUpdate.natureSite) &&
        Objects.equals(this.ecosysteme, siteNaturelUpdate.ecosysteme) &&
        Objects.equals(this.especesProtegees, siteNaturelUpdate.especesProtegees) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(superficie, natureSite, ecosysteme, especesProtegees, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SiteNaturelUpdate {\n");
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

