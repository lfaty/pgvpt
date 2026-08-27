package com.pgvpt.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "patrimoines")
@Inheritance(strategy = InheritanceType.JOINED)
public class PatrimoineEntity {

    public PatrimoineEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // =========================
    // Identification
    // =========================

    @Column(unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 255)
    private String nom;

    @Column(length = 255)
    private String nomLocal;

    @Column(length = 255)
    private String nomHistorique;

    @Column(nullable = false)
    private String type;

    private String categorie;

    private String sousCategorie;

    // =========================
    // Histoire
    // =========================

    private String periode;

    private String siecle;

    private LocalDate dateOuverture;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(length = 1000)
    private String descriptionCourte;

    @Column(columnDefinition = "TEXT")
    private String historique;

    @Column(columnDefinition = "TEXT")
    private String importanceHistorique;

    @Column(columnDefinition = "TEXT")
    private String importanceCulturelle;

    @Column(columnDefinition = "TEXT")
    private String importanceTouristique;

    @Column(columnDefinition = "TEXT")
    private String valeurPatrimoniale;

    @Column(columnDefinition = "TEXT")
    private String valeurSpirituelle;

    // =========================
    // Classement / protection
    // =========================

    private Boolean classePatrimoine;

    private String referenceClassement;

    private LocalDate dateClassement;

    private Boolean inscritUnesco;

    private String nomSiteUnesco;

    private LocalDate dateInscriptionUnesco;

    @Column(columnDefinition = "TEXT")
    private String protectionJuridique;

    // =========================
    // Gestion
    // =========================

    private String organismeGestionnaire;

    private String proprietaire;

    private String gestionnaire;

    private String statut;

