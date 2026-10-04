package com.pgvpt.controller;

import com.pgvpt.api.AdministrationApi;
import com.pgvpt.dto.RolesUpdateRequest;
import com.pgvpt.dto.Utilisateur;
import com.pgvpt.dto.UtilisateurCreateRequest;
import com.pgvpt.dto.UtilisateurUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class AdministrationController implements AdministrationApi{

    @Override
    public ResponseEntity<Utilisateur> activateUser(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> createUser(UtilisateurCreateRequest utilisateurCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> deactivateUser(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> updateUser(UUID id, UtilisateurUpdateRequest utilisateurUpdateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> updateUserRoles(UUID id, RolesUpdateRequest rolesUpdateRequest) {
        return null;
    }
}
