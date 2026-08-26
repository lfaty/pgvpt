package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
public class MonumentCreate implements PatrimoineCreate {

  private UUID id;

  private String code;

  private String nom;

  private String nomLocal;

  private String nomHistorique;

  private String type;

  private CategoriePatrimoine categorie;

  private String sousCategorie;

  private PeriodeHistorique periode;

  private String siecle;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOuverture;

  private String description;

  private String descriptionCourte;

  private String historique;

  private String importanceHistorique;

  private String importanceCulturelle;

  private String importanceTouristique;

  private String valeurPatrimoniale;

  private String valeurSpirituelle;

  @Valid
  private List<String> traditionsAssociees = new ArrayList<>();

  @Valid
  private List<String> langues = new ArrayList<>();

  private EtatConservation etatConservation;

  private StatutPatrimoine statut;

  private Boolean classePatrimoine;

  private String referenceClassement;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateClassement;

  private Boolean inscritUnesco;

  private String nomSiteUnesco;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateInscriptionUnesco;

  private String protectionJuridique;

  private String organismeGestionnaire;

  private String proprietaire;

  private String gestionnaire;

  private Contact contact;

  private URI siteWeb;

  private String email;

  private String telephone;

  private Accessibilite accessibilite;

  private Tarification tarification;

  @Valid
  private List<@Valid HoraireOuverture> horaires = new ArrayList<>();

  @Valid
  private List<@Valid Photo> photos = new ArrayList<>();

  @Valid
  private List<@Valid Media> medias = new ArrayList<>();

  private Conservation conservation;

  @Valid
  private List<@Valid Menace> menaces = new ArrayList<>();

  @Valid
  private List<String> recommandationsVisiteur = new ArrayList<>();

  @Valid
  private List<String> reglesVisite = new ArrayList<>();

  @Valid
  private List<String> activitesTouristiques = new ArrayList<>();

  private String meilleurePeriodeVisite;

  private Integer dureeVisiteEstimeeMinutes;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime publishedAt;

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
  public MonumentCreate(String nom, String type, CategoriePatrimoine categorie, String description, String styleArchitectural, NatureMonument natureMonument) {
    this.nom = nom;
    this.type = type;
    this.categorie = categorie;
    this.description = description;
    this.styleArchitectural = styleArchitectural;
    this.natureMonument = natureMonument;
  }

