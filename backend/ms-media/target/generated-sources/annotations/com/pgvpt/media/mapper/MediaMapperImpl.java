package com.pgvpt.media.mapper;

import com.pgvpt.dto.Media;
import com.pgvpt.dto.MediaCreate;
import com.pgvpt.dto.TypeMedia;
import com.pgvpt.media.enums.TypeMediaMetier;
import com.pgvpt.media.model.MediaEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-04T16:06:47+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 26.0.2.1 (Eclipse Adoptium)"
)
@Component
public class MediaMapperImpl implements MediaMapper {

    @Override
    public MediaEntity toEntity(MediaCreate dto) {
        if ( dto == null ) {
            return null;
        }

        MediaEntity mediaEntity = new MediaEntity();

        mediaEntity.setPatrimoineId( dto.getPatrimoineId() );
        mediaEntity.setType( toTypeMetier( dto.getType() ) );
        mediaEntity.setNom( dto.getNom() );
        mediaEntity.setDescription( dto.getDescription() );
        mediaEntity.setUrl( map( dto.getUrl() ) );
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
        if ( dto.getStatut() != null ) {
            mediaEntity.setStatut( toStatutMetier( dto.getStatut().name() ) );
        }
        mediaEntity.setChecksumSha256( dto.getChecksumSha256() );

        return mediaEntity;
    }

    @Override
    public Media toDto(MediaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Media media = new Media();

        media.setId( entity.getId() );
        media.setCreatedAt( map( entity.getCreatedAt() ) );
        media.setUpdatedAt( map( entity.getUpdatedAt() ) );
        media.setPatrimoineId( entity.getPatrimoineId() );
        media.setType( toTypeDto( entity.getType() ) );
        media.setNom( entity.getNom() );
        media.setDescription( entity.getDescription() );
        media.setUrl( map( entity.getUrl() ) );
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
        if ( entity.getStatut() != null ) {
            media.setStatut( Enum.valueOf( Media.StatutEnum.class, toStatutDto( entity.getStatut() ) ) );
        }
        media.setChecksumSha256( entity.getChecksumSha256() );

        return media;
    }

    @Override
    public List<Media> toDtoList(List<MediaEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Media> list = new ArrayList<Media>( entities.size() );
        for ( MediaEntity mediaEntity : entities ) {
            list.add( toDto( mediaEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(MediaCreate dto, MediaEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPatrimoineId() != null ) {
            entity.setPatrimoineId( dto.getPatrimoineId() );
        }
        if ( dto.getType() != null ) {
            entity.setType( toTypeMetier( dto.getType() ) );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getUrl() != null ) {
            entity.setUrl( map( dto.getUrl() ) );
        }
        if ( dto.getMimeType() != null ) {
            entity.setMimeType( dto.getMimeType() );
        }
        if ( dto.getTailleOctets() != null ) {
            entity.setTailleOctets( dto.getTailleOctets() );
        }
        if ( dto.getLangue() != null ) {
            entity.setLangue( dto.getLangue() );
        }
        if ( dto.getAuteur() != null ) {
            entity.setAuteur( dto.getAuteur() );
        }
        if ( dto.getDroitsUtilisation() != null ) {
            entity.setDroitsUtilisation( dto.getDroitsUtilisation() );
        }
        if ( dto.getCredit() != null ) {
            entity.setCredit( dto.getCredit() );
        }
        if ( dto.getLatitude() != null ) {
            entity.setLatitude( BigDecimal.valueOf( dto.getLatitude() ) );
        }
        if ( dto.getLongitude() != null ) {
            entity.setLongitude( BigDecimal.valueOf( dto.getLongitude() ) );
        }
        if ( dto.getStatut() != null ) {
            entity.setStatut( toStatutMetier( dto.getStatut().name() ) );
        }
        if ( dto.getChecksumSha256() != null ) {
            entity.setChecksumSha256( dto.getChecksumSha256() );
        }
    }

    @Override
    public TypeMediaMetier toTypeMetier(TypeMedia source) {
        if ( source == null ) {
            return null;
        }

        TypeMediaMetier typeMediaMetier;

        switch ( source ) {
            case MODELE_3_D: typeMediaMetier = TypeMediaMetier.MODELE_3D;
            break;
            case PHOTO: typeMediaMetier = TypeMediaMetier.PHOTO;
            break;
            case VIDEO: typeMediaMetier = TypeMediaMetier.VIDEO;
            break;
            case AUDIO: typeMediaMetier = TypeMediaMetier.AUDIO;
            break;
            case DOCUMENT: typeMediaMetier = TypeMediaMetier.DOCUMENT;
            break;
            case PANORAMA_360: typeMediaMetier = TypeMediaMetier.PANORAMA_360;
            break;
            case CARTE: typeMediaMetier = TypeMediaMetier.CARTE;
            break;
            case ARCHIVE_NUMERISEE: typeMediaMetier = TypeMediaMetier.ARCHIVE_NUMERISEE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return typeMediaMetier;
    }

    @Override
    public TypeMedia toTypeDto(TypeMediaMetier source) {
        if ( source == null ) {
            return null;
        }

        TypeMedia typeMedia;

        switch ( source ) {
            case MODELE_3D: typeMedia = TypeMedia.MODELE_3_D;
            break;
            case PHOTO: typeMedia = TypeMedia.PHOTO;
            break;
            case VIDEO: typeMedia = TypeMedia.VIDEO;
            break;
            case AUDIO: typeMedia = TypeMedia.AUDIO;
            break;
            case DOCUMENT: typeMedia = TypeMedia.DOCUMENT;
            break;
            case PANORAMA_360: typeMedia = TypeMedia.PANORAMA_360;
            break;
            case CARTE: typeMedia = TypeMedia.CARTE;
            break;
            case ARCHIVE_NUMERISEE: typeMedia = TypeMedia.ARCHIVE_NUMERISEE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return typeMedia;
    }
}
