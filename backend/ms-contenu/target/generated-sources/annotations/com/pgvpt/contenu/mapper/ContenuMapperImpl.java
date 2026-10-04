package com.pgvpt.contenu.mapper;

import com.pgvpt.contenu.enums.StatutContenuMetier;
import com.pgvpt.contenu.enums.TypeContenuMetier;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.Contenu;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import com.pgvpt.dto.StatutContenu;
import com.pgvpt.dto.TypeContenu;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-04T17:50:44+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 26.0.2.1 (Eclipse Adoptium)"
)
@Component
public class ContenuMapperImpl implements ContenuMapper {

    @Override
    public ContenuEntity toEntity(ContenuCreate dto) {
        if ( dto == null ) {
            return null;
        }

        ContenuEntity contenuEntity = new ContenuEntity();

        contenuEntity.setPatrimoineId( dto.getPatrimoineId() );
        contenuEntity.setType( toTypeMetier( dto.getType() ) );
        contenuEntity.setLangue( dto.getLangue() );
        contenuEntity.setTitre( dto.getTitre() );
        contenuEntity.setResume( dto.getResume() );
        contenuEntity.setCorps( dto.getCorps() );
        contenuEntity.setAuteurActeurId( dto.getAuteurActeurId() );
        List<String> list = dto.getMotsCles();
        if ( list != null ) {
            contenuEntity.setMotsCles( new ArrayList<String>( list ) );
        }
        Set<UUID> set = dto.getMediaIds();
        if ( set != null ) {
            contenuEntity.setMediaIds( new ArrayList<UUID>( set ) );
        }

        return contenuEntity;
    }

    @Override
    public Contenu toDto(ContenuEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Contenu contenu = new Contenu();

        contenu.setCreatedAt( instantToOffset( entity.getCreatedAt() ) );
        contenu.setUpdatedAt( instantToOffset( entity.getUpdatedAt() ) );
        contenu.setDatePublication( localDateTimeToOffset( entity.getDatePublication() ) );
        contenu.setId( entity.getId() );
        contenu.setPatrimoineId( entity.getPatrimoineId() );
        contenu.setType( toTypeDto( entity.getType() ) );
        contenu.setLangue( entity.getLangue() );
        contenu.setTitre( entity.getTitre() );
        contenu.setResume( entity.getResume() );
        contenu.setCorps( entity.getCorps() );
        contenu.setAuteurActeurId( entity.getAuteurActeurId() );
        List<String> list = entity.getMotsCles();
        if ( list != null ) {
            contenu.setMotsCles( new ArrayList<String>( list ) );
        }
        List<UUID> list1 = entity.getMediaIds();
        if ( list1 != null ) {
            contenu.setMediaIds( new LinkedHashSet<UUID>( list1 ) );
        }
        contenu.setStatut( toStatutDto( entity.getStatut() ) );

        return contenu;
    }

