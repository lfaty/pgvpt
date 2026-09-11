package com.pgvpt.contenu.mapper;

import com.pgvpt.contenu.enums.Statut;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.Contenu;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import com.pgvpt.dto.StatutContenu;
import com.pgvpt.dto.TypeContenu;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-11T17:00:14+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.12.1 (Oracle Corporation)"
)
@Component
public class ContenuApiMapperImpl implements ContenuApiMapper {

    @Override
    public Contenu toApi(ContenuEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Contenu contenu = new Contenu();

        contenu.setId( entity.getId() );
        contenu.setCreatedAt( map( entity.getCreatedAt() ) );
        contenu.setUpdatedAt( map( entity.getUpdatedAt() ) );
        contenu.setPatrimoineId( entity.getPatrimoineId() );
        if ( entity.getType() != null ) {
            contenu.setType( Enum.valueOf( TypeContenu.class, entity.getType() ) );
        }
        contenu.setLangue( entity.getLangue() );
        contenu.setTitre( entity.getTitre() );
        contenu.setResume( entity.getResume() );
        contenu.setCorps( entity.getCorps() );
        contenu.setAuteurActeurId( entity.getAuteurActeurId() );
        contenu.setMotsCles( mapToList( entity.getMotsCles() ) );
        contenu.setStatut( statutToStatutContenu( entity.getStatut() ) );

        return contenu;
    }

    @Override
    public List<Contenu> toApi(List<ContenuEntity> entity) {
        if ( entity == null ) {
            return null;
        }

        List<Contenu> list = new ArrayList<Contenu>( entity.size() );
        for ( ContenuEntity contenuEntity : entity ) {
            list.add( toApi( contenuEntity ) );
        }

        return list;
    }

    @Override
    public ContenuEntity toEntity(Contenu dto) {
        if ( dto == null ) {
            return null;
        }

        ContenuEntity contenuEntity = new ContenuEntity();

        contenuEntity.setId( dto.getId() );
        contenuEntity.setCreatedAt( map( dto.getCreatedAt() ) );
        contenuEntity.setUpdatedAt( map( dto.getUpdatedAt() ) );
        if ( dto.getType() != null ) {
            contenuEntity.setType( dto.getType().name() );
        }
        contenuEntity.setLangue( dto.getLangue() );
        contenuEntity.setTitre( dto.getTitre() );
        contenuEntity.setResume( dto.getResume() );
        contenuEntity.setCorps( dto.getCorps() );
        contenuEntity.setPatrimoineId( dto.getPatrimoineId() );
        contenuEntity.setAuteurActeurId( dto.getAuteurActeurId() );
        contenuEntity.setMotsCles( map( dto.getMotsCles() ) );
        contenuEntity.setStatut( toEntityEnum( dto.getStatut() ) );

        return contenuEntity;
    }

    @Override
    public ContenuEntity toEntity(ContenuCreate dto) {
        if ( dto == null ) {
            return null;
        }

        ContenuEntity contenuEntity = new ContenuEntity();

        if ( dto.getType() != null ) {
            contenuEntity.setType( dto.getType().name() );
        }
        contenuEntity.setLangue( dto.getLangue() );
        contenuEntity.setTitre( dto.getTitre() );
        contenuEntity.setResume( dto.getResume() );
        contenuEntity.setCorps( dto.getCorps() );
        contenuEntity.setPatrimoineId( dto.getPatrimoineId() );
        contenuEntity.setAuteurActeurId( dto.getAuteurActeurId() );
        contenuEntity.setMotsCles( map( dto.getMotsCles() ) );

        return contenuEntity;
    }

    @Override
    public ContenuEntity toEntity(ContenuUpdate dto) {
        if ( dto == null ) {
            return null;
        }

        ContenuEntity contenuEntity = new ContenuEntity();

        if ( dto.getType() != null ) {
            contenuEntity.setType( dto.getType().name() );
        }
        contenuEntity.setLangue( dto.getLangue() );
        contenuEntity.setTitre( dto.getTitre() );
        contenuEntity.setResume( dto.getResume() );
        contenuEntity.setCorps( dto.getCorps() );
        contenuEntity.setPatrimoineId( dto.getPatrimoineId() );
        contenuEntity.setAuteurActeurId( dto.getAuteurActeurId() );
        contenuEntity.setMotsCles( map( dto.getMotsCles() ) );

        return contenuEntity;
    }

    @Override
    public Statut toEntityEnum(StatutContenu statut) {
        if ( statut == null ) {
            return null;
        }

        Statut statut1;

        switch ( statut ) {
            case BROUILLON: statut1 = Statut.BROUILLON;
            break;
            case EN_REVISION: statut1 = Statut.EN_REVISION;
            break;
            case VALIDE: statut1 = Statut.VALIDE;
            break;
            case PUBLIE: statut1 = Statut.PUBLIE;
            break;
            case ARCHIVE: statut1 = Statut.ARCHIVE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + statut );
        }

        return statut1;
    }

    protected StatutContenu statutToStatutContenu(Statut statut) {
        if ( statut == null ) {
            return null;
        }

        StatutContenu statutContenu;

        switch ( statut ) {
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
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + statut );
        }

        return statutContenu;
    }
}
