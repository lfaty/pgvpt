package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.*;
import com.pgvpt.exception.InvalidRequestException;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class PatrimoineMapper {

    // ============================================================
    // POLYMORPHIC DISPATCHERS
    // ============================================================

    @SubclassMapping(source = SiteNaturelCreate.class, target = SiteNaturelEntity.class)
    @SubclassMapping(source = MuseeCreate.class, target = MuseeEntity.class)
    @SubclassMapping(source = MonumentCreate.class, target = MonumentEntity.class)
    public abstract PatrimoineEntity toEntity(PatrimoineCreate dto);

    @SubclassMapping(source = SiteNaturelEntity.class, target = SiteNaturel.class)
    @SubclassMapping(source = MuseeEntity.class, target = Musee.class)
    @SubclassMapping(source = MonumentEntity.class, target = Monument.class)
    @Mapping(target = "geolocalisation", ignore = true)
    public abstract Patrimoine toDto(PatrimoineEntity entity);

    public void updateEntity(PatrimoineUpdate dto, PatrimoineEntity entity) {
        if (dto instanceof SiteNaturelUpdate site && entity instanceof SiteNaturelEntity siteEntity) {
            updateSiteNaturelEntity(site, siteEntity);
        } else if (dto instanceof MuseeUpdate musee && entity instanceof MuseeEntity museeEntity) {
            updateMuseeEntity(musee, museeEntity);
        } else if (dto instanceof MonumentUpdate monument && entity instanceof MonumentEntity monumentEntity) {
            updateMonumentEntity(monument, monumentEntity);
        } else {
            throw new InvalidRequestException("Le type concret de patrimoine est obligatoire ou ne correspond pas à l'entité");
        }
    }

    @AfterMapping
    protected void linkChildren(@MappingTarget PatrimoineEntity entity) {
        if (entity.getHoraires() != null) {
            entity.getHoraires().forEach(h -> h.setPatrimoine(entity));
        }
        if (entity.getPhotos() != null) {
            entity.getPhotos().forEach(p -> p.setPatrimoine(entity));
        }
        if (entity.getMedias() != null) {
            entity.getMedias().forEach(m -> m.setPatrimoine(entity));
        }
    }

    // ============================================================
    // CREATE DTO -> ENTITY
    // ============================================================

    @Mapping(target = "natureMonument", source = "natureMonument.value")
    @Mapping(target = "categorie", source = "categorie.value")
    @Mapping(target = "statut", source = "statut.value")
    @Mapping(target = "anneeRenovation", ignore = true)
    @Mapping(target = "architecte", ignore = true)
    public abstract MonumentEntity toMonumentEntity(MonumentCreate dto);

    @Mapping(target = "categorie", source = "categorie.value")
    @Mapping(target = "statut", source = "statut.value")
    @Mapping(target = "nombreOeuvres", ignore = true)
    @Mapping(target = "typesCollections", ignore = true)
    @Mapping(target = "servicesMusee", ignore = true)
    @Mapping(target = "capaciteAccueil", ignore = true)
    @Mapping(target = "museographie", ignore = true)
    public abstract MuseeEntity toMuseeEntity(MuseeCreate dto);

    @Mapping(target = "natureSite", source = "natureSite.value")
    @Mapping(target = "categorie", source = "categorie.value")
    @Mapping(target = "statut", source = "statut.value")
    @Mapping(target = "biodiversite", ignore = true)
    @Mapping(target = "zoneProtegee", ignore = true)
    @Mapping(target = "categorieProtection", ignore = true)
    @Mapping(target = "risquesEnvironnementaux", ignore = true)
    @Mapping(target = "ressourcesNaturelles", ignore = true)
    @Mapping(target = "activitesEcotouristiques", ignore = true)
    @Mapping(target = "capaciteAccueil", ignore = true)
    public abstract SiteNaturelEntity toSiteNaturelEntity(SiteNaturelCreate dto);

    // ============================================================
    // UPDATE DTO -> ENTITY
    // ============================================================

    @Mapping(target = "natureMonument", source = "dto.natureMonument.value")
    @Mapping(target = "categorie", source = "dto.categorie.value")
    @Mapping(target = "statut", source = "dto.statut.value")
    @Mapping(target = "anneeRenovation", ignore = true)
    @Mapping(target = "architecte", ignore = true)
    public abstract void updateMonumentEntity(MonumentUpdate dto, @MappingTarget MonumentEntity entity);

    @Mapping(target = "categorie", source = "dto.categorie.value")
    @Mapping(target = "statut", source = "dto.statut.value")
    @Mapping(target = "nombreOeuvres", ignore = true)
    @Mapping(target = "typesCollections", ignore = true)
    @Mapping(target = "servicesMusee", ignore = true)
    @Mapping(target = "capaciteAccueil", ignore = true)
    @Mapping(target = "museographie", ignore = true)
    public abstract void updateMuseeEntity(MuseeUpdate dto, @MappingTarget MuseeEntity entity);

    @Mapping(target = "natureSite", source = "dto.natureSite.value")
    @Mapping(target = "categorie", source = "dto.categorie.value")
    @Mapping(target = "statut", source = "dto.statut.value")
    @Mapping(target = "biodiversite", ignore = true)
    @Mapping(target = "zoneProtegee", ignore = true)
    @Mapping(target = "categorieProtection", ignore = true)
    @Mapping(target = "risquesEnvironnementaux", ignore = true)
    @Mapping(target = "ressourcesNaturelles", ignore = true)
    @Mapping(target = "activitesEcotouristiques", ignore = true)
    @Mapping(target = "capaciteAccueil", ignore = true)
    public abstract void updateSiteNaturelEntity(SiteNaturelUpdate dto, @MappingTarget SiteNaturelEntity entity);

    // ============================================================
    // ENTITY -> DTO
    // ============================================================

    @Mapping(target = "id", ignore = true)
    public abstract AccessibiliteEntity toAccessibiliteEntity(Accessibilite dto);
    
    @Mapping(target = "id", ignore = true)
    public abstract ConservationEntity toConservationEntity(Conservation dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patrimoine", ignore = true)
    public abstract HoraireOuvertureEntity toHoraireOuvertureEntity(HoraireOuverture dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patrimoine", ignore = true)
    public abstract PhotoEntity toPhotoEntity(PhotoCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patrimoine", ignore = true)
    public abstract PhotoEntity toPhotoEntity(Photo dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patrimoine", ignore = true)
    public abstract MediaEntity toMediaEntity(Media dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "musee", ignore = true)
    public abstract CollectionMuseeEntity toCollectionMuseeEntity(CollectionMusee dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "musee", ignore = true)
    public abstract ExpositionEntity toExpositionEntity(Exposition dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "siteNaturel", ignore = true)
    public abstract EspeceProtegeeEntity toEspeceProtegeeEntity(EspeceProtegee dto);

    @Mapping(target = "geolocalisation", ignore = true)
    public abstract Monument toMonumentDto(MonumentEntity entity);

    @Mapping(target = "geolocalisation", ignore = true)
    public abstract Musee toMuseeDto(MuseeEntity entity);

    @Mapping(target = "geolocalisation", ignore = true)
    public abstract SiteNaturel toSiteNaturelDto(SiteNaturelEntity entity);
    
    // Convertisseurs URI / String
    public String mapUriToString(java.net.URI uri) {
        return uri != null ? uri.toString() : null;
    }

    public java.net.URI mapStringToUri(String value) {
        if (value == null) return null;
        try {
            return new java.net.URI(value);
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Convertisseurs Enum/String
    public String mapNatureMonument(NatureMonument value) {
        return value != null ? value.getValue() : null;
    }
    
    public String mapNatureSiteNaturel(NatureSiteNaturel value) {
        return value != null ? value.getValue() : null;
    }
    
    public String mapCategorie(CategoriePatrimoine value) {
        return value != null ? value.getValue() : null;
    }
    
    public String mapStatut(StatutPatrimoine value) {
        return value != null ? value.getValue() : null;
    }

    public NatureMonument mapNatureMonument(String value) {
        return value != null ? NatureMonument.fromValue(value) : null;
    }
    
    public NatureSiteNaturel mapNatureSiteNaturel(String value) {
        return value != null ? NatureSiteNaturel.fromValue(value) : null;
    }
    
    public CategoriePatrimoine mapCategorie(String value) {
        return value != null ? CategoriePatrimoine.fromValue(value) : null;
    }
    
    public StatutPatrimoine mapStatut(String value) {
        return value != null ? StatutPatrimoine.fromValue(value) : null;
    }
}