    @Override
    public List<Contenu> toDtoList(List<ContenuEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Contenu> list = new ArrayList<Contenu>( entities.size() );
        for ( ContenuEntity contenuEntity : entities ) {
            list.add( toDto( contenuEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(ContenuUpdate dto, ContenuEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPatrimoineId() != null ) {
            entity.setPatrimoineId( dto.getPatrimoineId() );
        }
        if ( dto.getType() != null ) {
            entity.setType( toTypeMetier( dto.getType() ) );
        }
        if ( dto.getLangue() != null ) {
            entity.setLangue( dto.getLangue() );
        }
        if ( dto.getTitre() != null ) {
            entity.setTitre( dto.getTitre() );
        }
        if ( dto.getResume() != null ) {
            entity.setResume( dto.getResume() );
        }
        if ( dto.getCorps() != null ) {
            entity.setCorps( dto.getCorps() );
        }
        if ( dto.getAuteurActeurId() != null ) {
            entity.setAuteurActeurId( dto.getAuteurActeurId() );
        }
        if ( entity.getMotsCles() != null ) {
            List<String> list = dto.getMotsCles();
            if ( list != null ) {
                entity.getMotsCles().clear();
                entity.getMotsCles().addAll( list );
            }
        }
        else {
            List<String> list = dto.getMotsCles();
            if ( list != null ) {
                entity.setMotsCles( new ArrayList<String>( list ) );
            }
        }
        if ( entity.getMediaIds() != null ) {
            Set<UUID> set = dto.getMediaIds();
            if ( set != null ) {
                entity.getMediaIds().clear();
                entity.getMediaIds().addAll( set );
            }
        }
        else {
            Set<UUID> set = dto.getMediaIds();
            if ( set != null ) {
                entity.setMediaIds( new ArrayList<UUID>( set ) );
            }
        }
    }

    @Override
    public TypeContenuMetier toTypeMetier(TypeContenu source) {
        if ( source == null ) {
            return null;
        }

        TypeContenuMetier typeContenuMetier;

        switch ( source ) {
            case ARTICLE: typeContenuMetier = TypeContenuMetier.ARTICLE;
            break;
            case DESCRIPTION: typeContenuMetier = TypeContenuMetier.DESCRIPTION;
            break;
            case HISTORIQUE: typeContenuMetier = TypeContenuMetier.HISTORIQUE;
            break;
            case RECIT: typeContenuMetier = TypeContenuMetier.RECIT;
            break;
            case TEMOIGNAGE: typeContenuMetier = TypeContenuMetier.TEMOIGNAGE;
            break;
            case GUIDE_VISITE: typeContenuMetier = TypeContenuMetier.GUIDE_VISITE;
            break;
            case ACTUALITE: typeContenuMetier = TypeContenuMetier.ACTUALITE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return typeContenuMetier;
    }

    @Override
    public TypeContenu toTypeDto(TypeContenuMetier source) {
        if ( source == null ) {
            return null;
        }

        TypeContenu typeContenu;

        switch ( source ) {
            case ARTICLE: typeContenu = TypeContenu.ARTICLE;
            break;
            case DESCRIPTION: typeContenu = TypeContenu.DESCRIPTION;
            break;
            case HISTORIQUE: typeContenu = TypeContenu.HISTORIQUE;
            break;
            case RECIT: typeContenu = TypeContenu.RECIT;
            break;
            case TEMOIGNAGE: typeContenu = TypeContenu.TEMOIGNAGE;
            break;
            case GUIDE_VISITE: typeContenu = TypeContenu.GUIDE_VISITE;
            break;
            case ACTUALITE: typeContenu = TypeContenu.ACTUALITE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return typeContenu;
    }

    @Override
    public StatutContenuMetier toStatutMetier(StatutContenu source) {
        if ( source == null ) {
            return null;
        }

        StatutContenuMetier statutContenuMetier;

        switch ( source ) {
            case BROUILLON: statutContenuMetier = StatutContenuMetier.BROUILLON;
            break;
            case EN_REVISION: statutContenuMetier = StatutContenuMetier.EN_REVISION;
            break;
            case VALIDE: statutContenuMetier = StatutContenuMetier.VALIDE;
            break;
            case PUBLIE: statutContenuMetier = StatutContenuMetier.PUBLIE;
            break;
            case ARCHIVE: statutContenuMetier = StatutContenuMetier.ARCHIVE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return statutContenuMetier;
    }

    @Override
    public StatutContenu toStatutDto(StatutContenuMetier source) {
        if ( source == null ) {
            return null;
        }

        StatutContenu statutContenu;

        switch ( source ) {
            case BROUILLON: statutContenu = StatutContenu.BROUILLON;
            break;
            case EN_REVISION: statutContenu = StatutContenu.EN_REVISION;
            break;
            case VALIDE: statutContenu = StatutContenu.VALIDE;
            break;
            case PUBLIE: statutContenu = StatutContenu.PUBLIE;
            break;
            case ARCHIVE: statutContenu = StatutContenu.ARCHIVE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + source );
        }

        return statutContenu;
    }
}
