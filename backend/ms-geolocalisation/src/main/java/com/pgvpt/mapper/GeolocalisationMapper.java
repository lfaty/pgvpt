package com.pgvpt.mapper;

import com.pgvpt.dto.GeolocalisationCreate;
import com.pgvpt.dto.Geolocalisation;
import com.pgvpt.dto.GeolocalisationUpdate;
import com.pgvpt.enums.CategoriePatrimoineMetier;
import com.pgvpt.enums.TypePatrimoineMetier;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.model.PointAccesEntity;
import org.mapstruct.*;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import com.pgvpt.dto.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GeolocalisationMapper {

    GeometryFactory GEOMETRY_FACTORY = new GeometryFactory(new PrecisionModel(), 4326);

    // --- Mappings pour Geolocalisation ---

    @Mapping(target = "geom", source = ".", qualifiedByName = "dtoToPoint")
    GeolocalisationEntity toEntity(GeolocalisationCreate dto);

    @Mapping(target = "geom", source = ".", qualifiedByName = "dtoToPoint")
    void updateEntity(GeolocalisationUpdate dto, @MappingTarget GeolocalisationEntity entity);

    Geolocalisation toDto(GeolocalisationEntity entity);

    // --- Mappings pour PointAcces ---

    @Mapping(target = "geom", source = ".", qualifiedByName = "dtoToPoint")
    PointAccesEntity toEntity(PointAccesCreate dto);

    // Correction cruciale : ajout du mapping de la géométrie pour la mise à jour
    @Mapping(target = "geom", source = ".", qualifiedByName = "dtoToPoint")
    void updateEntity(PointAccesCreate dto, @MappingTarget PointAccesEntity entity);

    PointAcces toDto(PointAccesEntity entity);

    java.util.List<PointAcces> toPointAccesDtoList(java.util.List<PointAccesEntity> list);

    // --- Méthode de conversion centralisée et partagée ---

    @Named("dtoToPoint")
    default Point mapCoordinatesToPoint(Object dto) {
        if (dto == null) return null;

        Double longitude = null;
        Double latitude = null;

        // Extraction par réflexion ou via des structures de contrôle simples
        // (MapStruct gérant l'appel au niveau de l'objet global source = ".")
        if (dto instanceof GeolocalisationCreate d) {
            longitude = d.getLongitude();
            latitude = d.getLatitude();
        } else if (dto instanceof GeolocalisationUpdate d) {
            longitude = d.getLongitude();
            latitude = d.getLatitude();
        } else if (dto instanceof PointAccesCreate d) {
            longitude = d.getLongitude();
            latitude = d.getLatitude();
        }

        if (longitude == null || latitude == null) {
            return null;
        }

        return GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
    }

    // --- Méthodes de conversion des Enums ---

    TypePatrimoineMetier toType(TypePatrimoine source);

    CategoriePatrimoineMetier toCategorie(CategoriePatrimoine source);

    CategoriePatrimoine toCategorieOpenApi(CategoriePatrimoineMetier source);

    // CONVERSIONS UTILES
    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) { return null; }
        return value.atOffset(ZoneOffset.UTC);
    }
}