package com.pgvpt.contenu.service.impl;

import com.pgvpt.contenu.client.MediaClient;
import com.pgvpt.contenu.client.PatrimoineClient;
import com.pgvpt.contenu.enums.StatutContenuMetier;
import com.pgvpt.contenu.exception.BusinessException;
import com.pgvpt.contenu.exception.ResourceNotFoundException;
import com.pgvpt.contenu.mapper.ContenuMapper;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.contenu.repository.ContenuRepository;
import com.pgvpt.contenu.service.ContenuService;
import com.pgvpt.dto.Contenu;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import com.pgvpt.dto.StatutContenu;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;



@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContenuServiceImpl implements ContenuService {

    private final ContenuRepository repository;
    private final PatrimoineClient patrimoineClient;
    private final MediaClient mediaClient;
    private final ContenuMapper mapper;

    @Override
    public List<Contenu> findByFilters(UUID patrimoineId, String langue, StatutContenu statut) {
        StatutContenuMetier statutMetier = statut != null ? mapper.toStatutMetier(statut) : null;
        String langueFiltre = (langue != null && !langue.isBlank()) ? langue.trim().toLowerCase() : null;
        List<ContenuEntity> entities = repository.findByFilters(patrimoineId, langueFiltre, statutMetier);

        return mapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Contenu getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable avec l'ID : " + id));
    }

    @Override
    @Transactional
    public Contenu create(ContenuCreate dto) {
        validerPatrimoine(dto.getPatrimoineId());
        validerMedias(dto.getMediaIds());

        ContenuEntity entity = mapper.toEntity(dto);
        entity.setStatut(StatutContenuMetier.BROUILLON);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public Contenu update(UUID id, ContenuUpdate dto) {
        ContenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable avec l'ID : " + id));

        validerPatrimoine(dto.getPatrimoineId());
        validerMedias(dto.getMediaIds());
        mapper.updateEntity(dto, entity);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contenu introuvable : " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Contenu valider(UUID id) {
        ContenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable avec l'ID : " + id));

        // Règle métier : Un contenu archivé ou déjà publié ne peut pas être re-validé directement
        if (entity.getStatut() == StatutContenuMetier.PUBLIE) {
            throw new BusinessException("Impossible de valider le contenu : il est déjà publié.");
        }
        if (entity.getStatut() == StatutContenuMetier.ARCHIVE) {
            throw new BusinessException("Impossible de valider le contenu : il est archivé.");
        }

        entity.setStatut(StatutContenuMetier.VALIDE);
        return mapper.toDto(repository.save(entity));
    }


    @Override
    @Transactional
    public Contenu publier(UUID id) {
        ContenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable avec l'ID : " + id));

        // Règle métier stricte : Seul un contenu au statut VALIDE ou EN_REVISION peut passer à l'état PUBLIE
        if (entity.getStatut() != StatutContenuMetier.VALIDE && entity.getStatut() != StatutContenuMetier.EN_REVISION) {
            throw new BusinessException("Interdiction de publier : le contenu doit d'abord être validé ou en révision. Statut actuel : " + entity.getStatut());
        }

        entity.setStatut(StatutContenuMetier.PUBLIE);
        entity.setDatePublication(LocalDateTime.now());

        return mapper.toDto(repository.save(entity));
    }


    @Override
    @Transactional
    public Contenu archiver(UUID id) {
        ContenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable avec l'ID : " + id));

        // Règle métier : L'archivage est une étape terminale destructive/historique.
        if (entity.getStatut() == StatutContenuMetier.ARCHIVE) {
            throw new BusinessException("Le contenu est déjà archivé.");
        }

        entity.setStatut(StatutContenuMetier.ARCHIVE);
        return mapper.toDto(repository.save(entity));
    }

    private void validerPatrimoine(UUID patrimoineId) {
        if (patrimoineId == null) {
            throw new BusinessException("Le champ patrimoineId est obligatoire.");
        }
        try {
            patrimoineClient.getById(patrimoineId);
        } catch (FeignException.NotFound e) {
            log.warn("Patrimoine introuvable : {}", patrimoineId);
            throw new BusinessException("Patrimoine introuvable : " + patrimoineId
                    + ". Veuillez d'abord créer le patrimoine via ms-patrimoine (port 3001).");
        } catch (FeignException e) {
            log.error("Erreur de communication avec ms-patrimoine pour le patrimoine : {}", patrimoineId, e);
            throw new BusinessException("Erreur de communication avec le service patrimoine. Vérifiez que ms-patrimoine est démarré sur le port 3001.");
        }
    }

    private void validerMedias(Set<UUID> mediaIds) {
        if (mediaIds == null || mediaIds.isEmpty()) {
            return;
        }
        for (UUID mediaId : mediaIds) {
            try {
                mediaClient.getById(mediaId);
            } catch (FeignException.NotFound e) {
                log.warn("Média introuvable : {}", mediaId);
                throw new BusinessException("Média introuvable : " + mediaId
                        + ". Veuillez d'abord créer le média via ms-media.");
            } catch (FeignException e) {
                log.error("Erreur de communication avec ms-media pour le média : {}", mediaId, e);
                throw new BusinessException("Erreur de communication avec le service média.");
            }
        }
    }
}
