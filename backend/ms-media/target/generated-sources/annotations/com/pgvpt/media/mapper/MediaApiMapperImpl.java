package com.pgvpt.media.mapper;

import com.pgvpt.dto.Media;
import com.pgvpt.dto.MediaCreate;
import com.pgvpt.dto.TypeMedia;
import com.pgvpt.media.model.MediaEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-11T15:43:54+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.100.v20260826-1225, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
@Component
public class MediaApiMapperImpl implements MediaApiMapper {

    @Override
    public MediaEntity toEntity(MediaCreate dto) {
        if ( dto == null ) {
            return null;
        }

        MediaEntity mediaEntity = new MediaEntity();

        mediaEntity.setPatrimoineId( dto.getPatrimoineId() );
        mediaEntity.setType( toEntityEnum( dto.getType() ) );
        mediaEntity.setNom( dto.getNom() );
        mediaEntity.setDescription( dto.getDescription() );
        mediaEntity.setUrl( mapUriToString( dto.getUrl() ) );
        mediaEntity.setMimeType( dto.getMimeType() );
        mediaEntity.setTailleOctets( dto.getTailleOctets() );
        mediaEntity.setLangue( dto.getLangue() );
        mediaEntity.setAuteur( dto.getAuteur() );
        mediaEntity.setDroitsUtilisation( dto.getDroitsUtilisation() );
        mediaEntity.setCredit( dto.getCredit() );
        if ( dto.getLatitude() != null ) {
            mediaEntity.setLatitude( BigDecimal.valueOf( dto.getLatitude() ) );
        }
        if ( dto.getLongitude() != null ) {
            mediaEntity.setLongitude( BigDecimal.valueOf( dto.getLongitude() ) );
        }

        return mediaEntity;
    }

    @Override
    public Media toApi(MediaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Media media = new Media();

        media.setId( entity.getId() );
        media.setCreatedAt( map( entity.getCreatedAt() ) );
        media.setUpdatedAt( map( entity.getUpdatedAt() ) );
        media.setPatrimoineId( entity.getPatrimoineId() );
        media.setType( typeMediaToTypeMedia( entity.getType() ) );
        media.setNom( entity.getNom() );
        media.setDescription( entity.getDescription() );
        media.setUrl( mapStringToUri( entity.getUrl() ) );
        media.setMimeType( entity.getMimeType() );
        media.setTailleOctets( entity.getTailleOctets() );
        media.setLangue( entity.getLangue() );
        media.setAuteur( entity.getAuteur() );
        media.setDroitsUtilisation( entity.getDroitsUtilisation() );
        media.setCredit( entity.getCredit() );
        if ( entity.getLatitude() != null ) {
            media.setLatitude( entity.getLatitude().doubleValue() );
        }
        if ( entity.getLongitude() != null ) {
            media.setLongitude( entity.getLongitude().doubleValue() );
        }

        return media;
    }

    @Override
    public List<Media> toApi(List<MediaEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Media> list = new ArrayList<Media>( entities.size() );
        for ( MediaEntity mediaEntity : entities ) {
            list.add( toApi( mediaEntity ) );
        }

        return list;
    }

    @Override
    public com.pgvpt.media.enums.TypeMedia toEntityEnum(TypeMedia typeMedia) {
        if ( typeMedia == null ) {
            return null;
        }

        com.pgvpt.media.enums.TypeMedia typeMedia1;

        switch ( typeMedia ) {
            case ARCHIVE_NUMERISEE: typeMedia1 = com.pgvpt.media.enums.TypeMedia.ARCHIVE_NUMERISEE;
            break;
            case AUDIO: typeMedia1 = com.pgvpt.media.enums.TypeMedia.AUDIO;
            break;
            case CARTE: typeMedia1 = com.pgvpt.media.enums.TypeMedia.CARTE;
            break;
            case DOCUMENT: typeMedia1 = com.pgvpt.media.enums.TypeMedia.DOCUMENT;
            break;
            case MODELE_3_D: typeMedia1 = com.pgvpt.media.enums.TypeMedia.MODELE_3_D;
            break;
            case PANORAMA_360: typeMedia1 = com.pgvpt.media.enums.TypeMedia.PANORAMA_360;
            break;
            case PHOTO: typeMedia1 = com.pgvpt.media.enums.TypeMedia.PHOTO;
            break;
            case VIDEO: typeMedia1 = com.pgvpt.media.enums.TypeMedia.VIDEO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + typeMedia );
        }

        return typeMedia1;
    }

    protected TypeMedia typeMediaToTypeMedia(com.pgvpt.media.enums.TypeMedia typeMedia) {
        if ( typeMedia == null ) {
            return null;
        }

        TypeMedia typeMedia1;

        switch ( typeMedia ) {
            case ARCHIVE_NUMERISEE: typeMedia1 = TypeMedia.ARCHIVE_NUMERISEE;
            break;
            case AUDIO: typeMedia1 = TypeMedia.AUDIO;
            break;
            case CARTE: typeMedia1 = TypeMedia.CARTE;
            break;
            case DOCUMENT: typeMedia1 = TypeMedia.DOCUMENT;
            break;
            case MODELE_3_D: typeMedia1 = TypeMedia.MODELE_3_D;
            break;
            case PANORAMA_360: typeMedia1 = TypeMedia.PANORAMA_360;
            break;
            case PHOTO: typeMedia1 = TypeMedia.PHOTO;
            break;
            case VIDEO: typeMedia1 = TypeMedia.VIDEO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + typeMedia );
        }

        return typeMedia1;
    }
}
