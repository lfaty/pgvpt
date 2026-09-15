package com.pgvpt.service.impl;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.TypePatrimoine;
import com.pgvpt.dto.StatutPatrimoine;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.dto.MuseeUpdate;
import com.pgvpt.dto.MonumentUpdate;
import com.pgvpt.dto.SiteNaturelUpdate;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.MonumentEntity;
import com.pgvpt.model.MuseeEntity;
import com.pgvpt.model.PatrimoineEntity;
import com.pgvpt.model.SiteNaturelEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.JoinType;
import com.pgvpt.exception.InvalidRequestException;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatrimoineServiceImpl implements PatrimoineService {

    private final PatrimoineRepository patrimoineRepository;
    private final PatrimoineMapper patrimoineMapper;

    @Override
    public Patrimoine create(PatrimoineCreate patrimoineCreate) {
        // 1. Convertir le DTO d'entrée en entité JPA
        PatrimoineEntity entity = patrimoineMapper.toEntity(patrimoineCreate);

        // 2. Sauvegarder en base de données
        PatrimoineEntity saved = patrimoineRepository.save(entity);

        // 3. Convertir l'entité sauvegardée en DTO de réponse
        Patrimoine dto = patrimoineMapper.toDto(saved);
        
        return dto;
    }

    @Override
    public Patrimoine getPatrimoine(UUID id) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));
        Patrimoine dto = patrimoineMapper.toDto(entity);
        return dto;
    }

    @Override
    public void deletePatrimoine(UUID id) {
        PatrimoineEntity existing = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));
        patrimoineRepository.delete(existing);
    }

    @Override
    public Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate) {
        PatrimoineEntity existing = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));
        
        if (patrimoineUpdate instanceof MuseeUpdate mUpdate && existing instanceof MuseeEntity mEntity) {
            patrimoineMapper.updateMuseeEntity(mUpdate, mEntity);
        } else if (patrimoineUpdate instanceof MonumentUpdate mUpdate && existing instanceof MonumentEntity mEntity) {
            patrimoineMapper.updateMonumentEntity(mUpdate, mEntity);
        } else if (patrimoineUpdate instanceof SiteNaturelUpdate sUpdate && existing instanceof SiteNaturelEntity sEntity) {
            patrimoineMapper.updateSiteNaturelEntity(sUpdate, sEntity);
        } else {
            throw new InvalidRequestException("Incompatibilité de type ou type de patrimoine inconnu pour la mise à jour");
        }
        
        PatrimoineEntity updated = patrimoineRepository.save(existing);
        Patrimoine dto = patrimoineMapper.toDto(updated);
        
        return dto;
    }

    @Override
    public PagePatrimoine getPatrimoines(Integer page, Integer size, String sort, 
        CategoriePatrimoine categorie, TypePatrimoine type, String region, 
        String departement, String commune, StatutPatrimoine statut, 
        EtatConservation etatConservation, Boolean accessiblePublic, 
        Boolean inscritUnesco, Boolean classePatrimoine, String q) {
        int requestedPage = page == null ? 0 : page;
        int requestedSize = size == null ? 20 : size;
        if (requestedPage < 0 || requestedSize < 1) {
            throw new InvalidRequestException("Les paramètres page et size doivent être positifs");
        }
        if (region != null || departement != null || commune != null) {
            throw new InvalidRequestException("Les filtres de localisation ne sont pas disponibles tant que le modèle ne persiste pas la localisation");
        }

        Pageable pageable = PageRequest.of(requestedPage, requestedSize, parseSort(sort));
        Specification<PatrimoineEntity> specification = (root, query, cb) -> cb.conjunction();
        if (categorie != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("categorie"), categorie.getValue()));
        if (type != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("type"), type.getValue()));
        if (statut != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("statut"), statut.getValue()));
        if (etatConservation != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("etatConservation"), etatConservation.getValue()));
        if (inscritUnesco != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("inscritUnesco"), inscritUnesco));
        if (classePatrimoine != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("classePatrimoine"), classePatrimoine));
        if (accessiblePublic != null) specification = specification.and((root, query, cb) -> cb.equal(root.join("accessibilite", JoinType.LEFT).get("accessiblePublic"), accessiblePublic));
        if (q != null && !q.isBlank()) {
            String pattern = "%" + q.trim().toLowerCase(java.util.Locale.ROOT) + "%";
            specification = specification.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("nom")), pattern),
                    cb.like(cb.lower(root.get("code")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern),
                    cb.like(cb.lower(root.get("descriptionCourte")), pattern)));
        }
        Page<PatrimoineEntity> entityPage = patrimoineRepository.findAll(specification, pageable);
        
        PagePatrimoine result = new PagePatrimoine();
        result.setContent(entityPage.getContent().stream().map(entity -> {
            Patrimoine dto = patrimoineMapper.toDto(entity);
            return dto;
        }).collect(Collectors.toList()));
        result.setPage(entityPage.getNumber());
        result.setSize(entityPage.getSize());
        result.setTotalElements(entityPage.getTotalElements());
        result.setTotalPages(entityPage.getTotalPages());
        result.setFirst(entityPage.isFirst());
        result.setLast(entityPage.isLast());
        
        return result;
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.unsorted();
        String[] parts = sort.split(",", -1);
        String property = parts[0].trim();
        if (!java.util.Set.of("code", "nom", "createdAt", "updatedAt", "dateOuverture", "statut", "etatConservation").contains(property)) {
            throw new InvalidRequestException("Champ de tri non supporté : " + property);
        }
        Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        if (parts.length > 2 || (parts.length == 2 && !"asc".equalsIgnoreCase(parts[1].trim()) && !"desc".equalsIgnoreCase(parts[1].trim()))) {
            throw new InvalidRequestException("Format de tri attendu : champ[,asc|desc]");
        }
        return Sort.by(direction, property);
    }
}