  public MonumentCreate id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public MonumentCreate code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Code métier unique du patrimoine
   * @return code
   */
  @Size(max = 50) 
  @Schema(name = "code", example = "SN-DKR-00125", description = "Code métier unique du patrimoine", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public MonumentCreate nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "nom", example = "Maison des Esclaves", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public MonumentCreate nomLocal(String nomLocal) {
    this.nomLocal = nomLocal;
    return this;
  }

  /**
   * Nom traditionnel ou nom local
   * @return nomLocal
   */
  @Size(max = 255) 
  @Schema(name = "nomLocal", description = "Nom traditionnel ou nom local", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomLocal")
  public String getNomLocal() {
    return nomLocal;
  }

  public void setNomLocal(String nomLocal) {
    this.nomLocal = nomLocal;
  }

  public MonumentCreate nomHistorique(String nomHistorique) {
    this.nomHistorique = nomHistorique;
    return this;
  }

  /**
   * Get nomHistorique
   * @return nomHistorique
   */
  @Size(max = 255) 
  @Schema(name = "nomHistorique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomHistorique")
  public String getNomHistorique() {
    return nomHistorique;
  }

  public void setNomHistorique(String nomHistorique) {
    this.nomHistorique = nomHistorique;
  }

  public MonumentCreate type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public MonumentCreate categorie(CategoriePatrimoine categorie) {
    this.categorie = categorie;
    return this;
  }

  /**
   * Get categorie
   * @return categorie
   */
  @NotNull @Valid 
  @Schema(name = "categorie", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categorie")
  public CategoriePatrimoine getCategorie() {
    return categorie;
  }

  public void setCategorie(CategoriePatrimoine categorie) {
    this.categorie = categorie;
  }

  public MonumentCreate sousCategorie(String sousCategorie) {
    this.sousCategorie = sousCategorie;
    return this;
  }

  /**
   * Get sousCategorie
   * @return sousCategorie
   */
  @Size(max = 150) 
  @Schema(name = "sousCategorie", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sousCategorie")
  public String getSousCategorie() {
    return sousCategorie;
  }

  public void setSousCategorie(String sousCategorie) {
    this.sousCategorie = sousCategorie;
  }

  public MonumentCreate periode(PeriodeHistorique periode) {
    this.periode = periode;
    return this;
  }

  /**
   * Get periode
   * @return periode
   */
  @Valid 
  @Schema(name = "periode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("periode")
  public PeriodeHistorique getPeriode() {
    return periode;
  }

  public void setPeriode(PeriodeHistorique periode) {
    this.periode = periode;
  }

  public MonumentCreate siecle(String siecle) {
    this.siecle = siecle;
    return this;
  }

  /**
   * Get siecle
   * @return siecle
   */
  
  @Schema(name = "siecle", example = "XVIIIe siècle", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("siecle")
  public String getSiecle() {
    return siecle;
  }

  public void setSiecle(String siecle) {
    this.siecle = siecle;
  }

  public MonumentCreate dateOuverture(LocalDate dateOuverture) {
    this.dateOuverture = dateOuverture;
    return this;
  }

  /**
   * Get dateOuverture
   * @return dateOuverture
   */
  @Valid 
  @Schema(name = "dateOuverture", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateOuverture")
  public LocalDate getDateOuverture() {
    return dateOuverture;
  }

  public void setDateOuverture(LocalDate dateOuverture) {
    this.dateOuverture = dateOuverture;
  }

  public MonumentCreate description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull 
  @Schema(name = "description", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MonumentCreate descriptionCourte(String descriptionCourte) {
    this.descriptionCourte = descriptionCourte;
    return this;
  }

  /**
   * Get descriptionCourte
   * @return descriptionCourte
   */
  @Size(max = 1000) 
  @Schema(name = "descriptionCourte", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("descriptionCourte")
  public String getDescriptionCourte() {
    return descriptionCourte;
  }

  public void setDescriptionCourte(String descriptionCourte) {
    this.descriptionCourte = descriptionCourte;
  }

  public MonumentCreate historique(String historique) {
    this.historique = historique;
    return this;
  }

  /**
   * Get historique
   * @return historique
   */
  
  @Schema(name = "historique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("historique")
  public String getHistorique() {
    return historique;
  }

  public void setHistorique(String historique) {
    this.historique = historique;
  }

  public MonumentCreate importanceHistorique(String importanceHistorique) {
    this.importanceHistorique = importanceHistorique;
    return this;
  }

  /**
   * Get importanceHistorique
   * @return importanceHistorique
   */
  
  @Schema(name = "importanceHistorique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("importanceHistorique")
  public String getImportanceHistorique() {
    return importanceHistorique;
  }

  public void setImportanceHistorique(String importanceHistorique) {
    this.importanceHistorique = importanceHistorique;
  }

  public MonumentCreate importanceCulturelle(String importanceCulturelle) {
    this.importanceCulturelle = importanceCulturelle;
    return this;
  }

  /**
   * Get importanceCulturelle
   * @return importanceCulturelle
   */
  
  @Schema(name = "importanceCulturelle", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("importanceCulturelle")
  public String getImportanceCulturelle() {
    return importanceCulturelle;
  }

  public void setImportanceCulturelle(String importanceCulturelle) {
    this.importanceCulturelle = importanceCulturelle;
  }

  public MonumentCreate importanceTouristique(String importanceTouristique) {
    this.importanceTouristique = importanceTouristique;
    return this;
  }

  /**
   * Get importanceTouristique
   * @return importanceTouristique
   */
  
  @Schema(name = "importanceTouristique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("importanceTouristique")
  public String getImportanceTouristique() {
    return importanceTouristique;
  }

  public void setImportanceTouristique(String importanceTouristique) {
    this.importanceTouristique = importanceTouristique;
  }

  public MonumentCreate valeurPatrimoniale(String valeurPatrimoniale) {
    this.valeurPatrimoniale = valeurPatrimoniale;
    return this;
  }

  /**
   * Get valeurPatrimoniale
   * @return valeurPatrimoniale
   */
  
  @Schema(name = "valeurPatrimoniale", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("valeurPatrimoniale")
  public String getValeurPatrimoniale() {
    return valeurPatrimoniale;
  }

  public void setValeurPatrimoniale(String valeurPatrimoniale) {
    this.valeurPatrimoniale = valeurPatrimoniale;
  }

  public MonumentCreate valeurSpirituelle(String valeurSpirituelle) {
    this.valeurSpirituelle = valeurSpirituelle;
    return this;
  }

  /**
   * Get valeurSpirituelle
   * @return valeurSpirituelle
   */
  
  @Schema(name = "valeurSpirituelle", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("valeurSpirituelle")
  public String getValeurSpirituelle() {
    return valeurSpirituelle;
  }

  public void setValeurSpirituelle(String valeurSpirituelle) {
    this.valeurSpirituelle = valeurSpirituelle;
  }

  public MonumentCreate traditionsAssociees(List<String> traditionsAssociees) {
    this.traditionsAssociees = traditionsAssociees;
    return this;
  }

  public MonumentCreate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
    if (this.traditionsAssociees == null) {
      this.traditionsAssociees = new ArrayList<>();
    }
    this.traditionsAssociees.add(traditionsAssocieesItem);
    return this;
  }

  /**
   * Get traditionsAssociees
   * @return traditionsAssociees
   */
  
  @Schema(name = "traditionsAssociees", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("traditionsAssociees")
  public List<String> getTraditionsAssociees() {
    return traditionsAssociees;
  }

  public void setTraditionsAssociees(List<String> traditionsAssociees) {
    this.traditionsAssociees = traditionsAssociees;
  }

  public MonumentCreate langues(List<String> langues) {
    this.langues = langues;
    return this;
  }

  public MonumentCreate addLanguesItem(String languesItem) {
    if (this.langues == null) {
      this.langues = new ArrayList<>();
    }
    this.langues.add(languesItem);
    return this;
  }

  /**
   * Get langues
   * @return langues
   */
  
  @Schema(name = "langues", example = "[Français, Wolof, Anglais]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("langues")
  public List<String> getLangues() {
    return langues;
  }

  public void setLangues(List<String> langues) {
    this.langues = langues;
  }

  public MonumentCreate etatConservation(EtatConservation etatConservation) {
    this.etatConservation = etatConservation;
    return this;
  }

  /**
   * Get etatConservation
   * @return etatConservation
   */
  @Valid 
  @Schema(name = "etatConservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("etatConservation")
  public EtatConservation getEtatConservation() {
    return etatConservation;
  }

  public void setEtatConservation(EtatConservation etatConservation) {
    this.etatConservation = etatConservation;
  }

  public MonumentCreate statut(StatutPatrimoine statut) {
    this.statut = statut;
    return this;
  }

  /**
   * Get statut
   * @return statut
   */
  @Valid 
  @Schema(name = "statut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statut")
  public StatutPatrimoine getStatut() {
    return statut;
  }

  public void setStatut(StatutPatrimoine statut) {
    this.statut = statut;
  }

  public MonumentCreate classePatrimoine(Boolean classePatrimoine) {
    this.classePatrimoine = classePatrimoine;
    return this;
  }

  /**
   * Indique si le patrimoine bénéficie d'un classement officiel
   * @return classePatrimoine
   */
  
  @Schema(name = "classePatrimoine", description = "Indique si le patrimoine bénéficie d'un classement officiel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("classePatrimoine")
  public Boolean getClassePatrimoine() {
    return classePatrimoine;
  }

  public void setClassePatrimoine(Boolean classePatrimoine) {
    this.classePatrimoine = classePatrimoine;
  }

  public MonumentCreate referenceClassement(String referenceClassement) {
    this.referenceClassement = referenceClassement;
    return this;
  }

  /**
   * Get referenceClassement
   * @return referenceClassement
   */
  
  @Schema(name = "referenceClassement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("referenceClassement")
  public String getReferenceClassement() {
    return referenceClassement;
  }

  public void setReferenceClassement(String referenceClassement) {
    this.referenceClassement = referenceClassement;
  }

  public MonumentCreate dateClassement(LocalDate dateClassement) {
    this.dateClassement = dateClassement;
    return this;
  }

  /**
   * Get dateClassement
   * @return dateClassement
   */
  @Valid 
  @Schema(name = "dateClassement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateClassement")
  public LocalDate getDateClassement() {
    return dateClassement;
  }

  public void setDateClassement(LocalDate dateClassement) {
    this.dateClassement = dateClassement;
  }

  public MonumentCreate inscritUnesco(Boolean inscritUnesco) {
    this.inscritUnesco = inscritUnesco;
    return this;
  }

  /**
   * Get inscritUnesco
   * @return inscritUnesco
   */
  
  @Schema(name = "inscritUnesco", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inscritUnesco")
  public Boolean getInscritUnesco() {
    return inscritUnesco;
  }

  public void setInscritUnesco(Boolean inscritUnesco) {
    this.inscritUnesco = inscritUnesco;
  }

  public MonumentCreate nomSiteUnesco(String nomSiteUnesco) {
    this.nomSiteUnesco = nomSiteUnesco;
    return this;
  }

  /**
   * Get nomSiteUnesco
   * @return nomSiteUnesco
   */
  
  @Schema(name = "nomSiteUnesco", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomSiteUnesco")
  public String getNomSiteUnesco() {
    return nomSiteUnesco;
  }

  public void setNomSiteUnesco(String nomSiteUnesco) {
    this.nomSiteUnesco = nomSiteUnesco;
  }

  public MonumentCreate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    this.dateInscriptionUnesco = dateInscriptionUnesco;
    return this;
  }

  /**
   * Get dateInscriptionUnesco
   * @return dateInscriptionUnesco
   */
  @Valid 
  @Schema(name = "dateInscriptionUnesco", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateInscriptionUnesco")
  public LocalDate getDateInscriptionUnesco() {
    return dateInscriptionUnesco;
  }

  public void setDateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
    this.dateInscriptionUnesco = dateInscriptionUnesco;
  }

  public MonumentCreate protectionJuridique(String protectionJuridique) {
    this.protectionJuridique = protectionJuridique;
    return this;
  }

  /**
   * Get protectionJuridique
   * @return protectionJuridique
   */
  
  @Schema(name = "protectionJuridique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("protectionJuridique")
  public String getProtectionJuridique() {
    return protectionJuridique;
  }

  public void setProtectionJuridique(String protectionJuridique) {
    this.protectionJuridique = protectionJuridique;
  }

  public MonumentCreate organismeGestionnaire(String organismeGestionnaire) {
    this.organismeGestionnaire = organismeGestionnaire;
    return this;
  }

  /**
   * Get organismeGestionnaire
   * @return organismeGestionnaire
   */
  
  @Schema(name = "organismeGestionnaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("organismeGestionnaire")
  public String getOrganismeGestionnaire() {
    return organismeGestionnaire;
  }

  public void setOrganismeGestionnaire(String organismeGestionnaire) {
    this.organismeGestionnaire = organismeGestionnaire;
  }

  public MonumentCreate proprietaire(String proprietaire) {
    this.proprietaire = proprietaire;
    return this;
  }

  /**
   * Get proprietaire
   * @return proprietaire
   */
  
  @Schema(name = "proprietaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("proprietaire")
  public String getProprietaire() {
    return proprietaire;
  }

  public void setProprietaire(String proprietaire) {
    this.proprietaire = proprietaire;
  }

  public MonumentCreate gestionnaire(String gestionnaire) {
    this.gestionnaire = gestionnaire;
    return this;
  }

  /**
   * Get gestionnaire
   * @return gestionnaire
   */
  
  @Schema(name = "gestionnaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gestionnaire")
  public String getGestionnaire() {
    return gestionnaire;
  }

  public void setGestionnaire(String gestionnaire) {
    this.gestionnaire = gestionnaire;
  }

  public MonumentCreate contact(Contact contact) {
    this.contact = contact;
    return this;
  }

  /**
   * Get contact
   * @return contact
   */
  @Valid 
  @Schema(name = "contact", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contact")
  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  public MonumentCreate siteWeb(URI siteWeb) {
    this.siteWeb = siteWeb;
    return this;
  }

  /**
   * Get siteWeb
   * @return siteWeb
   */
  @Valid 
  @Schema(name = "siteWeb", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("siteWeb")
  public URI getSiteWeb() {
    return siteWeb;
  }

  public void setSiteWeb(URI siteWeb) {
    this.siteWeb = siteWeb;
  }

  public MonumentCreate email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MonumentCreate telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

  /**
   * Get telephone
   * @return telephone
   */
  
  @Schema(name = "telephone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("telephone")
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public MonumentCreate accessibilite(Accessibilite accessibilite) {
    this.accessibilite = accessibilite;
    return this;
  }

  /**
   * Get accessibilite
   * @return accessibilite
   */
  @Valid 
  @Schema(name = "accessibilite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accessibilite")
  public Accessibilite getAccessibilite() {
    return accessibilite;
  }

  public void setAccessibilite(Accessibilite accessibilite) {
    this.accessibilite = accessibilite;
  }

  public MonumentCreate tarification(Tarification tarification) {
    this.tarification = tarification;
    return this;
  }

  /**
   * Get tarification
   * @return tarification
   */
  @Valid 
  @Schema(name = "tarification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tarification")
  public Tarification getTarification() {
    return tarification;
  }

  public void setTarification(Tarification tarification) {
    this.tarification = tarification;
  }

  public MonumentCreate horaires(List<@Valid HoraireOuverture> horaires) {
    this.horaires = horaires;
    return this;
  }

  public MonumentCreate addHorairesItem(HoraireOuverture horairesItem) {
    if (this.horaires == null) {
      this.horaires = new ArrayList<>();
    }
    this.horaires.add(horairesItem);
    return this;
  }

  /**
   * Get horaires
   * @return horaires
   */
  @Valid 
  @Schema(name = "horaires", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("horaires")
  public List<@Valid HoraireOuverture> getHoraires() {
    return horaires;
  }

  public void setHoraires(List<@Valid HoraireOuverture> horaires) {
    this.horaires = horaires;
  }

  public MonumentCreate photos(List<@Valid Photo> photos) {
    this.photos = photos;
    return this;
  }

  public MonumentCreate addPhotosItem(Photo photosItem) {
    if (this.photos == null) {
      this.photos = new ArrayList<>();
    }
    this.photos.add(photosItem);
    return this;
  }

  /**
   * Get photos
   * @return photos
   */
  @Valid 
  @Schema(name = "photos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photos")
  public List<@Valid Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<@Valid Photo> photos) {
    this.photos = photos;
  }

  public MonumentCreate medias(List<@Valid Media> medias) {
    this.medias = medias;
    return this;
  }

  public MonumentCreate addMediasItem(Media mediasItem) {
    if (this.medias == null) {
      this.medias = new ArrayList<>();
    }
    this.medias.add(mediasItem);
    return this;
  }

  /**
   * Get medias
   * @return medias
   */
  @Valid 
  @Schema(name = "medias", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("medias")
  public List<@Valid Media> getMedias() {
    return medias;
  }

  public void setMedias(List<@Valid Media> medias) {
    this.medias = medias;
  }

  public MonumentCreate conservation(Conservation conservation) {
    this.conservation = conservation;
    return this;
  }

  /**
   * Get conservation
   * @return conservation
   */
  @Valid 
  @Schema(name = "conservation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conservation")
  public Conservation getConservation() {
    return conservation;
  }

  public void setConservation(Conservation conservation) {
    this.conservation = conservation;
  }

  public MonumentCreate menaces(List<@Valid Menace> menaces) {
    this.menaces = menaces;
    return this;
  }

  public MonumentCreate addMenacesItem(Menace menacesItem) {
    if (this.menaces == null) {
      this.menaces = new ArrayList<>();
    }
    this.menaces.add(menacesItem);
    return this;
  }

  /**
   * Get menaces
   * @return menaces
   */
  @Valid 
  @Schema(name = "menaces", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("menaces")
  public List<@Valid Menace> getMenaces() {
    return menaces;
  }

  public void setMenaces(List<@Valid Menace> menaces) {
    this.menaces = menaces;
  }

  public MonumentCreate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    this.recommandationsVisiteur = recommandationsVisiteur;
    return this;
  }

  public MonumentCreate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
    if (this.recommandationsVisiteur == null) {
      this.recommandationsVisiteur = new ArrayList<>();
    }
    this.recommandationsVisiteur.add(recommandationsVisiteurItem);
    return this;
  }

  /**
   * Get recommandationsVisiteur
   * @return recommandationsVisiteur
   */
  
  @Schema(name = "recommandationsVisiteur", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recommandationsVisiteur")
  public List<String> getRecommandationsVisiteur() {
    return recommandationsVisiteur;
  }

  public void setRecommandationsVisiteur(List<String> recommandationsVisiteur) {
    this.recommandationsVisiteur = recommandationsVisiteur;
  }

  public MonumentCreate reglesVisite(List<String> reglesVisite) {
    this.reglesVisite = reglesVisite;
    return this;
  }

  public MonumentCreate addReglesVisiteItem(String reglesVisiteItem) {
    if (this.reglesVisite == null) {
      this.reglesVisite = new ArrayList<>();
    }
    this.reglesVisite.add(reglesVisiteItem);
    return this;
  }

  /**
   * Get reglesVisite
   * @return reglesVisite
   */
  
  @Schema(name = "reglesVisite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reglesVisite")
  public List<String> getReglesVisite() {
    return reglesVisite;
  }

  public void setReglesVisite(List<String> reglesVisite) {
    this.reglesVisite = reglesVisite;
  }

  public MonumentCreate activitesTouristiques(List<String> activitesTouristiques) {
    this.activitesTouristiques = activitesTouristiques;
    return this;
  }

  public MonumentCreate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
    if (this.activitesTouristiques == null) {
      this.activitesTouristiques = new ArrayList<>();
    }
    this.activitesTouristiques.add(activitesTouristiquesItem);
    return this;
  }

  /**
   * Get activitesTouristiques
   * @return activitesTouristiques
   */
  
  @Schema(name = "activitesTouristiques", example = "[Visite guidée, Photographie, Observation, Randonnée]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("activitesTouristiques")
  public List<String> getActivitesTouristiques() {
    return activitesTouristiques;
  }

  public void setActivitesTouristiques(List<String> activitesTouristiques) {
    this.activitesTouristiques = activitesTouristiques;
  }

  public MonumentCreate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
    this.meilleurePeriodeVisite = meilleurePeriodeVisite;
    return this;
  }

  /**
   * Get meilleurePeriodeVisite
   * @return meilleurePeriodeVisite
   */
  
  @Schema(name = "meilleurePeriodeVisite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meilleurePeriodeVisite")
  public String getMeilleurePeriodeVisite() {
    return meilleurePeriodeVisite;
  }

  public void setMeilleurePeriodeVisite(String meilleurePeriodeVisite) {
    this.meilleurePeriodeVisite = meilleurePeriodeVisite;
  }

  public MonumentCreate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    this.dureeVisiteEstimeeMinutes = dureeVisiteEstimeeMinutes;
    return this;
  }

  /**
   * Get dureeVisiteEstimeeMinutes
   * minimum: 0
   * @return dureeVisiteEstimeeMinutes
   */
  @Min(0) 
  @Schema(name = "dureeVisiteEstimeeMinutes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dureeVisiteEstimeeMinutes")
  public Integer getDureeVisiteEstimeeMinutes() {
    return dureeVisiteEstimeeMinutes;
  }

  public void setDureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
    this.dureeVisiteEstimeeMinutes = dureeVisiteEstimeeMinutes;
  }

  public MonumentCreate createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public MonumentCreate updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public MonumentCreate publishedAt(OffsetDateTime publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  /**
   * Get publishedAt
   * @return publishedAt
   */
  @Valid 
  @Schema(name = "publishedAt", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publishedAt")
  public OffsetDateTime getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(OffsetDateTime publishedAt) {
    this.publishedAt = publishedAt;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MonumentCreate monumentCreate = (MonumentCreate) o;
    return Objects.equals(this.id, monumentCreate.id) &&
        Objects.equals(this.code, monumentCreate.code) &&
        Objects.equals(this.nom, monumentCreate.nom) &&
        Objects.equals(this.nomLocal, monumentCreate.nomLocal) &&
        Objects.equals(this.nomHistorique, monumentCreate.nomHistorique) &&
        Objects.equals(this.type, monumentCreate.type) &&
        Objects.equals(this.categorie, monumentCreate.categorie) &&
        Objects.equals(this.sousCategorie, monumentCreate.sousCategorie) &&
        Objects.equals(this.periode, monumentCreate.periode) &&
        Objects.equals(this.siecle, monumentCreate.siecle) &&
        Objects.equals(this.dateOuverture, monumentCreate.dateOuverture) &&
        Objects.equals(this.description, monumentCreate.description) &&
        Objects.equals(this.descriptionCourte, monumentCreate.descriptionCourte) &&
        Objects.equals(this.historique, monumentCreate.historique) &&
        Objects.equals(this.importanceHistorique, monumentCreate.importanceHistorique) &&
        Objects.equals(this.importanceCulturelle, monumentCreate.importanceCulturelle) &&
        Objects.equals(this.importanceTouristique, monumentCreate.importanceTouristique) &&
        Objects.equals(this.valeurPatrimoniale, monumentCreate.valeurPatrimoniale) &&
        Objects.equals(this.valeurSpirituelle, monumentCreate.valeurSpirituelle) &&
        Objects.equals(this.traditionsAssociees, monumentCreate.traditionsAssociees) &&
        Objects.equals(this.langues, monumentCreate.langues) &&
        Objects.equals(this.etatConservation, monumentCreate.etatConservation) &&
        Objects.equals(this.statut, monumentCreate.statut) &&
        Objects.equals(this.classePatrimoine, monumentCreate.classePatrimoine) &&
        Objects.equals(this.referenceClassement, monumentCreate.referenceClassement) &&
        Objects.equals(this.dateClassement, monumentCreate.dateClassement) &&
        Objects.equals(this.inscritUnesco, monumentCreate.inscritUnesco) &&
        Objects.equals(this.nomSiteUnesco, monumentCreate.nomSiteUnesco) &&
        Objects.equals(this.dateInscriptionUnesco, monumentCreate.dateInscriptionUnesco) &&
        Objects.equals(this.protectionJuridique, monumentCreate.protectionJuridique) &&
        Objects.equals(this.organismeGestionnaire, monumentCreate.organismeGestionnaire) &&
        Objects.equals(this.proprietaire, monumentCreate.proprietaire) &&
        Objects.equals(this.gestionnaire, monumentCreate.gestionnaire) &&
        Objects.equals(this.contact, monumentCreate.contact) &&
        Objects.equals(this.siteWeb, monumentCreate.siteWeb) &&
        Objects.equals(this.email, monumentCreate.email) &&
        Objects.equals(this.telephone, monumentCreate.telephone) &&
        Objects.equals(this.accessibilite, monumentCreate.accessibilite) &&
        Objects.equals(this.tarification, monumentCreate.tarification) &&
        Objects.equals(this.horaires, monumentCreate.horaires) &&
        Objects.equals(this.photos, monumentCreate.photos) &&
        Objects.equals(this.medias, monumentCreate.medias) &&
        Objects.equals(this.conservation, monumentCreate.conservation) &&
        Objects.equals(this.menaces, monumentCreate.menaces) &&
        Objects.equals(this.recommandationsVisiteur, monumentCreate.recommandationsVisiteur) &&
        Objects.equals(this.reglesVisite, monumentCreate.reglesVisite) &&
        Objects.equals(this.activitesTouristiques, monumentCreate.activitesTouristiques) &&
        Objects.equals(this.meilleurePeriodeVisite, monumentCreate.meilleurePeriodeVisite) &&
        Objects.equals(this.dureeVisiteEstimeeMinutes, monumentCreate.dureeVisiteEstimeeMinutes) &&
        Objects.equals(this.createdAt, monumentCreate.createdAt) &&
        Objects.equals(this.updatedAt, monumentCreate.updatedAt) &&
        Objects.equals(this.publishedAt, monumentCreate.publishedAt) &&
        Objects.equals(this.styleArchitectural, monumentCreate.styleArchitectural) &&
        Objects.equals(this.anneeConstruction, monumentCreate.anneeConstruction) &&
        Objects.equals(this.identiteArchitecte, monumentCreate.identiteArchitecte) &&
        Objects.equals(this.natureMonument, monumentCreate.natureMonument);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, nom, nomLocal, nomHistorique, type, categorie, sousCategorie, periode, siecle, dateOuverture, description, descriptionCourte, historique, importanceHistorique, importanceCulturelle, importanceTouristique, valeurPatrimoniale, valeurSpirituelle, traditionsAssociees, langues, etatConservation, statut, classePatrimoine, referenceClassement, dateClassement, inscritUnesco, nomSiteUnesco, dateInscriptionUnesco, protectionJuridique, organismeGestionnaire, proprietaire, gestionnaire, contact, siteWeb, email, telephone, accessibilite, tarification, horaires, photos, medias, conservation, menaces, recommandationsVisiteur, reglesVisite, activitesTouristiques, meilleurePeriodeVisite, dureeVisiteEstimeeMinutes, createdAt, updatedAt, publishedAt, styleArchitectural, anneeConstruction, identiteArchitecte, natureMonument);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonumentCreate {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    nomLocal: ").append(toIndentedString(nomLocal)).append("\n");
    sb.append("    nomHistorique: ").append(toIndentedString(nomHistorique)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    categorie: ").append(toIndentedString(categorie)).append("\n");
    sb.append("    sousCategorie: ").append(toIndentedString(sousCategorie)).append("\n");
    sb.append("    periode: ").append(toIndentedString(periode)).append("\n");
    sb.append("    siecle: ").append(toIndentedString(siecle)).append("\n");
    sb.append("    dateOuverture: ").append(toIndentedString(dateOuverture)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    descriptionCourte: ").append(toIndentedString(descriptionCourte)).append("\n");
    sb.append("    historique: ").append(toIndentedString(historique)).append("\n");
    sb.append("    importanceHistorique: ").append(toIndentedString(importanceHistorique)).append("\n");
    sb.append("    importanceCulturelle: ").append(toIndentedString(importanceCulturelle)).append("\n");
    sb.append("    importanceTouristique: ").append(toIndentedString(importanceTouristique)).append("\n");
    sb.append("    valeurPatrimoniale: ").append(toIndentedString(valeurPatrimoniale)).append("\n");
    sb.append("    valeurSpirituelle: ").append(toIndentedString(valeurSpirituelle)).append("\n");
    sb.append("    traditionsAssociees: ").append(toIndentedString(traditionsAssociees)).append("\n");
    sb.append("    langues: ").append(toIndentedString(langues)).append("\n");
    sb.append("    etatConservation: ").append(toIndentedString(etatConservation)).append("\n");
    sb.append("    statut: ").append(toIndentedString(statut)).append("\n");
    sb.append("    classePatrimoine: ").append(toIndentedString(classePatrimoine)).append("\n");
    sb.append("    referenceClassement: ").append(toIndentedString(referenceClassement)).append("\n");
    sb.append("    dateClassement: ").append(toIndentedString(dateClassement)).append("\n");
    sb.append("    inscritUnesco: ").append(toIndentedString(inscritUnesco)).append("\n");
    sb.append("    nomSiteUnesco: ").append(toIndentedString(nomSiteUnesco)).append("\n");
    sb.append("    dateInscriptionUnesco: ").append(toIndentedString(dateInscriptionUnesco)).append("\n");
    sb.append("    protectionJuridique: ").append(toIndentedString(protectionJuridique)).append("\n");
    sb.append("    organismeGestionnaire: ").append(toIndentedString(organismeGestionnaire)).append("\n");
    sb.append("    proprietaire: ").append(toIndentedString(proprietaire)).append("\n");
    sb.append("    gestionnaire: ").append(toIndentedString(gestionnaire)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    siteWeb: ").append(toIndentedString(siteWeb)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    accessibilite: ").append(toIndentedString(accessibilite)).append("\n");
    sb.append("    tarification: ").append(toIndentedString(tarification)).append("\n");
    sb.append("    horaires: ").append(toIndentedString(horaires)).append("\n");
    sb.append("    photos: ").append(toIndentedString(photos)).append("\n");
    sb.append("    medias: ").append(toIndentedString(medias)).append("\n");
    sb.append("    conservation: ").append(toIndentedString(conservation)).append("\n");
    sb.append("    menaces: ").append(toIndentedString(menaces)).append("\n");
    sb.append("    recommandationsVisiteur: ").append(toIndentedString(recommandationsVisiteur)).append("\n");
    sb.append("    reglesVisite: ").append(toIndentedString(reglesVisite)).append("\n");
    sb.append("    activitesTouristiques: ").append(toIndentedString(activitesTouristiques)).append("\n");
    sb.append("    meilleurePeriodeVisite: ").append(toIndentedString(meilleurePeriodeVisite)).append("\n");
    sb.append("    dureeVisiteEstimeeMinutes: ").append(toIndentedString(dureeVisiteEstimeeMinutes)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    publishedAt: ").append(toIndentedString(publishedAt)).append("\n");
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

