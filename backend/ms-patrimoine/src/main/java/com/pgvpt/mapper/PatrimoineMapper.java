package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.embeddable.HoraireOuvertureEmbeddable;
import com.pgvpt.enums.CategoriePatrimoineMetier;
import com.pgvpt.enums.EtatConservationMetier;
import com.pgvpt.enums.StatutPatrimoineMetier;
import com.pgvpt.enums.TypePatrimoineMetier;
import com.pgvpt.model.*;
import org.mapstruct.*;

import com.pgvpt.record.PatrimoineSearchCriteria;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.net.URI;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatrimoineMapper {

    // --- Vers Entity (Création / Mutation) ---
    default PatrimoineEntity toEntity(PatrimoineCreate dto) {
        if (dto == null) return null;
        if (dto instanceof SiteNaturelCreate sn) return toSiteNaturelEntity(sn);
        if (dto instanceof MuseeCreate m) return toMuseeEntity(m);
        if (dto instanceof MonumentCreate mon) return toMonumentEntity(mon);
        throw new IllegalArgumentException("Type de patrimoine inconnu");
    }

    SiteNaturelEntity toSiteNaturelEntity(SiteNaturelCreate dto);
    MuseeEntity toMuseeEntity(MuseeCreate dto);
    MonumentEntity toMonumentEntity(MonumentCreate dto);

    // --- Vers DTO (Lecture) ---
    default Patrimoine toDto(PatrimoineEntity entity) {
        if (entity == null) return null;
        if (entity instanceof SiteNaturelEntity sn) return toSiteNaturelDto(sn);
        if (entity instanceof MuseeEntity m) return toMuseeDto(m);
        if (entity instanceof MonumentEntity mon) return toMonumentDto(mon);
        return toBaseDto(entity);
    }

    SiteNaturel toSiteNaturelDto(SiteNaturelEntity entity);
    Musee toMuseeDto(MuseeEntity entity);
    Monument toMonumentDto(MonumentEntity entity);
    Patrimoine toBaseDto(PatrimoineEntity entity);

    // --- Updates In-Place (CORRIGÉ POUR LE POLYMORPHISME) ---
    default void updateEntity(PatrimoineUpdate dto, @MappingTarget PatrimoineEntity entity) {
        if (dto == null || entity == null) return;
        if (dto instanceof SiteNaturelUpdate sn && entity instanceof SiteNaturelEntity snEntity) {
            updateSiteNaturelEntity(sn, snEntity);
        } else if (dto instanceof MuseeUpdate m && entity instanceof MuseeEntity mEntity) {
            updateMuseeEntity(m, mEntity);
        } else if (dto instanceof MonumentUpdate mon && entity instanceof MonumentEntity monEntity) {
            updateMonumentEntity(mon, monEntity);
        } else {
            throw new IllegalArgumentException("Incompatibilité de type pour la mise à jour polymorphe");
        }
    }

    void updateSiteNaturelEntity(SiteNaturelUpdate dto, @MappingTarget SiteNaturelEntity entity);
    void updateMuseeEntity(MuseeUpdate dto, @MappingTarget MuseeEntity entity);
    void updateMonumentEntity(MonumentUpdate dto, @MappingTarget MonumentEntity entity);

    // --- Sub-Objects Mappings ---
    HoraireOuverture toHoraireDto(HoraireOuvertureEmbeddable embeddable);
    HoraireOuvertureEmbeddable toHoraireEmbeddable(HoraireOuverture dto);
    List<HoraireOuverture> toHoraireDtoList(List<HoraireOuvertureEmbeddable> list);
//    List<HoraireOuvertureEmbeddable> toHoraireEmbeddableList(List<HoraireOuverture> list);

    List<HoraireOuvertureEmbeddable> toHoraireEmbeddableList(List<HoraireOuverture> dtos);

    @Mapping(target = "patrimoineId", source = "patrimoine.id")
    @Mapping(target = "contactConservateurId", source = "contactConservateur.id")
    Conservation toConservationDto(ConservationEntity entity);

    @Mapping(target = "patrimoine", ignore = true)
    @Mapping(target = "contactConservateur", ignore = true)
    void updateConservationEntity(Conservation dto, @MappingTarget ConservationEntity entity);

    PatrimoineSummary toSummaryDto(PatrimoineEntity entity);
    List<PatrimoineSummary> toSummaryDtoList(List<PatrimoineEntity> list);

    // --- ENUMS & CRITERIA (CORRIGÉ ET SÉCURISÉ) ---
    CategoriePatrimoineMetier toCategorie(CategoriePatrimoine source);
    TypePatrimoineMetier toType(TypePatrimoine source);
    StatutPatrimoineMetier toStatut(StatutPatrimoine source);
    EtatConservationMetier toEtat(EtatConservation source);

    PatrimoineSearchCriteria toCriteria(
            CategoriePatrimoine categorie,
            TypePatrimoine type,
            StatutPatrimoine statut,
            EtatConservation etatConservation,
            Boolean accessiblePublic,
            Boolean inscritUnesco,
            Boolean classePatrimoine,
            String q
    );

    // --- LIAISON BIDIRECTIONNELLE AUTOMATIQUE MAPSTRUCT ---
    @AfterMapping
    default void lierEspecesAuSiteNaturel(@MappingTarget SiteNaturelEntity site) {
        if (site != null && site.getEspecesProtegees() != null) {
            site.getEspecesProtegees().forEach(espece -> espece.setSiteNaturel(site));
        }
    }

    @AfterMapping
    default void lierEspecesAuSiteNaturelUpdate(SiteNaturelUpdate dto, @MappingTarget SiteNaturelEntity site) {
        if (site != null && site.getEspecesProtegees() != null) {
            site.getEspecesProtegees().forEach(espece -> espece.setSiteNaturel(site));
        }
    }

    @AfterMapping
    default void lierCollectionsEtExpositionsAuMusee(@MappingTarget MuseeEntity musee) {
        if (musee != null) {
            if (musee.getCollections() != null) {
                // Remplacer 'setMusee' par le nom exact de votre setter dans CollectionMuseeEntity
                musee.getCollections().forEach(collection -> collection.setMusee(musee));
            }
            if (musee.getExpositions() != null) {
                // Remplacer 'setMusee' par le nom exact de votre setter dans ExpositionEntity
                musee.getExpositions().forEach(exposition -> exposition.setMusee(musee));
            }
        }
    }

    @AfterMapping
    default void lierCollectionsEtExpositionsAuMuseeUpdate(MuseeUpdate dto, @MappingTarget MuseeEntity musee) {
        lierCollectionsEtExpositionsAuMusee(musee);
    }

    @Mapping(target = "id", ignore = true)
    void updateEntityFromPatch(PatrimoinePatch dto, @MappingTarget PatrimoineEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateSiteNaturelEntityFromPatch(PatrimoinePatch dto, @MappingTarget SiteNaturelEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateMuseeEntityFromPatch(PatrimoinePatch dto, @MappingTarget MuseeEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateMonumentEntityFromPatch(PatrimoinePatch dto, @MappingTarget MonumentEntity entity);




    // --- UTILITAIRES TEMPORELS ---
    default Instant map(OffsetDateTime value) { return value == null ? null : value.toInstant(); }
    default OffsetDateTime map(Instant value) { return value == null ? null : value.atOffset(ZoneOffset.UTC); }

    // --- UTILITAIRES URI ---
    default String map(URI value) { return value == null ? null : value.toString(); }
    default URI map(String value) { return value == null ? null : URI.create(value); }
}

