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
import com.pgvpt.dto.EspeceProtegee;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.dto.Media;
import com.pgvpt.dto.Menace;
import com.pgvpt.dto.NatureSiteNaturel;
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
 * SiteNaturelCreate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SiteNaturelCreate implements PatrimoineCreate {

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
  public SiteNaturelCreate(String nom, String type, CategoriePatrimoine categorie, String description, Double superficie, NatureSiteNaturel natureSite, String ecosysteme) {
    this.nom = nom;
    this.type = type;
    this.categorie = categorie;
    this.description = description;
    this.superficie = superficie;
    this.natureSite = natureSite;
    this.ecosysteme = ecosysteme;
  }

  public SiteNaturelCreate id(UUID id) {
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

  public SiteNaturelCreate code(String code) {
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

  public SiteNaturelCreate nom(String nom) {
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

  public SiteNaturelCreate nomLocal(String nomLocal) {
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

  public SiteNaturelCreate nomHistorique(String nomHistorique) {
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

  public SiteNaturelCreate type(String type) {
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

  public SiteNaturelCreate categorie(CategoriePatrimoine categorie) {
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

  public SiteNaturelCreate sousCategorie(String sousCategorie) {
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

  public SiteNaturelCreate periode(PeriodeHistorique periode) {
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

  public SiteNaturelCreate siecle(String siecle) {
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

  public SiteNaturelCreate dateOuverture(LocalDate dateOuverture) {
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

  public SiteNaturelCreate description(String description) {
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

  public SiteNaturelCreate descriptionCourte(String descriptionCourte) {
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

  public SiteNaturelCreate historique(String historique) {
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

  public SiteNaturelCreate importanceHistorique(String importanceHistorique) {
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

  public SiteNaturelCreate importanceCulturelle(String importanceCulturelle) {
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

  public SiteNaturelCreate importanceTouristique(String importanceTouristique) {
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

  public SiteNaturelCreate valeurPatrimoniale(String valeurPatrimoniale) {
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

  public SiteNaturelCreate valeurSpirituelle(String valeurSpirituelle) {
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

  public SiteNaturelCreate traditionsAssociees(List<String> traditionsAssociees) {
    this.traditionsAssociees = traditionsAssociees;
    return this;
  }

  public SiteNaturelCreate addTraditionsAssocieesItem(String traditionsAssocieesItem) {
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

  public SiteNaturelCreate langues(List<String> langues) {
    this.langues = langues;
    return this;
  }

  public SiteNaturelCreate addLanguesItem(String languesItem) {
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

  public SiteNaturelCreate etatConservation(EtatConservation etatConservation) {
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

  public SiteNaturelCreate statut(StatutPatrimoine statut) {
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

  public SiteNaturelCreate classePatrimoine(Boolean classePatrimoine) {
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

  public SiteNaturelCreate referenceClassement(String referenceClassement) {
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

  public SiteNaturelCreate dateClassement(LocalDate dateClassement) {
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

  public SiteNaturelCreate inscritUnesco(Boolean inscritUnesco) {
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

  public SiteNaturelCreate nomSiteUnesco(String nomSiteUnesco) {
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

  public SiteNaturelCreate dateInscriptionUnesco(LocalDate dateInscriptionUnesco) {
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

  public SiteNaturelCreate protectionJuridique(String protectionJuridique) {
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

  public SiteNaturelCreate organismeGestionnaire(String organismeGestionnaire) {
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

  public SiteNaturelCreate proprietaire(String proprietaire) {
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

  public SiteNaturelCreate gestionnaire(String gestionnaire) {
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

  public SiteNaturelCreate contact(Contact contact) {
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

  public SiteNaturelCreate siteWeb(URI siteWeb) {
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

  public SiteNaturelCreate email(String email) {
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

  public SiteNaturelCreate telephone(String telephone) {
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

  public SiteNaturelCreate accessibilite(Accessibilite accessibilite) {
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

  public SiteNaturelCreate tarification(Tarification tarification) {
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

  public SiteNaturelCreate horaires(List<@Valid HoraireOuverture> horaires) {
    this.horaires = horaires;
    return this;
  }

  public SiteNaturelCreate addHorairesItem(HoraireOuverture horairesItem) {
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

  public SiteNaturelCreate photos(List<@Valid Photo> photos) {
    this.photos = photos;
    return this;
  }

  public SiteNaturelCreate addPhotosItem(Photo photosItem) {
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

  public SiteNaturelCreate medias(List<@Valid Media> medias) {
    this.medias = medias;
    return this;
  }

  public SiteNaturelCreate addMediasItem(Media mediasItem) {
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

  public SiteNaturelCreate conservation(Conservation conservation) {
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

  public SiteNaturelCreate menaces(List<@Valid Menace> menaces) {
    this.menaces = menaces;
    return this;
  }

  public SiteNaturelCreate addMenacesItem(Menace menacesItem) {
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

  public SiteNaturelCreate recommandationsVisiteur(List<String> recommandationsVisiteur) {
    this.recommandationsVisiteur = recommandationsVisiteur;
    return this;
  }

  public SiteNaturelCreate addRecommandationsVisiteurItem(String recommandationsVisiteurItem) {
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

  public SiteNaturelCreate reglesVisite(List<String> reglesVisite) {
    this.reglesVisite = reglesVisite;
    return this;
  }

  public SiteNaturelCreate addReglesVisiteItem(String reglesVisiteItem) {
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

  public SiteNaturelCreate activitesTouristiques(List<String> activitesTouristiques) {
    this.activitesTouristiques = activitesTouristiques;
    return this;
  }

  public SiteNaturelCreate addActivitesTouristiquesItem(String activitesTouristiquesItem) {
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

  public SiteNaturelCreate meilleurePeriodeVisite(String meilleurePeriodeVisite) {
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

  public SiteNaturelCreate dureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) {
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

  public SiteNaturelCreate createdAt(OffsetDateTime createdAt) {
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

  public SiteNaturelCreate updatedAt(OffsetDateTime updatedAt) {
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

  public SiteNaturelCreate publishedAt(OffsetDateTime publishedAt) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SiteNaturelCreate siteNaturelCreate = (SiteNaturelCreate) o;
    return Objects.equals(this.id, siteNaturelCreate.id) &&
        Objects.equals(this.code, siteNaturelCreate.code) &&
        Objects.equals(this.nom, siteNaturelCreate.nom) &&
        Objects.equals(this.nomLocal, siteNaturelCreate.nomLocal) &&
        Objects.equals(this.nomHistorique, siteNaturelCreate.nomHistorique) &&
        Objects.equals(this.type, siteNaturelCreate.type) &&
        Objects.equals(this.categorie, siteNaturelCreate.categorie) &&
        Objects.equals(this.sousCategorie, siteNaturelCreate.sousCategorie) &&
        Objects.equals(this.periode, siteNaturelCreate.periode) &&
        Objects.equals(this.siecle, siteNaturelCreate.siecle) &&
        Objects.equals(this.dateOuverture, siteNaturelCreate.dateOuverture) &&
        Objects.equals(this.description, siteNaturelCreate.description) &&
        Objects.equals(this.descriptionCourte, siteNaturelCreate.descriptionCourte) &&
        Objects.equals(this.historique, siteNaturelCreate.historique) &&
        Objects.equals(this.importanceHistorique, siteNaturelCreate.importanceHistorique) &&
        Objects.equals(this.importanceCulturelle, siteNaturelCreate.importanceCulturelle) &&
        Objects.equals(this.importanceTouristique, siteNaturelCreate.importanceTouristique) &&
        Objects.equals(this.valeurPatrimoniale, siteNaturelCreate.valeurPatrimoniale) &&
        Objects.equals(this.valeurSpirituelle, siteNaturelCreate.valeurSpirituelle) &&
        Objects.equals(this.traditionsAssociees, siteNaturelCreate.traditionsAssociees) &&
        Objects.equals(this.langues, siteNaturelCreate.langues) &&
        Objects.equals(this.etatConservation, siteNaturelCreate.etatConservation) &&
        Objects.equals(this.statut, siteNaturelCreate.statut) &&
        Objects.equals(this.classePatrimoine, siteNaturelCreate.classePatrimoine) &&
        Objects.equals(this.referenceClassement, siteNaturelCreate.referenceClassement) &&
        Objects.equals(this.dateClassement, siteNaturelCreate.dateClassement) &&
        Objects.equals(this.inscritUnesco, siteNaturelCreate.inscritUnesco) &&
        Objects.equals(this.nomSiteUnesco, siteNaturelCreate.nomSiteUnesco) &&
        Objects.equals(this.dateInscriptionUnesco, siteNaturelCreate.dateInscriptionUnesco) &&
        Objects.equals(this.protectionJuridique, siteNaturelCreate.protectionJuridique) &&
        Objects.equals(this.organismeGestionnaire, siteNaturelCreate.organismeGestionnaire) &&
        Objects.equals(this.proprietaire, siteNaturelCreate.proprietaire) &&
        Objects.equals(this.gestionnaire, siteNaturelCreate.gestionnaire) &&
        Objects.equals(this.contact, siteNaturelCreate.contact) &&
        Objects.equals(this.siteWeb, siteNaturelCreate.siteWeb) &&
        Objects.equals(this.email, siteNaturelCreate.email) &&
        Objects.equals(this.telephone, siteNaturelCreate.telephone) &&
        Objects.equals(this.accessibilite, siteNaturelCreate.accessibilite) &&
        Objects.equals(this.tarification, siteNaturelCreate.tarification) &&
        Objects.equals(this.horaires, siteNaturelCreate.horaires) &&
        Objects.equals(this.photos, siteNaturelCreate.photos) &&
        Objects.equals(this.medias, siteNaturelCreate.medias) &&
        Objects.equals(this.conservation, siteNaturelCreate.conservation) &&
        Objects.equals(this.menaces, siteNaturelCreate.menaces) &&
        Objects.equals(this.recommandationsVisiteur, siteNaturelCreate.recommandationsVisiteur) &&
        Objects.equals(this.reglesVisite, siteNaturelCreate.reglesVisite) &&
        Objects.equals(this.activitesTouristiques, siteNaturelCreate.activitesTouristiques) &&
        Objects.equals(this.meilleurePeriodeVisite, siteNaturelCreate.meilleurePeriodeVisite) &&
        Objects.equals(this.dureeVisiteEstimeeMinutes, siteNaturelCreate.dureeVisiteEstimeeMinutes) &&
        Objects.equals(this.createdAt, siteNaturelCreate.createdAt) &&
        Objects.equals(this.updatedAt, siteNaturelCreate.updatedAt) &&
        Objects.equals(this.publishedAt, siteNaturelCreate.publishedAt) &&
        Objects.equals(this.superficie, siteNaturelCreate.superficie) &&
        Objects.equals(this.natureSite, siteNaturelCreate.natureSite) &&
        Objects.equals(this.ecosysteme, siteNaturelCreate.ecosysteme) &&
        Objects.equals(this.especesProtegees, siteNaturelCreate.especesProtegees);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, nom, nomLocal, nomHistorique, type, categorie, sousCategorie, periode, siecle, dateOuverture, description, descriptionCourte, historique, importanceHistorique, importanceCulturelle, importanceTouristique, valeurPatrimoniale, valeurSpirituelle, traditionsAssociees, langues, etatConservation, statut, classePatrimoine, referenceClassement, dateClassement, inscritUnesco, nomSiteUnesco, dateInscriptionUnesco, protectionJuridique, organismeGestionnaire, proprietaire, gestionnaire, contact, siteWeb, email, telephone, accessibilite, tarification, horaires, photos, medias, conservation, menaces, recommandationsVisiteur, reglesVisite, activitesTouristiques, meilleurePeriodeVisite, dureeVisiteEstimeeMinutes, createdAt, updatedAt, publishedAt, superficie, natureSite, ecosysteme, especesProtegees);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SiteNaturelCreate {\n");
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