    // =========================
    // Contact
    // =========================

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nom", column = @Column(name = "contact_nom")),
            @AttributeOverride(name = "fonction", column = @Column(name = "contact_fonction")),
            @AttributeOverride(name = "telephone", column = @Column(name = "contact_telephone")),
            @AttributeOverride(name = "email", column = @Column(name = "contact_email")),
            @AttributeOverride(name = "siteWeb", column = @Column(name = "contact_site_web"))
    })
    private ContactEntity contact;

    private String siteWeb;

    private String email;

    private String telephone;

    // =========================
    // Accessibilité
    // =========================

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "accessibilite_id")
    private AccessibiliteEntity accessibilite;

    // =========================
    // Tarification
    // =========================

    @Embedded
    private TarificationEntity tarification;

    // =========================
    // Conservation
    // =========================

    private String etatConservation;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "conservation_id")
    private ConservationEntity conservation;

    // =========================
    // Visite
    // =========================

    private String meilleurePeriodeVisite;

    private Integer dureeVisiteEstimeeMinutes;

    // =========================
    // Collections simples
    // =========================

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_traditions",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    @Column(name = "tradition")
    private List<String> traditionsAssociees = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_langues",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    @Column(name = "langue")
    private List<String> langues = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_recommandations",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    @Column(name = "recommandation")
    private List<String> recommandationsVisiteur = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_regles_visite",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    @Column(name = "regle")
    private List<String> reglesVisite = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_activites_touristiques",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    @Column(name = "activite")
    private List<String> activitesTouristiques = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "patrimoine_menaces",
            joinColumns = @JoinColumn(name = "patrimoine_id")
    )
    private List<MenaceValue> menaces = new ArrayList<>();

    // =========================
    // Relations
    // =========================

    @OneToMany(
            mappedBy = "patrimoine",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HoraireOuvertureEntity> horaires = new ArrayList<>();

    @OneToMany(
            mappedBy = "patrimoine",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhotoEntity> photos = new ArrayList<>();

    @OneToMany(
            mappedBy = "patrimoine",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MediaEntity> medias = new ArrayList<>();

    // =========================
    // Dates techniques
    // =========================

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime publishedAt;

    // =========================
    // Lifecycle
    // =========================

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNomLocal() { return nomLocal; }
    public void setNomLocal(String nomLocal) { this.nomLocal = nomLocal; }
    public String getNomHistorique() { return nomHistorique; }
    public void setNomHistorique(String nomHistorique) { this.nomHistorique = nomHistorique; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getSousCategorie() { return sousCategorie; }
    public void setSousCategorie(String sousCategorie) { this.sousCategorie = sousCategorie; }
    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }
    public String getSiecle() { return siecle; }
    public void setSiecle(String siecle) { this.siecle = siecle; }
    public LocalDate getDateOuverture() { return dateOuverture; }
    public void setDateOuverture(LocalDate dateOuverture) { this.dateOuverture = dateOuverture; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDescriptionCourte() { return descriptionCourte; }
    public void setDescriptionCourte(String descriptionCourte) { this.descriptionCourte = descriptionCourte; }
    public String getHistorique() { return historique; }
    public void setHistorique(String historique) { this.historique = historique; }
    public String getImportanceHistorique() { return importanceHistorique; }
    public void setImportanceHistorique(String importanceHistorique) { this.importanceHistorique = importanceHistorique; }
    public String getImportanceCulturelle() { return importanceCulturelle; }
    public void setImportanceCulturelle(String importanceCulturelle) { this.importanceCulturelle = importanceCulturelle; }
    public String getImportanceTouristique() { return importanceTouristique; }
    public void setImportanceTouristique(String importanceTouristique) { this.importanceTouristique = importanceTouristique; }
    public String getValeurPatrimoniale() { return valeurPatrimoniale; }
    public void setValeurPatrimoniale(String valeurPatrimoniale) { this.valeurPatrimoniale = valeurPatrimoniale; }
    public String getValeurSpirituelle() { return valeurSpirituelle; }
    public void setValeurSpirituelle(String valeurSpirituelle) { this.valeurSpirituelle = valeurSpirituelle; }
    public Boolean getClassePatrimoine() { return classePatrimoine; }
    public void setClassePatrimoine(Boolean classePatrimoine) { this.classePatrimoine = classePatrimoine; }
    public String getReferenceClassement() { return referenceClassement; }
    public void setReferenceClassement(String referenceClassement) { this.referenceClassement = referenceClassement; }
    public LocalDate getDateClassement() { return dateClassement; }
    public void setDateClassement(LocalDate dateClassement) { this.dateClassement = dateClassement; }
    public Boolean getInscritUnesco() { return inscritUnesco; }
    public void setInscritUnesco(Boolean inscritUnesco) { this.inscritUnesco = inscritUnesco; }
    public String getNomSiteUnesco() { return nomSiteUnesco; }
    public void setNomSiteUnesco(String nomSiteUnesco) { this.nomSiteUnesco = nomSiteUnesco; }
    public LocalDate getDateInscriptionUnesco() { return dateInscriptionUnesco; }
    public void setDateInscriptionUnesco(LocalDate dateInscriptionUnesco) { this.dateInscriptionUnesco = dateInscriptionUnesco; }
    public String getProtectionJuridique() { return protectionJuridique; }
    public void setProtectionJuridique(String protectionJuridique) { this.protectionJuridique = protectionJuridique; }
    public String getOrganismeGestionnaire() { return organismeGestionnaire; }
    public void setOrganismeGestionnaire(String organismeGestionnaire) { this.organismeGestionnaire = organismeGestionnaire; }
    public String getProprietaire() { return proprietaire; }
    public void setProprietaire(String proprietaire) { this.proprietaire = proprietaire; }
    public String getGestionnaire() { return gestionnaire; }
    public void setGestionnaire(String gestionnaire) { this.gestionnaire = gestionnaire; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public ContactEntity getContact() { return contact; }
    public void setContact(ContactEntity contact) { this.contact = contact; }
    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public AccessibiliteEntity getAccessibilite() { return accessibilite; }
    public void setAccessibilite(AccessibiliteEntity accessibilite) { this.accessibilite = accessibilite; }
    public TarificationEntity getTarification() { return tarification; }
    public void setTarification(TarificationEntity tarification) { this.tarification = tarification; }
    public String getEtatConservation() { return etatConservation; }
    public void setEtatConservation(String etatConservation) { this.etatConservation = etatConservation; }
    public ConservationEntity getConservation() { return conservation; }
    public void setConservation(ConservationEntity conservation) { this.conservation = conservation; }
    public String getMeilleurePeriodeVisite() { return meilleurePeriodeVisite; }
    public void setMeilleurePeriodeVisite(String meilleurePeriodeVisite) { this.meilleurePeriodeVisite = meilleurePeriodeVisite; }
    public Integer getDureeVisiteEstimeeMinutes() { return dureeVisiteEstimeeMinutes; }
    public void setDureeVisiteEstimeeMinutes(Integer dureeVisiteEstimeeMinutes) { this.dureeVisiteEstimeeMinutes = dureeVisiteEstimeeMinutes; }
    public List<String> getTraditionsAssociees() { return traditionsAssociees; }
    public void setTraditionsAssociees(List<String> traditionsAssociees) { this.traditionsAssociees = traditionsAssociees; }
    public List<String> getLangues() { return langues; }
    public void setLangues(List<String> langues) { this.langues = langues; }
    public List<String> getRecommandationsVisiteur() { return recommandationsVisiteur; }
    public void setRecommandationsVisiteur(List<String> recommandationsVisiteur) { this.recommandationsVisiteur = recommandationsVisiteur; }
    public List<String> getReglesVisite() { return reglesVisite; }
    public void setReglesVisite(List<String> reglesVisite) { this.reglesVisite = reglesVisite; }
    public List<String> getActivitesTouristiques() { return activitesTouristiques; }
    public void setActivitesTouristiques(List<String> activitesTouristiques) { this.activitesTouristiques = activitesTouristiques; }
    public List<MenaceValue> getMenaces() { return menaces; }
    public void setMenaces(List<MenaceValue> menaces) { this.menaces = menaces; }
    public List<HoraireOuvertureEntity> getHoraires() { return horaires; }
    public void setHoraires(List<HoraireOuvertureEntity> horaires) { this.horaires = horaires; }
    public List<PhotoEntity> getPhotos() { return photos; }
    public void setPhotos(List<PhotoEntity> photos) { this.photos = photos; }
    public List<MediaEntity> getMedias() { return medias; }
    public void setMedias(List<MediaEntity> medias) { this.medias = medias; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public OffsetDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(OffsetDateTime publishedAt) { this.publishedAt = publishedAt; }
}
