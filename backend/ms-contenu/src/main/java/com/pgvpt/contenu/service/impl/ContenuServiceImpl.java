package com.pgvpt.contenu.service.impl;

import com.pgvpt.contenu.exception.ResourceNotFoundException;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.contenu.repository.ContenuRepository;
import com.pgvpt.dto.StatutContenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pgvpt.contenu.service.ContenuService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContenuServiceImpl implements ContenuService {

    private final ContenuRepository repository;

    public ContenuServiceImpl(ContenuRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ContenuEntity> getAll() {

        return repository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public List<ContenuEntity> findByPatrimoineIdLangueAndStatut(UUID patrimoineId, String langue, StatutContenu statut) {
        return repository.findByPatrimoineIdLangueAndStatut(patrimoineId, langue, statut);
    }


    @Override
    @Transactional(readOnly=true)
    public ContenuEntity getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable: " + id));
    }


    @Override
    public ContenuEntity create(ContenuEntity entity) {
        return repository.save(entity);
    }

    @Override
    public ContenuEntity update(UUID id, ContenuEntity entity) {
        ContenuEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable: " + id));
        entity.setId(existing.getId());
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Contenu introuvable: " + id);
        repository.deleteById(id);
    }
}
