package com.pgvpt.entities;

import com.pgvpt.dto.*;
import com.pgvpt.enums.Categorie;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Patrimoine {

    private UUID id;

    private String code;

    private String nom;

    private String nomLocal;

    private String nomHistorique;

    private TypePatrimoine type;

    private Categorie categorie;

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

    private Localisation localisation;

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
}
